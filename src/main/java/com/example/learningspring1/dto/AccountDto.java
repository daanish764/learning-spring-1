package com.example.learningspring1.dto;

import com.example.learningspring1.model.Account;

public class AccountDto {
    private Long accountId;
    private String accountType;
    private String accountStatus;

    public AccountDto(Account acct) {
        this.accountId = acct.getAccountId();
        this.accountType = String.valueOf(acct.getAccountType());
        this.accountStatus = String.valueOf(acct.getAccountStatus());
    }

    public AccountDto(Long accountId, String accountType, String accountStatus) {
        this.accountId = accountId;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
    }

    // Getters and Setters


    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }
}
