package com.vlazma.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vlazma.Models.Sales;

public interface SalesRepository extends JpaRepository<Sales,Integer> {
    
}
