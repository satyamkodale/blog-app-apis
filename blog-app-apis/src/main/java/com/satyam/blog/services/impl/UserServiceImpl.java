package com.satyam.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.satyam.blog.config.AppConstants;
import com.satyam.blog.enities.Role;
import com.satyam.blog.enities.User;
import com.satyam.blog.exceptions.ResourceNotFoundException;
import com.satyam.blog.payloads.UserDto;
import com.satyam.blog.repositories.RoleRepo;
import com.satyam.blog.repositories.UserRepo;
import com.satyam.blog.services.UserService;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	
	
	
	// here we need to send User object to database but we are getting UserDto so we need to change it
	//here we are using object mapping manullay without using mapping  methods
	
//	but if objects increaes then its not easy work for us
	// so have library MODEL MAPPER LIBRARY  
	//just add modelMapper dependancy in pom 
	//and create madel mapper with bean annotations 
	//main / config package 
// and use with the help of autowired
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	
	private User dtoToUser(UserDto userDto) 
	{
//		Manually->
   //	User user = new User();
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
//		return user;
		
		//using maodel mapper 
		//dto ko convert karna hai user mai 
		User user = this.modelMapper.map(userDto, User.class);
		return user;
	}

	private UserDto userToDto(User user) 
	{
//		UserDto userDto = new UserDto();
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setPassword(user.getPassword());
//		return userDto;
		//using maodel mapper 
	   //user ko convert karna hai userdto mai 
		UserDto userDto=this.modelMapper.map(user,UserDto.class);
		return userDto;
	}
	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user =this.dtoToUser(userDto);
		
		
		User savedUser =userRepo.save(user);
		
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		// here we are getting uderdto and we need to update user of db so.,
		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		user.setName(userDto.getName());
		
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
	
		user.setAbout(userDto.getAbout());
		
		User updatedUser =this.userRepo.save(user);
		UserDto updateduserDto = this.userToDto(updatedUser);
		
		return  updateduserDto;
		
		
	}

	@Override
	public UserDto getUserById(Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		UserDto toReturnUDto = this.userToDto(user);
		return toReturnUDto;
	}

	@Override
	public List<UserDto> getAllUsers() {
		
		List <User> users=this.userRepo.findAll();
		// now we need to return userdto so., 
		// we do with foreach loop using List<UserDto>
		//or by stream().map -> api
		
		List<UserDto> userDtos=users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		
		return  userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
		userRepo.delete(user);
		
	}
	
	
	public UserDto registerNewUser(UserDto userDto) 
	{
		User user =this.modelMapper.map(userDto, User.class);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		//by default its normal user
		
		Role role =roleRepo.findById(AppConstants.ROLE_USER).get();
		user.getRoles().add(role);
		
		User newUser=this.userRepo.save(user);
		
		return this.modelMapper.map(newUser, UserDto.class);		
	} 
	
	

}
