import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

import groovy.json.*;

import java.io.FileReader;

import org.apache.commons.collections4.CollectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.util.FrameHelper;
import com.revenga.rits.back.data.core.model.VmsGraphicGraphicGroupValue;
import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.io.controller.service.EntitiesManager;


public void content(Element element, List<Byte> data, List<ElementValue> elementValuesToSend, org.apache.logging.log4j.Logger log){

	final List<Byte> CMD_VALUE_POWER_OFF_DGT = List.of((byte) 0x30);	
	final Long PARAM_MEASURE_DATAJSON = 3L;
	final Long PARAM_TYPE_MEASURE = 2L;

	byte[] newData = FrameHelper.byteListToArray(data)
	
	if(data == null || data.equals(CMD_VALUE_POWER_OFF_DGT)) {
		ElementValue elementValue = new ElementValue();
		elementValue.setElementTypeId(element.getElementTypeId());
		elementValue.setElementTypeParamId(PARAM_MEASURE_DATAJSON);
		elementValue.setParamTypeId(PARAM_TYPE_MEASURE);
		elementValue.setElementId(element.getId());
		elementValue.setValue("[]");
		elementValuesToSend.add(elementValue);
	}
	
	String result = construirVMS(newData, element, log);

	if (result == null || result.equals("")){
		
		ElementValue elementValue = new ElementValue();
		elementValue.setElementTypeId(element.getElementTypeId());
		elementValue.setElementTypeParamId(PARAM_MEASURE_DATAJSON);
		elementValue.setParamTypeId(PARAM_TYPE_MEASURE);
		elementValue.setElementId(element.getId());
		elementValue.setValue("[]");
		elementValuesToSend.add(elementValue);
	}else{
		
		ElementValue elementValue = new ElementValue();
		elementValue.setElementTypeId(element.getElementTypeId());
		elementValue.setElementTypeParamId(PARAM_MEASURE_DATAJSON);
		elementValue.setParamTypeId(PARAM_TYPE_MEASURE);
		elementValue.setElementId(element.getId());
		elementValue.setValue(result);
		elementValuesToSend.add(elementValue);
	}

}

