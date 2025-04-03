import groovy.json.JsonSlurper
import groovy.json.JsonOutput

@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')
import groovyx.net.http.RESTClient

// Configuración del bot
def API_TOKEN = System.getenv("API_TOKEN") ?: "TU_TOKEN_AQUI"
def CHAT_FILE = "subscribers.json"
def TELEGRAM_API_URL = "https://api.telegram.org/bot${API_TOKEN}/sendMessage"

// Función para cargar suscriptores desde JSON
def loadSubscribers() {
    def file = new File(CHAT_FILE)
    if (!file.exists()) {
        println "❌ Archivo de suscriptores no encontrado."
        return [:]
    }

    try {
        return new JsonSlurper().parse(file)
    } catch (Exception e) {
        println "❌ Error al leer el archivo JSON: ${e.message}"
        return [:]
    }
}

// Función para enviar mensaje por Telegram
def sendMessage(chatId, message) {
    def client = new RESTClient(TELEGRAM_API_URL)
    try {
        def response = client.post(
            body: [chat_id: chatId, text: message],
            requestContentType: 'application/json'
        )
        return response.status == 200
    } catch (Exception e) {
        println "⚠️ Error enviando mensaje a ${chatId}: ${e.message}"
        return false
    }
}

// Función principal: notificar a todos los suscriptores
def notifyUsers() {
    def subscribers = loadSubscribers()
    
    if (subscribers.isEmpty()) {
        println "⚠️ No hay suscriptores registrados."
        return
    }

    println "📢 Enviando notificaciones a ${subscribers.size()} usuarios..."

    subscribers.each { userId, _ ->
        if (sendMessage(userId, "🚨 ¡Nueva actualización importante! 🚨")) {
            println "✅ Notificación enviada a ${userId}"
        } else {
            println "⚠️ No se pudo enviar el mensaje a ${userId}"
        }
    }
}

// Ejecutar el script
notifyUsers()
