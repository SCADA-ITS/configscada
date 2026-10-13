package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.ImsIncidentReportTaskValue;
import com.revenga.rits.back.data.core.model.ImsTaskTypeParam;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentReportTaskValueHelper;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportTaskValueVO;
import com.revenga.rits.back.front.core.model.vo.ImsTaskTypeParamVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

public class ImsIncidentReportTaskValueGraphql extends ImsIncidentReportTaskValueVO {


	private static final long serialVersionUID = -6674658798128809776L;
	

	private ImsIncidentReportTaskValue imsIncidentReportTaskValue;


	public ImsIncidentReportTaskValueGraphql(final ImsIncidentReportTaskValue imsIncidentReportTaskValue) {

		ImsIncidentReportTaskValueHelper.assign(imsIncidentReportTaskValue, this);

		this.imsIncidentReportTaskValue = imsIncidentReportTaskValue;
	}
	
	@Override
	public ImsTaskTypeParamVO getTaskTypeParam() {


		if (super.getTaskTypeParam() == null && imsIncidentReportTaskValue != null
				&& imsIncidentReportTaskValue.getTaskTypeParamId() != null) {

			ImsTaskTypeParam imsTaskTypeParam = EntitiesManager.getInstance().getImsTaskTypeParamRepository()
								.get(imsIncidentReportTaskValue.getTaskTypeId(), imsIncidentReportTaskValue.getTaskTypeParamId(), false);

			if (imsTaskTypeParam != null) {

				super.setTaskTypeParam(new ImsTaskTypeParamGraphql(imsTaskTypeParam));
			}
		}

		return super.getTaskTypeParam();
	}
}