package com.project.FleetManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.project.FleetManagement.repository.DriverRepository;
import org.springframework.stereotype.Service;
import com.project.FleetManagement.entity.Driver;
import java.util.List;

@Service
public class DriverService {
    @Autowired
    private DriverRepository driverRepository;

    //create or update driver
    public Driver saveDriver(Driver driver){
        return driverRepository.save(driver);
    }

    //Get all driver
    public List<Driver> getAllDrivers(){

        return driverRepository.findAll();
    }

    //Get driver by id
    public Driver getDriverById(long id){
        return driverRepository.findById(id).orElseThrow(()->new RuntimeException("Driver Not Found with id"+id));
    }

    //Get available driver
    public List<Driver> getAvailableDrivers(String status){
        return driverRepository.findByStatus("AVAILABLE");
    }

    //Delete driver
    public void deleteDriver(Long id){
        driverRepository.deleteById(id);
    }

}
