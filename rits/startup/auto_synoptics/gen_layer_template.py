import os
import sys
import copy
import errno
import lxml.etree as ET
from operator import attrgetter

from Entities.Synoptic import Synoptic
from Entities.Module import Module
from Entities.ModuleValue import ModuleValue
from Entities.WindowValue import WindowValue
from Entities.GraphicIcon import GraphicIcon
from Entities.SynopticCommand import SynopticCommand
from Entities.SynopticElementTypeCommand import SynopticElementTypeCommand
from Entities.SynopticLayer import SynopticLayer
from Entities.SelectorTree import SelectorTree
from Entities.SelectorTreeNode import SelectorTreeNode
from Entities.SelectorTreeHirarchy import SelectorTreeHierarchy
from Entities.SynopticLayerRelationships import SynopticLayerRelationships
from Entities.SynopticLayerElement import SynopticLayerElement

if len(sys.argv) < 2:
    print("Usage:")
    print("  arg = Name of Proyect")
    exit()
    
# constants
ENVIROMENT_NAME = sys.argv[1]
LINE_BREAK = '\n'
VALUE_FALSE = 'false'
VALUE_TRUE = 'true'
VALUE_NULL = 'NULL'

# Directories:
rootDirectory = os.path.dirname(os.path.abspath(__file__))
projectDirectory = os.path.join(rootDirectory, ENVIROMENT_NAME)

# Output files
outputFile = os.path.join(projectDirectory + '/layers.csv')

# Recuperamos todos los sinopticos
synopticsArray = []
synopticsCsv = os.path.join(projectDirectory + '/CSV/synoptics.csv')
with open(synopticsCsv) as inputStreamFile:
    firstLine= 1
    nodeMap= {}
    lastNodeId = 1
    lastSynopticId= 1
    for line in inputStreamFile:
        if firstLine:
            head = line[:-1].split(';')
            firstLine = 0
        else:
            parametros = line[:-1].split(';')
            # Add Synoptic
            if parametros[head.index(SelectorTreeNode.MODULE_ID)]:
                synoptic = Synoptic()
                synoptic.id= "{:0>3d}".format(int(parametros[head.index(SelectorTreeNode.MODULE_ID)]))
                synoptic.name= parametros[head.index(Synoptic.NAME)]
                synoptic.description= parametros[head.index(Synoptic.DESCRIPTION)]
                synoptic.label= 'LBL_' + parametros[head.index(Synoptic.LABEL)]
                synoptic.icon= VALUE_NULL
                synoptic.ruta= parametros[head.index(Synoptic.BASE_LAYER)]
                synoptic.base_layer= 'img/synoptics/' + parametros[head.index(Synoptic.BASE_LAYER)]
                synoptic.exclusive_layers= VALUE_FALSE
                synopticsArray.append(copy.copy(synoptic))

# Recuperamos todos los graphic_icon
graphicIconMap= {}
graphicsCsv = os.path.join(projectDirectory + '/CSV/N_graphic_icons.csv')
with open(graphicsCsv) as inputStreamFile:
    firstLine = 1
    elementCount = 1
    lastElementCount = 0

    for line in inputStreamFile:
        graphicIconItem = GraphicIcon()
        if firstLine:
            head = line[:-1].split(';')
            firstLine = 0
        else:
            parametros = line[:-1].split(';')
            
            if parametros[head.index(GraphicIcon.ELEMENT_TYPE)] == lastElementCount:
                elementCount+=1
            else:
                elementCount = 1
            
            id_graphic_icon = "{:0>2d}".format(elementCount)
            if parametros[head.index(GraphicIcon.ELEMENT_SUBTYPE)] == '':
                element_subtype = "00"
            else:
                graphicIconItem.idSubtype= parametros[head.index(GraphicIcon.ELEMENT_SUBTYPE)]
                element_subtype = "{:0>2d}".format(int(graphicIconItem.idSubtype))
            graphicIconItem.idType= parametros[head.index(GraphicIcon.ELEMENT_TYPE)]
            element_type = "{:0>4d}".format(int(graphicIconItem.idType))
            
            graphicIconItem.id = id_graphic_icon + element_subtype + element_type
            graphicIconItem.name = parametros[head.index(GraphicIcon.ALIAS)]
            graphicIconItem.alarms = VALUE_FALSE
            if GraphicIcon.KEEP_ASPECT_RATIO in head and parametros[head.index(GraphicIcon.KEEP_ASPECT_RATIO)]:
                graphicIconItem.keep_aspect_ratio= VALUE_TRUE
            graphicIconItem.svg_file = "'img/graphic_icon/" + parametros[head.index(GraphicIcon.SVG_FILE)] + "'"
            if parametros[head.index(GraphicIcon.JS_CUSTOM_TYPE_RENDER)] == '':
                graphicIconItem.measures = VALUE_FALSE
                graphicIconItem.draw_file =  'component/graphicIcon/graphicIconDefault.js'
                graphicIconItem.js_custom = 'null'
            else:
                graphicIconItem.measures = VALUE_TRUE
                graphicIconItem.draw_file =  'component/graphicIcon/graphicIconCustom.js'
                graphicIconItem.js_custom = "component/graphicIcon/custom/" + parametros[head.index(GraphicIcon.JS_CUSTOM_TYPE_RENDER)]
            graphicIconMap[graphicIconItem.idType] = copy.copy(graphicIconItem)
            lastElementCount = parametros[head.index(GraphicIcon.ELEMENT_TYPE)]

# Creamos los synoptic layer en base a los svg
for synoptic in synopticsArray:
    synoptic.synopticLayerMap = {}
    xml = ET.parse(os.path.join(rootDirectory + '/' + ENVIROMENT_NAME + '/SVG/' + synoptic.ruta))
    rel_x = 1
    rel_y = 1
    root = xml.getroot()
    i = len(root)
    newIconCount= 0
                    
    while (i != 0):
        i = i-1
        id = root[i].attrib['id']
        if id.startswith("Element:"):
            idType = id[id.index(":") + 1:id.rindex(":")]
            if "-" in idType:
                idType = idType[idType.index("-") + 1:]
                idType = idType.lstrip("0")
            
            if not idType in synoptic.synopticLayerMap:
                synopticLayer = SynopticLayer()
                synopticLayer.synopticAlias = synoptic.name
                try:
                    synopticLayer.name = graphicIconMap[idType].name
                except Exception as e:
                    print("Es necesario dar de alta el icono asociado al tipo de equipo: " + idType + " en el csv: " + os.path.basename(graphicsCsv) + " para poder continuar")
                    exit()
                synopticLayer.idElementType = idType
                synoptic.synopticLayerMap[idType] = copy.copy(synopticLayer)

outputLine = SynopticLayer.getCsvHeader()
for synoptic in synopticsArray:
    for idType in synoptic.synopticLayerMap:
        outputLine += synoptic.synopticLayerMap[idType].getCsvRow()

def writeFile(file, text):
    if not os.path.exists(os.path.dirname(file)):
        try:
            os.makedirs(os.path.dirname(file))
        except OSError as exc:
            if exc.errno != errno.EEXIST:
                raise
    with open(file, "w") as inputStreamFile:
        try:
            inputStreamFile.write(text)
            inputStreamFile.close()
            print(os.path.basename(file) + " se generó sin errores")
        except Exception as e:
            print (e)

# Write tree menu file
writeFile(outputFile, outputLine)
