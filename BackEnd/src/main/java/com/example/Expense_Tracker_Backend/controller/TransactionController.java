package com.example.Expense_Tracker_Backend.controller;

import com.example.Expense_Tracker_Backend.model.Transaction;
import com.example.Expense_Tracker_Backend.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://127.0.0.1:5500")  // change to your frontend URL
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/{email}")
    public Transaction addTransaction(@PathVariable String email, @RequestBody Transaction transaction) {
        return transactionService.addTransaction(email, transaction);
    }

    @GetMapping("/{email}")
    public List<Transaction> getUserTransactions(@PathVariable String email) {
        return transactionService.getUserTransactions(email);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }
}
