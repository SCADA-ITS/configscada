#!#!/bin/bash
sleep 10
echo Loading SERVER-LAUNCHER ...
nohup java -jar /home/admin/app/dev03/backrits/server-launcher.jar -Dfile.encoding=UTF-8 --xml.adapter=server_launcher_adapter_v1.xml &
