package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.AlarmLevel;
import com.revenga.rits.back.data.core.model.AlarmType;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.front.core.model.helper.AlarmConfigHelper;
import com.revenga.rits.back.front.core.model.vo.AlarmConfigVO;
import com.revenga.rits.back.service.graphql.type.AlarmConfigGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AlarmConfigService implements QueryInterface<AlarmConfigVO>, MutationInterface<AlarmConfigVO> {

	@Override
	public List<AlarmConfigVO> getAll() {

		List<AlarmConfigVO> alarmsConfig = new ArrayList<>();

		for (AlarmConfig alarmConfig : EntitiesManager.getInstance().getAlarmConfigRepository().getAll(false)) {

			alarmsConfig.add(new AlarmConfigGraphql(alarmConfig));
		}

		return alarmsConfig;

	}

	@Override
	public Optional<AlarmConfigVO> get(String alarmConfigId) {

		Optional<AlarmConfigVO> alarmConfigVO = null;
		try {
			Key key = new Key(alarmConfigId);

			if (key.getClazz() == AlarmConfig.class) {

				AlarmConfig alarmConfig = EntitiesManager.getInstance().getAlarmConfigRepository()
						.get(Long.valueOf(key.getId(1)), false);

				if (alarmConfig != null) {

					alarmConfigVO = Optional.of(new AlarmConfigGraphql(alarmConfig));

				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: AlarmConfig");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return alarmConfigVO;
	}
	
	public List<AlarmConfigVO> getAlarmConfigsByAlarmType(String alarmTypeId) {

		List<AlarmConfigVO> alarmConfigsVO = new ArrayList<>();

		try {
			Key key = new Key(alarmTypeId);

			if (key.getClazz() == AlarmType.class) {

				for (AlarmConfig alarmConfig : EntitiesManager.getInstance().getAlarmConfigRepository()
						.getsByAlarmType(Long.valueOf(key.getId(1)), false)) {

					alarmConfigsVO.add(new AlarmConfigGraphql(alarmConfig));
				}

			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: AlarmConfig");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return alarmConfigsVO;
	}
	
	public List<AlarmConfigVO> getAlarmConfigsByAlarmLevel(String alarmLevelId) {

		List<AlarmConfigVO> alarmConfigsVO = new ArrayList<>();

		try {
			Key key = new Key(alarmLevelId);

			if (key.getClazz() == AlarmLevel.class) {

				for (AlarmConfig alarmConfig : EntitiesManager.getInstance().getAlarmConfigRepository()
						.getsByAlarmLevel(Long.valueOf(key.getId(1)), false)) {

					alarmConfigsVO.add(new AlarmConfigGraphql(alarmConfig));
				}

			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: AlarmConfig");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return alarmConfigsVO;
	}
	
	public List<AlarmConfigVO> getAlarmConfigsByElementType(String elementType){
		
		List<AlarmConfigVO> alarmConfigsVO = new ArrayList<>();
		
		try {
			Key key = new Key(elementType);

			if (key.getClazz() == ElementType.class) {

				for (AlarmConfig alarmConfig : EntitiesManager.getInstance().getAlarmConfigRepository()
						.getsByElementType(Long.valueOf(key.getId(1)), false)) {

					alarmConfigsVO.add(new AlarmConfigGraphql(alarmConfig));
				}

			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: AlarmConfig");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return alarmConfigsVO;
	}

	@Override
	public Optional<AlarmConfigVO> create(AlarmConfigVO alarmConfigVO) {

		Optional<AlarmConfigVO> resp = null;

		if (alarmConfigVO != null) {

			AlarmConfig alarmConfig = AlarmConfigHelper.get(alarmConfigVO);

			AlarmConfig alarmConfigCreated = EntitiesManager.getInstance().getAlarmConfigRepository().create(alarmConfig);

			if (alarmConfigCreated != null) {

				resp = Optional.of(new AlarmConfigGraphql(alarmConfigCreated));
			}
		}
		return resp;
	}

	@Override
	public Optional<AlarmConfigVO> clone(String item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<AlarmConfigVO> update(AlarmConfigVO alarmConfigVO) {

		Optional<AlarmConfigVO> resp = null;

		if (alarmConfigVO != null) {

			AlarmConfig alarmConfig = AlarmConfigHelper.get(alarmConfigVO);

			AlarmConfig alarmConfigCreated = EntitiesManager.getInstance().getAlarmConfigRepository().update(alarmConfig);

			if (alarmConfigCreated != null) {

				resp = Optional.of(new AlarmConfigGraphql(alarmConfigCreated));
			}
		}
		return resp;
	}

	@Override
	public Optional<AlarmConfigVO> delete(String alarmConfigId) {

		Optional<AlarmConfigVO> resp = null;

		if (alarmConfigId != null) {

			AlarmConfigVO alarmConfigVO = new AlarmConfigVO();
			alarmConfigVO.setId(alarmConfigId);

			AlarmConfig alarmConfig = AlarmConfigHelper.get(alarmConfigVO);

			AlarmConfig alarmConfigDeleted = EntitiesManager.getInstance().getAlarmConfigRepository().delete(alarmConfig);

			if (alarmConfigDeleted != null) {

				resp = Optional.of(new AlarmConfigGraphql(alarmConfigDeleted));
			}
		}

		return resp;
	}

	@Override
	public List<AlarmConfigVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}

}
