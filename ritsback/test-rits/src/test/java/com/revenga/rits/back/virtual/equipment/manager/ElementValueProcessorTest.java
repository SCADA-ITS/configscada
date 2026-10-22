package com.revenga.rits.back.virtual.equipment.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.command.virtual.SetElementValuesToVentilationCommand;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.entities.lib.repository.CacheManager;
import com.revenga.rits.back.entities.lib.repository.ElementStateRepository;
import com.revenga.rits.back.entities.lib.repository.ElementValueRepository;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ElementValueProcessorTest {

	protected static final String FILE_CONSTANTS = "constants.properties";

	// VENTILATION
	public final long ELEMENT_TYPE_PARAM_VENTILATION_MODE = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.type.tube.param.ventilation_mode"));

	public final long ELEMENT_TYPE_PARAM_VENTILATION_REGIME = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.type.tube.param.ventilation_regime"));

	public final long ELEMENT_TYPE_PARAM_VENTILATION_REGIME_PROPOSED = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(FILE_CONSTANTS, "element.type.tube.param.ventilation_regime_propoused"));

	public final String VENTILATION_MODE_AUTO = (ConfigProperty.getInstance().getProperty(FILE_CONSTANTS,
			"element.value.ventilation_mode.auto"));

	private ElementValueRepository elementValueRepository;
	private ElementStateRepository elementStateRepository;

	List<ElementValue> elementValues;
	ElementValue coConcentration;
	ElementValue noConcentration;
	ElementValue kConcentration;

	private void sendElementValues() {

		SetElementValuesToVentilationCommand setElementValuesToVentilationCommand = new SetElementValuesToVentilationCommand();

		if (CollectionUtils.isNotEmpty(elementValues)) {

			for (ElementValue elementValue : elementValues) {
				setElementValuesToVentilationCommand.addElementValue(elementValue);
			}
		}

		CacheManager.getInstance().sendCommand(setElementValuesToVentilationCommand, Command.DEFAULT_EXPIRE_TIME);
	}

	public ElementValueProcessorTest() {

		elementValueRepository = new ElementValueRepository(false);
		elementStateRepository = new ElementStateRepository(false);

		elementValues = new ArrayList<>();
		coConcentration = new ElementValue();
		noConcentration = new ElementValue();
		kConcentration = new ElementValue();

		coConcentration.setElementId(1L);
		coConcentration.setElementTypeId(19L);
		coConcentration.setElementTypeParamId(1L);
		coConcentration.setParamTypeId(2L);
		coConcentration.setValue("0");
		elementValues.add(coConcentration);

		noConcentration.setElementId(1L);
		noConcentration.setElementTypeId(20L);
		noConcentration.setElementTypeParamId(1L);
		noConcentration.setParamTypeId(2L);
		noConcentration.setValue("0.0");
		elementValues.add(noConcentration);

		kConcentration.setElementId(1L);
		kConcentration.setElementTypeId(21L);
		kConcentration.setElementTypeParamId(1L);
		kConcentration.setParamTypeId(2L);
		kConcentration.setValue("0.0");
		elementValues.add(kConcentration);
	}

	@Test
	public void test1() throws InterruptedException {

		CheckVentilationUp();
		CheckTubeModeAuto();
		CheckRegimen0();
		
		coConcentration.setValue("30");
		sendElementValues();
		coConcentration.setValue("72");
		sendElementValues();
		Thread.sleep(61000L);
		CheckRegimeBajo();

		coConcentration.setValue("50");
		sendElementValues();
		coConcentration.setValue("30");
		sendElementValues();
		coConcentration.setValue("148");
		sendElementValues();
		Thread.sleep(61000L);
		CheckRegimeMedio();

		coConcentration.setValue("121");
		sendElementValues();
		Thread.sleep(61000L);
		CheckRegimeAlto();
		
		kConcentration.setValue("0.01");
		sendElementValues();
		kConcentration.setValue("0.0142");
		sendElementValues();
		Thread.sleep(61000L);
		CheckRegimeEmergencia();
	}

	private void CheckVentilationUp() throws InterruptedException {

		System.out.printf("\nCheckVentilationUp...");

		List<ElementState> elementStates = elementStateRepository
				.getsByElementType(ElementType.ELEMENT_TYPE_VENTILATION, false);

		if (CollectionUtils.isNotEmpty(elementStates)) {

			for (ElementState elementState : elementStates) {
				assertEquals(elementState.getStateId(), ElementTypeState.STATE_UP);
			}
		}
	}

	private void CheckTubeModeAuto() throws InterruptedException {

		System.out.printf("\nCheckTubeModeAuto...");

		Thread.sleep(5000L);

		List<ElementValue> elementValues = elementValueRepository.getByElementTypeParam(ElementType.ELEMENT_TYPE_TUBE,
				ELEMENT_TYPE_PARAM_VENTILATION_MODE, ParamType.MEASURE, false);

		if (CollectionUtils.isNotEmpty(elementValues)) {

			for (ElementValue elementValue : elementValues) {
				assertEquals(elementValue.getValue(), VENTILATION_MODE_AUTO);
			}
		}
	}

	private void CheckRegimen0() throws InterruptedException {

		System.out.printf("\nCheckRegimen0...");

		ElementValue elementValue = elementValueRepository.get(ElementType.ELEMENT_TYPE_TUBE, 1L,
				ELEMENT_TYPE_PARAM_VENTILATION_REGIME, ParamType.MEASURE, false);

		assertEquals(elementValue.getValue(), "1");

		elementValue = elementValueRepository.get(ElementType.ELEMENT_TYPE_TUBE, 1L,
				ELEMENT_TYPE_PARAM_VENTILATION_REGIME_PROPOSED, ParamType.MEASURE, false);

		assertEquals(elementValue.getValue(), "1");
	}

	
	private void CheckRegimeBajo() throws InterruptedException {

		System.out.printf("\nCheckRegimenBajo...");

		ElementValue elementValue = elementValueRepository.get(ElementType.ELEMENT_TYPE_TUBE, 1L,
				ELEMENT_TYPE_PARAM_VENTILATION_REGIME, ParamType.MEASURE, false);

		assertEquals(elementValue.getValue(), "2");

		elementValue = elementValueRepository.get(ElementType.ELEMENT_TYPE_TUBE, 1L,
				ELEMENT_TYPE_PARAM_VENTILATION_REGIME_PROPOSED, ParamType.MEASURE, false);

		assertEquals(elementValue.getValue(), "2");
	}
	
	private void CheckRegimeMedio() throws InterruptedException {

		System.out.printf("\nCheckRegimenMedio...");

		ElementValue elementValue = elementValueRepository.get(ElementType.ELEMENT_TYPE_TUBE, 1L,
				ELEMENT_TYPE_PARAM_VENTILATION_REGIME, ParamType.MEASURE, false);

		assertEquals(elementValue.getValue(), "3");

		elementValue = elementValueRepository.get(ElementType.ELEMENT_TYPE_TUBE, 1L,
				ELEMENT_TYPE_PARAM_VENTILATION_REGIME_PROPOSED, ParamType.MEASURE, false);

		assertEquals(elementValue.getValue(), "3");
	}
	
	private void CheckRegimeAlto() throws InterruptedException {

		System.out.printf("\nCheckRegimenAlto...");

		ElementValue elementValue = elementValueRepository.get(ElementType.ELEMENT_TYPE_TUBE, 1L,
				ELEMENT_TYPE_PARAM_VENTILATION_REGIME, ParamType.MEASURE, false);

		assertEquals(elementValue.getValue(), "4");

		elementValue = elementValueRepository.get(ElementType.ELEMENT_TYPE_TUBE, 1L,
				ELEMENT_TYPE_PARAM_VENTILATION_REGIME_PROPOSED, ParamType.MEASURE, false);

		assertEquals(elementValue.getValue(), "4");
	}
	
	private void CheckRegimeEmergencia() throws InterruptedException {

		System.out.printf("\nCheckRegimenEmergencia...");

		ElementValue elementValue = elementValueRepository.get(ElementType.ELEMENT_TYPE_TUBE, 1L,
				ELEMENT_TYPE_PARAM_VENTILATION_REGIME, ParamType.MEASURE, false);

		assertEquals(elementValue.getValue(), "5");

		elementValue = elementValueRepository.get(ElementType.ELEMENT_TYPE_TUBE, 1L,
				ELEMENT_TYPE_PARAM_VENTILATION_REGIME_PROPOSED, ParamType.MEASURE, false);

		assertEquals(elementValue.getValue(), "5");
	}

}
