package com.vlazma.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vlazma.Models.Category;

public interface CategoryRepository extends JpaRepository <Category, Integer> {
    
}
