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
    public boolean addUser(@RequestBody User user) throws Exception
    {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getAddress());
        System.out.println(user.getEmail());
        System.out.println(user.getFirstname());
        System.out.println(user.getLastname());
        try
        {
            repository.save(user);
            System.out.println("unosenje usera");
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }

        return true;
    }



}