public String construirVMS(byte[] content, Element element, org.apache.logging.log4j.Logger log) {
	
	final String GRAPHIC = "graphic";
	final String TEXT = "text";
	
	//DGT protocol values	
	final int EST_ALT = 0x33;
	final int TIPO_CONT_MEM = 0x31;
	final int TIPO_CONT_GRAFICO = 0x32;
	final int TIPO_CONT_LIBRE = 0x33;
	final int PARAM_LINEA_INT = 0x58;
	final int INTERMITENCIA_SI = 0x53;

    List<Integer> graphics = new ArrayList();
    List<Integer> graphics_alt = new ArrayList();
    List<String> texts = new ArrayList();
    List<Zone> zones = new ArrayList();
    List<Long> intermitence = new ArrayList();
    Zone zone = null;
    int alternance, flashing_on, flashing_off, num_lineas;
    List<Integer> contentByte = new ArrayList<Integer>();
    String zone_type = "";
    boolean hayAlternancia = false;

    String propertiesPath = new File(ResourcesUtil.getPath("io-controller/groovy/mango/input_adapters/vms.properties")).toString();
    Properties p = new Properties();
    p.load(new FileReader(propertiesPath));
    
    contentByte = content.collect { it & 0xFF }

    if (contentByte == null || contentByte.isEmpty() || contentByte.size() < 3) {
        log.debug("Trama recibida vacia o demasiado corta.");
        return "";
    }

    if((contentByte.get(0) & 0xFF).equals(EST_ALT)){
        hayAlternancia = true;
    }

    // Inicializamos intermitencias para 3 zonas
    intermitence.add(0L); // zona 1
    intermitence.add(0L); // zona 2
    intermitence.add(0L); // zona 3

    int numSubpaneles = Character.getNumericValue((char)(contentByte.get(2) & 0xFF));
    int index = 3;

    log.debug("Estado recibido: " + contentByte.get(0));
    log.debug("Topologia recibida: " + contentByte.get(1));
    log.debug("Numero subpaneles: " + numSubpaneles);
    log.debug("Hay alternancia: " + hayAlternancia);

    if(hayAlternancia){
        for(int i = 0; i < numSubpaneles; i++){
            index = parseSubpanel(contentByte, index, element, graphics, graphics_alt, texts, intermitence, false, log);
            index = parseSubpanel(contentByte, index, element, graphics, graphics_alt, texts, intermitence, true, log);
        }
    }else{
        for(int i = 0; i < numSubpaneles; i++){
            index = parseSubpanel(contentByte, index, element, graphics, graphics_alt, texts, intermitence, false, log);
        }
    }
            
    if(element.getElementSubtypeId() == null || !element.getElementSubtypeId()){
        log.error("No se encuentra subtipo para el Element:" + element.getElementTypeId() + ":" + element.getId());
        return "";
    }
    
    int num_zones = Integer.parseInt(p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".num_zones"));
    int zones_graphic = 0;
    if(num_zones && num_zones != null){
        for(int n_zone = 1; n_zone <= num_zones; n_zone++){
            alternance = Integer.parseInt(p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".alternance"));
            flashing_on = intermitence.get(n_zone - 1);
            flashing_off = 0;
            
            if(alternance != null && flashing_on != null && flashing_off != null){	    
                zone = new Zone(n_zone, alternance, flashing_on, flashing_off);
            }else{
                log.debug("Problema al encontrar alguno de los parametros (alternance, flashing_on, flashing_off), para el Element:" + element.getElementTypeId() + ":" + element.getId());
                return "";
            }
            
            zone_type = p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".typezone_" + String.valueOf(n_zone));
            if(zone_type && zone_type != null){
                if(zone_type.equals(GRAPHIC)){
                    zone.fillGraphic(graphics, graphics_alt, zones_graphic, log);	
                    zones_graphic++;
                }else if(zone_type.equals(TEXT)){
                    num_lineas = Integer.parseInt(p.getProperty("vms.subtype_" + String.valueOf(element.getElementSubtypeId()) + ".zone_" + String.valueOf(n_zone) + "." + TEXT + ".num_lines"));
                    if(num_lineas && num_lineas != null){
                        zone.fillText(texts, num_lineas);
                    }else{
                        log.debug("No se encuenta el numero de lineas de la zona de texto " + n_zone + " para el Element:" + element.getElementTypeId() + ":" + element.getId());
                        return "";
                    }
                }else{
                    log.debug("Tipo de zona " + zone_type + " DESCONOCIDO para el Element:" + element.getElementTypeId() + ":" + element.getId());
                    return "";
                }
            }else{
                log.debug("No se ha encontrado el tipo de zona " + n_zone + " para el Element:" + element.getElementTypeId() + ":" + element.getId());
                return "";
            }
            zones.add(zone);
        }	
    }else{
        log.debug("No se ha encontrado el parametro num_zonas para el Element:" + element.getElementTypeId() + ":" + element.getId());
        return "";
    }
    
    try {
        ObjectMapper Obj = new ObjectMapper();
        String json = Obj.writeValueAsString(zones);
        return json;
    }catch(Exception e) {
        log.debug(e.getMessage());
        return "";
    }

}

