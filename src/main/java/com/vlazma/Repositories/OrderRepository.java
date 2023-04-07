package com.vlazma.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vlazma.Models.Orders;

public interface OrderRepository extends JpaRepository<Orders,Integer>{
    
}
