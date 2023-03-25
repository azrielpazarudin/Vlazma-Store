package com.vlazma.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vlazma.Enumerations.Role;
import com.vlazma.Models.Roles;

public interface RolesRepository extends JpaRepository<Roles,Integer> {
    Optional<Roles>findByRoleName(Role role);
}
