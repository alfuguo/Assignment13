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



    @GetMapping("/user/{userId}/accounts/{accountId}")
    public String viewOneUserAccount (ModelMap model, @PathVariable Long userId, @PathVariable Long accountId) {
       User user = userService .findById(userId) ;
       Account account = accountService.findById(accountId);
        model.addAttribute("user", user);
        model.addAttribute("account", account);

       return "account";
    }

    @PostMapping("/user/{userId}/accounts/{accountId}")
    public String updateAccountName (@PathVariable Long userId, @PathVariable Long accountId, @ModelAttribute Account account) {
        Account currentAccount = accountService.findById(accountId);
        currentAccount.setAccountName(account.getAccountName());
        accountService.save(currentAccount);

        userService.saveUser(userService.findById(userId));

        System.out.println(account.getAccountName());
        return "redirect:/users/" + userId + "/accounts/" + accountId;
    }



    @PostMapping("users/{userId}/accounts")
    public String postOneAccount (@PathVariable Long userId, @ModelAttribute Account account) {
        User users = userService.findById(userId);
        account.setUsers(Collections.singletonList(users));
        users.getAccounts().add(account);
        userService.saveUser(users);
        accountService.save(account);


        return "redirect:/users/" + userId;
    }
}
