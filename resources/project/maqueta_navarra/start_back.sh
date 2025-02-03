#!/bin/bash

echo "Matando procesos antiguos del backend..."
pkill --signal SIGKILL -f backrits
sleep 2

echo "Iniciando backend..."
nohup java -jar /home/admin/app/backrits/server-launcher.jar -Dfile.encoding=UTF-8 --xml.adapter=server_launcher_manager_adapter_v1.xml --xml.server_launcher_adapter=server_launcher_adapter_v1.xml &

sleep 3
pgrep -f backrits && echo "Backend iniciado correctamente" || echo "Error al iniciar el backend"
