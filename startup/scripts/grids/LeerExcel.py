import openpyxl as xl
import os
import re
import sys
from translation import todatabase, todatabasetypes 


#INFO DATABASE

# Variables para la conexion a BBDD
HOST = sys.argv[1]
PORT = sys.argv[2]
'''HOST = '192.168.88.177'
PORT= '5430'''
DB = "rits"
USER = "rits"
PASS = "rits"


cwd = os.path.dirname(os.path.abspath(__file__))
path_xlsx = 'Properties/grids.xlsx'
g_xlsx = os.path.join(cwd, path_xlsx)



def truncate_semicolon(g_sql):
    with open(g_sql, 'rb+') as filehandle:
        filehandle.seek(-1, os.SEEK_END)
        filehandle.truncate()
        filehandle.seek(-1, os.SEEK_END)
        filehandle.truncate()
        filehandle.seek(-1, os.SEEK_END)
        filehandle.truncate()

    f = open(g_sql  , 'a')
    f.write('\n\n')

#Given the the excel file's path
#create .sql file with all update consults
def read_excel(counter, g_sql):
    fourth = 'INSERT INTO ui.grid_option_values(grid_id, grid_option_id, value) VALUES\n'
    f = open(g_sql , 'a')
    f.write(fourth)
    f.close()
    # To open the workbook
    excel_file = xl.load_workbook(g_xlsx)

    #Number of sheets in the excel file
    number_sheets = len(excel_file.sheetnames)
    #All the sheets
    sheets = excel_file.sheetnames

    #Readying sheets loop
    #for i in range(1, number_sheets):
    actual_sheet_name = excel_file[sheets[counter]]
    excel_file.active =  actual_sheet_name
    active_sheet = excel_file.active

    max_rows = active_sheet.max_row +1
    element_type_id = int(actual_sheet_name.title.split('.-')[0])

    #Values on 4 column
    #Loop for getting all the params
    for j in range (3, max_rows):
        #Get all the variables for the query
        id = active_sheet.cell(row = j, column = 1).value
        val  = active_sheet.cell(row = j, column = 2).value
        print (str(val))
        if str(val) != 'None':
            if val == '=TRUE()':
                val = 'true' 
            if val == '=FALSE()':
                val = 'false'
            
            if str(val) != 'NULL':
                if int(id) == 16 or int(id) == 21:
                    val = val

            insert = '(' + str(element_type_id) +', '+str(id)+ ', \'' + str(val) +'\'), \n'
            f = open(g_sql , 'a')
            f.write(insert)
            f.close()
        
    if counter != (number_sheets-1) :
        f = open(g_sql , 'a')
        f.write('\n')
        f.close()
        truncate_semicolon(g_sql)


