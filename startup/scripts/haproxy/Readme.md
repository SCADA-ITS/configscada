# Manual de Usuario - Configuración de HAProxy con CSV

Este manual de usuario te guiará a través del proceso de configuración de HAProxy utilizando un archivo CSV. Sigue los pasos a continuación para generar los archivos `haproxy.cfg` y `docker-compose.yml` basados en tu archivo CSV de configuración.

## Paso 1: Configuración del archivo CSV

1. Abre el archivo `haproxy.csv` en un editor de texto o una hoja de cálculo.
2. Rellena cada fila del archivo con los datos correspondientes para cada configuración de HAProxy que deseas crear.
   - Asegúrate de seguir el formato y las cabeceras del archivo CSV como se muestra en el ejemplo proporcionado.

   A continuación, se describe el significado de cada campo en el archivo CSV:

   - `Server_Name`: El nombre del servidor o la configuración de HAProxy.
   - `PLC_IP`: La dirección IP del PLC al que HAProxy enviará las solicitudes.
   - `PLC_Port`: El puerto del PLC al que HAProxy enviará las solicitudes.
   - `HAProxy_Port`: El puerto en el que HAProxy escuchará las solicitudes de los clientes.
   - `Docker_Port`: El puerto que se mapeará desde el contenedor de HAProxy al host.
   - `Max_Connections`: El número máximo de conexiones permitidas para esta configuración de HAProxy.

   Proporciona los valores correspondientes para cada columna en cada fila del archivo CSV.
   - Puedes agregar tantas filas como desees para configuraciones adicionales de HAProxy.
   - Cada par tiene que tener el `Server_Name`que coincida para que el codigo entienda que son pareja.

3. Guarda el archivo `haproxy.csv` después de completar todos los datos.

## Paso 2: Generación de los archivos de configuración

1. Abre una terminal o línea de comandos en tu sistema.
2. Navega hasta el directorio donde tienes el archivo `haproxy.py`.
3. Ejecuta el siguiente comando para generar los archivos de configuración:

python3 haproxy.py 


El comando generará los archivos `haproxy.cfg` y `docker-compose.yml` basados en los datos proporcionados en el archivo CSV.

## Paso 3: Ejecución del contenedor HAProxy

1. Coloca los archivos `haproxy.cfg` en la carpeta `haproxy` y `docker-compose.yml` en el usuario principal fuera de esa carpeta.
2. En la terminal, navega hasta el directorio que contiene el `docker-compose.yml`.
3. Ejecuta el siguiente comando para iniciar el contenedor HAProxy:

docker-compose up -d

Esto iniciará el contenedor HAProxy en segundo plano. Si quieres ver si configuración esta arrojando algun error ejecuta el comando sin la opción `-d`.

## Uso de las estadísticas de HAProxy

HAProxy proporciona una interfaz de estadísticas que puedes utilizar para supervisar y analizar el rendimiento del balanceador de carga. Sigue estos pasos para acceder a las estadísticas de HAProxy:

1. Abre un navegador web en tu sistema y visita la siguiente URL:

http://localhost:10000/haproxy_stats

Asegúrate de reemplazar `localhost` por la dirección IP o el nombre de host del servidor donde se ejecuta HAProxy si no estás accediendo desde la misma máquina.

2. Se te pedirá que inicies sesión. Ingresa las siguientes credenciales:

   - Nombre de usuario: `admin`
   - Contraseña: `admin`

   Si deseas cambiar estas credenciales, puedes modificar la sección correspondiente en el archivo `haproxy.cfg` antes de generar los archivos de configuración.

3. Una vez que hayas iniciado sesión, se te presentará la interfaz de estadísticas de HAProxy.
   - Aquí encontrarás información detallada sobre el estado de los servidores, las conexiones, las sesiones y más.
   - Puedes utilizar estas estadísticas para monitorear el rendimiento y realizar ajustes en tu configuración de HAProxy si es necesario.

## Actualización de la configuración

Si deseas realizar cambios en la configuración, puedes editar el archivo CSV y repetir los pasos 2 y 3 para generar y ejecutar una nueva configuración.