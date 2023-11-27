# -*- coding: utf-8 -*-

'''
Created on 4 may. 2020

@author: jcaballeroa
'''
import errno
import sys
import os
import json
import copy
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
RATE_JSON_FILE= "esqueletos_json/status_keep_alive/rate_json.json"
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

def createRateJson(resultado, element_name):
    source = open(os.path.join(DIRECTORY_NAME, RATE_JSON_FILE))
    f_original = source.read()
    source.close()

    result_json = json.loads(resultado)

    with open(os.path.join(DIRECTORY_NAME, "csvs_proyecto/" + PROJECT_NAME + "/Properties/" + element_name + ".csv")) as font:
        clave = font.readline().strip().split(";")
        deviceName_aux = None
        json_dict = None

        for line in font:
            valor = line.strip().split(";")
            det_order = valor[clave.index("det_order")]
            det_name = valor[clave.index("det_name")]
            deviceName = valor[clave.index("device_name")]
            meteo_name = valor[clave.index("meteo_name")]  

            if deviceName != deviceName_aux:
                if json_dict:
                    #Se borran el contexto y se añaden solo el contexto que necesitamos, borrando asi lo del original.
                    json_dict_aux = copy.deepcopy(json_dict)
                    json_dict["dataPoints"][0]["pointLocator"]["context"] = []
                    for s in json_dict_aux["dataPoints"][0]["pointLocator"]["context"]:
                        if "{det_order}" in str(s) or "{meteo_name}" in str(s) or "{det_name}" in str(s):
                            pass
                        else:
                            json_dict["dataPoints"][0]["pointLocator"]["context"].append(s)
                    result_json["dataPoints"].append(json_dict["dataPoints"][0])
                    result_json["dataSources"].append(json_dict["dataSources"][0])

                json_dict = json.loads(f_original)
                device_name = json_dict["dataPoints"][0]["xid"].replace("{device_name}", deviceName)
                json_dict["dataPoints"][0]["xid"] = device_name
                json_dict["dataPoints"][0]["deviceName"] = device_name
            for i in range(6):
                varName = json_dict["dataPoints"][0]["pointLocator"]["context"][i]["varName"]
                dataPointXid = json_dict["dataPoints"][0]["pointLocator"]["context"][i]["dataPointXid"]

                if "{det_order}" in varName:
                    varName = varName.replace("{det_order}", det_order)
                if deviceName != deviceName_aux:
                    if "{meteo_name}" in dataPointXid:
                        dataPointXid = dataPointXid.replace("{meteo_name}", meteo_name)

                if "{det_name}" in dataPointXid:
                    dataPointXid = dataPointXid.replace("{det_name}", det_name)
                
                json_dict["dataPoints"][0]["pointLocator"]["context"].append({
                    "varName": varName,
                    "dataPointXid": dataPointXid,
                    "updateContext": bool(1)
                })

            deviceName_aux = deviceName
        
        if json_dict:          
            json_dict_aux = copy.deepcopy(json_dict)
            json_dict["dataPoints"][0]["pointLocator"]["context"] = []
            #Se borran el contexto y se añaden solo el contexto que necesitamos, borrando asi lo del original.
            for s in json_dict_aux["dataPoints"][0]["pointLocator"]["context"]:
 
                if "{det_order}" in str(s) or "{meteo_name}" in str(s) or "{det_name}" in str(s):
                    pass
                else:
                    json_dict["dataPoints"][0]["pointLocator"]["context"].append(s)
                
            result_json["dataPoints"].append(json_dict["dataPoints"][0])
            result_json["dataSources"].append(json_dict["dataSources"][0])

    resultado = json.dumps(result_json, indent=4)
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
        if element_name == 'ETD' or element_name == 'ETD_2' or element_name == 'ETD_3' or element_name == 'ETD_KINEO' or element_name == 'TFA_3D':
            if element_name == 'ETD_KINEO':
                measure_det = [s["xid"] for s in jsonDictElement["dataPoints"] if "DP_{device_name}_" in s["xid"] and s["xid"] == "DP_{device_name}_CARRIL_{carril}_timestamp"]
                measure_etd = [s["xid"] for s in jsonDictElement["dataPoints"] if "DP_{device_name}_" in s["xid"] and s["xid"] == "DP_{device_name}_ALARMAS_fecha_conexion"]

            if element_name == 'ETD' or element_name == 'ETD_2' or element_name == 'ETD_3':
                measure_det = [s["xid"] for s in jsonDictElement["dataPoints"] if "DP_{det_name}_" in s["xid"] and s["xid"] == "DP_{det_name}_anio"]
                measure_etd = [s["xid"] for s in jsonDictElement["dataPoints"] if "DP_{det_name}_" in s["xid"] and s["xid"] == "DP_{det_name}_mes"]
            if element_name == 'TFA_3D':
                measure_det = [s["xid"] for s in jsonDictElement["dataPoints"] if "DP_{det_name}_" in s["xid"] and s["xid"] == "DP_{det_name}_congestion_{line}"]
                measure_etd = [s["xid"] for s in jsonDictElement["dataPoints"] if "DP_{device_name}_" in s["xid"] and s["xid"] == "DP_{device_name}_activate_panel"]

            if measure_det:
                measure_det= measure_det[0]
                measure_etd = measure_etd[0]
                print("MEASURE_ETD-> " + measure_etd)
                print("MEASURE_DET-> " + measure_det)
                checkStatusElement= 1

        if element_name == 'VMS_ROTAPANEL':
            measure_ROTAPANEL = [s["xid"] for s in jsonDictElement["dataPoints"] if "DP_{device_name}_" in s["xid"] and s["xid"] == "DP_{device_name}_out_comunication"]
            if measure_ROTAPANEL:
                measure_ROTAPANEL = measure_ROTAPANEL[0]
                checkStatusElement= 1

        if measure_Xid:
            measure_Xid= measure_Xid[0]
            checkStatusElement= 1
            
    if checkStatusElement:
        # Se abre y rellena el esqueleto json keep_alive
        source = open(os.path.join(DIRECTORY_NAME, KEEP_ALIVE_SOURCE_JSON_FILE))
        f_original = source.read()
        source.close()
        json_dict = json.loads(f_original)
        json_dict["dataPoints"][0]["deviceName"]= element_name
        json_dict["dataPoints"][0]["xid"]= "DP_" + element_name + "_status_calcular"
        if element_name == 'WS_P1' or element_name == 'WS_P2':
            json_dict["dataPoints"][0]["pointLocator"]["script"]= "time_polling_min = 30;\ntime_polling_seg = time_polling_min * 60;\ntime_polling_ms = time_polling_seg * 1000;"    
            json_dict["dataPoints"][0]["pointLocator"]["context"]= []
        else:
            json_dict["dataPoints"][0]["pointLocator"]["script"]= "time_polling_min = 1;\ntime_polling_seg = time_polling_min * 60;\ntime_polling_ms = time_polling_seg * 1000;"    
            json_dict["dataPoints"][0]["pointLocator"]["context"]= []

        # Se abre el fichero csv para recuperar el array de elementos
        font = open(os.path.join(DIRECTORY_NAME, "csvs_proyecto/" + PROJECT_NAME + "/Properties/" + element_name + ".csv"))
        leida = font.readline()
        clave = leida.split(";")
        upd = []
        sos = []
        door = []
        i= 1
        deviceNameAnt = ""
        n = 0
        for line in font:
            valor= line.split(";")
            deviceName= valor[clave.index("device_name")]
            if deviceNameAnt == deviceName:
                continue
            measure= measure_Xid.replace("{device_name}", deviceName)
            measure= measure.replace("{carril}", "1")
            varStatusName= "status" + "{:0>3d}".format(i)
            measureStatusName= "measure" + "{:0>3d}".format(i)
            if element_name == 'ETD' or element_name == 'ETD_2' or element_name == 'ETD_3' or element_name == 'ETD_KINEO' or element_name == 'TFA3D':
                detectorName = valor[clave.index("det_name")]
            if element_name == 'VMS_ROTAPANEL':
                rotapanel_name = measure_ROTAPANEL.replace("{device_name}", deviceName)
                
            commStatus= ""
            commStatusName= ""
            upd_state = ""
            sos_electronic_fail = ""
            door_open = ""

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

            if "door_open(i)" in clave and valor[clave.index("door_open(i)")]:
                varStatusName= "plc_status" + "{:0>3d}".format(i)
                dp_ct = "DP_" + deviceName + "_door_open"

            if "offset_Live(i)" in clave and valor[clave.index("offset_Live(i)")]:
                varStatusName= "_status" + "{:0>3d}".format(i)
                dp_ct = "DP_" + deviceName + "_Live"
  
            # Se agregan los elementos a comprobar su estado en el script
            if "door_open(i)" in clave and valor[clave.index("door_open(i)")]:
                json_dict["dataPoints"][0]["pointLocator"]["updateCronPattern"]= JOB_MINUTE + " * * * * ?"
                json_dict["dataPoints"][0]["pointLocator"]["context"].append({
                    "varName": varStatusName,
                    "dataPointXid":"DP_" + deviceName + "_plc_status",
                    "updateContext": bool(0)
            })
                

            else:
                json_dict["dataPoints"][0]["pointLocator"]["updateCronPattern"]= JOB_MINUTE + " * * * * ?"
                json_dict["dataPoints"][0]["pointLocator"]["context"].append({
                    "varName": varStatusName,
                    "dataPointXid":"DP_" + deviceName + "_status",
                    "updateContext": bool(0)
            })
                
            

            if element_name == 'ETD' or element_name == 'ETD_2' or element_name == 'ETD_3' or element_name == 'ETD_KINEO' or element_name == 'TFA_3D':
                
                if element_name == 'ETD_KINEO' or element_name == 'TFA_3D':
                    measure= measure_etd.replace("{device_name}", deviceName)
                else:
                    measure= measure_etd.replace("{det_name}", detectorName)
                json_dict["dataPoints"][0]["pointLocator"]["context"].append({
                "varName": measureStatusName,
                "dataPointXid": measure,
                "updateContext": bool(0)})
            if element_name == 'VMS_ROTAPANEL':
                json_dict["dataPoints"][0]["pointLocator"]["script"] += "\n\ntry{\n    if(" + measureStatusName + ".time == null){\n        " + varStatusName + ".set(0.0);\n    } else{\n        if(Date.now() - " + measureStatusName + ".time > time_polling_ms || " + measureStatusName + ".value == true){\n            " + varStatusName + ".set(0.0);\n        } else{\n            " + varStatusName + ".set(1.0);\n        }\n    }\n} catch(e){\n   " + varStatusName + ".set(0.0);\n}"
                json_dict["dataPoints"][0]["pointLocator"]["context"].append({
                "varName": measureStatusName,
                "dataPointXid": rotapanel_name,
                "updateContext": bool(0)})
            else:
                json_dict["dataPoints"][0]["pointLocator"]["context"].append({
                    "varName": measureStatusName,
                    "dataPointXid": measure,
                    "updateContext": bool(0)})
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
            if door_open:

               json_dict["dataPoints"][0]["pointLocator"]["script"] += "time_polling_ms = 2*10000;\n\n  if(" + measureStatusName + ".time == null){\n        return 0;\n    } else{\n        if(Date.now() - " + measureStatusName + ".time > time_polling_ms){\n            return 0;\n        } else{\n            return 1;\n        }\n    }\n}"
               door.append(dp_ct)
            if not upd_state and not sos_electronic_fail and not door_open and element_name != 'VMS_ROTAPANEL':
                json_dict["dataPoints"][0]["pointLocator"]["script"] += "\n\ntry{\n    if(" + measureStatusName + ".time == null){\n        " + varStatusName + ".set(0.0);\n    } else{\n        if(Date.now() - " + measureStatusName + ".time > time_polling_ms){\n            " + varStatusName + ".set(0.0);\n        } else{\n            " + varStatusName + ".set(1.0);\n        }\n    }\n} catch(e){\n   " + varStatusName + ".set(0.0);\n}"
                #LINEA COMMUNICATION_FAIL / SOS_ELECTRONIC_FAIL
                #json_dict["dataPoints"][0]["pointLocator"]["script"] += "\n\ntry{\n    if(" + measureStatusName + ".time == null){\n        " + varStatusName + ".set(0.0);\n    } else{\n        if(Date.now() - " + measureStatusName + ".time > time_polling_ms || " + measureStatusName + ".value == true){\n            " + varStatusName + ".set(0.0);\n        } else{\n            " + varStatusName + ".set(1.0);\n        }\n    }\n} catch(e){\n   " + varStatusName + ".set(0.0);\n}"

            i= i+1
            n = i
            deviceNameAnt = deviceName
        detectorNameAnt = ""
        if element_name == 'ETD' or element_name == 'ETD_2' or element_name == 'ETD_3' or element_name == 'ETD_KINEO' or element_name == 'TFA_3D':

            font = open(os.path.join(DIRECTORY_NAME, "csvs_proyecto/" + PROJECT_NAME + "/Properties/" + element_name + ".csv"))
            leida = font.readline()
            clave = leida.split(";")
            for line in font:
                valor= line.split(";")
                detectorName = valor[clave.index("det_name")]
                deviceName = valor[clave.index("device_name")]

                if element_name == 'ETD_KINEO':
                    carril = valor[clave.index("carril")]
                if element_name == 'TFA_3D':
                    line = valor[clave.index("line")]
                if detectorNameAnt == detectorName:
                    continue
                if element_name == 'ETD_KINEO':
                    measure= measure_det.replace("{device_name}", deviceName).replace("{carril}", carril)

                if element_name == 'ETD' or element_name == 'ETD_2' or element_name == 'ETD_3':
                    measure= measure_det.replace("{det_name}", detectorName)

                if element_name == 'TFA_3D':
                    measure= measure_det.replace("{det_name}", detectorName).replace("{line}", line)
                varStatusName= "status" + "{:0>3d}".format(n)
                measureStatusName= "measure" + "{:0>3d}".format(n)
                json_dict["dataPoints"][0]["pointLocator"]["updateCronPattern"]= JOB_MINUTE + " * * * * ?"
                json_dict["dataPoints"][0]["pointLocator"]["context"].append({
                    "varName": varStatusName,
                    "dataPointXid":"DP_" + detectorName + "_status",
                    "updateContext": bool(0)})
                json_dict["dataPoints"][0]["pointLocator"]["context"].append({
                "varName": measureStatusName,
                "dataPointXid": measure,
                "updateContext": bool(0)
                })
                json_dict["dataPoints"][0]["pointLocator"]["script"] += "\n\ntry{\n    if(" + measureStatusName + ".time == null){\n        " + varStatusName + ".set(0.0);\n    } else{\n        if(Date.now() - " + measureStatusName + ".time > time_polling_ms){\n            " + varStatusName + ".set(0.0);\n        } else{\n            " + varStatusName + ".set(1.0);\n        }\n    }\n} catch(e){\n   " + varStatusName + ".set(0.0);\n}"
                n = n + 1
                detectorNameAnt = detectorName

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
        door = list(set(door))
        for i in door:
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
            
                if key == "dataPoints" and element == "det_order":
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
                    
        resultado=json.dumps(json_resultado, indent=1)          

    if (element_name == "ETD_VIRTUAL" or element_name == "ETD" or element_name == "ETD_2" or element_name == "ETD_3" or element_name == "51.etd"):
        resultado= getJsonETD(resultado,list,listxid)
    
    if (element_name == "TFA_3D"):    
        resultado= getJsonTFA_3D(resultado,list,listxid)
    
    if (element_name == "ETD_KINEO_VIRTUAL" or element_name == "ETD_KINEO"):
        resultado= getJsonKineo(resultado,list,listxid)

    if(element_name not in ELEMENTS_PING_HTTP and element_name not in ELEMENTS_PING_MANGO and element_name not in ELEMENTS_DEPRECATED):
        resultado= createStatusVirtual(resultado, element_name)
    
    if (element_name == "VMS_METEO_TRAFICO"):    
        resultado= createRateJson(resultado, element_name)

    outputFileName= element_name + ".json"
    if TARGET_FILE:
        outputFileName= TARGET_FILE + ".json" 
    destination = os.path.join(DIRECTORY_NAME, "csvs_proyecto/" + PROJECT_NAME + "/Config/"+ outputFileName)
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
    initialList = {'dataSources': [],
                   'dataPoints': [],
                   'publishers': []
                   }
    listMangoJsonstr = json.dumps(initialList)
    listMangoJson = json.loads(listMangoJsonstr,object_pairs_hook=OrderedDict)
    
    destination = os.path.join(DIRECTORY_NAME, "csvs_proyecto/" + PROJECT_NAME  + ".json")
    
    for element in listMangoConfig:
        source = open(os.path.join(DIRECTORY_NAME, "csvs_proyecto/" + PROJECT_NAME + "/Config/" + element));
        f_original = source.read()
        source.close()
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
