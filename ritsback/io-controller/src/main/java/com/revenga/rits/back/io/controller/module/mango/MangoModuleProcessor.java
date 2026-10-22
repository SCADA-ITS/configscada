package com.revenga.rits.back.io.controller.module.mango;

import com.revenga.rits.back.io.controller.driver.mango.MangoDriver;

public abstract class MangoModuleProcessor {

	protected MangoModule mangoModule;
	protected MangoDriver mangoDriver;
	
	protected int responseCount;
	
	public MangoModuleProcessor(MangoModule mangoModule) {
		
		this.mangoModule = mangoModule;
		this.mangoDriver = (MangoDriver) this.mangoModule.getDriver();
		
		this.responseCount = 0;
	}
}
