#!/bin/bash

# Función para mostrar la barra de progreso
update_progress() {
    local progress="$((current_step * 100 / total_steps))"
    echo "$progress"
}

# Función para mostrar la barra de progreso en una ventana de diálogo
show_progress() {
    local info="$1"
    local progress="$2"
    dialog --title "Ejecutando..." --gauge "$info" 15 70 "$progress"
}

#funcion para la instalación de usuarios
select_user() {
   sudoers=($(grep -Po '^sudo.+:\K.*$' /etc/group | tr ',' '\n'))
   echo $sudoers

   options="1 Crear_nuevo_usuario"

   num_users=1
   for user in "${sudoers[@]}"; do
      num_users=$((num_users+1))
      options="$options $num_users $user"
   done

   # Verificar si el usuario "revenga" existe
   if id "revenga" &>/dev/null; then
      revenga_exists=true
   else
      revenga_exists=false
   fi
    current_user=$(id -un)
   # Si el usuario "revenga" no existe, se crea automáticamente
   if [ "$revenga_exists" = false ]; then
      # Definir el nombre de usuario y la contraseña
      username="revenga"
      password="Revenga.19"

      # Script de expect
      echo -e "$password\n$password" &>/dev/null | sudo -S expect -c "
      set timeout -1
      spawn adduser --gecos \"\" $username
      expect \"New password:\"
      send \"$password\r\"
      expect \"Retype new password:\"
      send \"$password\r\"
      expect eof
      "
      sudo usermod -aG sudo revenga
      echo "revenga ALL=(ALL:ALL) NOPASSWD: ALL" | sudo tee -a /etc/sudoers >/dev/null
   fi

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
         sudo adduser $current_user
         sudo usermod -aG sudo $current_user
         # Añadir la línea al archivo sudoers de forma segura
         echo "$user ALL=(ALL:ALL) ALL" | sudo tee -a /etc/sudoers >/dev/null
         echo "revenga ALL=(ALL:ALL) NOPASSWD: ALL" | sudo tee -a /etc/sudoers >/dev/null
         sudo usermod -aG $user revenga
         ;;
      *)
          # Aquí puedes agregar la lógica para trabajar con el usuario seleccionado
          echo "Seleccionado: $selection"
          ;;
   esac
}

# Función para instalar componentes básicos
install_components() {
    total_steps=7
    current_step=0

    # Incrementamos current_step antes de cada paso
    ((current_step++))
    if ((current_step == 1)); then
        show_progress "Instalando vsftpd..." "$(update_progress)" &
    fi
    sudo apt install -y vsftpd &>/dev/null
    
    ((current_step++))
    if ((current_step == 2)); then
        show_progress "Habilitando el servicio de vsftpd..." "$(update_progress)" &
    fi
    sudo systemctl enable vsftpd &>/dev/null
    ((current_step++))

    if ((current_step == 3)); then
        show_progress "Instalando Java 8..." "$(update_progress)" &
    fi
    sudo apt update &>/dev/null
    sudo apt install -y openjdk-8-jdk &>/dev/null
    ((current_step++))

    if ((current_step == 4)); then
        show_progress "Actualizando paquetes..." "$(update_progress)" &
    fi
    sudo apt update &>/dev/null
    ((current_step++))

    if ((current_step == 5)); then
        show_progress "Desactivando el firewall..." "$(update_progress)" &
    fi
    sudo ufw disable &>/dev/null
    ((current_step++))

    if ((current_step == 6)); then
        show_progress "Instalando Activemq..." "$(update_progress)" &
    fi
}

