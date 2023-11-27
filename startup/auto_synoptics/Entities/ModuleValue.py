class ModuleValue:
    LINE_BREAK= "\n"
    NAME= "alias"
    DESCRIPTION= "alias"
    LABEL= "alias"
    BASE_LAYER= "layer"

    module_id= ""
    module_param_id= "4" 
    value= ""

    @classmethod
    def getInsertHeader(self):
        result = "INSERT INTO ui.module_values " + self.LINE_BREAK
        result += "(module_id, module_param_id, value) VALUES " + self.LINE_BREAK
        return result

    def getInsertRow(self):
        result = "(" + self.module_id + ", " + self.module_param_id + ", '" + self.value + "'),"  + self.LINE_BREAK
        return result