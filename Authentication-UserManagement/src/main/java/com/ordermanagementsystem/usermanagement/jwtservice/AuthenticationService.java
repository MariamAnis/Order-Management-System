package com.ordermanagementsystem.usermanagement.jwtservice;


import com.ordermanagementsystem.usermanagement.user.Role;
import com.ordermanagementsystem.usermanagement.user.User;
import com.ordermanagementsystem.usermanagement.userservice.AuthenticationRequest;
import com.ordermanagementsystem.usermanagement.userservice.RegisterRequest;
import com.ordermanagementsystem.usermanagement.userservice.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Request;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;



    public String authenticate(AuthenticationRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            System.out.println(request);
            var user = userService.findByEmail(request.getEmail()).orElseThrow();
            Collection<? extends GrantedAuthority> authorities = null;

            if (user.getRole() == Role.ADMIN){
                authorities = Arrays.asList(
                        new SimpleGrantedAuthority("ROLE_ADMIN")

                );
            }else if (user.getRole() == Role.USER){
                authorities = Arrays.asList(
                        new SimpleGrantedAuthority("ROLE_USER")
                );
            }else {
                authorities = Arrays.asList(
                        new SimpleGrantedAuthority("ROLE_MANAGER")
                );
            }

            System.out.println(user);

            user.setAuthorities(authorities);
            return jwtService.generateToken(user);

        }catch (Exception e){
            System.out.println("User not found");
            return null;
        }


    }

    public  String register(RegisterRequest request) {

        if (userService.findByEmail(request.getEmail()).isPresent()){return null;}

        Collection<? extends GrantedAuthority> authorities = null;
        if (request.getRole() == Role.ADMIN){
            authorities = Arrays.asList(
                    new SimpleGrantedAuthority("ROLE_ADMIN")

            );
        }else{
             authorities = Arrays.asList(
                    new SimpleGrantedAuthority("ROLE_USER")
            );
        }


        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .authorities(authorities)
                .build();
        userService.saveUser(user);

        return jwtService.generateToken(user);

    }
}
