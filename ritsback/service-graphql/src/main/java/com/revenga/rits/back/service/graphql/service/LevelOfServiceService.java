package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.LevelOfService;
import com.revenga.rits.back.front.core.model.vo.LevelOfServiceVO;
import com.revenga.rits.back.service.graphql.type.LevelOfServiceGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class LevelOfServiceService implements QueryInterface<LevelOfServiceVO>{
	
	
	@Override
	public List<LevelOfServiceVO> getAll() {
	
		List<LevelOfServiceVO> levelOfServicesVO = new ArrayList<>();

		for (LevelOfService levelOfService : EntitiesManager.getInstance().getLevelOfServiceRepository().getAll(false)) {

			levelOfServicesVO.add(new LevelOfServiceGraphql(levelOfService));
		}

		return levelOfServicesVO;
	}

	@Override
	public Optional<LevelOfServiceVO> get(String levelOfServiceId) {
		
		Optional<LevelOfServiceVO> levelOfServiceVO = null;
		try {
			Key key = new Key(levelOfServiceId);

			if (key.getClazz() == LevelOfService.class) {

				LevelOfService levelOfService = EntitiesManager.getInstance().getLevelOfServiceRepository()
						.get(Long.valueOf(key.getId(1)), false);

				if (levelOfService != null) {

					levelOfServiceVO = Optional.of(new LevelOfServiceGraphql(levelOfService));

				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: Level of service");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return levelOfServiceVO;
	}

	@Override
	public List<LevelOfServiceVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}

}
