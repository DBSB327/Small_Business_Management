package com.example.smallbusinessmanagement.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String phone;

    @OneToMany(mappedBy = "customer")
    private List<Sale> purchases;

    @Column(nullable = false, unique = true)
    private String email;
}