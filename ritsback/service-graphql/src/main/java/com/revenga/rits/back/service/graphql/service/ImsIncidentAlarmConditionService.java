package com.revenga.rits.back.service.graphql.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revenga.rits.back.data.core.model.ImsIncidentAlarmCondition;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentAlarmConditionHelper;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentAlarmConditionVO;
import com.revenga.rits.back.service.graphql.type.ImsIncidentAlarmConditionGraphql;


@Service
public class ImsIncidentAlarmConditionService
		implements QueryInterface<ImsIncidentAlarmConditionVO>, MutationInterface<ImsIncidentAlarmConditionVO> {
	
	@Override
	public List<ImsIncidentAlarmConditionVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ImsIncidentAlarmConditionVO> get(String idItem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ImsIncidentAlarmConditionVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Optional<ImsIncidentAlarmConditionVO> create(ImsIncidentAlarmConditionVO imsIncidentAlarmConditionVO) {

		Optional<ImsIncidentAlarmConditionVO> resp = null;

		if (imsIncidentAlarmConditionVO != null) {

			ImsIncidentAlarmCondition imsIncidentAlarmCondition = ImsIncidentAlarmConditionHelper.get(imsIncidentAlarmConditionVO);

			ImsIncidentAlarmCondition imsIncidentAlarmConditionCreated = EntitiesManager.getInstance().getImsIncidentAlarmConditionRepository().create(imsIncidentAlarmCondition);

			if (imsIncidentAlarmConditionCreated != null) {

				resp = Optional.of(new ImsIncidentAlarmConditionGraphql(imsIncidentAlarmConditionCreated));
			}
		}
		return resp;
	}

	@Override
	public Optional<ImsIncidentAlarmConditionVO> clone(String item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ImsIncidentAlarmConditionVO> update(ImsIncidentAlarmConditionVO imsIncidentAlarmConditionVO) {

		Optional<ImsIncidentAlarmConditionVO> resp = null;

		if (imsIncidentAlarmConditionVO != null) {

			ImsIncidentAlarmCondition imsIncidentAlarmCondition = ImsIncidentAlarmConditionHelper.get(imsIncidentAlarmConditionVO);

			ImsIncidentAlarmCondition imsIncidentAlarmConditionUpdated = EntitiesManager.getInstance().getImsIncidentAlarmConditionRepository().update(imsIncidentAlarmCondition);

			if (imsIncidentAlarmConditionUpdated != null) {

				resp = Optional.of(new ImsIncidentAlarmConditionGraphql(imsIncidentAlarmConditionUpdated));
			}
		}
		return resp;
	}

	@Override
	public Optional<ImsIncidentAlarmConditionVO> delete(String imsIncidentAlarmConditionId) {

		Optional<ImsIncidentAlarmConditionVO> resp = null;

		if (imsIncidentAlarmConditionId != null) {

			ImsIncidentAlarmConditionVO imsIncidentAlarmConditionVO = new ImsIncidentAlarmConditionVO();
			imsIncidentAlarmConditionVO.setId(imsIncidentAlarmConditionId);

			ImsIncidentAlarmCondition imsIncidentAlarmCondition = ImsIncidentAlarmConditionHelper.get(imsIncidentAlarmConditionVO);

			ImsIncidentAlarmCondition imsIncidentAlarmConditionDeleted = EntitiesManager.getInstance().getImsIncidentAlarmConditionRepository().delete(imsIncidentAlarmCondition);

			if (imsIncidentAlarmConditionDeleted != null) {

				resp = Optional.of(new ImsIncidentAlarmConditionGraphql(imsIncidentAlarmConditionDeleted));
			}
		}

		return resp;
	}

}
