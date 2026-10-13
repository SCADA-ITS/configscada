# -*- coding: utf-8 -*-

import sys
import os
import csv

param_type_id_CONF = 1
enabled = 'NULL'
visible = 'NULL'
created_at = 'CURRENT_TIMESTAMP'
updated_at = 'CURRENT_TIMESTAMP'
zoom = 'NULL'
child_inheritable_state_id = "3"
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

if len(sys.argv) < 3:
    print("Usage:")
    print("  arg1 = Name of project.")
    print("  arg2 = ID of parent type.")
    print("  arg3 = ID of child type.")
    print("\n")
    print("Dirs:")
    print("  Config --> Path config destination")
    exit()

alarm_config_id = int(sys.argv[2]);
parent_type_id = int(sys.argv[3]);
child_type_id = int(sys.argv[4]);


    
dirname = os.path.dirname(__file__)




with open(os.path.join(dirname, sys.argv[1] + '/Properties/Type ' + str(parent_type_id).zfill(2) + '/hierarchies.csv')) as f:
    reader = csv.reader(f, delimiter=';')
    line_count = 0
    resultado = ''
    resultado += "INSERT INTO onf.alarm_target_elements\n"
    resultado +="(alarm_config_id,source_element_type_id,source_element_id,target_element_type_id,target_element_id,created_at,updated_at)VALUES \n"
 
    for row in reader:
        if line_count == 0:
            encabezado = ';'.join(row).split((';'))
        else:   
            parametros = ';'.join(row).split((';'))

            resultado +="(" + str(alarm_config_id) + ", " 
          
            resultado += str(child_type_id) + ", " 
            resultado +=parametros[encabezado.index('Child')] + ", "
            resultado += str(parent_type_id) + ", " 
            resultado +=parametros[encabezado.index('Parent')] + ", "
            resultado +=enabled + ", "
            resultado +=visible + ", "
            resultado += created_at + ", "
            resultado += updated_at + "),"
            resultado +="\n"
        line_count += 1

last_char_index = resultado.rfind(",")
new_string = resultado[:last_char_index] + ";"

destination = os.path.join(dirname, sys.argv[1] + '/Config/elements/Type ' + str(parent_type_id).zfill(2) + '/alarm_target.sql')

if not os.path.exists(os.path.dirname(destination)):
    try:
        os.makedirs(os.path.dirname(destination))
    except OSError as exc: 
        if exc.errno != errno.EEXIST:
            raise

with open(destination, "w") as f:
    f.write(new_string)
    f.close()
    
print("Creado fichero alarm_target") 

