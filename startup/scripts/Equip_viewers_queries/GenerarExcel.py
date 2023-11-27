from openpyxl.styles import Alignment, Font
import openpyxl as xl
import psycopg2
from translation import translate_label_type
from translation import translate_label_param
import sys

#EXCEL CREATION
excel_file = xl.Workbook()


# Variables para la conexion a BBDD
HOST = sys.argv[1]
PORT = sys.argv[2]
DB = "rits"
USER = "rits"
PASS = "rits"
'''
#DB CONNECTION
HOST = "192.168.88.231"
PORT = "5430"
DB = "rits"
USER = "rits"
PASS = "rits"'''

#Function that returns all the information of the element_types from the master folder
def info_element_types():
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()
        cur.execute("SELECT distinct element_type_id, element_subtype_id FROM conf.elements;")

        info = cur.fetchall()
        cur.close()
        connection.close()
        return info

    except AttributeError:
        print(ValueError)

def info_params_element_type(id):
    params_element = []
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()
        consult = "SELECT label_alias, param_type_id, element_type_param_id, enabled FROM master.element_type_params WHERE element_type_id =" + str(id) + " and (element_type_param_id != 1003 and element_type_param_id != 1001 and element_type_param_id != 1004 and element_type_param_id != 1002) ;"
        cur.execute(consult)
        params_element = cur.fetchall()
        cur.close()
        connection.close()
        return params_element

    except AttributeError:
        print(ValueError)

def get_subtype_active(id, element_subtype_id):
    params_element = []
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()
        consult = "SELECT * FROM master.element_subtype_active_params WHERE element_type_id =" + str(id) + " and  element_subtype_id = "+str(element_subtype_id)+" and (element_type_param_id != 1001 and element_type_param_id != 1002);"
        cur.execute(consult)
        params_element = cur.fetchall()
        cur.close()
        connection.close()
        return params_element

    except AttributeError:
        print(ValueError)

def info_params_subtype(index, element_subtype_id):
    params_element = []
    params_element_aux = []
    aux = get_subtype_active(index, element_subtype_id)

    for param_active in aux:
        try:
            #DB Connection
            connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
                HOST, PORT, USER, PASS, DB)
            connection = psycopg2.connect(connectionChain)
            # Cursor to operate on the DB
            cur = connection.cursor()
            consult = "SELECT label_alias, param_type_id, element_type_param_id, enabled FROM master.element_type_params WHERE element_type_id =" + str(index) + " and element_type_param_id = "+str(param_active[3])+"and param_type_id = "+str(param_active[2])+"and element_type_param_id != 1003 and element_type_param_id != 1001 and element_type_param_id != 1002 and element_type_param_id != 1004;"
            cur.execute(consult)
            params_element_aux = cur.fetchall()
            cur.close()
            connection.close()
            params_element.append(params_element_aux[0])
        except AttributeError:
            print(ValueError)
    return params_element


def params_subtype_complete(params_measures, index):
    params_element = []
    total = []
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()
        consult = "SELECT label_alias, param_type_id, element_type_param_id, enabled FROM master.element_type_params WHERE element_type_id =" + str(index) + "and param_type_id = 1 and (element_type_param_id != 1003 and element_type_param_id != 1001 and element_type_param_id != 1004) ;"
        cur.execute(consult)
        params_element = cur.fetchall()
        cur.close()
        connection.close()
        total = params_element + params_measures
        return total

    except AttributeError:
        print(ValueError)    

