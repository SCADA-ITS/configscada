import { util } from "../../../lib/util.js";
import PasLibrary from "../pas/pasLibrary.js";
import DataTable from "../../../component/datatable.js";
import MessageBox from "../../../component/messageBox.js";

export default class PasSignalingOrderEdit {
	
	static get ENTITY_TYPE_ZONE() { return { id: 1, sent: { id: 1, label: "LBL_SENT_ZONE"}, stop: { id: 2, label: "LBL_STOP_ZONE" } } }
	static get ENTITY_TYPE_GROUP() { return { id: 2, sent: { id: 3, label: "LBL_SENT_GROUP"}, stop: { id: 4, label: "LBL_STOP_GROUP" } } }

	constructor(config, element, args) {

		this.config = config;
		this.element = element;
		this.args = args;
		this.zonesDataTableSent = undefined;
		this.zonesDataTableStop = undefined;
		this.groupsDataTableSent = undefined;
		this.groupsDataTableStop = undefined;
		this.groups = undefined;
		this.zones = undefined;
		this.selectedCommand = undefined;
		this.currentOption = PasSignalingOrderEdit.ENTITY_TYPE_ZONE.sent.id;

		this.uid = webix.uid();
	}

	getView() {

		let self = this;
		let view = {};

		view = {
			id: "view-main-content-" + self.uid,
			cols: [
				{
					id: "view-loading-" + self.uid,
					hidden: false,
					rows: [util.ui.getViewDrawLoading()]
				},
				{
					id: "view-content-" + self.uid,
					hidden: true
				}
			]
		};

		return view;
	}


