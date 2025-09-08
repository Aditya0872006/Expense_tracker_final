package com.example.Expense_Tracker_Backend.service;

import com.example.Expense_Tracker_Backend.dto.LoginRequest;
import com.example.Expense_Tracker_Backend.dto.SignupRequest;
import com.example.Expense_Tracker_Backend.dto.AuthResponse;
import com.example.Expense_Tracker_Backend.model.User;
import com.example.Expense_Tracker_Backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Register a new user
    public AuthResponse registerUser(SignupRequest signupRequest) {
        Optional<User> existingUser = userRepository.findByEmail(signupRequest.getEmail());
        if (existingUser.isPresent()) {
            return new AuthResponse("Email already registered!", signupRequest.getEmail(), null);
        }

        User user = new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(signupRequest.getPassword());
        userRepository.save(user);

        return new AuthResponse("Signup successful!", user.getEmail(), user.getName());
    }

    // Login
    public AuthResponse loginUser(LoginRequest loginRequest) {
        Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());

        if (userOpt.isEmpty()) {
            return new AuthResponse("User not found!", loginRequest.getEmail(), null);
        }

        User user = userOpt.get();
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            return new AuthResponse("Invalid password!", loginRequest.getEmail(), null);
        }

        return new AuthResponse("Login successful!", user.getEmail(), user.getName());
    }
}
