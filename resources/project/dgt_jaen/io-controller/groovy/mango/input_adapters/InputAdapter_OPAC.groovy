import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

import groovy.json.*;

import java.io.FileReader;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.model.ElementTypeParam;
import com.revenga.rits.back.data.core.model.VmsGraphicGraphicGroupValue;
import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.io.controller.service.EntitiesManager;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import groovy.json.JsonOutput

/**
 *
 * InputAdapter_CO.groovy: convert de value of CO
 *
 */
class InputAdapter_OPAC {
    org.apache.logging.log4j.Logger log;
    
    static final Long PARAM_MEASURE_OPAC_CONCENTRATION = 1L;
    static final Long PARAM_TYPE_MEASURE = 2L;
    
    InputAdapter_OPAC(org.apache.logging.log4j.Logger log) {
        this.log = log;
    }
    
    boolean opac_concentration(Element element, String value, List<ElementValue> elementValues) {
        String result = "";

        if (value == null || value.equals("")) {
            ElementValue elementValue = new ElementValue();
            elementValue.setElementTypeId(element.getElementTypeId());
            elementValue.setElementTypeParamId(PARAM_MEASURE_OPAC_CONCENTRATION);
            elementValue.setParamTypeId(PARAM_TYPE_MEASURE);
            elementValue.setElementId(element.getId());
            elementValue.setValue("");
            elementValues.add(elementValue);
            return true;
        }

        result = factorConversion(value, element);

        if (result == null || result.equals("")) {
            return false;
        } else {
            ElementValue elementValue = new ElementValue();
            elementValue.setElementTypeId(element.getElementTypeId());
            elementValue.setElementTypeParamId(PARAM_MEASURE_OPAC_CONCENTRATION);
            elementValue.setParamTypeId(PARAM_TYPE_MEASURE);
            elementValue.setElementId(element.getId());
            elementValue.setValue(result);
            elementValues.add(elementValue);
        
            return true;
        }
    }

    public String factorConversion(String opac_concentration, Element element) {
        try {
            // Convertir el valor de opac_concentration a un número
            double opac_concentrationValue = Double.parseDouble(opac_concentration);
        
            // Aplicar la ecuacion de la recta que pasa por dos puntos para sacar la intensidad
            double entrada_mA = 0.00048834 * opac_concentrationValue + 4;

        
            //Aplicar factor de conversion: (((entrada_mA - 4) * 1000) / (15 * 16)) + 0.1
            double finalResult = (((entrada_mA - 4) * 1000) / (15 * 16)) + 0.1;
        
            // Devolver el resultado final como String
            return String.valueOf(finalResult);
        
        } catch (NumberFormatException e) {
            log.error("Invalid OPAC concentration format: " + opac_concentration, e);
            return "";  // Retorna un String vacío si el formato no es válido
        }
    }
}
