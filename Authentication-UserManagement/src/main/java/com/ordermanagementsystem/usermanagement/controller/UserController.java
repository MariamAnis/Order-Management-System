package com.ordermanagementsystem.usermanagement.controller;

import com.ordermanagementsystem.usermanagement.jwtservice.AuthenticationService;
import com.ordermanagementsystem.usermanagement.jwtservice.JwtService;
import com.ordermanagementsystem.usermanagement.user.User;
import com.ordermanagementsystem.usermanagement.userservice.AuthenticationRequest;
import com.ordermanagementsystem.usermanagement.userservice.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor

public class UserController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request

    ){
        System.out.println(request);
        String token= authenticationService.register(request);

        if (token == null){return new ResponseEntity<String>("User Already Registered", HttpStatus.NOT_ACCEPTABLE);}

        return new ResponseEntity<String>(token, HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> register(
            @RequestBody AuthenticationRequest request
    ){
        String token= authenticationService.authenticate(request);
        System.out.println(token);

        if (token == null){return new ResponseEntity<String>("User Not Found", HttpStatus.FORBIDDEN);}

        return new ResponseEntity<String>(token, HttpStatus.OK);
    }

    @PostMapping("/user/{token}")
    public ResponseEntity<String> getUserEmail(
            @PathVariable String token
    ){

        System.out.println(token);
        return new ResponseEntity<String>( jwtService.extractUserEmail(token), HttpStatus.OK);
    }
}
