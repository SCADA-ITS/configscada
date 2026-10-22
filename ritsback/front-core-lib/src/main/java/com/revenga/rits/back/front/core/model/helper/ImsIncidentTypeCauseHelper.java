package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeCause;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeCauseVO;

public class ImsIncidentTypeCauseHelper {
	
	/**
	 * 
	 */
	private ImsIncidentTypeCauseHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @param imsIncidentTypeCause
	 * @return
	 */
	public static ImsIncidentTypeCauseVO get(final ImsIncidentTypeCause imsIncidentTypeCause) {

		ImsIncidentTypeCauseVO imsIncidentTypeCauseVO = null;

		if (imsIncidentTypeCause != null) {

			imsIncidentTypeCauseVO = new ImsIncidentTypeCauseVO();

			assign(imsIncidentTypeCause, imsIncidentTypeCauseVO);
		}

		return imsIncidentTypeCauseVO;
	}

	/**
	 * 
	 * @param imsIncidentTypeCauseVO
	 * @return
	 */
	public static ImsIncidentTypeCause get(final ImsIncidentTypeCauseVO imsIncidentTypeCauseVO) {

		ImsIncidentTypeCause imsIncidentLevel = null;

		if (imsIncidentTypeCauseVO != null) {

			imsIncidentLevel = new ImsIncidentTypeCause();
			if (imsIncidentTypeCauseVO.getId() != null) {

				Key key = new Key(imsIncidentTypeCauseVO.getId());

				if (key.getClazz() == ImsIncidentTypeCause.class) {

					imsIncidentLevel.setId(Long.valueOf(key.getId(1)));
				}
			}

			imsIncidentLevel.setAlias(imsIncidentTypeCauseVO.getAlias());
			imsIncidentLevel.setDescription((imsIncidentTypeCauseVO.getDescription()));

		}

		return imsIncidentLevel;
	}

	public static void assign(final ImsIncidentTypeCause imsIncidentTypeCause,
			ImsIncidentTypeCauseVO imsIncidentTypeCauseVO) {

		if (ObjectUtils.allNotNull(imsIncidentTypeCause, imsIncidentTypeCauseVO)) {

			BasicElementHelper.fillBasicProperties(imsIncidentTypeCauseVO, imsIncidentTypeCause);

		}
	}
	

}
