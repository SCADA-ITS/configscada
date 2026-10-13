package com.revenga.rits.back.io.controller.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.adapter.lib.model.JsonGenericMangoV1;
import com.revenga.rits.back.adapter.lib.model.JsonGenericMangoV1.Alarms.Alarm;
import com.revenga.rits.back.adapter.lib.model.JsonGenericMangoV1.Fields.Field;
import com.revenga.rits.back.adapter.lib.service.SessionAdapter;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementIOControllerModule;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.io.controller.module.mango.MangoEndpointHelper;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class MangoAdapter implements AdapterInterface<JsonGenericMangoV1, Element> {

	private static final String PREFIX_DATAPOINT = "DP";
	private static final String SEPARATOR = "_";
	private static final String ADAPTERS_MANGO_PATH = "/adapters/mango";
	
	private enum RequestType { FIELDS, ALARMS }

	// Map<sessionUID, Pair<elementTypeId, elementId>>
	private Map<String, Pair<Long, Long>> sessionUIDMap;
	private SessionAdapter sessionAdapter;
//	private long lastReloadSessionUIDMap;
	private Map<Pair<Long, Long>, String> adapters;
	
	private static MangoAdapter instance;

	public static MangoAdapter getInstance() {

		if (instance == null) {

			instance = new MangoAdapter();
		}

		return instance;
	}
	
	private MangoAdapter() {
		
		super();
		
		initialize();
	}

	private void initialize() {

		this.sessionUIDMap = Collections.synchronizedMap(new HashMap<>());
		this.sessionAdapter = new SessionAdapter();
		adapters = Collections.synchronizedMap(new HashMap<>());
		
		load(JsonGenericMangoV1.class);
	}

	@Override
	public void load(Class<JsonGenericMangoV1> adapterType) {

		List<String> adaptersFileName = ResourcesUtil.getFiles(ADAPTERS_MANGO_PATH);

		for (String adapterFileName : adaptersFileName) {

			Object adapter = adapterType.cast((sessionAdapter)
					.getAdapter(ADAPTERS_MANGO_PATH + File.separator + adapterFileName, JsonGenericMangoV1.class));
			
			Long[] elementIds = getElementIds(adapterType.cast((adapter)));
			
			if (ArrayUtils.isEmpty(elementIds)) {
			
				adapters.put(new ImmutablePair<>(adapterType.cast(adapter).getElementTypeId(), null), adapterFileName);
			}
			else {
				
				for (Long elementId : elementIds) {
				
					adapters.put(new ImmutablePair<>(adapterType.cast(adapter).getElementTypeId(), elementId), adapterFileName);
				}
			}
			
			
			loadSessionUIDs(adapterType.cast(adapter));
		}
		
//		lastReloadSessionUIDMap = Instant.now().getEpochSecond();
	}
	
	private Long[] getElementIds(JsonGenericMangoV1 adapter) {
		
		List<Long> elementIds = new ArrayList<>();
		
		String strArrayElementId = adapter.getArrayElementId();
		
		if (!StringUtils.isEmpty(strArrayElementId)) {
		
			String[] arrayElementId;
			
			arrayElementId = strArrayElementId.split(",");
			
			if (ArrayUtils.isEmpty(arrayElementId)) {
			
				arrayElementId = strArrayElementId.split(";");
			}
			
			if (!ArrayUtils.isEmpty(arrayElementId)) {
				
				for (String elementId : arrayElementId) {
					
					elementIds.add(Long.parseLong(elementId));
				}
			}
		}
		
		return !CollectionUtils.isEmpty(elementIds) ? elementIds.toArray(Long[]::new) : null;
	}
	
	private void loadSessionUIDs(JsonGenericMangoV1 adapter) {
		
		List<ElementValue> elementValues = EntitiesManager.getInstance().getElementValue(adapter.getElementTypeId(),
				adapter.getSessionUid().getElementTypeParamId(), adapter.getSessionUid().getParamTypeId());
		
		if (!CollectionUtils.isEmpty(elementValues)) {
			
			for (ElementValue elementValue : elementValues) {
			
				sessionUIDMap.put(elementValue.getValue(),
						new MutablePair<>(elementValue.getElementTypeId(), elementValue.getElementId()));
			}
		}
	}
	
	@Override
	public JsonGenericMangoV1 get(Element element) {
		
		JsonGenericMangoV1 adapter = null;
		String adapterName = adapters.get(new ImmutablePair<>(element.getElementTypeId(), element.getId()));
		
		if (!StringUtils.isEmpty(adapterName)) {
		
			adapter = (JsonGenericMangoV1) sessionAdapter.getAdapter(ADAPTERS_MANGO_PATH + File.separator + adapterName,
					JsonGenericMangoV1.class);
		}
		else {
			
			adapter = get(element.getElementTypeId());
		}
		
		return adapter;
	}
	
	private JsonGenericMangoV1 get(Long elementTypeId) {

		String adapterName = adapters.get(new ImmutablePair<>(elementTypeId, null)); 
		
		if (!StringUtils.isEmpty(adapterName)) {
		
			return (JsonGenericMangoV1) sessionAdapter.getAdapter(
					ADAPTERS_MANGO_PATH + File.separator + adapters.get(new ImmutablePair<>(elementTypeId, null)),
					JsonGenericMangoV1.class);
		}
		
		return null;
		
	}
	
	public synchronized Element get(String sessionUID, List<ElementIOControllerModule> elementsModule) {
		
		Element element = null;
		
//		if (Instant.now().getEpochSecond() - lastReloadSessionUIDMap > 60) {
//			
//			reloadSessionUIDs();
//		}
		
		if (!MapUtils.isEmpty(sessionUIDMap) && !CollectionUtils.isEmpty(elementsModule)) {
		
			Pair<Long, Long> elementPair = sessionUIDMap.get(sessionUID);
			
			if (elementPair != null) {
				
				ElementIOControllerModule elementModule = elementsModule.stream().filter(e -> e.getElementTypeId().equals(elementPair.getLeft())
						&& e.getElementId().equals(elementPair.getRight())).findFirst().orElse(null);
				
				if (elementModule != null) {
					
					element = elementModule.getElement();
				}
			}
		}
		
		return element;
	}
	
//	private void reloadSessionUIDs() {
//		
//		log.debug("Reloading sessionUID for element relations...");
//		
//		sessionUIDMap.clear();
//		
//		Iterator<Pair<Long, Long>> it = adapters.keySet().iterator();
//         
//        synchronized (adapters) {
//            
//        	while(it.hasNext()) {
//            	
//        		Pair<Long, Long> elementPair = it.next();
//        		
//        		Element element = new Element();
//        		
//        		element.setElementTypeId(elementPair.getLeft());
//        		element.setId(elementPair.getRight());
//        		
//            	JsonGenericMangoV1 adapter = get(element);
//            	
//            	loadSessionUIDs(adapter);
//            }
//        }
//        
//        lastReloadSessionUIDMap = Instant.now().getEpochSecond();
//        
//        log.debug("Reloading sessionUID element relations success");
//	}
	
	public String[] getDataPoints(Element element) {
		
		List<String> dataPoints = new ArrayList<>();
		JsonGenericMangoV1 adapter = get(element);
		
		if (adapter != null && adapter.getFields() != null &&
				!CollectionUtils.isEmpty(adapter.getFields().getField())) {
				
			for (Field field : adapter.getFields().getField()) {
				
				dataPoints.add(getDataSource(element) + SEPARATOR + field.getSource());
			}			
		}
		
		return dataPoints.stream().toArray(String[]::new);
	}
	
	public String[] getAlarmDataPoints(Element element) {
		
		List<String> alarmDataPoints = new ArrayList<>();
		JsonGenericMangoV1 adapter = get(element);
		
		if (adapter != null && adapter.getAlarms() != null &&
				!CollectionUtils.isEmpty(adapter.getAlarms().getAlarm())) {
				
			for (Alarm alarm : adapter.getAlarms().getAlarm()) {
				
				alarmDataPoints.add(getDataSource(element) + SEPARATOR + alarm.getSource());
			}			
		}
		
		return alarmDataPoints.stream().toArray(String[]::new);
	}
	
	public String getDataPoint(Element element, String source) {
		
		return getDataSource(element) + SEPARATOR + source;
	}
	
	public String getDataPointState(Element element) {
		
		String dataPointState = null;
		
		JsonGenericMangoV1 adapter = get(element);
		
		if (adapter != null && adapter.getState() != null) {
				
			dataPointState = getDataSource(element) + SEPARATOR + adapter.getState().getField();
		}
		
		return dataPointState;
	}
	
	public Field getFieldDataPointState(Element element) {
		
		Field fieldDataPointState = null;
		
		JsonGenericMangoV1 adapter = get(element);
		
		if (adapter != null && adapter.getState() != null) {
				
			String dataPointState = adapter.getState().getField();
			
			fieldDataPointState = adapter.getFields().getField().stream()
					.filter(e -> e.getSource().equals(dataPointState)).findFirst().orElse(null);
				
		}
		
		return fieldDataPointState;
	}
	
	public String getDataSource(Element element) {
		
		JsonGenericMangoV1 adapter = get(element);
		
		if (adapter != null) {
			
			String sessionUid = EntitiesManager.getInstance().getDataPoint(element,
					adapter.getSessionUid().getElementTypeParamId(), adapter.getSessionUid().getParamTypeId());
			
			return PREFIX_DATAPOINT + SEPARATOR + sessionUid;
		}
		
		return null;
	}
	
	public Map<String, String> getHash(String response) {
		
		Map<String, String> hashFinal = new HashMap<>();

		try {
			
			JsonNode rootArrayNode = (new ObjectMapper()).readTree(response);

			if (rootArrayNode.isArray()) {

				for (final JsonNode jsonNode : rootArrayNode) {

					Map<String, String> hashTemp = (new ObjectMapper()).convertValue(jsonNode,
							new TypeReference<Map<String, String>>() {
							});

					if (!MapUtils.isEmpty(hashTemp)) {
						
						for (Map.Entry<String, String> entryTemp : hashTemp.entrySet()) {
							
							if (!hashFinal.containsKey(entryTemp.getKey())) {
								
								hashFinal.put(entryTemp.getKey(), entryTemp.getValue());
							}
						}
					}
				}
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return hashFinal;
	}
	
	private Map<String, String> getHashFinal(String response, JsonGenericMangoV1 adapter, Element element, RequestType requestType) {
		
		Map<String, String> hashFinal = new HashMap<>();
		
		try {
			
			JsonNode rootArrayNode = (new ObjectMapper()).readTree(response);

			if (rootArrayNode.isArray()) {

				for (final JsonNode jsonNode : rootArrayNode) {

					Map<String, String> hashTemp = (new ObjectMapper()).convertValue(jsonNode,
							new TypeReference<Map<String, String>>() {
							});

					if (!MapUtils.isEmpty(hashTemp)) {

						Map<String, String> hashPartial = getBodyHash(hashTemp, adapter, element, requestType);
						
						for (Map.Entry<String, String> entryPartial : hashPartial.entrySet()) {
							
							if (!hashFinal.containsKey(entryPartial.getKey())) {
								
								hashFinal.put(entryPartial.getKey(), entryPartial.getValue());
							}
						}
					}
				}
			}
				
		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return hashFinal;
	}
	
	public List<ElementValue> getElementValues(Element element, String response) {

		List<ElementValue> elementValues = null;
		JsonGenericMangoV1 adapter;
		
		try {
			
			adapter = get(element);

			Map<String, String> hashFinal = getHashFinal(response, adapter, element, RequestType.FIELDS);
			
			if (!MapUtils.isEmpty(hashFinal)) {

				elementValues = MangoEndpointHelper.getElementValues(hashFinal, adapter, element);
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return elementValues;
	}
	
	public List<Pair<AlarmConfig, Boolean>> getAlarmConfigActivations(Element element, String response) {

		List<Pair<AlarmConfig, Boolean>> alarmConfigActivations = null;
		JsonGenericMangoV1 adapter;
		
		try {
			
			adapter = get(element);

			Map<String, String> hashFinal = getHashFinal(response, adapter, element, RequestType.ALARMS);
			
			if (!MapUtils.isEmpty(hashFinal)) {

				alarmConfigActivations = MangoEndpointHelper.getAlarmConfigs(hashFinal, adapter, element);
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return alarmConfigActivations;
	}
	
	private Map<String, String> getBodyHash(Map<String, String> hashTemp, JsonGenericMangoV1 adapter,
			Element element, RequestType requestType) {

		Map<String, String> hash = new HashMap<>();
		List<?> list = null;
		
		if (requestType == RequestType.FIELDS) {
			
			list = adapter.getFields().getField();
		}
		else if (requestType == RequestType.ALARMS) {
			
			list = adapter.getAlarms().getAlarm();
		}
		
		if (!CollectionUtils.isEmpty(list)) {
		
			for (Object obj : list) {
		
				String source = null;
				
				if (obj instanceof Field) {
					
					Field field = (Field) obj;
					
					source = field.getSource();
				}
				else if (obj instanceof Alarm) {
					
					Alarm alarm = (Alarm) obj;
					
					source = alarm.getSource();
				}
				
				if (!StringUtils.isEmpty(source)) {
				
					String dataPoint = MangoAdapter.getInstance().getDataPoint(element, source);
					
					if (hashTemp.containsKey(dataPoint)) {
						
						hash.put(source, hashTemp.get(dataPoint));
					}
				}
			}
		}

		return hash;
	}
}
