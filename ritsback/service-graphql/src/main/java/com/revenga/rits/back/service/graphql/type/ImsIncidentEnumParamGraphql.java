package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.ImsIncidentEnumParam;
import com.revenga.rits.back.data.core.model.ImsIncidentParam;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentEnumParamHelper;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentEnumParamVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentParamVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

public class ImsIncidentEnumParamGraphql extends ImsIncidentEnumParamVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4236372544860713429L;
	
	private ImsIncidentEnumParam incidentEnumParam;

	public ImsIncidentEnumParamGraphql(final ImsIncidentEnumParam incidentEnumParam) {

		ImsIncidentEnumParamHelper.assign(incidentEnumParam, this);

		this.incidentEnumParam = incidentEnumParam;
	}
	
	@Override
	public ImsIncidentParamVO getIncidentParam() {

		if (super.getIncidentParam() == null && incidentEnumParam != null
				&& incidentEnumParam.getIncidentParamId() != null) {

			ImsIncidentParam incidentParam = EntitiesManager.getInstance().getImsIncidentParamRepository()
					.get(incidentEnumParam.getIncidentParamId(), true);

			if (incidentParam != null) {

				super.setIncidentParam(new ImsIncidentParamGraphql(incidentParam));

			}
		}

		return super.getIncidentParam();
	}
}