package com.project.FleetManagement.service;

import com.project.FleetManagement.enums.DeliveryStatus;
import com.project.FleetManagement.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.FleetManagement.repository.*;
import com.project.FleetManagement.entity.*;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeliveryTaskService {
    @Autowired
    private DeliveryTaskRepository deliveryTaskRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    // Create new delivery task
    public DeliveryTask createDeliveryTask(DeliveryTask task) {
        task.setStatus("UNASSIGNED");
        task.setStatusUpdatedAt(LocalDateTime.now());
        return deliveryTaskRepository.save(task);
    }

    //Get all tasks
    public List<DeliveryTask> getAllTasks() {
        return deliveryTaskRepository.findAll();
    }

    // Get task by ID
    public DeliveryTask getTaskById(Long id) {
        return deliveryTaskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }

    //Assign vehicle and driver to a task
    public DeliveryTask assignTask(Long taskId, Long vehicleId, Long driverId) {
        DeliveryTask task=getTaskById(taskId);

        if(!task.getStatus().equals(DeliveryStatus.UNASSIGNED.getValue())) {
            throw new BusinessException(
                    "CANNOT ASSIGN VEHICLE/DRIVER TO TASK WITH STATUS:"+task.getStatus()+" TASK MUST BE IN UNASSIGNED STATE."
            );
        }
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("VEHICLE","ID",vehicleId));
        Driver driver=driverRepository.findById(driverId)
                .orElseThrow(()-> new ResourceNotFoundException("DRIVER","ID",driverId));

        if(!"ACTIVE".equals(vehicle.getMaintenanceStatus())){
            throw new BusinessException(("VEHICLE IS NOT AVAILABLE. STATUS: "+vehicle.getMaintenanceStatus()));
        }

        if(!"AVAILABLE".equals(driver.getStatus())){
            throw  new BusinessException(("DRIVER IS NOT AVAILABLE. STATUS "+driver.getStatus()));
        }

        task.setVehicle(vehicle);
        task.setDriver(driver);
        task.setStatus(DeliveryStatus.DISPATCHED.getValue());
        task.setStatusUpdatedAt(LocalDateTime.now());


        driver.setStatus("ON_ROAD");
        driverRepository.save(driver);

        return deliveryTaskRepository.save(task);
    }


    //update task status(UNASSIGNED -> DISPATCHED -> IN_TRANSIT -> DELIVERED)

    public DeliveryTask updateStatus(Long taskId,String newStatusString){
        DeliveryTask task=getTaskById(taskId);
        String currentStatusString=task.getStatus();
        DeliveryStatus currentStatus;
        DeliveryStatus newStatus;
        try{
            currentStatus= DeliveryStatus.valueOf(currentStatusString);
            newStatus= DeliveryStatus.valueOf(newStatusString);
        }catch(IllegalArgumentException e){
            throw new BusinessException("INVALID STATUS VALUE. VALID VALUES:UNASSIGNED,DISPATCHED,IN_TRANSIT,DELIVERED");
        }
        if(!currentStatus.canTransitionTo(newStatus)){
            throw new InvalidStateTransitionException(currentStatusString,newStatusString);
        }
        if(newStatus== DeliveryStatus.DISPATCHED && (task.getVehicle()==null || task.getDriver()==null)){
            throw new BusinessException("CANNOT DISPATCH TASK WITHOUT ASSIGNED VEHILCE AND DRIVER");
        }
        if(newStatus== DeliveryStatus.DELIVERED && task.getDeliveryLat()==null){
            throw new BusinessException("CANNOT MARK AS DELIVERED WITHOUT DELIVERY COORDINATES");
        }
        task.setStatus(newStatusString);
        task.setStatusUpdatedAt(LocalDateTime.now());

        if(newStatus== DeliveryStatus.DELIVERED && task.getDriver()!=null){
            Driver driver=task.getDriver();
            List<DeliveryTask>pendingTasks=deliveryTaskRepository
                    .findByStatusAndDriver(DeliveryStatus.IN_TRANSIT.getValue(),driver);

            if(pendingTasks.isEmpty()){
                driver.setStatus("AVAILABLE");
                driverRepository.save(driver);
            }
        }

        return deliveryTaskRepository.save(task);
    }

    //Get task by status
    public List<DeliveryTask>getTaskByStatus(String status){

        return deliveryTaskRepository.findByStatus(status);
    }

    //Check driver availability for new task
    public boolean isDriverAvailable(Long driverId){
        Driver driver=driverRepository.findById(driverId)
        .orElseThrow(()->new ResourceNotFoundException("DRIVER","ID",driverId));

        if(!"AVAILABLE".equals(driver.getStatus())){
            return false;
        }
        List<DeliveryTask>activeTask=deliveryTaskRepository.findByStatusAndDriver(DeliveryStatus.IN_TRANSIT.getValue(),driver);
        return activeTask.isEmpty();
    }

    //Get task by driver
    public List<DeliveryTask> getTaskByDriver(Long driverId){
        Driver driver=driverRepository.findById(driverId)
                .orElseThrow(()->new ResourceNotFoundException("DRIVER","ID",driverId));

        return deliveryTaskRepository.findByDriver(driver);
    }

    //Delete task
    public void deleteTask(Long taskId){

        deliveryTaskRepository.deleteById(taskId);
    }

}


