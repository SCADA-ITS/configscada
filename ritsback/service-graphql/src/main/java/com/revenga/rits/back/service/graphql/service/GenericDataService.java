package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.GenericData;
import com.revenga.rits.back.data.core.model.GenericData.ManagementAreaValue;
import com.revenga.rits.back.front.core.model.vo.GenericDataVO;
import com.revenga.rits.back.service.graphql.type.GenericDataGraphql;

@Service
public class GenericDataService {

	public List<GenericDataVO> getAll(Long managementAreaId) {

		List<GenericDataVO> genericDatas = new ArrayList<>();

		for (GenericData genericData : EntitiesManager.getInstance().getGenericDataRepository().getAll(false)) {

			genericDatas.add(new GenericDataGraphql(getByManagementArea(genericData, managementAreaId)));
		}

		return genericDatas;
	}

	public Optional<GenericDataVO> get(String genericDataId, Long managementAreaId) {

		Optional<GenericDataVO> genericDataVO = Optional.empty();

		Key key = new Key(genericDataId);

		if (key.getClazz() == GenericData.class) {

			GenericData genericData = EntitiesManager.getInstance().getGenericDataRepository()
					.get(Long.valueOf(key.getId(1)), false);

			if (genericData != null) {

				genericDataVO = Optional.of(new GenericDataGraphql(getByManagementArea(genericData, managementAreaId)));

			}
		}
		return genericDataVO;
	}

	private GenericData getByManagementArea(final GenericData genericData, final Long managementAreaId) {

		GenericData data = new GenericData();
		data.setId(genericData.getId());
		data.setValue(genericData.getValue());

		if (managementAreaId != null && CollectionUtils.isNotEmpty(genericData.getManagementAreaValues())) {

			ManagementAreaValue value = genericData.getManagementAreaValues().stream()
					.filter(e -> managementAreaId.equals(e.getManagementAreaId())).findFirst().orElse(null);

			if (value != null) {
				data.setValue(value.getValue());
			}
		}

		return data;

	}
}
