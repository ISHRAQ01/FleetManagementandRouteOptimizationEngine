package com.project.FleetManagement.dto;

import com.project.FleetManagement.entity.DeliveryTask;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManifestDto {

    private Long manifestId;
    private LocalDateTime generatedAt;
    private String dispatcherName;
    private Long vehicleId;
    private String vehicleLicensePlate;
    private Long driverId;
    private String driverName;
    private List<DeliveryTask> tasks;
    private Integer totalStops;
    private Double totalEstimatedDistanceKm;
    private Double totalEstimatedTimeMin;


}
