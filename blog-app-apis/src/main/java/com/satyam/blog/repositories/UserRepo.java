package com.satyam.blog.repositories;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.satyam.blog.enities.Role;
import com.satyam.blog.enities.User;

public interface UserRepo extends JpaRepository<User, Integer>
{
	Optional<User>  findByEmail(String email);
	
	
	//chatgpt working properly it only fteches user with user id
	@Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles WHERE u.id = :userId")
	User findUserWithRoles(@Param("userId") Integer userId);
	
}
