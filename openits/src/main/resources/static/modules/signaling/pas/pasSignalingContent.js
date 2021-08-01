import { util } from "../../../lib/util.js";
import DataTable from "../../../component/datatable.js";
import SoundMessageDialogBox from "../../../component/soundMessageDialogBox.js";
import SoundPlayerDialogBox from "../../../component/soundPlayerDialogBox.js";

export default class PasSignalingContent {

	constructor(config, element) {

		this.config = config;
		this.element = element;
		this.uid = webix.uid();
		
		this.pasLibraries;
		
		this.zones = undefined;
		this.zonesDataTable = undefined;

		this.idStompSubscriptorZones = undefined;
	}

	getView() {

		let self = this;
		let view = {
			id: "view-main-content-" + self.uid,
			rows: [
				getViewHeader(),
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
		
		function getViewHeader() {
			
			let viewHeader = {
				id: "view-content-header-" + self.uid,
				rows: [
					{
						id: "view-content-header-bar-color-" + self.uid,
						height: 15,
						css: { "background": util.element.getColor(getCurrentPasState()) }
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
												<span class="ims-incident-badge" style="background: ${util.element.getColor(getCurrentPasState())}; color: white;">${i18n.t(getCurrentPasState() ? getCurrentPasState().labelAlias : "LBL_UNKNOWN")}</span>
											</div>`;
								}
							},
							{ width: 24 }
						]
					}
				]
			};

			return viewHeader;
		}
		
		function getViewBody() {

			let viewBody = 
			{
				cols: [
					{ width : 10},
					{
						height: 540,
						rows: getZonesTable()
					},
					{ width : 10}
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
						label: "LBL_PAS_ZONE_MESSAGE_ALIAS",
						labelTooltip: "LBL_PAS_ZONE_MESSAGE_ALIAS",
						parameterized: false,
						position: 5,
						required: false,
						sourceField: "messageAlias",
						visible: true,
						widthPx: 200,
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
						icon: "mdi mdi-send-circle-outline",
						label: "LBL_PAS_SEND",
						multiselect: true,
						position: 1,
						showText: false,
						itemRequired: true
					},
					{
						id: 2,
						default_: false,
						icon: "mdi mdi-stop-circle-outline",
						label: "LBL_PAS_STOP",
						multiselect: true,
						position: 2,
						showText: false,
						itemRequired: true
					},
					{
						id: 3,
						default_: false,
						icon: "mdi mdi-play-circle-outline",
						label: "LBL_PAS_PLAY",
						multiselect: false,
						position: 3,
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

					let selectedZones = self.zonesDataTable.getSelectedItems();
					let selectedZonesValue = "[";

					for (var i = 0; i < selectedZones.length; i++) {

						if (i > 0) {
							selectedZonesValue += ", ";
						}
						selectedZonesValue += selectedZones[i].id;
					}

					selectedZonesValue += "]";

					function callBack(messageId) {

						let command = self.config.commands.SEND_MESSAGE;

						command.elementId = self.element.id;
						command.commandElementValues[0].value = selectedZonesValue;
						command.commandElementValues[1].value = messageId;

						let body = {
							commands: []
						};
						body.commands.push(command);

						webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.restEndpoints.command, body).then(function(data) {

							if (!data.json()) {

								webix.message({ type: "error", text: i18n.t("LBL_ERROR_OPERATION") });
							}
						});
					}

					let soundMessageDialogBox = new SoundMessageDialogBox(self.element, callBack);
					soundMessageDialogBox.show();
				}
			});

			events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
				commandId: 2,
				function: () => {

					webix.confirm({
						title: i18n.t("LBL_PAS_SWITCH_OFF_CONFIRM"),
						ok: i18n.t("LBL_ACCEPT"),
						cancel: i18n.t("LBL_CANCEL"),
						text: i18n.t("LBL_PAS_SWITCH_OFF_CONFIRM_DESCRIPTION"),
						width: 600
					}).then(function(res) {

						let selectedZones = self.zonesDataTable.getSelectedItems();
						let selectedZonesValue = "[";

						for (var i = 0; i < selectedZones.length; i++) {

							if (i > 0) {
								selectedZonesValue += ", ";
							}
							selectedZonesValue += selectedZones[i].id;
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
				}
			});

			events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
				commandId: 3,
				function: () => {

					if (!self.zonesDataTable.getSelectedItems()[0].messageId) {

						webix.alert({
							title: i18n.t("LBL_PAS_ZONE_NOT_MESSAGED"),
							text: i18n.t("LBL_PAS_ZONE_NOT_MESSAGED_DESCRIPTION"),
							type: "alert-warning",
							width: 500
						});
					} else {

						let passLibraryNode = self.pasLibraries.pasLibrary.pasLibraryNodes.find(pasLibraryNode => pasLibraryNode.pasMessage.id == self.zonesDataTable.getSelectedItems()[0].messageId);

						let soundPlayerDialogBox = new SoundPlayerDialogBox(passLibraryNode.pasMessage.audio);
						soundPlayerDialogBox.show();
					}
				}
			});

			self.zonesDataTable = new DataTable(null, null, metadata, data, events);

			return self.zonesDataTable.getView();
		}
		
		function getCurrentPasState() {

			let currentPasState = self.element.elementTypeState;
			return currentPasState && currentPasState.value ? window.em.getByField(EntityType.ELEMENT_TYPE_STATE, "id", currentPasState.id) : undefined;

			// let currentPasState = self.element.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.pas.paramState);
			// return currentPasState && currentPasState.value ? window.em.getByField(EntityType.ELEMENT_TYPE_STATE, "id", currentPasState.value) : undefined;
		}
				
		return view;
	}
	
	loadData() {

		let pasLibrariesResponse = webix.ajax().sync().get(appConfig.restUrl + util.fillTemplate(this.config.restEndpoints.pasLibraryByElement, { id: this.element.id }));

		if (pasLibrariesResponse != null) {
						
			this.pasLibraries = JSON.parse(pasLibrariesResponse.responseText);
		}
		
		// let elementResponse = webix.ajax().sync().get(appConfig.restUrl + util.fillTemplate(this.config.restEndpoints.elementById, { id: this.element.id }));
		
		// if (elementResponse != null) {
	
		// 	this.element = JSON.parse(elementResponse.responseText);
		// }
		let elementResponse = webix.ajax().sync().get(appConfig.restUrl + util.fillTemplate(this.config.restEndpoints.elementsByType, { id: this.config.zone.type }));		
		if (elementResponse != null) {				
			this.element.childs = JSON.parse(elementResponse.responseText);
		}

		this.zones = this.element.childs.filter(element => element.elementType.id == this.config.zone.type);

		this.zones.forEach(element => {

			let onAir = element.elementValues.find(elementValue => elementValue.elementTypeParam.id == this.config.zone.paramOnAir);
			let messageId = element.elementValues.find(elementValue => elementValue.elementTypeParam.id == this.config.zone.paramMessageId);
			let messageAlias = element.elementValues.find(elementValue => elementValue.elementTypeParam.id == this.config.zone.paramMessageAlias);
			
			if(onAir && onAir.value && onAir.value == "1"){
				
				element.onAir = onAir.value;
			}else {

				element.onAir = "0";
			}
			
			if(messageId && messageId.value){
				
				element.messageId = messageId;
			}

			if(messageAlias && messageAlias.value){
				
				element.messageAlias = messageAlias;
			}
		});

		this.connectWS();
	}

	callbacks() {

		if(this.zonesDataTable) {
			
			this.zonesDataTable.callbacks();
		}
	}

	connectWS() {

		let self = this;

		let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

		if (stompSubscriptorService) {

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

		function onMessageZone(data) {

			if (data) {

				let oldZoneData = self.zonesDataTable.getItem(data.id);

				if (oldZoneData && data.elementValues) {

					let onAir = data.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.config.zone.paramOnAir);
					let messageId = data.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.config.zone.paramMessageId);
					let messageAlias = data.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.config.zone.paramMessageAlias);
					
					if(onAir && onAir.value != undefined && onAir.value == "1"){
						
						oldZoneData.onAir = onAir.value;

					} else if(onAir && onAir.value != undefined && onAir.value != "1") {

						oldZoneData.onAir = "0";
					}
					
					if(messageId && messageId.value != undefined){
						
						oldZoneData.messageId = messageId;
					} 

					if(messageAlias && messageAlias.value != undefined){
						
						oldZoneData.messageAlias = messageAlias;
					} 

					self.zonesDataTable.update(oldZoneData);
				}
			}
		}
	}

	disConnectWS() {

		let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

		if (stompSubscriptorService) {

			if (this.idStompSubscriptorZones){

				stompSubscriptorService.unsubscribe(this.idStompSubscriptorZones);
			}
		}
	}
}	
