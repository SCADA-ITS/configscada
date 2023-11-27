# -*- coding: utf-8 -*-

'''
Created on 4 may. 2020

@author: jcaballeroa
'''

import sys
import os
import json

dirname = os.path.dirname(__file__)
font = open(os.path.join(dirname, "../../../mango/csvs_proyecto/" + sys.argv[1] + "/Properties/" + sys.argv[2] + ".csv"));
json_font = open(os.path.join(dirname, "json/" + sys.argv[2] + ".json"));

with json_font as json_file:
    jfile = json.load(json_font)

resultado = 'TITLE ' + sys.argv[2] + '\n'
resultado += '@echo off\n' 
   
contador = 0
for line in font: 
    if contador == 0:
        encabezado = line[:-1].split(';')
    else:  
        parametros = line[:-1].split(';') 
        name = parametros[encabezado.index('PLC_name')]
        port = int(parametros[encabezado.index('PLC_port(i)')])
        jfile['tcpPort'] = port; port+=1;
        jfile['api']['port']=7000 + port;
        destination = os.path.join(dirname, "element/" + sys.argv[2] + "/" + name + ".json")
        if not os.path.exists(os.path.dirname(destination)):
            try:
                os.makedirs(os.path.dirname(destination))
            except OSError as exc: 
                if exc.errno != errno.EEXIST:
                    raise
            
        with open(destination, 'w') as outfile:
            json.dump(jfile, outfile, indent=2)
         
        resultado += 'START /b /MIN unslave ' + sys.argv[2] + "/" + name + '.json\n'    
            
    contador+=1
    
resultado += 'EXIT'
destination = os.path.join(dirname, "element/" + sys.argv[2] + "/" + sys.argv[2] + ".bat")    
print(destination)
if not os.path.exists(os.path.dirname(destination)):
    try:
        os.makedirs(os.path.dirname(destination))
    except OSError as exc: 
        if exc.errno != errno.EEXIST:
            raise
            
with open(destination, "w") as f:
    f.write(resultado);
    f.close();
                
print("finalizado")