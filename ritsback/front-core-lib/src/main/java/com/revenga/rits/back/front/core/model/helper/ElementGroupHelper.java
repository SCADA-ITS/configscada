package com.revenga.rits.back.front.core.model.helper;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ElementGroup;
import com.revenga.rits.back.front.core.model.vo.ElementGroupVO;

public class ElementGroupHelper {

	private ElementGroupHelper() {

		super();
	}

	public static ElementGroupVO get(ElementGroup elementGroup) {

		ElementGroupVO elementGroupVO = null;

		if (elementGroup != null) {

			elementGroupVO = new ElementGroupVO();

			assign(elementGroup, elementGroupVO);
		}

		return elementGroupVO;
	}

	public static ElementGroup get(ElementGroupVO elementGroupVO) {

		ElementGroup elementGroup = null;

		if (elementGroupVO != null) {

			elementGroup = new ElementGroup();

			if (elementGroupVO.getId() != null) {

				Key key = new Key(elementGroupVO.getId());

				if (key.getClazz() == ElementGroup.class) {

					elementGroup.setId(Long.valueOf(key.getId(1)));
				}
			}

			elementGroup.setAlias(elementGroupVO.getAlias());
			elementGroup.setDescription((elementGroupVO.getDescription()));

		}

		return elementGroup;
	}

	public static void assign(final ElementGroup elementGroup, ElementGroupVO elementGroupVO) {

		if (elementGroup != null && elementGroupVO != null) {

			BasicElementHelper.fillBasicProperties(elementGroupVO, elementGroup);

		}

	}

}
