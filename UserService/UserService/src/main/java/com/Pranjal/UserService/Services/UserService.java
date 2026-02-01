package com.Pranjal.UserService.Services;


import com.Pranjal.UserService.Entity.User;
import com.Pranjal.UserService.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }

    public User saveUser(User user) { return userRepository.save(user); }

    public Optional<User> getUserById(Long id) { return userRepository.findById(id); }

    public List<User> getAllUsers() { return userRepository.findAll(); }

    // NEW method
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }



}
