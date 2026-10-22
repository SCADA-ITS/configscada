package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentReportTaskState;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportTaskStateVO;

public class ImsIncidentReportTaskStateHelper {
	
	private ImsIncidentReportTaskStateHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static ImsIncidentReportTaskStateVO get(final ImsIncidentReportTaskState imsIncidentReportTaskState) {

		ImsIncidentReportTaskStateVO imsIncidentReportTaskStateVO = null;

		if (imsIncidentReportTaskState != null) {

			imsIncidentReportTaskStateVO = new ImsIncidentReportTaskStateVO();

			assign(imsIncidentReportTaskState, imsIncidentReportTaskStateVO);
		}

		return imsIncidentReportTaskStateVO;
	}

	public static ImsIncidentReportTaskState get(final ImsIncidentReportTaskStateVO imsIncidentReportTaskStateVO) {

		ImsIncidentReportTaskState imsIncidentReportTaskState = null;

		if (imsIncidentReportTaskStateVO != null) {

			imsIncidentReportTaskState = new ImsIncidentReportTaskState();
			if (imsIncidentReportTaskStateVO.getId() != null) {

				Key key = new Key(imsIncidentReportTaskStateVO.getId());

				if (key.getClazz() == ImsIncidentReportTaskState.class) {

					imsIncidentReportTaskState.setIncidentReportId(Long.valueOf(key.getId(1)));
					imsIncidentReportTaskState.setIncidentTypeTaskId(Long.valueOf(key.getId(2)));
				}
			}

			imsIncidentReportTaskState.setCompletedAt(imsIncidentReportTaskStateVO.getCompletedAt());
			imsIncidentReportTaskState.setError(imsIncidentReportTaskStateVO.getError());

		}

		return imsIncidentReportTaskState;
	}

	public static void assign(final ImsIncidentReportTaskState imsIncidentReportTaskState,
			ImsIncidentReportTaskStateVO imsIncidentReportTaskStateVO) {

		if (ObjectUtils.allNotNull(imsIncidentReportTaskState, imsIncidentReportTaskStateVO)) {

			BasicElementHelper.fillBasicProperties(imsIncidentReportTaskStateVO, imsIncidentReportTaskState);

			imsIncidentReportTaskStateVO.setCompletedAt(imsIncidentReportTaskState.getCompletedAt());
			imsIncidentReportTaskStateVO.setError(imsIncidentReportTaskState.getError());

		}
	}


}
