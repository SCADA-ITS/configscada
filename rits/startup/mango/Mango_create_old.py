# -*- coding: utf-8 -*-
'''
Created on 4 may. 2020
@author: jcaballeroa
'''
import sys
import os
import json
from collections import OrderedDict
if len(sys.argv) < 2:
    print("Usage:")
    print("  arg1 = Name of Proyect")
    print("  arg2 (optional) = Name of Individual Element")
    print("\n\n")
    print("Dirs:")
    print("  csvs_proyecto/Properties --> Path with element parameters")
    print("  csvs_proyecto/Config --> Path config destination")
    exit()
def is_number(s):
    try:
        float(s)
        return True
    except ValueError:
        return False
def getJsonKineo(resultado,list,listxid):
    json_result = json.loads(resultado)
    counter = 0;
    for listI in list:         
        datapoints = json_result['dataPoints']
        contDataPoint = 0;
        for datapoint in datapoints:
                contDataPoint = contDataPoint + 1;
                if  'name'  in datapoint:   
                    if "etd_info" == datapoint['name'] and datapoint['xid'] == listxid[counter]:
                        pointsLocator = datapoint['pointLocator']
                        context = pointsLocator['context']
                        for datapointContext in listI:
                            if datapointContext not in context:
                                context.append(datapointContext)
                        json_result['dataPoints'][contDataPoint - 1]['pointLocator']['context'] = context
                    if "fuera_comunicaciones" == datapoint['name'] and datapoint['xid'] == listxid[counter]:
                        pointsLocator = datapoint['pointLocator']
                        context = pointsLocator['context']
                        for datapointContext in listI:
                            if datapointContext not in context:
                                context.append(datapointContext)
                        json_result['dataPoints'][contDataPoint - 1]['pointLocator']['context'] = context
        counter = counter + 1                 
    datapoints = json_result['dataPoints']
    
    
    seen = []
    newDatapoint = []
    for datapoint in datapoints:
        if datapoint['xid'] not in seen:
            seen.append(datapoint['xid']) 
            newDatapoint.append(datapoint)
                    
    json_result['dataPoints'] = newDatapoint
    resultado = json.dumps(json_result,indent=2)
    return resultado
def getJsonETD(resultado,list,listxid):   
    json_result = json.loads(resultado)
    counter = 0;
    for listI in list:         
        datapoints = json_result['dataPoints']
        for datapoint in datapoints:
                if  'name'  in datapoint:    
                    if "etd_info" == datapoint['name'] and datapoint['xid'] == listxid[counter]:
                        pointsLocator = datapoint['pointLocator']
                        context = pointsLocator['context']  
                        for datapointContext in listI:
                            if datapointContext not in context:
                                context.append(datapointContext)    
        counter = counter + 1                 
            
    datapoints = json_result['dataPoints']
    
    
    seen = []
    newDatapoint = []
    for datapoint in datapoints:
        if datapoint['xid'] not in seen:
            seen.append(datapoint['xid']) 
            newDatapoint.append(datapoint)
                    
    json_result['dataPoints'] = newDatapoint
    resultado = json.dumps(json_result,indent=2)
    return resultado
def getJsonTFA_3D(resultado,list,listxid):   
    json_result = json.loads(resultado)
    counter = 0;
    for listI in list:         
        datapoints = json_result['dataPoints']
        contDataPoint = 0;
        for datapoint in datapoints:
                contDataPoint = contDataPoint + 1;
                if  'name'  in datapoint:    
                    if "etd_info" == datapoint['name'] and datapoint['xid'] == listxid[counter]:
                        pointsLocator = datapoint['pointLocator']
                        context = pointsLocator['context']
                        for datapointContext in listI:
                            if datapointContext not in context:
                                context.append(datapointContext)
                        json_result['dataPoints'][contDataPoint - 1]['pointLocator']['context'] = context
        counter = counter + 1                 
            
    datapoints = json_result['dataPoints']
    
    
    seen = []
    newDatapoint = []
    for datapoint in datapoints:
        if datapoint['xid'] not in seen:
            seen.append(datapoint['xid']) 
            newDatapoint.append(datapoint)
                    
    json_result['dataPoints'] = newDatapoint
    resultado = json.dumps(json_result,indent=2)
    return resultado

