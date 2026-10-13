class GraphicIcon:

    LINE_BREAK = "\n"
    
    ELEMENT_TYPE= "element_type"
    ELEMENT_SUBTYPE= "element_subtype"
    ALIAS= "alias"
    WIDTH= "width"
    HEIGHT= "height"
    SVG_FILE= "svg_file"
    KEEP_ASPECT_RATIO= "keepRatio"
    JS_CUSTOM_TYPE_RENDER= "js_custom_type_render"

    id= ""
    idType= ""
    idSubtype= ""
    name= ""
    alarms= ""
    measures= ""
    draw_file= ""
    width= ""
    height= ""
    svg_file= ""
    js_custom= ""
    keep_aspect_ratio= "false"
    layerId= 0

    @classmethod
    def getInsertHeader(self):
        result =   "-- Para los identificadores de los graphic_icons seguiremos los siguientes criterios" + self.LINE_BREAK
        result +=  "-- Orden del graphic_icon dentro del mismo tipo --> 2 cifras" + self.LINE_BREAK
        result +=  "-- Subtipo del elemento --> 2 cifras (0 si no tiene subtipo)" + self.LINE_BREAK
        result +=  "-- Tipo del elemento --> 4 cifras" + self.LINE_BREAK
        result +=  "INSERT INTO ui.graphic_icons " + self.LINE_BREAK
        result +=  "(id, name, description, alarms, measures, js_draw_file, width, height, svg_file, js_custom_type_render, keep_aspect_ratio) VALUES" + self.LINE_BREAK
        return result

    def getInsertRow(self):
        result =  "(" + self.id + ", '" + self.name + "', '" + self.name + "', " + self.alarms + ", " + self.measures + ", '" + self.draw_file + "', "
        result += self.width + ", " + self.height + ", " + self.svg_file + ", '" + self.js_custom + "'," + self.keep_aspect_ratio + "),"  + self.LINE_BREAK        
        return result 