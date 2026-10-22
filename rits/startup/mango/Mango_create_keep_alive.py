# -*- coding: utf-8 -*-
import errno
import sys
import os
import json
from collections import OrderedDict

if len(sys.argv) < 2:
    print("Usage:")
    print("  arg1 = Name of Project")
    print("  arg2 (optional) = Minute when job execute")
    print("  arg3 (optional) = Name of Individual Element")
    print("  arg4 (optional) = Targets file name")
    print("\n")
    print("Example:")
    print(" python3 Mango_create_keep_alive.py ausol 5 SOS_1alarm output_file")
    print("\n")
    exit()

KEEP_ALIVE_SOURCE_JSON_FILE= "esqueletos_json/status_keep_alive/keep_alive.json"
DIRECTORY_NAME= os.path.dirname(__file__)
PROJECT_NAME= sys.argv[1]
SOURCE_FILE= ""
JOB_MINUTE= "0/60"
TARGET_FILE= ""
ELEMENTS_PING_HTTP= [
    "CAMERA",
    "PLC"
]
ELEMENTS_PING_MANGO= [
    "SOS_1alarm",
    "SOS_4alarm",
    "SWITCH"
] 

if len(sys.argv) > 2:
    try:
        JOB_MINUTE = sys.argv[2]
        if int(JOB_MINUTE) > 60:
            raise Exception("Number not in range 0 to 60")
        else:
            JOB_MINUTE= "0/" + JOB_MINUTE
    except Exception as e:
        print(e)
        exit()
    if len(sys.argv) > 3:
        SOURCE_FILE = sys.argv[3]
        if len(sys.argv) > 4:
            TARGET_FILE= sys.argv[4]
        

def createElementDefault(element_name):
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
        i= 1
        for line in font:
            valor= line.split(";")
            deviceName= valor[clave.index("device_name")]
            measure= measure_Xid.replace("{device_name}", deviceName)
            varStatusName= "status" + "{:0>3d}".format(i)
            measureStatusName= "measure" + "{:0>3d}".format(i)
            commStatus= ""
            commStatusName= ""
            
            if "eru_name" in clave and valor[clave.index("eru_name")]:
                commStatus= "DP_" + deviceName + "_commstatus"
                commStatusName= "comstatus" + "{:0>3d}".format(i)

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
            else:
                json_dict["dataPoints"][0]["pointLocator"]["script"] += "\n\ntry{\n    if(" + measureStatusName + ".time == null){\n        " + varStatusName + ".set(0.0);\n    } else{\n        if(Date.now() - " + measureStatusName + ".time > time_polling_ms){\n            " + varStatusName + ".set(0.0);\n        } else{\n            " + varStatusName + ".set(1.0);\n        }\n    }\n} catch(e){\n   " + varStatusName + ".set(0.0);\n}"
            
            # Se crea un dataPoint virtual del estado por cada elemento
            json_dict["dataPoints"].append({
                "purgePeriod":1,
                "setExtremeLowLimit":-179769313486231570814527423731704356798070567525844996598917476803157260780028538760589558632766878171540458953514382464234321326889464182768467546703537516986049910576551282076245490090389328944075868508455133942304583236903222948165808559332123348274797826204144723168738177180919299881250404026184124858368,
                "intervalLoggingType":"AVERAGE",
                "dataSourceXid":"DS_status_virtual",
                "discardHighLimit":179769313486231570814527423731704356798070567525844996598917476803157260780028538760589558632766878171540458953514382464234321326889464182768467546703537516986049910576551282076245490090389328944075868508455133942304583236903222948165808559332123348274797826204144723168738177180919299881250404026184124858368,
                "deviceName": deviceName,
                "enabled":bool(1),
                "rollup":"NONE",
                "purgeOverride": bool(0),
                "chartRenderer":{
                    "numberOfPeriods":1,
                    "type":"IMAGE",
                    "timePeriodType":"DAYS"
                },
                "xid":"DP_" + deviceName + "_status",
                "eventDetectors":[
                ],
                "textRenderer":{
                    "useUnitAsSuffix":bool(0),
                    "format":"0.00",
                    "type":"ANALOG",
                    "suffix":""
                },
                "loggingType":"INTERVAL",
                "overrideIntervalLoggingSamples":bool(0),
                "defaultCacheSize":1,
                "readPermission":"",
                "setPermission":"",
                "intervalLoggingPeriodType":"MINUTES",
                "intervalLoggingSampleWindowSize":0,
                "tolerance":0,
                "purgeType":"YEARS",
                "plotType":"SPLINE",
                "intervalLoggingPeriod":1,
                "discardExtremeValues":bool(0),
                "tags":{
                },
                "unit":"",
                "pointLocator":{
                    "settable":bool(1),
                    "dataType":"NUMERIC",
                    "changeType":{
                        "startValue":"0",
                        "type":"NO_CHANGE"
                    }
                },
                "discardLowLimit":-179769313486231570814527423731704356798070567525844996598917476803157260780028538760589558632766878171540458953514382464234321326889464182768467546703537516986049910576551282076245490090389328944075868508455133942304583236903222948165808559332123348274797826204144723168738177180919299881250404026184124858368,
                "setExtremeHighLimit":179769313486231570814527423731704356798070567525844996598917476803157260780028538760589558632766878171540458953514382464234321326889464182768467546703537516986049910576551282076245490090389328944075868508455133942304583236903222948165808559332123348274797826204144723168738177180919299881250404026184124858368,
                "name":"status",
                "chartColour":"",
                "preventSetExtremeValues":bool(0),
                "simplifyType":"NONE"
                })
            i= i+1
        json_dict["dataPoints"][0]["pointLocator"]["script"] += "\n\nreturn 1"
        font.close()

        resultado=json.dumps(json_dict, indent=4)          
        
        outputFileName= "status_" + element_name + ".json"
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

if SOURCE_FILE:
    createElementDefault(SOURCE_FILE)
else:
    listElementsCSV = os.listdir(os.path.join(DIRECTORY_NAME, "csvs_proyecto/" + PROJECT_NAME + "/Properties/")) 
    for element in listElementsCSV:
         createElementDefault(element[:-4])

