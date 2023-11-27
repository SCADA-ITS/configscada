from GenerarExcel import generate_excel
from LeerExcel import read_excel
import sys

print('<----------------->')
print('CONFIG ALARMS EXCEL')
print('<------------------>')

first_option = int(input('Do you want to generate the excel file or read an excel file? (1/2): '))


if(first_option == 1):
    '''name = input('File name: \n')
    if name.endswith('.xlsx'):
        pass
    else:
        name = name +'.xlsx'''''
    name = 'alarmas.xlsx'
    #language = sys.argv[3]
    language = input('Language: (sk_SK/en_GB/es_ES/es_CL): \n')
    generate_excel(name, language)

elif(first_option == 2):
    '''path = input('Introduce the path of the excel file:\n')
    if path.endswith('.xlsx'):
        pass
    else:
        path = path +'.xlsx'''''
    path = 'alarmas.xlsx'
    #option = int(input('1. If you want to execute the updates on the database. \n2. If you want to generate the sql files with the update queries.\n'))
    option = 2
    read_excel(path, option)