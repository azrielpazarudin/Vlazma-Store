package com.vlazma.Authentications;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.vlazma.Configurations.JwtService;
import com.vlazma.Enumerations.Role;
import com.vlazma.Models.Users;
import com.vlazma.Repositories.RolesRepository;
import com.vlazma.Repositories.UsersRepository;
import com.vlazma.Utils.PasswordEncryptor;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UsersRepository userRepository;
    private final RolesRepository rolesRepository;
    @Autowired
    private PasswordEncryptor passwordEncryptor;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        
        var user = Users
                .builder()
                .email(request.getEmail())
                .password(passwordEncryptor.encryption(request.getPassword()))
                .active(1)
                .role(rolesRepository.findByRoleName(Role.valueOf(request.getRole())).get())
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken((request.getEmail()), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(user)).build();
    }

    
}
