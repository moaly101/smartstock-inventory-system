package org.example.smartstocksystem.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class ProductWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnInventoryView() throws Exception {
        mockMvc.perform(get("/inventory"))
                .andExpect(status().isOk()) // Prüft, ob kein 500er Fehler kommt
                .andExpect(view().name("inventory")); // Prüft, ob er die Datei "inventory.html" sucht
    }
}
