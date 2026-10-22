import csv
import os
import concurrent.futures

LOG_FOLDER = 'logs'

ports = []

with open('ports.csv', newline='') as csvfile:
    csv_ports = csv.reader(csvfile, delimiter=' ', quotechar='|')
    for row in csv_ports:
        if row == ['ports']:
            pass
        else:
            ports.append(int(row[-1]))

os.makedirs(LOG_FOLDER, exist_ok=True)

print('Application running... Use Ctrl+C to exit.')
print('The log files are located at "logs" folder and named port_<PORT_USED>')

def main(ports):
    os.system(f"python3 server.py {ports} &")

#Creamos un hilo por cada puerto que tenga la lista "ports" y lanzamos la funcion "main" con la variable "ports" como parámetro
with concurrent.futures.ThreadPoolExecutor(len(ports)) as executor:
    executor.map(main, ports)

