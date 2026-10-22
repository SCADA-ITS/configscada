# -*- coding: utf-8 -*-

'''
Created on 4 may. 2020

@author: jcaballeroa
'''
import errno
import sys
import os
import json
from collections import OrderedDict

if len(sys.argv) < 2:
    print("Usage:")
    print("  arg1 = Name of Project")
    print("  arg2 (optional) = Name of Individual Element")
    print("  arg3 (optional) = Minute when job execute")
    print("  arg4 (optional) = Targets file name")
    print("\n")
    print("Example:")
    print(" python3 Mango_create.py ausol 5 SOS_1alarm output_file")
    print("\n")
    exit()

KEEP_ALIVE_SOURCE_JSON_FILE= "esqueletos_json/status_keep_alive/ds_status_calculator.json"
DIRECTORY_NAME= os.path.dirname(__file__)
PROJECT_NAME= sys.argv[1]
SOURCE_FILE= ""
JOB_MINUTE= "50"
TARGET_FILE= ""
BLOCK_SIZE= 100
ELEMENTS_PING_HTTP= [
    "CAMERA",
    "PLC"
]
ELEMENTS_PING_MANGO= [
    "SOS_1alarm",
    "SOS_4alarm",
    "SWITCH",
    "NAS"
]
# TODO DEPRECATED
ELEMENTS_DEPRECATED= [
    "CENTRAL_LAZ",
    "CENTRAL_FIBRO",
    "ERU_CONNECTOR"
]

if len(sys.argv) > 2:
    SOURCE_FILE = sys.argv[2]
    if len(sys.argv) > 3:
        try:
            JOB_MINUTE = sys.argv[3]
            if int(JOB_MINUTE) > 60:
                raise Exception("Number not in range 0 to 60")
            else:
                JOB_MINUTE= "0/" + JOB_MINUTE
        except Exception as e:
            print(e)
            exit()
        if len(sys.argv) > 4:
            TARGET_FILE= sys.argv[4]

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

