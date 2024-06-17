package com.example.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import com.example.entity.User;
import com.example.security.JwtUtil;
import com.example.service.UserService;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/authenticate")
    public String authenticate(@RequestBody Optional<User>  user) throws Exception {
    	 if (user.isPresent()) {
             User u = user.get();
             try {
                 authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword()));
             } catch (Exception e) {
                 throw new Exception("Invalid credentials");
             }
             final UserDetails userDetails = userDetailsService.loadUserByUsername(u.getUsername());
             return jwtUtil.generateToken(userDetails);
         } else {
             throw new Exception("User details not provided");
         }
     }
}
