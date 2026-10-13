package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.ImsIncidentParam;
import com.revenga.rits.back.data.core.model.ImsIncidentReportParamValue;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentReportParamValueHelper;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentParamVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportParamValueVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

public class ImsIncidentReportParamValueGraphql extends ImsIncidentReportParamValueVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2027411446407464753L;
	
	private ImsIncidentReportParamValue incidentParamValue;

	public ImsIncidentReportParamValueGraphql(final ImsIncidentReportParamValue incidentParamValue) {

		ImsIncidentReportParamValueHelper.assign(incidentParamValue, this);

		this.incidentParamValue = incidentParamValue;
	}
	
	@Override
	public ImsIncidentParamVO getIncidentParam() {

		if (super.getIncidentParam() == null && incidentParamValue != null
				&& incidentParamValue.getIncidentParamId() != null) {

			ImsIncidentParam incidentParam = EntitiesManager.getInstance().getImsIncidentParamRepository()
					.get(incidentParamValue.getIncidentParamId(), true);

			if (incidentParam != null) {

				super.setIncidentParam(new ImsIncidentParamGraphql(incidentParam));

			}
		}

		return super.getIncidentParam();
	}
	
	@Override
	public ImsIncidentReportVO getIncidentReport() {

		if (super.getIncidentReport() == null && incidentParamValue != null
				&& incidentParamValue.getIncidentReportId() != null) {

			ImsIncidentReport incidentReport = EntitiesManager.getInstance().getImsIncidentReportRepository()
					.get(incidentParamValue.getIncidentReportId(), true);

			if (incidentReport != null) {

				super.setIncidentReport(new ImsIncidentReportGraphql(incidentReport));

			}
		}

		return super.getIncidentReport();
	}
}