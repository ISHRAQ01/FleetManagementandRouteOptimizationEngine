package com.project.FleetManagement.repository;

import com.project.FleetManagement.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    //query to find driver by status
    List<Driver>findByStatus(String status);

    //query to find driver by license number
    List<Driver>findByLicenseNumber(String licenseNumber);

}
