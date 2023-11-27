from GenerarExcel import generate_excel
from LeerExcel import read_excel
from join_sql import *
import sys


print('<------------------->')
print('ELEMENT PARAMS EXCEL')
print('<------------------->')


first_option = int(input('Do you want to generate the excel file or read an excel file? (1/2): '))
if(first_option == 1):
    name = input('File name: \n')
    if name.endswith('.xlsx'):
        pass
    else:
        name = name +'.xlsx'
    language = input('Language: (sk_SK/en_GB/es_ES/es_CL): \n')
    print('Generating ' + name + ' in ' + language +'.....')
    generate_excel(name, language)
    print('DONE')

elif(first_option == 2):

    path = input('Introduce the path of the excel file:\n')
    if path.endswith('.xlsx'):
        pass
    else:
        path = path +'.xlsx'
    print('Reading ' + path + ' and generating the queries....')
    read_excel(path)
    join_sql()
    clean()
    print('DONE!')