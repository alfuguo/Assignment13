package com.coderscampus.assignment13.web;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.service.AccountService;
import com.coderscampus.assignment13.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import java.util.Collections;
import java.util.Optional;

@Controller

public class AccountController {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;

    @PostMapping("users/{userId}/accounts")
    public String postOneAccount (@PathVariable Long userId) {
        accountService.saveAccount(userId);
        return "redirect:/users/" + userId;
    }

    @GetMapping("/user/{userId}/accounts/{accountId}")
    public String viewOneUserAccount (ModelMap model, @PathVariable Long accountId) {
        Account account = accountService.findById(accountId);
        User user = account.getUsers().get(0);
        model.addAttribute("user", user);
        model.addAttribute("account", account);

       return "account";
    }

    @PostMapping("/user/{userId}/accounts/{accountId}")
    public String updateAccountName (@PathVariable Long userId, @PathVariable Long accountId, Account account) {
        account.setAccountName(account.getAccountName());
        accountService.save(account);
        userService.saveUser(userService.findById(userId));
        return "redirect:/users/" + userId + "/accounts/" + accountId;
    }




}
