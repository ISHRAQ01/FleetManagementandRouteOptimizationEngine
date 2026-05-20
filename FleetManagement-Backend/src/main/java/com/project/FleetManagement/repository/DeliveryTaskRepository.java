package com.project.FleetManagement.repository;

import com.project.FleetManagement.entity.Driver;
import com.project.FleetManagement.entity.Vehicle;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.FleetManagement.entity.DeliveryTask;
import java.util.List;

@Repository
public interface DeliveryTaskRepository extends JpaRepository<DeliveryTask,Long> {
    //Find all task
    List<DeliveryTask>findByStatus(String status);

    //find task assigned to specific vehicle
    List<DeliveryTask>findByVehicle(Vehicle vehicle);

    //find task assigned to specific driver
    List<DeliveryTask>findByDriver(Driver driver);

    //find task taken by staus and driver
    List<DeliveryTask>findByStatusAndDriver(String status,Driver driver);

}
