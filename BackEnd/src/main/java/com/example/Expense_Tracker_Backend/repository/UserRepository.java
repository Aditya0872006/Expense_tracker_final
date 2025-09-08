package com.example.Expense_Tracker_Backend.repository;

import com.example.Expense_Tracker_Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}