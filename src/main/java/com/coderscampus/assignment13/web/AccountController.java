package com.coderscampus.assignment13.web;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.service.AccountService;
import com.coderscampus.assignment13.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


@Controller

public class AccountController {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;

    @PostMapping("/users/{userId}/accounts")
    public String postOneAccount(@PathVariable Long userId, Model model) {
        User user = userService.findById(userId);
        Account newAccount = accountService.saveAccount(userId);
        model.addAttribute("user", user);
        model.addAttribute("account", newAccount);
        return "redirect:/users/" + userId + "/accounts/" + newAccount.getAccountId();
    }


    @GetMapping("/users/{userId}/accounts/{accountId}")
    public String viewOneUserAccount(ModelMap model, @PathVariable Long userId, @PathVariable Long accountId) {
        Account account = accountService.findById(accountId);
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        model.addAttribute("account", account);
        return "account";
    }

    @PostMapping("/users/{userId}/accounts/{accountId}")
    public String updateAccountName(ModelMap model, @PathVariable Long userId, @PathVariable Long accountId, @ModelAttribute Account account) {
        Account existingAccount = accountService.findById(accountId);
        existingAccount.setAccountName(account.getAccountName());
        accountService.save(existingAccount);
        User userToUpdate = userService.findById(userId);
        userToUpdate.getAccounts().add(account);
        User updatedUser = userService.saveUser(userToUpdate);
        model.addAttribute("user", updatedUser);
        return "redirect:/users/" + userId + "/accounts/" + accountId;
    }
}
