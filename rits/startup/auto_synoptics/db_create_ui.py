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

if len(sys.argv) > 2:
    ONE_FILE = 1
else:
    ONE_FILE = 0

# constants
ENVIROMENT_NAME = sys.argv[1]
LINE_BREAK = '\n'
VALUE_FALSE = 'false'
VALUE_TRUE = 'true'
VALUE_NULL = 'NULL'
DEFAULT_VALUE_WIDTH = '50'
DEFAULT_VALUE_HEIGHT = '50'


# Directories:
rootDirectory = os.path.dirname(os.path.abspath(__file__))
projectDirectory = os.path.join(rootDirectory, ENVIROMENT_NAME)

# Output files
treeMenuOutputFile = os.path.join(projectDirectory + '/12.selector_trees.sql')
outputFolder = os.path.join(projectDirectory + "/08.synoptics/")
graphicIconFileName = "00.graphic_icons.sql"
moduleFileName = "01.modules.sql"

treeNodesArray = []
modulesArray = []
moduleValuesArray = []
windowValuesArray = []
synopticsArray = []
synopticLayersArray = []
synopticLayerHirarchyArray = []
synopticCommandArray= []
synopticElementTypeCommandArray= []

# Relleno la tabla ui.synoptics
synopticsCsv = os.path.join(projectDirectory + '/CSV/synoptics.csv')
with open(synopticsCsv) as inputStreamFile:
    selectorTree = SelectorTree()
    treeMenuOutputLine = SelectorTree.getInsertHeader()
    treeMenuOutputLine += selectorTree.getInsertRow()
    treeMenuOutputLine = treeMenuOutputLine[:-2] + ';' + LINE_BREAK + LINE_BREAK

    firstLine= 1
    nodeMap= {}
    for line in inputStreamFile:
        if firstLine:
            head = line[:-1].split(';')
            treeMenuOutputLine += SelectorTreeNode.getInsertHeader()
            firstLine = 0
        else:
            # Add SelectedTreeNode
            parametros = line[:-1].split(';')
            treeNode = SelectorTreeNode()
            treeNode.id= str(len(treeNodesArray)+1)
            treeNode.label= 'LBL_' + parametros[head.index(SelectorTreeNode.ALIAS)]
            if parametros[head.index(SelectorTreeNode.MODULE_ID)]:
                treeNode.module_id= parametros[head.index(SelectorTreeNode.MODULE_ID)]
            else:
                treeNode.module_id= VALUE_NULL
            treeMenuOutputLine += treeNode.getInsertRow()

            treeNodeDic= {}
            treeNodeDic["id"]= treeNode.id
            treeNodeDic[SelectorTreeHierarchy.CHILD_ID]= parametros[head.index(SelectorTreeHierarchy.CHILD_ID)]
            treeNodeDic[SelectorTreeHierarchy.PARENT_ID]= parametros[head.index(SelectorTreeHierarchy.PARENT_ID)]
            treeNodesArray.append(treeNodeDic)

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

                #Add Module
                module = Module()
                module.id = synoptic.id
                module.name = parametros[head.index(Synoptic.LABEL)]
                module.description = parametros[head.index(Synoptic.LABEL)]
                modulesArray.append(copy.copy(module))

                #Add ModuleValue
                moduleValue = ModuleValue()
                moduleValue.module_id = module.id
                moduleValue.value = synoptic.id
                moduleValuesArray.append(copy.copy(moduleValue))

                #Add WindowValue
                windowValue = WindowValue()
                windowValue.module_id = module.id
                windowValue.label = synoptic.label
                windowValuesArray.append(copy.copy(windowValue))

treeMenuOutputLine = treeMenuOutputLine[:-2] + ';' + LINE_BREAK + LINE_BREAK

# Relleno la tabla ui.Selector_tree_hirarchies
if treeNodesArray:
    treeMenuOutputLine += SelectorTreeHierarchy.getInsertHeader()
    for treeNode in treeNodesArray:
        if treeNode[SelectorTreeHierarchy.PARENT_ID]:
            treeHirarchyNode= SelectorTreeHierarchy()
            treeHirarchyNode.node_parent_id= [x['id'] for x in treeNodesArray if x[SelectorTreeHierarchy.CHILD_ID] == treeNode[SelectorTreeHierarchy.PARENT_ID]][0]
            treeHirarchyNode.node_child_id= treeNode['id']
            treeMenuOutputLine += treeHirarchyNode.getInsertRow()
    treeMenuOutputLine = treeMenuOutputLine[:-2] + ';' + LINE_BREAK + LINE_BREAK

