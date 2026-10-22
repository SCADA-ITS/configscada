package com.revenga.rits.back.service.graphql.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.revenga.rits.back.front.core.model.vo.SmStateMachineVO;
import com.revenga.rits.back.front.core.model.vo.SmThresholdVO;
import com.revenga.rits.back.service.graphql.service.SmStateMachineService;

@Component
public class SmQuery implements GraphQLQueryResolver {
	
	@Autowired
	private SmStateMachineService smStateMachineService;
	
	public List<SmThresholdVO> getSmThresholds(final String stateMachineId) {

		return smStateMachineService.getsSmThresholds(stateMachineId);
	}
	
	public List<SmStateMachineVO> getSmStateMachines(List<String> smStateMachineIds){
		
		return smStateMachineService.getSmStateMachines(smStateMachineIds);
	}
}
