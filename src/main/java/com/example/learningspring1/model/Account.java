package com.example.learningspring1.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashMap;

@Entity
public class Account {

    public Account() {
    }

    public Account(Long accountId, String accountNumber, AccountType accountType, Double balance, LocalDate dateOpened, AccountStatus accountStatus, LocalDate dateClosed) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.dateOpened = dateOpened;
        this.accountStatus = accountStatus;
        this.dateClosed = dateClosed;
    }

    public HashMap<String, String> toHashMapRepresentation() {
        HashMap<String, String> map = new HashMap<>();
        map.put("accountId", String.valueOf(accountId));
        map.put("accountNumber", String.valueOf(accountNumber));
        map.put("accountType", String.valueOf(accountType));
        map.put("balance", String.valueOf(balance));
        map.put("dateOpened", String.valueOf(dateOpened));
        map.put("accountStatus", String.valueOf(accountStatus));
        map.put("dateClosed", String.valueOf(dateClosed));
        return map;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(unique = true, nullable = false)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(nullable = false)
    private Double balance;

    @Column(nullable = false)
    private LocalDate dateOpened;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    private LocalDate dateClosed;

    public void setUser(User user) {
        this.user = user;
    }


    public enum AccountType {
        SAVINGS,
        CHECKING,
        CREDIT
    }

    public enum AccountStatus {
        ACTIVE,
        INACTIVE,
        CLOSED
    }


    public User getUser() {
        return user;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public LocalDate getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(LocalDate dateOpened) {
        this.dateOpened = dateOpened;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public LocalDate getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(LocalDate dateClosed) {
        this.dateClosed = dateClosed;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
