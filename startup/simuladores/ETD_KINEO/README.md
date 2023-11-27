1- Copiar postgresql-42.5.0.jar en /opt/mango/lib (está en la carpeta \\192.168.88.4\s02\Tecnologia\Equipamiento Campo\ETDs\KINEO\Mango).
2- Reboot mango.
3- Copiar CREATE_TABLE.sql en la carpeta ddl y ejecutar make_param.sh para que cree la tabla que se va a usar para insertar los datos.
4- Usar json ETD_KINEO junto con su csv que encontramos en la capeta.
5- Una vez configurado el csv ejecutar copy_files.sh [nombre proyecto] y ejecutar Mango_create_old.py
6- En el archivo conect.py cambiar los parametros necesarios para la bbdd que se requiere(host, user...).
7- Ejecutar conect.py, se requiere instalar la libreria psycopg2 de python. Se le debe de meter por argumento el tiempo de insercion de datos
    (ej: tiempo de insercion = 1 min -> python3 conect.py 100.0)
8- Si se quiere borrar los registros de la tabla ejecutar DELELTE_TABLE.py
9- para parar el simulador CTRL + C.
