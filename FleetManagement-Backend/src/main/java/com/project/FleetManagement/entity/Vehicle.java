package com.project.FleetManagement.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor

@Table(name="VEHICLE")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String licensePlate;
    private String model;
    private Double capacityKg;
    private String fuelType;        //diesel,petrol,electric
    private String maintenanceStatus;       //active,under_maintenance
    private LocalDate lastServiceDate;

    public Vehicle(String licensePlate,String model,Double capacityKg,String fuelType,String maintenanceStatus){
        this.licensePlate=licensePlate;
        this.model=model;
        this.capacityKg=capacityKg;
        this.fuelType=fuelType;
        this.maintenanceStatus=maintenanceStatus;
        this.lastServiceDate=LocalDate.now();
    }

}
