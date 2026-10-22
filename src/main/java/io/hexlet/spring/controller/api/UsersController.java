package io.hexlet.spring.controller.api;

import io.hexlet.spring.model.User;
import io.hexlet.spring.repository.UserRepository;
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
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping // Список пользователей
    public ResponseEntity<List<User>> index(@RequestParam(defaultValue = "${app.page-size}") Integer limit) {
        List<User> users = userRepository.findAll();
        List<User> result = users.stream().limit(limit).toList();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(users.size()))
                .header("X-Source", "memory")
                .body(result);
    }

    @PostMapping// Создание пользователя
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        userRepository.save(user);
        var location = URI.create("/api/users/" + user.getId());
        return ResponseEntity.created(location).body(user);
    }

    @GetMapping("/{id}") // Вывод пользователя
    public ResponseEntity<User> show(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return ResponseEntity.of(user);
    }

    @PutMapping("/{id}") // Обновление пользователя
    public User update(@PathVariable Long id, @Valid @RequestBody User data) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        user.setFirstName(data.getFirstName());
        user.setLastName(data.getLastName());
        user.setBirthday(data.getBirthday());
        userRepository.save(user);

        return user;
    }

    @DeleteMapping("/{id}") // Удаление пользователя
    public ResponseEntity<Void> destroy(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
