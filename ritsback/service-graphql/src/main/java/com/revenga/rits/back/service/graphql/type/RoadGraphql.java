package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.Road;
import com.revenga.rits.back.data.core.model.RoadType;
import com.revenga.rits.back.front.core.model.helper.RoadHelper;
import com.revenga.rits.back.front.core.model.vo.RoadTypeVO;
import com.revenga.rits.back.front.core.model.vo.RoadVO;
import com.revenga.rits.back.service.graphql.service.EntitiesManager;

public class RoadGraphql extends RoadVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2024413709955798391L;
	
	private Road road;

	public RoadGraphql(final Road road) {

		RoadHelper.assign(road, this);	
		this.road = road;

	}
	
	@Override
	public RoadTypeVO getRoadType() {

		if (super.getRoadType() == null && road != null
				&& road.getRoadTypeId() != null) {

			RoadType roadType = EntitiesManager.getInstance().getRoadTypeRepository()
					.get(road.getRoadTypeId(), false);

			if (roadType != null) {

				super.setRoadType(new RoadTypeGraphql(roadType));
			}
		}

		return super.getRoadType();
	}
}
