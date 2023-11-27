# -*- coding: utf-8 -*-

'''
Created on 17 jun. 2020

@author: aortega
'''

import sys
import os
import csv

enabled = 'true'
visible = 'true'
created_at = 'CURRENT_TIMESTAMP'
updated_at = 'CURRENT_TIMESTAMP'


if len(sys.argv) < 3:
    print("Usage:")
    print("  arg1 = Name of project.")
    print("  arg2 = element_type_id.")
    print("\n")
    print("Dirs:")
    print("  Config --> Path config destination")
    exit()

dirname = os.path.dirname(__file__)

with open(os.path.join(dirname, sys.argv[1] + '/Properties/VMS_GRAPHICS/vms_graphics.csv')) as f:
    reader = csv.reader(f, delimiter=';')
    line_count = 0
    resultado = ''
    resultado += "INSERT INTO master.vms_graphics\n"
    resultado +="(graphic_type_id, graphic_id, alias, description, enabled, visible, created_at, updated_at) VALUES \n"
    for row in reader:
        if line_count == 0:
            encabezado = ';'.join(row).split((';'))
        else:   
            parametros = ';'.join(row).split((';'))

            resultado +="(" + parametros[encabezado.index('type')] + ","
            resultado +=parametros[encabezado.index('id')] + ","
            resultado +="'" + parametros[encabezado.index('alias')] + "',"
            resultado +="'" + parametros[encabezado.index('alias')] + "',"
            resultado +=enabled + ","
            resultado +=visible + ","
            resultado +=created_at + ","
            resultado += updated_at + "),"
            resultado +="\n"
        line_count += 1

last_char_index = resultado.rfind(",")
new_string = resultado[:last_char_index] + ";"

destination = os.path.join(dirname, sys.argv[1] + '/Config/VMS_GRAPHICS/vms_graphics.sql')

if not os.path.exists(os.path.dirname(destination)):
    try:
        os.makedirs(os.path.dirname(destination))
    except OSError as exc: 
        if exc.errno != errno.EEXIST:
            raise

with open(destination, "w") as f:
    f.write(new_string)
    f.close()
    
print("Creado fichero pictograms.sql")


with open(os.path.join(dirname, sys.argv[1] + '/Properties/VMS_GRAPHICS/Type ' + sys.argv[2] + '/vms_graphic_params_values.csv')) as f:
    reader = csv.reader(f, delimiter=';')
    line_count = 0
    resultado = ''
    resultado += "INSERT INTO conf.vms_graphic_element_type_params\n"
    resultado +="(graphic_type_id, graphic_id, graphic_protocol_type_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at) VALUES \n"
    for row in reader:
        if line_count == 0:
            encabezado = ';'.join(row).split((';'))
        else:   
            parametros = ';'.join(row).split((';'))

            resultado +="(" + parametros[encabezado.index('type')] + ","
            resultado +=parametros[encabezado.index('id')] + ","
            resultado +=parametros[encabezado.index('protocol_type')] + ","
            resultado +=sys.argv[2] + ","
            resultado +=parametros[encabezado.index('param_type')] + ","
            resultado +=parametros[encabezado.index('param')] + ","
            resultado +=enabled + ","
            resultado +=visible + ","
            resultado +=created_at + ","
            resultado += updated_at + "),"
            resultado +="\n"
        line_count += 1

last_char_index = resultado.rfind(",")
new_string = resultado[:last_char_index] + ";"

destination = os.path.join(dirname, sys.argv[1] + '/Config/VMS_GRAPHICS/Type ' + sys.argv[2] + '/vms_graphic_element_type_params.sql')

if not os.path.exists(os.path.dirname(destination)):
    try:
        os.makedirs(os.path.dirname(destination))
    except OSError as exc: 
        if exc.errno != errno.EEXIST:
            raise

with open(destination, "w") as f:
    f.write(new_string)
    f.close()
    
print("Creado fichero pictograms_element_types.sql")


with open(os.path.join(dirname, sys.argv[1] + '/Properties/VMS_GRAPHICS/Type ' + sys.argv[2] + '/vms_graphic_params_values.csv')) as f:
    reader = csv.reader(f, delimiter=';')
    line_count = 0
    resultado = ''
    resultado += "INSERT INTO conf.vms_graphic_protocol_values\n"
    resultado +="(graphic_type_id, graphic_id, graphic_protocol_type_id, graphic_protocol_type_param_id, element_type_id, param_type_id, element_type_param_id, value, enabled, visible, created_at, updated_at) VALUES \n"
    for row in reader:
        if line_count == 0:
            encabezado = ';'.join(row).split((';'))
        else:   
            parametros = ';'.join(row).split((';'))

            resultado +="(" + parametros[encabezado.index('type')] + ","
            resultado +=parametros[encabezado.index('id')] + ","
            resultado +=parametros[encabezado.index('protocol_type')] + ","
            resultado +=parametros[encabezado.index('protocol_param_id')] + ","
            resultado +=sys.argv[2] + ","
            resultado +=parametros[encabezado.index('param_type')] + ","
            resultado +=parametros[encabezado.index('param')] + ","
            resultado +="'" + parametros[encabezado.index('value')] + "',"
            resultado +=enabled + ","
            resultado +=visible + ","
            resultado +=created_at + ","
            resultado += updated_at + "),"
            resultado +="\n"
        line_count += 1

last_char_index = resultado.rfind(",")
new_string = resultado[:last_char_index] + ";"

destination = os.path.join(dirname, sys.argv[1] + '/Config/VMS_GRAPHICS/Type ' + sys.argv[2] + '/vms_graphic_protocol_values.sql')

if not os.path.exists(os.path.dirname(destination)):
    try:
        os.makedirs(os.path.dirname(destination))
    except OSError as exc: 
        if exc.errno != errno.EEXIST:
            raise

with open(destination, "w") as f:
    f.write(new_string)
    f.close()
    
print("Creado fichero pictograms_protocol.sql")