package com.vlazma.Authentications;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/vlazma/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @CrossOrigin(origins = "*")
    @PostMapping("/register")
    public Object register(@Valid@RequestBody RegisterRequest registerRequest,Errors errors){
        return authenticationService.register(registerRequest,errors);
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/authentication")
    public Object authentication(AuthenticationRequest request){
        return authenticationService.authenticate(request);
    }
    
}
