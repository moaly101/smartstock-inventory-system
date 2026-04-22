package org.example.smartstocksystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")  // gesamter Controller nur für Admins
public class AdminController {

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        return "adminDashboard"; // → templates/adminDashboard.html
    }

    @GetMapping("/users")
    public ResponseEntity<String> listUsers() {
        return ResponseEntity.ok("Benutzerliste (nur für Admins sichtbar)");
    }
}
