package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ImsIncidentReportAlarm;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentReportAlarmHelper;
import com.revenga.rits.back.front.core.model.vo.AlarmConfigVO;
import com.revenga.rits.back.front.core.model.vo.ElementVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportAlarmVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class ImsIncidentReportAlarmGraphql extends ImsIncidentReportAlarmVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4407504405154188276L;

	private ImsIncidentReportAlarm incidentReportAlarm;

	public ImsIncidentReportAlarmGraphql(final ImsIncidentReportAlarm incidentReportAlarm) {

		ImsIncidentReportAlarmHelper.assign(incidentReportAlarm, this);

		this.incidentReportAlarm = incidentReportAlarm;
	}

	@Override
	public AlarmConfigVO getAlarmConfig() {

		if (super.getAlarmConfig() == null && incidentReportAlarm != null) {

			AlarmConfig alarmConfig = EntitiesManager.getInstance().getAlarmConfigRepository()
					.get(incidentReportAlarm.getAlarmConfigId(), false);

			if (alarmConfig != null) {

				super.setAlarmConfig(new AlarmConfigGraphql(alarmConfig));
			}
		}

		return super.getAlarmConfig();
	}

	@Override
	public ElementVO getElement() {

		if (super.getElement() == null && incidentReportAlarm != null) {

			Element element = EntitiesManager.getInstance().getElementRepository()
					.get(incidentReportAlarm.getElementTypeId(), incidentReportAlarm.getElementId(), false);

			if (element != null) {

				super.setElement(new ElementGraphql(element));
			}
		}
		return super.getElement();
	}

}
