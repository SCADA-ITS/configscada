# -*- coding: utf-8 -*-

'''
Created on 13 jul. 2020

@author: jcaballeroa
'''

import sys
import os
import csv
from os import walk

BBX = 'BBX'
ENCODING = 'ENCODING'
CHARSET_ENCODING = 'CHARSET_ENCODING'

dirname = os.path.dirname(__file__)

files=walk(os.path.join(dirname, sys.argv[1] + '/Properties/VMS_GRAPHICS/Fonts'))
for (dirpath, dirnames, filenames) in files:
    for name in filenames:
        contador = 0
        fichero = "id,character,tamanio\n"
        with open(os.path.join(dirname, sys.argv[1] + '/Properties/VMS_GRAPHICS/Fonts/' + name)) as f:
            letra = []
            tamanio = []
            for line in f:
                if ENCODING in line and CHARSET_ENCODING not in line:
                    letra.append("CH{:04X}".format(int(line.split(" ")[1][:-1])))
                if BBX in line:
                    tamanio.append(line.split(" ")[1] + "x" + line.split(" ")[2])
                contador+=1
            f.close()
        
        with open(os.path.join(dirname, sys.argv[1] + '/Properties/VMS_GRAPHICS/vms_graphics.csv')) as f:
            reader = csv.reader(f, delimiter=';')
            findencabezado = 0
            
            for row in reader:
                if findencabezado == 0:
                    encabezado = ';'.join(row).split((';'))
                    findencabezado=1
                else:
                    parametros = ';'.join(row).split((';'))
                    
                    contador = 0
                    for l in letra:
                        if (parametros[encabezado.index('alias')] == l):
                            fichero += parametros[encabezado.index('id')] + "," + tamanio[contador] + "," + letra[contador] + "\n"
                        contador+=1
            f.close()               
    
        destination = os.path.join(dirname, sys.argv[1] + '/Config/VMS_GRAPHICS/Fonts/CSV/' + name.split(".")[0] + '_values.csv')     
                
        if not os.path.exists(os.path.dirname(destination)):
            try:
                os.makedirs(os.path.dirname(destination))
            except OSError as exc: 
                if exc.errno != errno.EEXIST:
                    raise        
                
        with open(destination, "w") as f:
            f.write(fichero)
            f.close()
            
        print('Creado fichero /Config/VMS_GRAPHICS/Fonts/CSV/' + name.split(".")[0] + '_values.csv') 
    
                    