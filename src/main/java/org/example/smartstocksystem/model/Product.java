package org.example.smartstocksystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Der Name darf nicht leer sein")
    private String name;
    @Min(value = 0, message = "Bestand kann nicht negativ sein")
    private int stock;
    @Min(value = 1, message = "Der Schwellenwert muss mindestens 1 sein")
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
