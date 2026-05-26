package com.project.FleetManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RouteResponseDto {
    private List<LocationDto> optimizedStops;         //sorted orders
    private Double totalDistanceKm;                   //total distance
    private Double totalTimeMin;                      //total time
    private String algorithm;                         //nearest points
}
