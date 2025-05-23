package com.example.smallbusinessmanagement.controller;

import com.example.smallbusinessmanagement.dto.SupplyRequest;
import com.example.smallbusinessmanagement.dto.SupplyResponse;
import com.example.smallbusinessmanagement.service.SupplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/supplies")
@RequiredArgsConstructor
public class SupplyController {

    private final SupplyService supplyService;

    @PostMapping
    public ResponseEntity<SupplyResponse> createSupply(@RequestBody SupplyRequest request,
                                                       @RequestParam Long employeeId) {
        return ResponseEntity.ok(supplyService.createSupply(request, employeeId));
    }
}
