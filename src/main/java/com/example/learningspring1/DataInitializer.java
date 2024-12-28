package com.example.learningspring1;

import com.example.learningspring1.config.SecSecurityConfig;
import com.example.learningspring1.model.Account;
import com.example.learningspring1.model.User;
import com.example.learningspring1.repository.AccountRepository;
import com.example.learningspring1.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.DependsOn;
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