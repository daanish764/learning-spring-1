package com.example.learningspring1.payment;

import com.example.learningspring1.account.Account;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(nullable = false)
    private Double paymentAmount;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(nullable = false)
    private Long userId;

    public enum PaymentStatus {
        SUCCESS,
        FAILED,
        PENDING
    }
}