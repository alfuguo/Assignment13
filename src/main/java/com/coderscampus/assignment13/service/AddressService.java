package com.coderscampus.assignment13.service;

import com.coderscampus.assignment13.domain.Address;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

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
        return existingAddress;
    }


}
