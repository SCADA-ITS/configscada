#!/bin/bash

REMOTE_HOST="" 
REMOTE_BBDD="" 
PROJECT="" 
PROJECTBBDD="" 
USER="" 

#Limpio la pantalla~/
clear

ctrl_c() {
   clear
   exit 1
}

mostrar_menu() {
	selection=$(dialog \
	  --backtitle "SCADA OPENITS" \
	  --title "Instalación" \
	  --stdout --menu "\nIndique la acción que desea realizar:" 15 70 5 \
	  "1" "Actualizar TODO" \
	  "2" "Actualizar back y ejecutar scripts de BBDD de back" \
 	  "3" "Actualizar front y ejecutar scripts de BBDD de front" \
 	  "4" "Ejecutar scripts de BBDD" \
 	  "5" "Salir")
 	  
 	if [ -z "$selection" ]; then
	   clear
	   exit 1
	fi
}

rellenar_parametros() {
	while true; do
	  dialog --backtitle "SCADA OPENITS" \
	    --title "Nombre del proyecto" \
	    --inputbox "Indique el nombre del proyecto que está instalando" 8 40 2>/tmp/input_result
	
	  if [ $? -ne 0 ]; then
	     clear
	     exit 0
	  fi
	
	  declare -g PROJECT=$(cat /tmp/input_result)
	
	  if [ -n "$project" ]; then
	     dialog \
	         --msgbox "!El nombre del proyecto no puede estar vacío! Por favor, ingrésalo de nuevo." \
	         15 70
	  else
	     break
	  fi
	done

	while true; do
	  dialog --backtitle "SCADA OPENITS" \
	    --title "Usuario de la aplicación" \
	    --inputbox "Indique el usuario de la aplicación" 8 40 2>/tmp/input_result
	
	  if [ $? -ne 0 ]; then
	     clear
	     exit 0
	  fi
	
	  declare -g USER=$(cat /tmp/input_result)
	
	  if [ -n "$project" ]; then
	     dialog \
	         --msgbox "!El usuario no puede estar vacío! Por favor, ingrésalo de nuevo." \
	         15 70
	  else
	     break
	  fi
	done
	
	while true; do
	  dialog --backtitle "SCADA OPENITS" \
	    --title "IP aplicación" \
	    --inputbox "Indique la IP de la maquina donde está la aplicación" 8 40 2>/tmp/input_result
	
	  if [ $? -ne 0 ]; then
	     clear
	     exit 0
	  fi
	
	  declare -g REMOTE_HOST=$(cat /tmp/input_result)
	
	  if [ -n "$project" ]; then
	     dialog \
	         --msgbox "!La IP no puede estar vacía! Por favor, ingrésala de nuevo." \
	         15 70
	  else
	     break
	  fi
	done
	
	while true; do
	  dialog --backtitle "SCADA OPENITS" \
	    --title "UP BBDD" \
	    --inputbox "Indique la IP de la maquina donde está la BBDD" 8 40 2>/tmp/input_result
	
	  if [ $? -ne 0 ]; then
	     clear
	     exit 0
	  fi
	
	  declare -g REMOTE_BBDD=$(cat /tmp/input_result)
	
	  if [ -n "$project" ]; then
	     dialog \
	         --msgbox "!La IP no puede estar vacía! Por favor, ingrésala de nuevo." \
	         15 70
	  else
	     break
	  fi
	done
	
	maqueta=$(dialog \
	  --backtitle "SCADA OPENITS" \
	  --title "Instalación" \
	  --stdout --menu "\n¿Esta actualizando maqueta o producción?" 15 70 2 \
	  "1" "Maqueta" \
	  "2" "Producción")
 	  
 	if [ -z "$maqueta" ]; then
	   clear
	   exit 1
	fi
	
	case $maqueta in
	   1)
	   	echo "Entra en 1"
	   		declare -g PROJECTBBDD="maqueta_$PROJECT"
	   		;;
	   2)
	   	echo "Entra en 2"
	   		declare -g PROJECTBBDD=$PROJECT
	   		;;
	esac	
	
	clear
}

update_progress() {
    progress=$((current_file * 100 / files_count))
    echo "$progress"
}

