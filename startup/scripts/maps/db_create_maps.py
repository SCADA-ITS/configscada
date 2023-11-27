import os
import psycopg2
from translation import translation

cwd = os.path.dirname(os.path.abspath(__file__))
path = 'Properties/layers.csv'
layers_csv = os.path.join(cwd, path)

path = '../../../ritsfront/openits/static/img/graphic_icon/resources'
folder_graphic_icons =os.path.join(cwd, path)

path = 'Properties/elements.csv'
elements_csv = os.path.join(cwd, path)

path_maps = 'Config/09.maps.sql'
maps_sql = os.path.join(cwd, path_maps)

path = 'Properties/graphic_subtypes.csv'
graphic_subtypes_csv = os.path.join(cwd, path)

path_config = 'Config/'
config_path = os.path.join(cwd, path_config)
if not os.path.exists(config_path):
   os.makedirs(config_path)
   
if not os.path.exists(elements_csv):
    f = open(elements_csv  , 'w')      
    f.write('layer;element;icon_widht;icon_height;longitude;latitude')
    f.close()
    print('No existe elements.csv...')
else:
    num_lines = 0
    num_lines = sum(1 for line in open(elements_csv))
    if num_lines == 1:
        print('El archivo elements.csv esta vacio...')


if not os.path.exists(layers_csv):
    f = open(layers_csv  , 'w')      
    f.write('label;layer_relationship')
    f.close()
    print('No existe layers.csv...')
else:
    num_lines = 0
    num_lines = sum(1 for line in open(layers_csv))
    if num_lines == 1:
        print('El archivo layers.csv esta vacio...')

if not os.path.exists(graphic_subtypes_csv):
    f = open(graphic_subtypes_csv  , 'w')      
    f.write('type;subtype;icon;js')
    f.close()
    print('No existe graphic_subtypes.csv...')
else:
    num_lines = 0
    num_lines = sum(1 for line in open(graphic_subtypes_csv))
    if num_lines == 1:
        print('El archivo graphic_subtypes.csv esta vacio...')

any_graphic_icon_written = False

HOST = '192.168.88.177'
PORT = '5430'
DB = "rits"
USER = "rits"
PASS = "rits"

#Adds espaces and semicolon
#times = 1 if need to truncate twice before semicolon
def truncate_semicolon(path, times):
    with open(path, 'rb+') as filehandle:
        filehandle.seek(-1, os.SEEK_END)
        filehandle.truncate()
        filehandle.seek(-1, os.SEEK_END)
        filehandle.truncate()
        if (times == 1):
            filehandle.seek(-1, os.SEEK_END)
        filehandle.truncate()
    filehandle.close()
    f = open(path  , 'a')      
    f.write(';\n\n')
    f.close()
        
#Writes on maps.sql layers based on Properties/layers.csv     
def add_map_layers(layer_name, cont):
    trasl = translation('es_ES', layer_name, HOST, PORT)

    ins = '('+str(cont)+', NULL, 1, \'Layer '+trasl+'\', \'Layer '+trasl+'\', \''+layer_name+'\', NULL, true),\n'
    f = open(maps_sql, 'a')
    f.write(ins)
    f.close

#Writes on maps.sql layer relationships based on Properties/layers.csv     
def add_map_layer_relationships(line, cont):
    parent  =str(line[1]).replace('\n','')
    ins = '(1, '+parent+ ', ' +str(cont)+'),\n'
    f = open(maps_sql, 'a')
    f.write(ins)
    f.close

#Returns subtype of an element, if doesnt have --> returns None    
def get_subtype(element):
    type = element.split(':')[1]
    element = element.split(':')[2]
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()

        consult = 'SELECT element_subtype_id FROM conf.elements WHERE element_type_id = ' + str(type) + ' and element_id = ' + str(element) + ';'
        cur.execute(consult)

        info = cur.fetchone()
        
        if info != None:
            info = info[0]
        cur.close()
        connection.close()
        return info

    except AttributeError:
        print(ValueError)
   
