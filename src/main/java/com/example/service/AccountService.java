package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account addAccount(Account account) {
        if (accountRepository.existsByUsername(account.getUsername())) {
            return null;
        }
        return accountRepository.save(account);
    }

    public Account login(String username, String password) {
        return accountRepository.findByUsernameAndPassword(username, password).orElse(null);
    }

    // return account with the given id or null if none existent 
    public Account getAccountById(Integer id) {
        return accountRepository.findById(id).orElse(null);
    }
}
