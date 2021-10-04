import PlanLibraryOrder from "./planLibraryOrder.js";
import ConditionDialogBox from "./conditionDialogBox.js";
import DataTable from "../../../../component/datatable.js";

const MOVE_PLAN_ORDER_POSITION_UP = 1;
const MOVE_PLAN_ORDER_POSITION_DOWN = 2;

export default class PlanLibraryConditionOrder extends PlanLibraryOrder {

	constructor(config, uid, plan, planOrder, onUpdate) {
		
		super(config, uid, plan, planOrder, onUpdate);		
		this.dtParams = undefined;
	}
	
	loadData() {
		
		if (this.dtParams) {
			
			this.dtParams.callbacks();
		}
	}
	
	getContent() {
		
		let self = this;		
		let viewParams = {};
		
		viewParams = {
		
			rows: [
				{
					cols: [
						{
							rows: getDataTable()
						}
					]	
				},
				{ height: 30 }
			]
		}	
		
		function getDataTable() {
		
			let events = {};
			
			function getMetadata() {
		
				let gridMetadata = {};

				const gridOptionValues = [
					{ gridOption: { id: 1 }, value: "false" },
					{ gridOption: { id: 2 }, value: "false" },
					{ gridOption: { id: 3 }, value: "1" },
					{ gridOption: { id: 4 }, value: "asc" },
					{ gridOption: { id: 7 }, value: "false" },
					{ gridOption: { id: 8 }, value: "false" },
					{ gridOption: { id: 9 }, value: "true" },
					{ gridOption: { id: 11 }, value: "false" },
					{ gridOption: { id: 13 }, value: "false" },
					{ gridOption: { id: 16 }, value: "img/grid/default.png" },
					{ gridOption: { id: 21 }, value: "auto" },
					{ gridOption: { id: 23 }, value: "false" }
				];
				
				gridMetadata = {
					filter: true,
					label: "",
					visible: true,
					gridOptionValues: gridOptionValues,
					gridFields: [],
					gridCommands: []
				};
				
				gridMetadata.gridCommands = [
					{
						id: 1,
						value: "LBL_NEW",
						icon: "mdi mdi-file-plus-outline",
						default_: false,
						label: "LBL_NEW",
						showText: true,
						position: 1,
						multiselect: true,
						itemRequired: false
					},{
						id: 2,
						value: "LBL_EDIT",
						icon: "mdi mdi-pencil",
						default_: true,
						label: "LBL_EDIT",
						showText: true,
						position: 2,
						multiselect: false,
						itemRequired: true
					},
					{
						id: 3,
						default_: false,
						icon: "mdi mdi-arrow-up-bold",
						label: "LBL_MOVE_UP",
						multiselect: false,
						position: 3,
						showText: false,
						itemRequired: true
					},
					{
						id: 4,
						default_: false,
						icon: "mdi mdi-arrow-down-bold",
						label: "LBL_MOVE_DOWN",
						multiselect: false,
						position: 4,
						showText: false,
						itemRequired: true
					},
					{
						id: 5,
						value: "LBL_DELETE",
						icon: "mdi mdi-delete-outline",
						label: "LBL_DELETE",
						default_: false,
						showText: true,
						position: 5,
						multiselect: false,
						itemRequired: true
					}
				]
				
				events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];

				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 1,
					function: onCreate
				});

				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 2,
					function: onEdit
				});

				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 3,
					function: (selectedIds) => {
	
						if (Array.isArray(selectedIds) && selectedIds.length === 1) {
							
							movePlanOrderCondition(selectedIds[0], MOVE_PLAN_ORDER_POSITION_UP);
						} 
					}
				});
				
				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 4,
					function: (selectedIds) => {
	
						if (Array.isArray(selectedIds) && selectedIds.length === 1) {
							
							movePlanOrderCondition(selectedIds[0], MOVE_PLAN_ORDER_POSITION_DOWN);
						} 
					}
				});
				
				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 5,
					function: function() {
						
						webix.confirm({
							title: i18n.t("LBL_DIALOG_REMOVE_CONDITION"),
							ok: i18n.t("LBL_YES"),
							cancel: i18n.t("LBL_NO"),
							text: i18n.t("LBL_ARE_YOU_SURE_REMOVE_CONDITION"),
							width: 600
						}).then(function() {
						
							onRemove();
						});
					}
				});
				
				gridMetadata.gridFields = [
					{
						id: 1,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: true,
						needsTranslation: false,
						parameterized: false,
						position: 1,
						required: true,
						sourceField: "id",
						visible: false,
						gridFieldDataType: {
							id: 1,
							name: "string"
						}	
					},
					{
						id: 2,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_ELEMENT",
						needsTranslation: false,
						parameterized: false,
						position: 2,
						required: true,
						sourceField: "elementAlias",
						visible: true,
						gridFieldDataType: {
							id: 1,
							name: "string"
						}	
					},
					{
						id: 3,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_PARAM",
						needsTranslation: false,
						parameterized: false,
						position: 3,
						required: true,
						sourceField: "parameter",
						visible: true,
						gridFieldDataType: {
							id: 1,
							name: "string"
						}	
					},
					{
						id: 4,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_STATE",
						needsTranslation: false,
						parameterized: false,
						position: 4,
						required: false,
						sourceField: "elementState",
						visible: true,
						gridFieldDataType: {
							id: 1,
							name: "string"
						}	
					},
					{
						id: 5,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_OPERATION",
						needsTranslation: false,
						parameterized: false,
						position: 5,
						required: false,
						sourceField: "operation",
						visible: true,
						gridFieldDataType: {
							id: 1,
							name: "string"
						}	
					},
					{
						id: 6,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_OPERATION_VALUE_ONE",
						needsTranslation: false,
						parameterized: false,
						position: 6,
						required: false,
						sourceField: "operationVal1",
						visible: true,
						gridFieldDataType: {
							id: 1,
							name: "string"
						}	
					},
					{
						id: 5,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_OPERATION_VALUE_TWO",
						needsTranslation: false,
						parameterized: false,
						position: 7,
						required: false,
						sourceField: "operationVal2",
						visible: true,
						gridFieldDataType: {
							id: 1,
							name: "string"
						}	
					},
					{
						id: 5,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_NEXUS_OPERATOR",
						needsTranslation: false,
						parameterized: false,
						position: 5,
						required: false,
						sourceField: "nexusOperator",
						visible: true,
						gridFieldDataType: {
							id: 1,
							name: "string"
						}	
					}
				];
				
				return gridMetadata;
			}
			
			function onCreate() {

				(new ConditionDialogBox(null, self.config, function (planOrderCondition){

					if (!self.planOrder.planOrderConditions || !Array.isArray(self.planOrder.planOrderConditions)){

						self.planOrder.planOrderConditions = [];
					}
				
					planOrderCondition.planOrderConditionOrder = self.planOrder.planOrderConditions.length;

					planOrderCondition.id = "PlanOrderCondition:" + self.planOrder.id.substr(self.planOrder.id.indexOf(":"), self.planOrder.id.length) + planOrderCondition.planOrderConditionOrder;
					
					self.planOrder.planOrderConditions.push(planOrderCondition);
				
					let newItem ={};				
					newItem.id = planOrderCondition.id;
					newItem.elementAlias = planOrderCondition.element.alias;
					
					if(planOrderCondition.elementTypeParam){
					
						newItem.parameter =  i18n.t(planOrderCondition.elementTypeParam.labelAlias);
					}
					
					if(planOrderCondition.elementTypeState){
					
						newItem.elementState =  i18n.t(planOrderCondition.elementTypeState.labelAlias);
					}
					
					if(planOrderCondition.thresholdOperation){
					
						newItem.operation = i18n.t(planOrderCondition.thresholdOperation.labelAlias);
					}
					
					newItem.planOrderConditionOrder = planOrderCondition.planOrderConditionOrder;
					newItem.operationVal1 = planOrderCondition.thresholdVal1;
					newItem.operationVal2 = planOrderCondition.thresholdVal2;
					newItem.nexusOperator = (planOrderCondition.nexusAnd?"And":"Or");
					
					self.dtParams.add(newItem);

				})).show();
			}

			function onEdit() {

				let selectedItems = self.dtParams.getSelectedItems()
						
				if (Array.isArray(selectedItems) && selectedItems.length === 1) {
					
					let selectedItem = self.planOrder.planOrderConditions.find(e => e.id == selectedItems[0].id);

					(new ConditionDialogBox(selectedItem, self.config, function (planOrderCondition){
				
						selectedItem.element = planOrderCondition.element;
						selectedItem.elementTypeParam = planOrderCondition.elementTypeParam;
						selectedItem.elementTypeState = planOrderCondition.elementTypeState;
						selectedItem.thresholdOperation = planOrderCondition.thresholdOperation;
					
						selectedItems[0].elementAlias = selectedItem.element.alias;
						selectedItems[0].parameter =  i18n.t(selectedItem.elementTypeParam.labelAlias);
						selectedItems[0].elementState =  i18n.t(selectedItem.elementTypeState.labelAlias);
						selectedItems[0].operation = i18n.t(selectedItem.thresholdOperation.labelAlias);
						selectedItems[0].operationVal1 = selectedItem.thresholdVal1;
						selectedItems[0].operationVal2 = selectedItem.thresholdVal2;
						selectedItems[0].nexusOperator = (selectedItem.nexusAnd?"And":"Or");

						self.dtParams.update(selectedItems[0]);
	
					})).show();
				}
			}

			function movePlanOrderCondition(planOrderConditionId, movePlanOrderType) {

			}

			function onRemove() {

				let selectedItems = self.dtParams.getSelectedItems()
						
				if (Array.isArray(selectedItems) && selectedItems.length === 1) {
				
					self.dtParams.delete(selectedItems);
				}
			}
			
			function getInitialData() {

				let gridData = [];

				if(self.planOrder.planOrderConditions && Array.isArray(self.planOrder.planOrderConditions) && self.planOrder.planOrderConditions.length > 0){

					gridData = self.planOrder.planOrderConditions.map(e => {
						
						return {
							id: e.id,
							elementAlias: e.element.alias,
							parameter: i18n.t(e.elementTypeParam.labelAlias),
							parameter: i18n.t(e.elementTypeState.labelAlias),
							operation: i18n.t(e.thresholdOperation.labelAlias),
							operationVal1: e.thresholdVal1,
							operationVal2: e.thresholdVal2,
							nexusOperator: (e.nexusAnd?"And":"Or")
						}
					});
				}
				
				return gridData;
			}

			events[DataTable.EventTypes.ON_ITEM_DBL_CLIC] = onEdit;
			
			self.dtParams = new DataTable(null, null, getMetadata(), getInitialData(), events);
			
			return self.dtParams.getView();
		}
		
		return viewParams;
	}
	
	createCustomPlanOrder(newPlanOrder) {
		
		if (newPlanOrder) {
			
			let planOrderType = window.em.getByField(EntityType.PLAN_ORDER_TYPE, "id", newPlanOrder.planOrderTypeId);
				
			if (planOrderType && Array.isArray(planOrderType.planOrderTypeParams)) {
				
				let planOrderValues = planOrderType.planOrderTypeParams.map(e => {
					
					return {

						planOrderTypeParamId: e.id,
						value: "" 
					}
				});
				
				newPlanOrder.planOrderValues = planOrderValues;
			}
		}
	}
	
	getPlanOrder() {
		
		let self = this;		
		let planOrder;
		
		if (self.planOrder) {
			
			planOrder = {
				
				id: self.planOrder.id,
				planOrderTypeId: self.planOrder.planOrderType.id,
				planOrderConditions: []
			}
			
			if (Array.isArray(self.planOrder.planOrderConditions) && self.planOrder.planOrderConditions.length > 0) {
								
				self.planOrder.planOrderConditions.forEach(row => {
				
					let planOrderCondition = {
						value: row.value,
						elementId: row.element.id,
						elementTypeParamId: row.elementTypeParam.id,
						thresholdOperationId: row.thresholdOperation.id,
						thresholdVal1: row.thresholdVal1,
						thresholdVal2: row.thresholdVal2,
						nexusAnd: row.nexusAnd
					}
					
					planOrder.planOrderConditions.push(planOrderCondition);
				});	
			}
		}
		
		return planOrder;
	}
}	
