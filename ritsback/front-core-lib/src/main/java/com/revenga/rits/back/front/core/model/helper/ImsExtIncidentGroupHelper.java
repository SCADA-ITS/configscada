package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsExtIncidentGroup;
import com.revenga.rits.back.front.core.model.vo.ImsExtIncidentGroupVO;

public class ImsExtIncidentGroupHelper {
	
	private ImsExtIncidentGroupHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static ImsExtIncidentGroupVO get(final ImsExtIncidentGroup imsExtIncidentGroup) {

		ImsExtIncidentGroupVO imsExtIncidentGroupVO = null;

		if (imsExtIncidentGroup != null) {

			imsExtIncidentGroupVO = new ImsExtIncidentGroupVO();

			assign(imsExtIncidentGroup, imsExtIncidentGroupVO);
		}

		return imsExtIncidentGroupVO;
	}

	public static ImsExtIncidentGroup get(final ImsExtIncidentGroupVO imsExtIncidentGroupVO) {

		ImsExtIncidentGroup imsExtIncidentGroup = null;

		if (imsExtIncidentGroupVO != null) {

			imsExtIncidentGroup = new ImsExtIncidentGroup();
			if (imsExtIncidentGroupVO.getId() != null) {

				Key key = new Key(imsExtIncidentGroupVO.getId());

				if (key.getClazz() == ImsExtIncidentGroup.class) {

					imsExtIncidentGroup.setId(Long.valueOf(key.getId(1)));
				}
			}

			imsExtIncidentGroup.setAlias(imsExtIncidentGroupVO.getAlias());
			imsExtIncidentGroup.setDescription((imsExtIncidentGroupVO.getDescription()));

		}

		return imsExtIncidentGroup;
	}

	public static void assign(final ImsExtIncidentGroup imsExtIncidentGroup, ImsExtIncidentGroupVO imsExtIncidentGroupVO) {

		if (ObjectUtils.allNotNull(imsExtIncidentGroup, imsExtIncidentGroupVO)) {

			BasicElementHelper.fillBasicProperties(imsExtIncidentGroupVO, imsExtIncidentGroup);


		}
	}

}
