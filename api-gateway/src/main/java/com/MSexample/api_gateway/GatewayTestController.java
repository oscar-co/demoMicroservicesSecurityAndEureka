package com.MSexample.api_gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayTestController {

    @GetMapping("/ping")
    public String test() {
        return "Gateway está respondiendo";
    }
}