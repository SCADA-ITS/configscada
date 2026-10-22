package com.revenga.rits.back.front.core.model.helper;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentParamGroup;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentParamGroupVO;

public class ImsIncidentParamGroupHelper {

	private ImsIncidentParamGroupHelper() {

		super();
	}

	public static ImsIncidentParamGroupVO get(ImsIncidentParamGroup imsIncidentParamGroup) {

		ImsIncidentParamGroupVO imsIncidentParamGroupVO = null;

		if (imsIncidentParamGroup != null) {

			imsIncidentParamGroupVO = new ImsIncidentParamGroupVO();

			assign(imsIncidentParamGroup, imsIncidentParamGroupVO);
		}

		return imsIncidentParamGroupVO;
	}

	public static ImsIncidentParamGroup get(ImsIncidentParamGroupVO imsIncidentParamGroupVO) {

		ImsIncidentParamGroup imsIncidentParamGroup = null;

		if (imsIncidentParamGroupVO != null) {

			imsIncidentParamGroup = new ImsIncidentParamGroup();

			if (imsIncidentParamGroupVO.getId() != null) {

				Key key = new Key(imsIncidentParamGroupVO.getId());

				if (key.getClazz() == ImsIncidentParamGroup.class) {

					imsIncidentParamGroup.setId(Long.valueOf(key.getId(1)));
				}
			}

			imsIncidentParamGroup.setAlias(imsIncidentParamGroupVO.getAlias());
			imsIncidentParamGroup.setDescription((imsIncidentParamGroupVO.getDescription()));
			imsIncidentParamGroup.setLabelAlias(imsIncidentParamGroupVO.getLabelAlias());
			imsIncidentParamGroup.setLabelDescription((imsIncidentParamGroupVO.getLabelDescription()));

		}

		return imsIncidentParamGroup;
	}

	public static void assign(final ImsIncidentParamGroup imsIncidentParamGroup, ImsIncidentParamGroupVO imsIncidentParamGroupVO) {

		if (imsIncidentParamGroup != null && imsIncidentParamGroupVO != null) {

			BasicElementHelper.fillBasicProperties(imsIncidentParamGroupVO, imsIncidentParamGroup);
			

		}

	}

}