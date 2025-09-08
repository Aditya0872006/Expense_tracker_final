package com.example.Expense_Tracker_Backend.service;

import com.example.Expense_Tracker_Backend.model.Transaction;
import com.example.Expense_Tracker_Backend.model.User;
import com.example.Expense_Tracker_Backend.repository.TransactionRepository;
import com.example.Expense_Tracker_Backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }


    public Transaction addTransaction(String email, Transaction transaction) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        transaction.setUser(user);
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getUserTransactions(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return transactionRepository.findByUser(user);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}
