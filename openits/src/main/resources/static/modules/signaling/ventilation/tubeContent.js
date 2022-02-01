import { util } from "../../../lib/util.js";
import { adapter } from "../../../lib/adapter.js";
import DataTable from "../../../component/datatable.js";
import OptionDialogBox from "../../../component/optionDialogBox.js";
import MessageBox from "../../../component/messageBox.js";
import EquipmentMeasures from "../../../component/equipmentMeasures.js";

export default class TubeContent {

	constructor(tubeId, parent) {

		this.tubeId = tubeId;
		this.parent = parent;
		this.uid = parent.uid;
		this.enabledFanIds = parent.selectedFansIds;

		this.selectedTube = undefined;
		this.fanGrid = undefined;
		this.tubeFans = undefined;
		this.tubeEquipmentsNotFans = undefined;
		this.tubeFansDataTable = undefined;
		this.infoRegime = undefined;
		this.equipmentMeasures = undefined;

		this.idStompSubscriptorTube = undefined;
		this.idStompSubscriptorFans = undefined;
	}

	getView() {

		let self = this;

		let viewMain = {
			id: "view-main-content-" + self.uid,
			cols: [],
			on: {
				onDestruct: (obj) => {

					disConnectWS();
				}
			}
		};

		let viewMainLoading = {
			id: "view-tube-loading-" + self.uid,
			hidden: false,
			rows: [util.ui.getViewDrawLoading()]
		};

		let viewMainContent = {
			id: "view-tube-content-" + self.uid,
			hidden: true
		};

		viewMain.cols = [viewMainLoading, viewMainContent];

		function disConnectWS() {

			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

			if (stompSubscriptorService) {

				if (self.idStompSubscriptorTube)

					stompSubscriptorService.unsubscribe(self.idStompSubscriptorTube);

				if (self.idStompSubscriptorFans)
					stompSubscriptorService.unsubscribe(self.idStompSubscriptorFans);
			}
		}

		return viewMain;
	}

