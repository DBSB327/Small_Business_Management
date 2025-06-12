package com.example.smallbusinessmanagement.controller;

import com.example.smallbusinessmanagement.dto.CustomerInfo;
import com.example.smallbusinessmanagement.dto.CustomerRequest;
import com.example.smallbusinessmanagement.dto.CustomerSalesResponse;
import com.example.smallbusinessmanagement.model.Customer;
import com.example.smallbusinessmanagement.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CustomerRequest request) {
        Customer createdCustomer = customerService.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @GetMapping("/{id}/sales")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'ACCOUNTANT','EMPLOYEE')")
    public ResponseEntity<CustomerSalesResponse> getCustomerSales(@PathVariable Long id) {
        CustomerSalesResponse response = customerService.getCustomerSales(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'EMPLOYEE', 'ACCOUNTANT','EMPLOYEE')")
    public ResponseEntity<List<CustomerInfo>> getAllCustomers() {
        List<CustomerInfo> customerInfos = customerService.getAllCustomerInfos();
        return ResponseEntity.ok(customerInfos);
    }

}