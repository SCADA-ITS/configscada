package com.revenga.rits.back.front.core.model.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Location;
import com.revenga.rits.back.data.core.model.LocationType;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.data.core.model.StretchLocation;
import com.revenga.rits.back.front.core.model.vo.LocationVO;
import com.revenga.rits.back.front.core.model.vo.StretchLocationVO;

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

			if (locationVO.getStretchLocations()!= null) {

				List<StretchLocation> stretchLocations = new ArrayList<>();

				for (StretchLocationVO stretchLocationVO : locationVO.getStretchLocations()) {

					if (stretchLocationVO.getStretchId() != null) {

						Key key = new Key(stretchLocationVO.getStretchId());

						if (key.getClazz() == Stretch.class) {

							StretchLocation stretchLocation = new StretchLocation();
							stretchLocation.setStretchId(Long.valueOf(key.getId(1)));
							stretchLocation.setPosition(stretchLocationVO.getPosition());
							stretchLocations.add(stretchLocation);

						}
					}
				}
				
				location.setStretchLocations(stretchLocations);
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
