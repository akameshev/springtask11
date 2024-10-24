package org.project.springtask10;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.springtask10.model.User;
import org.project.springtask10.repository.UserRepository;
import org.project.springtask10.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testCreateUser() {
        User user = new User(null, "Silvia Saint", "silvia.saint@example.com");
        User savedUser = new User(1L, "Silvia Saint", "silvia.saint@example.com");

        Mockito.when(userRepository.save(user)).thenReturn(savedUser);

        User result = userService.createUser(user);

        assertNotNull(result);
        assertEquals(savedUser.getId(), result.getId());
        assertEquals(savedUser.getUsername(), result.getUsername());
        assertEquals(savedUser.getEmail(), result.getEmail());

        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void testGetUserById() {
        User user = new User(1L, "Silvia Saint", "silvia.saint@example.com");

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getEmail(), result.getEmail());

        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = Arrays.asList(
                new User(1L, "Silvia Saint", "silvia.saint@example.com"),
                new User(2L, "Pier Woodman", "pier.woodman@example.com")
        );

        Mockito.when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testUpdateUser() {
        User existingUser = new User(1L, "Silvia Saint", "john.doe@example.com");
        User updatedUser = new User(1L, "Silvia Woodman", "silvia.woodman@example.com");

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        Mockito.when(userRepository.save(existingUser)).thenReturn(updatedUser);

        User result = userService.updateUser(1L, updatedUser);

        assertNotNull(result);
        assertEquals(updatedUser.getUsername(), result.getUsername());
        assertEquals(updatedUser.getEmail(), result.getEmail());

        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(userRepository, Mockito.times(1)).save(existingUser);
    }

    @Test
    public void testDeleteUser() {
        Long userId = 1L;

        userService.deleteUser(userId);

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(userId);
    }
}
