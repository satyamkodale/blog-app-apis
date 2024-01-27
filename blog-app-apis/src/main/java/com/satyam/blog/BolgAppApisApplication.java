package com.satyam.blog;

import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.satyam.blog.config.AppConstants;
import com.satyam.blog.enities.Role;
import com.satyam.blog.enities.User;
import com.satyam.blog.exceptions.ResourceNotFoundException;
import com.satyam.blog.repositories.RoleRepo;
import com.satyam.blog.repositories.UserRepo;

@SpringBootApplication
public class BolgAppApisApplication implements CommandLineRunner{
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	

	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private UserRepo userRepo;

	public static void main(String[] args) {
		SpringApplication.run(BolgAppApisApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() 
	{
		return new ModelMapper();
	}
	
	
	// we are checing for this work create a class 
	public void run(String... args) throws Exception
	{
		System.out.println(this.passwordEncoder.encode("saksham"));
		
		//done for checking errors(giving only 1 dingl role)
//		Set<Role> roles = this.roleRepo.findAllById(2);
//		System.out.println("getting by user id");
//		if (roles != null && !roles.isEmpty()) {
//		    for (Role role : roles) {
//		        System.out.println("Role Name: " + role.getName());
//		        // Add any other relevant information about the role that you want to print
//		    }
//		} else {
//		    System.out.println("No roles found for the specified user.");
//		}
//		
//		
//		//working properly 
//		User user = this.userRepo.findById(2).orElseThrow(()-> new ResourceNotFoundException("User","id",2));
//		
//		Set<Role> rolesByUser = user.getRoles();
//		System.out.println("getting by user obj");
//
//		for (Role role : rolesByUser) {
//		    System.out.println("Role Name: " + role.getName());
//		    // Add any other relevant information about the role that you want to print
//		}
		
		//if role exsits it not crette bocz id is not incrementing 
		try {
			Role role1 = new Role();
			role1.setId(AppConstants.ROLE_ADMIN);
			role1.setName("ROLE_ADMIN");
			
			Role role2 = new Role();
			role2.setId(AppConstants.ROLE_USER);
			role2.setName("ROLE_USER");
			
			List<Role> roles=List.of(role1,role2);
			List<Role> result=this.roleRepo.saveAll(roles);
			result.forEach(
					r->{
						System.out.println(r.getName());
						});
			
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		
	
	}
	
	

	
}
