class WindowValue:
    LINE_BREAK= "\n"
    NAME= "alias"
    DESCRIPTION= "alias"
    LABEL= "alias"
    BASE_LAYER= "layer"

    module_id= ""
    window_param_id= ""
    value= ""
    label= ""

    @classmethod
    def getInsertHeader(self):
        result = "INSERT INTO ui.window_values " + self.LINE_BREAK
        result += "(module_id, window_param_id, value) VALUES " + self.LINE_BREAK
        return result

    def getInsertRow(self):
        result = "(" + self.module_id + ", " + self.window_param_id + ", '" + self.value + "'),"  + self.LINE_BREAK
        return result

    def getInsertRowArray(self):
        result = "(" + self.module_id + ", 4, '825'),"  + self.LINE_BREAK
        result += "(" + self.module_id + ", 8, 'true'),"  + self.LINE_BREAK
        result += "(" + self.module_id + ", 9, 'img/module/default.png'),"  + self.LINE_BREAK
        result += "(" + self.module_id + ", 10, 'img/module/default.png'),"  + self.LINE_BREAK
        result += "(" + self.module_id + ", 11, '#464646'),"  + self.LINE_BREAK
        result += "(" + self.module_id + ", 12, '" + self.label + "'),"  + self.LINE_BREAK
        result += "(" + self.module_id + ", 14, 'true'),"  + self.LINE_BREAK
        return result