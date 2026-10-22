package com.revenga.rits.back.front.core.model.helper;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentEnumParam;
import com.revenga.rits.back.data.core.model.ImsIncidentParam;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentEnumParamVO;

public class ImsIncidentEnumParamHelper {

	private ImsIncidentEnumParamHelper() {

		super();
	}

	public static ImsIncidentEnumParamVO get(ImsIncidentEnumParam imsIncidentEnumParam) {

		ImsIncidentEnumParamVO imsIncidentEnumParamVO = null;

		if (imsIncidentEnumParam != null) {

			imsIncidentEnumParamVO = new ImsIncidentEnumParamVO();

			assign(imsIncidentEnumParam, imsIncidentEnumParamVO);
		}

		return imsIncidentEnumParamVO;
	}

	public static ImsIncidentEnumParam get(ImsIncidentEnumParamVO imsIncidentEnumParamVO) {

		ImsIncidentEnumParam imsIncidentEnumParam = null;

		if (imsIncidentEnumParamVO != null) {

			imsIncidentEnumParam = new ImsIncidentEnumParam();

			if (imsIncidentEnumParamVO.getId() != null) {

				Key key = new Key(imsIncidentEnumParamVO.getId());

				if (key.getClazz() == ImsIncidentEnumParam.class) {

					imsIncidentEnumParam.setId(Long.valueOf(key.getId(1)));
					imsIncidentEnumParam.setIncidentParamId(Long.valueOf(key.getId(1)));
				}
			}

			imsIncidentEnumParam.setValue(imsIncidentEnumParamVO.getValue());
		}

		return imsIncidentEnumParam;
	}

	public static void assign(final ImsIncidentEnumParam imsIncidentEnumParam, ImsIncidentEnumParamVO imsIncidentEnumParamVO) {

		if (imsIncidentEnumParam != null && imsIncidentEnumParamVO != null) {

			BasicElementHelper.fillBasicProperties(imsIncidentEnumParamVO, imsIncidentEnumParam);
			
			if (imsIncidentEnumParam.getIncidentParamId() != null) {

				ImsIncidentParam incidentParam = new ImsIncidentParam();
				incidentParam.setId(imsIncidentEnumParam.getIncidentParamId());
				imsIncidentEnumParamVO.setIncidentParamId(BasicElementHelper.getIdFromElement(incidentParam));
			}
			
			imsIncidentEnumParamVO.setValue(imsIncidentEnumParam.getValue());

		}

	}

}