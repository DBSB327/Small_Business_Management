package com.example.smallbusinessmanagement.mapper;

import com.example.smallbusinessmanagement.dto.ProductResponse;
import com.example.smallbusinessmanagement.dto.WarehouseResponse;
import com.example.smallbusinessmanagement.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponse toResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setCategory(product.getCategory());
        response.setSize(product.getSize());
        response.setColor(product.getColor());
        response.setSellingPrice(product.getSellingPrice());
        response.setStock(product.getStock());
        WarehouseResponse warehouseResponse = new WarehouseResponse();
        warehouseResponse.setId(product.getWarehouse().getId());
        warehouseResponse.setName(product.getWarehouse().getName());
        warehouseResponse.setAddress(product.getWarehouse().getAddress());
        response.setWarehouse(warehouseResponse);
        return response;
    }
}