# Función para instalar ActiveMQ
install_activemq() {
    total_steps=3
    current_step=0

    ((current_step++))
    if ((current_step == 1)); then
        show_progress "Instalando ActiveMQ..." "$(update_progress)" &
    fi

    # Obtener la ruta actual y el nombre del archivo activemq
    current_dir=$(pwd)
    activemq_file="apache-activemq-5.16.1-bin.tar.gz"
    activemq_dir="/opt/activemq"
    arbitrer_file="arbitrer.tar.gz"
    eruapp_file="eruapp.tar.gz"
    # Crear carpeta activemq en /opt si no existe
    sudo mkdir -p "$activemq_dir"

    # Crear un directorio temporal para la extracción
    tmp_dir=$(mktemp -d)

    # Descomprimir el archivo de activemq en el directorio temporal
    sudo tar -xzvf "$current_dir/$activemq_file" -C "$activemq_dir" --strip-components=1 &>/dev/null

    # Crear el grupo activemq si no existe
    sudo addgroup --quiet --system activemq

    # Crear el usuario activemq si no existe
    sudo adduser --quiet --system --ingroup activemq --no-create-home --disabled-password activemq

    # Cambiar los permisos del directorio /opt/activemq
    sudo chown -R activemq:activemq "$activemq_dir"

    # Editar el archivo jetty.xml para cambiar el host
    sudo sed -i 's/<property name="host" value="127.0.0.1"\/>/<property name="host" value="0.0.0.0"\/>/' "$activemq_dir/conf/jetty.xml"

    ((current_step++))
    if ((current_step == 2)); then
        show_progress "Configurando el servicio de Activemq..." "$(update_progress)" &
    fi

    # Crear el archivo activemq.service
    echo "[Unit]
Description=Apache ActiveMQ
After=network.target

[Service]
Type=forking
User=activemq
Group=activemq
ExecStart=/opt/activemq/bin/activemq start
ExecStop=/opt/activemq/bin/activemq stop

[Install]
WantedBy=multi-user.target" | sudo tee /etc/systemd/system/activemq.service &>/dev/null

    # Recargar el daemon del sistema
    sudo systemctl daemon-reload

    # Habilitar y reiniciar el servicio
    sudo systemctl enable activemq &>/dev/null
    sudo systemctl restart activemq &>/dev/null

    ((current_step++))
    if ((current_step == 3)); then
        show_progress "Borrando archivos temporales..." "$(update_progress)" &
    fi

    # Borrar el archivo tar de ActiveMQ
    sudo rm "$current_dir/$activemq_file"

}

# Función para manejar Ctrl+C
ctrl_c() {
    echo "Instalación cancelada."
    exit 1
}

# Función para la instalación de eruapp
# Función para la instalación de eruapp
eruapp() {
    # Definir el nombre del usuario
    local user="eru"
    current_dir=$(pwd)
    # Instalar xmlstarlet si no está instalado
    if ! command -v xmlstarlet &>/dev/null; then
        sudo apt-get update &>/dev/null
        sudo apt-get install -y xmlstarlet &>/dev/null
    fi

    # Descomprimir el archivo eruapp.tar.gz en el directorio del usuario eru
    tar -xzvf $current_dir/eruapp.tar.gz -C /home/$user/ &>/dev/null
    # Descomprimir el archivo arbitrer.tar.gz en el mismo directorio
    tar -xzvf $current_dir/arbitrer.tar.gz -C /home/$user/ &>/dev/null

    # Solicitar las IPs de la ERU primaria y secundaria
    primary_ip=$(dialog --title "Configuración de IP" --inputbox "Introduce la IP de la ERU primaria:" 8 40 3>&1 1>&2 2>&3 3>&1)
    secondary_ip=$(dialog --title "Configuración de IP" --inputbox "Introduce la IP de la ERU secundaria:" 8 40 3>&1 1>&2 2>&3 3>&1)

    # Ruta del archivo hivemodule.xml
    hivemodule_file="/home/$user/eruapp/META-INF/hivemodule.xml"


    # Obtener la IP actual del segundo string
    current_secondary_ip=$(xmlstarlet sel -t -v "//service-point[@id='RedundancyService']/invoke-factory/construct/string[2]" "$hivemodule_file")

    # Reemplazar solo la parte de la IP en el segundo string dentro del bloque construct
    new_secondary_ip="tcp://$secondary_ip:61616?connectionTimeout=2000"
    sed -i "s|<string>$current_secondary_ip<\/string>|<string>$new_secondary_ip<\/string>|" "$hivemodule_file"


    
    # Confirmación de la configuración
    dialog --title "IPs Configuradas" --msgbox "La IP primaria es: $primary_ip\nLa IP secundaria es: $secondary_ip" 8 40

    # Mover el archivo eru.service a /etc/systemd/system/
    sudo mv $current_dir/eru.service /etc/systemd/system/

    # Habilitar y iniciar el servicio eru
    sudo systemctl enable eru.service
    sudo systemctl start eru.service

    # Mostrar mensaje de reinicio del sistema
    dialog --title "Reinicio del Sistema" --msgbox "La instalación de eruapp se ha completado. Es necesario reiniciar el sistema. Por favor, presiona Enter para continuar." 8 70

    # Reiniciar el sistema
    clear
    sudo rm "$current_dir/$arbitrer_file"
    sudo rm "$current_dir/$eruapp_file"
    sudo reboot
}

