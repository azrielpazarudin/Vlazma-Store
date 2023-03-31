package com.vlazma.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vlazma.Models.Image;

public interface ImageRepository extends JpaRepository <Image, Integer>{
    
}
