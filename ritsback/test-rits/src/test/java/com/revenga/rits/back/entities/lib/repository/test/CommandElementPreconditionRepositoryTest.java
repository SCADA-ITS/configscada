package com.revenga.rits.back.entities.lib.repository.test;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.revenga.rits.back.data.core.model.Alarm;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.CommandElement;
import com.revenga.rits.back.data.core.model.CommandElementPrecondition;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.CommandElementValue;
import com.revenga.rits.back.data.core.model.CommandElementValuePrecondition;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.loader.service.CollectionManagerService;
import com.revenga.rits.back.entities.lib.repository.AlarmConfigRepository;
import com.revenga.rits.back.entities.lib.repository.AlarmRepository;
import com.revenga.rits.back.entities.lib.repository.CommandElementPreconditionRepository;
import com.revenga.rits.back.entities.lib.repository.CommandElementValuePreconditionRepository;
import com.revenga.rits.back.entities.lib.repository.CommandElementValueRepository;
import com.revenga.rits.back.entities.lib.repository.ElementRepository;
import com.revenga.rits.back.entities.lib.repository.ElementStateRepository;
import com.revenga.rits.back.entities.lib.repository.ElementTypeParamRepository;
import com.revenga.rits.back.entities.lib.repository.ElementTypeStateRepository;
import com.revenga.rits.back.entities.lib.repository.ElementValueRepository;
import com.revenga.rits.back.entities.lib.repository.EntityRepository;
import com.revenga.rits.back.entities.lib.repository.ThresholdOperationRepository;

public class CommandElementPreconditionRepositoryTest {

	private ElementValueRepository elementValueRepository;
	private ElementRepository elementRepository;
	private ElementTypeParamRepository elementTypeParamRepository;
	private ElementStateRepository elementStateRepository;
	private ThresholdOperationRepository thresholdOperationRepository;
	private AlarmRepository alarmRepository;
	private AlarmConfigRepository alarmConfigRepository;
	private CommandElementPreconditionRepository commandElementPreconditionRepository;
	private ElementTypeStateRepository elementTypeStateRepository;
	private CommandElementValuePreconditionRepository commandElementValuePreconditionRepository;
	private List<EntityRepository> repositories;
	private CommandElementValueRepository commandElementValueRepository;

	public CommandElementPreconditionRepositoryTest() {

		CollectionManagerService cms = new CollectionManagerService();
		cms.load();
		
		this.repositories = new ArrayList<>();

		// Declarar los repositorios que necesitamos

		this.elementValueRepository = new ElementValueRepository(false);
		repositories.add(elementValueRepository);

		this.elementTypeParamRepository = new ElementTypeParamRepository(false);
		repositories.add(elementTypeParamRepository);

		this.elementStateRepository = new ElementStateRepository(false);
		repositories.add(elementStateRepository);
		
		this.thresholdOperationRepository = new ThresholdOperationRepository(false);
		repositories.add(thresholdOperationRepository);
		
		this.alarmRepository = new AlarmRepository(true);
		repositories.add(alarmRepository);
		
		this.commandElementValueRepository = new CommandElementValueRepository(false);
		repositories.add(commandElementValueRepository);
		
		this.alarmConfigRepository = new AlarmConfigRepository(false);
		repositories.add(alarmConfigRepository);
		
		this.elementRepository = new ElementRepository(true);
		repositories.add(elementRepository);
		
		this.elementTypeStateRepository = new ElementTypeStateRepository(false);
		repositories.add(elementTypeStateRepository);
		
		this.commandElementPreconditionRepository = new CommandElementPreconditionRepository(true, repositories);
		repositories.add(commandElementPreconditionRepository);

		this.commandElementValuePreconditionRepository = new CommandElementValuePreconditionRepository(true);
		repositories.add(commandElementValuePreconditionRepository);
		
		
	}

