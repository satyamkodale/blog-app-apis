package com.satyam.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.satyam.blog.enities.User;
import com.satyam.blog.exceptions.ResourceNotFoundException;
import com.satyam.blog.repositories.UserRepo;

//UDS loads user specific data when spring security need any things 
//ex., username and password 
//locates  user based on user name

@Service
public class CustomUserDetailService implements UserDetailsService  
{
	@Autowired
	private UserRepo userRepo; 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		// loading user from db by user name
		User user =this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User","email :"+username,0));
		// now ResourceNotFoundException our class have last field as long id; 
		// now our field is string so we need to adjust by 0 
		// for main project just create another custom class like this 
		
		//UserDeatilsProvider userDetailsProvider = new UserDeatilsProvider(user); --> when it not worked
		
		return user;
	}
	

}
