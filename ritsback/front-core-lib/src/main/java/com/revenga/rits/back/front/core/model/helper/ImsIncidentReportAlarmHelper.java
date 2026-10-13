package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.data.core.model.ImsIncidentReportAlarm;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportAlarmVO;

public class ImsIncidentReportAlarmHelper {

	private ImsIncidentReportAlarmHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static ImsIncidentReportAlarmVO get(final ImsIncidentReportAlarm incidentReportAlarm) {

		ImsIncidentReportAlarmVO incidentReportAlarmVO = null;

		if (incidentReportAlarm != null) {

			incidentReportAlarmVO = new ImsIncidentReportAlarmVO();

			assign(incidentReportAlarm, incidentReportAlarmVO);
		}

		return incidentReportAlarmVO;
	}

	public static void assign(final ImsIncidentReportAlarm incidentReportAlarm,
			ImsIncidentReportAlarmVO incidentReportAlarmVO) {

		if (ObjectUtils.allNotNull(incidentReportAlarm, incidentReportAlarmVO)) {

			BasicElementHelper.fillBasicProperties(incidentReportAlarmVO, incidentReportAlarm);
			
			incidentReportAlarmVO.setActivation(incidentReportAlarm.getActivation());
		}

	}

}
