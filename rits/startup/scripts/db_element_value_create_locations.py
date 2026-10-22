# -*- coding: utf-8 -*-
'''
Created on 9 dec. 2020
@author: jcaballeroa
'''
import sys;
import os;
import csv;
import errno
from shutil import rmtree
# constants
ZERO = '0'
TRUE = 'true'
FALSE = 'false'
SALTO_LINEA = '\n'
CURRENT_TIMESTAMP = 'CURRENT_TIMESTAMP'
NULL_VALUE = 'NULL'
PARAM_TYPE_CONFIG = '1'
if len(sys.argv) < 2:
    print("Usage:")
    print("  arg1 = Name of project.")
    print("\n")
    print("Dirs:")
    print("  Config --> Path config destination")
    exit()
    
cwd = os.path.dirname(os.path.abspath(__file__));
cwdProperties = os.path.join(cwd, sys.argv[1] + '/Properties');
cwdConfig = os.path.join(cwd, sys.argv[1] + '/Config/');
cwdMaster = cwd + '/../../ritsback/resources/db/dml/master';
cwdElements = os.path.join(cwd, sys.argv[1] + '/Properties/elements/elements.csv');
# Borrar Config
# rmtree(cwdConfig)
# Extraer nombre fichero
nameElementsList = [];
contName = 0;
with open(os.path.join(cwdElements)) as f:
    reader = csv.reader(f, delimiter=';')
    for line in reader:
        if (contName == 0):
               encabezado = line 
                
        else:
            
            parametros = line
            nameElementsList.append(parametros[encabezado.index('element')])
            
        contName += 1;
        
# Inserts for roads
masterRoadDirectory = cwdMaster + '/08_roads';
roadDirectory = cwdProperties + '/roads';
roads = os.listdir(roadDirectory);
roadsList = [];
roadsAliasList = [];
with open(os.path.join(masterRoadDirectory, '01.roads.sql')) as f:
    for line in f:
        if (line[0] == '('):
            roadsList.append(line.split(',')[1].strip().replace("'", ""));
        elif(line == '-- Table: master.road_impacts;\n'):
            break;
for nombre_fichero in roads:
    if(nombre_fichero == "elements.csv"):
        with open(os.path.join(roadDirectory, nombre_fichero)) as f:
            resultado_roads = '';
            resultado_roads += "INSERT INTO conf.roads ";
            resultado_roads += "(road_id, road_type_id, alias, description) VALUES \n";
            
            contadorRoads = 0;
            for line in f:
                if contadorRoads == 0:
                    encabezado = line[:-1].split(';')
                else:  
                    parametros = line[:-1].split(';') 
                    resultado_roads += "(" + parametros[encabezado.index('item')] + ', '  # road_id
                    resultado_roads += str(roadsList.index(line.split(';')[1]) + 1) + ', '  # road_type_id
                    roadsAliasList.append(parametros[encabezado.index('alias')]);
                    resultado_roads += "'" + parametros[encabezado.index('alias')] + "', "  # alias
                    resultado_roads += "'" + parametros[encabezado.index('alias')] + "'),\n"  # description
                contadorRoads += 1;
resultado_roads = resultado_roads[:-2] + ';';
destination = os.path.join(cwdConfig + 'roads/roads.sql');
if not os.path.exists(os.path.dirname(destination)):
    try:
        os.makedirs(os.path.dirname(destination))
    except OSError as exc: 
        if exc.errno != errno.EEXIST:
            raise
with open(destination, "w+") as f:
    f.write(resultado_roads);
    f.close();
    
print("Creado fichero roads.sql");
# Inserts for stretchs
masterStretchsDirectory = cwdMaster + '/08_roads';
stretchsDirectory = cwdProperties + '/stretchs';
stretchs = os.listdir(stretchsDirectory);
stretchsList = [];
stretchsAliasList = {
    "alias":[],
    "id":[]
}
stretchsLocAlias = [];
stretchsSecc = [];
stretchsSeccItem = [];
with open(os.path.join(masterStretchsDirectory, '02.stretchs.sql')) as f:
    for line in f:
        if (line[0] == '('):
            stretchsList.append(line.split(',')[1].strip().replace("'", ""));
