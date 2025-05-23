package com.example.smallbusinessmanagement.repository;

import com.example.smallbusinessmanagement.enums.Color;
import com.example.smallbusinessmanagement.enums.ProductCategory;
import com.example.smallbusinessmanagement.enums.Size;
import com.example.smallbusinessmanagement.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByWarehouseId(Long warehouseId);

    List<Product> findByCategory(ProductCategory category);

    List<Product> findByStockLessThan(int threshold);

    @Query("SELECT p FROM Product p WHERE "
            + "(:category IS NULL OR p.category = :category) AND "
            + "(:size IS NULL OR p.size = :size) AND "
            + "(:color IS NULL OR p.color = :color)")
    List<Product> findByFilters(
            @Param("category") ProductCategory category,
            @Param("size") Size size,
            @Param("color") Color color);

//    List<Product> findBySize(Size size);
//
//    List<Product> findByColor(Color color);
//
//    List<Product> findByCategoryAndSizeAndColor(ProductCategory category, Size size, Color color);
}
