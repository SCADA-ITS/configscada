import configparser
import random
import time
from pymodbus.client import ModbusTcpClient

pair_list = []

def cambiar_holding_register(ip, puerto, offset_measures1, offset_commands1, offset_measures2, offset_commands2, offset_measures3, offset_commands3):
    # Crea un cliente Modbus TCP
    client = ModbusTcpClient(ip, port=puerto, timeout=0.5)
    
    try:
        # Conecta al dispositivo
        client.connect()

        registers_fan_order = client.read_holding_registers(int(offset_commands1), 1, slave=1)
        registers_Door = client.read_holding_registers(int(offset_commands2), 1, slave=1)
        registers_grid = client.read_holding_registers(int(offset_commands3), 1, slave=1)
        
        if (registers_fan_order.registers[0] == 0):
            client.write_registers(int(offset_measures1), 0)
            client.write_registers(int(offset_measures2), 0)
            client.write_registers(int(offset_measures3), 1)            
        elif (registers_fan_order.registers[0] == 1 and registers_Door.registers[0] == 1 and registers_grid.registers[0] == 0):
            client.write_registers(int(offset_measures1), 0)
            client.write_registers(int(offset_measures2), 1)
            client.write_registers(int(offset_measures3), 0)
        elif (registers_fan_order.registers[0] == 1 and registers_Door.registers[0] == 0 and registers_grid.registers[0] == 0):
            client.write_registers(int(offset_measures1), 1)
            client.write_registers(int(offset_measures2), 0)
            client.write_registers(int(offset_measures3), 0)            

    except Exception as e:
        print(f'Error: {e}')
    finally:
        #print("Se han actualizado " + str(len(addresses)) + " registros desde el " + init_register)
        
        # Cierra la conexión
        client.close()

def main():
    # Lee la configuración desde el archivo properties
    config = configparser.ConfigParser()
    config.read('/home/admin/measures_simulator/ES-01-GA-4/config_commands.properties')
    
    ip = config.get('Modbus', 'ip')
    puerto = int(config.get('Modbus', 'puerto'))
    
    nodos_principales = config.sections()

    while True:
        
        for nodo in nodos_principales:
            if nodo != 'Modbus':
                

                
                for key, field_value in config[nodo].items():
                    
                    if key.rstrip(" \t\n") == 'measuresoffset1':
                        offset_measures1 = field_value;
                    elif key.rstrip(" \t\n") == 'measuresoffset2':
                        offset_measures2 = field_value;
                    elif key.rstrip(" \t\n") == 'measuresoffset3':
                        offset_measures3 = field_value;                    
                    elif key.rstrip(" \t\n") == 'commandsoffset1':
                        offset_commands1 = field_value;
                    elif key.rstrip(" \t\n") == 'commandsoffset2':
                        offset_commands2 = field_value;
                    elif key.rstrip(" \t\n") == 'commandsoffset3':
                        offset_commands3 = field_value;                                                                   

                cambiar_holding_register(ip, puerto, offset_measures1, offset_commands1, offset_measures2, offset_commands2, offset_measures3, offset_commands3)
        time.sleep(1)

if __name__ == '__main__':
    main()