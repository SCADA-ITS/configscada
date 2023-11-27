import os
import psycopg2
import sys
from GenerarExcel import generate_excel
from LeerExcel import read_excel
from GenerarExcel import element_info

cwd = os.path.dirname(os.path.abspath(__file__))
path_properties = 'Properties/'
g_properties = os.path.join(cwd, path_properties)
if not os.path.exists(g_properties):
   os.makedirs(path_properties)
path_xlsx = 'Properties/grids.xlsx'
g_xlsx = os.path.join(cwd, path_xlsx)

path = os.path.join(cwd, 'Config/')
path_grids = os.path.join(cwd, 'Config/04.grids/')

if not os.path.exists(path):
   os.makedirs(path)
if not os.path.exists(path_grids):
   os.makedirs(path_grids) 

#DB CONNECTION
'''HOST = '192.168.88.177'
PORT = '5430'''
HOST = sys.argv[1]
PORT = sys.argv[2]
DB = "rits"
USER = "rits"
PASS = "rits"

#incidents = 4


def get_elements():
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()
        cur.execute("SELECT distinct element_type_id FROM conf.elements WHERE element_type_id  != 1000 ORDER BY element_type_id ASC;")

        info = cur.fetchall()

        cur.close()
        connection.close()
        return info

    except AttributeError:
        print(ValueError)

def get_alias_by_id(id):
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()
        consult = 'SELECT label_alias FROM master.element_types WHERE element_type_id = ' + str(id) + ';'
        cur.execute(consult)

        info = cur.fetchone()

        cur.close()
        connection.close()
        return info[0]

    except AttributeError:
        print(ValueError)

def get_measures(id):
    measures_enabled = []
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()
        #Si quiere que salgan todas las alarmas posibles, incluyendo las enabled = False borre de la siguiente consulta 'and enabled = True'
        consult = "SELECT DISTINCT vo_entities_id FROM ui.equip_viewer_measures WHERE visible = True"
        cur.execute(consult)
        measures_element = cur.fetchall()
        cur.close()
        connection.close()
        for vo_entitie in measures_element:
            info  = vo_entitie[0].split(':')
            if int(info[1]) == int(id):
                measures_enabled.append(info)
        return measures_enabled
    except AttributeError:
        print(ValueError)

def get_data_type(param):
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()
        #Si quiere que salgan todas las alarmas posibles, incluyendo las enabled = False borre de la siguiente consulta 'and enabled = True'
        consult = 'SELECT data_type_id FROM master.element_type_params WHERE element_type_id ='+param[1]+' AND param_type_id = '+param[2]+' AND element_type_param_id = '+param[3]
        cur.execute(consult)
        info = cur.fetchone()
        cur.close()
        connection.close()
        
        return info[0]
    except AttributeError:
        print(ValueError)

def get_label(param):
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()
        #Si quiere que salgan todas las alarmas posibles, incluyendo las enabled = False borre de la siguiente consulta 'and enabled = True'
        consult = 'SELECT label_alias FROM master.element_type_params WHERE element_type_id ='+param[1]+' AND param_type_id = '+param[2]+' AND element_type_param_id = '+param[3]
        cur.execute(consult)
        info = cur.fetchone()
        cur.close()
        connection.close()
        if info != None:
            info = info[0]
        else:
            info = param[0]+':' + param[1]+':'+ param[2]+':' + param[3]
        return info
    except AttributeError:
        print(ValueError)

def add_table_grids(g_sql, elements):
    print (g_sql)
    if elements != None:
        element_id = elements
        label = get_alias_by_id(element_id)
        label = label.replace('ELEMENT_TYPE_','')
        grid = '('+ str(element_id) +', 2, \''+ str(label) +'\', true, true),\n'
        f = open(g_sql  , 'a')
        f.write(grid)
        f.close()

