package com.example.learningspring1.service;

import com.example.learningspring1.model.Account;
import com.example.learningspring1.model.Payment;
import com.example.learningspring1.model.User;
import com.example.learningspring1.repository.AccountRepository;
import com.example.learningspring1.repository.PaymentRepository;
import com.example.learningspring1.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {

    private final AccountRepository accountRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    public BankService(AccountRepository accountRepository, PaymentRepository paymentRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Payment recordPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public List<Payment> getPaymentsByAccountId(Long accountId) {
        return paymentRepository.findByAccount_AccountId(accountId);
    }

    public Account createAccountForUser(String username, Account account) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        account.setUser(user);
        return accountRepository.save(account);
    }

    public List<Account> getAccountsForUser(String username) {
        return accountRepository.findByUser_Username(username);
    }
}