package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Location;
import com.revenga.rits.back.data.core.model.LocationType;
import com.revenga.rits.back.front.core.model.vo.LocationVO;

public class LocationHelper {

	private LocationHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static LocationVO get(final Location location) {

		LocationVO locationVO = null;

		if (location != null) {

			locationVO = new LocationVO();

			assign(location, locationVO);
		}

		return locationVO;
	}

	public static Location get(final LocationVO locationVO) {

		Location location = null;

		if (locationVO != null) {

			location = new Location();
			if (locationVO.getId() != null) {

				Key key = new Key(locationVO.getId());

				if (key.getClazz() == Location.class) {

					location.setId(Long.valueOf(key.getId(1)));
				}
			}

			if (locationVO.getLocationTypeId() != null) {

				Key key = new Key(locationVO.getLocationTypeId());

				if (key.getClazz() == LocationType.class) {

					location.setLocationTypeId(Long.valueOf(key.getId(1)));
				}
			}

			location.setAlias(locationVO.getAlias());
			location.setDescription((locationVO.getDescription()));
			location.setLane(locationVO.getLane());
		}

		return location;
	}

	public static void assign(final Location location, LocationVO locationVO) {

		if (ObjectUtils.allNotNull(location, locationVO)) {

			BasicElementHelper.fillBasicProperties(locationVO, location);

			if (location.getLocationTypeId() != null) {

				LocationType locationType = new LocationType();
				locationType.setId(location.getLocationTypeId());
				locationVO.setLocationTypeId(BasicElementHelper.getIdFromElement(locationType));
				locationVO.setLane(location.getLane());
			}

		}
	}

}
