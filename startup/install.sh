#!/bin/bash
user=""
project="fullequip"
path=""
ip_address_db=""


ctrl_c() {
   clear
   exit 1
}

select_user() {
   sudoers=($(grep -Po '^sudo.+:\K.*$' /etc/group | tr ',' '\n'))
   echo $sudoers

   options="1 Crear_nuevo_usuario"

   num_users=1
   for user in "${sudoers[@]}"; do
      num_users=$((num_users+1))
      options="$options $num_users $user"
   done

   selection=$(dialog --title "Selección de usuario" --stdout --menu "\nIndique el usuario que se utilizará para la instalación (Debe estar en minúsculas y sin símbolos)" 20 50 $num_users $options)
   echo $selection 

   if [ -z "$selection" ]; then
      clear
      exit 1
   fi

   case "$selection" in
      1)
         while true; do
            dialog \
              --inputbox "\nIntroduce el nombre de usuario que quieres utilizar para la instalacion" \
              15 70 2>/tmp/user

            declare -g user=$(cat /tmp/user)

	    if [[ -n "$user" ]] && [[ $user =~ ^[a-z]+$ ]]; then
               break
            else
               dialog \
                  --msgbox "!El nombre de usuario no puede estar vacío o no es valido! Por favor, ingrésalo de nuevo." \
                  15 70

               if [ $? -ne 0 ]; then
                  exit 0
               fi
            fi
         done

	 clear
         sudo adduser $user 
         sudo usermod -aG sudo $user
         ;;
      *)
          # Aquí puedes agregar la lógica para trabajar con el usuario seleccionado
          echo "Seleccionado: $selection"
          ;;
   esac
}

install_docker() {
   #Primero docker
   sudo apt update
   sudo apt install apt-transport-https ca-certificates curl software-properties-common

   curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
   echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
   sudo apt update

   sudo apt install docker-ce
   clear

   #Después dockercompose
   latest_version=$(curl -s https://api.github.com/repos/docker/compose/releases/latest \
                 | grep -oP '"tag_name": "\K[^"]+')

   mkdir -p ~/.docker/cli-plugins/
   curl -SL https://github.com/docker/compose/releases/download/$latest_version/docker-compose-linux-x86_64 -o ~/.docker/cli-plugins/docker-compose

   chmod +x ~/.docker/cli-plugins/docker-compose
   sudo apt update
   clear
   
   #Finalmente asigno usuario al grupo docker para no necesitar usar sudo
   sudo usermod -aG docker $user
   sudo usermod -aG docker $USER
   sudo apt update
}

install_components() {
   #Instalar jre de java 11
   sudo apt install openjdk-11-jdk
   sudo apt update

   #Instalar psql
   sudo apt install postgresql postgresql-common 
   sudo apt update

   #Instalar net-tools
   sudo apt install net-tools
   sudo apt update
}

