package org.example.smartstocksystem.service;

import org.example.smartstocksystem.model.Product;
import org.example.smartstocksystem.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product updateStock(Long productId, int amount) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produkt nicht gefunden"));

        product.setStock(product.getStock() + amount);

        // Logik-Check:
        if (product.getStock() < product.getMinThreshold()) {
            System.out.println("WARNUNG: " + product.getName() + " muss nachbestellt werden!");
        }

        return productRepository.save(product);
    }
}
