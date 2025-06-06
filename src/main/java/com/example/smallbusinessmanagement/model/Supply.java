package com.example.smallbusinessmanagement.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "supplies")
public class Supply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User employee;

    @OneToMany(mappedBy = "supply", cascade = CascadeType.ALL)
    private List<SupplyItem> items;

    @OneToOne(mappedBy = "supply", cascade = CascadeType.ALL)
    private FinancialTransaction transaction;
}