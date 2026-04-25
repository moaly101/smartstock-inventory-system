package org.example.smartstocksystem.controller;

import org.example.smartstocksystem.service.JWTService;
import org.example.smartstocksystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private JWTService jwtService;

    @MockitoBean
    private UserService userService;

    @Test
    void adminShouldRedirectToDashboard() throws Exception {
        // Mocking des UserDetails und der Authentication
        UserDetails admin = User.withUsername("admin")
                .password("pw")
                .roles("ADMIN").build();
        Authentication auth = new UsernamePasswordAuthenticationToken(admin, null, admin.getAuthorities());

        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(jwtService.generateToken(any())).thenReturn("mock-jwt-token");

        mockMvc.perform(post("/auth/login-form")
                        .param("username", "admin")
                        .param("password", "pw"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/dashboard"))
                .andExpect(cookie().exists("JWT_TOKEN"))
                .andExpect(cookie().httpOnly("JWT_TOKEN", true));
    }
    @Test
    void userShouldRedirectToInventory() throws Exception {
        // Mocking des UserDetails und der Authentication
        UserDetails admin = User.withUsername("user")
                .password("us")
                .roles("USER").build();
        Authentication auth = new UsernamePasswordAuthenticationToken(admin, null, admin.getAuthorities());

        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(jwtService.generateToken(any())).thenReturn("mock-jwt-token");

        mockMvc.perform(post("/auth/login-form")
                        .param("username", "user")
                        .param("password", "us"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/inventory"))
                .andExpect(cookie().exists("JWT_TOKEN"))
                .andExpect(cookie().httpOnly("JWT_TOKEN", true));
    }
    @Test
    void login_ShouldRedirectWithError_WhenCredentialsAreInvalid() throws Exception {
        // 1. Arrange
        // Wir simulieren, dass der AuthenticationManager eine BadCredentialsException wirft
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid username or password"));

        // 2. Act & Assert
        mockMvc.perform(post("/auth/login-form")
                        .param("username", "falscherUser")
                        .param("password", "falschesPasswort"))
                .andExpect(status().is3xxRedirection()) // Erwartet einen Redirect (302)
                .andExpect(redirectedUrl("/auth/login?error")); // Prüft das Ziel inklusive Parameter

        // Verifizieren, dass der JWT-Service NIE aufgerufen wurde (Sicherheit!)
        verifyNoInteractions(jwtService);
    }
}
