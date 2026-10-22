package com.revenga.rits.back.io.controller.driver;

import com.revenga.rits.back.data.core.model.IOControllerModule;
import com.revenga.rits.back.data.core.model.IOControllerModuleValue;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

import lombok.Getter;

@Getter
public abstract class GenericDriver {

	protected IOControllerModule ioControllerModule;
	
	protected GenericDriver(IOControllerModule ioControllerModule) {
		
		this.ioControllerModule = ioControllerModule;
	}
	
	protected String getValue(Long paramId) {

		String value = null;

		IOControllerModuleValue ioControllerModuleValue = EntitiesManager.getInstance()
				.getIOControllerModuleValue(this.ioControllerModule, paramId);

		if (ioControllerModuleValue != null) {

			value = ioControllerModuleValue.getValue();
		}

		return value;
	}
	
	public abstract void start();
}
