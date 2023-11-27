import java.util.List
import java.util.ArrayList
import java.util.Properties

import groovy.json.*

import java.io.FileReader

import org.apache.commons.lang3.exception.ExceptionUtils
import org.apache.commons.lang3.math.NumberUtils

import com.fasterxml.jackson.databind.ObjectMapper

import com.revenga.rits.back.data.core.model.Element
import com.revenga.rits.back.data.core.model.ElementValue
import com.revenga.rits.back.data.core.model.VmsGraphicGraphicGroupValue
import com.revenga.rits.back.data.core.util.ResourcesUtil
import com.revenga.rits.back.io.controller.service.EntitiesManager

import java.net.URLDecoder
import java.nio.charset.StandardCharsets

/**
 *
 * InputAdapter_VMS_modbus.groovy: Save the measurement with the current signaling value of the panel
 *
 */
class InputAdapter_VMS_modbus {

    org.apache.logging.log4j.Logger log;

    final String GRAPHIC = 'graphic'
    final String TEXT = 'text'

    static final Long CONFIG_TYPE_PARAM = 1L
    static final Long JSON_PARAM = 4L
    static final Long GRAPHIC_TYPE = 1L

    InputAdapter_VMS_modbus(org.apache.logging.log4j.Logger log) {
        this.log = log
    }

    boolean content(Element element, String value, List<ElementValue> elementValues) {
        boolean intermitence = false;

        if (value == null || value.equals('') || value.equals("0.0")) {
            ElementValue elementValue = new ElementValue()
            elementValue.setElementTypeId(element.getElementTypeId())
            elementValue.setElementTypeParamId(3L)
            elementValue.setParamTypeId(2L)
            elementValue.setElementId(element.getId())
            elementValue.setValue('[]')
            elementValues.add(elementValue)
            return true
        }
        //JSON
        double pictoValue = Double.parseDouble(value)
        int data = (int)pictoValue
        if (data > 256)
            intermitence = true;

        value = URLDecoder.decode(value)
        String result = construirVMS(value, element, intermitence)
        if (result == null || result.equals('')) {
            return false
        }else {
            ElementValue elementValue = new ElementValue()
            elementValue.setElementTypeId(element.getElementTypeId())
            elementValue.setElementTypeParamId(3L)
            elementValue.setParamTypeId(2L)
            elementValue.setElementId(element.getId())
            elementValue.setValue(result)
            elementValues.add(elementValue)

            return true
        }
    }

