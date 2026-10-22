package com.revenga.rits.back.io.controller.model;

import com.revenga.rits.back.adapter.lib.model.ControllerAdapterV1;
import com.revenga.rits.back.data.core.model.IOControllerModuleType;
import com.revenga.rits.back.io.controller.driver.citilog.CitilogDriver;
import com.revenga.rits.back.io.controller.driver.mango.MangoDriver;
import com.revenga.rits.back.io.controller.module.citilog.CitilogModule;
import com.revenga.rits.back.io.controller.module.mango.MangoModule;
import com.revenga.rits.back.io.controller.module.rtms.RtmsModule;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ControllerModel {
	
	private String xmlAdapter;
	private ControllerAdapterV1 controllerAdapterV1;
	
	public Class<?> getModuleClass(Long type) {
		
		Class<?> modClass = null;
		
		if (type.equals(IOControllerModuleType.MANGO)) {
			
			modClass = MangoModule.class;
		}
		else if (type.equals(IOControllerModuleType.CITILOG)) {
			
			modClass = CitilogModule.class;
		}
		else if (type.equals(IOControllerModuleType.RTMS)) {
			
			modClass = RtmsModule.class;
		}

		return modClass;
	}
	
	public Class<?> getDriverClass(Long type) {
		
		Class<?> driverClass = null;
		
		if (type.equals(IOControllerModuleType.MANGO)) {
			
			driverClass = MangoDriver.class;
		}
		else if (type.equals(IOControllerModuleType.CITILOG)) {
			
			driverClass = CitilogDriver.class;
		}

		return driverClass;
	}
}
