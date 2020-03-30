package com.recycle.backend.controllers;


import java.util.List;

import com.recycle.backend.entities.Schedule;
import com.recycle.backend.repositories.ScheduleRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/schedule")
public class ScheduleController
{

    @Autowired
    private ScheduleRepository repository ;
    

   

    public ScheduleController()
    {  
    }

   /* @GetMapping("")
    public List<Schedule> getAll()
    {
        System.out.println("get all");
        return repository.findAll();
    }*/

    @PostMapping("")
    public String postMethodName(@RequestBody Schedule schedule) {
        repository.save(schedule);
        
        return "schedule saved";
    }
    
    @GetMapping("")
    public List<Schedule> getByAddress(@RequestParam String address)
    {
        //System.out.println(address);
        return repository.findByAddress(address);
    }

}