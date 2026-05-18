package com.project.FleetManagement.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="DRIVER")
public class Driver {
   @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

   @Column(nullable = false,unique = true)
    private long licenseNumber;

   @Column(nullable = false)
    private String name;

   @Column(nullable = false)
    private String phoneNumber;

    public Driver() {}

    public Driver(long licenseNumber, String name, String phoneNumber) {
        this.licenseNumber = licenseNumber;
        this.name = name;
        this.phoneNumber = phoneNumber;

    }

}
