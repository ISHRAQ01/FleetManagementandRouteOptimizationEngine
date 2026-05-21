package com.project.FleetManagement.repository;

import com.project.FleetManagement.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    //custom query to find vehicle by maintenance status
    List<Vehicle> findByMaintenanceStatus(String status);

    //find vehicles by fuel type
    List<Vehicle> findByFuelType(String fuelType);

}
