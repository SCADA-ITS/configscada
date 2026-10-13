package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.LogType;
import com.revenga.rits.back.front.core.model.vo.LogTypeVO;
import com.revenga.rits.back.service.graphql.type.LogTypeGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class LogTypeService implements QueryInterface<LogTypeVO> {

	public List<LogTypeVO> getAll() {

		List<LogTypeVO> logTypes = new ArrayList<>();

		for (LogType logType : EntitiesManager.getInstance().getLogTypeRepository().getAll(false)) {

			logTypes.add(new LogTypeGraphql(logType));
		}

		return logTypes;
	}

	public Optional<LogTypeVO> get(String logTypeId) {

		Optional<LogTypeVO> logTypesVO = null;
		try {
			Key key = new Key(logTypeId);

			if (key.getClazz() == LogType.class) {

				LogType logType = EntitiesManager.getInstance().getLogTypeRepository().get(Long.valueOf(key.getId(1)),
						false);

				if (logType != null) {

					logTypesVO = Optional.of(new LogTypeGraphql(logType));

				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: LogType");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return logTypesVO;
	}

	@Override
	public List<LogTypeVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}
}