for nombre_fichero in stretchs:
    if(nombre_fichero == "elements.csv"):
        with open(os.path.join(stretchsDirectory, nombre_fichero)) as f:
            resultado_stretchs = '';
            resultado_stretchs += "INSERT INTO conf.stretchs ";
            resultado_stretchs += "(stretch_id, stretch_type_id, alias, description, pk_ini, pk_end, lanes, road_id, enabled, visible, created_at, updated_at)VALUES\n";
            
            contadorStretchs = 0;
            for line in f:
                if contadorStretchs == 0:
                    encabezado = line[:-1].split(';')
                else:  
                    parametros = line[:-1].split(';') 
                    resultado_stretchs += "(" + parametros[encabezado.index('item')] + ', '  # stretch_id
                    resultado_stretchs += str(stretchsList.index(line.split(';')[1]) + 1) + ', '  # stretch_type_id
                    stretchsAliasList["alias"].append(parametros[encabezado.index('alias')]);
                    stretchsAliasList["id"].append(parametros[encabezado.index('item')]);
                    stretchsLocAlias.append(parametros[encabezado.index('alias')].replace("'", "")) 
                    resultado_stretchs += "'" + parametros[encabezado.index('alias')] + "', "  # alias
                    if (sys.argv[1] == "Avo"):
                        resultado_stretchs += "'" + parametros[encabezado.index('alias')] + "', "  # description
                    else:
                        resultado_stretchs += "'" + parametros[encabezado.index('description')] + "', "  # description
                    resultado_stretchs += ZERO + ', '  # pk_ini
                    resultado_stretchs += ZERO + ', '  # pk_end
                    resultado_stretchs += ZERO + ', '  # lanes
                    resultado_stretchs += str(roadsAliasList.index(parametros[encabezado.index('road')]) + 1) + ', '  # road_id
                    resultado_stretchs += TRUE + ', '  # enabled
                    resultado_stretchs += TRUE + ', '  # visible
                    resultado_stretchs += CURRENT_TIMESTAMP + ', ' # created_at
                    resultado_stretchs += CURRENT_TIMESTAMP + '),\n'  # updated_at
                    stretchsSecc.append(parametros[encabezado.index('alias')])
                    stretchsSeccItem.append(parametros[encabezado.index('item')])
                    
                contadorStretchs += 1;
resultado_stretchs = resultado_stretchs[:-2] + ';';
destination = os.path.join(cwdConfig + 'stretchs/stretchs.sql');
if not os.path.exists(os.path.dirname(destination)):
    try:
        os.makedirs(os.path.dirname(destination))
    except OSError as exc: 
        if exc.errno != errno.EEXIST:
            raise
with open(destination, "w+") as f:
    f.write(resultado_stretchs);
    f.close();
    
print("Creado fichero stretchs.sql");
# loadLocations
locationsList = [];
locationsListStretch = [];
stretchsLoc = [];
locationsDirectory = cwdProperties + '/locations/';
for nombre_directorio, dirs, ficheros in sorted(os.walk(cwdProperties)):
    for nombre_fichero in ficheros:
        if(nombre_fichero == "element_values.csv"):
            with open(os.path.join(nombre_directorio, nombre_fichero)) as f:
                reader = csv.reader(f, delimiter=';')
                resultado_elements = '';
                contadorElements = 0;
                for line in reader:
                    if contadorElements == 0:
                        encabezado = line

                    else:  
                        parametros = line
                        locationsList.append(str(parametros[encabezado.index('item')].rjust(3, '0')) + ("%04d" % (int(nombre_directorio.split("Type")[1])))) 
                        if (sys.argv[1] == "Avo"):
                            locationsListStretch.append(str(' pk ' + parametros[encabezado.index('pk')] + ' dm ' + parametros[encabezado.index('dm')]))
                        else:
                            locationsListStretch.append(str(' pk ' + parametros[encabezado.index('pk')] ))
                
                        stretchsLoc.append(str(parametros[encabezado.index('stretchs')]))
                    contadorElements += 1               

