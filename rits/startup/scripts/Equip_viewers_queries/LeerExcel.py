import sys
import os
import shutil
import openpyxl as xl
import psycopg2
import re
from translation import translate_param_label
#INFO DATABASE

# Variables para la conexion a BBDD
HOST = sys.argv[1]
PORT = sys.argv[2]
DB = "rits"
USER = "rits"
PASS = "rits"


'''#DB CONNECTION
HOST = "192.168.88.231"
PORT = "5430"
DB = "rits"
USER = "rits"
PASS = "rits"'''



def get_name_element(element):
    reg = '(\d*)'
    element_aux = re.findall(reg,str(element))
    element_type_aux= get_element_info(element_aux[1])
    return element_type_aux

#Gets alias and label_alias from master element_types by element_type_id
def get_element_info(id):
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()
        consult = "SELECT alias, label_alias FROM master.element_types WHERE element_type_id = " + str(id) +";"

        cur.execute(consult)
        info = cur.fetchall()

        cur.close()
        connection.close()

        return info
    except AttributeError:
        print(ValueError)

def get_subtype_element_info(id):
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()
        consult = "SELECT alias, label_alias FROM master.element_subtypes WHERE element_type_id = " + str(id[0]) + "and element_subtype_id = " + str(id[2]) + ";"

        cur.execute(consult)
        info = cur.fetchall()
        cur.close()
        connection.close()

        return info
    except AttributeError:
        print(ValueError)


#Gets param_type_id and element_type_param_id from master element_type_params by element_type_id
def get_param_type_id(element_type_id, element_type_param_id, label_alias):
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()
        consult = "SELECT param_type_id FROM master.element_type_params WHERE element_type_id =" + str(element_type_id)+ " and element_type_param_id = "+ str(element_type_param_id)+ ' and label_alias = \'' +label_alias+"\';"

        cur.execute(consult)
        info = cur.fetchall()

        cur.close()
        connection.close()

        return info
    except AttributeError:
        print(ValueError)

#Gets element_type_param_id from master element_type_params by element_type_id and label_alias
def get_element_type_param_id(element_type_id, label):
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()
        consult = "SELECT element_type_param_id FROM master.element_type_params WHERE element_type_id =" + str(element_type_id)+ " and label_alias = \'" + label + "\';"

        cur.execute(consult)
        info = cur.fetchall()

        cur.close()
        connection.close()
        return info
    except AttributeError:
        print(ValueError)

#Gets all element_type_id from conf elements
def get_elements():
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()
        consult = "SELECT DISTINCT element_type_id,element_subtype_id FROM conf.elements WHERE element_type_id != 1000"

        cur.execute(consult)
        info = cur.fetchall()

        cur.close()
        connection.close()

        return info
    except AttributeError:
        print(ValueError)

#Gets all element_type_id from master element_type
def get_all_elements():
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()
        consult = "SELECT DISTINCT element_type_id FROM conf.elements WHERE element_type_id != 1000"

        cur.execute(consult)
        info = cur.fetchall()

        cur.close()
        connection.close()

        return info
    except AttributeError:
        print(ValueError)


#Checks if de vo_entitie exists on ui vo_entitie by the id
def vo_entitie_element_exists(id):
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()
       
        consult = "SELECT id FROM ui.vo_entities WHERE id = " + id + ";"

        cur.execute(consult)
        info = cur.fetchall()

        cur.close()
        connection.close()

        if len(info) != 0:
            return True
        else: 
            return False
    except AttributeError:
        print(ValueError)



#GENERATE SQL QUERIES
#Generates the inserts for the ui vo_entities for the ones that don't exists
def vo_entities():
    consult = 'INSERT INTO ui.vo_entities(id, vo_entity_type_id, enabled, visible) VALUES'
    
    file_name = 'QUERIES/vo_entities.sql'
    f = open(file_name , 'a')
    f.write(consult)
    f.close()
    array_elements = get_all_elements()
    for element in array_elements:
        element_type_id = element[0]

        element_type_id = element[0]
        element_id = '\n(\'ElementType:' + str(element_type_id) + '\','
        vo_entitiy_type_id = '\'ElementTypeUI\' ,'
        true_1 = True
        enable  =  str(true_1) + ','
        visible =  str(true_1) + '),'
        
        consult = element_id + vo_entitiy_type_id + enable + visible 
        
        f = open(file_name , 'a')
        f.write(consult)
        f.close()

    # last read byte is our truncation point, move back to it.
    f = open(file_name , 'rb+')
    f.seek(-1, os.SEEK_END)
    f.truncate()  
    f.close()
    f = open(file_name , 'a')
    f.write(';')
    f.close()
    return 0

