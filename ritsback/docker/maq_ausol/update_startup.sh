#!/bin/bash 

echo "Number of arguments: $#"
if test "$#" -ne 3; then
		echo "usage:"
		echo "  param1 = name of sql project"
		echo "  param2 = ip addres"
		echo "  param3 = port"
else
	echo Deleting old binaries...
	rm -f ./backrits/*.jar
	echo Deleting trace files...
	rm -Rf ./backrits/logs/*
	echo Copying new binaries...
	cp ../../data-loader/target/data-loader.jar ./backrits/data-loader.jar
	cp ../../equipment-manager/target/equipment-manager.jar ./backrits/equipment-manager.jar
	cp ../../ep-pub-stomp/target/ep-pub-stomp.jar ./backrits/ep-pub-stomp.jar
	cp ../../historical-manager/target/historical-manager.jar ./backrits/historical-manager.jar
	cp ../../io-controller/target/io-controller.jar ./backrits/io-controller.jar
	cp ../../plan-manager/target/plan-manager.jar ./backrits/plan-manager.jar
	cp ../../service-graphql/target/service-graphql.jar ./backrits/service-graphql.jar
	cp ../../user-manager/target/user-manager.jar ./backrits/user-manager.jar
	cp ../../incident-manager/target/incident-manager.jar ./backrits/incident-manager.jar
	
	echo Deleting old resource files...
	rm -Rf ./backrits/resources/*
	echo Copying new resources...
	cp -R ../../resources ./backrits
	echo Executing scripts over db container...
	cd ../../_rits/resources/db
	./make_param.sh $1 $2 $3
	sleep 2
	echo Script finalized
fi
