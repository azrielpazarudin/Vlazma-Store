package com.vlazma.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vlazma.Models.Color;

public interface ColorRepository extends JpaRepository <Color, Integer>{
    
}
