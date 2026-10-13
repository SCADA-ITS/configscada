# -*- coding: utf-8 -*-
'''
Created on 8 feb. 2022
@author: jdiaz
'''
import sys;
import os;
import csv;
import errno
import base64
from shutil import rmtree
#constants

HOST = '\tproxy_set_header Host $host;\n'
X_REAL_IP = '\tproxy_set_header X-Real-IP $remote_addr;\n'
X_FORWARDED = '\tproxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;\n'

if len(sys.argv) < 2:
    print("Usage:")
    print("  arg1 = Name of project.")
    print("\n")
    print("Dirs:")
    print("  Config --> Path config destination")
    exit()

cwd = os.path.dirname(os.path.abspath(__file__));
cwdProperties = os.path.join(cwd, sys.argv[1] + '/Properties/Type 02/element_values.csv');
cwdConfig = os.path.join(cwd, sys.argv[1] + '/Config/');
#insert alias
contadorElements = 0;

with open(os.path.join(cwdProperties)) as f:
	
	reader = csv.reader(f, delimiter=';')
	resultado_camera = '';
	for line in reader:
		if contadorElements == 0:
			encabezado = line
		else:
			parametros = line
			resultado_camera += "location = /" + parametros[encabezado.index('alias')] + "{\n" + HOST + X_REAL_IP + X_FORWARDED
			ip = parametros[encabezado.index('data_proxy')]
			authorization = ip.split('http://')[1].split('@')[0]
			authorization = authorization.encode('utf-8')
			authorization_b64 = base64.encodebytes(authorization)
			authorization_b64 = authorization_b64.decode('utf-8')
			ip_final = ip[0:7] + ip.split('@')[1]
			resultado_camera += "\tproxy_pass " + ip_final + ";\n" + "\tproxy_set_header Authorization " + "\"""Basic " + authorization_b64[:-1] + "\"" + ";\n" +"}"
			resultado_camera += "\n"

		contadorElements += 1

destination = os.path.join(cwdConfig + 'default.conf');
if not os.path.exists(os.path.dirname(destination)):
        try:
            os.makedirs(os.path.dirname(destination))
        except OSError as exc: 
            if exc.errno != errno.EEXIST:
                raise
with open(destination, "w") as f:
	f.write(resultado_camera);
	f.close();
