package com.project.FleetManagement.service;

import org.springframework.stereotype.Service;
import com.project.FleetManagement.dto.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class RouteOptimizationService {

    private final WebClient webClient;

    private static final String OSRM_URL = "http://router.project-osrm.org/table/v1/driving/";

    public RouteOptimizationService() {
        this.webClient = WebClient.builder().build();
    }

    // Optimizing the stops
    public RouteResponseDto optimizedRoute(List<LocationDto> stops) {
        if (stops == null || stops.size() < 2) {
            throw new RuntimeException("AT LEAST 2 STOPS REQUIRED FOR OPTIMIZATION");
        }

        // First step: get distance matrix from OSRM API
        double[][] distanceMatrix = getDistanceMatrix(stops);

        // Second step: apply nearest neighbor algorithm
        List<Integer> optimizedOrder = nearestNeighbor(distanceMatrix);

        // Third step: build response with optimized stops
        List<LocationDto> optimizedStops = new ArrayList<>();
        double totalDistance = 0.0;

        for (int i = 0; i < optimizedOrder.size(); i++) {
            int stopIndex = optimizedOrder.get(i);
            optimizedStops.add(stops.get(stopIndex));

            // Add distance between consecutive stops
            if (i < optimizedOrder.size() - 1) {
                int current = optimizedOrder.get(i);
                int next = optimizedOrder.get(i + 1);
                totalDistance += distanceMatrix[current][next];
            }
        }

        // Create new response DTO (don't use @Autowired)
        RouteResponseDto routeResponseDto = new RouteResponseDto();
        routeResponseDto.setOptimizedStops(optimizedStops);
        routeResponseDto.setTotalDistanceKm(totalDistance);
        routeResponseDto.setTotalTimeMin(totalDistance * 1.5);
        routeResponseDto.setAlgorithm("NEAREST NEIGHBOR");

        return routeResponseDto;
    }

    // Distance matrix function
    private double[][] getDistanceMatrix(List<LocationDto> stops) {
        int n = stops.size();
        double[][] matrix = new double[n][n];

        // Build coordinate string: lng,lat;lng,lat;...
        StringBuilder coordinates = new StringBuilder();
        for (LocationDto stop : stops) {
            if (coordinates.length() > 0) {
                coordinates.append(";");
            }
            coordinates.append(stop.getLng()).append(",").append(stop.getLat());
        }

        String url = OSRM_URL + coordinates.toString() + "?annotations=distance";

        try {
            // Calling OSRM API
            String response = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            matrix = parseDistanceMatrix(response, n);
        } catch (Exception e) {
            System.err.println("ERROR CALLING OSRM API: " + e.getMessage());
            matrix = getMockDistanceMatrix(stops);
        }
        return matrix;
    }

    // Parse API response
    private double[][] parseDistanceMatrix(String response, int n) {
        double[][] matrix = new double[n][n];

        try {
            int startIndex = response.indexOf("\"distances\":[[");
            if (startIndex != -1) {
                String distancesPart = response.substring(startIndex + 13);
                int endIndex = distancesPart.indexOf("]]");
                String distanceStr = distancesPart.substring(0, endIndex + 1);

                // Parse rows
                String[] rows = distanceStr.split("\\],\\[");
                for (int i = 0; i < n && i < rows.length; i++) {
                    String cleanRow = rows[i].replace("[", "").replace("]", "");
                    String[] values = cleanRow.split(",");
                    for (int j = 0; j < n && j < values.length; j++) {
                        matrix[i][j] = Double.parseDouble(values[j]) / 1000.0; // Convert meters to KM
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("ERROR PARSING MATRIX: " + e.getMessage());
            return getMockDistanceMatrix(null);
        }
        return matrix;
    }

    // Mock distance matrix for testing
    private double[][] getMockDistanceMatrix(List<LocationDto> stops) {
        int n = stops.size();
        double[][] matrix = new double[n][n];

        // Mock distances: random between 10-100 km
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    matrix[i][j] = 0;
                } else {
                    matrix[i][j] = 10 + random.nextDouble() * 90;
                }
            }
        }
        return matrix;
    }

    // Nearest Neighbor Algorithm for TSP
    private List<Integer> nearestNeighbor(double[][] distanceMatrix) {
        int n = distanceMatrix.length;
        boolean[] visited = new boolean[n];
        List<Integer> order = new ArrayList<>();

        // Start from first stop (index 0)
        int current = 0;
        order.add(current);
        visited[current] = true;

        while (order.size() < n) {
            int next = -1;
            double minDistance = Double.MAX_VALUE;

            // Find nearest unvisited stop
            for (int i = 0; i < n; i++) {
                if (!visited[i] && distanceMatrix[current][i] < minDistance) {
                    minDistance = distanceMatrix[current][i];
                    next = i;
                }
            }

            if (next != -1) {
                order.add(next);
                visited[next] = true;
                current = next;
            }
        }
        return order;
    }
}