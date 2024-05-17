package com.coderscampus.assignment13.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.coderscampus.assignment13.domain.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AccountRepository;
import com.coderscampus.assignment13.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final AddressService addressService;

    @Autowired
    public UserService(UserRepository userRepo, AddressService addressService) {
        this.userRepo = userRepo;
        this.addressService = addressService;
    }

    public Set<User> findAll() {

        return userRepo.findAllUsersWithAccountsAndAddresses();
    }

    public User findById(Long userId) {
        Optional<User> userOpt = userRepo.findById(userId);
        return userOpt.orElse(new User());
    }


    public User saveUser(User user) {
        return userRepo.save(user);
    }


    public void delete(Long userId) {

        userRepo.deleteById(userId);
    }

    public User postOneUser(Long userId, User user, Address address) {
        User existingUser = findById(userId);
        existingUser.setUsername(user.getUsername());
        existingUser.setName(user.getName());
        Address existingAddress = existingUser.getAddress();
        Address updatedAddress = addressService.saveAllAddress(existingAddress, address);
        existingUser.setAddress(updatedAddress);
        saveUser(existingUser);
        return existingUser;
    }
}
