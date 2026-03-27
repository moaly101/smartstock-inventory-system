package org.example.smartstocksystem.model;

public class Product {
    
    private Long id;
    private String name;
    private int stock;
    private int minThreshold; // Der Schwellenwert für die Warnung
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Eine kleine "smarte" Methode für die Logik
    public boolean needsRestock() {
        return this.stock < this.minThreshold;
    }
    
    
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }


    public int getMinThreshold() {
        return minThreshold;
    }

    public void setMinThreshold(int minThreshold) {
        this.minThreshold = minThreshold;
    }


}
