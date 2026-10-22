package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.ImsIncidentGroup;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentGroupHelper;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentGroupVO;

public class ImsIncidentGroupGraphql extends ImsIncidentGroupVO{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5759738224164825070L;

	public ImsIncidentGroupGraphql(final ImsIncidentGroup imsIncidentGroup) {
		
		ImsIncidentGroupHelper.assign(imsIncidentGroup, this);
	}	

}
