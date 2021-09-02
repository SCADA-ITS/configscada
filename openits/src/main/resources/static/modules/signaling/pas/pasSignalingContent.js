import { util } from "../../../lib/util.js";
import DataTable from "../../../component/datatable.js";
import PasLibrary from "../pas/pasLibrary.js";
import SoundPlayerDialogBox from "../../../component/soundPlayerDialogBox.js";
import { adapter } from "../../../lib/adapter.js";

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
		let view = {
			id: "view-main-content-" + self.uid,
			rows: [
				self.getViewHeader(),
				{ height: 32 },
				getViewBody(),
				{}
			],
			on: {
				onDestruct: (obj) => {

					this.disConnectWS();
				}
			}
		};

		function getViewBody() {

			let viewBody =
			{
				cols: [
					{ width: 10 },
					{
						height: 540,
						rows: getZonesTable()
					},
					{ width: 10 }
				]
			};

			return viewBody;
		}

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
						id: 102901,
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
						id: 102902,
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
						id: 102903,
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
						id: 102904,
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
						id: 102905,
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
						id: 102906,
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
						id: 102907,
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
						id: 102908,
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
						icon: "mdi mdi-send",
						label: "LBL_PAS_SEND",
						multiselect: true,
						position: 3,
						showText: false,
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
					let selectedZonesValue = "[";

					for (var i = 0; i < selectedItems.length; i++) {

						if (i > 0) {
							selectedZonesValue += ", ";
						}
						selectedZonesValue += selectedItems[i].messageZoneId;
					}

					selectedZonesValue += "]";

					function callBack(message) {

						let command = self.config.commands.SEND_MESSAGE;

						command.elementId = self.element.id;
						command.commandElementValues[0].value = selectedZonesValue;
						command.commandElementValues[1].value = "[" + message + "]";

						let body = {
							commands: []
						};
						body.commands.push(command);

						webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.restEndpoints.command, body).then(function(data) {

							if (!data.json()) {

								webix.message({ type: "error", text: i18n.t("LBL_ERROR_OPERATION") });
							} else {
								webix.alert({
									title: i18n.t("LBL_COMMAND_SEND"),
									ok: i18n.t("LBL_ACCEPT"),
									text: i18n.t("LBL_COMMAND_SEND_SUCCESSFULLY"),
									width: 600
								})
							}
						});
					}

					if (self.pasLibraries && self.pasLibraries.pasLibrary) {

						let pasLibrary = new PasLibrary(self.pasLibraries.pasLibrary, callBack, selectedItems.length);
						pasLibrary.show();
						
					} else {

						webix.alert({
							title: i18n.t("LBL_NOT_FOUND_PAS_LIBRARY"),
							text: i18n.t("LBL_NOT_FOUND_PAS_LIBRARY_TEXT"),
							type: "alert-error",
							width: 600
						});
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

							webix.confirm({
								title: i18n.t("LBL_PAS_SWITCH_OFF_CONFIRM"),
								ok: i18n.t("LBL_ACCEPT"),
								cancel: i18n.t("LBL_CANCEL"),
								text: labelDescription,
								width: 600
							}).then(function(res) {

								let selectedZonesValue = "[";

								for (var i = 0; i < selectedItems.length; i++) {

									if (i > 0) {
										selectedZonesValue += ", ";
									}
									selectedZonesValue += selectedItems[i].messageZoneId;
								}

								selectedZonesValue += "]";

								let command = self.config.commands.SWITCH_OFF;

								command.elementId = self.element.id;
								command.commandElementValues[0].value = selectedZonesValue;

								let body = {
									commands: []
								};
								body.commands.push(command);

								webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.restEndpoints.command, body).then(function(data) {

									if (!data.json()) {

										webix.message({ type: "error", text: i18n.t("LBL_ERROR_OPERATION") });
									}
								});
							});

						} else {

							let label = (selectedItems.length > 1) ? i18n.t("LBL_NOT_ALL_SELECTED_ZONES_ARE_PLAYING") : i18n.t("LBL_SELECTED_ZONE_IS_NOT_PLAYING");
							let labelDescription = (selectedItems.length > 1) ? i18n.t("LBL_NOT_ALL_SELECTED_ZONES_ARE_PLAYING_DESCRIPTION") : i18n.t("LBL_SELECTED_ZONE_IS_NOT_PLAYING_DESCRIPTION");

							webix.alert({
								title: label,
								text: labelDescription,
								type: "alert-warning",
								width: 600
							});
						}
					}
				}
			});

			events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
				commandId: 3,
				function: () => {

					if (!self.zonesDataTable.getSelectedItems()[0].messageAudio) {

						webix.alert({
							title: i18n.t("LBL_PAS_ZONE_NOT_AUDIO"),
							text: i18n.t("LBL_PAS_ZONE_NOT_AUDIO_DESCRIPTION"),
							type: "alert-warning",
							width: 500
						});
					} else {

						let soundPlayerDialogBox = new SoundPlayerDialogBox(self.zonesDataTable.getSelectedItems()[0].messageAudio);
						soundPlayerDialogBox.show();
					}
				}
			});

			self.zonesDataTable = new DataTable(null, null, metadata, data, events);

			return self.zonesDataTable.getView();
		}

		return view;
	}

	getViewHeader() {

		let self = this;
		let currentPasState = getCurrentPasState();

		let viewHeader = {
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
						},
						{ width: 24 }
					]
				}
			]
		};

		function getCurrentPasState() {

			let currentPasState = self.element.elementTypeState;
			return currentPasState && currentPasState.id ? window.em.getByField(EntityType.ELEMENT_TYPE_STATE, "id", currentPasState.id) : undefined;
		}

		return viewHeader;
	}

	loadData() {

		let pasLibrariesResponse = webix.ajax().sync().get(appConfig.restUrl + util.fillTemplate(this.config.restEndpoints.pasLibraryByElement, { id: this.element.id }));

		if (pasLibrariesResponse && pasLibrariesResponse.responseText) {

			this.pasLibraries = JSON.parse(pasLibrariesResponse.responseText);
		}

		let elementResponse = webix.ajax().sync().get(appConfig.restUrl + util.fillTemplate(this.config.restEndpoints.elementById, { id: this.element.id }));

		if (elementResponse && elementResponse.responseText) {

			this.element = JSON.parse(elementResponse.responseText);
		}

		this.zones = this.element.childs.filter(element => element.elementType.id == this.config.zone.type);

		this.zones.forEach(element => {

			this.updateZone(element);
		});

		this.connectWS();
	}

	updateZone(element) {

		element.messageAlias = undefined;
		element.messageAudio = undefined;
		element.messageDesc = undefined;

		let messageZoneId = element.elementValues.find(elementValue => elementValue.elementTypeParam.id == this.config.zone.paramZoneId);
		let messageTag = element.elementValues.find(elementValue => elementValue.elementTypeParam.id == this.config.zone.paramMessageTag);
		let messageId = element.elementValues.find(elementValue => elementValue.elementTypeParam.id == this.config.zone.paramMessageId);
		let messageDesc = element.elementValues.find(elementValue => elementValue.elementTypeParam.id == this.config.zone.paramMessageDesc);

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

			if (this.pasLibraries && this.pasLibraries.pasLibrary && Array.isArray(this.pasLibraries.pasLibrary.pasLibraryNodes) && this.pasLibraries.pasLibrary.pasLibraryNodes.length > 0) {

				let passLibraryNode = this.pasLibraries.pasLibrary.pasLibraryNodes.find(pasLibraryNode => pasLibraryNode.pasMessage && pasLibraryNode.pasMessage.message == element.messageId);
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

	callbacks() {

		if (this.zonesDataTable) {

			this.zonesDataTable.callbacks();
		}

		if (this.selectedPks) {

			this.zonesDataTable.select(this.selectedPks);
		}
	}

	connectWS() {

		let self = this;

		let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

		if (stompSubscriptorService) {

			if (this.element) {

				let topic = this.config.topics.pas + "." + this.element.id;
				this.idStompSubscriptorPas = stompSubscriptorService.subscribe(util.stomp.getDataSourceFromTopic(topic), onMessagePas);

			}

			if (this.zones && Array.isArray(this.zones) && this.zones.length > 0) {

				let fields = [];
				fields.push({
					target: "id",
					values: []
				});
				this.zones.forEach(element => fields[0].values.push(element.id));

				this.idStompSubscriptorZones = stompSubscriptorService.subscribe(util.stomp.getDataSourceFromTopic(this.config.topics.zones), onMessageZone, fields);
			}
		}

		function onMessagePas(data) {

			if (data) {

				self.element = eval("adapter." + self.config.adapters.element)(self.element, data);

				webix.ui(self.getViewHeader(), $$("view-content-header-" + self.uid));
			}
		}

		function onMessageZone(data) {

			if (data) {

				let oldZoneData = self.zonesDataTable.getItem(data.id);

				if (oldZoneData) {

					var zoneIndex = self.zones.findIndex(element => element.id == data.id);

					self.zones[zoneIndex] = eval("adapter." + self.config.adapters.element)(self.zones[zoneIndex], data);

					self.updateZone(self.zones[zoneIndex]);

					self.zonesDataTable.update(self.zones[zoneIndex]);
				}
			}
		}
	}

	disConnectWS() {

		let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

		if (stompSubscriptorService) {

			if (this.idStompSubscriptorPas) {

				stompSubscriptorService.unsubscribe(this.idStompSubscriptorPas);
			}

			if (this.idStompSubscriptorZones) {

				stompSubscriptorService.unsubscribe(this.idStompSubscriptorZones);
			}
		}
	}
}	
