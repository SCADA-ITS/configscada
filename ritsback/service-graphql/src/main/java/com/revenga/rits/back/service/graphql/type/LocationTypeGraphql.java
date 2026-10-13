package com.revenga.rits.back.service.graphql.type;

import com.revenga.rits.back.data.core.model.LocationType;
import com.revenga.rits.back.front.core.model.helper.LocationTypeHelper;
import com.revenga.rits.back.front.core.model.vo.LocationTypeVO;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class LocationTypeGraphql extends LocationTypeVO {
	/**
	* 
	*/
	private static final long serialVersionUID = 3874080186931463780L;

	public LocationTypeGraphql(final LocationType locationType) {

		LocationTypeHelper.assign(locationType, this);

	}

}
