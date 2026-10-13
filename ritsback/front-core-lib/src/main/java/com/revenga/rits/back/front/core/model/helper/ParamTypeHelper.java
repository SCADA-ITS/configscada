package com.revenga.rits.back.front.core.model.helper;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.front.core.model.vo.ParamTypeVO;

public class ParamTypeHelper {

	private ParamTypeHelper() {

		super();
	}

	public static ParamTypeVO get(ParamType paramType) {

		ParamTypeVO paramTypeVO = null;

		if (paramType != null) {

			paramTypeVO = new ParamTypeVO();

			assign(paramType, paramTypeVO);
		}

		return paramTypeVO;
	}

	public static ParamType get(ParamTypeVO paramTypeVO) {

		ParamType paramType = null;

		if (paramTypeVO != null) {

			paramType = new ParamType();

			if (paramTypeVO.getId() != null) {

				Key key = new Key(paramTypeVO.getId());

				if (key.getClazz() == ParamType.class) {

					paramType.setId(Long.valueOf(key.getId(1)));
				}
			}

			paramType.setAlias(paramTypeVO.getAlias());
			paramType.setDescription((paramTypeVO.getDescription()));

		}

		return paramType;
	}

	public static void assign(final ParamType paramType, ParamTypeVO paramTypeVO) {

		if (paramType != null && paramTypeVO != null) {

			BasicElementHelper.fillBasicProperties(paramTypeVO, paramType);

		}
	}

}
