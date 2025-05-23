package com.example.smallbusinessmanagement.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String contactPhone;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "supplier")
    private List<Supply> supplies;
}