package com.example.smallbusinessmanagement.model;

import com.example.smallbusinessmanagement.enums.Color;
import com.example.smallbusinessmanagement.enums.ProductCategory;
import com.example.smallbusinessmanagement.enums.Size;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Size size;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Color color;

    @Column(nullable = false)
    private BigDecimal purchasePrice; // Закупочная цена

    @Column(nullable = false)
    private BigDecimal sellingPrice; // Цена продажи

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @OneToMany(mappedBy = "product")
    private List<SupplyItem> supplyItems;

    @OneToMany(mappedBy = "product")
    private List<SaleItem> saleItems;

    @Column(nullable = false)
    private int stock;
}