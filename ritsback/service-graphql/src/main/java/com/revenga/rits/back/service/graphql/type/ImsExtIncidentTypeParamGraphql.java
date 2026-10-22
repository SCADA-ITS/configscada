package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.ImsExtIncidentTypeParam;
import com.revenga.rits.back.front.core.model.helper.ImsExtIncidentTypeParamHelper;
import com.revenga.rits.back.front.core.model.vo.ImsExtIncidentTypeParamVO;

public class ImsExtIncidentTypeParamGraphql extends ImsExtIncidentTypeParamVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5565852379075646652L;
	
	public ImsExtIncidentTypeParamGraphql(final ImsExtIncidentTypeParam imsExtIncidentTypeParam) {

		ImsExtIncidentTypeParamHelper.assign(imsExtIncidentTypeParam, this);
	}

}
