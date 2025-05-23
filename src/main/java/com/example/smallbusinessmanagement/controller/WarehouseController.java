package com.example.smallbusinessmanagement.controller;

import com.example.smallbusinessmanagement.dto.WarehouseRequest;
import com.example.smallbusinessmanagement.dto.WarehouseStockResponse;
import com.example.smallbusinessmanagement.model.Warehouse;
import com.example.smallbusinessmanagement.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping
    public ResponseEntity<Warehouse> createWarehouse(@RequestBody WarehouseRequest request) {
        return ResponseEntity.ok(warehouseService.createWarehouse(request));
    }

    @GetMapping("/{id}/stock")
    public ResponseEntity<WarehouseStockResponse> getStock(@PathVariable Long id) {
        return ResponseEntity.ok(warehouseService.getWarehouseStock(id));
    }
}

