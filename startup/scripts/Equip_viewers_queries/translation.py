import psycopg2
import sys
#sk_SK, en_GB, es_ES, es_CL on table master.i18n_labels

#DB CONNECTION
HOST = sys.argv[1]
PORT = sys.argv[2]
'''HOST = "192.168.88.231"
PORT = "5430"'''
DB = "rits"
USER = "rits"
PASS = "rits"

def translate_label_param(label, language):
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()
        consult = 'select master.i18n_labels.translation  from master.i18n_labels inner join master.element_type_params on master.i18n_labels.label = master.element_type_params.label_alias where master.i18n_labels.locale_code = \''+ str(language)+'\' and master.i18n_labels.label = \'' + str(label) + '\';'
        cur.execute(consult)
        trans = cur.fetchone()
        if (trans == None):
            return label
        else:
            return trans[0]

    except AttributeError:
        print(ValueError)

def translate_label_type(label, language):
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()
        consult = 'select master.i18n_labels.translation  from master.i18n_labels inner join master.element_types on master.i18n_labels.label = master.element_types.label_alias where master.i18n_labels.locale_code = \''+ str(language)+'\' and master.i18n_labels.label = \'' + str(label) + '\';'
        cur.execute(consult)
        trans = cur.fetchone()
        if (trans == None):
            return label
        else:
            return trans[0]

    except AttributeError:
        print(ValueError)

def translate_param_label(translation):
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()
        consult = 'select distinct master.i18n_labels.label  from master.i18n_labels inner join master.element_type_params on master.i18n_labels.label = master.element_type_params.label_alias where master.i18n_labels.translation = \'' + str(translation) + '\';'
        cur.execute(consult)
        trans = cur.fetchall()
        if (len(trans) != 0):
            return trans
        else:
            return translation


    except AttributeError:
        print(ValueError)

