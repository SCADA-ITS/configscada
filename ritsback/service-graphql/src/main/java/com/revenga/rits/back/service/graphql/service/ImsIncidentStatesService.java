package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentState;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentStateVO;
import com.revenga.rits.back.service.graphql.type.ImsIncidentStateGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ImsIncidentStatesService implements QueryInterface<ImsIncidentStateVO> {
	
	@Override
	public List<ImsIncidentStateVO> getAll() {

		List<ImsIncidentStateVO> incidentStates = new ArrayList<>();

		for (ImsIncidentState incidentState : EntitiesManager.getInstance().getImsIncidentStateRepository().getAll(false)) {

			incidentStates.add(new ImsIncidentStateGraphql(incidentState));
		}

		return incidentStates;
	}

	@Override
	public Optional<ImsIncidentStateVO> get(String incidentStateId) {

		Optional<ImsIncidentStateVO> imsIncidentStateVO = null;
		
		try {
			Key key = new Key(incidentStateId);

			if (key.getClazz() == ImsIncidentState.class) {

				ImsIncidentState imsIncidentState = EntitiesManager.getInstance().getImsIncidentStateRepository().get(Long.valueOf(key.getId(1)), false);

				if (imsIncidentState != null) {

					imsIncidentStateVO = Optional.of(new ImsIncidentStateGraphql(imsIncidentState));

				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: ImsIncidentStateVO");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return imsIncidentStateVO;
	}

	@Override
	public List<ImsIncidentStateVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}

}
