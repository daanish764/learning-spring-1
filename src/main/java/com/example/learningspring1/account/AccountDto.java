package com.example.learningspring1.account;

public class AccountDto {
    private String accountNumber;
    private String accountType;
    private String balance;
    private String dateOpened;
    private String accountStatus;
    private String dateClosed;


    public AccountDto(Account acct) {
        this.accountNumber = String.valueOf(acct.getAccountNumber());
        this.accountType = String.valueOf(acct.getAccountType());
        this.accountStatus = String.valueOf(acct.getAccountStatus());
        this.balance = String.valueOf(acct.getBalance());
        this.dateOpened = String.valueOf(acct.getDateOpened());
        this.accountStatus = String.valueOf(acct.getAccountId());
        this.dateClosed = String.valueOf(acct.getDateClosed());

    }


    // Getters and Setters

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

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(String dateOpened) {
        this.dateOpened = dateOpened;
    }

    public String getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(String dateClosed) {
        this.dateClosed = dateClosed;
    }
}
