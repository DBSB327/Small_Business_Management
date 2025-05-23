package com.example.smallbusinessmanagement.controller;

import com.example.smallbusinessmanagement.dto.SupplyRequest;
import com.example.smallbusinessmanagement.dto.SupplyResponse;
import com.example.smallbusinessmanagement.service.SupplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/supplies")
@RequiredArgsConstructor
public class SupplyController {

    private final SupplyService supplyService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<SupplyResponse> createSupply(@RequestBody SupplyRequest request,
                                                       @RequestParam Long employeeId) {
        return ResponseEntity.ok(supplyService.createSupply(request, employeeId));
    }
}
