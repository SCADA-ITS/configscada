import PlanLibraryOrder from "./planLibraryOrder.js";
import ChangeParamDialogBox from "./changeParamDialogBox.js";
import DataTable from "../../../../component/datatable.js";
import SearchDialogBox from "../../../../component/searchDialogBox.js";
import InputDialogBox from "../../../../component/inputDialogBox.js";
import { util } from "../../../../lib/util.js";

export default class PlanLibraryChangeParamConfigOrder extends PlanLibraryOrder {

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
						
						webix.confirm({
							title: i18n.t("LBL_DIALOG_REMOVE_CHANGE_PARAM_CONFIG"),
							ok: i18n.t("LBL_YES"),
							cancel: i18n.t("LBL_NO"),
							text: i18n.t("LBL_ARE_YOU_SURE_REMOVE_CHANGE_PARAM_CONFIG"),
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
						label: "LBL_VALUE",
						needsTranslation: false,
						parameterized: false,
						position: 4,
						required: false,
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
			
			function onCreate() {

				(new ChangeParamDialogBox(null, function (planOrderElement){
				
					planOrderElement.id = "PlanOrderElementValue:" + self.planOrder.id.substr(self.planOrder.id.indexOf(":"), self.planOrder.id.length) + planOrderElement.elementTypeParam.id.substr(planOrderElement.elementTypeParam.id.indexOf(":"), planOrderElement.elementTypeParam.id.length);
					
					self.planOrder.planOrderElementValues.push(planOrderElement);
				
					let newItem ={};				
					newItem.id = planOrderElement.id;
					newItem.elementAlias = planOrderElement.element.alias;
					newItem.parameter =  i18n.t(planOrderElement.elementTypeParam.labelAlias);
					newItem.value = planOrderElement.value;

					self.dtParams.add(newItem);

				})).show();
			}

			function onEdit() {

				let selectedItems = self.dtParams.getSelectedItems()
						
				if (Array.isArray(selectedItems) && selectedItems.length === 1) {
					
					let selectedPlanOrder = self.planOrder.planOrderElementValues.find(e => e.id == selectedItems[0].id);

					(new ChangeParamDialogBox(selectedPlanOrder, function (planOrderElement){
				
						selectedPlanOrder.element = planOrderElement.element;
						selectedPlanOrder.elementTypeParam = planOrderElement.elementTypeParam;
						selectedPlanOrder.value = planOrderElement.value;
					
						selectedItems[0].elementAlias = selectedPlanOrder.element.alias;
						selectedItems[0].parameter =  i18n.t(selectedPlanOrder.elementTypeParam.labelAlias);
						selectedItems[0].value = selectedPlanOrder.value;

						self.dtParams.update(selectedItems[0]);
	
					})).show();
				}
			}

			function onRemove() {

				let selectedItems = self.dtParams.getSelectedItems()
						
				if (Array.isArray(selectedItems) && selectedItems.length === 1) {
				
					self.dtParams.delete(selectedItems);
				}
			}
			
			function getInitialData() {

				let gridData = [];
				
				let orderResponse = webix.ajax().sync().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.order, { id: self.planOrder.id }));
				
				if (orderResponse && orderResponse.responseText) {

					self.planOrder = JSON.parse(orderResponse.responseText);

					if(self.planOrder.planOrderElementValues && Array.isArray(self.planOrder.planOrderElementValues) && self.planOrder.planOrderElementValues.length > 0){

						gridData = self.planOrder.planOrderElementValues.map(e => {
							
							return {
								id: e.id,
								elementAlias: e.element.alias,
								parameter: i18n.t(e.elementTypeParam.labelAlias),
								value: e.value
							}
						});
					}	
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
