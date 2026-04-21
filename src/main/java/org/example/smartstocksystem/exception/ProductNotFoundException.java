package org.example.smartstocksystem.exception;


import lombok.Getter;

@Getter
public class ProductNotFoundException extends RuntimeException {
    private final Long productId;

    public ProductNotFoundException(Long productId) {
        super("Produkt mit ID " + productId + " wurde nicht gefunden.");
        this.productId = productId;
    }
}
