package com.project.FleetManagement.service;

import com.project.FleetManagement.dto.ManifestDto;
import com.project.FleetManagement.entity.DeliveryTask;
import com.project.FleetManagement.entity.Driver;
import com.project.FleetManagement.entity.Vehicle;
import com.project.FleetManagement.exception.ResourceNotFoundException;
import com.project.FleetManagement.repository.DeliveryTaskRepository;
import com.project.FleetManagement.repository.DriverRepository;
import com.project.FleetManagement.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ManifestService {
    @Autowired
    private DeliveryTaskRepository deliveryTaskRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private DriverRepository driverRepository;

    public ManifestDto generateManifest(Long vehicleId, Long driverId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("VEHICLE", "ID", vehicleId));
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("DRIVER", "ID", driverId));

        List<DeliveryTask> tasks = deliveryTaskRepository.findByVehicle(vehicle);
        tasks.removeIf(task -> "DELIVERED".equals(task.getStatus()));

        ManifestDto manifest = new ManifestDto();
        manifest.setManifestId(System.currentTimeMillis());
        manifest.setGeneratedAt(LocalDateTime.now());
        manifest.setDispatcherName("System");
        manifest.setVehicleId(vehicle.getId());
        manifest.setVehicleLicensePlate(vehicle.getLicensePlate());
        manifest.setDriverId(driver.getId());
        manifest.setDriverName(driver.getName());
        manifest.setTasks(tasks);
        manifest.setTotalStops(tasks.size());

        // Calculate total distance using Haversine formula
        double totalDistance = tasks.stream()
                .mapToDouble(t -> {
                    if (t.getPickupLat() != null && t.getDeliveryLat() != null) {
                        return calculateDistance(
                                t.getPickupLat(), t.getPickupLng(),
                                t.getDeliveryLat(), t.getDeliveryLng()
                        );
                    }
                    return 0;
                })
                .sum();

        manifest.setTotalEstimatedDistanceKm(totalDistance);
        manifest.setTotalEstimatedTimeMin(totalDistance * 1.5); // 1.5 min per km

        return manifest;
    }

    // Haversine formula to calculate distance between two coordinates (in KM)
    private double calculateDistance(Double lat1, Double lng1, Double lat2, Double lng2) {
        if (lat1 == null || lng1 == null || lat2 == null || lng2 == null) {
            return 0.0;
        }

        double earthRadius = 6371; // Earth's radius in kilometers

        // Convert latitude and longitude to radians
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double deltaLat = Math.toRadians(lat2 - lat1);
        double deltaLng = Math.toRadians(lng2 - lng1);

        // Haversine formula
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(deltaLng / 2) * Math.sin(deltaLng / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = earthRadius * c;

        // Round to 2 decimal places
        return Math.round(distance * 100.0) / 100.0;
    }
}