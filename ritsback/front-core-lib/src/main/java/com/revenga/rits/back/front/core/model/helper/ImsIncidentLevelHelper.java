package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentLevel;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentLevelVO;

public class ImsIncidentLevelHelper {
	
	private ImsIncidentLevelHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static ImsIncidentLevelVO get(final ImsIncidentLevel imsIncidentLevel) {

		ImsIncidentLevelVO imsIncidentLevelVO = null;

		if (imsIncidentLevel != null) {

			imsIncidentLevelVO = new ImsIncidentLevelVO();

			assign(imsIncidentLevel, imsIncidentLevelVO);
		}

		return imsIncidentLevelVO;
	}

	public static ImsIncidentLevel get(final ImsIncidentLevelVO imsIncidentLevelVO) {

		ImsIncidentLevel imsIncidentLevel = null;

		if (imsIncidentLevelVO != null) {

			imsIncidentLevel = new ImsIncidentLevel();
			if (imsIncidentLevelVO.getId() != null) {

				Key key = new Key(imsIncidentLevelVO.getId());

				if (key.getClazz() == ImsIncidentLevel.class) {

					imsIncidentLevel.setId(Long.valueOf(key.getId(1)));
				}
			}

			imsIncidentLevel.setPriority ((imsIncidentLevelVO.getPriority()));
			imsIncidentLevel.setAlias(imsIncidentLevelVO.getAlias());
			imsIncidentLevel.setDescription((imsIncidentLevelVO.getDescription()));

		}

		return imsIncidentLevel;
	}

	public static void assign(final ImsIncidentLevel imsIncidentLevel,
			ImsIncidentLevelVO imsIncidentLevelVO) {

		if (ObjectUtils.allNotNull(imsIncidentLevel, imsIncidentLevelVO)) {

			BasicElementHelper.fillBasicProperties(imsIncidentLevelVO, imsIncidentLevel);
			
			imsIncidentLevelVO.setPriority(imsIncidentLevel.getPriority());
		}
	}


}
