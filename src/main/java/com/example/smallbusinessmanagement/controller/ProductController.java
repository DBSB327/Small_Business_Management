    package com.example.smallbusinessmanagement.controller;

    import com.example.smallbusinessmanagement.dto.ProductRequest;
    import com.example.smallbusinessmanagement.dto.ProductResponse;
    import com.example.smallbusinessmanagement.enums.Color;
    import com.example.smallbusinessmanagement.enums.ProductCategory;
    import com.example.smallbusinessmanagement.enums.Size;
    import com.example.smallbusinessmanagement.model.Product;
    import com.example.smallbusinessmanagement.service.ProductService;
    import jakarta.validation.Valid;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.web.bind.annotation.*;

    import java.math.BigDecimal;
    import java.util.List;

    @RestController
    @RequestMapping("/api/products")
    @RequiredArgsConstructor
    public class ProductController {
        private final ProductService productService;

        @PostMapping("/{warehouseId}")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
        public ResponseEntity<ProductResponse> createProduct(
                @Valid @RequestBody ProductRequest request,
                @PathVariable Long warehouseId
        ) {
            ProductResponse response = productService.createProduct(request, warehouseId);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        @GetMapping("/search")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'EMPLOYEE' , 'ACCOUNTANT')")
        public ResponseEntity<List<ProductResponse>> searchProducts(
                @RequestParam(required = false) ProductCategory category,
                @RequestParam(required = false) Size size,
                @RequestParam(required = false) Color color
        ) {
            List<ProductResponse> products = productService.findByFilters(category, size, color);
            return ResponseEntity.ok(products);
        }


        @GetMapping("/low-stock")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
        public ResponseEntity<List<ProductResponse>> getLowStockProducts(
                @RequestParam(defaultValue = "5") int threshold
        ) {
            List<ProductResponse> products = productService.getLowStockProducts(threshold);
            return ResponseEntity.ok(products);
        }

        @PatchMapping("/{id}/price")
        @PreAuthorize("hasAuthority('ADMIN')")
        public ResponseEntity<ProductResponse> updateProductPrice(
                @PathVariable Long id,
                @RequestParam BigDecimal newPrice
        ) {
            ProductResponse updatedProduct = productService.updateProductPrice(id, newPrice);
            return ResponseEntity.ok(updatedProduct);
        }
    }