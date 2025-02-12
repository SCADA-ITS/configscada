package com.revenga.rits.back.io.controller.module.socketune.adapter;
import java.nio.charset.StandardCharsets
import java.util.List;
import java.util.Calendar;
import com.revenga.rits.back.data.core.model.AlarmConfig;
import com.revenga.rits.back.data.core.model.Element;
import com.revenga.rits.back.data.core.model.ElementValue;
import com.revenga.rits.back.data.core.util.FrameHelper;
import com.revenga.rits.back.io.controller.service.EntitiesManager;
import com.revenga.rits.back.data.core.model.ElementTypeParam;

public class ProcessFrame_1 {

    private org.apache.logging.log4j.Logger log;

    static final String NO_DATA = "00";
    // Variables para almacenar los valores
    def airTempValue;
    def relHumValue;
    def airPressureValue;
    def visibilityValue;
    def windVelValue;
    def windDirValue;
    def windTypeValue;
    def dewPointTempValue;

    // Variables para almacenar la fecha y la hora
    def diaValue;
    def mesValue;
    def anioValue;
    def horaValue;
    def minutoValue;
    def segundoValue;
    def timestampValue;
    def periodoIntegracionValue;
    def failData;
    def InvalidData;

    static final Long TYPE_PARAM_MEASURE = 2L;
    static final long PARAM_MEASURE_AIR_RPESSURE = 4L;
    static final long PARAM_MEASURE_RELATIVE_HUMIDITY = 10L;
    static final long PARAM_MEASURE_WIND_SPEED = 26L;
    static final long PARAM_MEASURE_WIND_DIRECTION = 27L;
    static final long PARAM_MEASURE_AIR_TEMPERATURE = 17L;
    static final long PARAM_MEASURE_DEW_POINT_TEMPERATURE = 18L;
    static final long PARAM_MEASURE_VISIBILITY = 20L;
    static final long PARAM_MEASURE_WIND_TYPE = 28L;
    static final Long PARAM_MEASURE_DATE = 29L;
    static final Long PARAM_MEASURE_PERIOD = 30L;

    public ProcessFrame_1(org.apache.logging.log4j.Logger log) {
        this.log = log;
    }

    public void processResponse_0x87(Element element, List<Byte> data, List<ElementValue> elementValuesToSend,
                                     List<AlarmConfig> activateAlarmsToSend, List<AlarmConfig> deactivateAlarmsToSend) {
        StringBuilder dataHex = new StringBuilder();

        for (Byte b : data) {
            dataHex.append(String.format("%02X", b & 0xFF)); // Se eliminan los espacios
        }

        String dataString = dataHex.toString(); // Convertimos a String final sin espacios

         // Validar si empieza con "00" (NO_DATA)
        if (dataString.startsWith(NO_DATA)) {
            log.error("Error: No hay datos de sensores válidos en la trama de la Meteo Element:1:" + element.getId());
            return;
        }

        // Capturar la fecha y hora
        capturarFechaHora(dataString, element);

		// Procesar la trama
        def medidas = [
            "01": "Temperatura del aire",
            "02": "Humedad relativa",
            "03": "Presión atmosférica",
            "04": "Visibilidad",
            "0A": "Velocidad del viento",
            "0B": "Dirección del viento",
            "0C": "Tipo de viento",
            "10": "Temperatura del punto de rocío"
        ]
		int startIndex = 11 // El primer byte de las medidas, se empieza en 11 por que despues de quitar los bytes especiales la primera medida comienza en esa posicion.
        encontrarMedidas(dataString, medidas, startIndex)

        List<ElementValue> listElements = new ArrayList();
        listElements.add(elementSetValue(PARAM_MEASURE_AIR_RPESSURE, TYPE_PARAM_MEASURE,element.getId(), airPressureValue.toString()))
        listElements.add(elementSetValue(PARAM_MEASURE_RELATIVE_HUMIDITY, TYPE_PARAM_MEASURE,element.getId(), relHumValue.toString()))
        listElements.add(elementSetValue(PARAM_MEASURE_WIND_SPEED, TYPE_PARAM_MEASURE,element.getId(), windVelValue.toString()))
        listElements.add(elementSetValue(PARAM_MEASURE_WIND_DIRECTION, TYPE_PARAM_MEASURE,element.getId(), windDirValue.toString()))
        listElements.add(elementSetValue(PARAM_MEASURE_AIR_TEMPERATURE, TYPE_PARAM_MEASURE,element.getId(), airTempValue.toString()))
        listElements.add(elementSetValue(PARAM_MEASURE_DEW_POINT_TEMPERATURE, TYPE_PARAM_MEASURE,element.getId(), dewPointTempValue.toString()))
        listElements.add(elementSetValue(PARAM_MEASURE_VISIBILITY, TYPE_PARAM_MEASURE,element.getId(), visibilityValue.toString()))
        listElements.add(elementSetValue(PARAM_MEASURE_WIND_TYPE, TYPE_PARAM_MEASURE,element.getId(), windTypeValue.toString()))
        listElements.add(elementSetValue(PARAM_MEASURE_DATE, TYPE_PARAM_MEASURE,element.getId(), timestampValue.toString()))
        listElements.add(elementSetValue(PARAM_MEASURE_PERIOD, TYPE_PARAM_MEASURE,element.getId(), periodoIntegracionValue.toString()))
        EntitiesManager.getInstance().putElementValues(listElements);
		return;
    }

