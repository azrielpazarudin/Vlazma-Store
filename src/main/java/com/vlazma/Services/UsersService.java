package com.vlazma.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.vlazma.Dto.ResponseData;
import com.vlazma.Dto.Users.UsersRequest;
import com.vlazma.Dto.Users.UsersResponse;
import com.vlazma.Models.Users;
import com.vlazma.Repositories.RolesRepository;
import com.vlazma.Repositories.UsersRepository;

import java.util.Collections;
import lombok.var;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RolesRepository rolesRepository;

    public Object createUser(UsersRequest usersRequest, Errors errors) {
        ResponseData<Users> responseData = new ResponseData<>();
        if (errors.hasErrors()) {
            for (ObjectError err : errors.getAllErrors()) {
                responseData.getMessages().add(err.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.getMessages().add("Success");
        responseData.setStatus(true);

        Users user = Users.builder()
                .email(usersRequest.getEmail())
                .password(usersRequest.getPassword())
                .role(rolesRepository.findById(Integer.parseInt(usersRequest.getRoleId())).get())
                .build();
        responseData.setPayload(usersRepository.save(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
    }

    public List<UsersResponse> getAllUsers() {
        List<Users> users = usersRepository.findAll();
        return users.stream().map(this::mapToResponse).toList();
    }

    private UsersResponse mapToResponse(Users users) {
        return UsersResponse.builder()
                .id(users.getId())
                .email(users.getEmail())
                .password(users.getPassword())
                .roleId(users.getRole().getId())
                .build();
    }

    public UsersResponse findById(int id) {
        var users = usersRepository.findById(id).get();

        return UsersResponse.builder()
                .id(users.getId())
                .email(users.getEmail())
                .password(users.getPassword())
                .roleId(users.getRole().getId())
                .build();

    }

    public Object editUser(UsersRequest usersRequest, int id, Errors errors) {
        var updatedUser = usersRepository.findById(id);
        ResponseData<Users> responseData = new ResponseData<>();
        if (updatedUser.isEmpty() || errors.hasErrors()) {
            for (ObjectError err : errors.getAllErrors()) {
                responseData.getMessages().add(err.getDefaultMessage());
            }
            responseData.getMessages().add(updatedUser.isEmpty() ? "Ekskul Tidak Ditemukan" : null);
            responseData.getMessages().removeAll(Collections.singleton(null));
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        updatedUser.get().setEmail(usersRequest.getEmail());
        updatedUser.get().setPassword(usersRequest.getPassword());
        updatedUser.get().setRole(rolesRepository.findById(Integer.parseInt(usersRequest.getRoleId())).get());
        return ResponseEntity.status(HttpStatus.OK).body( usersRepository.save(updatedUser.get()));
    }

    public Object deleteUser(int id){
        Optional<Users> user = usersRepository.findById(id);
        ResponseData<Users> responseData = new ResponseData<>();
        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND);
        }
        responseData.getMessages().add("User Deleted");
        responseData.setStatus(true);
        responseData.setPayload(user.get());
        user.get().isEnabled();
        return ResponseEntity.ok(responseData);

        
        
    }
}