	loadData() {

		let self = this;

		$$("view-tube-loading-" + self.uid).show();
		$$("view-tube-content-" + self.uid).hide();

		if (this.tubeId) {

			webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.parent.config.restEndpoints.elementByIdWithoutChilds, { id: self.tubeId })).then(function(data) {

				if (data != null) {

					self.selectedTube = data.json();
					
					webix.ajax().get(appConfig.restUrl + self.parent.config.restEndpoints.elementsHierarchy).then(function(dataHierarchies) {
						
						if (dataHierarchies != null) {

							let hierarchies = dataHierarchies.json();
							
							if (Array.isArray(hierarchies)) {
								
								let tubeFans = hierarchies.filter(h => h.parent.id === self.selectedTube.id && h.child.elementType.id === self.parent.config.elementTypeFan);

								self.tubeEquipmentsNotFans = hierarchies.filter(h => h.parent.id === self.selectedTube.id && h.child.elementType.id != self.parent.config.elementTypeFan);

								if (Array.isArray(tubeFans)) {
									
									let fanIds = tubeFans.map(t => {
										
										return t.child.id;
									});
									
									webix.ajax().headers(HEADER_POST).post(appConfig.restUrl + self.parent.config.restEndpoints.elementByIds, JSON.stringify(fanIds)).then(function(dataFans) {
									
										if (dataFans != null) {
											
											let fans = dataFans.json();
											
											if (Array.isArray(fans) && fans.length > 0) {
												
												self.tubeFans = eval("adapter." + self.parent.config.adapters.fan)(fans, null);
												
												webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.parent.config.restEndpoints.grid, { id: self.parent.config.gridId })).then(function(dataGrid) {
													
													if (dataGrid != null) {
							
														self.fanGrid = dataGrid.json();
														
														webix.ui(drawContent(), $$("view-tube-content-" + self.uid));
														$$("view-tube-loading-" + self.uid).hide();
									
														if (self.tubeFansDataTable) {
									
															self.tubeFansDataTable.callbacks();
									
															if (self.enabledFanIds) {
									
																self.tubeFansDataTable.select(self.enabledFanIds);
															}
									
															if (getVentilationMode() && self.parent.config.ventilationModeOptions.find(option => option.id == getVentilationMode()).disableFanChange) {
									
																self.tubeFansDataTable.disable();
															}
														}
														
														if(self.equipmentMeasures){
															
															self.equipmentMeasures.loadData();
														}
									
														connectWS();
														
													}
												});
											}
										}
									});
								}
							}
						}
					});
				}
			});
		}

		function checkDisableChangeStatusButton() {

			let changeStatusButton = $$("view-content-button-change-fans-status-" + self.uid);

			if (changeStatusButton) {

				let ventilationMode = getVentilationMode();

				let modeEnableFanActions = !(!ventilationMode || self.parent.config.ventilationModeOptions.find(option => option.id == ventilationMode).disableFanChange);

				if (self.tubeFansDataTable && self.tubeFansDataTable.getAllItems() && Array.isArray(self.tubeFansDataTable.getAllItems()) && self.tubeFansDataTable.getAllItems().length > 0 && modeEnableFanActions) {

					let enabled = self.tubeFansDataTable.getAllItems().find(item => (item.tubeCmdToSend != undefined && item.tubeCmdParamIds != undefined));

					if (enabled) {

						changeStatusButton.enable();
						
					} else {

						changeStatusButton.disable();
					}
				}
				else {

					changeStatusButton.disable();
				}
			}
		}

		function getCurrentVentilationState() {

			let currentVentilationState = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.currentVentilationState);
			return currentVentilationState && currentVentilationState.value ? window.em.getByField(EntityType.ELEMENT_TYPE_STATE, "id", currentVentilationState.value) : undefined;
		}

		function getDisabledChangeRegime() {

			let currentVentilationState = getCurrentVentilationState();
			
			let result = (currentVentilationState && currentVentilationState.id &&
				self.parent.config.ventilationStates[currentVentilationState.id].actions.includes("SEND_VENTILATION_REGIME"));
				
			return !result; 
		}

		function getVentilationRegime(regimeId) {

			let ventilationRegime = undefined;

			if (regimeId) {

				let ventilationRegimesConfig = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.ventilationRegimesConfig);

				if (ventilationRegimesConfig && ventilationRegimesConfig.value) {

					ventilationRegimesConfig = JSON.parse(ventilationRegimesConfig.value);

					if (ventilationRegimesConfig && Array.isArray(ventilationRegimesConfig.ventilationRegimes) && ventilationRegimesConfig.ventilationRegimes.length > 0) {

						ventilationRegime = ventilationRegimesConfig.ventilationRegimes.find(e => e.id == regimeId);
					}

				}
			}
			return ventilationRegime;
		}

		function getCurrentRegime() {

			let currentRegime = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.currentRegime);

			return currentRegime && currentRegime.value ? getVentilationRegime(currentRegime.value) : undefined;
		}

		function getProposedRegime() {

			let currentRegime = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.proposedRegime);

			return currentRegime && currentRegime.value ? getVentilationRegime(currentRegime.value) : undefined;
		}

		function getVentilationMode() {

			let ventilationMode = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.ventilationMode);
			return ventilationMode ? ventilationMode.value : undefined;
		}

		function getContentHeader() {

			let header;

			header = {
				id: "view-content-header-" + self.uid,
				rows: [
					{
						id: "view-content-header-bar-color-" + self.uid,
						height: 15,
						css: { "background": util.element.getColor(getCurrentVentilationState()) }
					},
					{ height: 4 },
					{
						cols: [
							{
								borderless: true,
								id: "view-content-header-info" + self.uid,
								height: 80,
								template: function() {

									return `<image class="ims-incident-report-icon" src="./img/grid/element_type_07.png" />
											<div class="ims-incident-report-title">
												${self.selectedTube.alias}
											</div>
											<div class="ims-incident-report-title">
												<span class="ims-incident-badge" style="background: ${util.element.getColor(getCurrentVentilationState())}; color: white;">${i18n.t(getCurrentVentilationState() ? getCurrentVentilationState().labelAlias : "LBL_UNKNOWN")}</span>
											</div>`;
								}
							},
							{ width: 24 }
						]
					}
				]
			};

			return header;
		}

		function getContentToolbar() {

			let ventilationModeOptions = [];

			self.parent.config.ventilationModeOptions.forEach(option => {

				ventilationModeOptions.push({
					id: option.id,
					value: i18n.t(option.label)
				});
			});

			let viewContentToolbar = {

				id: "view-content-toolbar-" + self.uid,
				view: "toolbar",
				height: 28,
				css: { "box-sizing": "border-box" },
				elements: [],
				borderless: true
			}

			let actionPanelSwitcher = {
				id: "segmented-" + self.parent.uid,
				view: "segmented",
				css: "segment-custom-2",
				value: getVentilationMode(),
				width: 500,
				options: ventilationModeOptions,
				tooltip: true,
				on: {
					onChange(newv, oldv) {

						this.blockEvent();
						this.setValue(oldv);
						this.unblockEvent();

						let options = [];

						for (let modeOption of ventilationModeOptions) {

							let newSelection = modeOption.id === newv ? true : false;

							options.push({
								id: modeOption.id,
								label: modeOption.value,
								description: i18n.t("LBL_MODE_" + modeOption.value.toUpperCase() + "_VENTILATION_DESCRIPTION"),
								default: newSelection
							});
						}

						let optionDialog = new OptionDialogBox(i18n.t("LBL_SELECT_ANY_OPTION"), options, changeMode, OptionDialogBox.MODE_TOGGLE);
						optionDialog.show(oldv);

						function changeMode(newModeId, callbackCloseDialog) {

							let ms = new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_VENTILATION_CHANGE_MODE_CONFIRM_DESCRIPTION"), onAccept, null);
							ms.show();
							
							function onAccept(res) {
								
								callbackCloseDialog(true);

								let command = self.parent.config.commands.SEND_VENTILATION_MODE;
								command.elementId = self.selectedTube.id;
								command.commandElementValues[0].value = newModeId;

								let commands = [];

								commands.push(command);
								
								if (Array.isArray(commands) && commands.length > 0) {
											
									util.element.sendCommand(commands, function(res) {
										
										let ms;
										
										if (res) {
										
											ms = new MessageBox (MessageBox.INFO, i18n.t("LBL_COMMAND_SEND_SUCCESSFULLY"), null, null);	
										}
										else {
											
											ms = new MessageBox (MessageBox.ERROR, i18n.t("LBL_COMMAND_SEND_ERROR"), null, null);
										}
										
										ms.show();
									});
								}		
							}
						}
					}
				}
			};

			viewContentToolbar.elements.push({});
			viewContentToolbar.elements.push(actionPanelSwitcher);

			return viewContentToolbar;
		}

		function connectWS() {

			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

			if (stompSubscriptorService) {

				if (self.tubeId) {

					let topic = self.parent.config.topics.tube + "." + self.tubeId;
					self.idStompSubscriptorTube = stompSubscriptorService.subscribe(appConfig.stompUrl, topic, onMessageTube);

				}

				if (self.tubeFans && Array.isArray(self.tubeFans) && self.tubeFans.length > 0) {

					let fields = [];
					fields.push({
						target: "id",
						values: []
					});
					self.tubeFans.forEach(element => fields[0].values.push(element.id));

					self.idStompSubscriptorFans = stompSubscriptorService.subscribe(appConfig.stompUrl, self.parent.config.topics.fan, onMessageFan, fields);
				}
			}

			function onMessageTube(data) {

				if (data) {

					self.selectedTube = eval("adapter." + self.parent.config.adapters.element)(self.selectedTube, data);
					let ventilationMode = getVentilationMode();

					webix.ui(getContentHeader(), $$("view-content-header-" + self.uid));
					webix.ui(getContentToolbar(), $$("view-content-toolbar-" + self.uid));

					$$("label-regimen-1-" + self.uid).setValue('<div><span class="global-text-badge" style="background:#9c70303b; color: #9c7030a0;">' + i18n.t(getCurrentRegime() ? getCurrentRegime().labelAlias : "LBL_UNKNOWN") + '</span></div>')
					$$("label-regimen-2-" + self.uid).setValue('<div><span class="global-text-badge" style="background:#9c70303b; color: #9c7030a0;">' + i18n.t(getProposedRegime() ? getProposedRegime().labelAlias : "LBL_UNKNOWN") + '</span></div>')

					if (!ventilationMode) {

						self.tubeFansDataTable.disable();
						$$("view-content-button-change-regime-" + self.uid).disable();

					} else {

						if (self.parent.config.ventilationModeOptions.find(option => option.id == ventilationMode).disableRegimeChange) {

							$$("view-content-button-change-regime-" + self.uid).disable();

						} else {

							if (getDisabledChangeRegime()) {

								$$("view-content-button-change-regime-" + self.uid).disable();

							} else {

								$$("view-content-button-change-regime-" + self.uid).enable();
							}

							self.tubeFansDataTable.enable();
						}

						if (self.parent.config.ventilationModeOptions.find(option => option.id == ventilationMode).disableFanChange) {

							self.tubeFansDataTable.disable();

						} else {

							self.tubeFansDataTable.enable();
						}
					}

					checkDisableChangeStatusButton();
				}
			}

			function onMessageFan(data) {

				if (data) {
					
					let newFanData = webix.copy(data);

					let oldFanData = self.tubeFansDataTable.getItem(newFanData.id);

					if (oldFanData) {

						newFanData.tubeCmdToSend = oldFanData.tubeCmdToSend;
						newFanData.tubeCmdParamIds = oldFanData.tubeCmdParamIds 
						newFanData.tubeCmdToSendAlias = oldFanData.tubeCmdToSendAlias;
						newFanData.alias = oldFanData.alias;

						let fanData = eval("adapter." + self.parent.config.adapters.fan)([newFanData], null);
						self.tubeFansDataTable.update(fanData);
					}
				}
			}
		}

		function drawContent() {

			let viewContent = {
				id: "view-tube-content-" + self.uid,
				css: { "background-color": "white" },
				rows: [
					{ height: 6 },
					getContentHeader(),
					{ height: 12 },
					{
						cols: [
							{ width: 3 },
							getContentToolbar(),
							{ width: 3 }
						]
					},
					{
						cols: [
							{ width: 3 },
							getContentBody(),
							{ width: 3 }
						]
					}
				]
			};

			return viewContent;

			function getContentBody() {

				let contentBody = {
					view: "form",
					id: "view-content-body-" + self.uid,
					css: { "box-sizing": "border-box" },
					rows: [
						getRegime(),
						{
							cols: [
								{
									rows: [getContent()]
								}
							]
						},
						getBottom()
					]
				};

				function getRegime() {

					let ventilationRegimesConfig = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.ventilationRegimesConfig);
					let availableRegimes;

					try {

						ventilationRegimesConfig = JSON.parse(ventilationRegimesConfig.value);

						availableRegimes = ventilationRegimesConfig.ventilationRegimes.map(function(reg) {
							return {
								id: reg.id.toString(),
								value: i18n.t(reg.labelAlias)
							};
						});

						updateInfoRegime(getCurrentRegime());

					} catch (error) {

						console.error("El Json de regimenes no esta correctamente formateado.")
					}

					let regime = {
						cols: [
							{
								rows: [
									{ height: 26 },
									{
										width: 500,
										cols: [
											{
												view: "label",
												height: 24,
												//width: 180,
												template: "<span>" + i18n.t("LBL_CURRENT_REGIME") + "</span>",
												css: "global-text"
											},
											{ width: 12 },
											{
												view: "label",
												height: 24,
												//width: 180,
												template: "<span>" + i18n.t("LBL_PROPOSED_REGIME") + "</span> ",
												css: "global-text"
											},
											{ width: 12 },
											{
												view: "label",
												height: 24,
												//width: 180,
												template: "<span>" + i18n.t("LBL_CHANGE_REGIME_TO") + "</span>",
												css: "global-text"
											}
										]
									},
									{ height: 12 },
									{
										width: 500,
										cols: [
											{
												view: "label",
												//width: 180,
												height: 24,
												id: "label-regimen-1-" + self.uid,
												template: '<div><span class="global-text-badge" style="background:#9c70303b; color: #9c7030a0;">' + i18n.t(getCurrentRegime() ? getCurrentRegime().labelAlias : "LBL_UNKNOWN") + '</span></div>',
												css: "global-text"
											},
											{ width: 12 },
											{
												view: "label",
												//width: 180,
												height: 24,
												id: "label-regimen-2-" + self.uid,
												template: '<div><span class="global-text-badge" style="background:#9c70303b; color: #9c7030a0;">' + i18n.t(getProposedRegime() ? getProposedRegime().labelAlias : "LBL_UNKNOWN") + '</span></div>',
												css: "global-text"

											},
											{ width: 12 },
											{
												id: "label-regimen-3-" + self.uid,
												//width: 180,
												height: 28,
												view: "richselect",
												value: getCurrentRegime() ? getCurrentRegime().id : undefined,
												options: availableRegimes,
												on: {
													onChange(newv, oldv) {

														updateInfoRegime(getVentilationRegime(newv));
														webix.ui(getViewDocumentText(), $$("view-content-regime-document-text-" + self.uid));
														webix.ui(getViewDocument(), $$("view-content-regime-document-" + self.uid));
													}
												}
											}
										]
									},
									{}
								]
							},
							{ width: 50 },
							{
								rows: [
									getViewDocument(),
									getViewDocumentText(),
									{ height: 12 },
									{
										cols: [
											{},
											{
												id: "view-content-button-change-regime-" + self.uid,
												width: 130,
												view: "button",
												align: "right",
												css: "button-cta",
												value: i18n.t("LBL_CHANGE_REGIME"),
												disabled: self.parent.config.ventilationModeOptions.find(option => option.id == getVentilationMode()).disableRegimeChange ||
													getDisabledChangeRegime(),
												click: function() {

													let command = self.parent.config.commands.SEND_VENTILATION_REGIME;
													command.elementId = self.tubeId;
													command.commandElementValues[0].value = $$("label-regimen-3-" + self.uid).getValue();

													if (!command.commandElementValues[0].value || command.commandElementValues[0].value === 0) {

														let ms = new MessageBox (MessageBox.WARN, i18n.t("LBL_NO_REGIME_SELECTED_DESCRIPTION"), null, null);
														ms.show();

													} else {

														let ms = new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_SEND_VENTILATION_REGIME_CONFIRM_DESCRIPTION"), onAccept, null);
														ms.show();
														
														function onAccept() {
															
															let commands = [];
															
															commands.push(command);
															
															if (Array.isArray(commands) && commands.length > 0) {
											
																util.element.sendCommand(commands, function(res) {

																	let ms;
										
																	if (res) {
																	
																		ms = new MessageBox (MessageBox.INFO, i18n.t("LBL_COMMAND_SEND_SUCCESSFULLY"), null, null);	
																	}
																	else {
																		
																		ms = new MessageBox (MessageBox.ERROR, i18n.t("LBL_COMMAND_SEND_ERROR"), null, null);
																	}
																	
																	ms.show();
																});
															}
														}
													}
												}
											}
										]
									}
								]
							}
						]
					};

					function getViewDocument() {

						let viewDocument = {
							id: "view-content-regime-document-" + self.uid,
							height: 26
						};

						if (self.infoRegime && self.infoRegime.documentUrl) {

							viewDocument = {
								id: "view-content-regime-document-" + self.uid,
								cols: [
									{
										height: 26,
										align: "right",
										view: "button",
										type: "icon",
										label: self.infoRegime.documentName + " " + self.infoRegime.documentType,
										value: self.infoRegime.documentName + " " + self.infoRegime.documentType,
										tooltip: true,
										icon: "mdi mdi-file",
										click: function() {
											window.open(self.infoRegime.documentUrl, '_blank');
										},
										css: "button-icon-transparent"
									}
								]
							};
						}

						return viewDocument;
					}

					function getViewDocumentText() {
						return {
							id: "view-content-regime-document-text-" + self.uid,
							height: 60,
							//width: 500,
							borderless: true,
							view: "template",
							template: `<div class="ventilation-description-box"><span > ${self.infoRegime.description}</span></div>`
						};
					}

					function updateInfoRegime(ventilationRegime) {

						self.infoRegime = {};

						if (ventilationRegime) {

							self.infoRegime.labelAlias = ventilationRegime.labelAlias;
							self.infoRegime.description = ventilationRegime.description;
							self.infoRegime.documentUrl = ventilationRegime.documentUrl

							if (ventilationRegime.documentUrl) {

								self.infoRegime.documentName = ventilationRegime.documentUrl.substring(ventilationRegime.documentUrl.lastIndexOf("/") + 1, ventilationRegime.documentUrl.lastIndexOf("."));
								self.infoRegime.documentType = ventilationRegime.documentUrl.substring(ventilationRegime.documentUrl.lastIndexOf(".") + 1, ventilationRegime.documentUrl.length);
							}
						} else {

							self.infoRegime.labelAlias = "LBL_NO_REGIME";
							self.infoRegime.description = "";
						}
					}

					return regime;
				}


				function getContent() {

					let viewContent = {};

					viewContent = {
						view: "tabview",
						id: "content-" + self.uid,
						css: "auxiliary-tab",
						tabbar: {
							multiview: true
						},
						multiview: {
							keepViews: true
						},
						animate: false,
						cells: []
					};

					viewContent.cells.push({
						header: i18n.t("LBL_FANS"),
						body: {
							rows: getTable()
						}
					});
					
					if (self.parent.config.viewEquipmentMeasures && self.parent.config.viewEquipmentMeasures.enabled === true) {

						let elementsId = [];

						for (let equipment of self.tubeEquipmentsNotFans) {

							elementsId.push(equipment.child.id);
						}

						self.equipmentMeasures = new EquipmentMeasures(elementsId, null, self.parent.config.viewEquipmentMeasures.options);

						viewContent.cells.push({
							header: i18n.t("LBL_EQUIPMENT_MEASURES"),
							body: self.equipmentMeasures.getView()
						});
					}

					return viewContent;
				}

				function getTable() {

					let data = self.tubeFans;
					let metadata = self.fanGrid.metadata;				

					let events = {};
					events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];
					
					if (Array.isArray(metadata.gridCommands) && metadata.gridCommands.length > 0) {
						
						metadata.gridCommands.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));
						
						let cleanButtonId = metadata.gridCommands[metadata.gridCommands.length - 1].id + 1;
						
						metadata.gridCommands.push({
							id: cleanButtonId,
							default_: false,
							icon: "mdi mdi-broom",
							label: "LBL_CLEAN",
							multiselect: true,
							position: metadata.gridCommands.length + 1,
							showText: false,
							itemRequired: false,
							unselectAllAfter: true
						});
						
						metadata.gridCommands.forEach(gridCommand => {
							
							if (gridCommand.args) {
								
								let args = JSON.parse(gridCommand.args);
								
								if (args.command && args.ids) {
									
									events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
										commandId: gridCommand.id,
										function: () => {
											
											let commandElement = self.selectedTube.commandElements.find(e => e.id === args.command);
				
											self.tubeFansDataTable.getSelectedItems().forEach(item => {
												
												let fanElementTypeState = item[self.parent.config.fanElementTypeState.field];
												
												if (fanElementTypeState && 
													self.parent.config.fanElementTypeState.allowed.includes(fanElementTypeState)) {
													
													item.tubeCmdToSend = commandElement.id;
													item.tubeCmdParamIds = args.ids;
													item.tubeCmdToSendAlias = i18n.t(commandElement.labelAlias);
													self.tubeFansDataTable.update(item);	
												}
											});
				
											checkDisableChangeStatusButton();
										}
									});
								}
							}
							
						});
						
						events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
							commandId: cleanButtonId,
							function: () => {
	
								clearGrid();
							}
						});
					}

					self.tubeFansDataTable = new DataTable(null, null, metadata, data, events);

					return self.tubeFansDataTable.getView();
				}
				
				function clearGrid() {
					
					self.tubeFansDataTable.getAllItems().forEach(item => {
	
						item.tubeCmdToSend = null;
						item.tubeCmdParamIds = null;
						item.tubeCmdToSendAlias = "-";
						self.tubeFansDataTable.update(item);
					});

					checkDisableChangeStatusButton();	
				}

				function getBottom() {

					let bottom;

					bottom = {
						cols: [
							{},
							{
								id: "view-content-button-change-fans-status-" + self.uid,
								width: 130,
								view: "button",
								align: "right",
								css: "button-cta",
								value: i18n.t("LBL_VENTILATION_CHANGE_STATUS"),
								disabled: true,
								click: function() {

									let ms = new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_VENTILATION_CHANGE_STATUS_CONFIRM_DESCRIPTION"), onAccept, null);
									ms.show();
									
									function onAccept() {
										
										let rows = self.tubeFansDataTable.getAllItems();
										
										let cmdMap = new Map();
										
										rows.forEach(row => {
											
											if (row.tubeCmdToSend && row.tubeCmdParamIds) {
												
												let mapItem = cmdMap.get([row.tubeCmdToSend, row.tubeCmdParamIds]);
											
												if (Array.isArray(mapItem)) {
													
													mapItem.push(row.id);
												}
												else {
													
													cmdMap.set([row.tubeCmdToSend, row.tubeCmdParamIds], [row.id]);
												}	
											}
										});
										
										cmdMap.forEach((ids, command) => {
											
											executeFansCommand(command, ids);
										});
										
										self.tubeFansDataTable.unselectAll();
										
										function executeFansCommand(command, ids) {
											
											let commands = [{
												commandElementTypeId: command[0],
												elementId: self.selectedTube.id,
												commandElementValues: [
													{
														commandElementTypeParamId: command[1],
														value: "[" + ids.join() + "]"
													}
												]
											}];
											
											if (Array.isArray(commands) && commands.length > 0) {
											
												util.element.sendCommand(commands, function(res) {
													
													clearGrid();

													let ms;
										
													if (res) {
													
														ms = new MessageBox (MessageBox.INFO, i18n.t("LBL_COMMAND_SEND_SUCCESSFULLY"), null, null);	
													}
													else {
														
														ms = new MessageBox (MessageBox.ERROR, i18n.t("LBL_COMMAND_SEND_ERROR"), null, null);
													}
													
													ms.show();
												});
											}
										}
									}
								}
							}
						]
					};

					return bottom;
				}

				return contentBody;
			}

		}
	}
}


