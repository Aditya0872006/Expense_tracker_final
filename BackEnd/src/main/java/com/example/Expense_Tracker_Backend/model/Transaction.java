package com.example.Expense_Tracker_Backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
    private double amount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
