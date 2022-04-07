import { util } from "../../../lib/util.js";
import { adapter } from "../../../lib/adapter.js";
import PasLibrary from "../pas/pasLibrary.js";
import DataTable from "../../../component/datatable.js";
import SoundPlayerDialogBox from "../../../component/soundPlayerDialogBox.js";
import MessageBox from "../../../component/messageBox.js";

export default class PasSignalingContent {

	constructor(config, element, selectedPks) {

		this.config = config;
		this.element = element;
		this.uid = webix.uid();
		this.selectedPks = selectedPks;

		this.pasLibraries = undefined;

		this.zones = undefined;
		this.zonesDataTable = undefined;

		this.idStompSubscriptorPas = undefined;
		this.idStompSubscriptorZones = undefined;
	}

	getView() {

		let self = this;
		let view;

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
			],
			on: {
				onDestruct: () => {

					disConnectWS();
				}
			}
		};


		function disConnectWS() {

			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

			if (stompSubscriptorService) {

				if (self.idStompSubscriptorPas) {

					stompSubscriptorService.unsubscribe(self.idStompSubscriptorPas);
				}

				if (self.idStompSubscriptorZones) {

					stompSubscriptorService.unsubscribe(self.idStompSubscriptorZones);
				}
			}
		}

		return view;
	}


	loadData() {

		let self = this;

		webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.pasLibraryByElement, { id: self.element.id })).then(function(pasLibrariesResponse) {

			if (pasLibrariesResponse != null) {

				self.pasLibraries = pasLibrariesResponse.json();
			}

			webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.elementById, { id: self.element.id })).then(function(elementResponse) {

				if (elementResponse != null) {

					self.element = elementResponse.json();
				}

				if (self.element.childs) {

					self.zones = self.element.childs.filter(element => element.elementType.id == self.config.zone.type);

					webix.ui(drawContent(), $$("view-content-" + self.uid));

					if (self.zonesDataTable) {

						self.zonesDataTable.callbacks();
					}

					$$("view-loading-" + self.uid).hide();

					self.zones.forEach(element => {

						updateZone(element);
					});

					$$("dt-" + self.zonesDataTable.uid).refresh();
				}

				connectWS();
			});
		});

		function updateZone(element) {

			element.messageAlias = undefined;
			element.messageAudio = undefined;
			element.messageDesc = undefined;

			let messageZoneId = element.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.config.zone.paramZoneId);
			let messageTag = element.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.config.zone.paramMessageTag);
			let messageId = element.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.config.zone.paramMessageId);
			let messageDesc = element.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.config.zone.paramMessageDesc);

			if (messageZoneId && messageZoneId.value != undefined) {

				element.messageZoneId = messageZoneId.value;
			}

			if (messageTag && messageTag.value != undefined) {

				if (messageTag.value && messageTag.value != "" && messageTag.value != " ") {

					element.onAir = "1";
				} else {

					element.onAir = "0";
				}

				element.messageTag = messageTag.value;

			} else {

				element.onAir = "0";
			}

			if (messageId && messageId.value != undefined) {

				element.messageId = messageId.value;
				element.messageId = element.messageId.replace('[', '');
				element.messageId = element.messageId.replace(']', '');

				if (self.pasLibraries && self.pasLibraries.pasLibrary && Array.isArray(self.pasLibraries.pasLibrary.pasLibraryNodes) && self.pasLibraries.pasLibrary.pasLibraryNodes.length > 0) {

					let passLibraryNode = self.pasLibraries.pasLibrary.pasLibraryNodes.find(pasLibraryNode => pasLibraryNode.pasMessage && pasLibraryNode.pasMessage.message == element.messageId);
					if (passLibraryNode) {

						element.messageAlias = passLibraryNode.pasMessage.alias;
						element.messageAudio = passLibraryNode.pasMessage.audio;
					}
				}
			}

			if (messageDesc && messageDesc.value != undefined) {

				element.messageDesc = messageDesc.value;
			}

		}


		function connectWS() {

			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

			if (stompSubscriptorService) {

				if (self.element) {

					let topic = self.config.topics.pas + "." + self.element.id;
					self.idStompSubscriptorPas = stompSubscriptorService.subscribe(appConfig.stompUrl, topic, onMessagePas);

				}

				if (self.zones && Array.isArray(self.zones) && self.zones.length > 0) {

					let fields = [];
					fields.push({
						target: "id",
						values: []
					});
					self.zones.forEach(element => fields[0].values.push(element.id));

					self.idStompSubscriptorZones = stompSubscriptorService.subscribe(appConfig.stompUrl, self.config.topics.zones, onMessageZone, fields);
				}
			}
		}


		function drawContent() {

			let viewContent = {};

			viewContent = {
				id: "view-content-" + self.uid,
				rows: [
					getViewHeader(),
					{ height: 32 },
					getViewBody()
				]
			};

			return viewContent;
		}
		
		function getViewHeader() {

			let currentPasState = getCurrentPasState();
			let viewHeader = {};

			viewHeader = {
				id: "view-content-header-" + self.uid,
				rows: [
					{
						id: "view-content-header-bar-color-" + self.uid,
						height: 15,
						css: { "background": util.element.getColor(currentPasState) }
					},
					{ height: 4 },
					{
						cols: [
							{
								borderless: true,
								id: "view-content-header-info" + self.uid,
								height: 80,
								template: function() {

									return `<image class="ims-incident-report-icon" src="./img/grid/element_type_05.png" />
									<div class="ims-incident-report-title">
										${self.element.alias}
									</div>
									<div class="ims-incident-report-title">
										<span class="ims-incident-badge" style="background: ${util.element.getColor(currentPasState)}; color: white;">${i18n.t(currentPasState ? currentPasState.labelAlias : "LBL_UNKNOWN")}</span>
									</div>`;
								}
							}
						]
					}
				]
			};

			function getCurrentPasState() {

				return self.element.elementTypeState && self.element.elementTypeState.id ? window.em.getByField(EntityType.ELEMENT_TYPE_STATE, "id", self.element.elementTypeState.id) : undefined;
			}

			return viewHeader;
		}

		function getViewBody() {

			let viewBody = {};

			viewBody = {
				cols: [
					{ width: 6 },
					{
						css: { "box-sizing": "border-box" },
						rows: getZonesTable()
					},
					{ width: 6 }
				]
			};

			function getZonesTable() {

				let data = self.zones;

				const defaultGridOptionValues = [
					{ gridOption: { id: 2 }, value: "true" },
					{ gridOption: { id: 3 }, value: "2" },
					{ gridOption: { id: 4 }, value: "asc" },
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
							// label: "LBL_PAS_ZONE_ON_AIR",
							// labelTooltip: "LBL_PAS_ZONE_ON_AIR",
							parameterized: false,
							position: 3,
							required: false,
							sourceField: "onAir",
							visible: true,
							widthPx: 60,
							gridFieldDataType: {
								id: 1,
								name: "string"
							},
							gridFieldIcons: [
								{
									icon: "./img/grid/mute.png",
									showValue: false,
									value: "0",
									labelValue: "LBL_PAS_NOT_ON_AIR"
								},
								{
									icon: "./img/grid/sound.png",
									showValue: false,
									value: "1",
									labelValue: "LBL_PAS_ON_AIR"
								},
							]
						},
						{
							id: 4,
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
							id: 5,
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: "LBL_PAS_ZONE_MESSAGE_TAG",
							labelTooltip: "LBL_PAS_ZONE_MESSAGE_TAG",
							parameterized: false,
							position: 5,
							required: false,
							sourceField: "messageTag",
							visible: true,
							widthPx: 100,
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
							label: "LBL_PAS_ZONE_MESSAGE_ALIAS",
							labelTooltip: "LBL_PAS_ZONE_MESSAGE_ALIAS",
							parameterized: false,
							position: 6,
							required: false,
							sourceField: "messageAlias",
							visible: true,
							widthPx: 150,
							gridFieldDataType: {
								id: 1,
								name: "string"
							},
							gridFieldIcons: []
						},
						{
							id: 7,
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: "LBL_PAS_ZONE_MESSAGE_DESCRIPTION",
							labelTooltip: "LBL_PAS_ZONE_MESSAGE_DESCRIPTION",
							parameterized: false,
							position: 7,
							required: false,
							sourceField: "messageDesc",
							visible: true,
							widthPx: 150,
							gridFieldDataType: {
								id: 1,
								name: "string"
							},
							gridFieldIcons: []
						},
						{
							id: 8,
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							parameterized: false,
							position: 8,
							required: false,
							sourceField: "messageZoneId",
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
							icon: "mdi mdi-play",
							label: "LBL_PAS_SEND",
							multiselect: true,
							position: 3,
							showText: true,
							itemRequired: true
						},
						{
							id: 2,
							default_: false,
							icon: "mdi mdi-stop",
							label: "LBL_PAS_STOP",
							multiselect: true,
							position: 2,
							showText: false,
							itemRequired: true
						},
						{
							id: 3,
							default_: false,
							icon: "mdi mdi-volume-high",
							label: "LBL_PAS_LISTEN_PLAYED_AUDIO",//"LBL_PAS_PLAY"
							multiselect: false,
							position: 1,
							showText: false,
							itemRequired: true
						}
					]
				};

				let events = {};
				events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];

				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 1,
					function: () => {

						let selectedItems = self.zonesDataTable.getSelectedItems();
						
						if (Array.isArray(selectedItems) && selectedItems.length > 0) {
						
							function callBack(message) {
	
								let command = self.config.commands.SEND_MESSAGE;
	
								command.elementId = self.element.id;
								command.commandElementValues[0].value = JSON.stringify(selectedItems.map(e => {return e.messageZoneId}));
								command.commandElementValues[1].value = JSON.stringify([message]);
	
								let commands = [];
								
								commands.push(command);
							
								if (Array.isArray(commands) && commands.length > 0) {
												
									util.element.sendCommand(commands, function(res) {
										
										if (res) {
										
											(new MessageBox (MessageBox.INFO, i18n.t("LBL_COMMAND_SEND_SUCCESSFULLY"), null, null)).show();	
										}
									});
								}
							}
	
							if (self.pasLibraries && self.pasLibraries.pasLibrary) {
	
								let pasLibrary = new PasLibrary(self.pasLibraries.pasLibrary, callBack, selectedItems.length);
								pasLibrary.show();
	
							} else {
	
								let ms = new MessageBox(MessageBox.ERROR, i18n.t("LBL_NOT_FOUND_PAS_LIBRARY_TEXT"), null, null);
								ms.show();
							}	
						}
						
						
						

					}
				});

				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 2,
					function: () => {

						let selectedItems = self.zonesDataTable.getSelectedItems();

						if (Array.isArray(selectedItems) && selectedItems.length > 0) {

							let onAirItems = selectedItems.filter(item => item.onAir == 1);

							if (selectedItems.length === onAirItems.length) {

								let labelDescription = (onAirItems.length > 1) ? i18n.t("LBL_PAS_SWITCH_OFF_CONFIRM_OF_THE") + " " + onAirItems.length + " " + i18n.t("LBL_SELECTED_ZONES_DESCRIPTION") : i18n.t("LBL_PAS_SWITCH_OFF_CONFIRM_DESCRIPTION");

								let ms = new MessageBox(MessageBox.CONFIRM, labelDescription, onAccept, null);
								ms.show();

								function onAccept() {

									let command = self.config.commands.SWITCH_OFF;

									command.elementId = self.element.id;
									command.commandElementValues[0].value = JSON.stringify(selectedItems.map(e => {return e.messageZoneId}));

									let body = {
										commands: []
									};
									body.commands.push(command);

									webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.restEndpoints.command, body).then(function(commandResponse) {

										if (!commandResponse.json()) {

											webix.message({ type: "error", text: i18n.t("LBL_ERROR_OPERATION") });
										}
									});
								}

							} else {

								let labelDescription = (selectedItems.length > 1) ? i18n.t("LBL_NOT_ALL_SELECTED_ZONES_ARE_PLAYING_DESCRIPTION") : i18n.t("LBL_SELECTED_ZONE_IS_NOT_PLAYING_DESCRIPTION");

								let ms = new MessageBox(MessageBox.WARN, labelDescription, null, null);
								ms.show();
							}
						}
					}
				});

				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 3,
					function: () => {

						if (!self.zonesDataTable.getSelectedItems()[0].messageAudio) {

							let ms = new MessageBox(MessageBox.WARN, i18n.t("LBL_PAS_ZONE_NOT_AUDIO_DESCRIPTION"), null, null);
							ms.show();

						} else {

							let soundPlayerDialogBox = new SoundPlayerDialogBox(self.zonesDataTable.getSelectedItems()[0].messageAudio);
							soundPlayerDialogBox.show();
						}
					}
				});

				self.zonesDataTable = new DataTable(null, null, metadata, data, events);

				return self.zonesDataTable.getView();
			}
			return viewBody;
		}
		
		function onMessagePas(data) {

			if (data) {
	
				self.element = eval("adapter." + self.config.adapters.element)(self.element, data);
	
				webix.ui(getViewHeader(), $$("view-content-header-" + self.uid));
			}
		}
	
		function onMessageZone(data) {
	
			if (data) {
	
				let oldZoneData = self.zonesDataTable.getItem(data.id);
	
				if (oldZoneData) {
	
					var zoneIndex = self.zones.findIndex(element => element.id == data.id);
	
					self.zones[zoneIndex] = eval("adapter." + self.config.adapters.element)(self.zones[zoneIndex], data);
	
					updateZone(self.zones[zoneIndex]);
	
					self.zonesDataTable.update(self.zones[zoneIndex]);
				}
			}
		}
	}
}	
