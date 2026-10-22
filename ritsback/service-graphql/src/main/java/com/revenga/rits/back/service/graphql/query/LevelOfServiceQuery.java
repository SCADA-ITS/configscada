package com.revenga.rits.back.service.graphql.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.revenga.rits.back.front.core.model.vo.LevelOfServiceVO;
import com.revenga.rits.back.service.graphql.service.LevelOfServiceService;

@Component
public class LevelOfServiceQuery implements GraphQLQueryResolver {
	
	@Autowired
	private LevelOfServiceService levelOfServiceService;
	
	public List<LevelOfServiceVO> getLevelOfServices() {

		return levelOfServiceService.getAll();
	}

	public Optional<LevelOfServiceVO> getLevelOfService(final String levelOfServicesId) {

		return levelOfServiceService.get(levelOfServicesId);
	}

}
