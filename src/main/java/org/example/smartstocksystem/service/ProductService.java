package org.example.smartstocksystem.service;

import lombok.extern.slf4j.Slf4j;
import org.example.smartstocksystem.dto.ProductDTO;
import org.example.smartstocksystem.exception.ProductNotFoundException;
import org.example.smartstocksystem.model.Product;
import org.example.smartstocksystem.repository.ProductRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll().stream().filter(product -> !product.needsRestock()).collect(Collectors.toList());
    }

    // Neu: Ein Produkt erstellen
    public Product createProduct(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setStock(dto.getStock());
        product.setMinThreshold(dto.getMinThreshold());
        return productRepository.save(product);
    }

    public Product updateStock(Long productId, int amount) {
        return productRepository.findById(productId)
                .map(product -> {
                    product.setStock(product.getStock() + amount);
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
