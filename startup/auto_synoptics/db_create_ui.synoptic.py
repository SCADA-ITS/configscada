import os
import sys
import csv
import errno
import lxml.etree as ET


# constants
TRUE = 'true'
FALSE = 'false'
CURRENT_TIMESTAMP = 'CURRENT_TIMESTAMP'
NULL_VALUE = 'NULL'
NAME = sys.argv[1]
SALTO_LINEA = '\n'

cwd = os.path.dirname(os.path.abspath(__file__))
PROJECT = os.path.join(cwd, NAME)

cwdSynoptics = os.path.join(PROJECT + '/CSV/synoptics.csv')
cwdLayer = os.path.join(PROJECT + '/CSV/layer.csv')
cwdRelacion = os.path.join(PROJECT + '/CSV/relacion_tipos_subsistemas.csv')
cwdSQL = os.path.join(PROJECT + '/08.synoptics.sql')
cwdAlias = os.path.join(PROJECT + '/CSV/listado_elementos_nombre.csv')
cwdGraphic = os.path.join(PROJECT + '/CSV/graphic_icons.csv')
cwdSubsitemas = os.path.join(PROJECT + '/CSV/subsistemas.csv')
cwdProperties = os.path.join(cwd + '/../scripts/' + NAME + '/Properties')

resultado_synoptics = ''
resultado_synoptics_element_list = ''
alias_aux = []
alias = []
rutas = []

# Crear csv Listado de elementos

for nombre_directorio, dirs, ficheros in sorted(os.walk(cwdProperties)):
    for nombre_fichero in ficheros:
        if(nombre_fichero == "element_values.csv"):
            with open(os.path.join(nombre_directorio, nombre_fichero)) as f:
                reader = csv.reader(f, delimiter=';')
                cont = 0
                for line in f:
                    if cont == 0:
                        encabezado = line[:-1].split(';')
                        cont += 1
                    else:
                        parametros = line[:-1].split(';')
                        cadena_directorio = nombre_directorio.split(' ')
                        # "{:0>2d}".format(int(parametros[encabezado.index('element_subtype')])) +
                        if encabezado[1] == 'element_subtype':
                            resultado_synoptics_element_list += str(int(cadena_directorio[1])) + ';' + "{:0>2d}".format(int(
                                parametros[encabezado.index('item')])) + ";" + parametros[encabezado.index('alias')] + ';' + SALTO_LINEA
                        else:
                            resultado_synoptics_element_list += str(int(cadena_directorio[1])) + ';' + parametros[encabezado.index(
                                'item')] + ";" + parametros[encabezado.index('alias')] + ';' + SALTO_LINEA

if not os.path.exists(os.path.dirname(cwdAlias)):
    try:
        os.makedirs(os.path.dirname(cwdAlias))
    except OSError as exc:
        if exc.errno != errno.EEXIST:
            raise

with open(cwdAlias, "w+") as x:
    try:
        x.write('element_type;id;alias')
        x.write(SALTO_LINEA)
        x.write(resultado_synoptics_element_list)
        x.close()
    except Exception as e:
        print (e)

# Crear array elementos+nombre
with open(os.path.join(cwdAlias)) as c:
    contadorAlias = 0
    reader = csv.reader(c, delimiter=';')
    for line in c:
        if contadorAlias == 0:
            encabezado = line[:-1].split(';')
            contadorAlias += 1
        else:
            parametros = line[:-1].split(';')
            alias_aux.append(
                parametros[encabezado.index('element_type')])  # [0][x]
            alias_aux.append(parametros[encabezado.index('id')])  # [1][x]
            alias_aux.append(parametros[encabezado.index('alias')])  # [2][x]
            alias.append([alias_aux[0], alias_aux[1], alias_aux[2]])
            alias_aux.clear()
