package org.example.smartstocksystem.controller;

import jakarta.transaction.Transactional;
import org.example.smartstocksystem.model.Product;
import org.example.smartstocksystem.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // Lädt den gesamten Spring-Kontext
@AutoConfigureMockMvc // Konfiguriert das MockMvc-Tool
@ActiveProfiles("test") // Aktiviert application-test.properties
@Transactional
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }



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
                        .with(user("testadmin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)) // <- Hier korrigieren
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test-Hose")); // Prüfe, ob der Name im JSON stimmt
    }
    @Test
    void adminShouldCreateProduct() throws Exception {
        String productJson = """
        {
            "name": "Neu Produkt",
            "stock": 50,
            "minThreshold": 10
        }
        """;

        mockMvc.perform(post("/api/products")
                        .with(user("admin").roles("ADMIN")) // Erfordert statischen Import
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "USER") // Ein normaler User darf NICHT löschen
    void userShouldNotBeAllowedToDelete() throws Exception {
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturnOnlyLowStockProducts() throws Exception {
        // Arrange
        productRepository.deleteAll(); // Sicherheitshalber hier nochmal

        Product p1 = new Product();
        p1.setName("Kritisch");
        p1.setStock(2);
        p1.setMinThreshold(5);

        Product p2 = new Product();
        p2.setName("OK");
        p2.setStock(20);
        p2.setMinThreshold(5);

        productRepository.saveAll(List.of(p1, p2));

        // Act & Assert
        mockMvc.perform(get("/api/products/alerts")
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Kritisch"));
    }
}
