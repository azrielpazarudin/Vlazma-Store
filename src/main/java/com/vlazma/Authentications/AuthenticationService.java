package com.vlazma.Authentications;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.vlazma.Configurations.JwtService;
import com.vlazma.Dto.ResponseData;
import com.vlazma.Enumerations.Role;
import com.vlazma.Models.Users;
import com.vlazma.Repositories.RolesRepository;
import com.vlazma.Repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UsersRepository userRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<ResponseData<AuthenticationResponse>> register(RegisterRequest request, Errors errors) {
        ResponseData<AuthenticationResponse> responseData = new ResponseData<>();
        var userFind = userRepository.findByEmail(request.getEmail());
        if (errors.hasErrors() || userFind.isPresent()
                || !(request.getRole().equals("ADMIN") || request.getRole().equals("CUSTOMER"))) {
            for (ObjectError err : errors.getAllErrors()) {
                responseData.getMessages().add(err.getDefaultMessage());
            }
            responseData.getMessages().add(userFind.isPresent() ? "Email Already Registered" : null);
            responseData.getMessages()
                    .add(!(request.getRole().equals("ADMIN") || request.getRole().equals("CUSTOMER"))
                            ? "Role Must Be ADMIN Or CUSTOMER"
                            : null);
            responseData.getMessages().removeAll(Collections.singleton(null));
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }
        Role myRole = Role.valueOf(request.getRole());
        var user = Users
                .builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(1)
                .role(rolesRepository.findByRoleName(myRole).get())
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(AuthenticationResponse.builder().token(jwtToken).build());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
    }

    public ResponseEntity<ResponseData<AuthenticationResponse>> authenticate(AuthenticationRequest request) {
        ResponseData<AuthenticationResponse> responseData = new ResponseData<>();
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken((request.getEmail()), request.getPassword()));
        } catch (AuthenticationException exception) {
            responseData.getMessages().add("Username Or Password Is Incorrect Or Maybe User Is Deactivated");
            return ResponseEntity.badRequest().body(responseData);
        }
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        responseData.getMessages().add("Succes");
        responseData.setStatus(true);
        responseData.setPayload(AuthenticationResponse.builder()
                .token(jwtService.generateToken(user)).build());

        return ResponseEntity.ok(responseData);
    }

}
