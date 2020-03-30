package com.recycle.backend.repositories;

import java.util.List;

import com.recycle.backend.entities.Schedule;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ScheduleRepository extends JpaRepository<Schedule,Long>
{

	List<Schedule> findByAddress(String address);

}