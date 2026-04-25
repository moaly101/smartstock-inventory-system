package org.example.smartstocksystem.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.example.smartstocksystem.model.Product;

public class ProductDTO {

    private Long id;

    @NotBlank(message = "Produktname darf nicht leer sein")
    private String name;

    @Min(value = 0, message = "Bestand kann nicht negativ sein")
    private int stock;

    @Min(value = 1, message = "Schwellenwert muss mindestens 1 sein")
    private int minThreshold;

    // fromEntity - was der Client zurückbekommt
    public static ProductDTO fromEntity(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setName(product.getName());
        dto.setStock(product.getStock());
        dto.setMinThreshold(product.getMinThreshold());
        return dto;
    }

    // Getter & Setter
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public int getMinThreshold() { return minThreshold; }
    public void setMinThreshold(int minThreshold) { this.minThreshold = minThreshold; }

    public ProductDTO() {}
}