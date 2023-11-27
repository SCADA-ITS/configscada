import java.util.ArrayList
import java.util.List

import org.apache.commons.lang3.exception.ExceptionUtils

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.revenga.rits.back.data.core.model.Element
import com.revenga.rits.back.data.core.model.ElementValue
import com.revenga.rits.back.data.core.model.command.SignallingCommand
import com.revenga.rits.back.io.controller.driver.mango.MangoDriver
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel.DataTypeEnum
import com.revenga.rits.back.io.controller.service.EntitiesManager

import com.revenga.rits.back.data.core.util.ResourcesUtil

/**
 *
 * SignallingCommand_2000_15: Tube CHANGE ILLUMINATION MODE
 *
 */
class SignallingCommand_2000_15 {

    GroovyShell shell;
    def signallingCommandUtils;

    static final String MODE_CMD = 'traffic_state';

    org.apache.logging.log4j.Logger log;

    SignallingCommand_2000_15(org.apache.logging.log4j.Logger log) {
        shell = new GroovyShell();
        signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath('io-controller/groovy/mango/SignallingCommandUtils.groovy')));
        this.log = log;
    }

    boolean process(String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver) {
        try {
            List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>();
            XidPointValueTimeModel xidPointValueTimeModel;
            
            xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 'DP_canton_1_' + MODE_CMD, Long.parseLong(signallingCommand.signallingParams.get(0).getValue()));        
            xidPointValueTimeModels.add(xidPointValueTimeModel);
            xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 'DP_canton_2_' + MODE_CMD, Long.parseLong(signallingCommand.signallingParams.get(0).getValue()));        
            xidPointValueTimeModels.add(xidPointValueTimeModel);
            xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 'DP_canton_3_' + MODE_CMD, Long.parseLong(signallingCommand.signallingParams.get(0).getValue()));        
            xidPointValueTimeModels.add(xidPointValueTimeModel);
            xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 'DP_canton_4_' + MODE_CMD, Long.parseLong(signallingCommand.signallingParams.get(0).getValue()));        
            xidPointValueTimeModels.add(xidPointValueTimeModel);
            xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 'DP_canton_5_' + MODE_CMD, Long.parseLong(signallingCommand.signallingParams.get(0).getValue()));        
            xidPointValueTimeModels.add(xidPointValueTimeModel);
            xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 'DP_canton_6_' + MODE_CMD, Long.parseLong(signallingCommand.signallingParams.get(0).getValue()));        
            xidPointValueTimeModels.add(xidPointValueTimeModel);
            xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 'DP_canton_7_' + MODE_CMD, Long.parseLong(signallingCommand.signallingParams.get(0).getValue()));        
            xidPointValueTimeModels.add(xidPointValueTimeModel);
            xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 'DP_canton_8_' + MODE_CMD, Long.parseLong(signallingCommand.signallingParams.get(0).getValue()));        
            xidPointValueTimeModels.add(xidPointValueTimeModel);
            xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 'DP_canton_9_' + MODE_CMD, Long.parseLong(signallingCommand.signallingParams.get(0).getValue()));        
            xidPointValueTimeModels.add(xidPointValueTimeModel);
            xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 'DP_canton_10_' + MODE_CMD, Long.parseLong(signallingCommand.signallingParams.get(0).getValue()));        
            xidPointValueTimeModels.add(xidPointValueTimeModel);
            xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 'DP_canton_11_' + MODE_CMD, Long.parseLong(signallingCommand.signallingParams.get(0).getValue()));        
            xidPointValueTimeModels.add(xidPointValueTimeModel);
            xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, 'DP_canton_12_' + MODE_CMD, Long.parseLong(signallingCommand.signallingParams.get(0).getValue()));        
            xidPointValueTimeModels.add(xidPointValueTimeModel);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(Include.NON_NULL);
            String message = objectMapper.writeValueAsString(xidPointValueTimeModels);
            
            if (driver != null) {

                driver.send(message)
            }
        } catch (NumberFormatException | JsonProcessingException e) {
            log.error(e.getMessage());
            log.debug(ExceptionUtils.getStackTrace(e));
        }

        return true
    }

}
