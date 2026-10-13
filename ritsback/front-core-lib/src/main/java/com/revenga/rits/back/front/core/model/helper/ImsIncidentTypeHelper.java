package com.revenga.rits.back.front.core.model.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentGroup;
import com.revenga.rits.back.data.core.model.ImsIncidentLevel;
import com.revenga.rits.back.data.core.model.ImsIncidentState;
import com.revenga.rits.back.data.core.model.ImsIncidentType;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeCause;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeCauseVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeVO;

public class ImsIncidentTypeHelper {

	private ImsIncidentTypeHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static ImsIncidentTypeVO get(final ImsIncidentType imsIncidentType) {

		ImsIncidentTypeVO imsIncidentTypeVO = null;

		if (imsIncidentType != null) {

			imsIncidentTypeVO = new ImsIncidentTypeVO();

			assign(imsIncidentType, imsIncidentTypeVO);
		}

		return imsIncidentTypeVO;
	}

	public static ImsIncidentType get(final ImsIncidentTypeVO imsIncidentTypeVO) {

		ImsIncidentType imsIncidentType = null;

		if (imsIncidentTypeVO != null) {

			imsIncidentType = new ImsIncidentType();
			if (imsIncidentTypeVO.getId() != null) {

				Key key = new Key(imsIncidentTypeVO.getId());

				if (key.getClazz() == ImsIncidentType.class) {

					imsIncidentType.setId(Long.valueOf(key.getId(1)));
				}
			}

			imsIncidentType.setAlias(imsIncidentTypeVO.getAlias());
			imsIncidentType.setDescription((imsIncidentTypeVO.getDescription()));
			imsIncidentType.setAutoConfirmIncident(imsIncidentTypeVO.getAutoConfirmIncident());
			imsIncidentType.setValidForMin(imsIncidentTypeVO.getValidForMin());
			imsIncidentType.setCancelIncident(imsIncidentTypeVO.getCancelIncident());

			
			if (imsIncidentTypeVO.getIncidentGroupId() != null) {

				Key key = new Key(imsIncidentTypeVO.getIncidentGroupId());

				if (key.getClazz() == ImsIncidentGroup.class) {

					imsIncidentType.setIncidentGroupId(Long.valueOf(key.getId(1)));
				}
			}

			if (imsIncidentTypeVO.getDefaultUserId() != null) {

				Key key = new Key(imsIncidentTypeVO.getDefaultUserId());

				if (key.getClazz() == User.class) {

					imsIncidentType.setDefaultUserId(Long.valueOf(key.getId(1)));
				}
			}

			if (imsIncidentTypeVO.getIncidentLevelId() != null) {

				Key key = new Key(imsIncidentTypeVO.getIncidentLevelId());

				if (key.getClazz() == ImsIncidentLevel.class) {

					imsIncidentType.setIncidentLevelId(Long.valueOf(key.getId(1)));
				}
			}

			if (imsIncidentTypeVO.getIniIncidentStateId() != null) {

				Key key = new Key(imsIncidentTypeVO.getIniIncidentStateId());

				if (key.getClazz() == ImsIncidentState.class) {

					imsIncidentType.setIniIncidentStateId(Long.valueOf(key.getId(1)));
				}
			}
			
			if (imsIncidentTypeVO.getIncidentTypeCauses() != null){
				
				List<ImsIncidentTypeCause> imsIncidentTypeCauses = new ArrayList<>();
				
				for (ImsIncidentTypeCauseVO indcidentTypeCause : imsIncidentTypeVO.getIncidentTypeCauses()) {
					
					if (indcidentTypeCause != null) {
						
						imsIncidentTypeCauses.add(ImsIncidentTypeCauseHelper.get(indcidentTypeCause));
					}
				}
				
				imsIncidentType.setIncidentTypeCauses(imsIncidentTypeCauses);
			}

		}

		return imsIncidentType;
	}

	public static void assign(final ImsIncidentType imsIncidentType, ImsIncidentTypeVO imsIncidentTypeVO) {

		if (ObjectUtils.allNotNull(imsIncidentType, imsIncidentTypeVO)) {

			BasicElementHelper.fillBasicProperties(imsIncidentTypeVO, imsIncidentType);

			if (imsIncidentType.getDefaultUserId() != null) {

				User user = new User();
				user.setId(imsIncidentType.getDefaultUserId());
				imsIncidentTypeVO.setDefaultUserId(BasicElementHelper.getIdFromElement(user));
			}

			if (imsIncidentType.getIncidentLevelId() != null) {

				ImsIncidentLevel imsIncidentLevel = new ImsIncidentLevel();
				imsIncidentLevel.setId(imsIncidentType.getIncidentLevelId());
				imsIncidentTypeVO.setIncidentLevelId(BasicElementHelper.getIdFromElement(imsIncidentLevel));
			}

			if (imsIncidentType.getIniIncidentStateId() != null) {

				ImsIncidentState imsIncidentState = new ImsIncidentState();
				imsIncidentState.setId(imsIncidentType.getIniIncidentStateId());
				imsIncidentTypeVO.setIncidentLevelId(BasicElementHelper.getIdFromElement(imsIncidentState));
			}
			
			if (imsIncidentType.getIncidentGroupId() != null) {

				ImsIncidentGroup imsIncidentGroup = new ImsIncidentGroup();
				imsIncidentGroup.setId(imsIncidentType.getIncidentGroupId());
				imsIncidentTypeVO.setIncidentGroupId(BasicElementHelper.getIdFromElement(imsIncidentGroup));
			}
			
			imsIncidentTypeVO.setAutoConfirmIncident(imsIncidentType.getAutoConfirmIncident());	
			imsIncidentTypeVO.setValidForMin(imsIncidentType.getValidForMin());
			imsIncidentTypeVO.setCancelIncident(imsIncidentType.getCancelIncident());
		}
	}
}
