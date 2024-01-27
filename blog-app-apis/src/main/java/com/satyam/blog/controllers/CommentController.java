package com.satyam.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.satyam.blog.enities.Comment;
import com.satyam.blog.payloads.ApiResponse;
import com.satyam.blog.payloads.CommentDto;
import com.satyam.blog.services.CommentService;

@RestController
@RequestMapping("api/comments")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}/user/{userId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,@PathVariable("postId") Integer postId ,@PathVariable("userId") Integer userId )
	{
		CommentDto createdComment =this.commentService.createComment(commentDto, postId,userId);	
		return new ResponseEntity<CommentDto>(createdComment,HttpStatus.CREATED);	
	}
	
	
	@PostMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId") Integer commentId  )
	{
              this.commentService.deleteComment(commentId);
              return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Successfully",true),HttpStatus.OK);	
	}
	
	
	

}