show_progress() {
   local info="$1"
   local progress="$2"
   dialog --title "Ejecutando...." --gauge "\nEjecutando scripts de BBDD $1" 15 70 "$progress"
}

fill_db_back() {
  psql postgresql://rits:rits@$REMOTE_BBDD:5430/rits -c "SELECT 1;" >/dev/null 2>&1

  if [ $? -eq 0 ]; then  
     #Empezamos con los scripts de ddl
     cd ~/repo_github/configscada/ritsback/resources/db/ddl
  
     yourfilenames=`find . -name '*.sql' -print0 | sort -z | xargs -r0`
     files_count=`echo $yourfilenames | wc -w`
     current_file=0
     for eachfile in $yourfilenames; do
     	current_file=$((current_file+1))
		progress=$(update_progress)
	
	    if ((progress >= last_progress + 10)); then
		   show_progress ". Creando tablas..." "$progress" &
		   last_progress="$progress"
		fi
	
	    psql postgresql://rits:rits@$REMOTE_BBDD:5430/rits -b -f $eachfile >/dev/null
	 done
  
     wait
     last_progress=0

     #Continuamos con los scripts de master 
     cd ~/repo_github/configscada/ritsback/resources/db/dml/master
  
     yourfilenames=`find . -name '*.sql' -print0 | sort -z | xargs -r0`
     files_count=`echo $yourfilenames | wc -w`
     current_file=0
     for eachfile in $yourfilenames; do
        current_file=$((current_file+1))
	  	progress=$(update_progress)
	  	
        if ((progress >= last_progress + 5)); then
	       show_progress ". Rellenando tablas master..." "$progress" &
	       last_progress="$progress"
	  	fi

        psql postgresql://rits:rits@$REMOTE_BBDD:5430/rits -b -f $eachfile >/dev/null
     done
  
     wait
     last_progress=0

     #Continuamos con los scripts de proyecto 
     cd ~/repo_github/configscada/ritsback/resources/db/dml/$PROJECTBBDD
  
     yourfilenames=`find . -name '*.sql' -print0 | sort -z | xargs -r0`
     files_count=`echo $yourfilenames | wc -w`
     current_file=0
     for eachfile in $yourfilenames; do
     	current_file=$((current_file+1))
	    progress=$(update_progress)
	  	
        if ((progress >= last_progress + 5)); then
	       show_progress ". Rellenando tablas de proyecto..." "$progress" &
	       last_progress="$progress"
	    fi

        psql postgresql://rits:rits@$REMOTE_BBDD:5430/rits -b -f $eachfile >/dev/null
     done
  fi
}

fill_db_front() {
  psql postgresql://rits:rits@$REMOTE_BBDD:5430/rits -c "SELECT 1;" >/dev/null 2>&1

  if [ $? -eq 0 ]; then  
     #Empezamos con los scripts de ddl
     cd ~/repo_github/configscada/ritsfront/resources/db/ddl
  
     yourfilenames=`find . -name '*.sql' -print0 | sort -z | xargs -r0`
     files_count=`echo $yourfilenames | wc -w`
     current_file=0
     for eachfile in $yourfilenames; do
     	current_file=$((current_file+1))
		progress=$(update_progress)
	
	    if ((progress >= last_progress + 10)); then
		   #show_progress ". Creando tablas..." "$progress" &
		   last_progress="$progress"
		fi
	
	    psql postgresql://rits:rits@$REMOTE_BBDD:5430/rits -b -f $eachfile >/dev/null
	 done
  
     wait
     last_progress=0

     #Continuamos con los scripts de master 
     cd ~/repo_github/configscada/ritsfront/resources/db/dml/master
  
     yourfilenames=`find . -name '*.sql' -print0 | sort -z | xargs -r0`
     files_count=`echo $yourfilenames | wc -w`
     current_file=0
     for eachfile in $yourfilenames; do
        current_file=$((current_file+1))
	  	progress=$(update_progress)
	  	
        if ((progress >= last_progress + 5)); then
	       #show_progress ". Rellenando tablas master..." "$progress" &
	       last_progress="$progress"
	  	fi

        psql postgresql://rits:rits@$REMOTE_BBDD:5430/rits -b -f $eachfile >/dev/null
     done
  
     wait
     last_progress=0

     #Continuamos con los scripts de proyecto 
     cd ~/repo_github/configscada/ritsfront/resources/db/dml/$PROJECTBBDD

     yourfilenames=`find . -name '*.sql' -print0 | sort -z | xargs -r0`
     files_count=`echo $yourfilenames | wc -w`
     current_file=0
     for eachfile in $yourfilenames; do
     	current_file=$((current_file+1))
	    progress=$(update_progress)
	  	
        if ((progress >= last_progress + 5)); then
	       #show_progress ". Rellenando tablas de proyecto..." "$progress" &
	       last_progress="$progress"
	    fi

        psql postgresql://rits:rits@$REMOTE_BBDD:5430/rits -b -f $eachfile >/dev/null
     done
  fi
  
  psql postgresql://rits:rits@$REMOTE_BBDD:5430/rits -c "UPDATE ui.data_source_values SET value='http://$REMOTE_HOST:8082/graphql' WHERE data_source_param_id = 1;"
  psql postgresql://rits:rits@$REMOTE_BBDD:5430/rits -c "UPDATE ui.data_source_values SET value='ws://$REMOTE_HOST:61614' WHERE data_source_param_id = 5;"
  psql postgresql://rits:rits@$REMOTE_BBDD:5430/rits -c "UPDATE ui.data_source_values SET value='http://$REMOTE_HOST:8082/graphql' WHERE data_source_param_id = 8;"
}

