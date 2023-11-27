#LIBRARIES
import openpyxl as xl
from openpyxl.styles import Alignment, Font
import os
import psycopg2
import sys
from translation import translation

#EXCEL CREATION
excel_file = xl.Workbook()

#DB CONNECTION
HOST = sys.argv[1]
PORT = sys.argv[2]
'''HOST = '192.168.88.177'
PORT = '5430'''
DB = "rits"
USER = "rits"
PASS = "rits"



#Function that returns all the information of the element_types from the master folder
def info_element_types():
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()
        cur.execute("SELECT distinct element_type_id FROM conf.elements WHERE element_type_id  != 1000 ORDER BY element_type_id ASC;")

        info = cur.fetchall()

        cur.close()
        connection.close()
        return info

    except AttributeError:
        print(ValueError)

    
#Adds to the excel the label alias, alarm level id and enabled tags of all the measures.
def sheet_content (sheet_name, language):
    #for i in range(0,25):
    id =sheet_name.split('.-')[0]
    active_sheet = excel_file[sheet_name]
    excel_file.active = active_sheet
    #element_type_param_id, param_type_id, label_alias, enabled
    active_sheet.cell(row = 1, column = 1).value = sheet_name
    active_sheet.append(('ID','VALUE'))
    

    info = ['1', True ]
    active_sheet.append(info)
    info = ['2', True ]
    active_sheet.append(info)
    info = ['3', 'NULL' ]
    active_sheet.append(info)
    info = ['4', 'NULL']
    active_sheet.append(info)
    info = ['5', 'NULL']
    active_sheet.append(info)
    info = ['6', 'NULL']
    active_sheet.append(info)
    info = ['7', True]
    active_sheet.append(info)
    info = ['8', True]
    active_sheet.append(info)
    info = ['9', False]
    active_sheet.append(info)
    info = ['10', True]
    active_sheet.append(info)
    info = ['11', True]
    active_sheet.append(info)
    info = ['12', True]
    active_sheet.append(info)
    info = ['13', True]
    active_sheet.append(info)
    info = ['14', False]
    active_sheet.append(info)
    info = ['15', False]
    active_sheet.append(info)
    info = ['16', 'img/grid/element_type_'+str("{:0>2d}".format(int(id)))+'.png']
    active_sheet.append(info)
    info = ['17', True]
    active_sheet.append(info)
    info = ['18', 'NULL']
    active_sheet.append(info)
    info = ['19', 'NULL']
    active_sheet.append(info)
    info = ['20', True]
    active_sheet.append(info)
    info = ['21', 'data']
    active_sheet.append(info)
    info = ['22', 'NULL']
    active_sheet.append(info)
    info = ['23', True]
    active_sheet.append(info)
    info = ['24', True]
    active_sheet.append(info)
    info = ['25', False]
    active_sheet.append(info)

    max_columns = active_sheet.max_column +1
    max_rows = active_sheet.max_row +1

    #Fixs the column sizes
    for col in active_sheet.columns:
        max_length = 0
        try:
            
            column = col[1].column_letter # Get the column name
            for cell in col:
                # Necessary to avoid error on empty cells
                if len(str(cell.value)) > max_length:
                    max_length = len(str(cell.value))
        except:
            pass
        adjusted_width = (max_length + 5) * 1.1
        active_sheet.column_dimensions[column].width = adjusted_width

    #Pretty drawing
    title_font = Font(size=14, bold=True, color='00800000')
    cell_alignment =  Alignment(horizontal='center', vertical='center')
    for i in range (1,max_rows):
        for j in  range (1, max_columns):
            if (i == 1) or (i == 2):
                active_sheet.cell(row = i, column=j).font = title_font
            active_sheet.cell(row=i, column=j).alignment = cell_alignment
    return True


#Consults info for a given element id
def element_info(id):
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()
        consult = "select alias from master.element_types where element_type_id = " +str(id) +";"
        cur.execute(consult)
        info = cur.fetchone()
        cur.close()
        connection.close()
        return info[0]

    except AttributeError:
        print(ValueError)    


