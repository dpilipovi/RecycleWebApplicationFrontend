package com.recycle.backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.recycle.backend.repositories.UserRecycleRepository;
import com.recycle.backend.repositories.UserRepository;
import com.recycle.backend.services.UserService;
import com.recycle.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

import com.recycle.backend.entities.*;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/user")
public class UserController
{
    
    @Autowired
    private JwtUtil jwtUtil;
   
    @Autowired
    private UserRecycleRepository rep;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public User getUser(@RequestHeader HttpHeaders headers)
    {

       return userRepository.findByUsername(userService.getUsernameFromToken(headers.get("Authorization").toString().substring(8)));  

    }
    @GetMapping("/data_month")
    public List<UserRecycle> getData(@RequestParam(name="username") String username, @RequestParam(name="year") String year, @RequestParam(name="month") String month)
    {
        return rep.findByUserIdAndMonthAndYear(userRepository.findByUsername(username).getId(),Integer.parseInt(month),Integer.parseInt(year));
    }

    @GetMapping("/data_year")
    public List<UserRecycle> getData(@RequestParam(name="username") String username, @RequestParam(name="year") String year)
    {
        return rep.findByUserIdAndYear(userRepository.findByUsername(username).getId(),Integer.parseInt(year));  
    }

    @GetMapping("/data")
    public List<UserRecycle> getData(@RequestParam(name="username") String username)
    {
        return rep.findByUserId(userRepository.findByUsername(username).getId());
    }

    @PutMapping("")
    public User editUser(@RequestHeader HttpHeaders headers, @RequestBody User editedUser)
    {
        String token= headers.get("Authorization").toString().substring(8);
        return userService.editUser(userRepository.findByUsername(jwtUtil.extractUsername(token.substring(0,token.length()-1))),editedUser);
    }

    @PostMapping
    public User addUser(@RequestBody User user)
    {
        return userRepository.save(user);
    }



}