# Relleno la tabla ui.synoptic_layers
layersCsv = os.path.join(projectDirectory + '/CSV/layers.csv')
with open(layersCsv) as inputStreamFile:
    i= 0
    firstLine = 1
    for line in inputStreamFile:
        synopticLayer = SynopticLayer()
        if firstLine:
            head = line[:-1].split(';')
            firstLine = 0
        else:
            parametros = line[:-1].split(';')
            synopticLayer.synoptic_id= "{:0>3d}".format(int([x.id for x in synopticsArray if x.label == 'LBL_' + parametros[head.index(SynopticLayer.ID_SYNOPTIC)]][0]))
            synopticLayer.id= "{:0>3d}".format(int(synopticLayer.synoptic_id))
            if parametros[head.index(SynopticLayer.ID_ELEMENT_TYPE)]:
                synopticLayer.id += "{:0>3d}".format(int(parametros[head.index(SynopticLayer.ID_ELEMENT_TYPE)]))
                synopticLayer.idElementType += parametros[head.index(SynopticLayer.ID_ELEMENT_TYPE)]
                if parametros[head.index(SynopticLayer.ID_ELEMENT_SUBTYPE)]:
                    synopticLayer.id += "{:0>2d}".format(int(parametros[head.index(SynopticLayer.ID_ELEMENT_SUBTYPE)]))
                    synopticLayer.idElementSubtype += parametros[head.index(SynopticLayer.ID_ELEMENT_SUBTYPE)]
                else:
                    synopticLayer.id += "00"
            else:
                synopticLayer.id+= "000" + "{:0>2d}".format(i)
                i= i+1
            
            synopticLayer.name = parametros[head.index(SynopticLayer.NAME)]
            synopticLayer.label ='LBL_' + parametros[head.index(SynopticLayer.NAME)]
            
            if parametros[head.index(SynopticLayer.MODULE_ACTION_ID)]:
                synopticLayer.moduleActionId = parametros[head.index(SynopticLayer.MODULE_ACTION_ID)]

            if SynopticLayer.SVG_FILE in head and parametros[head.index(SynopticLayer.SVG_FILE)]:
                synopticLayer.svgFile = parametros[head.index(SynopticLayer.SVG_FILE)]

            if parametros[head.index(SynopticLayer.ID_PARENT_ALIAS)]:
                synopticLayerHirarchy = SynopticLayerRelationships()
                synopticLayerHirarchy.synoptic_id= synopticLayer.synoptic_id
                synopticLayerHirarchy.parent_alias= parametros[head.index(SynopticLayer.ID_PARENT_ALIAS)]
                synopticLayer.parentAlias= parametros[head.index(SynopticLayer.ID_PARENT_ALIAS)]
                synopticLayer.idElementType= parametros[head.index(SynopticLayer.ID_ELEMENT_TYPE)]
                synopticLayerHirarchy.child_layer_id= synopticLayer.id
                if(not synopticLayersArray or not (synopticLayersArray[-1].name == synopticLayer.name and synopticLayersArray[-1].parentAlias == synopticLayer.parentAlias and not synopticLayersArray[-1].synoptic_id != synopticLayer.synoptic_id)):
                    synopticLayerHirarchyArray.append(synopticLayerHirarchy)
            synopticLayersArray.append(synopticLayer)

# Relleno la tabla ui.synoptic_layer_relationships
if synopticLayerHirarchyArray:
    i= 0
    for synopticLayerHirarchy in synopticLayerHirarchyArray:
        aux = [x.id for x in synopticLayersArray if x.name == synopticLayerHirarchy.parent_alias]
        synopticLayerHirarchy.parent_layer_id = [x.id for x in synopticLayersArray if x.name == synopticLayerHirarchy.parent_alias and x.id < synopticLayerHirarchy.child_layer_id][-1]    

