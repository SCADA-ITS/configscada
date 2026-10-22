package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Road;
import com.revenga.rits.back.data.core.model.RoadType;
import com.revenga.rits.back.front.core.model.vo.RoadVO;

public class RoadHelper {

	private RoadHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static RoadVO get(final Road road) {

		RoadVO roadVO = null;

		if (road != null) {

			roadVO = new RoadVO();

			assign(road, roadVO);
		}

		return roadVO;
	}

	public static Road get(final RoadVO roadVO) {

		Road road = null;

		if (roadVO != null) {

			road = new Road();
			if (roadVO.getId() != null) {

				Key key = new Key(roadVO.getId());

				if (key.getClazz() == Road.class) {

					road.setId(Long.valueOf(key.getId(1)));
				}
			}

			road.setAlias(roadVO.getAlias());
			road.setDescription((roadVO.getDescription()));

			if (roadVO.getRoadTypeId() != null) {

				Key key = new Key(roadVO.getRoadTypeId());

				if (key.getClazz() == RoadType.class) {

					road.setRoadTypeId(Long.valueOf(key.getId(1)));
				}
			}

		}

		return road;
	}

	public static void assign(final Road road, RoadVO roadVO) {

		if (ObjectUtils.allNotNull(road, roadVO)) {

			BasicElementHelper.fillBasicProperties(roadVO, road);
		}
	}

}
