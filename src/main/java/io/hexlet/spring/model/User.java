package io.hexlet.spring.model;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {

    @NotBlank(message = "ID обязателен")
    private String id;
    @NotBlank(message = "Имя обязательно")
    private String name;
    @NotBlank(message = "Email обязателен")
    private String email;
}
