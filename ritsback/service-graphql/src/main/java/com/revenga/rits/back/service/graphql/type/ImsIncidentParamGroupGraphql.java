package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.ImsIncidentParamGroup;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentParamGroupHelper;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentParamGroupVO;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class ImsIncidentParamGroupGraphql extends ImsIncidentParamGroupVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5023722221098844697L;

ImsIncidentParamGroupGraphql(final ImsIncidentParamGroup elementGroup) {

		ImsIncidentParamGroupHelper.assign(elementGroup, this);

	}
}