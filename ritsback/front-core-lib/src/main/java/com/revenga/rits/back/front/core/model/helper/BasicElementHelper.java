package com.revenga.rits.back.front.core.model.helper;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.BasicElement;
import com.revenga.rits.back.front.core.model.vo.BasicElementVO;

public class BasicElementHelper {

	private BasicElementHelper() {
	    throw new IllegalStateException(this.getClass().getSimpleName());
	  } 
	
	public static void fillBasicProperties(BasicElementVO basicElementVO, BasicElement basicElement) {
		
		basicElementVO.setId(getIdFromElement(basicElement));
		basicElementVO.setAlias(basicElement.getAlias());
		basicElementVO.setDescription(basicElement.getDescription());
		basicElementVO.setLabelAlias(basicElement.getLabelAlias());
		basicElementVO.setLabelDescription(basicElement.getLabelDescription());
		basicElementVO.setCreatedAt(basicElement.getCreatedAt());
		basicElementVO.setUpdatedAt(basicElement.getUpdatedAt());
	}
	
	public static String getIdFromElement(final BasicElement basicElement) {

		return (new Key(basicElement)).getSimpleValue();
	}
}
