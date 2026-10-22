package com.revenga.rits.back.service.graphql.type;

import java.util.List;

import com.revenga.rits.back.data.core.model.SmStateMachine;
import com.revenga.rits.back.data.core.model.SmThreshold;
import com.revenga.rits.back.front.core.model.helper.SmStateMachineHelper;
import com.revenga.rits.back.front.core.model.vo.SmStateMachineVO;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class SmStateMachineGraphql extends SmStateMachineVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7443840719137447218L;

	private SmStateMachine smStateMachine;
	private List<SmThreshold> smThrehsolds;

	public SmStateMachineGraphql(SmStateMachine smStateMachine) {
		
		SmStateMachineHelper.assign(smStateMachine, this);

		this.smStateMachine = smStateMachine;
		this.smThrehsolds = null;
		
	}
	
}
