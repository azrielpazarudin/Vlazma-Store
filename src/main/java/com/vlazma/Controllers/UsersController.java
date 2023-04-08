package com.vlazma.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vlazma.Dto.Users.UsersRequest;
import com.vlazma.Services.UsersService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/vlazma/user")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping("/")
    public Object getAll(){
      return  usersService.getAllUsers();
    }

    @GetMapping("/find-by-id/{id}")
    public Object getById(@PathVariable int id){
      return usersService.findById(id);
    }

    @PostMapping("/")
    public Object create(@Valid@RequestBody UsersRequest usersRequest,Errors errors){
        return usersService.createUser(usersRequest, errors);
    }

    @PostMapping("/edit/{id}")
    public Object edit(@Valid@RequestBody UsersRequest usersRequest,Errors errors,@PathVariable int id){
        return usersService.editUser(usersRequest, id, errors);
    }

    @DeleteMapping("/deactivate/{id}")
    public Object deactivate(@PathVariable int id){
        return usersService.deactivateUser(id);
    }
    
}
