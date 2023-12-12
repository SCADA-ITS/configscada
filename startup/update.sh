#!/bin/bash

readonly REMOTE_HOST="192.168.88.56" 
readonly REMOTE_BBDD="192.168.88.56" 
readonly PROJECT="fullequip" 
readonly PROJECTBBDD="maqueta_fullequip" 
readonly USER="admin" 

#Limpio la pantalla~/
clear

mostrar_menu() {
    echo "==== Menú ===="
    echo "1. Actualizar TODO"
    echo "2. Actualizar back y ejecutar scripts de BBDD de back"
    echo "3. Actualizar front y ejecutar scripts de BBDD de front"
    echo "4. Salir"
}

mostrar_menu
read -p "Seleccione una opcion (1-4): " opcion


if [ "$opcion" == 1 ] || [ "$opcion" == 2 ]; then
#Compilo el back
cd ~/repositorio/ritsback/_ritsback
mvn install

sleep 4

#Recopilo los binarios del back en un fichero comprimido
echo Deleting old binaries...
sudo rm -f ~/dev03/backrits/*.jar

echo Deleting trace files...
sudo rm -Rf ~/dev03/backrits/logs/*

echo Copying new binaries...
cp ~/repositorio/ritsback/data-loader/target/data-loader.jar ~/dev03/backrits/data-loader.jar
cp ~/repositorio/ritsback/equipment-manager/target/equipment-manager.jar ~/dev03/backrits/equipment-manager.jar
cp ~/repositorio/ritsback/ep-pub-stomp/target/ep-pub-stomp.jar ~/dev03/backrits/ep-pub-stomp.jar
cp ~/repositorio/ritsback/historical-manager/target/historical-manager.jar ~/dev03/backrits/historical-manager.jar
cp ~/repositorio/ritsback/io-controller/target/io-controller.jar ~/dev03/backrits/io-controller.jar
cp ~/repositorio/ritsback/plan-manager/target/plan-manager.jar ~/dev03/backrits/plan-manager.jar
cp ~/repositorio/ritsback/service-graphql/target/service-graphql.jar ~/dev03/backrits/service-graphql.jar
cp ~/repositorio/ritsback/incident-manager/target/incident-manager.jar ~/dev03/backrits/incident-manager.jar
cp ~/repositorio/ritsback/traffic-manager/target/traffic-manager.jar ~/dev03/backrits/traffic-manager.jar
cp ~/repositorio/ritsback/virtual-equipment-manager/target/virtual-equipment-manager.jar ~/dev03/backrits/virtual-equipment-manager.jar
cp ~/repositorio/ritsback/io-external-manager/target/io-external-manager.jar ~/dev03/backrits/io-external-manager.jar
cp ~/repositorio/ritsback/server-launcher/target/server-launcher.jar ~/dev03/backrits/server-launcher.jar
        
echo Deleting old resource files...
sudo rm -Rf ~/dev03/backrits/resources/*

echo Copying new resources...
cp -R ~/repositorio/rits/resources/master/* ~/dev03/backrits/resources
cp -R ~/repositorio/rits/resources/project/$PROJECT/* ~/dev03/backrits/resources
cp -R ~/repositorio/rits/resources/project/$PROJECT/start.sh ~/dev03/backrits/start.sh

#Comprimo el fichero para mandarlo por sftp
cd ~/dev03
tar zcvf backrits.tar.gz backrits
   
#Subo el archivo a la maquina remota
sftp $USER@$REMOTE_HOST<<EOF
   put backrits.tar.gz
   quit
EOF
 
#Entro por ssh y actualizo los ficheros del back
ssh $USER@$REMOTE_HOST<<EOF
   mv /home/$USER/backrits.tar.gz /home/$USER/app/
   cd /home/$USER/app
   rm -rf backrits 
   tar zxvf backrits.tar.gz
   sleep 15
   exit 
EOF
	
#Ejecuto scripts de BBDD de back
cd ~/repositorio/rits/ritsback/resources/db
./make_param.sh $PROJECTBBDD $REMOTE_BBDD 5430
fi

if [ "$opcion" == 1 ] || [ "$opcion" == 3 ]; then
#Copio recursos del front
sed -i "s|\(spring.datasource.url=jdbc:postgresql://\).*|\1$REMOTE_BBDD:5430/rits|" ~/repositorio/ritsfront/openits/src/main/resources/application.properties
sed -i "s|\(ws://\).*\(:61614\)|\1$REMOTE_HOST\2|" ~/repositorio/ritsfront/openits/src/main/resources/project/$PROJECT/data/config.js
rm -R ~/repositorio/ritsfront/openits/src/main/resources/project
cp ~/repositorio/rits/ritsfront/openits/*.properties ~/repositorio/ritsfront/openits/src/main/resources/
cp -r ~/repositorio/rits/ritsfront/openits/static ~/repositorio/ritsfront/openits/src/main/resources/
cp -r ~/repositorio/rits/ritsfront/openits/project ~/repositorio/ritsfront/openits/src/main/resources/

#Compilo el front
cd
cd repositorio/ritsfront/_ritsfront
mvn install

sleep 10 

#Subo el archivo a la maquina remota
cd ~/repositorio/ritsfront/openits/target
sftp $USER@$REMOTE_HOST<<EOF
   put openits.jar
   quit
EOF

#Entro por ssh y actualizo los ficheros del front 
ssh $USER@$REMOTE_HOST<<EOF
   cd app/frontrits
   rm -rf openits.jar
   mv /home/$USER/openits.jar ./
   sed -i 's|\(active=\).*\( -jar\)|\1$PROJECT\2|' ~/app/start.sh
   exit
EOF

#Ejecuto scripts de BBDD de front 
cd ~/repositorio/rits/ritsfront/resources/db
./make_param.sh $PROJECTBBDD $REMOTE_BBDD 5430 $REMOTE_HOST
fi

if [ "$opcion" == 4 ]; then
exit
fi
