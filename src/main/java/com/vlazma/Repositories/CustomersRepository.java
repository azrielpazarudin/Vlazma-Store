package com.vlazma.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vlazma.Models.Customers;
import java.util.Optional;
public interface CustomersRepository extends JpaRepository<Customers,Integer> {
    Optional<Customers> findByUserId(int id);
    Optional<Customers> findByUserEmail(String email);
}
