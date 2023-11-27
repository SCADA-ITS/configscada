# Script de Generación de Sentencias INSERT SQL

Este script se utiliza para conectar a una base de datos PostgreSQL, obtener el contenido de varias tablas y preparar sentencias INSERT SQL para respaldar los datos de esas tablas en archivos SQL individuales.

## Requisitos

- Python 3.x
- Biblioteca psycopg2 para conectarse a la base de datos PostgreSQL.

Puede instalar psycopg2 utilizando pip:

```shell
pip install psycopg2
```
Uso
Ejecute el script con los siguientes argumentos:

```shell
python3 db_backup_bbdd.py <host> <puerto>
Donde <host> es la dirección IP de la base de datos PostgreSQL a la que desea conectarse y <puerto> es el puerto de la base de datos.
```
El script leerá los nombres de las tablas desde el archivo tables_backup.txt. Asegúrese de que este archivo esté presente en el mismo directorio que el script y contenga los nombres de las tablas que desea respaldar. Puede estructurar el archivo tables_backup.txt de la siguiente manera:

```diff
+Carpeta_1
    Nombre_de_la_Tabla_3
    Nombre_de_la_Tabla_4
+Carpeta_2
    Nombre_de_la_Tabla_5
```
Las líneas que comienzan con + se consideran nombres de carpetas y se crearán como directorios para organizar los archivos SQL generados.

Las sentencias INSERT SQL generadas se guardarán en archivos individuales en carpetas correspondientes a las tablas y subcarpetas según la estructura definida en tables_backup.txt.

Notas

El script generará un archivo SQL por cada tabla en la base de datos y organizará estos archivos en carpetas según la estructura de tables_backup.txt.

Los archivos SQL generados tendrán el formato XX.Nombre_de_la_Tabla.sql, donde XX es un número de dos dígitos que indica el orden en el que se generaron las sentencias INSERT SQL.
