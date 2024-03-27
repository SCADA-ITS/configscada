#!/bin/bash
user=""
path=""
ip_address_db=$(hostname -I | awk '{print $1}')


ctrl_c() {
   clear
   exit 1
}

print_banner() {
    echo "  ______   .______    _______ .__   __.  __  .___________.    _______.";
    echo " /  __  \  |   _  \  |   ____||  \ |  | |  | |           |   /       |";
    echo "|  |  |  | |  |_)  | |  |__   |   \|  | |  | \`---|  |----\`  |   (----\`";
    echo "|  |  |  | |   ___/  |   __|  |  . \`  | |  |     |  |        \   \    ";
    echo "|  \`--'  | |  |      |  |____ |  |\   | |  |     |  |    .----)   |   ";
    echo " \______/  | _|      |_______||__| \__| |__|     |__|    |_______/    ";
    echo "                                                                      ";
    echo "                                                                      ";
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
         sudo adduser $USER $user
         sudo usermod -aG sudo $user
         ;;
      *)

   esac
       # Actualizar variables user y path con el nuevo usuario
      user=$user
      path="/home/$user"
}

install_docker() {
    print_banner
   #Primero docker
   sudo apt update
   sudo apt install apt-transport-https ca-certificates curl software-properties-common

   curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
   echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
   sudo apt update

   sudo apt install docker-ce
   clear
   print_banner
   #Después dockercompose
   latest_version=$(curl -s https://api.github.com/repos/docker/compose/releases/latest \
                 | grep -oP '"tag_name": "\K[^"]+')

   mkdir -p ~/.docker/cli-plugins/
   curl -SL https://github.com/docker/compose/releases/download/$latest_version/docker-compose-linux-x86_64 -o ~/.docker/cli-plugins/docker-compose

   chmod +x ~/.docker/cli-plugins/docker-compose
   sudo apt update
   clear
   print_banner
   #Finalmente asigno usuario al grupo docker para no necesitar usar sudo
   sudo usermod -aG docker $user
   sudo usermod -aG docker $USER
   sudo apt update
}

install_components() {
   print_banner
   #Instalar psql
   sudo apt install postgresql postgresql-common 
   sudo apt update

   #Instalar net-tools
   sudo apt install net-tools
   sudo apt update
}

levantar_contenedor(){
 print_banner
  sudo chown -R $USER:$USER $path
  echo -e '\e[32mMoviendo docker-compose.yml al directorio del usuario...\e[0m'
  cp docker-compose.yml "$path/"
  
  echo -e '\e[32mLevantando el contenedor....\e[0m'
  cd "$path" || exit 1  # Cambiar al directorio del usuario
  docker compose up -d

  echo -e '\e[32mEsperando a que el contenedor esté preparado...\e[0m'
  wait
  docker ps
  echo -e '\e[32mEsperando a que psql esté preparado...\e[0m'
  sleep 10
  sudo psql postgresql://rits:rits@$ip_address_db:5430/rits -c "SELECT 1;" 2>&1
  
  if [ $? -eq 0 ]; then
	  directories=("tbl_conf" "tbl_static" "tbl_master" "tbl_rt" "tbl_hist" "tbl_ui")
		for dir_name in "${directories[@]}"; do
        	
          if [ -d "$path/timescaledb/$dir_name" ]; then
            echo -e "\e[33mLa carpeta $dir_name ya existe.\e[0m"
          else
            sudo mkdir -p "$path/timescaledb/$dir_name"
            echo -e "\e[32mLa carpeta $dir_name se ha creado correctamente.\e[0m"
          fi
        	sudo chown -R 70:$USER $path/timescaledb/$dir_name
      
        	psql postgresql://rits:rits@$ip_address_db:5430/rits -c "CREATE TABLESPACE $dir_name OWNER rits LOCATION '/var/lib/postgresql/data/$dir_name';" > /dev/null 2>&1
           
     	done
	fi
 sudo chown -R $user:$user $path
 sudo chown -R 70:root $path/timescaledb
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



trap ctrl_c SIGINT

sudo apt install dialog
sudo apt update

msg_bienvenida="
Bienvenido al programa de instalación de la base de datos OpenITS Revenga.

Este programa instalará todo lo necesario para tener operativa en esta máquina la base de datos OpenITS para el control y monitorización de tráfico en túneles y sistemas ITS.

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
    levantar_contenedor;
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
