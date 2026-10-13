package com.revenga.rits.back.service.graphql.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.revenga.rits.back.front.core.model.vo.ProcessInfoVO;
import com.revenga.rits.back.service.graphql.service.ProcessInfoService;

@Component
public class ProcessInfoQuery implements GraphQLQueryResolver {

	@Autowired
	private ProcessInfoService processInfoService;
	
	public List<ProcessInfoVO> getProcessesInfo() {
		
		return processInfoService.getAll();
	}
}
