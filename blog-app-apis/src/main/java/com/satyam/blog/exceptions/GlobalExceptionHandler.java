package com.satyam.blog.exceptions;










import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.satyam.blog.payloads.ApiResponse;

@RestControllerAdvice

public class GlobalExceptionHandler {

	// agar  ResourceNotFoundException (jo ham ne banayi hai class )ayigi to ye method chale ga 
	//agar multiple class use karna hai to (,) deke age likho 
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex)
	{
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
		
	}
	
	
	
	
	
	// for validation handler
	
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<Map<String,String>> handleMetgodArgsNotValidException (MethodArgumentNotValidException ex)
//	{
//		// if we got 4 invalid fields 
//		// then we have to get messages and there related field
//		
//		
//		Map<String,String> resp= new HashMap<>();
//		
//		ex.getBindingResult().getAllErrors().forEach((error)->{
//			String fieldName=((FieldError)error).getField();
//			String message= error.getDefaultMessage();
//			resp.put(fieldName, message);
//			
//		});
//		
//		return new ResponseEntity<Map<String ,String >>(resp,HttpStatus.BAD_REQUEST);
//		
      
//	}
	
	
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> handleApiException(ApiException ex)
	{
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, true);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.BAD_REQUEST);
		
	}
	
	
}
