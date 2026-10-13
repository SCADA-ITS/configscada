import argparse
import psycopg2
import os

file_counter = {}


def connect_to_database(host, port):
    try:
        cnx = psycopg2.connect(
            host=host,
            port=port,
            user='rits',
            password='rits',
            database='rits'
        )
        return cnx
    except psycopg2.Error as err:
        print(f'Error al conectar a la base de datos: {err}')
        return None


def table_exists(cursor, table_name):
    table_name = table_name.split('.')[1]
    query = f"SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = '{table_name}')"
    cursor.execute(query, (table_name,))
    return cursor.fetchone()[0]


def get_table_content(cursor, table_name):
    query = f'SELECT * FROM {table_name}'
    try:
        cursor.execute(query)
        return cursor.fetchall()
    except psycopg2.Error as err:
        print(f'Error al obtener contenido de la tabla {table_name}: {err}')
        return []


def is_column_int8(column):
    return column.type_code == psycopg2.extensions.INTEGER and column.internal_size == 8


def prepare_insert_statement(cursor, table_name, table_content, index):
    columns = ', '.join(f"{column[0]}" for column in cursor.description)
    values = []
    for row in table_content:
        row_values = []
        for i, value in enumerate(row):
            column_name = cursor.description[i][0]
            column = cursor.description[i]
            if is_column_int8(column) and value is not None:
                row_values.append(str(value))
            elif column_name.lower() in ['created_at', 'updated_at']:
                row_values.append('CURRENT_TIMESTAMP')
            elif value is None:
                row_values.append('NULL')
            elif isinstance(value, str):
                row_values.append(f"'{value}'")
            else:
                row_values.append(str(value))
        values.append('(' + ', '.join(row_values) + ')')

    index_str = f'{index:02d}'

    statement = f"{index_str}.{table_name}.sql\n"
    statement = "INSERT INTO {} ({}) VALUES\n{};\n".format(table_name, columns, ',\n'.join(values))

    file_name = f'{index_str}.{table_name}.sql'
    return statement, file_name


def write_insert_statement_to_file(directory, table_name, statement):
    if directory not in file_counter:
        file_counter[directory] = 1
    else:
        file_counter[directory] += 1

    file_index = str(file_counter[directory]).zfill(2)
    file_name = os.path.join(directory, f'{file_index}.{table_name}.sql')
    with open(file_name, 'w') as file:
        file.write(f'{statement}\n')
    print(f'Sentencia INSERT SQL escrita en el archivo: {file_name}')


def check_table_existence(cursor, table_name):
    if not table_exists(cursor, table_name):
        print(f'La tabla {table_name} no existe. Continuando con las siguientes tablas.')
        return False
    return True


def main(host, port):
    cnx = connect_to_database(host, port)
    if cnx is None:
        return

    cursor = cnx.cursor()

    file_directory = os.path.dirname(os.path.abspath(__file__))
    backup_file_path = os.path.join(file_directory, 'tables_backup.txt')

    with open(backup_file_path, 'r') as file:
        table_names = file.read().splitlines()

    active_directory = 'tables'

    for index, line in enumerate(table_names, start=1):
        if line.startswith("+"):
            directory_name = line[1:].strip()
            directory_name = os.path.join('tables', directory_name)
            os.makedirs(directory_name, exist_ok=True)
            active_directory = directory_name
        else:
            table_name = line.strip()
            if not check_table_existence(cursor, table_name):
                continue

            table_content = get_table_content(cursor, table_name)

            if not table_content:
                print(f'Error al obtener contenido de la tabla {table_name}. Continuando con las siguientes tablas.')
                continue

            insert_statement, file_name = prepare_insert_statement(cursor, table_name, table_content, index)

            active_directory = directory_name if 'directory_name' in locals() else 'tables'
            write_insert_statement_to_file(active_directory, table_name, insert_statement)

    cursor.close()
    cnx.close()


if __name__ == '__main__':
    parser = argparse.ArgumentParser(
        description='Script para obtener contenido de tablas de una base de datos y preparar sentencias INSERT SQL',
        epilog='Para seleccionar las tablas a generar, modificar el archivo --> /_rits/startup/scripts/backup_bbdd/tables_backup.txt')
    parser.add_argument('host', type=str, help='Dirección IP de la base de datos')
    parser.add_argument('port', type=int, help='Puerto de la base de datos')

    args = parser.parse_args()

    main(args.host, args.port)
