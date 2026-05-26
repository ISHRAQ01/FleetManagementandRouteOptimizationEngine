package com.project.FleetManagement.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class RouteRequestDto {

    private Long vehicleId;                 //which vehicle
    private List<LocationDto> stops;        //all points
}
