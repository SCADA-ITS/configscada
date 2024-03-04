#!/bin/bash

# Configuraicón Telegram
TELEGRAM_BOT_TOKEN="6399005960:AAFKJ22L5NKTjYeg99JyLIkv3DaKqZ6sBYI"
TELEGRAM_CHAT_ID="-624389004"

# Configuración SSH
SSH_USER="admin"
SSH_HOST="192.168.0.150"

# Directorio de archivos de log
LOG_DIR="/home/admin/app/dev03/backrits/logs"

# Listado de archivos de log a revisar
LOG_FILES=("ep-pub-stomp/ep-pub-stomp.log"
"history-manager/history-manager.log"
"io-controller/io-controller.log"
"openits/openits.log"
"server-launcher/server-launcher.log"
"traffic-manager/traffic-manager.log"
"equipment-manager/equipment-manager.log"
"incident-manager/incident-manager.log"
"io-external-manager/io-external-manager.log"
"plan-manager/plan-manager.log"
"service-graphql/service-graphql.log"
"virtual-equipment-manager/virtual-equipment-manager.log")

# Texto a buscar en los archivos de log
SEARCH_TEXT="ERROR"

# Obtener la fecha de ayer en el formato esperado
YESTERDAY=$(date -d "yesterday" "+%d/%m/%Y")

# Variable para almacenar los errores
ERRORS=""

# Función para enviar mensajes a través de Telegram
send_telegram_message() {
    local file="$1"
    curl -s -X POST "https://api.telegram.org/bot$TELEGRAM_BOT_TOKEN/sendDocument" -F "chat_id=$TELEGRAM_CHAT_ID" -F "document=@$file"
}

# Búsqueda de errores en los archivos de log
echo "INFORME DE ERRORES OPENITS `date`:" >> /tmp/errors.txt
echo "" >> /tmp/errors.txt
echo "" >> /tmp/errors.txt

for log_file in "${LOG_FILES[@]}"; do
	if ssh "$SSH_USER@$SSH_HOST" "[[ -f '$LOG_DIR/$log_file' ]]"; then
		# Buscar errores y guardarlos en un archivo temporal
		echo $log_file >> /tmp/errors.txt
		echo "-----------------------------------------------------------------------------" >> /tmp/errors.txt
		ssh "$SSH_USER@$SSH_HOST" "grep $SEARCH_TEXT $LOG_DIR/$log_file | grep $YESTERDAY" >> /tmp/errors.txt

		if [ $? -ne 0 ]; then
		   echo "No hay trazas de error nuevas para este proceso en el día actual" >> /tmp/errors.txt	
		fi

		echo "" >> /tmp/errors.txt
		echo "" >> /tmp/errors.txt
	else
		# Indicar que el archivo de registro no existe
                echo "El archivo de registro $log_file no existe en el servidor remoto." >> /tmp/errors.txt
	fi
done

# Leer errores encontrados
if [ -s "/tmp/errors.txt" ]; then
    ERRORS=$(cat /tmp/errors.txt)
    rm /tmp/errors.txt
else
    echo "No se encontraron errores."
    exit 0
fi

# Guardar errores en un archivo de texto
echo "$ERRORS" > errors.txt

# Enviar errores a través de Telegram
send_telegram_message "errors.txt"

rm -rf errors.txt
