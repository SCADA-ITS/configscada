# -*- coding: utf-8 -*-

'''
Created on 02 January 2024

@author: alvaro.ortega
@mail: alvaro.ortega@revenga.com
'''

import psycopg2
import sys
import os
import errno

if len(sys.argv) < 4:
    print("Argumentos necesarios: ")
    print("1.Dirección IP de la BBDD")
    print("2.Locale code válido, por ejemplo: es_ES, en_GB")
    print("3.El nombre del schema de la bbdd, por ejemplo: master, conf")
    print("Ejemplo: python3 db_missing_labels.py 192.168.88.51 es_ES master")
    exit()

HOST, LOCALE_CODE, SCHEMA = sys.argv[1:4]
PORT = "5430"
DB = "rits"
USER = "rits"
PASS = "rits"

'''
    Funcion para conectarme a la BBDD
'''
def database_connection():
    connectionChain = f"host={HOST} port={PORT} user={USER} password={PASS} dbname={DB}"
    return psycopg2.connect(connectionChain)

'''
    Funcion que maneja las queries y devuelve el resultado de la query
'''
def execute_query(cursor, query):
    cursor.execute(query)
    return cursor.fetchall()

'''
    Funcion que maneja las listas pasadas por parametro, las convierte en "sets" que son listas sin valores repetidos
    y devuelve un set con los valores que existen en el set de nuestro repositorio y no existen en BBDD
'''
def get_difference_between_sets(labels, labels_bbdd):
    new_set_labels = set(labels)
    new_set_labels_bbdd = set(labels_bbdd)
    return new_set_labels - new_set_labels_bbdd

'''
    Funcion que crea el archivo SQL con las labels que faltan
'''
def write_file(location, query):
    try:
        os.makedirs(os.path.dirname(location))
    except OSError as exc: 
        if exc.errno != errno.EEXIST:
            raise

    with open(location, "w+") as f:
        f.write(query)
        f.close()
        print(f"Creado fichero missing_labels.sql en {cwd}") 

try:
    queryInsert = "insert into master.i18n_labels(locale_code, module_id, label, translation, created_at, updated_at) values\n"

    cwd = os.path.dirname(os.path.abspath(__file__))
    destination = os.path.join(cwd + '/missing_labels.sql')

    connection = database_connection()

    # Abrir un cursor para realizar operaciones sobre la base de datos
    cursor = connection.cursor()

    # Ejecutar una consulta SELECT  PARA LA TABLA MASTER O CONF
    queryTables = f"SELECT TABLE_NAME FROM INFORMATION_SCHEMA.columns WHERE table_schema = '{SCHEMA}' and column_name = 'label_alias';"
    tables = execute_query(cursor, queryTables)

    queryLabels = f"SELECT label from master.i18n_labels where module_id = 1 and locale_code='{LOCALE_CODE}'"
    labels_i18n = execute_query(cursor, queryLabels)

    labels = []
    labels_bbdd = []
    
    for label in labels_i18n:
        labels_bbdd.append(label[0])

    for table in tables:
        queryData = f"SELECT label_alias ,label_description from {SCHEMA}.{table[0]};"
        cursor.execute(queryData)
        data = cursor.fetchall()

        for datt in data:
            if(datt[0] != None):
                labels.append(datt[0])

            if(datt[1] != None):
                labels.append(datt[1])
    
    cursor.close()
    connection.close()

    labels_insert = get_difference_between_sets(labels, labels_bbdd)

    for label in labels_insert:
        queryInsert += f"('{LOCALE_CODE}', 1, '{label}', '{label}', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),\n"

    write_file(destination, queryInsert)

except Exception as e:
    print(e)
