package com.revenga.rits.back.front.core.model.helper;

import org.apache.commons.lang3.ObjectUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.DataType;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.front.core.model.vo.ElementTypeParamVO;

public class ElementTypeParamHelper {

	private ElementTypeParamHelper() {

		super();
	}

	public static ElementTypeParamVO get(ElementTypeParam elementTypeParam) {

		ElementTypeParamVO elementTypeParamVO = null;

		if (elementTypeParam != null) {

			elementTypeParamVO = new ElementTypeParamVO();

			assign(elementTypeParam, elementTypeParamVO);
		}

		return elementTypeParamVO;
	}

	public static ElementTypeParam get(ElementTypeParamVO elementTypeParamVO) {

		ElementTypeParam elementTypeParam = null;

		if (elementTypeParamVO != null) {

			elementTypeParam = new ElementTypeParam();

			if (elementTypeParamVO.getId() != null) {

				Key key = new Key(elementTypeParamVO.getId());

				if (key.getClazz() == ElementTypeParam.class) {

					elementTypeParam.setElementTypeId(Long.valueOf(key.getId(1)));
					elementTypeParam.setParamTypeId(Long.valueOf(key.getId(2)));
					elementTypeParam.setId(Long.valueOf(key.getId(3)));
				}
			}

			if (elementTypeParamVO.getParamTypeId() != null) {

				Key key = new Key(elementTypeParamVO.getParamTypeId());

				if (key.getClazz() == ParamType.class) {

					elementTypeParam.setParamTypeId(Long.valueOf(key.getId(1)));
				}
			}

			if (elementTypeParamVO.getDataTypeId() != null) {

				Key key = new Key(elementTypeParamVO.getDataTypeId());

				if (key.getClazz() == DataType.class) {

					elementTypeParam.setDataTypeId(Long.valueOf(key.getId(1)));
				}
			}

			elementTypeParam.setAlias(elementTypeParamVO.getAlias());
			elementTypeParam.setDescription((elementTypeParamVO.getDescription()));
			elementTypeParam.setDefaultValue(elementTypeParamVO.getDefaultValue());
			elementTypeParam.setDurable(elementTypeParamVO.getDurable());
		}

		return elementTypeParam;
	}

	public static void assign(final ElementTypeParam elementTypeParam, ElementTypeParamVO elementTypeParamVO) {

		if (elementTypeParam != null && elementTypeParamVO != null) {

			BasicElementHelper.fillBasicProperties(elementTypeParamVO, elementTypeParam);

			if (ObjectUtils.allNotNull(elementTypeParam.getElementTypeParamUnit())
					&& ObjectUtils.allNotNull(elementTypeParam.getElementTypeParamUnit().getLabelAlias())) {

				elementTypeParamVO.setLabelUnit(elementTypeParam.getElementTypeParamUnit().getLabelAlias());
			}

			if (elementTypeParam.getParamTypeId() != null) {

				ParamType paramType = new ParamType();
				paramType.setId(elementTypeParam.getParamTypeId());
				elementTypeParamVO.setParamTypeId(BasicElementHelper.getIdFromElement(paramType));
			}

			if (elementTypeParam.getDataTypeId() != null) {

				DataType dataType = new DataType();
				dataType.setId(elementTypeParam.getDataTypeId());
				elementTypeParamVO.setDataTypeId(BasicElementHelper.getIdFromElement(dataType));
			}

			elementTypeParamVO.setDefaultValue(elementTypeParam.getDefaultValue());
			elementTypeParamVO.setDurable(elementTypeParam.getDurable());
			elementTypeParamVO.setThreshold1(elementTypeParam.getThreshold1());
			elementTypeParamVO.setThreshold2(elementTypeParam.getThreshold2());
		}

		ModelVOHelper.translate(elementTypeParamVO);
	}

}
