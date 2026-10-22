package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.ImsIncidentReportLogValue;
import com.revenga.rits.back.data.core.model.LogTypeParam;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentReportLogValueHelper;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportLogValueVO;
import com.revenga.rits.back.front.core.model.vo.LogTypeParamVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class ImsIncidentReportLogValueGraphql extends ImsIncidentReportLogValueVO {
	/**
	* 
	*/
	private static final long serialVersionUID = 2337896986787124229L;

	private ImsIncidentReportLogValue imsIncidentReportLogValue;

	public ImsIncidentReportLogValueGraphql(final ImsIncidentReportLogValue imsIncidentReportLogValue) {

		ImsIncidentReportLogValueHelper.assign(imsIncidentReportLogValue, this);
		this.imsIncidentReportLogValue = imsIncidentReportLogValue;

	}

	@Override
	public LogTypeParamVO getLogTypeParam() {

		if (super.getLogTypeParam() == null && imsIncidentReportLogValue != null
				&& imsIncidentReportLogValue.getLogTypeParamId() != null) {

			LogTypeParam logTypeParam = EntitiesManager.getInstance().getLogTypeParamRepository().get(
					imsIncidentReportLogValue.getLogTypeId(), imsIncidentReportLogValue.getLogTypeParamId(), false);

			if (logTypeParam != null) {

				super.setLogTypeParam(new LogTypeParamGraphql(logTypeParam));
			}
		}

		return super.getLogTypeParam();
	}

}
