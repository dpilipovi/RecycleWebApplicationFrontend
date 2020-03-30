package com.recycle.backend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="schedule")
public class Schedule
{
    // PrimaryKey
    @Id
    @GeneratedValue
    private long id;
    @Column(name="address")
    private String address;
    @Column(name="type")
    private String type;
    @Column(name="day")
    private String day;
    
    public Schedule(int id, String adresa, String vrstaOtpada, String dan) {
        this.id = id;
        this.address = adresa;
        this.type = vrstaOtpada;
        this.day = dan;
    }

    public Schedule(String adresa, String vrstaOtpada, String dan) {
        this.address = adresa;
        this.type = vrstaOtpada;
        this.day = dan;
    }

    public Schedule()
    {
        
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String adresa) {
        this.address = adresa;
    }

    public String getType() {
        return type;
    }

    public void setType(String vrstaOtpada) {
        this.type = vrstaOtpada;
    }

    public String getDay() {
        return day;
    }

    public void setDan(String dan) {
        this.day = dan;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Raspored [adresa=" + address + ", dan=" + day + ", vrstaOtpada=" + type + "]";
    }

   
    

}