class SynopticElementTypeCommand:
    LINE_BREAK= "\n"
    
    synopticCommandId= ""
    elementType= ""
    module_action_id= ""
    view_type_id= "2"

    @classmethod
    def getInsertHeader(self):
        result = "INSERT INTO ui.synoptic_element_type_commands " + self.LINE_BREAK
        result += "(synoptic_command_id, element_type, module_action_id, view_type_id) VALUES " + self.LINE_BREAK
        return result

    def getInsertRow(self):
        result = "(" + self.synopticCommandId + ", '" + self.elementType + "', " + self.module_action_id + ", " + self.view_type_id + "),"  + self.LINE_BREAK
        return result