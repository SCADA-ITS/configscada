package com.revenga.rits.back.io.controller.module.citilog;

import com.revenga.rits.back.io.controller.driver.citilog.CitilogDriver;

public abstract class CitilogModuleProcessor {

	protected CitilogModule citilogModule;
	protected CitilogDriver citilogDriver;
	
	protected int responseCount;
	
	protected CitilogModuleProcessor(CitilogModule citilogModule) {
		
		this.citilogModule = citilogModule;
		this.citilogDriver = (CitilogDriver) this.citilogModule.getDriver();
		
		this.responseCount = 0;
	}
}
