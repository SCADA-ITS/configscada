#LIBRARIES
import os
import sys
import shutil
import re

HOST = sys.argv[2]
PORT = sys.argv[3]
DB = "rits"
USER = "rits"
PASS = "rits"


path_ev = '../../../ritsfront/resources/db/dml/'+sys.argv[1]+'/07.equip_viewers/'
element_insert = 'INSERT INTO ui.vo_entities(id, vo_entity_type_id, enabled, visible) VALUES'
path_elementSubType = 'Config/03.vo_extensions/element_subtype'
path_elementType = 'Config/03.vo_extensions/element_type'
path_params = 'Config/03.vo_extensions/element_type_param'
entities_sql = '01.entities.sql'
entities_values_sql = '02.entities_values.sql'
entities_values_insert = 'INSERT INTO ui.vo_entity_values(vo_entity_id, vo_entity_field_id, value) VALUES\n'

def obtener_ultimo_numero(elemento):
    if isinstance(elemento, str):
        partes = elemento.split(":")
        if len(partes) >= 2:
            try:
                ultimo_numero = int(partes[-1].strip("' "))
                return ultimo_numero
            except ValueError:
                pass
    return 0

def vo_entities(elements):

    entities = os.path.join(path_elementSubType, entities_sql)
    elements_sorted = sorted(elements, key=lambda x: obtener_ultimo_numero(x))    
    if os.path.exists(entities):
        os.remove(entities)
    for i, element in enumerate(elements_sorted):
        if "ElementSubtype" in element:
            elementtype = element.split(":")[2]
            if not os.path.exists(path_elementSubType):
                os.makedirs(path_elementSubType)

            with open(entities, 'a+') as archivo:
                archivo.seek(0)
                first_line = archivo.read()
                if element_insert not in first_line:
                    archivo.write(element_insert)
                    if i < len(elements) - 1:
                        archivo.write('(\'' + element + '\'' + ', \'ElementSubtypeUI\', ' + 'true, true),\n')
                    else:
                        archivo.write('(\'' + element + '\'' + ', \'ElementSubtypeUI\', ' + 'true, true);\n')
                else:
                    if i < len(elements) - 1:
                        archivo.write('(\'' + element + '\'' + ', \'ElementSubtypeUI\', ' + 'true, true),\n')
                    else:
                        archivo.write('(\'' + element + '\'' + ', \'ElementSubtypeUI\', ' + 'true, true);\n')

                    element_type_folder = os.path.join(os.path.dirname(path_elementSubType), "element_type")
                    if not os.path.exists(element_type_folder):
                        os.makedirs(element_type_folder)
                    entities_file = os.path.join(element_type_folder, "01.entities.sql")
                    with open(entities_file, 'a+') as element_file:
                        element_file.seek(0)
                        first_line2 = element_file.read()
                        if element_insert + '\n' not in first_line2:
                            element_file.write(element_insert + '\n')
                        if i < len(elements) - 1:
                            element_file.write('(\'ElementType:' + elementtype + '\'' + ', \'ElementSubtypeUI\', ' + 'true, true),\n')
                        else:
                            element_file.write('(\'ElementType:' + elementtype + '\'' + ', \'ElementSubtypeUI\', ' + 'true, true);\n')


        elif "ElementType" in element:
                if not os.path.exists(path_elementType):
                    os.makedirs(path_elementType)
                entities = os.path.join(path_elementType, entities_sql)
                with open(entities, 'a+') as archivo:
                    archivo.seek(0)
                    first_line = archivo.read()
                    if element_insert not in first_line:
                        archivo.write(element_insert)
                        if i < len(elements) - 1:
                            archivo.write('(\'' + element + '\'' + ', \'ElementTypeUI\', ' + 'true, true),\n')
                        else:
                            archivo.write('(\'' + element + '\'' + ', \'ElementTypeUI\', ' + 'true, true);\n')
                    else:
                        if i < len(elements) - 1:
                            archivo.write('(\'' + element + '\'' + ', \'ElementTypeUI\', ' + 'true, true),\n')
                        else:
                            archivo.write('(\'' + element + '\'' + ', \'ElementTypeUI\', ' + 'true, true);\n')

