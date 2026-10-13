package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.ImsIncidentState;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentStateHelper;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentStateVO;

public class ImsIncidentStateGraphql extends ImsIncidentStateVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2256445169091124655L;
	
	public ImsIncidentStateGraphql(final ImsIncidentState imsIncidentState) {

		ImsIncidentStateHelper.assign(imsIncidentState, this);
	}

}