# Relleno la tabla ui.synoptic_commands
if synopticsArray:
    i= 0
    for synoptic in synopticsArray:
        synopticCommand= SynopticCommand()
        synopticCommand.id= str(i)
        synopticCommand.synoptic_id= synoptic.id
        synopticCommand.position= '0'
        synopticCommand.label= 'LBL_CMD_ALARMS'
        synopticCommand.icon= 'mdi mdi-pencil'
        synopticCommand.default= VALUE_FALSE
        synopticCommand.module_action_id= '100501'
        synopticCommand.args= VALUE_NULL
        synopticCommand.view_type_id= '2'
        synopticCommand.multiselect= VALUE_TRUE
        synopticCommand.show_text= VALUE_NULL
        synopticCommand.item_required= VALUE_FALSE
        synopticCommand.context= VALUE_TRUE
        synopticCommandArray.append(copy.copy(synopticCommand))
        i+=1
        
        # Relleno la tabla ui.synoptic_element_type_commands
        for synopticModuleAction in [x for x in synopticLayersArray if x.moduleActionId and x.synoptic_id == synoptic.id]:
            synopticElementTypeCommand= SynopticElementTypeCommand()
            synopticElementTypeCommand.synopticCommandId= str(i)
            synopticElementTypeCommand.elementType= 'ElementType:' + synopticModuleAction.idElementType
            synopticElementTypeCommand.module_action_id= synopticModuleAction.moduleActionId
            synopticElementTypeCommandArray.append(synopticElementTypeCommand)

        synopticCommand.id= str(i)
        synopticCommand.position= '1'
        synopticCommand.label= 'LBL_ELEMENT'
        synopticCommand.default= VALUE_TRUE
        synopticCommand.module_action_id= '101401'
        synopticCommand.multiselect= VALUE_FALSE
        synopticCommand.item_required= VALUE_TRUE
        synopticCommandArray.append(copy.copy(synopticCommand))
        i+=1

        synopticCommand.id= str(i)
        synopticCommand.position= '2'
        synopticCommand.label= 'LBL_COMMAND_EQUIPMENT'
        synopticCommand.default= VALUE_FALSE
        synopticCommand.module_action_id= '101701'
        synopticCommand.multiselect= VALUE_TRUE
        synopticCommand.item_required= VALUE_FALSE
        synopticCommandArray.append(copy.copy(synopticCommand))
        i+=1

# Relleno la tabla ui.graphic_icons
graphicIconArray= []
graphicsCsv = os.path.join(projectDirectory + '/CSV/N_graphic_icons.csv')
with open(graphicsCsv) as inputStreamFile:
    firstLine = 1

    for line in inputStreamFile:
        graphicIconItem = GraphicIcon()
        if firstLine:
            head = line[:-1].split(';')
            firstLine = 0
        else:
            parametros = line[:-1].split(';')
            
            if parametros[head.index(GraphicIcon.ELEMENT_SUBTYPE)] == '':
                element_subtype = "00"
            else:
                graphicIconItem.idSubtype= parametros[head.index(GraphicIcon.ELEMENT_SUBTYPE)]
                element_subtype = "{:0>2d}".format(int(graphicIconItem.idSubtype))
            graphicIconItem.idType= parametros[head.index(GraphicIcon.ELEMENT_TYPE)]
            element_type = "{:0>4d}".format(int(graphicIconItem.idType))
            
            graphicIconItem.id =  element_subtype + element_type
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
            graphicIconArray.append(graphicIconItem)

