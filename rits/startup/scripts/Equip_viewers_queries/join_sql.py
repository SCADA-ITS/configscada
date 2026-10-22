import os
import openpyxl as xl
import psycopg2
import sys
import shutil


HOST = sys.argv[1]
PORT = sys.argv[2]
DB = "rits"
USER = "rits"
PASS = "rits"
equip_viewers_consult = 'INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties) VALUES\n' 
equip_viewers_tabs_consult = 'INSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, \"label\", icon, \"position\", visible) VALUES\n'
equip_viewers_grp_measures_consult = 'INSERT INTO ui.equip_viewer_grp_measures(id, equip_viewer_id, name, description, "label", icon, position, visible) VALUES\n' 
equip_viewers_measures_consult = "INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, position, visible) VALUES\n"
equip_viewers_configs_consult = "INSERT INTO ui.equip_viewer_configs (id, equip_viewer_id, vo_entities_id, \"position\", visible) VALUES\n"

cwd = os.path.dirname(os.path.abspath(__file__))
path_element_type = 'QUERIES/07.equip_viewers/'
path = os.path.join(cwd, path_element_type)

#path = "/home/revenga/repositorio/_rits/startup/scripts/Equip_viewers_queries/QUERIES/07.equip_viewers/"

def element_info(id):
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()
        consult = "select alias from master.element_types where element_type_id = " + str(id) +";"

        cur.execute(consult)

        info = cur.fetchone()

        cur.close()
        connection.close()
        return info[0]

    except AttributeError:
        print(ValueError)    


def write_archive(name,tipo,index, contenido,equip_viewers_index,equip_viewers_tabs_index):
    element_type_aux = name.split('.')
    if tipo == False:
        element_type = (contenido[1].split(':')[2]).split("'")[0]
        archivo_destino = element_info(element_type)
        file_name = 'QUERIES/07.equip_viewers/' + element_type + '.' + archivo_destino + '.sql'
        
    else:
        if '-' in element_type_aux[0]:
            element_type = element_type_aux[0].split('-')[1]
        else:
            element_type = element_type_aux[0]
        archivo_destino = element_info(element_type)
        file_name = 'QUERIES/07.equip_viewers/' + element_type + '.' + archivo_destino + '_aux.sql'
    with  open(file_name, "a") as archivo_final:
        if index == True:
            archivo_final.write(equip_viewers_consult)
            
        for lineas in contenido[equip_viewers_index + 1:equip_viewers_tabs_index - 1]:
            archivo_final.write(lineas.replace(';',','))
    return file_name 


def write_archive_tabs(name, tipo, index, contenido,equip_viewers_tabs_index,equip_viewers_grp_measure_index):
    element_type_aux = name.split('.')
    if tipo == False:
        element_type = (contenido[1].split(':')[2]).split("'")[0]
        archivo_destino = element_info(element_type)
        file_name = 'QUERIES/07.equip_viewers/' + element_type + '.' + archivo_destino + '.sql'
        
    else:
        if '-' in element_type_aux[0]:
            element_type = element_type_aux[0].split('-')[1]
        else:
            element_type = element_type_aux[0]
        archivo_destino = element_info(element_type)
        file_name = 'QUERIES/07.equip_viewers/' + element_type + '.' + archivo_destino + '_aux.sql'
    with  open(file_name, "a") as archivo_final:
        if index:
            archivo_final.write('\n\n')
            archivo_final.write(equip_viewers_tabs_consult)
            
        for lineas in contenido [equip_viewers_tabs_index + 1:equip_viewers_grp_measure_index - 2]:
            archivo_final.write(lineas.replace(';',','))
    return file_name

def write_archive_grp_measure(name, tipo,index, contenido,equip_viewers_grp_measure_index,equip_viewers_measure_index):
    element_type_aux = name.split('.')
    if tipo == False:
        element_type = (contenido[1].split(':')[2]).split("'")[0]
        archivo_destino = element_info(element_type)
        file_name = 'QUERIES/07.equip_viewers/' + element_type + '.' + archivo_destino + '.sql'
        
    else:
        if '-' in element_type_aux[0]:
            element_type = element_type_aux[0].split('-')[1]
        else:
            element_type = element_type_aux[0]
        archivo_destino = element_info(element_type)
        file_name = 'QUERIES/07.equip_viewers/' + element_type + '.' + archivo_destino + '_aux.sql'
    with  open(file_name, "a") as archivo_final:
        if index:
            archivo_final.write('\n\n')
            archivo_final.write('--equip_viewer_grp_measure_id = Subtype(2d) + Type(4d) + contador\n--if it is going to be put by element use this format for the id: ELEMENT + Subtype(2d) + Type(4d) + contador\n' + equip_viewers_grp_measures_consult)

        for lineas in contenido [equip_viewers_grp_measure_index + 1:equip_viewers_measure_index - 1]:
            archivo_final.write(lineas.replace(';',','))
    return file_name     