    def capturarFechaHora(String dataString, Element element) {

        // Acceder a los valores de bytes de forma correcta
        diaValue = Integer.parseInt(dataString.substring(2, 4), 16); // Día (índices 2-3 en la cadena)
        mesValue = Integer.parseInt(dataString.substring(4, 6), 16); // Mes (índices 4-5)
        // Concatenar el segundo y el primer byte para el año (índices 6-7)
        anioValue = Integer.parseInt(dataString.substring(8, 10) + dataString.substring(6, 8), 16); // Año
        horaValue = Integer.parseInt(dataString.substring(10, 12), 16); // Hora
        minutoValue = Integer.parseInt(dataString.substring(12, 14), 16); // Minuto
        segundoValue = Integer.parseInt(dataString.substring(14, 16), 16); // Segundo

        // Concatenar el segundo y el primer byte para el periodo (índices 18-19)
        periodoIntegracionValue = Integer.parseInt(dataString.substring(18, 20) + dataString.substring(16, 18), 16) / 60; // Periodo de integración en minutos

        // Convertir la fecha y hora a timestamp
        timestampValue = convertToTimestamp(diaValue, mesValue, anioValue, horaValue, minutoValue, segundoValue); // Guardar en la nueva variable        
    }

    def convertToTimestamp(dia, mes, anio, hora, minuto, segundo) {
        // Crear un objeto Calendar y establecer cada componente
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, anio); // Establece el año
        calendar.set(Calendar.MONTH, mes - 1); // Mes en Calendar es 0-based
        calendar.set(Calendar.DATE, dia); // Establece el día
        calendar.set(Calendar.HOUR_OF_DAY, hora); // Establece la hora
        calendar.set(Calendar.MINUTE, minuto); // Establece los minutos
        calendar.set(Calendar.SECOND, segundo); // Establece los segundos

        return calendar.getTimeInMillis(); // Retorna el tiempo en milisegundos desde 1970
    }

    def bytesToASCII(bytes) {
        // Convertir cada byte hexadecimal en su representación de carácter ASCII
        def asciiString = bytes.collect { b -> new String(b.decodeHex(), StandardCharsets.US_ASCII) }.join("");
        return asciiString;
    }

 def encontrarMedidas(trama, medidas, startIndex) {
    // Convertimos la trama en una lista de bytes
    def tramaString = trama.toString() // Convertimos StringBuilder a String
    def bytes = tramaString.toList().collate(2).collect { it.join('') } // Agrupar en pares de caracteres hexadecimales

    def i = 11  // Ajustamos el índice inicial a bytes

    while (i < bytes.size()) {
        def codigo = bytes[i]


        if (medidas.containsKey(codigo)) {
            def numSensores = bytes[i + 1]
            def sensor = bytes[i + 2]
            def valor = []
            i += 3

            int numBytes = obtenerNumBytes(codigo)

            // Capturar el valor en bytes
            valor = bytes[i..(i + numBytes - 1)]
            i += numBytes

            if (valor.size() == numBytes) {
                asignarValor(codigo, valor)
                
                if (codigo == "10") { // Última medida relevante
                    break
                }
            } else {
                log.warn("No se capturaron suficientes bytes para el código: " + codigo)
            }
        } else {
            i++ // Avanzar al siguiente byte si no se encuentra en medidas
        }
    }
}




    // Método para obtener el número de bytes que tiene cada tipo de medida
    int obtenerNumBytes(codigo) {
        switch (codigo) {
            case "01": return 5 // Temperatura del aire
            case "02": return 3 // Humedad relativa
            case "03": return 4 // Presión atmosférica
            case "04": return 4 // Visibilidad
            case "0A": return 3 // Velocidad del viento
            case "0B": return 3 // Dirección del viento
            case "0C": return 1 // Tipo de viento
            case "10": return 5 // Temperatura de aparición de rocío
            default: return 0
        }
    }

    // Método para asignar el valor a la variable correspondiente
    void asignarValor(codigo, valor) {
        switch (codigo) {
            case "01":
                airTempValue = (bytesToASCII(valor) as Double) / 100 
                break
            case "02":
                relHumValue = bytesToASCII(valor) 
                break
            case "03":
                airPressureValue = bytesToASCII(valor)
                break
            case "04":
                visibilityValue = bytesToASCII(valor)
                break
            case "0A":
                windVelValue = bytesToASCII(valor)
                break
            case "0B":
                windDirValue = bytesToASCII(valor)
                break
            case "0C":
                windTypeValue = bytesToASCII(valor)
                break
            case "10":
                dewPointTempValue = (bytesToASCII(valor) as Double) / 100
                break
        }
    }


    ElementValue elementSetValue(Long elementTypeParamId, Long paramTypeId, Long elementId, String value){
        ElementValue element = new ElementValue();
        element.setElementTypeId(1);
        element.setElementTypeParamId(elementTypeParamId);
        element.setParamTypeId(paramTypeId);
        element.setElementId(elementId);
        element.setValue(value);

        return element;
    }
}
