package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsExtIncidentTypeParam;
import com.revenga.rits.back.data.core.model.ImsExtIncidentValue;
import com.revenga.rits.back.front.core.model.vo.ImsExtIncidentValueVO;

public class ImsExtIncidentValueHelper {

	private ImsExtIncidentValueHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static ImsExtIncidentValueVO get(final ImsExtIncidentValue imsExtIncidentValue) {

		ImsExtIncidentValueVO imsExtIncidentValueVO = null;

		if (imsExtIncidentValue != null) {

			imsExtIncidentValueVO = new ImsExtIncidentValueVO();

			assign(imsExtIncidentValue, imsExtIncidentValueVO);
		}

		return imsExtIncidentValueVO;
	}

	public static ImsExtIncidentValue get(final ImsExtIncidentValueVO imsExtIncidentValueVO) {

		ImsExtIncidentValue imsExtIncidentValue = null;

		if (imsExtIncidentValueVO != null) {

			imsExtIncidentValue = new ImsExtIncidentValue();

			if (imsExtIncidentValueVO.getExtIncidentTypeParamId() != null) {

				Key key = new Key(imsExtIncidentValueVO.getExtIncidentTypeParamId());

				if (key.getClazz() == ImsExtIncidentTypeParam.class) {

					imsExtIncidentValue.setExtIncidentTypeId(Long.valueOf(key.getId(1)));
					imsExtIncidentValue.setExtIncidentTypeParamId(Long.valueOf(key.getId(2)));
				}
			}

			if (imsExtIncidentValueVO.getExtIncidentTypeParam() != null) {

				ImsExtIncidentTypeParam imsExtIncidentTypeParam = ImsExtIncidentTypeParamHelper
						.get(imsExtIncidentValueVO.getExtIncidentTypeParam());

				if (imsExtIncidentTypeParam != null) {

					if (imsExtIncidentValueVO.getExtIncidentTypeParamId() == null) {

						imsExtIncidentValue.setExtIncidentTypeId(imsExtIncidentTypeParam.getExtIncidentTypeId());
						imsExtIncidentValue.setExtIncidentTypeParamId(imsExtIncidentTypeParam.getId());
					}
					imsExtIncidentValue.setExtIncidentTypeParam(imsExtIncidentTypeParam);
				}
			}

			if (imsExtIncidentValueVO.getId() != null) {

				Key key = new Key(imsExtIncidentValueVO.getId());

				if (key.getClazz() == ImsExtIncidentValue.class) {

					imsExtIncidentValue.setExtIncidentTypeId(Long.valueOf(key.getId(1)));
					imsExtIncidentValue.setExtIncidentTypeParamId(Long.valueOf(key.getId(2)));
					imsExtIncidentValue.setExtIncidentId(Long.valueOf(key.getId(3)));
				}
			}

			imsExtIncidentValue.setAlias(imsExtIncidentValueVO.getAlias());
			imsExtIncidentValue.setDescription((imsExtIncidentValueVO.getDescription()));
			imsExtIncidentValue.setValue((imsExtIncidentValueVO.getValue()));
		}

		return imsExtIncidentValue;
	}

	public static void assign(final ImsExtIncidentValue imsExtIncidentValue,
			ImsExtIncidentValueVO imsExtIncidentValueVO) {

		if (ObjectUtils.allNotNull(imsExtIncidentValue, imsExtIncidentValueVO)) {

			BasicElementHelper.fillBasicProperties(imsExtIncidentValueVO, imsExtIncidentValue);

			imsExtIncidentValueVO.setExtIncidentTypeParam(
					ImsExtIncidentTypeParamHelper.get(imsExtIncidentValue.getExtIncidentTypeParam()));

			imsExtIncidentValueVO.setValue((imsExtIncidentValue.getValue()));
		}
	}

}
