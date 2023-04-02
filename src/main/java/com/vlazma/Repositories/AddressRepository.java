package com.vlazma.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vlazma.Models.Address;

public interface AddressRepository extends JpaRepository<Address,Integer> {
    
}
