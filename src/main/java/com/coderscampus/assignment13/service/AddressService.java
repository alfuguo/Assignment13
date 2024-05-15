package com.coderscampus.assignment13.service;
import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AddressRepository;
import com.coderscampus.assignment13.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserService userService;

    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    public Address saveAllAddress(Address existingAddress, Address newAddress) {
        if (existingAddress == null) {
            existingAddress = new Address();
        }

        existingAddress.setAddressLine1(newAddress.getAddressLine1());
        existingAddress.setAddressLine2(newAddress.getAddressLine2());
        existingAddress.setCity(newAddress.getCity());
        existingAddress.setRegion(newAddress.getRegion());
        existingAddress.setCountry(newAddress.getCountry());
        existingAddress.setZipCode(newAddress.getZipCode());
        System.out.println("AAAAAAAAAddress saved" + existingAddress);
        //addressRepository.save(existingAddress);
        return existingAddress;
    }


}
