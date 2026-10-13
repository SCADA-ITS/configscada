package com.revenga.rits.back.service.graphql.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.revenga.rits.back.front.core.model.vo.GenericDataVO;
import com.revenga.rits.back.service.graphql.service.GenericDataService;

@Component
public class GenericDataQuery implements GraphQLQueryResolver {

	@Autowired
	private GenericDataService genericDataService;
	
	public Optional<GenericDataVO> getGenericData(final String genericDataId) {

		return genericDataService.get(genericDataId);
	}
	
	public List<GenericDataVO> getGenericDatas() {

		return genericDataService.getAll();
	}
	
}
