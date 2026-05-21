package com.project.FleetManagement.controller;

import com.project.FleetManagement.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.FleetManagement.entity.Vehicle;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")    //endpoints starts with api/vehivles
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    //Creating vehicle //connection: https://localhost:8080/api/vehicles
    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle){
        Vehicle savedVehicle=vehicleService.saveVehicle(vehicle);
        return new ResponseEntity<>(savedVehicle,HttpStatus.CREATED);
    }

    //Read all vehicle
    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles(){
        List<Vehicle> vehicles=vehicleService.getAllVehicle();
        return ResponseEntity.ok(vehicles);
    }

    //Read vehicle by ID
    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id){
        Vehicle vehicle=vehicleService.getVehicleById(id);
        return ResponseEntity.ok(vehicle);
    }

    //Update a vehicle
    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long id,@RequestBody Vehicle vehicle) {
        Vehicle existingVehicle = vehicleService.getVehicleById(id);
        existingVehicle.setLicensePlate(vehicle.getLicensePlate());
        existingVehicle.setModel(vehicle.getModel());
        existingVehicle.setCapacityKg(vehicle.getCapacityKg());
        existingVehicle.setFuelType(vehicle.getFuelType());
        existingVehicle.setMaintenanceStatus(vehicle.getMaintenanceStatus());
        existingVehicle.setLastServiceDate(vehicle.getLastServiceDate());

        Vehicle updatedVehicle=vehicleService.saveVehicle(existingVehicle);
        return ResponseEntity.ok(updatedVehicle);
    }

    //Delete a vehicle
    @DeleteMapping("/{id}")
    public ResponseEntity<Vehicle> deleteVehicle(@PathVariable Long id){
        vehicleService.deleteVehicle(id);

        return ResponseEntity.noContent().build();
    }



}