	loadData() {

		let self = this;

		webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.pasLibraryByElement, { id: self.element.id })).then(function(librariesResponse) {

			if (librariesResponse != null) {

				let pasLibrariesResponse = librariesResponse.json();

				if (pasLibrariesResponse) {

					self.pasLibraries = pasLibrariesResponse;
				}

				webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.elementById, { id: self.element.id })).then(function(elResponse) {

					if (elResponse != null) {

						let elementResponse = elResponse.json();

						if (elementResponse) {

							self.element = elementResponse;
						}

						if (self.element.childs) {

							self.groups = self.element.childs.filter(element => element.elementType.id == self.config.group.type);
							self.zones = self.element.childs.filter(element => element.elementType.id == self.config.zone.type);

							webix.ui(drawContent(), $$("view-content-" + self.uid));

							if (self.zonesDataTableSent) {

								self.zonesDataTableSent.callbacks();
							}
							
							if (self.zonesDataTableStop) {
								
								self.zonesDataTableStop.callbacks();
							}
							
							if (self.groupsDataTableSent) {

								self.groupsDataTableSent.callbacks();
							}
							
							if (self.groupsDataTableStop) {
								
								self.groupsDataTableStop.callbacks();
							}

							$$("view-loading-" + self.uid).hide();

							self.zones.forEach(zone => {

								update(zone, PasSignalingOrderEdit.ENTITY_TYPE_ZONE);
							});
							
							self.groups.forEach(group => {

								update(group, PasSignalingOrderEdit.ENTITY_TYPE_GROUP);
							});
						
							$$("radioButtons-" + self.uid).setValue(self.currentOption);

							$$("dt-" + self.zonesDataTableSent.uid).refresh();
							$$("dt-" + self.zonesDataTableStop.uid).refresh();
							$$("dt-" + self.groupsDataTableSent.uid).refresh();
							$$("dt-" + self.groupsDataTableStop.uid).refresh();
						}
					}
				});
			}
		});


		function update(element, entityType) {

			let commandElementValues = self.args.data.commandElement.commandElementValues;
			let arrayIds = [];
			let arrayMessagesId = [];
			let messageId = undefined;
			let messageZoneId = undefined;
			let messageGroupId = undefined;
			let entityId = undefined;
			
			if (self.args.data.commandElement.commandElementType.id === self.config.commands.SEND_MESSAGE_ZONES.commandElementTypeId) {
				
				self.currentOption = PasSignalingOrderEdit.ENTITY_TYPE_ZONE.sent.id;
			}
			else if (self.args.data.commandElement.commandElementType.id === self.config.commands.SWITCH_OFF_ZONES.commandElementTypeId) {
				
				self.currentOption = PasSignalingOrderEdit.ENTITY_TYPE_ZONE.stop.id;
			}
			else if (self.args.data.commandElement.commandElementType.id === self.config.commands.SEND_MESSAGE_GROUPS.commandElementTypeId) {
				
				self.currentOption = PasSignalingOrderEdit.ENTITY_TYPE_GROUP.sent.id;
			}
			else if (self.args.data.commandElement.commandElementType.id === self.config.commands.SWITCH_OFF_GROUPS.commandElementTypeId) {
				
				self.currentOption = PasSignalingOrderEdit.ENTITY_TYPE_GROUP.stop.id;
			}
			
			if (entityType.id === PasSignalingOrderEdit.ENTITY_TYPE_ZONE.id) {
				
				messageZoneId = element.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.config.zone.paramZoneId);
				
				if (messageZoneId && messageZoneId.value != undefined) {

					element.messageZoneId = messageZoneId.value;
				}	
			}
			else if (entityType.id === PasSignalingOrderEdit.ENTITY_TYPE_GROUP.id) {
				
				messageGroupId = element.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.config.zone.paramGroupId);
				
				if (messageGroupId && messageGroupId.value != undefined) {

					element.messageGroupId = messageGroupId.value;
				}	
			}
			
			let cmdSendMessage;
			let cmdSwitchOff;
			
			if (entityType.id === PasSignalingOrderEdit.ENTITY_TYPE_ZONE.id) {
				
				cmdSendMessage = self.config.commands.SEND_MESSAGE_ZONES;
				cmdSwitchOff = self.config.commands.SWITCH_OFF_ZONES;
			}
			else if (entityType.id === PasSignalingOrderEdit.ENTITY_TYPE_GROUP.id) {
				
				cmdSendMessage = self.config.commands.SEND_MESSAGE_GROUPS;
				cmdSwitchOff = self.config.commands.SWITCH_OFF_GROUPS;
			}
			
			if (commandElementValues) {
				
				for (let elementValue of commandElementValues) {

					if (elementValue.value) {

						if (elementValue.commandElementTypeParam.id === cmdSendMessage.commandElementValues[0].commandElementTypeParamId) {

							arrayIds = JSON.parse(elementValue.value);
						}
						else if (elementValue.commandElementTypeParam.id === cmdSwitchOff.commandElementValues[0].commandElementTypeParamId) {

							arrayIds = JSON.parse(elementValue.value);
						}
						else if (elementValue.commandElementTypeParam.id === cmdSendMessage.commandElementValues[1].commandElementTypeParamId) {

							arrayMessagesId = JSON.parse(elementValue.value);
						}
					}
				}
			}

			entityId = arrayIds.find(id => id === element.id);

			if (entityId) {

				let index = arrayIds.indexOf(entityId);

				messageId = (arrayMessagesId[index]) ? arrayMessagesId[index] : false;
			}

			if (messageId) {

				element.messageId = messageId;

				if (self.pasLibraries && self.pasLibraries.pasLibrary && Array.isArray(self.pasLibraries.pasLibrary.pasLibraryNodes) && self.pasLibraries.pasLibrary.pasLibraryNodes.length > 0) {

					let passLibraryNode = self.pasLibraries.pasLibrary.pasLibraryNodes.find(pasLibraryNode => pasLibraryNode.pasMessage && pasLibraryNode.pasMessage.id == element.messageId);

					if (passLibraryNode) {

						element.messageAlias = passLibraryNode.pasMessage.alias;
						element.messageAction = i18n.t("LBL_PAS_SEND_MESSAGE");
						element.messageSent = true;
						element.onSent = "1";
					}
				}

			} else if (messageId === false) {

				element.messageSent = false;
				element.messageActionStop = i18n.t("LBL_PAS_STOP_MESSAGE");
				element.onStop = "1";

				self.stopOption = entityType.stop.id;
			}
		}

		function drawContent() {

			let view = {};

			view = {
				id: "view-content-" + self.uid,
				rows: [
					getViewHeader(),
					getViewBody()
				]
			};

			function getViewHeader() {

				let viewHeader = {};

				viewHeader = {
					rows: [
						{
							cols: [
								{
									view: "label",
									label: `<div class="title"><i style="color:#33425e; font-size: 2em" class="mdi mdi-view-headline"></i></div>`,
									align: "center",
									height: 50,
									width: 56
								},
								{
									id: "elements-selected-label" + self.uid,
									view: "label",
									css: "view-header-details-text",
									label: self.element.alias,
									align: "left",
									height: 50
								},
								{}
							]
						},
						{
							css: { "padding-left": "15px" },
							cols: [
								{
									view: "label",
									label: `<div class="title"><i style="color:#33425e; font-size: 2em" class="mdi mdi-cog"></i></div>`,
									align: "center",
									height: 50,
									width: 56
								},
								{
									view: "label",
									css: "view-header-details-text",
									label: `<span class="view-header-details-text">${i18n.t("LBL_SIGNALING")}</span>`,
									align: "left",
									height: 50
								}
							]
						}
					]
				};

				return viewHeader;
			}


			function getViewBody() {

				createTable(PasSignalingOrderEdit.ENTITY_TYPE_ZONE);
				createTable(PasSignalingOrderEdit.ENTITY_TYPE_GROUP);

				let viewBody = {};

				viewBody = {
					view: "form",
					css: {"box-sizing": "border-box"},
					elements: [
						{
							cols: [
								{ width: 6 },
								{
									rows: [
										{
											view: "radio",
											id: "radioButtons-" + self.uid,
											value: 1,
											multiview: true,
											css: { "padding-left": "15px" },
											options: [
												{ id: PasSignalingOrderEdit.ENTITY_TYPE_ZONE.sent.id, value: i18n.t(PasSignalingOrderEdit.ENTITY_TYPE_ZONE.sent.label) },
												{ id: PasSignalingOrderEdit.ENTITY_TYPE_ZONE.stop.id, value: i18n.t(PasSignalingOrderEdit.ENTITY_TYPE_ZONE.stop.label) },
												{ id: PasSignalingOrderEdit.ENTITY_TYPE_GROUP.sent.id, value: i18n.t(PasSignalingOrderEdit.ENTITY_TYPE_GROUP.sent.label) },
												{ id: PasSignalingOrderEdit.ENTITY_TYPE_GROUP.stop.id, value: i18n.t(PasSignalingOrderEdit.ENTITY_TYPE_GROUP.stop.label) }
											],
											on: {
												onChange: function(newv, oldv) {
													$$(newv).show();
												}
											}
										},
										{
											view: "multiview",
											keepViews: true,
											animate: false,
											scroll: "auto",
											cells: [
												{ id: PasSignalingOrderEdit.ENTITY_TYPE_ZONE.sent.id, rows: self.zonesDataTableSent.getView() },
												{ id: PasSignalingOrderEdit.ENTITY_TYPE_ZONE.stop.id, rows: self.zonesDataTableStop.getView() },
												{ id: PasSignalingOrderEdit.ENTITY_TYPE_GROUP.sent.id, rows: self.groupsDataTableSent.getView() },
												{ id: PasSignalingOrderEdit.ENTITY_TYPE_GROUP.stop.id, rows: self.groupsDataTableStop.getView() }
											]
										}
									]
								},
								{ width: 6 }
							]
						}
					]
				};

				function createTable(entityType) {

					let data = entityType.id === PasSignalingOrderEdit.ENTITY_TYPE_ZONE.id ? self.zones : self.groups;

					const defaultGridOptionValues = [
						{ gridOption: { id: 5 }, value: undefined },
						{ gridOption: { id: 6 }, value: undefined },
						{ gridOption: { id: 7 }, value: "false" },
						{ gridOption: { id: 8 }, value: "false" },
						{ gridOption: { id: 9 }, value: "true" },
						{ gridOption: { id: 10 }, value: "true" },
						{ gridOption: { id: 16 }, value: "img/grid/default.png" },
						{ gridOption: { id: 21 }, value: "custom" },
						{ gridOption: { id: 23 }, value: "true" }
					];

					let metadata = {
						filter: true,
						label: entityType.id === PasSignalingOrderEdit.ENTITY_TYPE_ZONE.id ? "LBL_PAS_ZONE" : "LBL_PAS_GROUP",
						visible: true,
						gridOptionValues: defaultGridOptionValues,
						gridFields: [
							{
								id: 1,
								extended: false,
								filtered: false,
								isEpochMilli: false,
								isPk: true,
								parameterized: false,
								position: 1,
								required: true,
								sourceField: "id",
								visible: false,
								widthPx: 1,
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
								label: entityType.id === PasSignalingOrderEdit.ENTITY_TYPE_ZONE.id ? "LBL_PAS_ZONE_ALIAS" : "LBL_PAS_GROUP_ALIAS",
								labelTooltip: entityType.id === PasSignalingOrderEdit.ENTITY_TYPE_ZONE.id ? "LBL_PAS_ZONE_ALIAS" : "LBL_PAS_GROUP_ALIAS",
								parameterized: false,
								position: 2,
								required: false,
								sourceField: "alias",
								visible: true,
								widthPx: 200,
								gridFieldDataType: {
									id: 1,
									name: "string"
								},
								gridFieldIcons: []
							},
							{
								id: 3,
								extended: false,
								filtered: false,
								isEpochMilli: false,
								isPk: false,
								parameterized: false,
								position: 4,
								required: false,
								sourceField: "messageId",
								visible: false,
								widthPx: 1,
								gridFieldDataType: {
									id: 1,
									name: "string"
								},
								gridFieldIcons: []
							},
							{
								id: 4,
								extended: false,
								filtered: false,
								isEpochMilli: false,
								isPk: false,
								label: entityType.id === PasSignalingOrderEdit.ENTITY_TYPE_ZONE.id ? "LBL_PAS_ZONE_ID" : "LBL_PAS_GROUP_ID",
								labelTooltip: entityType.id === PasSignalingOrderEdit.ENTITY_TYPE_ZONE.id ? "LBL_PAS_ZONE_ID" : "LBL_PAS_GROUP_ID",
								parameterized: false,
								position: 5,
								required: false,
								sourceField: entityType.id === PasSignalingOrderEdit.ENTITY_TYPE_ZONE.id ? "messageZoneId" : "messageGroupId",
								visible: false,
								widthPx: 1,
								gridFieldDataType: {
									id: 1,
									name: "string"
								},
								gridFieldIcons: []
							},
							{
								id: 5,
								extended: false,
								filtered: false,
								isEpochMilli: false,
								isPk: false,
								parameterized: false,
								position: 6,
								required: false,
								sourceField: "messageSent",
								visible: false,
								widthPx: 1,
								gridFieldDataType: {
									id: 1,
									name: "string"
								},
								gridFieldIcons: []
							},
							{
								id: 6,
								extended: false,
								filtered: false,
								isEpochMilli: false,
								isPk: false,
								parameterized: false,
								position: 9,
								required: false,
								sourceField: "message",
								visible: false,
								widthPx: 1,
								gridFieldDataType: {
									id: 1,
									name: "string"
								},
								gridFieldIcons: []
							}
						],
						gridCommands: [
							{
								id: 1,
								default_: false,
								icon: "mdi mdi-broom",
								label: "LBL_PAS_CLEAN",
								multiselect: true,
								position: 2,
								showText: true,
								itemRequired: false
							}
						]
					};

					let metadataSent = webix.copy(metadata);
					let metadataStop = webix.copy(metadata);

					metadataSent.gridFields.push(
						{
							id: 7,
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							parameterized: false,
							position: 3,
							required: false,
							sourceField: "onSent",
							visible: true,
							widthPx: 60,
							gridFieldDataType: {
								id: 1,
								name: "string"
							},
							gridFieldIcons: [
								{
									icon: "./img/grid/play.png",
									showValue: false,
									value: "1",
									labelValue: "LBL_PAS_SENT"
								}
							]
						},
						{
							id: 8,
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: "LBL_PAS_ACTION",
							labelTooltip: "LBL_PAS_ACTION",
							parameterized: false,
							position: 7,
							required: false,
							sourceField: "messageAction",
							visible: true,
							widthPx: 250,
							gridFieldDataType: {
								id: 1,
								name: "string"
							},
							gridFieldIcons: []
						},
						{
							id: 9,
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: "LBL_PAS_MESSAGE",
							labelTooltip: "LBL_PAS_MESSAGE",
							parameterized: false,
							position: 8,
							required: false,
							sourceField: "messageAlias",
							visible: true,
							widthPx: 250,
							gridFieldDataType: {
								id: 1,
								name: "string"
							},
							gridFieldIcons: []
						});

					metadataSent.gridCommands.push(
						{
							id: 2,
							default_: false,
							icon: "mdi mdi-play-circle-outline",
							label: "LBL_PAS_ADD_MESSAGE",
							multiselect: true,
							position: 1,
							showText: true,
							itemRequired: true
						});

					metadataStop.gridFields.push(
						{
							id: 10,
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							parameterized: false,
							position: 3,
							required: false,
							sourceField: "onStop",
							visible: true,
							widthPx: 60,
							gridFieldDataType: {
								id: 1,
								name: "string"
							},
							gridFieldIcons: [
								{
									icon: "./img/grid/stop.png",
									showValue: false,
									value: "1",
									labelValue: "LBL_PAS_STOP"
								}
							]
						},
						{
							id: 11,
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: "LBL_PAS_ACTION",
							labelTooltip: "LBL_PAS_ACTION",
							parameterized: false,
							position: 7,
							required: false,
							sourceField: "messageActionStop",
							visible: true,
							widthPx: 250,
							gridFieldDataType: {
								id: 1,
								name: "string"
							},
							gridFieldIcons: []
						});

					metadataStop.gridCommands.push(
						{
							id: 3,
							default_: false,
							icon: "mdi mdi-stop-circle-outline",
							label: "LBL_PAS_STOP",
							multiselect: true,
							position: 1,
							showText: true,
							itemRequired: true
						});

					let events = {};
					events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];

					events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
						commandId: 1,
						function: () => {

							let radioButtonsValue = $$("radioButtons-" + self.uid).getValue();
							
							let sent;
							let dataTable;
							
							if (entityType.id === PasSignalingOrderEdit.ENTITY_TYPE_ZONE.id) {
								
								sent = (radioButtonsValue == PasSignalingOrderEdit.ENTITY_TYPE_ZONE.sent.id);
								dataTable = (sent) ? self.zonesDataTableSent : self.zonesDataTableStop;
							} 
							else if (entityType.id === PasSignalingOrderEdit.ENTITY_TYPE_GROUP.id) {
								
								sent = (radioButtonsValue == PasSignalingOrderEdit.ENTITY_TYPE_GROUP.sent.id);
								dataTable = (sent) ? self.groupsDataTableSent : self.groupsDataTableStop;
							}
							
							let selectedItems = dataTable.getSelectedItems();
							let arrayItems = (Array.isArray(selectedItems) && selectedItems.length > 0) ? selectedItems : dataTable.getAllItems();
							let itemsToUpdate = [];

							if (sent) {

								let itemsNotClean = arrayItems.find(item => (item.onSent));

								if (itemsNotClean) {
									
									if (entityType.id === PasSignalingOrderEdit.ENTITY_TYPE_ZONE.id) {
									
										self.selectedCommand = self.config.commands.SEND_MESSAGE_ZONES.commandElementTypeId;	
									}
									else if (entityType.id === PasSignalingOrderEdit.ENTITY_TYPE_GROUP.id) {
										
										self.selectedCommand = self.config.commands.SEND_MESSAGE_GROUPS.commandElementTypeId;
									}

									for (let item of arrayItems) {

										let it = {
											id: item.id,
											messageId: "-",
											messageAction: "-",
											messageAlias: "-",
											messageSent: " ",
											message: " ",
											onSent: " "
										};

										itemsToUpdate.push(it);
									}

									dataTable.update(itemsToUpdate);
								}

							} else {

								let itemsNotClean = arrayItems.find(item => (item.onStop));

								if (itemsNotClean) {

									if (entityType.id === PasSignalingOrderEdit.ENTITY_TYPE_ZONE.id) {
										
										self.selectedCommand = self.config.commands.SWITCH_OFF_ZONES.commandElementTypeId;	
									}
									else if (entityType.id === PasSignalingOrderEdit.ENTITY_TYPE_GROUP.id) {
										
										self.selectedCommand = self.config.commands.SWITCH_OFF_GROUPS.commandElementTypeId;
									}
									

									for (let item of arrayItems) {

										let it = {
											id: item.id,
											messageId: "-",
											messageSent: " ",
											onStop: " ",
											messageActionStop: "-"
										};

										itemsToUpdate.push(it);
									}

									dataTable.update(itemsToUpdate);
								}
							}
						}
					});

					events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
						commandId: 2,
						function: () => {

							let selectedItems;
							
							if (entityType.id === PasSignalingOrderEdit.ENTITY_TYPE_ZONE.id) {
							
								self.selectedCommand = self.config.commands.SEND_MESSAGE_ZONES.commandElementTypeId;
								selectedItems = self.zonesDataTableSent.getSelectedItems();
									
							}
							else if (entityType.id === PasSignalingOrderEdit.ENTITY_TYPE_GROUP.id) {
								
								self.selectedCommand = self.config.commands.SEND_MESSAGE_GROUPS.commandElementTypeId;
								selectedItems = self.groupsDataTableSent.getSelectedItems();	
							}

							if (self.pasLibraries && self.pasLibraries.pasLibrary) {

								let pasLibrary = new PasLibrary(self.pasLibraries.pasLibrary, onAccept, true);
								
								util.ui.loadWindow(i18n.t("LBL_PAS_LIBRARY_PLAYER_HEADER"), pasLibrary.getView(), 700, 600, true);

							} else {

								let ms = new MessageBox(MessageBox.MODE_ERROR, i18n.t("LBL_NOT_FOUND_PAS_LIBRARY_TEXT"), null, null);
								ms.show();
							}

							function onAccept(message) {

								let itemsToUpdate = [];

								for (let item of selectedItems) {

									let it = {
										id: item.id,
										messageId: message.pasMessage.id,
										messageAction: i18n.t("LBL_PAS_SEND_MESSAGE"),
										messageAlias: message.pasMessage.alias,
										messageSent: true,
										message: message.pasMessage.message,
										messageZoneId: item.messageZoneId,
										onSent: "1"
									};

									itemsToUpdate.push(it);
								}
								
								if (entityType.id === PasSignalingOrderEdit.ENTITY_TYPE_ZONE.id) {
								
									self.zonesDataTableSent.update(itemsToUpdate);	
								}
								else if (entityType.id === PasSignalingOrderEdit.ENTITY_TYPE_GROUP.id){
									
									self.groupsDataTableSent.update(itemsToUpdate);
								}
							}
						}
					});

					events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
						commandId: 3,
						function: () => {

							let selectedItems;
							
							if (entityType.id === PasSignalingOrderEdit.ENTITY_TYPE_ZONE.id) {
								
								selectedItems = self.zonesDataTableStop.getSelectedItems();
								self.selectedCommand = self.config.commands.SWITCH_OFF_ZONES.commandElementTypeId;	
							}
							else if (entityType.id === PasSignalingOrderEdit.ENTITY_TYPE_GROUP.id){
								
								selectedItems = self.groupsDataTableStop.getSelectedItems();
								self.selectedCommand = self.config.commands.SWITCH_OFF_GROUPS.commandElementTypeId;
							}

							if (Array.isArray(selectedItems) && selectedItems.length > 0) {

								let itemsToUpdate = [];

								for (let item of selectedItems) {

									let it = {
										id: item.id,
										messageId: "0",
										messageSent: false,
										onStop: "1",
										messageActionStop: i18n.t("LBL_PAS_STOP_MESSAGE"),
									};

									itemsToUpdate.push(it);
								}
								
								if (entityType.id === PasSignalingOrderEdit.ENTITY_TYPE_ZONE.id) {
									
									self.zonesDataTableStop.update(itemsToUpdate);
								}
								else if (entityType.id === PasSignalingOrderEdit.ENTITY_TYPE_GROUP.id){
									
									self.groupsDataTableStop.update(itemsToUpdate);
								}
							}
						}
					});
					
					if (entityType.id === PasSignalingOrderEdit.ENTITY_TYPE_ZONE.id) {
									
						self.zonesDataTableSent = new DataTable(null, null, metadataSent, data, events);
						self.zonesDataTableStop = new DataTable(null, null, metadataStop, data, events);
					}
					else if (entityType.id === PasSignalingOrderEdit.ENTITY_TYPE_GROUP.id){
						
						self.groupsDataTableSent = new DataTable(null, null, metadataSent, data, events);
						self.groupsDataTableStop = new DataTable(null, null, metadataStop, data, events);
					}
				}

				return viewBody;
			}

			return view;
		}
	}


	getCommandElement() {

		let self = this;
		let commandElement = undefined;
		let radioButtonsValue = $$("radioButtons-" + self.uid).getValue();
		let table;
		let toSent = [];
		let toStop = [];

		if (self.element && radioButtonsValue) {
			
			if (radioButtonsValue == PasSignalingOrderEdit.ENTITY_TYPE_ZONE.sent.id) {
				
				table = self.zonesDataTableSent;
			}
			else if (radioButtonsValue == PasSignalingOrderEdit.ENTITY_TYPE_ZONE.stop.id) {
				
				table = self.zonesDataTableStop;
			}
			else if (radioButtonsValue == PasSignalingOrderEdit.ENTITY_TYPE_GROUP.sent.id) {
				
				table = self.groupsDataTableSent;
			}
			else if (radioButtonsValue == PasSignalingOrderEdit.ENTITY_TYPE_GROUP.stop.id) {
				
				table = self.groupsDataTableStop;
			}

			if (table && Array.isArray(self.element.commandElements) && self.element.commandElements.length > 0) {

				let rows = table.getAllItems();

				if (Array.isArray(rows)) {

					rows.forEach(row => {

						// PLAY
						if (row["messageSent"] === true) {

							let ms = {
								id: row.id,
								messageId: row.messageId,
								messageAction: row.messageAction,
								messageAlias: row.messageAlias,
								messageSent: row.messageSent,
								messageZoneId: row.id,
							};

							toSent.push(ms);
						}

						// STOP
						else if (row["messageSent"] === false) {

							let ms = {
								id: row.id,
								messageId: row.messageId,
								messageAction: row.messageAction,
								messageAlias: row.messageAlias,
								messageSent: row.messageSent,
								messageZoneId: row.id,
							};

							toStop.push(ms);
						}
					});
				}

				let selectedCommandElement = self.element.commandElements.find(e => e.id === self.selectedCommand);

				if (selectedCommandElement) {

					if (Array.isArray(selectedCommandElement.commandElementTypeParams) && selectedCommandElement.commandElementTypeParams.length > 0) {

						let elementId = self.element.id;

						// SEND MESSAGE
						if (selectedCommandElement.id === self.config.commands.SEND_MESSAGE_ZONES.commandElementTypeId ||
							selectedCommandElement.id === self.config.commands.SEND_MESSAGE_GROUPS.commandElementTypeId) {

							let zoneValue = [];
							let messageValue = [];

							for (let element of toSent) {

								zoneValue.push(element.messageZoneId);
								messageValue.push(element.messageId.toString());
							}

							commandElement = {
								element: {
									id: elementId
								},
								commandElementType: {
									id: selectedCommandElement.id
								},
								id: selectedCommandElement.id + ":" + elementId.substring(elementId.lastIndexOf(':') + 1)
							};

							commandElement.commandElementValues = [];

							commandElement.commandElementValues.push(
								{
									commandElementTypeParam: { id: selectedCommandElement.commandElementTypeParams[1].id },
									value: zoneValue
								},
								{
									commandElementTypeParam: { id: selectedCommandElement.commandElementTypeParams[0].id },
									value: messageValue
								}
							);
						}

						//STOP MESSAGE
						if (selectedCommandElement.id === self.config.commands.SWITCH_OFF_ZONES.commandElementTypeId ||
							selectedCommandElement.id === self.config.commands.SWITCH_OFF_GROUPS.commandElementTypeId) {

							let zoneValue = [];

							for (let element of toStop) {

								zoneValue.push(element.messageZoneId);
							}

							commandElement = {
								element: {
									id: elementId
								},
								commandElementType: {
									id: selectedCommandElement.id
								},
								id: selectedCommandElement.id + ":" + elementId.substring(elementId.lastIndexOf(':') + 1)
							};

							commandElement.commandElementValues = [];

							commandElement.commandElementValues.push(
								{
									commandElementTypeParam: { id: selectedCommandElement.commandElementTypeParams[0].id },
									value: zoneValue
								}
							);
						}
					}
				}
			}
		}

		return commandElement;
	}

}