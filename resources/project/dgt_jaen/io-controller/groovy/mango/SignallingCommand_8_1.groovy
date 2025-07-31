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
 * SignallingCommand_8_1: Barrier pull down signalling command
 *
 */
class SignallingCommand_8_1 {

    GroovyShell shell;
    def signallingCommandUtils;

    static final String DOWN_ORDER = 'down_order'
    static final String UP_ORDER = 'up_order'

    static final boolean TRUE = true
    static final boolean FALSE = false

    org.apache.logging.log4j.Logger log;

    SignallingCommand_8_1(org.apache.logging.log4j.Logger log) {
        shell = new GroovyShell()
        signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath('io-controller/groovy/mango/SignallingCommandUtils.groovy')))
        this.log = log
    }

    boolean process(String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver) {
        try {
            List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>()
            XidPointValueTimeModel xidPointValueTimeModel;

            xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand,
                    dataSourceXid + '_' + DOWN_ORDER, TRUE)
            xidPointValueTimeModels.add(xidPointValueTimeModel)

            xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand,
                    dataSourceXid + '_' + UP_ORDER, FALSE)
            xidPointValueTimeModels.add(xidPointValueTimeModel)

            ObjectMapper objectMapper = new ObjectMapper()
            objectMapper.setSerializationInclusion(Include.NON_NULL)
            String message = objectMapper.writeValueAsString(xidPointValueTimeModels)

            if (driver != null) {
                driver.send(message)
            }

            Thread.sleep(2000)
            xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand,
                dataSourceXid + '_' + DOWN_ORDER, FALSE)

             // Enviar solo UDOWN_ORDER = false
            xidPointValueTimeModels = new ArrayList<>()
            xidPointValueTimeModels.add(xidPointValueTimeModel)

            message = objectMapper.writeValueAsString(xidPointValueTimeModels)
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
