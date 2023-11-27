# Excel migration to database queries
## Leer Excel
 Este archivo permite leer un excel y generar SQL queries para la actualización de la base de datos
 Permite dos opciones:
 * Ejecutar las consultas automaticamente
 * Generar un archivo sql con todas las actualizaciones

## Generar Excel
Crea un archivo excel con una hoja para cada elemento dentro de cada una de estas todas las alarmas posibles, indicando el nivel de la alarma y si este activo o inactivo.

## Para ejecutar:
Ejecutar el archivo db_create_alarms.py con la IP y el puerto como parametros. Seguir las instrucciones que aparecen por linea de comandos

## IMPORTANTE
Instalar las librerías: openpyxl, psycopg2 mediante:
* pip install openpyxl
* pip install psycopg2