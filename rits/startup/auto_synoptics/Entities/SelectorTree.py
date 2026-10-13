class SelectorTree:
    LINE_BREAK = "\n"
    
    id= "1"
    default_module_id= "920"
    label= "LBL_SINOPTICOS"

    @classmethod  
    def getInsertHeader(self):
        result = "INSERT INTO ui.selector_trees " + self.LINE_BREAK
        result += "(id, default_module_id, label) VALUES " + self.LINE_BREAK
        return result

    def getInsertRow(self):
        result = "(" + self.id + ", " + self.default_module_id + ", '" + self.label + "')," + self.LINE_BREAK
        return result