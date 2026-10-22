package com.revenga.rits.back.service.graphql.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Location;
import com.revenga.rits.back.data.core.model.Road;
import com.revenga.rits.back.data.core.model.Stretch;
import com.revenga.rits.back.data.core.model.StretchLocation;
import com.revenga.rits.back.front.core.model.vo.StretchVO;
import com.revenga.rits.back.service.graphql.type.StretchGraphql;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class StretchService implements QueryInterface<StretchVO> {

	public List<StretchVO> getAll(Long managementAreaId) {

		List<StretchVO> stretchs = new ArrayList<>();

		for (Stretch stretch : EntitiesManager.getInstance().getStretchRepository().getAll(false)) {

			if (checkManagementArea(stretch, managementAreaId)) {

				stretchs.add(new StretchGraphql(stretch));
			}
		}

		return stretchs;
	}

	@Override
	public Optional<StretchVO> get(String stretchKey) {

		Optional<StretchVO> stretchVO = Optional.empty();
		try {
			Key key = new Key(stretchKey);

			if (key.getClazz() == Stretch.class) {

				Stretch stretch = EntitiesManager.getInstance().getStretchRepository().get(Long.valueOf(key.getId(1)),
						false);

				if (stretch != null) {

					stretchVO = Optional.of(new StretchGraphql(stretch));

				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: Stretch");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return stretchVO;
	}

	public List<StretchVO> getByRoad(String roadId) {

		List<StretchVO> stretchVO = new ArrayList<>();

		try {
			Key key = new Key(roadId);

			if (key.getClazz() == Road.class) {

				for (Stretch stretch : EntitiesManager.getInstance().getStretchRepository()
						.getsByRoad(Long.valueOf(key.getId(1)), false)) {

					stretchVO.add(new StretchGraphql(stretch));
				}

			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: Road");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return stretchVO;
	}

	public List<StretchVO> getByLocation(String locationId) {

		List<StretchVO> stretchVO = new ArrayList<>();

		try {
			Key key = new Key(locationId);

			if (key.getClazz() == Location.class) {

				for (StretchLocation stretchLocation : EntitiesManager.getInstance().getStretchLocationRepository()
						.getByLocation(Long.valueOf(key.getId(1)), false)) {

					Stretch stretch = EntitiesManager.getInstance().getStretchRepository()
							.get(stretchLocation.getStretchId(), false);

					if (stretch != null) {

						stretchVO.add(new StretchGraphql(stretch));
					}
				}
			}

		} catch (Exception e) {

			log.error("The id does not correspond to the required object: Location");
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return stretchVO;
	}

	@Override
	public List<StretchVO> gets(List<String> idItems) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StretchVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean checkManagementArea(final Stretch stretch, final Long managementAreaId) {

		boolean resp = false;

		if (stretch != null && stretch.getRoadId() != null) {

			Road road = EntitiesManager.getInstance().getRoadRepository().get(stretch.getRoadId(), false);

			resp = road != null
					&& (road.getManagementAreaId() == null || road.getManagementAreaId().equals(managementAreaId));
		}

		return resp;
	}
}
