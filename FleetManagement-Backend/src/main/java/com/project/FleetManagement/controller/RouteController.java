package com.project.FleetManagement.controller;

import com.project.FleetManagement.dto.RouteRequestDto;
import com.project.FleetManagement.dto.RouteResponseDto;
import com.project.FleetManagement.service.RouteOptimizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/routes")
public class RouteController {
    @Autowired
    private RouteOptimizationService routeOptimizationService;

    //optimized delivery task

    @PostMapping("/optimize")
    public ResponseEntity<RouteResponseDto> optimizeRoute(@RequestBody RouteRequestDto request) {
        RouteResponseDto optimizedRoute=routeOptimizationService.optimizedRoute(request.getStops());
        return ResponseEntity.ok(optimizedRoute);
    }

}
