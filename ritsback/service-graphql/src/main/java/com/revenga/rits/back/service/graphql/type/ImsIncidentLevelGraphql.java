package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.ImsIncidentLevel;
import com.revenga.rits.back.data.core.model.PmQueuePriority;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentLevelHelper;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentLevelVO;
import com.revenga.rits.back.front.core.model.vo.PmQueuePriorityVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

public class ImsIncidentLevelGraphql extends ImsIncidentLevelVO  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8038762106452278310L;
	
	private ImsIncidentLevel imsIncidentLevel;

	public ImsIncidentLevelGraphql(final ImsIncidentLevel imsIncidentLevel) {

		ImsIncidentLevelHelper.assign(imsIncidentLevel, this);
		
		this.imsIncidentLevel = imsIncidentLevel;
	}
	
	@Override
	public PmQueuePriorityVO getPmQueuePriority() {

		if (super.getPmQueuePriority() == null && imsIncidentLevel != null
				&& imsIncidentLevel.getPmQueuePriorityId() != null && imsIncidentLevel.getPmQueueId() != null) {

			PmQueuePriority pmQueuePriority = EntitiesManager.getInstance().getPmQueuePriorityRepository()
					.get(imsIncidentLevel.getPmQueueId(), imsIncidentLevel.getPmQueuePriorityId(), true);

			if (pmQueuePriority != null) {

				super.setPmQueuePriority(new PmQueuePriorityGraphql(pmQueuePriority, null));

			}
		}

		return super.getPmQueuePriority();
	}
}
