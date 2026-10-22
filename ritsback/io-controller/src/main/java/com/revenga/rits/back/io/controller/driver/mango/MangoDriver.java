package com.revenga.rits.back.io.controller.driver.mango;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.adapter.lib.model.JsonGenericMangoV1;
import com.revenga.rits.back.adapter.lib.model.JsonGenericMangoV1.State.StateValue;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.IOControllerModule;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.io.controller.adapter.MangoAdapter;
import com.revenga.rits.back.io.controller.driver.DriverException;
import com.revenga.rits.back.io.controller.driver.GenericDriver;
import com.revenga.rits.back.io.controller.module.GenericEndpointHelper;
import com.revenga.rits.back.io.controller.module.GenericModule;
import com.revenga.rits.back.io.controller.module.mango.MangoEndpointHelper;
import com.revenga.rits.back.io.controller.service.ControllerManager;
import com.sun.net.httpserver.Headers;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2
public class MangoDriver extends GenericDriver {
	
	// region Constants
		private static final String FILE_APP_PROP = "application.properties";
		private static final Long PARAM_SUBSCRIPTOR_ACTIVE = Long
				.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROP, "driver.mango.subscriptor.active"));
		private static final Long PARAM_SUBSCRIPTOR_USER_AGENT = Long
				.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROP, "driver.mango.subscriptor.user_agent"));
		private static final Long PARAM_SUBSCRIPTOR_SESSION_UID = Long
				.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROP, "driver.mango.subscriptor.session_uid"));
		private static final Long PARAM_SUBSCRIPTOR_PATH = Long
				.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROP, "driver.mango.subscriptor.path"));
		private static final Long PARAM_SUBSCRIPTOR_PORT = Long
				.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROP, "driver.mango.subscriptor.port"));
		private static final Long PARAM_SUBSCRIPTOR_IP_ADDRESSES_ALLOWED = Long
				.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROP, "driver.mango.subscriptor.ip_addresses_allowed"));
		private static final Long PARAM_HTTP_POOL_THREAD_SIZE = Long
				.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROP, "driver.mango.subscriptor.http_pool_thread_size"));
		private static final Long PARAM_PUBLISHER_URL = Long
				.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROP, "driver.mango.publisher.url"));
		private static final Long PARAM_PUBLISHER_URL_LOGIN = Long
				.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROP, "driver.mango.publisher.url_login"));
		private static final Long PARAM_PUBLISHER_USER = Long
				.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROP, "driver.mango.publisher.user"));
		private static final Long PARAM_PUBLISHER_PASSWORD = Long
				.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROP, "driver.mango.publisher.password"));
		private static final Long PARAM_PUBLISHER_REQUEST_URL = Long
				.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROP, "driver.mango.publisher.request_url"));
		private static final Long PARAM_PUBLISHER_REQUEST_METHOD_URL = Long.valueOf(
				ConfigProperty.getInstance().getProperty(FILE_APP_PROP, "driver.mango.publisher.request_method_url"));
		private static final Long PARAM_PUBLISHER_SEND_URL = Long
				.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROP, "driver.mango.publisher.send_url"));
		private static final Long PARAM_PUBLISHER_SEND_METHOD_URL = Long.valueOf(
				ConfigProperty.getInstance().getProperty(FILE_APP_PROP, "driver.mango.publisher.send_method_url"));
		private static final Long PARAM_PUBLISHER_KEEPALIVE_URL = Long
				.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROP, "driver.mango.publisher.keepalive_url"));
		private static final Long PARAM_PUBLISHER_KEEPALIVE_METHOD_URL = Long.valueOf(
				ConfigProperty.getInstance().getProperty(FILE_APP_PROP, "driver.mango.publisher.keepalive_method_url"));
		
	// endregion
		
	// region attributes
	private boolean subscriptorActive;
	private String subscriptorUserAgent;
	private String subscriptorSessionUid;
	private String subscriptorPath;
	private Integer subscriptorPort;
	private String subscriptorIpAddressesAllowed;
	private Integer httpPoolThreadSize;
	
	private String publisherUrl;
	private String publisherUrlLogin;
	private String publisherUser;
	private String publisherPassword;
	
	private String publisherRequestUrl;
	private String publisherRequestMethodUrl;
	private String publisherSendUrl;
	private String publisherSendMethodUrl;
	private String publisherKeepAliveUrl;
	private String publisherKeepAliveMethodUrl;
	
	private MangoSubscriptor mangoSubscriptor;
	private MangoHttpSession mangoHttpSession;
	
	private int rxCount;
	private int txCount;
	
	public MangoDriver(IOControllerModule ioControllerModule) {
		
		super(ioControllerModule);
		
		this.subscriptorActive = StringUtils.equalsAny(getValue(PARAM_SUBSCRIPTOR_ACTIVE), "1", "true");
		this.subscriptorUserAgent = getValue(PARAM_SUBSCRIPTOR_USER_AGENT);
		this.subscriptorSessionUid = getValue(PARAM_SUBSCRIPTOR_SESSION_UID);
		this.subscriptorPath = getValue(PARAM_SUBSCRIPTOR_PATH); 
		this.subscriptorPort = Integer.valueOf(getValue(PARAM_SUBSCRIPTOR_PORT));
		this.subscriptorIpAddressesAllowed = getValue(PARAM_SUBSCRIPTOR_IP_ADDRESSES_ALLOWED);
		this.httpPoolThreadSize = Integer.valueOf(getValue(PARAM_HTTP_POOL_THREAD_SIZE));
		this.publisherUrl = getValue(PARAM_PUBLISHER_URL);
		this.publisherUrlLogin = getValue(PARAM_PUBLISHER_URL_LOGIN);
		this.publisherUser = getValue(PARAM_PUBLISHER_USER); 
		this.publisherPassword = getValue(PARAM_PUBLISHER_PASSWORD);
		this.publisherRequestUrl = getValue(PARAM_PUBLISHER_REQUEST_URL);
		this.publisherRequestMethodUrl = getValue(PARAM_PUBLISHER_REQUEST_METHOD_URL);
		this.publisherSendUrl = getValue(PARAM_PUBLISHER_SEND_URL);
		this.publisherSendMethodUrl = getValue(PARAM_PUBLISHER_SEND_METHOD_URL);
		this.publisherKeepAliveUrl = getValue(PARAM_PUBLISHER_KEEPALIVE_URL);
		this.publisherKeepAliveMethodUrl = getValue(PARAM_PUBLISHER_KEEPALIVE_METHOD_URL);
		
		mangoHttpSession = new MangoHttpSession(this);
		
		this.rxCount = 0;
		this.txCount = 0;
	}

	@Override
	public void start() {
		
		mangoSubscriptor = new MangoSubscriptor(this);
	}

	public void receive(String remoteAddress, Headers headers, String body) {
		
		String sessionUid;
		String userAgent;
		JsonGenericMangoV1 adapter = null;
		
		try {
			
			sessionUid = headers.getFirst(subscriptorSessionUid);
			
			if (StringUtils.isEmpty(sessionUid)) {
				
				throw new DriverException(this, "sessionUid is empty");
			}
			
			userAgent = headers.getFirst(subscriptorUserAgent);
			
			if (StringUtils.isEmpty(userAgent)) {
				
				throw new DriverException(this, "userAgent is empty");
			}
			
			Element element = MangoAdapter.getInstance().get(sessionUid, getIoControllerModule().getModuleElements());
					
			if (element == null) {
				
				throw new DriverException(this, "Not found element for sessionUid = " + sessionUid);
			}
			
			adapter = MangoAdapter.getInstance().get(element);
			
			if (adapter == null) {
				
				throw new DriverException(this, "Can not load adapter for sessionUid = " + sessionUid);
			}
			
			JsonNode jsonNodeBody = (new ObjectMapper()).readTree(body);
						
			log.debug("[Subscriptor " + remoteAddress + "](" + (++rxCount) + ")[" + (new Key(element)).getSimpleValue() + "] << " + jsonNodeBody.toString());
			
			ObjectMapper mapper = new ObjectMapper();
			
			Map<String, String> hash = mapper.convertValue(jsonNodeBody, new TypeReference<Map<String, String>>(){});
			
			List<ElementValue> elementValues = MangoEndpointHelper.getElementValues(hash, adapter, element);
			
			List<Pair<AlarmConfig, Boolean>> alarmConfigActivations = MangoEndpointHelper.getAlarmConfigs(hash, adapter, element);
					
			GenericModule genericModule = ControllerManager.getInstance().getControllerService().getRouteService().getModule(element);
			
			if (genericModule == null) {
				
				throw new DriverException(this, "Can not load GenericModule for " + (new Key(element)).getSimpleValue());
			}
			
			sendState(genericModule, adapter, hash, element);
			
			receivedMeasures(element, elementValues);
			
			receivedAlarms(element, alarmConfigActivations);		
				
		} catch (Exception e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	private void receivedMeasures(Element element, List<ElementValue> elementValues) {
		
		GenericModule genericModule = ControllerManager.getInstance().getControllerService().getRouteService().getModule(element);
		
		if (genericModule != null) {
			
			genericModule.receiveMeasures(element, elementValues);
		}
	}
	
	private void receivedAlarms(Element element, List<Pair<AlarmConfig, Boolean>> alarmConfigActivations) {
		
		GenericModule genericModule = ControllerManager.getInstance().getControllerService().getRouteService().getModule(element);
		
		if (genericModule != null) {
			
			genericModule.receiveAlarms(element, alarmConfigActivations);
		}
	}
	
	private void sendState(GenericModule genericModule, JsonGenericMangoV1 adapter, Map<String, String> hash, Element element) {
		
		String fieldStatus = adapter.getState().getField();
		
		String valueFieldStatus = hash.get(fieldStatus);
		
		if (!StringUtils.isEmpty(valueFieldStatus)) {
			
			StateValue stateReceived = adapter.getState().getStateValue().stream()
					.filter(e -> e.getSource() == Double.valueOf(valueFieldStatus).longValue()).findFirst()
					.orElse(null);
			
			if (stateReceived != null) {
			
				ElementState stateToSend = GenericEndpointHelper.createElementState(element, stateReceived.getInput());
				
				genericModule.receiveState(stateToSend);
			}
		}
	}
	
	public String keepAlive() {
		
		return mangoHttpSession.send(publisherKeepAliveUrl, publisherKeepAliveMethodUrl, null);
	}
	
	public String request(String[] datapointsXid) {
		
		return mangoHttpSession.send(getRequestUrl(publisherRequestUrl, datapointsXid), publisherRequestMethodUrl, null);
	}
	
	public void send(String body) {
		
		String response = mangoHttpSession.send(publisherSendUrl, publisherSendMethodUrl, body);
		
		if (!StringUtils.isEmpty(response)) {
			
			log.debug("[" + this.getClass().getSimpleName() + "](" + (++txCount) + ") >> " + body);
		}
	}
	
	private String getRequestUrl(String request, String[] datapointsXid) {

		String payload = null;

		if (request != null && !ArrayUtils.isEmpty(datapointsXid)) {
			
			String xidsValueStr = String.join("%2C", datapointsXid);
			payload = request.replace("{xids}", xidsValueStr);
		}

		return payload;
	}
}