#Generates the inserts for the ui vo_entities for the ones that don't exists
def vo_entities_if_not_exists(array_entities):
    file_name = 'QUERIES/vo_entities.sql'
    f = open(file_name , 'rb+')
    f.seek(-1, os.SEEK_END)
    f.truncate()  
    f.close()

    for element in array_entities:
        
        if (vo_entitie_element_exists(element) == False):
            element_id = ',\n(' + element + ', '
            vo_entitiy_type_id = '\'ElementTypeParamUI\', '
            true_1 = True
            enable  =  str(true_1) + ', '
            visible =  str(true_1) + ')'
            
            consult = element_id + vo_entitiy_type_id + enable + visible 
            
            f = open(file_name , 'a')
            f.write(consult)
            f.close()

    # last read byte is our truncation point, move back to it.

    f = open(file_name , 'a')
    f.write(';')
    f.close()
    return 0

#Generates the inserts for the ui equip_viewers
def equip_viewers_queries(array_info,array_measures):
    #Default 4 columns
    columns = 4
    if array_info[2] != None:
        element_info =  get_subtype_element_info(array_info)
        element_type_aux = element_info[0]
        nombre_archivo = str("{:02d}".format(int(array_info[2]))) + '-' + str(array_info[0]) + '.' + str(element_type_aux[0])
    else:
        element_info =  get_element_info(array_info[0])
        element_type_aux = element_info[0]
        nombre_archivo = str(array_info[0]) + '.' + str(element_type_aux[0])

    #consult = 'INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties) VALUES' 
    file_name = 'QUERIES/07.equip_viewers/' + nombre_archivo + '.sql'
    consult = 'INSERT INTO ui.equip_viewers (id, name, description, "label", icon, show_info_tab, show_childs, show_actions, initial_tab, measure_width, measure_max_rows, measure_only, graphic_icon_id, info_tab_mode, no_show_properties) VALUES' 
    f = open(file_name , 'a')
    f.write(consult)
    f.close()
    if array_info[2] == None:
  
        element_type_id = array_info[0]
        element_id = '\n(\'ElementType:' + str(element_type_id) + '\','
        name = '\'' + element_type_aux[0] + '\','
        label = '\'' + element_type_aux[1] + '\','
        true_1 = True
        icon = 'null,'
        show_info_tab  =  str(true_1) + ','
        show_actions  = str(true_1) + ','
        initial_tab = 'null,'
        measure = 900 / columns
        measure_width = str(measure) + ','
        measure_max_rows = str(3) + ','
        measure_only = 'null,'
        graphic_icon_id = str(element_type_id) + ','
        #graphic_icon_id = str("{:04d}".format(element_type_id)) +'00,'
        false= False
        show_childs = str(false) + ','
        info_tab_mode = '2,'

        if array_measures == []:
            no_show_properties = 'True);'
        for measure in array_measures:
            if ((type(measure[3]) is list)):
                for posible_translation in measure[3]:
                    element_type_param_id_aux = get_element_type_param_id(measure[0], posible_translation[0])
                    if len(element_type_param_id_aux) != 0:
                        element_type_param_id = element_type_param_id_aux[0][0]
                        type_param_aux = get_param_type_id(measure[0], element_type_param_id, posible_translation[0])
                        if len(type_param_aux) != 0:
                            type_param = type_param_aux[0][0]
                        break
            else:
                element_type_param_id = get_element_type_param_id(measure[0], measure[3])[0][0]
                type_param = get_param_type_id(measure[0], element_type_param_id, measure[3])[0][0]

            if type_param == 1:
                type_param_true = True
            else:
                type_param_true = False

            if type_param_true == True:
                no_show_properties = 'null);'
            else:
                no_show_properties = 'True);'

        consult = element_id + name + name + label + icon + show_info_tab + show_childs + show_actions + initial_tab + measure_width + measure_max_rows + measure_only + graphic_icon_id + info_tab_mode + no_show_properties

        f = open(file_name , 'a')
        f.write(consult)
        f.close()
    else:
        element_type_id = array_info[0]
        element_id = '\n(\'ElementSubtype:' +str(array_info[2]) + ':' + str(element_type_id) + '\','
        name = '\'' + element_type_aux[0] + '\','
        label = '\'' + element_type_aux[1] + '\','
        true_1 = True
        icon = 'null,'
        show_info_tab  =  str(true_1) + ','
        show_actions  = str(true_1) + ','
        initial_tab = 'null,'
        measure = 900 / columns
        measure_width = str(measure) + ','
        measure_max_rows = str(3) + ','
        measure_only = 'null,'
        graphic_icon_id = str(element_type_id) + ','
        #graphic_icon_id = str("{:04d}".format(element_type_id)) +'00,'
        false= False
        show_childs = str(false) + ','
        info_tab_mode = '2,'
        type_param_true = False
        if array_measures == []:
            no_show_properties = 'True);'
        for measure in array_measures:
            if ((type(measure[3]) is list)):
                for posible_translation in measure[3]:
                    element_type_param_id_aux = get_element_type_param_id(measure[0], posible_translation[0])
                    if len(element_type_param_id_aux) != 0:
                        element_type_param_id = element_type_param_id_aux[0][0]
                        type_param_aux = get_param_type_id(measure[0], element_type_param_id, posible_translation[0])
                        if len(type_param_aux) != 0:
                            type_param = type_param_aux[0][0]
                        break
            else:
                element_type_param_id = get_element_type_param_id(measure[0], measure[3])[0][0]
                type_param = get_param_type_id(measure[0], element_type_param_id, measure[3])[0][0]
            if type_param == 1:
                type_param_true = True

            if type_param_true == True:
                no_show_properties = 'null);'
            else:
                no_show_properties = 'True);'


        consult = element_id + name + name + label + icon + show_info_tab + show_childs + show_actions + initial_tab + measure_width + measure_max_rows + measure_only + graphic_icon_id + info_tab_mode + no_show_properties

        f = open(file_name , 'a')
        f.write(consult)
        f.close()

    # last read byte is our truncation point, move back to it.
    f = open(file_name , 'rb+')
    f.seek(-1, os.SEEK_END)
    f.truncate()  
    f.close()
    f = open(file_name , 'a')
    f.write(';')
    f.close()
    return 0

