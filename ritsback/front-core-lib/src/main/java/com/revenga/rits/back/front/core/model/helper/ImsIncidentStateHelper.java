package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentState;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentStateVO;

public class ImsIncidentStateHelper {
	
	private ImsIncidentStateHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static ImsIncidentStateVO get(final ImsIncidentState imsIncidentState) {

		ImsIncidentStateVO imsIncidentStateVO = null;

		if (imsIncidentState != null) {

			imsIncidentStateVO = new ImsIncidentStateVO();

			assign(imsIncidentState, imsIncidentStateVO);
		}

		return imsIncidentStateVO;
	}

	public static ImsIncidentState get(final ImsIncidentStateVO imsIncidentStateVO) {

		ImsIncidentState imsIncidentState = null;

		if (imsIncidentStateVO != null) {

			imsIncidentState = new ImsIncidentState();
			if (imsIncidentStateVO.getId() != null) {

				Key key = new Key(imsIncidentStateVO.getId());

				if (key.getClazz() == ImsIncidentState.class) {

					imsIncidentState.setId(Long.valueOf(key.getId(1)));
				}
			}

			imsIncidentState.setAlias(imsIncidentStateVO.getAlias());
			imsIncidentState.setDescription((imsIncidentStateVO.getDescription()));
			imsIncidentState.setFinalState((imsIncidentStateVO.getFinalState()));

		}

		return imsIncidentState;
	}

	public static void assign(final ImsIncidentState imsIncidentState,
			ImsIncidentStateVO imsIncidentStateVO) {

		if (ObjectUtils.allNotNull(imsIncidentState, imsIncidentStateVO)) {

			BasicElementHelper.fillBasicProperties(imsIncidentStateVO, imsIncidentState);
			
			imsIncidentStateVO.setFinalState((imsIncidentState.getFinalState()));

		}
	}

}
