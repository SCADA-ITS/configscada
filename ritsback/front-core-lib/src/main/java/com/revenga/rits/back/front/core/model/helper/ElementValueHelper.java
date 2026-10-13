package com.revenga.rits.back.front.core.model.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.DataType;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ElementValueState;
import com.revenga.rits.back.front.core.model.vo.ElementVO;
import com.revenga.rits.back.front.core.model.vo.ElementValueStateVO;
import com.revenga.rits.back.front.core.model.vo.ElementValueVO;

public class ElementValueHelper {

	private ElementValueHelper() {
		super();
	}

	public static ElementValueVO get(final ElementValue elementValue) {

		ElementValueVO elementValueVO = null;

		if (elementValue != null) {

			elementValueVO = new ElementValueVO();

			assign(elementValue, elementValueVO);
		}

		return elementValueVO;
	}

	public static ElementValue get(final ElementValueVO elementValueVO) {

		ElementValue elementValue = null;

		if (elementValueVO != null) {

			elementValue = new ElementValue();

			if (elementValueVO.getElementTypeParamId() != null) {

				Key key = new Key(elementValueVO.getElementTypeParamId());

				if (key.getClazz() == ElementTypeParam.class) {

					elementValue.setElementTypeId(Long.valueOf(key.getId(1)));
					elementValue.setParamTypeId(Long.valueOf(key.getId(2)));
					elementValue.setElementTypeParamId(Long.valueOf(key.getId(3)));
				}
			}

			if (elementValueVO.getElementTypeParam() != null) {

				ElementTypeParam elementTypeParam = ElementTypeParamHelper.get(elementValueVO.getElementTypeParam());

				if (elementTypeParam != null) {

					if (elementValueVO.getElementTypeParamId() == null) {

						elementValue.setElementTypeId(elementTypeParam.getElementTypeId());
						elementValue.setParamTypeId(elementTypeParam.getParamTypeId());
					}
					elementValue.setElementTypeParam(elementTypeParam);
				}
			}

			if (elementValueVO.getId() != null) {

				Key key = new Key(elementValueVO.getId());

				if (key.getClazz() == ElementValue.class) {

					elementValue.setElementTypeId(Long.valueOf(key.getId(1)));
					elementValue.setElementId(Long.valueOf(key.getId(2)));
					elementValue.setElementTypeParamId(Long.valueOf(key.getId(3)));
					elementValue.setParamTypeId(Long.valueOf(key.getId(4)));
				}
			}

//			if (elementValueVO.getState() != null) {
//				
//				elementValue.setElementValueStateId(elementValueVO.getState());
//			}

			if (elementValueVO.getElementValueState() != null) {

				elementValue.setElementValueStateId(
						ElementValueStateHelper.get(elementValueVO.getElementValueState()).getId());
			}

			elementValue.setValue(elementValueVO.getValue());

		}

		return elementValue;
	}

	public static void assign(final ElementValue elementValue, ElementValueVO elementValueVO) {

		if (elementValue != null && elementValueVO != null) {

			BasicElementHelper.fillBasicProperties(elementValueVO, elementValue);

			elementValueVO.setValue(getValue(elementValue));

			elementValueVO.setElementTypeParam(ElementTypeParamHelper.get(elementValue.getElementTypeParam()));

			if (elementValue.getElementValueStateId() != null) {

				elementValueVO.setState(elementValue.getElementValueStateId());

				ElementValueStateVO elementValueStateVO = new ElementValueStateVO();
				elementValueStateVO.setId((new Key(ElementValueState.class,
						new String[] { String.valueOf(elementValue.getElementValueStateId()) })).getSimpleValue());

				elementValueVO.setElementValueState(elementValueStateVO);
			}
		}

	}

	public static ElementVO getElementVO(ElementValue elementValue) {

		Element element = new Element();
		ElementVO elementVO = new ElementVO();

		element.setElementTypeId(elementValue.getElementTypeId());
		element.setId(elementValue.getElementId());

		elementVO.setId(BasicElementHelper.getIdFromElement(element));

		return elementVO;
	}

	public static List<ElementValueVO> gets(final List<ElementValue> elementValues) {

		List<ElementValueVO> elementValuesVO = null;

		if (!CollectionUtils.isEmpty(elementValues)) {

			elementValuesVO = new ArrayList<>();

			for (ElementValue e : elementValues) {

				elementValuesVO.add(ElementValueHelper.get(e));
			}

		}

		return elementValuesVO;
	}

	private static String getValue(ElementValue elementValue) {

		String value = elementValue.getValue();

		if (!StringUtils.isEmpty(value)) {

			if (elementValue.getElementTypeParam() == null) {

				return value;
			} else if (elementValue.getElementTypeParam().getDataTypeId() == DataType.DATA_TYPE_ALPHANUMERIC) {

				return value;
			} else if (elementValue.getElementTypeParam().getDataTypeId() == DataType.DATA_TYPE_INTEGER) {

				if (value.contains(".")) {

					Double d = Double.valueOf(value);

					return String.valueOf(d.intValue());
				} else {

					return value;
				}
			} else if (elementValue.getElementTypeParam().getDataTypeId() == DataType.DATA_TYPE_TIMESTAMP) {

				if (value.contains(".")) {

					Double d = Double.valueOf(value);

					return String.valueOf(d.longValue());
				} else {

					return value;
				}

			} else if (elementValue.getElementTypeParam().getDataTypeId() == DataType.DATA_TYPE_REAL) {

				if (value.contains(".")) {

					Double d = Double.valueOf(value);

					return String.valueOf(d);
				}
			} else if (elementValue.getElementTypeParam().getDataTypeId() == DataType.DATA_TYPE_BOOLEAN) {

				return String.valueOf(StringUtils.equalsAny(elementValue.getValue(), "1", "true"));
			}
		}

		return null;
	}

	public static void update(final ElementValueVO elementValueVO, ElementValue elementValue) {

		if (elementValueVO != null && elementValue != null && elementValueVO.getValue() != null) {

			elementValue.setValue(elementValueVO.getValue());
		}

	}
}
