package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentState;
import com.revenga.rits.back.data.core.model.ImsIncidentType;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentTypeTaskHelper;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeTaskVO;
import com.revenga.rits.back.service.graphql.type.ImsIncidentTypeTaskGraphql;


@Service
public class ImsIncidentTypeTaskService
		implements QueryInterface<ImsIncidentTypeTaskVO>, MutationInterface<ImsIncidentTypeTaskVO> {
	
	public List<ImsIncidentTypeTaskVO> getImsIncidentTypeTasks(String incidentTypeId,
			String incidentStateId) {

		List<ImsIncidentTypeTaskVO> imsIncidentTypeTasksVO = null;

		Key keyIncidentTypeId = new Key(incidentTypeId);
		
		if (incidentStateId == null) {
			
			if (keyIncidentTypeId.getClazz() == ImsIncidentType.class) {

				imsIncidentTypeTasksVO = new ArrayList<>();

				for (ImsIncidentTypeTask imsIncidentTypeTask : EntitiesManager.getInstance()
						.getImsIncidentTypeTaskRepository()
						.getByIncidentType(Long.valueOf(keyIncidentTypeId.getId(1)), false)) {

					imsIncidentTypeTasksVO.add(new ImsIncidentTypeTaskGraphql(imsIncidentTypeTask));
				}
			}
		}
		else {
			
			Key keyIncidentStateId = new Key(incidentStateId);

			if (keyIncidentTypeId.getClazz() == ImsIncidentType.class
					&& keyIncidentStateId.getClazz() == ImsIncidentState.class) {

				imsIncidentTypeTasksVO = new ArrayList<>();

				for (ImsIncidentTypeTask imsIncidentTypeTask : EntitiesManager.getInstance()
						.getImsIncidentTypeTaskRepository().gets(Long.valueOf(keyIncidentTypeId.getId(1)),
								Long.valueOf(keyIncidentStateId.getId(1)), null, false)) {

					imsIncidentTypeTasksVO.add(new ImsIncidentTypeTaskGraphql(imsIncidentTypeTask));
				}
			}
		}

		return imsIncidentTypeTasksVO;
	}

	@Override
	public List<ImsIncidentTypeTaskVO> getAll() {

		List<ImsIncidentTypeTaskVO> imsIncidentTypeTaskVO = new ArrayList<>();

		for (ImsIncidentTypeTask imsIncidentTypeTask : EntitiesManager.getInstance().getImsIncidentTypeTaskRepository()
				.getAll(false)) {

			imsIncidentTypeTaskVO.add(new ImsIncidentTypeTaskGraphql(imsIncidentTypeTask));
		}

		return imsIncidentTypeTaskVO;
	}

	@Override
	public Optional<ImsIncidentTypeTaskVO> get(String idItem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ImsIncidentTypeTaskVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ImsIncidentTypeTaskVO> create(ImsIncidentTypeTaskVO imsIncidentTypeTaskVO) {

		Optional<ImsIncidentTypeTaskVO> resp = null;

		if (imsIncidentTypeTaskVO != null) {

			ImsIncidentTypeTask imsIncidentTypeTask = ImsIncidentTypeTaskHelper.get(imsIncidentTypeTaskVO);

			ImsIncidentTypeTask imsIncidentTypeTaskCreated = EntitiesManager.getInstance()
					.getImsIncidentTypeTaskRepository().create(imsIncidentTypeTask);

			if (imsIncidentTypeTaskCreated != null) {

				resp = Optional.of(new ImsIncidentTypeTaskGraphql(imsIncidentTypeTaskCreated));
			}
		}
		return resp;
		
	}

	@Override
	public Optional<ImsIncidentTypeTaskVO> clone(String item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ImsIncidentTypeTaskVO> update(ImsIncidentTypeTaskVO imsIncidentTypeTaskVO) {

		Optional<ImsIncidentTypeTaskVO> resp = null;

		if (imsIncidentTypeTaskVO != null) {

			ImsIncidentTypeTask imsIncidentTypeTask = ImsIncidentTypeTaskHelper.get(imsIncidentTypeTaskVO);

			ImsIncidentTypeTask imsIncidentTypeTaskUpdated = EntitiesManager.getInstance()
					.getImsIncidentTypeTaskRepository().update(imsIncidentTypeTask);

			if (imsIncidentTypeTaskUpdated != null) {

				resp = Optional.of(new ImsIncidentTypeTaskGraphql(imsIncidentTypeTaskUpdated));
			}
		}
		return resp;
	}

	@Override
	public Optional<ImsIncidentTypeTaskVO> delete(String imsIncidentTypeTaskId) {

		Optional<ImsIncidentTypeTaskVO> resp = null;

		if (imsIncidentTypeTaskId != null) {

			ImsIncidentTypeTaskVO imsIncidentTypeTaskVO = new ImsIncidentTypeTaskVO();
			imsIncidentTypeTaskVO.setId(imsIncidentTypeTaskId);

			ImsIncidentTypeTask imsIncidentTypeTask = ImsIncidentTypeTaskHelper.get(imsIncidentTypeTaskVO);

			ImsIncidentTypeTask imsIncidentTypeTaskDeleted = EntitiesManager.getInstance()
					.getImsIncidentTypeTaskRepository().delete(imsIncidentTypeTask);

			if (imsIncidentTypeTaskDeleted != null) {

				resp = Optional.of(new ImsIncidentTypeTaskGraphql(imsIncidentTypeTaskDeleted));
			}
		}

		return resp;
	}

}