# Inserts for elements
lane = []
resultado_stretch_locations = '';
resultado_stretch_locations += "INSERT INTO conf.stretch_locations ";
resultado_stretch_locations += "(stretch_id, location_id, position, enabled, visible, created_at, updated_at) VALUES\n"; 
resultado_stretch_locations_secc = '';
resultado_stretch_locations_secc += '\n\n-- SECCIONES\n\n' ;
resultado_locations = '';
resultado_locations += "INSERT INTO conf.locations ";
resultado_locations += "(location_id, location_type_id, alias, description, lane, enabled, visible, created_at, updated_at) VALUES\n";                   
for nombre_directorio, dirs, ficheros in sorted(os.walk(cwdProperties)):
    for nombre_fichero in ficheros:
        if(nombre_fichero == "element_values.csv"):
            with open(os.path.join(nombre_directorio, nombre_fichero)) as f:
                reader = csv.reader(f, delimiter=';')
                resultado_elements = '';
                resultado_elements += "INSERT INTO conf.elements ";
                resultado_elements += "(element_type_id, element_subtype_id, element_id, location_id, keep_values_on_disconnected, inherit_state_id, alias, description, on_change_alarm, on_change_state, on_change_measure, on_schedule, enabled, visible, alert, alarm_count, created_at, updated_at) VALUES\n";
                
                resultado_elements_values = '';
                resultado_elements_values += "INSERT INTO conf.element_values ";
                resultado_elements_values += "(element_type_id, element_type_param_id, param_type_id, element_id, element_value_state_id, value, enabled, visible, created_at, updated_at) VALUES\n";
                
                resultado_elements_io_controller = '';
                resultado_elements_io_controller += "INSERT INTO conf.element_io_controller_modules ";
                resultado_elements_io_controller += "(element_type_id, element_id, module_type_id, module_id, enabled, visible, created_at, updated_at) VALUES\n";
                       
                contadorElements = 0;
                contadorEntradas = 0
                for line in reader:
                    if contadorElements == 0:
                        encabezado = line
                        encabezado.append('1003')
                    else:  
                        parametros = line
                        if (sys.argv[1] == "Avo"):
                            stretch = ''
                            stretchSecc = ''
                            
                            for  z in range(2):
                                if (z == 0):
                                    resultado_stretch_locations += "(" +  str(stretchsLocAlias.index(stretch)+1) + ', '  # stretch_id
                                    stretch = stretchsLoc[locationsList.index(str( parametros[encabezado.index('item')].rjust(3, '0')) + ("%04d" % (int(nombre_directorio.split("Type")[1]))))]+ ', '  # location_id
                                    stretch = stretch.replace(",","") 
                                    if stretch != '' or str.contains(" ")  :
                                        stretch = stretch.replace(" ","") 
                                        resultado_stretch_locations += str(parametros[encabezado.index('item')].rjust(3, '0') + ("%04d" % (int(nombre_directorio.split("Type")[1])))) + ', '
                                    else:
                                        resultado_stretch_locations += str(parametros[encabezado.index('item')].rjust(3, '0') + ("%04d" % (int(nombre_directorio.split("Type")[1])))) + ', '
                                else:
                                    stretchSecc = stretchsSeccItem[stretchsSecc.index(str(parametros[encabezado.index('secc')]))] + ', '  # location_id
                                    if (str(stretchSecc) == '201') :
                                        resultado_stretch_locations_secc += "-- (" +  stretchSecc + ', '  # stretch_id
                                        resultado_stretch_locations_secc += str(parametros[encabezado.index('item')].rjust(3, '0') + ("%04d" % (int(nombre_directorio.split("Type")[1]))))
                                        resultado_stretch_locations_secc += '1, '  # position
                                        resultado_stretch_locations_secc += TRUE + ', '  # enabled
                                        resultado_stretch_locations_secc += TRUE + ', '  # visible
                                        resultado_stretch_locations_secc += CURRENT_TIMESTAMP + ', '  # created_at
                                        resultado_stretch_locations_secc += CURRENT_TIMESTAMP + '), '  # updated_at
                                        resultado_stretch_locations_secc += SALTO_LINEA 
                                    else:
                                        resultado_stretch_locations_secc += "(" +  stretchSecc   # stretch_id
                                        resultado_stretch_locations_secc += str(parametros[encabezado.index('item')].rjust(3, '0') + ("%04d" % (int(nombre_directorio.split("Type")[1]))))
                                        resultado_stretch_locations_secc += '1, '  # position
                                        resultado_stretch_locations_secc += TRUE + ', '  # enabled
                                        resultado_stretch_locations_secc += TRUE + ', '  # visible
                                        resultado_stretch_locations_secc += CURRENT_TIMESTAMP + ', '  # created_at
                                        resultado_stretch_locations_secc += CURRENT_TIMESTAMP + '), '  # updated_at
                                        resultado_stretch_locations_secc += SALTO_LINEA 
                            resultado_stretch_locations += '1, '  # position
                            resultado_stretch_locations += TRUE + ', '  # enabled
                            resultado_stretch_locations += TRUE + ', '  # visible
                            resultado_stretch_locations += CURRENT_TIMESTAMP + ', '  # created_at
                            resultado_stretch_locations += CURRENT_TIMESTAMP + '), '  # updated_at
                            resultado_stretch_locations += SALTO_LINEA
                        else:
                            for  z in range(2):
                                if (z == 0):
                                    stretch = stretchsLoc[locationsList.index(str( parametros[encabezado.index('item')].rjust(3, '0')) + ("%04d" % (int(nombre_directorio.split("Type")[1]))))]+ ', '  
                                    stretch = stretch.replace(",","") 
                                    if stretch != '' or str.contains(" ")  :
                                        stretch = stretch.replace(" ","") 
                                        for i in range(len(stretchsAliasList["alias"])):
                                            if(stretchsAliasList["alias"][i] == parametros[encabezado.index('stretchs')]):
                                                resultado_stretch_locations += "(" + str(stretchsAliasList["id"][i]) + ', '  # stretch_id
                                    else:
                                        resultado_stretch_locations += "(" + str(stretchsLocAlias.index(stretch)+1) + ', '
                                    resultado_stretch_locations += str(parametros[encabezado.index('item')].rjust(3, '0') + ("%04d" % (int(nombre_directorio.split("Type")[1]))))  + ', '  # location_id
                                else:
                                    if (parametros[encabezado.index('stretchs')] != ''):
                                        stretchSecc = stretchsSeccItem[stretchsSecc.index(str(parametros[encabezado.index('stretchs')]))] + ', '  # location_id
                                        if (sys.argv[1] == "Avo"):
                                            if (str(stretchSecc) == '201') :
                                                resultado_stretch_locations_secc += "-- (" +  stretchSecc + ', '  # stretch_id
                                                resultado_stretch_locations_secc += str(parametros[encabezado.index('item')].rjust(3, '0') + ("%04d" % (int(nombre_directorio.split("Type")[1]))))
                                                resultado_stretch_locations_secc += '1, '  # position
                                                resultado_stretch_locations_secc += TRUE + ', '  # enabled
                                                resultado_stretch_locations_secc += TRUE + ', '  # visible
                                                resultado_stretch_locations_secc += CURRENT_TIMESTAMP + ', '  # created_at
                                                resultado_stretch_locations_secc += CURRENT_TIMESTAMP + '), '  # updated_at
                                                resultado_stretch_locations_secc += SALTO_LINEA 
                                            else:
                                                resultado_stretch_locations_secc += "(" + stretchSecc  + ', '  # stretch_id
                                                resultado_stretch_locations_secc += str(parametros[encabezado.index('item')].rjust(3, '0') + ("%04d" % (int(nombre_directorio.split("Type")[1]))))
                                                resultado_stretch_locations_secc += '1, '  # position
                                                resultado_stretch_locations_secc += TRUE + ', '  # enabled
                                                resultado_stretch_locations_secc += TRUE + ', '  # visible
                                                resultado_stretch_locations_secc += CURRENT_TIMESTAMP + ', '  # created_at
                                                resultado_stretch_locations_secc += CURRENT_TIMESTAMP + '), '  # updated_at
                                                resultado_stretch_locations_secc += SALTO_LINEA 

                            resultado_stretch_locations += '1, '  # position
                            resultado_stretch_locations += TRUE + ', '  # enabled
                            resultado_stretch_locations += TRUE + ', '  # visible
                            resultado_stretch_locations += CURRENT_TIMESTAMP + ', '  # created_at
                            resultado_stretch_locations += CURRENT_TIMESTAMP + '), '  # updated_at
                            resultado_stretch_locations += SALTO_LINEA
                        resultado_elements += '(' + str(int(nombre_directorio.split("Type")[1])) + ', '  # element_type_id
                                         
                        try:
                            resultado_elements += parametros[encabezado.index('element_subtype')] + ', '  # element_subtype_id
                        except ValueError:
                            resultado_elements += NULL_VALUE + ', '  # element_subtype_id
                        resultado_elements += parametros[encabezado.index('item')] + ', '  # element_id
                        resultado_elements += str( parametros[encabezado.index('item')].rjust(3, '0') + ("%04d" % (int(nombre_directorio.split("Type")[1])))) +', '                           # str(locationsListStretch.index(new_location) + 1) + ', '  # location_id
                        resultado_elements += NULL_VALUE + ', '  # keep_values_on_disconnected
                        
                        try:
                            resultado_elements += parametros[encabezado.index('inherit_state_id')] + ', '  # inherit_state_id
                        except ValueError:
                            resultado_elements += NULL_VALUE + ', '  # inherit_state_id
                        
                        resultado_elements += "'" + parametros[encabezado.index('alias')] + "', "  # alias
                        if 'description' not in encabezado:
                            resultado_elements += "'" + parametros[encabezado.index('alias')] + "', "  # description         
                                                                  # 
                        else:
                            resultado_elements += "'" + parametros[encabezado.index('description')] + "', "  # description   
                        
                        try:
                            if (parametros[encabezado.index('lane')] == ''):
                                lane.append(NULL_VALUE)  # on_change_alarm
                            else:
                                lane.append(str(parametros[encabezado.index('lane')]))  # on_change_alarm
                        except ValueError:
                            lane.append(NULL_VALUE) # on_change_alarm
                        
                        try:
                            if (parametros[encabezado.index('on_change_alarm')] == ''):
                                resultado_elements += NULL_VALUE + ', '  # on_change_alarm
                            else:
                                resultado_elements += "'" + parametros[encabezado.index('on_change_alarm')] + "', "  # on_change_alarm
                        except ValueError:
                            resultado_elements += NULL_VALUE + ', '  # on_change_alarm
                        
                        try:
                            if (parametros[encabezado.index('on_change_state')] == ''):
                                resultado_elements += NULL_VALUE + ', '  # on_change_state
                            else:
                                resultado_elements += "'" + parametros[encabezado.index('on_change_state')] + "', "  # on_change_state
                        except ValueError:
                            resultado_elements += NULL_VALUE + ', '  # on_change_state
                                  
                        try:
                            if (parametros[encabezado.index('on_change_measure')] == ''):
                                resultado_elements += NULL_VALUE + ', '  # on_change_measure
                            else:
                                resultado_elements += "'" + parametros[encabezado.index('on_change_measure')] + "', "  # on_change_measure
                        except ValueError:
                            resultado_elements += NULL_VALUE + ', '  # on_change_measure
                        
                        try:
                            if (parametros[encabezado.index('on_schedule')] == ''):
                                resultado_elements += NULL_VALUE + ', '  # on_schedule
                            else:
                                resultado_elements += "'" + parametros[encabezado.index('on_schedule')] + "', "  # on_schedule
                        except ValueError:
                            resultado_elements += NULL_VALUE + ', '  # on_schedule
                            
                        resultado_elements += TRUE + ', '  # enabled
                        resultado_elements += TRUE + ', '  # visible
                        resultado_elements += FALSE + ', '  # alert
                        resultado_elements += ZERO + ', '  # alarm_count
                        resultado_elements += CURRENT_TIMESTAMP + ', '  # created_at
                        resultado_elements += CURRENT_TIMESTAMP + '), '  # updated_at 
                        resultado_elements += SALTO_LINEA
                        
                        # io_controller
                        resultado_elements_io_controller += '(' + str(int(nombre_directorio.split("Type")[1])) + ', '  # element_type_id
                        resultado_elements_io_controller += parametros[encabezado.index('item')] + ', '  # element_id
                        resultado_elements_io_controller += '1' + ', '  # module_type
                        resultado_elements_io_controller += '1' + ', '  # module_id
                        resultado_elements_io_controller += TRUE + ', '  # enabled
                        resultado_elements_io_controller += TRUE + ', '  # visible
                        resultado_elements_io_controller += CURRENT_TIMESTAMP + ', '  # created_at
                        resultado_elements_io_controller += CURRENT_TIMESTAMP + '), '  # updated_at         
                        resultado_elements_io_controller += SALTO_LINEA       
                        
                        contParams = 0
                        for param in encabezado:
                            if(param.split("_")[0][0:5] == "param" or param == '1003'):
                                if (param == '1003' and nombre_directorio.split("Type")[1].strip() == '04'):
                                    continue
                                resultado_aux = '(' + str(int(nombre_directorio.split("Type")[1])) + ', '  # element_type_id     
                                if(param != '1003'):    
                                    resultado_aux += param.split("_")[0][5:] + ', '  # element_type_param_id
                                else:
                                    resultado_aux += '1003, '
                                resultado_aux += PARAM_TYPE_CONFIG + ', '  # param_type_id
                                resultado_aux += parametros[encabezado.index('item')] + ', '  # element_id
                                resultado_aux += NULL_VALUE + ', '  # element_value_state_id
                                if(param != '1003'):
                                    resultado_aux += "'" + parametros[contParams] + "', "  # value
                                else:
                                    resultado_aux += "'" + parametros[encabezado.index('sessionuid')] + "', "  # value
                                resultado_aux += TRUE + ', '  # enabled
                                resultado_aux += TRUE + ', '  # visible
                                resultado_aux += CURRENT_TIMESTAMP + ', '  # created_at
                                resultado_aux += CURRENT_TIMESTAMP + '), '  # updated_at         
                                resultado_aux += '\n'  
                                
                                if(param != '1003'):
                                    if parametros[contParams] != '':
                                        resultado_elements_values += resultado_aux; 
                                else:
                                    resultado_elements_values += resultado_aux;
                                    
                            contParams += 1
                        resultado_elements_values += SALTO_LINEA
                    contadorElements += 1  
            resultado_elements = resultado_elements[:-3] + ';'; 
            resultado_elements_values = resultado_elements_values[:-4] + ';';                         
            resultado_elements_io_controller = resultado_elements_io_controller[:-3] + ';'; 
            
            destination = os.path.join(cwdConfig + 'elements/' + nombre_directorio.split("Type")[1].strip() + '.' + nameElementsList[(int(nombre_directorio.split("Type")[1])) - 1] + '.sql');
            # + nombre_directorio.split("Type")[1] + '/elements.sql'
    
            if not os.path.exists(os.path.dirname(destination)):
                try:
                    os.makedirs(os.path.dirname(destination))
                except OSError as exc: 
                    if exc.errno != errno.EEXIST:
                        raise
            
            with open(destination, "w") as f:
                f.write(resultado_elements + "\n" + "\n" + resultado_elements_values + "\n" + "\n" + resultado_elements_io_controller);
                f.close();
                
            print('Creado fichero /Type' + nombre_directorio.split("Type")[1] + '/elements.sql');    
                 