#  Relleno de la tabla ui.synoptic_layer_elements
synopticLayerElementsArray =[]
elementsByElementType= {"elements" : {}}
for synoptic in synopticsArray:
    xml = ET.parse(os.path.join(rootDirectory + '/' + ENVIROMENT_NAME + '/SVG/' + synoptic.ruta))
    rel_x = 1
    rel_y = 1
    root = xml.getroot()
    i = len(root)
    newIconCount= 0
    
    layersBySynoptic= [x for x in synopticLayersArray if synoptic.id == x.synoptic_id and x.idElementType]
    for layer in layersBySynoptic:
        i = len(root)
        idElementType= "{:0>2d}".format(int(layer.idElementType))
        if not elementsByElementType or not idElementType in elementsByElementType:
            with open(os.path.join(rootDirectory + '/../scripts/' + ENVIROMENT_NAME + '/Properties/Type ' + idElementType, "element_values.csv")) as file:
                firstLine = 1
                for line in file:
                    if firstLine:
                        encabezado = line[:-1].split(';')
                        elementsByElementType[idElementType]= 1
                        firstLine= 0
                    else:
                        parametros = line[:-1].split(';')
                        element= {}
                        element["idType"]= str(int(idElementType))
                        element["alias"]= parametros[encabezado.index('alias')]
                        element["id"]= "Element:" + element["idType"] + ":" + parametros[encabezado.index('item')]
                        element["elementId"]= "Element:" + element["idType"] + ":" + parametros[encabezado.index('item')]
                        # element["idLayer"]= layer.id
                        if encabezado[1] == 'element_subtype' and parametros[encabezado.index('element_subtype')] and parametros[encabezado.index('element_subtype')] != VALUE_NULL:
                            element["idSubtype"]= parametros[encabezado.index('element_subtype')]
                            # Retrocompatibilidad con xml antiguos cambiar vinculaciones elementId
                            auxElement= copy.copy(element)
                            auxElement["id"]= "Element:" + "{:0>2d}".format(int(parametros[encabezado.index('element_subtype')])) + "-" + element["idType"] + ":" + parametros[encabezado.index('item')]
                            elementsByElementType["elements"][auxElement["id"]]= auxElement
                        elementsByElementType["elements"][element["id"]]= element
        
        if layer.svgFile:
            graphicIconItem = GraphicIcon()                
            if not layer.idElementSubtype:
                element_subtype = "00"
            else:
                graphicIconItem.idSubtype= layer.idElementSubtype
                element_subtype = "{:0>2d}".format(int(layer.idElementSubtype))
            graphicIconItem.idType= layer.idElementType
            element_type = "{:0>4d}".format(int(graphicIconItem.idType))
            
            graphicIconsByElementType = [x for x in graphicIconArray if layer.idElementType == x.idType and (not x.idSubtype or layer.idElementSubtype == x.idSubtype)]
            graphicIconMaxId = max(graphicIconsByElementType, key=attrgetter('id'))
            if len(graphicIconMaxId.id) == 6:
                graphicIconItem.id+= str("{:0>2d}".format(newIconCount))
            else:
                graphicIconItem.id= str("{:0>8d}".format(int(graphicIconMaxId.id)+1))
            newIconCount= newIconCount + 1
            
            graphicIconItem.name = graphicIconMaxId.name
            graphicIconItem.alarms = VALUE_FALSE
            graphicIconItem.keep_aspect_ratio= graphicIconMaxId.keep_aspect_ratio
            graphicIconItem.svg_file = "'img/graphic_icon/" + layer.svgFile + "'"
            graphicIconItem.measures = graphicIconMaxId.measures
            graphicIconItem.draw_file =  graphicIconMaxId.draw_file
            graphicIconItem.js_custom = graphicIconMaxId.js_custom
            graphicIconItem.layerId = layer.id
            graphicIconArray.append(copy.copy(graphicIconItem))
                      
        while (i != 0):
            i = i-1
            id = root[i].attrib['id']
            if id in elementsByElementType["elements"] and layer.idElementType == elementsByElementType["elements"][id]["idType"] and (not layer.idElementSubtype or layer.idElementSubtype == elementsByElementType["elements"][id]["idSubtype"]):
                synopticLayerElement = SynopticLayerElement()
                synopticLayerElement.idElementType= layer.idElementType
                synopticLayerElement.x= str(int(float(root[i].attrib['x'])*float(rel_x)))
                synopticLayerElement.y= str(int(float(root[i].attrib['y'])*float(rel_y)))
                synopticLayerElement.height= root[i].attrib['height']
                synopticLayerElement.width= root[i].attrib['width']

                if not layer.svgFile: 
                    graphicIconsByElementType = [x for x in graphicIconArray if not x.layerId and elementsByElementType["elements"][id]["idType"] == x.idType and (not x.idSubtype or elementsByElementType["elements"][id]["idSubtype"] == x.idSubtype)]
                    if len(graphicIconsByElementType) == 1 and not graphicIconsByElementType[0].width:
                        graphicIconArray[graphicIconArray.index(graphicIconsByElementType[0])].height= synopticLayerElement.height
                        graphicIconArray[graphicIconArray.index(graphicIconsByElementType[0])].width= synopticLayerElement.width
                    graphicIconId = [x.id for x in graphicIconArray if not x.layerId and synopticLayerElement.height == x.height and synopticLayerElement.width == x.width and x.idType == synopticLayerElement.idElementType and (not x.idSubtype or elementsByElementType["elements"][id]["idSubtype"] == x.idSubtype)]
                    if not graphicIconId:
                        try:
                            newGraphicIcon= copy.copy(max(graphicIconsByElementType, key=attrgetter('id')))
                            newGraphicIcon.id = max([x for x in graphicIconArray if elementsByElementType["elements"][id]["idType"] == x.idType and (not x.idSubtype or elementsByElementType["elements"][id]["idSubtype"] == x.idSubtype)], key=attrgetter('id')).id
                        except Exception as e:
                            print("Error: Se ha configurado incorrectamente el graphic icon asociado al elemento del xml: " + id)
                            print("Compruebe que el subtipo conigurado en el csv GRAPHIC_ICONS es correcto. En caso de duda y no necesitar especificar graphic icons distintos para distintos subtipos deje la columna subtipo vacia.")
                            exit()
                        
                        if len(newGraphicIcon.id) == 6:
                            newGraphicIcon.id+= str("{:0>2d}".format(newIconCount))
                        else:
                            newGraphicIcon.id= str("{:0>8d}".format(int(newGraphicIcon.id)+1))
                        graphicIconId= newGraphicIcon.id
                        newIconCount= newIconCount + 1
                        newGraphicIcon.height= synopticLayerElement.height
                        newGraphicIcon.width= synopticLayerElement.width
                        graphicIconArray.append(newGraphicIcon)
                    else:
                        graphicIconId= graphicIconId[0]
                else:
                    graphicIcon = [x for x in graphicIconArray if layer.id == x.layerId][0]
                    graphicIcon.height= synopticLayerElement.height
                    graphicIcon.width= synopticLayerElement.width
                    graphicIconId= graphicIcon.id
                
                if 'svg_file' in root[i].attrib:
                    graphicIcon = [x for x in graphicIconArray if root[i].attrib['svg_file'] == "'img/graphic_icon/" + x.svg_file + "'" and synopticLayerElement.height == x.height and synopticLayerElement.width == x.width and x.idType == synopticLayerElement.idElementType and (not x.idSubtype or elementsByElementType["elements"][id]["idSubtype"] == x.idSubtype)]
                    if not graphicIcon:
                        graphicIcon = [x for x in graphicIconArray if root[i].attrib['svg_file'] == "'img/graphic_icon/" + x.svg_file + "'" and not x.width]
                        if graphicIcon:
                            graphicIconArray[graphicIconArray.index(graphicIcon[0])].height= synopticLayerElement.height
                            graphicIconArray[graphicIconArray.index(graphicIcon[0])].width= synopticLayerElement.width
                        else:
                            graphicIcon = [x for x in graphicIconArray if x.svg_file == "'img/graphic_icon/" + root[i].attrib['svg_file'] + "'"]
                            newGraphicIcon= copy.copy(max(graphicIcon, key=attrgetter('id')))
                            if len(newGraphicIcon.id) == 6:
                                newGraphicIcon.id+= str("{:0>2d}".format(newIconCount))
                            else:
                                newGraphicIcon.id= str("{:0>8d}".format(int(newGraphicIcon.id)+1))
                            newGraphicIcon.height= synopticLayerElement.height
                            newGraphicIcon.width= synopticLayerElement.width
                            graphicIconArray.append(newGraphicIcon)
                        graphicIconId = newGraphicIcon.id
                    else:
                        graphicIconId = graphicIcon[0].id
                
                synopticLayerElement.graphic_icon_id= graphicIconId
                synopticLayerElement.element_id= elementsByElementType["elements"][id]["elementId"]
                synopticLayerElement.layer_id= layer.id
                synopticLayerElement.label= elementsByElementType["elements"][id]["alias"]
                if 'rotate' in root[i].attrib:
                    synopticLayerElement.horizontal_flip= VALUE_TRUE
                # if 'keepratio' in root[i].attrib:
                #     synopticLayerElement.keep_aspect_ratio= VALUE_FALSE

                synopticLayerElementsArray.append(synopticLayerElement)

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
writeFile(treeMenuOutputFile, treeMenuOutputLine)

