class SynopticLayer:
    LINE_BREAK= "\n"

    ID_ELEMENT_TYPE= "id_element_type"
    ID_ELEMENT_SUBTYPE= "id_element_subtype"
    ID_SYNOPTIC= "synoptic_alias"
    ID_PARENT_ALIAS= "parent_alias"
    NAME= "alias"
    MODULE_ACTION_ID= "module_action_id"
    SVG_FILE= "svg_file"
    
    id= ""
    # base_layer= ""
    synoptic_id= ""
    name= ""
    parentAlias= ""
    label= ""
    moduleActionId= ""
    visible= "true"
    idElementType= ""
    idElementSubtype= ""
    synopticAlias= ""
    svgFile= ""
        
    @classmethod
    def getInsertHeader(self):
        result =  "-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios" + self.LINE_BREAK
        result += "-- Id Synoptico                                   --> 3 cifras" + self.LINE_BREAK
        result += "-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras" + self.LINE_BREAK
        result += "-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras" + self.LINE_BREAK
        result += "INSERT INTO ui.synoptic_layers " + self.LINE_BREAK
        result += "(id, synoptic_id, name, label, visible) VALUES " + self.LINE_BREAK
        return result

    def getInsertRow(self):
        result = "(" + self.id + ", " + self.synoptic_id + ", '" + self.name + "', '" + self.label + "', " + self.visible + ")," + self.LINE_BREAK
        return result

    @classmethod
    def getCsvHeader(self):
        result = "synoptic_alias;alias;parent_alias;id_element_type;id_element_subtype;module_action_id;svg_file;" + self.LINE_BREAK
        return result

    def getCsvRow(self):
        result = self.synopticAlias + ";" + self.name + ";" + self.parentAlias + ";" + self.idElementType + ";" + self.idElementSubtype + ";" + self.moduleActionId + ";" + self.svgFile + ";" + self.LINE_BREAK
        return result