package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.data.core.model.StretchStretch;
import com.revenga.rits.back.data.core.model.StretchStretchStretch;
import com.revenga.rits.back.front.core.model.vo.StretchStretchVO;
import com.revenga.rits.back.service.graphql.type.StretchStretchGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class StretchStretchService implements QueryInterface<StretchStretchVO> {

	public List<StretchStretchVO> getsByStretch(String stretchId) {

		List<StretchStretchVO> stretchStretchsVO = new ArrayList<>();

		try {
			Key key = new Key(stretchId);

			if (key.getClazz() == Stretch.class) {

				List<StretchStretchStretch> stretchStretchsStretchs = EntitiesManager.getInstance()
						.getStretchStretchStretchRepository().getsByStretch(Long.valueOf(key.getId(1)), false);

				if (CollectionUtils.isNotEmpty(stretchStretchsStretchs)) {

					for (StretchStretchStretch stretchStretchStretch : stretchStretchsStretchs) {

						StretchStretch stretchStretch = EntitiesManager.getInstance().getStretchStretchRepository().get(stretchStretchStretch.getStretchStretchId(), false);
						
						if (stretchStretch != null) {
							
							stretchStretchsVO.add(new StretchStretchGraphql(stretchStretch));
						}
					}

				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: Road");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return stretchStretchsVO;
	}

	@Override
	public List<StretchStretchVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<StretchStretchVO> get(String idItem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StretchStretchVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}
}