back() {
#Compilo el back
echo -e "\n\nCompiling backrits..."
cd ~/repo_github/backscada
mvn install

sleep 4

#Recopilo los binarios del back en un fichero comprimido
echo -e "\n\nDeleting old binaries..."
sudo rm -f ~/app/backrits/*.jar

echo -e "\n\nDeleting trace files..."
sudo rm -Rf ~/app/backrits/logs/*

echo -e "\n\nCopying new binaries..."
cp ~/repo_github/backscada/data-loader/target/data-loader.jar ~/app/backrits/data-loader.jar
cp ~/repo_github/backscada/equipment-manager/target/equipment-manager.jar ~/app/backrits/equipment-manager.jar
cp ~/repo_github/backscada/ep-pub-stomp/target/ep-pub-stomp.jar ~/app/backrits/ep-pub-stomp.jar
cp ~/repo_github/backscada/historical-manager/target/historical-manager.jar ~/app/backrits/historical-manager.jar
cp ~/repo_github/backscada/io-controller/target/io-controller.jar ~/app/backrits/io-controller.jar
cp ~/repo_github/backscada/plan-manager/target/plan-manager.jar ~/app/backrits/plan-manager.jar
cp ~/repo_github/backscada/service-graphql/target/service-graphql.jar ~/app/backrits/service-graphql.jar
cp ~/repo_github/backscada/incident-manager/target/incident-manager.jar ~/app/backrits/incident-manager.jar
cp ~/repo_github/backscada/traffic-manager/target/traffic-manager.jar ~/app/backrits/traffic-manager.jar
cp ~/repo_github/backscada/virtual-equipment-manager/target/virtual-equipment-manager.jar ~/app/backrits/virtual-equipment-manager.jar
cp ~/repo_github/backscada/io-external-manager/target/io-external-manager.jar ~/app/backrits/io-external-manager.jar
cp ~/repo_github/backscada/server-launcher/target/server-launcher.jar ~/app/backrits/server-launcher.jar
        
echo -e "\n\nDeleting old resource files..."
sudo rm -Rf ~/app/backrits/resources/*

echo -e "\n\nChanging back parameters..."
sed -i "s|\(<p:broker>ws://\).*|\1$REMOTE_HOST:61614</p:broker>|" ~/repo_github/configscada/resources/project/$PROJECT/adapters/pub-stomp/pub_stomp_adapter_v1.xml
sed -i "s|\(<p:path>/home/\).*|\1$USER/app/backrits/</p:path>|" ~/repo_github/configscada/resources/project/$PROJECT/adapters/server-launcher/server_launcher_adapter_v1.xml
sed -i "s|\(jdbc.url=jdbc:postgresql://\).*|\1$REMOTE_BBDD:5430/rits|" ~/repo_github/configscada/resources/project/$PROJECT/jdbc.properties
sed -i "s|\(redis.host=\).*|\1$REMOTE_HOST|" ~/repo_github/configscada/resources/project/$PROJECT/redis.properties

echo -e "\n\nCopying new resources..."
cp -R ~/repo_github/configscada/resources/master/* ~/app/backrits/resources
cp -R ~/repo_github/configscada/resources/project/$PROJECT/* ~/app/backrits/resources
cp -R ~/repo_github/configscada/resources/project/$PROJECT/start.sh ~/app/backrits/start.sh

#Comprimo el fichero para mandarlo por sftp
echo -e "\n\nCompressing backrits..."
cd ~/app
tar zcvf backrits.tar.gz backrits
   
#Subo el archivo a la maquina remota
echo -e "\n\nUploading backrits to remote machine..."
sftp $USER@$REMOTE_HOST<<EOF
   put backrits.tar.gz
   quit
EOF
 
#Entro por ssh y actualizo los ficheros del back
echo -e "\n\nUpdating backrits on remote machine..."
ssh $USER@$REMOTE_HOST<<EOF
   mv /home/$USER/backrits.tar.gz /home/$USER/app/
   cd /home/$USER/app
   rm -rf backrits 
   tar zxvf backrits.tar.gz
   sleep 15
   exit 
EOF
}

front() {
#Copio recursos del front
echo -e "\n\nCopying front resources..."
sed -i "s|\(spring.profiles.active=\).*|\1$PROJECT|" ~/repo_github/frontscada/openits/src/main/resources/application.properties
sed -i "s|\(spring.datasource.url=jdbc:postgresql://\).*|\1$REMOTE_BBDD:5430/rits|" ~/repo_github/frontscada/openits/src/main/resources/application.properties
sed -i "s|\(ws://\).*\(:61614\)|\1$REMOTE_HOST\2|" ~/repo_github/frontscada/openits/src/main/resources/project/$PROJECT/data/config.js
rm -R ~/repo_github/frontscada/openits/src/main/resources/project
cp ~/repo_github/configscada/ritsfront/openits/*.properties ~/repo_github/frontscada/openits/src/main/resources/
cp -r ~/repo_github/configscada/ritsfront/openits/static ~/repo_github/frontscada/openits/src/main/resources/
cp -r ~/repo_github/configscada/ritsfront/openits/project ~/repo_github/frontscada/openits/src/main/resources/
cp -r ~/repo_github/configscada/ritsfront/openits/config ~/repo_github/frontscada/openits/src/main/resources/

#Compilo el front
echo -e "\n\nCompiling front..."
cd
cd ~/repo_github/frontscada/_ritsfront
mvn install

sleep 10 

#Subo el archivo a la maquina remota
echo -e "\n\nUploading openits.jar to remote machine..."
cd ~/repo_github/frontscada/openits/target
sftp $USER@$REMOTE_HOST<<EOF
   put openits.jar
   quit
EOF

#Entro por ssh y actualizo los ficheros del front 
echo -e "\n\nUpdating frontrits on remote machine..."
ssh $USER@$REMOTE_HOST<<EOF
   cd app/frontrits
   rm -rf openits.jar
   mv /home/$USER/openits.jar ./
   sed -i 's|\(active=\).*\( -jar\)|\1$PROJECT\2|' ~/app/start.sh
   exit
EOF
}

# Verificar si dialog está instalado
if ! command -v dialog &> /dev/null; then
    echo "Se ha detectado que no tiene dialog instalado en su sistema y es necesario para mostrar las opciones de actualización. Se procederá a instalar a continuación."
    sleep 2

    sudo apt update
    sudo apt install dialog
fi

trap ctrl_c SIGINT

mostrar_menu;clear
rellenar_parametros;clear

if [ "$selection" == 1 ] || [ "$selection" == 2 ]; then
   back;clear
fi

if [ "$selection" == 1 ] || [ "$selection" == 3 ]; then
   front;clear
fi

if [ "$selection" == 5 ]; then
   exit
fi

fill_db_back;clear
fill_db_front
