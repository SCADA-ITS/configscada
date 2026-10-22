#!/bin/bash

# Variables de configuración
TELEGRAM_BOT_TOKEN="6399005960:AAFKJ22L5NKTjYeg99JyLIkv3DaKqZ6sBYI"
TELEGRAM_CHAT_ID="-624389004"
LOG_FILE_PATH="/home/dai/DAI/grabador/socketManager/logs/logs.log"
THRESHOLD_SIZE_GB=1  # Umbral de tamaño del fichero en gigabytes
REPORT_TIME="9"  # Hora a la que se enviará el informe por Telegram (en formato 24 horas)

# Función para enviar un mensaje por Telegram con parse_mode markdown
send_telegram_message() {
    curl -s -X POST https://api.telegram.org/bot$TELEGRAM_BOT_TOKEN/sendMessage -d chat_id=$TELEGRAM_CHAT_ID -d text="$1" -d parse_mode=markdown
}

# Función para obtener el tamaño del fichero de log en un host remoto en GB
get_log_file_size() {
    HOST=$1
    FILE_SIZE=$(ssh dai@$HOST "stat --format=%s $LOG_FILE_PATH" 2>/dev/null)
    FILE_SIZE_GB=$(echo "scale=2; $FILE_SIZE / (1024 * 1024 * 1024)" | bc)
	
	REPORT_MESSAGE=""
	# Comprobar si el tamaño supera el umbral
	if (( $(echo "$FILE_SIZE_GB > $THRESHOLD_SIZE_GB" | bc -l) )); then
		REPORT_MESSAGE="*INDICACION DE PROBLEMA DE ESTADO EN MAQUINA DAI $HOSTNAME_LOG ($host)*

"

		REPORT_MESSAGE+="Tamaño fichero log grabador: $FILE_SIZE_GB (AVISO: Supera el umbral de $THRESHOLD_SIZE_GB GB)
"
		
		# Enviar el informe por Telegram
		send_telegram_message "$REPORT_MESSAGE"
	fi
	
    echo "$FILE_SIZE (bytes) - $FILE_SIZE_GB (Gbytes)"
}

# Lista de hosts
HOSTS=(
    "192.168.5.147"
    "192.168.9.18"
    "192.168.15.20"
    "192.168.16.18"
    "192.168.14.18"
    "192.168.12.18"
    "192.168.17.15"
    "192.168.11.18"
    "192.168.10.18"
    "192.168.13.18"
)

# Obtener la fecha y hora actual
CURRENT_HOUR=$(date +"%H")

LOG_MESSAGE="*INFORME DE ESTADO MAQUINAS DAIs*

"

# Iterar sobre cada host
for host in "${HOSTS[@]}"; do
    # Obtener el tamaño del fichero de log en GB
    FILE_SIZE_GB=$(get_log_file_size "$host")

    HOSTNAME_LOG=$(ssh dai@$host "hostname")

    LOG_MESSAGE+="*$HOSTNAME_LOG ($host)*
"
    LOG_MESSAGE+="---------------------------------------------------------------------------------------
"
    LOG_MESSAGE+="Tamaño fichero log grabador:     $FILE_SIZE_GB
"
	# Verificar el estado de los contenedores Docker
    CONTAINERS=("ffmpeg" "visionanlt" "doublecheck" "dai_web" "dai_celeryworker" "websocket" "dai_celerybeat" "relay" "dai_nginx" "tunelia" "filemanager" "dai_db" "grabador_nginx" "grabador_web" "grabador_celerybeat" "grabador_celeryworker" "grabador_db" "grabador_redis" "recorder" "socketmanager")
	
	LOG_MESSAGE+="LISTADO DE PROCESOS CAIDOS (Todo OK si no aparece ninguno:
	"
	for container in "${CONTAINERS[@]}"; do
		ssh dai@$host "docker ps | grep $container > /dev/null"

		if [ $? -eq 1 ]; then
		   container_con_espacios=$(echo "$container" | sed 's/_/ /g')
		   LOG_MESSAGE+="Estado del proceso $container_con_espacios = DOWN 
	"
		fi
	done

	ssh dai@$host 'df -h' | grep 'vg-ubuntu' > /dev/null
	
	if [ $? -eq 0 ]; then
		LOG_MESSAGE+="Disco duro libre:    $(ssh dai@$host 'df -h' | grep 'vg-ubuntu' | awk '{print $4}')

"
	else
		LOG_MESSAGE+="Disco duro libre:    $(ssh dai@$host 'df -h' | grep 'sda4' | awk '{print $4}')

"
	fi
done

if [ "$CURRENT_HOUR" -eq "$REPORT_TIME" ]; then
    # Enviar el informe por Telegram
    send_telegram_message "$LOG_MESSAGE"
fi
