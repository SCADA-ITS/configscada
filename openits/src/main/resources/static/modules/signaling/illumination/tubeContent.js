import { util } from "../../../lib/util.js";
import DataTable from "../../../component/datatable.js";
import { adapter } from "../../../lib/adapter.js";

const CIRCUIT_COMMAND_SEND_ON = "LBL_CIRCUIT_COMMAND_SEND_ON";
const CIRCUIT_COMMAND_SEND_OFF = "LBL_CIRCUIT_COMMAND_SEND_OFF";

export default class TubeContent {
	
	constructor(tunnelId, tubeId, parent) {

		this.tunnelId = tunnelId;
		this.tubeId = tubeId;
		this.parent = parent;

		this.selectedTube;
		this.tubeCircuits;
		this.tubeCircuitsDataTable;
		this.tubeDalisDataTable;
		this.currentRegime;
		this.proposedRegime;
		this.selectedTunnel;
		this.illuminationModeValueParam;

		this.idStompSubscriptorTunnel = undefined;
		this.idStompSubscriptorTube = undefined;
		this.idStompSubscriptorCircuits = undefined;
	}

	loadData(enabledCircuitIds, drawContent) {

		let self = this;

		webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.parent.config.restEndpoints.elementById, { id: self.tubeId })).then((data) => {

			self.selectedTube = data.json();
			let circuits = self.selectedTube.childs.filter(element => element.elementType.id == self.parent.config.elementTypeIdCircuit);

			if(circuits && Array.isArray(circuits) && circuits.length > 0){

				self.tubeCircuits = [];

				circuits.forEach(element => {

					let circuit = {};

					circuit.id = element.id;
					circuit.alias = element.alias;
					circuit.state = (element.elementTypeState)?element.elementTypeState.id:null;

					if(element.elementValues && Array.isArray(element.elementValues) && element.elementValues.length > 0){
					
						element.elementValues.forEach(elementValue => {
						
							circuit[elementValue.elementTypeParam.id] = elementValue.value;
						});
					}
					
					self.tubeCircuits.push(circuit);
				});
			}

			self.connectWS();

			drawContent(self.getView());

			if (this.tubeCircuitsDataTable) {

				this.tubeCircuitsDataTable.callbacks();

				if (enabledCircuitIds) {
					
					this.tubeCircuitsDataTable.select(enabledCircuitIds);
				}

				if (this.illuminationModeValueParam && this.parent.config.illuminationModeOptions.find(option => option.id == this.illuminationModeValueParam.value).disable) {
					
					this.tubeCircuitsDataTable.disable();
				}
			}

		});
	}


	getView() {
				
		let viewContent = {
			type:"line",
			rows: [
				this.getContentHeader(),
				{
					type:"line",
					cols: [
						this.getContentToolbar(),
					]
				},
				{
					type:"form",
					cols: [
						this.getContentBody(),
					]
				}
			],
			on: {
				onDestruct: (obj) => {

				  this.disConnectWS();
				}
			}
		};

		return viewContent;
	}

	getContentHeader() {

		let self = this;

		let header = {
			id: "view-content-header-" + self.parent.uid,
			type:"line",
			rows: [
				{
					id: "view-content-header-bar-color-" + self.parent.uid,
					height: 15,
					css: { "background": util.element.getColor(self.selectedTube.elementTypeState) }
				},
				{
					type:"line",
					cols: [
						{
							borderless: true,
							id: "view-content-header-info" + self.parent.uid,
							height: 80,
							template: function() {

								return `<image class="ims-incident-report-icon" src="./img/grid/element_type_07.png" />
										<div class="ims-incident-report-title">
											${self.selectedTube.alias}
										</div>
										<div class="ims-incident-report-title">
											<span class="ims-incident-badge" style="background: ${util.element.getColor(self.selectedTube.elementTypeState)}; color: white;">${i18n.t(self.selectedTube.elementTypeState.labelAlias)}</span>
										</div>`;
							}
						}
					]
				}
			]
		};
	
		return header;
	}

	getContentToolbar() {

		let self = this;

		self.illuminationModeValueParam = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.illuminationMode);

		let illuminationModeOptions = [];

		self.parent.config.illuminationModeOptions.forEach(option => {

			illuminationModeOptions.push({
				id: option.id, 
				value: i18n.t(option.label) 
			});
		});
				
		let viewContentToolbar = {
			
			id: "view-content-toolbar-" + self.parent.uid,
			view: "toolbar",
			height: 28,
			elements: [],
			borderless: true
		}
		
		let actionPanelSwitcher = {
			view: "segmented",
			css: "segment-custom-2",
			value: self.illuminationModeValueParam.value,
			width: 500,
			options: illuminationModeOptions,
			tooltip: true,
			on: {
				onChange(newv, oldv) {

					webix.confirm({
						title: i18n.t("LBL_ILLUMINATION_CHANGE_MODE_CONFIRM"),
						ok: i18n.t("LBL_ACCEPT"),
						cancel: i18n.t("LBL_CANCEL"),
						text: i18n.t("LBL_ILLUMINATION_CHANGE_MODE_CONFIRM_DESCRIPTION"),
						width: 600
					}).then(function() {
						
						let command = self.parent.config.commands.SEND_ILLUMINATION_MODE;
						command.elementId = self.selectedTube.id;
						command.commandElementValues[0].value = newv;

						let body = {
							commands: []
						};
						body.commands.push(command);

						webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.parent.config.restEndpoints.command, body).then(function(data) {
							
							if (!data.json()){
								
								webix.message({type:"error", text:i18n.t("LBL_ERROR_OPERATION")});
							}
						});
						
					}).fail(() => {

						this.blockEvent();
						this.setValue(oldv); 
						this.unblockEvent();
					});
				}
			}
		};
		
		viewContentToolbar.elements.push({});
		viewContentToolbar.elements.push(actionPanelSwitcher);
		
		return viewContentToolbar;
	}

	getContentBody(){

		let self = this;

		let contentBody = {
			css: { "margin-top" : "0px !important" },
			view: "form", 
			id: "view-content-body-" + self.parent.uid,
			rows:[
				getRegime(),
				getButtons(),
				{
					id: "view-content-body-dt-" + self.parent.uid,
					cols: [
						getCircuitsTable()
					]
				},
				getBottom(),
			]
		}

		function getRegime(){

			let illuminationRegimeConfig = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.illuminationRegimeConfig);
			let availableRegimes;
			self.currentRegime = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.currentRegime);
			self.proposedRegime = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.proposedRegime);

			try {

				illuminationRegimeConfig = JSON.parse(illuminationRegimeConfig.value);
				//TODO
				// let REGIMES = "illuminationRegimes";
				let REGIMES = "ventilationRegimes";
				availableRegimes = illuminationRegimeConfig[REGIMES].map(function(regime) {
					return { 
						id: regime.id, 
						value: i18n.t(regime.labelAlias)
					};
				});

				if(self.currentRegime.value && illuminationRegimeConfig[REGIMES]) {

					self.currentRegime.labelAlias = illuminationRegimeConfig[REGIMES].find(e =>e.id == self.currentRegime.value).labelAlias;
				} else {
					self.currentRegime.labelAlias = "LBL_NO_REGIME";
				}

				updateProposedRegime(self.proposedRegime.value, illuminationRegimeConfig[REGIMES]);
				
			} catch (error) {

				console.error("El Json de regimenes no esta correctamente formateado.")
			}

			let regime = {
				type:"line",
				cols: [
					{
						type:"line",
						rows: [
							{ height: 26 },
							{
								type:"line",
								cols: [
									{
										view: "label",
										height: 24,
										width: 180,
										template: "<span>" + i18n.t("LBL_ACTUAL_REGIME") + "</span>",
										css: "global-text"
									},
									{ width: 12 },
									{
										view: "label",
										height: 24,
										width: 180,
										template: "<span>" + i18n.t("LBL_PROPOSED_REGIME") + "</span> ",
										css: "global-text"
									},
									{ width: 12 },
									{
										view: "label",
										height: 24,
										width: 180,
										template: "<span>" + i18n.t("LBL_CHANGE_REGIME_TO") + "</span>",
										css: "global-text"
									}
								]
							},
							{ height: 12 },
							{
								type:"line",
								cols: [
									{
										view: "label",
										width: 180,
										height: 24,
										id: "label-regimen-1-" + self.parent.uid,
										template: '<div><span class="global-text-badge" style="background:#9c70303b; color: #9c7030a0;">' + i18n.t(self.currentRegime.labelAlias) + '</span></div>',
										css: "global-text"
									},
									{ width: 12 },
									{
										view: "label",
										width: 180,
										height: 24,
										id: "label-regimen-2-" + self.parent.uid,
										template: '<div><span class="global-text-badge" style="background:#9c70303b; color: #9c7030a0;">' + i18n.t(self.proposedRegime.labelAlias) + '</span></div>',
										css: "global-text"
									},
									{ width: 12 },
									{
										id: "label-regimen-3-" + self.parent.uid,
										width: 180,
										height: 28,
										view: "combo", 
										// css: {"margin-top": "-5px"},
										value: self.proposedRegime.value, 
										options: availableRegimes,
										on: {
											onChange(newv, oldv) {

												//TODO
												// updateProposedRegime(newv, illuminationRegimeConfig.illuminationRegimes);
												updateProposedRegime(newv, illuminationRegimeConfig.ventilationRegimes);
												webix.ui(getViewDocumentText(), $$("view-content-regime-document-text-" + self.parent.uid));
												webix.ui(getViewDocument(), $$("view-content-regime-document-" + self.parent.uid));
											}
										}
									}										
								]
							},
							{}
						] 
					},
					{},
					{
						type:"line",
						rows: [
							getViewDocument(),
							getViewDocumentText(),
							{ height: 12 },
							{ 	
								type:"line",
								cols:[
									{},
									{ 	
										id: "view-content-button-change-regime-" + self.parent.uid,
										width: 130,
										view: "button",
										align: "right", 
										css: "button-cta",
										value: i18n.t("LBL_CHANGE_REGIME"),
										disabled: self.parent.config.illuminationModeOptions.find(option => option.id == self.illuminationModeValueParam.value).disable,
										click: function() {

											let command = self.parent.config.commands.SEND_ILLUMINATION_REGIME;
											command.elementId = self.tubeId;
											command.commandElementValues[0].value = $$("label-regimen-3-" + self.parent.uid).getValue();

											if (!command.commandElementValues[0].value || command.commandElementValues[0].value == 0) {

												webix.alert({
													title: i18n.t("LBL_NO_REGIME_SELECTED"),
													text: i18n.t("LBL_NO_REGIME_SELECTED_DESCRIPTION"),
													type: "alert-warning",
													width: 500
												});

											} else {

												webix.confirm({
													title: i18n.t("LBL_SEND_ILLUMINATION_REGIME_CONFIRM"),
													ok: i18n.t("LBL_ACCEPT"),
													cancel: i18n.t("LBL_CANCEL"),
													text: i18n.t("LBL_SEND_ILLUMINATION_REGIME_CONFIRM_DESCRIPTION"),
													width: 600
												}).then(function() {

													let body = {
														commands: []
													};
													body.commands.push(command);

													webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.parent.config.restEndpoints.command, body).then(function(data) {

														if (!data.json()) {

															webix.message({ type: "error", text: i18n.t("LBL_ERROR_OPERATION") });
														}
													});
												});
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
					id: "view-content-regime-document-" + self.parent.uid,
					height: 26 
				};

				if(self.proposedRegime.documentUrl) {

					viewDocument = {
						id: "view-content-regime-document-" + self.parent.uid,
						type:"line",
						cols: [
							{},
							{
								height: 26,
								align: "right",
								width: 500,
								view: "button",
								type: "icon",
								label: self.proposedRegime.documentName + " " + self.proposedRegime.documentType,
								value: self.proposedRegime.documentName + " " + self.proposedRegime.documentType,
								tooltip: true,
								icon: "mdi mdi-file",
								click: function() {
									window.open(self.proposedRegime.documentUrl, '_blank');
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
					id: "view-content-regime-document-text-"+ self.parent.uid,
					height: 60,
					width: 500,
					borderless: true,
					view: "template",
					template: `<div class="ventilation-description-box"><span > ${self.proposedRegime.description}</span></div>`
				};
			}

			function updateProposedRegime(value, illuminationRegimes) {

				self.proposedRegime.value = value;

				if (self.proposedRegime.value && illuminationRegimes) {

					let proposedConfig = illuminationRegimes.find(e => e.id == self.proposedRegime.value);
					self.proposedRegime.labelAlias = proposedConfig.labelAlias;
					self.proposedRegime.description = proposedConfig.description;
					self.proposedRegime.documentUrl = proposedConfig.documentUrl

					if (self.proposedRegime.documentUrl) {

						self.proposedRegime.documentName = self.proposedRegime.documentUrl.substring(self.proposedRegime.documentUrl.lastIndexOf("/") + 1, self.proposedRegime.documentUrl.lastIndexOf("."));
						self.proposedRegime.documentType = self.proposedRegime.documentUrl.substring(self.proposedRegime.documentUrl.lastIndexOf(".") + 1, self.proposedRegime.documentUrl.length);
					}
				} else {

					self.proposedRegime.labelAlias = "LBL_NO_REGIME";
					self.proposedRegime.description = "";
				}
			}

			return regime;
		}

		function getButtons() {

			let buttons = {
				type:"line",
				cols: [
					{
						view: "segmented",
						css: "segment-custom-2",
						value: "action-panel-switcher-circuit" + self.parent.uid,
						width: 300,
						options: [
							{
								id: "action-panel-switcher-circuit" + self.parent.uid,
								value: "<span>" + i18n.t("LBL_CIRCUIT") + "</span>",
							},
							{
								id: "action-panel-switcher-dali" + self.parent.uid,
								value: "<span>" + i18n.t("LBL_DALI") + "</span>",
							},
							{
								id: "action-panel-switcher-tube" + self.parent.uid,
								value: "<span>" + i18n.t("LBL_TUBE") + "</span>",
							}
						],
						tooltip: function(obj) {
		
							if (obj.id === "action-panel-switcher-circuit" + self.parent.uid) {
		
								return i18n.t("LBL_CIRCUIT");
							}
							else if (obj.id === "action-panel-switcher-dali" + self.parent.uid) {
		
								return i18n.t("LBL_DALI");
							}
							else if (obj.id === "action-panel-switcher-tube" + self.parent.uid) {
		
								return i18n.t("LBL_TUBE");
							}
						},
						on: {
							onChange(newv, oldv) {
		
								let targetLayout = {
									id: "view-content-body-dt-" + self.parent.uid
								};

								if (newv === "action-panel-switcher-circuit" + self.parent.uid) {
									
									targetLayout.cols = [getCircuitsTable()];
								}
								else if (newv === "action-panel-switcher-dali" + self.parent.uid) {
		
									targetLayout.cols = [getDalisTable()];
								}
								else if (newv === "action-panel-switcher-tube" + self.parent.uid) {
		
									webix.message("show-tube");
								}
								
								webix.ui(targetLayout, $$("view-content-body-dt-" + self.parent.uid));
							}
						}
					},
					{}
				]
			};

			return buttons;
		}

		function getCircuitsTable() {

			let data = self.tubeCircuits;

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
				label: "LBL_CIRCUIT",
				visible: true,
				gridOptionValues: defaultGridOptionValues,
				gridFields: [
					{
						id: 102801,
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
						id: 102802,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						parameterized: false,
						position: 2,
						required: true,
						sourceField: "state",
						visible: true,
						widthPx: 30,
						adjust: false,
						gridFieldDataType: {
							id: 1,
							name: "string"
						},
						gridFieldIcons: [
							{
								icon: "./img/state/stateUp.png",
								showValue: false,
								value: "ElementTypeState:9:1",
								labelValue: "LBL_ELEMENT_TYPE_STATE_UP"
							},
							{
								icon: "./img/state/stateDown.png",
								showValue: false,
								value: "ElementTypeState:9:2",
								labelValue: "LBL_ELEMENT_TYPE_STATE_DOWN"
							},
							{
								icon: "./img/state/stateAlarm.png",
								showValue: false,
								value: "ElementTypeState:9:3",
								labelValue: "LBL_ELEMENT_TYPE_STATE_ALARMS"

							}
						]
					},
					{
						id: 102803,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_FAN_ALIAS",
						labelTooltip: "LBL_FAN_ALIAS",
						parameterized: false,
						position: 3,
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
						id: 102804,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_FAN_STATE",
						labelTooltip: "LBL_FAN_STATE",
						parameterized: false,
						position: 4,
						required: true,
						sourceField: self.parent.config.params.fanState,
						visible: true,
						widthPx: 250,
						adjust: false,
						gridFieldDataType: {
							id: 1,
							name: "string"
						},
						replacementFunction: "getLabelToFanState",
						gridFieldIcons: [
							{
								showValue: false,
								badgeColor: true,
								avatar: false,
								enabled: true
							}
						]
					},
					{
						id: 102805,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						needsTranslation: false,
						parameterized: false,
						position: 5,
						required: false,
						sourceField: "statusSendId",
						visible: false,
						widthPx: 1,
						gridFieldDataType: {
							id: 1,
							name: "string"
						}
					},{
						id: 102806,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_CIRCUIT_SEND_STATUS",
						labelTooltip: "LBL_CIRCUIT_SEND_STATUS",
						needsTranslation: false,
						parameterized: false,
						position: 6,
						required: true,
						sourceField: "statusSendAlias",
						visible: true,
						widthPx: 400,
						adjust: false,
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
						icon: "mdi mdi-toggle-switch",
						label: "LBL_SWITCH_ON",
						multiselect: true,
						position: 1,
						showText: true,
						itemRequired: true
					},
					{
						id: 2,
						default_: false,
						icon: "mdi mdi-toggle-switch-off-outline",
						label: "LBL_SWITCH_OFF",
						multiselect: true,
						position: 2,
						showText: true,
						itemRequired: true
					},
					{
						id: 3,
						default_: false,
						icon: "mdi mdi-cancel",
						label: "LBL_CLEAR",
						multiselect: true,
						position: 3,
						showText: false,
						itemRequired: true
					},
					{
						id: 4,
						default_: false,
						icon: "mdi mdi-pen",
						label: "LBL_CHANGE_REGIME",
						multiselect: true,
						position: 4,
						showText: true,
						itemRequired: true
					}
				]

			};

			let events = {};
			events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];

			events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
				commandId: 1,
				function: () => {

					self.tubeCircuitsDataTable.getSelectedItems().forEach(item => {
						
						item.statusSendId = CIRCUIT_COMMAND_SEND_ON;
						item.statusSendAlias = i18n.t("LBL_CIRCUIT_COMMAND_SEND_ON");
						self.tubeCircuitsDataTable.update(item);
					});						
				}
			});

			events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
				commandId: 2,
				function: () => {

					self.tubeCircuitsDataTable.getSelectedItems().forEach(item => {

						item.statusSendId = CIRCUIT_COMMAND_SEND_OFF;
						item.statusSendAlias = i18n.t("LBL_CIRCUIT_COMMAND_SEND_OFF");
						self.tubeCircuitsDataTable.update(item);
					});
				}
			});

			events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
				commandId: 3,
				function: () => {

					self.tubeCircuitsDataTable.getSelectedItems().forEach(item => {

						item.statusSendId = null;
						item.statusSendAlias = "-";
						self.tubeCircuitsDataTable.update(item);
					});
				}
			});

			events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
				commandId: 4,
				function: () => {

					let head = {
						view: "toolbar",
						css: "default-window-head",
						cols: [
							{
	
								view: "label",
								label: i18n.t("LBL_CHANGE_REGIME")
							},
							{
								view: "icon",
								icon: "mdi mdi-close",
								on: {
									onItemClick: function(id) {
										$$("view-windows-regime-" + self.parent.uid).close();
									}
								}
							}
						],
						borderless: 1
					};
	
					webix.ui({
						view: "window",
						id: "view-windows-regime-" + self.parent.uid,
						move: true,
						position: "center",
						modal: true,
						css: "default-window",
						head: head,
						height: 500,
						width: 800,
						body: getViewRegimeWindow()
					}).show();					
				}
			});

			function getViewRegimeWindow(){

				let illuminationRegimeConfig = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.illuminationRegimeConfig);
				illuminationRegimeConfig = JSON.parse(illuminationRegimeConfig.value);
				//TODO ventilationRegimes
				let availableRegimes = illuminationRegimeConfig["ventilationRegimes"].map(function(regime) {
					return { 
						id: regime.id, 
						value: i18n.t(regime.labelAlias)
					};
				});

				let combo = {
					rows: [
						{
							view: "label",
							height: 24,
							width: 180,
							template: "<span>" + i18n.t("LBL_CHANGE_REGIME_TO") + "</span>",
							css: "global-text"
						},
						{ height: 12 },
						{
							width: 180,
							height: 28,
							view: "combo", 
							options: availableRegimes,
							on: {
								onChange(newv, oldv) {
									updateProposedRegime(newv, illuminationRegimeConfig.ventilationRegimes);
									webix.ui(getViewDocumentText(), $$("view-windows-regime-document-text-" + self.parent.uid));
									webix.ui(getViewDocument(), $$("view-windows-regime-document-" + self.parent.uid));
								}
							}
						}
					]
				};

				let docs = {
					rows: [
						getViewDocument(),
						getViewDocumentText()
					]
				};

				let viewRegimesWindow = {
					cols: [
						combo,
						{width: 12},
						docs
					]
				};

				return viewRegimesWindow;

				function updateProposedRegime(value, illuminationRegimes) {

					self.proposedWindowRegimeValue = value;
	
					if (self.proposedWindowRegimeValue && illuminationRegimes) {
	
						let proposedConfig = illuminationRegimes.find(e => e.id == self.proposedWindowRegimeValue);
						self.proposedWinRegimeDescription = proposedConfig.description;
						self.proposedWinRegimeDocumentUrl = proposedConfig.documentUrl
	
						if (self.proposedWinRegimeDocumentUrl) {
	
							self.proposedWinRegimeDocumentName = self.proposedWinRegimeDocumentUrl.substring(self.proposedWinRegimeDocumentUrl.lastIndexOf("/") + 1, self.proposedWinRegimeDocumentUrl.lastIndexOf("."));
							self.proposedWinRegimeDocumentType = self.proposedWinRegimeDocumentUrl.substring(self.proposedWinRegimeDocumentUrl.lastIndexOf(".") + 1, self.proposedWinRegimeDocumentUrl.length);
						}
					} else {
	
						self.proposedWinRegimeDescription = "";
					}
				}

				function getViewDocument() {

					let viewDocument = {
						id: "view-windows-regime-document" + self.parent.uid,
						height: 26 
					};
				
					if(self.proposedWinRegimeDocumentUrl) {
				
						viewDocument = {
							id: "view-windows-regime-document-" + self.parent.uid,
							type:"line",
							cols: [
								{},
								{
									height: 26,
									align: "right",
									width: 500,
									view: "button",
									type: "icon",
									label: self.proposedWinRegimeDocumentName + " " + self.proposedWinRegimeDocumentType,
									value: self.proposedWinRegimeDocumentName + " " + self.proposedWinRegimeDocumentType,
									tooltip: true,
									icon: "mdi mdi-file",
									click: function() {
										window.open(self.proposedWinRegimeDocumentUrl, '_blank');
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
						id: "view-windows-regime-document-text-"+ self.parent.uid,
						height: 60,
						width: 500,
						borderless: true,
						view: "template",
						template: `<div class="ventilation-description-box"><span > ${self.proposedWinRegimeDescription}</span></div>`
					};
				}

			}

			self.tubeCircuitsDataTable = new DataTable(null, null, metadata, data, events);
			
			return {height: 338,rows:self.tubeCircuitsDataTable.getView()};
		};

		function getDalisTable() {

			let data = self.tubeCircuits;

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
				label: "LBL_DALI",
				visible: true,
				gridOptionValues: defaultGridOptionValues,
				gridFields: [
					{
						id: 102801,
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
						id: 102802,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						parameterized: false,
						position: 2,
						required: true,
						sourceField: "state",
						visible: true,
						widthPx: 30,
						adjust: false,
						gridFieldDataType: {
							id: 1,
							name: "string"
						},
						gridFieldIcons: [
							{
								icon: "./img/state/stateUp.png",
								showValue: false,
								value: "ElementTypeState:9:1",
								labelValue: "LBL_ELEMENT_TYPE_STATE_UP"
							},
							{
								icon: "./img/state/stateDown.png",
								showValue: false,
								value: "ElementTypeState:9:2",
								labelValue: "LBL_ELEMENT_TYPE_STATE_DOWN"
							},
							{
								icon: "./img/state/stateAlarm.png",
								showValue: false,
								value: "ElementTypeState:9:3",
								labelValue: "LBL_ELEMENT_TYPE_STATE_ALARMS"

							}
						]
					},
					{
						id: 102803,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_DALI_ALIAS",
						labelTooltip: "LBL_DALI_ALIAS",
						parameterized: false,
						position: 3,
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
						id: 102804,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_DALI_STATE",
						labelTooltip: "LBL_DALI_STATE",
						parameterized: false,
						position: 4,
						required: true,
						sourceField: self.parent.config.params.fanState,
						visible: true,
						widthPx: 250,
						adjust: false,
						gridFieldDataType: {
							id: 1,
							name: "string"
						},
						replacementFunction: "getLabelToFanState",
						gridFieldIcons: [
							{
								showValue: false,
								badgeColor: true,
								avatar: false,
								enabled: true
							}
						]
					},
					{
						id: 102805,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						needsTranslation: false,
						parameterized: false,
						position: 5,
						required: false,
						sourceField: "statusSendId",
						visible: false,
						widthPx: 1,
						gridFieldDataType: {
							id: 1,
							name: "string"
						}
					},{
						id: 102806,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_DALI_SEND_STATUS",
						labelTooltip: "LBL_DALI_SEND_STATUS",
						needsTranslation: false,
						parameterized: false,
						position: 6,
						required: true,
						sourceField: "statusSendAlias",
						visible: true,
						widthPx: 400,
						adjust: false,
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
						icon: "mdi mdi-pen",
						label: "LBL_CHANGE_REGIME",
						multiselect: true,
						position: 1,
						showText: true,
						itemRequired: true
					},
					{
						id: 2,
						default_: false,
						icon: "mdi mdi-cancel",
						label: "LBL_CLEAR",
						multiselect: true,
						position: 2,
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

					let head = {
						view: "toolbar",
						css: "default-window-head",
						cols: [
							{
	
								view: "label",
								label: i18n.t("LBL_CHANGE_REGIME")
							},
							{
								view: "icon",
								icon: "mdi mdi-close",
								on: {
									onItemClick: function(id) {
										$$("view-windows-regime-" + self.parent.uid).close();
									}
								}
							}
						],
						borderless: 1
					};
	
					webix.ui({
						view: "window",
						id: "view-windows-regime-" + self.parent.uid,
						move: true,
						position: "center",
						modal: true,
						css: "default-window",
						head: head,
						height: 400,
						width: 800,
						body: {
							rows: [
								{
									view: "template",
									template: function() {
					
										let icon = "<img src='img/loading.svg' align='center'>";
					
										return "<div class='empty-app-inner'>" + icon + "<span class='empty-app-inner-text'>" + i18n.t("LBL_LOADING_DATA") + "</span></div>";
									}
								}
							]
						}
					}).show();					
				}
			});

			events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
				commandId: 2,
				function: () => {

					self.tubeDalisDataTable.getSelectedItems().forEach(item => {

						item.statusSendId = null;
						item.statusSendAlias = "-";
						self.tubeDalisDataTable.update(item);
					});
				}
			});

			self.tubeDalisDataTable = new DataTable(null, null, metadata, data, events);
			
			return {height: 338,rows:self.tubeDalisDataTable.getView()};
		};

		function getBottom(){

			let bottom = {
				type:"line",
				cols:[
					{},
					{ 	
						id:"view-content-button-change-circuits-status-" + self.parent.uid,
						width: 130,
						view: "button",
						align:"right", 
						css: "button-cta",
						value: i18n.t("LBL_ILLUMINATION_CHANGE_STATUS"),
						disabled: self.parent.config.illuminationModeOptions.find(option => option.id == self.illuminationModeValueParam.value).disable,
						click: function () { 
							
							webix.confirm({
								title: i18n.t("LBL_ILLUMINATION_CHANGE_STATUS_CONFIRM"),
								ok: i18n.t("LBL_ACCEPT"),
								cancel: i18n.t("LBL_CANCEL"),
								text: i18n.t("LBL_ILLUMINATION_CHANGE_STATUS_CONFIRM_DESCRIPTION"),
								width: 600
							}).then(function() {
								
								let allTableItems = self.tubeCircuitsDataTable.getAllItems();
								
								let circuitsOnCommand = [];
								let circuitsOffCommand = [];
								
								allTableItems.forEach(row => {

									if(row.statusSendId == CIRCUIT_COMMAND_SEND_ON){

										circuitsOnCommand.push(row.id);
									
									} else if(row.statusSendId == CIRCUIT_COMMAND_SEND_OFF){

										circuitsOffCommand.push(row.id);
									
									}
								});

								if(circuitsOnCommand.length > 0) {

									executeCircuitCommand(CIRCUIT_COMMAND_SEND_ON, circuitsOnCommand);
								}  
								
								if(circuitsOffCommand.length > 0) {

									executeCircuitCommand(CIRCUIT_COMMAND_SEND_OFF, circuitsOffCommand);
								}

								function executeCircuitCommand(commandAction, values){

									let command = self.parent.config.commands[commandAction];
									command.elementId = self.selectedTube.id;
									command.commandElementValues[0].value = JSON.stringify(values);
		
									let body = {
										commands : []
									};
									body.commands.push(command);
		
									webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.parent.config.restEndpoints.command, body).then(function(data) {
										
										self.tubeFansDataTable.getAllItems().forEach(row => {

											row.statusSendId = null;
											row.statusSendAlias = null;
											self.tubeCircuitsDataTable.update(row);
										});

										if (!data.json()) {

											webix.message({ type: "error", text: i18n.t("LBL_ERROR_OPERATION") });
										}
									});
								}
							});
						} 
					}
				]
			};

			return bottom;
		}

		return contentBody;
	}

	connectWS() {
	
		let self = this;

		let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

		if (stompSubscriptorService) {

			if (self.tubeId) {

				let topic = self.parent.config.topics.tube + "." + self.tubeId;
				self.idStompSubscriptorTube = stompSubscriptorService.subscribe(util.stomp.getDataSourceFromTopic(topic), onMessageTube);
			
			} else {

				let topic = self.parent.config.topics.tunnel + "." + self.tunnelId;
				self.idStompSubscriptorTunnel = stompSubscriptorService.subscribe(util.stomp.getDataSourceFromTopic(topic), onMessageTunnel);
			}

			if (self.tubeCircuits && Array.isArray(self.tubeCircuits) && self.tubeCircuits.length > 0) {

				let fields = [];
				fields.push({
					target: "id",
					values: []
				});
				self.tubeCircuits.forEach(element => fields[0].values.push(element.id));
								
				self.idStompSubscriptorCircuits = stompSubscriptorService.subscribe(util.stomp.getDataSourceFromTopic(self.parent.config.topics.circuits), onMessageCircuit, fields);
			}	
		}

		function onMessageTunnel(data) {

			if (data) {

			}
		}

		function onMessageTube(data) {

			if (data) {

				self.selectedTube = eval("adapter." + self.parent.config.adapters.element)(self.selectedTube, data);
				self.currentRegime = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.currentRegime);
				self.proposedRegime = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.proposedRegime);
				self.illuminationModeValueParam = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.illuminationMode);

				webix.ui(self.getContentHeader(), $$("view-content-header-" + self.parent.uid));
				webix.ui(self.getContentToolbar(), $$("view-content-toolbar-" + self.parent.uid));
				
				let actualRegimeLabel = self.currentRegime.value ? self.currentRegime.labelAlias : "LBL_NO_REGIME";
				let proposedRegimeLabel = self.proposedRegime.value ? self.proposedRegime.labelAlias : "LBL_NO_REGIME";

				$$("label-regimen-1-" + self.parent.uid).setValue('<div><span class="global-text-badge" style="background:#9c70303b; color: #9c7030a0;">' + i18n.t(actualRegimeLabel) + '</span></div>')
				$$("label-regimen-2-" + self.parent.uid).setValue('<div><span class="global-text-badge" style="background:#9c70303b; color: #9c7030a0;">' + i18n.t(proposedRegimeLabel) + '</span></div>')

			
				if (!self.illuminationModeValueParam || self.parent.config.illuminationModeOptions.find(option => option.id == self.illuminationModeValueParam.value).disable) {
						
					self.tubeCircuitsDataTable.disable();
					$$("view-content-button-change-regime-" + self.parent.uid).disable();
					$$("view-content-button-change-circuits-status-" + self.parent.uid).disable();
				} else {

					self.tubeCircuitsDataTable.enable();
					$$("view-content-button-change-regime-" + self.parent.uid).enable();
					$$("view-content-button-change-circuits-status-" + self.parent.uid).enable();
				}
			}
		}

		function onMessageCircuit(data) {

			if (data) {

			}
		}
	}

	disConnectWS() {

		let self = this;

		let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

		if (stompSubscriptorService) {

			if (self.idStompSubscriptorTunnel){

				stompSubscriptorService.unsubscribe(self.idStompSubscriptorTunnel);
			}
				
			if (self.idStompSubscriptorTube){

				stompSubscriptorService.unsubscribe(self.idStompSubscriptorTube);
			}

				
			if (self.idStompSubscriptorCircuits){

				stompSubscriptorService.unsubscribe(self.idStompSubscriptorCircuits);
			}
		}
	}
}
