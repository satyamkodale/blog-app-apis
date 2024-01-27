package com.satyam.blog.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.satyam.blog.payloads.ApiResponse;
import com.satyam.blog.payloads.UserDto;
import com.satyam.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController
{
	@Autowired
	private UserService userService;
	
	

	
	// you can also manage a seperate class for API RESPONSE in payloads ->
	//
	
	// POST - Create user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
	{
		UserDto createUserDto=this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
	}
	
	// PUT - update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid  @RequestBody UserDto userDto,@PathVariable("userId") Integer userId)
	{
	       UserDto updatedUser =userService.updateUser(userDto, userId);
	       return ResponseEntity.ok(updatedUser);
	       
	}
	
	
	
	//ONlY ADMIN CAN DELETE USER
	// DELETE - delete user
	@PreAuthorize("hasRole('ADMIN')")  //with the help of security config // @EnableGlobalMethodSecurity(prePostEnabled=true)
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId)
	{
		userService.deleteUser(userId);
		// when you didn't created any payload class for ApiResponse 
		//and
		//return type--> ResponseEntity<?>
		//return new ResponseEntity(Map.of("message","User Deleted Successfully"),HttpStatus.OK);
		
		// with the help of payload class 
		return  new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully",true),HttpStatus.OK);
		
	}
	
	
	// GET - get all  users
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers()
	{
	return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Integer userId)
	{
	     return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
     //	   or
	   //  return ResponseEntity.ok(this.userService.getUserById(userId));
	}
	
	

}