#Returns a list of graphic icons by <subtype><type>
#Use: know how many of one kind exists
def get_all_graphic_icons(posible):
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()

        consult = 'SELECT * FROM ui.graphic_icons where CAST(id AS TEXT) LIKE \''+posible+'%\''
        cur.execute(consult)

        info = cur.fetchall()
        
        cur.close()
        connection.close()
        return info

    except AttributeError:
        print(ValueError)

#Returns the graphic icon id for an especific width, height and svg_file
#If doesnt exists --> returns None
def exists_graphic_icon_db(width, height, svg_file):
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()

        consult ='SELECT id from ui.graphic_icons where width = '+width+' and height = '+str(height)+' and svg_file = \'img/graphic_icon/'+svg_file + '\' '

        cur.execute(consult)

        info = cur.fetchone()
        if info != None:
            info = info[0]
        cur.close()
        connection.close()
        return info

    except AttributeError:
        print(ValueError)

#Gets the svg file from graphic_subtypes.csv, if not subtype --> by type                    
def get_svg_file(element_type, element_subtype):
    if element_subtype == None:
        element_subtype =''
    path = 'Properties/graphic_subtypes.csv'
    csv = os.path.join(cwd, path)
    f = open(csv  , 'r')
    cont = f.readlines()
    aux = 0
    if len(cont) != 1:
        for line in cont:
                if aux == 0:
                    aux = 1
                else:
                    line = line.split(';')
                    type = line[0]
                    sub = line[1]
                    if type == element_type and sub == str(element_subtype):
                        f.close()
                        return line[2]
        f = open(csv  , 'r')
    cont = f.readlines()
    aux = 0
    if len(cont) != 1:
        for line in cont:
                if aux == 0:
                    aux = 1
                else:
                    line = line.split(';')
                    type = line[0]
                    sub = line[1]
                    if type == element_type:
                        f.close()
                        return line[2]
              
#Gets the javascript file from graphic_subtypes.csv, if not subtype --> by type                    
def get_js_file(element_type, element_subtype):
    if element_subtype == None:
        element_subtype =''
    path = 'Properties/graphic_subtypes.csv'
    csv = os.path.join(cwd, path)
    f = open(csv  , 'r')
    cont = f.readlines()
    aux = 0
    if len(cont) != 1:
        for line in cont:
                if aux == 0:
                    aux = 1
                else:
                    line = line.split(';')
                    type = line[0]
                    sub = line[1]
                    if type == element_type and sub == str(element_subtype):
                        f.close()
                        javasc = line[3].replace('\n','')
                        return javasc
    f = open(csv  , 'r')
    cont = f.readlines()
    aux = 0
    if len(cont) != 1:
        for line in cont:
                if aux == 0:
                    aux = 1
                else:
                    line = line.split(';')
                    type = line[0]
                    sub = line[1]
                    if type == element_type:
                        f.close()
                        javasc = line[3].replace('\n','')
                        return javasc

#Gets the alias of an element on master.element_types
def get_name_by_type(el_type):
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()
        consult = "SELECT alias FROM master.element_types WHERE element_type_id  = " + str(el_type)

        cur.execute(consult)

        info = cur.fetchone()
        
        if info != None:
            info = info [0]
        cur.close()
        connection.close()
        return info

    except AttributeError:
        print(ValueError)

#Gets the alias of an element on master.element_subtypes
def get_name_by_sub(el_type, el_subtype):
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()
        consult = "SELECT alias FROM master.element_subtypes  WHERE element_type_id  = "+str(el_type)+" and element_subtype_id = "+str(el_subtype)


        cur.execute(consult)

        info = cur.fetchone()
        
        if info != None:
            info = info [0]
        cur.close()
        connection.close()
        return info

    except AttributeError:
        print(ValueError)

#Get element alias from conf.elements        
def get_name(element):
    el_type = element.split(':')[1]
    el_id = element.split(':')[2]
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()
        consult = "SELECT alias FROM conf.elements  WHERE element_type_id  = "+str(el_type)+" and element_id = "+str(el_id)


        cur.execute(consult)

        info = cur.fetchone()
        
        if info != None:
            info = info [0]
        cur.close()
        connection.close()
        return info

    except AttributeError:
        print(ValueError)

