package com.revenga.rits.back.io.controller.module.citilog;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementIOControllerModule;
import com.revenga.rits.back.io.controller.adapter.CitilogAdapter;

public class CitilogModuleRequestProcessor extends CitilogModuleProcessor {
	
	boolean connected = true;
	
	List<Element> elements = new ArrayList<>();
	
	public CitilogModuleRequestProcessor(CitilogModule citilogModule) {

		super(citilogModule);
		
		initialize();
	}
		
	private void initialize() {
		
		if (!CollectionUtils.isEmpty(citilogModule.getIoControllerModule().getModuleElements())) {
			
			for (ElementIOControllerModule moduleElement : citilogModule.getIoControllerModule().getModuleElements()) {
				
				if (moduleElement.getElement() != null) {
				
					elements.add(moduleElement.getElement());
				}
			}
		}
	}
	
	public void requestStates() {
		
		citilogDriver.send(CitilogAdapter.getInstance().getDevicesRequest());
	}
	
	public void requestState(Element element) {
		
		citilogDriver.send(CitilogAdapter.getInstance().getDevicesRequest());
	}
		 	
	public void requestAlarms() {
		
		citilogDriver.send(CitilogAdapter.getInstance().getAlarmsRequest());
	}
	
	public void requestAlarm(Element element) {
		
		citilogDriver.send(CitilogAdapter.getInstance().getDevicesRequest());
	}
	
	public void requestKeepAlive() {
		
		citilogDriver.send(CitilogAdapter.getInstance().getKeepAliveRequest());
	}
	
	public Element addElement(Element element) {
		
		return this.elements.add(element) ? element : null;
	}
	
	public Element deleteElement(Element element) {
		
		return this.elements.removeIf(
				e -> e.getElementTypeId().equals(element.getElementTypeId()) && e.getId().equals(element.getId()))
						? element
						: null;
	}
}