#  Write graphic Icon file
if graphicIconArray:
    outputLine = GraphicIcon.getInsertHeader()
    for graphicIcon in graphicIconArray:
        if graphicIcon.width:
            outputLine += graphicIcon.getInsertRow()
        else:
            graphicIcon.width= DEFAULT_VALUE_WIDTH
            graphicIcon.height = DEFAULT_VALUE_HEIGHT
            outputLine += graphicIcon.getInsertRow()
    outputLine = outputLine[:-2] + ';' + LINE_BREAK + LINE_BREAK 
    outputFile = outputFolder + graphicIconFileName
    
    writeFile(outputFile, outputLine)

#  Write Module file
if modulesArray:
    outputLine = Module.getInsertHeader()
    for item in modulesArray:
        outputLine += item.getInsertRow()
    outputLine = outputLine[:-2] + ';' + LINE_BREAK + LINE_BREAK

    outputLine += ModuleValue.getInsertHeader()
    for item in moduleValuesArray:
        outputLine += item.getInsertRow()
    outputLine = outputLine[:-2] + ';' + LINE_BREAK + LINE_BREAK

    outputLine += WindowValue.getInsertHeader()
    for item in windowValuesArray:
        outputLine += item.getInsertRowArray()
        outputLine += LINE_BREAK 
    outputLine = outputLine[:-3] + ';' + LINE_BREAK + LINE_BREAK

    outputFile = outputFolder + moduleFileName
    writeFile(outputFile, outputLine)

