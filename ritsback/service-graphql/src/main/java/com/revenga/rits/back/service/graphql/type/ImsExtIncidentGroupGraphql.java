package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.ImsExtIncidentGroup;
import com.revenga.rits.back.front.core.model.helper.ImsExtIncidentGroupHelper;
import com.revenga.rits.back.front.core.model.vo.ImsExtIncidentGroupVO;

public class ImsExtIncidentGroupGraphql extends ImsExtIncidentGroupVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4770465298058892329L;
	
	public ImsExtIncidentGroupGraphql(final ImsExtIncidentGroup extIncidentGroup) {
		
		ImsExtIncidentGroupHelper.assign(extIncidentGroup, this);

	}

}
