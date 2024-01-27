package com.satyam.blog.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	//it call when api request hit 
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
// 1. get jwt token from request
		
		
		
		//token is coming in header 
		
		String requestToken =request.getHeader("Authorization") ;
		//this is our key 
		//jiski help se hum token ko get karenge 
		//hum header me Authorization likhnge 
		
		// token starts with  [Bearer 374576436]
		
		String username = null;
		
		String token =null;
		
		
		if(request!=null && requestToken.startsWith("Bearer")) 
		{
			
			token =requestToken.substring(7);
			// actual token without Bearer 
			try {
			// username= this.jwtTokenHelper.getuserNameFromToken(token);
				}
			catch(IllegalArgumentException e) 
			{
				System.out.println("unable to get token ");

			}
			catch(ExpiredJwtException e) 
			{
				System.out.println(" token  has expired ");

			}
			catch(MalformedJwtException e) 
			{
				System.out.println(" invalid ");

			}
		}
		else 
		{
			System.out.println("jwt token does not start with bearer");
		}
		
		
		
// 2. validate the token 
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) 
		{
			//username is not null and contenxt is null
			
			  UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			  
			//if(this.jwtTokenHelper.validateToken(token,null))
			//{
			  // all ok do authentication	
			  // authentication karna hai 
			  
			  UsernamePasswordAuthenticationToken   usernamePasswordAuthenticationToken = new   UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
			  usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			  SecurityContextHolder.getContext().setAuthentication( usernamePasswordAuthenticationToken);
			//}
			 // else 
			 // {
				//  System.out.println("Invalid jwt token ");
			//  }
			
		}
		else 
		{
			System.out.println("username is null or cotext is not null");
		}
		
		
		
		filterChain.doFilter(request, response);
		
	}

}