# required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function) VALUES\n'
def add_grid_fields(g_sql, elements):
    #for i in range(0, len(elements)):
        grid_id = elements
        
        measures = get_measures(grid_id)
        contador = 6
        if len(measures) != 0:
            for j in range (0, len(measures)):
                if j == 0:
                    first_field = '('+str(grid_id) + '0001'+', '+str(grid_id)+', 1, 1, true, false, false, false, \'LBL_ID\', \'LBL_ID\', \'id\', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL),\n('+str(grid_id) + '0002'+', '+str(grid_id)+', 1, 2, true, false, false, true, \'LBL_NAME\', \'LBL_NAME\', \'alias\', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, true, NULL),\n('+str(grid_id) + '0003'+', '+str(grid_id)+', 1, 3, true, false, false, false, \'LBL_LOCATION\', \'LBL_LOCATION\', \'locationAlias\', false, false, null, false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL),\n('+str(grid_id) + '0004'+', '+str(grid_id)+', 1, 4, true, true, false, false, NULL, NULL, \'state\', false, false, null, false, false, 35, NULL, true, true, NULL, NULL, NULL, NULL),\n('+str(grid_id) + '0005'+', '+str(grid_id)+', 1, 5, true, true, false, true, \'LBL_STATE_LABEL\', \'LBL_STATE_LABEL\', \'stateLabel\', false, false, null, true, false, 250, NULL, true, true, NULL, NULL, NULL, NULL),\n'
                    f = open(g_sql  , 'a')
                    f.write(first_field)
                    f.close()
                measure = measures[j]
                #element_type_param_id, param_type_id, label_alias, enabled
                id = '(' + str(grid_id) + str("{:0>4d}".format(int(contador))) +', '
                label = '\'' + get_label(measure) + '\', '
                data_type = get_data_type(measure)
                type_param = '\'ElementTypeParam:' + str(grid_id) +':' +str(measure[2])+ ':' + str(measure[3]) +'\', '
                required = 'true, '
                parameterized = 'false, '
                filtered = 'false, '
                if int(measure[2]) == 1:
                    extended = 'true, '
                    required = 'false, '
                else:
                    extended = 'false, '
                is_pk = 'false, '
                is_epoch_milli = 'false, '
                if int(data_type) == 4:
                    is_epoch_milli = 'true, '
                align = '\'left\', '
                needs_translation = 'false, '
                grouping_summary = 'false, '
                width_px = '200, '
                grid_field_group_operation_id = 'NULL, '
                enabled = 'true, '
                visible = 'true, '
                adjust = 'true, '
                header_vertical = 'false, '
                fixed_column = 'true, '
                replacement_function = 'NULL'
                field = id + str(grid_id) + ', ' + str(data_type)+', ' + str(contador) +', '+ required + parameterized + extended + filtered + label + label+ type_param + is_pk+ is_epoch_milli+ align + needs_translation + grouping_summary + width_px + grid_field_group_operation_id + enabled+ visible + adjust + header_vertical + fixed_column + replacement_function +'),\n'
                f = open(g_sql  , 'a')
                f.write(field)
                f.close()
                contador = contador + 1 
        
        else:
            first_field = '('+str(grid_id) + '0001'+', '+str(grid_id)+', 1, 1, true, false, false, false, \'LBL_ID\', \'LBL_ID\', \'id\', true, false, null, false, false, 500, NULL, true, false, NULL, NULL, NULL, NULL),\n('+str(grid_id) + '002'+', '+str(grid_id)+', 1, 2, true, false, false, true, \'LBL_NAME\', \'LBL_NAME\', \'alias\', false, false, null, false, false, 150, NULL, true, true, NULL, NULL, true, NULL),\n('+str(grid_id) + '0003'+', '+str(grid_id)+', 1, 3, true, false, false, false, \'LBL_LOCATION\', \'LBL_LOCATION\', \'locationAlias\', false, false, null, false, false, 250, NULL, true, true, NULL, NULL, NULL, NULL),\n('+str(grid_id) + '0004'+', '+str(grid_id)+', 1, 4, true, true, false, false, NULL, NULL, \'state\', false, false, null, false, false, 35, NULL, true, true, NULL, NULL, NULL, NULL),\n('+str(grid_id) + '0005'+', '+str(grid_id)+', 1, 5, true, true, false, true, \'LBL_STATE_LABEL\', \'LBL_STATE_LABEL\', \'stateLabel\', false, false, null, true, false, 250, NULL, true, true, NULL, NULL, NULL, NULL),\n'
            f = open(g_sql  , 'a')
            f.write(first_field)
            f.close()
        f = open(g_sql  , 'a')
        f.write('\n')
        f.close()