# Crear inser Synoptics + commmands
with open(os.path.join(cwdSynoptics)) as c:
    reader = csv.reader(c, delimiter=';')
    contadorSyn = 0
    id_commands = 0
    cwdSVG = ''
    resultado_synoptics += "INSERT INTO ui.synoptics  "
    resultado_synoptics += "(id, name, description, label, icon, base_layer, exclusive_layers) VALUES" + SALTO_LINEA

    resultado_synoptics_commands = ''
    resultado_synoptics_commands += 'INSERT INTO ui.synoptic_commands (id, synoptic_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES' + SALTO_LINEA

    for line in c:
        if contadorSyn == 0:
            encabezado = line[:-1].split(';')

        else:
            parametros = line[:-1].split(';')
            resultado_synoptics += "(" + str(contadorSyn) + ", '"
            resultado_synoptics += parametros[encabezado.index('name')] + "', '"
            resultado_synoptics += parametros[encabezado.index('description')] + "', '"
            resultado_synoptics += parametros[encabezado.index('label')] + "', "
            resultado_synoptics += NULL_VALUE + ", '"
            resultado_synoptics += 'img/synoptics/' + parametros[encabezado.index('base_layer')] + "', "
            resultado_synoptics += FALSE
            resultado_synoptics += '),\n'

            resultado_synoptics_commands += "(" + str(id_commands) + ", " + str(contadorSyn) + ", 0, "
            resultado_synoptics_commands += "'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true)," + SALTO_LINEA
            id_commands += 1
            resultado_synoptics_commands += "(" + str(id_commands) + ", " + str(contadorSyn) + ', 1, '
            resultado_synoptics_commands += "'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true)," + SALTO_LINEA
            id_commands += 1
            resultado_synoptics_commands += "(" + str(id_commands) + ", " + str(contadorSyn) + ", 2, "
            resultado_synoptics_commands += "'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true)," + SALTO_LINEA + SALTO_LINEA

            id_commands += 1

            rutas.append(parametros[encabezado.index('base_layer')])
        contadorSyn += 1
    resultado_synoptics = resultado_synoptics[:-2] + ';'
    resultado_synoptics_commands = resultado_synoptics_commands[:-3] + ';'


# Crear insert layer
with open(cwdLayer) as c:
    resultado_synoptics_layer = ''
    resultado_synoptics_layer += "INSERT INTO ui.synoptic_layers  "
    resultado_synoptics_layer += "(id, base_layer, synoptic_id, name, description, label, icon, visible, active) VALUES" + SALTO_LINEA

    reader = csv.reader(c, delimiter=';')
    contadorSubelement = 0
    for line in c:
        if contadorSubelement == 0:
            encabezado = line[:-1].split(';')

        else:
            parametros = line[:-1].split(';')
            if parametros[encabezado.index('id_element_type')] != NULL_VALUE:
                resultado_synoptics_layer += "(" + "{:0>2d}".format(int(parametros[encabezado.index('id_synoptic')])) + "{:0>2d}".format(int(
                    parametros[encabezado.index('id_subsistema')])) + "{:0>2d}".format(int(parametros[encabezado.index('id_element_type')])) + ", "
                resultado_synoptics_layer += parametros[encabezado.index(
                    'base_layer')] + ', '
                resultado_synoptics_layer += parametros[encabezado.index(
                    'id_synoptic')] + ", '"
                resultado_synoptics_layer += parametros[encabezado.index(
                    'name')] + "', '"
                resultado_synoptics_layer += parametros[encabezado.index(
                    'description')] + "', '"
                resultado_synoptics_layer += parametros[encabezado.index(
                    'label')] + "', "
                resultado_synoptics_layer += NULL_VALUE + ', '
                resultado_synoptics_layer += TRUE + ', '+ NULL_VALUE + '),' + SALTO_LINEA

            else:
                resultado_synoptics_layer += "(" + "{:0>2d}".format(int(parametros[encabezado.index(
                    'id_synoptic')])) + "{:0>2d}".format(int(parametros[encabezado.index('id_subsistema')])) + ", "
                resultado_synoptics_layer += parametros[encabezado.index(
                    'base_layer')] + ', '
                resultado_synoptics_layer += parametros[encabezado.index(
                    'id_synoptic')] + ", '"
                resultado_synoptics_layer += parametros[encabezado.index(
                    'name')] + "', '"
                resultado_synoptics_layer += parametros[encabezado.index(
                    'description')] + "', '"
                resultado_synoptics_layer += parametros[encabezado.index(
                    'label')] + "', "
                resultado_synoptics_layer += NULL_VALUE + ', '
                resultado_synoptics_layer += TRUE + ', '+ NULL_VALUE + '),' + SALTO_LINEA

        contadorSubelement += 1
