package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.ProcessInfo;
import com.revenga.rits.back.front.core.model.helper.ProcessInfoHelper;
import com.revenga.rits.back.front.core.model.vo.ProcessInfoVO;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class ProcessInfoGraphql extends ProcessInfoVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3219798252805649068L;
	
	private ProcessInfo processInfo;
	
	public ProcessInfoGraphql(ProcessInfo processInfo) {
		
		ProcessInfoHelper.assign(processInfo, this);

		this.processInfo = processInfo;
		
	}

}
