package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.LevelOfService;
import com.revenga.rits.back.front.core.model.helper.LevelOfServiceHelper;
import com.revenga.rits.back.front.core.model.vo.LevelOfServiceVO;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class LevelOfServiceGraphql extends LevelOfServiceVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4137316809000213176L;
	private LevelOfService levelOfService;

	public LevelOfServiceGraphql(final LevelOfService levelOfService) {

		LevelOfServiceHelper.assign(levelOfService, this);

	}


}
