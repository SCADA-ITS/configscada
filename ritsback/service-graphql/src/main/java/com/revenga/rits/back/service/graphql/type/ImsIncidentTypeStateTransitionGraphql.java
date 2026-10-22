package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.ImsIncidentState;
import com.revenga.rits.back.data.core.model.ImsIncidentType;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeStateTransition;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentTypeStateTransitionHelper;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentStateVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeStateTransitionVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class ImsIncidentTypeStateTransitionGraphql extends ImsIncidentTypeStateTransitionVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5871008905702516922L;

	private ImsIncidentTypeStateTransition imsIncidentTypeStateTransition;

	public ImsIncidentTypeStateTransitionGraphql(final ImsIncidentTypeStateTransition imsIncidentTypeStateTransition) {

		ImsIncidentTypeStateTransitionHelper.assign(imsIncidentTypeStateTransition, this);
		this.imsIncidentTypeStateTransition = imsIncidentTypeStateTransition;

	}

	@Override
	public ImsIncidentTypeVO getIncidentType() {

		if (super.getIncidentType() == null && imsIncidentTypeStateTransition != null
				&& imsIncidentTypeStateTransition.getIncidentTypeId() != null) {

			ImsIncidentType imsIncidentType = EntitiesManager.getInstance().getImsIncidentTypeRepository()
					.get(imsIncidentTypeStateTransition.getIncidentTypeId(), false);

			if (imsIncidentType != null) {

				super.setIncidentType(new ImsIncidentTypeGraphql(imsIncidentType));
			}
		}

		return super.getIncidentType();
	}

	@Override
	public ImsIncidentStateVO getIniIncidentState() {

		if (super.getIniIncidentState() == null && imsIncidentTypeStateTransition != null
				&& imsIncidentTypeStateTransition.getIniIncidentStateId() != null) {

			ImsIncidentState imsIncidentState = EntitiesManager.getInstance().getImsIncidentStateRepository()
					.get(imsIncidentTypeStateTransition.getIniIncidentStateId(), false);

			if (imsIncidentState != null) {

				super.setIniIncidentState(new ImsIncidentStateGraphql(imsIncidentState));
			}
		}

		return super.getIniIncidentState();
	}

	@Override
	public ImsIncidentStateVO getEndIncidentState() {

		if (super.getEndIncidentState() == null && imsIncidentTypeStateTransition != null
				&& imsIncidentTypeStateTransition.getEndIncidentStateId() != null) {

			ImsIncidentState imsIncidentState = EntitiesManager.getInstance().getImsIncidentStateRepository()
					.get(imsIncidentTypeStateTransition.getEndIncidentStateId(), false);

			if (imsIncidentState != null) {

				super.setEndIncidentState(new ImsIncidentStateGraphql(imsIncidentState));
			}
		}

		return super.getEndIncidentState();
	}

}
