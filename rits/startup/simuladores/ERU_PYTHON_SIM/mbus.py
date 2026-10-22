#!/usr/bin/python
# -*- coding: utf-8 -*-

# module: mbus.py 
import socket
import struct
import time
import sys

#---------------------------------
# Modbus Function Codes
# 1 : B, # Read Coils (1 byte)
# 2 : B, # Read Input Discrete Registers (1 byte)
# 3 : H, # Read Holding Registers (2 byte)
# 4 : H  # Read Input Registers (2 byte)
#-------------------------------------------------
# 5 : B  # Force Single Coil (1 byte)
# 6 : H  # Preset Single Register (2 byte)

FC_READ_COILS = 1
FC_READ_INPUT_DISCRETE_REGISTERS = 2
FC_READ_HOLDING_REGISTER = 3
FC_READ_INPUT_REGISTER = 4

TCP_PORT = 502
BUFFER_SIZE = 0

class mbus:

    def __init__(self, TCPaddress,TCPport,FC,initRegister,numRegister):       
        self.TCPport = TCPaddress
        self.sck = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.sck.connect((TCPaddress, TCPport))
        self.unitId = 1 # Unidad Modbus
        self.functionCode = FC # Function Code
        self.startRegister = initRegister # Registro inicial 
        self.numRegister = numRegister # Registros a leer
    
                
    def read_data(self):        
        try:
            # estructura paquete petición de datos
            req = struct.pack('>3H 2B 2H', 0, 0, 6, int(self.unitId), int(self.functionCode), int(self.startRegister), int(self.numRegister))
            self.sck.send(req)
         
            # Calculo la estructura y buffer de datos
            BUFFER_SIZE = (3*2) + (3*1) + (int(self.numRegister)*2)

            rec = self.sck.recv(BUFFER_SIZE)
            data_size = len(rec) - (3*2) - (3*1)
            if self.functionCode == FC_READ_HOLDING_REGISTER: 
                data_size = int(data_size /2);
                s = struct.Struct('>3H 3B' + str(data_size)+ 'H')
                data = s.unpack(rec)
            elif self.functionCode == FC_READ_INPUT_DISCRETE_REGISTERS: 
                s = struct.Struct('>3H 3B' + str(data_size)+ 'B')
                data = s.unpack(rec)
            elif self.functionCode == FC_READ_COILS: 
                s = struct.Struct('>3H 3B' + str(data_size)+ 'B')
                data = s.unpack(rec)
            elif self.functionCode == FC_READ_INPUT_REGISTER: 
                data_size = int(data_size /2);
                s = struct.Struct('>3H 3B' + str(data_size)+ 'H')
                data = s.unpack(rec)
            
            return data
        except:    
            print ("error")
    
    def write_data(self,val):
        if val == "ON":
          req = struct.pack('>3H 2B 2H', 0, 0, 6, int(self.unitId), int(self.functionCode), int(self.startRegister), int(0xFF00))
        elif val == "OFF":
          req = struct.pack('>3H 2B 2H', 0, 0, 6, int(self.unitId), int(self.functionCode), int(self.startRegister), int(0x0000))
          
        self.sck.send(req)
           # Calculo la estructura y buffer de datos
        BUFFER_SIZE = (3*2) + (3*1) + (int(self.numRegister)*2)

        rec = self.sck.recv(BUFFER_SIZE)
        data_size = len(rec) - (3*2) - (3*1)
          
        
    def closeSCK(self):
        try:
            self.sck.close()
        except:    
            print ("\nError cerrando conexión TCP...")

    
