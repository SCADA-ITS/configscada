package com.revenga.rits.back.front.core.model.helper;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.front.core.model.vo.ElementStateVO;

public class ElementStateHelper {

	private ElementStateHelper() {

		super();
	}

	public static ElementStateVO get(ElementState elementState) {

		ElementStateVO elementStateVO = null;

		if (elementState != null) {

			elementStateVO = new ElementStateVO();

			assign(elementState, elementStateVO);
		}

		return elementStateVO;
	}
	
	public static ElementState get(ElementStateVO elementStateVO) {
		
		ElementState elementState = null;
		
		if (elementStateVO != null) {
			
			elementState = new ElementState();
			
			if (elementStateVO.getElementId() != null) {
				
				Key key = new Key(elementStateVO.getElementId());

				if (key.getClazz() == Element.class) {

					elementState.setElementTypeId(Long.valueOf(key.getId(1)));
					elementState.setElementId(Long.valueOf(key.getId(2)));
				}
			}
			
			if (elementStateVO.getCurrentElementTypeStateId() != null) {
				
				Key key = new Key(elementStateVO.getCurrentElementTypeStateId());

				if (key.getClazz() == ElementTypeState.class) {

					elementState.setStateId(Long.valueOf(key.getId(2)));
					
				}
			}
			
			elementState.setMaintenanceComment(elementStateVO.getMaintenanceComment());
		}
		
		
		return elementState;
	}

	public static void assign(final ElementState elementState, ElementStateVO elementStateVO) {

		if (elementState != null && elementStateVO != null) {

			BasicElementHelper.fillBasicProperties(elementStateVO, elementState);
			elementStateVO.setMaintenanceComment(elementState.getMaintenanceComment());

		}
	}
}