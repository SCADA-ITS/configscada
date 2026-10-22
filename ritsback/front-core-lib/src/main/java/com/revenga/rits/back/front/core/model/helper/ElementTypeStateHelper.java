package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.front.core.model.vo.ElementTypeStateVO;

public class ElementTypeStateHelper {

	private ElementTypeStateHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static void assign(final ElementTypeState elementTypeState, ElementTypeStateVO elementTypeStateVO) {

		if (ObjectUtils.allNotNull(elementTypeState, elementTypeStateVO)) {

			BasicElementHelper.fillBasicProperties(elementTypeStateVO, elementTypeState);
			
			elementTypeStateVO.setStateId(elementTypeState.getStateId());
			elementTypeStateVO.setElementTypeId(
					(new Key(ElementType.class, new String[] { String.valueOf(elementTypeState.getElementTypeId()) }))
							.getSimpleValue());
		}
	}

	public static ElementTypeStateVO get(ElementTypeState elementTypeState) {

		ElementTypeStateVO elementTypeStateVO = null;

		if (elementTypeState != null) {

			elementTypeStateVO = new ElementTypeStateVO();
			
			assign(elementTypeState, elementTypeStateVO);
		}

		return elementTypeStateVO;
	}
	
	public static ElementTypeState get(ElementTypeStateVO elementTypeStateVO) {

		ElementTypeState elementTypeState = null;

		if (elementTypeStateVO != null) {

			elementTypeState = new ElementTypeState();

			if (elementTypeStateVO.getId() != null) {

				Key key = new Key(elementTypeStateVO.getId());

				if (key.getClazz() == ElementTypeState.class) {

					elementTypeState.setElementTypeId(Long.valueOf(key.getId(1)));
					elementTypeState.setStateId(Long.valueOf(key.getId(2)));
				}
			}

			elementTypeState.setAlias(elementTypeStateVO.getAlias());
			elementTypeState.setDescription((elementTypeStateVO.getDescription()));
			elementTypeState.setLabelAlias(elementTypeStateVO.getLabelAlias());
			elementTypeState.setLabelDescription(elementTypeStateVO.getLabelDescription());
		}

		return elementTypeState;
	}

}
