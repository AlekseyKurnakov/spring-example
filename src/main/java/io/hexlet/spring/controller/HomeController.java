package io.hexlet.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Hello World!";
    }
    @GetMapping("/about")
    public String about() {
        return "Hexlet Spring Blog - учебный блог на Spring Boot. Здесь я учусь делать маршруты и контроллеры.";
    }
}
