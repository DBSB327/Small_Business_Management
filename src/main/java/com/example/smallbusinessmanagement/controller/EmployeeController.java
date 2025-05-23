package com.example.smallbusinessmanagement.controller;

import com.example.smallbusinessmanagement.dto.EmployeeRequest;
import com.example.smallbusinessmanagement.model.Employee;
import com.example.smallbusinessmanagement.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody EmployeeRequest request, Long userId) {
        Employee createdEmployee = employeeService.createEmployee(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

}