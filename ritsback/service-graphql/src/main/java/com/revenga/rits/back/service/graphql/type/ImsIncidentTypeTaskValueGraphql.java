package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.ImsTaskTypeParam;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentTypeTaskValueHelper;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeTaskValueVO;
import com.revenga.rits.back.front.core.model.vo.ImsTaskTypeParamVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

public class ImsIncidentTypeTaskValueGraphql extends ImsIncidentTypeTaskValueVO {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3047785479935053869L;
	
	
	/**
	 * 
	 */
	private ImsIncidentTypeTaskValue imsIncidentTypeTaskValue;

	public ImsIncidentTypeTaskValueGraphql(final ImsIncidentTypeTaskValue imsIncidentTypeTaskValue) {

		ImsIncidentTypeTaskValueHelper.assign(imsIncidentTypeTaskValue, this);

		this.imsIncidentTypeTaskValue = imsIncidentTypeTaskValue;
	}
	
	/**
	 * 
	 */
	@Override
	public ImsTaskTypeParamVO getTaskTypeParam() {


		if (super.getTaskTypeParam() == null && imsIncidentTypeTaskValue != null
				&& imsIncidentTypeTaskValue.getTaskTypeParamId() != null) {

			ImsTaskTypeParam taskTypeParam = EntitiesManager.getInstance().getImsTaskTypeParamRepository()
								.get(imsIncidentTypeTaskValue.getTaskTypeId(), imsIncidentTypeTaskValue.getTaskTypeParamId(), false);

			if (taskTypeParam != null) {

				super.setTaskTypeParam(new ImsTaskTypeParamGraphql(taskTypeParam));
			}
		}

		return super.getTaskTypeParam();
	}
}