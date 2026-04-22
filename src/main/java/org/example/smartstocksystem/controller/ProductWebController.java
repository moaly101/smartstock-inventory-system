package org.example.smartstocksystem.controller;

import org.example.smartstocksystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductWebController {

    @Autowired
    private ProductService productService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/inventory")
    public String getInventory(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "inventory"; // Sucht nach inventory.html in templates
    }

}
