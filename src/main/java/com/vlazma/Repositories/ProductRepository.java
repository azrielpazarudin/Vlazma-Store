package com.vlazma.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vlazma.Models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
    List<Product> findByAvailableAndCategoryId(int available,int id);
    List<Product> findByAvailableAndCategoryName(int available,String  name);
    List<Product> findByAvailableAndNameContains(int available,String name);
    List<Product> findByAvailable(int available);
    List<Product> findByCategoryId(int id);
}
