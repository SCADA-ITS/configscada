class Module:
    LINE_BREAK= "\n"
    NAME= "alias"
    DESCRIPTION= "alias"
    LABEL= "alias"
    BASE_LAYER= "layer"

    id= ""
    module_type_id= "4"
    name= ""
    description= ""
    default_view_type_id= "1"
    default_system_permission_id= "NULL"
    js_file= 'modules/synopticModule.js'
    js_method_draw= "getView"
    js_method_callbacks= "loadData"
    data_query= "NULL" 
    wss_topic= "NULL" 
    enabled= "true" 
    visible= "true" 
    autoload= "NULL"

    @classmethod
    def getInsertHeader(self):
        result = "INSERT INTO ui.modules " + self.LINE_BREAK
        result += '(id, module_type_id, "name", description, default_view_type_id, default_system_permission_id, js_file, js_method_draw, js_method_callbacks, data_query, wss_topic, enabled, visible, autoload) VALUES ' + self.LINE_BREAK
        return result

    def getInsertRow(self):
        result = "(" + self.id + ", " + self.module_type_id + ", '" + self.name + "', '" + self.description + "', " + self.default_view_type_id + ", " + self.default_system_permission_id + ", '" + self.js_file
        result += "', '" + self.js_method_draw + "', '" + self.js_method_callbacks + "', " + self.data_query + ", " + self.wss_topic + ", " + self.enabled + ", " + self.visible + ", " + self.autoload + "),"  + self.LINE_BREAK
        return result