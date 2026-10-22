#!/usr/bin/python
import psycopg2
import time
import datetime
import os
import random
import sys

insertion = float(sys.argv[1])
print(insertion)
try:
    connection=psycopg2.connect(
        host="192.168.88.191",
        user="rits",
        password="rits",
        port="5430",
        database="rits"
    )
    print("conectado")

    dirname = os.path.dirname(__file__) 
    while True:
        starttime = time.time()
        now = datetime.datetime.now()
        dirname = os.path.dirname(__file__)
        f = open(os.path.join(dirname, "ETD_KINEO" + ".csv"));
        leida = f.readline()
        clave = leida.split(";")

        nivel_servicio = random.randrange(1,4)
        if nivel_servicio == 3 : #Nivel de servicio 3
            occupancy = random.randrange(35,100)
            speed= random.randrange(32,72)
        elif nivel_servicio == 2 : #Nivel de servicio 2
            occupancy = random.randrange(16,28)
            speed = random.randrange(32,72)
        elif nivel_servicio == 1 : #Nivel de servicio 1
            occupancy = random.randrange(1,16)
            speed = random.randrange(72,140)
        else :                  #Nivel de servicio 4
            occupancy = random.randrange(35,100)
            speed = random.randrange(16,28)


        longitud = random.randrange(40,100)
        volumen0 = random.randrange(0,3)
        volumen1 = random.randrange(0,3)
        volumen2 = random.randrange(0,3)
        volumen3 = random.randrange(0,3)
        volumen4 = random.randrange(0,3)
        volumen5 = random.randrange(0,3)
        volumen6 = random.randrange(0,3)
        intensidad = volumen0 + volumen1 + volumen2 + volumen3 + volumen4 + volumen5 + volumen6   
        percent_speed1 = round(random.uniform(0,1),1)
        percent_speed2 = round(random.uniform(0,1-percent_speed1),1)
        veh_speed1 = int(intensidad * percent_speed1)
        veh_speed2 = int(intensidad * percent_speed2)
        veh_speed3 = int(intensidad * (1 - percent_speed1 - percent_speed2))
    

        if(veh_speed1 + veh_speed2 + veh_speed3 < intensidad):
            veh_speed1 += 1

        for line in f:
            valor = line.split(";")
            det_order = valor[clave.index("det_order")]
            print(det_order)
            date = str(now)
            print(date)  
            cursor=connection.cursor()
            cursor.execute("INSERT INTO conf.simulador_etd VALUES('"+ det_order + "','" + date + "',1,'" + str(intensidad) + "','" + str(occupancy) + "','" + str(speed) + "',0,0,0,0,0,0,'" + str(longitud) + "','" + str(volumen0) + "','" + str(volumen1) + "','" + str(volumen2) + "','" + str(volumen3) + "','" + str(volumen4) + "','" + str(volumen5) + "','" + str(volumen6) +"','" + str(veh_speed1) + "','" + str(veh_speed2) + "','" + str(veh_speed3) + "',0);")
            connection.commit()
        f.close()
        time.sleep( insertion - ((time.time() - starttime) % 1.0)) 

        
except Exception as ex:
    print(ex)


    