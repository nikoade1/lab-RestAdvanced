package com.epam.esm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomController {

    @GetMapping("/jim")
    public String hello() {
        return "SUI JIM";
    }
}
