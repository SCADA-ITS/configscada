package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.Location;
import com.revenga.rits.back.data.core.model.LocationType;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.data.core.model.StretchLocation;
import com.revenga.rits.back.front.core.model.helper.ElementHelper;
import com.revenga.rits.back.front.core.model.helper.LocationHelper;
import com.revenga.rits.back.front.core.model.vo.ElementVO;
import com.revenga.rits.back.front.core.model.vo.LocationTypeVO;
import com.revenga.rits.back.front.core.model.vo.LocationVO;
import com.revenga.rits.back.service.graphql.type.ElementGraphql;
import com.revenga.rits.back.service.graphql.type.LocationGraphql;
import com.revenga.rits.back.service.graphql.type.LocationTypeGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class LocationTypeService implements QueryInterface<LocationTypeVO>{

	@Override
	public List<LocationTypeVO> getAll() {

		List<LocationTypeVO> locationTypesVOs = new ArrayList<>();

		for (LocationType locationType : EntitiesManager.getInstance().getLocationTypeRepository().getAll(false)) {

			locationTypesVOs.add(new LocationTypeGraphql(locationType));
		}

		return locationTypesVOs;
	}

	@Override
	public Optional<LocationTypeVO> get(String idItem) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<LocationTypeVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}
}


