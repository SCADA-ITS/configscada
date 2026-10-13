from logging.handlers import RotatingFileHandler
import socket
import sys
import logging

KEEP_ALIVE_RECEIVED = '0520'
KEEP_ALIVE_SEND = '06'
END_OF_FRAME = '03'
SIGNALLING_PETITION_RECEIVED = '02201a'
SIGNALLING_PETITION_SENDED = '02209a'
STATE_PETITION_RECEIVED = '02201b'
STATE_PETITION_SENDED = '02209b'
DATE_FORMAT = '%d/%m/%Y %H:%M:%S'

HOST = '192.168.88.72'
PORT = int(sys.argv[1])

signalling = ''
signalling_aux = ''

logger = logging.getLogger("Rotating Log")
logger.setLevel(logging.DEBUG)
handler = RotatingFileHandler(f'logs/port_{PORT}.log', maxBytes=5000000, backupCount=8)
formatter = logging.Formatter('%(asctime)s - %(levelname)s - %(message)s')
handler.setFormatter(formatter)
logger.addHandler(handler)

"""
    En esta función se calcula el valor de los crc junto al protocolo Xmodem
"""
def calculate_crc_xmodem(data):
    # Polinomio generador CRC-16/XMODEM: x^16 + x^12 + x^5 + 1 (0x1021)
    polynomial = 0x1021
    crc = 0x0000  # Valor inicial del CRC

    for byte in data:
        crc ^= (byte << 8)
        for _ in range(8):
            if crc & 0x8000:
                crc = (crc << 1) ^ polynomial
            else:
                crc <<= 1
            crc &= 0xFFFF  # Limitar a 16 bits
    formatted_crc = f'0x{crc:04x}'  # Formatea el valor con ceros a la izquierda y 4 caracteres en total
    return formatted_crc

"""
    En esta función se reemplazan una serie de bytes por un valor adecuado para calcular el CRC de la trama
"""
def replace_characters(data):
    if "1082" or "1083" or "1085" or "1086" or "1090" in data:
        data = data.replace("1082", "02").replace("1083", "03").replace("1085", "05").replace("1086", "06").replace("1090", "10")
    else:
        pass
    return data


def keep_alive_request(connection, port, data):
    global signalling_aux
    logger.info(f'New request => Keep alive request from port {port}')
    logger.info(f'Received => {str(data)}')
    logger.info(f'Sending => {KEEP_ALIVE_SEND}')
    logger.debug(f'Signalling => {signalling_aux}')
    connection.sendall(bytes.fromhex(KEEP_ALIVE_SEND))
    logger.info('Data successfully submitted\n')

def signalling_request(connection, port, data):
    global signalling_aux
    logger.info(f'New request => Signalling request from port {port}')
    logger.info(f'Received => {str(data)}')
    data = data.replace(SIGNALLING_PETITION_RECEIVED, SIGNALLING_PETITION_SENDED)
    data_replace = replace_characters(data[2:-2])
    logger.debug(f'Data_replace => {data_replace}')
    data_crc = calculate_crc_xmodem(bytes.fromhex(data_replace[:-4]))
    logger.debug(f'Data_crc => {data_crc}')
    data = data[:-6] + str(data_crc)[2:] + END_OF_FRAME
    logger.info(f'Sending => {data}')
    signalling_aux = data[6:-6]
    logger.debug(f'Signalling => {signalling_aux}')
    connection.sendall(bytes.fromhex(data))
    logger.info('Data successfully submitted\n')

def state_request(connection, port, data):
    global signalling_aux
    global signalling
    logger.info(f'New request => State request from port {port}')
    logger.info(f'Received => {str(data)}')
    data = data.replace(STATE_PETITION_RECEIVED, STATE_PETITION_SENDED)
    if signalling_aux == '':
        data_replace = replace_characters(data[2:-2])
        logger.debug(f'Data_replace => {data_replace}')
        data_crc = calculate_crc_xmodem(bytes.fromhex(data_replace[:-4]))
        logger.debug(f'Data_crc => {data_crc}')
        data = data[:-6] + str(data_crc)[2:] + END_OF_FRAME
        logger.debug(f'Data => {data}')
    else:
        data_replace = replace_characters(signalling_aux)
        logger.debug(f'Data_replace => {data_replace}')
        data_crc = calculate_crc_xmodem(bytes.fromhex(STATE_PETITION_SENDED[2:] + signalling_aux))
        logger.debug(f'Data_crc => {data_crc}')
        data = data[:-6] + signalling_aux + str(data_crc)[2:] + END_OF_FRAME
        logger.debug(f'Data => {data}')
    signalling = data
    logger.debug(f'Signalling => {signalling}')
    logger.info(f'Sending => {signalling}')
    connection.sendall(bytes.fromhex(signalling))
    logger.info('Data successfully submitted\n')

def empty_data(data, port):
    global signalling_aux
    logger.warning(f'Empty value on port {port}')
    logger.info(f'Received => {str(data)}')
    logger.debug(f'Signalling => {signalling_aux}')
    logger.error('Data not sent\n')

def error(port, data):
    global signalling_aux
    global signalling
    logger.error(f'Request not supported on port {port}')
    logger.info(f'Received => {str(data)}')
    signalling = ''
    signalling = data + signalling_aux
    logger.debug(f'Signalling => {signalling_aux}')
    logger.error('Data not sent\n')

def port_connections(PORT):
    try:
        # Create a TCP/IP socket
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        print(f"Running at port {PORT}...")

        # Bind the socket to the port
        server_address = (HOST, PORT)
        logger.info(f'Starting up on {server_address[0]} port {server_address[1]}')
        sock.bind(server_address)

        # Listen for incoming connections
        sock.listen(1)

        while True:
            # Wait for a connection
            logger.info(f'Waiting for a connection on port {PORT} ...')
            connection, client_address = sock.accept()
            logger.warning(f'New connection from {client_address[0]}:{client_address[1]} on port {PORT}')

            # Receive the data in small chunks and retransmit it
            while True:
                try:
                    data = connection.recv(1024)
                    data = data.hex()
                    code = data[0:6]

                    if code == KEEP_ALIVE_RECEIVED:
                        keep_alive_request(connection, PORT, data)
                    elif code == SIGNALLING_PETITION_RECEIVED:
                        signalling_request(connection, PORT, data)
                    elif code == STATE_PETITION_RECEIVED:
                        state_request(connection, PORT, data)
                    elif data == '':
                        empty_data(data, PORT)
                        break
                    else:
                        error(PORT, data)
                        break

                except ConnectionResetError:
                    # Handle connection reset by peer
                    print(f"Connection reset by peer. Reconnecting on port {PORT}...")
                    connection.close()
                    break

    except KeyboardInterrupt:
        print(f"Closing port {PORT}...")
        sock.close()
        print(f'Port {PORT} closed.')
    except NameError as err:
        print(f"ERROR => Variable {err} not found in local or global scope. Closing port {PORT}...")
        sock.close()
        print(f'Port {PORT} closed.')
    except OSError as err:
        print(err)

port_connections(PORT)