def createElement(project_name,element_name):
    source = open(os.path.join(dirname, "esqueletos_json/" + project_name + "/" + element_name + ".json"));
    f_original = source.read()
    f_final = f_original
    source.close()
    
    json_string = json.loads(f_original, object_pairs_hook=OrderedDict)
    json_keys = json_string.keys()
    
    resultado = "{\n  " 
    list = []
    listxid = []
    
    for key in json_keys:
        font = open(os.path.join(dirname, "csvs_proyecto/" + project_name + "/Properties/" + element_name + ".csv"));
        leida = font.readline()
        clave = leida.split(";")
        
        resultado += "\"" + key + "\": ["
        json_formatted_str = json.dumps(json_string[key], indent=2)
        f_final = json_formatted_str[1:-1]
       
        for line in font:
            valor = line.split(";")
            if(len(sys.argv) > 3):
              if (valor[clave.index("device_name")] != sys.argv[3]):
                continue
    
            for element in clave:
                if('(i)' in element):
                    f_final = f_final.replace('"{' + element.replace("(i)",'').replace("\n", '') + '}"', valor[clave.index(element)].replace("\n", ''))
                else:
                    f_final = f_final.replace('{' + element.replace("\n", '') + '}', valor[clave.index(element)].replace("\n", ''))
            
                if (element_name == "ETD_VIRTUAL" or element_name == "ETD") and key == "dataPoints" and element == "det_order":
                    pos_ini_offset = 1
                    while pos_ini_offset > 0 :
                        pos_ini_offset = f_final.find("offset",pos_ini_offset+1,-1)
                        if pos_ini_offset < 0 :
                            break;
                        pos_fin_offset = f_final[pos_ini_offset:-1].find(",")
                        val_offset = f_final[pos_ini_offset+9:pos_ini_offset+pos_fin_offset]
                        int_val_offset = int(val_offset) + ((int(valor[clave.index(element)]) - 1 ) *100)
                        f_final = f_final[:pos_ini_offset+9] + str(int_val_offset) + f_final[pos_ini_offset+pos_fin_offset:]
            
            
            resultado += f_final[:len(f_final)-1] + ','
            f_final = json_formatted_str[1:-1]
            
            
        resultado = resultado[:-1] + "\n  ],"
        font.close()
        
    resultado = resultado[:len(resultado)-1] + "\n}"
    
    json_resultado = json.loads(resultado, object_pairs_hook=OrderedDict)
    json_keys2 = json_resultado.keys()
    
    contador = 0
    cont_ini = 0
    est = OrderedDict()
    
    
                        
                
    for key2 in json_keys2:       
        if key2=="dataPoints":     
            for elemento in json_resultado[key2]:   
                if("etd_info" in json_resultado[key2][contador]['xid']):#ETD
                    list.append(json_resultado[key2][contador]['pointLocator']['context']) 
                    listxid.append(json_resultado[key2][contador]['xid']) 
                if("json" in json_resultado[key2][contador]['xid']):#ETD_KINEO
                    list.append(json_resultado[key2][contador]['pointLocator']['context']) 
                    listxid.append(json_resultado[key2][contador]['xid'])
                if("fuera_comunicaciones" in json_resultado[key2][contador]['xid']):#ETD_KINEO
                    list.append(json_resultado[key2][contador]['pointLocator']['context']) 
                    listxid.append(json_resultado[key2][contador]['xid']) 
                contador = contador + 1;
        # Remove duplicates 
        seen = []
        for elemento in json_resultado[key2]:
            if elemento not in seen:
                seen.append(elemento)
        json_resultado[key2] = seen;
                    
    
    resultado=json.dumps(json_resultado, indent=4)          
    
    
    if (element_name == "ETD_VIRTUAL" or element_name == "ETD"):    
        resultado = getJsonETD(resultado,list,listxid)
    
    if (element_name == "TFA_3D"):    
        resultado = getJsonTFA_3D(resultado,list,listxid)
    
    if (element_name == "ETD_KINEO_VIRTUAL" or element_name == "ETD_KINEO" or element_name == "ETD_KINEO_TT"):
        resultado = getJsonKineo(resultado,list,listxid)

    if(len(sys.argv) > 3):
        destination = os.path.join(dirname, "csvs_proyecto/" + project_name + "/Config/" + element_name + "/" + sys.argv[3] +".json")
        print("Creando fichero: " + sys.argv[3] +".json")
    else:
        destination = os.path.join(dirname, "csvs_proyecto/" + project_name + "/Config/" + element_name + "/" + element_name + ".json")
        print("Creando fichero: " + element_name + ".json")
    
    if not os.path.exists(os.path.dirname(destination)):
        try:
            os.makedirs(os.path.dirname(destination))
        except OSError as exc: 
            if exc.errno != errno.EEXIST:
                raise
            
    with open(destination, "w") as f:
        f.write(resultado)
        f.close()
dirname = os.path.dirname(__file__)
if len(sys.argv) == 2:
    project_name = sys.argv[1];     
    listElementsJson = os.listdir(os.path.join(dirname, "esqueletos_json/" + project_name))          
    listElementsCSV = os.listdir(os.path.join(dirname, "csvs_proyecto/" + project_name + "/Properties/")) 
    for element in listElementsCSV:
         createElement(project_name,element[:-4])
    
    listMangoConfig = os.listdir(os.path.join(dirname, "csvs_proyecto/" + project_name + "/Config/"))
    print("Creando config de mango para todos los elementos ...")
    initialList = {'dataSources': [],
                   'dataPoints': [],
                   'publishers': []
                   }
    listMangoJsonstr = json.dumps(initialList)
    listMangoJson = json.loads(listMangoJsonstr,object_pairs_hook=OrderedDict)
    
    destination = os.path.join(dirname, "csvs_proyecto/" + project_name  + ".json")
    
    for element in listMangoConfig:
        
        
         
        source = open(os.path.join(dirname, "csvs_proyecto/" + project_name + "/Config/"+ element + "/"+ element + ".json"));
        f_original = source.read()
        source.close()
        print("Añadiendo configuracion : " + element + ".json")
        jsonElement = json.loads(f_original,object_pairs_hook=OrderedDict)
        if 'dataSources' in jsonElement:
            for element in jsonElement['dataSources']:
                listMangoJson['dataSources'].append(element);
        if 'dataPoints' in jsonElement:
             for element in jsonElement['dataPoints']:
                 listMangoJson['dataPoints'].append(element);
        if 'publishers' in jsonElement:
            for element in jsonElement['publishers']:
                listMangoJson['publishers'].append(element);
    
    resultado=json.dumps(listMangoJson, indent=4)        
    
    if not os.path.exists(os.path.dirname(destination)):
        try:
            os.makedirs(os.path.dirname(destination))
        except OSError as exc: 
            if exc.errno != errno.EEXIST:
                raise
            
    with open(destination, "w") as f:
        f.write(resultado)
        f.close()
    print("Fichero " + project_name +".json creado")    
else:
    element_name = sys.argv[2];
    project_name = sys.argv[1];
    createElement(project_name,element_name)