package org.project.springtask10.controller;

import lombok.AllArgsConstructor;
import org.project.springtask10.model.User;
import org.project.springtask10.repository.UserRepository;
import org.project.springtask10.service.UserService;
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
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return ResponseEntity.ok(optionalUser.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        Optional<User> optionalUser = Optional.ofNullable(user);
        if (optionalUser.isPresent()) {
            return ResponseEntity.ok(userService.createUser(optionalUser.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(Long id, User user) {
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
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userService.deleteUser(id);
        }
        return ResponseEntity.notFound().build();
    }
}
