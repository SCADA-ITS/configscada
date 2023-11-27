import psycopg2
import os

# Configura los parámetros de conexión a tu base de datos PostgreSQL
config = {
    'host': '192.168.88.52',
    'port': '5430',
    'database': 'rits',
    'user': 'rits',
    'password': 'rits',
}


# Conectarse a la base de datos
conexion = psycopg2.connect(**config)
cursor = conexion.cursor()

# Pedir al usuario que ingrese el parámetro
parametro = input("Ingresa el parámetro: ")

# Pedir al usuario el ID de área
id_area = input("Ingresa el ID de área: ")

# Ejecutar la consulta SQL con el parámetro
consulta = """
    SELECT conf.elements.element_type_id, elements.element_id, master.element_types.alias
    FROM conf.elements
    INNER JOIN master.element_types ON conf.elements.element_type_id = master.element_types.element_type_id
    WHERE conf.elements.alias LIKE %s
"""
cursor.execute(consulta, (parametro + '%',))

# Obtener los resultados de la consulta
resultados = cursor.fetchall()

# Verificar si se encontraron resultados
if resultados:
    # Crear una carpeta con el mismo nombre que el parámetro
    carpeta_nombre = parametro
    os.makedirs(carpeta_nombre, exist_ok=True)
    
    # Establecer la carpeta como directorio de trabajo
    os.chdir(carpeta_nombre)

    # Crear archivos SQL por cada element_type_id
    archivos_sql = {}

    for resultado in resultados:
        element_type_id = resultado[0]
        element_id = resultado[1]
        alias = resultado[2]
        
        
        if element_type_id not in archivos_sql:
            archivos_sql[element_type_id] = []

        archivos_sql[element_type_id].append((element_id, alias))

    # Escribir los datos en archivos SQL por cada element_type_id
    for element_type_id, elementos in archivos_sql.items():
        archivo_sql_nombre = f"{element_type_id}_{elementos[0][1]}.sql"  # Usar el alias como parte del nombre


        with open(archivo_sql_nombre, 'w') as archivo_sql:
            archivo_sql.write("INSERT INTO conf.management_area_elements (management_area_id, element_type_id, element_id, enabled, visible, created_at, updated_at) VALUES\n")

            for i, element_id in enumerate(elementos):
                if i == len(elementos) - 1:  # Verificar si es la última línea
                    archivo_sql.write(f"({id_area}, {element_type_id}, {element_id[0]}, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);\n")
                else:
                    archivo_sql.write(f"({id_area}, {element_type_id}, {element_id[0]}, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),\n")

        print(f"Archivo SQL '{archivo_sql_nombre}' creado para element_type_id {element_type_id}")

    print(f"Archivos SQL creados exitosamente en la carpeta '{carpeta_nombre}'.")
else:
    print("No se encontraron resultados.")

# Cerrar la conexión a la base de datos
conexion.close()