public int parseSubpanel(List<Integer> contentByte, int index, Element element, List<Integer> graphics, List<Integer> graphics_alt, List<String> texts,
                         List<Long> intermitence, boolean isAlternance, org.apache.logging.log4j.Logger log){

    if(index >= contentByte.size() || index + 1 >= contentByte.size()){
        log.debug("Indice fuera de rango parseando subpanel. index=" + index);
        return contentByte.size();
    }

    final int TIPO_CONT_MEM = 0x31;
    final int TIPO_CONT_GRAFICO = 0x32;
    final int TIPO_CONT_LIBRE = 0x33;
    final int INTERMITENCIA_SI = 0x53;
    final int PARAM_LINEA_INT = 0x58;

    int nsp = contentByte.get(index) & 0xFF;
    int tipo = contentByte.get(index + 1) & 0xFF;
    int zoneIndex = Character.getNumericValue((char)nsp) - 1;

    log.debug("Parseando subpanel NSP=" + nsp + " tipo=" + tipo + " alternancia=" + isAlternance + " index=" + index);

    // Subpanel grafico (NSP impar)
    if((nsp % 2) != 0){
        if(tipo == TIPO_CONT_MEM){
            if(index + 3 >= contentByte.size()){
                log.debug("Bloque grafico por memoria incompleto.");
                return contentByte.size();
            }

            int memoria = contentByte.get(index + 2) & 0xFF;
            int inter = contentByte.get(index + 3) & 0xFF;

            int graphicId = obtenerGraphicPorMemoria(element, memoria, zoneIndex + 1, log);

            if(isAlternance){
                graphics_alt.add(graphicId);
            }else{
                graphics.add(graphicId);
            }

            intermitence.set(zoneIndex, inter == INTERMITENCIA_SI ? 1000L : 0L);

            log.debug("Grafico memoria recibido. zona=" + (zoneIndex + 1) + " memoria=" + memoria + " graphicId=" + graphicId);

            return index + 4;
        }

        if(tipo == TIPO_CONT_GRAFICO){
            if(index + 3 >= contentByte.size()){
                log.debug("Bloque grafico por nombre incompleto.");
                return contentByte.size();
            }

            int len = contentByte.get(index + 2) & 0xFF;
            int nameStart = index + 3;
            int nameEnd = nameStart + len;
            int interPos = nameEnd;

            if(interPos >= contentByte.size()){
                log.debug("Bloque grafico por nombre con longitud fuera de rango. len=" + len);
                return contentByte.size();
            }

            byte[] nameBytes = new byte[len];
            for(int i = 0; i < len; i++){
                nameBytes[i] = (byte)(contentByte.get(nameStart + i) & 0xFF);
            }

            String nombre = new String(nameBytes, "US-ASCII").trim().toUpperCase();
            int inter = contentByte.get(interPos) & 0xFF;

            int graphicId = obtenerGraphic(element, nombre, zoneIndex + 1, log);

            if(isAlternance){
                graphics_alt.add(graphicId);
            }else{
                graphics.add(graphicId);
            }

            intermitence.set(zoneIndex, inter == INTERMITENCIA_SI ? 1000L : 0L);

            log.debug("Grafico nombre recibido. zona=" + (zoneIndex + 1) + " nombre=" + nombre + " graphicId=" + graphicId);

            return index + 4 + len;
        }

        log.debug("Tipo de contenido grafico no soportado: " + tipo);
        return contentByte.size();
    }

    // Subpanel alfanumerico (NSP par)
    if(tipo == TIPO_CONT_LIBRE){
        if(index + 2 >= contentByte.size()){
            log.debug("Bloque texto libre incompleto.");
            return contentByte.size();
        }

        int len = contentByte.get(index + 2) & 0xFF;
        int contentStart = index + 3;
        int contentEnd = contentStart + len;

        if(contentEnd > contentByte.size()){
            log.debug("Bloque texto libre con longitud fuera de rango. len=" + len);
            return contentByte.size();
        }

        List<Integer> textContent = contentByte.subList(contentStart, contentEnd);

        if(textContent.size() >= 4){
            int nl = textContent.get(2) & 0xFF;
            int attrsStart = 3;
            int textStart = attrsStart + nl;

            if(textStart <= textContent.size()){
                for(int i = attrsStart; i < textStart && i < textContent.size(); i++){
                    if((textContent.get(i) & 0xFF) == PARAM_LINEA_INT){
                        intermitence.set(zoneIndex, 1000L);
                    }
                }

                List<Integer> rawText = textContent.subList(textStart, textContent.size());
                if(!rawText.isEmpty() && (rawText.get(rawText.size() - 1) & 0xFF) == 0x00){
                    rawText = rawText.subList(0, rawText.size() - 1);
                }

                byte[] textBytes = new byte[rawText.size()];
                for(int i = 0; i < rawText.size(); i++){
                    textBytes[i] = (byte)(rawText.get(i) & 0xFF);
                }

                String resultado = new String(textBytes, "Cp437");

                if(isAlternance){
                    if(texts.size() == 0){
                        texts.add("");
                    }
                    texts.add(resultado);
                }else{
                    texts.add(resultado);
                }

                log.debug("Texto recibido. zona=" + (zoneIndex + 1) + " texto=" + resultado.replace("\n", "\\n"));
            }
        }

        return contentEnd;
    }

    log.debug("Tipo de contenido alfanumerico no soportado: " + tipo);
    return contentByte.size();
}


class Graphic{
    Integer id;
    Integer value;
    Integer alternance;
    
    public Graphic(Integer id, Integer value, Integer alternance){
        this.id = id;
        this.value = value;
        this.alternance = alternance;
    }
}

class Text{
    Integer id;
    String value;
    String alternance;
    String color;
    
    public Text(Integer id, String value, String alternance, String color){
        this.id = id;
        this.value = value;
        this.alternance = alternance;
        this.color = color;
    }
}

class Zone{
    Integer zone;
    Integer alternance_ms;
    Integer flashing_on_ms;
    Integer flashing_off_ms;
    List<Text> texts;
    List<Graphic> graphics;
    
    public Zone(Integer zone, Integer alternance_ms, Integer flashing_on_ms, Integer flashing_off_ms){
        this.zone = zone;
        this.alternance_ms = alternance_ms;
        this.flashing_on_ms = flashing_on_ms;
        this.flashing_off_ms = flashing_off_ms;
        graphics = new ArrayList();
        texts = new ArrayList();
    }
    
