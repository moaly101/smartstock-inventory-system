package org.example.smartstocksystem.controller;

import org.example.smartstocksystem.service.JWTService;
import org.example.smartstocksystem.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@AutoConfigureMockMvc
@WebMvcTest(ProductWebController.class)
class ProductWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    JWTService jwtService;

    @MockitoBean
    UserDetailsService userDetailsService;

    @Test
    void shouldReturnInventoryView() throws Exception {
        mockMvc.perform(get("/inventory")
                        .with(user("testUSER").roles("USER")))
                .andExpect(status().isOk()) // Prüft, ob kein 500er Fehler kommt
                .andExpect(view().name("inventory")); // Prüft, ob er die Datei "inventory.html" sucht
    }
}
