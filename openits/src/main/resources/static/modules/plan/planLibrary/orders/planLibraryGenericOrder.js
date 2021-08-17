import PlanLibraryOrder from "./planLibraryOrder.js";
import DataTable from "../../../../component/datatable.js";
import InputDialogBox from "../../../../component/inputDialogBox.js";

export default class PlanLibraryGenericOrder extends PlanLibraryOrder {

	constructor(config, uid, plan, planOrder) {
		
		super(config, uid, plan, planOrder);		
		this.dtParams = undefined;
	}
	
	getView() {
		
		let self = this;
		
		let view = {
			id: "plan-order-lib-plan-" + self.uid,
			rows: [
				drawHeader(),
				{ height: 10 },
				drawContent()
			]
		};
		
		function drawHeader() {
			
			let viewHeader = {
				view: "toolbar",
				height: 30,
				borderless: true,
				elements: [
					{
						view: "label",
						label: "<img src='" + self.planOrder.planOrderType.icon + "' class='grid-icon'/><span class='grid-title'>" + i18n.t(self.planOrder.planOrderType.labelAlias) + "</span>" 
					}
				]
			}
			
			return viewHeader;
		}
		
		function drawContent() {
				
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
					]
					
					events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];

					events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
						commandId: 1,
						function: onEdit
					});
					
					events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
						commandId: 2,
						function: function() {
							
							webix.confirm({
								title: i18n.t("LBL_DIALOG_UNDO_ALL_VALUES"),
								ok: i18n.t("LBL_YES"),
								cancel: i18n.t("LBL_NO"),
								text: i18n.t("LBL_ARE_YOU_SURE_UNDO_ALL_VALUES"),
								width: 600
							}).then(function() {
							
								self.dtParams.clear();
								self.dtParams.add(getInitialData());
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
							label: "LBL_VALUE",
							needsTranslation: false,
							parameterized: false,
							position: 4,
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
						
						(new InputDialogBox(selectedItem.param, i18n.t("LBL_ENTER_VALUE_OF_TYPE") + ": " + selectedItem.dataType, function(value) {
							
							let newItem = webix.copy(selectedItem);
							
							newItem.value = value;
							self.dtParams.update(newItem);
							
						}, true, selectedItem.value)).show();
					}
				}
				
				function getInitialData() {
					
					let gridData = [];
					
					let planOrderType = window.em.getByField(EntityType.PLAN_ORDER_TYPE, "id", self.planOrder.planOrderType.id);
					
					if (planOrderType) {
						
						gridData = planOrderType.planOrderTypeParams.map(e => {
						
							let value = undefined;
							
							if (Array.isArray(self.planOrder.planOrderValues) && self.planOrder.planOrderValues.length > 0) {
								
								let planOrderValue = self.planOrder.planOrderValues.find(p => p.planOrderTypeParam.id === e.id);
								
								if (planOrderValue) {
									
									value = planOrderValue.value;
								}
							}
							
							return {
								id: e.id,
								param: i18n.t(e.labelAlias),
								dataTypeId: e.dataType.id,
								dataType: i18n.t(e.dataType.labelAlias),
								value: value
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
		
		return view;
	}
	
	loadData() {
		
		if (this.dtParams) {
			
			this.dtParams.callbacks();
		}
	}
}	
