import configparser
import random
import time
from pymodbus.client import ModbusTcpClient

pair_list = []

def cambiar_holding_register(ip, puerto, init_register, addresses):
    # Crea un cliente Modbus TCP
    client = ModbusTcpClient(ip, port=puerto, timeout=0.5)
    
    try:
        # Conecta al dispositivo
        client.connect()

        for i in range(0, len(addresses), 100):
            client.write_registers(1 + int(int(init_register) + 100 * (i/100)), addresses[i:i + 100])

    except Exception as e:
        print(f'Error: {e}')
    finally:
        print("Se han actualizado " + str(len(addresses)) + " registros desde el " + init_register)
        
        # Cierra la conexión
        client.close()

def main():
    # Lee la configuración desde el archivo properties
    config = configparser.ConfigParser()
    config.read('config_measures.properties')
    
    ip = config.get('Modbus', 'ip')
    puerto = int(config.get('Modbus', 'puerto'))
    
    nodos_principales = config.sections()
    
    while True:
        
        for nodo in nodos_principales:
            if nodo != 'Modbus':
                
                pair_list = []
                addresses = []
                for key, field_value in config[nodo].items():
                    lines = field_value.split('\n')
                    min = 0;
                    max = 0;
                    mod = "";
                    
                    if key.rstrip(" \t\n") == 'initregister':
                        init_register = field_value;
                    elif key.rstrip(" \t\n") == 'numequipments':
                        num_equipments = field_value;
                    else:
                    
                        for line in lines:
                            parts = line.split('=')
                            if len(parts) == 2:
                                name = parts[0]
                                value = parts[1]
                                
                                if name.rstrip(" \t\n") == 'min':
                                    min = int(value)
                                elif name.rstrip(" \t\n") == 'max':
                                    max = int(value)
                                elif name.rstrip(" \t\n") == 'mod':
                                    mod = value
                    
                        pair_list.append({"min_range":min, "max_range":max, "mod":mod, "name":key})
                
                for i in range(int(num_equipments)):
                    for pair in pair_list:
                        if(pair['mod'] == ""):
                            random_measure = round(random.randint(pair['min_range'], pair['max_range']))
                            #print("Creando medida " + str(pair['name']) + "para equipo " + nodo + "[" + str(i + 1) + 
                            #      "] con rango entre " + str(pair['min_range']) + ' y ' + str(pair['max_range']) +
                            #      " = " + str(random_measure))
                            addresses.append(random_measure);   
                        else:
                            parts = pair['mod'].split(',')
                            random_measure = round(random.randint(1, len(parts)))
                            addresses.append(int(parts[random_measure - 1]))                                                         

                cambiar_holding_register(ip, puerto, init_register, addresses)
        time.sleep(10)

if __name__ == '__main__':
    main()