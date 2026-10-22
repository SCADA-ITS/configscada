#!/bin/bash 

echo "Number of arguments: $#"
if test "$#" -ne 1; then
		echo "usage:"
		echo "  param1 = name of sql project"
else
	echo Deleting old binaries...
	sudo rm -f ~/dev03/backrits/*.jar
	echo Deleting trace files...
	sudo rm -Rf ~/dev03/backrits/logs/*
	echo Copying new binaries...
	cp ../../ritsback/data-loader/target/data-loader.jar ~/dev03/backrits/data-loader.jar
	cp ../../ritsback/equipment-manager/target/equipment-manager.jar ~/dev03/backrits/equipment-manager.jar
	cp ../../ritsback/ep-pub-stomp/target/ep-pub-stomp.jar ~/dev03/backrits/ep-pub-stomp.jar
	cp ../../ritsback/historical-manager/target/historical-manager.jar ~/dev03/backrits/historical-manager.jar
	cp ../../ritsback/io-controller/target/io-controller.jar ~/dev03/backrits/io-controller.jar
	cp ../../ritsback/plan-manager/target/plan-manager.jar ~/dev03/backrits/plan-manager.jar
	cp ../../ritsback/service-graphql/target/service-graphql.jar ~/dev03/backrits/service-graphql.jar
	cp ../../ritsback/incident-manager/target/incident-manager.jar ~/dev03/backrits/incident-manager.jar
	cp ../../ritsback/traffic-manager/target/traffic-manager.jar ~/dev03/backrits/traffic-manager.jar
	cp ../../ritsback/virtual-equipment-manager/target/virtual-equipment-manager.jar ~/dev03/backrits/virtual-equipment-manager.jar
	cp ../../ritsback/io-external-manager/target/io-external-manager.jar ~/dev03/backrits/io-external-manager.jar
	cp ../../ritsback/server-launcher/target/server-launcher.jar ~/dev03/backrits/server-launcher.jar
	cp ../ritsback/stop.sh ~/dev03/backrits/stop.sh
	
	echo Deleting old resource files...
	sudo rm -Rf ~/dev03/backrits/resources/*
	echo Copying new resources...
	cp -R ../resources/master/* ~/dev03/backrits/resources
	cp -R ../resources/project/$1/* ~/dev03/backrits/resources
	cp -R ../resources/project/$1/start.sh ~/dev03/backrits/start.sh
	sleep 2
	echo Script finalized
fi
