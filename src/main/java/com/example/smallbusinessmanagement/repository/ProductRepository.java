package com.example.smallbusinessmanagement.repository;

import com.example.smallbusinessmanagement.enums.Color;
import com.example.smallbusinessmanagement.enums.ProductCategory;
import com.example.smallbusinessmanagement.enums.Size;
import com.example.smallbusinessmanagement.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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

    @Query("SELECT SUM(p.stock) FROM Product p")
    Integer sumStock();

    @Query("SELECT COUNT(p) FROM Product p WHERE p.stock < :threshold")
    Long countByStockLessThan(@Param("threshold") int threshold);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.stock = 0")
    Long countByStock(int stock);

    @Query("SELECT SUM(p.stock * p.purchasePrice) FROM Product p")
    BigDecimal calculateInventoryValue();
}
