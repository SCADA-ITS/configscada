  # Instalo docker
  user=$(whoami)
  path=$(pwd)
  ip_address_db=$(hostname -I | awk '{print $1}')
  clear

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
  print_banner
  #Instalo Docker
  echo -e '\e[32mInstalando Docker\e[0m'                                                                                                                                                         
  sudo apt update
  sudo apt install apt-transport-https ca-certificates curl software-properties-common
  curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
  echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
  sudo apt update
  sudo apt install docker-ce
  clear
    
  #instalo dockercompose 
  print_banner

  echo -e '\e[32mInstalando Docker-compose\e[0m'
  latest_version=$(curl -s https://api.github.com/repos/docker/compose/releases/latest \ grep -oP '"tag_name": "\K[^"]+')
  mkdir -p ~/.docker/cli-plugins/
  curl -SL https://github.com/docker/compose/releases/download/$latest_version/docker-compose-linux-x86_64 -o ~/.docker/cli-plugins/docker-compose
  chmod +x ~/.docker/cli-plugins/docker-compose
  sudo apt update
  
  #Finalmente asigno usuario al grupo docker para no necesitar usar sudo
  sudo usermod -aG docker $user
  sudo usermod -aG docker $USER
  sudo apt update

  #Instalar psql
  clear
  print_banner

  echo -e '\e[32mInstalando PostgreSQL\e[0m'  
  sudo apt install postgresql postgresql-common 
  sudo apt update
  clear

  print_banner

  echo -e '\e[32mLevantando el contenedor....\e[0m'

  docker-compose up -d

  echo -e '\e[32mEsperando a que el contenedor esté preparado...\e[0m'
  wait
  docker ps

  psql postgresql://rits:rits@$ip_address_db:5430/rits -c "SELECT 1;" >/dev/null 2>&1
  
  if [ $? -eq 0 ]; then
	  directories=("tbl_conf" "tbl_static" "tbl_master" "tbl_rt" "tbl_hist" "tbl_ui")
		for dir_name in "${directories[@]}"; do
        	
          if [ -d "$path/timescaledb/$dir_name" ]; then
            echo -e "\e[33mLa carpeta $dir_name ya existe.\e[0m"
          else
            sudo mkdir -p "$path/timescaledb/$dir_name"
            echo -e "\e[32mLa carpeta $dir_name se ha creado correctamente.\e[0m"
          fi
        	sudo chown -R 70:$user $path/timescaledb/$dir_name
      
        	psql postgresql://rits:rits@$ip_address_db:5430/rits -c "CREATE TABLESPACE $dir_name OWNER rits LOCATION '/var/lib/postgresql/data/$dir_name';" > /dev/null 2>&1
           
     	done
	fi