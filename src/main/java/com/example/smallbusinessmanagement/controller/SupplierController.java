package com.example.smallbusinessmanagement.controller;

import com.example.smallbusinessmanagement.dto.SupplierRequest;
import com.example.smallbusinessmanagement.model.Supplier;
import com.example.smallbusinessmanagement.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<Supplier> createSupplier(@RequestBody SupplierRequest request) {
        return ResponseEntity.ok(supplierService.createSupplier(request));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<List<Supplier>> searchSuppliers(@RequestParam String name) {
        return ResponseEntity.ok(supplierService.searchSuppliers(name));
    }

    @PutMapping("/{id}/contact")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<Supplier> updateContact(@PathVariable Long id,
                                                  @RequestParam String phone,
                                                  @RequestParam String email) {
        return ResponseEntity.ok(supplierService.updateSupplierContact(id, phone, email));
    }
}