resultado_synoptics_layer = resultado_synoptics_layer[:-2] + ';'
# Crear graphic icon
with open(cwdGraphic) as c:
    resultado_synoptics_graphic_icon = ''
    resultado_synoptics_graphic_icon += 'INSERT INTO ui.graphic_icons '
    resultado_synoptics_graphic_icon += '(id, "name", description, alarms, measures, js_draw_file, width, height, svg_pattern_var, svg_file, js_custom_type_render) VALUES' + SALTO_LINEA

    reader = csv.reader(c, delimiter=';')
    cont = 0
    for line in c:
        if cont == 0:
            encabezado = line[:-1].split(';')
            cont += 1
        else:
            parametros = line[:-1].split(';')
            resultado_synoptics_graphic_icon += "(" + parametros[encabezado.index('id')] + ", "
            resultado_synoptics_graphic_icon += "'" + parametros[encabezado.index('name')] + "', "
            resultado_synoptics_graphic_icon += "'" + parametros[encabezado.index('description')] + "', "
            resultado_synoptics_graphic_icon += parametros[encabezado.index('alarms')] + ", "
            resultado_synoptics_graphic_icon += parametros[encabezado.index('measures')] + ", "
            resultado_synoptics_graphic_icon += parametros[encabezado.index('js_draw_file')] + ", "
            resultado_synoptics_graphic_icon += parametros[encabezado.index('width')] + ", "
            resultado_synoptics_graphic_icon += parametros[encabezado.index('height')] + ", "
            resultado_synoptics_graphic_icon += parametros[encabezado.index('svg_pattern_var')] + ", "
            resultado_synoptics_graphic_icon += parametros[encabezado.index('svg_file')] + ", "
            resultado_synoptics_graphic_icon += parametros[encabezado.index('js_custom_type_render')] + '),' + SALTO_LINEA

resultado_synoptics_graphic_icon = resultado_synoptics_graphic_icon[:-2] + ';'
# Crear insert Relationships
with open(cwdLayer) as c:
    resultado_synoptics_layer_relationships = ''
    resultado_synoptics_layer_relationships += "INSERT INTO ui.synoptic_layer_relationships  "
    resultado_synoptics_layer_relationships += "(synoptic_id, parent_layer_id, child_layer_id) VALUES" + SALTO_LINEA

    reader = csv.reader(c, delimiter=';')
    contadorRel = 0
    relations = 0
    for line in c:
        if contadorRel == 0:
            encabezado = line[:-1].split(';')
        else:
            parametros = line[:-1].split(';')
            if parametros[encabezado.index('id_element_type')] != NULL_VALUE:
                resultado_synoptics_layer_relationships += "(" + parametros[encabezado.index('id_synoptic')] + ", "
                resultado_synoptics_layer_relationships += "{:0>2d}".format(int(parametros[encabezado.index('id_synoptic')])) + "{:0>2d}".format(int(parametros[encabezado.index('id_subsistema')])) + ", "
                resultado_synoptics_layer_relationships += "{:0>2d}".format(int(parametros[encabezado.index('id_synoptic')])) + "{:0>2d}".format(int(parametros[encabezado.index('id_subsistema')])) + "{:0>2d}".format(int(parametros[encabezado.index('id_element_type')])) + "), " + SALTO_LINEA
                relations += 1
        contadorRel += 1
    if relations != 0:
        resultado_synoptics_layer_relationships = resultado_synoptics_layer_relationships[:-3] + ';'
    else:
        resultado_synoptics_layer_relationships = ''


resultado_synoptics_element = ''
resultado_synoptics_element += "INSERT INTO ui.synoptic_layer_elements  "
resultado_synoptics_element += "(layer_id, element_id, graphic_icon_id, \"label\", x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES" + SALTO_LINEA

