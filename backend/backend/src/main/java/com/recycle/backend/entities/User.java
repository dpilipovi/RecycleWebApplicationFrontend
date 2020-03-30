package com.recycle.backend.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User
{
    @Id
    @GeneratedValue
    @Column(name="id")
    private long id;
    @Column(name="firstname")
    private String firstname;
    @Column(name="lastname")
    private String lastname;
    @Column(name="username", unique = true)
    private String username;
    @Column(name="email", unique = true)
    private String email;
    @Column(name="password")
    private String password;
    @Column(name="address")
    private String address;
    @Column(name="level")
    private int level;

    @OneToMany(mappedBy = "user")
    Set<UserRecycle> user_recycle;


    public User(String name, String lastname, String username, String email, String password, String address) {
        this.firstname = name;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.level=0;
    }

    public User(long id,String name, String lastname, String username, String email, String password, String address) {
        this.id = id;
        this.firstname = name;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.level=0;
    }

    public User()
    {

    }

    
    

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String name) {
        this.firstname = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    



}