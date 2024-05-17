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
    private final UserService userService;
    private final AccountService accountService;
    @Autowired
    public AccountController(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @PostMapping("/users/{userId}/accounts")
    public String postOneAccount(@PathVariable Long userId, Model model) {
        Account newAccount = accountService.createNewAccount(userId);
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        model.addAttribute("account", newAccount);
        return "redirect:/users/" + userId + "/accounts/" + newAccount.getAccountId();
    }


    @GetMapping("/users/{userId}/accounts/{accountId}")
    public String viewOneUserAccount(ModelMap model,
                                     @PathVariable Long userId,
                                     @PathVariable Long accountId) {
        Account account = accountService.findById(accountId);
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        model.addAttribute("account", account);
        return "account";
    }

    @PostMapping("/users/{userId}/accounts/{accountId}")
    public String updateAccountName(ModelMap model,
                                    @PathVariable Long userId,
                                    @PathVariable Long accountId,
                                    @ModelAttribute("account") Account newAccountDetails) {
        Account updatedAccount = accountService.updateAccount(accountId, newAccountDetails, userId);
        model.addAttribute("account", updatedAccount);
        return "redirect:/users/" + userId + "/accounts/" + accountId;
    }
}

