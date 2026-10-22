package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.ImsIncidentTypeCause;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentTypeCauseHelper;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeCauseVO;

public class ImsIncidentTypeCauseGraphql extends ImsIncidentTypeCauseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5438341531321992550L;

	public ImsIncidentTypeCauseGraphql(final ImsIncidentTypeCause imsIncidentTypeCause) {

		ImsIncidentTypeCauseHelper.assign(imsIncidentTypeCause, this);
	}
}
