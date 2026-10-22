package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentTaskPlan;
import com.revenga.rits.back.data.core.model.ImsIncidentType;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentTaskPlanHelper;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTaskPlanVO;
import com.revenga.rits.back.service.graphql.type.ImsIncidentTaskPlanGraphql;

@Service
public class ImsIncidentTaskPlanService
		implements QueryInterface<ImsIncidentTaskPlanVO>, MutationInterface<ImsIncidentTaskPlanVO> {

	public List<ImsIncidentTaskPlanVO> getImsTaskPlans(String stretchId, String incidentTypeId,
			String incidentTypeTaskId) {

		List<ImsIncidentTaskPlanVO> imsIncidentTaskPlansVO = null;

		Key keyStretch = new Key(stretchId);
		Key keyIncidentTypeId = new Key(incidentTypeId);
		Key keyIncidentTypeTask = new Key(incidentTypeTaskId);

		if (keyStretch.getClazz() == Stretch.class && keyIncidentTypeId.getClazz() == ImsIncidentType.class
				&& keyIncidentTypeTask.getClazz() == ImsIncidentTypeTask.class) {

			imsIncidentTaskPlansVO = new ArrayList<>();

			for (ImsIncidentTaskPlan imsIncidentTaskPlan : EntitiesManager.getInstance()
					.getImsIncidentTaskPlanRepository().gets(Long.valueOf(keyStretch.getId(1)),
							Long.valueOf(keyIncidentTypeId.getId(1)), Long.valueOf(keyIncidentTypeTask.getId(1)),
							false)) {

				imsIncidentTaskPlansVO.add(new ImsIncidentTaskPlanGraphql(imsIncidentTaskPlan));
			}
		}

		return imsIncidentTaskPlansVO;
	}

	@Override
	public Optional<ImsIncidentTaskPlanVO> create(ImsIncidentTaskPlanVO imsIncidentTaskPlanVO) {

		Optional<ImsIncidentTaskPlanVO> resp = null;

		if (imsIncidentTaskPlanVO != null) {

			ImsIncidentTaskPlan imsIncidentTaskPlan = ImsIncidentTaskPlanHelper.get(imsIncidentTaskPlanVO);

			ImsIncidentTaskPlan imsIncidentTaskPlanCreated = EntitiesManager.getInstance()
					.getImsIncidentTaskPlanRepository().create(imsIncidentTaskPlan);

			if (imsIncidentTaskPlanCreated != null) {

				resp = Optional.of(new ImsIncidentTaskPlanGraphql(imsIncidentTaskPlanCreated));
			}
		}
		return resp;
	}

	@Override
	public Optional<ImsIncidentTaskPlanVO> clone(String item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ImsIncidentTaskPlanVO> update(ImsIncidentTaskPlanVO imsIncidentTaskPlanVO) {

		Optional<ImsIncidentTaskPlanVO> resp = null;

		if (imsIncidentTaskPlanVO != null) {

			ImsIncidentTaskPlan imsIncidentTaskPlan = ImsIncidentTaskPlanHelper.get(imsIncidentTaskPlanVO);

			ImsIncidentTaskPlan imsIncidentTaskPlanUpdated = EntitiesManager.getInstance()
					.getImsIncidentTaskPlanRepository().update(imsIncidentTaskPlan);

			if (imsIncidentTaskPlanUpdated != null) {

				resp = Optional.of(new ImsIncidentTaskPlanGraphql(imsIncidentTaskPlanUpdated));
			}
		}
		return resp;
	}

	@Override
	public Optional<ImsIncidentTaskPlanVO> delete(String imsIncidentTaskPlanId) {

		Optional<ImsIncidentTaskPlanVO> resp = null;

		if (imsIncidentTaskPlanId != null) {

			ImsIncidentTaskPlanVO imsIncidentTaskPlanVO = new ImsIncidentTaskPlanVO();
			imsIncidentTaskPlanVO.setId(imsIncidentTaskPlanId);

			ImsIncidentTaskPlan imsIncidentTaskPlan = ImsIncidentTaskPlanHelper.get(imsIncidentTaskPlanVO);

			ImsIncidentTaskPlan imsIncidentTaskPlanDeleted = EntitiesManager.getInstance()
					.getImsIncidentTaskPlanRepository().delete(imsIncidentTaskPlan);

			if (imsIncidentTaskPlanDeleted != null) {

				resp = Optional.of(new ImsIncidentTaskPlanGraphql(imsIncidentTaskPlanDeleted));
			}
		}

		return resp;
	}

	@Override
	public List<ImsIncidentTaskPlanVO> getAll() {

		List<ImsIncidentTaskPlanVO> imsIncidentTaskPlansVO = new ArrayList<>();

		for (ImsIncidentTaskPlan imsIncidentTaskPlan : EntitiesManager.getInstance().getImsIncidentTaskPlanRepository()
				.getAll(false)) {

			imsIncidentTaskPlansVO.add(new ImsIncidentTaskPlanGraphql(imsIncidentTaskPlan));
		}

		return imsIncidentTaskPlansVO;
	}

	@Override
	public Optional<ImsIncidentTaskPlanVO> get(String idItem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ImsIncidentTaskPlanVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}

}
