package com.coderscampus.assignment13.web;

import java.util.Arrays;
import java.util.Set;

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

    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;


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
        model.put("users", Arrays.asList(user));
        model.put("user", user);
        model.put("address", address);
        return "users";
    }

    @PostMapping("/users/{userId}")
    public String postOneUser(User user, Address address) throws Exception {
        User existingUser = userService.findById(user.getUserId());

        if (existingUser == null) {
            throw new Exception("User not found with id: " + user.getUserId());
        }

        existingUser.setUsername(user.getUsername());
        existingUser.setName(user.getName());

        if (!user.getPassword().isEmpty()) {
            existingUser.setPassword(user.getPassword());
        }
        Address existingAddress = existingUser.getAddress();
        if (existingAddress != null) {
            addressService.saveAllAddress(existingAddress, address);
            addressService.saveAddress(existingAddress);
        } else {
            existingUser.setAddress(address);
            addressService.saveAddress(address);
        }
        userService.saveUser(existingUser);
        return "redirect:/users/" + existingUser.getUserId();
    }

    @PostMapping("/users/{userId}/delete")
    public String deleteOneUser(@PathVariable Long userId) {
        userService.delete(userId);
        return "redirect:/users";
    }


}
