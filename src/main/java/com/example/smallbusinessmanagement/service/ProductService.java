package com.example.smallbusinessmanagement.service;

import com.example.smallbusinessmanagement.dto.ProductRequest;
import com.example.smallbusinessmanagement.dto.ProductResponse;
import com.example.smallbusinessmanagement.dto.WarehouseResponse;
import com.example.smallbusinessmanagement.enums.Color;
import com.example.smallbusinessmanagement.enums.ProductCategory;
import com.example.smallbusinessmanagement.enums.Size;
import com.example.smallbusinessmanagement.mapper.ProductMapper;
import com.example.smallbusinessmanagement.model.Product;
import com.example.smallbusinessmanagement.model.Warehouse;
import com.example.smallbusinessmanagement.repo.ProductRepository;
import com.example.smallbusinessmanagement.repo.WarehouseRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductResponse createProduct(ProductRequest request, Long warehouseId) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new EntityNotFoundException("Склад не найден"));

        Product product = new Product();
        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setSize(request.getSize());
        product.setColor(request.getColor());
        product.setPurchasePrice(request.getPurchasePrice());
        product.setSellingPrice(request.getSellingPrice());
        product.setWarehouse(warehouse);
        product.setStock(0);

        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    private ProductResponse mapToResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setCategory(product.getCategory());
        response.setSize(product.getSize());
        response.setColor(product.getColor());
        response.setSellingPrice(product.getSellingPrice());
        response.setStock(product.getStock());
        Warehouse warehouse = product.getWarehouse();
        WarehouseResponse warehouseResponse = new WarehouseResponse();
        warehouseResponse.setId(warehouse.getId());
        warehouseResponse.setName(warehouse.getName());
        warehouseResponse.setAddress(warehouse.getAddress());

        response.setWarehouse(warehouseResponse);
        return response;
    }

    public List<ProductResponse> findByFilters(ProductCategory category, Size size, Color color) {
        List<Product> products = productRepository.findByFilters(category, size, color);
        return products.stream()
                .map(productMapper::toResponse)
                .toList();
    }


    public List<ProductResponse> getLowStockProducts(int threshold) {
        List<Product> products = productRepository.findByStockLessThan(threshold);
        return products.stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Transactional
    public ProductResponse updateProductPrice(Long productId, BigDecimal newPrice) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Товар не найден"));
        product.setSellingPrice(newPrice);
        Product updatedProduct = productRepository.save(product);
        return productMapper.toResponse(updatedProduct);
    }
}