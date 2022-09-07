import { util } from "../../../lib/util.js";
import { adapter } from "../../../lib/adapter.js";
import PasLibrary from "../pas/pasLibrary.js";
import DataTable from "../../../component/datatable.js";
import SoundPlayerDialogBox from "../../../component/soundPlayerDialogBox.js";
import MessageBox from "../../../component/messageBox.js";

export default class PasSignalingContent {
	
	static get ENTITY_TYPE_ZONE() { return 1 }
	static get ENTITY_TYPE_GROUP() { return 2 }

	constructor(config, element, childs) {

		this.config = config;
		this.element = element;
		this.uid = webix.uid();
		this.childs = childs;

		this.pasLibraries = undefined;

		this.zones = undefined;
		this.groups = undefined;
		this.zonesDataTable = undefined;
		this.groupsDataTable = undefined;

		this.idStompSubscriptorPas = undefined;
		this.idStompSubscriptorZones = undefined;

		this.commandEquipmentPermissions = util.module.isAllowedPermission(this.config.requiredPermissions.commandEquipment);
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

					self.groups = self.element.childs.filter(element => element.elementType.id == self.config.group.type);
					self.zones = self.element.childs.filter(element => element.elementType.id == self.config.zone.type);

					webix.ui(drawContent(), $$("view-content-" + self.uid));
					
					if (self.groupsDataTable) {

						self.groupsDataTable.callbacks();
						
						if (Array.isArray(self.childs) && self.childs.length > 0) {
							
							let childsSelected = self.childs.filter(child => child.elementType.id == self.config.group.type);
							
							if (childsSelected.length > 0) {
								
								self.groupsDataTable.select(childsSelected.map(e => e.id));
							}
						}
					}
					
					if (self.zonesDataTable) {

						self.zonesDataTable.callbacks();
						
						if (Array.isArray(self.childs) && self.childs.length > 0) {
							
							let childsSelected = self.childs.filter(child => child.elementType.id == self.config.zone.type);
							
							if (childsSelected.length > 0) {
								
								self.zonesDataTable.select(childsSelected.map(e => e.id));
							}
						}
					}

					$$("view-loading-" + self.uid).hide();

					self.groups.forEach(element => {

						updateChild(element);
					});
					
					self.zones.forEach(element => {

						updateChild(element);
					});

					if ($$("dt-" + self.groupsDataTable.uid)) {
						
						$$("dt-" + self.groupsDataTable.uid).refresh();	
					}
					
					if ($$("dt-" + self.zonesDataTable.uid)) {
						
						$$("dt-" + self.zonesDataTable.uid).refresh();	
					}
				}

