package com.revenga.rits.back.service.graphql.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.revenga.rits.back.data.core.util.ResourcesUtil;

@Service
public class ResourcesService {

	public List<String> getResources(String relativePath, String extension){
		
		List<String> files = null;

		files = ResourcesUtil.getFilesByExtension(relativePath, extension);
		
		return files;
	}
	
}