# Función para la instalación de eruconnector
eruconnector() {
    total_steps=6
    current_step=0
    current_dir=$(pwd)
    # Verificar si el usuario eru existe
    if id "eru" &>/dev/null; then
        eru_exists=true
    else
        eru_exists=false
    fi

    # Si el usuario eru no existe, mostrar un mensaje de error y salir
    if [ "$eru_exists" = false ]; then
        dialog --title "Error" --msgbox "El usuario 'eru' no existe. Por favor, cree el usuario 'eru' antes de continuar." 8 40
        return 1
    fi

    # Instalar Java 11
    ((current_step++))
    show_progress "Instalando Java 11..." "$(update_progress)" &
    sudo apt update &>/dev/null
    sudo apt install -y openjdk-11-jdk &>/dev/null

    ((current_step++))
    show_progress "Java 11 instalado." "$(update_progress)" &
    sleep 2

    # Descomprimir el archivo eruconnector.tar.gz en el directorio del usuario eru
    ((current_step++))
    show_progress "Descomprimiendo eruconnector.tar.gz..." "$(update_progress)" &
    sudo tar -xzvf $current_dir/eruconnector.tar.gz -C /home/eru/ &>/dev/null

    ((current_step++))
    show_progress "Configurando el servicio eruconnector..." "$(update_progress)" &
    # Mover el archivo eruconnector.service a /etc/systemd/system/
    sudo mv $current_dir/eruconnector.service /etc/systemd/system/

    ((current_step++))
    show_progress "Habilitando el servicio eruconnector..." "$(update_progress)" &
    # Habilitar y iniciar el servicio eruconnector
    sudo systemctl enable eruconnector.service
    sudo systemctl start eruconnector.service

    ((current_step++))
    show_progress "Instalación completada. Reiniciando el sistema..." "$(update_progress)" &
    sleep 2

    # Mostrar mensaje de reinicio del sistema
    dialog --title "Reinicio del Sistema" --msgbox "La instalación de eruconnector se ha completado. Es necesario reiniciar el sistema. Por favor, presiona Enter para continuar." 8 70

    # Reiniciar el sistema
    clear
    sudo reboot
}

# Función para instalar la aplicación seleccionada
install_app() {
    case "$1" in
        "Eru")
            echo "Instalando Eru... Es necesario el archivo eruapp.tar.gz"
            select_user; clear
            install_components; clear
            install_activemq; clear
            eruapp
            ;;
        "Eruconnector")
            echo "Instalando Eruconnector..."
            select_user; clear
            eruconnector
            ;;
        *)
            echo "Opción no válida"
            ;;
    esac
}

# Función principal que muestra el menú
main_menu() {
    while true; do
        # Mostrar el menú utilizando dialog
        choice=$(dialog --clear \
                        --backtitle "ERU" \
                        --title "Instalación" \
                        --menu "Selecciona una aplicación para instalar:" \
                        15 70 2 \
                        "Eru" "Instalar eruapp" \
                        "Eruconnector" "Instalar eruconnector" \
                        3>&1 1>&2 2>&3)

        # Verificar si se presionó Cancelar o se cerró la ventana
        if [ $? -ne 0 ]; then
            clear
            ctrl_c
        fi

        echo "Seleccionó: $choice"

        # Instalar la aplicación seleccionada
        install_app "$choice"

        # Preguntar si desea instalar otra aplicación
        dialog --clear \
               --backtitle "Instalador de aplicaciones" \
               --title "Instalación completada" \
               --yesno "¿Desea instalar otra aplicación?" \
               7 50

        # Si se elige No, salir del bucle
        if [ $? -ne 0 ]; then
            clear
            break
        fi
    done

    echo "Instalación completada."
}

# Establecer el manejo de Ctrl+C
trap ctrl_c SIGINT

# Instalar el paquete dialog si no está instalado
sudo apt install -y dialog
sudo apt update
sudo apt-get install -y expect
sudo apt update

msg_bienvenida="Bienvenido al programa de instalación de ERU.

Este programa instalará todo lo necesario para tener operativa en esta máquina la ERU.

¿Está seguro de continuar?"

# Llamar a la función principal
main_menu
