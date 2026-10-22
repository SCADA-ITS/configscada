package com.revenga.rits.back.front.core.model.helper;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.ImsTaskTypeParam;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeTaskValueVO;

public class ImsIncidentTypeTaskValueHelper {

	private ImsIncidentTypeTaskValueHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static ImsIncidentTypeTaskValueVO get(ImsIncidentTypeTaskValue imsIncidentTypeTaskValue) {

		ImsIncidentTypeTaskValueVO imsIncidentTypeTaskValueVO = null;

		if (imsIncidentTypeTaskValue != null) {

			imsIncidentTypeTaskValueVO = new ImsIncidentTypeTaskValueVO();

			assign(imsIncidentTypeTaskValue, imsIncidentTypeTaskValueVO);
		}

		return imsIncidentTypeTaskValueVO;
	}

	public static ImsIncidentTypeTaskValue get(ImsIncidentTypeTaskValueVO imsIncidentTypeTaskValueVO) {

		ImsIncidentTypeTaskValue imsIncidentTypeTaskValue = null;

		if (imsIncidentTypeTaskValueVO != null) {

			imsIncidentTypeTaskValue = new ImsIncidentTypeTaskValue();

			if (imsIncidentTypeTaskValueVO.getTaskTypeParamId() != null) {

				Key key = new Key(imsIncidentTypeTaskValueVO.getTaskTypeParamId());

				if (key.getClazz() == ImsTaskTypeParam.class) {

					imsIncidentTypeTaskValue.setTaskTypeId(Long.valueOf(key.getId(1)));
					imsIncidentTypeTaskValue.setTaskTypeParamId(Long.valueOf(key.getId(2)));
				}
			}
			imsIncidentTypeTaskValue.setValue(imsIncidentTypeTaskValueVO.getValue());
		}

		return imsIncidentTypeTaskValue;
	}


	public static void assign(final ImsIncidentTypeTaskValue imsIncidentTypeTaskValue,
			ImsIncidentTypeTaskValueVO imsIncidentTypeTaskValueVO) {

		if (imsIncidentTypeTaskValue != null && imsIncidentTypeTaskValueVO != null) {

			BasicElementHelper.fillBasicProperties(imsIncidentTypeTaskValueVO, imsIncidentTypeTaskValue);

			imsIncidentTypeTaskValueVO.setValue(imsIncidentTypeTaskValue.getValue());
		}

	}

}
