package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentLevel;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentLevelVO;
import com.revenga.rits.back.service.graphql.type.ImsIncidentLevelGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ImsIncidentLevelService implements QueryInterface<ImsIncidentLevelVO> {
	
	@Override
	public List<ImsIncidentLevelVO> getAll() {

		List<ImsIncidentLevelVO> incidentLevels = new ArrayList<>();

		for (ImsIncidentLevel imsIncidentLevel : EntitiesManager.getInstance().getImsIncidentLevelRepository()
				.getAll(false)) {

			incidentLevels.add(new ImsIncidentLevelGraphql(imsIncidentLevel));
		}

		return incidentLevels;
	}

	@Override
	public Optional<ImsIncidentLevelVO> get(String imsIncidentLevelId) {

		Optional<ImsIncidentLevelVO> imsIncidentLevelVO = null;

		try {
			Key key = new Key(imsIncidentLevelId);

			if (key.getClazz() == ImsIncidentLevel.class) {

				ImsIncidentLevel imsIncidentLevel = EntitiesManager.getInstance().getImsIncidentLevelRepository()
						.get(Long.valueOf(key.getId(1)), false);

				if (imsIncidentLevel != null) {

					imsIncidentLevelVO = Optional.of(new ImsIncidentLevelGraphql(imsIncidentLevel));

				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: imsIncidentLevel");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return imsIncidentLevelVO;
	}

	@Override
	public List<ImsIncidentLevelVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}

}
