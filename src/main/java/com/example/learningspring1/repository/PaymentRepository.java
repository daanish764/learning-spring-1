package com.example.learningspring1.repository;

import com.example.learningspring1.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByAccount_AccountId(Long accountId);
}