def add_grid_field_icons(g_sql, incidents):
    for i in range (0, incidents):
        icon  ='(100307, \'./img/incident_level/incident_level_'+str(i+1)+'.png\', \'ImsIncidentLevel:'+str(i+1)+'\', false, false, false, true, \'LBL_ICON_IMS_INCIDENT_LEVEL_'+str(i+1)+'\'),\n'
        f = open(g_sql  , 'a')
        f.write(icon)
        f.close()

def add_grid_commands(g_sql, element):
        #id = element[0]
        command = '('+str(element)+', 1, \'LBL_CMD_VIEW_EQUIPMENT\', \'mdi mdi-eye\', true, 101401, null, null, false, false, true),\n('+str(element)+', 2, \'LBL_CMD_SHOW_IN_MAP\', \'mdi mdi-map-search\', false, 200004, null, null, false, false, true),\n('+str(element)+', 3, \'LBL_CMD_SHOW_ALARMS\', \'mdi mdi-bell\', false, 100501, null, 2, true, true, true),\n\n'
        f = open(g_sql  , 'a')
        f.write(command)
        f.close()

def add_grid_field_icons_2(g_sql, element):
        #id = element[0]
        icon = '('+str(element)+'0004, \'./img/state/stateUnknown.png\', \'ElementTypeState:'+str(element)+':0\', false, false, false, true, \'LBL_ICON_STATE_UNKNOWN\'),\n('+str(element)+'0004, \'./img/state/stateUp.png\', \'ElementTypeState:'+str(element)+':1\', false, false, false, true, \'LBL_ICON_STATE_UP\'),\n('+str(element)+'0004, \'./img/state/stateDown.png\', \'ElementTypeState:'+str(element)+':2\', false, false, false, true, \'LBL_ICON_STATE_DOWN\'),\n('+str(element)+'0004, \'./img/state/stateAlarm.png\', \'ElementTypeState:'+str(element)+':3\', false, false, false, true, \'LBL_ICON_STATE_ALARM\'),\n\n'
        f = open(g_sql  , 'a')
        f.write(icon)
        f.close()

def add_grid_field_references(g_sql, element):
        #id = element[0]
        references = '('+str(element)+'0005, '+str(element)+'0004, \'ELEMENT_TYPE_STATE\', \'id\', \'labelAlias\', true),\n'
        f = open(g_sql  , 'a')
        f.write(references)
        f.close()

def truncate_semicolon(g_sql, times):
    with open(g_sql, 'rb+') as filehandle:
        filehandle.seek(-1, os.SEEK_END)
        filehandle.truncate()
        filehandle.seek(-1, os.SEEK_END)
        filehandle.truncate()
        if(times == 1):
            filehandle.seek(-1, os.SEEK_END)
        filehandle.truncate()
    filehandle.close()
    f = open(g_sql  , 'a')
    f.write(';\n\n')
    f.close()

