package org.example.smartstocksystem.controller;

import jakarta.validation.Valid;
import org.example.smartstocksystem.dto.ProductDTO;
import org.example.smartstocksystem.model.Product;
import org.example.smartstocksystem.repository.ProductRepository;
import org.example.smartstocksystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PreAuthorize("hasRole('ADMIN')")
    // 2. Ein neues Produkt anlegen (POST)
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO dto) {
        Product savedProduct = productService.createProduct(dto);
        return ResponseEntity.status(201).body(ProductDTO.fromEntity(savedProduct));
    }

    @PreAuthorize("hasRole('ADMIN')")
    // 3. Bestand aktualisieren (PUT)
    @PutMapping("/{id}/stock")
    public ProductDTO updateStock(@PathVariable Long id, @RequestParam int amount) {
        Product product = productService.updateStock(id, amount);
        return ProductDTO.fromEntity(product);
    }


    @PreAuthorize("hasRole('ADMIN')")
    // 4. Nur kritische Bestände anzeigen (GET)
    @GetMapping("/alerts")
    public List<Product> getLowStockProducts() {
        // Wir holen alle Produkte, wo stock < minThreshold
        // Das nutzt deine Custom Query aus dem Repository!
        return productService.getAllProducts().stream()
                .filter(Product::needsRestock)
                .toList();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
