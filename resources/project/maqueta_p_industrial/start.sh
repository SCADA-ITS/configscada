#!/bin/bash
echo Kill old process
pkill --signal SIGKILL -f backrits
pkill --signal SIGKILL -f frontrits


echo Loading SERVER-LAUNCHER-MANAGER
nohup java -jar /home/revenga/app/backrits/server-launcher.jar  -Dfile.encoding=UTF-8 --xml.adapter=server_launcher_manager_adapter_v1.xml --xml.server_launcher_adapter=server_launcher_adapter_v1.xml &
sleep 5
echo Loading FRONT
nohup java -Dspring.profiles.active=nogales -jar /home/revenga/app/frontrits/openits.jar
