package com.satyam.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.satyam.blog.enities.Category;
import com.satyam.blog.enities.Comment;
import com.satyam.blog.enities.User;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class PostDto {

	private int postId;
	private String title;

	private String content;

//	private String imageName="default.png";  you can also set first default.png and then update when it needed 
	private String imageName;
	private Date addedDate; // automatic

//	category id and User id ko --> lene ke {2} tarike hai 
//1  ek to yanha pe direct le lo 
//	private Category category;
//	private User user;

//2 dusra direct url me le lo 
// is project ke liye hum url me le lenge 

	// to return user and category when ever we are creating any new post
	// if you keep like this and add post
//	private Category category;
//	private User user;
	// it goes to recurrsion bcoz of actual cat..and user fetching so soln is
	
	private CategoryDto category;// category dto ke ander koi post nai hai 
	private UserDto user;// userdto ke ander koi post nai hai
	private Set<CommentDto> comments = new HashSet<>();//commentdto ke ander koi post nai hai
	

}
