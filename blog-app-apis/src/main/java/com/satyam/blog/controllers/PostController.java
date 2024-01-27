package com.satyam.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.satyam.blog.config.AppConstants;
import com.satyam.blog.enities.Category;
import com.satyam.blog.enities.Post;
import com.satyam.blog.enities.User;
import com.satyam.blog.payloads.ApiResponse;
import com.satyam.blog.payloads.PostDto;
import com.satyam.blog.payloads.PostResponse;
import com.satyam.blog.services.FileService;
import com.satyam.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;
	
	//#we are giving path to save images in database 
	//#and with help of this we directly using project.image variable to give in code
	// variable created in properties file
	@Value("${project.image}")
	private String path;

	// image upload
	// if you want customize response then return ImageResponse class

	// imp while selecting form postman
//	body -> form data -> key image slecet file 

	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable("postId") Integer postId) throws IOException
	// here we are handling exception golbaly so for temparay we added thorws
	// if we want to handle it with proper message then create exception class
	{

		PostDto postDto = this.postService.getPostById(postId);
		// using this post id we will update field of postImage
		String fileName = this.fileService.uploadImage(path, image);

		postDto.setImageName(fileName);
		PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPostDto, HttpStatus.OK);

	}
	
	//to get image  
	//search in browers 
	//http://localhost:8080/api/post/image/2fc5f911-b9dd-48a6-9ef6-a0763ee4c43d.jpg
	@GetMapping(value="/post/image/{imageName}",produces=MediaType.ALL_VALUE) //-->MediaType.IMAGE_JPEG_VALUE
	public void downloadImage(@PathVariable("imageName") String imageName,HttpServletResponse response )throws IOException
	{
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.ALL_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}

	// Post
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable("userId") Integer userId,
			@PathVariable("categoryId") Integer categoryId)
	// if parameters are so many then its not good to use url method to set userid
	// and category id
	{
		PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);

		return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
	}

	// get by Posts userId
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUserId(@PathVariable("userId") Integer userId) {
		List<PostDto> postDtos = this.postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);
	}

	// get by Users categoryId
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostBycategoryId(@PathVariable("categoryId") Integer categoryId) {
		List<PostDto> postDtos = this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);
	}

	// get Single Post By Id
	@GetMapping("/post/{postId}/posts")
	public ResponseEntity<PostDto> getPostByPostId(@PathVariable("postId") Integer postId) {
		PostDto post = this.postService.getPostById(postId);

		return new ResponseEntity<PostDto>(post, HttpStatus.OK);

	}

//    get All Posts(with out using pagination )
//	@GetMapping("/posts")
//	public  ResponseEntity<List<PostDto>> getAllPosts()
//	{
//		List<PostDto> allPosts=this.postService.getAllPost();
//		return new ResponseEntity<List<PostDto>>(allPosts,HttpStatus.OK);
//	}

//	(get all post using pagination )
// page size and page number comes in url as parameter  
	// http://localhost:8080/api/posts?pageNumber=0&pageSize=5

	// --> with out full information

//	@GetMapping("/posts")
//	public ResponseEntity<List<PostDto>> getAllPosts
//	(
//			@RequestParam(value="pageNumber",defaultValue="0",required=false) Integer pageNumber,
//			@RequestParam(value="pageSize",defaultValue="5",required=false) Integer pageSize
//     )
//	{
//		List<PostDto> allPost=this.postService.getAllPost(pageNumber, pageSize);
//		return new ResponseEntity<List<PostDto>>(allPost,HttpStatus.OK);
//		
//	}

//	(get all post using pagination )
	// when ever we are sending page data according to request we also have to send
	// page data + page number + page size + total elements + total pages + is Last
	// Page

	// so we use payload / helper
	// # class PostResponse

	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
		PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);

	}

	// delete Mapping
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deleteById(@PathVariable("postId") Integer postId) {
		this.postService.deletePost(postId);

		// return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted
		// successfully",true),HttpStatus.ACCEPTED);

		// or

		return new ApiResponse("Post deleted Successfully", true);
	}

	// Put
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePostByPostId(@RequestBody PostDto postDto,
			@PathVariable("postId") Integer postId) {
		PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPostDto, HttpStatus.OK);

	}

	// search
	// you can take from user using -> form , url , parameter
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> serachPostByTitle(@PathVariable("keyword") String kerword) {
		List<PostDto> result = this.postService.searchPosts(kerword);
		return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);

	}

}