#Generate the inserts for the ui equip_viewer_grp_measures
def equip_viewer_grp_measures_queries(array_info, arrays_measures):
        if array_info[2] != None:
            element_info =  get_subtype_element_info(array_info)
            element_type_aux = element_info[0]
            nombre_archivo = str("{:02d}".format(int(array_info[2]))) + '-' + str(array_info[0]) + '.' + str(element_type_aux[0])
        else:
            element_info =  get_element_info(array_info[0])
            element_type_aux = element_info[0]
            nombre_archivo = str(array_info[0]) + '.' + str(element_type_aux[0])
        

        file_name = 'QUERIES/07.equip_viewers/' + nombre_archivo + '.sql'
        consult = '\n\n--equip_viewer_grp_measure_id = Subtype(2d) + Type(4d) + contador\n--if it is going to be put by element use this format for the id: ELEMENT + Subtype(2d) + Type(4d) + contador\nINSERT INTO ui.equip_viewer_grp_measures(id, equip_viewer_id, name, description, "label", icon, position, visible) VALUES' 
        f = open(file_name , 'a')
        f.write(consult)
        f.close()

        if array_info[2] == None:
            array_group_name = []
            group_number = 0
            arrays_group_id = []
            for measure in arrays_measures:
                
                if ((type(measure[3]) is list)):
                    for posible_translation in measure[3]:
                        element_type_param_id_aux = get_element_type_param_id(measure[0], posible_translation[0])
                        if len(element_type_param_id_aux) != 0:
                            element_type_param_id = element_type_param_id_aux[0][0]
                            type_param_aux = get_param_type_id(measure[0], element_type_param_id, posible_translation[0])
                            if len(type_param_aux) != 0:
                                type_param = type_param_aux[0][0]
                            break
                else:
                   element_type_param_id = get_element_type_param_id(measure[0], measure[3])[0][0]
                   type_param = get_param_type_id(measure[0], element_type_param_id, measure[3])[0][0]
                if type_param == 2:
                    group_name = measure[4]
                    if group_name not in array_group_name:
                        array_group_name.append(group_name)
            if not array_group_name:
                element_type_id = array_info[0]
                element_id = ', \'ElementType:' + str(element_type_id) + '\','
                name = ' \'Measures\','
                position = ' ' + str(1) + ','
                visible = ' ' + str(True) +');'
                icon = ' null,'
                label = ' \'LBL_GRP_MEASURES\','
                consult = '\n(00' + str("{:04d}".format(int(element_type_id))) + '01' + element_id  + name + name + label + icon + position + visible

                f = open(file_name , 'a')
                f.write(consult)
                f.close()
            else:
                for name in array_group_name:
                    array_grp_id = []
                    group_number = group_number + 1
                    if name == None:
                        element_type_id = array_info[0]
                        element_id = ', \'ElementType:' + str(element_type_id) + '\','
                        name_aux = ' \'Measures\','
                        name_label = name_aux.upper()
                        position = ' ' + str(1) + ','
                        visible = ' ' + str(True) +'),'
                        icon = ' null,'
                        label = ' \'LBL_GRP_MEASURES\','
                        id = '00' + str("{:04d}".format(int(element_type_id))) + str("{:02d}".format(int(group_number)))
                        consult = '\n(00' + str("{:04d}".format(int(element_type_id))) + str("{:02d}".format(int(group_number))) + element_id + name_aux + name_aux + label + icon + position + visible
                        consult_aux = '(00' + str("{:04d}".format(int(element_type_id))) + str("{:02d}".format(int(group_number) - 1)) + element_id + name_aux + name_aux + label + icon + position + visible + '\n'
                        f = open(file_name , 'r')
                        lineas = f.readlines()
                        if consult_aux not in lineas:
                            array_grp_id.append(id)
                            array_grp_id.append(name_aux)
                            arrays_group_id.append(array_grp_id)
                            f = open(file_name , 'a')
                            f.write(consult)
                            f.close()
                    else:
                        element_type_id = array_info[0]
                        element_id = ', \'ElementType:' + str(element_type_id) + '\','
                        name_aux = '\'' + str(name) + '\','
                        name_label = name.upper()
                        position = ' ' + str(1) + ','
                        visible = ' ' + str(True) +'),'
                        icon = ' null,'
                        label = ' \'LBL_GRP_' + name_label + '\','
                        id = '00' + str("{:04d}".format(int(element_type_id)))+ str("{:02d}".format(int(group_number)))
                        consult = '\n(00' + str("{:04d}".format(int(element_type_id)))+ str("{:02d}".format(int(group_number))) + element_id + name_aux + name_aux + label + icon + position + visible 
                        consult_aux = '(00' + str("{:04d}".format(int(element_type_id)))+ str("{:02d}".format(int(group_number) - 1)) + element_id + name_aux + name_aux + label + icon + position + visible + '\n'
                        f = open(file_name , 'r')
                        lineas = f.readlines()
                        if consult_aux not in lineas:
                            array_grp_id.append(id)
                            array_grp_id.append(name_aux)
                            arrays_group_id.append(array_grp_id)
                            f = open(file_name , 'a')
                            f.write(consult)
                            f.close()
        else:
            array_group_name = []
            group_number = 0
            arrays_group_id = []
            for measure in arrays_measures:
                
                if ((type(measure[3]) is list)):
                    for posible_translation in measure[3]:
                        element_type_param_id_aux = get_element_type_param_id(measure[0], posible_translation[0])
                        if len(element_type_param_id_aux) != 0:
                            element_type_param_id = element_type_param_id_aux[0][0]
                            type_param_aux = get_param_type_id(measure[0], element_type_param_id, posible_translation[0])
                            if len(type_param_aux) != 0:
                                type_param = type_param_aux[0][0]
                            break
                else:
                   element_type_param_id = get_element_type_param_id(measure[0], measure[3])[0][0]
                   type_param = get_param_type_id(measure[0], element_type_param_id, measure[3])[0][0]
                if type_param == 2:
                    group_name = measure[4]
                    if group_name not in array_group_name:
                        array_group_name.append(group_name)
            if not array_group_name:
                element_type_id = array_info[0]
                element_id = ', \'ElementSubtype:' + str(array_info[2]) + ':' + str(element_type_id) + '\','
                name = ' \'Measures\','
                position = ' ' + str(1) + ','
                visible = ' ' + str(True) +'),'
                icon = ' null,'
                label = ' \'LBL_GRP_MEASURES\','
                id = str(array_info[2]) + str("{:04d}".format(int(element_type_id))) + '01'
                consult = '\n' + str(array_info[2]) + str("{:04d}".format(int(element_type_id))) + '01' + element_id  + name + name + label + icon + position + visible
                f = open(file_name , 'a')
                f.write(consult)
                f.close()
            else:
                for name in array_group_name:
                    array_grp_id = []
                    group_number = group_number + 1
                    if name == None:
                        element_type_id = array_info[0]
                        element_id = ', \'ElementSubtype:' + str(array_info[2]) + ':' + str(element_type_id) + '\','
                        name = ' \'Measures\','
                        position = ' ' + str(1) + ','
                        visible = ' ' + str(True) +'),'
                        icon = ' null,'
                        label = ' \'LBL_GRP_MEASURES\','
                        id = str("{:02d}".format(int(array_info[2]))) + str("{:04d}".format(int(element_type_id))) + str("{:02d}".format(int(group_number)))
                        consult = '\n(' + id + element_id + name + name + label + icon + position + visible
                        consult_aux = '(' + str("{:02d}".format(int(array_info[2]))) + str("{:04d}".format(int(element_type_id))) + str("{:02d}".format(int(group_number) - 1)) + element_id + name + name + label + icon + position + visible + '\n'
                        f = open(file_name , 'r')
                        lineas = f.readlines()
                        if consult_aux not in lineas:
                            array_grp_id.append(id)
                            array_grp_id.append(name)
                            arrays_group_id.append(array_grp_id)

                            f = open(file_name , 'a')
                            f.write(consult)
                            f.close()
                    else:
                        element_type_id = array_info[0]
                        element_id = ', \'ElementSubtype:' + str(array_info[2]) + ':' + str(element_type_id) + '\','
                        name_aux = '\'' + str(name) + '\','
                        name_label = name.upper()
                        position = ' ' + str(1) + ','
                        visible = ' ' + str(True) +'),'
                        icon = ' null,'
                        label = ' \'LBL_GRP_' + name_label + '\','
                        id = str("{:02d}".format(int(array_info[2]))) + str("{:04d}".format(int(element_type_id))) + str("{:02d}".format(int(group_number)))
                        consult = '\n(' + id + element_id + name_aux + name_aux + label + icon + position + visible 
                        consult_aux = '(' + str("{:02d}".format(int(array_info[2]))) + str("{:04d}".format(int(element_type_id))) + str("{:02d}".format(int(group_number) - 1)) + element_id + name_aux + name_aux + label + icon + position + visible + '\n'
                        f = open(file_name , 'r')
                        lineas = f.readlines()
                        if consult_aux not in lineas:
                            array_grp_id.append(id)
                            array_grp_id.append(name)
                            f = open(file_name , 'a')
                            f.write(consult)
                            f.close()
                            arrays_group_id.append(array_grp_id)
        # last read byte is our truncation point, move back to it.
        f = open(file_name , 'rb+')
        f.seek(-1, os.SEEK_END)
        f.truncate()  
        f.close()
        f = open(file_name , 'a')
        f.write(';')
        f.close()
        return arrays_group_id

