import psycopg2
import sys

#DB CONNECTION

'''HOST = '192.168.88.177'
PORT= '5430'''
DB = "rits"
USER = "rits"
PASS = "rits"

def translation(language, label, HOST, PORT):
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()
        consult = "SELECT translation FROM master.i18n_labels WHERE label = \'" + str(label) + "\' AND locale_code = \'"+ str(language)+"\';"
        cur.execute(consult)
        trans = cur.fetchone()
        if (trans == None):
            return label
        else:
            return trans[0]

    except AttributeError:
        print(ValueError)


def todatabase(label, HOST, PORT):
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()
        consult = "SELECT label FROM master.i18n_labels WHERE translation = \'" + str(label) + "\'AND label LIKE '%ALARM_CONFIG%';"
        cur.execute(consult)
        trans = cur.fetchone()
        if (trans == None):
            return label
        else:
            return trans[0]

    except AttributeError:
        print(ValueError)

def todatabasetypes(label, HOST, PORT):
    try:
        #DB Connection
        connectionChain = "host=%s port=%s user=%s password=%s dbname=%s" % (
            HOST, PORT, USER, PASS, DB)
        connection = psycopg2.connect(connectionChain)
        # Cursor to operate on the DB
        cur = connection.cursor()
        consult = 'SELECT label, translation FROM master.i18n_labels INNER JOIN master.element_types ON master.i18n_labels.label = master.element_types.label_alias WHERE translation = \'' + str(label) + '\';'
        cur.execute(consult)
        trans = cur.fetchone()
        if (trans == None):
            return label
        else:
            return trans[0]

    except AttributeError:
        print(ValueError)