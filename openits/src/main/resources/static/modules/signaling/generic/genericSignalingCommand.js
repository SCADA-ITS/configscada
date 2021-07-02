import DataTable from "../../../component/datatable.js";
import InputDialogBox from "../../../component/inputDialogBox.js";

export default class GenericSignalingCommand {

	constructor(parent, selectedElementIds, commandElement) {

		this.uid = webix.uid();
		this.parent = parent;
		this.selectedElementIds = selectedElementIds;
		this.commandElement = commandElement;
		this.dtParams = undefined;
	}

	getView() {
		
		let self = this;

		let view = {
			
			id: this.uid,
			header: i18n.t(this.commandElement.labelAlias),
			body: drawComponents()
		}
		
		function drawComponents() {
			
			let viewComponents = {
				
				rows: [
					{ height: 30 },
					getViewInfo(),
					{ height: 20 },
					getViewParams(),
					getViewActions()
				]
			};
			
			function getViewInfo() {
				
				let viewInfo;
						
				viewInfo = {
					
					rows: [
						{
							cols: [
								{
									view: "label",
									label: `<div class="title"><i style="color:#33425e; font-size: 2em" class="mdi mdi-information"></i></div>`,
									align: "center",
									height: 50,
									width: 56
								},
								{
									view: "label",
									css: "alarm-view-header-details-text",
									label: `<span class="alarm-view-header-details-text">${i18n.t("LBL_INFO")}</span>`,
									align: "left",
									height: 50
								},
								{}
							]
						},
						{
							cols: [
								{ width: 50 },
								{
									borderless: true,
									width: 100,
									template: `<image style="width: 90px; height: 90px; display: block; margin: 0 auto" src="img/${self.commandElement.image}" onerror="this.onerror=null;this.src='img/send-command-default.png';" />`
								},
								{ width: 20	},
								{
									view: "textarea",
									readonly : true,
									height: 100,
									value: self.commandElement.labelDescription ? i18n.t(self.commandElement.labelDescription) : i18n.t(self.commandElement.labelAlias) 
								}
							]
						}
					]
				}
				
				return viewInfo;
			}
			
			function getViewParams() {
				
				let viewParams = {};
				
				if (Array.isArray(self.commandElement.commandElementTypeParams) && self.commandElement.commandElementTypeParams.length > 0) {
					
					viewParams = {
					
						rows: [
							{
								cols: [
									{
										view: "label",
										label: `<div class="title"><i style="color:#33425e; font-size: 2em" class="mdi mdi-table"></i></div>`,
										align: "center",
										height: 50,
										width: 56
									},
									{
										view: "label",
										css: "alarm-view-header-details-text",
										label: `<span class="alarm-view-header-details-text">${i18n.t("LBL_PARAMS")}</span>`,
										align: "left",
										height: 50
									},
									{}
								]
							},
							{
								cols: [
									{ width: 50 },
									{
										rows: getDataTable()
									}
								]	
							},
							{ height: 30 }
						]
					}	
				}
				
				function getDataTable() {
				
					let events = {};
					
					function getMetadata() {
				
						let gridMetadata = {};

						const gridOptionValues = [
							{ gridOption: { id: 1 }, value: "false" },
							{ gridOption: { id: 7 }, value: "false" },
							{ gridOption: { id: 8 }, value: "false" },
							{ gridOption: { id: 9 }, value: "true" },
							{ gridOption: { id: 11 }, value: "false" },
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
								value: "LBL_CLEAR_ALL",
								icon: "wxi-trash",
								label: "LBL_CLEAR_ALL",
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
									title: i18n.t("LBL_DIALOG_RESET_COMMAND_PARAMS"),
									ok: i18n.t("LBL_YES"),
									cancel: i18n.t("LBL_NO"),
									text: i18n.t("LBL_ARE_YOU_SURE_RESET_COMMAND_PARAMS"),
									width: 600
								}).then(function() {
								
									self.dtParams.clear();
									self.dtParams.add(getInitialData());
								});
							}
						});
						
						gridMetadata.gridFields = [
							{
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
					
						let gridData = self.commandElement.commandElementTypeParams.map(e => {
							
							return {
								id: e.id,
								param: i18n.t(e.labelAlias),
								dataTypeId: e.dataType.id,
								dataType: i18n.t(e.dataType.labelAlias),
								value: undefined
							}
						});
						
						return gridData;
					}
	
					events[DataTable.EventTypes.ON_ITEM_DBL_CLIC] = onEdit;
					
					self.dtParams = new DataTable(null, null, getMetadata(), getInitialData(), events);
					
					return self.dtParams.getView();
				}
				
				return viewParams;
			}
			
			function getViewActions() {
			
				let viewActions = {};
				
				viewActions = {
					view: "toolbar",
					height: 45,
					borderless: true,
					elements: [
						{},
						{ 
							view: "button", 
							id: "btn-send-" + self.uid,
							css: "action-button",
							width: 130,
							height: 35,
							label: i18n.t("LBL_SEND"),
							on: {
								onItemClick() {
			
									webix.confirm({
										title: i18n.t("LBL_SEND_SIGNALING_CONFIRM"),
										ok: i18n.t("LBL_YES"),
										cancel: i18n.t("LBL_NO"),
										text: i18n.t("LBL_SEND_SIGNALING_CONFIRM_DESCRIPTION") + (self.selectedElementIds.length > 1 ? " " + self.selectedElementIds.length + " " + i18n.t("LBL_EQUIPMENT_SELECTED") : ""),
										width: 600
									}).then(function() {
										
										sendCommandElement();
										
									});					
								}
							}
						}
					]
				};
				
				function sendCommandElement() {

					let body = {
						commands : []
					};
					
					if (self.parent.config.restEndpoints && self.parent.config.restEndpoints.command) {
						
						self.selectedElementIds.forEach(selectedElementId => {
	
							let command = {
								elementId: selectedElementId,
								commandElementTypeId: self.commandElement.id
							}
						
							if (Array.isArray(self.commandElement.commandElementTypeParams) && self.commandElement.commandElementTypeParams.length > 0 &&
							    self.dtParams) {
							
								let params = self.dtParams.getAllItems();
								
								if (Array.isArray(params) && params.length > 0) {
									
									command.commandElementValues = [];
									
									params.forEach(param => {
										
										command.commandElementValues.push({
											
											commandElementTypeParamId: param.id,
											value: param.value
										});	
									});
								}
							}
							
							body.commands.push(command);	
							
						});
						
						if (Array.isArray(body.commands) && body.commands.length > 0) {
												
							webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.parent.config.restEndpoints.command, body).then(function(data) {
								
								webix.alert({
									title: i18n.t("LBL_COMMAND_SEND"),
								  	ok: i18n.t("LBL_ACCEPT"),
								  	text: i18n.t("LBL_COMMAND_SEND_SUCCESSFULLY"),
									width: 600
								})
							});	
						}
					}
				}
				
				return viewActions;
			}
			
			return viewComponents;
		}
		
		return view;
	}
	
	loadData() {
		
		if (this.dtParams) {
			
			this.dtParams.callbacks();
		}
	}
}