def createStatusVirtual(resultStream, element_name):
    # Se comprueba si el elemento contiene medidas que usar para checkear su estado periodicamente
    checkStatusElement = 0 
    source = open(os.path.join(DIRECTORY_NAME, "esqueletos_json/" + PROJECT_NAME + "/" + element_name + ".json"));
    f_original = source.read()
    source.close()
    jsonDictElement= json.loads(f_original)
    if "dataPoints" in jsonDictElement:
        measure_Xid = [s["xid"] for s in jsonDictElement["dataPoints"] if "DP_{device_name}_" in s["xid"] and s["xid"] != "DP_{device_name}_status"]
        if measure_Xid:
            measure_Xid= measure_Xid[0]
            #-------POSTE SOS -> SOS_ELECTRONIC_FAIL----------
            #measure_Xid= measure_Xid[18] 
            #-------PMV AF -> COMMUNICATION_FAIL--------------
            #measure_Xid= measure_Xid[3] 
            #-------PMV CLV -> COMMUNICATION_FAIL-------------
            #measure_Xid= measure_Xid[1] 
            #-------------------------------------------------

            checkStatusElement= 1

    if checkStatusElement:
        # Se abre y rellena el esqueleto json keep_alive
        source = open(os.path.join(DIRECTORY_NAME, KEEP_ALIVE_SOURCE_JSON_FILE))
        f_original = source.read()
        source.close()
        json_dict = json.loads(f_original)
        json_dict["dataPoints"][0]["deviceName"]= element_name
        json_dict["dataPoints"][0]["xid"]= "DP_" + element_name + "_status_calcular"
        json_dict["dataPoints"][0]["pointLocator"]["script"]= "time_polling_min = 1;\ntime_polling_seg = time_polling_min * 60;\ntime_polling_ms = time_polling_seg * 1000;"    
        json_dict["dataPoints"][0]["pointLocator"]["context"]= []

        # Se abre el fichero csv para recuperar el array de elementos
        font = open(os.path.join(DIRECTORY_NAME, "csvs_proyecto/" + PROJECT_NAME + "/Properties/" + element_name + ".csv"))
        leida = font.readline()
        clave = leida.split(";")
        upd = []
        sos = []
        i= 1
        for line in font:
            valor= line.split(";")
            deviceName= valor[clave.index("device_name")]
            measure= measure_Xid.replace("{device_name}", deviceName)
            varStatusName= "status" + "{:0>3d}".format(i)
            measureStatusName= "measure" + "{:0>3d}".format(i)
            commStatus= ""
            commStatusName= ""
            upd_state = ""
            sos_electronic_fail = ""
            
            if "eru_name" in clave and valor[clave.index("eru_name")]:
                commStatus= "DP_" + deviceName + "_commstatus"
                commStatusName= "comstatus" + "{:0>3d}".format(i)

            if "upd_state" in clave and valor[clave.index("upd_state")]:
                upd_state= valor[clave.index("upd_state")]
                upd_ct = valor[clave.index("ct_upd")]
                dp_ct = "DP_" + upd_ct + "_" + upd_state

            if "sos_electronic_fail" in clave and valor[clave.index("sos_electronic_fail")]:
                sos_electronic_fail = valor[clave.index("sos_electronic_fail")]
                dp_ct = "DP_" + sos_electronic_fail + "_sos_electronic_fail"
            # Se agregan los elementos a comprobar su estado en el script
            json_dict["dataPoints"][0]["pointLocator"]["updateCronPattern"]= JOB_MINUTE + " * * * * ?"
            json_dict["dataPoints"][0]["pointLocator"]["context"].append({
                "varName": varStatusName,
                "dataPointXid":"DP_" + deviceName + "_status",
                "updateContext": bool(0)
            })

                

            json_dict["dataPoints"][0]["pointLocator"]["context"].append({
                "varName": measureStatusName,
                "dataPointXid": measure,
                "updateContext": bool(0)
            })
            if commStatus:
                json_dict["dataPoints"][0]["pointLocator"]["script"] += "\n\ntry{\n    if(" + measureStatusName + ".time == null){\n        " + varStatusName + ".set(0.0);\n    } else{\n        if(Date.now() - " + measureStatusName + ".time > time_polling_ms){\n            " + varStatusName + ".set(0.0);\n        } else{\n            " + varStatusName + ".set((" + commStatusName + ".value? 1.0 : 0.0));\n        }\n    }\n} catch(e){\n   " + varStatusName + ".set(0.0);\n}"
                json_dict["dataPoints"][0]["pointLocator"]["context"].append({
                "varName": commStatusName,
                "dataPointXid": commStatus,
                "updateContext": bool(0)
            })
            if upd_state:
                json_dict["dataPoints"][0]["pointLocator"]["script"] += "\n\ntry{\n    if(" + measureStatusName + ".time == null){\n        " + varStatusName + ".set(0.0);\n    } else{\n        if(" + upd_state + ".value == true){ \n           " + varStatusName + ".set(0.0); \n        }else{ \n            if(Date.now() - " + measureStatusName + ".time > time_polling_ms){\n                " + varStatusName + ".set(0.0);\n            } else{\n                " + varStatusName + ".set(1.0);\n            }\n        }\n    } \n}catch(e){\n   " + varStatusName + ".set(0.0);\n}"
                upd.append(dp_ct)            
            if sos_electronic_fail:
               json_dict["dataPoints"][0]["pointLocator"]["script"] += "\n\ntry{\n    if(" + measureStatusName + ".time == null){\n        " + varStatusName + ".set(0.0);\n    } else{\n        if(" + sos_electronic_fail.replace("-", "_") + ".value == true){ \n           " + varStatusName + ".set(0.0); \n        }else{ \n            if(Date.now() - " + measureStatusName + ".time > time_polling_ms){\n                " + varStatusName + ".set(0.0);\n            } else{\n                " + varStatusName + ".set(1.0);\n            }\n        }\n    } \n}catch(e){\n   " + varStatusName + ".set(0.0);\n}"
               sos.append(dp_ct)
            if not upd_state and not sos_electronic_fail:
                json_dict["dataPoints"][0]["pointLocator"]["script"] += "\n\ntry{\n    if(" + measureStatusName + ".time == null){\n        " + varStatusName + ".set(0.0);\n    } else{\n        if(Date.now() - " + measureStatusName + ".time > time_polling_ms){\n            " + varStatusName + ".set(0.0);\n        } else{\n            " + varStatusName + ".set(1.0);\n        }\n    }\n} catch(e){\n   " + varStatusName + ".set(0.0);\n}"
                #LINEA COMMUNICATION_FAIL / SOS_ELECTRONIC_FAIL
                #json_dict["dataPoints"][0]["pointLocator"]["script"] += "\n\ntry{\n    if(" + measureStatusName + ".time == null){\n        " + varStatusName + ".set(0.0);\n    } else{\n        if(Date.now() - " + measureStatusName + ".time > time_polling_ms || " + measureStatusName + ".value == true){\n            " + varStatusName + ".set(0.0);\n        } else{\n            " + varStatusName + ".set(1.0);\n        }\n    }\n} catch(e){\n   " + varStatusName + ".set(0.0);\n}"

            i= i+1
        upd = list(set(upd))
        for i in upd:
            json_dict["dataPoints"][0]["pointLocator"]["context"].append({
                "varName": i.split("_", 3)[3],
                "dataPointXid": i,
                "updateContext": bool(0)
            })
        sos = list(set(sos))
        for i in sos:
            json_dict["dataPoints"][0]["pointLocator"]["context"].append({
                "varName": i.split("_", 2)[1].replace("-","_"),
                "dataPointXid": i,
                "updateContext": bool(0)
            })
        json_dict["dataPoints"][0]["pointLocator"]["script"] += "\n\nreturn 1"
        font.close()
        
        result_json = json.loads(resultStream)
        result_json["dataSources"].append(json_dict["dataSources"][0])
        result_json["dataPoints"].append(json_dict["dataPoints"][0])

        resultStream=json.dumps(result_json, indent=4)          
        return resultStream

