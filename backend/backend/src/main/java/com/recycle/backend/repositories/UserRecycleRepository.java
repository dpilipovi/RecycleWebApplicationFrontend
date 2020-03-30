package com.recycle.backend.repositories;


import java.util.List;

import com.recycle.backend.entities.UserRecycle;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRecycleRepository extends JpaRepository<UserRecycle,Long>
{

	List<UserRecycle> findByUserId(long id);

	List<UserRecycle> findByUserIdAndMonthAndYear(long id, int month, int year);

	List<UserRecycle> findByUserIdAndYear(long id, int year);



}