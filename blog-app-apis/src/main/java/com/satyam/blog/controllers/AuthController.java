package com.satyam.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.satyam.blog.exceptions.ApiException;
import com.satyam.blog.payloads.JwtAuthRequest;
import com.satyam.blog.payloads.JwtAuthResponse;
import com.satyam.blog.payloads.UserDto;
import com.satyam.blog.security.JwtTokenHelper;
import com.satyam.blog.services.UserService;


//
//api/v1/auth/login
//
//body -> row 
//
//valid user to login
//
//{
//    "username":"john@dev.in"
//    "password":"xyz"
//}

//{
//"token":3097326873487tuygwudt7w6rd;	
//}



//get all post 
//
// authorization -> no auth
//headers 
//
//key->Authorization value ->Bearer 3097326873487tuygwudt7w6rd;


@RestController
@RequestMapping("api/v1/auth/")
public class AuthController {
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	@Autowired
	private UserDetailsService userDeatilsService;
	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
//	@PostMapping("/login")
//	public ResponseEntity<JwtAuthResponse> createToken
//	(
//			@RequestBody JwtAuthRequest request
//			)
//	{
//		this.authenticate(request.getUsername(),request.getPassword());
//		UserDetails userDetails = this.userDeatilsService.loadUserByUsername(request.getUsername());
//		String token = this.jwtTokenHelper.genrateToken(userDetails);
//		
//		JwtAuthResponse response = new JwtAuthResponse();
//		response.setToken(token);
//		return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
//		
//		
//	}
//	
	private void authenticate(String username, String password) throws Exception 
	{
		
		UsernamePasswordAuthenticationToken	usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,password);
	
		try {
		this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		}
		catch(BadCredentialsException e) 
		{
			System.out.println("invalid details");
			throw new ApiException("Invaild Username or password ");
		}
	
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto)
	{
		UserDto registeredUser=this.userService.registerNewUser(userDto);
		return new ResponseEntity<UserDto>( registeredUser,HttpStatus.CREATED);
		
	}

}


