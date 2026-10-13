package com.revenga.rits.back.io.controller.module;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.command.GenericCommand.DataStruct.Param;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

public class ModuleHelper {

	private ModuleHelper() {
		
		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static ElementValue getElementValue(Element element, Param param) {
		
		return EntitiesManager.getInstance().getElementValue(element, param.getId(), param.getType());
	}
}
