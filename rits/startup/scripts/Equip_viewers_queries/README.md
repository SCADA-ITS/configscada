# Excel migration to database queries
## Leer Excel
 Este archivo permite leer un excel y generar SQL queries para la actualización de la base de datos.\
 Genera los siguientes archivos .sql en el directorio QUERIES/:
 * vo_entities.sql
 * equip_viewers.sql
 * equip_viewer_tabs.sql
 * equip_viewer_grp_measures.sql
 * equip_viewer_measures.sql

 Se deben actualizar las queries de la carpeta dml por las generadas en estos archivos.\
 Archivos a actualizar:
 * dml/proyecto/06.vo_extensions/99.vo_entities.sql
 * dml/proyecto/10.equip_viewers.sql

## Generar Excel
Crea un archivo excel con una hoja para cada elemento dentro de cada una de estas todos los parámetros posibles, indicando la posicion del parámetro y si este activo o inactivo.\
Al final de cada hoja se podrá indicar el numero de columnas que se quieren visualizar en cada elemento.

## Para ejecutar:
Ejecutar el archivo main.py con la IP y el puerto como parametros. Seguir las instrucciones que aparecen por línea de comandos.

## IMPORTANTE
Instalar las librerías: openpyxl, psycopg2 mediante:
* pip install openpyxl
* pip install psycopg2
