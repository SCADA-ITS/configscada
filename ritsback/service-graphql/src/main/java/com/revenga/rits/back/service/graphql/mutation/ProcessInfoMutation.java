package com.revenga.rits.back.service.graphql.mutation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.revenga.rits.back.service.graphql.service.ProcessInfoService;

@Component
public class ProcessInfoMutation  implements GraphQLMutationResolver{

	
	@Autowired
	private ProcessInfoService processInfoService;
	
	public Boolean stopProcesses(List<Long> pids){
		
		return processInfoService.stopProcesses(pids);
	}
}
