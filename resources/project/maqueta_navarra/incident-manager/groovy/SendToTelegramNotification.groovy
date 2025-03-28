import groovy.json.JsonSlurper
import groovy.json.JsonOutput

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.revenga.rits.back.data.core.model.command.SignallingCommand;
import com.revenga.rits.back.data.core.model.ImsIncidentReport;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTask;
import com.revenga.rits.back.data.core.model.ImsIncidentTypeTaskValue;
import com.revenga.rits.back.data.core.model.command.Command;

class SendToTelegramNotification {

    // Configuración del bot
    private final String API_TOKEN = System.getenv("API_TOKEN") ?: "7673259805:AAEqCaKMZ-SvxE8wpUriuGLegpNxLlOLgRM"
    private final String CHAT_FILE = "/home/admin/subscribers.json"
    private final String CHANNEL_ID = "@TraficoNavarra" // 🔹 Si es público, usa @username. Si es privado, usa el chat_id (-100xxxxxxxxxx).
    private final String TELEGRAM_API_URL = "https://api.telegram.org/bot${API_TOKEN}/sendMessage"

    Logger log;

    // Constructor
    SendToTelegramNotification(Logger log) {
        this.log = log;
        log.info("🔔 Inicializando el bot de Telegram...");
    }

    // Cargar suscriptores desde JSON
    private Map loadSubscribers() {
        def file = new File(CHAT_FILE)
        if (!file.exists()) {
            log.error("❌ Archivo de suscriptores no encontrado.");
            return [:]
        }

        try {
            return new JsonSlurper().parse(file)
        } catch (Exception e) {
            log.error("❌ Error al leer el archivo JSON: ${e.message}");
            return [:]
        }
    }

    // Enviar mensaje por Telegram
    private boolean sendMessage(String chatId, String message) {
        try {
            def url = new URL(TELEGRAM_API_URL)
            def connection = url.openConnection()
            connection.setRequestMethod("POST")
            connection.setDoOutput(true)
            connection.setRequestProperty("Content-Type", "application/json")

            def payload = JsonOutput.toJson([chat_id: chatId, text: message])
            connection.outputStream.withWriter("UTF-8") { it.write(payload) }

            def responseCode = connection.responseCode
            if (responseCode == 200) {
                log.info("✅ Mensaje enviado a ${chatId}")
                return true
            } else {
                log.warn("⚠️ No se pudo enviar el mensaje a ${chatId}. Código: ${responseCode}")
                return false
            }
        } catch (Exception e) {
            log.error("⚠️ Error enviando mensaje a ${chatId}: ${e.message}")
            return false
        }
    }

    // Notificar a todos los suscriptores **y al canal**
    void notifyUsers() {
        def subscribers = loadSubscribers()
        def message = "🚨 ¡INICIO DE EVENTO DE TRIATLÓN! 🚨"

        if (subscribers.isEmpty()) {
            log.warn("⚠️ No hay suscriptores registrados.");
        } else {
            log.info("📢 Enviando notificaciones a ${subscribers.size()} usuarios...");
            subscribers.each { userId, _ -> 
                sendMessage(userId, message) 
            }
        }

        // 🔹 **Enviar también al canal**
        log.info("📢 Enviando notificación al canal ${CHANNEL_ID}...");
        sendMessage(CHANNEL_ID, message);
    }

    List<Command> process(ImsIncidentReport incidentReport, ImsIncidentTypeTask incidentTypeTask, List<ImsIncidentTypeTaskValue> values) {

        List<Command> commands = null;

        try {
            notifyUsers();            

            if (commands == null) {
                commands = new ArrayList<>();
            }

            // commands.add(new SignallingCommand());

        } catch (Exception e) {
            log.error("⚠️ Error en process(): ${e.message}")
        }

        return commands;
    }
}
