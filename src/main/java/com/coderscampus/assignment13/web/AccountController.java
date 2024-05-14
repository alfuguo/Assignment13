package com.coderscampus.assignment13.web;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.service.AccountService;
import com.coderscampus.assignment13.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Controller

public class AccountController {
    @Autowired
    private UserService userService;
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
    public String updateOneUserAccount (@PathVariable Long accountId, @PathVariable String accountName) {
        accountService.updateAccountNameById(accountId, accountName);
        return "redirect:/user/{userId}/accounts/{accountId}";
    }


    @PostMapping("/user/{userId}/accounts")
    public String createAccount(@PathVariable Long accountId, @RequestParam Optional<String> accountName) {
        accountService.createAccountForUser(accountId, accountName);
        return "redirect:/user/{userId}/accounts/{accountId}";
    }
}
