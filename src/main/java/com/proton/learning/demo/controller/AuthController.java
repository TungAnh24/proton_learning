package com.proton.learning.demo.controller;

import com.proton.learning.demo.entities.AuthRequest;
import com.proton.learning.demo.entities.Employee;
import com.proton.learning.demo.service.JwtService;
import com.proton.learning.demo.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService service;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService service, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.service = service;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/sign-up")
    public String addNewUser(@RequestBody Employee userInfo) {
        return service.addUser(userInfo);
    }

    @PostMapping("/sign-in")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

}
