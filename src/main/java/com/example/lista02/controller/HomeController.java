package com.example.lista02.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/products/list";  // Przekierowanie z głównej strony do listy produktów
    }

}