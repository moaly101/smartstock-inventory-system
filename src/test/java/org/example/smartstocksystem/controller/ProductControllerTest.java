package org.example.smartstocksystem.controller;

import org.example.smartstocksystem.model.Product;
import org.example.smartstocksystem.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // Lädt den gesamten Spring-Kontext
@AutoConfigureMockMvc // Konfiguriert das MockMvc-Tool
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldGetAllProducts() throws Exception {
        // 1. Arrange: Ein Test-Produkt in die Datenbank speichern
        Product p = new Product();
        p.setName("Test-Hose");
        p.setStock(10);
        p.setMinThreshold(5);
        productRepository.save(p);

        // 2. Act & Assert: Den Endpunkt aufrufen und prüfen
        // Ersetze APPLICATION_BITSTREAM durch APPLICATION_JSON
        mockMvc.perform(get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)) // <- Hier korrigieren
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test-Hose")); // Prüfe, ob der Name im JSON stimmt
    }
}
