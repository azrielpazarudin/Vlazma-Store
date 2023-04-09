package com.vlazma.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.vlazma.Dto.ResponseData;
import com.vlazma.Dto.Users.ChangePassword;
import com.vlazma.Dto.Users.UsersResponse;
import com.vlazma.Models.Users;
import com.vlazma.Repositories.UsersRepository;

import java.util.Collections;

import lombok.RequiredArgsConstructor;
import lombok.var;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<ResponseData<List<UsersResponse>>> getAllUsers() {
        List<Users> users = usersRepository.findAll();
        ResponseData<List<UsersResponse>> responseData = new ResponseData<>();
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(users.stream().map(this::mapToResponse).toList());

        return ResponseEntity.ok(responseData);
    }

    private UsersResponse mapToResponse(Users users) {
        return UsersResponse.builder()
                .id(users.getId())
                .email(users.getEmail())
                .password(users.getPassword())
                .active(users.getActive() == 1 ? true : false)
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

    public ResponseEntity<ResponseData<?>> changePassword(int userId, ChangePassword changePassword, Errors errors) {
        var user = usersRepository.findById(userId);
        ResponseData<Object> responseData = new ResponseData<>();
        if (user.isEmpty() || errors.hasErrors()) {
            for (ObjectError err : errors.getAllErrors()) {
                responseData.getMessages().add(err.getDefaultMessage());
            }
            responseData.getMessages().add(user.isEmpty()?"User Not Found":null);
            responseData.getMessages().removeAll(Collections.singleton(null));
            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.badRequest().body(responseData);
        }
        if(!passwordEncoder.matches(changePassword.getOldPassword(),user.get().getPassword())){
            responseData.getMessages().add("Your Old Password Is Not Match"+user.get().getUsername()+changePassword.getOldPassword());
            responseData.getMessages().removeAll(Collections.singleton(null));
            responseData.setStatus(false);
            responseData.setPayload(null);

            return ResponseEntity.badRequest().body(responseData);
        }
        user.get().setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
        usersRepository.save(user.get());
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload("Your Password Is Up To Date");
        return ResponseEntity.ok(responseData);
    }

    public ResponseEntity<ResponseData<UsersResponse>> deactivateUser(int id) {

        Optional<Users> user = usersRepository.findById(id);
        ResponseData<UsersResponse> responseData = new ResponseData<>();
        if (user.isEmpty()) {
            responseData.getMessages().add("Users Not Found");
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.getMessages()
                .add("User Is Deactivated, Now This Account No Longer Able To Login, But Still Stored");
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
