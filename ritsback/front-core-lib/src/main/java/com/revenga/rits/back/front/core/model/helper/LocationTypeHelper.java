package com.revenga.rits.back.front.core.model.helper;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.LocationType;
import com.revenga.rits.back.front.core.model.vo.LocationTypeVO;

public class LocationTypeHelper {

	private LocationTypeHelper() {

		super();
	}

	public static LocationTypeVO get(LocationType locationType) {

		LocationTypeVO locationTypeVO = null;

		if (locationType != null) {

			locationTypeVO = new LocationTypeVO();

			assign(locationType, locationTypeVO);
		}

		return locationTypeVO;
	}

	public static LocationType get(LocationTypeVO locationTypeVO) {

		LocationType locationType = null;

		if (locationTypeVO != null) {

			locationType = new LocationType();

			if (locationTypeVO.getId() != null) {

				Key key = new Key(locationTypeVO.getId());

				if (key.getClazz() == LocationType.class) {

					locationType.setId(Long.valueOf(key.getId(1)));
				}
			}

			locationType.setAlias(locationTypeVO.getAlias());
			locationType.setDescription((locationTypeVO.getDescription()));

		}

		return locationType;
	}

	public static void assign(final LocationType locationType, LocationTypeVO locationTypeVO) {

		if (locationType != null && locationTypeVO != null) {

			BasicElementHelper.fillBasicProperties(locationTypeVO, locationType);

		}
	}

}
