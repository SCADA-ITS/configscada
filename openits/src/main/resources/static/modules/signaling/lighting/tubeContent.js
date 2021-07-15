import { util } from "../../../lib/util.js";
import DaliRegimeDialogBox from "./daliRegimeDialogBox.js";
import DataTable from "../../../component/datatable.js";
import { adapter } from "../../../lib/adapter.js";

const CIRCUIT_COMMAND_SEND_ON = "SEND_CIRCUIT_ON";
const CIRCUIT_COMMAND_SEND_OFF = "SEND_CIRCUIT_OFF";

const CircuitState = Object.freeze([
	{ id: 1, value: "0", label: "LBL_CIRCUIT_STATE_OFF" },
	{ id: 2, value: "1", label: "LBL_CIRCUIT_STATE_ON" }
]);

export default class TubeContent {

	constructor(tunnelId, tubeId, parent) {

		this.tunnelId = tunnelId;
		this.tubeId = tubeId;
		this.parent = parent;

		this.selectedTube = undefined;
		this.selectedTable = "action-panel-switcher-circuit" + this.parent.uid;
		this.lightingModeValueParam;

		this.tubeCircuits;
		this.tubeCircuitsDataTable;
		this.tubeDalis;
		this.tubeDalisDataTable;

		this.idStompSubscriptorTube = undefined;
		this.idStompSubscriptorCircuits = undefined;
		this.idStompSubscriptorDalis = undefined;
	}

