package com.revenga.rits.back.virtual.equipment.manager.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.data.core.model.command.SetElementMeasuresChangeCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetIlluminationModeCommand;
import com.revenga.rits.back.data.core.model.command.virtual.SetIlluminationRegimeCommand;
import com.revenga.rits.back.data.core.util.ConfigProperty;

public class SetElementMeasuresChangeCommandHelper {

	protected static final String FILE_CONSTANTS = "constants.properties";

	public static final long ELEMENT_TYPE_PARAM_ILLUMINATION_MODE = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.type.tube.param.illumination_mode"));

	public static final long ELEMENT_TYPE_PARAM_ILLUMINATION_REGIME = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.type.tube.param.illumination_regime"));

	private SetElementMeasuresChangeCommandHelper() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static SetElementMeasuresChangeCommand get(final Long elementId, final Long elementTypeId,
			final Long elementTypeParamId, final String Value) {

		SetElementMeasuresChangeCommand setElementMeasuresChangeCommand = null;

		if (elementId != null && elementTypeId != null && elementTypeParamId != null) {

			setElementMeasuresChangeCommand = new SetElementMeasuresChangeCommand();

			List<ElementValue> elementValues = new ArrayList<>();

			ElementValue elementValue = new ElementValue();
			elementValue.setElementId(elementId);
			elementValue.setElementTypeId(elementTypeId);
			elementValue.setParamTypeId(ParamType.MEASURE);
			elementValue.setElementTypeParamId(elementTypeParamId);
			elementValue.setValue(Value);

			elementValues.add(elementValue);

			Element element = new Element();
			element.setElementTypeId(elementTypeId);
			element.setId(elementId);
			element.setElementValues(elementValues);

			setElementMeasuresChangeCommand.addElement(element);
		}

		return setElementMeasuresChangeCommand;
	}

	public static SetElementMeasuresChangeCommand get(final List<Element> elements) {

		SetElementMeasuresChangeCommand setElementMeasuresChangeCommand = null;

		if (CollectionUtils.isNotEmpty(elements)) {

			setElementMeasuresChangeCommand = new SetElementMeasuresChangeCommand();

			for (Element element : elements) {
				
				setElementMeasuresChangeCommand.addElement(element);
			}
		}

		return setElementMeasuresChangeCommand;
	}

	public static SetElementMeasuresChangeCommand get(final SetIlluminationModeCommand cmd) {

		return get(cmd.getElementId(), cmd.getElementTypeId(), ELEMENT_TYPE_PARAM_ILLUMINATION_MODE,
				cmd.getMode() != null ? cmd.getMode().toString() : null);
	}

	public static SetElementMeasuresChangeCommand get(final SetIlluminationRegimeCommand cmd) {

		return get(cmd.getElementId(), cmd.getElementTypeId(), ELEMENT_TYPE_PARAM_ILLUMINATION_REGIME,
				cmd.getRegime() != null ? cmd.getRegime().toString() : null);
	}

}
