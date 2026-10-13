package com.revenga.rits.back.service.graphql.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revenga.rits.back.data.core.model.ImsIncidentTypeStateTransition;
import com.revenga.rits.back.front.core.model.helper.ImsIncidentTypeStateTransitionHelper;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentTypeStateTransitionVO;
import com.revenga.rits.back.service.graphql.type.ImsIncidentTypeStateTransitionGraphql;


@Service
public class ImsIncidentTypeStateTransitionService implements QueryInterface<ImsIncidentTypeStateTransitionVO>,
		MutationInterface<ImsIncidentTypeStateTransitionVO> {

	@Override
	public List<ImsIncidentTypeStateTransitionVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ImsIncidentTypeStateTransitionVO> get(String idItem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ImsIncidentTypeStateTransitionVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ImsIncidentTypeStateTransitionVO> create(ImsIncidentTypeStateTransitionVO imsIncidentTypeStateTransitionVO) {

		Optional<ImsIncidentTypeStateTransitionVO> resp = null;

		if (imsIncidentTypeStateTransitionVO != null) {

			ImsIncidentTypeStateTransition imsIncidentTypeStateTransition = ImsIncidentTypeStateTransitionHelper.get(imsIncidentTypeStateTransitionVO);

			ImsIncidentTypeStateTransition imsIncidentTypeStateTransitionCreated = EntitiesManager.getInstance().getImsIncidentTypeStateTransitionRepository().create(imsIncidentTypeStateTransition);

			if (imsIncidentTypeStateTransitionCreated != null) {

				resp = Optional.of(new ImsIncidentTypeStateTransitionGraphql(imsIncidentTypeStateTransitionCreated));
			}
		}
		return resp;
	}

	@Override
	public Optional<ImsIncidentTypeStateTransitionVO> clone(String item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ImsIncidentTypeStateTransitionVO> update(ImsIncidentTypeStateTransitionVO imsIncidentTypeStateTransitionVO) {

		Optional<ImsIncidentTypeStateTransitionVO> resp = null;

		if (imsIncidentTypeStateTransitionVO != null) {

			ImsIncidentTypeStateTransition imsIncidentTypeStateTransition = ImsIncidentTypeStateTransitionHelper.get(imsIncidentTypeStateTransitionVO);

			ImsIncidentTypeStateTransition imsIncidentTypeStateTransitionUpdated = EntitiesManager.getInstance().getImsIncidentTypeStateTransitionRepository().update(imsIncidentTypeStateTransition);

			if (imsIncidentTypeStateTransitionUpdated != null) {

				resp = Optional.of(new ImsIncidentTypeStateTransitionGraphql(imsIncidentTypeStateTransitionUpdated));
			}
		}
		return resp;
	}

	@Override
	public Optional<ImsIncidentTypeStateTransitionVO> delete(String imsIncidentTypeStateTransitionId) {

		Optional<ImsIncidentTypeStateTransitionVO> resp = null;

		if (imsIncidentTypeStateTransitionId != null) {

			ImsIncidentTypeStateTransitionVO imsIncidentTypeStateTransitionVO = new ImsIncidentTypeStateTransitionVO();
			imsIncidentTypeStateTransitionVO.setId(imsIncidentTypeStateTransitionId);

			ImsIncidentTypeStateTransition imsIncidentTypeStateTransition = ImsIncidentTypeStateTransitionHelper.get(imsIncidentTypeStateTransitionVO);

			ImsIncidentTypeStateTransition imsIncidentTypeStateTransitionDeleted = EntitiesManager.getInstance().getImsIncidentTypeStateTransitionRepository().delete(imsIncidentTypeStateTransition);

			if (imsIncidentTypeStateTransitionDeleted != null) {

				resp = Optional.of(new ImsIncidentTypeStateTransitionGraphql(imsIncidentTypeStateTransitionDeleted));
			}
		}

		return resp;
	}

}
