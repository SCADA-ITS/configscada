class SynopticLayerElement:
    LINE_BREAK = "\n"
    
    layer_id= ""
    element_id= ""
    graphic_icon_id= ""
    label= ""
    x= ""
    y= ""
    horizontal_flip= "false"
    rotate= "NULL"
    tooltip= "true"
    show_text= "false"
    text_color= "NULL"
    selectable= "true"
    idElementType= ""

    @classmethod    
    def getInsertHeader(self):
        result = "INSERT INTO ui.synoptic_layer_elements " + self.LINE_BREAK
        result += "(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES " + self.LINE_BREAK
        return result

    def getInsertRow(self):
        result = "(" + self.layer_id + ", '" + self.element_id + "', " + self.graphic_icon_id + ", '" + self.label + "', " + self.x + ", " + self.y + ", " + self.horizontal_flip
        result += ", " + self.rotate + ", " + self.tooltip + ", " + self.show_text + ", " + self.text_color + ", " + self.selectable + "),"  + self.LINE_BREAK
        return result