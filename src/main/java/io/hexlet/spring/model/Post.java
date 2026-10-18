package io.hexlet.spring.model;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Post {
    @NotBlank(message = "id обязателен")
    private String id;
    @NotBlank(message = "Заголовок обязателен")
    private String title;
    @NotBlank(message = "Текст поста обязателен")
    private String content;
    private String author;
    private LocalDateTime createdAt;

    public Post() {
        createdAt = LocalDateTime.now();
    }

}
