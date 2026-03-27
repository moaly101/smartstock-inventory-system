package org.example.smartstocksystem.controller;

import org.example.smartstocksystem.model.Product;
import org.example.smartstocksystem.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/{id}/stock")
    public ResponseEntity<Product> updateStock(@PathVariable Long id, @RequestParam int amount) {
        // TDD RED: Der Endpunkt existiert, aber wir rufen den Service NICHT auf.
        // Wir geben einfach nur "null" oder ein leeres Objekt zurück.
        return ResponseEntity.ok(null);
    }
}
