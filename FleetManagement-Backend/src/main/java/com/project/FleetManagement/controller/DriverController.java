package com.project.FleetManagement.controller;

import com.project.FleetManagement.entity.Driver;
import com.project.FleetManagement.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/drivers")
public class DriverController {

    @Autowired
    private DriverService driverService;

    //Create Driver
    @PostMapping
    public ResponseEntity<Driver> createDriver(@RequestBody Driver driver){
    Driver savedDriver=driverService.saveDriver(driver);
    return new ResponseEntity<>(savedDriver, HttpStatus.CREATED);
    }

    //Get All Driver
    @GetMapping
    public ResponseEntity<List<Driver>> getAllDriver(){
        List<Driver> driver=driverService.getAllDrivers();
        return ResponseEntity.ok(driver);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Driver> getDriverById(@PathVariable Long id){
        Driver driver=driverService.getDriverById(id);
        return ResponseEntity.ok(driver);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Driver> updateDriver(@PathVariable Long id,@RequestBody Driver driver){
        Driver existingDriver=driverService.getDriverById(id);
        existingDriver.setName(driver.getName());
        existingDriver.setLicenseNumber(driver.getLicenseNumber());
        existingDriver.setShiftStart(driver.getShiftStart());
        existingDriver.setShiftEnd(driver.getShiftEnd());
        existingDriver.setPhone(driver.getPhone());
        existingDriver.setStatus(driver.getStatus());

        Driver updateDriver=driverService.saveDriver(existingDriver);

        return ResponseEntity.ok(updateDriver);
    }

    //Delete Driver
    @DeleteMapping("/{id}")
    public ResponseEntity<Driver> deleteDriver(@PathVariable Long id){
        driverService.deleteDriver(id);
        return ResponseEntity.noContent().build();

    }

}

