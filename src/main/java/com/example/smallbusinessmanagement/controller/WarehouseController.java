package com.example.smallbusinessmanagement.controller;

import com.example.smallbusinessmanagement.dto.WarehouseRequest;
import com.example.smallbusinessmanagement.dto.WarehouseResponse;
import com.example.smallbusinessmanagement.dto.WarehouseStockResponse;
import com.example.smallbusinessmanagement.model.Warehouse;
import com.example.smallbusinessmanagement.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<Warehouse> createWarehouse(@RequestBody WarehouseRequest request) {
        return ResponseEntity.ok(warehouseService.createWarehouse(request));
    }

    @GetMapping("/{id}/stock")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'ACCOUNTANT', 'EMPLOYEE')")
    public ResponseEntity<WarehouseStockResponse> getStock(@PathVariable Long id) {
        return ResponseEntity.ok(warehouseService.getWarehouseStock(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'ACCOUNTANT', 'EMPLOYEE')")
    public ResponseEntity<List<WarehouseResponse>> getAllWarehouses() {
        List<WarehouseResponse> warehouses = warehouseService.getAllWarehouses();
        return ResponseEntity.ok(warehouses);
    }

}

