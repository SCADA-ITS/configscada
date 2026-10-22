package com.revenga.rits.back.service.graphql.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.revenga.rits.back.front.core.model.vo.GenericDataVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;
import com.revenga.rits.back.service.graphql.service.GenericDataService;

import graphql.schema.DataFetchingEnvironment;

@Component
public class GenericDataQuery implements GraphQLQueryResolver {

	@Autowired
	private GenericDataService genericDataService;
	
	public Optional<GenericDataVO> getGenericData(final String genericDataId, DataFetchingEnvironment env) {

		return genericDataService.get(genericDataId, EntitiesManager.getManagementAreaId(env));
	}
	
	public List<GenericDataVO> getGenericDatas(DataFetchingEnvironment env) {

		return genericDataService.getAll(EntitiesManager.getManagementAreaId(env));
	}
	
}
