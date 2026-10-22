# -*- coding: utf-8 -*-

'''
Created on 16 dic. 2020

@author: aortega
'''

import os
import csv
import psycopg2
import sys
from _ast import If
from importlib import reload
reload(sys)

if len(sys.argv) < 2:
    print("Argumentos necesarios: ")
    print("1.IP de la BBDD")
    print("2.Puerto de la BBDD")
    exit()

HOST = sys.argv[1]
PORT = sys.argv[2]
DB = "rits"
USER = "rits"
PASS = "rits"

FIRST_PARAM_SUBSTRING = 2
SECOND_PARAM_SUBSTRING = -3

MODULE_ID = 0
created_at = 'CURRENT_TIMESTAMP'
updated_at = 'CURRENT_TIMESTAMP'

cwd = os.path.dirname(os.path.abspath(__file__))
archivoLabels = os.path.join(cwd + '/Labels/labels.csv')
archivoQuery = os.path.join(cwd + '/Labels/inserts.sql')

resultado_labels = "INSERT INTO master.i18n_labels (locale_code, module_id, \"label\", \"translation\", created_at, updated_at) VALUES \n"

try:

    # Conectarse a la base de datos
    connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
        HOST, PORT, USER, PASS, DB)
    connection = psycopg2.connect(connectionChain)

    # Abrir un cursor para realizar operaciones sobre la base de datos
    cur = connection.cursor()

    # Ejecutar una consulta SELECT PARA RECOGER TODAS LAS TABLAS QUE CONTENGAN 'label_alias' EN SUS COLUMNAS
    queryLocales = "SELECT locale_code FROM master.i18n_locales;"
    cur.execute(queryLocales)

    # Obtener los resultados como objetos Python
    locales = cur.fetchall()

    localParseado = []
    localesErroneos = []

    for local in locales:
        localString = str(local)
        localParse = localString[FIRST_PARAM_SUBSTRING:SECOND_PARAM_SUBSTRING]
        localParseado.append(localParse)

    with open(archivoLabels, "r") as f:
        reader = csv.reader(f, delimiter=',')
        line_count = 0

        for row in reader:

            if line_count == 0:

                encabezado = ','.join(row).split((','))
            else:

                parametros = ','.join(row).split((','))
                contador = 0
                for positionEncabezado in encabezado:
                    if contador == 0:
                        contador += 1
                        pass
                    else:
                        if encabezado[contador] in localParseado:
                            resultado_labels += "('" + \
                                encabezado[contador] + "', "
                            resultado_labels += "" + str(MODULE_ID) + ", "
                            resultado_labels += "'" + parametros[0] + "', "
                            resultado_labels += "'" + \
                                parametros[contador] + "', "
                            resultado_labels += created_at + ","
                            resultado_labels += updated_at + "),"
                            resultado_labels += "\n"
                            contador += 1
                        else:
                            localesErroneos.append(encabezado[contador])
                            contador += 1

            line_count += 1
    f.close()

except AttributeError as attributeError:
    print(attributeError)
except ValueError as valueError:
    print(valueError)
except Exception as exception:
    print(exception)
else:
    try:
        # Eliminamos los duplicados del array localesErroneos
        localesErroneosCorregido = set(localesErroneos)

        print("Los siguientes locales no son validos: ", localesErroneosCorregido)

        last_char_index_labels = resultado_labels.rfind(",")
        new_string_labels = resultado_labels[:last_char_index_labels] + ";"

        with open(archivoQuery, "w", newline='') as f:
            f.write(new_string_labels)
        f.close()

        print("Creado fichero " + archivoQuery)
    except GeneratorExit:
        print(GeneratorExit)