				connectWS();
			});
		});

		function updateChild(element) {

			element.messageAlias = undefined;
			element.messageAudio = undefined;
			element.messageDesc = undefined;
			
			let messageChildId = undefined;
			let messageTag = undefined;
			let messageId = undefined;
			let messageDesc = undefined;
			
			if (element.elementType.id === self.config.group.type) {
				
				messageChildId = element.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.config.group.paramGroupId);
				messageTag = element.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.config.group.paramMessageTag);
				messageId = element.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.config.group.paramMessageId);
				messageDesc = element.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.config.group.paramMessageDesc);
				
				if (messageChildId && messageChildId.value != undefined) {

					element.messageGroupId = messageChildId.value;
				}
			}
			else if (element.elementType.id === self.config.zone.type) {
				
				messageChildId = element.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.config.zone.paramZoneId);
				messageTag = element.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.config.zone.paramMessageTag);
				messageId = element.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.config.zone.paramMessageId);
				messageDesc = element.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.config.zone.paramMessageDesc);
				
				if (messageChildId && messageChildId.value != undefined) {

					element.messageZoneId = messageChildId.value;
				}
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
				
				if (self.groups && Array.isArray(self.groups) && self.groups.length > 0) {

					let fields = [];
					fields.push({
						target: "id",
						values: []
					});
					self.groups.forEach(element => fields[0].values.push(element.id));

					self.idStompSubscriptorGroups = stompSubscriptorService.subscribe(appConfig.stompUrl, self.config.topics.groups, onMessageGroup, fields);
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

			let viewBodyTab = {
				id: "PasSignalingContent-tab-" + self.uid,
				view: "tabview",
				css: "secondary-tab",
				animate: false,
				borderless: true,
				cells: function() {
					
					let items = [];
					
					if (Array.isArray(self.groups) && self.groups.length > 0) {
						
						if (!self.childs || (Array.isArray(self.childs) && self.childs.length > 0 && self.childs.filter(child => child.elementType.id == self.config.group.type).length > 0)) {
							
							items.push({
								header: i18n.t("LBL_PAS_GROUPS"), 
								body: getTable(self.groups, PasSignalingContent.ENTITY_TYPE_GROUP)
							});	
						}
					}
					
					if (Array.isArray(self.zones) && self.zones.length > 0) {
						
						if (!self.childs || (Array.isArray(self.childs) && self.childs.length > 0 && self.childs.filter(child => child.elementType.id == self.config.zone.type).length > 0)) {
							
							items.push({
								header: i18n.t("LBL_PAS_ZONES"), 
								body: getTable(self.zones, PasSignalingContent.ENTITY_TYPE_ZONE)
							});
						}
					}
					
					return items;
				}(),
				multiview: {
					keepViews: true
		        },
				tabbar: {
					tabMargin: 6
				}
			}

			let viewBody = {
				cols: [
					{ width: 6 },
					viewBodyTab,
					{ width: 6 }
				]
			};

			function getTable(dat, entityType) {

				let data = dat;
				let dt;

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
					label: entityType === PasSignalingContent.ENTITY_TYPE_ZONE ? "LBL_PAS_ZONE" : "LBL_PAS_GROUP",
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
							label: entityType === PasSignalingContent.ENTITY_TYPE_ZONE ? "LBL_PAS_ZONE_ALIAS" : "LBL_PAS_GROUP_ALIAS",
							labelTooltip: entityType === PasSignalingContent.ENTITY_TYPE_ZONE ? "LBL_PAS_ZONE_ALIAS" : "LBL_PAS_GROUP_ALIAS",
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
							label: entityType === PasSignalingContent.ENTITY_TYPE_ZONE ? "LBL_PAS_ZONE_MESSAGE_TAG" : "LBL_PAS_GROUP_MESSAGE_TAG",
							labelTooltip: entityType === PasSignalingContent.ENTITY_TYPE_ZONE ? "LBL_PAS_ZONE_MESSAGE_TAG" : "LBL_PAS_GROUP_MESSAGE_TAG",
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
							label: entityType === PasSignalingContent.ENTITY_TYPE_ZONE ? "LBL_PAS_ZONE_MESSAGE_ALIAS" : "LBL_PAS_GROUP_MESSAGE_ALIAS",
							labelTooltip: entityType === PasSignalingContent.ENTITY_TYPE_ZONE ? "LBL_PAS_ZONE_MESSAGE_ALIAS" : "LBL_PAS_GROUP_MESSAGE_ALIAS",
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
							label: entityType === PasSignalingContent.ENTITY_TYPE_ZONE ? "LBL_PAS_ZONE_MESSAGE_DESCRIPTION" : "LBL_PAS_GROUP_MESSAGE_DESCRIPTION",
							labelTooltip: entityType === PasSignalingContent.ENTITY_TYPE_ZONE ? "LBL_PAS_ZONE_MESSAGE_DESCRIPTION" : "LBL_PAS_GROUP_MESSAGE_DESCRIPTION",
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
							sourceField: entityType === PasSignalingContent.ENTITY_TYPE_ZONE ? "messageZoneId" : "messageGroupId",
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
							id: 3,
							default_: false,
							icon: "mdi mdi-volume-high",
							label: "LBL_PAS_LISTEN_PLAYED_AUDIO",//"LBL_PAS_PLAY"
							multiselect: false,
							position: 1,
							showText: false,
							itemRequired: true
						},
						{
							id: 4,
							default_: true,
							icon: "mdi mdi-information-outline",
							label: "LBL_INFO",
							multiselect: false,
							position: 0,
							showText: false,
							itemRequired: true,
							unselectAllAfter: true
						}
					]
				};

				if (self.commandEquipmentPermissions) {

					metadata.gridCommands.push(
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
						}
					);
						
				}

				let events = {};
				events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];

				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 1,
					function: () => {

						let selectedItems = dt.getSelectedItems();

						if (Array.isArray(selectedItems) && selectedItems.length > 0) {

							function callBack(message) {

								let command = (entityType === PasSignalingContent.ENTITY_TYPE_ZONE) ? self.config.commands.SEND_MESSAGE_ZONES : self.config.commands.SEND_MESSAGE_GROUPS;

								command.elementId = self.element.id;
								command.commandElementValues[0].value = JSON.stringify(selectedItems.map(e => { return entityType === PasSignalingContent.ENTITY_TYPE_ZONE ? e.messageZoneId : e.messageGroupId }));
								command.commandElementValues[1].value = JSON.stringify([message]);

								util.element.sendCommand([command], function(res) {

									if (res) {

										(new MessageBox(MessageBox.INFO, i18n.t("LBL_COMMAND_SEND_SUCCESSFULLY"), null, null)).show();
									}
								});
							}

							if (self.pasLibraries && self.pasLibraries.pasLibrary) {

								let pasLibrary = new PasLibrary(self.pasLibraries.pasLibrary, callBack);
								
								let label = i18n.t("LBL_PAS_LIBRARY_PLAYER_HEADER");
								
								if (selectedItems.length > 1) {
									
									label = i18n.t("LBL_PAS_LIBRARY_PLAYER_HEADER") + " - " + selectedItems.length + " ";
									
									if (entityType === PasSignalingContent.ENTITY_TYPE_GROUP) {
										
										label += i18n.t("LBL_SELECTED_GROUPS")
									}
									else if (entityType === PasSignalingContent.ENTITY_TYPE_ZONE) {
										
										label += i18n.t("LBL_SELECTED_ZONES")
									}	
								}
								
								util.ui.loadWindow(label, pasLibrary.getView(), 700, 600, true);

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

						let selectedItems = dt.getSelectedItems();

						if (Array.isArray(selectedItems) && selectedItems.length > 0) {

							let onAirItems = selectedItems.filter(item => item.onAir == 1);

							if (selectedItems.length === onAirItems.length) {

								let labelDescription = i18n.t("LBL_PAS_SWITCH_OFF_CONFIRM_DESCRIPTION");
								
								if (onAirItems.length > 1) {
									
									labelDescription = i18n.t("LBL_PAS_SWITCH_OFF_CONFIRM_OF_THE") + " " + onAirItems.length + " "; 
									
									if (entityType === PasSignalingContent.ENTITY_TYPE_ZONE) {
								
										labelDescription += i18n.t("LBL_SELECTED_ZONES_DESCRIPTION");	
									}
									else if (entityType === PasSignalingContent.ENTITY_TYPE_GROUP) {
										
										labelDescription += i18n.t("LBL_SELECTED_GROUPS_DESCRIPTION");
									}	
								}
								
								let command = (entityType === PasSignalingContent.ENTITY_TYPE_ZONE) ? self.config.commands.SWITCH_OFF_ZONES : self.config.commands.SWITCH_OFF_GROUPS;

								command.elementId = self.element.id;
								command.commandElementValues[0].value = JSON.stringify(selectedItems.map(e => { return entityType === PasSignalingContent.ENTITY_TYPE_ZONE ? e.messageZoneId : e.messageGroupId }));
								
								util.element.sendCommand([command], function(res) {

									if (res) {

										(new MessageBox(MessageBox.INFO, i18n.t("LBL_COMMAND_SEND_SUCCESSFULLY"), null, null)).show();
									}
								}, labelDescription);

							} else {

								let labelDescription;
								
								if (entityType === PasSignalingContent.ENTITY_TYPE_ZONE) {
									
									labelDescription = (selectedItems.length > 1) ? i18n.t("LBL_NOT_ALL_SELECTED_ZONES_ARE_PLAYING_DESCRIPTION") : i18n.t("LBL_SELECTED_ZONE_IS_NOT_PLAYING_DESCRIPTION");
								}
								else if (entityType === PasSignalingContent.ENTITY_TYPE_GROUP) {
									
									labelDescription = (selectedItems.length > 1) ? i18n.t("LBL_NOT_ALL_SELECTED_GROUPS_ARE_PLAYING_DESCRIPTION") : i18n.t("LBL_SELECTED_GROUP_IS_NOT_PLAYING_DESCRIPTION");
								}

								(new MessageBox(MessageBox.WARN, labelDescription, null, null)).show();
							}
						}
					}
				});

				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 3,
					function: () => {

						if (!dt.getSelectedItems()[0].messageAudio) {

							let ms = new MessageBox(MessageBox.WARN, entityType === PasSignalingContent.ENTITY_TYPE_ZONE ? i18n.t("LBL_PAS_ZONE_NOT_AUDIO_DESCRIPTION") : i18n.t("LBL_PAS_GROUP_NOT_AUDIO_DESCRIPTION"), null, null);
							ms.show();

						} else {

							let soundPlayerDialogBox = new SoundPlayerDialogBox(dt.getSelectedItems()[0].messageAudio);
							soundPlayerDialogBox.show();
						}
					}
				});
				
				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 4,
					function: () => {

						openEquipmentViewer();
					}
				});
				
				dt = new DataTable(null, null, metadata, data, events);
				
				if (entityType === PasSignalingContent.ENTITY_TYPE_ZONE) {
					
					self.zonesDataTable = dt;	
				}
				else if (entityType === PasSignalingContent.ENTITY_TYPE_GROUP) {
					
					self.groupsDataTable = dt;
				}
				
				function openEquipmentViewer() {

					let selectedItems = dt.getSelectedItems();
		
					if (Array.isArray(selectedItems) && selectedItems.length == 1) {
		
						let selectedItem = selectedItems[0];
		
						if (selectedItem) {
		
							let module = util.module.getFromModuleAction(self.config.equipmentViewer);
		
							if (module) {
		
								let moduleAction = { id: self.config.equipmentViewer };
								let args = { selectedPks: [selectedItem.id] };
		
								util.module.open(module, moduleAction, VIEW_TYPE_WINDOW, null, args);
							}
						}
					}
				}

				return { id: "PasSignalingContent-table-" + entityType + "-" + self.uid, css: "bordered-layout", rows: dt.getView() };
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

					updateChild(self.zones[zoneIndex]);

					self.zonesDataTable.update(self.zones[zoneIndex]);
				}
			}
		}
		
		function onMessageGroup(data) {

			if (data) {

				let oldGroupData = self.groupsDataTable.getItem(data.id);

				if (oldGroupData) {

					var groupIndex = self.groups.findIndex(element => element.id == data.id);

					self.groups[groupIndex] = eval("adapter." + self.config.adapters.element)(self.groups[groupIndex], data);

					updateChild(self.groups[groupIndex]);

					self.groupsDataTable.update(self.groups[groupIndex]);
				}
			}
		}
	}
}	