#Checks if the id exists on maps.sql, if exists lasts 2 digits increments 1        
def check_id_maps(id):
    nuevo_id = None
    aux_id = id
    f = open(maps_sql  , 'r')
    Lines = f.readlines()
    aux = 0
    for i in range (0, len(Lines)):
        line = Lines[i]
        l = line.strip()
        if "INSERT INTO ui.graphic_icons" in l:
            aux = i
            break
    if aux != 0:
        for j in range(aux +1  , len(Lines)):
            line = Lines[j]
            absa = line.split(',')[0]
            absa = absa.replace('(','')
            inicio = absa [0:8]
            inicio_id = id[0:8]
            if inicio == inicio_id:
                aux_id = absa
            
            
        inicio = aux_id [0:8]
        fin = int(aux_id[8:10]) + 1
        fin = str("{:0>2d}".format(int(fin)))
        nuevo_id = str(inicio) + str(fin)

    return nuevo_id

#Checks if graphic icon already added on maps.sql
#Return none if not exists, else id
def exists_graphic_icon_sql(width, height, svg_file):
    exists = None
    f = open(maps_sql  , 'r')
    Lines = f.readlines()
    aux = 0
    for i in range (0, len(Lines)):
        line = Lines[i]
        l = line.strip()
        if "INSERT INTO ui.graphic_icons" in l:
            aux = i
            break
    if aux != 0:
        for j in range(aux +1  , len(Lines)):
            line = Lines[j]
            width_aux = line.split(',')[6]
            width_aux = width_aux.replace(' ','')
            height_aux = line.split(',')[7]
            height_aux = height_aux.replace(' ','')
            svg_aux = line.split(',')[9]
            svg_aux = svg_aux.replace('\'','')
            svg_aux = svg_aux.split('/')[2]
            if width_aux == width and height == height_aux and svg_aux == svg_file:
                exists = line.split(',')[0]
                exists = exists.replace('(','')
                break
    return exists

#Writes on maps.sql new graphic icons needed
def create_graphic_icon(id, width, height, svg_file, js_file):
    
    global any_graphic_icon_written
    el_type = id[2:6]
    el_subtype = id[0:2]
    id = '01' + id
    if el_subtype == '00':
        name = get_name_by_type(el_type)
    else:
        name = get_name_by_sub(el_type, el_subtype)

    id_aux = check_id_maps(id)
    if id_aux  != None:
        id = id_aux

    if js_file == None:
        insert = "("+id+", '"+name+"', '"+name+"', false, true, 'component/graphicIcon/graphicIconCustom.js', "+width+", "+height+", null, 'img/graphic_icon/"+svg_file+"', null),\n"
    else:
        insert = "("+id+", '"+name+"', '"+name+"', false, true, 'component/graphicIcon/graphicIconCustom.js', "+width+", "+height+", null, 'img/graphic_icon/"+svg_file+"', 'component/graphicIcon/custom/"+js_file+"'),\n"

    #if havent made any insert, writes the INSERT INTO
    if any_graphic_icon_written == False:
        first = 'INSERT INTO ui.graphic_icons (id, "name", description, alarms, measures, js_draw_file, width, height, svg_pattern_var, svg_file, js_custom_type_render) VALUES\n'
        f = open(maps_sql  , 'a')
        f.write(first)
        f.close()
        any_graphic_icon_written = True

    f = open(maps_sql  , 'a')
    f.write(insert)
    f.close()
    
