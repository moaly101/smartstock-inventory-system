package org.example.smartstocksystem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductTest {

    @Test
    void shouldNeedRestockWhenStockIsBelowThreshold() {
        // 1. Arrange (Vorbereiten)
        Product product = new Product();
        product.setStock(5);
        product.setMinThreshold(10);

        // 2. Act (Ausführen)
        boolean result = product.needsRestock();

        // 3. Assert (Prüfen)
        assertTrue(result, "Das Produkt sollte Nachschub benötigen, wenn der Bestand unter dem Limit liegt.");
    }
    @Test
    void shouldNotNeedRestockWhenStockIsHigh() {
        // Arrange
        Product product = new Product();
        product.setStock(20);
        product.setMinThreshold(10);

        // Act & Assert
        assertFalse(product.needsRestock(), "Das Produkt sollte KEINEN Nachschub benötigen.");
    }
    @Test
    void testNeedsRestockLogic() {
        Product p = new Product();
        p.setStock(10);
        p.setMinThreshold(10);

        // Fall 1: Bestand GENAU am Schwellenwert -> sollte false sein (oder true, je nach deiner Definition)
        // Wenn Logik: stock < minThreshold
        assertFalse(p.needsRestock(), "Bei genau 10 von 10 sollte keine Warnung kommen");

        // Fall 2: Bestand darunter
        p.setStock(9);
        assertTrue(p.needsRestock(), "Bei 9 von 10 MUSS eine Warnung kommen");
    }
}
