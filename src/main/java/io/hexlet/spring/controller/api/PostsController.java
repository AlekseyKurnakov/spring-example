package io.hexlet.spring.controller.api;

import io.hexlet.spring.model.Post;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostsController {

    private List<Post> posts = new ArrayList<Post>();

    @GetMapping // Список постов
    public ResponseEntity<List<Post>> index(@RequestParam(defaultValue = "10") Integer limit) {
        var result = posts.stream().limit(limit).toList();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(posts.size()))
                .header("X-Source", "memory")
                .body(result);
    }

    @PostMapping // Создание поста
    public ResponseEntity<Post> create(@Valid @RequestBody Post post) {
        posts.add(post);
        var location = URI.create("/api/posts/" + post.getId());
        return ResponseEntity.created(location).body(post);
    }

    @GetMapping("/{id}") // Вывод поста
    public ResponseEntity<Post> show(@PathVariable String id) {
        var post = posts.stream().filter(p -> id.equals(p.getId())).findFirst();
        return ResponseEntity.of(post);
    }

    @PutMapping("/{id}") // Обновление поста
    public Post update(@PathVariable String id, @Valid @RequestBody Post data) {
        var post =
                posts.stream()
                        .filter(p -> id.equals(p.getId()))
                        .findFirst()
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


        post.setTitle(data.getTitle());
        post.setContent(data.getContent());

        return post;
    }

    @DeleteMapping("/{id}") // Удаление поста
    public ResponseEntity<Void> destroy(@PathVariable String id) {
        posts.removeIf(p -> id.equals(p.getId()));
        return ResponseEntity.noContent().build();
    }
}
