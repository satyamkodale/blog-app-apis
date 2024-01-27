package com.satyam.blog.enities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


// we are fetching comments with post so we added set of comments in post dto 
// and in this we are only assigning post with comment not user but you can do it later 




@Entity
@Table(name="comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	
	private String content;
	 
	//forgeain key in comments table so add mappedBy to post entity
	@ManyToOne
	private Post post;
	@ManyToOne
	private User user;
	
	
	
	
}
