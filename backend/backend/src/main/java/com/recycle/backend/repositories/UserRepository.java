package com.recycle.backend.repositories;



import com.recycle.backend.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Long>
{

	User findByUsername(String username);

}