package com.project.FleetManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.project.FleetManagement.repository.DriverRepository;
import org.springframework.stereotype.Service;
import com.project.FleetManagement.entity.Driver;
import com.project.FleetManagement.exception.ResourceNotFoundException;
import java.util.List;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    // Create or update driver
    public Driver saveDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    // Get all drivers
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    // Get driver by id
    public Driver getDriverById(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver", "id", id));
    }

    // Get available drivers
    public List<Driver> getAvailableDrivers() {
        return driverRepository.findByStatus("AVAILABLE");
    }

    // Update driver status
    public Driver updateDriverStatus(Long driverId, String status) {
        Driver driver = getDriverById(driverId);
        driver.setStatus(status);
        return driverRepository.save(driver);
    }

    // Check if driver is available
    public boolean isDriverAvailable(Long driverId) {
        Driver driver = getDriverById(driverId);
        return "AVAILABLE".equals(driver.getStatus());
    }

    // Delete driver
    public void deleteDriver(Long id) {
        driverRepository.deleteById(id);
    }
}