    public String construirVMS(String content, Element element, boolean intermitence) {
        List<Zone> zones = new ArrayList()
        Zone zone = null
        int alternance, flashing_on, flashing_off, num_lineas
        String zone_type
        ElementValue elementValue_vms = new ElementValue()

        String propertiesPath = new File(ResourcesUtil.getPath('io-controller/groovy/mango/input_adapters/vms.properties')).toString()
        Properties p = new Properties()
        p.load(new FileReader(propertiesPath))
    
        if (element.getElementSubtypeId() == null || !element.getElementSubtypeId()) {
            log.debug('No se encuentra subtipo para el Element:' + element.getElementTypeId() + ':' + element.getId())
            return ''
        }

        int num_zones = Integer.parseInt(p.getProperty('vms.subtype_' + String.valueOf(element.getElementSubtypeId()) + '.num_zones'))

        if (num_zones && num_zones != null) {
            for (int n_zone = 1; n_zone <= num_zones; n_zone++) {
               if(intermitence){
				    flashing_on = Integer.parseInt(p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".flashing_on"));
				    flashing_off = Integer.parseInt(p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".flashing_off"));
			    }else{
				    flashing_on = 0;
				    flashing_off = 0;
				}

                if (alternance != null && flashing_on != null &&  flashing_off != null) {
                    zone = new Zone(n_zone, alternance, flashing_on, flashing_off)
                }else {
                    log.debug('Problema al encontrar alguno de los parametros (alternance, flashing_on, flashing_off), para el Element:' + element.getElementTypeId() + ':' + element.getId())
                    return ''
                }

                zone_type = p.getProperty('vms.subtype_' + String.valueOf(element.getElementSubtypeId()) + '.typezone_' + String.valueOf(n_zone))
                if (zone_type && zone_type != null) {
                    if (zone_type.equals(GRAPHIC)) {

                        zone.fillGraphic(content, element)
                    }
                }else {
                    log.debug('No se ha encontrado el tipo de zona ' + n_zone + ' para el Element:' + element.getElementTypeId() + ':' + element.getId())
                    return ''
                }
                zones.add(zone)

            }
        }else {
            log.debug('No se ha encontrado el parametro num_zonas para el Element:' + element.getElementTypeId() + ':' + element.getId())
            return ''
        }

        try {
            ObjectMapper Obj = new ObjectMapper();
            String json = Obj.writeValueAsString(zones);
            return json

        }catch (Exception e) {
            log.debug(e.getMessage())
            return ''
        }
    }

    class Graphic {

        Integer id;
        Integer value;
        Integer alternance;

        public Graphic(Integer id, Integer value, Integer alternance) {
            this.id = id
            this.value = value
            this.alternance = alternance
        }

    }

    class Text {

        Integer id;
        String value;
        String alternance;
        String color;

        public Text(Integer id, String value, String alternance, String color) {
            this.id = id
            this.value = value
            this.alternance = alternance
            this.color = color
        }

    }

    class Zone {

        Integer zone;
        Integer alternance_ms;
        Integer flashing_on_ms;
        Integer flashing_off_ms;
        List<Graphic> graphics;
        List<Text> texts;

        public Zone(Integer zone, Integer alternance_ms, Integer flashing_on_ms, Integer flashing_off_ms) {
            this.zone = zone
            this.alternance_ms = alternance_ms
            this.flashing_on_ms = flashing_on_ms
            this.flashing_off_ms = flashing_off_ms
            graphics = new ArrayList()
            texts = new ArrayList()
        }

        public void fillText(List<String> messages, Integer numLineas) {
            List<String> values = Arrays.asList(messages.get(0).split('#'))
            List<String> alternances
            
            if (messages.size() > 1) alternances = Arrays.asList(messages.get(1).split('#'))

            String aux_value
            String aux_alternance

            Text textLine

            for (int contador = 1; contador <= numLineas; contador++) {
                if (contador <= values.size() && values.get(contador - 1) != null) {
                    aux_value = values.get(contador - 1)
                }else {
                    aux_value = ''
                }

                if (messages.size() > 1) {
                    if (contador <= alternances.size() && alternances.get(contador - 1) != null) {
                        aux_alternance = alternances.get(contador - 1)
                    }else {
                        aux_alternance = ''
                    }
                    textLine = new Text(contador, aux_value, aux_alternance, '#FFFFFF')
                }else {
                    textLine = new Text(contador, aux_value, null, '#FFFFFF')
                }

                texts.add(textLine)
            }
        }

        public void fillGraphic(String message, Element element) {
            Integer values = 0
            Integer alternances = 0

            double pictoValue = Double.parseDouble(message)

            int data = (int)pictoValue
            if (data > 256){
                data = data - 256;
            }
                
            values = obtenerGraphic(element, data)
            Graphic graphic = new Graphic(1, values, alternances)
            graphics.add(graphic)
        }

        public int obtenerGraphic(Element element, Integer pictoValue) {
            ElementValue jsonGroup = EntitiesManager.getInstance().getElementValueConfig(element, JSON_PARAM);
            def jsonGroup_parsed = new JsonSlurper().parseText(jsonGroup.value);
            int group = jsonGroup_parsed.vms_group_id[0];
            Integer pictoId = 0

            List<VmsGraphicGraphicGroupValue> groupPictos = EntitiesManager.getInstance().getByGroup(jsonGroup_parsed.vms_group_id[0]);

            for (element_group in groupPictos) {

                if(Integer.parseInt(element_group.value).equals(pictoValue)){
                    return element_group.graphicId
                }
            }

        }

    }

}
