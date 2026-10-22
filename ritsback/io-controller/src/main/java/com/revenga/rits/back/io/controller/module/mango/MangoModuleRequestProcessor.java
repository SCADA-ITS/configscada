package com.revenga.rits.back.io.controller.module.mango;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.adapter.lib.model.JsonGenericMangoV1;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementIOControllerModule;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementType;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.LogLevel;
import com.revenga.rits.back.data.core.model.LogSubtype;
import com.revenga.rits.back.data.core.model.LogType;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.entities.lib.helper.AuditHelper;
import com.revenga.rits.back.io.controller.adapter.MangoAdapter;
import com.revenga.rits.back.io.controller.module.GenericEndpointHelper;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class MangoModuleRequestProcessor extends MangoModuleProcessor {

	private static final String FILE_APP_PROPS = "application.properties";
	private static final String REQUEST_STATES_FUNCTION = "Origin: RequestStates function";
	private static final Long PARAM_MAX_DATAPOINTS_PER_REQUEST = Long.valueOf(ConfigProperty.getInstance()
			.getProperty(FILE_APP_PROPS, "module.mango.request.max_datapoints_per_request"));

	@Getter
	boolean connected = true;

	List<Element> elements = new ArrayList<>();
	Map<String, Element> dataPointStates = new HashMap<>();
	Integer maxDataPointsPerRequest = 0;

	public MangoModuleRequestProcessor(MangoModule mangoModule) {

		super(mangoModule);

		initialize();
	}

	private void initialize() {

		if (!CollectionUtils.isEmpty(mangoModule.getIoControllerModule().getModuleElements())) {

			for (ElementIOControllerModule moduleElement : mangoModule.getIoControllerModule().getModuleElements()) {

				if (moduleElement.getElement() != null) {

					elements.add(moduleElement.getElement());
				}
			}
		}

		if (!CollectionUtils.isEmpty(elements)) {

			for (Element element : elements) {

				dataPointStates.put(MangoAdapter.getInstance().getDataPointState(element), element);
			}
		}

		maxDataPointsPerRequest = Integer.valueOf(EntitiesManager.getInstance()
				.getIOControllerModuleValue(mangoModule.getIoControllerModule(), PARAM_MAX_DATAPOINTS_PER_REQUEST)
				.getValue());
	}

	public ElementState requestState(Element element) {

		String response = mangoDriver.request(new String[] { MangoAdapter.getInstance().getDataPointState(element) });
		JsonGenericMangoV1 adapter;
		ElementState elementState = null;
		ElementState disconnectedElementState = null;

		adapter = MangoAdapter.getInstance().get(element);

		disconnectedElementState = GenericEndpointHelper.createElementState(element,
				GenericEndpointHelper.getDisconnectedStateId(element));

		if (!StringUtils.isEmpty(response) && !StringUtils.equals(response, "[]") && adapter != null) {

			log.debug("[" + mangoModule.getName() + "](" + (++responseCount) + ")[RequestState - "
					+ (new Key(element)).getSimpleValue() + "] << " + response);

			elementState = MangoEndpointHelper.getElementState(response, adapter, element);
		} else {

			log.warn("[" + mangoModule.getName() + "][" + (new Key(element)).getSimpleValue()
					+ "] is not responding state request");

			elementState = disconnectedElementState;
		}

		return elementState;
	}

	public List<ElementState> requestStates() {

		int currentDatapointPartial = 0;
		List<ElementState> elementStates = new ArrayList<>();

		Map<String, Element> dataPointStatesPartial = new HashMap<>();

		if (!MapUtils.isEmpty(this.dataPointStates)) {

			if (maxDataPointsPerRequest > 0) {

				log.debug(
						"Request for all states (" + this.dataPointStates.size() + ") split into "
								+ ((this.dataPointStates.size() / maxDataPointsPerRequest)
										+ ((this.dataPointStates.size() % maxDataPointsPerRequest > 0) ? 1 : 0))
								+ " parts");

				int i = 0;

				for (Map.Entry<String, Element> dataPointState : this.dataPointStates.entrySet()) {

					if (currentDatapointPartial++ < maxDataPointsPerRequest) {

						dataPointStatesPartial.put(dataPointState.getKey(), dataPointState.getValue());

						if (currentDatapointPartial == maxDataPointsPerRequest
								|| (i == this.dataPointStates.size() - 1)) {

							elementStates.addAll(requestStates(dataPointStatesPartial));

							dataPointStatesPartial.clear();
							currentDatapointPartial = 0;
						}
					}

					i++;
				}
			} else {

				elementStates.addAll(requestStates(dataPointStates));
			}
			
			this.connected = CollectionUtils.isNotEmpty(elementStates);
		}
				
		return elementStates;
	}

	private List<ElementState> requestStates(Map<String, Element> dataPointStatesPartial) {

		List<ElementState> elementStates = Collections.emptyList();

		if (!MapUtils.isEmpty(dataPointStatesPartial)) {

			Instant instant = Instant.now();

			log.debug("[" + mangoModule.getName() + "][RequestStates] Sending request for "
					+ dataPointStatesPartial.size() + " measures => " + dataPointStatesPartial.keySet().toString());

			String response = mangoDriver
					.request(new ArrayList<String>(dataPointStatesPartial.keySet()).toArray(String[]::new));

			String[] args = {mangoModule.getName(), null, REQUEST_STATES_FUNCTION};
			
			if (!StringUtils.isEmpty(response) && !StringUtils.equals(response, "[]")) {

				AuditHelper.Audit(LogType.LOG_TYPE_PROCESSES, LogSubtype.LOG_SUBTYPE_PROCESS_FEATURE_UP, User.USER_SYSTEM,
						LogLevel.INFO, args);

				log.debug("[" + mangoModule.getName() + "](" + (++responseCount) + ")[RequestStates] ("
						+ Duration.between(instant, Instant.now()).toMillis() + " ms.) << " + response);

				elementStates = MangoEndpointHelper.getElementStates(response,
						dataPointStatesPartial.values().stream().collect(Collectors.toList()));

				log.debug("[" + mangoModule.getName() + "][RequestStates] Equipment that changes state = "
						+ elementStates.size());
			} else {

				AuditHelper.Audit(LogType.LOG_TYPE_PROCESSES, LogSubtype.LOG_SUBTYPE_PROCESS_FEATURE_DOWN, User.USER_SYSTEM,
						LogLevel.INFO, args);

				log.warn("[" + mangoModule.getName() + "][RequestStates] ("
						+ +Duration.between(instant, Instant.now()).toMillis()
						+ " ms.) is not responding states request");

				elementStates = new ArrayList<>();

				for (Map.Entry<String, Element> dataPointState : dataPointStatesPartial.entrySet()) {

					Element element = dataPointState.getValue();

					if (element != null) {

						elementStates.add(GenericEndpointHelper.createElementState(element,
								element.getElementType().getDisconnectedElementTypeStateId()));
					}
				}
			}
		}

		return elementStates;
	}

	public List<ElementValue> requestMeasures(Element element) {

		List<ElementValue> elementValues = Collections.emptyList();
		ElementType elementType = element.getElementType();
		String[] dataPoints = MangoAdapter.getInstance().getDataPoints(element);

		if (this.connected && !ArrayUtils.isEmpty(dataPoints)) {

			if (elementType == null) {

				elementType = EntitiesManager.getInstance().getElementType(element.getElementTypeId());
			}

			Instant instant = Instant.now();

			String response = mangoDriver.request(dataPoints);

			if (!StringUtils.isEmpty(response) && !StringUtils.equals(response, "[]")) {

				log.debug("[" + mangoModule.getName() + "](#" + (++responseCount) + ")[RequestMeasures - "
						+ (new Key(element)).getSimpleValue() + "] ("
						+ Duration.between(instant, Instant.now()).toMillis() + " ms.) << " + response);

				elementValues = MangoAdapter.getInstance().getElementValues(element, response);

				if (CollectionUtils.isEmpty(elementValues)) {

					log.warn("[" + mangoModule.getName() + "][RequestMeasures - " + (new Key(element)).getSimpleValue()
							+ "] (" + Duration.between(instant, Instant.now()).toMillis()
							+ " ms.) No response. Check equipment configuration in Mango for equipment "
							+ element.getAlias());
				}
			} else {

				log.warn("[" + mangoModule.getName() + "][RequestMeasures - " + (new Key(element)).getSimpleValue()
						+ "] (" + Duration.between(instant, Instant.now()).toMillis()
						+ " ms.) is not responding measures request");
			}
		}

		return elementValues;
	}

	public List<Pair<AlarmConfig, Boolean>> requestAlarms(Element element) {

		List<Pair<AlarmConfig, Boolean>> alarmConfigActivations = Collections.emptyList();
		ElementType elementType = element.getElementType();
		String[] alarmDataPoints = MangoAdapter.getInstance().getAlarmDataPoints(element);

		if (this.connected && !ArrayUtils.isEmpty(alarmDataPoints)) {

			if (elementType == null) {

				elementType = EntitiesManager.getInstance().getElementType(element.getElementTypeId());
			}

			Instant instant = Instant.now();

			String response = mangoDriver.request(alarmDataPoints);

			if (!StringUtils.isEmpty(response) && !StringUtils.equals(response, "[]")) {

				log.debug("[" + mangoModule.getName() + "](" + (++responseCount) + ")[RequestAlarms - "
						+ (new Key(element)).getSimpleValue() + "] ("
						+ Duration.between(instant, Instant.now()).toMillis() + " ms.) << " + response);

				alarmConfigActivations = MangoAdapter.getInstance().getAlarmConfigActivations(element, response);

				if (CollectionUtils.isEmpty(alarmConfigActivations)) {

					log.warn("[" + mangoModule.getName() + "][RequestAlarms - " + (new Key(element)).getSimpleValue()
							+ "] (" + Duration.between(instant, Instant.now()).toMillis()
							+ " ms.) No response. Check equipment configuration in Mango for equipment "
							+ element.getAlias());
				}
			} else {

				log.warn("[" + mangoModule.getName() + "][RequestAlarms - " + (new Key(element)).getSimpleValue()
						+ "] (" + Duration.between(instant, Instant.now()).toMillis()
						+ " ms.) is not responding alarms request");
			}
		}

		return alarmConfigActivations;
	}

	public List<ElementState> requestKeepAlive() {

		List<ElementState> elementStates = new ArrayList<>();
		Instant instant = Instant.now();

		String response = mangoDriver.keepAlive();

		if (!StringUtils.isEmpty(response) && !StringUtils.equals(response, "[]")) {

			log.debug("[" + mangoModule.getName() + "][KeepAlive] ("
					+ Duration.between(instant, Instant.now()).toMillis() + " ms.) << " + response);
			
			if (!this.connected) {
				
				AuditHelper.Audit(LogType.LOG_TYPE_PROCESSES, LogSubtype.LOG_SUBTYPE_PROCESS_UP, User.USER_SYSTEM,
						LogLevel.INFO, mangoModule.getName());
				
			}
			
			this.connected = true;
			
		} else {

			if (this.connected) {
				
				this.connected = false;
				
 				AuditHelper.Audit(LogType.LOG_TYPE_PROCESSES, LogSubtype.LOG_SUBTYPE_PROCESS_DOWN, User.USER_SYSTEM,
						LogLevel.INFO, mangoModule.getName());
 				
			}

			log.warn("[" + mangoModule.getName() + "][KeepAlive] ("
					+ Duration.between(instant, Instant.now()).toMillis() + " ms.) is not responding request");

			if (!MapUtils.isEmpty(this.dataPointStates)) {

				for (Map.Entry<String, Element> dataPointState : this.dataPointStates.entrySet()) {

					Element element = dataPointState.getValue();

					if (element != null) {

						elementStates.add(GenericEndpointHelper.createElementState(element,
								element.getElementType().getDisconnectedElementTypeStateId()));
					}
				}
			}
		}

		return elementStates;
	}
	
	public Element addElement(Element element) {

		dataPointStates.put(MangoAdapter.getInstance().getDataPointState(element), element);
		
		MangoAdapter.getInstance().addSessionUID(element);
		
		return this.elements.add(element) ? element : null;
	}
	
	public Element deleteElement(Element element) {
		
		dataPointStates.remove(MangoAdapter.getInstance().getDataPointState(element));
		
		MangoAdapter.getInstance().deleteSessionUID(element);
		
		return this.elements.removeIf(
				e -> e.getElementTypeId().equals(element.getElementTypeId()) && e.getId().equals(element.getId()))
						? element
						: null;
	}
}
