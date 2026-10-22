package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsExtIncidentTypeParam;
import com.revenga.rits.back.front.core.model.vo.ImsExtIncidentTypeParamVO;

public class ImsExtIncidentTypeParamHelper {
	
	private ImsExtIncidentTypeParamHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static ImsExtIncidentTypeParamVO get(final ImsExtIncidentTypeParam imsExtIncidentTypeParam) {

		ImsExtIncidentTypeParamVO imsExtIncidentTypeParamVO = null;

		if (imsExtIncidentTypeParam != null) {

			imsExtIncidentTypeParamVO = new ImsExtIncidentTypeParamVO();

			assign(imsExtIncidentTypeParam, imsExtIncidentTypeParamVO);
		}

		return imsExtIncidentTypeParamVO;
	}

	public static ImsExtIncidentTypeParam get(final ImsExtIncidentTypeParamVO imsExtIncidentTypeParamVO) {

		ImsExtIncidentTypeParam imsExtIncidentTypeParam = null;

		if (imsExtIncidentTypeParamVO != null) {

			imsExtIncidentTypeParam = new ImsExtIncidentTypeParam();
			if (imsExtIncidentTypeParamVO.getId() != null) {

				Key key = new Key(imsExtIncidentTypeParamVO.getId());

				if (key.getClazz() == ImsExtIncidentTypeParam.class) {

					imsExtIncidentTypeParam.setExtIncidentTypeId(Long.valueOf(key.getId(1)));
					imsExtIncidentTypeParam.setId(Long.valueOf(key.getId(2)));
				}
			}

			imsExtIncidentTypeParam.setAlias(imsExtIncidentTypeParamVO.getAlias());
			imsExtIncidentTypeParam.setDescription((imsExtIncidentTypeParamVO.getDescription()));

		}

		return imsExtIncidentTypeParam;
	}

	public static void assign(final ImsExtIncidentTypeParam imsIncidentTypeParam,
			ImsExtIncidentTypeParamVO imsIncidentTypeParamVO) {

		if (ObjectUtils.allNotNull(imsIncidentTypeParam, imsIncidentTypeParamVO)) {

			BasicElementHelper.fillBasicProperties(imsIncidentTypeParamVO, imsIncidentTypeParam);

		}
	}

}
