class SynopticCommand:
    LINE_BREAK= "\n"
    
    id= ""
    synoptic_id= ""
    position= ""
    label= ""
    icon= ""
    default= ""
    module_action_id= ""
    args= ""
    view_type_id= ""
    multiselect= ""
    show_text= ""
    item_required= ""
    context= ""

    @classmethod
    def getInsertHeader(self):
        result = "INSERT INTO ui.synoptic_commands " + self.LINE_BREAK
        result += "(id, synoptic_id, position, label, icon, \"default\", module_action_id, args, view_type_id, multiselect, show_text, "
        result += "item_required, context) VALUES " + self.LINE_BREAK
        return result

    def getInsertRow(self):
        result = "(" + self.id + ", " + self.synoptic_id + ", " + self.position + ", '" + self.label + "', '" + self.icon + "', " + self.default 
        result += ", " + self.module_action_id + ", " + self.args + ", " + self.view_type_id + ", " + self.multiselect + ", " + self.show_text 
        result += ", " + self.item_required + ", " + self.context + "),"  + self.LINE_BREAK
        return result