if synopticsArray:
    if ONE_FILE:
        outputFile = outputFolder + "synopticsFull.sql"
        outputLine = ""
    for synoptic in synopticsArray:
        if not ONE_FILE:
            outputFile = outputFolder + synoptic.id + "." + synoptic.name + ".sql"
            outputLine = Synoptic.getInsertHeader()
        else:
            outputLine += Synoptic.getInsertHeader()
        outputLine += synoptic.getInsertRow()
        outputLine = outputLine[:-2] + ';' + LINE_BREAK + LINE_BREAK

        if synopticLayersArray:
            synopticLayersBySynoptic= [x for x in synopticLayersArray if synoptic.id == x.synoptic_id]
            if synopticLayersBySynoptic:
                outputLine += SynopticLayer.getInsertHeader()
                for item in synopticLayersBySynoptic:
                    outputLine += item.getInsertRow()
                outputLine = outputLine[:-2] + ';' + LINE_BREAK + LINE_BREAK

        if synopticLayerHirarchyArray:
            synopticLayerHirarchyBySynoptic= [x for x in synopticLayerHirarchyArray if synoptic.id == x.synoptic_id]
            if synopticLayerHirarchyBySynoptic:
                outputLine += SynopticLayerRelationships.getInsertHeader()
                for item in synopticLayerHirarchyBySynoptic:
                    outputLine += item.getInsertRow()
                outputLine = outputLine[:-2] + ';' + LINE_BREAK + LINE_BREAK

        if synopticCommandArray:
            synopticCommandsBySynoptic= [x for x in synopticCommandArray if synoptic.id == x.synoptic_id]
            if synopticCommandsBySynoptic:
                outputLine += SynopticCommand.getInsertHeader()
                for item in synopticCommandsBySynoptic:
                    outputLine += item.getInsertRow()
                outputLine = outputLine[:-2] + ';' + LINE_BREAK + LINE_BREAK

        if synopticElementTypeCommandArray:
            synopticElementTypeCommandsBySynoptic= [x for x in synopticElementTypeCommandArray if any(y.id == x.synopticCommandId for y in synopticCommandsBySynoptic)]
            if synopticElementTypeCommandsBySynoptic:
                outputLine += SynopticElementTypeCommand.getInsertHeader()
                for item in synopticElementTypeCommandsBySynoptic:
                    outputLine += item.getInsertRow()
                outputLine = outputLine[:-2] + ';' + LINE_BREAK + LINE_BREAK 

        if synopticLayerElementsArray:
            synopticLayerElementsBySynoptic= [x for x in synopticLayerElementsArray if any(y.id == x.layer_id for y in synopticLayersBySynoptic)]
            if synopticLayerElementsBySynoptic:
                outputLine += SynopticLayerElement.getInsertHeader()
                for synopticLayerElement in synopticLayerElementsBySynoptic:
                    outputLine += synopticLayerElement.getInsertRow()
                outputLine = outputLine[:-2] + ';' + LINE_BREAK + LINE_BREAK
        if not ONE_FILE:
            writeFile(outputFile, outputLine)
    if ONE_FILE:
        writeFile(outputFile, outputLine)