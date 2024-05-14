package com.coderscampus.assignment13.service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AccountRepository;
import com.coderscampus.assignment13.repository.UserRepository;
import com.coderscampus.assignment13.web.AccountController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.security.auth.login.AccountNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    UserRepository userRepository;


    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public Account findById(Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        return account.orElse(new Account());
    }


    public Account createAccountForUser(Long accountId  , Optional<String> accountName) {
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with ID: " + accountId));

        Account account = new Account();
        account.setAccountName(accountName.orElse("Default Account Name"));

        List<User> users = new ArrayList<>();
        users.add(user);
        account.setUsers(users);

        return accountRepository.save(account);
    }

    public Account updateAccountNameById(Long accountId, String accountName) {
        Account account = accountRepository.findById(accountId)
                .map(a -> {
                    a.setAccountName(accountName != null && !accountName.isBlank() ? accountName : accountName);
                    return a;
                })
                .orElseGet(() -> {
                    Account newAccount = new Account();
                    newAccount.setAccountName(accountName != null && !accountName.isBlank() ? accountName : accountName);
                    return newAccount;
                });

        return accountRepository.save(account);
    }
}




