package com.example.learningspring1;

import com.example.learningspring1.account.Account;
import com.example.learningspring1.user.User;
import com.example.learningspring1.account.AccountRepository;
import com.example.learningspring1.user.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final AccountRepository repository;
    private final UserRepository userRepository;

    public DataInitializer(AccountRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (repository.count() == 0) {

            User user = userRepository.findByUsername("admin").orElseThrow(() -> new RuntimeException("cannot find user"));

            repository.save(new Account(
                    null,
                    user,
                    "1234567890",
                    Account.AccountType.CHECKING,
                    Double.valueOf("2002.55"),
                    LocalDate.now(),
                    Account.AccountStatus.ACTIVE,
                    null));
        }
    }
}