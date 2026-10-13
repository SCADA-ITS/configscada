class SelectorTreeNode:
    LINE_BREAK = "\n"

    ALIAS= "alias"
    MODULE_ID= "module_id"

    id= ""
    selector_tree_id= "1"
    label= ""
    module_id= ""

    @classmethod    
    def getInsertHeader(self):
        result = "INSERT INTO ui.selector_tree_nodes " + self.LINE_BREAK
        result += "(id, selector_tree_id, label, module_id) VALUES " + self.LINE_BREAK
        return result

    def getInsertRow(self):
        result = "(" + self.id + ", " + self.selector_tree_id + ", '" + self.label + "', " + self.module_id + ")," + self.LINE_BREAK
        return result