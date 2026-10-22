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
FC_5 = 5 # Force Single Coil
FC_1 = 1 # Read Coils

if __name__ == '__main__':
    
    if len(sys.argv) < 4:
        print("Usage:")
        print("  arg1 = IP PLC")
        print("  arg2 = port")
        print("  arg3 = direction ")
        print("  arg4 = val (ON or OFF) ")
        exit()
        
    TCP_IP = sys.argv[1]
    TCP_PORT = int(sys.argv[2])
    ini_dir = int(sys.argv[3])
    val = sys.argv[4]
    
# Print cabecera de datos (solo a efectos de debug)
    
    #print("\nMODBUS PACKET HEADER")
    #print(" Transaction Identifier : %s" %data[0])
    #print(" Protocol Identifier : %s" %data[1])
   # print(" Length : %s" %data[2])
   # print(" Unit Identifier : %s" %data[3])
   # print(" Function Code : %s" %data[4])
    #print(" Byte Count : %s" %data[5])

    mbuswrite = mbus.mbus(TCP_IP,TCP_PORT,FC_5,ini_dir,1)          
    mbuswrite.write_data(val)
    mbuswrite.closeSCK()
    
    mbusread = mbus.mbus(TCP_IP,TCP_PORT,FC_1,ini_dir,8)
    data = mbusread.read_data()
    mbusread.closeSCK()
    
    print("\nREGISTER VALUES")
    num_reg = len(data) - 6
    for i in range(6, 6 + num_reg):
        j = 0  
        index = j + 8*(i-6)
        mask = 0x01 << j   
        currentRegister = str(index + int(ini_dir)).zfill(2)
        print(" Register DO #%s %s " %(currentRegister,(data[i] & mask) >> j))
    
    
    

