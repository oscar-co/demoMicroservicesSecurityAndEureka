package com.MSexample.order_service;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @GetMapping
    public List<String> getAllOrders() {
        return List.of("Order #1001", "Order #1002", "Order #1003");
    }

}
