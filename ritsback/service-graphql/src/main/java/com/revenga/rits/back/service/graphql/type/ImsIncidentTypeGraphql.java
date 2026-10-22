package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.ImsIncidentGroup;
import com.revenga.rits.back.data.core.model.ImsIncidentLevel;
import com.revenga.rits.back.data.core.model.ImsIncidentState;
import com.revenga.rits.back.data.core.model.ImsIncidentType;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeCause;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeStateTransition;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeStretch;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentTypeHelper;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentGroupVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentLevelVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentStateVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeCauseVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeStateTransitionVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeStretchVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeVO;
import com.revenga.rits.back.front.core.model.vo.UserVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class ImsIncidentTypeGraphql extends ImsIncidentTypeVO {
	
	/**
	* 
	*/
	private static final long serialVersionUID = -2805042331047111123L;

	/**
	 * 
	 */
	private ImsIncidentType imsIncidentType;

	/**
	 * 
	 * @param imsIncidentType
	 */
	public ImsIncidentTypeGraphql(final ImsIncidentType imsIncidentType) {

		ImsIncidentTypeHelper.assign(imsIncidentType, this);
		this.imsIncidentType = imsIncidentType;

	}
	
	public List<ImsIncidentTypeCauseVO> getIncidentTypeCauses() {
		
		if (super.getIncidentTypeCauses() == null && imsIncidentType != null && imsIncidentType.getId() != null) {

			List<ImsIncidentTypeCause> imsIncidentTypeCauses = EntitiesManager.getInstance()
					.getImsIncidentTypeCauseRepository().getsByIncidentType(imsIncidentType.getId(), false);

			if (!CollectionUtils.isEmpty(imsIncidentTypeCauses)) {

				List<ImsIncidentTypeCauseVO> imsIncidentTypeCausesVO = new ArrayList<>();

				for (ImsIncidentTypeCause imsIncidentTypeCause : imsIncidentTypeCauses) {

					imsIncidentTypeCausesVO.add(new ImsIncidentTypeCauseGraphql(imsIncidentTypeCause));
				}
				
				super.setIncidentTypeCauses(imsIncidentTypeCausesVO);
			}
		}
		
		return super.getIncidentTypeCauses();		
	}

	/**
	 * 
	 */
	@Override
	public ImsIncidentStateVO getIniIncidentState() {

		if (super.getIniIncidentState() == null && imsIncidentType != null
				&& imsIncidentType.getIniIncidentStateId() != null) {

			ImsIncidentState imsIncidentState = EntitiesManager.getInstance().getImsIncidentStateRepository()
					.get(imsIncidentType.getIniIncidentStateId(), false);

			if (imsIncidentState != null) {

				super.setIniIncidentState(new ImsIncidentStateGraphql(imsIncidentState));
			}
		}

		return super.getIniIncidentState();
	}
	
	/**
	 * 
	 */
	@Override
	public ImsIncidentGroupVO getIncidentGroup() {

		if (super.getIncidentGroup() == null && imsIncidentType != null	&& imsIncidentType.getIncidentGroupId() != null) {

			ImsIncidentGroup imsIncidentGroup = EntitiesManager.getInstance().getImsIncidentGroupRepository()
					.get(imsIncidentType.getIncidentGroupId(), false);

			if (imsIncidentGroup != null) {

				super.setIncidentGroup(new ImsIncidentGroupGraphql(imsIncidentGroup));
			}
		}

		return super.getIncidentGroup();
	}
	

	/**
	 * 
	 */
	@Override
	public ImsIncidentLevelVO getIncidentLevel() {

		if (super.getIncidentLevel() == null && imsIncidentType != null
				&& imsIncidentType.getIncidentLevelId() != null) {

			ImsIncidentLevel imsIncidentLevel = EntitiesManager.getInstance().getImsIncidentLevelRepository()
					.get(imsIncidentType.getIncidentLevelId(), false);

			if (imsIncidentLevel != null) {

				super.setIncidentLevel(new ImsIncidentLevelGraphql(imsIncidentLevel));
			}
		}

		return super.getIncidentLevel();
	}

	/**
	 * 
	 */
	@Override
	public UserVO getDefaultUser() {

		if (super.getDefaultUser() == null && imsIncidentType != null && imsIncidentType.getDefaultUserId() != null) {

			User user = EntitiesManager.getInstance().getUserRepository().get(imsIncidentType.getDefaultUserId(),
					false);

			if (user != null) {

				super.setDefaultUser(new UserGraphql(user));
			}
		}

		return super.getDefaultUser();
	}
	
	@Override
	public List<ImsIncidentTypeStretchVO> getIncidentTypeStretchs() {
		
		if (super.getIncidentTypeStretchs() == null && imsIncidentType != null && imsIncidentType.getId() != null) {

			List<ImsIncidentTypeStretch> imsIncidentTypeStretchs = EntitiesManager.getInstance()
					.getImsIncidentTypeStretchRepository().getsByIncidentType(imsIncidentType.getId(), false);

			if (!CollectionUtils.isEmpty(imsIncidentTypeStretchs)) {

				List<ImsIncidentTypeStretchVO> imsIncidentTypeStretchsVO = new ArrayList<>();

				for (ImsIncidentTypeStretch imsIncidentTypeStretch : imsIncidentTypeStretchs) {

					imsIncidentTypeStretchsVO.add(new ImsIncidentTypeStretchGraphql(imsIncidentTypeStretch));
				}
				
				super.setIncidentTypeStretchs(imsIncidentTypeStretchsVO);
			}
		}
		
		return super.getIncidentTypeStretchs();		
	}
	
	@Override
	public List<ImsIncidentTypeStateTransitionVO> getIncidentTypeStateTransitions() {
		
		if (super.getIncidentTypeStateTransitions() == null && imsIncidentType != null && imsIncidentType.getId() != null) {

			List<ImsIncidentTypeStateTransition> imsIncidentTypeStateTransitions = EntitiesManager.getInstance()
					.getImsIncidentTypeStateTransitionRepository().getsByIncidentType(imsIncidentType.getId(), false);

			if (!CollectionUtils.isEmpty(imsIncidentTypeStateTransitions)) {

				List<ImsIncidentTypeStateTransitionVO> imsIncidentTypeStateTransitionsVO = new ArrayList<>();

				for (ImsIncidentTypeStateTransition imsIncidentTypeStateTransition : imsIncidentTypeStateTransitions) {

					imsIncidentTypeStateTransitionsVO.add(new ImsIncidentTypeStateTransitionGraphql(imsIncidentTypeStateTransition));
				}
				
				super.setIncidentTypeStateTransitions(imsIncidentTypeStateTransitionsVO);
			}
		}
		
		return super.getIncidentTypeStateTransitions();		
	}
}
