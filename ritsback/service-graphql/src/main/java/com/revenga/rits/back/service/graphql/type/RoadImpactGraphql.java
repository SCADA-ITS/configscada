package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.RoadImpact;
import com.revenga.rits.back.front.core.model.helper.RoadImpactHelper;
import com.revenga.rits.back.front.core.model.vo.RoadImpactVO;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class RoadImpactGraphql extends RoadImpactVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -170363755507165162L;
	
	private RoadImpact roadImpact;
	
	public RoadImpactGraphql(final RoadImpact roadImpact) {

		RoadImpactHelper.assign(roadImpact, this);

		this.roadImpact = roadImpact;
	}

}