contadorLoc = 0
for location in locationsList:
    resultado_locations += '(' + location + ', '  # location_id 
    if (sys.argv[1] == "Avo"):
        resultado_locations += '2, '  # location_type_id 
    else:
        resultado_locations += '1, '  # location_type_id 
    resultado_locations += "'" + locationsListStretch[contadorLoc] + "', "  # alias
    resultado_locations += "'" + locationsListStretch[contadorLoc] + "', "  # description
    resultado_locations += lane[contadorLoc] + ", "  # on_change_alarm
    resultado_locations += TRUE + ', '  # enabled
    resultado_locations += TRUE + ', '  # visible
    resultado_locations += CURRENT_TIMESTAMP + ', '  # created_at
    resultado_locations += CURRENT_TIMESTAMP + '), '  # updated_at
    resultado_locations += SALTO_LINEA
    contadorLoc += 1 
destination = os.path.join(cwdConfig + 'locations/locations.sql');
if not os.path.exists(os.path.dirname(destination)):
    try:
        os.makedirs(os.path.dirname(destination))
    except OSError as exc: 
        if exc.errno != errno.EEXIST:
            raise
with open(destination, "w") as f:
    f.write(resultado_locations[:-3] + ';');
    f.close();
    
print("Creado fichero locations.sql");    
resultado_locations_csv = "location_id;alias\n"
index = 1
for location in locationsListStretch:
    resultado_locations_csv += str(index) + ";" + location + "\n"
    index += 1
destination = os.path.join(cwdProperties + '/locations/locations.csv');
if not os.path.exists(os.path.dirname(destination)):
    try:
        os.makedirs(os.path.dirname(destination))
    except OSError as exc: 
        if exc.errno != errno.EEXIST:
            raise
with open(destination, "w") as f:
    f.write(resultado_locations_csv);
    f.close();
    
print("Creado fichero locations.csv"); 

resultado_stretch_locations += resultado_stretch_locations_secc    
destination = os.path.join(cwdConfig + 'stretchs/stretchs_locations.sql');
if not os.path.exists(os.path.dirname(destination)):
    try:
        os.makedirs(os.path.dirname(destination))
    except OSError as exc: 
        if exc.errno != errno.EEXIST:
            raise
with open(destination, "w") as f:
    f.write(resultado_stretch_locations[:-3] + ';');
    f.close();
    
print("Creado fichero stretchs_locations.sql");   
