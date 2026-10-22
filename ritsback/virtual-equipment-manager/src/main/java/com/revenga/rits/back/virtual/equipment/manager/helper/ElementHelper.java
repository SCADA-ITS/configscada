package com.revenga.rits.back.virtual.equipment.manager.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;

public class ElementHelper {

	private ElementHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static List<Element> getElements(List<String> elementsId) {

		List<Element> elements = null;

		if (CollectionUtils.isNotEmpty(elementsId)) {

			elements = new ArrayList<>();

			for (String elementId : elementsId) {

				Key key = new Key(elementId);

				if (key.getClazz() == Element.class) {

					Element element = new Element();
					element.setPks(key.getIds());

					elements.add(element);
				}
			}
		}
		return elements;
	}
	
	public static List<ElementValue> getElementValues(List<String> elementValuesId) {

		List<ElementValue> elementValues = null;

		if (CollectionUtils.isNotEmpty(elementValuesId)) {

			elementValues = new ArrayList<>();

			for (String elementValueId : elementValuesId) {

				Key key = new Key(elementValueId);

				if (key.getClazz() == ElementValue.class) {

					ElementValue elementValue = new ElementValue();
					elementValue.setPks(key.getIds());

					elementValues.add(elementValue);
				}
			}
		}
		return elementValues;
	}
}
