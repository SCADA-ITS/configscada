package com.revenga.rits.back.service.graphql.mutation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.revenga.rits.back.front.core.model.vo.SmThresholdVO;
import com.revenga.rits.back.service.graphql.service.SmStateMachineService;

@Component
public class SmMutation implements GraphQLMutationResolver{

	@Autowired
	private SmStateMachineService smThresholdService;
	
	public List<SmThresholdVO> updateSmThresholds(List<SmThresholdVO> smThresholds){
		
		return smThresholdService.updateSmThresholds(smThresholds);
	}
}
