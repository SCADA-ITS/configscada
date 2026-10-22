#!/bin/bash

# Obtener la fecha y hora actual
CURRENT_DATE=$(date +"%Y-%m-%d_%H%M%S")

# Lista de direcciones IP de los hosts remotos
HOSTS=("192.168.0.150" "192.168.0.151" "192.168.0.152")

# Variable para almacenar la información de todos los hosts
ALL_INFO="$CURRENT_DATE\n\n"

# Variables de configuración para Telegram
TELEGRAM_BOT_TOKEN="6399005960:AAFKJ22L5NKTjYeg99JyLIkv3DaKqZ6sBYI"
TELEGRAM_CHAT_ID="-624389004"

# Función para comprobar la disponibilidad de un host
check_host_availability() {
    ping -c 1 "$1" &>/dev/null
    return $?
}

# Iterar sobre cada host remoto
for HOST in "${HOSTS[@]}"; do
    # Verificar la disponibilidad del host
    if check_host_availability "$HOST"; then
        # Archivo remoto y local para cada host
        REMOTE_FILENAME="/home/admin/remote_system_info_$HOST.txt"

        # Obtener información del sistema remoto
        DISCO=$(ssh admin@$HOST df -h | awk '/\/dev\/mapper/{print $4}')
        PORCENTAJE_DISCO=$(ssh admin@$HOST df -h | awk '/\/dev\/mapper/{print 100-$5}')
        CPU=$(ssh admin@$HOST mpstat 1 1 | awk '/Average/{print 100 - $12}')
        PROCESOS=$(ssh admin@$HOST ps -e | wc -l)
        MEMORIA_TOTAL=$(ssh admin@$HOST free -h | awk '/Mem/{print $2}')
        MEMORIA_USADA=$(ssh admin@$HOST free -h | awk '/Mem/{print $3}')
        PORCENTAJE_MEMORIA_USO=$(ssh admin@$HOST free | awk '/Mem/{print $3/$2 * 100}')

        # Formatear la información actual
        INFO_ACTUAL="INFORMACIÓN DEL SISTEMA EN $HOST:
-------------------------------------------------------------------------------
        - Disco Duro Libre: $DISCO/$PORCENTAJE_DISCO%
        - Consumo de CPU: $CPU%
        - Número de Procesos en Ejecución: $PROCESOS
        - Memoria: $MEMORIA_USADA/$MEMORIA_TOTAL ($PORCENTAJE_MEMORIA_USO%)"
        
        ALL_INFO+="$INFO_ACTUAL\n"

        # Descargar el archivo del host remoto
        scp admin@$HOST:$REMOTE_FILENAME $REMOTE_FILENAME 2>/dev/null

        # Verificar si la descarga fue exitosa
        if [ $? -eq 0 ]; then
            # Comparar valores con el archivo remoto
            INFO_ANTERIOR=$(cat "$REMOTE_FILENAME")

            IFS=$'\n' read -r -d '' -a lines_actual < <(tail -n +3 <<< "$INFO_ACTUAL")
            IFS=$'\n' read -r -d '' -a lines_anterior < <(tail -n +3 <<< "$INFO_ANTERIOR")
            
            ALL_INFO+="DIFERENCIA ENTRE LA INFORMACIÓN ANTERIOR Y LA ACTUAL:\n"
            ALL_INFO+="-------------------------------------------------------------------------------\n"
            
            # Mostrar la diferencia parámetro a parámetro
            for ((i=0; i<${#lines_actual[@]}; i++)); do
                ALL_INFO+="${lines_actual[$i]} (anterior: ${lines_anterior[$i]})\n"
            done
        fi
        
        # Guardar la información en un archivo en el host remoto
        echo "$INFO_ACTUAL" | ssh admin@$HOST "cat > $REMOTE_FILENAME"
    else
        # Agregar una línea indicando que el host no está disponible
        ALL_INFO+="El host $HOST no está disponible\n\n"
        echo "El host $HOST no está disponible. Saltando..."
    fi
    ALL_INFO+="***********************************************************************************************************************************\n\n\n\n"
done

# Guardar la información de todos los hosts en un único archivo
rm system_info*
ALL_FILENAME="system_info_${CURRENT_DATE}.txt"
echo -e "$ALL_INFO" > "$ALL_FILENAME"

# Enviar el archivo a Telegram
curl -F chat_id="$TELEGRAM_CHAT_ID" -F document=@"$ALL_FILENAME" https://api.telegram.org/bot$TELEGRAM_BOT_TOKEN/sendDocument

