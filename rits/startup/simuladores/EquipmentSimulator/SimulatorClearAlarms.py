import configparser
from pymodbus.client import ModbusTcpClient

def cambiar_holding_register(ip, puerto, init_register, addresses):
    # Crea un cliente Modbus TCP
    client = ModbusTcpClient(ip, port=puerto, timeout=0.5)
    
    try:
        # Conecta al dispositivo
        client.connect()

        for i in range(0, len(addresses), 100):
            client.write_registers(1 + int(init_register + 100 * (i/100)), addresses[i:i + 100])

    except Exception as e:
        print(f'Error: {e}')
    finally:
        print("Se han actualizado " + str(len(addresses)) + " registros desde el " + str(init_register))
        
        # Cierra la conexión
        client.close()

def main():
    # Lee la configuración desde el archivo properties
    config = configparser.ConfigParser()
    config.read('config_measures.properties')
    
    ip = config.get('Modbus', 'ip')
    puerto = int(config.get('Modbus', 'puerto'))

    cambiar_holding_register(ip, 503, 1, [0] * 3000)

if __name__ == '__main__':
    main()