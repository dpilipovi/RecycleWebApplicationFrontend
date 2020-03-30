package com.recycle.backend.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="type")
public class Type
{
    @Id
    @GeneratedValue
    @Column(name="id")
    private long id;
    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "type")
    Set<UserRecycle> user_recycle;


    public Type() {
    }

    public Type(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Type( String name) {
        
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   

    

    
  






}