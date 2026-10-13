package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.GenericData;
import com.revenga.rits.back.front.core.model.vo.GenericDataVO;
import com.revenga.rits.back.service.graphql.type.GenericDataGraphql;

@Service
public class GenericDataService implements QueryInterface<GenericDataVO> {

	@Override
	public List<GenericDataVO> getAll() {

		List<GenericDataVO> genericDatas = new ArrayList<>();

		for (GenericData genericData : EntitiesManager.getInstance().getGenericDataRepository().getAll(false)) {

			genericDatas.add(new GenericDataGraphql(genericData));
		}

		return genericDatas;
	}

	@Override
	public Optional<GenericDataVO> get(String genericDataId) {

		Optional<GenericDataVO> genericDataVO = null;

		Key key = new Key(genericDataId);

		if (key.getClazz() == GenericData.class) {

			GenericData genericData = EntitiesManager.getInstance().getGenericDataRepository()
					.get(Long.valueOf(key.getId(1)), false);

			if (genericData != null) {

				genericDataVO = Optional.of(new GenericDataGraphql(genericData));

			}
		}
		return genericDataVO;
	}

	@Override
	public List<GenericDataVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}

}
