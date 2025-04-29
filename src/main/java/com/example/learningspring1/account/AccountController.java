package com.example.learningspring1.account;

import com.example.learningspring1.service.BankService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
