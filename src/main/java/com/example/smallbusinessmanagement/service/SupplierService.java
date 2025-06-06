package com.example.smallbusinessmanagement.service;

import com.example.smallbusinessmanagement.dto.SupplierRequest;
import com.example.smallbusinessmanagement.dto.SupplierResponse;
import com.example.smallbusinessmanagement.model.Supplier;
import com.example.smallbusinessmanagement.repository.SupplierRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<SupplierResponse> getAllSuppliers() {
        List<Supplier> suppliers = supplierRepository.findAll();
        return suppliers.stream()
                .map(s -> new SupplierResponse(s.getId(), s.getName(), s.getContactPhone(), s.getEmail()))
                .collect(Collectors.toList());
    }
}