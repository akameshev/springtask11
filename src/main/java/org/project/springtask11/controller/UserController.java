package org.project.springtask11.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import lombok.AllArgsConstructor;
import org.project.springtask11.model.User;
import org.project.springtask11.repository.UserRepository;
import org.project.springtask11.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final Counter requestCounter = Metrics.counter("requests");

    @GetMapping
    public ResponseEntity<List<User>> getUser() {
        Optional<List<User>> optionalUsers = Optional.ofNullable(userService.getAllUsers());
        if (optionalUsers.isPresent()) {
            return ResponseEntity.ok(optionalUsers.get());
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        requestCounter.increment();
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return ResponseEntity.ok(optionalUser.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        requestCounter.increment();
        Optional<User> optionalUser = Optional.ofNullable(user);
        if (optionalUser.isPresent()) {
            return ResponseEntity.ok(userService.createUser(optionalUser.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(Long id, User user) {
        requestCounter.increment();
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User updatedUser = optionalUser.get();
            updatedUser.setUsername(user.getUsername());
            updatedUser.setEmail(user.getEmail());
            return ResponseEntity.ok(userService.updateUser(id, updatedUser));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(Long id) {
        requestCounter.increment();
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userService.deleteUser(id);
        }
        return ResponseEntity.notFound().build();
    }
}