#Generates the inserts for the ui equip_viewer_tabs
def equip_viewer_tabs_queries(array_info):

    if array_info[2] != None:
        element_info =  get_subtype_element_info(array_info)
        element_type_aux = element_info[0]
        nombre_archivo = str("{:02d}".format(int(array_info[2]))) + '-' + str(array_info[0]) + '.' + str(element_type_aux[0])
    else:
        element_info =  get_element_info(array_info[0])
        element_type_aux = element_info[0]
        nombre_archivo = str(array_info[0]) + '.' + str(element_type_aux[0])

    file_name = 'QUERIES/07.equip_viewers/' + nombre_archivo + '.sql'
    consult = "\n\nINSERT INTO ui.equip_viewer_tabs (equip_viewer_id, module_action_id, alias, description, \"label\", icon, \"position\", visible) VALUES"
    f = open(file_name , 'a')
    f.write(consult)
    f.close()

    if array_info[2] == None:
        element_type_id = array_info[0]
        element_id = '\n(\'ElementType:' + str(element_type_id) + '\','
        if (str(array_info[0]) == '2'):
            module_action_id = '102001,'
            alias = '\'SingleCam\' ,'
            description =  '\'CCTV Module - Action SingleCam\', '
            label = '\'LBL_ELEMENT_TYPE_PARAM_VIDEO\', '
            icon = 'null, '
            position = '1, '
            visible = 'True),'
            consult = element_id + module_action_id + alias + description + label + icon + position + visible
            f = open(file_name , 'a')
            f.write(consult)
    
        module_action_id = '100501,'
        alias = '\'Alarms module\' ,'
        description =  '\'Alarms module\', '
        label = '\'LBL_ALARMS\', '
        icon = 'null, '
        position = ' 1, '
        visible = 'True );'
        consult = element_id + module_action_id + alias + description + label + icon + position + visible
        f = open(file_name , 'a')
        f.write(consult)
    else:

        element_type_id = array_info[0]
        element_id = '\n(\'ElementSubtype:' + str(array_info[2]) + ':' + str(element_type_id) + '\','
        module_action_id = '100501,'
        alias = '\'Alarms module\' ,'
        description =  '\'Alarms module\', '
        label = '\'LBL_ALARMS\', '
        icon = 'null, '
        position = '1, '
        visible = 'True),'
        consult = element_id + module_action_id + alias + description + label + icon + position + visible
        f = open(file_name , 'a')
        f.write(consult)

        if (str(array_info[0]) == '2'):
            module_action_id = '102001,'
            alias = '\'SingleCam\' ,'
            description =  '\'CCTV Module - Action SingleCam\', '
            label = '\'LBL_ELEMENT_TYPE_PARAM_VIDEO\', '
            icon = 'null, '
            position = ' 1, '
            visible = 'True );'
            consult = element_id + module_action_id + alias + description + label + icon + position + visible
            f = open(file_name , 'a')
            f.write(consult)

    # last read byte is our truncation point, move back to it.
    f = open(file_name , 'rb+')
    f.seek(-1, os.SEEK_END)
    f.truncate()  
    f.close()
    f = open(file_name , 'a')
    f.write(';')
    f.close()
    return 0
    
