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
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.data.core.model.StretchLocation;
import com.revenga.rits.back.front.core.model.helper.ElementHelper;
import com.revenga.rits.back.front.core.model.helper.LocationHelper;
import com.revenga.rits.back.front.core.model.vo.ElementVO;
import com.revenga.rits.back.front.core.model.vo.LocationVO;
import com.revenga.rits.back.service.graphql.type.ElementGraphql;
import com.revenga.rits.back.service.graphql.type.LocationGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class LocationService implements QueryInterface<LocationVO>, MutationInterface<LocationVO> {

	@Override
	public List<LocationVO> getAll() {

		List<LocationVO> locations = new ArrayList<>();

		for (Location location : EntitiesManager.getInstance().getLocationRepository().getAll(false)) {

			locations.add(new LocationGraphql(location));
		}

		return locations;
	}

	@Override
	public Optional<LocationVO> get(String locationKey) {

		Optional<LocationVO> locationVO = null;
		try {
			Key key = new Key(locationKey);

			if (key.getClazz() == Location.class) {

				Location location = EntitiesManager.getInstance().getLocationRepository()
						.get(Long.valueOf(key.getId(1)), false);

				if (location != null) {

					locationVO = Optional.of(new LocationGraphql(location));

				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: Location");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return locationVO;
	}

	public List<LocationVO> getByStretch(String stretchId) {

		List<LocationVO> locationVO = new ArrayList<>();
		Location location;

		try {
			Key key = new Key(stretchId);

			if (key.getClazz() == Stretch.class) {

				for (StretchLocation stretchLocation : EntitiesManager.getInstance().getStretchLocationRepository()
						.getByStretch(Long.valueOf(key.getId(1)), false)) {

					location = EntitiesManager.getInstance().getLocationRepository()
							.get(stretchLocation.getLocationId(), false);

					if (location != null) {

						locationVO.add(new LocationGraphql(location));
					}
				}

			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: Stretch");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return locationVO;
	}
	
	public List<LocationVO> getByStretchs(final List<String> stretchIds) {
		
		List<LocationVO> locations = null;
		
		try {
		
			if (CollectionUtils.isEmpty(stretchIds)) {
				
				locations = getAll();
			}
			else {
				
				locations = new ArrayList<>();
				
				for (String stretchId : stretchIds) {
					
					locations.addAll(getByStretch(stretchId));
				}
			}
		
		} catch (Exception e) {

			log.error("The id does not correspond to the required object: Stretch");
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	
		return locations;
	}
	
	@Override
	public List<LocationVO> gets(List<String> locationIds) {
		
		List<LocationVO> locations = null;
		
		try {
		
			if (CollectionUtils.isEmpty(locationIds)) {
				
				locations = getAll();
			}
			else {
				
				locations = new ArrayList<>();
				
				for (String locationId : locationIds) {
					
					Optional<LocationVO> locationVO = get(locationId);
					
					if (locationVO.isPresent()) {
						
						locations.add(locationVO.get());
					}
				}
			}
		
		} catch (Exception e) {

			log.error("The id does not correspond to the required object: Location");
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	
		return locations;
	}
	
	@Override
	public Optional<LocationVO> create(LocationVO locationVO) {

		Optional<LocationVO> resp = null;

		if (locationVO != null) {

			Location location = LocationHelper.get(locationVO);

			Location locationCreated = EntitiesManager.getInstance().getLocationRepository().create(location);

			if (locationCreated != null) {

				resp = Optional.of(new LocationGraphql(locationCreated));
			}
		}
		return resp;
	}

	@Override
	public Optional<LocationVO> clone(String item) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<LocationVO> update(LocationVO locationVO) {
		
		Optional<LocationVO> resp = Optional.empty();

		if (locationVO != null) {

			Location location = LocationHelper.get(locationVO);

			Location locationUpdated = EntitiesManager.getInstance().getLocationRepository().update(location);

			if (locationUpdated != null) {

				resp = Optional.of(new LocationGraphql(locationUpdated));
			}
		}
		return resp;
	}

	@Override
	public Optional<LocationVO> delete(String id) {
		
		Optional<LocationVO> resp = Optional.empty();

		if (id != null) {

			LocationVO locationVO = new LocationVO();
			locationVO.setId(id);

			Location location = LocationHelper.get(locationVO);

			Location locationDeleted = EntitiesManager.getInstance().getLocationRepository().delete(location);

			if (locationDeleted != null) {

				resp = Optional.of(new LocationGraphql(locationDeleted));
			}
		}

		return resp;
	}

}
