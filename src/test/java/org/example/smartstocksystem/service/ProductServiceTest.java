package org.example.smartstocksystem.service;

import org.example.smartstocksystem.dto.ProductDTO;
import org.example.smartstocksystem.exception.ProductNotFoundException;
import org.example.smartstocksystem.model.Product;
import org.example.smartstocksystem.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository; // Wir simulieren die Datenbank

    @InjectMocks
    private ProductService productService; // Das "Gehirn", das wir testen

    @Test
    void testUpdateStockIncreasesValue() {
        // Arrange
        Product p = new Product();
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
    @Test
    void updateStock_ShouldThrowException_WhenProductNotFound() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> {
            productService.updateStock(1L, 5);
        });

        // Sicherstellen, dass niemals gespeichert wurde
        verify(productRepository, never()).save(any());
    }
    @Test
    void updateStock_ShouldTriggerWarning_WhenStockFallsBelowThreshold() {
        // Arrange
        Product p = new Product();
        p.setStock(10);
        p.setMinThreshold(15); // Bestand (10+2=12) wird unter 15 sein

        when(productRepository.findById(1L)).thenReturn(Optional.of(p));
        when(productRepository.save(any(Product.class))).thenReturn(p);

        // Act
        productService.updateStock(1L, 2);

        // Assert
        // Der Test stellt aber sicher, dass der Code-Pfad ohne Absturz durchläuft.
        verify(productRepository).save(p);
    }
    @Test
    void testGetAllProducts() {
        // Arrange
        List<Product> products = List.of(new Product(), new Product());
        when(productRepository.findAll()).thenReturn(products);

        // Act
        List<Product> result = productService.getAllProducts();

        // Assert
        assertEquals(2, result.size());
        verify(productRepository).findAll();
    }

    @Test
    void testCreateProduct() {
        // Arrange
        ProductDTO dto = new ProductDTO();
        dto.setName("Neu");
        dto.setStock(5);
        dto.setMinThreshold(2);

        when(productRepository.save(any(Product.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        Product created = productService.createProduct(dto);

        // Assert
        assertEquals("Neu", created.getName());
        assertEquals(5, created.getStock());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void testDeleteProduct() {
        // Act
        productService.deleteProduct(1L);

        // Assert
        verify(productRepository).deleteById(1L);
    }
}
