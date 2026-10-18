package io.hexlet.spring.controller.api;

import io.hexlet.spring.model.User;
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
@RequestMapping("/api/users")
public class UsersController {

    private List<User> users = new ArrayList<User>();

    @GetMapping // Список пользователей
    public ResponseEntity<List<User>> index(@RequestParam(defaultValue = "10") Integer limit) {
        var result = users.stream().limit(limit).toList();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(users.size()))
                .header("X-Source", "memory")
                .body(result);
    }

    @PostMapping// Создание пользователя
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        users.add(user);
        var location = URI.create("/api/users/" + user.getId());
        return ResponseEntity.created(location).body(user);
    }

    @GetMapping("/{id}") // Вывод пользователя
    public ResponseEntity<User> show(@PathVariable String id) {
        var user = users.stream().filter(p -> id.equals(p.getId())).findFirst();
        return ResponseEntity.of(user);
    }

    @PutMapping("/{id}") // Обновление пользователя
    public User update(@PathVariable String id, @Valid @RequestBody User data) {
        var user =
                users.stream()
                        .filter(u -> id.equals(u.getId()))
                        .findFirst()
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


        user.setName(data.getName());
        user.setEmail(data.getEmail());

        return user;
    }

    @DeleteMapping("/{id}") // Удаление пользователя
    public ResponseEntity<Void> destroy(@PathVariable String id) {
        users.removeIf(p -> id.equals(p.getId()));
        return ResponseEntity.noContent().build();
    }
}
