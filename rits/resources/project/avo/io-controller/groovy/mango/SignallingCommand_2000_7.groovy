import java.util.ArrayList
import java.util.List

import org.apache.commons.lang3.exception.ExceptionUtils

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.revenga.rits.back.data.core.model.Element
import com.revenga.rits.back.data.core.model.command.SignallingCommand
import com.revenga.rits.back.io.controller.driver.mango.MangoDriver
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel.DataTypeEnum
import com.revenga.rits.back.io.controller.service.EntitiesManager

import com.revenga.rits.back.data.core.util.ResourcesUtil

/**
 *
 * SignallingCommand_2000_7: Tube CHANGE ILLUMINATION REGIME
 *
 */
class SignallingCommand_2000_7 {

    GroovyShell shell;
    def signallingCommandUtils;

    static final String REGIME_CMD = 'illumination_regime_writter';
	static final int PARAM_ID_ILLUMINATION_REGIME= 1;

    org.apache.logging.log4j.Logger log;

    SignallingCommand_2000_7(org.apache.logging.log4j.Logger log) {
        shell = new GroovyShell();
        signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath('io-controller/groovy/mango/SignallingCommandUtils.groovy')));
        this.log = log;
    }

    boolean process(String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver) {
        try {

            List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>();
            XidPointValueTimeModel xidPointValueTimeModel;
			
			for(int i=1;i<=8;i++){
	            xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand, PARAM_ID_ILLUMINATION_REGIME, dataSourceXid + '_' + REGIME_CMD + '_CT0' + i + '_PLC_G');		
	            xidPointValueTimeModels.add(xidPointValueTimeModel);
	        }

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(Include.NON_NULL);
            String message = objectMapper.writeValueAsString(xidPointValueTimeModels);

            if (driver != null) {

                driver.send(message);
            }
        } catch (NumberFormatException | JsonProcessingException e) {
            log.error(e.getMessage());
            log.debug(ExceptionUtils.getStackTrace(e));
        }

        return true
    }

}
