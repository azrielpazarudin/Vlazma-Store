package com.vlazma.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vlazma.Models.Admin;

public interface AdminRepository extends JpaRepository<Admin,Integer>{
    Optional<Admin> findByUserId(int id);
}