def extract_pattern(array):
    return tuple(map(int, array.split(',')[0:2]))

#Generate the inserts for the ui equip_viewer_measures
def equip_viewer_measures_queries(array_measures,array_info,array_grp_id):
    if array_info[2] != None:
        element_info =  get_subtype_element_info(array_info)
        element_type_aux = element_info[0]
        nombre_archivo = str("{:02d}".format(int(array_info[2]))) + '-' + str(array_info[0]) + '.' + str(element_type_aux[0])
    else:
        element_info =  get_element_info(array_info[0])
        element_type_aux = element_info[0]
        nombre_archivo = str(array_info[0]) + '.' + str(element_type_aux[0])

    file_name = 'QUERIES/07.equip_viewers/' + nombre_archivo + '.sql'

        
    consult = ''
    array_entities = []
    array_consult = []
    for measure in array_measures:
        if measure[4] != None:
            for grp_id in array_grp_id:
                id_aux = str(grp_id[1].replace('\'', '').replace(',', ''))
                if id_aux == measure[4]:
                    equip_viewer_grp_measure_id = '\n(' + grp_id[0] + ','
        else:
            for grp_id in array_grp_id:
                if grp_id[1] == ' \'Measures\',' and measure[4] == None:
                    equip_viewer_grp_measure_id = '\n(' + grp_id[0] + ','
       #element_type_param_id = get_element_type_param_id(measure[0], measure[3])[0][0]
        if ((type(measure[3]) is list)):
               for posible_translation in measure[3]:
                   element_type_param_id_aux = get_element_type_param_id(measure[0], posible_translation[0])
                   if len(element_type_param_id_aux) != 0:
                       element_type_param_id = element_type_param_id_aux[0][0]
                       type_param_aux = get_param_type_id(measure[0], element_type_param_id, posible_translation[0])
                       if len(type_param_aux) != 0:
                           type_param = type_param_aux[0][0]
                       break
        else:
           element_type_param_id = get_element_type_param_id(measure[0], measure[3])[0][0]
           type_param = get_param_type_id(measure[0], element_type_param_id, measure[3])[0][0]

         

        vo_entities_id = '\'ElementTypeParam:' + str(measure[0])+':'+ str(type_param) +':' +str(element_type_param_id) + '\''
        #if (vo_entities_id != )
        array_entities.append(vo_entities_id)
        position = str(measure[2]) + ', '
        visible = str(bool(measure[1])) +'),'
        if (type_param == 2):
            consult = "\n\nINSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, position, visible) VALUES"
            consult_aux = "INSERT INTO ui.equip_viewer_measures (equip_viewer_grp_measure_id, vo_entities_id, position, visible) VALUES\n"
            f = open(file_name, 'r')
            filename_aux = f.readlines()
            f.close()
            if consult_aux not in filename_aux:
                f = open(file_name , 'a')
                f.write(consult)
                f.close()
            f = open(file_name, 'r')
            filename_aux = f.readlines()
            f.close()
            if ('--' + str(element_type_aux[0])) not in filename_aux:
                f = open(file_name , 'a')
                f.write('\n--' + str(element_type_aux[0]))
                f.close()
                
            consult = equip_viewer_grp_measure_id + vo_entities_id +', '+ position + visible
            array_consult.append(consult)

    order_consult = sorted(array_consult)
    grp_order = []
    array_group_order = []
    for i in range(len(order_consult)):
        consult_aux = order_consult[i].split(',')[0].split('(')[1]
        if i != len(order_consult) - 1:
            consult_aux_next = order_consult[i + 1].split(',')[0].split('(')[1]
        else:
            consult_aux_next = 0

        if consult_aux == consult_aux_next:
            grp_order.append(order_consult[i])
        else:
            grp_order.append(order_consult[i])
            array_group_order.append(grp_order)
            grp_order = []
    for order_group_position in array_group_order:
        if None in order_group_position:
            print ("Rellena la posicion en el Excel")
        else:
            final_order = sorted(order_group_position, key=lambda x: int(x.split(',')[2]))
        for consult in final_order:
            f = open(file_name , 'a')
            f.write(consult)
            f.close()

    # last read byte is our truncation point, move back to it.
    f = open(file_name , 'rb+')
    f.seek(-1, os.SEEK_END)
    f.truncate()  
    f.close()
    f = open(file_name , 'a')
    f.write(';')
    f.close()
    return array_entities

