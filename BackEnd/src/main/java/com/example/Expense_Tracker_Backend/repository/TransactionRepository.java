package com.example.Expense_Tracker_Backend.repository;

import com.example.Expense_Tracker_Backend.model.Transaction;
import com.example.Expense_Tracker_Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
}
