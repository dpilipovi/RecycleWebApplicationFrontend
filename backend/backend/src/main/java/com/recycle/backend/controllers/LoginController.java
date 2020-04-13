package com.recycle.backend.controllers;

import com.recycle.backend.entities.AuthRequest;
import com.recycle.backend.util.JwtUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/login")
public class LoginController
{
    

    //@Autowired
    //private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("")
    public String login(@RequestBody AuthRequest authRequest) throws Exception{
         
       // authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        return jwtUtil.generateToken(authRequest.getUsername());
    }


}