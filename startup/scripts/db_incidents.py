# -*- coding: utf-8 -*-

'''
Created on 5 oct. 2022

@author: jcaballeroa
'''

import os
import sys

if len(sys.argv) < 1:
    print("Argumentos necesarios: ")
    print("1.Nombre del proyecto")
    exit()

PROYECTO = sys.argv[1]
INCIDENT_STRECTH_TYPE = 2
ZERO = 0
UNO = 1

cwd = os.path.dirname(os.path.abspath(__file__))
incidentDirectory = os.path.join(cwd + '/' + PROYECTO + '/Properties/ims/')

detailFile = os.path.join(incidentDirectory + 'detail.csv')

stretchList = []
roadsList = []
roads = ['D4', 'R7', 'D2'];
stretchResult = 'INSERT INTO conf.stretchs (stretch_id, stretch_type_id, alias, description, pk_ini, pk_end, lanes, road_id, enabled, visible, created_at, updated_at)VALUES\n'

with open(os.path.join(detailFile)) as f:    
    contadorRoads = 0;
    for line in f:
        if contadorRoads == 0:
            encabezado = line[:-1].split(';')
        else:  
            parametros = line[:-1].split(';') 
            if(parametros[encabezado.index('Element Detection Name')] + '_' + parametros[encabezado.index('PK')]) not in stretchList:
                stretchList.append(parametros[encabezado.index('Element Detection Name')] + '_' + parametros[encabezado.index('PK')])
                roadsList.append(parametros[encabezado.index('Road')])
        contadorRoads += 1;      
        
idStretchIncident = 1000;        
    
rowid = 0;
for row in stretchList:
    stretchResult += '(' + str(idStretchIncident) + ', '                #strecth_id
    stretchResult += str(INCIDENT_STRECTH_TYPE) + ', '                  #stretch_type_id
    stretchResult += "'" + row + "'" +', '                              #alias
    stretchResult += "'" + row + "'" +', '                              #description
    stretchResult += str(ZERO) + ', '                                   #pk_ini
    stretchResult += str(ZERO) + ', '                                   #pk_end
    stretchResult += str(ZERO) + ', '                                   #lanes
    stretchResult += str(roads.index(roadsList[rowid]) + 1) + ', '      #road_id
    stretchResult += 'true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),'     
    stretchResult += '\n'     
    idStretchIncident+=1
    rowid+=1
    
stretchResult=stretchResult[:-2] + ';'    
    
print(stretchResult)   

destination = os.path.join(cwd + '/' + PROYECTO + '/Properties/ims/02.stretchs.sql');                    
if not os.path.exists(os.path.dirname(destination)):
    try:
        os.makedirs(os.path.dirname(destination))
    except OSError as exc: 
        if exc.errno != errno.EEXIST:
            raise
with open(destination, "w+") as f:
    f.write(stretchResult);
    f.close();
    
print("Creado fichero stretchs.sql");         


taskResult = 'INSERT INTO conf.ims_incident_type_task_values(task_type_id, task_type_param_id, incident_type_task_id, value, enabled, visible, created_at, updated_at)VALUES\n'
taskResult += '(' + str(UNO) + ', '          #task_type_id
taskResult += str(UNO) + ', '                #task_type_param_id
print(taskResult);      
                    