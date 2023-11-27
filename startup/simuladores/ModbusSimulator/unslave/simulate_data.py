import requests
import concurrent.futures
import time
import os
import random

ip = '192.168.88.189'
import datetime
dirname = os.path.dirname(__file__)
font = open(os.path.join(dirname, "DETECTOR_VIRTUAL_SIM" + ".csv"));
leida = font.readline()
clave = leida.split(";")
 
def sendDataThread(now,line):
    valor = line.split(";")  
        
    port = valor[clave.index("port_sim_publisher")]
    det_order = valor[clave.index("det_order")]
    print("Sending data... " + ip + ':' + port)           
    request_strHR0=   'http://' + ip + ':' + port + '/slaves/1/registers/HR' + str(0+(int(det_order)-1)*100)   
    request_strHR3=   'http://' + ip + ':' + port + '/slaves/1/registers/HR' + str(3+(int(det_order)-1)*100) 
    request_strHR2=   'http://' + ip + ':' + port + '/slaves/1/registers/HR' + str(2+(int(det_order)-1)*100) 
    request_strHR1=   'http://' + ip + ':' + port + '/slaves/1/registers/HR' + str(1+(int(det_order)-1)*100) 
    request_strHR4=   'http://' + ip + ':' + port + '/slaves/1/registers/HR' + str(4+(int(det_order)-1)*100) 
    request_strHR9=   'http://' + ip + ':' + port + '/slaves/1/registers/HR' + str(9+(int(det_order)-1)*100) 
    request_strHR10=   'http://' + ip + ':' + port + '/slaves/1/registers/HR' + str(10+(int(det_order)-1)*100) 
    request_strHR14=   'http://' + ip + ':' + port + '/slaves/1/registers/HR' + str(14+(int(det_order)-1)*100) 
    request_strHR15=   'http://' + ip + ':' + port + '/slaves/1/registers/HR' + str(15+(int(det_order)-1)*100)   
    request_strHR17=   'http://' + ip + ':' + port + '/slaves/1/registers/HR' + str(17+(int(det_order)-1)*100) 
    request_strHR18=   'http://' + ip + ':' + port + '/slaves/1/registers/HR' + str(18+(int(det_order)-1)*100)           
    request_strHR33=   'http://' + ip + ':' + port + '/slaves/1/registers/HR' + str(33+(int(det_order)-1)*100)   
    request_strHR34=   'http://' + ip + ':' + port + '/slaves/1/registers/HR' + str(34+(int(det_order)-1)*100) 
    request_strHR35=   'http://' + ip + ':' + port + '/slaves/1/registers/HR' + str(35+(int(det_order)-1)*100)           
        
    nivel_servicio = random.randrange(1,4)
    if nivel_servicio == 3 : #Nivel de servicio 3
        ocupacion = random.randrange(35,100)
        velocidad= random.randrange(32,72)
    elif nivel_servicio == 2 : #Nivel de servicio 2
        ocupacion = random.randrange(16,28)
        velocidad = random.randrange(32,72)
    elif nivel_servicio == 1 : #Nivel de servicio 1
        ocupacion = random.randrange(1,16)
        velocidad = random.randrange(72,140)
    else :                  #Nivel de servicio 4
        ocupacion = random.randrange(35,100)
        velocidad = random.randrange(16,28)
        
    intensidad = random.randrange(1,15)       
    longitud = random.randrange(40,100)
    percent_long = round(random.uniform(0,1),1)
    veh_long1 = int(intensidad * percent_long)
    veh_long2 = int(intensidad * (1-percent_long))
    percent_speed1 = round(random.uniform(0,1),1)
    percent_speed2 = round(random.uniform(0,1-percent_speed1),1)
    veh_speed1 = int(intensidad * percent_speed1)
    veh_speed2 = int(intensidad * percent_speed2)
    veh_speed3 = int(intensidad * (1 - percent_speed1 - percent_speed2))
    
    if(veh_long1 + veh_long2 < intensidad):
        veh_long1 += 1
        
    if(veh_speed1 + veh_speed2 + veh_speed3 < intensidad):
        veh_speed1 += 1
        
    try:
        r = requests.post(request_strHR2, json={"value": now.year}) # anio   
        r = requests.post(request_strHR0, json={"value": now.day}) # dia
        r = requests.post(request_strHR3, json={"value": now.hour}) # hora
        r = requests.post(request_strHR1, json={"value": now.month}) # mes
        r = requests.post(request_strHR4, json={"value": now.minute}) # minuto
        r = requests.post(request_strHR9, json={"value": intensidad}) # intensidad
        r = requests.post(request_strHR10, json={"value": ocupacion}) # ocupacion
        r = requests.post(request_strHR14, json={"value": velocidad}) # velocidad
        r = requests.post(request_strHR15, json={"value": longitud}) # longitud
        r = requests.post(request_strHR17, json={"value": veh_long1}) # veh_long1
        r = requests.post(request_strHR18, json={"value": veh_long2}) # veh_long2
        r = requests.post(request_strHR33, json={"value": veh_speed1}) # veh_speed1
        r = requests.post(request_strHR34, json={"value": veh_speed2}) # veh_speed2
        r = requests.post(request_strHR35, json={"value": veh_speed3}) # veh_speed3
    except: 
        print ("Error: unable to connect " + ip + ':' + port)
    return True
     
     
def sendData(now):
    with concurrent.futures.ProcessPoolExecutor(max_workers=64) as executor:          
        future_to_url  = {executor.submit(sendDataThread, now, line): line for line in font} 
        for future in concurrent.futures.as_completed(future_to_url ):          
            url = future_to_url[future]
            try:
                data = future.result()
            except Exception as exc:
                print('%r generated an exception: %s' % (url, exc))
            

    return True    


now = datetime.datetime.now()         
sendData(now)
    
    

