package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.RoadImpact;
import com.revenga.rits.back.front.core.model.vo.RoadImpactVO;

public class RoadImpactHelper {

	private RoadImpactHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static RoadImpactVO get(final RoadImpact roadImpact) {

		RoadImpactVO roadImpactVO = null;

		if (roadImpact != null) {

			roadImpactVO = new RoadImpactVO();

			assign(roadImpact, roadImpactVO);
		}

		return roadImpactVO;
	}

	public static RoadImpact get(final RoadImpactVO roadImpactVO) {

		RoadImpact roadImpact = null;

		if (roadImpactVO != null) {

			roadImpact = new RoadImpact();
			if (roadImpactVO.getId() != null) {

				Key key = new Key(roadImpactVO.getId());

				if (key.getClazz() == RoadImpact.class) {

					roadImpact.setId(Long.valueOf(key.getId(1)));
				}
			}

			roadImpact.setAlias(roadImpactVO.getAlias());
			roadImpact.setDescription((roadImpactVO.getDescription()));
		}

		return roadImpact;
	}

	public static void assign(final RoadImpact roadImpact, RoadImpactVO roadImpactVO) {

		if (ObjectUtils.allNotNull(roadImpact, roadImpactVO)) {

			BasicElementHelper.fillBasicProperties(roadImpactVO, roadImpact);
		}
	}

}