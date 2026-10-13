package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Road;
import com.revenga.rits.back.front.core.model.vo.RoadVO;
import com.revenga.rits.back.service.graphql.type.RoadGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class RoadService implements QueryInterface<RoadVO> {

	@Override
	public List<RoadVO> getAll() {

		List<RoadVO> roads = new ArrayList<>();

		for (Road road : EntitiesManager.getInstance().getRoadRepository().getAll(false)) {

			roads.add(new RoadGraphql(road));
		}

		return roads;
	}

	@Override
	public Optional<RoadVO> get(String roadKey) {

		Optional<RoadVO> roadVO = null;
		try {
			Key key = new Key(roadKey);

			if (key.getClazz() == Road.class) {

				Road road = EntitiesManager.getInstance().getRoadRepository().get(Long.valueOf(key.getId(1)), false);

				if (road != null) {

					roadVO = Optional.of(new RoadGraphql(road));

				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: Road");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return roadVO;
	}

	@Override
	public List<RoadVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}
}
