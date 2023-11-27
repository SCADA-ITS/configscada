# -*- coding: utf-8 -*-

import sys
import os
import csv
from pathlib import Path

param_type_id_CONF = 1
enabled = 'NULL'
visible = 'NULL'
created_at = 'CURRENT_TIMESTAMP'
updated_at = 'CURRENT_TIMESTAMP'
zoom = 'NULL'
alert = 'false'
longitude = 0
latitude = 0
icon = "\'\'"
zoom_in = 0
zoom_out = 0
position_x = 0
position_y = 0
alarm_count = 0
icon_parameter = 'NULL'
z = 0
width_ = 0
height_ = 0

if len(sys.argv) < 2:
    print("Usage:")
    print("  arg1 = Name of project.")
    print("\n")
    print("Dirs:")
    print("  Config --> Path config destination")
    exit()




    
dirname = os.path.dirname(__file__)

resultado = ''
resultado += "INSERT INTO conf.element_hierarchies\n"
resultado +="(parent_element_type_id, parent_element_id, child_element_type_id, child_element_id, child_inheritable_state_id, enabled, visible, created_at, updated_at) VALUES \n"


def find_all(name, path):
    result = []
    for root, dirs, files in os.walk(path):
        if name in files:
            result.append(os.path.join(root, name))
    return result

def parsecsv(resultado,filename):
    
    with open(filename) as f:
        reader = csv.reader(f, delimiter=';')
        line_count = 0
        
    
        for row in reader:
            if line_count == 0:
                encabezado = ';'.join(row).split((';'))
            else:   
                parametros = ';'.join(row).split((';'))
    
                resultado +="(" + parametros[encabezado.index('Parent_type')] + ", " 
                resultado +=parametros[encabezado.index('Parent')] + ", "
                resultado += parametros[encabezado.index('Child_type')] + ", " 
                resultado +=parametros[encabezado.index('Child')] + ", "
                if len(parametros[encabezado.index('child_inheritable_state_id')]) > 0 :
                    resultado += parametros[encabezado.index('child_inheritable_state_id')] + ", "
                else :
                    resultado += "NULL" + ", "
                resultado +=enabled + ", "
                resultado +=visible + ", "
                resultado += created_at + ", "
                resultado += updated_at + "),"
                resultado +="\n"
            line_count += 1
    
    return resultado


print ("Creando fichero hierachies.sql proyecto " + sys.argv[1])

destinationSearch= os.path.join(dirname, sys.argv[1] + '/Properties/')
destination = destinationSearch + '/hierarchies.sql'

csvFiles = find_all('hierarchies.csv',destinationSearch)

for filename in csvFiles:
        resultado = parsecsv(resultado,filename)
          
last_char_index = resultado.rfind(",")
new_string = resultado[:last_char_index] + ";"


if not os.path.exists(os.path.dirname(destination)):
    try:
        os.makedirs(os.path.dirname(destination))
    except OSError as exc: 
        if exc.errno != errno.EEXIST:
            raise

with open(destination, "w") as f:
    f.write(new_string)
    f.close()
    
print("Creado fichero hierarchies.sql") 

