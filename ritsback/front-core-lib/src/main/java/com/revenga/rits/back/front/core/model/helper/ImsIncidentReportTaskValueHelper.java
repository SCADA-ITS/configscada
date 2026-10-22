package com.revenga.rits.back.front.core.model.helper;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentReportTaskValue;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentReportTaskValueVO;

public class ImsIncidentReportTaskValueHelper {

	private ImsIncidentReportTaskValueHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static ImsIncidentReportTaskValueVO get(ImsIncidentReportTaskValue imsIncidentReportTaskValue) {

		ImsIncidentReportTaskValueVO imsIncidentReportTaskValueVO = null;

		if (imsIncidentReportTaskValue != null) {

			imsIncidentReportTaskValueVO = new ImsIncidentReportTaskValueVO();

			assign(imsIncidentReportTaskValue, imsIncidentReportTaskValueVO);
		}

		return imsIncidentReportTaskValueVO;
	}

	public static ImsIncidentReportTaskValue get(ImsIncidentReportTaskValueVO imsIncidentReportTaskValueVO) {

		ImsIncidentReportTaskValue imsIncidentReportTaskValue = null;

		if (imsIncidentReportTaskValueVO != null) {

			imsIncidentReportTaskValue = new ImsIncidentReportTaskValue();

			if (imsIncidentReportTaskValueVO.getId() != null) {

				Key key = new Key(imsIncidentReportTaskValueVO.getId());

				if (key.getClazz() == ImsIncidentReportTaskValue.class) {

					imsIncidentReportTaskValue.setPks(key.getIds());
				}
			}
		}

		return imsIncidentReportTaskValue;
	}

	public static void assign(final ImsIncidentReportTaskValue imsIncidentReportTaskValue,
			ImsIncidentReportTaskValueVO imsIncidentReportTaskValueVO) {

		if (imsIncidentReportTaskValue != null && imsIncidentReportTaskValueVO != null) {

			BasicElementHelper.fillBasicProperties(imsIncidentReportTaskValueVO, imsIncidentReportTaskValue);

			imsIncidentReportTaskValueVO.setValue(imsIncidentReportTaskValue.getValue());
		}
	}
}
