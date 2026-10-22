package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentState;
import com.revenga.rits.back.data.core.model.ImsIncidentType;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeStateTransition;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeStateTransitionVO;

public class ImsIncidentTypeStateTransitionHelper {

	private ImsIncidentTypeStateTransitionHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static ImsIncidentTypeStateTransitionVO get(
			final ImsIncidentTypeStateTransition imsIncidentTypeStateTransition) {

		ImsIncidentTypeStateTransitionVO imsIncidentTypeStateTransitionVO = null;

		if (imsIncidentTypeStateTransition != null) {

			imsIncidentTypeStateTransitionVO = new ImsIncidentTypeStateTransitionVO();

			assign(imsIncidentTypeStateTransition, imsIncidentTypeStateTransitionVO);
		}

		return imsIncidentTypeStateTransitionVO;
	}

	public static ImsIncidentTypeStateTransition get(
			final ImsIncidentTypeStateTransitionVO imsIncidentTypeStateTransitionVO) {

		ImsIncidentTypeStateTransition imsIncidentTypeStateTransition = null;

		if (imsIncidentTypeStateTransitionVO != null) {

			imsIncidentTypeStateTransition = new ImsIncidentTypeStateTransition();
			if (imsIncidentTypeStateTransitionVO.getId() != null) {

				Key key = new Key(imsIncidentTypeStateTransitionVO.getId());

				if (key.getClazz() == ImsIncidentTypeStateTransition.class) {

					imsIncidentTypeStateTransition.setIncidentTypeId(Long.valueOf(key.getId(1)));
					imsIncidentTypeStateTransition.setIniIncidentStateId(Long.valueOf(key.getId(2)));
				}
			}

			imsIncidentTypeStateTransition.setAuto(imsIncidentTypeStateTransitionVO.getAuto());

			if (imsIncidentTypeStateTransitionVO.getIncidentTypeId() != null) {

				Key key = new Key(imsIncidentTypeStateTransitionVO.getIncidentTypeId());

				if (key.getClazz() == ImsIncidentType.class) {

					imsIncidentTypeStateTransition.setIncidentTypeId(Long.valueOf(key.getId(1)));
				}
			}

			if (imsIncidentTypeStateTransitionVO.getIniIncidentStateId() != null) {

				Key key = new Key(imsIncidentTypeStateTransitionVO.getIniIncidentStateId());

				if (key.getClazz() == ImsIncidentState.class) {

					imsIncidentTypeStateTransition.setIniIncidentStateId(Long.valueOf(key.getId(1)));
				}
			}

			if (imsIncidentTypeStateTransitionVO.getEndIncidentStateId() != null) {

				Key key = new Key(imsIncidentTypeStateTransitionVO.getEndIncidentStateId());

				if (key.getClazz() == ImsIncidentState.class) {

					imsIncidentTypeStateTransition.setEndIncidentStateId(Long.valueOf(key.getId(1)));
				}
			}

		}

		return imsIncidentTypeStateTransition;
	}

	public static void assign(final ImsIncidentTypeStateTransition imsIncidentTypeStateTransition,
			ImsIncidentTypeStateTransitionVO imsIncidentTypeStateTransitionVO) {

		if (ObjectUtils.allNotNull(imsIncidentTypeStateTransition, imsIncidentTypeStateTransitionVO)) {

			BasicElementHelper.fillBasicProperties(imsIncidentTypeStateTransitionVO, imsIncidentTypeStateTransition);
			
			imsIncidentTypeStateTransitionVO.setAuto(imsIncidentTypeStateTransition.getAuto());

			if (imsIncidentTypeStateTransition.getIncidentTypeId() != null) {

				ImsIncidentType imsIncidentType = new ImsIncidentType();
				imsIncidentType.setId(imsIncidentTypeStateTransition.getIncidentTypeId());
				imsIncidentTypeStateTransitionVO
						.setIncidentTypeId(BasicElementHelper.getIdFromElement(imsIncidentType));
			}

			if (imsIncidentTypeStateTransition.getIniIncidentStateId() != null) {

				ImsIncidentState imsIncidentState = new ImsIncidentState();
				imsIncidentState.setId(imsIncidentTypeStateTransition.getIniIncidentStateId());
				imsIncidentTypeStateTransitionVO
						.setIniIncidentStateId(BasicElementHelper.getIdFromElement(imsIncidentState));
			}

			if (imsIncidentTypeStateTransition.getEndIncidentStateId() != null) {

				ImsIncidentState imsIncidentState = new ImsIncidentState();
				imsIncidentState.setId(imsIncidentTypeStateTransition.getEndIncidentStateId());
				imsIncidentTypeStateTransitionVO
						.setEndIncidentStateId(BasicElementHelper.getIdFromElement(imsIncidentState));
			}

		}
	}

}
