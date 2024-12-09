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
import com.revenga.rits.back.data.core.model.ElementTypeParam
import com.revenga.rits.back.data.core.model.command.SetActivationAlarmsCommand;
import com.revenga.rits.back.data.core.model.command.SetDeactivationAlarmsCommand;
import com.revenga.rits.back.data.core.util.ResourcesUtil
import com.revenga.rits.back.io.controller.service.EntitiesManager
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.util.Calendar

class InputAdapter_WS_jaen {
    org.apache.logging.log4j.Logger log

    // Variables para almacenar los valores
    def airTempValue
    def relHumValue
    def airPressureValue
    def visibilityValue
    def windVelValue 
    def windDirValue 
    def windTypeValue 
    def dewPointTempValue 

    // Variables para almacenar la fecha y la hora
    def diaValue
    def mesValue
    def anioValue
    def horaValue
    def minutoValue
    def segundoValue
    def timestampValue
    def periodoIntegracionValue
    def failData
    def InvalidData

    static final Long TYPE_PARAM_MEASURE = 2L;
    static final long PARAM_MEASURE_AIR_RPESSURE = 1L
    static final long PARAM_MEASURE_RELATIVE_HUMIDITY = 2L
    static final long PARAM_MEASURE_WIND_SPEED = 3L
    static final long PARAM_MEASURE_WIND_DIRECTION = 4L
    static final long PARAM_MEASURE_AIR_TEMPERATURE = 5L
    static final long PARAM_MEASURE_DEW_POINT_TEMPERATURE = 6L
    static final long PARAM_MEASURE_VISIBILITY = 7L
    static final long PARAM_MEASURE_WIND_TYPE = 8L    
	static final Long PARAM_MEASURE_DATE = 9L;
	static final Long PARAM_MEASURE_PERIOD = 10L;

    static final String NO_DATA = "00"
    static final String CTRL = "10"
    static final String BYTE_82 = "82"
    static final String BYTE_83 = "83"
    static final String BYTE_90 = "90"
    static final String BYTE_02 = "02"
    static final String BYTE_03 = "03"

    InputAdapter_WS_jaen(org.apache.logging.log4j.Logger log) {
        this.log = log
    }

    boolean data(Element element, String value, List<ElementValue> elementValues) {
		SetActivationAlarmsCommand setActivationAlarmsCommandList = new SetActivationAlarmsCommand();
    	SetDeactivationAlarmsCommand setDeactivationAlarmsCommandList = new SetDeactivationAlarmsCommand();

        // Verificar si la longitud del valor supera los 88 caracteres
        failData = 20001L
        InvalidData = 30001L

        if (value.length() > 152) {
            log.error("Error: La trama recibida tiene un tamaño de ${value.length()} caracteres, que excede el límite permitido de 152 caracteres.")
            throw new IllegalArgumentException("Tamaño de trama inválido: ${value.length()} caracteres.")
        }
        
        def bytes
        if (value.contains(" ")) {
            bytes = value.split(" ")
        } else {
            // Dividir la cadena en pares de caracteres de forma segura
            bytes = []
            for (int i = 0; i < value.length(); i += 2) {
                // Verificar que hay suficientes caracteres para formar un par
                if (i + 1 < value.length()) {
                    bytes << value[i..i + 1] // Añadir el par de caracteres
                }
            }
        }

        // Realiza los reemplazos
        for (int i = 0; i < bytes.size(); i++) {
            if (bytes[i] == CTRL) {
                // Asegurarse de que no nos salgamos del rango
                if (i + 1 < bytes.size()) {
                    def siguienteByte = bytes[i + 1]
                    if (siguienteByte == BYTE_82) {
                        bytes[i] = BYTE_02 // Reemplazar 10 82 por 02
                        bytes.remove(i + 1) // Eliminar el 82
                        i--; // Ajustar el índice para evitar saltar el siguiente byte
                    } else if (siguienteByte == BYTE_83) {
                        bytes[i] = BYTE_03 // Reemplazar 10 83 por 03
                        bytes.remove(i + 1) // Eliminar el 83
                        i--; // Ajustar el índice para evitar saltar el siguiente byte
                    } else if (siguienteByte == BYTE_90) {
                        // Dejar el 10 si sigue un 90, pero eliminar el 90
                        bytes.remove(i + 1) // Eliminar el 90
                    }
                }
            }
        }

        // Comprobación de los datos de sensores
        if (bytes[3] == NO_DATA) {
			setActivationAlarmsCommandList.add(element, EntitiesManager.getInstance().getAlarmConfig(failData));
            log.error("Error: No hay datos de sensores válidos en la trama de la Meteo Element:1:${element.getId()}")
            EntitiesManager.getInstance().sendCommand(setActivationAlarmsCommandList);
    		EntitiesManager.getInstance().sendCommand(setDeactivationAlarmsCommandList);
            return true
        }else{

			setDeactivationAlarmsCommandList.add(element, EntitiesManager.getInstance().getAlarmConfig(failData));
            EntitiesManager.getInstance().sendCommand(setActivationAlarmsCommandList);
            EntitiesManager.getInstance().sendCommand(setDeactivationAlarmsCommandList);            
		}

        // Capturar la fecha y hora
        capturarFechaHora(bytes, element)
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

        int startIndex = 14 // El primer byte de las medidas, se empieza en 14 por que despues de quitar los bytes especiales la primera medida comienza en esa posicion.
        
        encontrarMedidas(bytes, medidas, startIndex)

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

        return true
    }

