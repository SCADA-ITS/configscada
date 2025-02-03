#!/bin/bash

echo "Matando procesos antiguos del frontend..."
pkill --signal SIGKILL -f frontrits
sleep 2

echo "Iniciando frontend..."
nohup java -Dspring.profiles.active=maqueta_navarra -jar /home/admin/app/frontrits/openits.jar

