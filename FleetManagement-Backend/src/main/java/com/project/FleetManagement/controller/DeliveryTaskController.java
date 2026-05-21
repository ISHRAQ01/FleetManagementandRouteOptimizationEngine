package com.project.FleetManagement.controller;

import com.project.FleetManagement.service.DeliveryTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.project.FleetManagement.entity.*;

@RestController
@RequestMapping("api/delivery-tasks")
public class DeliveryTaskController {

    @Autowired
    private DeliveryTaskService deliveryTaskService;

    //Create a task
    @PostMapping
    public ResponseEntity<DeliveryTask> createTask(@RequestBody DeliveryTask task){
        return new ResponseEntity<>(deliveryTaskService.createDeliveryTask(task), HttpStatus.CREATED);
    }

    //Read all task
    @GetMapping
    public ResponseEntity<List<DeliveryTask>> getAllTask(){
        return ResponseEntity.ok(deliveryTaskService.getAllTasks());
    }

    //read task by id
    @GetMapping("/{id}")
    public ResponseEntity<DeliveryTask> getTaskById(@PathVariable Long id){
        return ResponseEntity.ok(deliveryTaskService.getTaskById(id));
    }

    @PutMapping("/{taskId}/assign")
    public ResponseEntity<DeliveryTask> assignTask(@PathVariable Long taskId,@RequestParam Long vehicleId,@RequestParam Long driverId){
        DeliveryTask assignedTask=deliveryTaskService.assignTask(taskId,vehicleId,driverId);
        return ResponseEntity.ok(assignedTask);
    }

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<DeliveryTask> updateTaskStatus(@PathVariable Long taskId,@RequestParam String newStatus){
        DeliveryTask deliveryTask=deliveryTaskService.updateStatus(taskId,newStatus);
        return ResponseEntity.ok(deliveryTask);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<DeliveryTask>> getTaskByStatus(@PathVariable String status){
        return ResponseEntity.ok(deliveryTaskService.getTaskByStatus(status));
    }

    //Delete Task
    @DeleteMapping("/{id}")
    public ResponseEntity<DeliveryTask> deleteTask(@PathVariable Long id){
        deliveryTaskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
