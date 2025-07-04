package com.example.smallbusinessmanagement.controller;

import com.example.smallbusinessmanagement.dto.TaskRequest;
import com.example.smallbusinessmanagement.enums.TaskStatus;
import com.example.smallbusinessmanagement.model.Task;
import com.example.smallbusinessmanagement.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<Task> createTask(@RequestBody TaskRequest request) {
        return ResponseEntity.ok(taskService.createTask(request));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<Task> updateStatus(@PathVariable Long id,
                                             @RequestParam TaskStatus status) {
        return ResponseEntity.ok(taskService.updateTaskStatus(id, status));
    }

    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasAuthority('EMPLOYEE') and #employeeId == authentication.principal.id or hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<List<Task>> getTasksByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(taskService.getEmployeeTasks(employeeId));
    }
}

