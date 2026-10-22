import sys
import os
import copy
import errno

from Entities.Threshold import Threshold
from Entities.Transition import Transition

if len(sys.argv) < 2:
    print("Usage:")
    print("  arg1 = Name of project.")
    print("\n")
    print("Dirs:")
    print("  Config --> '/Properties/state_machine/conditions.csv'")
    print("  Config --> '/Properties/state_machine/transitions.csv'")
    exit()

THRESHOLD_ONE_SUFFIX= "_1"
THRESHOLD_TWO_SUFFIX= "_2"
LINE_BREAK = '\n'
VALUE_NULL = 'NULL'

DIRECTORY_NAME= os.path.dirname(__file__)
PROJECT_NAME= sys.argv[1]

# Output files
outputFile = os.path.join(DIRECTORY_NAME, PROJECT_NAME + "/Config/state_machine/14.state_machine/")

# Se precargan los operadores logicos
operatorsFile = open(os.path.join(DIRECTORY_NAME, PROJECT_NAME + "/Properties/state_machine/operation_ids.csv"))
operatorsOutputStream = operatorsFile.readline()
operatorHeaders = operatorsOutputStream.split(";")
operatorsArray= []
for line in operatorsFile:
    operator= {}
    params= line.split(";")
    operator["id"]= params[operatorHeaders.index("operation_id")]
    operator["exp"]= params[operatorHeaders.index("operation_exp")]
    operatorsArray.append(operator)

# Se abre el fichero csv para recuperar el array de elementos
conditionsFile = open(os.path.join(DIRECTORY_NAME, PROJECT_NAME + "/Properties/state_machine/conditions.csv"))
conditionsOutputStream = conditionsFile.readline()
conditionHeaders = conditionsOutputStream.split(";")
conditionId= 1
thresholdId= 1
thresholdBeanArray = []
conditionBeanArray = []
for line in conditionsFile:
    params= line.split(";")

    conditionBean= Threshold()
    conditionBean.conditionId= str(conditionId)
    conditionBean.alias= params[conditionHeaders.index(Threshold.COLUMN_ALIAS)]
    conditionBean.elementTypeId= params[conditionHeaders.index(Threshold.COLUMN_ELEMENT_TYPE_ID)]
    conditionBean.operationId= params[conditionHeaders.index(Threshold.COLUMN_OPERATION_ID)]
    if any(x["exp"] == conditionBean.operationId for x in operatorsArray):
        conditionBean.operationId= [x["id"] for x in operatorsArray if x["exp"] == conditionBean.operationId][0]
    conditionBean.value1= params[conditionHeaders.index(Threshold.COLUMN_VALUE_1)]
    if Threshold.COLUMN_VALUE_2 in conditionHeaders and conditionHeaders.index(Threshold.COLUMN_VALUE_2):
        conditionBean.value2= params[conditionHeaders.index(Threshold.COLUMN_VALUE_2)]
    if Threshold.COLUMN_TIMER in conditionHeaders and conditionHeaders.index(Threshold.COLUMN_TIMER) and params[conditionHeaders.index(Threshold.COLUMN_TIMER)]:
        conditionBean.timer= params[conditionHeaders.index(Threshold.COLUMN_TIMER)]
    else:
        conditionBean.timer= VALUE_NULL

    # Threshold 1
    threshold= copy.copy(conditionBean)
    threshold.thresholdId= str(thresholdId)
    threshold.thresholdAlias= conditionBean.alias + THRESHOLD_ONE_SUFFIX
    threshold.thresholdDescription= conditionBean.alias + THRESHOLD_ONE_SUFFIX + "_DESC"
    threshold.thresholdLabelAlias= "LBL_" + conditionBean.alias + THRESHOLD_ONE_SUFFIX
    threshold.thresholdLabelDescription= "LBL_" + conditionBean.alias + THRESHOLD_ONE_SUFFIX + "_DESC"
    threshold.thresholdValue= conditionBean.value1
    # Default
    # threshold1.thresholdDataTypeId= Threshold.VALU_DATA_TYPE_FLOAT_ID
    # threshold1.enabled= Threshold.VALUE_TRUE
    # threshold1.visible= Threshold.VALUE_TRUE
    # threshold1.createdAt= Threshold.VALUE_CURRENT_TIME
    # threshold1.updatedAt= Threshold.VALUE_CURRENT_TIME
    conditionBean.thresholdVal1Id= threshold.thresholdId
    thresholdBeanArray.append(copy.copy(threshold))
    conditionBean.threshold1= copy.copy(threshold)
    thresholdId = thresholdId+1

    # Threshold 2
    if conditionBean.value2:
        threshold.thresholdId= str(thresholdId)
        threshold.thresholdAlias= conditionBean.alias + THRESHOLD_TWO_SUFFIX
        threshold.thresholdDescription= conditionBean.alias + THRESHOLD_TWO_SUFFIX + "_DESC"
        threshold.thresholdLabelAlias= "LBL_" + conditionBean.alias + THRESHOLD_TWO_SUFFIX
        threshold.thresholdLabelDescription= "LBL_" + conditionBean.alias + THRESHOLD_TWO_SUFFIX + "_DESC"
        threshold.thresholdValue= conditionBean.value2
        thresholdBeanArray.append(copy.copy(threshold))
        conditionBean.thresholdVal2Id= threshold.thresholdId
        conditionBean.threshold2= copy.copy(threshold)
        thresholdId = thresholdId+1
    else:
        conditionBean.thresholdVal2Id= VALUE_NULL
    
    conditionId= conditionId+1
    conditionBeanArray.append(conditionBean)

