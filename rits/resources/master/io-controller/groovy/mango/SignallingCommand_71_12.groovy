import java.util.ArrayList;
import java.util.List;

import groovy.json.*

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.io.controller.driver.mango.MangoDriver;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel;
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel.DataTypeEnum;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.VmsGraphicGraphicGroupValue
import com.revenga.rits.back.io.controller.service.EntitiesManager;
import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.data.core.model.command.SetDeactivationAlarmsCommand;

/**
 * 
 * SignallingCommand_71_10: Signalling command for VMS (elementTypeId = 71) ROTAPANEL
 * 
 */
class SignallingCommand_71_12 {				
	GroovyShell shell;
	def signallingCommandUtils;
	org.apache.logging.log4j.Logger log;
	
	SignallingCommand_71_12(org.apache.logging.log4j.Logger log) {
	
		this.log = log;
		shell = new GroovyShell();
		signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/SignallingCommandUtils.groovy")));
	}
	
	boolean process(String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver) {
		try {
			List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>();	
			ObjectMapper  mapper = new ObjectMapper();
								
			XidPointValueTimeModel xidPointValueTimeModel;	
				
			String message = "";

			Element element = EntitiesManager.getInstance().getElement(signallingCommand.elementTypeId, signallingCommand.elementId);
			
			if(element.elementSubtypeId == 23){
				
				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
				dataSourceXid + "_" + "Q1", false);
						
				xidPointValueTimeModels.add(xidPointValueTimeModel);
				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
				dataSourceXid + "_" + "Q2", false);
						
				xidPointValueTimeModels.add(xidPointValueTimeModel);

				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
				dataSourceXid + "_" + "Q3", false);
						
				xidPointValueTimeModels.add(xidPointValueTimeModel);
				mapper.setSerializationInclusion(Include.NON_NULL);
				message = mapper.writeValueAsString(xidPointValueTimeModels);

				if (driver != null) {
			
					driver.send(message);
				}
				Thread.sleep(5000);

				xidPointValueTimeModels.clear();

				xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 
				dataSourceXid + "_" + "Q2", true);
						
				xidPointValueTimeModels.add(xidPointValueTimeModel);
				mapper.setSerializationInclusion(Include.NON_NULL);
				message = mapper.writeValueAsString(xidPointValueTimeModels);

				if (driver != null) {
			
					driver.send(message);
				}				
			}
		}catch (NumberFormatException | JsonProcessingException e) {

			log.error(e.getMessage());
			log.debug(ExceptionUtils.getStackTrace(e));
		}
				
		return true;
	}
	

}
