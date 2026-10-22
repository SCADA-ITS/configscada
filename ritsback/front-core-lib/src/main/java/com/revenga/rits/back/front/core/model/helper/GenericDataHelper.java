package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.data.core.model.GenericData;
import com.revenga.rits.back.front.core.model.vo.GenericDataVO;

public class GenericDataHelper {

	private GenericDataHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static GenericDataVO get(final GenericData genericData) {

		GenericDataVO genericDataVO = new GenericDataVO();
		
		assign(genericData, genericDataVO);
		
		return genericDataVO;
	}

	public static void assign(final GenericData genericData, GenericDataVO genericDataVO ) {

		if (ObjectUtils.allNotNull(genericData, genericDataVO)) {
		
			BasicElementHelper.fillBasicProperties(genericDataVO, genericData);

			genericDataVO.setValue(genericData.getValue());
			
		}
	}
	
}
