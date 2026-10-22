package com.revenga.rits.back.io.controller.adapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.revenga.rits.back.adapter.lib.model.CLEnumDeviceStateType;
import com.revenga.rits.back.adapter.lib.model.CLEnumRequestType;
import com.revenga.rits.back.adapter.lib.model.CLTypeAlarm;
import com.revenga.rits.back.adapter.lib.model.CLTypeDevice;
import com.revenga.rits.back.adapter.lib.model.CitiAnswer;
import com.revenga.rits.back.adapter.lib.model.CitiEvent;
import com.revenga.rits.back.adapter.lib.model.CitilogConfigV1;
import com.revenga.rits.back.adapter.lib.model.CitilogConfigV1.CameraAlarms;
import com.revenga.rits.back.adapter.lib.model.CitilogConfigV1.CameraAlarms.CameraAlarm;
import com.revenga.rits.back.adapter.lib.model.CitilogConfigV1.DaiAlarms;
import com.revenga.rits.back.adapter.lib.model.CitilogConfigV1.DaiAlarms.DaiAlarm;
import com.revenga.rits.back.adapter.lib.model.CitilogConfigV1.DaiStates;
import com.revenga.rits.back.adapter.lib.model.CitilogConfigV1.DaiStates.DaiState;
import com.revenga.rits.back.adapter.lib.model.CitilogConfigV1.Requests.Request;
import com.revenga.rits.back.adapter.lib.service.SessionAdapter;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.io.controller.module.GenericEndpointHelper;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CitilogAdapter implements AdapterInterface<CitilogConfigV1, Element> {

	public static final String RESPONSE_TYPE_CITIANSWER = "CitiAnswer";
	public static final String RESPONSE_TYPE_CITIEVENT = "CitiEvent";

	private static final String ADAPTER_CITILOG_PATH = "/adapters/citilog/citilog_config.xml";

	private SessionAdapter sessionAdapter;
	private Map<String, Pair<Long, Long>> daisUIDMap;
	private Map<String, Pair<Long, Long>> camerasUIDMap;

	private static CitilogAdapter instance;

	private CitilogConfigV1 citilogConfigAdapter;

	public static CitilogAdapter getInstance() {

		if (instance == null) {

			instance = new CitilogAdapter();
		}

		return instance;
	}

	private CitilogAdapter() {

		super();

		initialize();
	}

	private void initialize() {

		this.camerasUIDMap = Collections.synchronizedMap(new HashMap<>());
		this.daisUIDMap = Collections.synchronizedMap(new HashMap<>());

		this.sessionAdapter = new SessionAdapter();

		load(CitilogConfigV1.class);
	}

	@Override
	public void load(Class<CitilogConfigV1> adapterType) {

		this.citilogConfigAdapter = CitilogConfigV1.class
				.cast((this.sessionAdapter).getAdapter(ADAPTER_CITILOG_PATH, CitilogConfigV1.class));

		loadCameras(adapterType.cast(this.citilogConfigAdapter));
		loadDais(adapterType.cast(this.citilogConfigAdapter));

	}

	private void loadCameras(CitilogConfigV1 adapter) {

		List<ElementValue> elementValues = EntitiesManager.getInstance().getElementValue(
				adapter.getCameraUid().getElementTypeId(), adapter.getCameraUid().getElementTypeParamId(),
				adapter.getCameraUid().getParamTypeId());

		if (!CollectionUtils.isEmpty(elementValues)) {

			for (ElementValue elementValue : elementValues) {

				camerasUIDMap.put(elementValue.getValue(),
						new MutablePair<>(elementValue.getElementTypeId(), elementValue.getElementId()));
			}
		}
	}
	
	private void loadDais(CitilogConfigV1 adapter) {

		List<ElementValue> elementValues = EntitiesManager.getInstance().getElementValue(
				adapter.getDaiUid().getElementTypeId(), adapter.getDaiUid().getElementTypeParamId(),
				adapter.getDaiUid().getParamTypeId());

		if (!CollectionUtils.isEmpty(elementValues)) {

			for (ElementValue elementValue : elementValues) {

				daisUIDMap.put(elementValue.getValue(),
						new MutablePair<>(elementValue.getElementTypeId(), elementValue.getElementId()));
			}
		}
	}

	public String getAlarmsRequest() {

		return getRequestXML(CLEnumRequestType.ALARMS);

	}

	public String getDevicesRequest() {

		return getRequestXML(CLEnumRequestType.DEVICES);
	}

	public String getKeepAliveRequest() {

		return getRequestXML(CLEnumRequestType.KEEP_ALIVE);
	}

	private String getRequestXML(CLEnumRequestType clEnumRequestType) {

		String xml = null;

		if (this.citilogConfigAdapter.getRequests() != null
				&& !CollectionUtils.isEmpty(this.citilogConfigAdapter.getRequests().getRequest())) {

			Request req = this.citilogConfigAdapter.getRequests().getRequest().stream()
					.filter(e -> e.getType() == clEnumRequestType).findFirst().orElse(null);

			if (req != null) {

				xml = req.getValue();
			}
		}

		return xml;
	}

	public List<String> splitResponse(@NonNull String xml) {

		List<String> xmls = new ArrayList<>();

		try {

			String strXml = "<root>" + xml + "</root>";

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

			DocumentBuilder db = dbf.newDocumentBuilder();

			InputStream is = new ByteArrayInputStream(strXml.getBytes());

			Document doc = db.parse(is);

			doc.getDocumentElement().normalize();

			NodeList rootNodeList = doc.getChildNodes();

			if (rootNodeList != null) {

				for (int i = 0; i < rootNodeList.getLength(); i++) {

					Node rootNode = rootNodeList.item(i);

					if (rootNode != null) {

						NodeList nodes = rootNode.getChildNodes();

						if (nodes != null) {

							for (int j = 0; j < nodes.getLength(); j++) {

								Node node = nodes.item(j);

								if (node != null) {

									xmls.add(nodeToString(node));
								}
							}
						}
					}
				}
			}

		} catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return xmls;
	}

	private static String nodeToString(Node node) throws TransformerException {

		StringWriter sw = new StringWriter();

		Transformer t = TransformerFactory.newInstance().newTransformer();
		t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		t.setOutputProperty(OutputKeys.INDENT, "yes");
		t.transform(new DOMSource(node), new StreamResult(sw));

		return sw.toString();
	}

	public Class<?> getResponseType(String xml) {

		Class<?> clazz = null;

		if (!StringUtils.isEmpty(xml)) {

			if (xml.contains(CitiAnswer.class.getSimpleName())) {

				clazz = CitiAnswer.class;
			} else if (xml.contains(CitiEvent.class.getSimpleName())) {

				clazz = CitiEvent.class;
			}
		}

		return clazz;
	}

	public CitiAnswer getCitiAnswer(String xml) {

		CitiAnswer citiAnswer = null;

		if (!StringUtils.isEmpty(xml)) {

			try {

				JAXBContext context = JAXBContext.newInstance(CitiAnswer.class);
				InputStream is = new ByteArrayInputStream(xml.getBytes());
				Object citiAnswerObj = context.createUnmarshaller().unmarshal(is);

				if (citiAnswerObj instanceof CitiAnswer) {

					citiAnswer = (CitiAnswer) citiAnswerObj;
				}

			} catch (JAXBException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}

		return citiAnswer;
	}

	public CitiEvent getCitiEvent(String xml) {

		CitiEvent citiEvent = null;

		if (!StringUtils.isEmpty(xml)) {

			try {

				JAXBContext context = JAXBContext.newInstance(CitiEvent.class);
				InputStream is = new ByteArrayInputStream(xml.getBytes());
				Object citiEventObj = context.createUnmarshaller().unmarshal(is);

				if (citiEventObj instanceof CitiEvent) {

					citiEvent = (CitiEvent) citiEventObj;
				}

			} catch (JAXBException e) {

				log.error(e.getMessage());
				log.debug(ExceptionUtils.getStackTrace(e));
			}
		}

		return citiEvent;
	}

	@Override
	public CitilogConfigV1 get(Element entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public synchronized Element getCamera(String cameraUID, List<Element> cameras) {

		Element camera = null;

		if (!MapUtils.isEmpty(camerasUIDMap) && !CollectionUtils.isEmpty(cameras)) {

			Pair<Long, Long> elementPair = camerasUIDMap.get(cameraUID);

			if (elementPair != null) {

				camera = cameras.stream().filter(e -> e.getElementTypeId().equals(elementPair.getLeft())
						&& e.getId().equals(elementPair.getRight())).findFirst().orElse(null);
			}
		}

		return camera;
	}
	
	public synchronized Element getDai(String daiUID, List<Element> dais) {

		Element dai = null;

		if (!MapUtils.isEmpty(daisUIDMap) && !CollectionUtils.isEmpty(dais)) {

			Pair<Long, Long> elementPair = daisUIDMap.get(daiUID);

			if (elementPair != null) {

				dai = dais.stream().filter(e -> e.getElementTypeId().equals(elementPair.getLeft())
						&& e.getId().equals(elementPair.getRight())).findFirst().orElse(null);
			}
		}

		return dai;
	}

	public List<Pair<AlarmConfig, Boolean>> getAlarmConfigs(CLTypeAlarm citilogAlarm, Element element) {

		List<Pair<AlarmConfig, Boolean>> alarmConfigActivations = null;
		CameraAlarms cameraAlarms = this.citilogConfigAdapter.getCameraAlarms();

		try {

			if (cameraAlarms != null) {

				alarmConfigActivations = new ArrayList<>();

				if (!CollectionUtils.isEmpty(cameraAlarms.getCameraAlarm())) {

					CameraAlarm alarmMatched = cameraAlarms.getCameraAlarm().stream().filter(
							e -> e.getAlarm().equals(citilogAlarm.getType()) && Long.valueOf(e.getLaneId()).equals(Long.valueOf(citilogAlarm.getLaneId())))
							.findFirst().orElse(null);

					if (alarmMatched != null) {

						AlarmConfig alarmConfig = EntitiesManager.getInstance()
								.getAlarmConfig(Long.valueOf(alarmMatched.getAlarmConfigId()));

						if (alarmConfig != null) {

							alarmConfig.setElement(element);

							alarmConfigActivations
									.add(new ImmutablePair<>(alarmConfig, (citilogAlarm.getEndTime() == null)));
						}
					}
				}
			}

		} catch (IllegalArgumentException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}

		return alarmConfigActivations;
	}

	public List<Pair<AlarmConfig, Boolean>> getAlarmConfigs(CLTypeDevice citilogDevice, Element element) {

		List<Pair<AlarmConfig, Boolean>> alarmConfigActivations = null;
		DaiAlarms daiAlarms = this.citilogConfigAdapter.getDaiAlarms();

		if (daiAlarms != null && !CollectionUtils.isEmpty(daiAlarms.getDaiAlarm())) {

			CLEnumDeviceStateType deviceStateType = Arrays.asList(CLEnumDeviceStateType.values()).stream()
					.filter(e -> citilogDevice.getState() != null && e.value().equals(citilogDevice.getState().value())).findFirst().orElse(null);

			if (deviceStateType != null) {
				
				if (deviceStateType != CLEnumDeviceStateType.OK) {
					
					alarmConfigActivations = getAlarmConfigsActivated(daiAlarms, element, citilogDevice);
					
					if (alarmConfigActivations == null) {
					
						alarmConfigActivations = getAlarmConfigsDeactivated(daiAlarms);
					}
				}
				else {
					
					alarmConfigActivations = getAlarmConfigsDeactivated(daiAlarms);
				}
			}
		}

		return alarmConfigActivations;
	}
	
	public ElementState getElementState(CLTypeDevice citilogDevice, Element element) {
		
		ElementState elementState = null;
		
		DaiStates daiStates = this.citilogConfigAdapter.getDaiStates();

		if (daiStates != null && !CollectionUtils.isEmpty(daiStates.getDaiState())) {

			CLEnumDeviceStateType deviceStateType = Arrays.asList(CLEnumDeviceStateType.values()).stream()
					.filter(e -> citilogDevice.getState() != null && e.value().equals(citilogDevice.getState().value())).findFirst().orElse(null);
			
			if (deviceStateType != null) {
				
				DaiState stateMatched = daiStates.getDaiState().stream().filter(e -> e.getState() != null && e.getState().equals(citilogDevice.getState()))
						.findFirst().orElse(null);
				
				if (stateMatched != null) {
					
					elementState = GenericEndpointHelper.createElementState(element, stateMatched.getElementStateId());
				}
			}
		}
		
		return elementState;
	}
	
	private List<Pair<AlarmConfig, Boolean>> getAlarmConfigsActivated(DaiAlarms daiAlarms, Element element, CLTypeDevice citilogDevice) {

		List<Pair<AlarmConfig, Boolean>> alarmConfigActivations = null;
		
		DaiAlarm stateMatched = daiAlarms.getDaiAlarm().stream().filter(e -> e.getState().equals(citilogDevice.getState()))
				.findFirst().orElse(null);
		
		if (stateMatched != null) {
			
			AlarmConfig alarmConfig = EntitiesManager.getInstance()
					.getAlarmConfig(Long.valueOf(stateMatched.getAlarmConfigId()));

			if (alarmConfig != null) {

				alarmConfig.setElement(element);
				
				alarmConfigActivations = new ArrayList<>();

				alarmConfigActivations.add(new ImmutablePair<>(alarmConfig, true));
			}
		}
		
		return alarmConfigActivations;
	}
	
	private List<Pair<AlarmConfig, Boolean>> getAlarmConfigsDeactivated(DaiAlarms daiAlarms) {

		List<Pair<AlarmConfig, Boolean>> alarmConfigActivations = null;
		
		for (DaiAlarm daiAlarm : daiAlarms.getDaiAlarm()) {

			AlarmConfig alarmConfig = EntitiesManager.getInstance()
					.getAlarmConfig(Long.valueOf(daiAlarm.getAlarmConfigId()));
			
			if (alarmConfig != null) {
				
				if (alarmConfigActivations == null) {
					
					alarmConfigActivations = new ArrayList<>();
				}
				
				alarmConfigActivations.add(new ImmutablePair<>(alarmConfig, false));
			}
		}
		
		return alarmConfigActivations;
	}
}
