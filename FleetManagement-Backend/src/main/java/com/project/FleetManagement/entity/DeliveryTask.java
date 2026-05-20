package com.project.FleetManagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="DELIVERY_TASK")
@Data
@NoArgsConstructor
public class DeliveryTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNumber;
    private String pickupAdd;
    private String deliveryAdd;
    private String status;
    private Double pickupLat;
    private Double pickupLng;
    private Double deliveryLat;
    private Double deliveryLng;

    @ManyToOne
    @JoinColumn(name="vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name="driver_id")
    private Driver driver;

    private LocalDateTime statusUpdatedAt;

    public DeliveryTask(String orderNumber, String pickupAdd, String deliveryAdd, String status,Double pickupLat,Double pickupLng,Double deliveryLat,Double deliveryLng) {
        this.orderNumber = orderNumber;
        this.pickupAdd = pickupAdd;
        this.deliveryAdd = deliveryAdd;
        this.status = status;
        this.pickupLat = pickupLat;
        this.pickupLng = pickupLng;
        this.deliveryLat = deliveryLat;
        this.deliveryLng = deliveryLng;
        this.statusUpdatedAt = LocalDateTime.now();

    }



}
