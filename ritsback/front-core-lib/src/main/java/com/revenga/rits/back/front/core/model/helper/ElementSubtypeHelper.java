package com.revenga.rits.back.front.core.model.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ElementSubtype;
import com.revenga.rits.back.data.core.model.ElementSubtypeActiveParam;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.front.core.model.vo.ElementSubtypeVO;
import com.revenga.rits.back.front.core.model.vo.ElementTypeParamVO;

public class ElementSubtypeHelper {

	private ElementSubtypeHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static ElementSubtypeVO get(final ElementSubtype elementSubtype) {

		ElementSubtypeVO elementSubtypeVO = null;

		if (elementSubtype != null) {

			elementSubtypeVO = new ElementSubtypeVO();

			assign(elementSubtype, elementSubtypeVO);
		}

		return elementSubtypeVO;
	}

	public static ElementSubtype get(final ElementSubtypeVO elementSubtypeVO) {

		ElementSubtype elementSubtype = null;

		if (elementSubtypeVO != null) {

			elementSubtype = new ElementSubtype();

			elementSubtype.setAlias(elementSubtypeVO.getAlias());
			elementSubtype.setDescription((elementSubtypeVO.getDescription()));

			if (elementSubtypeVO.getId() != null) {

				Key key = new Key(elementSubtypeVO.getId());

				if (key.getClazz() == ElementSubtype.class) {

					elementSubtype.setId(Long.valueOf(key.getId(1)));
					elementSubtype.setElementTypeId(Long.valueOf(key.getId(2)));
				}
			}

		}

		return elementSubtype;
	}

	public static void assign(final ElementSubtype elementSubtype, ElementSubtypeVO elementSubtypeVO) {

		if (ObjectUtils.allNotNull(elementSubtype, elementSubtypeVO)) {

			BasicElementHelper.fillBasicProperties(elementSubtypeVO, elementSubtype);

			if (elementSubtype.getElementSubtypeActiveParams() != null) {

				List<ElementTypeParamVO> elementTypeParamVO = new ArrayList<>();

				for (ElementSubtypeActiveParam elementSubtypeActiveParam : elementSubtype
						.getElementSubtypeActiveParams()) {

					if (elementSubtypeActiveParam.getElementSubtypeId() != null) {

						ElementTypeParam elementTypeParam = new ElementTypeParam();

						elementTypeParam.setElementTypeId(elementSubtypeActiveParam.getElementTypeId());
						elementTypeParam.setParamTypeId(elementSubtypeActiveParam.getParamTypeId());
						elementTypeParam.setId(elementSubtypeActiveParam.getId());

						elementTypeParamVO.add(ElementTypeParamHelper.get(elementTypeParam));
					}
				}

				elementSubtypeVO.setElementSubtypeActiveParams(elementTypeParamVO);
			}

		}
	}

}
