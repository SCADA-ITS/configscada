package com.revenga.rits.back.io.controller.module.mango;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.adapter.lib.model.JsonGenericMangoV1;
import com.revenga.rits.back.adapter.lib.model.JsonGenericMangoV1.Alarms;
import com.revenga.rits.back.adapter.lib.model.JsonGenericMangoV1.Alarms.Alarm;
import com.revenga.rits.back.adapter.lib.model.JsonGenericMangoV1.Fields;
import com.revenga.rits.back.adapter.lib.model.JsonGenericMangoV1.Fields.Field;
import com.revenga.rits.back.adapter.lib.model.JsonGenericMangoV1.State.StateValue;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.DataType;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.util.GroovyManager;
import com.revenga.rits.back.io.controller.adapter.MangoAdapter;
import com.revenga.rits.back.io.controller.module.GenericEndpointHelper;
import com.revenga.rits.back.io.controller.module.ModuleException;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

import groovy.lang.GroovyObject;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class MangoEndpointHelper {
	
	private static final String PATH_GROOVY_INPUT_ADAPTERS = "io-controller/groovy/mango/input_adapters/";
	
	private MangoEndpointHelper() {
		
		throw new IllegalStateException(this.getClass().getSimpleName());
	}
	
	public static ElementState getElementState(@NonNull String response, JsonGenericMangoV1 adapter, Element element) {
		
		String fieldStateNameMango = adapter.getState().getField();
		ElementState elementState = null;
		Map<String, String> hashMap = MangoAdapter.getInstance().getHash(response);
		
		if (fieldStateNameMango != null) {
			
			String valueFieldStateMango = hashMap.get(MangoAdapter.getInstance().getDataPoint(element, fieldStateNameMango));
			
			if (!StringUtils.isEmpty(valueFieldStateMango) && NumberUtils.isCreatable(valueFieldStateMango)) {
			
				StateValue stateValue = adapter.getState().getStateValue().stream()
						.filter(e -> e.getSource() == Double.valueOf(valueFieldStateMango).longValue()).findFirst().orElse(null);
				
				
				elementState = GenericEndpointHelper.createElementState(element,
						stateValue != null ? Long.valueOf(stateValue.getInput())
								: GenericEndpointHelper.getInitialStateId(element));
			}
			else {

				if (StringUtils.isEmpty(valueFieldStateMango)) {
				
					log.warn((new Key(element)) + " => " + fieldStateNameMango + " is empty");
				}
				else {
				
					log.warn((new Key(element)) + " => " + fieldStateNameMango + " = " + valueFieldStateMango + " (not is numeric)");
				}
			}
		}
		
		return elementState;
	}
	
	public static List<ElementState> getElementStates(@NonNull String response, List<Element> elements) {

		Map<String, String> hashMap = MangoAdapter.getInstance().getHash(response);
		
		log.debug("[RequestStates] Mango responds with " + hashMap.size() + " state measures ");
		
		if (hashMap.size() < elements.size()) {
		
			log.warn("[RequestStates] Mango should respond with " + elements.size() + " state measures ");
		}

		List<ElementState> elementStatesChanged = new ArrayList<>();

		for (Element element : elements) {
						
			JsonGenericMangoV1 adapter = MangoAdapter.getInstance().get(element);

			if (adapter != null) {

				String fieldStateNameMango = adapter.getState().getField();

				if (!StringUtils.isEmpty(fieldStateNameMango)) {

					String dataPoint = MangoAdapter.getInstance().getDataPoint(element, fieldStateNameMango);
					
					String valueFieldStateMango = hashMap.get(dataPoint);

					if (!StringUtils.isEmpty(valueFieldStateMango) && NumberUtils.isCreatable(valueFieldStateMango)) {

						StateValue stateValue = adapter.getState().getStateValue().stream()
								.filter(e -> e.getSource() == Double.valueOf(valueFieldStateMango).longValue())
								.findFirst().orElse(null);
						
						if (stateValue != null) {
							
							ElementState elementState = GenericEndpointHelper.createElementState(element,
									Long.valueOf(stateValue.getInput()));							
							
							elementStatesChanged.add(elementState);
						}
					} 
					else {

						if (StringUtils.isEmpty(valueFieldStateMango)) {

							String equipmentTypeAlias = "";
							
							if (element.getElementType() != null) {
								
								equipmentTypeAlias = element.getElementType().getAlias();
							}
							
							log.warn((new Key(element)).getSimpleValue() + " => " + fieldStateNameMango
									+ " is empty. Check if exist equipment " + equipmentTypeAlias + "("
									+ element.getElementTypeId() + ") - " + element.getAlias() + " in Mango");
						} else {

							log.warn((new Key(element)).getSimpleValue() + " => " + fieldStateNameMango + " = " + valueFieldStateMango
									+ " (not is numeric)");
						}
					}
				}
			}
		}

		return elementStatesChanged;
	}
	
	public static List<ElementValue> getElementValues(@NonNull String response, List<Element> elements) {

		List<ElementValue> elementValues = new ArrayList<>(); 
		Map<String, String> hashMap = MangoAdapter.getInstance().getHash(response);	

		for (Element element : elements) {

			JsonGenericMangoV1 adapter = MangoAdapter.getInstance().get(element);
			Map<String, String> responseElement = new HashMap<>();

			if (adapter != null) {
				
				if (adapter.getFields() != null && !CollectionUtils.isEmpty(adapter.getFields().getField())) {
				
					for (Field field : adapter.getFields().getField()) {
						
						String fieldNameMango = field.getSource();
						
						if (!StringUtils.isEmpty(fieldNameMango)) {
							
							String valueFieldMango = hashMap
									.get(MangoAdapter.getInstance().getDataPoint(element, fieldNameMango));
							
							if (valueFieldMango != null) {
								
								responseElement.put(MangoAdapter.getInstance().getDataPoint(element, fieldNameMango), valueFieldMango);
							}
						}
					} 
				}				
				
				if (!MapUtils.isEmpty(responseElement) && adapter != null && element != null) {
				
					elementValues.addAll(getElementValues(responseElement, adapter, element));
				}
			}
		}

		return elementValues;
	}

	public static List<ElementValue> getElementValues(@NonNull Map<String, String> response, JsonGenericMangoV1 adapter, Element element) {

		List<ElementValue> elementValues = null;
		Fields fields = null;

		fields = adapter.getFields();

		if (fields != null) {

			elementValues = new ArrayList<>();

			if (!CollectionUtils.isEmpty(fields.getField())) {

				for (Field field : fields.getField()) {

					if (field.isUseAdapter() == null || (field.isUseAdapter() != null && !field.isUseAdapter().booleanValue())) {
					
						proccesParam(adapter, element, response, field, elementValues);
					}
					else {
						
						processGroovyAdapter(adapter, element, response, field, elementValues);
					}
				}
			}
		}

		return elementValues;
	}
	
	private static void proccesParam(JsonGenericMangoV1 adapter, Element element, Map<String, String> response, Field field, List<ElementValue> elementValues) {
	
		Long elementTypeId = adapter.getElementTypeId();
		Long elementId = element.getId();
		
		if (field.getInput() != null) {
		
			Long elementTypeParamId = field.getInput().getElementTypeParamId();
			Long paramTypeId = field.getInput().getParamTypeId();
			String source = field.getSource();

			String value = response.get(source);

			if (!StringUtils.isEmpty(value)) {

				ElementValue elementValue = null;
				ElementTypeParam elementTypeParam = null;
				
			
				elementValue = new ElementValue();
			
				elementValue.setElementTypeId(elementTypeId);
				elementValue.setElementTypeParamId(elementTypeParamId);
				elementValue.setParamTypeId(paramTypeId);
				elementValue.setElementId(elementId);

				
				elementTypeParam = new ElementTypeParam();
				
				elementTypeParam.setElementTypeId(elementTypeId);
				elementTypeParam.setParamTypeId(paramTypeId);
				elementTypeParam.setId(elementTypeParamId);

				String strValue = getCustomValue(value, elementTypeParam,
						element.getElementType().getElementTypeParams());
				
				elementValue.setValue(strValue);
								
				elementValues.add(elementValue);
			}
		}
	}
	
	private static void processGroovyAdapter(JsonGenericMangoV1 adapter, Element element, Map<String, String> response, Field field, List<ElementValue> elementValues) {
		
		String groovyFile = PATH_GROOVY_INPUT_ADAPTERS + adapter.getGroovyAdapter().getFile();
		String source = field.getSource();
		String value = response.get(source);
		
		try {
			
			if (value != null && !StringUtils.isEmpty(groovyFile)) {
							
				log.debug("Processing param " + source + " = " + value + " with groovy adapter "
						+ adapter.getGroovyAdapter().getFile() + " for " + (new Key(element)).getSimpleValue()
						+ " ...");
				
				GroovyObject groovyObj = GroovyManager.getInstance().getGroovyObject(groovyFile);
							
//				EtdParamsAdapter etdParamsAdapter = new EtdParamsAdapter();
				
//				boolean res = etdParamsAdapter.groovy_test(element, value, elementValues);

				boolean res = (boolean) groovyObj.invokeMethod(source, new Object[] { element, value, elementValues });

				if (res) {
					
					log.debug("Success processed param " + source + " = " + value + " with groovy adapter "
							+ adapter.getGroovyAdapter().getFile() + " for " + (new Key(element)).getSimpleValue());
				}
				else {
					
					throw new ModuleException(null,
							groovyFile + "Process param " + source + " = " + value + " with groovy adapter "
									+ adapter.getGroovyAdapter().getFile() + " for "
									+ (new Key(element)).getSimpleValue() + " exists with errors");
				}

			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

	}
	
	public static List<Pair<AlarmConfig, Boolean>> getAlarmConfigs(@NonNull Map<String, String> response, JsonGenericMangoV1 adapter, Element element) {

		List<Pair<AlarmConfig, Boolean>> alarmConfigActivations = null;
		Alarms alarms = null;

		alarms = adapter.getAlarms();

		if (alarms != null) {

			alarmConfigActivations = new ArrayList<>();

			if (!CollectionUtils.isEmpty(alarms.getAlarm())) {

				for (Alarm alarm : alarms.getAlarm()) {

					if (!StringUtils.isAllEmpty(alarm.getSource(), alarm.getActiveValue()) && alarm.getAlarmConfigId() > 0) {

						String value = response.get(alarm.getSource());
						
						if (!StringUtils.isEmpty(value)) {
							
							AlarmConfig alarmConfig = EntitiesManager.getInstance().getAlarmConfig(alarm.getAlarmConfigId());
							
							if (alarmConfig != null) {
								
								alarmConfig.setElement(element);
								
								Boolean createAlarm = false;
								
								if (NumberUtils.isCreatable(alarm.getActiveValue()) && NumberUtils.isCreatable(value)) {
									
									createAlarm = (Double.parseDouble(alarm.getActiveValue()) == Double.parseDouble(value));
									
								}
								else {
									
									createAlarm = alarm.getActiveValue().equals(value);
								}
							
								alarmConfigActivations.add(new ImmutablePair<>(alarmConfig, createAlarm));
							}
						}
					}
				}
			}
		}

		return alarmConfigActivations;
	}
	
	public static ElementValue getElementValueStateDisconnected(Element element, ElementTypeParam paramState, JsonGenericMangoV1 adapter) {
	
		StateValue stateValue =  adapter.getState().getStateValue().stream().filter(e -> e.isDisconnected().booleanValue()).findFirst().orElse(null);
		
		if (stateValue != null) {
			
			ElementValue elementValueDisconnected = new ElementValue();
			
			elementValueDisconnected.setElementTypeId(element.getElementTypeId());
			elementValueDisconnected.setElementId(element.getId());
			elementValueDisconnected.setElementTypeParamId(paramState.getId());
			elementValueDisconnected.setParamTypeId(paramState.getParamTypeId());
			
			elementValueDisconnected.setValue(String.valueOf(stateValue.getSource()));
			
			return elementValueDisconnected;
		}
		
		return null;
	}
	
	public static ElementState getElementState(ElementValue elementValueState, JsonGenericMangoV1 adapter) {

		ElementState elementState = null;

		if (adapter.getState() != null && !CollectionUtils.isEmpty(adapter.getState().getStateValue())) {

			StateValue stateValue = adapter.getState().getStateValue().stream()
					.filter(e -> e.getSource() == Long.parseLong(elementValueState.getValue())).findFirst()
					.orElse(null);

			if (stateValue != null) {
				
				elementState = GenericEndpointHelper.createElementState(
						new Element(elementValueState.getElementTypeId(), elementValueState.getElementId()),
						stateValue.getInput());
			}
		}

		return elementState;
	}
	
	private static String getCustomValue(String value, ElementTypeParam elementTypeParamToFound,
			List<ElementTypeParam> elementTypeParams) {

		String strReturn = null;

		if (!StringUtils.isEmpty(value) && elementTypeParamToFound != null
				&& !CollectionUtils.isEmpty(elementTypeParams)) {

			ElementTypeParam foundElemTypeParam = elementTypeParams.stream().filter(
					elemTypeParam -> elemTypeParam.getElementTypeId().equals(elementTypeParamToFound.getElementTypeId())
							&& elemTypeParam.getParamTypeId().equals(elementTypeParamToFound.getParamTypeId())
							&& elemTypeParam.getId().equals(elementTypeParamToFound.getId()))
					.findFirst().orElse(null);
			
			if (!value.equalsIgnoreCase("NaN")) {
				
				strReturn = value;
			}

			if (foundElemTypeParam != null && foundElemTypeParam.getDataTypeId() == DataType.DATA_TYPE_INTEGER ||
					foundElemTypeParam.getDataTypeId() == DataType.DATA_TYPE_TIMESTAMP
					&& NumberUtils.isCreatable(value)) {

				strReturn = String.valueOf(Math.round(Double.valueOf(value)));
			}
		}

		return strReturn;
	}
}