def equip_viewer_configs(array_measures,array_info):

    if array_info[2] != None:
        element_info =  get_subtype_element_info(array_info)
        element_type_aux = element_info[0]
        nombre_archivo = str("{:02d}".format(int(array_info[2]))) + '-' + str(array_info[0]) + '.' + str(element_type_aux[0])
    else:
        element_info =  get_element_info(array_info[0])
        element_type_aux = element_info[0]
        nombre_archivo = str(array_info[0]) + '.' + str(element_type_aux[0])
  

    file_name = 'QUERIES/07.equip_viewers/' + nombre_archivo + '.sql'



    array_entities = []
    i = 0
    for measure in array_measures:
       i = i + 1
       if array_info[2] != None: 
            equip_viewer_grp_measure_id = '\n(' + str("{:02d}".format(int(measure[0]))) +  str("{:02d}".format(int(array_info[2]))) + str("{:02d}".format(int(i))) +','
       else:
            equip_viewer_grp_measure_id = '\n(00' + str("{:02d}".format(int(measure[0]))) + str("{:02d}".format(int(i))) +','

       #element_type_param_id = get_element_type_param_id(measure[0], measure[3])[0][0]
       
       if ((type(measure[3]) is list)):
           for posible_translation in measure[3]:
               element_type_param_id_aux = get_element_type_param_id(measure[0], posible_translation[0])
               if len(element_type_param_id_aux) != 0:
                   element_type_param_id = element_type_param_id_aux[0][0]
                   type_param_aux = get_param_type_id(measure[0], element_type_param_id, posible_translation[0])
                   if len(type_param_aux) != 0:
                       type_param = type_param_aux[0][0]
                   break
       else:
           element_type_param_id = get_element_type_param_id(measure[0], measure[3])[0][0]
           type_param = get_param_type_id(measure[0], element_type_param_id, measure[3])[0][0]
      
       vo_entities_id = '\'ElementTypeParam:' + str(measure[0])+':'+ str(type_param) +':' +str(element_type_param_id) + '\''
       #if (vo_entities_id != )
       if array_info[2] == None:
            equip_viewer_id = '\'ElementType:' + str(measure[0]) + '\'' + ','
       else:
            equip_viewer_id = '\'Elementsubtype:' + str(array_info[2]) + ':' + str(measure[0]) + '\'' + ','

       array_entities.append(vo_entities_id)
       position = str(measure[2]) + ', '
       visible = str(bool(measure[1])) +'),'

       if (type_param == 1):
            #reg = '(\d*)'
           # equip_viewers_id_aux = re.findall(reg,str(measure[0]))
            #element_aux = re.findall(reg,str(element))
            consult = "\n\nINSERT INTO ui.equip_viewer_configs (id, equip_viewer_id, vo_entities_id, \"position\", visible) VALUES"
            consult_aux = "INSERT INTO ui.equip_viewer_configs (id, equip_viewer_id, vo_entities_id, \"position\", visible) VALUES\n"
            f = open(file_name, 'r')
            filename_aux = f.readlines()
            f.close()
            if consult_aux not in filename_aux:
                f = open(file_name , 'a')
                f.write(consult)
                f.close()


            consult = equip_viewer_grp_measure_id + equip_viewer_id + vo_entities_id +', '+ position + visible 
            f = open(file_name , 'a')
            f.write(consult)
            f.close()
    # last read byte is our truncation point, move back to it.
    f = open(file_name , 'rb+')
    f.seek(-1, os.SEEK_END)
    f.truncate()  
    f.close()
    f = open(file_name , 'a')
    f.write(';')
    f.close()


