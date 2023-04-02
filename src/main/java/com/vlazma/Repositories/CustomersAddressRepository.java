package com.vlazma.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vlazma.Models.CustomerAddress;
import com.vlazma.Models.CustomerAddressId;

public interface CustomersAddressRepository extends JpaRepository<CustomerAddress,CustomerAddressId> {
}
