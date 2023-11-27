# -*- coding: utf-8 -*-

'''
Created on 4 may. 2020

@author: eliasTesta
'''

import sys
reload(sys)
sys.setdefaultencoding('utf8')
import os
import json
import collections
import openpyxl
import base64

wb = openpyxl.load_workbook('Pictogramas.xlsx', data_only=True)

sheetList = wb.sheetnames
graphics = "{\n\t"
translations = ""
ids = ["A", "I"]
labels = ["F", "N"]
destino = ""
empty_picto = '"":{ \n\t\t"alias": "empty", \n\t\t"type_label": "empty", //Advertencia de peligro \n\t\t"type": 1, \n\t\t"file_type": "bmp", \n\t\t"base64": "iVBORw0KGgoAAAANSUhEUgAAAEAAAABAAgMAAADXB5lNAAAADFBMVEUAAAD/AAD///8AAACMA1FsAAAAAWJLR0QAiAUdSAAAAAlwSFlzAAAOwwAADsMBx2+oZAAAAAd0SU1FB+QGGgsuHgy4asEAAAASSURBVDjLY2AYBaNgFIwC8gAABEAAASmN4VUAAAAASUVORK5CYII="},\n\t'
picto_0 = '"0":{ \n\t\t"alias": "empty", \n\t\t"type_label": "empty", //Advertencia de peligro \n\t\t"type": 1,\n\t\t"file_type": "bmp", \n\t\t"base64": "iVBORw0KGgoAAAANSUhEUgAAAEAAAABAAgMAAADXB5lNAAAADFBMVEUAAAD/AAD///8AAACMA1FsAAAAAWJLR0QAiAUdSAAAAAlwSFlzAAAOwwAADsMBx2+oZAAAAAd0SU1FB+QGGgsuHgy4asEAAAASSURBVDjLY2AYBaNgFIwC8gAABEAAASmN4VUAAAAASUVORK5CYII="},\n\t'
undefined_picto = '"undefined": { \n\t\t"alias": "empty", \n\t\t"type_label": "empty", //Advertencia de peligro \n\t\t"type": 1, \n\t\t"file_type": "bmp", \n\t\t"base64": "iVBORw0KGgoAAAANSUhEUgAAAEAAAABAAgMAAADXB5lNAAAADFBMVEUAAAD/AAD///8AAACMA1FsAAAAAWJLR0QAiAUdSAAAAAlwSFlzAAAOwwAADsMBx2+oZAAAAAd0SU1FB+QGGgsuHgy4asEAAAASSURBVDjLY2AYBaNgFIwC8gAABEAAASmN4VUAAAAASUVORK5CYII="},\n\t'

if len(sys.argv) < 2:
    print("Es necesario el nombre del proyecto como argumento:")
    print("  arg1 = Name of Proyect")
    exit()

if not (sys.argv[1] == 'ausol' or sys.argv[1] == 'bratislava'):
    print("Es necesario escribir el nombre del proyecto correctamente:")
    print("Opciones:")
    print("1: ausol:")
    print("2: bratislava:")
    exit()

path = os.path.abspath("proyect/" + sys.argv[1] + "/img/pictograms")
name_project = sys.argv[1]

for sheetCount in range(len(wb.sheetnames)):
    translations += '"pictograms.type.' + str(sheetCount+1) + '":"' + wb.sheetnames[sheetCount] + '",\n'
translations = translations[:-3] + '\n\n'

cont_column = 0;
graphics += empty_picto + picto_0 + undefined_picto
for column_name in ids:
    for sheetCount in range(len(wb.sheetnames)):
        translations += '\n//' + wb.sheetnames[sheetCount] + '\n'
        wb.active = sheetCount
        worksheet = wb.get_sheet_by_name(wb.get_sheet_names()[sheetCount])
        for row in range(3, wb.active.max_row + 1):
            id = worksheet[column_name + str(row)].value
            label = worksheet[labels[cont_column] + str(row)].value
            if label != None:
                translations += '"VmsGraphic:2:' + str(id) + '":"' + label + '",\n'
            if (sys.argv[1] == name_project):
                if os.path.exists(path + "/" + str(id) + ".bmp"):                  
                    if id != None:
                        graphics += '"' + str(id) + '": {\n\t\t'
                        graphics += '"alias": $.i18n("VmsGraphic:2:' + str(id) + '"),\n\t\t'
                        graphics += '"type_label": $.i18n("pictograms.type.' + str(sheetCount + 1) + '"),\t//' + \
                                     wb.sheetnames[sheetCount] + "\n\t\t"
                        graphics += '"type": ' + str(sheetCount + 1) + ",\n\t\t"
                        graphics += '"file_type": "bmp",\n\t\t'
                        if id != None:
                            with open(path + "/" + str(id) + ".bmp", "rb") as image_file:
                                data = image_file.read()
                                graphics += '"base64": "' + data.encode("base64").replace('\n', '') + '"},\n\t'
                                destino = sys.argv[1]
    cont_column += 1

graphics = graphics[:-3]
graphics += '\n}'

file_name = "data" + destino + ".json"
out_file = open(file_name, "w+")
out_file.write(graphics)
out_file.close()
print("archivo creado")

file_name = "data" + destino + ".txt"
out_file = open(file_name, "w+")
out_file.write(translations)
out_file.close()
print("archivo creado")

