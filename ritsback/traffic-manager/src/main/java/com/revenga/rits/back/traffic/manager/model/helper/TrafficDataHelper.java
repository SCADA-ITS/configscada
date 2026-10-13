package com.revenga.rits.back.traffic.manager.model.helper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.command.SetElementMeasuresChangeCommand;
import com.revenga.rits.back.data.core.model.command.SetHistoricElementCommand;
import com.revenga.rits.back.traffic.manager.annotation.AElementTypeParam;
import com.revenga.rits.back.traffic.manager.service.EntitiesManager;

import lombok.extern.log4j.Log4j2;;

@Log4j2
public class TrafficDataHelper {

	private TrafficDataHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	private static void setElementValues(final List<ElementValue> values, Object dstObj) {

		if (!CollectionUtils.isEmpty(values)) {

			EntitiesManager.getInstance().setId(dstObj, values.get(0).getElementId());

			for (ElementValue value : values) {

				EntitiesManager.getInstance().setElementValue(dstObj, value);
			}
		}
	}

	public static Object get(Class<?> clazz, final List<ElementValue> configValues,
			final List<ElementValue> measuresValues) {

		Object object = null;

		try {
			object = clazz.getDeclaredConstructor().newInstance();

			setElementValues(configValues, object);
			setElementValues(measuresValues, object);

		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {

			log.error(e.getMessage());
		}

		return object;
	}

	public static List<ElementValue> get(final Object objSrc, Boolean getMeasures, Boolean getConfig) {

		List<ElementValue> elementValues = null;

		for (Field field : objSrc.getClass().getDeclaredFields()) {

			if (field.isAnnotationPresent(AElementTypeParam.class)) {

				AElementTypeParam aElementTypeParam = field.getAnnotation(AElementTypeParam.class);

				if ((aElementTypeParam.paramType().equals(AElementTypeParam.paramType.MEASURE) && getMeasures)
						|| (aElementTypeParam.paramType().equals(AElementTypeParam.paramType.CONFIG) && getConfig)) {

					for (int i = 0; i < aElementTypeParam.references().length; i++) {

						Pair<Field, Integer> fieldPair = null;

						if (aElementTypeParam.references().length == 1) {
							fieldPair = new MutablePair<>(field, null);
						} else {
							fieldPair = new MutablePair<>(field, i);
						}

						ElementValue elementValue = EntitiesManager.getInstance().getElementValue(objSrc, fieldPair);

						if (elementValue != null) {

							if (elementValues == null) {

								elementValues = new ArrayList<>();
							}
							elementValues.add(elementValue);
						}
					}

				}
			}
		}
		return elementValues;
	}

	public static Element get(final Object object) {

		Element element = null;

		if (object != null) {

			List<ElementValue> elementValues = get(object, true, false);

			if (!CollectionUtils.isEmpty(elementValues)) {

				element = new Element();
				element.setElementTypeId(elementValues.get(0).getElementTypeId());
				element.setId(elementValues.get(0).getElementId());
				element.setElementValues(elementValues);
			}
		}

		return element;
	}

	public static SetElementMeasuresChangeCommand getSetElementMeasuresChangeCommand(final List<?> objects) {

		SetElementMeasuresChangeCommand setElementMeasuresChangeCommand = null;

		if (!CollectionUtils.isEmpty(objects)) {

			for (Object object : objects) {

				Element element = get(object);

				if (element != null) {

					if (setElementMeasuresChangeCommand == null) {

						setElementMeasuresChangeCommand = new SetElementMeasuresChangeCommand();
					}
					setElementMeasuresChangeCommand.addElement(element);
				}
			}
		}
		return setElementMeasuresChangeCommand;
	}

	public static SetHistoricElementCommand getSetHistoricElementCommandfinal(List<?> objects) {

		SetHistoricElementCommand setHistoricElementCommand = null;

		if (!CollectionUtils.isEmpty(objects)) {

			for (Object object : objects) {

				Element element = get(object);

				if (element != null) {

					if (setHistoricElementCommand == null) {

						setHistoricElementCommand = new SetHistoricElementCommand();
					}
					setHistoricElementCommand.addElement(element);
				}
			}

		}
		return setHistoricElementCommand;
	}

}
