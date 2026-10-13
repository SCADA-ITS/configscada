#!/bin/bash 
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
cp ../../traffic-manager/target/traffic-manager.jar ./backrits/traffic-manager.jar
cp ../../virtual-equipment-manager/target/virtual-equipment-manager.jar ./backrits/virtual-equipment-manager.jar

echo Deleting old resource files...
rm -Rf ./backrits/resources/*
echo Copying new resources...
cp -R ../../resources ./backrits
echo Loading db container...
docker-compose up -d db
echo Waiting to load db container...
sleep 10
echo Executing scripts over db container...
cd ../../_rits/resources/db
./make.sh
sleep 2
echo Shut down db container...
cd ../../../docker/maq_bratislava
docker-compose down
chown -R revenga:revenga db/
chmod -R 755 db/
echo Done
