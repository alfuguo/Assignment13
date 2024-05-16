package com.coderscampus.assignment13.service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AccountRepository;
import com.coderscampus.assignment13.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private UserService userService;


    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public void delete(Long accountId) {
        accountRepository.deleteById(accountId);
    }

    public Account findById(Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        return account.orElse(null);
    }

    public Account saveAccount(Long userId) {
        User user = userService.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found for ID: " + userId);
        }

        Account account = new Account();
        user.getAccounts().add(account);
        account.getUsers().add(user);

        userRepository.save(user);
        return accountRepository.save(account);
    }
}




