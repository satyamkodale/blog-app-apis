package com.satyam.blog.services;

import com.satyam.blog.payloads.CommentDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto, Integer postId,Integer userId);
	void deleteComment(Integer commentId);
	
	

}