    def capturarFechaHora(bytes, element) {
        // Asegúrate de que estás accediendo a los índices correctos
		SetActivationAlarmsCommand setActivationAlarmsCommandList = new SetActivationAlarmsCommand();
    	SetDeactivationAlarmsCommand setDeactivationAlarmsCommandList = new SetDeactivationAlarmsCommand();

        diaValue = Integer.parseInt(bytes[4], 16) // Día
        mesValue = Integer.parseInt(bytes[5], 16) // Mes
        // Concatenar el segundo y el primer byte para el año
        anioValue = Integer.parseInt("${bytes[7]}${bytes[6]}", 16) // Año
        horaValue = Integer.parseInt(bytes[8], 16) // Hora
        minutoValue = Integer.parseInt(bytes[9], 16) // Minuto
        segundoValue = Integer.parseInt(bytes[10], 16) // Segundo
        // Concatenar el segundo y el primer byte para el periodo
        periodoIntegracionValue = (Integer.parseInt("${bytes[12]}${bytes[11]}", 16))/60 // Periodo de integración en minutos

        // Convertir la fecha y hora a timestamp
        timestampValue = convertToTimestamp(diaValue, mesValue, anioValue, horaValue, minutoValue, segundoValue) // Guardar en la nueva variable        

            // Obtener el tiempo actual en milisegundos
        long currentTimeMillis = System.currentTimeMillis()

        // Calcular la diferencia en minutos
        long differenceInMinutes = Math.abs(currentTimeMillis - timestampValue) / (1000 * 60)

        // Verificar si la diferencia es mayor de 10 minutos
        if (differenceInMinutes > 10) {
			setActivationAlarmsCommandList.add(element, EntitiesManager.getInstance().getAlarmConfig(InvalidData));

            EntitiesManager.getInstance().sendCommand(setActivationAlarmsCommandList);
    		EntitiesManager.getInstance().sendCommand(setDeactivationAlarmsCommandList);
            log.error("Alarma: La diferencia de tiempo entre los datos y la fecha actual es mayor de 10 minutos.")
        }else{

			setDeactivationAlarmsCommandList.add(element, EntitiesManager.getInstance().getAlarmConfig(InvalidData));
            EntitiesManager.getInstance().sendCommand(setActivationAlarmsCommandList);
            EntitiesManager.getInstance().sendCommand(setDeactivationAlarmsCommandList);            
		}

    }

    def convertToTimestamp(dia, mes, anio, hora, minuto, segundo) {
        // Crear un objeto Calendar y establecer cada componente
        Calendar calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, anio) // Establece el año
        calendar.set(Calendar.MONTH, mes - 1) // Mes en Calendar es 0-based
        calendar.set(Calendar.DATE, dia) // Establece el día
        calendar.set(Calendar.HOUR_OF_DAY, hora) // Establece la hora
        calendar.set(Calendar.MINUTE, minuto) // Establece los minutos
        calendar.set(Calendar.SECOND, segundo) // Establece los segundos
        
        return calendar.getTimeInMillis() // Retorna el tiempo en milisegundos desde 1970
    }

    def bytesToASCII(bytes) {
        // Convertir cada byte hexadecimal en su representación de carácter ASCII
        def asciiString = bytes.collect { b -> new String(b.decodeHex(), StandardCharsets.US_ASCII) }.join("")
        return asciiString
    }

    def encontrarMedidas(trama, medidas, startIndex) {
        def i = startIndex

        while (i < trama.size()) {
            def codigo = trama[i]
            
            // Si el código está en las medidas de interés, procesamos el valor
            if (medidas.containsKey(codigo)) {
                def numSensores = trama[i + 1]
                def sensor = trama[i + 2]
                def valor = []
                i += 3

                // Definir el número de bytes a tomar basado en el código
                int numBytes = obtenerNumBytes(codigo)

                // Capturar el valor
                valor = trama[i..(i + numBytes - 1)]
                i += numBytes

                // Validar si capturamos el número correcto de bytes
                if (valor.size() == numBytes) {
                    // Asignamos el valor a la variable correspondiente
                    asignarValor(codigo, valor)

                    // Salir del bucle si el código procesado es "10" ya que es la ultima medida que nos interesa y no deberian de llegar mas datos de sensores, solo y exlusivamente el CRC y el ETX(final de trama)
                    if (codigo == CTRL) {
                        break // Salir del bucle
                    }
                } else {
                    log.warn("No se capturaron suficientes bytes para el código: " + codigo)
                }
            } else {
                // Ignorar códigos no relevantes y avanzar al siguiente byte
                i++
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
