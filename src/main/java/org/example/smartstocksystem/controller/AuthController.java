package org.example.smartstocksystem.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.smartstocksystem.service.JWTService;
import org.example.smartstocksystem.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.Cookie;

import java.util.Set;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    public AuthController(AuthenticationManager authenticationManager, JWTService jwtService,UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";          // → templates/login.html
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";       // → templates/register.html
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           Model model) {
        // Benutzer speichern...
        userService.registerUser(username, password, Set.of("ROLE_USER"));
        return "redirect:/auth/login?registered";
    }
    // POST /auth/login-form → einloggen, Rolle prüfen, weiterleiten
    @PostMapping("/login-form")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpServletResponse response,
                        Model model) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String token = jwtService.generateToken(userDetails);

            // JWT als Cookie setzen, damit der Browser ihn mitschickt
            Cookie cookie = new Cookie("JWT_TOKEN", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);

            // Je nach Rolle weiterleiten
            boolean isAdmin = userDetails.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

            return isAdmin
                    ? "redirect:/admin/dashboard"
                    : "redirect:/inventory";

        } catch (BadCredentialsException e) {
            return "redirect:/auth/login?error";
        }
    }
}

