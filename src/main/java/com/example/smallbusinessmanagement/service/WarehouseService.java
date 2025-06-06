package com.example.smallbusinessmanagement.service;

import com.example.smallbusinessmanagement.dto.ProductStockInfo;
import com.example.smallbusinessmanagement.dto.WarehouseRequest;
import com.example.smallbusinessmanagement.dto.WarehouseResponse;
import com.example.smallbusinessmanagement.dto.WarehouseStockResponse;
import com.example.smallbusinessmanagement.model.Product;
import com.example.smallbusinessmanagement.model.Warehouse;
import com.example.smallbusinessmanagement.repository.ProductRepository;
import com.example.smallbusinessmanagement.repository.WarehouseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;

    public Warehouse createWarehouse(WarehouseRequest request) {
        Warehouse warehouse = new Warehouse();
        warehouse.setName(request.getName());
        warehouse.setAddress(request.getAddress());
        return warehouseRepository.save(warehouse);
    }

    public WarehouseStockResponse getWarehouseStock(Long warehouseId) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new EntityNotFoundException("Склад не найден"));

        List<Product> products = productRepository.findByWarehouseId(warehouseId);
        List<ProductStockInfo> stockInfo = products.stream()
                .map(p -> new ProductStockInfo(
                        p.getId(),
                        p.getName(),
                        p.getStock(),
                        p.getSellingPrice()))
                .toList();

        return new WarehouseStockResponse(
                warehouse.getId(),
                warehouse.getName(),
                stockInfo
        );
    }

    public List<WarehouseResponse> getAllWarehouses() {
        List<Warehouse> warehouses = warehouseRepository.findAll();
        return warehouses.stream()
                .map(warehouse -> {
                    WarehouseResponse response = new WarehouseResponse();
                    response.setId(warehouse.getId());
                    response.setName(warehouse.getName());
                    response.setAddress(warehouse.getAddress());
                    return response;
                })
                .collect(Collectors.toList());
    }
}
