package com.project.FleetManagement.controller;

import com.project.FleetManagement.dto.ManifestDto;
import com.project.FleetManagement.service.ManifestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/manifests")
public class ManifestController {
    @Autowired
    private ManifestService manifestService;

    @GetMapping("/generate")
    public ResponseEntity<ManifestDto> generateManifest(@RequestParam Long vehicleId,@RequestParam Long driverId) {
        ManifestDto manifest=manifestService.generateManifest(vehicleId,driverId);
        return ResponseEntity.ok(manifest);
    }

}
