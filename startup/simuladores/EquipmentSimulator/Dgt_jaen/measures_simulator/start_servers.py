import os
import concurrent.futures

# Obtiene el directorio actual
directorio_actual = os.getcwd()

# Lista todas las carpetas en el directorio actual
carpetas = [nombre for nombre in os.listdir(directorio_actual) if os.path.isdir(os.path.join(directorio_actual, nombre))]

def main(carpetas):
    # Imprime la lista de carpetas
    ruta_archivo = os.path.join(directorio_actual, carpetas)
    # Verifica si el elemento es un directorio y contiene el archivo que deseas ejecutar
    if os.path.isdir(ruta_archivo):
        # Ejecuta el archivo
        os.system(f'sh {ruta_archivo}/start.sh')

#Creamos un hilo por cada plc que tenga la lista "carpetas" y lanzamos la funcion "main" con la variable "carpetas" como parámetro
with concurrent.futures.ThreadPoolExecutor(len(carpetas)) as executor:
    executor.map(main, carpetas)