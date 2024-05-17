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
    final
    AccountRepository accountRepository;
    final
    UserRepository userRepository;
    private final UserService userService;


    @Autowired
    public AccountService(AccountRepository accountRepository, UserRepository userRepository, UserService userService) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }


    public Account save(Account account) {
        return accountRepository.save(account);
    }


    public Account findById(Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        return account.orElse(null);
    }

    public Account createNewAccount(Long userId) {
        User user = userService.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found for ID: " + userId);
        }
        Account account = new Account();
        account.setAccountName("New Account");
        user.getAccounts().add(account);
        account.getUsers().add(user);

        userRepository.save(user);
        return accountRepository.save(account);
    }

    public Account updateAccount(Long accountId, Account newAccountDetails, Long userId) {
        Account existingAccount = accountRepository.findById(accountId).orElse(null);
        if (existingAccount == null) {
            throw new IllegalArgumentException("Account not found for ID: " + accountId);
        }
        System.out.println("Account details: " + newAccountDetails);
        existingAccount.setAccountName(newAccountDetails.getAccountName());
//        User userToUpdate = userService.findById(userId);
//        userService.saveUser(userToUpdate);
        return accountRepository.save(existingAccount);
    }
}




