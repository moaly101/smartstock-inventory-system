package org.example.smartstocksystem.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void dashboard_ShouldAccessSuccessfully_WhenUserIsAdmin() throws Exception {
        mockMvc.perform(get("/admin/dashboard").with(user("user").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(view().name("adminDashboard"));
    }

    @Test
    void dashboard_ShouldBeForbidden_WhenUserIsNotAdmin() throws Exception {
        mockMvc.perform(get("/admin/dashboard").with(user("testUSER").roles("USER")))
                .andExpect(status().isForbidden()); // Erwartet 403
    }

    @Test
    void listUsers_ShouldReturnResponse_WhenUserIsAdmin() throws Exception {
        mockMvc.perform(get("/admin/users").with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().string("Benutzerliste (nur für Admins sichtbar)"));
    }

    @Test
    void listUsers_ShouldRedirectToLogin_WhenUserNotAuthenticated() throws Exception {
        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isForbidden());
    }
}