#Checks if exists graphic icon based on info of Properties/elements.csv
#If dont, writes it with the function above        
def add_graphic_icon(line):
    et = line[1].split(':')[1]
    element_type = str("{:0>4d}".format(int(et)))
    width = line[2]
    height = line[3]
    subtype = get_subtype(line[1])


    '''graphic_icon = get_graphic_icon(posible_grap, line[1], line[2])
    if graphic_icon == None:
        create_graphic_icon(posible_grap, line[1], line[2])
    #select * from ui.graphic_icons where CAST(id AS TEXT) LIKE '302%'''
    #Consults on graphic_subtypes.csv svg and javascript file
    svg_file = get_svg_file(et, subtype)
    js_file = get_js_file(et,subtype)

    #Checks if the graphic icon already exists on db 
    exists_db = exists_graphic_icon_db(width, height, svg_file)
    #Checks if the graphic icon already exists on maps.sql 
    exists_sql = exists_graphic_icon_sql(width, height, svg_file)
    if exists_db == None and exists_sql == None:
        if subtype == None:
                subtype = 0
        element_subtype =  str("{:0>2d}".format(int(subtype)))
        aux =  element_subtype + element_type
        #Gets the incremental for an especific graphic icon
        list_gi = get_all_graphic_icons(aux)
        if list_gi == None:
            new_graphic_icon_id = element_subtype + element_type 
        else:
            len_list_gi = len(list_gi)
            new_graphic_icon_id = element_subtype + element_type + str("{:0>2d}".format(int(len_list_gi)))
        #Writes the insert of the new graphic icon
        create_graphic_icon(new_graphic_icon_id,width, height, svg_file, js_file)

#Gets the layer_id by layer_name
def get_layer_id(layer_name):
    f = open(maps_sql  , 'r')
    Lines = f.readlines()
    aux = 0
    for i in range (0, len(Lines)):
        line = Lines[i]
        l = line.strip()
        if "INSERT INTO ui.map_layers" in l:
            aux = i
            break
    if aux != 0:
        for j in range(aux +1  , len(Lines)):
            line = Lines[j]
            label = line.split(',')[5]
            label = label.replace(' ','')
            label = label.replace('\'','')
            if label == layer_name:
                layer_id = line.split(',')[0]
                layer_id = layer_id.replace(' ','')
                layer_id = layer_id.replace('(','')
                return layer_id

#Returns the especific graphic icon for the given features 
def get_graphic_icon(element, width, height):
    graphic_icon = None
    element_subtype = get_subtype(element)
    element_type = element.split(':')[1] 
    svg_file = get_svg_file(element_type, element_subtype)
    graphic_icon = exists_graphic_icon_db(width, height, svg_file)
    if graphic_icon == None:
        graphic_icon = exists_graphic_icon_sql(width, height, svg_file)
    return graphic_icon 

#line = array[layer;element;icon_widht;icon_height;longitude;latitude]
def add_map_layer_element(line, cont):
    layer_id = get_layer_id(line[0])
    name = get_name(line[1])
    gi = get_graphic_icon(line[1], line[2], line[3]) 
    ins = "("+str(cont)+", "+str(layer_id)+", '"+line[1]+"', "+str(gi)+", '"+name+"', " +line[4]+" , "+line[5]+", false, null, true, false, NULL,true),\n"
    f = open(maps_sql  , 'a')
    f.write(ins)
    f.close()

#Writes on maps.sql ui.map_commands
def add_map_commands():
    ins = "INSERT INTO ui.map_commands(id, map_id, \"position\", \"label\", icon, \"default\", module_action_id, args, view_type_id, multiselect, show_text, item_required,  context) VALUES\n(0, 1, 0, 'LBL_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, true, true),\n(1, 1, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, false, true),\n(2, 1, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, true, true);\n\n"
    f = open(maps_sql  , 'a')
    f.write(ins)
    f.close()

#Adds ui.map_layer_element_zooms, just an example
def add_map_layer_element_zooms(line, cont):
    name = get_name(line[1])
    gi = get_graphic_icon(line[1], line[2], line[3]) 
    ins = "("+str(cont)+",  min_zoom, max_zoom, "+str(gi)+", "+str(line[4])+", "+str(line[5])+",  false, null, true, false, '#00000'), --"+name+"\n"
    f = open(maps_sql  , 'a')
    f.write(ins)
    f.close()

    
