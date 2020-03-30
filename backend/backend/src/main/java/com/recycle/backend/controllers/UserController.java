package com.recycle.backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.recycle.backend.repositories.UserRecycleRepository;
import com.recycle.backend.repositories.UserRepository;
import com.recycle.backend.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
   
    @Autowired
    private UserRecycleRepository rep;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public User getUser(@RequestHeader HttpHeaders headers)
    {

        String token= headers.get("Authorization").toString().substring(8);
        token=token.substring(0,token.length()-1);
        
       /* System.out.println("Token: "jwtUtil.getToken());
        String token=jwtUtil.getToken();*/
        String username = jwtUtil.extractUsername(token);

        User user= userRepository.findByUsername(username);

        return user;

    }

    @GetMapping("/data_month")
    public List<UserRecycle> getData(@RequestParam(name="username") String username, @RequestParam(name="year") String year, @RequestParam(name="month") String month)
    {
       // System.out.println(username);
       // System.out.println(month);
       // System.out.println(year);

        User user = userRepository.findByUsername(username);

        //List<UserRecycle> lista =rep.findByUserId(user.getId());

        List<UserRecycle> lista = rep.findByUserIdAndMonthAndYear(user.getId(),Integer.parseInt(month),Integer.parseInt(year));

        //System.out.println(lista.get(0).getAmount());

        return lista;

       
    }

    @GetMapping("/data_year")
    public List<UserRecycle> getData(@RequestParam(name="username") String username, @RequestParam(name="year") String year)
    {
       // System.out.println(username);
       // System.out.println(year);

        User user = userRepository.findByUsername(username);

        List<UserRecycle> lista = rep.findByUserIdAndYear(user.getId(),Integer.parseInt(year));

        return lista;

       
    }

    @GetMapping("/data")
    public List<UserRecycle> getData(@RequestParam(name="username") String username)
    {

        User user = userRepository.findByUsername(username);

        List<UserRecycle> lista = rep.findByUserId(user.getId());

        return lista;

       
    }

    @PostMapping("/edit")
    public User editUser(@RequestHeader HttpHeaders headers, @RequestBody User editedUser)
    {
        String token= headers.get("Authorization").toString().substring(8);
        token=token.substring(0,token.length()-1);

        System.out.println(editedUser.getPassword());
 
        String username = jwtUtil.extractUsername(token);

        User user= userRepository.findByUsername(username);

        user.setFirstname(editedUser.getFirstname());
        user.setLastname(editedUser.getLastname());
        user.setAddress(editedUser.getAddress());
        user.setEmail(editedUser.getEmail());
        user.setUsername(editedUser.getUsername());

        if(editedUser.getPassword() !="" && user.getPassword() != bCryptPasswordEncoder.encode(editedUser.getPassword()))
        {
            user.setPassword(bCryptPasswordEncoder.encode(editedUser.getPassword()));
            System.out.println("CHANGED PASSWORD");
        }  
        System.out.println(user.getPassword());
        userRepository.save(user);

        return user;
    }



}