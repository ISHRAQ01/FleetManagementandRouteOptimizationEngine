package com.project.FleetManagement.service;

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
        DeliveryTask task = getTaskById(taskId);
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + vehicleId));
        Driver driver = driverRepository.findById(driverId).orElseThrow(() -> new RuntimeException("Driver not found with id: " + driverId));

        task.setVehicle(vehicle);
        task.setDriver(driver);
        task.setStatus("ASSIGNED");
        task.setStatusUpdatedAt(LocalDateTime.now());

        return deliveryTaskRepository.save(task);
    }

    //update task status(UNASSIGNED -> DISPATCHED -> IN_TRANSIT -> DELIVERED)

    public DeliveryTask updateStatus(Long taskId,String newStatus){
        DeliveryTask task=getTaskById(taskId);
        task.setStatus(newStatus);
        task.setStatusUpdatedAt(LocalDateTime.now());

        return deliveryTaskRepository.save(task);
    }

    //Get task by status
    public List<DeliveryTask>getTaskByStatus(String status){

        return deliveryTaskRepository.findByStatus(status);
    }

    //Delete task
    public void deleteTask(Long taskId){

        deliveryTaskRepository.deleteById(taskId);
    }

}


