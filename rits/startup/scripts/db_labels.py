# -*- coding: utf-8 -*-

'''
Created on 15 dic. 2020

@author: aortega
'''

import os
import psycopg2
import csv
from datetime import datetime
import sys
from _ast import If

if len(sys.argv) < 4:
    print("Argumentos necesarios: ")
    print("1.Un/varios dato/s de la columna locale_code de la tabla master.i18n_locales")
    print("2.Un dato del campo module_id de la tabla master.i18n_modules , COMO TIPO INT, ejemplo : 1 ")
    print("3.IP de la BBDD")
    print("4.Puerto de la BBDD")
    exit()

# Variables para la conexion a BBDD
HOST = sys.argv[3]
PORT = sys.argv[4]
DB = "rits"
USER = "rits"
PASS = "rits"

FIRST_PARAM_SUBSTRING = 2
SECOND_PARAM_SUBSTRING = -3

cwd = os.path.dirname(os.path.abspath(__file__))
directorioLabels = os.path.join(cwd + '/Labels/')

destination_location = os.path.join(directorioLabels + 'labels.csv')

if not os.path.exists(os.path.dirname(destination_location)):

    try:

        os.makedirs(os.path.dirname(destination_location))
    except OSError as exc:

        if exc.errno != errno.EEXIST:

            raise

# creacion de array donde se insertaran los locales pasados por parametros
row_locales = [' ']
locales = sys.argv[1].split(',')
for item in locales:
    row_locales.append(item)

# Creacion de array donde se insertaran los labels
labels = []

try:
    # Conectarse a la base de datos
    connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
        HOST, PORT, USER, PASS, DB)
    connection = psycopg2.connect(connectionChain)

    # Abrir un cursor para realizar operaciones sobre la base de datos
    cur = connection.cursor()
    cur_schema = connection.cursor()

    # Ejecutar una consulta SELECT PARA RECOGER TODAS LAS TABLAS QUE CONTENGAN 'label_alias' EN SUS COLUMNAS
    queryTables = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.columns where column_name = 'label_alias';"
    queySchema = "SELECT table_schema FROM INFORMATION_SCHEMA.columns where column_name = 'label_alias';"
    cur.execute(queryTables)
    cur_schema.execute(queySchema)

    # Obtener los resultados como objetos Python
    tables = cur.fetchall()
    schemas = cur_schema.fetchall()

    schemaParseado = []

    countExist = 0
    contador = 0

    for schema in schemas:
        schemaString = str(schema)
        schemaParse = schemaString[FIRST_PARAM_SUBSTRING:SECOND_PARAM_SUBSTRING]
        schemaParseado.append(schemaParse)

    for table in tables:

        tableString = str(table)
        tableParse = tableString[FIRST_PARAM_SUBSTRING:SECOND_PARAM_SUBSTRING]
        queryData = "SELECT label_alias ,label_description from " + \
            str(schemaParseado[contador]) + "." + tableParse + ";"

        cur.execute(queryData)
        data = cur.fetchall()

        fila_data = 0
        for datt in data:
            queryDataExist = "SELECT label, module_id, locale_code from master.i18n_labels;"
            cur.execute(queryDataExist)
            dataExist = cur.fetchall()

            if(datt[0] != None):
                labels.append(datt[0])
                countExist += 1

            countExist = 0
        contador += 1
    cur.close()
    connection.close()
except AttributeError:
    print(AttributeError)
except ValueError:
    print(ValueError)
except Exception:
    print(Exception)

# Eliminamos los duplicados del array labels
labelsErroneosCorregido = set(labels)

with open(destination_location, "w", newline='') as f:
    escritura_csv = csv.writer(f)
    escritura_csv.writerow(row_locales)
    for i in sorted(labelsErroneosCorregido):
        escritura_csv.writerow([i, i, i, i])
f.close()
print("Creado fichero " + destination_location)
