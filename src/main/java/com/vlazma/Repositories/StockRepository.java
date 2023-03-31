package com.vlazma.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vlazma.Models.Stock;

public interface StockRepository extends JpaRepository <Stock, Integer> {
    
}
