package com.example.smallbusinessmanagement.service;

import com.example.smallbusinessmanagement.dto.SupplierRequest;
import com.example.smallbusinessmanagement.model.Supplier;
import com.example.smallbusinessmanagement.repo.SupplierRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public Supplier createSupplier(SupplierRequest request) {
        Supplier supplier = new Supplier();
        supplier.setName(request.getName());
        supplier.setContactPhone(request.getContactPhone());
        supplier.setEmail(request.getEmail());
        return supplierRepository.save(supplier);
    }

    public List<Supplier> searchSuppliers(String searchTerm) {
        return supplierRepository.findByNameContainingIgnoreCase(searchTerm);
    }

    @Transactional
    public Supplier updateSupplierContact(Long supplierId, String newPhone, String newEmail) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new EntityNotFoundException("Поставщик не найден"));
        supplier.setContactPhone(newPhone);
        supplier.setEmail(newEmail);
        return supplierRepository.save(supplier);
    }
}