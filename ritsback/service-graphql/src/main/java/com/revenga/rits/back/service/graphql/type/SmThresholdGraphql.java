package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.SmThreshold;
import com.revenga.rits.back.front.core.model.helper.SmThresholdHelper;
import com.revenga.rits.back.front.core.model.vo.SmThresholdVO;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class SmThresholdGraphql extends SmThresholdVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4842563792796801463L;
	
	private SmThreshold smThreshold;

	public SmThresholdGraphql(SmThreshold smThreshold) {
		
		SmThresholdHelper.assign(smThreshold, this);

		this.smThreshold = smThreshold;
		
	}
}