# Se abre el fichero csv para recuperar el array de elementos
transitionsFile = open(os.path.join(DIRECTORY_NAME, PROJECT_NAME + "/Properties/state_machine/transitions.csv"))
transitionsOutputStream = transitionsFile.readline()
transitionHeaders = transitionsOutputStream.split(";")
stateMachineId= 1
stateMachineBean= ""
stateMachineBeanArray = []
stateId= 1
isNewTransition = 1
for line in transitionsFile:
    params= line.split(";")

    # state_machine
    if not stateMachineBean or stateMachineBean.stateMachineAlias != params[transitionHeaders.index(Transition.COLUMN_STATE_MACHINE_ALIAS)]:
        if stateMachineBean:
            stateMachineId= stateMachineId+1
            stateMachineBeanArray.append(stateMachineBean)
        isNewTransition = 1
        stateMachineBean= Transition()
        stateMachineBean.statusArray= []
        stateMachineBean.transtionsArray= []
        stateMachineBean.transtionConditionArray= []
        stateMachineBean.stateMachineId= str(stateMachineId)
        stateMachineBean.stateMachineAlias= params[transitionHeaders.index(Transition.COLUMN_STATE_MACHINE_ALIAS)]
        stateMachineBean.stateMachineDescription= stateMachineBean.stateMachineAlias + "_DESC"
        stateMachineBean.stateMachineLabelAlias= "LBL_" + stateMachineBean.stateMachineAlias
        stateMachineBean.stateMachineLabelDescription= "LBL_" + stateMachineBean.stateMachineAlias + "_DESC"
    elif stateMachineBean.stateAliasIn != params[transitionHeaders.index(Transition.COLUMN_STATE_ALIAS_IN)]:
        isNewTransition = 1
    elif Transition.COLUMN_STATE_ALIAS_OUT in transitionHeaders and transitionHeaders.index(Transition.COLUMN_STATE_ALIAS_OUT) and params[transitionHeaders.index(Transition.COLUMN_STATE_ALIAS_OUT)] != stateMachineBean.stateAliasOut:
        isNewTransition = 1
    elif (not Transition.COLUMN_STATE_ALIAS_OUT in transitionHeaders or not transitionHeaders.index(Transition.COLUMN_STATE_ALIAS_OUT) or not params[transitionHeaders.index(Transition.COLUMN_STATE_ALIAS_OUT)]) and stateMachineBean.stateAliasOut:
        isNewTransition = 1
    else:
        isNewTransition = 0
    
    stateMachineBean.stateAliasIn= params[transitionHeaders.index(Transition.COLUMN_STATE_ALIAS_IN)]
    stateMachineBean.nexusAnd= params[transitionHeaders.index(Transition.COLUMN_NEXUS_AND)]
    stateMachineBean.conditionAlias= params[transitionHeaders.index(Transition.COLUMN_CONDITION_ALIAS)]
    if Transition.COLUMN_STATE_ALIAS_OUT in transitionHeaders and transitionHeaders.index(Transition.COLUMN_STATE_ALIAS_OUT):
        stateMachineBean.stateAliasOut= params[transitionHeaders.index(Transition.COLUMN_STATE_ALIAS_OUT)]
    
    if isNewTransition:
        transitionBean= Transition()
        transitionBean.stateMachineId= stateMachineBean.stateMachineId
        # state In
        stateBean= [x for x in stateMachineBean.statusArray if x.stateAlias == stateMachineBean.stateAliasIn]
        if not stateBean:
            stateBean= Transition()
            stateBean.stateMachineId= stateMachineBean.stateMachineId
            stateBean.stateId= str(stateId)
            stateBean.stateAlias= stateMachineBean.stateAliasIn
            stateBean.stateDescription= stateMachineBean.stateAliasIn + "_DESC"
            stateBean.stateLabelAlias= "LBL_" + stateMachineBean.stateAliasIn
            stateBean.stateLabelDescription= "LBL_" + stateMachineBean.stateAliasIn + "_DESC"
            if stateMachineBean.transtionsArray and any(x.stateAliasOut == stateMachineBean.stateAliasIn for x in stateMachineBean.transtionsArray):
                stateBean.initState = Transition.VALUE_FALSE
            else:
                stateBean.initState = Transition.VALUE_TRUE
            stateId= stateId+1
            stateMachineBean.statusArray.append(copy.copy(stateBean))
        else:
            stateBean= stateBean[0]
        transitionBean.stateInId= stateBean.stateId
        transitionBean.stateIn= copy.copy(stateBean)

        # state Out
        stateBean= [x for x in stateMachineBean.statusArray if x.stateAlias == stateMachineBean.stateAliasOut]
        if not stateBean:
            stateBean= Transition()
            stateBean.stateMachineId= stateMachineBean.stateMachineId
            stateBean.stateId= str(stateId)
            stateBean.stateAlias= stateMachineBean.stateAliasOut
            stateBean.stateDescription= stateMachineBean.stateAliasOut + "_DESC"
            stateBean.stateLabelAlias= "LBL_" + stateMachineBean.stateAliasOut
            stateBean.stateLabelDescription= "LBL_" + stateMachineBean.stateAliasOut + "_DESC"
            stateBean.initState = Transition.VALUE_FALSE
            stateMachineBean.statusArray.append(copy.copy(stateBean))
            stateId= stateId+1
        else:
            stateBean= stateBean[0]
        transitionBean.stateOutId= stateBean.stateId
        transitionBean.stateOut= copy.copy(stateBean)
        
        # transition
        transitionByStateIn= [x for x in stateMachineBean.transtionsArray if x.stateInId == transitionBean.stateInId]
        transitionBean.transitionId= str(len(transitionByStateIn) + 1)
        transitionBean.nexusAnd= stateMachineBean.nexusAnd
        stateMachineBean.transtionsArray.append(copy.copy(transitionBean))

    # transition_condition
    conditionBean= [x for x in conditionBeanArray if x.alias == stateMachineBean.conditionAlias][0]
    transtionConditionBean= copy.copy(transitionBean)
    transtionConditionBean.conditionId= conditionBean.conditionId
    stateMachineBean.transtionConditionArray.append(copy.copy(transtionConditionBean))
