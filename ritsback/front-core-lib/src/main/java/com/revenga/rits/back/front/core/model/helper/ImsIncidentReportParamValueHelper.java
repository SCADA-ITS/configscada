package com.revenga.rits.back.front.core.model.helper;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentParam;
import com.revenga.rits.back.data.core.model.ImsIncidentReportParamValue;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportParamValueVO;

public class ImsIncidentReportParamValueHelper {

	private ImsIncidentReportParamValueHelper() {
		super();
	}

	public static ImsIncidentReportParamValueVO get(final ImsIncidentReportParamValue imsIncidentParamValue) {

		ImsIncidentReportParamValueVO imsIncidentParamValueVO = null;

		if (imsIncidentParamValue != null) {

			imsIncidentParamValueVO = new ImsIncidentReportParamValueVO();

			assign(imsIncidentParamValue, imsIncidentParamValueVO);
		}

		return imsIncidentParamValueVO;
	}

	public static ImsIncidentReportParamValue get(final ImsIncidentReportParamValueVO imsIncidentParamValueVO) {

		ImsIncidentReportParamValue imsIncidentParamValue = null;

		if (imsIncidentParamValueVO != null) {

			imsIncidentParamValue = new ImsIncidentReportParamValue();

			if (imsIncidentParamValueVO.getIncidentParamId() != null) {

				Key key = new Key(imsIncidentParamValueVO.getIncidentParamId());

				if (key.getClazz() == ImsIncidentParam.class) {

					imsIncidentParamValue.setIncidentParamId(Long.valueOf(key.getId(1)));
				}
			}
			
			if (imsIncidentParamValueVO.getIncidentReportId() != null) {

				Key key = new Key(imsIncidentParamValueVO.getIncidentReportId());

				if (key.getClazz() == ImsIncidentReport.class) {

					imsIncidentParamValue.setIncidentReportId(Long.valueOf(key.getId(1)));
				}
			}
			
			imsIncidentParamValue.setValue(imsIncidentParamValueVO.getValue());
		}
		
		return imsIncidentParamValue;
	}

	public static void assign(final ImsIncidentReportParamValue imsIncidentParamValue, ImsIncidentReportParamValueVO imsIncidentParamValueVO) {

		if (imsIncidentParamValue != null && imsIncidentParamValueVO != null) {

			BasicElementHelper.fillBasicProperties(imsIncidentParamValueVO, imsIncidentParamValue);

			if (imsIncidentParamValue.getIncidentParamId() != null) {

				ImsIncidentParam incidentParam = new ImsIncidentParam();
				incidentParam.setId(imsIncidentParamValue.getIncidentParamId());
				imsIncidentParamValueVO.setIncidentParamId(BasicElementHelper.getIdFromElement(incidentParam));
			}
			
			if (imsIncidentParamValue.getIncidentReportId() != null) {

				ImsIncidentReport incidentReport = new ImsIncidentReport();
				incidentReport.setId(imsIncidentParamValue.getIncidentReportId());
				imsIncidentParamValueVO.setIncidentReportId(BasicElementHelper.getIdFromElement(incidentReport));
			}
			
			imsIncidentParamValueVO.setValue(imsIncidentParamValue.getValue());
		}

		ModelVOHelper.translate(imsIncidentParamValueVO);
	}
}