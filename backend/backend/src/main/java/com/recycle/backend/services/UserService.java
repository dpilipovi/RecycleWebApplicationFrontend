package com.recycle.backend.services;

import com.recycle.backend.entities.User;
import com.recycle.backend.repositories.UserRepository;
import com.recycle.backend.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService  {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

   
    public User editUser(User user, User editedUser) {

        user.setFirstname(editedUser.getFirstname());
        user.setLastname(editedUser.getLastname());
        user.setAddress(editedUser.getAddress());
        user.setEmail(editedUser.getEmail());
        user.setUsername(editedUser.getUsername());

        if(editedUser.getPassword() !="" && user.getPassword() != bCryptPasswordEncoder.encode(editedUser.getPassword()))
        {
            user.setPassword(bCryptPasswordEncoder.encode(editedUser.getPassword()));
        }  
      
        return userRepository.save(user);
    }

    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }

   
    public String getUsernameFromToken(String token) {
        return jwtUtil.extractUsername(token.substring(0,token.length()-1));
    }

}