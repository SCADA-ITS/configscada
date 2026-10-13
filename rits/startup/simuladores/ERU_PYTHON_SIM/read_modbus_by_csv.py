#!/usr/bin/python
# -*- coding: utf-8 -*-

#---------------------------------
# Modbus Function Codes
# 1 : B, # Read Coils (1 byte)
# 2 : B, # Read Input Discrete Registers (1 byte)
# 3 : H, # Read Holding Registers (2 byte)
# 4 : H  # Read Input Registers (2 byte)
#-------------------------------------------------


import socket
import struct
import time
import sys
import mbus
import os




BUFFER_SIZE = 0

# Direcciones TCP/IP para Classic1 y Classic2
TCP_IP = '192.168.88.189'
TCP_PORT = 1036
FC_1 = 1 # Read Coils
FC_2 = 2 # Read Input Discrete Registers
FC_4 = 4 # Read Input Registers
FC_5 = 5 # Force Single Coil

INI_REGISTER = 0
NUMBER_REGISTERS = 8

INDEX_DIR = 0
INDEX_NAME = 1
INDEX_ALIAS = 2
INDEX_LOW_AI = 3
INDEX_HIGH_AI = 4
INDEX_LOW_SENSOR = 5
INDEX_HIGH_SENSOR = 6
INDEX_MED = 7

if __name__ == '__main__':
    
    if len(sys.argv) < 3:
        print("Usage:")
        print("  arg1 = IP PLC")
        print("  arg2 = port")
        print("  arg3 = File Name csv ")
        exit()
        
    TCP_IP = sys.argv[1];
    TCP_PORT = int(sys.argv[2]);
    csv_name = sys.argv[3];
    
    dirname = os.path.dirname(__file__)
    font = open(os.path.join(dirname, csv_name));
    leida = font.readline()
    clave = leida.split(";")


    EDList = list() 
    AIList = list()
  
    
    for line in font:
        valor = line.split(";")
        
        if('ED' in valor[INDEX_DIR]):
            EDList.append([valor[INDEX_DIR],valor[INDEX_NAME],valor[INDEX_ALIAS]])
            
        elif ('AI' in valor[INDEX_DIR]):
            AIList.append([valor[INDEX_DIR],
                           valor[INDEX_NAME],
                           valor[INDEX_ALIAS],
                           valor[INDEX_LOW_AI],
                           valor[INDEX_HIGH_AI],
                           valor[INDEX_LOW_SENSOR],
                           valor[INDEX_HIGH_SENSOR],
                           valor[INDEX_MED]
                           ])
    
    
    if len(EDList) > 0 :
        mbusED = mbus.mbus(TCP_IP,TCP_PORT,FC_2,INI_REGISTER,len(EDList))
        data = mbusED.read_data()
        mbusED.closeSCK()
    
    print("\nREGISTER VALUES")
    num_reg = len(data) - 6
    for i in range(6, 6 + num_reg):
        for j in range(0,8):   
            index = j + 8*(i-6)
            mask = 0x01 << j   
            currentRegister = str(index + int(INI_REGISTER)).zfill(2)
            print(" Register DI #%s %s: %s: %s " %(currentRegister, EDList[index][INDEX_ALIAS], EDList[index][INDEX_NAME],(data[i] & mask) >> j))
    
# Print cabecera de datos (solo a efectos de debug)
    
    #print("\nMODBUS PACKET HEADER")
    #print(" Transaction Identifier : %s" %data[0])
    #print(" Protocol Identifier : %s" %data[1])
   # print(" Length : %s" %data[2])
   # print(" Unit Identifier : %s" %data[3])
   # print(" Function Code : %s" %data[4])
    #print(" Byte Count : %s" %data[5])
    if len(AIList) > 0 :
        mbusAI = mbus.mbus(TCP_IP,TCP_PORT,FC_4,INI_REGISTER,len(AIList))
        data = mbusAI.read_data()
        mbusAI.closeSCK()
        
        # Print valores de los registros
        print("\nREGISTER VALUES")
        num_reg = len(data) - 6
        for i in range(6, 6 + num_reg): 
            currentRegister = str(int(INI_REGISTER) + (i-6) ).zfill(2)
            
            y1 = int(AIList[i - 6][INDEX_HIGH_SENSOR])
            y2 = int(AIList[i - 6][INDEX_LOW_SENSOR])
            
            x1 = int(AIList[i - 6][INDEX_HIGH_AI])
            x2 = int(AIList[i - 6][INDEX_LOW_AI])
            
            m = (y1 - y2) / (x1 - x2)
    
            b = y2 - m*x2
            y = m*data[i] + b
            
            print(" Register AI #%s %s: %s: %s -> %f %s (multiplier : %f , offset: %f) " 
                  %(currentRegister,
                     AIList[i - 6][INDEX_ALIAS],
                     AIList[i - 6][INDEX_NAME],
                     data[i],
                     y,
                     AIList[i - 6][INDEX_MED],
                     m,
                     b))
              
    
    
    

