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
import com.revenga.rits.back.io.controller.service.EntitiesManager;
import com.revenga.rits.back.data.core.util.ResourcesUtil;

/**
 * Conserva el comportamiento existente para el resto de equipos.
 * Solo los cuatro equipos indicados utilizan off_order.
 */
class SignallingCommand_13_4 {

    GroovyShell shell;
    def signallingCommandUtils;

    static final String GREEN_ORDER = "green_order";
    static final String AMBER_ORDER = "amber_order";
    static final String RED_ORDER = "red_order";
    static final String OFF_ORDER = "off_order";

    static final List<String> SEMAFOROS_CON_APAGADO = [
        "DP_SMF-2-01-CZ-I",
        "DP_SMF-2-01-CZ-D",
        "DP_SMF-2-02-CZ-I",
        "DP_SMF-2-02-CZ-D"
    ];

    static final boolean OFF = false;
    static final boolean ON = true;

    org.apache.logging.log4j.Logger log;

    SignallingCommand_13_4(org.apache.logging.log4j.Logger log) {
        shell = new GroovyShell();
        signallingCommandUtils = shell.parse(new File(ResourcesUtil.getPath(
            "io-controller/groovy/mango/SignallingCommandUtils.groovy")));
        this.log = log;
    }

    boolean process(String dataSourceXid, SignallingCommand signallingCommand, MangoDriver driver) {
        try {
            List<XidPointValueTimeModel> xidPointValueTimeModels = new ArrayList<>();
            XidPointValueTimeModel xidPointValueTimeModel;

            xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(
                signallingCommand, dataSourceXid + "_" + GREEN_ORDER, OFF);
            xidPointValueTimeModels.add(xidPointValueTimeModel);

            xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(
                signallingCommand, dataSourceXid + "_" + AMBER_ORDER, OFF);
            xidPointValueTimeModels.add(xidPointValueTimeModel);

            xidPointValueTimeModel = signallingCommandUtils.getXidPointValueTimeModel(
                signallingCommand, dataSourceXid + "_" + RED_ORDER, OFF);
            xidPointValueTimeModels.add(xidPointValueTimeModel);

            if (SEMAFOROS_CON_APAGADO.contains(dataSourceXid)) {
                xidPointValueTimeModels.add(
                    signallingCommandUtils.getXidPointValueTimeModel(
                        signallingCommand, dataSourceXid + "_" + OFF_ORDER, ON));
            } else {
                // Conservar tambien la escritura repetida del script original.
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
        return true;
    }
}
