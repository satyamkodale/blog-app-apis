package com.satyam.blog.services;

import com.satyam.blog.enities.Post;
import com.satyam.blog.payloads.PostDto;
import com.satyam.blog.payloads.PostResponse;

import java.util.*;

public interface PostService {
	
	//Post
     PostDto createPost(PostDto postDto,Integer userId, Integer categoryId);
     
     //get All post by Category 
     List<PostDto> getPostsByCategory(Integer categoryId);
     
     //get All posts by User 
     List<PostDto> getPostsByUser(Integer userId);
     
     
     //get single 
     PostDto getPostById(Integer postId);
     
     //getAllPost(without pagination )
   //  List<PostDto> getAllPost();
    //getAllPost(with pagination )(with less data)
   //   List<PostDto> getAllPost(Integer pageNumber,Integer pageSize);  
     //getAllPost(with pagination )(with full data using PostResponse payload )
     PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);  
     
     
   //Put
     PostDto updatePost(PostDto postDto, Integer postId);

    // delete 
     void deletePost(Integer postId);
     
 
     
     //based on keyword it searches posts
     List<PostDto> searchPosts(String keyword);
}
