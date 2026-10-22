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

echo Deleting old resource files...
rm -Rf ./backrits/resources/*
echo Copying new resources...
cp -R ../../resources ./backrits
echo Loading timescaledb container...
docker-compose up -d timescaledb
echo Waiting to load timescaledb container...
sleep 10
echo Executing scripts over timescale container...
cd ../../_rits/resources/db
./make_param.sh maqueta_dev localhost 5430
sleep 2
echo Shut down db container...
cd ../../../docker/maq_dev_163
docker-compose down
chown -R alvaro:alvaro timescaledb/
chmod -R 755 timescaledb/
echo Done
