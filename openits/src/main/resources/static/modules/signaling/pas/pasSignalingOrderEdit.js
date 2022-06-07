import { util } from "../../../lib/util.js";
import PasLibrary from "../pas/pasLibrary.js";
import DataTable from "../../../component/datatable.js";
import MessageBox from "../../../component/messageBox.js";

export default class PasSignalingOrderEdit {

	constructor(config, element, args) {

		this.config = config;
		this.element = element;
		this.args = args;
		this.zonesDataTableSent = undefined;
		this.zonesDataTableStop = undefined;
		this.zones = undefined;
		this.selectedCommand = undefined;
		this.stopOption = false;

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

							self.zones = self.element.childs.filter(element => element.elementType.id == self.config.zone.type);

							webix.ui(drawContent(), $$("view-content-" + self.uid));

							if (self.zonesDataTableSent && self.zonesDataTableStop) {

								self.zonesDataTableSent.callbacks();
								self.zonesDataTableStop.callbacks();
							}

							$$("view-loading-" + self.uid).hide();

							self.zones.forEach(element => {

								updateZone(element);
							});

							if (self.stopOption) {

								$$("radioButtons-" + self.uid).setValue(2);
							}

							$$("dt-" + self.zonesDataTableSent.uid).refresh();
							$$("dt-" + self.zonesDataTableStop.uid).refresh();
						}
					}
				});
			}
		});


		function updateZone(element) {

			let commandElementValues = self.args.data.commandElement.commandElementValues;
			let arrayZonesId = [];
			let arrayMessagesId = [];
			let messageId = undefined;
			let messageZoneId = undefined;
			let zoneId = undefined;

			messageZoneId = element.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.config.zone.paramZoneId);

			if (messageZoneId && messageZoneId.value != undefined) {

				element.messageZoneId = messageZoneId.value;
			}

			if (commandElementValues) {

				for (let elementValue of commandElementValues) {

					if (elementValue.value) {

						if (elementValue.commandElementTypeParam.id === self.config.commands.SEND_MESSAGE.commandElementValues[0].commandElementTypeParamId) {

							arrayZonesId = JSON.parse(elementValue.value);
						}

						if (elementValue.commandElementTypeParam.id === self.config.commands.SWITCH_OFF.commandElementValues[0].commandElementTypeParamId) {

							arrayZonesId = JSON.parse(elementValue.value);
						}

						if (elementValue.commandElementTypeParam.id === self.config.commands.SEND_MESSAGE.commandElementValues[1].commandElementTypeParamId) {

							arrayMessagesId = JSON.parse(elementValue.value);
						}
					}
				}
			}

			zoneId = arrayZonesId.find(zone => zone === element.id);

			if (zoneId) {

				let index = arrayZonesId.indexOf(zoneId);

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

				self.stopOption = true;
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
									css: "alarm-view-header-details-text",
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
									css: "alarm-view-header-details-text",
									label: `<span class="alarm-view-header-details-text">${i18n.t("LBL_SIGNALING")}</span>`,
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

				getZonesTable();

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
												{ id: 1, value: i18n.t("LBL_PAS_SENT") },
												{ id: 2, value: i18n.t("LBL_PAS_STOP") }
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
												{ id: 1, rows: self.zonesDataTableSent.getView() },
												{ id: 2, rows: self.zonesDataTableStop.getView() }
											]
										}
									]
								},
								{ width: 6 }
							]
						}
					]
				};

				function getZonesTable() {

					let data = self.zones;

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
						label: "LBL_PAS_ZONE",
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
								label: "LBL_PAS_ZONE_ALIAS",
								labelTooltip: "LBL_PAS_ZONE_ALIAS",
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
								label: "LBL_PAS_ZONE_ID",
								labelTooltip: "LBL_PAS_ZONE_ID",
								parameterized: false,
								position: 5,
								required: false,
								sourceField: "messageZoneId",
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
							label: "LBL_PAS_MESSAGE",
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
							let sent = (radioButtonsValue == 1);
							let zoneDataTable = (sent) ? self.zonesDataTableSent : self.zonesDataTableStop;
							let selectedItems = zoneDataTable.getSelectedItems();
							let arrayItems = (Array.isArray(selectedItems) && selectedItems.length > 0) ? selectedItems : zoneDataTable.getAllItems();
							let itemsToUpdate = [];

							if (sent) {

								let itemsNotClean = arrayItems.find(item => (item.onSent));

								if (itemsNotClean) {

									self.selectedCommand = self.config.commands.SEND_MESSAGE.commandElementTypeId;

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

									zoneDataTable.update(itemsToUpdate);
								}

							} else {

								let itemsNotClean = arrayItems.find(item => (item.onStop));

								if (itemsNotClean) {

									self.selectedCommand = self.config.commands.SWITCH_OFF.commandElementTypeId;

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

									zoneDataTable.update(itemsToUpdate);
								}
							}
						}
					});

					events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
						commandId: 2,
						function: () => {

							let selectedItems = self.zonesDataTableSent.getSelectedItems();
							self.selectedCommand = self.config.commands.SEND_MESSAGE.commandElementTypeId;

							if (self.pasLibraries && self.pasLibraries.pasLibrary) {

								let pasLibrary = new PasLibrary(self.pasLibraries.pasLibrary, onAccept, selectedItems.length, true);
								pasLibrary.show();

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

								self.zonesDataTableSent.update(itemsToUpdate);
							}
						}
					});

					events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
						commandId: 3,
						function: () => {

							let selectedItems = self.zonesDataTableStop.getSelectedItems();
							self.selectedCommand = self.config.commands.SWITCH_OFF.commandElementTypeId;

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

								self.zonesDataTableStop.update(itemsToUpdate);

							}
						}
					});

					self.zonesDataTableSent = new DataTable(null, null, metadataSent, data, events);
					self.zonesDataTableStop = new DataTable(null, null, metadataStop, data, events);

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
		let table = (radioButtonsValue == 1) ? self.zonesDataTableSent : self.zonesDataTableStop;
		let toSent = [];
		let toStop = [];

		if (self.element) {

			if (Array.isArray(self.element.commandElements) && self.element.commandElements.length > 0) {

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
						if (selectedCommandElement.id === self.config.commands.SEND_MESSAGE.commandElementTypeId) {

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
						if (selectedCommandElement.id === self.config.commands.SWITCH_OFF.commandElementTypeId) {

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