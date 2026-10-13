package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ElementValueState;
import com.revenga.rits.back.front.core.model.vo.ElementValueStateVO;

public class ElementValueStateHelper {

	private ElementValueStateHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static ElementValueStateVO get(final ElementValueState elementValueState) {

		ElementValueStateVO elementValueStateVO = null;

		if (elementValueState != null) {

			elementValueStateVO = new ElementValueStateVO();

			assign(elementValueState, elementValueStateVO);
		}

		return elementValueStateVO;
	}

	public static ElementValueState get(final ElementValueStateVO elementValueStateVO) {

		ElementValueState elementValueState = null;

		if (elementValueStateVO != null) {

			elementValueState = new ElementValueState();
			if (elementValueStateVO.getId() != null) {

				Key key = new Key(elementValueStateVO.getId());

				if (key.getClazz() == ElementValueState.class) {

					elementValueState.setId(Long.valueOf(key.getId(1)));
				}
			}

			elementValueState.setAlias(elementValueStateVO.getAlias());
			elementValueState.setDescription((elementValueStateVO.getDescription()));

		}

		return elementValueState;
	}

	public static void assign(final ElementValueState elementValueState, ElementValueStateVO elementValueStateVO) {

		if (ObjectUtils.allNotNull(elementValueState, elementValueStateVO)) {

			BasicElementHelper.fillBasicProperties(elementValueStateVO, elementValueState);

		}
	}

}
