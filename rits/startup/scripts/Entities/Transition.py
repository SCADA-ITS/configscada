class Transition:

    LINE_BREAK = "\n"
    VALUE_TRUE = "true"
    VALUE_FALSE = "false"
    VALUE_CURRENT_TIME = "CURRENT_TIMESTAMP"
    VALUE_NULL = "NULL"

    COLUMN_STATE_MACHINE_ALIAS= "state_machine_alias"
    COLUMN_STATE_ALIAS_IN= "state_alias_in"
    COLUMN_STATE_ALIAS_OUT= "state_alias_out"
    COLUMN_NEXUS_AND= "nexus_and"
    COLUMN_CONDITION_ALIAS= "condition_alias"

    # neg
    stateMachineAlias= ""
    stateAliasIn= ""
    stateAliasOut= ""
    nexusAnd= ""
    conditionAlias= ""
    
    # common 
    enabled= VALUE_TRUE
    visible= VALUE_TRUE
    createdAt= VALUE_CURRENT_TIME
    updatedAt= VALUE_CURRENT_TIME

    # state_machine POJO
    stateMachineId= ""
    stateMachineDescription= ""
    stateMachineLabelAlias= ""
    stateMachineLabelDescription= ""

    # state POJO
    stateId= ""
    stateAlias= ""
    stateDescription= ""
    stateLabelAlias= ""
    stateLabelDescription= ""
    initState= VALUE_FALSE
    time= VALUE_NULL

    # transition POJO
    transitionId= ""
    stateInId= ""
    stateOutId= ""

    # transition condition POJO
    conditionId= ""

    @classmethod
    def getStateMachineInsertHeader(self):
        result = "INSERT INTO static.sm_state_machines " + self.LINE_BREAK
        result += "(state_machine_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES " + self.LINE_BREAK
        return result

    def getStateMachineInsertRow(self):
        result =  "(" + self.stateMachineId + ", '" + self.stateMachineAlias + "', '" + self.stateMachineDescription + "', '" + self.stateMachineLabelAlias + "', '" + self.stateMachineLabelDescription + "', "
        result += self.enabled + ", " + self.visible + ", " + self.createdAt + "," + self.updatedAt + "),"  + self.LINE_BREAK        
        return result

    @classmethod
    def getStateInsertHeader(self):
        result = "INSERT INTO static.sm_states " + self.LINE_BREAK
        result += "(state_machine_id, state_id, alias, description, label_alias, label_description, init_state, t_min, enabled, visible, created_at, updated_at) VALUES " + self.LINE_BREAK
        return result

    def getStateInsertRow(self):
        result =  "(" + self.stateMachineId + ", " + self.stateId + ", '" + self.stateAlias + "', '" + self.stateDescription + "', '" + self.stateLabelAlias + "', '" + self.stateLabelDescription + "', "
        result += self.initState + ", " + self.time + ", " + self.enabled + ", " + self.visible + ", " + self.createdAt + "," + self.updatedAt + "),"  + self.LINE_BREAK        
        return result
    
    @classmethod
    def getTransitionInsertHeader(self):
        result = "INSERT INTO static.sm_transitions " + self.LINE_BREAK
        result += "(state_machine_id, state_id, transition_id, nexus_and, next_state_id, enabled, visible, created_at, updated_at) VALUES " + self.LINE_BREAK
        return result

    def getTransitionInsertRow(self):
        result =  "(" + self.stateMachineId + ", " + self.stateInId + ", " + self.transitionId + ", " + self.nexusAnd + ", " + self.stateOutId + ", "
        result += self.enabled + ", " + self.visible + ", " + self.createdAt + "," + self.updatedAt + "),"  + self.LINE_BREAK        
        return result
    
    @classmethod
    def getTransitionConditionInsertHeader(self):
        result = "INSERT INTO static.sm_transition_conditions " + self.LINE_BREAK
        result += "(state_machine_id, state_id, transition_id, condition_id, enabled, visible, created_at, updated_at) VALUES " + self.LINE_BREAK
        return result

    def getTransitionConditionInsertRow(self):
        result =  "(" + self.stateMachineId + ", " + self.stateInId + ", " + self.transitionId + ", " + self.conditionId + ", "
        result += self.enabled + ", " + self.visible + ", " + self.createdAt + "," + self.updatedAt + "),"  + self.LINE_BREAK        
        return result