	loadData(args, drawContent) {

		let self = this;

		webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.parent.config.restEndpoints.elementById, { id: self.tubeId })).then((data) => {

			self.selectedTube = data.json();

			if (args && args.tubeTableOption) {

				self.selectedTable = args.tubeTableOption;
			}

			loadDataCircuits();
			loadDataDalis();

			self.connectWS();

			drawContent(self.getView());

			if (this.tubeCircuitsDataTable) {

				this.tubeCircuitsDataTable.callbacks();

				if (args.selectedPks) {

					this.tubeCircuitsDataTable.select(args.selectedPks);
				}

				if (this.lightingModeValueParam && this.parent.config.lightingModeOptions.find(option => option.id == this.lightingModeValueParam.value).disable) {

					this.tubeCircuitsDataTable.disable();
				}
			}

			if (this.tubeDalisDataTable) {

				this.tubeDalisDataTable.callbacks();

				if (args.selectedPks) {

					this.tubeDalisDataTable.select(args.selectedPks);
				}

				if (this.lightingModeValueParam && this.parent.config.lightingModeOptions.find(option => option.id == this.lightingModeValueParam.value).disable) {

					this.tubeDalisDataTable.disable();
				}
			}

			function loadDataCircuits() {

				let circuits = self.selectedTube.childs.filter(element => element.elementType.id == self.parent.config.elementTypeIdCircuit);

				if (circuits && Array.isArray(circuits) && circuits.length > 0) {

					self.tubeCircuits = [];

					circuits.forEach(element => {

						let circuit = {};

						circuit.id = element.id;
						circuit.alias = element.alias;
						circuit.state = (element.elementTypeState) ? element.elementTypeState.id : null;

						if (element.elementValues && Array.isArray(element.elementValues) && element.elementValues.length > 0) {

							element.elementValues.forEach(elementValue => {

								circuit[elementValue.elementTypeParam.id] = elementValue.value;

								if (elementValue.elementTypeParam.id === self.parent.config.params.circuitState) {

									let circuitState = CircuitState.find(e => e.value == elementValue.value);

									if (circuitState) {

										circuit["circuitStateLabel"] = i18n.t(circuitState.label);
									}
								}
							});
						}

						self.tubeCircuits.push(circuit);
					});
				}
			}

			function loadDataDalis() {

				let dalis = self.selectedTube.childs.filter(element => element.elementType.id == self.parent.config.elementTypeIdDali);

				if (dalis && Array.isArray(dalis) && dalis.length > 0) {

					self.tubeDalis = [];

					dalis.forEach(element => {

						let dali = {};

						dali.id = element.id;
						dali.alias = element.alias;
						dali.state = (element.elementTypeState) ? element.elementTypeState.id : null;

						if (element.elementValues && Array.isArray(element.elementValues) && element.elementValues.length > 0) {

							element.elementValues.forEach(elementValue => {

								dali[elementValue.elementTypeParam.id] = elementValue.value;
							});
						}

						let daliState = dali[self.parent.config.params.daliState];
						let daliRegimeConfig = JSON.parse(dali[self.parent.config.params.daliRegimeConfig]);

						if (daliState && daliRegimeConfig && Array.isArray(daliRegimeConfig["regimes"])) {

							let daliRegime = daliRegimeConfig["regimes"].find(e => e.id == daliState);

							if (daliRegime) {

								dali["daliStateAlias"] = daliRegime.alias;
							}
						}

						self.tubeDalis.push(dali);
					});
				}
			}

		});
	}


	getView() {

		let viewContent = {
			css: { "background-color": "white" },
			rows: [
				this.getContentHeader(),
				{
					borderless: true,
					cols: [
						{ width: 15 },
						this.getContentToolbar(),
						{ width: 15 }
					]
				},
				{
					css: { "border-width": "0px !important" },
					type: "form",
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
		let tubeTypeState = undefined;

		if (self.selectedTube && Array.isArray(self.selectedTube.elementValues)) {

			let tubeStateParam = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.tubeState);

			if (tubeStateParam) {

				tubeTypeState = window.em.getByField(EntityType.ELEMENT_TYPE_STATE, "id", tubeStateParam.value);
			}
		}

		let header = {
			id: "view-content-header-" + self.parent.uid,
			rows: [
				{
					id: "view-content-header-bar-color-" + self.parent.uid,
					height: 15,
					css: { "background": util.element.getColor(tubeTypeState) }
				},
				{
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
											<span class="ims-incident-badge" style="background: ${util.element.getColor(tubeTypeState)}; color: white;">${i18n.t(tubeTypeState.labelAlias)}</span>
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

		self.lightingModeValueParam = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.lightingMode);

		let lightingModeOptions = [];

		self.parent.config.lightingModeOptions.forEach(option => {

			lightingModeOptions.push({
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
			value: self.lightingModeValueParam.value,
			width: 330,
			options: lightingModeOptions,
			tooltip: true,
			on: {
				onChange(newv, oldv) {

					webix.confirm({
						title: i18n.t("LBL_LIGHTING_CHANGE_MODE_CONFIRM"),
						ok: i18n.t("LBL_ACCEPT"),
						cancel: i18n.t("LBL_CANCEL"),
						text: i18n.t("LBL_LIGHTING_CHANGE_MODE_CONFIRM_DESCRIPTION"),
						width: 600
					}).then(function() {

						let command = self.parent.config.commands.SEND_LIGHTING_MODE;
						command.elementId = self.selectedTube.id;
						command.commandElementValues[0].value = newv;

						let body = {
							commands: []
						};
						body.commands.push(command);

						webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.parent.config.restEndpoints.command, body).then(function(data) {

							if (!data.json()) {

								webix.message({ type: "error", text: i18n.t("LBL_ERROR_OPERATION") });
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

	getContentBody() {

		let self = this;

		let contentBody = {
			//css: { "margin-top" : "0px !important" },
			view: "form",
			id: "view-content-body-" + self.parent.uid,
			rows: [
				getRegime(),
				getButtons(),
				{
					cols: [
						{
							id: "view-content-body-circuit-dt-" + self.parent.uid,
							rows: [getCircuitsTable()],
							hidden: (self.selectedTable != "action-panel-switcher-circuit" + self.parent.uid)
						},
						{
							id: "view-content-body-dali-dt-" + self.parent.uid,
							rows: [getDalisTable()],
							hidden: (self.selectedTable != "action-panel-switcher-dali" + self.parent.uid)
						}
					]
				},
				getBottom(),
			]
		}

		function getRegime() {

			let lightingRegimeConfig = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.lightingRegimeConfig);
			let availableRegimes;

			try {

				lightingRegimeConfig = JSON.parse(lightingRegimeConfig.value);

				availableRegimes = lightingRegimeConfig.regimes.map(function(regime) {
					return {
						id: regime.id,
						value: i18n.t(regime.labelAlias)
					};
				});

				updateInfoRegime(self.getCurrentRegime());

			} catch (error) {

				console.error("El Json de regimenes no esta correctamente formateado.")
			}

			let regime = {
				cols: [
					{
						rows: [
							{ height: 26 },
							{
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
								cols: [
									{
										view: "label",
										width: 180,
										height: 24,
										id: "label-regimen-1-" + self.parent.uid,
										template: '<div><span class="global-text-badge" style="background:#9c70303b; color: #9c7030a0;">' + i18n.t(self.getCurrentRegime() ? self.getCurrentRegime().labelAlias : "LBL_UNKNOWN") + '</span></div>',
										css: "global-text"
									},
									{ width: 12 },
									{
										view: "label",
										width: 180,
										height: 24,
										id: "label-regimen-2-" + self.parent.uid,
										template: '<div><span class="global-text-badge" style="background:#9c70303b; color: #9c7030a0;">' + i18n.t(self.getProposedRegime() ? self.getProposedRegime().labelAlias : "LBL_UNKNOWN") + '</span></div>',
										css: "global-text"
									},
									{ width: 12 },
									{
										id: "label-regimen-3-" + self.parent.uid,
										width: 180,
										height: 28,
										view: "combo",
										// css: {"margin-top": "-5px"},
										value: self.getCurrentRegime() ? self.getCurrentRegime().id : undefined,
										options: availableRegimes,
										on: {
											onChange(newv, oldv) {

												updateInfoRegime(self.getLightingRegime(newv));

												//updateProposedRegime(newv, lightingRegimeConfig.regimes);
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
					{ width: 20 },
					{
						rows: [
							getViewDocument(),
							getViewDocumentText(),
							{ height: 12 },
							{
								cols: [
									{},
									{
										id: "view-content-button-change-regime-" + self.parent.uid,
										width: 130,
										view: "button",
										align: "right",
										css: "button-cta",
										value: i18n.t("LBL_CHANGE_REGIME"),
										disabled: self.parent.config.lightingModeOptions.find(option => option.id == self.lightingModeValueParam.value).disable,
										click: function() {

											let command = self.parent.config.commands.SEND_LIGHTING_REGIME;
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
													title: i18n.t("LBL_SEND_LIGHTING_REGIME_CONFIRM"),
													ok: i18n.t("LBL_ACCEPT"),
													cancel: i18n.t("LBL_CANCEL"),
													text: i18n.t("LBL_SEND_LIGHTING_REGIME_CONFIRM_DESCRIPTION"),
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

				if (self.infoRegime.documentUrl) {

					viewDocument = {
						id: "view-content-regime-document-" + self.parent.uid,
						cols: [
							{},
							{
								height: 26,
								align: "right",
								width: 500,
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
					id: "view-content-regime-document-text-" + self.parent.uid,
					height: 60,
					//width: 500,
					borderless: true,
					view: "template",
					template: `<div class="ventilation-description-box"><span > ${self.infoRegime.description}</span></div>`
				};
			}

			function updateInfoRegime(lightingRegime) {

				self.infoRegime = {};

				if (lightingRegime) {

					self.infoRegime.labelAlias = lightingRegime.labelAlias;
					self.infoRegime.description = lightingRegime.description;
					self.infoRegime.documentUrl = lightingRegime.documentUrl

					if (lightingRegime.documentUrl) {

						self.infoRegime.documentName = lightingRegime.documentUrl.substring(lightingRegime.documentUrl.lastIndexOf("/") + 1, lightingRegime.documentUrl.lastIndexOf("."));
						self.infoRegime.documentType = lightingRegime.documentUrl.substring(lightingRegime.documentUrl.lastIndexOf(".") + 1, lightingRegime.documentUrl.length);
					}
				} else {

					self.infoRegime.labelAlias = "LBL_NO_REGIME";
					self.infoRegime.description = "";
				}
			}

			return regime;
		}

		function getButtons() {

			let buttons = {
				cols: [
					{
						view: "segmented",
						css: "segment-custom-2",
						width: 300,
						value: self.selectedTable,
						tooltip: true,
						options: [
							{
								id: "action-panel-switcher-circuit" + self.parent.uid,
								value: "<span>" + i18n.t("LBL_CIRCUIT") + "</span>",
							},
							{
								id: "action-panel-switcher-dali" + self.parent.uid,
								value: "<span>" + i18n.t("LBL_DALI") + "</span>",
							},
							// {
							// 	id: "action-panel-switcher-tube" + self.parent.uid,
							// 	value: "<span>" + i18n.t("LBL_TUBE") + "</span>",
							// }
						],
						on: {
							onChange(newv, oldv) {

								self.selectedTable = newv;

								if (newv === "action-panel-switcher-circuit" + self.parent.uid) {

									$$("view-content-body-dali-dt-" + self.parent.uid).hide();
									$$("view-content-body-circuit-dt-" + self.parent.uid).show();
									// $$("view-content-button-change-circuits-status-" + self.parent.uid).enable();
									$$("view-content-button-change-circuits-status-" + self.parent.uid).show();
								}
								else if (newv === "action-panel-switcher-dali" + self.parent.uid) {

									$$("view-content-body-circuit-dt-" + self.parent.uid).hide();
									$$("view-content-body-dali-dt-" + self.parent.uid).show();
									// $$("view-content-button-change-circuits-status-" + self.parent.uid).disable();
									$$("view-content-button-change-circuits-status-" + self.parent.uid).hide();
								}
								// else if (newv === "action-panel-switcher-tube" + self.parent.uid) {

								// 	webix.message("show-tube");
								// }
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
								value: "ElementTypeState:84:1",
								labelValue: "LBL_ELEMENT_TYPE_STATE_UP"
							},
							{
								icon: "./img/state/stateDown.png",
								showValue: false,
								value: "ElementTypeState:84:2",
								labelValue: "LBL_ELEMENT_TYPE_STATE_DOWN"
							},
							{
								icon: "./img/state/stateAlarm.png",
								showValue: false,
								value: "ElementTypeState:84:3",
								labelValue: "LBL_ELEMENT_TYPE_STATE_ALARMS"
							}
						]
					},
					{
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_CIRCUIT_ALIAS",
						labelTooltip: "LBL_CIRCUIT_ALIAS",
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
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "",
						labelTooltip: "LBL_CIRCUIT_STATE",
						parameterized: false,
						position: 4,
						required: true,
						sourceField: self.parent.config.params.circuitState,
						visible: true,
						widthPx: 1,
						adjust: false,
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
						label: "LBL_CIRCUIT_STATE",
						labelTooltip: "LBL_CIRCUIT_STATE",
						parameterized: false,
						position: 5,
						required: true,
						sourceField: "circuitStateLabel",
						visible: true,
						widthPx: 250,
						adjust: false,
						gridFieldDataType: {
							id: 1,
							name: "string"
						},
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
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						parameterized: false,
						position: 6,
						required: false,
						sourceField: "circuitStateToSend",
						visible: false,
						widthPx: 1,
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
						label: "LBL_CIRCUIT_SEND_STATUS",
						labelTooltip: "LBL_CIRCUIT_SEND_STATUS",
						parameterized: false,
						position: 7,
						required: true,
						sourceField: "circuitStateToSendAlias",
						visible: true,
						widthPx: 400,
						adjust: false,
						gridFieldDataType: {
							id: 1,
							name: "string"
						}
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
					}
				]

			};

			let events = {};
			events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];

			events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
				commandId: 1,
				function: () => {

					self.tubeCircuitsDataTable.getSelectedItems().forEach(item => {

						item.circuitStateToSend = CIRCUIT_COMMAND_SEND_ON;
						item.circuitStateToSendAlias = i18n.t("LBL_CIRCUIT_COMMAND_SEND_ON");
						self.tubeCircuitsDataTable.update(item);
					});
				}
			});

			events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
				commandId: 2,
				function: () => {

					self.tubeCircuitsDataTable.getSelectedItems().forEach(item => {

						item.circuitStateToSend = CIRCUIT_COMMAND_SEND_OFF;
						item.circuitStateToSendAlias = i18n.t("LBL_CIRCUIT_COMMAND_SEND_OFF");
						self.tubeCircuitsDataTable.update(item);
					});
				}
			});

			events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
				commandId: 3,
				function: () => {

					self.tubeCircuitsDataTable.getSelectedItems().forEach(item => {

						item.circuitStateToSend = null;
						item.circuitStateToSendAlias = "-";
						self.tubeCircuitsDataTable.update(item);
					});
				}
			});

			self.tubeCircuitsDataTable = new DataTable(null, null, metadata, data, events);

			return { rows: self.tubeCircuitsDataTable.getView() };
		};

		function getDalisTable() {

			let data = self.tubeDalis;

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
								value: "ElementTypeState:85:1",
								labelValue: "LBL_ELEMENT_TYPE_STATE_UP"
							},
							{
								icon: "./img/state/stateDown.png",
								showValue: false,
								value: "ElementTypeState:85:2",
								labelValue: "LBL_ELEMENT_TYPE_STATE_DOWN"
							},
							{
								icon: "./img/state/stateAlarm.png",
								showValue: false,
								value: "ElementTypeState:85:3",
								labelValue: "LBL_ELEMENT_TYPE_STATE_ALARMS"
							}
						]
					},
					{
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
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						parameterized: false,
						position: 4,
						required: true,
						sourceField: self.parent.config.params.daliState,
						visible: false,
						widthPx: 1,
						adjust: false,
						gridFieldDataType: {
							id: 1,
							name: "string"
						},
						gridFieldIcons: []
					},
					{
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_DALI_REGIME",
						labelTooltip: "LBL_DALI_REGIME",
						parameterized: false,
						position: 5,
						required: true,
						sourceField: "daliStateAlias",
						visible: true,
						widthPx: 250,
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
						multiselect: false,
						position: 1,
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

					let dali = self.tubeDalis.find(dali => dali.id == self.tubeDalisDataTable.getSelectedItems()[0].id);

					let daliRegimeDialogBox = new DaliRegimeDialogBox(dali, self.parent)
					daliRegimeDialogBox.show();
				}
			});

			self.tubeDalisDataTable = new DataTable(null, null, metadata, data, events);

			//return {height: 338,rows:self.tubeDalisDataTable.getView()};
			return { rows: self.tubeDalisDataTable.getView() };
		};

		function getBottom() {

			let bottom = {
				height: 40,
				cols: [
					{},
					{
						id: "view-content-button-change-circuits-status-" + self.parent.uid,
						width: 130,
						view: "button",
						align: "right",
						css: "button-cta",
						value: i18n.t("LBL_LIGHTING_CHANGE_STATUS"),
						disabled: self.parent.config.lightingModeOptions.find(option => option.id == self.lightingModeValueParam.value).disable,
						click: function() {

							webix.confirm({
								title: i18n.t("LBL_LIGHTING_CHANGE_STATUS_CONFIRM"),
								ok: i18n.t("LBL_ACCEPT"),
								cancel: i18n.t("LBL_CANCEL"),
								text: i18n.t("LBL_LIGHTING_CHANGE_STATUS_CONFIRM_DESCRIPTION"),
								width: 600
							}).then(function() {

								let allTableItems = self.tubeCircuitsDataTable.getAllItems();

								let circuitsOnCommand = [];
								let circuitsOffCommand = [];

								allTableItems.forEach(row => {

									if (row.circuitStateToSend == CIRCUIT_COMMAND_SEND_ON) {

										circuitsOnCommand.push(row.id);

									} else if (row.circuitStateToSend == CIRCUIT_COMMAND_SEND_OFF) {

										circuitsOffCommand.push(row.id);

									}
								});

								if (circuitsOnCommand.length > 0) {

									executeCircuitCommand(CIRCUIT_COMMAND_SEND_ON, circuitsOnCommand);
								}

								if (circuitsOffCommand.length > 0) {

									executeCircuitCommand(CIRCUIT_COMMAND_SEND_OFF, circuitsOffCommand);
								}

								function executeCircuitCommand(commandAction, values) {

									let command = self.parent.config.commands[commandAction];
									let value = "[";
									command.elementId = self.selectedTube.id;

									for (var i = 0; i < values.length; i++) {

										if (i > 0) {
											value += ", ";
										}
										value += values[i];
									}

									value += "]";
									command.commandElementValues[0].value = value;

									let body = {
										commands: []
									};
									body.commands.push(command);

									webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.parent.config.restEndpoints.command, body).then(function(data) {

										self.tubeCircuitsDataTable.getAllItems().forEach(row => {

											row.circuitStateToSend = null;
											row.circuitStateToSendAlias = null;
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
			}

			if (Array.isArray(self.tubeCircuits) && self.tubeCircuits.length > 0) {

				let fields = [];
				fields.push({
					target: "id",
					values: []
				});
				self.tubeCircuits.forEach(element => fields[0].values.push(element.id));

				self.idStompSubscriptorCircuits = stompSubscriptorService.subscribe(util.stomp.getDataSourceFromTopic(self.parent.config.topics.circuits), onMessageCircuit, fields);
			}

			if (Array.isArray(self.tubeDalis) && self.tubeDalis.length > 0) {

				let fields = [];
				fields.push({
					target: "id",
					values: []
				});
				self.tubeDalis.forEach(element => fields[0].values.push(element.id));

				self.idStompSubscriptorDalis = stompSubscriptorService.subscribe(util.stomp.getDataSourceFromTopic(self.parent.config.topics.dalis), onMessageDali, fields);
			}
		}

		//		function onMessageTube(data) {
		//
		//			if (data) {
		//
		//				self.selectedTube = eval("adapter." + self.parent.config.adapters.element)(self.selectedTube, data);
		//				self.currentRegime = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.currentRegime);
		//				self.proposedRegime = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.proposedRegime);
		//				self.lightingModeValueParam = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.lightingMode);
		//
		//				webix.ui(self.getContentHeader(), $$("view-content-header-" + self.parent.uid));
		//				webix.ui(self.getContentToolbar(), $$("view-content-toolbar-" + self.parent.uid));
		//				
		//				let actualRegimeLabel = self.currentRegime.value ? self.currentRegime.labelAlias : "LBL_NO_REGIME";
		//				let proposedRegimeLabel = self.proposedRegime.value ? self.proposedRegime.labelAlias : "LBL_NO_REGIME";
		//
		//				$$("label-regimen-1-" + self.parent.uid).setValue('<div><span class="global-text-badge" style="background:#9c70303b; color: #9c7030a0;">' + i18n.t(actualRegimeLabel) + '</span></div>')
		//				$$("label-regimen-2-" + self.parent.uid).setValue('<div><span class="global-text-badge" style="background:#9c70303b; color: #9c7030a0;">' + i18n.t(proposedRegimeLabel) + '</span></div>')
		//
		//			
		//				if (!self.lightingModeValueParam || self.parent.config.lightingModeOptions.find(option => option.id == self.lightingModeValueParam.value).disable) {
		//						
		//					self.tubeCircuitsDataTable.disable();
		//					self.tubeDalisDataTable.disable();
		//					$$("view-content-button-change-regime-" + self.parent.uid).disable();
		//					$$("view-content-button-change-circuits-status-" + self.parent.uid).disable();
		//				} else {
		//
		//					self.tubeCircuitsDataTable.enable();
		//					self.tubeDalisDataTable.enable();
		//					$$("view-content-button-change-regime-" + self.parent.uid).enable();
		//					$$("view-content-button-change-circuits-status-" + self.parent.uid).enable();
		//				}
		//			}
		//		}

		function onMessageTube(data) {

			if (data) {

				self.selectedTube = eval("adapter." + self.parent.config.adapters.element)(self.selectedTube, data);
				let lightingMode = self.getLightingMode();

				webix.ui(self.getContentHeader(), $$("view-content-header-" + self.parent.uid));
				webix.ui(self.getContentToolbar(), $$("view-content-toolbar-" + self.parent.uid));

				$$("label-regimen-1-" + self.parent.uid).setValue('<div><span class="global-text-badge" style="background:#9c70303b; color: #9c7030a0;">' + i18n.t(self.getCurrentRegime() ? self.getCurrentRegime().labelAlias : "LBL_UNKNOWN") + '</span></div>')
				$$("label-regimen-2-" + self.parent.uid).setValue('<div><span class="global-text-badge" style="background:#9c70303b; color: #9c7030a0;">' + i18n.t(self.getProposedRegime() ? self.getProposedRegime().labelAlias : "LBL_UNKNOWN") + '</span></div>')

				if (!lightingMode || self.parent.config.lightingModeOptions.find(option => option.id == lightingMode).disable) {

					self.tubeCircuitsDataTable.disable();
					self.tubeDalisDataTable.disable();
					$$("view-content-button-change-regime-" + self.parent.uid).disable();
					$$("view-content-button-change-circuits-status-" + self.parent.uid).disable();

				} else {

					self.tubeCircuitsDataTable.enable();
					self.tubeDalisDataTable.enable();
					$$("view-content-button-change-regime-" + self.parent.uid).enable();
					$$("view-content-button-change-circuits-status-" + self.parent.uid).enable();
				}
			}
		}

		function onMessageCircuit(data) {

			let oldItemData = self.tubeCircuitsDataTable.getItem(data.id);
			let newItemData = undefined;

			if (oldItemData) {

				if (data.elementTypeState) {

					newItemData = webix.copy(oldItemData);
					newItemData.state = data.elementTypeState.id;
				}

				if (Array.isArray(data.elementValues) && data.elementValues.length > 0) {

					let circuitState = data.elementValues.find(e => util.element.getElementTypeParamId(e.id) == self.parent.config.params.circuitState);

					if (circuitState && circuitState.value) {

						if (oldItemData[self.parent.config.params.circuitState] != circuitState.value) {

							if (!newItemData) {

								newItemData = webix.copy(oldItemData);
							}

							newItemData[self.parent.config.params.circuitState] = circuitState.value;

							let circuitStateLabel = CircuitState.find(e => e.value == circuitState.value);

							if (circuitStateLabel) {

								newItemData["circuitStateLabel"] = i18n.t(circuitStateLabel.label);
							}
						}
					}
				}

				if (newItemData) {

					self.tubeCircuitsDataTable.update(newItemData);
				}
			}
		}

		function onMessageDali(data) {

			let oldItemData = self.tubeDalisDataTable.getItem(data.id);
			let newItemData = undefined;

			if (oldItemData) {

				if (data.elementTypeState) {

					newItemData = webix.copy(oldItemData);
					newItemData.state = data.elementTypeState.id;
				}

				if (Array.isArray(data.elementValues) && data.elementValues.length > 0) {

					let daliState = data.elementValues.find(e => util.element.getElementTypeParamId(e.id) == self.parent.config.params.daliState);

					if (daliState && daliState.value) {

						if (oldItemData[self.parent.config.params.daliState] != daliState.value) {

							if (!newItemData) {

								newItemData = webix.copy(oldItemData);
							}

							newItemData[self.parent.config.params.daliState] = daliState.value;

							if (Array.isArray(self.tubeDalis)) {

								let tubeDali = self.tubeDalis.find(dali => dali.id == data.id)

								if (tubeDali) {

									let daliRegimeConfig = JSON.parse(tubeDali[self.parent.config.params.daliRegimeConfig]);

									if (daliRegimeConfig && Array.isArray(daliRegimeConfig["regimes"])) {

										let daliRegime = daliRegimeConfig["regimes"].find(e => e.id == daliState.value);

										if (daliRegime) {

											newItemData["daliStateAlias"] = daliRegime.alias;
										}
									}

								}
							}
						}
					}
				}

				if (newItemData) {
					
					self.tubeDalisDataTable.update(newItemData);
				}
			}
		}
	}

	disConnectWS() {

		let self = this;

		let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

		if (stompSubscriptorService) {

			if (self.idStompSubscriptorTube) {

				stompSubscriptorService.unsubscribe(self.idStompSubscriptorTube);
			}


			if (self.idStompSubscriptorCircuits) {

				stompSubscriptorService.unsubscribe(self.idStompSubscriptorCircuits);
			}

			if (self.idStompSubscriptorDalis) {

				stompSubscriptorService.unsubscribe(self.idStompSubscriptorDalis);
			}
		}
	}

	getLightingRegime(regimeId) {

		let lightingRegime = undefined;

		if (regimeId) {

			let lightingRegimesConfig = this.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == this.parent.config.params.lightingRegimeConfig);

			if (lightingRegimesConfig && lightingRegimesConfig.value) {

				lightingRegimesConfig = JSON.parse(lightingRegimesConfig.value);

				if (lightingRegimesConfig && Array.isArray(lightingRegimesConfig.regimes) && lightingRegimesConfig.regimes.length > 0) {

					lightingRegime = lightingRegimesConfig.regimes.find(e => e.id == regimeId);
				}

			}
		}

		return lightingRegime;
	}

	getCurrentRegime() {

		let currentRegime = this.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == this.parent.config.params.currentRegime);

		return currentRegime && currentRegime.value ? this.getLightingRegime(currentRegime.value) : undefined;
	}

	getProposedRegime() {

		let proposedRegime = this.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == this.parent.config.params.proposedRegime);

		return proposedRegime && proposedRegime.value ? this.getLightingRegime(proposedRegime.value) : undefined;
	}

	getLightingMode() {

		let lightingMode = this.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == this.parent.config.params.lightingMode);

		return lightingMode ? lightingMode.value : undefined;
	}
}