#Given the the excel file's path
def read_excel(path):
    # To open the workbook
    excel_file = xl.load_workbook(path)
    
    #If the update folder exists, erase all the data from it
    if os.path.exists('QUERIES/07.equip_viewers'):
        shutil.rmtree('QUERIES/07.equip_viewers')
    else:
        if os.path.exists('QUERIES'):
            os.mkdir('QUERIES/07.equip_viewers')
        else:
            os.mkdir('QUERIES')
    os.mkdir('QUERIES/07.equip_viewers')

    #Number of sheets in the excel file
    number_sheets = len(excel_file.sheetnames)
    #All the sheets
    sheets = excel_file.sheetnames


    #array_for_measures = []
    #Readying sheets loop
    for i in range(0, number_sheets):

        information = []
        array_grp_id = []
        actual_sheet_name = excel_file[sheets[i]]
        excel_file.active =  actual_sheet_name
        active_sheet = excel_file.active

        max_rows = active_sheet.max_row +1
        element_type_id = int(actual_sheet_name.title.split('.-')[0])
        element_subtype_id = None
        if len(actual_sheet_name.title.split('.-')) == 3:
            element_subtype_id = actual_sheet_name.title.split('.-')[2]
        information.append(element_type_id)
        arrays_measures = []
        #Loop for generating all the update queries
        for j in range (2, max_rows):
            measure = []
            measure.append(element_type_id)
            #Get all the variables for the query
            text = active_sheet.cell(row = j, column = 1).value
            if (text == 'Number of columns on the ui'):
                columns = active_sheet.cell(row = j , column = 2).value
                information.append(columns)
                information.append(element_subtype_id)
            enabled = active_sheet.cell(row = j, column = 3).value
            measure.append(enabled)
            groups_name = active_sheet.cell(row = j, column = 4).value
            if (enabled != None and enabled != 'ENABLED'):
                position = active_sheet.cell(row = j, column = 2).value
                measure.append(position)
                label =  translate_param_label(active_sheet.cell(row = j , column= 1).value)

                if (type(label) is list):
                    if len(label) != 1:
                        fail = active_sheet.cell(row = j , column= 1).value +'--> '+ str(label) + '\n'
                        f = open('translation_fails.txt' , 'a+')
                        if fail in open('translation_fails.txt').read():
                            pass
                        else:
                            f.write(fail)
                        f.close()

                measure.append(label)
                measure.append(groups_name)          
                if enabled != 0:
                    arrays_measures.append(measure)
        #array_measures = split_array_measures()[0]
        #array_configs = split_array_measures()[1]
        #Create the inserts on ui.equip_viewers
        vo_entities()
        equip_viewers_queries(information,arrays_measures)
        equip_viewer_tabs_queries(information)
        array_grp_id = equip_viewer_grp_measures_queries(information, arrays_measures)
        array_entities = equip_viewer_measures_queries(arrays_measures, information, array_grp_id)
        vo_entities_if_not_exists(array_entities)
        equip_viewer_configs(arrays_measures,information)
