package com.recycle.backend.controllers;


import com.recycle.backend.entities.User;
import com.recycle.backend.repositories.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/register")
public class RegisterController
{

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository repository;

    @PostMapping("")
    public User addUser(@RequestBody User user) throws Exception
    {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return  repository.save(user);

    }



}