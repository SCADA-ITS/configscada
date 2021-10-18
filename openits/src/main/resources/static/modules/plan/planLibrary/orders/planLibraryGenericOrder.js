import { util } from "../../../../lib/util.js";
import PlanLibraryOrder from "./planLibraryOrder.js";
import DataTable from "../../../../component/datatable.js";
import InputDialogBox from "../../../../component/inputDialogBox.js";
import MessageBox from "../../../../component/messageBox.js";

export default class PlanLibraryGenericOrder extends PlanLibraryOrder {

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
				let editPermission = util.module.isAllowedPermission(self.config.requiredPermissions.edit);

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

				if (editPermission) {

					gridMetadata.gridCommands.push(
						{
							id: 1,
							value: "LBL_EDIT",
							icon: "mdi mdi-pencil",
							default_: true,
							label: "LBL_EDIT",
							showText: true,
							position: 1,
							multiselect: false,
							itemRequired: true
						},
						{
							id: 2,
							value: "LBL_UNDO_ALL",
							icon: "mdi mdi-undo",
							label: "LBL_UNDO_ALL",
							default_: false,
							showText: true,
							position: 2,
							multiselect: false,
							itemRequired: false
						}
					);
				}

				events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];

				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 1,
					function: onEdit
				});
				
				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 2,
					function: function() {
						
						let ms = new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_ARE_YOU_SURE_UNDO_ALL_VALUES"), onAccept, null);
						ms.show();
						
						function onAccept() {

							self.dtParams.clear();
							self.dtParams.add(getInitialData());							
						};
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
						label: "LBL_PARAM",
						needsTranslation: false,
						parameterized: false,
						position: 2,
						required: true,
						sourceField: "param",
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
						label: "LBL_DATA_TYPE",
						needsTranslation: false,
						parameterized: false,
						position: 3,
						required: true,
						sourceField: "dataType",
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
						label: "LBL_PLAN_ORDER_VALUE_ID",
						needsTranslation: false,
						parameterized: false,
						position: 4,
						required: true,
						sourceField: "planOrderValueId",
						visible: false,
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
						label: "LBL_VALUE",
						needsTranslation: false,
						replacementFunction: "getLocalDateString",
						parameterized: false,
						position: 5,
						required: true,
						sourceField: "value",
						visible: true,
						gridFieldDataType: {
							id: 1,
							name: "string"
						}	
					}
				];
				
				return gridMetadata;
			}
			
			function onEdit() {
				
				let selectedItems = self.dtParams.getSelectedItems()
						
				if (Array.isArray(selectedItems) && selectedItems.length === 1) {
					
					let selectedItem = selectedItems[0];
					
					(new InputDialogBox(selectedItem.param, i18n.t("LBL_ENTER_VALUE"), function(value) {
						
						let newItem = webix.copy(selectedItem);
						
						newItem.value = value;
						self.dtParams.update(newItem);
						
					}, true, selectedItem.value, 
						{id: selectedItem.dataTypeId,
						labelUnit: selectedItem.labelUnit,
						threshold1: selectedItem.threshold1,
						threshold2: selectedItem.threshold2})).show();
				}
			}
			
			function getInitialData() {
				
				let gridData = [];
				
				let planOrderType = window.em.getByField(EntityType.PLAN_ORDER_TYPE, "id", self.planOrder.planOrderType.id);
				
				if (planOrderType) {
					
					gridData = planOrderType.planOrderTypeParams.map(e => {
					
						let planOrderValue = undefined;
						
						if (Array.isArray(self.planOrder.planOrderValues) && self.planOrder.planOrderValues.length > 0) {
							
							planOrderValue = self.planOrder.planOrderValues.find(p => p.planOrderTypeParam.id === e.id);
						}
						
						return {
							id: e.id,
							param: i18n.t(e.labelAlias),
							dataTypeId: e.dataType.id,
							labelUnit: e.labelUnit,
							threshold1: e.threshold1,
							threshold2: e.threshold2,
							dataType: i18n.t(e.dataType.labelAlias),
							planOrderValueId: planOrderValue ? planOrderValue.id : undefined,
							value: planOrderValue ? planOrderValue.value : undefined 
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
				planOrderTypeId: self.planOrder.planOrderType.id
			}
			
			if (self.dtParams) {
			
				let dtParamRows = self.dtParams.getAllItems();
				
				if (Array.isArray(dtParamRows) && dtParamRows.length > 0) {
					
					planOrder.planOrderValues = [];
					
					dtParamRows.forEach(row => {
					
						let planOrderValue = {
							id: row.planOrderValueId,
							planOrderTypeParamId: row.id,
							value: row.value
						}
						
						planOrder.planOrderValues.push(planOrderValue);
					});	
				}
			}
		}
		
		return planOrder;
	}
}	
