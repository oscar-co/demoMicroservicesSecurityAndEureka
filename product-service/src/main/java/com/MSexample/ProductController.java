package com.MSexample;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping
    public List<String> getAllProducts() {
        return List.of("Laptop", "Mouse", "Keyboard");
    }
}
