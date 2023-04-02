package com.vlazma.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vlazma.Models.Roles;
import com.vlazma.Repositories.RolesRepository;

@RestController
@RequestMapping("/vlazma/role")
public class Role {
    @Autowired
    private RolesRepository rolesRepository;

    @GetMapping("/")
    public Object getAll(){
        return rolesRepository.findAll();
    }
    @PostMapping("/")
    public Object create(@RequestBody Roles roles){
        return rolesRepository.save(roles);
    }
    
}

