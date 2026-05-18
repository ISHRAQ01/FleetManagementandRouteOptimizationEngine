package com.project.FleetManagement.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

   @Column(nullable = false,unique = true)
    private int vehicleNumber;

   public Vehicle() { }

    public Vehicle(int id, int vehicleNumber) {
       this.id = id;
        this.vehicleNumber = vehicleNumber;
    }




}
