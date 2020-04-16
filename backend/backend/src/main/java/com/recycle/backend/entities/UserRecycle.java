package com.recycle.backend.entities;

import javax.persistence.Column;
//import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name="user_recycle")
public class UserRecycle {


    //@EmbeddedId
    //UserRecycleKey id;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @ManyToOne
    @MapsId("userid")
    @JoinColumn(name = "userid")
    User user;
 
    @ManyToOne
    @MapsId("typeid")
    @JoinColumn(name = "typeid")
    Type type;

    @Column(name="amount")
    private int amount;

    @Column(name="year")
    private int year;

    @Column(name="month")
    private int month;
   
  /*public UserRecycleKey getId() {
        return id;
    }

    public void setId(UserRecycleKey id) {
        this.id = id;
    }*/

    public long getId()
    {
        return id;
    }

    public void setId(long id)
     {
         this.id=id;
     }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

     public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }


    public UserRecycle() {
    }

    public UserRecycle(long id, User user, Type type, int amount, int year, int month) {
        this.id = id;
        this.user = user;
        this.type = type;
        this.amount = amount;
        this.year = year;
        this.month = month;
    }

    public UserRecycle(User user, Type type, int amount, int year, int month) {
        this.user = user;
        this.type = type;
        this.amount = amount;
        this.year = year;
        this.month = month;
    }

   

   
}