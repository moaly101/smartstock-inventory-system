package org.example.smartstocksystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @NotBlank(message = "Der Name darf nicht leer sein")
    private String name;
    @Min(value = 0, message = "Bestand kann nicht negativ sein")

    @Setter
    @Min(value = 1, message = "Stock muss mindestens 1 sein")
    private int stock;

    @Setter
    @Min(value = 1, message = "Der Schwellenwert muss mindestens 1 sein")
    private int minThreshold; // Der Schwellenwert für die Warnung


    // Eine kleine "smarte" Methode für die Logik
    public boolean needsRestock() {
        return this.stock < this.minThreshold;
    }

}
