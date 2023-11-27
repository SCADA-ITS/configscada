#LIBRARIES
from openpyxl.styles import Alignment, Font
from translation import translation
import openpyxl as xl
import psycopg2
import sys



#EXCEL CREATION
excel_file = xl.Workbook()

#DB CONNECTION
HOST = sys.argv[1]
PORT = sys.argv[2]
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
        cur.execute("SELECT distinct element_type_id FROM conf.elements ORDER BY element_type_id ASC;")

        info = cur.fetchall()

        cur.close()
        connection.close()
        return info

    except AttributeError:
        print(ValueError)

#Given the element id, returns all the alarms on the alarm_configs table
def info_alarms_element_type(id):
    alarms_element = []
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()
        #Si quiere que salgan todas las alarmas posibles, incluyendo las enabled = False borre de la siguiente consulta 'and enabled = True'
        consult = "SELECT alarm_config_id, label_alias, element_type_id, alarm_level_id, enabled, treatment FROM conf.alarm_configs WHERE element_type_id = " + str(id) + " ORDER BY alarm_config_id ASC;"
        cur.execute(consult)
        alarms_element = cur.fetchall()
        cur.close()
        connection.close()
        return alarms_element

    except AttributeError:
        print(ValueError)

#Adds to the excel the label alias, alarm level id and enabled tags of all the alarms.
def sheet_content (sheet_name, language):
    index = sheet_name.split('.-')[0]
    alarms = info_alarms_element_type(index)
    if (len(alarms) != 0):
        active_sheet = excel_file[sheet_name]
        excel_file.active = active_sheet
        #alarm_config_id, label_alias, element_type_id, alarm_level_id, enabled
        active_sheet.append(('ALARM CONFIG ID','LABEL ALIAS', 'ALARM LEVEL ID', 'ENABLED', 'TREATMENT'))
        
        for alarm in alarms:
            trans_alarm0 = translation(language, alarm[1])
            level_id = int(alarm[3]) -2
            #producto es una tupla con los valores de un producto 
            info = [alarm[0], trans_alarm0, str(level_id), alarm[4], alarm[5]]
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
            adjusted_width = (max_length + 2) * 1.5
            active_sheet.column_dimensions[column].width = adjusted_width

        #Pretty drawing
        title_font = Font(size=14, bold=True, color='00800000')
        cell_alignment =  Alignment(horizontal='center', vertical='center')
        for i in range (1,max_rows):
            for j in  range (1, max_columns):
                if (i == 1):
                    active_sheet.cell(row = i, column=j).font = title_font
                active_sheet.cell(row=i, column=j).alignment = cell_alignment
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
        consult = "select label_alias from master.element_types where element_type_id = " +str(id) +";"
        cur.execute(consult)
        info = cur.fetchone()
        cur.close()
        connection.close()
        if info != None:
            info = info[0]
        return info

    except AttributeError:
        print(ValueError)    

#Creates the excel file and draws all the content
def generate_excel(name, language):
    element_types = info_element_types()
    for i in range (0, len(element_types)):
        element_id = element_types[i][0]
        info_element = element_info(element_id)
        element_name = translation(language, info_element)
        has_alarms = info_alarms_element_type(element_id)
        if has_alarms:
            sheet_name = str(element_id) +'.-' + element_name.upper()
            if len(sheet_name) > 31:
                sheet_name = sheet_name[0:30]
            if (i == 0):
                excel_file.active.title = sheet_name
            else:
                try:
                    excel_file.create_sheet(sheet_name)
                except:
                    element_name = info_element
                    excel_file.create_sheet(element_name)
            sheet_content(sheet_name, language)
        else:
            pass
    excel_file.save(filename = name)
