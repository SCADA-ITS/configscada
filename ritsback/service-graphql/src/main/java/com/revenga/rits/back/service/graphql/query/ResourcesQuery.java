package com.revenga.rits.back.service.graphql.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.revenga.rits.back.service.graphql.service.ResourcesService;

@Component
public class ResourcesQuery implements GraphQLQueryResolver {

	@Autowired
	private ResourcesService resourcesService;
	
	public List<String> getResources(String relativePath, String extension) {
		
		return resourcesService.getResources(relativePath, extension);	
	}
}
