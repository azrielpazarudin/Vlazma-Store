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

    public ResponseEntity<ResponseData<UsersResponse>> createUser(UsersRequest usersRequest, Errors errors) {
        ResponseData<UsersResponse> responseData = new ResponseData<>();
        var email = usersRepository.findByEmail(usersRequest.getEmail());
        var myRole = rolesRepository.findById(0);
        try {
            var buffer = Integer.parseInt(usersRequest.getRoleId());
            myRole = rolesRepository.findById(buffer);
        } catch (NumberFormatException e) {
        }
        if (errors.hasErrors() || email.isPresent() || myRole.isEmpty()) {
            for (ObjectError err : errors.getAllErrors()) {
                responseData.getMessages().add(err.getDefaultMessage());
            }
            responseData.getMessages().add(email.isPresent() ? "Email Is Registered" : null);
            responseData.getMessages().add(myRole.isEmpty() ? "Role Not Found" : null);
            responseData.getMessages().removeAll(Collections.singleton(null));
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        responseData.getMessages().add("Success");
        responseData.setStatus(true);
        var user = Users.builder()
                .email(usersRequest.getEmail())
                .password(usersRequest.getPassword())
                .active(1)
                .role(rolesRepository.findById(Integer.parseInt(usersRequest.getRoleId())).get())
                .build();
        usersRepository.save(user);
        responseData.setPayload(UsersResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .active(user.getActive() == 1 ? true : false)
                .roleId(user.getRole().getId())
                .build());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
    }

    public ResponseEntity<ResponseData<List<UsersResponse>>> getAllUsers() {
        List<Users> users = usersRepository.findAll();
        ResponseData<List<UsersResponse>> responseData = new ResponseData<>();
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(users.stream().map(this::mapToResponse).toList());

        return ResponseEntity.ok(responseData);
    }

    private UsersResponse mapToResponse(Users users) {
        boolean act = users.getActive() == 1 ? true : false;
        return UsersResponse.builder()
                .id(users.getId())
                .email(users.getEmail())
                .password(users.getPassword())
                .active(act)
                .roleId(users.getRole().getId())
                .build();
    }

    public ResponseEntity<ResponseData<UsersResponse>> findById(int id) {
        var users = usersRepository.findById(id);
        ResponseData<UsersResponse> responseData = new ResponseData<>();
        if (users.isEmpty()) {
            responseData.getMessages().add("User Not Found");
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(
                UsersResponse.builder()
                        .id(users.get().getId())
                        .email(users.get().getEmail())
                        .password(users.get().getPassword())
                        .roleId(users.get().getRole().getId())
                        .build());
        return ResponseEntity.ok().body(responseData);
    }

    public ResponseEntity<ResponseData<UsersResponse>> editUser(UsersRequest usersRequest, int id, Errors errors) {
        var updatedUser = usersRepository.findById(id);
        ResponseData<UsersResponse> responseData = new ResponseData<>();
        var email = usersRepository.findByEmail(usersRequest.getEmail());
        if (updatedUser.isEmpty() || errors.hasErrors()
                || (email.isPresent() && !email.get().getEmail().equals(updatedUser.get().getEmail()))) {
            for (ObjectError err : errors.getAllErrors()) {
                responseData.getMessages().add(err.getDefaultMessage());
            }
            responseData.getMessages().add(updatedUser.isEmpty() ? "User Not Found" : null);
            responseData.getMessages()
                    .add(email.isPresent() && !email.get().getEmail().equals(updatedUser.get().getEmail())
                            ? "Email Is Registered"
                            : null);
            responseData.getMessages().removeAll(Collections.singleton(null));
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        updatedUser.get().setEmail(usersRequest.getEmail());
        updatedUser.get().setPassword(usersRequest.getPassword());
        updatedUser.get().setRole(updatedUser.get().getRole());
        usersRepository.save(updatedUser.get());
        responseData.setPayload(UsersResponse.builder()
                .id(updatedUser.get().getId())
                .email(updatedUser.get().getEmail())
                .password(updatedUser.get().getPassword())
                .active(true)
                .roleId(updatedUser.get().getId()).build());
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    public ResponseEntity<ResponseData<UsersResponse>> deleteUser(int id) {
        
        System.out.println("INI ISI DARI ID : "+id);
        Optional<Users> user = usersRepository.findById(id);
        ResponseData<UsersResponse> responseData = new ResponseData<>();
        if (user.isEmpty()) {
            responseData.getMessages().add("Users Not Found");
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.getMessages().add("User Deleted");
        responseData.setStatus(true);
        user.get().setActive(0);
        usersRepository.save(user.get());
        responseData.setPayload(UsersResponse.builder()
                .id(id)
                .email(user.get().getEmail())
                .password(user.get().getPassword())
                .active(false)
                .roleId(user.get().getRole().getId())
                .build());

        return ResponseEntity.ok(responseData);
    }
}
