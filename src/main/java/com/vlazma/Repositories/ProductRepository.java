package com.vlazma.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vlazma.Models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
    
}