def write_archive_measure(name, tipo,index, contenido,equip_viewers_measure_index,equip_viewers_configs_index):
    element_type_aux = name.split('.')
    if tipo == False:
        element_type = (contenido[1].split(':')[2]).split("'")[0]
        archivo_destino = element_info(element_type)
        file_name = 'QUERIES/07.equip_viewers/' + element_type + '.' + archivo_destino + '.sql'
        
    else:
        if '-' in element_type_aux[0]:
            element_type = element_type_aux[0].split('-')[1]
        else:
            element_type = element_type_aux[0]
        archivo_destino = element_info(element_type)
        file_name = 'QUERIES/07.equip_viewers/' + element_type + '.' + archivo_destino + '_aux.sql'
    archivo_final = open(file_name, 'r')
    archivo_final_aux = archivo_final.readlines()
    archivo_final.close()
    aux2 = []
    for aux in archivo_final_aux:
        aux2.append(aux.replace(';',','))

    with  open(file_name, "a") as archivo_final:
        if index:
            archivo_final.write('\n\n')
            archivo_final.write(equip_viewers_measures_consult)

        for lineas in contenido [equip_viewers_measure_index + 1:equip_viewers_configs_index - 1]:

            linea = lineas.replace(';',',')

            if linea not in aux2:
                archivo_final.write(linea)
    
    return file_name     

def write_archive_configs(name, tipo,index, contenido,equip_viewers_configs_index):
    element_type_aux = name.split('.')
    if tipo == False:
        element_type = (contenido[1].split(':')[2]).split("'")[0]
        archivo_destino = element_info(element_type)
        file_name = 'QUERIES/07.equip_viewers/' + element_type + '.' + archivo_destino + '.sql'
        
    else:
        if '-' in element_type_aux[0]:
            element_type = element_type_aux[0].split('-')[1]
        else:
            element_type = element_type_aux[0]
        archivo_destino = element_info(element_type)
        file_name = 'QUERIES/07.equip_viewers/' + element_type + '.' + archivo_destino + '_aux.sql'
    with  open(file_name, "a") as archivo_final:
        if index:
            archivo_final.write('\n\n')
            archivo_final.write(equip_viewers_configs_consult)
            
        for lineas in contenido [equip_viewers_configs_index + 1:]:
            archivo_final.write(lineas.replace(';',','))
        archivo_final.write('\n')
    return file_name     

