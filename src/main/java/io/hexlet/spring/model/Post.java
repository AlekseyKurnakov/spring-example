package io.hexlet.spring.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;
    @NotBlank
    @Column(nullable = false)
    private String content;
    @NotBlank
    @Column(nullable = false)
    private String author;
    private LocalDateTime createdAt;
    private boolean published;

    public Post() {
        createdAt = LocalDateTime.now();
    }

}
