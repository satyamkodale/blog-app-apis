package com.satyam.blog.services;

import java.util.List;

import com.satyam.blog.payloads.CategoryDto;

public interface CategoryService {
	
	//create 
	
	public CategoryDto createCategory(CategoryDto categoryDto);
	
	// update 
	public CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	// delete 
	public void deleteCategory(Integer categoryId);
	// get 
	public CategoryDto getCategory(Integer categoryId);
	// get All
	public List<CategoryDto> getCategories();
}
