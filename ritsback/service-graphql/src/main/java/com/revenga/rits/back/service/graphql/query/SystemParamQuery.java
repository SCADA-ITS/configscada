package com.revenga.rits.back.service.graphql.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.revenga.rits.back.front.core.model.vo.SystemParamVO;
import com.revenga.rits.back.service.graphql.service.SystemParamService;


@Component
public class SystemParamQuery implements GraphQLQueryResolver {
	
	
	@Autowired
	private SystemParamService systemParamService;
	
	
	/**
	 * Function that return a list of SystemParams and their values
	 * @param systemParamIds
	 * @return
	 */
	public List<SystemParamVO> getSystemParams(final List<String> systemParamIds) {

		return systemParamService.gets(systemParamIds);
	}			
}