	@Test
	public void testByElementState() {

		CommandElementPrecondition commandElementPrecondition = new CommandElementPrecondition();
		commandElementPrecondition.setId(20000000L);
		commandElementPrecondition.setCommandElementTypeId(3L);
		commandElementPrecondition.setElementTypeId(2L);
		commandElementPrecondition.setElementId(2L);
		commandElementPrecondition.setTargetElementTypeId(2L);
		commandElementPrecondition.setTargetElementId(2L);
		commandElementPrecondition.setElementTypeStateId(1L);
		commandElementPrecondition.setNegative(false);
		

		ElementState elementState = new ElementState();
		elementState.setElementId(2L);
		elementState.setElementTypeId(2L);
		elementState.setStateId(2L);
		
		
		elementStateRepository.put(elementState);
		commandElementPreconditionRepository.put(commandElementPrecondition);
		
		CommandElement commandElement = new CommandElement();
		
		commandElement.setElementId(2L);
		commandElement.setElementTypeId(2L);
		commandElement.setCommandElementTypeId(3L);
		String salida = commandElementPreconditionRepository.validate(commandElement).get(0).getToStr();
		assertEquals(salida, "(#01 LBL_STATE) CAM-2 LBL_ELEMENT_TYPE_STATE_UP;");
	}
	
	@Test
	public void testByElementStateNegative() {

		CommandElementPrecondition commandElementPrecondition = new CommandElementPrecondition();
		commandElementPrecondition.setId(200L);
		commandElementPrecondition.setCommandElementTypeId(3L);
		commandElementPrecondition.setElementTypeId(2L);
		commandElementPrecondition.setElementId(2L);
		commandElementPrecondition.setTargetElementTypeId(2L);
		commandElementPrecondition.setTargetElementId(2L);
		commandElementPrecondition.setElementTypeStateId(1L);
		commandElementPrecondition.setNegative(true);
		

		ElementState elementState = new ElementState();
		elementState.setElementId(2L);
		elementState.setElementTypeId(2L);
		elementState.setStateId(1L);
		
		
		elementStateRepository.put(elementState);
		commandElementPreconditionRepository.put(commandElementPrecondition);
		
		CommandElement commandElement = new CommandElement();
		
		commandElement.setElementId(2L);
		commandElement.setElementTypeId(2L);
		commandElement.setCommandElementTypeId(3L);
		String salida = commandElementPreconditionRepository.validate(commandElement).get(0).getToStr();
		assertEquals("(#01 LBL_STATE) LBL_NOT CAM-2 LBL_ELEMENT_TYPE_STATE_UP;", salida );
	}
	
	
	@Test
	public void testByAlarm() {
		
		CommandElementPrecondition commandElementPrecondition = new CommandElementPrecondition();
		commandElementPrecondition.setId(200000010L);
		commandElementPrecondition.setCommandElementTypeId(3L);
		commandElementPrecondition.setElementTypeId(2L);
		commandElementPrecondition.setElementId(2L);
		commandElementPrecondition.setTargetElementTypeId(2L);
		commandElementPrecondition.setTargetElementId(2L);
		commandElementPrecondition.setNegative(false);
		commandElementPrecondition.setActiveAlarmConfigId(30001L);
		
		
		AlarmConfig alarmConfig = new AlarmConfig();
		alarmConfig.setId(30001L);
		alarmConfig.setAlarmTypeId(2L);
		alarmConfig.setElementTypeStateId(3L);
		alarmConfig.setId(30001L);
		alarmConfig.setAlarmTypeId(2L);
		alarmConfig.setLabelAlias("LBL_ALARM_CONFIGS_ALARM_WS_AIR_PRESSURE_FAIL");
		alarmConfigRepository.put(alarmConfig);
		
		Alarm alarm = new Alarm();
		alarm.generateId();
		alarm.setAlarmConfig(alarmConfig);
		
		alarm.setAlarmConfigId(30001L);
		alarm.setTargetElementTypeId(2L);
		alarm.setTargetElementId(2L);
		alarm.setEnabled(true);
		Element sourceElement = new Element();
		sourceElement.setId(2L);
		Element targetElement = new Element();
		targetElement.setId(2L);
		
		alarm.setSourceElement(sourceElement);
		alarm.setTargetElement(targetElement);
		alarmRepository.put(alarm);
	
		commandElementPreconditionRepository.put(commandElementPrecondition);
		
		CommandElement commandElement = new CommandElement();
		
		commandElement.setElementId(2L);
		commandElement.setElementTypeId(2L);
		commandElement.setCommandElementTypeId(3L);

		String salida = commandElementPreconditionRepository.validate(commandElement).get(0).getToStr();
		assertEquals(salida, "(#01 LBL_ALARM) CAM-2 LBL_ALARM_CONFIGS_ALARM_WS_AIR_PRESSURE_FAIL;");
	}
	
