package io.hexlet.spring.controller.api;

import io.hexlet.spring.model.Post;
import io.hexlet.spring.repository.PostRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostsController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping // Список постов
    public ResponseEntity<List<Post>> index(@RequestParam(defaultValue = "${app.page-size}") Integer limit) {

        List<Post> posts = postRepository.findAll();
        List<Post> result = posts.stream().limit(limit).toList();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(posts.size()))
                .header("X-Source", "memory")
                .body(result);
    }

    @PostMapping // Создание поста
    public ResponseEntity<Post> create(@Valid @RequestBody Post post) {
        postRepository.save(post);
        var location = URI.create("/api/posts/" + post.getId());
        return ResponseEntity.created(location).body(post);
    }

    @GetMapping("/{id}") // Вывод поста
    public ResponseEntity<Post> show(@PathVariable Long id) {
        Optional<Post> post = postRepository.findById(id);
        return ResponseEntity.of(post);
    }

    @PutMapping("/{id}") // Обновление поста
    public Post update(@PathVariable Long id, @Valid @RequestBody Post data) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        post.setTitle(data.getTitle());
        post.setContent(data.getContent());
        postRepository.save(post);

        return post;
    }

    @DeleteMapping("/{id}") // Удаление поста
    public ResponseEntity<Void> destroy(@PathVariable Long id) {
        postRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
