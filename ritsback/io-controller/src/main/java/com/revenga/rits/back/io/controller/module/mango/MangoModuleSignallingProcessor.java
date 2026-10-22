package com.revenga.rits.back.io.controller.module.mango;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.cache.core.model.Key;
import com.revenga.rits.back.data.core.model.CommandElementType;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.LogLevel;
import com.revenga.rits.back.data.core.model.LogSubtype;
import com.revenga.rits.back.data.core.model.LogType;
import com.revenga.rits.back.data.core.model.User;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.util.GroovyManager;
import com.revenga.rits.back.entities.lib.helper.AuditHelper;
import com.revenga.rits.back.io.controller.adapter.MangoAdapter;
import com.revenga.rits.back.io.controller.module.ModuleException;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

import groovy.lang.GroovyObject;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class MangoModuleSignallingProcessor extends MangoModuleProcessor {

	public MangoModuleSignallingProcessor(MangoModule mangoModule) {

		super(mangoModule);
	}

	public void process(SignallingCommand cmd) {

		try {

			Element element = EntitiesManager.getInstance().getElement(cmd.getElementTypeId(), cmd.getElementId());

			if (element != null) {

				String groovyFile = "io-controller/groovy/mango/SignallingCommand_" + cmd.getElementTypeId() + "_"
						+ cmd.getSignallingCommandId() + ".groovy";

				log.debug("Process signalling command " + cmd.getSignallingCommandId() + " for "
						+ (new Key(element)).getSimpleValue());
				log.debug("    --> " + groovyFile);

				GroovyObject groovyObj = GroovyManager.getInstance().getGroovyObject(groovyFile);

				boolean res = (boolean) groovyObj.invokeMethod("process",
						new Object[] { MangoAdapter.getInstance().getDataSource(element), cmd, mangoDriver });

				if (res) {

					log.debug("Success processed signalling command " + cmd.getSignallingCommandId() + " for "
							+ (new Key(element)).getSimpleValue());

					CommandElementType commandElementType = EntitiesManager.getInstance()
							.getCommandElementType(cmd.getSignallingCommandId(), element.getElementTypeId(), false);

					if (commandElementType != null) {

						AuditHelper.Audit(LogType.LOG_TYPE_COMMANDS, LogSubtype.LOG_SUBTYPE_COMMAND_EXECUTED,
								User.USER_SYSTEM, LogLevel.INFO, commandElementType.getLabelAlias(), element.getAlias(),
								new ObjectMapper().writeValueAsString(cmd.getSignallingParams()));
					} else {
						
						log.error("Signalling command:" + cmd.getSignallingCommandId()
								+ "not found in this elemetentType:" + element.getElementTypeId());
					}

				} else {

					throw new ModuleException(mangoModule, groovyFile + " exist with code error");
				}
			} else {
				log.error("Element:" + cmd.getElementTypeId() + "." + cmd.getElementId() + " not found");
			}

		} catch (Exception e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
	}
}
