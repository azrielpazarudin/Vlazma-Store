package com.vlazma.Authentications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/vlazma/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @PostMapping("/register")
    public Object register(RegisterRequest registerRequest){
        return authenticationService.register(registerRequest);
    }

    @PostMapping("/authentication")
    public Object authentication(AuthenticationRequest request){
        return authenticationService.authenticate(request);
    }
}
