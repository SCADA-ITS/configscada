package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.RoadType;
import com.revenga.rits.back.front.core.model.helper.RoadTypeHelper;
import com.revenga.rits.back.front.core.model.vo.RoadTypeVO;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class RoadTypeGraphql extends RoadTypeVO {
	/**
	* 
	*/
	private static final long serialVersionUID = 2283790711598452844L;

	public RoadTypeGraphql(final RoadType roadType) {

		RoadTypeHelper.assign(roadType, this);

	}

}
