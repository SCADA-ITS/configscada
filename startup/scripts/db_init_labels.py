# -*- coding: utf-8 -*-

'''
Created on 10 ago. 2020

@author: eliasTesta
'''
import psycopg2
from datetime import datetime
import sys
from importlib import reload

reload(sys)

HOST = sys.argv[1]
PORT = "5430"
DB = "rits"
USER = "rits"
PASS = "rits"

LOCALE_CODE = sys.argv[2]
MODULE_ID = sys.argv[3]
SCHEMA = sys.argv[4]

FIRST_PARAM_SUBSTRING = 2
SECOND_PARAM_SUBSTRING = -3

# current date and time
dateTimeObj = datetime.now()

if len(sys.argv) < 4:
    print("Argumentos necesarios: ")
    print("1.Un dato del campo locale_code de la tabla master.i18n_locales : ")
    print("2.Un dato del campo module_id de la tabla master.i18n_modules , COMO TIPO LONG, ejemplo : 3L: ")
    print("3.El nombre del table space de la bbdd  , pueden ser : master o conf")
    exit()

try:
    # Conectarse a la base de datos

    connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
        HOST, PORT, USER, PASS, DB)
    connection = psycopg2.connect(connectionChain)

    # Abrir un cursor para realizar operaciones sobre la base de datos
    cur = connection.cursor()

    # Ejecutar una consulta SELECT  PARA LA TABLA MASTER O CONF
    queryTables = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.columns WHERE table_schema = '" + \
        SCHEMA + "' and column_name = 'label_alias';"
    cur.execute(queryTables)

    # Obtener los resultados como objetos Python
    tables = cur.fetchall()
    countExist = 0

    for table in tables:
        tableString = str(table)
        tableParse = tableString[FIRST_PARAM_SUBSTRING:SECOND_PARAM_SUBSTRING]
        queryData = "SELECT label_alias ,label_description from " + \
            SCHEMA + "." + tableParse + ";"
        cur.execute(queryData)
        data = cur.fetchall()

        for datt in data:
            queryDataExist = "SELECT label, module_id, locale_code from master.i18n_labels;"
            cur.execute(queryDataExist)
            dataExist = cur.fetchall()

            for dattExist in dataExist:

                if(datt[0] != None):
                    label = datt[0]
                    values = (label, int(MODULE_ID), LOCALE_CODE)
                    countExist += 1

                    if values == dattExist:
                        countExist -= 1

                    if(countExist == len(dataExist)):
                        queryInsertAlias = "insert into master.i18n_labels(locale_code, module_id, label, translation, created_at, updated_at) values ('" + \
                            LOCALE_CODE + "'," + str(int(MODULE_ID)) + ",'" + \
                            datt[0] + "','¿¿" + datt[0] + \
                            "??', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);"
                        cur.execute(queryInsertAlias)
                        connection.commit()

            countExist = 0

            for dattExist in dataExist:

                if(datt[1] != None):
                    label = datt[1]
                    values = (label, int(MODULE_ID), LOCALE_CODE)
                    countExist += 1

                    if values == dattExist:
                        countExist -= 1

                    if(countExist == len(dataExist)):
                        queryInsertDescription = "insert into master.i18n_labels(locale_code, module_id, label, translation, created_at, updated_at) values ('" + \
                            LOCALE_CODE + "'," + str(int(MODULE_ID)) + ",'" + \
                            datt[1] + "','¿¿" + datt[1] + \
                            "??', CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);"
                        cur.execute(queryInsertDescription)
                        connection.commit()

            countExist = 0

    cur.close()
    connection.close()

except Exception as e:
    print(e)
