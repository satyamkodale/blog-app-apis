package com.satyam.blog.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.satyam.blog.enities.Role;
import com.satyam.blog.enities.User;


public interface RoleRepo extends JpaRepository<Role, Integer>{
	
	
//     @Query("select r From Role r where r.users.id=:userId")
//	 Set<Role> findAllByUsers_Id(@Param("userId")int userId);
//
//     my method which if i used giving unauthorized error
     Set<Role> findAllById(int userId);

     
     // chatgpt methods -> which giving single role
  // Method to retrieve roles based on User entity  
     
     //BolgAppApisApplication view this class we done well in run method 
     
     
     @Query("SELECT u.roles FROM User u WHERE u = :user")
     Set<Role> findRolesByUser(@Param("user") User user);

     // Method to retrieve roles based on user ID
     @Query("SELECT u.roles FROM User u WHERE u.id = :userId")
     Set<Role> findRolesByUserId(@Param("userId") Long userId);
	
}
