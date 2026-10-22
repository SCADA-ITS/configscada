package com.revenga.rits.back.io.controller.driver.citilog;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.revenga.rits.back.adapter.lib.model.CLEnumCitiAnswerType;
import com.revenga.rits.back.adapter.lib.model.CLEnumCitiEventType;
import com.revenga.rits.back.adapter.lib.model.CLTypeAlarm;
import com.revenga.rits.back.adapter.lib.model.CLTypeDevice;
import com.revenga.rits.back.adapter.lib.model.CitiAnswer;
import com.revenga.rits.back.adapter.lib.model.CitiEvent;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementIOControllerModule;
import com.revenga.rits.back.data.core.model.ElementState;
import com.revenga.rits.back.data.core.model.IOControllerModule;
import com.revenga.rits.back.data.core.util.ConfigProperty;
import com.revenga.rits.back.io.controller.adapter.CitilogAdapter;
import com.revenga.rits.back.io.controller.driver.GenericDriver;
import com.revenga.rits.back.io.controller.module.GenericEndpointHelper;
import com.revenga.rits.back.io.controller.module.GenericModule;
import com.revenga.rits.back.io.controller.module.citilog.CitilogModule;
import com.revenga.rits.back.io.controller.service.ControllerManager;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2
public class CitilogDriver extends GenericDriver {
	
	CitilogModule citilogModule;
		
	// region Constants
	private static final String FILE_APP_PROP = "application.properties";

	private static final Long PARAM_HOST = Long
			.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROP, "driver.citilog.host"));
	private static final Long PARAM_PORT = Long
	.valueOf(ConfigProperty.getInstance().getProperty(FILE_APP_PROP, "driver.citilog.port"));
	
	// endregion
	
	// region attributes
	private String host;
	private Integer port;
	private CitilogSocketClient citilogSocketClient;
	// endregion
	
	// region public methods
	public CitilogDriver(IOControllerModule ioControllerModule) {
		
		super(ioControllerModule);
		
		this.host = getValue(PARAM_HOST); 
		this.port = Integer.valueOf(getValue(PARAM_PORT));
			
		this.citilogSocketClient = new CitilogSocketClient(this);
		
		(new Thread(this.citilogSocketClient, CitilogSocketClient.class.getSimpleName() + "_" + ioControllerModule.getAlias())).start();
	}

	@Override
	public void start() {
		
		if (!CollectionUtils.isEmpty(ioControllerModule.getModuleElements())) {
			
			ElementIOControllerModule elementIOControllerModule = ioControllerModule.getModuleElements().get(0);
			
			if (elementIOControllerModule != null && elementIOControllerModule.getElement() != null) {
				
				GenericModule genericModule = ControllerManager.getInstance().getControllerService().getRouteService().getModule(elementIOControllerModule.getElement());
				
				if (genericModule instanceof CitilogModule) {
					
					this.citilogModule = (CitilogModule) genericModule;
				}
			}
		}
	}
		
	public void receive(@NonNull String xml) {
		
		for (String xmlItem : CitilogAdapter.getInstance().splitResponse(xml)) {
			
			Class<?> responseType = CitilogAdapter.getInstance().getResponseType(xmlItem);
			
			if (responseType == CitiAnswer.class) {
				
				receiveCitiAnswer(CitilogAdapter.getInstance().getCitiAnswer(xmlItem));
			}
			else if (responseType == CitiEvent.class) {
				
				receiveCitiEvent(CitilogAdapter.getInstance().getCitiEvent(xmlItem));
			}
		} 
	}
	
	public void disconnect() {
		
		if (!CollectionUtils.isEmpty(ioControllerModule.getModuleElements())) {
			
			List<Element> dais = ioControllerModule.getModuleElements().stream()
					.filter(e -> e.getElement() != null)
					.map(ElementIOControllerModule::getElement).collect(Collectors.toList());
			
			for (Element dai : dais) {
				
				ElementState newElementState = GenericEndpointHelper.createElementState(dai,
						GenericEndpointHelper.getDisconnectedStateId(dai));
				
				if (newElementState != null) {
					
					this.citilogModule.receiveState(newElementState);
				}
			}
		}
	}
	
	public void send(String xml) {
		
		if (this.citilogSocketClient != null && !StringUtils.isEmpty(xml)) {
			
			this.citilogSocketClient.send(xml);
		}
	}
	// endregion
	
	// region private methods
	
	private void receiveCitiAnswer(@NonNull CitiAnswer citiAnswer) {
		
		if (citiAnswer.getType() == CLEnumCitiAnswerType.ALARM) {
			
			receiveAlarms(citiAnswer.getAlarm());
		}
		else if (citiAnswer.getType() == CLEnumCitiAnswerType.DEVICE) {
			
			receiveDevices(citiAnswer.getDevice());
		}
	}
	
	private void receiveCitiEvent(@NonNull CitiEvent citiEvent) {
		
		if (citiEvent.getType() == CLEnumCitiEventType.ALARM) {
			
			receiveAlarms(citiEvent.getAlarm());
		}
		else if (citiEvent.getType() == CLEnumCitiEventType.DEVICE) {
			
			receiveDevices(citiEvent.getDevice());
		}
		else if (citiEvent.getType() == CLEnumCitiEventType.LIFESIG) {
			
			// Keep alive from citilog server. Nothing to do
		}
	}
	
	private void receiveAlarms(List<CLTypeAlarm> alarms) {
		
		try {
			
			for (CLTypeAlarm alarm : alarms) {
				
				Element camera;
				
				camera = CitilogAdapter.getInstance().getCamera(String.valueOf(alarm.getCameraId()), this.citilogModule.getCameras());
				
				if (camera != null) {
	
					List<Pair<AlarmConfig, Boolean>> alarmConfigs = CitilogAdapter.getInstance().getAlarmConfigs(alarm, camera);
					
					if (!CollectionUtils.isEmpty(alarmConfigs)) {
						
						receivedAlarms(camera, alarmConfigs);
					}
				}
			}
			
		} catch (IllegalArgumentException e) {
			
			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
	
	private void receiveDevices(List<CLTypeDevice> devices) {
		
		if (!CollectionUtils.isEmpty(ioControllerModule.getModuleElements())) {
			
			List<Element> dais = ioControllerModule.getModuleElements().stream()
					.filter(e -> e.getElement() != null)
					.map(ElementIOControllerModule::getElement).collect(Collectors.toList());
			
			if (!CollectionUtils.isEmpty(dais)) {
				
				for (CLTypeDevice device : devices) {
					
					Element dai = CitilogAdapter.getInstance().getDai(device.getName(), dais);
					
					if (dai != null) {
						
						ElementState newElementState = CitilogAdapter.getInstance().getElementState(device, dai);
						
						if (newElementState != null) {
							
							this.citilogModule.receiveState(newElementState);
						}
						
						List<Pair<AlarmConfig, Boolean>> alarmConfigs = CitilogAdapter.getInstance().getAlarmConfigs(device, dai);
						
						if (!CollectionUtils.isEmpty(alarmConfigs)) {
							
							receivedAlarms(dai, alarmConfigs);
						}
					}
				}
			}
		}
	}
	
	private void receivedAlarms(Element element, List<Pair<AlarmConfig, Boolean>> alarmConfigActivations) {
		
		if (!CollectionUtils.isEmpty(alarmConfigActivations)) {
			
			this.citilogModule.receiveAlarms(element, alarmConfigActivations);
		}
	}
	
	// endregion
}
