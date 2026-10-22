package com.revenga.rits.back.front.core.model.helper;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.RoadType;
import com.revenga.rits.back.front.core.model.vo.RoadTypeVO;

public class RoadTypeHelper {

	private RoadTypeHelper() {

		super();
	}

	public static RoadTypeVO get(RoadType roadType) {

		RoadTypeVO roadTypeVO = null;

		if (roadType != null) {

			roadTypeVO = new RoadTypeVO();

			assign(roadType, roadTypeVO);
		}

		return roadTypeVO;
	}

	public static RoadType get(RoadTypeVO roadTypeVO) {

		RoadType roadType = null;

		if (roadTypeVO != null) {

			roadType = new RoadType();

			if (roadTypeVO.getId() != null) {

				Key key = new Key(roadTypeVO.getId());

				if (key.getClazz() == RoadType.class) {

					roadType.setId(Long.valueOf(key.getId(1)));
				}
			}

			roadType.setAlias(roadTypeVO.getAlias());
			roadType.setDescription((roadTypeVO.getDescription()));

		}

		return roadType;
	}

	public static void assign(final RoadType roadType, RoadTypeVO roadTypeVO) {

		if (roadType != null && roadTypeVO != null) {

			BasicElementHelper.fillBasicProperties(roadTypeVO, roadType);

		}
	}

}
