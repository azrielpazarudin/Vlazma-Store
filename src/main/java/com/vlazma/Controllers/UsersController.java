package com.vlazma.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vlazma.Services.UsersService;

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

    @DeleteMapping("/deactivate/{id}")
    public Object deactivate(@PathVariable int id){
        return usersService.deactivateUser(id);
    }
    
}
