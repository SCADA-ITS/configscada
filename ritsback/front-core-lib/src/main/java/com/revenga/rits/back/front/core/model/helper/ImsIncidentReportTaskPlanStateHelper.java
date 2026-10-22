package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentReportTaskPlanState;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportTaskPlanStateVO;

public class ImsIncidentReportTaskPlanStateHelper {
	
	private ImsIncidentReportTaskPlanStateHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static ImsIncidentReportTaskPlanStateVO get(final ImsIncidentReportTaskPlanState imsIncidentReportTaskPlanState) {

		ImsIncidentReportTaskPlanStateVO imsIncidentReportTaskPlanStateVO = null;

		if (imsIncidentReportTaskPlanState != null) {

			imsIncidentReportTaskPlanStateVO = new ImsIncidentReportTaskPlanStateVO();

			assign(imsIncidentReportTaskPlanState, imsIncidentReportTaskPlanStateVO);
		}

		return imsIncidentReportTaskPlanStateVO;
	}

	public static ImsIncidentReportTaskPlanState get(final ImsIncidentReportTaskPlanStateVO imsIncidentReportTaskPlanStateVO) {

		ImsIncidentReportTaskPlanState imsIncidentReportTaskPlanState = null;

		if (imsIncidentReportTaskPlanStateVO != null) {

			imsIncidentReportTaskPlanState = new ImsIncidentReportTaskPlanState();
			if (imsIncidentReportTaskPlanStateVO.getId() != null) {

				Key key = new Key(imsIncidentReportTaskPlanStateVO.getId());

				if (key.getClazz() == ImsIncidentReportTaskPlanState.class) {

					imsIncidentReportTaskPlanState.setIncidentReportId(Long.valueOf(key.getId(1)));
					imsIncidentReportTaskPlanState.setIncidentTaskPlanId(Long.valueOf(key.getId(2)));
				}
			}

			imsIncidentReportTaskPlanState.setCompletedAt(imsIncidentReportTaskPlanStateVO.getCompletedAt());

		}

		return imsIncidentReportTaskPlanState;
	}

	public static void assign(final ImsIncidentReportTaskPlanState imsIncidentReportTaskPlanState,
			ImsIncidentReportTaskPlanStateVO imsIncidentReportTaskPlanStateVO) {

		if (ObjectUtils.allNotNull(imsIncidentReportTaskPlanState, imsIncidentReportTaskPlanStateVO)) {

			BasicElementHelper.fillBasicProperties(imsIncidentReportTaskPlanStateVO, imsIncidentReportTaskPlanState);

			imsIncidentReportTaskPlanStateVO.setCompletedAt(imsIncidentReportTaskPlanState.getCompletedAt());

		}
	}

}
