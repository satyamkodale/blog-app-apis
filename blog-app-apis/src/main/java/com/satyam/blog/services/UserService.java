package com.satyam.blog.services;

import java.util.List;

import com.satyam.blog.payloads.UserDto;

//@Service  i think boz its interface so no need
public interface UserService {
	
	UserDto registerNewUser(UserDto user);
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user, Integer userId);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUsers();
	void deleteUser(Integer userId);

}
