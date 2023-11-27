class Synoptic:
    LINE_BREAK= "\n"
    NAME= "alias"
    DESCRIPTION= "alias"
    LABEL= "alias"
    BASE_LAYER= "layer"

    id= ""
    name= ""
    description= ""
    label= ""
    icon= ""
    base_layer= ""
    exclusive_layers= ""
    ruta= ""

    @classmethod
    def getInsertHeader(self):
        result =  "-- Para los identificadores de los sinopticos seguiremos los siguientes criterios" + self.LINE_BREAK
        result += "-- Id iterado --> 3 cifras" + self.LINE_BREAK
        result += "INSERT INTO ui.synoptics " + self.LINE_BREAK
        result += "(id, name, description, label, icon, base_layer, exclusive_layers) VALUES " + self.LINE_BREAK
        return result

    def getInsertRow(self):
        result = "(" + self.id + ", '" + self.name + "', '" + self.description + "', '" + self.label + "', " + self.icon + ", '" + self.base_layer
        result += "', " + self.exclusive_layers + "),"  + self.LINE_BREAK
        return result