def obtener_numeros(elemento):
    if elemento:
        primer_numero = 0
        ultimo_numero = 0
        if isinstance(elemento, str):
            partes = elemento.split(":")
            if len(partes) >= 4:
                try:
                    primer_numero = int(partes[1].strip("' "))
                    ultimo_numero = int(partes[-1].strip("' "))
                except ValueError:
                    pass
        return (primer_numero, ultimo_numero)
    else:
        return (0, 0)

def vo_entities_param(params):
    entities = os.path.join(path_params, entities_sql)
    if not os.path.exists(entities):
        os.makedirs(path_params)
    else:
        os.remove(entities)

    params_ordenados = sorted(params, key=obtener_numeros)
    
    with open(entities, 'w') as archivo:
        archivo.write(element_insert)
        for i, param in enumerate(params_ordenados):
            if i < len(params_ordenados) - 1:
                archivo.write("\n('{}, 'ElementTypeParamUI', true, true),".format(param))
            else:
                archivo.write("\n('{}, 'ElementTypeParamUI', true, true);".format(param))

def vo_entities_values(elements):
    ruta_archivo = os.path.join(path_elementType, entities_values_sql)
    # Comprobar si el archivo existe
    if os.path.exists(ruta_archivo):
        # Borrar el archivo existente
        os.remove(ruta_archivo)
    with open(ruta_archivo, 'w') as archivo:
        archivo.write(entities_values_insert)
        for index, element in enumerate(elements):
           if element.split(":")[0] == 'ElementType':
               element_type = element.split(":")[1]
               
           else:
               element_type = element.split(":")[2]
           archivo.write('(\'ElementType:' + element_type + '\', 1, \'' + element_type + '\'),\n')
           archivo.write('(\'ElementType:' + element_type + '\', 2, \' element_type_' + str(element_type).zfill(2) + '.svg\'),\n')
           archivo.write('(\'ElementType:' + element_type + '\', 14, \' grid/element_type_' + str(element_type).zfill(2) + '.png\')')
           
           # Verificar si es la última iteración
           if index == len(elements) - 1:
               archivo.write(';')  # Escribir punto y coma en lugar de coma
           else:
               archivo.write(',\n\n')



elements = set()
params = set()
archivos = os.listdir(path_ev)
respuesta = input("¿Has configurado previamente Equip_viewers? (si/no)")

respuesta = respuesta.lower()

# Validar la respuesta del usuario
if respuesta == "si":
    print("Perfecto, ¡comencemos!")
elif respuesta == "no":
    print("Sin esa configuración previa, este script no va a funcionar. Por favor configuralo primero.")
    sys.exit()
else:
    print("Respuesta no válida. Por favor, responde con 'sí' o 'no'.")

path_config = 'Config/'
if os.path.exists(path_config):
    shutil.rmtree(path_config)
else:
    cwd = os.path.dirname(os.path.abspath(__file__))
    config_path = os.path.join(cwd, path_config)
    if not os.path.exists(config_path):
        os.makedirs(config_path)
    path_vo = 'Config/03.vo_extensions'
    if not os.path.exists(path_vo):
        os.makedirs(path_vo)


for archivo in archivos:
    ruta_completa = os.path.join(path_ev, archivo)
    if os.path.isfile(ruta_completa):
        with open(ruta_completa, 'r') as archivo_actual:
             for linea in archivo_actual:
                if "INSERT INTO" not in linea:
                    line = linea.rstrip()  # rstrip() para eliminar el salto de línea adicional
                    first_row = line.split(",")[0]
                    if "ElementSubtype" in first_row:
                        element = line.split(",")[0].split("'")[1]
                        elements.add(element)
                    elif "ElementType" in first_row:
                        element = line.split(",")[0].split("'")[1]
                        elements.add(element)
                    elif "ElementTypeParam" in line:
                        patron = r'ElementTypeParam:[^,\n]*'
                        param = resultados = re.findall(patron, line)
                        if param:
                            param = tuple(param)[0]
                            params.add(param)

#generate_excel(elements)                        
vo_entities(elements)
vo_entities_values(elements)
vo_entities_param(params)
