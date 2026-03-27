package org.example.smartstocksystem.model;

import org.junit.jupiter.api.Test;

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
}
