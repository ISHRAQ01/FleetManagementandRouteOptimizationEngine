package com.project.FleetManagement.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="DRIVER")
public class Driver {
   @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String licenseNumber;
    private String shiftStart;
    private String shiftEnd;
    private String phone;
    private String status;

    public Driver(String name, String licenseNumber, String shiftStart, String shiftEnd, String phone, String status) {
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        this.phone = phone;
        this.status = status;
    }


}
