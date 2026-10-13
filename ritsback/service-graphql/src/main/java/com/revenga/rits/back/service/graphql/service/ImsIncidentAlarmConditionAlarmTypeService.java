package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ImsIncidentAlarmConditionAlarmType;
import com.revenga.rits.back.front.core.model.vo.ImsIncidentAlarmConditionAlarmTypeVO;
import com.revenga.rits.back.service.graphql.type.ImsIncidentAlarmConditionAlarmTypeGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ImsIncidentAlarmConditionAlarmTypeService implements QueryInterface<ImsIncidentAlarmConditionAlarmTypeVO> {

	@Override
	public List<ImsIncidentAlarmConditionAlarmTypeVO> getAll() {

		List<ImsIncidentAlarmConditionAlarmTypeVO> imsIncidentAlarmConditionAlarmTypes = new ArrayList<>();

		for (ImsIncidentAlarmConditionAlarmType imsIncidentAlarmConditionAlarmType : EntitiesManager.getInstance().getImsIncidentAlarmConditionAlarmTypeRepository()
				.getAll(false)) {

			imsIncidentAlarmConditionAlarmTypes.add(new ImsIncidentAlarmConditionAlarmTypeGraphql(imsIncidentAlarmConditionAlarmType));
		}

		return imsIncidentAlarmConditionAlarmTypes;
	}

	@Override
	public Optional<ImsIncidentAlarmConditionAlarmTypeVO> get(String imsIncidentAlarmConditionAlarmTypeId) {

		Optional<ImsIncidentAlarmConditionAlarmTypeVO> ImsIncidentAlarmConditionAlarmTypeVO = null;

		try {
			Key key = new Key(imsIncidentAlarmConditionAlarmTypeId);

			if (key.getClazz() == ImsIncidentAlarmConditionAlarmType.class) {

				ImsIncidentAlarmConditionAlarmType imsIncidentAlarmConditionAlarmType = EntitiesManager.getInstance().getImsIncidentAlarmConditionAlarmTypeRepository()
						.get(Long.valueOf(key.getId(1)), false);

				if (imsIncidentAlarmConditionAlarmType != null) {

					ImsIncidentAlarmConditionAlarmTypeVO = Optional.of(new ImsIncidentAlarmConditionAlarmTypeGraphql(imsIncidentAlarmConditionAlarmType));

				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: ImsIncidentAlarmConditionAlarmType");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return ImsIncidentAlarmConditionAlarmTypeVO;
	}

	@Override
	public List<ImsIncidentAlarmConditionAlarmTypeVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}

}
