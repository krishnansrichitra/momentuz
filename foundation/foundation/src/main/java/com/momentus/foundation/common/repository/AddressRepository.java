package com.momentus.foundation.common.repository;



import com.momentus.foundation.common.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    // add custom queries if needed
}

