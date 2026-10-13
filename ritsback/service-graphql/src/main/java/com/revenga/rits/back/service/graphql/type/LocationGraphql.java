package com.revenga.rits.back.service.graphql.type;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.data.core.model.Location;
import com.revenga.rits.back.data.core.model.LocationType;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.data.core.model.StretchLocation;
import com.revenga.rits.back.front.core.model.helper.LocationHelper;
import com.revenga.rits.back.front.core.model.vo.LocationTypeVO;
import com.revenga.rits.back.front.core.model.vo.LocationVO;
import com.revenga.rits.back.front.core.model.vo.StretchVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class LocationGraphql extends LocationVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2024413709955798391L;
	private Location location;

	public LocationGraphql(final Location location) {

		LocationHelper.assign(location, this);
		this.location = location;

	}

	@Override
	public LocationTypeVO getLocationType() {

		if (super.getLocationType() == null && location != null && location.getLocationTypeId() != null) {

			LocationType locationType = EntitiesManager.getInstance().getLocationTypeRepository()
					.get(location.getLocationTypeId(), false);

			if (locationType != null) {

				super.setLocationType(new LocationTypeGraphql(locationType));
			}
		}

		return super.getLocationType();
	}

	@Override
	public List<StretchVO> getStretchs() {

		if (super.getStretchs() == null) {

			if (ObjectUtils.allNotNull(location)) {

				List<StretchLocation> stretchLocations = EntitiesManager.getInstance().getStretchLocationRepository()
						.getByLocation(location.getId(), false);

				if (!CollectionUtils.isEmpty(stretchLocations)) {

					List<StretchVO> stretchVOs = new ArrayList<>();

					for (StretchLocation stretchLocation : stretchLocations) {

						Stretch stretch = EntitiesManager.getInstance().getStretchRepository()
								.get(stretchLocation.getStretchId(), false);

						if (stretch != null) {

							stretchVOs.add(new StretchGraphql(stretch, stretchLocation));
						}
					}
					super.setStretchs(stretchVOs);
				}

			} else {

				log.error("location has null pointer");
			}
		}

		return super.getStretchs();

	}

}
