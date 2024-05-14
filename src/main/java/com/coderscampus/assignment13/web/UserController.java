package com.coderscampus.assignment13.web;

import java.util.Arrays;
import java.util.Set;

import com.coderscampus.assignment13.domain.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/register")
    public String getCreateUser(ModelMap model) {

        model.put("user", new User());
        model.put("address", new Address());
        return "register";
    }

    @PostMapping("/register")
    public String postCreateUser(User user) {
        userService.save(user);
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
        model.put("users", Arrays.asList(user));
        model.put("user", user);
        return "users";
    }

    @PostMapping("/users/{userId}")
    public String postOneUser(@ModelAttribute User user) {
        userService.save(user);
        return "redirect:/users/" + user.getUserId();
    }

    @PostMapping("/users/{userId}/delete")
    public String deleteOneUser(@PathVariable Long userId) {
        userService.delete(userId);
        return "redirect:/users";
    }

    @GetMapping("/users/{userId}/accouts/{accoutId}")
    public String getAccount(@PathVariable Long userId, @PathVariable Long accoutId) {
        User user = userService.findById(userId);
        return "redirect:/users/" + user.getUserId() + "/" + accoutId;
    }
}
