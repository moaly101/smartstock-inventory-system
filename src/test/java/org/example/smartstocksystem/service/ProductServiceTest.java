package org.example.smartstocksystem.service;

import org.example.smartstocksystem.model.Product;
import org.example.smartstocksystem.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository; // Wir simulieren die Datenbank

    @InjectMocks
    private ProductService productService; // Das "Gehirn", das wir testen

    @Test
    void testUpdateStockIncreasesValue() {
        // Arrange
        Product p = new Product();
        p.setId(1L);
        p.setStock(10);

        // Wir sagen dem Mock: "Wenn jemand nach ID 1 sucht, gib Produkt p zurück"
        when(productRepository.findById(1L)).thenReturn(Optional.of(p));
        when(productRepository.save(any(Product.class))).thenReturn(p);

        // Act
        Product updated = productService.updateStock(1L, 5);

        // Assert
        assertEquals(15, updated.getStock());
        verify(productRepository, times(1)).save(p); // Prüfen, ob save() gerufen wurde
    }
}