	@Test
	public void testByAlarmNegative() {
		
		CommandElementPrecondition commandElementPrecondition = new CommandElementPrecondition();
		commandElementPrecondition.setId(200000010L);
		commandElementPrecondition.setCommandElementTypeId(3L);
		commandElementPrecondition.setElementTypeId(2L);
		commandElementPrecondition.setElementId(2L);
		commandElementPrecondition.setTargetElementTypeId(2L);
		commandElementPrecondition.setTargetElementId(2L);
		commandElementPrecondition.setNegative(true);
		commandElementPrecondition.setActiveAlarmConfigId(31L);
		
		
		AlarmConfig alarmConfig = new AlarmConfig();
		alarmConfig.setId(31L);
		alarmConfig.setAlarmTypeId(2L);
		alarmConfig.setElementTypeStateId(3L);
		alarmConfig.setId(31L);
		alarmConfig.setAlarmTypeId(2L);
		alarmConfig.setLabelAlias("LBL_ALARM_CONFIGS_ALARM_WS_AIR_PRESSURE_FAIL");
		alarmConfigRepository.put(alarmConfig);
		
		Alarm alarm = new Alarm();
		alarm.generateId();
		alarm.setAlarmConfig(alarmConfig);
		
		alarm.setAlarmConfigId(31L);
		alarm.setTargetElementTypeId(2L);
		alarm.setTargetElementId(2L);
		alarm.setDeactivation(446464L);
		alarm.setEnabled(true);
		Element sourceElement = new Element();
		sourceElement.setId(2L);
		Element targetElement = new Element();
		targetElement.setId(2L);
		
		alarm.setSourceElement(sourceElement);
		alarm.setTargetElement(targetElement);
		alarmRepository.put(alarm);
	
		commandElementPreconditionRepository.put(commandElementPrecondition);
		
		CommandElement commandElement = new CommandElement();
		
		commandElement.setElementId(2L);
		commandElement.setElementTypeId(2L);
		commandElement.setCommandElementTypeId(3L);

		String salida = commandElementPreconditionRepository.validate(commandElement).get(0).getToStr();
		assertEquals("(#01 LBL_ALARM) LBL_NOT CAM-2 LBL_ALARM_CONFIGS_ALARM_WS_AIR_PRESSURE_FAIL;", salida);
	}
	
	@Test
	public void testByMeasures() {
		
		CommandElementPrecondition commandElementPrecondition = new CommandElementPrecondition();
		commandElementPrecondition.setId(200000210L);
		commandElementPrecondition.setCommandElementTypeId(3L);
		commandElementPrecondition.setElementTypeId(2L);
		commandElementPrecondition.setElementId(2L);
		commandElementPrecondition.setTargetElementTypeId(1L);
		commandElementPrecondition.setTargetElementId(6L);
		commandElementPrecondition.setNegative(false);
		commandElementPrecondition.setThresholdOperationId(1L);
		commandElementPrecondition.setThresholdVal1("70");
		commandElementPrecondition.setThresholdVal2("120");
		commandElementPrecondition.setTargetElementTypeParamId(1L);
		commandElementPrecondition.setTargetParamTypeId(2L);
		
		Element element = new Element();
		element.setElementTypeId(1L);
		element.setId(6L);
		element.setAlias("SENSOR_M");
		
		elementRepository.put(element);
		
		ElementValue elementValue = new ElementValue();
		elementValue.setId(1L);
		elementValue.setValue("121");
		elementValue.setElementTypeId(commandElementPrecondition.getTargetElementTypeId());
		elementValue.setElementId(commandElementPrecondition.getTargetElementId());
		elementValue.setElementTypeParamId(commandElementPrecondition.getTargetElementTypeParamId());
		elementValue.setParamTypeId(commandElementPrecondition.getTargetParamTypeId());		
		elementValueRepository.put(elementValue);
		
		CommandElement commandElement = new CommandElement();
		commandElement.setElementId(2L);
		commandElement.setElementTypeId(2L);
		commandElement.setCommandElementTypeId(3L);

		commandElementPreconditionRepository.put(commandElementPrecondition);

		String salida = commandElementPreconditionRepository.validate(commandElement).get(0).getToStr();
		assertEquals(salida,
				"(#01 LBL_MEASURE) SENSOR_M - LBL_ELEMENT_TYPE_PARAM_CONCENTRATION_NACL LBL_THRESHOLD_OPERATION_BETWEEN 70 - 120;");
	}
	
	
	@Test
	public void testByMeasuresNegative() {
		
		CommandElementPrecondition commandElementPrecondition = new CommandElementPrecondition();
		commandElementPrecondition.setId(200000210L);
		commandElementPrecondition.setCommandElementTypeId(3L);
		commandElementPrecondition.setElementTypeId(2L);
		commandElementPrecondition.setElementId(2L);
		commandElementPrecondition.setTargetElementTypeId(1L);
		commandElementPrecondition.setTargetElementId(6L);
		commandElementPrecondition.setNegative(true);
		commandElementPrecondition.setThresholdOperationId(1L);
		commandElementPrecondition.setThresholdVal1("70");
		commandElementPrecondition.setThresholdVal2("120");
		commandElementPrecondition.setTargetElementTypeParamId(1L);
		commandElementPrecondition.setTargetParamTypeId(2L);
		
		Element element = new Element();
		//Element element = elementRepository.get(notComplyPreconditions.get(i).getTargetElementTypeId(),
		//notComplyPreconditions.get(i).getTargetElementId(), true);
		element.setElementTypeId(1L);
		element.setId(6L);
		element.setAlias("SENSOR_M");
		
		elementRepository.put(element);
		
		ElementValue elementValue = new ElementValue();
		elementValue.setId(1L);
		elementValue.setValue("119");
		elementValue.setElementTypeId(commandElementPrecondition.getTargetElementTypeId());
		elementValue.setElementId(commandElementPrecondition.getTargetElementId());
		elementValue.setElementTypeParamId(commandElementPrecondition.getTargetElementTypeParamId());
		elementValue.setParamTypeId(commandElementPrecondition.getTargetParamTypeId());		
		elementValueRepository.put(elementValue);
		
		CommandElement commandElement = new CommandElement();
		commandElement.setElementId(2L);
		commandElement.setElementTypeId(2L);
		commandElement.setCommandElementTypeId(3L);

		commandElementPreconditionRepository.put(commandElementPrecondition);

		String salida = commandElementPreconditionRepository.validate(commandElement).get(0).getToStr();
		assertEquals(salida,
				"(#01 LBL_MEASURE) LBL_NOT SENSOR_M - LBL_ELEMENT_TYPE_PARAM_CONCENTRATION_NACL LBL_THRESHOLD_OPERATION_BETWEEN 70 - 120;");
	}
	
