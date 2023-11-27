#!/bin/bash 

echo "Number of arguments: $#"
if test "$#" -ne 1; then
		echo "usage:"
		echo "  param1 = name of sql project"
else
	echo Deleting old binaries...
	rm -f ./backrits/*.jar
	echo Deleting trace files...
	rm -Rf ./backrits/logs/*
	echo Copying new binaries...
	cp ../../ritsback/data-loader/target/data-loader.jar ./backrits/data-loader.jar
	cp ../../ritsback/equipment-manager/target/equipment-manager.jar ./backrits/equipment-manager.jar
	cp ../../ritsback/ep-pub-stomp/target/ep-pub-stomp.jar ./backrits/ep-pub-stomp.jar
	cp ../../ritsback/historical-manager/target/historical-manager.jar ./backrits/historical-manager.jar
	cp ../../ritsback/io-controller/target/io-controller.jar ./backrits/io-controller.jar
	cp ../../ritsback/plan-manager/target/plan-manager.jar ./backrits/plan-manager.jar
	cp ../../ritsback/service-graphql/target/service-graphql.jar ./backrits/service-graphql.jar
	cp ../../ritsback/user-manager/target/user-manager.jar ./backrits/user-manager.jar
	cp ../../ritsback/incident-manager/target/incident-manager.jar ./backrits/incident-manager.jar
	cp ../../ritsback/traffic-manager/target/traffic-manager.jar ./backrits/traffic-manager.jar
	cp ../../ritsback/virtual-equipment-manager/target/virtual-equipment-manager.jar ./backrits/virtual-equipment-manager.jar
	cp ../ritsback/start.sh ./backrits/start.sh
	cp ../ritsback/stop.sh ./backrits/stop.sh
	
	echo Deleting old resource files...
	rm -Rf ./backrits/resources/*
	echo Copying new resources...
	cp -R ../resources/master/* ./backrits/resources
	rm -rf ./backrits/resources/adapters/mango/*
	cp -R ../resources/project/$1/* ./backrits/resources
	sleep 2
	echo Script finalized
fi
