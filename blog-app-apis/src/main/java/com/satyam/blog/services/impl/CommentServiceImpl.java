package com.satyam.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.satyam.blog.enities.Comment;
import com.satyam.blog.enities.Post;
import com.satyam.blog.enities.User;
import com.satyam.blog.exceptions.ResourceNotFoundException;
import com.satyam.blog.payloads.CommentDto;
import com.satyam.blog.repositories.CommentRepo;
import com.satyam.blog.repositories.PostRepo;
import com.satyam.blog.repositories.UserRepo;
import com.satyam.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId,Integer userId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","post Id",postId));
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","user Id",userId));

		Comment comment=this.modelMapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		comment.setUser(user);
		Comment savedComment=this.commentRepo.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		// TODO Auto-generated method stub
		
		Comment comment =this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","Comment Id",commentId));
		this.commentRepo.delete(comment);
	}

}
