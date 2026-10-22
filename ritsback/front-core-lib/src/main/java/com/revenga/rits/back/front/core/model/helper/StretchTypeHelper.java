package com.revenga.rits.back.front.core.model.helper;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.StretchType;
import com.revenga.rits.back.front.core.model.vo.StretchTypeVO;

public class StretchTypeHelper {

	private StretchTypeHelper() {

		super();
	}

	public static StretchTypeVO get(StretchType stretchType) {

		StretchTypeVO stretchTypeVO = null;

		if (stretchType != null) {

			stretchTypeVO = new StretchTypeVO();

			assign(stretchType, stretchTypeVO);
		}

		return stretchTypeVO;
	}

	public static StretchType get(StretchTypeVO stretchTypeVO) {

		StretchType stretchType = null;

		if (stretchTypeVO != null) {

			stretchType = new StretchType();

			if (stretchTypeVO.getId() != null) {

				Key key = new Key(stretchTypeVO.getId());

				if (key.getClazz() == StretchType.class) {

					stretchType.setId(Long.valueOf(key.getId(1)));
				}
			}

			stretchType.setAlias(stretchTypeVO.getAlias());
			stretchType.setDescription((stretchTypeVO.getDescription()));

		}

		return stretchType;
	}

	public static void assign(final StretchType stretchType, StretchTypeVO stretchTypeVO) {

		if (stretchType != null && stretchTypeVO != null) {

			BasicElementHelper.fillBasicProperties(stretchTypeVO, stretchType);

		}
	}

}
