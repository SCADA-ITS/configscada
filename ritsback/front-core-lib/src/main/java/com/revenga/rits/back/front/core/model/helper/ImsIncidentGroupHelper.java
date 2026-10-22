package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentGroup;
import com.revenga.rits.back.data.core.model.ImsIncidentState;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentGroupVO;

/**
 * 
 * @author F. Olivares
 *
 */
public class ImsIncidentGroupHelper {
	
	/**
	 * 
	 */
	private ImsIncidentGroupHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	/**
	 * 
	 * @param imsIncidentGroup
	 * @return
	 */
	public static ImsIncidentGroupVO get(final ImsIncidentGroup imsIncidentGroup) {

		ImsIncidentGroupVO imsIncidentGroupVO = null;

		if (imsIncidentGroup != null) {

			imsIncidentGroupVO = new ImsIncidentGroupVO();

			assign(imsIncidentGroup, imsIncidentGroupVO);
		}

		return imsIncidentGroupVO;
	}

	/**
	 * 
	 * @param imsIncidentGroupVO
	 * @return
	 */
	public static ImsIncidentGroup get(final ImsIncidentGroupVO imsIncidentGroupVO) {

		ImsIncidentGroup imsIncidentGroup = null;

		if (imsIncidentGroupVO != null) {

			imsIncidentGroup = new ImsIncidentGroup();
			if (imsIncidentGroupVO.getId() != null) {

				Key key = new Key(imsIncidentGroupVO.getId());

				if (key.getClazz() == ImsIncidentState.class) {

					imsIncidentGroup.setId(Long.valueOf(key.getId(1)));
				}
			}

			imsIncidentGroup.setAlias(imsIncidentGroupVO.getAlias());
			imsIncidentGroup.setDescription((imsIncidentGroupVO.getDescription()));
		}

		return imsIncidentGroup;
	}

	public static void assign(final ImsIncidentGroup imsIncidentGroup, ImsIncidentGroupVO imsIncidentGroupVO) {

		if (ObjectUtils.allNotNull(imsIncidentGroup, imsIncidentGroupVO)) {

			BasicElementHelper.fillBasicProperties(imsIncidentGroupVO, imsIncidentGroup);
		}
	}
	

}
