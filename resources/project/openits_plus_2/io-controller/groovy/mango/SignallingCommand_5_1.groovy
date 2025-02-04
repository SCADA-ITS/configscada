 


import java.util.ArrayList;
import java.util.List;
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
import com.revenga.rits.back.data.core.model.ElementHierarchy;
import com.revenga.rits.back.io.controller.service.EntitiesManager;
import com.revenga.rits.back.data.core.model.command.SetActivationAlarmsCommand;
import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.data.core.model.CommandElementTypeParam;
import com.revenga.rits.back.data.core.model.command.SignallingCommand.SignallingParam;
import com.revenga.rits.back.data.core.model.command.SignallingCommand;



class SignallingCommand_5_1 {
    
    static final Integer PA_CMD_VALUE_ZONES = 1;
    static final Integer PA_CMD_VALUE_MESSAGE = 2;
    static final String PA_SYSTEM_CMD = "start_paging_command";
	
	// Child ID Elements Params
	static final Long PARAM_MEASURE_MESSAGE_ID = 1001L;
	static final Long PARAM_MEASURE_MESSAGE_DESC = 1002L;
	static final Long PARAM_MEASURE_MESSAGE_TEXT = 1003L;
	static final Long PARAM_MEASURE_MESSAGE_TAG = 1004L;

	static final Integer PARAM_CONFIG_PROTOCOLO = 1003L;
    static final Long TYPE_PARAM_CONFIG = 1L;
    static final Long TYPE_PARAM_MEASURE = 2L;
    static final Long GRAPHIC_TYPE_AUDIO = 3L;
    static final Long GRAPHIC_PROTOCOL_TYPE_PARAM_ID_AUDIO = 2L;
    
    static final Long PARAM_CONFIG_SENDER_IP= 5L;
    static final Long PARAM_MEASURE_MESSAGES_ON_AIR= 1L;
    
    static final Long PA_SYSTEM_STATUS_PLAYING= 8L;
    
    static final Long ELEMENT_SUBTYPE_PASYSTEM_ZONES_JSON = 1;
    static final Long ELEMENT_SUBTYPE_PASYSTEM_GROUPS_XML = 2;
    
	static final String MESSAGE_ID = "message_id";
    static final String MESSAGE_TAG = "tag_message";
	static final String MESSAGE_TEXT = "message_text";
	static final String MESSAGE_DESC = "message_desc";
    static final String MULTITAG = ' ';
	static final String MULTI = ' ';
	static final Long MULTI_ID = 0;

    
    GroovyShell shell;
    def signallingCommandUtils;
    org.apache.logging.log4j.Logger log;
    
    SignallingCommand_5_1(org.apache.logging.log4j.Logger log) {
        this.log = log;
        shell = new GroovyShell();
        signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/SignallingCommandUtils.groovy")));
    }
    
    boolean process(String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver) {
        try {
            List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>();
            XidPointValueTimeModel xidPointValueTimeModel;
			String zones;
			
			xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, PA_CMD_VALUE_ZONES,
				dataSourceXid + "_" + PA_SYSTEM_CMD);
			
			zones = xidPointValueTimeModel.getValue();
			log.debug("zones --> " + zones);

			Element element = EntitiesManager.getInstance().getElement(signallingCommand.getElementTypeId(), signallingCommand.getElementId());
			log.debug("Element --> " + element);
			List<ElementHierarchy> childs;
			childs = EntitiesManager.getInstance().getsByParent(element.getElementTypeId(), element.getId());
			log.debug("Element ZONA --> " + childs);
			for (ElementHierarchy child : childs){
					Element childElement = EntitiesManager.getInstance().getElement(child.getChildElementTypeId(), child.getChildElementId());
					log.debug("child  --> " + child.getChildElementId());
					log.debug("childElement  --> " + childElement);
					String childChild = EntitiesManager.getInstance().getElementValueConfig(childElement, PARAM_CONFIG_PROTOCOLO).getValue();
					log.debug("childChild  --> " + childChild);
					if (zones.contains("\"" + child.getChildElementId() + "\"")) { 
               			xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, "DP_" + childChild + "_" + MESSAGE_TAG, MULTITAG);
               			xidPointValueTimeModels.add(xidPointValueTimeModel);
						xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, "DP_" + childChild + "_" + MESSAGE_TEXT, MULTI);
               			xidPointValueTimeModels.add(xidPointValueTimeModel);
						xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, "DP_" + childChild + "_" + MESSAGE_DESC, MULTI);
               			xidPointValueTimeModels.add(xidPointValueTimeModel);
						xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, "DP_" + childChild + "_" + MESSAGE_ID, MULTI);
               			xidPointValueTimeModels.add(xidPointValueTimeModel);
           			 }

					//xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, "DP_" + childChild + "_" + MESSAGE_TAG, MULTI);
					//xidPointValueTimeModels.add(xidPointValueTimeModel);
			}
			xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand,
			dataSourceXid + "_" + PA_SYSTEM_CMD, MULTI);
			xidPointValueTimeModels.add(xidPointValueTimeModel);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(Include.NON_NULL);
            String message = objectMapper.writeValueAsString(xidPointValueTimeModels);
			log.debug("message --> " + message);

			log.debug("xidPointValueTimeModels --> " + xidPointValueTimeModels);
            if (driver != null) {
                driver.send(message);
            }
        } catch (NumberFormatException | JsonProcessingException e) {
            log.error(e.getMessage());
            log.debug(ExceptionUtils.getStackTrace(e));
        }
        return true;
    }
}
