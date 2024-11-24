package com.example.learningspring1.controller;

import com.example.learningspring1.dto.AccountDto;
import com.example.learningspring1.model.Account;
import com.example.learningspring1.service.BankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final BankService bankService;

    public AccountController(BankService bankService) {
        this.bankService = bankService;
    }


    @GetMapping
    public  List<AccountDto>  getUserAccounts(Principal principal) {
        String username = principal.getName();
        List<Account> accounts = bankService.getAccountsForUser(username);

        // Convert entities to DTOs
        return accounts.stream()
                .map(AccountDto::new)
                .toList();
    }
}
