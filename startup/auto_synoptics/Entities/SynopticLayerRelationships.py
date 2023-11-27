class SynopticLayerRelationships:
    LINE_BREAK = "\n"
    
    synoptic_id= ""
    parent_alias= ""
    parent_layer_id= ""
    child_layer_id= ""

    @classmethod    
    def getInsertHeader(self):
        result = "INSERT INTO ui.synoptic_layer_relationships " + self.LINE_BREAK
        result += "(synoptic_id, parent_layer_id, child_layer_id) VALUES " + self.LINE_BREAK
        return result

    def getInsertRow(self):
        result = "(" + self.synoptic_id + ", " + self.parent_layer_id + ", " + self.child_layer_id + ")," + self.LINE_BREAK
        return result