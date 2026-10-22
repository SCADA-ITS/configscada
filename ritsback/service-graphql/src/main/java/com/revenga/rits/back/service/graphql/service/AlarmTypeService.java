package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.AlarmType;
import com.revenga.rits.back.front.core.model.vo.AlarmTypeVO;
import com.revenga.rits.back.service.graphql.type.AlarmTypeGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AlarmTypeService implements QueryInterface<AlarmTypeVO> {

	@Override
	public List<AlarmTypeVO> getAll() {

		List<AlarmTypeVO> alarmsType = new ArrayList<>();

		for (AlarmType alarmType : EntitiesManager.getInstance().getAlarmTypeRepository().getAll(false)) {

			alarmsType.add(new AlarmTypeGraphql(alarmType));
		}

		return alarmsType;

	}

	@Override
	public Optional<AlarmTypeVO> get(String alarmTypeId) {

		Optional<AlarmTypeVO> alarmTypeVO = null;
		try {
			Key key = new Key(alarmTypeId);

			if (key.getClazz() == AlarmType.class) {

				AlarmType alarmType = EntitiesManager.getInstance().getAlarmTypeRepository()
						.get(Long.valueOf(key.getId(1)), false);

				if (alarmType != null) {

					alarmTypeVO = Optional.of(new AlarmTypeGraphql(alarmType));

				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: AlarmConfig");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return alarmTypeVO;
	}

	@Override
	public List<AlarmTypeVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}
}