def array_index(array_global):
    for i in array_global:
        index = True 
        tipo = False
        for type_name in i:
            if '-' not in type_name:
                tipo = True
        for j in i:
            archivo = open (path + j, 'r')
            contenido = archivo.readlines()
            equip_viewers_index = contenido.index(equip_viewers_consult)
            equip_viewers_tabs_index = contenido.index(equip_viewers_tabs_consult)
            file_name = write_archive(j, tipo,index, contenido,equip_viewers_index,equip_viewers_tabs_index)
            index =False            
        f = open(file_name , 'rb+')
        f.seek(-2, os.SEEK_END)
        f.truncate()  
        f.close()
        f = open(file_name , 'a')
        f.write(';')
        f.close()
        index = True 
        for j in i:
            archivo = open (path + j, 'r')
            contenido = archivo.readlines()
            equip_viewers_tabs_index = contenido.index(equip_viewers_tabs_consult)
            equip_viewers_grp_measure_index = contenido.index(equip_viewers_grp_measures_consult)
            file_name = write_archive_tabs(j, tipo, index, contenido,equip_viewers_tabs_index,equip_viewers_grp_measure_index - 1)
            index =False            
        f = open(file_name , 'rb+')
        f.seek(-2, os.SEEK_END)
        f.truncate()  
        f.close()
        f = open(file_name , 'a')
        f.write(';')
        f.close()
        index = True
        for j in i:
            archivo = open (path + j, 'r')
            contenido = archivo.readlines()
            if equip_viewers_measures_consult in contenido:
                equip_viewers_measure_index = contenido.index(equip_viewers_measures_consult)
            else:
                equip_viewers_measure_index = contenido.index(equip_viewers_configs_consult)
            equip_viewers_grp_measure_index = contenido.index(equip_viewers_grp_measures_consult)
            file_name = write_archive_grp_measure(j,tipo, index, contenido,equip_viewers_grp_measure_index,equip_viewers_measure_index)
            index =False            
        f = open(file_name , 'rb+')
        f.seek(-2, os.SEEK_END)
        f.truncate()  
        f.close()
        f = open(file_name , 'a')
        f.write(';')
        f.close()
        index = True
        for j in i:
            archivo = open (path + j, 'r')
            contenido = archivo.readlines()
            if equip_viewers_measures_consult in contenido:
                equip_viewers_measure_index = contenido.index(equip_viewers_measures_consult)
                if equip_viewers_configs_consult in contenido:
                    equip_viewers_configs_index = contenido.index(equip_viewers_configs_consult)
                else:
                    equip_viewers_configs_index = len(contenido) - 1
                file_name = write_archive_measure(j,tipo, index, contenido,equip_viewers_measure_index,equip_viewers_configs_index)
                index =False            
        f = open(file_name , 'rb+')
        f.seek(-2, os.SEEK_END)
        f.truncate()  
        f.close()
        f = open(file_name , 'a')
        f.write(';')
        f.close()
        index = True
        for j in i:
            archivo = open (path + j, 'r')
            contenido = archivo.readlines()
            if equip_viewers_configs_consult in contenido:
                equip_viewers_configs_index = contenido.index(equip_viewers_configs_consult)
                file_name = write_archive_configs(j,tipo, index, contenido,equip_viewers_configs_index)
                index =False            
        f = open(file_name , 'rb+')
        f.seek(-2, os.SEEK_END)
        f.truncate()  
        f.close()
        f = open(file_name , 'a')
        f.write(';')
        f.close()

def not_in(array_global, nombre):
    for i in array_global:
        for j in i:
            if j == nombre:
                return False
    return True

def clean():
    global path
    nombre_archivos = os.listdir(path)

    arrayOfarray_clean = []
    for i in range(0,len(nombre_archivos)):
        coincidence = 0
        array_clean = []
        archivo = nombre_archivos[i]
        
        if '-' in archivo or 'aux' in archivo:
            if '-' in archivo:
                type_archivo = archivo.split('.')[0].split('-')[1]
            else:
                type_archivo = archivo.split('.')[0]
            array_clean.append(archivo)
            for j in range (0, len(nombre_archivos)):
                aux = nombre_archivos[j]
                if '-' in aux:
                    type_aux = aux.split('.')[0].split('-')[1]
                    if type_archivo == type_aux:
                        coincidence = coincidence + 1
            array_clean.append(coincidence)
            arrayOfarray_clean.append(array_clean)
    for archivo in arrayOfarray_clean:
        archivo_aux = archivo[1]
        if archivo_aux != 1 and 'aux' not in archivo[0]:
            os.remove(os.path.join(path, archivo[0]))
        else:
            if 'aux' in archivo[0]:
                element_type = archivo[0].split('.')[0]
            else:
                element_type = (archivo[0].split('.')[0].split('-')[1])
            archivo_destino = element_info(element_type)
            rename = element_type + '.' + archivo_destino + '.sql'
            os.rename(os.path.join(path,archivo[0]), os.path.join(path, rename))


def join_sql():
    global path
    nombre_archivos = os.listdir(path)
    array_global_nombres = []
    for i in range(len(nombre_archivos)):
        element_type = nombre_archivos[i].split(".")[0]
        if '-' in element_type:
                element_type =  element_type.split('-')[1]        
        array_nombres = []
        array_nombres.append(nombre_archivos[i])
        for j in range(i+1, len(nombre_archivos)):
            
            element_type_aux = nombre_archivos[j].split(".")[0]
            if '-' in element_type_aux:
                element_type_aux =  element_type_aux.split('-')[1]
            if element_type == element_type_aux:
                if not_in(array_global_nombres, nombre_archivos[j]):
                    array_nombres.append(nombre_archivos[j])
        if len(array_nombres) != 1:
            array_nombres.sort(key = lambda x: x.split('-')[0])
            array_global_nombres.append(array_nombres)
    array_index(array_global_nombres)

    return 1
