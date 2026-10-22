import com.revenga.rits.back.data.core.util.ResourcesUtil
import com.revenga.rits.back.data.core.model.command.SignallingCommand
import com.revenga.rits.back.mango.core.model.XidPointValueTimeModel
import com.revenga.rits.back.io.controller.driver.mango.MangoDriver
import com.revenga.rits.back.data.core.model.Element
import com.revenga.rits.back.data.core.model.ElementValue
import com.revenga.rits.back.io.controller.service.EntitiesManager
import org.apache.commons.collections4.CollectionUtils
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.*
import com.fasterxml.jackson.core.type.TypeReference

def void signallingABLCS(SignallingCommand signallingCommand, String dataSourceXid, MangoDriver driver, Element element, org.apache.logging.log4j.Logger log) {

    ObjectMapper mapper = new ObjectMapper()
    GroovyShell shell = new GroovyShell()
    def pmv = shell.parse(new File(ResourcesUtil.getPath("io-controller/groovy/mango/pmv_subtypes/utils/utilsPMV.groovy")))
    // Obtener las zonas de signallingCommand como un JSON
    List<Zone> signallingZones = mapper.readValue(signallingCommand.signallingParams.get(0).getValue(), List.class)
    List<String> graphics = new ArrayList<>()

    int zone
    def jsonMap = [:]
    def jsonAux
    Long zona1, zona2, zona3, zona4
    final Long element166 = 166L
    final Long element165 = 165L
    final Long elementType = 71L
    final Long element_type_param_id = 3L
    final Long param_type_id = 2L
    Element elementAux = new Element()

    if (element.getId() == element166) {
        elementAux = EntitiesManager.getInstance().getElement(elementType, element165)
        // Obtener el ElementValue y su JSON 
        ElementValue elementDataJson = EntitiesManager.getInstance().getElementValue(elementAux, element_type_param_id, param_type_id)
        def jsonObject = new JsonSlurper().parseText(elementDataJson.getValue())
        jsonMap = jsonObject.collectEntries { zoneData -> [(zoneData.zone): zoneData] }

        zona1 = pmv.getGraphic(element, 1L, obtenerValorPorZona(jsonMap, 1))
        zona2 = pmv.getGraphic(element, 1L, obtenerValorPorZona(jsonMap, 2))
        zona3 = pmv.getGraphic(element, 1L, obtenerValorPorZonaSignnaling(signallingZones, 1))
        zona4 = pmv.getGraphic(element, 1L, obtenerValorPorZonaSignnaling(signallingZones, 2))
        
    } else if (element.getId() == element165) {
        elementAux = EntitiesManager.getInstance().getElement(elementType, element166)
        ElementValue elementDataJson = EntitiesManager.getInstance().getElementValue(elementAux, element_type_param_id, param_type_id)
        def jsonObject = new JsonSlurper().parseText(elementDataJson.getValue())
        jsonMap = jsonObject.collectEntries { zoneData -> [(zoneData.zone): zoneData] }

        zona1 = pmv.getGraphic(element, 1L, obtenerValorPorZonaSignnaling(signallingZones, 1))
        zona2 = pmv.getGraphic(element, 1L, obtenerValorPorZonaSignnaling(signallingZones, 2))
        zona3 = pmv.getGraphic(element, 1L, obtenerValorPorZona(jsonMap, 1))
        zona4 = pmv.getGraphic(element, 1L, obtenerValorPorZona(jsonMap, 2))
    }
    
    String multi = "" 
    
    // Lista de zonas y sus coordenadas
    def zonas = [zona1, zona2, zona3, zona4]
    def coordenadas = [1, 49, 97, 145]

    // Iteramos sobre las zonas con un bucle for
    for (int i = 0; i < zonas.size(); i++) {
        if (zonas[i] != 0) {
            multi += "[g" + zonas[i] + "," + coordenadas[i] + ",1]"
        }
    }
   
    pmv.activarVMSNTCIP(multi, dataSourceXid, signallingCommand, driver, false, false, 255, log)
}

class Graphic {
    Long id
    Long value
    Long alternance
}

class Text {
    Long id
    String value
    String alternance
    String color
}

class Zone {
    Long zone
    String align
    Long alternance_ms
    Long flashing_on_ms
    Long flashing_off_ms
    List<Graphic> graphics
    List<Text> texts
}

def obtenerValorPorZona(data, zonaBuscada) {
    def zona = data.find { it.key == zonaBuscada }

    if (zona && zona.value.graphics && !zona.value.graphics.isEmpty()) {
        return zona.value.graphics[0]?.value?.toString()?.toLong()
    } else {
        return null  // Devuelve null si la zona no existe o no tiene gráficos
    }
}

def obtenerValorPorZonaSignnaling(List<Zone> data, Long zonaBuscada) {
    def zona = data.find { it.zone == zonaBuscada }

    if (zona && zona.graphics && !zona.graphics.isEmpty()) {
        return zona.graphics[0]?.value?.toString()?.toLong()
    } else {
        return null  // Devuelve null si la zona no existe o no tiene gráficos
    }
}