def main(g_sql, incidents):

    
        if (element[0] !=None):
            #Inicio
            #FIRST TABLE
            first_insert = 'INSERT INTO ui.grids(id, adapter_id, "label", enabled, visible) VALUES\n'
            f = open(g_sql  , 'w')
            f.write(first_insert)
            f.close()


            add_table_grids(g_sql, element[0])
            truncate_semicolon(g_sql, 0)
            print('ui.grids DONE')

            #SECOND TABLE
            second_insert = 'INSERT INTO ui.grid_fields(id, grid_id, grid_field_data_type_id, "position", required, parameterized, extended, filtered, "label", label_tooltip, source_field, is_pk, is_epoch_milli, align, needs_translation, grouping_summary, width_px, grid_field_group_operation_id, enabled, visible, adjust, header_vertical, fixed_column, replacement_function) VALUES\n'
            f = open(g_sql  , 'a')
            f.write(second_insert)
            f.close()
            add_grid_fields(g_sql, element[0])
            truncate_semicolon(g_sql, 1)
            print('ui.grid_fields DONE')

            #THIRD TABLE
            if incidents != 0:
                third_insert = 'INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES\n'
                f = open(g_sql  , 'a')
                f.write(third_insert)
                f.close()
                add_grid_field_icons(g_sql, incidents)
                truncate_semicolon(g_sql, 0)
                print('ui.grid_field_icons DONE')

            
        else:
            print('Revisar la BBDD ya que no hay elementos.')

def read_excel_function(counter, g_sql, element):
    elements = get_elements()
    if elements != None:
        #FOURTH TABLE
        read_excel(counter, g_sql)
        truncate_semicolon(g_sql, 1)
        print('ui.grid_option_values DONE')

        #FIFTH TABLE
        fifth_insert = 'INSERT INTO ui.grid_commands(grid_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required) VALUES\n'
        f = open(g_sql  , 'a')
        f.write(fifth_insert)
        f.close()
        add_grid_commands(g_sql, element)
        truncate_semicolon(g_sql, 1)
        print('ui.grid_commands DONE')

        #SIXTH TABLE
        sixth_insert = 'INSERT INTO ui.grid_field_icons(grid_field_id, icon, value, show_value, badge_color, avatar, enabled, label_value) VALUES\n'
        f = open(g_sql  , 'a')
        f.write(sixth_insert)
        f.close()
        add_grid_field_icons_2(g_sql, element)
        truncate_semicolon(g_sql, 1)
        print('ui.grid_field_icons DONE')

        #SEVENTH TABLE
        seventh_insert = 'INSERT INTO ui.grid_field_references(grid_field_id, ref_field_id, em_id, em_field, em_field_returned, enabled) VALUES\n'
        f = open(g_sql  , 'a')
        f.write(seventh_insert)
        f.close()
        add_grid_field_references(g_sql, element)
        truncate_semicolon(g_sql, 0)
        print('ui.grid_field_references DONE')
    else:
        print('Revisar la BBDD ya que no hay elementos.')


if sys.argv[3] == '1':
    print('Creando fichero .sql y excel de configuracion de parametros...')
    elements = get_elements()
    for element in elements:
        element_name = element_info(element[0])
        element_format = f"{element[0]:02d}"
        path_sql = 'Config/04.grids/' + str(element_format) + '.' + element_name + '.sql'
        g_sql = os.path.join(cwd, path_sql)
        if not os.path.exists(g_sql):
            open(g_sql, 'w').close()       
        incidents = int(sys.argv[4])
        main(g_sql, incidents)
    generate_excel(g_xlsx, 'es_ES')
    print('Ejecucion completada con exito.')
elif sys.argv[3] == '2':
    print('Leyendo excel....')
    elements = get_elements()
    counter = 1
    for element in elements:
        element_name = element_info(element[0])
        element_format = f"{element[0]:02d}"
        path_sql = 'Config/04.grids/' + str(element_format) + '.' + element_name + '.sql'
        g_sql = os.path.join(cwd, path_sql)
        if not os.path.exists(g_sql):
            open(g_sql, 'w').close()
        print (g_sql)           
        read_excel_function(counter, g_sql, element[0])
        counter = counter + 1
    print('Ejecucion completada con exito.')