	@Test
	public void testByVa() {
		
		CommandElementPrecondition commandElementPrecondition = new CommandElementPrecondition();
		commandElementPrecondition.setId(2324L);
		commandElementPrecondition.setCommandElementTypeId(3L);
		commandElementPrecondition.setElementTypeId(2L);
		commandElementPrecondition.setElementId(2L);
		commandElementPrecondition.setTargetElementTypeId(2L);
		commandElementPrecondition.setTargetElementId(2L);
		commandElementPrecondition.setElementTypeStateId(1L);
		commandElementPrecondition.setNegative(false);
		ElementState elementState = new ElementState();
		elementState.setElementId(2L);
		elementState.setElementTypeId(2L);
		elementState.setStateId(2L);
		
		Element element = new Element();;
		element.setElementTypeId(1L);
		element.setId(6L);
		element.setAlias("SENSOR_M");
		
		elementRepository.put(element);
		
		CommandElementTypeParam commandElementTypeParam = new CommandElementTypeParam();
		commandElementTypeParam.setCommandElementTypeId(2L);
		commandElementTypeParam.setElementTypeId(71L);
		commandElementTypeParam.setId(56L);
		commandElementTypeParam.setDataTypeId(1L);
		
		CommandElementValue commandElementValue = new CommandElementValue();
		commandElementValue.setCommandElementId(2L);
		commandElementValue.setId(1L);
		commandElementValue.setValue("90");
		commandElementValue.setElementTypeId(2L);
		commandElementValue.setCommandElementTypeParamId(1L);
		commandElementValue.setCommandElementTypeId(3L);
		commandElementValue.setCommandElementTypeParam(commandElementTypeParam);
		commandElementValueRepository.put(commandElementValue);
		
		CommandElementValuePrecondition commandElementValuePrecondition = new CommandElementValuePrecondition();

		commandElementValuePrecondition.setId(989L);
		commandElementValuePrecondition.setCommandElementTypeId(3L);
		commandElementValuePrecondition.setElementTypeId(2L);
		commandElementValuePrecondition.setElementId(2L);
		commandElementValuePrecondition.setPreconditionId(2324L);
		commandElementValuePrecondition.setCommandElementTypeParamId(1L);
		commandElementValuePrecondition.setThresholdOperationId(1L);
		commandElementValuePrecondition.setThresholdVal1("20");
		commandElementValuePrecondition.setThresholdVal2("70");
		
		commandElementValuePreconditionRepository.put(commandElementValuePrecondition);
		elementStateRepository.put(elementState);
		List<CommandElementValuePrecondition> listaValuePrecon =  new ArrayList<>();
		listaValuePrecon.add(commandElementValuePrecondition);
		commandElementPrecondition.setCommandElementValuePrecondition(listaValuePrecon);
		commandElementPreconditionRepository.put(commandElementPrecondition);
		
		
		CommandElement commandElement = new CommandElement();
		
		List<CommandElementValue> values = new ArrayList<>();
		values.add(commandElementValue);
		commandElement.setId(2L);
		commandElement.setCommandElementValues(values);
		commandElement.setElementId(2L);
		commandElement.setElementTypeId(2L);
		commandElement.setCommandElementTypeId(3L);
		assertEquals(new ArrayList<>(), commandElementPreconditionRepository.validate(commandElement));
		
		
	}
	
