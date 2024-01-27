package com.satyam.blog.security;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.satyam.blog.enities.Role;
import com.satyam.blog.enities.User;
import com.satyam.blog.exceptions.ResourceNotFoundException;
import com.satyam.blog.repositories.RoleRepo;
import com.satyam.blog.repositories.UserRepo;



//not using this class doing by user implements UserDetails
//but sloved error of roles ingranted authority

public class UserDeatilsProvider implements UserDetails {
	
	private com.satyam.blog.enities.User user;

	public UserDeatilsProvider(User user) {
		super();
		this.user = user;
	}

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RoleRepo roleRepo;

	
	
 
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		
	     Set<Role> roles = user.getRoles();
//	     for (Role role : rolesByUser) {
//			    System.out.println("Role Name: " + role.getName());
//			    // Add any other relevant information about the role that you want to print
//			}
		List<SimpleGrantedAuthority> authorities=roles.stream().map((role)->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	

}
