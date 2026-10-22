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
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.util.ResourcesUtil

/**
 *
 * SignallingCommand_71_6: CLV pixel test signalling command
 *
 */
class SignallingCommand_71_5 {

    GroovyShell shell;
    def signallingCommandUtils;

    static final String BRIGHTNESS = 'manual_brightness'
    static final String BRIGHTNESS_PMV = 'dmsIlumControl'
    static final String MANUAL_BRIGHTNESS_PMV = 'brightness'
    static final Long BRIGHTNESS_PMV_VALUE = 5L

    org.apache.logging.log4j.Logger log;

    SignallingCommand_71_5(org.apache.logging.log4j.Logger log) {
        shell = new GroovyShell()
        signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath('io-controller/groovy/mango/SignallingCommandUtils.groovy')))
        this.log = log
    }

    boolean process(String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver) {
        try {

            List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>()
            Element element = EntitiesManager.getInstance().getElement(signallingCommand.elementTypeId, signallingCommand.elementId);
            XidPointValueTimeModel xidPointValueTimeModel;
            Long manual_brightness_value = Integer.parseInt(signallingCommand.getSignallingParams().get(0).getValue());

            if (element.elementSubtypeId == 11 || element.elementSubtypeId == 12){

                xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand,
                        dataSourceXid + '_' + BRIGHTNESS, manual_brightness_value)

                xidPointValueTimeModels.add(xidPointValueTimeModel)

                ObjectMapper objectMapper = new ObjectMapper()
                objectMapper.setSerializationInclusion(Include.NON_NULL)
                String message = objectMapper.writeValueAsString(xidPointValueTimeModels)

                if (driver != null) {
                    driver.send(message)
                }
            }else if (element.elementSubtypeId == 19){

                xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand,
                        dataSourceXid + '_' + BRIGHTNESS_PMV, BRIGHTNESS_PMV_VALUE)

                xidPointValueTimeModels.add(xidPointValueTimeModel)

                ObjectMapper objectMapper = new ObjectMapper()
                objectMapper.setSerializationInclusion(Include.NON_NULL)
                String message = objectMapper.writeValueAsString(xidPointValueTimeModels)

                if (driver != null) {
                    driver.send(message)
                }

                xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand,
                        dataSourceXid + '_' + MANUAL_BRIGHTNESS_PMV, manual_brightness_value)

                xidPointValueTimeModels.add(xidPointValueTimeModel)

                objectMapper = new ObjectMapper()
                objectMapper.setSerializationInclusion(Include.NON_NULL)
                message = objectMapper.writeValueAsString(xidPointValueTimeModels)

                if (driver != null) {
                    driver.send(message)
                }

            }
        } catch (NumberFormatException | JsonProcessingException e) {
            log.error(e.getMessage())
            log.debug(ExceptionUtils.getStackTrace(e))
        }

        return true
    }

}
