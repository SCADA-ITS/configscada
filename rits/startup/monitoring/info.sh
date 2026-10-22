#!/bin/bash

# Definir el token y el chat ID de Telegram
TOKEN="6399005960:AAFKJ22L5NKTjYeg99JyLIkv3DaKqZ6sBYI"
CHAT_ID="-1001909861911"

# Obtener la fecha de ayer
YESTERDAY=$(date -d "yesterday" +%Y-%m-%d)

# Obtener la fecha del último reinicio
LAST_REBOOT=$(who -b | awk '{print $3, $4}')

# Verificar si el último reinicio fue ayer
if [ "$LAST_REBOOT" != "$YESTERDAY" ]; then
    echo "No se encontraron reinicios el $YESTERDAY."
    exit 0
fi

# Obtener el nombre de usuario que realizó el reinicio
USER=$(who -b | awk '{print $1}')

# Formatear el mensaje
MESSAGE="Reinicio MV DAI Calahonda realizado por $USER el $LAST_REBOOT."

# Enviar mensaje por Telegram
curl -s -X POST "https://api.telegram.org/bot$TOKEN/sendMessage" -d "chat_id=$CHAT_ID&text=$MESSAGE"
