package com.satyam.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import com.satyam.blog.enities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	// Add @Vaild in controller field 
private int id;
@NotEmpty
@Size(min=4, message="Username must be min of 4 char")
private String name;
@Email(message="Email address is not valid")
private String email;
@NotEmpty
@Size(min=3, max=10 , message = "password must be betn 3-10")
private String password;
@NotEmpty
private String about;

private Set<CommentDto> comments = new HashSet<>();

private Set<RoleDto> roles = new HashSet<>();


}