#MAIN FUNCTION
def create_first():
    global any_graphic_icon_written
    f = open(layers_csv  , 'r')
    cont = f.readlines()
    if len(cont) == 1:
        f = open(maps_sql  , 'w')
        f.close()  
    else:
        first = 'INSERT INTO ui.maps (id, name, description, "label", icon, longitude, latitude, zoom, zoom_max, zoom_min, limit_init_lon, limit_init_lat, limit_end_lon, limit_end_lat, custom_provider_url, default_zoom_search, dark_mode) VALUES\n'
        sec = '--EXAMPLE\n--(1, \'mapa\', \'mapa\', \'LBL_MAP\', NULL, -4.956077777777778, 36.46929444444445, 11, 16, 10, -6.962095, 34.525937, -2.962095, 38.525937, null, null, true);\n'
        third = '(1, \'mapa\', \'mapa\', \'LBL_MAP\', NULL, longitude, latitude, zoom, zoom_max, zoom_min, limit_init_lon, limit_init_lat, limit_end_lon, limit_end_lat, null, null, true);\n\n'

        f = open(maps_sql  , 'w')
        f.write(first)
        f.write(sec)
        f.write(third)

        map_layers = 'INSERT INTO ui.map_layers (id, base_layer, map_id, "name", description, "label", icon, visible) VALUES\n'
        f.write(map_layers)
        f.close()
        with open (layers_csv , 'r') as f:
            #Content first table
            cont = 0
            for line in f:
                if cont == 0:
                    cont = 1
                else:
                    line = line.split(';')
                    add_map_layers(line[0], cont)
                    cont = cont +1
            truncate_semicolon(maps_sql, 0)

        f = open(maps_sql  , 'a')
        relationships = 'INSERT INTO ui.map_layer_relationships  (map_id, parent_layer_id, child_layer_id) VALUES\n'
        f.write(relationships)
        f.close()
        with open (layers_csv , 'r') as f:
            #Content first table
            cont = 0
            for line in f:
                if cont == 0:
                    cont = 1
                else:
                    line = line.split(';')
                    if line[1] != '\n':
                        add_map_layer_relationships(line, cont)
                    cont = cont +1
            truncate_semicolon(maps_sql, 0)
        f.close()

    #Add graphic icons that dont exists
    f = open(elements_csv  , 'r')
    cont = f.readlines()
    if len(cont) != 1:
        with open (elements_csv , 'r') as f:
            #Content first table
            cont = 0
            for line in f:
                if cont == 0:
                    cont = 1
                else:
                    line = line.split(';')
                    add_graphic_icon(line)
        
            if any_graphic_icon_written:        
                truncate_semicolon(maps_sql, 0)
    f.close()

    
    #Add ui.map_layer_elements
    f = open(elements_csv  , 'r')
    cont = f.readlines()
    if len(cont) != 1:
        inicial = 'INSERT INTO ui.map_layer_elements (id, layer_id, element_id, graphic_icon_id, "label", longitude, latitude, horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES\n'
        f = open(maps_sql  , 'a')
        f.write(inicial)
        f.close()
        with open (elements_csv , 'r') as f:
            #Content first table
            cont = 0
            for line in f:
                if cont == 0:
                    pass
                else:
                    line = line.replace('\n','')
                    line = line.split(';')
                    add_map_layer_element(line, cont)
                cont = cont +1
            truncate_semicolon(maps_sql, 0)            
    
    add_map_commands()
    
    #Add ui.map_layer_element_zooms
    f = open(elements_csv  , 'r')
    cont = f.readlines()
    if len(cont) != 1:
        inicial = "INSERT INTO ui.map_layer_element_zooms (map_layer_element_id, zoom_lower, zoom_upper, graphic_icon_id, longitude, latitude, horizontal_flip, rotate, tooltip, show_text, text_color) VALUES\n"
        f = open(maps_sql  , 'a')
        f.write(inicial)
        f.close()
        with open (elements_csv , 'r') as f:
            cont = 0
            for line in f:
                if cont == 0:
                    pass
                else:
                    line = line.replace('\n','')
                    line = line.split(';')
                    add_map_layer_element_zooms(line, cont)
                cont = cont +1

        #Final semicolon
        for line in reversed(open(maps_sql).readlines()):
            if ',' in line:
                    line = line.replace('),' ,');')
                    break

        file = open(maps_sql, 'r')
        lines = file.readlines()
        lines = lines[:-1]
        file.close()
        file = open(maps_sql, 'w')
        file.writelines(lines)
        file.write(line)
        file.close()
    print('Done')
    

create_first()