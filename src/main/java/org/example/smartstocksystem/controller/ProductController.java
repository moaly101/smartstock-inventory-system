package org.example.smartstocksystem.controller;

import jakarta.validation.Valid;
import org.example.smartstocksystem.model.Product;
import org.example.smartstocksystem.repository.ProductRepository;
import org.example.smartstocksystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    // 2. Ein neues Produkt anlegen (POST)
    @PostMapping
    public Product createProduct(@Valid @RequestBody Product product) {
        return productService.createProduct(product);
    }

    // 3. Bestand aktualisieren (PUT)
    @PutMapping("/{id}/stock")
    public Product updateStock(@PathVariable Long id, @RequestParam int amount) {
        return productService.updateStock(id, amount);
    }

    // 4. Nur kritische Bestände anzeigen (GET)
    @GetMapping("/alerts")
    public List<Product> getLowStockProducts() {
        // Wir holen alle Produkte, wo stock < minThreshold
        // Das nutzt deine Custom Query aus dem Repository!
        return productService.getAllProducts().stream()
                .filter(Product::needsRestock)
                .toList();
    }
}
