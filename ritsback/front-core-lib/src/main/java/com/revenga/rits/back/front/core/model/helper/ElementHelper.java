package com.revenga.rits.back.front.core.model.helper;

import java.util.ArrayList;
import java.util.List;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.Location;
import com.revenga.rits.back.front.core.model.vo.ElementVO;
import com.revenga.rits.back.front.core.model.vo.ElementValueVO;

public class ElementHelper {

	private ElementHelper() {

		super();
	}

	public static ElementVO get(final Element element) {

		ElementVO elementVO = null;

		if (element != null) {

			elementVO = new ElementVO();

			assign(element, elementVO);
		}

		return elementVO;
	}

	public static Element get(final ElementVO elementVO) {

		Element element = null;

		if (elementVO != null) {

			element = new Element();
			
			element.setKeepValuesOnDisconnected(elementVO.getKeepValuesOnDisconnected());
			element.setAlias(elementVO.getAlias());
			element.setDescription((elementVO.getDescription()));

			if (elementVO.getElementTypeId() != null) {

				Key key = new Key(elementVO.getElementTypeId());

				if (key.getClazz() == ElementType.class) {

					element.setElementTypeId(Long.valueOf(key.getId(1)));
				}
			}
			
			if (elementVO.getLocationId() != null) {

				Key key = new Key(elementVO.getLocationId());

				if (key.getClazz() == Location.class) {

					element.setLocationId(Long.valueOf(key.getId(1)));
				}
			}
			
			if (elementVO.getLocation() != null) {

				Location location = LocationHelper.get(elementVO.getLocation());

				if (location != null) {

					if (elementVO.getLocationId() == null) {

						element.setLocationId(location.getId());
					}
					element.setLocation(location);
				}
			}


			if (elementVO.getElementType() != null) {

				ElementType elementType = ElementTypeHelper.get(elementVO.getElementType());

				if (elementType != null) {

					if (elementVO.getElementTypeId() == null) {

						element.setElementTypeId(elementType.getId());
					}
					element.setElementType(elementType);
				}
			}

			if (elementVO.getId() != null) {

				Key key = new Key(elementVO.getId());

				if (key.getClazz() == Element.class) {

					element.setElementTypeId(Long.valueOf(key.getId(1)));
					element.setId(Long.valueOf(key.getId(2)));

				}
			}

			if (elementVO.getElementValues() != null) {

				List<ElementValue> elementValues = new ArrayList<>();

				for (ElementValueVO elementValueVO : elementVO.getElementValues()) {

					ElementValue elementValue = ElementValueHelper.get(elementValueVO);

					elementValues.add(elementValue);
				}

				element.setElementValues(elementValues);
			}

			if (elementVO.getParentIds() != null) {

				Key key = null;

				List<ElementHierarchy> parentElementHierarchies = new ArrayList<>();

				for (String elementParentId : elementVO.getParentIds()) {

					key = new Key(elementParentId);

					if (key.getClazz() == Element.class) {

						ElementHierarchy elementParentHierarchy = new ElementHierarchy();
						elementParentHierarchy.setParentElementTypeId(Long.valueOf(key.getId(1)));
						elementParentHierarchy.setParentElementId(Long.valueOf(key.getId(2)));
						elementParentHierarchy.setChildElementId(element.getId());
						elementParentHierarchy.setChildElementTypeId(element.getElementTypeId());
						parentElementHierarchies.add(elementParentHierarchy);
					}
				}
				element.setParentElementHierarchies(parentElementHierarchies);
			}

		}

		return element;
	}

	public static void assign(final Element element, ElementVO elementVO) {

		if (element != null && elementVO != null) {

			BasicElementHelper.fillBasicProperties(elementVO, element);

			elementVO.setState(ElementTypeState.STATE_UNKNOWN);
			elementVO.setKeepValuesOnDisconnected(element.getKeepValuesOnDisconnected());

			if (element.getLocationId() != null) {

				Location location = new Location();
				location.setId(element.getLocationId());
				elementVO.setLocationId(BasicElementHelper.getIdFromElement(location));
			}
			
			if (element.getElementTypeId() != null) {

				ElementType elementType = new ElementType();
				elementType.setId(element.getElementTypeId());
				elementVO.setElementTypeId(BasicElementHelper.getIdFromElement(elementType));
			}

			elementVO.setElementType(ElementTypeHelper.get(element.getElementType()));

			if (element.getElementValues() != null) {

				List<ElementValueVO> elementValuesVO = new ArrayList<>();

				for (ElementValue elementValue : element.getElementValues()) {

					if (elementValue.getElementId() != null) {

						elementValuesVO.add(ElementValueHelper.get(elementValue));
					}
				}

				elementVO.setElementValues(elementValuesVO);
			}

		}

		ModelVOHelper.translate(elementVO);
	}
}