def createElement(element_name):
    print(element_name)
    source = open(os.path.join(DIRECTORY_NAME, "esqueletos_json/" + PROJECT_NAME + "/" + element_name + ".json"));
    f_original = source.read()
    f_final = f_original
    source.close()
    
    json_string = json.loads(f_original, object_pairs_hook=OrderedDict)
    json_keys = json_string.keys()
    
    resultado = "{\n  " 
    list = []
    listxid = []
    
    for key in json_keys:
        font = open(os.path.join(DIRECTORY_NAME, "csvs_proyecto/" + PROJECT_NAME + "/Properties/" + element_name + ".csv"));
        leida = font.readline()
        clave = leida.split(";")
        
        resultado += "\"" + key + "\": ["
        json_formatted_str = json.dumps(json_string[key], indent=2)
        f_final = json_formatted_str[1:-1]
       
        for line in font:
            valor = line.split(";")
            if(len(sys.argv) > 5):
              if (valor[clave.index("device_name")] != sys.argv[5]):
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
                contador = contador + 1;
        # Remove duplicates 
        seen = []
        for elemento in json_resultado[key2]:
            if elemento not in seen:
                seen.append(elemento)
        json_resultado[key2] = seen;
                    
    
    resultado=json.dumps(json_resultado, indent=4)          
    
    
    if (element_name == "ETD_VIRTUAL" or element_name == "ETD"):    
        resultado= getJsonETD(resultado,list,listxid)
    
    if (element_name == "TFA_3D"):    
        resultado= getJsonTFA_3D(resultado,list,listxid)
    
    if (element_name == "ETD_KINEO_VIRTUAL" or element_name == "ETD_KINEO"):
        resultado= getJsonKineo(resultado,list,listxid)

    if(element_name not in ELEMENTS_PING_HTTP and element_name not in ELEMENTS_PING_MANGO and element_name not in ELEMENTS_DEPRECATED):
        resultado= createStatusVirtual(resultado, element_name)
    
    outputFileName= element_name + ".json"
    if TARGET_FILE:
        outputFileName= TARGET_FILE + ".json" 
    destination = os.path.join(DIRECTORY_NAME, "csvs_proyecto/" + PROJECT_NAME + "/Config/" + element_name + "/" + outputFileName)
    print("Creando fichero: " + outputFileName)
    
    if not os.path.exists(os.path.dirname(destination)):
        try:
            os.makedirs(os.path.dirname(destination))
        except OSError as exc: 
            if exc.errno != errno.EEXIST:
                raise
            
    with open(destination, "w") as f:
        f.write(resultado)
        f.close()

DIRECTORY_NAME = os.path.dirname(__file__)

if SOURCE_FILE:
    createElement(SOURCE_FILE)    
else:
    listElementsCSV = os.listdir(os.path.join(DIRECTORY_NAME, "csvs_proyecto/" + PROJECT_NAME + "/Properties/")) 
    for element in listElementsCSV:
        createElement(element[:-4])
    
    listMangoConfig = os.listdir(os.path.join(DIRECTORY_NAME, "csvs_proyecto/" + PROJECT_NAME + "/Config/"))
    print("Creando config de mango para todos los elementos ...")
    initialList = {'dataSources': [],
                   'dataPoints': [],
                   'publishers': []
                   }
    listMangoJsonstr = json.dumps(initialList)
    listMangoJson = json.loads(listMangoJsonstr,object_pairs_hook=OrderedDict)
    
    destination = os.path.join(DIRECTORY_NAME, "csvs_proyecto/" + PROJECT_NAME  + ".json")
    
    for element in listMangoConfig:
        source = open(os.path.join(DIRECTORY_NAME, "csvs_proyecto/" + PROJECT_NAME + "/Config/"+ element + "/"+ element + ".json"));
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
    print("Fichero " + PROJECT_NAME +".json creado")
