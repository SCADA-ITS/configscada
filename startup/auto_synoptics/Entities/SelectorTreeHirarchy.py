class SelectorTreeHierarchy:
    LINE_BREAK = "\n"
    
    PARENT_ID= "parent_alias"
    CHILD_ID= "alias"

    # id= ""
    selector_tree_id= "1"
    node_parent_id= ""
    node_child_id= ""

    @classmethod    
    def getInsertHeader(self):
        result = "INSERT INTO ui.selector_tree_hierarchies " + self.LINE_BREAK
        result += "(selector_tree_id, node_parent_id, node_child_id) VALUES " + self.LINE_BREAK
        return result

    def getInsertRow(self):
        result = "(" + self.selector_tree_id + ", " + self.node_parent_id + ", " + self.node_child_id + ")," + self.LINE_BREAK
        return result