package com.satyam.blog.payloads;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	
	private Integer categoryId;

	@NotEmpty
	@Size(min=4,message="Size must be more than 4 char ")
	private String categoryTitle;

	@NotBlank
	@Size(min=4, message="Size must be more than 4 char ")
	private String categoryDescription;
	
}