def sheet_content (sheet_name, language, element_subtype_id):

    index = sheet_name.split('.-')[0]
    if element_subtype_id == None:
        params = info_params_element_type(index)
    else:
        params_measures = info_params_subtype(index, element_subtype_id)
        params = params_subtype_complete(params_measures, index)

    if (len(params) != 0):
        active_sheet = excel_file[sheet_name]
        excel_file.active = active_sheet



        params1 = []
        params2 = []

        for param in params:
            param_type = int(param[1])
            if (param_type == 1):
                params1.append(param)
            elif (param_type ==2):
                params2.append(param)
            else:
                pass

        if(len(params1) != 0):
            active_sheet.append(('TYPE 1: CONFIGURATION',''))
            active_sheet.append(('LABEL ALIAS', 'POSITION', 'ENABLED'))
            for par1 in params1:
                param_type = int(par1[1])
                param_type_id = int(par1[2])
                trans_param0 = translate_label_param(par1[0], language)
                info = [trans_param0, param_type_id, int(par1[3])]
                active_sheet.append(info)
        if(len(params2) != 0):
            active_sheet.append(('TYPE 2: MEASURES',''))
            active_sheet.append(('LABEL ALIAS', 'POSITION', 'ENABLED', 'NOMBRE GRUPO(valor por defecto=Medidas)'))
            for par2 in params2:
                param_type = int(par2[1])
                param_type_id = int(par2[2])
                trans_param0 = translate_label_param(par2[0], language)

                info = [trans_param0, param_type_id, int(par2[3])]
                active_sheet.append(info)

        max_columns = active_sheet.max_column +1
        max_rows = active_sheet.max_row +1

        #Fixs the column sizes
        for col in active_sheet.columns:
            max_length = 0
            column = col[0].column_letter # Get the column name
            for cell in col:
                try: # Necessary to avoid error on empty cells
                    if len(str(cell.value)) > max_length:
                        max_length = len(str(cell.value))
                except:
                    pass
            adjusted_width = (max_length + 3) * 1.5
            active_sheet.column_dimensions[column].width = adjusted_width

        #Pretty drawing
        next_title = len(params1)+3
        title_font = Font(size=14, bold=True, color='00800000')
        title_font2 = Font(size=12, bold=True, color='00800000')
        cell_alignment =  Alignment(horizontal='center', vertical='center')
        for i in range (1,max_rows):
            for j in  range (1, max_columns):
                if (i == 1 or i == next_title):
                    active_sheet.cell(row = i, column=j).font = title_font
                elif (i == 2 or i == next_title+1):
                    active_sheet.cell(row = i, column=j).font = title_font2
                active_sheet.cell(row=i, column=j).alignment = cell_alignment
        active_sheet.insert_rows(len(params1)+3, amount = 2)


        active_sheet.append(('Number of columns on the ui',4))
        if (len(params2) != 0):
            new_index = 7 +len(params1) + len(params2)
        else:
            new_index = 3 +len(params1)
        active_sheet.cell(row = new_index, column=1).font = title_font2
        active_sheet.cell(row = new_index, column=1).alignment = cell_alignment
        active_sheet.cell(row = new_index, column=2).alignment = cell_alignment

        active_sheet.insert_rows(new_index, amount= 2)





        return True
    else:
        return False

#Consults info for a given element id
def element_info(id):
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()
        consult = "select label_alias from master.element_types where element_type_id = " + str(id) +";"

        cur.execute(consult)

        info = cur.fetchone()

        cur.close()
        connection.close()
        return info[0]

    except AttributeError:
        print(ValueError)    

#Creates the excel file and draws all the content
def generate_excel(name, language):
    element_types = info_element_types()
    for i in range (0, len(element_types)):
        element_id = element_types[i][0]
        element_subtype_id= element_types[i][1]

        info_element = element_info(element_id)
        element_name = translate_label_type(info_element, language)
        element_name = element_name.replace('/', ' ')
        has_params = info_params_element_type(element_id)
        if has_params:
            if len(element_name) > 20:
                element_name = element_name[0:19]

            if element_subtype_id != None:
                sheet_name = str(element_id) +'.-' + element_name.upper() +'.-' +str(element_subtype_id)
            else:
                sheet_name = str(element_id) +'.-' + element_name.upper()
            
            
            if (i == 0):
                excel_file.active.title = sheet_name

            else:
                excel_file.create_sheet(sheet_name)
            sheet_content(sheet_name, language, element_subtype_id)
        else:
            pass
        try:
            Sheet1 = excel_file['Sheet']
            excel_file.remove(Sheet1)
        except:
            pass
    excel_file.save(filename = name)
