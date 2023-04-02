package com.vlazma.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vlazma.Models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
    List<Product> findByCategoryId(int id);
    List<Product> findByCategoryName(String  name);
    List<Product> findByNameContains(String name);
}
