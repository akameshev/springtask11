package org.project.springtask10.service;

import lombok.AllArgsConstructor;
import org.project.springtask10.model.User;
import org.project.springtask10.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }
    public User createUser(User user) {
        return userRepository.save(user);
    }
    public User updateUser(Long id, User user) {
        User userToUpdate = userRepository.findById(id).get();
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setEmail(user.getEmail());
        return userRepository.save(userToUpdate);
    }
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


}
