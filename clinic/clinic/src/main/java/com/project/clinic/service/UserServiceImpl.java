package com.project.clinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.project.clinic.model.User;
import com.project.clinic.dto.UserDto;
import com.project.clinic.repository.UserRepository;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    @Override
    public User save(UserDto userDto) {
        User user = new User(
            userDto.getEmail(), 
            passwordEncoder.encode(userDto.getPassword()), 
            userDto.getFullname(),
            "USER" // Default role assigned
        );
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {  // ✅ Added missing method
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }
}