stateMachineBeanArray.append(stateMachineBean)


# write lines
for stateMachine in stateMachineBeanArray:
    usedIdsMap= {}
    outputLine= Threshold.getThresholdInsertHeader()
    for row in stateMachine.transtionConditionArray:
        condition= [x for x in conditionBeanArray if x.conditionId == row.conditionId][0]
        if condition.threshold1.thresholdId not in usedIdsMap.keys():
            outputLine+= condition.threshold1.getThresholdInsertRow()
            usedIdsMap[condition.threshold1.thresholdId]= 1
        if condition.thresholdVal2Id != VALUE_NULL and condition.threshold2.thresholdId not in usedIdsMap.keys():
            outputLine+= condition.threshold2.getThresholdInsertRow()
            usedIdsMap[condition.threshold2.thresholdId]= 1
    outputLine = outputLine[:-2] + ';' + LINE_BREAK + LINE_BREAK

    usedIdsMap= {}
    outputLine+= Threshold.getConditionInsertHeader()
    for row in stateMachine.transtionConditionArray:
        condition= [x for x in conditionBeanArray if x.conditionId == row.conditionId][0]
        if condition.conditionId not in usedIdsMap.keys():
            outputLine+= condition.getConditionInsertRow()
            usedIdsMap[condition.conditionId]= 1
    outputLine = outputLine[:-2] + ';' + LINE_BREAK + LINE_BREAK

    outputLine+= Transition.getStateMachineInsertHeader()
    outputLine+= stateMachine.getStateMachineInsertRow()
    outputLine = outputLine[:-2] + ';' + LINE_BREAK + LINE_BREAK
    
    outputLine+= Transition.getStateInsertHeader()
    for row in stateMachine.statusArray:
        outputLine+= row.getStateInsertRow()
    outputLine = outputLine[:-2] + ';' + LINE_BREAK + LINE_BREAK

    outputLine+= Transition.getTransitionInsertHeader()
    for row in stateMachine.transtionsArray:
        outputLine+= row.getTransitionInsertRow()
    outputLine = outputLine[:-2] + ';' + LINE_BREAK + LINE_BREAK

    outputLine+= Transition.getTransitionConditionInsertHeader()
    for row in stateMachine.transtionConditionArray:
        outputLine+= row.getTransitionConditionInsertRow()
    outputLine = outputLine[:-2] + ';' + LINE_BREAK + LINE_BREAK

    # save file
    stateMachineFile= outputFile + "{:0>2d}".format(int(stateMachine.stateMachineId)) + "_" + stateMachine.stateMachineAlias + ".sql"
    if not os.path.exists(os.path.dirname(stateMachineFile)):
        try:
            os.makedirs(os.path.dirname(stateMachineFile))
        except OSError as exc:
            if exc.errno != errno.EEXIST:
                raise
    with open(stateMachineFile, "w") as inputStreamFile:
        try:
            inputStreamFile.write(outputLine)
            inputStreamFile.close()
            print(stateMachineFile + " se generó sin errores")
        except Exception as e:
            print (e)
    