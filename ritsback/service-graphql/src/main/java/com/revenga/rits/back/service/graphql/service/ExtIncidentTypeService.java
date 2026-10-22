package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsExtIncidentType;
import com.revenga.rits.back.front.core.model.vo.ImsExtIncidentTypeVO;
import com.revenga.rits.back.service.graphql.type.ImsExtIncidentTypeGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ExtIncidentTypeService implements QueryInterface<ImsExtIncidentTypeVO> {

	@Override
	public List<ImsExtIncidentTypeVO> getAll() {

		List<ImsExtIncidentTypeVO> imsExtIncidentTypes = new ArrayList<>();

		for (ImsExtIncidentType imsExtIncidentType : EntitiesManager.getInstance().getImsExtIncidentTypeRepository()
				.getAll(false)) {

			imsExtIncidentTypes.add(new ImsExtIncidentTypeGraphql(imsExtIncidentType));
		}

		return imsExtIncidentTypes;
	}

	@Override
	public Optional<ImsExtIncidentTypeVO> get(String extIncidentTypeId) {

		Optional<ImsExtIncidentTypeVO> imsExtIncidentTypeVO = null;
		try {
			Key key = new Key(extIncidentTypeId);

			if (key.getClazz() == ImsExtIncidentType.class) {

				ImsExtIncidentType imsExtIncidentType = EntitiesManager.getInstance().getImsExtIncidentTypeRepository()
						.get(Long.valueOf(key.getId(1)), false);

				if (imsExtIncidentType != null) {

					imsExtIncidentTypeVO = Optional.of(new ImsExtIncidentTypeGraphql(imsExtIncidentType));

				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: ImsExtIncidentType");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return imsExtIncidentTypeVO;
	}

	@Override
	public List<ImsExtIncidentTypeVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}

}
