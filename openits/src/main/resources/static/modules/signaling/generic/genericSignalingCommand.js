import DataTable from "../../../component/datatable.js";
import InputDialogBox from "../../../component/inputDialogBox.js";
import MessageBox from "../../../component/messageBox.js";
import { util } from "../../../lib/util.js";

export default class GenericSignalingCommand {

	constructor(parent, selectedElementIds, commandElementType, isEditPlanOrder, isEmbedded) {

		this.parent = parent;
		this.uid = parent.uid;
		this.selectedElementIds = selectedElementIds;
		this.commandElementType = commandElementType;
		this.dtParams = undefined;
		this.isEditPlanOrder = isEditPlanOrder;
		this.isEmbedded = (isEmbedded === true);
	}

	getView() {

		let self = this;

		let view = {

			header: function() {

				const TAB_SIZES = {
					TAB_WIDTH: 180,
					TABBAR_WIDTH_PROPORTION: 0.953
				};

				let totalTabs = self.parent.element.commandElements.length;
				let maxWidth = "";
				let idHtml = "view-generic-signaling-command-body-"+ self.commandElementType.id.split(":").join("") + "-" + self.uid;

				if ($$(self.uid)) {

					let tabbarWidth = $$(self.uid).getParentView().$width * TAB_SIZES.TABBAR_WIDTH_PROPORTION;

					if (totalTabs * TAB_SIZES.TAB_WIDTH > tabbarWidth) {

						maxWidth = "inherit";
					}
				}

				return `<div title="${i18n.t(self.commandElementType.labelAlias)}" id='${idHtml}' style='width:${maxWidth}; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;'>
							<span>${i18n.t(self.commandElementType.labelAlias)}</span>
						</div>`;

			}(),
			body: drawComponents()
		}

		function drawComponents() {

			let viewComponents = {

				id: "view-generic-signaling-command-body-" + self.commandElementType.id + "-" + self.uid,
				commandElementTypeId: self.commandElementType.id,
				rows: []
			};

			viewComponents.rows.push({ height: 10 });

			if (!self.isEditPlanOrder || (self.isEditPlanOrder && !self.commandElementType.commandElementTypeParams)) {

				viewComponents.rows.push(getViewInfo());
				viewComponents.rows.push({ height: 10 });
			}

			viewComponents.rows.push(getViewParams());

			if (!self.isEditPlanOrder) {

				viewComponents.rows.push(getViewActions());
			}
			
			if (self.isEditPlanOrder && self.selectedElementIds && self.selectedElementIds.length >1) {

				viewComponents.rows.push(getViewActions());
			}

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
									template: `<image style="width: 90px; height: 90px; display: block; margin: 0 auto" src="img/${self.commandElementType.image}" onerror="this.onerror=null;this.src='img/send-command-default.png';" />`
								},
								{ width: 20 },
								{
									view: "textarea",
									readonly: true,
									height: 100,
									value: self.commandElementType.labelDescription ? i18n.t(self.commandElementType.labelDescription) : i18n.t(self.commandElementType.labelAlias)
								}
							]
						}
					]
				}

				return viewInfo;
			}

			function getViewParams() {

				let viewParams = {};

				if (Array.isArray(self.commandElementType.commandElementTypeParams) && self.commandElementType.commandElementTypeParams.length > 0) {

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

								let ms = new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_ARE_YOU_SURE_UNDO_COMMAND_PARAMS"), onAccept, null);
								ms.show();
								
								function onAccept() {
									
									//self.dtParams.clear();
									//self.dtParams.add(getInitialData());
									
									undoAll();									
								};
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
								replacementFunction: "getLocalDateString",
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
							},
							{
								extended: false,
								filtered: false,
								isEpochMilli: false,
								isPk: false,
								label: "LBL_CURRENT_VALUE",
								replacementFunction: "getLocalDateString",
								needsTranslation: false,
								parameterized: false,
								position: 5,
								required: true,
								sourceField: "currentValue",
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
							
							let currentValue = selectedItem.value;
							
							if (!currentValue) {
								
								currentValue = selectedItem.currentValue;
							} 

							(new InputDialogBox(selectedItem.param, i18n.t("LBL_ENTER_VALUE"), function(value) {

								let newItem = webix.copy(selectedItem);

								newItem.value = value;
								self.dtParams.update(newItem);

							}, true, currentValue, {
								id: selectedItem.dataTypeId,
								labelUnit: selectedItem.labelUnit,
								threshold1: selectedItem.threshold1,
								threshold2: selectedItem.threshold2
							})).show();
						}
					}
					
					function undoAll() {
						
						if (self.dtParams) {
							
							let oldData = self.dtParams.getAllItems();
							
							if (Array.isArray(oldData) && oldData.length > 0) {
								
								let newData = oldData.map(e => {
									
									let newItem = webix.copy(e);
									
									newItem.value = undefined;
									
									return newItem;
								})
								
								self.dtParams.update(newData);
								
							}
						}
					}

					function getInitialData() {

						let gridData;

						gridData = self.commandElementType.commandElementTypeParams.map(e => {

							let value = undefined;

							if (Array.isArray(self.commandElementType.commandElementValues) && self.commandElementType.commandElementValues.length > 0) {

								let commandElementValue = self.commandElementType.commandElementValues.find(v => v.commandElementTypeParam.id === e.id);

								if (commandElementValue) {

									value = commandElementValue.value;
								}
							}

							return {
								id: e.id,
								param: i18n.t(e.labelAlias),
								dataTypeId: e.dataType.id,
								labelUnit: e.labelUnit,
								threshold1: e.threshold1,
								threshold2: e.threshold2,
								dataType: i18n.t(e.dataType.labelAlias),
								value: value
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
							css: "action-button",
							disabled: (self.parent.args.readOnly === true),
							hidden: self.isEmbedded,
							width: 130,
							height: 35,
							label: (self.isEditPlanOrder && self.selectedElementIds && self.selectedElementIds.length >1) ? i18n.t("LBL_ACCEPT") : i18n.t("LBL_SEND"),
							on: {
								onItemClick() {

									if (self.isEditPlanOrder && self.selectedElementIds && self.selectedElementIds.length >1) {
										
										const win = $$(self.parent.args.containerId).getTopParentView();
										win.hide();
										
										self.parent.args.callbacks[0]();
																				
									} else {

										let ms = new MessageBox (MessageBox.CONFIRM, (i18n.t("LBL_SEND_SIGNALING_CONFIRM_DESCRIPTION") + (self.selectedElementIds.length > 1 ? " " + self.selectedElementIds.length + " " + i18n.t("LBL_EQUIPMENT_SELECTED") : "")), onAccept, null);
										ms.show();
									
										function onAccept() {
											
											sendCommandElement();
										};										
									}

								}
							}
						}
					]
				};

				function sendCommandElement() {
					
					let commands = [];
					
					if (self.parent.config.restEndpoints && self.parent.config.restEndpoints.command) {

						self.selectedElementIds.forEach(selectedElementId => {

							commands.push(getCommandElementToSend(self.getCommandElement(selectedElementId)));

						});

						if (Array.isArray(commands) && commands.length > 0) {

							util.element.sendCommand(commands, function(res) {
												
								if (res) {
								
									(new MessageBox (MessageBox.INFO, i18n.t("LBL_COMMAND_SEND_SUCCESSFULLY"), null, null)).show();	
								}
							});
						}
					}

					function getCommandElementToSend(commandElement) {

						let commandElementToSend = {
							elementId: commandElement.element.id,
							commandElementTypeId: commandElement.commandElementType.id,
						}

						if (Array.isArray(commandElement.commandElementValues) && commandElement.commandElementValues.length > 0) {

							commandElementToSend.commandElementValues = [];

							commandElement.commandElementValues.forEach(commandElementValue => {

								commandElementToSend.commandElementValues.push({

									commandElementTypeParamId: commandElementValue.commandElementTypeParam.id,
									value: commandElementValue.value
								});
							});
						}

						return commandElementToSend;
					}
				}

				return viewActions;
			}

			return viewComponents;
		}

		return view;
	}

	loadData() {

		let self = this;
		
		if (self.dtParams) {

			self.dtParams.callbacks();
			
			loadCurrentValues();
		}
		
		
		function loadCurrentValues() {
		
			if (Array.isArray(self.selectedElementIds) && self.selectedElementIds.length === 1) {
				
				if (self.commandElementType && Array.isArray(self.commandElementType.commandElementTypeParams) &&
					self.commandElementType.commandElementTypeParams.find(e => e.elementTypeParam != undefined)) {
				
					webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.parent.config.restEndpoints.element, { id: self.selectedElementIds[0] })).then(
				
						function(elementResponse) {
						
							if (elementResponse != null) {
							
								let element = elementResponse.json();
								
								if (element && Array.isArray(element.elementValues)) {
									
									self.commandElementType.commandElementTypeParams.forEach(commandElementTypeParam => {
									
										if (commandElementTypeParam.elementTypeParam) {
											
											let elementValue = element.elementValues.find(e => e.elementTypeParam.id === commandElementTypeParam.elementTypeParam.id);
											
											if (elementValue) {
												
												let item = self.dtParams.getItem(commandElementTypeParam.id);
												
												if (item) {
													
													let newItem = webix.copy(item);
													
													newItem.currentValue = elementValue.value;
													
													self.dtParams.update(newItem);
												}
											}
										}
									});	
								}
							}
						}
					);		
				}
			}
		}
	}

	getCommandElement(elementId) {

		let self = this;
		let commandElement = undefined;

		if (!elementId && Array.isArray(self.selectedElementIds) && self.selectedElementIds.length > 0) {

			elementId = self.selectedElementIds[0];
		}

		if (elementId) {

			commandElement = {
				element: {
					id: elementId
				},
				commandElementType: {
					id: self.commandElementType.id
				},
				id: self.commandElementType.id + ":" + elementId.substring(elementId.lastIndexOf(':') + 1)

			};

			if (Array.isArray(self.commandElementType.commandElementTypeParams) && self.commandElementType.commandElementTypeParams.length > 0 &&
				self.dtParams) {

				let params = self.dtParams.getAllItems();

				if (Array.isArray(params) && params.length > 0) {

					commandElement.commandElementValues = [];

					params.forEach(param => {

						commandElement.commandElementValues.push({

							commandElementTypeParam: {
								id: param.id
							},
							value: param.value
						});
					});
				}
			}
		}

		return commandElement;
	}
}