def first_page():
    excel_file.create_sheet('INFO')
    active_sheet = excel_file['INFO']
    excel_file.active = active_sheet
    active_sheet.append(('ID','NAME', 'DESCRIPTION'))
    
    info = ['1', 'multiselect', 'Habilita multiselección de filas. Valor = true/false/NULL']
    active_sheet.append(info)
    info = ['2', 'sort', 'Habilitar ordenación. Valor =  true/false/NULL']
    active_sheet.append(info)
    info = ['3', 'sort_field_id', 'Identificador de grid_fields de la columna a ordenar. Valor = campo grid_fields.id']
    active_sheet.append(info)
    info = ['4', 'sort_field_order', 'Permite seleccionar el sentido del orden. Valor = asc/desc']
    active_sheet.append(info)
    info = ['5', 'filter_field_id', 'Selecciona el campo a filtrar. Valor: campo grid_fields.id']
    active_sheet.append(info)
    info = ['6', 'filter_field_value', 'Valor del filtro a aplicar. Valor: ']
    active_sheet.append(info)
    info = ['7', 'tb_main', 'Mostrar toolbar con titulo de tabla e icono representativo. Valor: true/false/NULL' ]
    active_sheet.append(info)
    info = ['8', 'tb_main_segmented', 'Habilitar botón mostrar/ocultar vista detallada. Valor: true/false/NULL' ]
    active_sheet.append(info)
    info = ['9', 'show_details', 'Visualizar detalles. Valor: true/false/NULL' ]
    active_sheet.append(info)
    info = ['10', 'tb_actions', 'Mostrar toolbar con botones de comando para que el usuario realice acciones. Valor: true/false/NULL' ]
    active_sheet.append(info)
    info = ['11', 'tb_actions_buttons_commons', 'Muestra el primer grupo de botones comunes en cualquier contexto. Valor: true/false/NULL' ]
    active_sheet.append(info)
    info = ['12', 'tb_actions_buttons_commands', 'Muestra el segundo grupo de botones de comando específicos asociados al grid. Valor: true/false/NULL']
    active_sheet.append(info)
    info = ['13', 'search', 'Muestra el cuadro de texto de búsqueda rápida. Valor: true/false/NULL']
    active_sheet.append(info)
    info = ['14', 'filter', 'Muestra filtros al inicio. Valor: true/false/NULL']
    active_sheet.append(info)
    info = ['15', 'pagination', 'Visualiza iconos de paginación. Valor: true/false/NULL']
    active_sheet.append(info)
    info = ['16', 'icon', 'Icono de la tabla. Valor: img/grid/element_type_x.png']
    active_sheet.append(info)
    info = ['17', 'select', 'Indica si la tabla es seleccionable. Valor: true/false/NULL']
    active_sheet.append(info)
    info = ['18', 'row_color', 'Establece color de fondo a una fila. Valor: {  field: [grid_field_id],  value: [value_of_field],  backcolor: [rgb hex]}' ]
    active_sheet.append(info)
    info = ['19', 'balloon_color', 'Muestra el valor del campo resaltado con un fondo aleatorio o personalizdo en función del valor. Valor: como el anterior' ]
    active_sheet.append(info)
    info = ['20', 'refresh_button', 'Mostrar botón refrescar. Valor: true/false/NULL']
    active_sheet.append(info)
    info = ['21', 'row_adjustement', 'Modo de ajuste. Valor: data/header/auto/custom/NULL']
    active_sheet.append(info)
    info = ['22', 'header_row_height', 'Altura de la fila de headers.' ]
    active_sheet.append(info)
    info = ['23', 'show_selected', 'Muestra en la tabla el numero de filas seleccionadas. Valor = true/false/NULL']
    active_sheet.append(info)
    info = ['24', 'show_header', 'Muestra la cabecera de la tabla, es decir, el nombre de las columnas. Valor = true/false/NULL']
    active_sheet.append(info)
    info = ['25', 'show_print_export', 'Mostrar botones imprimir, exportar XLS, exportar PDF. Valor: true/false/NULL' ]
    active_sheet.append(info)

    #Fixs the column sizes
    for col in active_sheet.columns:
        max_length = 0
        try:
            
            column = col[1].column_letter # Get the column name
            for cell in col:
                # Necessary to avoid error on empty cells
                if len(str(cell.value)) > max_length:
                    max_length = len(str(cell.value))
        except:
            pass
        adjusted_width = (max_length + 5) * 1.2
        active_sheet.column_dimensions[column].width = adjusted_width

        title_font = Font(name='Calibri', size=14, bold=True, color='00800000')
        cell_alignment =  Alignment(horizontal='center', vertical='center')
        active_sheet.cell(row=1, column=1).alignment = cell_alignment
        active_sheet.cell(row=1, column=1).alignment = cell_alignment
        active_sheet.cell(row=1, column=1).alignment = cell_alignment
        active_sheet.cell(row = 1 , column=1).font = title_font
        active_sheet.cell(row = 1 , column=2).font = title_font
        active_sheet.cell(row = 1 , column=3).font = title_font

#Creates the excel file and draws all the content
def generate_excel(name, language):
    first_page()
    element_types = info_element_types()
    for i in range (0, len(element_types)):
        element_id = element_types[i][0]
        info_element = element_info(element_id)
        element_name = translation(language, info_element)
        print(element_name)
        sheet_name = str(element_id) +'.-' + element_name.upper()
        if len(sheet_name) > 31:
            sheet_name = sheet_name[0:30]
        '''if (i == 0):
            excel_file.active.title = sheet_name'''
        #else:
        try:
            excel_file.create_sheet(sheet_name)
        except:
            element_name = info_element
            excel_file.create_sheet(element_name)
        sheet_content(sheet_name, language)
    try:
        Sheet1 = excel_file['Sheet']
        excel_file.remove(Sheet1)
    except:
        pass
    excel_file.save(filename = name)

