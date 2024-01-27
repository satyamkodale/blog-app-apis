package com.satyam.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.satyam.blog.enities.Category;
import com.satyam.blog.enities.Post;
import com.satyam.blog.enities.User;

import java.util.*;
public interface PostRepo extends JpaRepository<Post, Integer> {
	
	//user ke post chahiye
	//ek post belong karta hai specific user se 
	// to Post ke andar User field hai 
	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);
	
	
	// sometime its not working for int value 
	List<Post> findByTitleContaining(String title);
	
//	so optional 
//	@Query("select p from Post p where p.title like : key")
	//List<Post> searchByTitle(@Param("key")  String title);
	
//	this.postRepo.searchByTitle("%"+keyword+"%");

}
