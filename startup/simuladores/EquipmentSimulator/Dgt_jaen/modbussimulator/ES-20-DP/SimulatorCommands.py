import configparser
import time
from pymodbus.client import ModbusTcpClient
from pymodbus import ModbusException

pair_list = []

def cambiar_holding_register(ip, puerto, offset_measures, offset_commands, equipments, commands):
    # Crea un cliente Modbus TCP
    client = ModbusTcpClient(ip, port=puerto, timeout=0.5)
    
    try:
        # Conecta al dispositivo
        client.connect()

        for i in range(0, int(equipments) * int(commands),100):
            num_registers = int(equipments) * int(commands);

            print(f"Leyendo {num_registers} posiciones desde el offset {offset_commands}")
            
            registers = client.read_holding_registers(int(offset_commands), num_registers, slave=1)
            print("Lectura de registros: " + str(registers.registers))
            
            client.write_registers(int(offset_measures), registers.registers, slave=1)
            
    except ModbusException as modbus_error:
        print(f"Modbus error {modbus_error}")
    except Exception as e:
        print(f'Error: {e}')
    finally:
        #print("Se han actualizado " + str(len(addresses)) + " registros desde el " + init_register)
        
        # Cierra la conexión
        client.close()

def main():
    file = '/home/admin/app/modbussimulator/ES-20-DP/ES-20-DP_commands.properties'
    # Lee la configuración desde el archivo properties
    config = configparser.ConfigParser()
    config.read(file)
    
    ip = config.get('Modbus', 'ip')
    puerto = int(config.get('Modbus', 'puerto'))
    
    nodos_principales = config.sections()
    
    while True:
        
        for nodo in nodos_principales:
            if nodo != 'Modbus':
                
                offset_measures = 0
                offset_commands = 0
                equipments = 0
                commands = 0
                
                for key, field_value in config[nodo].items():
                    
                    if key.rstrip(" \t\n") == 'measuresoffset':
                        offset_measures = field_value;
                    elif key.rstrip(" \t\n") == 'commandsoffset':
                        offset_commands = field_value;
                    elif key.rstrip(" \t\n") == 'numequipments':
                        equipments = field_value;
                    elif key.rstrip(" \t\n") == 'numcommands':
                        commands = field_value;                                                    
                cambiar_holding_register(ip, puerto, offset_measures, offset_commands, equipments, commands)
        time.sleep(1)

if __name__ == '__main__':
    main()
