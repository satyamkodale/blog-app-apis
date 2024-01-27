package com.satyam.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.satyam.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService{
	
//	add this lines to appln properties
//	spring.servlet.multipart.max.file-size=10MB
//			spring.servlet.multipart.max.request-size=10MB
//
//			project.image=images/

	//image upload 
	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
		// File name
		String name = file.getOriginalFilename();
		//abc.png
		
		//random name genrate file 
		String randomId = UUID.randomUUID().toString();
		String fileName1 = randomId.concat(name.substring(name.lastIndexOf(".")));
		//run:
		//65790a12-2255-46e3-a4a6-22509651e711.png
		
		//fullpath
		String filePath= path+ File.pathSeparator+fileName1;
		
		//create a folder if its not created 
		File f = new File(path);
		if(!f.exists()) 
		{
			f.mkdir();
		}
		
		// file copy 
		Files.copy(file.getInputStream(),Paths.get(filePath));
		
		return fileName1;   //if we need normal name then use-> name 
		                    //if we need random name-> fileName1
	}

	// image serve
	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException
	{
		String fullPath = path+File.pathSeparator+fileName;
		InputStream is = new FileInputStream(fullPath);
		//db logic to return inputsteam 
		
		return is;
		
		
	}

}