contadorRutas = 0
contadorSynoptics = 0
for contadorRutas in rutas:
    xml = ET.parse(os.path.join(cwd + '/' + NAME + '/SVG/' + contadorRutas))
    with open(cwdSynoptics) as s:
        for line in s:
            if contadorSynoptics == 0:
                encabezado3 = line[:-1].split(';')
                contadorSynoptics += 1
            else:
                parametros3 = line[:-1].split(';')
                contadorSynoptics += 1
                if parametros3[encabezado3.index('base_layer')] == contadorRutas:

                    id_syn = str(parametros3[encabezado3.index('id')])
                    syn_name = parametros3[encabezado3.index('name')]
                    rel_x = parametros3[encabezado3.index('x')]
                    rel_y = parametros3[encabezado3.index('y')]

    root = xml.getroot()

    i = len(root)

    resultado_synoptics_element += SALTO_LINEA + \
        '--Synoptic ' + id_syn + ' ' + syn_name + SALTO_LINEA
    while (i != 0):

        sub_type = NULL_VALUE
        i = i-1
        id = root[i].attrib['id']
        cadena_separada = id.split(':')
        if cadena_separada[0] == 'Element':
            # obtener element_type
            element_type = cadena_separada[1]
            if len(element_type.split('-')) > 1:
                element_type = element_type.split('-')
                sub_type = element_type[0]
                element_type = element_type[1]
                id = 'Element:' + element_type + ':' + cadena_separada[2]
            num_elem = cadena_separada[2]
             
            with open(cwdRelacion) as z:
                reader = csv.reader(z, delimiter=';')
                contadorRelacion = 0
                layer_id = ''
                line = line[0]
                for line in z:
                    if contadorRelacion == 0:
                        encabezado_relacion = line[:-1].split(';')
                        contadorRelacion += 1
                    else:
                        parametro_relacion = line[:-1].split(';')

                        if sub_type == NULL_VALUE:
                            if element_type == parametro_relacion[encabezado_relacion.index('element_type')]:
                                with open(cwdSubsitemas) as r:
                                    contadorSubsistema = 0
                                    for linea in r:
                                        if contadorSubsistema == 0:
                                            encabezado_subsistema1 = linea[:-1].split(';')
                                            contadorSubsistema += 1
                                        else:
                                            parametros_subsistema1 = linea[:-1].split(';')
                                            if parametro_relacion[encabezado_relacion.index('subsistema')] == parametros_subsistema1[encabezado_subsistema1.index('subsistema')] and id_syn == parametros_subsistema1[encabezado_subsistema1.index('id_synoptic')]:
                                                with open(cwdLayer) as d:
                                                    contadorLayer = 0
                                                    layer_id_elemento = ''
                                                    for line2 in d:
                                                        if contadorLayer == 0:
                                                            encabezado_layer1 = line2[:-1].split(';')
                                                            contadorLayer += 1
                                                        else:
                                                            parametros_layer1 = line2[:-1].split(';')
                                                            if element_type == parametros_layer1[encabezado_layer1.index('id_element_type')]:
                                                                layer_id_elemento = "{:0>2d}".format(int(parametros_subsistema1[encabezado_subsistema1.index('id_synoptic')])) + "{:0>2d}".format(int(parametros_subsistema1[encabezado_subsistema1.index('num_subsistema')])) + "{:0>2d}".format(int(parametro_relacion[encabezado_relacion.index('element_type')]))

                                                layer_id = "{:0>2d}".format(int(parametros_subsistema1[encabezado_subsistema1.index('id_synoptic')])) + "{:0>2d}".format(int(parametros_subsistema1[encabezado_subsistema1.index('num_subsistema')]))
                                                name_elem = ''
                                                for z in range(len(alias)):
                                                    if alias[z][0] == element_type:
                                                        if alias[z][1] == num_elem:
                                                            name_elem = "'" + alias[z][2] + "', "
                                                if name_elem == '':
                                                    name_elem += '--'
                                                if layer_id_elemento != '':
                                                    resultado_synoptics_element += '(' + layer_id_elemento + ', '
                                                else:
                                                    resultado_synoptics_element += '(' + layer_id + ', '
                                                resultado_synoptics_element += "'" + id + "', "   # element_id
                                                resultado_synoptics_element += "{:0>2d}".format(int(element_type)) + ', '
                                                resultado_synoptics_element += name_elem  # element_typ
                                                resultado_synoptics_element += str(int(float(root[i].attrib['x'])*float(rel_x))) + ', ' # y
                                                resultado_synoptics_element += str(int(float(root[i].attrib['y'])*float(rel_y))) + ', '
                                                name_elem = ''
                        else:
                            type = sub_type + element_type
                            if parametro_relacion[encabezado_relacion.index('subtype')] != 'NULL':
                                if type == "{:0>2d}".format(int(parametro_relacion[encabezado_relacion.index('subtype')])) + parametro_relacion[encabezado_relacion.index('element_type')]:
                                    with open(cwdSubsitemas) as t:
                                        contadorSubsistema = 0
                                        for linea3 in t:
                                            if contadorSubsistema == 0:
                                                encabezado_subsistema2 = linea3[:-1].split(';')
                                                contadorSubsistema += 1
                                            else:
                                                parametros_subsistema2 = linea3[:-1].split(';')
                                                if parametro_relacion[encabezado_relacion.index('subsistema')] == parametros_subsistema2[encabezado_subsistema2.index('subsistema')] and id_syn == parametros_subsistema2[encabezado_subsistema2.index('id_synoptic')]:
                                                    with open(cwdLayer) as d:
                                                        contadorLayer = 0
                                                        layer_id_elemento = ''
                                                        for line2 in d:
                                                            if contadorLayer == 0:
                                                                encabezado_layer2 = line2[:-1].split(';')
                                                                contadorLayer += 1
                                                            else:
                                                                parametros_layer2 = line2[:-1].split(';')
                                                                if element_type == parametros_layer2[encabezado_layer2.index('id_element_type')]:
                                                                    layer_id_elemento = "{:0>2d}".format(int(parametros_subsistema2[encabezado_subsistema2.index('id_synoptic')])) + "{:0>2d}".format(int(
                                                                        parametros_subsistema2[encabezado_subsistema2.index('num_subsistema')])) + "{:0>2d}".format(int(parametro_relacion[encabezado_relacion.index('element_type')]))

                                                    layer_id = "{:0>2d}".format(int(parametros_subsistema2[encabezado_subsistema2.index('id_synoptic')])) + "{:0>2d}".format(int(parametros_subsistema2[encabezado_subsistema2.index('num_subsistema')]))
                                                    name_elem = ''
                                                    for z in range(len(alias)):
                                                        if alias[z][0] == element_type:
                                                            if int(alias[z][1]) == int(num_elem):
                                                                name_elem = "'" + alias[z][2] + "'" + ', '
                                                    if name_elem == '':
                                                        name_elem += '--'
                                                    if layer_id_elemento != '':
                                                        resultado_synoptics_element +=  '(' + layer_id_elemento + ', '
                                                    else:
                                                        resultado_synoptics_element += '(' + layer_id + ', '
                                                    resultado_synoptics_element += "'" + id + "', "  # element_id
                                                    resultado_synoptics_element += sub_type + "{:0>2d}".format(int(element_type)) + ', '
                                                    resultado_synoptics_element += name_elem  # element_typ 
                                                    resultado_synoptics_element += str(int(float(root[i].attrib['x'])*float(rel_x))) + ', '# x
                                                    resultado_synoptics_element += str(int(float(root[i].attrib['y'])*float(rel_y))) + ', '# y
                                                    name_elem = ''

            resultado_synoptics_element += FALSE + ', '  # horizontal_flip
            resultado_synoptics_element += NULL_VALUE + ', '  # rotate
            resultado_synoptics_element += TRUE + ', '  # tooltip
            resultado_synoptics_element += FALSE + ', '  # show_text
            resultado_synoptics_element += NULL_VALUE + ', '  # text_color
            resultado_synoptics_element += TRUE
            resultado_synoptics_element += '),\n'                                           
resultado_synoptics_element = resultado_synoptics_element[:-2] + ';'

if not os.path.exists(os.path.dirname(cwdSQL)):
    try:
        os.makedirs(os.path.dirname(cwdSQL))
    except OSError as exc:
        if exc.errno != errno.EEXIST:
            raise

with open(cwdSQL, "w") as f:
    try:
        f.write(SALTO_LINEA + resultado_synoptics + SALTO_LINEA + SALTO_LINEA + resultado_synoptics_layer + SALTO_LINEA + SALTO_LINEA + resultado_synoptics_graphic_icon + SALTO_LINEA +
                SALTO_LINEA + resultado_synoptics_layer_relationships + SALTO_LINEA + SALTO_LINEA + resultado_synoptics_element + SALTO_LINEA + SALTO_LINEA + resultado_synoptics_commands)
        f.close()
        print("El script finalizo sin errores")
    except Exception as e:
        print (e)
