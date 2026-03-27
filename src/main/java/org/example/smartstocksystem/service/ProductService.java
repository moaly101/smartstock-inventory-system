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

        // TDD RED: Wir tun hier NICHTS mit dem Bestand.
        // Der Test, der erwartet, dass der Bestand um 'amount' steigt, wird fehlschlagen.

        return productRepository.save(product);
    }
}
