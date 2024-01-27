package com.satyam.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.satyam.blog.enities.Category;
import com.satyam.blog.enities.Post;
import com.satyam.blog.enities.User;
import com.satyam.blog.exceptions.ResourceNotFoundException;
import com.satyam.blog.payloads.PostDto;
import com.satyam.blog.payloads.PostResponse;
import com.satyam.blog.repositories.CategoryRepo;
import com.satyam.blog.repositories.PostRepo;
import com.satyam.blog.repositories.UserRepo;
import com.satyam.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService{
	@Autowired
	private ModelMapper modelMapper;
	@Autowired 
	private PostRepo postRepo;
	@Autowired 
	private UserRepo userRepo;
	@Autowired 
	private CategoryRepo categoryRepo;
	
	//understand and apply your updation standards
	
	@Override
	public PostDto updatePost(PostDto postDto, Integer postId)
	{
		//this is done by me which i think changes all but we have to give specified user info and category info also
	    // like this 
//		{
//		    "postId": 4,
//		    "title": "can't see namo",
//		    "content": "updated with help of mapping ",
//		    "imageName": "default.png",
//		    "addedDate": "2023-12-16T11:23:45.134+00:00",
//		    "category": {
//		        "categoryId": 1,
//		        "categoryTitle": "The life termination event at Kashi",
//		        "categoryDescription": "this book contain how he realised the truth of his life and started a new life "
//		    },
//		    "user": {
//		        "id": 1,
//		        "name": "satyam",
//		        "email": "satyamkodale@gmail.com",
//		        "password": "satyam",
//		        "about": "I am java developer"
//		    }
//		}
	// if you doesn't gived user and category its chages creates new post which doesnt alsocied with any category and post
//		Post post =this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post Id",postId));
//		Post updatedPost=this.modelMapper.map(postDto, Post.class);
//    
//		this.postRepo.save(updatedPost);
//		return this.modelMapper.map(updatedPost, PostDto.class);
		
		//done by durgesh--> working fine 
		Post post =this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post Id",postId));
		
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatedPost=this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}
	
	 
// without pagination get all post 
	
//	@Override
//	public List<PostDto> getAllPost()
//	{
		// with out pagination 
//		List<Post> posts=this.postRepo.findAll();
//		
//	    List<PostDto> dtoPosts=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
//		
//		return dtoPosts;	
//		
//	}
	
	
	// get all posts (using pagination)

	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir)
	{
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc")) 
		{
			sort=Sort.by(sortBy);
		
		}
		else 
		{
			sort=Sort.by(sortBy).descending();
			
		}
		Pageable p = PageRequest.of(pageNumber, pageSize,sort);
		
//		if not appiled custom sorting
		//Pageable p = PageRequest.of(pageNumber, pageSize,Sort.by(sortBy).descending());
		Page<Post> pagePost=this.postRepo.findAll(p);
		
		List<Post> allPosts=pagePost.getContent();
		
		
		List<PostDto> postDtos=allPosts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		
		PostResponse  postResponse = new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());//the number of current slices
		postResponse.setPageSize(pagePost.getSize());
		
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}
	
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		
		Category category= this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		
		
		
		Post post=this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		//later we will create a service method to update the image 
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost =postRepo.save(post);
		
		return this.modelMapper.map(newPost, PostDto.class);
	}

	

	@Override
	public void deletePost(Integer postId) {
		Post post =this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post Id",postId));
		this.postRepo.delete(post);
	
	}

	@Override
	public PostDto getPostById(Integer postId) 
	{
		Post post =this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Post Id",postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId)
	{
	    Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category Id",categoryId));
	    
	    List<Post> posts=this.postRepo.findByCategory(category);
	    
	    //now we have to return PostDto List
	    
	   List<PostDto> postDtos= posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		 User user =this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","User Id",userId));
		    
		    List<Post> posts=this.postRepo.findByUser(user);
		    
		    //now we have to return PostDto List
		    
		   List<PostDto> postDtos= posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
			
			return postDtos;
	
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDto=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDto;
	}

}
