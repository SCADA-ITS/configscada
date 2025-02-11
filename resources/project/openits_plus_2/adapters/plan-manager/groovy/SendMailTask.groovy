import groovy.json.JsonSlurper
import org.apache.commons.lang3.tuple.Pair
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.collections4.MapUtils
import org.apache.logging.log4j.Logger
import java.text.SimpleDateFormat
import com.revenga.rits.back.data.core.model.Alarm
import com.revenga.rits.back.data.core.model.Element
import com.revenga.rits.back.data.core.model.SystemParam;
import com.revenga.rits.back.data.core.model.SystemParamValue
import com.revenga.rits.back.data.core.util.MailUtil
import com.revenga.rits.back.data.core.util.ResourcesUtil;
import com.revenga.rits.back.entities.lib.repository.SystemParamValueRepository
import com.revenga.rits.back.plan.manager.helper.GroovyHelper;
import com.revenga.rits.back.entities.lib.repository.AlarmRepository;
import com.revenga.rits.back.entities.lib.repository.ElementRepository;

class SendMailTask {
    Logger log

    static final Map<Long, String> TASK_TYPE_PARAMS = [
        1L: 'ADDRESS_TO', 2L: 'ADDRESS_CC', 3L: 'ADDRESS_BCC',
        4L: 'SUBJECT', 5L: 'BODY'
    ]

    SendMailTask(Logger log) {
        this.log = log
    }

    boolean process(String value) {
        try {
            def arrayTotal = initializeList()
            def totales = arrayTotal.flatten().size()

			def systemParamValueSmtpConfig = new SystemParamValueRepository(false)
			    .get(SystemParam.SYSTEM_PARAM_SMTP_CONFIG, false)

            if (!systemParamValueSmtpConfig?.value) {
                log.error("No se encontró el parámetro SMTP para enviar el correo")
                return false
            }

            def mapProps = new JsonSlurper().parseText(systemParamValueSmtpConfig.value) as Map
            if (MapUtils.isEmpty(mapProps)) return false

            def props = new Properties()
            mapProps.each { key, val -> props[key] = val }

            if (!value) {
                log.debug("Los destinatarios del correo están vacíos")
                return false
            }

            def body = """<!DOCTYPE html>
                        <html>
                        <body>
                        <h2>NÚMERO DE ALARMAS ACTIVAS TOTALES: $totales</h2>
                        <table>
                        ${getBody("PRUEBAS", arrayTotal[0])}
                        </table>
                        </body>
                        </html>"""

            def subjectDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date())
            def subjectHour = new SimpleDateFormat("HH:mm").format(new Date())

            if (!value.startsWith("{")) {
                value = "{\"addressTO\": \"$value\"}"
            }

            def mail = new JsonSlurper().parseText(value) as Mail
            mail.subject = mail.subject?.replace("%DATE%", subjectDate)?.replace("%HOUR%", subjectHour) ?: "Listado de alarmas activas $subjectDate"

            MailUtil.send(props, mail.addressTO, mail.addressCC, mail.addressBCC, mail.subject, body)
            return true
        } catch (Exception e) {
            log.error("Error al procesar el envío de correo: ", e)
            return false
        }
    }

    private String getBody(String tunnelName, List<Pair<Alarm, Element>> tunnel) {
        if (!tunnel) return ""

        def formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss")
        def color = ["#FFFFFF", "#ECF0F1"]
        def content = new StringBuilder()
        
        // Cargar propiedades desde el archivo
    	def propsLabels = loadProperties("plan-manager/groovy/i18n.properties")
    	def propsElemenTypes = loadProperties("plan-manager/groovy/elementTypes.properties")

        content.append "<tr><th colspan='4'><h2>TUNEL DE $tunnelName (${tunnel.size()})</h2></th></tr>"
        content.append """<tr>
                        <th style='background-color: #ADD8E6; width: 150px'>Activación</th>
                        <th style='background-color: #ADD8E6; width: 500px'>Alarma</th>
                        <th style='background-color: #ADD8E6; width: 200px'>Tipo de equipo</th>
                        <th style='background-color: #ADD8E6; width: 200px'>Elemento</th>
                        </tr>"""
                        
        tunnel.eachWithIndex { Pair<Alarm, Element> el, int index ->
        	def alarmConfigId = el.left.alarmConfigId.toString()
        	def alarmDescription = propsLabels.getProperty(alarmConfigId, alarmConfigId) // Si no hay traducción, usa el ID
        	
        	def elementTypeId = el.right.elementTypeId.toString()
        	def elementDescription = propsElemenTypes.getProperty(elementTypeId, elementTypeId) // Si no hay traducción, usa el ID

            content.append """<tr>
                            <td style='background-color: ${color[index % 2]}'>${formatter.format(new Date(el.left.activation))}</td>
                            <td style='background-color: ${color[index % 2]}'>${alarmDescription}</td>
                            <td style='background-color: ${color[index % 2]}'>${elementDescription}</td>
                            <td style='background-color: ${color[index % 2]}'>${el.right.alias}</td>
                            </tr>"""
        }

        return content.toString()
    }
    
    private Properties loadProperties(String filePath) {
	    def props = new Properties()
	    def file = new File(ResourcesUtil.getPath(filePath))

	    if (file.exists()) {
	        file.withInputStream { stream -> props.load(stream) }
	    } else {
	        println("Archivo de propiedades no encontrado: $filePath")
	    }
	
	    return props
	}

    private List<List<Pair<Alarm, Element>>> initializeList() {
        return GroovyHelper.createAlarmRepository() && GroovyHelper.createElementRepository() ? getLists(GroovyHelper.createAlarmRepository(), GroovyHelper.createElementRepository()) : []
    }
    
    private List<List<Pair<Alarm, Element>>> getLists(AlarmRepository alarmRepository, ElementRepository elementRepository) {
        List<List<Pair<Alarm, Element>>> totalArray = new ArrayList<>()
        List<Pair<Alarm, Element>> tunnelList = new ArrayList<>()
        
        List<Alarm> alarms = alarmRepository.getAll(false)
        List<Element> elements = elementRepository.getAll(false)

        if (!alarms.isEmpty()) {
            alarms.each { al ->
                if (al.getDeactivation() == null && al.getFilteredUntil() == null) {
                    def element = elements.find { it.elementTypeId == al.sourceElementTypeId && it.id == al.sourceElementId }
                    if (element) {
                        tunnelList.add(new ImmutablePair<>(al, element))
                    }
                }
            }
            totalArray.add(tunnelList)
        }
        return totalArray
    }

    static class Mail {
        Properties props
        String addressTO
        String addressCC
        String addressBCC
        String subject
        String body
    }
}