install_openits() {
   default_value="/home/$user"

   while true; do
     dialog --backtitle "SCADA OPENITS" \
       --title "Ruta de instalación" \
       --inputbox "\nIndique la ruta para la instalación de la aplicación:\n" 8 40 "$default_value" 2>/tmp/input_result

     if [ $? -ne 0 ]; then
	clear
        exit 0
     fi

     declare -g path=$(cat /tmp/input_result)

     if [ -n "$path" ]; then
       break 
     else
       dialog \
         --msgbox "!El path de instalación no puede estar vacío! Por favor, ingrésalo de nuevo." \
         15 70
     fi
   done

   rm /tmp/input_result

   sudo mkdir -p $path
   sudo chown -R $USER:$USER $path

   while true; do
      if ls openits* 1>/dev/null 2>&1; then
        clear

        tempfile=$(mktemp)

        mv ./openits_*.tar.gz $path
        cd $path
        tar zxvf openits_*.tar.gz > "$tempfile"  2>&1 &

        tar_pid=$!

        progress=0
        dialog --title "Copiando" --gauge "\nCopiando recursos.Espere por favor..." 10 70 < <(
           while kill -0 $tar_pid 2>/dev/null; do
	      progress=$((progress+10))

	      if (( $progress > 100 )); then
	         progress=100
	      fi  

              echo "$progress"

              sleep 1
           done
        )
        echo 100 | dialog --title "Progreso" --gauge "\nProgreso completado" 10 70
        sleep 2

	mkdir -p $path/app/haproxy
	mv $path/app/haproxy.cfg $path/app/haproxy/
        clear

	cd $path/app/
        docker compose up -d &

	wait

	echo "Pulse una tecla para continuar"
	read

	break
      else
         dialog \
	       --msgbox "No existe ningún fichero de intalación (tiene que tener el formato opentis_*.tar.gz) en la ruta de instalación actual: $PWD. Copie uno y pulse Aceptar." \
         15 70
      fi
   done

   sed -i "s/admin/$user/g" "/home/$user/app/app.service"
   sudo mv $path/app/app.service /etc/systemd/system/
   sudo mv $path/app/backrits/resources/start.sh /home/$user/app/

   ip_default_value=$(hostname -I | awk '{print $1}')

   while true; do
     dialog --backtitle "SCADA OPENITS" \
       --title "Dirección IP SCADA OPENITS" \
       --inputbox "\nIndique la dirección IP de la máquina de la aplicación:\n" 8 40 "$ip_default_value" 2>/tmp/input_result

     if [ $? -ne 0 ]; then
	clear
        exit 0
     fi

     ip_address=$(cat /tmp/input_result)

     if [ -n "$ip_address" ]; then
       break 
     else
       dialog \
         --msgbox "!La IP de instalación no puede estar vacío! Por favor, ingrésala de nuevo." \
         15 70
     fi
   done

   rm /tmp/input_result

   while true; do
     dialog --backtitle "SCADA OPENITS" \
       --title "Dirección IP BBDD SCADA OPENITS" \
       --inputbox "\nIndique la dirección IP de la máquina de la BBDD:\n" 8 40 "$ip_default_value" 2>/tmp/input_result

     if [ $? -ne 0 ]; then
	clear
        exit 0
     fi

     declare -g ip_address_db=$(cat /tmp/input_result)

     if [ -n "$ip_address_db" ]; then
       break 
     else
       dialog \
         --msgbox "!La IP de la BBDD no puede estar vacío! Por favor, ingrésala de nuevo." \
         15 70
     fi
   done

   rm /tmp/input_result

   start_sh="$path/app/start.sh"
   jdbc_properties="$path/app/backrits/resources/jdbc.properties"
   redis_properties="$path/app/backrits/resources/redis.properties"
   stomp_properties="$path/app/backrits/resources/adapters/pub-stomp/pub_stomp_adapter_v1.xml"

   sed -i "s|/home/[^[:space:]]*/app/backrits/server-launcher.jar|/home/$user/app/backrits/server-launcher.jar|" "$start_sh"
   sed -i "s|/home/[^[:space:]]*/app/frontrits/openits.jar|/home/$user/app/frontrits/openits.jar|" "$start_sh"

   sed -i "s/jdbc.url=jdbc:postgresql:\/\/.*$/jdbc.url=jdbc:postgresql:\/\/$ip_address_db:5430\/rits/" "$jdbc_properties"
   sed -i "s|^redis.host=.*$|redis.host=$ip_address|" "$redis_properties"
   sed -i "s|<p:broker>ws://.*$|<p:broker>ws://$ip_address:61614</p:broker>|" "$stomp_properties"

   clear

   tempfile=$(mktemp)
   directory="BOOT-INF/classes"

   cd $path/app/frontrits
   jar xvf openits.jar $directory > "$tempfile" 2>&1 &

   jar_pid=$!

   progress=0
   dialog --title "Copiando" --gauge "\nCopiando recursos.Espere por favor..." 10 70 < <(
      while kill -0 $jar_pid 2>/dev/null; do
	 progress=$((progress+5))

	 if (( $progress > 100 )); then
	    progress=100
	 fi 

         echo "$progress"

         sleep 1
      done
   )
   echo 100 | dialog --title "Progreso" --gauge "\nProgreso completado" 10 70
   sleep 2
   clear

   #while true; do
   #  dialog --backtitle "SCADA OPENITS" \
   #    --title "Nombre del proyecto" \
   #    --inputbox "Indique el nombre del proyecto que está instalando" 8 40 2>/tmp/input_result

   #  if [ $? -ne 0 ]; then
   #     clear
   #     exit 0
   #  fi

   #  declare -g project=$(cat /tmp/input_result)

   #  if [ -n "$project" ]; then
   #    directory="BOOT-INF/classes"

   ##    if [ -d "$directory/project/$project" ]; then
   #       break
   #    else
   #       dialog \
   #         --msgbox "El fichero de instalación no contiene recursos para un proyecto de nombre $project. Ingrese otro nombre" \
   #         15 70
   #    fi
   #  else
    #   dialog \
    #     --msgbox "!El nombre del proyecto no puede estar vacío! Por favor, ingrésalo de nuevo." \
    #     15 70
    # fi
   #done

   application_properties="$directory/application.properties"
   data_properties="$directory/project/fullequip/data/config.js"
   sed -i "s/spring.datasource.url=jdbc:postgresql:\/\/.*$/spring.datasource.url=jdbc:postgresql:\/\/$ip_address_db:5430\/rits/" "$application_properties"
   sed -i "s/ws:\/\/[^:]*:61614/ws:\/\/$ip_address:61614/g" "$data_properties"

   jar uvf openits.jar $directory > "$tempfile" 2>&1 &

   jar_pid=$!

   progress=0
   dialog --title "Copiando" --gauge "\nCopiando recursos.Espere por favor..." 10 70 < <(
      while kill -0 $jar_pid 2>/dev/null; do
	 progress=$((progress+5))

	 if (( $progress > 100 )); then
	    progress=100
	 fi 

         echo "$progress"

         sleep 1
      done
   )
   echo 100 | dialog --title "Progreso" --gauge "\nProgreso completado" 10 70
   rm -rf $directory
   sleep 2
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

fill_db() {
  psql postgresql://rits:rits@$ip_address_db:5430/rits -c "SELECT 1;" >/dev/null 2>&1

  if [ $? -eq 0 ]; then 
     directories=("tbl_conf" "tbl_static" "tbl_master" "tbl_rt" "tbl_hist" "tbl_ui")

     #for dir_name in "${directories[@]}"; do
     #   sudo mkdir -p "$path/app/timescaledb/$dir_name"
     #   sudo chown -R 70:$user $path/app/timescaledb/$dir_name
      
     #   psql postgresql://rits:rits@$ip_address_db:5430/rits -c "CREATE TABLESPACE $dir_name OWNER rits LOCATION '/var/lib/postgresql/data/$dir_name';" > /dev/null 2>&1 
     #done
 
     #Empezamos con los scripts de ddl
     cd $path/app/db_scripts/ritsback/ddl
  
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

          psql postgresql://rits:rits@$ip_address_db:5430/rits -b -f $eachfile >/dev/null
     done
  
     wait
     last_progress=0

     #Continuamos con los scripts de master 
     cd $path/app/db_scripts/ritsback/dml/master
  
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

          psql postgresql://rits:rits@$ip_address_db:5430/rits -b -f $eachfile >/dev/null
     done
  
     wait
     last_progress=0

     #Continuamos con los scripts de proyecto 
     cd $path/app/db_scripts/ritsback/dml/maqueta_fullequip
  
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

          psql postgresql://rits:rits@$ip_address_db:5430/rits -b -f $eachfile >/dev/null
     done

     wait

     rm -rf $path/app/db_scripts
     #rm -rf $path/openits_*.tar.gz
     
     clear
     systemctl enable app
     systemctl start app
     sudo chown -R $user:$user $path
     dialog --backtitle "SCADA OPENITS" --title "Instalación completada" --msgbox "\nEnhorabuena ha completado satisfactoriamente la instalación de la aplicación OPENITS en la máquina actual. La maquina se reiniciará." 15 70
     clear
     sudo reboot
  else
     rm -rf $path/app/db_scripts
     #rm -rf $path/openits_*.tar.gz

     clear
     systemctl enable app
     systemctl start app
     sudo chown -R $user:$user $path
     dialog --backtitle "SCADA OPENITS" --title "Instalación completada sin acceso a la BBDD" --msgbox "\nEnhorabuena ha completado satisfactoriamente la instalación de la aplicación OPENITS en la máquina actual. Si necesita rellenar la BBDD puede hacerlo de forma automática a posteriori con el fichero de instalación correspondiente. La máquina se reiniciará." 15 70
     clear
     sudo reboot
  fi
}


trap ctrl_c SIGINT

sudo apt install dialog
sudo apt update

msg_bienvenida="
Bienvenido al programa de instalación de SCADA OpenITS Revenga.

Este programa instalará todo lo necesario para tener operativa en esta máquina la aplicación OpenITS para el control y monitorización de tráfico en túneles y sistemas ITS.

¿Está seguro de continuar?"

dialog \
  --backtitle "SCADA OPENITS" \
  --title "Instalación" \
  --yesno "$msg_bienvenida" \
  15 70

response=$?

case $response in
  0) 
    select_user;clear
    install_docker;clear
    install_components;clear
    install_openits;clear
    fill_db
    ;;
  1) 
    echo "Saliendo del instalador..."
    clear
    ;;
  255) 
    echo "Bye!"
    clear
    ;;
esac

# Fin del script
