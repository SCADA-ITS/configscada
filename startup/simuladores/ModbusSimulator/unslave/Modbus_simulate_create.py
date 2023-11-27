import sys
import os
import json
from collections import OrderedDict

TYPE_WS = 1;
TYPE_CAMERA = 2;
TYPE_PLC_LOGO = 3;
TYPE_BARIER = 8;
TYPE_FAN = 9;
TYPE_ROTAPANEL = 10;
TYPE_SOS = 11;
TYPE_LUX = 12;
TYPE_SEM = 13;
TYPE_LUM = 14;
TYPE_FIRE_DET = 15;
TYPE_MEG = 16;
TYPE_ANEM_EXT = 17;
TYPE_ANEM_INT = 18;
TYPE_CO = 19;
TYPE_NO = 20;
TYPE_OPAC = 21;
TYPE_VMS_A_C = 24;
TYPE_ELECTRICAL_CABINET = 25;
TYPE_VMS_MAT_64_64 = 26;
TYPE_VMS_A_B = 27;
TYPE_VMS_MAT_192_64 = 28;
TYPE_VMS_B_D = 29;
TYPE_VMS_MAT_128_64 = 30;
TYPE_VMS_TEMP = 31;
TYPE_VMS_WEIGHT = 32;
TYPE_ELECTRICAL_CABINET_SCN = 34;
TYPE_ELECTRICAL_CABINET_ATC = 41;
TYPE_ELECTRICAL_CABINET_NRL = 42;
TYPE_ELECTRICAL_CABINET_NRS = 43;
TYPE_ELECTRICAL_CABINET_TFA_3D = 44;
TYPE_ELECTRICAL_CABINET_TFA_Loops = 45;
TYPE_ELECTRICAL_CABINET_TOLL = 46;
TYPE_ELECTRICAL_CABINET_TU = 47;
TYPE_VMS_CPU = 48;
TYPE_CAMERA_DETECTOR_DAI = 51;
TYPE_ETD = 52;

if len(sys.argv) < 3:
    print("Usage:")
    print("  arg1 = Name of Proyect")
    print("  arg2 = Name of element type or element_type_id")
    print("       1 = Ws --> Estacion Meteorologica")
    print("       2 = CAMERA --> PLC with Camera Alarms")
    print("       3 = PLC_LOGO --> PLC with 8DI")
    print("       8 = BARIER --> Barrera")
    print("       9 = FAN --> Ventiladores")
    print("       10 = ROTAPANEL --> Rotapanel")
    print("       11 = SOS --> Poste SOS")
    print("       12 = LUX --> Luxometro")
    print("       13 = SEM --> Semaforo")
    print("       14 = LUM --> Luminancimetro")
    print("       15 = FIRE_DET --> Detector de incendios")
    print("       16 = MEG --> Megafonia")
    print("       17 = ANEM_EXT --> Anemometro exterior")
    print("       18 = ANEM_INT --> Anemometro interior")
    print("       19 = CO --> Detector de CO")
    print("       20 = NO --> Detector de NO")
    print("       21 = OPAC --> Opacimetro")
    print("       25 = ELECTRICAL_CABINET --> Armario Electrico")
    print("       34 = ELECTRICAL_CABINET_SCN --> Armario Electrico SCN")
    print("       41 = ELECTRICAL_CABINET_ATC_VIRTUAL")
    print("       42 = ELECTRICAL_CABINET_NRL")
    print("       43 = ELECTRICAL_CABINET_NRS")
    print("       44 = ELECTRICAL_CABINET_TFA_3D")
    print("       45 = ELECTRICAL_CABINET_TFA_Loops")
    print("       46 = ELECTRICAL_CABINET_TOLL")
    print("       47 = ELECTRICAL_CABINET_TU")
    print("       48 = VMS_CPU")
    print("       51 = CAMERA_DETECTOR_DAI")
    print("       52 = ETD")
    print("  arg3 (optional) = Name of Individual Element")
    print("\n\n")
    print("Dirs:")
    print("  element/Properties --> Path with element parameters")
    print("  element/Config --> Path config destination")
    exit()

def is_number(s):
    try:
        float(s)
        return True
    except ValueError:
        return False




element_name = sys.argv[2];

dirname = os.path.dirname(__file__)

source = open(os.path.join(dirname, "json/" + element_name + ".json"));
f_original = source.read()
f_final = f_original
source.close()

json_string = json.loads(f_original, object_pairs_hook=OrderedDict)
json_keys = json_string.keys()

resultado = "{\n  " 
list = []


    
font = open(os.path.join(dirname, "../../mango/element/" + sys.argv[1] + "/Properties/" + element_name + ".csv"));
leida = font.readline()
clave = leida.split(";")

string_bat = "@echo off\n" 
       
for line in font:
        f_final = f_original
        valor = line.split(";")   
        for element in clave:
       
            if('(i)' in element):
                f_final = f_final.replace('"{' + element.replace("(i)",'').replace("\n", '') + '}"', valor[clave.index(element)].replace("\n", ''))
            elif("port_sim_input_publisher" == element):
                f_final = f_final.replace('"{' + element.replace("\n",'') + '}"', valor[clave.index(element)].replace("\n", ''))
            else:
                f_final = f_final.replace('{' + element.replace("\n", '') + '}', valor[clave.index(element)].replace("\n", ''))
                
            
        

    
    
        resultado = f_final
        destination = os.path.join(dirname, "element/" + sys.argv[1] + "/Config/" + element_name + "/" + valor[clave.index("device_name")] + ".json")
        print("Creando fichero: " + valor[clave.index("device_name")]+ ".json")

        if not os.path.exists(os.path.dirname(destination)):
            try:
                os.makedirs(os.path.dirname(destination))
            except OSError as exc: 
                if exc.errno != errno.EEXIST:
                    raise
        
        with open(destination, "w") as f:
            f.write(resultado)
            f.close()

        string_bat += "START /b /MIN unslave " + element_name + "/" + valor[clave.index("device_name")] + ".json\n"

string_bat += "EXIT"  
destination = os.path.join(dirname, "element/" + sys.argv[1] + "/Config/" + element_name + "/" + element_name + ".bat")
print("Creando fichero: " + element_name + ".bat")

if not os.path.exists(os.path.dirname(destination)):
    try:
         os.makedirs(os.path.dirname(destination))
    except OSError as exc: 
        if exc.errno != errno.EEXIST:
            raise
        
with open(destination, "w") as f:
    f.write(string_bat)
    f.close()
font.close()

