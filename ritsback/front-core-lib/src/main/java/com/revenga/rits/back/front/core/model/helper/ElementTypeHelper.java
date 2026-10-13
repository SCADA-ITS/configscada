package com.revenga.rits.back.front.core.model.helper;

import java.util.ArrayList;
import java.util.List;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.ElementGroup;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.front.core.model.vo.ElementTypeParamVO;
import com.revenga.rits.back.front.core.model.vo.ElementTypeStateVO;
import com.revenga.rits.back.front.core.model.vo.ElementTypeVO;

public class ElementTypeHelper {

	private ElementTypeHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static ElementTypeVO get(final ElementType elementType) {

		ElementTypeVO elementTypeVO = null;

		if (elementType != null) {

			elementTypeVO = new ElementTypeVO();

			assign(elementType, elementTypeVO);
		}

		return elementTypeVO;
	}

	public static ElementType get(final ElementTypeVO elementTypeVO) {

		ElementType elementType = null;

		if (elementTypeVO != null) {

			elementType = new ElementType();

			elementType.setAlias(elementTypeVO.getAlias());
			elementType.setDescription((elementTypeVO.getDescription()));
			elementType.setIsLogic(elementTypeVO.getIsLogic());

			if (elementTypeVO.getId() != null) {

				Key key = new Key(elementTypeVO.getId());

				if (key.getClazz() == ElementType.class) {

					elementType.setId(Long.valueOf(key.getId(1)));

				}
			}

			if (elementTypeVO.getInitialStateId() != null) {

				Key key = new Key(elementTypeVO.getInitialStateId());

				if (key.getClazz() == ElementTypeState.class) {

					elementType.setInitialElementTypeStateId(Long.valueOf(key.getId(1)));
				}
			}

			if (elementTypeVO.getElementGroup() != null) {

				ElementGroup elementGroup = ElementGroupHelper.get(elementTypeVO.getElementGroup());

				if (elementGroup != null) {

					elementType.setElementGroup(elementGroup);
				}
			}

			if (elementTypeVO.getElementTypeParams() != null) {

				List<ElementTypeParam> elementTypeParams = new ArrayList<>();

				for (ElementTypeParamVO elementTypeParamVO : elementTypeVO.getElementTypeParams()) {

					ElementTypeParam elementTypeParam = ElementTypeParamHelper.get(elementTypeParamVO);

					elementTypeParams.add(elementTypeParam);
				}

				elementType.setElementTypeParams(elementTypeParams);
			}

			if (elementTypeVO.getElementTypeStates() != null) {

				List<ElementTypeState> elementTypeStates = new ArrayList<>();

				for (ElementTypeStateVO elementTypeStateVO : elementTypeVO.getElementTypeStates()) {

					ElementTypeState elementTypeState = ElementTypeStateHelper.get(elementTypeStateVO);

					elementTypeStates.add(elementTypeState);
				}

				elementType.setElementTypeStates(elementTypeStates);
			}

		}

		return elementType;
	}

	public static void assign(final ElementType elementType, ElementTypeVO elementTypeVO) {

		if (elementType != null && elementTypeVO != null) {

			BasicElementHelper.fillBasicProperties(elementTypeVO, elementType);

			elementTypeVO.setElementGroup(ElementGroupHelper.get(elementType.getElementGroup()));
			elementTypeVO.setIsLogic(elementType.getIsLogic());

			if (elementType.getInitialElementTypeStateId() != null) {

				ElementTypeState elementTypeState = new ElementTypeState();
				elementTypeState.setId(elementType.getInitialElementTypeStateId());
				elementTypeVO.setInitialStateId(BasicElementHelper.getIdFromElement(elementTypeState));
			}

			if (elementType.getElementTypeParams() != null) {

				List<ElementTypeParamVO> elementTypeParamsVO = new ArrayList<>();

				for (ElementTypeParam elementTypeParam : elementType.getElementTypeParams()) {

					if (elementTypeParam.getElementTypeId() != null) {

						elementTypeParamsVO.add(ElementTypeParamHelper.get(elementTypeParam));
					}
				}

				elementTypeVO.setElementTypeParams(elementTypeParamsVO);
			}

		}
	}
}
