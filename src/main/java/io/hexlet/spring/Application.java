package io.hexlet.spring;

import io.hexlet.spring.model.Post;
import jakarta.validation.Valid;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
public class Application {

    private List<Post> posts = new ArrayList<Post>();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/")
    public String home() {
        return "Hello World!";
    }
    @GetMapping("/about")
    public String about() {
        return "Hexlet Spring Blog - учебный блог на Spring Boot. Здесь я учусь делать маршруты и контроллеры.";
    }

    @GetMapping("/posts") // Список страниц
    public List<Post> index(@RequestParam(defaultValue = "10") Integer limit) {
        return posts.stream().limit(limit).toList();
    }

    @PostMapping("/posts") // Создание страницы
    public Post create(@Valid @RequestBody Post post) {

        posts.add(post);
        return post;
    }

    @GetMapping("/posts/{id}") // Вывод страницы
    public Optional<Post> show(@PathVariable String id) {
        var post = posts.stream().filter(p -> id.equals(p.getSlug())).findFirst();
        return post;
    }

    @PutMapping("/posts/{id}") // Обновление поста
    public Post update(@PathVariable String id, @Valid @RequestBody Post data) {
        var post =
                posts.stream()
                        .filter(p -> p.getSlug().equals(id))
                        .findFirst()
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


        post.setTitle(data.getTitle());
        post.setContent(data.getContent());

        return post;
    }

    @DeleteMapping("/posts/{id}") // Удаление страницы
    public void destroy(@PathVariable String id) {
        posts.removeIf(p -> id.equals(p.getSlug()));
    }
}