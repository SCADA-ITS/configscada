package com.revenga.rits.back.front.core.model.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsExtIncidentGroup;
import com.revenga.rits.back.data.core.model.ImsExtIncidentType;
import com.revenga.rits.back.data.core.model.ImsExtIncidentTypeParam;
import com.revenga.rits.back.front.core.model.vo.ImsExtIncidentTypeParamVO;
import com.revenga.rits.back.front.core.model.vo.ImsExtIncidentTypeVO;

public class ImsExtIncidentTypeHelper {

	private ImsExtIncidentTypeHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static ImsExtIncidentTypeVO get(final ImsExtIncidentType imsExtIncidentType) {

		ImsExtIncidentTypeVO imsExtIncidentTypeVO = null;

		if (imsExtIncidentType != null) {

			imsExtIncidentTypeVO = new ImsExtIncidentTypeVO();

			assign(imsExtIncidentType, imsExtIncidentTypeVO);
		}

		return imsExtIncidentTypeVO;
	}

	public static ImsExtIncidentType get(final ImsExtIncidentTypeVO imsExtIncidentTypeVO) {

		ImsExtIncidentType imsExtIncidentType = null;

		if (imsExtIncidentTypeVO != null) {

			imsExtIncidentType = new ImsExtIncidentType();
			if (imsExtIncidentTypeVO.getId() != null) {

				Key key = new Key(imsExtIncidentTypeVO.getId());

				if (key.getClazz() == ImsExtIncidentType.class) {

					imsExtIncidentType.setId(Long.valueOf(key.getId(1)));
				}
			}

			imsExtIncidentType.setAlias(imsExtIncidentTypeVO.getAlias());
			imsExtIncidentType.setDescription((imsExtIncidentTypeVO.getDescription()));
			imsExtIncidentType.setConfirmationIsNeeded(imsExtIncidentTypeVO.getConfirmationIsNeeded());

			if (imsExtIncidentTypeVO.getExtIncidentGroup() != null) {

				ImsExtIncidentGroup extIncidentGroup = ImsExtIncidentGroupHelper
						.get(imsExtIncidentTypeVO.getExtIncidentGroup());

				if (extIncidentGroup != null) {

					imsExtIncidentType.setExtIncidentGroup(extIncidentGroup);
				}
			}

			if (imsExtIncidentTypeVO.getExtIncidentTypeParams() != null) {

				List<ImsExtIncidentTypeParam> imsExtIncidentTypeParams = new ArrayList<>();

				for (ImsExtIncidentTypeParamVO imsExtIncidentTypeParamsVO : imsExtIncidentTypeVO
						.getExtIncidentTypeParams()) {

					ImsExtIncidentTypeParam imsExtIncidentTypeParam = ImsExtIncidentTypeParamHelper
							.get(imsExtIncidentTypeParamsVO);

					imsExtIncidentTypeParams.add(imsExtIncidentTypeParam);
				}

				imsExtIncidentType.setExtIncidentTypeParams(imsExtIncidentTypeParams);
			}
		}

		return imsExtIncidentType;
	}

	public static void assign(final ImsExtIncidentType imsExtIncidentType, ImsExtIncidentTypeVO imsExtIncidentTypeVO) {

		if (ObjectUtils.allNotNull(imsExtIncidentType, imsExtIncidentTypeVO)) {

			BasicElementHelper.fillBasicProperties(imsExtIncidentTypeVO, imsExtIncidentType);

			imsExtIncidentTypeVO.setConfirmationIsNeeded(imsExtIncidentType.getConfirmationIsNeeded());

			imsExtIncidentTypeVO
					.setExtIncidentGroup(ImsExtIncidentGroupHelper.get(imsExtIncidentType.getExtIncidentGroup()));

			if (imsExtIncidentType.getExtIncidentTypeParams() != null) {

				List<ImsExtIncidentTypeParamVO> imsExtIncidentTypeParamVO = new ArrayList<>();

				for (ImsExtIncidentTypeParam imsExtIncidentTypeParam : imsExtIncidentType.getExtIncidentTypeParams()) {

					if (imsExtIncidentTypeParam.getExtIncidentTypeId() != null) {

						imsExtIncidentTypeParamVO.add(ImsExtIncidentTypeParamHelper.get(imsExtIncidentTypeParam));
					}
				}

				imsExtIncidentTypeVO.setExtIncidentTypeParams(imsExtIncidentTypeParamVO);
			}

		}
	}

}
