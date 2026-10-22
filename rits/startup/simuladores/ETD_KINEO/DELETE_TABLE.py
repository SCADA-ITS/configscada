#!/usr/bin/python
import psycopg2

try:
    connection=psycopg2.connect(
        host="192.168.88.191",
        user="rits",
        password="rits",
        port="5430",
        database="rits"
    )
    print("Conectado!!")

    cursor=connection.cursor()
    cursor.execute("DELETE FROM conf.simulador_etd")
    connection.commit()
    print("Registros borrados")
    connection.close()
    print("Conexion cerrada")
except Exception as ex:
    print(ex)
