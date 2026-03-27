package org.example.smartstocksystem.controller;

import org.example.smartstocksystem.repository.ProductRepository;
import org.example.smartstocksystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductWebController {

    @Autowired
    private ProductService productService;

    @GetMapping("/inventory")
    public String getInventory(Model model) {
        // TDD RED: Wir geben "wrong-view" zurück.
        // Der Test erwartet aber "inventory", also wird er fehlschlagen.
        return "wrong-view";
    }
}
