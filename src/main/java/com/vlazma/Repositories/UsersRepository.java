package com.vlazma.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vlazma.Models.Users;

public interface UsersRepository extends JpaRepository<Users,Integer>{
    Optional<Users> findByEmail(String email);
}
