package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.ImsIncidentState;
import com.revenga.rits.back.data.core.model.ImsIncidentType;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.ImsTaskType;
import com.revenga.rits.back.data.core.model.RoadImpact;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentTypeTaskHelper;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentStateVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeTaskVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeTaskValueVO;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeVO;
import com.revenga.rits.back.front.core.model.vo.ImsTaskTypeVO;
import com.revenga.rits.back.front.core.model.vo.RoadImpactVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class ImsIncidentTypeTaskGraphql extends ImsIncidentTypeTaskVO {
	/**
	* 
	*/
	private static final long serialVersionUID = 161521602807575725L;

	private ImsIncidentTypeTask imsIncidentTypeTask;

	public ImsIncidentTypeTaskGraphql(final ImsIncidentTypeTask imsIncidentTypeTask) {

		ImsIncidentTypeTaskHelper.assign(imsIncidentTypeTask, this);
		this.imsIncidentTypeTask = imsIncidentTypeTask;

	}

	@Override
	public ImsIncidentTypeVO getIncidentType() {

		if (super.getIncidentType() == null && imsIncidentTypeTask != null
				&& imsIncidentTypeTask.getIncidentTypeId() != null) {

			ImsIncidentType imsIncidentType = EntitiesManager.getInstance().getImsIncidentTypeRepository()
					.get(imsIncidentTypeTask.getIncidentTypeId(), false);

			if (imsIncidentType != null) {

				super.setIncidentType(new ImsIncidentTypeGraphql(imsIncidentType));
			}
		}

		return super.getIncidentType();
	}

	@Override
	public ImsIncidentStateVO getIncidentState() {

		if (super.getIncidentState() == null && imsIncidentTypeTask != null
				&& imsIncidentTypeTask.getIncidentStateId() != null) {

			ImsIncidentState imsIncidentState = EntitiesManager.getInstance().getImsIncidentStateRepository()
					.get(imsIncidentTypeTask.getIncidentStateId(), false);

			if (imsIncidentState != null) {

				super.setIncidentState(new ImsIncidentStateGraphql(imsIncidentState));
			}
		}

		return super.getIncidentState();
	}

	@Override
	public ImsTaskTypeVO getTaskType() {

		if (super.getTaskType() == null && imsIncidentTypeTask != null && imsIncidentTypeTask.getTaskTypeId() != null) {

			ImsTaskType imsTaskType = EntitiesManager.getInstance().getImsTaskTypeRepository()
					.get(imsIncidentTypeTask.getTaskTypeId(), false);

			if (imsTaskType != null) {

				super.setTaskType(new ImsTaskTypeGraphql(imsTaskType));
			}
		}

		return super.getTaskType();
	}
	
	@Override
	public RoadImpactVO getRoadImpact() {

		if (super.getRoadImpact() == null && imsIncidentTypeTask != null && imsIncidentTypeTask.getRoadImpactId() != null) {

			RoadImpact roadImpact = EntitiesManager.getInstance().getRoadImpactRepository()
					.get(imsIncidentTypeTask.getRoadImpactId(), false);

			if (roadImpact != null) {

				super.setRoadImpact(new RoadImpactGraphql(roadImpact));
			}
		}

		return super.getRoadImpact();
	}
	
	
	/**
	 * 
	 */
	public List<ImsIncidentTypeTaskValueVO> getIncidentTypeTaskValues() {
		
		if (super.getIncidentTypeTaskValues() == null && imsIncidentTypeTask != null && imsIncidentTypeTask.getId() != null) {
			
			List<ImsIncidentTypeTaskValue> imsIncidentTypeTaskValues = EntitiesManager.getInstance().getImsIncidentTypeTaskValueRepository()
					.getsByIncidentTypeTask(imsIncidentTypeTask.getId(), false);

			if (!CollectionUtils.isEmpty(imsIncidentTypeTaskValues)) {

				List<ImsIncidentTypeTaskValueVO> imsIncidentTypeTaskValuesVO = new ArrayList<>();

				for (ImsIncidentTypeTaskValue imsIncidentTypeTaskValue : imsIncidentTypeTaskValues) {

				
					imsIncidentTypeTaskValuesVO.add(new ImsIncidentTypeTaskValueGraphql(imsIncidentTypeTaskValue));
				}
				
				super.setIncidentTypeTaskValues(imsIncidentTypeTaskValuesVO);
			}
		}
		
		return super.getIncidentTypeTaskValues();		
	}	

}
