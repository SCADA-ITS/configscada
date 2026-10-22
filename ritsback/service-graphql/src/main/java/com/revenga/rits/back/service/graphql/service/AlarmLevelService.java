package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.AlarmLevel;
import com.revenga.rits.back.front.core.model.vo.AlarmLevelVO;
import com.revenga.rits.back.service.graphql.type.AlarmLevelGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AlarmLevelService implements QueryInterface<AlarmLevelVO> {

	@Override
	public List<AlarmLevelVO> getAll() {

		List<AlarmLevelVO> alarmsType = new ArrayList<>();

		for (AlarmLevel alarmLevel : EntitiesManager.getInstance().getAlarmLevelRepository().getAll(false)) {

			alarmsType.add(new AlarmLevelGraphql(alarmLevel));
		}

		return alarmsType;

	}

	@Override
	public Optional<AlarmLevelVO> get(String alarmLevelId) {

		Optional<AlarmLevelVO> alarmLevelVO = null;
		try {
			Key key = new Key(alarmLevelId);

			if (key.getClazz() == AlarmLevel.class) {

				AlarmLevel alarmLevel = EntitiesManager.getInstance().getAlarmLevelRepository()
						.get(Long.valueOf(key.getId(1)), false);

				if (alarmLevel != null) {

					alarmLevelVO = Optional.of(new AlarmLevelGraphql(alarmLevel));

				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: AlarmConfig");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return alarmLevelVO;
	}

	@Override
	public List<AlarmLevelVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}
}