    public void fillText(List<String> messages, Integer numLineas) {
        final String TEXT_COLOR = "#FFBF00";
        
        List<String> values = Arrays.asList(messages.get(0).split("\n"));
        List<String> alternances;
        if (messages.size() > 1) alternances = Arrays.asList(messages.get(1).split("\n"));
        
        String aux_value;
        String aux_alternance;
        
        Text textLine;
        
        for(int contador = 1; contador <= numLineas; contador++) {
            if(contador <= values.size() && values.get(contador - 1) != null) {
                aux_value = values.get(contador - 1);
            }else {
                aux_value = "";
            }
            
            if (messages.size() > 1){
                if(contador <= alternances.size() && alternances.get(contador - 1) != null) {
                    aux_alternance = alternances.get(contador - 1);
                }else {
                    aux_alternance = "";
                }
                textLine = new Text(contador, aux_value, aux_alternance, TEXT_COLOR);
            }else{
                textLine = new Text(contador, aux_value, null, TEXT_COLOR);
            }
            texts.add(textLine);
        }
        
    }

    public void fillGraphic(List<Integer> message, List<Integer> message_alt, int idGraphic, org.apache.logging.log4j.Logger log) {
        
        Integer values = -1;
        Integer alternances = 0;

        if(idGraphic < message.size()){
            values = message.get(idGraphic);
        }
        
        if (values != -1){
            if (idGraphic < message_alt.size()) {
                alternances = message_alt.get(idGraphic);
            }
            
            Graphic graphic = new Graphic(1, values, alternances);
            graphics.add(graphic);
        }
    
    }
}

public int obtenerGraphic(Element element, String pictoValue, Integer numZone, org.apache.logging.log4j.Logger log){

    final Long PARAM_CONFIG_JSONCONFIG = 4L;

    ElementValue elementDataJson = EntitiesManager.getInstance().getElementValueConfig(element, PARAM_CONFIG_JSONCONFIG);
    
    def jsonObject = new JsonSlurper().parseText(elementDataJson.getValue());
    Long group = jsonObject.vms_group_id[numZone - 1];
    
    List<VmsGraphicGraphicGroupValue> vmsGraphicGraphicGroupValue = EntitiesManager.getInstance().getByGroup(group);
    
    if (!CollectionUtils.isEmpty(vmsGraphicGraphicGroupValue)) {
        for(VmsGraphicGraphicGroupValue groupValue : vmsGraphicGraphicGroupValue){
            if(groupValue.getValue() != null && !groupValue.getValue().equals("")){
                if(pictoValue.equalsIgnoreCase(groupValue.getValue())){
                    return groupValue.getGraphicId();
                }
            }
        }
        log.debug("Nombre de pictograma no encontrado en BD: " + pictoValue + " zona=" + numZone);
        return -1;
    }else{
        log.debug("No existen valores graficos para el grupo. zona=" + numZone);
        return -1;
    }
}

public int obtenerGraphicPorMemoria(Element element, Integer pictoValue, Integer numZone, org.apache.logging.log4j.Logger log){

    final Long PARAM_CONFIG_JSONCONFIG = 4L;

    ElementValue elementDataJson = EntitiesManager.getInstance().getElementValueConfig(element, PARAM_CONFIG_JSONCONFIG);
    
    def jsonObject = new JsonSlurper().parseText(elementDataJson.getValue());
    Long group = jsonObject.vms_group_id[numZone - 1];
    
    List<VmsGraphicGraphicGroupValue> vmsGraphicGraphicGroupValue = EntitiesManager.getInstance().getByGroup(group);
    
    if (!CollectionUtils.isEmpty(vmsGraphicGraphicGroupValue)) {
        for(VmsGraphicGraphicGroupValue groupValue : vmsGraphicGraphicGroupValue){
            if(groupValue.getValue() != null && !groupValue.getValue().equals("")){
                try{
                    if(pictoValue.equals(Integer.parseInt(groupValue.getValue()))){
                        return groupValue.getGraphicId();
                    }
                }catch(Exception e){
                    // Ignoramos los valores no numericos cuando la trama recibida venga por memoria.
                }
            }
        }
        log.debug("Memoria grafica no encontrada en BD: " + pictoValue + " zona=" + numZone);
        return -1;
    }else{
        log.debug("No existen valores graficos para el grupo. zona=" + numZone);
        return -1;
    }
}