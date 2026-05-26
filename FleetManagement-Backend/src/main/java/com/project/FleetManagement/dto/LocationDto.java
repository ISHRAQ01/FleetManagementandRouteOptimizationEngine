package com.project.FleetManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class LocationDto {
    private String address;
    private Double lat;
    private Double lng;
}
