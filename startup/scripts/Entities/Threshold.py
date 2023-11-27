class Threshold:

    LINE_BREAK = "\n"
    VALUE_TRUE = "true"
    VALUE_CURRENT_TIME = "CURRENT_TIMESTAMP"
    VALUE_DATA_TYPE_FLOAT_ID = 3
    VALUE_ELEMENT_TYPE_PARAM_ID = 1
    VALUE_PARAM_TYPE_ID = 2
    VALUE_THRESHOLD_NULL = "null"
    VALUE_THRESHOLD_UNDEFINED = "NULL"

    COLUMN_ALIAS= "alias"
    COLUMN_VALUE_1= "value_1"
    COLUMN_OPERATION_ID= "operation_id"
    COLUMN_VALUE_2= "value_2"
    COLUMN_ELEMENT_TYPE_ID= "element_type_id"
    COLUMN_TIMER= "timer"

    # neg
    alias= ""
    value1= ""
    operationId= ""
    value2= ""
    elementTypeId= ""
    timer= ""

    # common
    enabled= VALUE_TRUE
    visible= VALUE_TRUE
    createdAt= VALUE_CURRENT_TIME
    updatedAt= VALUE_CURRENT_TIME

    # threshold POJO
    thresholdId= ""
    thresholdAlias= ""
    thresholdDescription= ""
    thresholdLabelAlias= ""
    thresholdLabelDescription= ""
    thresholdDataTypeId= str(VALUE_DATA_TYPE_FLOAT_ID)
    thresholdValue = ""
    
    # condition POJO
    conditionId= ""
    thresholdVal1Id= ""
    thresholdVal2Id= ""
    elementTypeParamId= str(VALUE_ELEMENT_TYPE_PARAM_ID)
    paramTypeId= str(VALUE_PARAM_TYPE_ID)

    @classmethod
    def getThresholdInsertHeader(self):
        result = "INSERT INTO static.sm_thresholds " + self.LINE_BREAK
        result += "(threshold_id, alias, description, label_alias, label_description, data_type_id, value, enabled, visible, created_at, updated_at) VALUES " + self.LINE_BREAK
        return result

    def getThresholdInsertRow(self):
        result =  "(" + self.thresholdId + ", '" + self.thresholdAlias + "', '" + self.thresholdDescription + "', '" + self.thresholdLabelAlias + "', '" + self.thresholdLabelDescription + "', "
        result += self.thresholdDataTypeId + ", '" + self.thresholdValue + "', " + self.enabled + ", " + self.visible + ", " + self.createdAt + "," + self.updatedAt + "),"  + self.LINE_BREAK        
        return result

    @classmethod
    def getConditionInsertHeader(self):
        result = "INSERT INTO static.sm_conditions " + self.LINE_BREAK
        result += "(condition_id, threshold_operation_id, threshold_val1_id, threshold_val2_id, element_type_id, element_type_param_id, param_type_id, timer_id, enabled, visible, created_at, updated_at) VALUES " + self.LINE_BREAK
        return result

    def getConditionInsertRow(self):
        result =  "(" + self.conditionId + ", " + self.operationId + ", " + self.thresholdVal1Id + ", " + self.thresholdVal2Id + ", " + self.elementTypeId + ", "
        result += self.elementTypeParamId + ", " + self.paramTypeId + ", " + self.timer + ", " + self.enabled + ", " + self.visible + ", " + self.createdAt + "," + self.updatedAt + "),"  + self.LINE_BREAK        
        return result 