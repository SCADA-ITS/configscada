package com.revenga.rits.back.io.controller.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.adapter.lib.model.RtmsConfigV1;
import com.revenga.rits.back.adapter.lib.model.RtmsConfigV1.Fields;
import com.revenga.rits.back.adapter.lib.model.RtmsConfigV1.Fields.Field;
import com.revenga.rits.back.adapter.lib.service.SessionAdapter;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.DataType;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.data.core.util.GroovyManager;
import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.io.controller.module.ModuleException;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

import groovy.lang.GroovyObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class RtmsAdapter implements AdapterInterface<RtmsConfigV1, Element> {

	private static final String FILE_APP_PROPS = "application.properties";
	private static final String ADAPTERS_RTMS_PATH = "/adapters/rtms";
	private static final Long ZONE_ID_TYPE_PARAM = Long.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROPS,
			"module.rtms.request.measures.zone.id.type.param"));
	private static final Long ZONE_ID_PARAM_TYPE = Long.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROPS,
			"module.rtms.request.measures.zone.id.param.type"));
	
	private static final String PATH_GROOVY_INPUT_ADAPTERS = "io-controller/groovy/rtms/input_adapters/";

	private enum RequestType {
		FIELDS
	}

	// Map<sessionUID, Pair<elementTypeId, elementId>>
	private SessionAdapter sessionAdapter;
//	private long lastReloadSessionUIDMap;
	private Map<Pair<Long, Long>, String> adapters;

	private static RtmsAdapter instance;

	public static RtmsAdapter getInstance() {

		if (instance == null) {

			instance = new RtmsAdapter();
		}

		return instance;
	}
	
	private RtmsAdapter() {
		
		super();
		
		initialize();
	}

	private void initialize() {

		this.sessionAdapter = new SessionAdapter();
		adapters = Collections.synchronizedMap(new HashMap<>());
		
		load(RtmsConfigV1.class);
	}

	@Override
	public void load(Class<RtmsConfigV1> adapterType) {

		List<String> adaptersFileName = ResourcesUtil.getFiles(ADAPTERS_RTMS_PATH);

		for (String adapterFileName : adaptersFileName) {

			Object adapter = adapterType.cast((sessionAdapter)
					.getAdapter(ADAPTERS_RTMS_PATH + File.separator + adapterFileName, RtmsConfigV1.class));
			
				adapters.put(new ImmutablePair<>(adapterType.cast(adapter).getElementTypeId(), null), adapterFileName);
			}
		}
	
	@Override
	public RtmsConfigV1 get(Element element) {
		
		RtmsConfigV1 adapter = null;
		String adapterName = adapters.get(new ImmutablePair<>(element.getElementTypeId(), element.getId()));
		
		if (!StringUtils.isEmpty(adapterName)) {
		
			adapter = (RtmsConfigV1) sessionAdapter.getAdapter(ADAPTERS_RTMS_PATH + File.separator + adapterName,
					RtmsConfigV1.class);
		}
		else {
			
			adapter = get(element.getElementTypeId());
		}
		
		return adapter;
	}
	
	private RtmsConfigV1 get(Long elementTypeId) {

		String adapterName = adapters.get(new ImmutablePair<>(elementTypeId, null)); 
		
		if (!StringUtils.isEmpty(adapterName)) {
		
			return (RtmsConfigV1) sessionAdapter.getAdapter(
					ADAPTERS_RTMS_PATH + File.separator + adapters.get(new ImmutablePair<>(elementTypeId, null)),
					RtmsConfigV1.class);
		}
		
		return null;
		
	}
	
	private List<Map<String, String>> getHash(String response, RtmsConfigV1 adapter, Element element, RequestType requestType) {
		
		List<Map<String, String>> mapsList = new ArrayList<>();
		
		try {

			JsonNode rootArrayNode = (new ObjectMapper()).readTree(response);

			if (rootArrayNode.isArray()) {

				for (final JsonNode jsonNode : rootArrayNode) {
					Map<String, String> hashFinal = (new ObjectMapper()).convertValue(jsonNode,
							new TypeReference<Map<String, String>>() {
							});
					
					if (!MapUtils.isEmpty(hashFinal)) {
						
						mapsList.add(hashFinal);
					}
				}
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return mapsList;
	}
	
	public List<List<ElementValue>> getElementValues(Element element, String response) {

		List<List<ElementValue>> elemenValuesList = null;
		
		RtmsConfigV1 adapter;
		
		try {
			
			adapter = get(element);

			List<Map<String, String>> mapList = getHash(response, adapter, element, RequestType.FIELDS);
			
			if (!CollectionUtils.isEmpty(mapList)) {
				
				elemenValuesList = new ArrayList<>();
				
				for (Map<String, String> hashFinal : mapList) {
					
					if (!MapUtils.isEmpty(hashFinal)) {
						
						List<ElementValue> elementValues = null;
						
						Fields fields = null;

						fields = adapter.getFields();

						if (fields != null) {

							elementValues = new ArrayList<>();

							if (!CollectionUtils.isEmpty(fields.getField())) {

								for (Field field : fields.getField()) {
									
									proccesParam(adapter, element, hashFinal, field, elementValues);	
								}
							}
						}
						
						elemenValuesList.add(elementValues);
					}
				}
			}
			
		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return elemenValuesList;
	}
	
	public boolean processGroovyAdapter(final Element element, final List<List<ElementValue>> listElementValues, List<ElementValue> finalElementValues) {
		
		boolean res = false;
		
		try {
			
			RtmsConfigV1 adapter = get(element);
			
			if (adapter != null) {
				
				String groovyFile = PATH_GROOVY_INPUT_ADAPTERS + adapter.getGroovyAdapter().getFile();
				
				if (!StringUtils.isEmpty(groovyFile)) {
					
					log.debug("Processing params with groovy adapter "
							+ adapter.getGroovyAdapter().getFile() + " for " + (new Key(element)).getSimpleValue()
							+ " ...");
					
					GroovyObject groovyObj = GroovyManager.getInstance().getGroovyObject(groovyFile);
								
					res = (boolean) groovyObj.invokeMethod("content", new Object[] { element, listElementValues, finalElementValues });
					
//					InputAdapter_RTMS inputAdapterRTMS = new InputAdapter_RTMS(log);
//					
//					res = inputAdapterRTMS.content(element, listElementValues, finalElementValues);

					if (res) {
						
						log.debug("Success processed params with groovy adapter "
								+ adapter.getGroovyAdapter().getFile() + " for " + (new Key(element)).getSimpleValue());
					}
					else {
						
						throw new ModuleException(null,
								groovyFile + "Process params with groovy adapter "
										+ adapter.getGroovyAdapter().getFile() + " for "
										+ (new Key(element)).getSimpleValue() + " exists with errors");
					}
				}
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
		
		return res;
	}
	
	private void proccesParam(RtmsConfigV1 adapter, Element element, Map<String, String> response, Field field, List<ElementValue> elementValues) {
		
		Long elementTypeId = adapter.getElementTypeId();
		Long elementId = element.getId();
		String rtmsZoneId = response.get("zoneId");
		String detectorZoneId = null;

		detectorZoneId = EntitiesManager.getInstance().getElementValue(element, ZONE_ID_TYPE_PARAM, ZONE_ID_PARAM_TYPE)
				.getValue();

		if (detectorZoneId != null && rtmsZoneId != null && detectorZoneId.equals(rtmsZoneId)) {

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
	}
	
	private String getCustomValue(String value, ElementTypeParam elementTypeParamToFound,
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