	@Test
	public void testByValNe() {
		
		CommandElementPrecondition commandElementPrecondition = new CommandElementPrecondition();
		commandElementPrecondition.setId(2324L);
		commandElementPrecondition.setCommandElementTypeId(3L);
		commandElementPrecondition.setElementTypeId(2L);
		commandElementPrecondition.setElementId(2L);
		commandElementPrecondition.setTargetElementTypeId(2L);
		commandElementPrecondition.setTargetElementId(2L);
		commandElementPrecondition.setElementTypeStateId(1L);
		commandElementPrecondition.setNegative(false);
		ElementState elementState = new ElementState();
		elementState.setElementId(2L);
		elementState.setElementTypeId(2L);
		elementState.setStateId(2L);
		/*
		Element element = new Element();;
		element.setElementTypeId(1L);
		element.setId(6L);
		element.setAlias("SENSOR_M");
		*/
		//elementRepository.put(element);
		
		CommandElementTypeParam commandElementTypeParam = new CommandElementTypeParam();
		commandElementTypeParam.setCommandElementTypeId(2L);
		commandElementTypeParam.setElementTypeId(71L);
		commandElementTypeParam.setId(56L);
		commandElementTypeParam.setDataTypeId(1L);
		
		CommandElementValue commandElementValue = new CommandElementValue();
		commandElementValue.setCommandElementId(2L);
		commandElementValue.setId(1L);
		commandElementValue.setValue("40");
		commandElementValue.setElementTypeId(2L);
		commandElementValue.setCommandElementTypeParamId(1L);
		commandElementValue.setCommandElementTypeId(3L);
		commandElementValue.setCommandElementTypeParam(commandElementTypeParam);
		commandElementValueRepository.put(commandElementValue);
		
		CommandElementValuePrecondition commandElementValuePrecondition = new CommandElementValuePrecondition();

		commandElementValuePrecondition.setId(989L);
		commandElementValuePrecondition.setCommandElementTypeId(3L);
		commandElementValuePrecondition.setElementTypeId(2L);
		commandElementValuePrecondition.setElementId(2L);
		commandElementValuePrecondition.setPreconditionId(2324L);
		commandElementValuePrecondition.setCommandElementTypeParamId(1L);
		commandElementValuePrecondition.setThresholdOperationId(1L);
		commandElementValuePrecondition.setThresholdVal1("20");
		commandElementValuePrecondition.setThresholdVal2("70");
		
		commandElementValuePreconditionRepository.put(commandElementValuePrecondition);
		elementStateRepository.put(elementState);
		List<CommandElementValuePrecondition> listaValuePrecon =  new ArrayList<>();
		listaValuePrecon.add(commandElementValuePrecondition);
		commandElementPrecondition.setCommandElementValuePrecondition(listaValuePrecon);
		commandElementPreconditionRepository.put(commandElementPrecondition);
		
		
		CommandElement commandElement = new CommandElement();
		
		List<CommandElementValue> values = new ArrayList<>();
		values.add(commandElementValue);
		commandElement.setId(2L);
		commandElement.setCommandElementValues(values);
		commandElement.setElementId(2L);
		commandElement.setElementTypeId(2L);
		commandElement.setCommandElementTypeId(3L);
		String salida = commandElementPreconditionRepository.validate(commandElement).get(0).getToStr();
		assertEquals(salida, "(#01 LBL_STATE) CAM-2 LBL_ELEMENT_TYPE_STATE_UP;");
		
		
	}
	
}
