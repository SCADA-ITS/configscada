import java.util.ArrayList
import java.util.List

import org.apache.commons.lang3.exception.ExceptionUtils

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.revenga.rits.back.data.core.model.command.SignallingCommand
import com.revenga.rits.back.io.controller.driver.mango.MangoDriver
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel.DataTypeEnum
import com.revenga.rits.back.io.controller.service.EntitiesManager

import com.revenga.rits.back.data.core.util.ResourcesUtil

/**
 *
 * SignallingCommand_38_2: BUS-VAO
 *
 */
class SignallingCommand_38_2 {

    GroovyShell shell;
    def signallingCommandUtils;

    //static final String BALIZA_CMD = 'state'
    static final String C0 = 'C0'
    static final String C1 = 'C1'
    static final String C2 = 'C2'

    //static final Long ENCENDER_100 = 2L
    static final boolean OFF = false
    static final boolean ON = true

    org.apache.logging.log4j.Logger log;

    SignallingCommand_38_2(org.apache.logging.log4j.Logger log) {
        shell = new GroovyShell()
        signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath('io-controller/groovy/mango/SignallingCommandUtils.groovy')))
        this.log = log
    }

    boolean process(String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver) {
        try {
            List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>()
            XidPointValueTimeModel xidPointValueTimeModel;
            /*
            xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand,'DP_BAL_A_' + BALIZA_CMD, ENCENDER_100);
            xidPointValueTimeModels.add(xidPointValueTimeModel);
            xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand,'DP_BAL_V_' + BALIZA_CMD, ENCENDER_100);
            xidPointValueTimeModels.add(xidPointValueTimeModel);
            */
            xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand,'DP_BUS-VAO_' + C0, ON);
            xidPointValueTimeModels.add(xidPointValueTimeModel);
            xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand,'DP_BUS-VAO_' + C1, OFF);
            xidPointValueTimeModels.add(xidPointValueTimeModel);
            xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand,'DP_BUS-VAO_' + C2, OFF);
            xidPointValueTimeModels.add(xidPointValueTimeModel);

            ObjectMapper objectMapper = new ObjectMapper()
            objectMapper.setSerializationInclusion(Include.NON_NULL)
            String message = objectMapper.writeValueAsString(xidPointValueTimeModels)

            if (driver != null) {
                driver.send(message)
            }
        } catch (NumberFormatException | JsonProcessingException e) {
            log.error(e.getMessage())
            log.debug(ExceptionUtils.getStackTrace(e))
        }

        return true
    }

}
