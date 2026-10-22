package com.revenga.rits.back.virtual.equipment.manager;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementTypeState;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ParamType;
import com.revenga.rits.back.data.core.model.command.Command;
import com.revenga.rits.back.data.core.model.helper.command.SetElementStatesToVentilationCommandHelper;
import com.revenga.rits.back.data.core.model.helper.command.SetFansStartCommandHelper;
import com.revenga.rits.back.data.core.model.helper.command.SetFansStartReverseCommandHelper;
import com.revenga.rits.back.data.core.model.helper.command.SetFansStopCommandHelper;
import com.revenga.rits.back.data.core.model.helper.command.SetFireAlarmCommandHelper;
import com.revenga.rits.back.data.core.model.helper.command.SetVentilationModeCommandHelper;
import com.revenga.rits.back.data.core.model.helper.command.SetVentilationRegimeCommandHelper;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.entities.lib.repository.CacheManager;
import com.revenga.rits.back.entities.lib.repository.ElementRepository;
import com.revenga.rits.back.entities.lib.repository.ElementStateRepository;
import com.revenga.rits.back.entities.lib.repository.ElementValueRepository;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Ventilation {

	protected static final String FILE_CONSTANTS = "constants.properties";

	// VENTILATION
	public final long ELEMENT_TYPE_PARAM_VENTILATION_REGIMES_CONFIGS = Long.parseLong(ConfigProperty.getInstance()
			.getProperty(FILE_CONSTANTS, "element.type.tube.param.ventilation_regimes_configs"));

	public final long ELEMENT_TYPE_PARAM_VENTILATION_MODE = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.type.tube.param.ventilation_mode"));

	public final long ELEMENT_TYPE_PARAM_VENTILATION_REGIME = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.type.tube.param.ventilation_regime"));

	public final String VENTILATION_MODE_MANUAL = (ConfigProperty.getInstance().getProperty(FILE_CONSTANTS,
			"element.value.ventilation_mode.manual"));

	public final String VENTILATION_MODE_AUTO = (ConfigProperty.getInstance().getProperty(FILE_CONSTANTS,
			"element.value.ventilation_mode.auto"));

	public final String VENTILATION_MODE_AUTO_MAINTENACE = (ConfigProperty.getInstance().getProperty(FILE_CONSTANTS,
			"element.value.ventilation_mode.auto_maintenace"));

	// FAN
	public final long ELEMENT_TYPE_PARAM_DELAY_BEFORE_START = Long.parseLong(
			ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.type.fan.param.delay_before_start"));

	public final long ELEMENT_TYPE_PARAM_FAN_STATE = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.type.fan.param.fan_state"));

	public final long FAN_STATE_VALUE_STOP = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.value.fan_state.stop"));

	public final long FAN_STATE_VALUE_START = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.value.fan_state.start"));

	public final long FAN_STATE_VALUE_REVERSE = Long
			.parseLong(ConfigProperty.getInstance().getProperty(FILE_CONSTANTS, "element.value.fan_state.reverse"));

	public final int MAX_N_FAN = 8;

	private ElementValueRepository elementValueRepository;
	private ElementRepository elementRepository;
	private ElementStateRepository elementStateRepository;

	private Command sendCommand(Command command) {

		return CacheManager.getInstance().sendCommand(command, Command.DEFAULT_EXPIRE_TIME);
	}

	public Ventilation() {
		elementValueRepository = new ElementValueRepository(false);
		elementRepository = new ElementRepository(false);
		elementStateRepository = new ElementStateRepository(false);
	}

	
	
	@Test
	public void test1() throws InterruptedException {
		
		CheckVentilationInit();

		CheckTubeModeAuto();
		SetRegimen2();
		
		StopVentilation();
		StartVentilation();
		
		SetManualMode();
		SetRegimen2();
		CheckTubeRegimen2(41000L);
		
		StopVentilation();
		StartVentilation();
		
		StartFans();
		StartReverseFans();
		CheckTubeRegimenNull();
		
		SetRegimen2();
		CheckTubeRegimen2(41000L); 
		
		SetAutoMode();
		StopFansInAutoMode();
		CheckTubeRegimen2(0L);
		
		StopVentilation();
		AlarmVentilation();
		Extraction();
		CheckTubeRegimenNull();
		
		StartFansInAutoMode();
		SetManualMode();
		StartFans();
		
		StopVentilation();
		StartVentilation();
	}
	
	private void CheckVentilationInit() throws InterruptedException {

		List<Element> elements = elementRepository.gets(ElementType.ELEMENT_TYPE_VENTILATION, false);

		if (CollectionUtils.isNotEmpty(elements)) {

			for (Element element : elements) {

				Command cmd = SetElementStatesToVentilationCommandHelper.get(ElementType.ELEMENT_TYPE_VENTILATION,
						element.getId(), ElementTypeState.STATE_UP);
				sendCommand(cmd);
			}
		}

		System.out.printf("\nCheckVentilationInit...");
		Thread.sleep(5000L);

		List<ElementState> elementStates = elementStateRepository
				.getsByElementType(ElementType.ELEMENT_TYPE_VENTILATION, false);

		if (CollectionUtils.isNotEmpty(elementStates)) {

			for (ElementState elementState : elementStates) {
				assertEquals(elementState.getStateId(), ElementTypeState.STATE_UP);
			}
		}
	}

	private void CheckTubeModeAuto() throws InterruptedException {

		List<Element> elements = elementRepository.gets(ElementType.ELEMENT_TYPE_TUBE, false);

		if (CollectionUtils.isNotEmpty(elements)) {

			for (Element element : elements) {

				Command cmd = SetVentilationModeCommandHelper.get(ElementType.ELEMENT_TYPE_TUBE, element.getId(),
						VENTILATION_MODE_AUTO);
				sendCommand(cmd);
			}
		}

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
	
	private void CheckTubeRegimenNull() throws InterruptedException {

		System.out.printf("\nCheckTubeRegimenNull...");
		List<ElementValue> elementValues = elementValueRepository.getByElementTypeParam(ElementType.ELEMENT_TYPE_TUBE,
				ELEMENT_TYPE_PARAM_VENTILATION_REGIME, ParamType.MEASURE, false);

		
		if (CollectionUtils.isNotEmpty(elementValues)) {

			for (ElementValue elementValue : elementValues) {
				assertNull(elementValue.getValue());
			}
		}
	}
	
	private void CheckTubeRegimen2(Long time) throws InterruptedException {

		List<ElementValue> elementValues = elementValueRepository.getByElementTypeParam(ElementType.ELEMENT_TYPE_TUBE,
				ELEMENT_TYPE_PARAM_VENTILATION_REGIME, ParamType.MEASURE, false);

		if (CollectionUtils.isNotEmpty(elementValues)) {

			for (ElementValue elementValue : elementValues) {
				assertEquals(elementValue.getValue(), "2");
			}
		}
	
		System.out.printf("\nCheckTubeRegimen2...");
		Thread.sleep(time);
		elementValues = elementValueRepository.getByElementTypeParam(ElementType.ELEMENT_TYPE_FAN,
				ELEMENT_TYPE_PARAM_FAN_STATE, ParamType.MEASURE, false);
		
		assertEquals(5, elementValues.stream()
				.filter(e -> Long.valueOf(FAN_STATE_VALUE_START).equals(Long.parseLong(e.getValue()))).count());
	}
	
	private void StopVentilation() throws InterruptedException {

		List<Element> elements = elementRepository.gets(ElementType.ELEMENT_TYPE_VENTILATION, false);

		if (CollectionUtils.isNotEmpty(elements)) {

			for (Element element : elements) {

				Command cmd = SetElementStatesToVentilationCommandHelper.get(ElementType.ELEMENT_TYPE_VENTILATION,
						element.getId(), ElementTypeState.STATE_STOPPED);
				sendCommand(cmd);
			}
		}

		System.out.printf("\nStopVentilation...");
		Thread.sleep(5000);

		List<ElementState> elementStates = elementStateRepository
				.getsByElementType(ElementType.ELEMENT_TYPE_VENTILATION, false);

		if (CollectionUtils.isNotEmpty(elementStates)) {

			for (ElementState elementState : elementStates) {
				assertEquals(elementState.getStateId(), ElementTypeState.STATE_STOPPED);
			}
		}

		List<ElementValue> elementValues = elementValueRepository.getByElementTypeParam(ElementType.ELEMENT_TYPE_FAN,
				ELEMENT_TYPE_PARAM_FAN_STATE, ParamType.MEASURE, false);

		if (CollectionUtils.isNotEmpty(elementValues)) {

			for (ElementValue elementValue : elementValues) {
				assertEquals(Long.parseLong(elementValue.getValue()), FAN_STATE_VALUE_STOP);
			}
		}
	}

	private void StartVentilation() throws InterruptedException {
		
		List<Element> elements = elementRepository.gets(ElementType.ELEMENT_TYPE_VENTILATION, false);

		if (CollectionUtils.isNotEmpty(elements)) {

			for (Element element : elements) {

				Command cmd = SetElementStatesToVentilationCommandHelper.get(ElementType.ELEMENT_TYPE_VENTILATION,
						element.getId(), ElementTypeState.STATE_UP);
				sendCommand(cmd);
			}
		}

		System.out.printf("\nStartVentilation...");
		Thread.sleep(5000L);

		List<ElementState> elementStates = elementStateRepository
				.getsByElementType(ElementType.ELEMENT_TYPE_VENTILATION, false);

		if (CollectionUtils.isNotEmpty(elementStates)) {

			for (ElementState elementState : elementStates) {
				assertEquals(elementState.getStateId(), ElementTypeState.STATE_UP);
			}
		}
	}

	private void SetManualMode() throws InterruptedException {

		List<Element> elements = elementRepository.gets(ElementType.ELEMENT_TYPE_TUBE, false);

		if (CollectionUtils.isNotEmpty(elements)) {

			for (Element element : elements) {

				Command cmd = SetVentilationModeCommandHelper.get(ElementType.ELEMENT_TYPE_TUBE, element.getId(),
						VENTILATION_MODE_MANUAL);
				sendCommand(cmd);
			}
		}

		System.out.printf("\nSetManualMode...");
		Thread.sleep(5000L);

		List<ElementValue> elementValues = elementValueRepository.getByElementTypeParam(ElementType.ELEMENT_TYPE_TUBE,
				ELEMENT_TYPE_PARAM_VENTILATION_MODE, ParamType.MEASURE, false);

		if (CollectionUtils.isNotEmpty(elementValues)) {

			for (ElementValue elementValue : elementValues) {
				assertEquals(elementValue.getValue(), VENTILATION_MODE_MANUAL);
			}
		}
	}

	
	private void SetRegimen2() throws InterruptedException {

		List<Element> elements = elementRepository.gets(ElementType.ELEMENT_TYPE_TUBE, false);

		if (CollectionUtils.isNotEmpty(elements)) {

			for (Element element : elements) {

				Command cmd = SetVentilationRegimeCommandHelper.get(ElementType.ELEMENT_TYPE_TUBE, element.getId(),
						"2");
				sendCommand(cmd);
			}
		}

		System.out.printf("\nSetRegimen...");
		Thread.sleep(2000L);

	}
	
	private void StartFans() throws InterruptedException {
		
		List<Element> elements = elementRepository.gets(ElementType.ELEMENT_TYPE_FAN, false);

		if (CollectionUtils.isNotEmpty(elements)) {

			Command cmd = SetFansStartCommandHelper.get(null, null, elements);
			sendCommand(cmd);
		}

		System.out.printf("\nStartFans...");
		Thread.sleep(10000L);
		List<ElementValue> elementValues = elementValueRepository.getByElementTypeParam(ElementType.ELEMENT_TYPE_FAN,
				ELEMENT_TYPE_PARAM_FAN_STATE, ParamType.MEASURE, false);

		assertEquals(4, elementValues.stream()
				.filter(e -> Long.valueOf(FAN_STATE_VALUE_START).equals(Long.parseLong(e.getValue()))).count());

		System.out.printf("\nStartFans...");
		Thread.sleep(31000L);
		elementValues = elementValueRepository.getByElementTypeParam(ElementType.ELEMENT_TYPE_FAN,
				ELEMENT_TYPE_PARAM_FAN_STATE, ParamType.MEASURE, false);

		assertEquals(6, elementValues.stream()
				.filter(e -> Long.valueOf(FAN_STATE_VALUE_START).equals(Long.parseLong(e.getValue()))).count());

		System.out.printf("\nStartFans...");
		Thread.sleep(31000L);
		elementValues = elementValueRepository.getByElementTypeParam(ElementType.ELEMENT_TYPE_FAN,
				ELEMENT_TYPE_PARAM_FAN_STATE, ParamType.MEASURE, false);

		assertEquals(7, elementValues.stream()
				.filter(e -> Long.valueOf(FAN_STATE_VALUE_START).equals(Long.parseLong(e.getValue()))).count());

		System.out.printf("\nStartFans...");
		Thread.sleep(31000L);
		elementValues = elementValueRepository.getByElementTypeParam(ElementType.ELEMENT_TYPE_FAN,
				ELEMENT_TYPE_PARAM_FAN_STATE, ParamType.MEASURE, false);

		assertEquals(8, elementValues.stream()
				.filter(e -> Long.valueOf(FAN_STATE_VALUE_START).equals(Long.parseLong(e.getValue()))).count());
	}

	private void StartReverseFans() throws InterruptedException {

		List<ElementValue> elementValues = elementValueRepository.getByElementTypeParam(ElementType.ELEMENT_TYPE_FAN,
				ELEMENT_TYPE_PARAM_FAN_STATE, ParamType.MEASURE, false);

		ArrayList<Element> elements = new ArrayList<Element>();

		for (ElementValue e : elementValues) {

			if (Long.valueOf(FAN_STATE_VALUE_START).equals(Long.parseLong(e.getValue()))) {
				elements.add(new Element(e.getElementTypeId(), e.getElementId()));
			}
		}

		if (CollectionUtils.isNotEmpty(elements)) {

			Command cmd = SetFansStartReverseCommandHelper.get(null, null, elements);
			sendCommand(cmd);
		}

		System.out.printf("\nCheck all fan Stop...");
		Thread.sleep(5000L);

		elementValues = elementValueRepository.getByElementTypeParam(ElementType.ELEMENT_TYPE_FAN,
				ELEMENT_TYPE_PARAM_FAN_STATE, ParamType.MEASURE, false);

		if (CollectionUtils.isNotEmpty(elementValues)) {

			for (ElementValue elementValue : elementValues) {
				assertEquals(Long.parseLong(elementValue.getValue()), FAN_STATE_VALUE_STOP);
			}
		}

		System.out.printf("\nStartReverseFans...");
		Thread.sleep(20000L);
		
		elementValues = elementValueRepository.getByElementTypeParam(ElementType.ELEMENT_TYPE_FAN,
				ELEMENT_TYPE_PARAM_FAN_STATE, ParamType.MEASURE, false);
		assertEquals(4, elementValues.stream()
				.filter(e -> Long.valueOf(FAN_STATE_VALUE_REVERSE).equals(Long.parseLong(e.getValue()))).count());

		System.out.printf("\nStartReverseFans...");
		Thread.sleep(31000L);
		elementValues = elementValueRepository.getByElementTypeParam(ElementType.ELEMENT_TYPE_FAN,
				ELEMENT_TYPE_PARAM_FAN_STATE, ParamType.MEASURE, false);

		assertEquals(6, elementValues.stream()
				.filter(e -> Long.valueOf(FAN_STATE_VALUE_REVERSE).equals(Long.parseLong(e.getValue()))).count());

		System.out.printf("\nStartReverseFans...");
		Thread.sleep(31000L);
		elementValues = elementValueRepository.getByElementTypeParam(ElementType.ELEMENT_TYPE_FAN,
				ELEMENT_TYPE_PARAM_FAN_STATE, ParamType.MEASURE, false);

		assertEquals(7, elementValues.stream()
				.filter(e -> Long.valueOf(FAN_STATE_VALUE_REVERSE).equals(Long.parseLong(e.getValue()))).count());

		System.out.printf("\nStartReverseFans...");
		Thread.sleep(31000L);
		elementValues = elementValueRepository.getByElementTypeParam(ElementType.ELEMENT_TYPE_FAN,
				ELEMENT_TYPE_PARAM_FAN_STATE, ParamType.MEASURE, false);

		assertEquals(8, elementValues.stream()
				.filter(e -> Long.valueOf(FAN_STATE_VALUE_REVERSE).equals(Long.parseLong(e.getValue()))).count());
	}

	private void SetAutoMode() throws InterruptedException {

		List<Element> elements = elementRepository.gets(ElementType.ELEMENT_TYPE_TUBE, false);

		if (CollectionUtils.isNotEmpty(elements)) {

			for (Element element : elements) {

				Command cmd = SetVentilationModeCommandHelper.get(ElementType.ELEMENT_TYPE_TUBE, element.getId(),
						VENTILATION_MODE_AUTO);
				sendCommand(cmd);
			}
		}

		System.out.printf("\nSetManualAuto...");
		Thread.sleep(5000L);

		List<ElementValue> elementValues = elementValueRepository.getByElementTypeParam(ElementType.ELEMENT_TYPE_TUBE,
				ELEMENT_TYPE_PARAM_VENTILATION_MODE, ParamType.MEASURE, false);

		if (CollectionUtils.isNotEmpty(elementValues)) {

			for (ElementValue elementValue : elementValues) {
				assertEquals(elementValue.getValue(), VENTILATION_MODE_AUTO);
			}
		}
	}
	
	private void StopFansInAutoMode() throws InterruptedException {
		
		List<Element> elements = elementRepository.gets(ElementType.ELEMENT_TYPE_FAN, false);

		if (CollectionUtils.isNotEmpty(elements)) {

			Command cmd = SetFansStopCommandHelper.get(null, null, elements);
			sendCommand(cmd);
		}
		System.out.printf("\nTryStopFans...");
		
		Thread.sleep(5000L);
		
		List<ElementValue> elementValues = elementValueRepository.getByElementTypeParam(ElementType.ELEMENT_TYPE_FAN,
				ELEMENT_TYPE_PARAM_FAN_STATE, ParamType.MEASURE, false);

		assertEquals(5, elementValues.stream()
				.filter(e -> Long.valueOf(FAN_STATE_VALUE_START).equals(Long.parseLong(e.getValue()))).count());
	}


	private void AlarmVentilation() throws InterruptedException {

		List<Element> elements = elementRepository.gets(ElementType.ELEMENT_TYPE_VENTILATION, false);

		if (CollectionUtils.isNotEmpty(elements)) {

			for (Element element : elements) {

				Command cmd = SetFireAlarmCommandHelper.get(ElementType.ELEMENT_TYPE_VENTILATION,
						element.getId(), 1000L);
				sendCommand(cmd);
			}
		}

		System.out.printf("\nAlarmVentilation...");
		Thread.sleep(10000L);

		List<ElementState> elementStates = elementStateRepository
				.getsByElementType(ElementType.ELEMENT_TYPE_VENTILATION, false);

		if (CollectionUtils.isNotEmpty(elementStates)) {

			for (ElementState elementState : elementStates) {
				assertEquals(elementState.getStateId(), ElementTypeState.STATE_EVACUATION);
			}
		}

		List<ElementValue> elementValues = elementValueRepository.getByElementTypeParam(ElementType.ELEMENT_TYPE_FAN,
				ELEMENT_TYPE_PARAM_FAN_STATE, ParamType.MEASURE, false);

		assertEquals(1, elementValues.stream()
				.filter(e -> Long.valueOf(FAN_STATE_VALUE_REVERSE).equals(Long.parseLong(e.getValue()))).count());
		
		assertEquals(1, elementValues.stream()
				.filter(e -> Long.valueOf(FAN_STATE_VALUE_START).equals(Long.parseLong(e.getValue()))).count());
	}
	

	private void Extraction() throws InterruptedException {
		
		List<Element> elements = elementRepository.gets(ElementType.ELEMENT_TYPE_VENTILATION, false);

		if (CollectionUtils.isNotEmpty(elements)) {

			for (Element element : elements) {

				Command cmd = SetElementStatesToVentilationCommandHelper.get(ElementType.ELEMENT_TYPE_VENTILATION,
						element.getId(), ElementTypeState.STATE_EXTRACTION);
				sendCommand(cmd);
			}
		}

		System.out.printf("\nExtraction...");
		Thread.sleep(5000L);

		List<ElementState> elementStates = elementStateRepository
				.getsByElementType(ElementType.ELEMENT_TYPE_VENTILATION, false);

		if (CollectionUtils.isNotEmpty(elementStates)) {

			for (ElementState elementState : elementStates) {
				assertEquals(elementState.getStateId(), ElementTypeState.STATE_EXTRACTION);
			}
		}
		
		List<ElementValue> elementValues = elementValueRepository.getByElementTypeParam(ElementType.ELEMENT_TYPE_FAN,
				ELEMENT_TYPE_PARAM_FAN_STATE, ParamType.MEASURE, false);
		
		assertEquals(1, elementValues.stream()
				.filter(e -> Long.valueOf(FAN_STATE_VALUE_REVERSE).equals(Long.parseLong(e.getValue()))).count());
		
		assertEquals(3, elementValues.stream()
				.filter(e -> Long.valueOf(FAN_STATE_VALUE_START).equals(Long.parseLong(e.getValue()))).count());
	}
	
	
	private void StartFansInAutoMode() throws InterruptedException {
		
		List<Element> elements = elementRepository.gets(ElementType.ELEMENT_TYPE_FAN, false);

		if (CollectionUtils.isNotEmpty(elements)) {

			Command cmd = SetFansStartCommandHelper.get(null, null, elements);
			sendCommand(cmd);
		}
		System.out.printf("\nTryStopFans...");
		
		Thread.sleep(5000L);
		
		List<ElementValue> elementValues = elementValueRepository.getByElementTypeParam(ElementType.ELEMENT_TYPE_FAN,
				ELEMENT_TYPE_PARAM_FAN_STATE, ParamType.MEASURE, false);

		assertEquals(1, elementValues.stream()
				.filter(e -> Long.valueOf(FAN_STATE_VALUE_REVERSE).equals(Long.parseLong(e.getValue()))).count());
		
		assertEquals(3, elementValues.stream()
				.filter(e -> Long.valueOf(FAN_STATE_VALUE_START).equals(Long.parseLong(e.getValue()))).count());
	}
}
