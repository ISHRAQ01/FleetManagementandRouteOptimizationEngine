package com.project.FleetManagement.service;

import com.project.FleetManagement.entity.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.FleetManagement.repository.VehicleRepository;
import java.util.List;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    //Create or update vehicle
    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    //Get all vehicle
    public List<Vehicle> getAllVehicle() {
        return vehicleRepository.findAll();
    }

    //Get vehicle by ID
    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Vehicle not found with id" + id));
    }

    //Get vehicles by maintenance status
    public List<Vehicle> findByMaintenanceStatus(String status) {
        return vehicleRepository.findByMaintenanceStatus(status);
    }

    //Delete vehicle
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }



}
