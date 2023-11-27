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
 * SignallingCommand_11_1: Barrier pull down signalling command
 *
 */
class SignallingCommand_11_1 {

    GroovyShell shell;
    def signallingCommandUtils;

    static final String TEST_AUDIO = 'test_audio_writer'
    static final String VOL_MIC = 'vol_mic_writer'
    static final String VOL_AUDIO = 'vol_audio_writer'

    static final Long TEST_AUDIO_VALUE = 1L
    static final Long VOLUMEN_MICRO = 7L
    static final Long VOLUMEN_AUDIO = 7L
    static final Long VOLUMEN_MICRO_OFF = 0L
    static final Long VOLUMEN_AUDIO_OFF = 0L
    
    org.apache.logging.log4j.Logger log;

    SignallingCommand_11_1(org.apache.logging.log4j.Logger log) {
        shell = new GroovyShell()
        signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath('io-controller/groovy/mango/SignallingCommandUtils.groovy')))
        this.log = log
    }

    boolean process(String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver) {
        try {
            List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>()

            XidPointValueTimeModel xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand,
                    dataSourceXid + '_' + TEST_AUDIO, TEST_AUDIO_VALUE)

            xidPointValueTimeModels.add(xidPointValueTimeModel)

            ObjectMapper objectMapper = new ObjectMapper()
            objectMapper.setSerializationInclusion(Include.NON_NULL)
            String message = objectMapper.writeValueAsString(xidPointValueTimeModels)
            if (driver != null) {
                driver.send(message)
            }
            Thread.sleep(30*1000);
            xidPointValueTimeModels.clear();
            
            xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand,
                    dataSourceXid + '_' + VOL_MIC, VOLUMEN_MICRO_OFF)

            xidPointValueTimeModels.add(xidPointValueTimeModel)
            
            message = objectMapper.writeValueAsString(xidPointValueTimeModels)
            if (driver != null) {
                driver.send(message)
            }

            Thread.sleep(20*1000);
            xidPointValueTimeModels.clear();
            
            
            xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand,
                    dataSourceXid + '_' + VOL_MIC, VOLUMEN_MICRO)

            xidPointValueTimeModels.add(xidPointValueTimeModel)

            
            message = objectMapper.writeValueAsString(xidPointValueTimeModels)
            if (driver != null) {
                driver.send(message)
            }

            Thread.sleep(15*1000);
            xidPointValueTimeModels.clear();

            xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand,
                    dataSourceXid + '_' + VOL_AUDIO, VOLUMEN_AUDIO_OFF)

            xidPointValueTimeModels.add(xidPointValueTimeModel)

            message = objectMapper.writeValueAsString(xidPointValueTimeModels)

            if (driver != null) {
                driver.send(message)
            }            

            Thread.sleep(20*1000);
            xidPointValueTimeModels.clear();

            xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(signallingCommand,
                    dataSourceXid + '_' + VOL_AUDIO, VOLUMEN_AUDIO)

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
