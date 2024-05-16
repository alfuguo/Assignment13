package com.coderscampus.assignment13.web;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/register")
    public String getCreateUser(ModelMap model) {

        model.put("user", new User());
        model.put("address", new Address());
        return "register";
    }

    @PostMapping("/register")
    public String postCreateUser(User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }


    @GetMapping("/users")
    public String getAllUsers(ModelMap model) {
        Set<User> users = userService.findAll();
        model.put("users", users);
        if (users.size() == 1) {
            model.put("user", users.iterator().next());
        }
        return "users";
    }


    @GetMapping("/users/{userId}")
    public String getOneUser(ModelMap model, @PathVariable Long userId) {
        User user = userService.findById(userId);
        Address address = user.getAddress();
        List<Account> accounts = user.getAccounts();
        model.put("users", Arrays.asList(user));
        model.put("user", user);
        model.put("address", address);
        model.put("accounts", accounts);
        for (Account account : accounts) {
            System.out.println("accounts: " + account.getAccountName());
        }

        return "users";
    }

    @PostMapping("/users/{userId}")
    public String postOneUser(@PathVariable Long userId, User user, ModelMap model) {

        User postedUser = userService.postOneUser(userId, user, user.getAddress());
        model.addAttribute("user", postedUser);
        model.addAttribute("address", postedUser.getAddress());

        return "redirect:/users/" + postedUser.getUserId();
    }

    @PostMapping("/users/{userId}/delete")
    public String deleteOneUser(@PathVariable Long userId) {
        userService.delete(userId);
        return "redirect:/users";
    }


}
