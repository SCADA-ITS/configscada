package com.revenga.rits.back.service.graphql.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.revenga.rits.back.front.core.model.vo.IOControllerModuleVO;
import com.revenga.rits.back.service.graphql.service.IOControllerModuleService;

@Component
public class IOControllerModuleQuery implements GraphQLQueryResolver{
	
	@Autowired
	private IOControllerModuleService ioControllerModuleService;
	
	public List<IOControllerModuleVO> getIOControllerMudules() {

		return ioControllerModuleService.getAll();
	}
}
