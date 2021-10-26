import PlanLibraryOrder from "./planLibraryOrder.js";
import ChangeStatusDialogBox from "./changeStatusDialogBox.js";
import DataTable from "../../../../component/datatable.js";
import MessageBox from "../../../../component/messageBox.js";

export default class PlanLibraryChangeStatusOrder extends PlanLibraryOrder {

	constructor(config, uid, plan, planOrder, onUpdate) {
		
		super(config, uid, plan, planOrder, onUpdate);		
		
		this.dtParams = undefined;

		this.planOrderElementTypeStates = [];		
		if(planOrder){
			this.planOrderElementTypeStates = webix.copy(this.planOrder.planOrderElementTypeStates);
		}
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
						value: "LBL_DELETE",
						icon: "mdi mdi-delete-outline",
						label: "LBL_DELETE",
						default_: false,
						showText: true,
						position: 3,
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
					function: function() {
						
						let ms = new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_ARE_YOU_SURE_REMOVE_CHANGE_PARAM_CONFIG"), onAccept, null);
						ms.show();
						
						function onAccept() {
							
							onRemove();
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
						label: "LBL_ELEMENT_STATE",
						needsTranslation: false,
						parameterized: false,
						position: 3,
						required: true,
						sourceField: "elementState",
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

				(new ChangeStatusDialogBox(null, function (planOrderElementTypeState){
									
					planOrderElementTypeState.id = "PlanOrderElementTypeState:" + self.planOrder.id.substr(self.planOrder.id.indexOf(":"), self.planOrder.id.length) + planOrderElementTypeState.elementTypeState.id.substr(planOrderElementTypeState.elementTypeState.id.indexOf(":"), planOrderElementTypeState.elementTypeState.id.length);

					if(!self.planOrderElementTypeStates || !Array.isArray(self.planOrderElementTypeStates)){

						self.planOrderElementTypeStates = [];	
					}

					self.planOrderElementTypeStates.push(planOrderElementTypeState);
				
					let newItem ={};				
					newItem.id = planOrderElementTypeState.id;
					newItem.elementAlias = planOrderElementTypeState.element.alias;
					newItem.elementState =  i18n.t(planOrderElementTypeState.elementTypeState.labelAlias);

					self.dtParams.add(newItem);

				})).show();
			}

			function onEdit() {

				let selectedItems = self.dtParams.getSelectedItems()
						
				if (Array.isArray(selectedItems) && selectedItems.length === 1) {
					
					let selectedPlanOrder = self.planOrderElementTypeStates.find(e => e.id == selectedItems[0].id);

					(new ChangeStatusDialogBox(selectedPlanOrder, function (planOrderElementTypeState){
				
						selectedPlanOrder.element = planOrderElementTypeState.element;
						selectedPlanOrder.elementTypeState = planOrderElementTypeState.elementTypeState;
					
						selectedItems[0].elementAlias = selectedPlanOrder.element.alias;
						selectedItems[0].elementState =  i18n.t(selectedPlanOrder.elementTypeState.labelAlias);

						self.dtParams.update(selectedItems[0]);
	
					})).show();
				}
			}

			function onRemove() {

				let selectedItems = self.dtParams.getSelectedItems()
						
				if (Array.isArray(selectedItems) && selectedItems.length === 1) {
				
					let rowIndexRemove = self.planOrderElementTypeStates.findIndex(e => e.id == selectedItems[0].id);
					self.planOrderElementTypeStates.splice(rowIndexRemove, 1);
					self.dtParams.delete(selectedItems);
				}
			}
			
			function getInitialData() {

				let gridData = [];

				if(self.planOrderElementTypeStates && Array.isArray(self.planOrderElementTypeStates) && self.planOrderElementTypeStates.length > 0){

					gridData = self.planOrderElementTypeStates.map(e => {
						
						return {
							id: e.id,
							elementAlias: e.element.alias,
							elementState: i18n.t(e.elementTypeState.labelAlias)
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
		
	}
	
	getPlanOrder() {
		
		let self = this;		
		let planOrder;
		
		if (self.planOrder) {
			
			planOrder = {
				
				id: self.planOrder.id,
				planOrderTypeId: self.planOrder.planOrderType.id,
				planOrderElementTypeStates: []
			}
			
			if (Array.isArray(self.planOrderElementTypeStates) && self.planOrderElementTypeStates.length > 0) {
								
				self.planOrderElementTypeStates.forEach(row => {
				
					let planOrderElementTypeState = {
						elementId: row.element.id,
						elementTypeStateId: row.elementTypeState.id
					}
					
					planOrder.planOrderElementTypeStates.push(planOrderElementTypeState);
				});	
			}
		}
		
		return planOrder;
	}
}	
