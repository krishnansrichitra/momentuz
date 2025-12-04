package com.momentus.foundation.common.service;

// src/main/java/com/example/demo/service/AddressService.java


import com.momentus.foundation.common.exception.ResourceNotFoundException;
import com.momentus.foundation.common.model.Address;
import com.momentus.foundation.common.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressService {

    private final AddressRepository addressRepository;

    /** Create/save a new Address */
    public Address create(Address address) {
        return addressRepository.save(address);
    }

    /** Get an address by id or throw ResourceNotFoundException */
    @Transactional(readOnly = true)
    public Address getById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));
    }

    /** Get all addresses */
    @Transactional(readOnly = true)
    public List<Address> getAll() {
        return addressRepository.findAll();
    }

    /** Update an existing address (partial or full) */
    public Address update(Long id, Address updated) {
        Address existing = getById(id);
        // update allowed fields (null checks omitted — overwrite all fields from updated)
        existing.setAddress1(updated.getAddress1());
        existing.setAddress2(updated.getAddress2());
        existing.setCity(updated.getCity());
        existing.setState(updated.getState());
        existing.setCountry(updated.getCountry());
        existing.setZipcode(updated.getZipcode());
        existing.setPhoneNumber(updated.getPhoneNumber());
        return addressRepository.save(existing);
    }

    /** Delete by id */
    public void delete(Long id) {
        if (!addressRepository.existsById(id)) {
            throw new ResourceNotFoundException("Address not found with id: " + id);
        }
        addressRepository.deleteById(id);
    }
}
