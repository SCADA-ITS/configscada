package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsTaskType;
import com.revenga.rits.back.front.core.model.vo.ImsTaskTypeVO;
import com.revenga.rits.back.service.graphql.type.ImsTaskTypeGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ImsTaskTypeService implements QueryInterface<ImsTaskTypeVO> {

	@Override
	public List<ImsTaskTypeVO> getAll() {

		List<ImsTaskTypeVO> imsTaskTypes = new ArrayList<>();

		for (ImsTaskType imsTaskType : EntitiesManager.getInstance().getImsTaskTypeRepository().getAll(false)) {

			imsTaskTypes.add(new ImsTaskTypeGraphql(imsTaskType));
		}

		return imsTaskTypes;
	}

	@Override
	public Optional<ImsTaskTypeVO> get(String imsTaskTypeId) {

		Optional<ImsTaskTypeVO> imsTaskTypeVO = null;

		try {
			Key key = new Key(imsTaskTypeId);

			if (key.getClazz() == ImsTaskType.class) {

				ImsTaskType imsTaskType = EntitiesManager.getInstance().getImsTaskTypeRepository()
						.get(Long.valueOf(key.getId(1)), false);

				if (imsTaskType != null) {

					imsTaskTypeVO = Optional.of(new ImsTaskTypeGraphql(imsTaskType));

				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: imsTaskType");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return imsTaskTypeVO;
	}

	@Override
	public List<ImsTaskTypeVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}

}
