import { util } from "../../../lib/util.js";
import { adapter } from "../../../lib/adapter.js";
import DataTable from "../../../component/datatable.js";
import OptionDialogBox from "../../../component/optionDialogBox.js";
import MessageBox from "../../../component/messageBox.js";
import EquipmentMeasures from "../../../component/equipmentMeasures.js";

const CIRCUIT_COMMAND_SEND_ON = "SEND_CIRCUIT_ON";
const CIRCUIT_COMMAND_SEND_OFF = "SEND_CIRCUIT_OFF";

const CircuitState = Object.freeze([
	{ id: 1, value: "false", label: "LBL_CIRCUIT_STATE_OFF" },
	{ id: 2, value: "true", label: "LBL_CIRCUIT_STATE_ON" }
]);

export default class TubeContent {

	constructor(tunnelId, tubeId, parent) {

		this.tunnelId = tunnelId;
		this.tubeId = tubeId;
		this.parent = parent;

		this.selectedTube = undefined;
		this.selectedTable = "action-panel-switcher-circuit" + this.parent.uid;
		this.lightingModeValueParam = undefined;

		this.circuitsGrid = undefined;
		this.tubeCircuits = undefined;
		this.tubeCircuitsDataTable = undefined;
		this.tubeDalis = undefined;
		this.tubeDalisDataTable = undefined;
		this.restTubeEquipments = undefined;
		this.equipmentMeasures = undefined;

		this.idStompSubscriptorTube = undefined;
		this.idStompSubscriptorCircuits = undefined;
		this.idStompSubscriptorDalis = undefined;
	}

	getView() {

		let viewContent;

		viewContent = {
			css: { "background-color": "white" },
			rows: [
				{ height: 6 },
				this.getContentHeader(),
				{ height: 12 },
				{
					cols: [
						{ width: 3 },
						this.getContentToolbar(),
						{ width: 3 }
					]
				},
				{
					cols: [
						{ width: 3 },
						this.getContentBody(),
						{ width: 3 }
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

	loadData(args, drawContent) {

		let self = this;

		webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.parent.config.restEndpoints.elementById, { id: self.tubeId })).then((data) => {

			self.selectedTube = data.json();

			if (args && args.tubeTableOption) {

				self.selectedTable = args.tubeTableOption;
			}

			webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.parent.config.restEndpoints.grid, { id: self.parent.config.gridId })).then(function(dataGrid) {

				if (dataGrid != null) {

					self.circuitsGrid = dataGrid.json();

					if (self.selectedTube.childs && Array.isArray(self.selectedTube.childs)) {

						loadDataCircuits();
						loadDataDalis();

						self.restTubeEquipments = self.selectedTube.childs.filter(element => (element.elementType.id != self.parent.config.elementTypeIdCircuit) && (element.elementType.id != self.parent.config.elementTypeIdDali));
					}

					self.connectWS();

					drawContent(self.getView());

					if (self.tubeCircuitsDataTable) {

						self.tubeCircuitsDataTable.callbacks();

						if (args.selectedPks) {

							self.tubeCircuitsDataTable.select(args.selectedPks);
						}

						if (self.lightingModeValueParam && self.parent.config.lightingModeOptions.find(option => option.id == self.lightingModeValueParam.value).disable) {

							self.enableGridButtons(self.tubeCircuitsDataTable, false);
						}
						else {

							self.enableGridButtons(self.tubeCircuitsDataTable, true);
						}
					}

					if (self.tubeDalisDataTable) {

						self.tubeDalisDataTable.callbacks();

						if (args.selectedPks) {

							self.tubeDalisDataTable.select(args.selectedPks);
						}

						if (self.lightingModeValueParam && self.parent.config.lightingModeOptions.find(option => option.id == self.lightingModeValueParam.value).disable) {

							self.enableGridButtons(self.tubeDalisDataTable, false);
						}
						else {

							self.enableGridButtons(self.tubeDalisDataTable, true);
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

											let circuitStateLabel = CircuitState.find(e => e.value == elementValue.value);

											if (circuitStateLabel) {

												circuit["circuitStateLabel"] = i18n.t(circuitStateLabel.label);
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

					if (self.equipmentMeasures) {

						self.equipmentMeasures.loadData();
					}
				}
			});
		});
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

		let header;

		header = {
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
			css: { "box-sizing": "border-box" },
			elements: [],
			borderless: true
		}

		let actionPanelSwitcher = {
			view: "segmented",
			css: "segment-custom-2",
			value: self.lightingModeValueParam.value,
			disabled: (self.parent.args && self.parent.args.readOnly === true) || !self.parent.commandEquipmentPermission,
			width: 500,
			options: lightingModeOptions,
			tooltip: true,
			on: {
				onChange(newv, oldv) {

					this.blockEvent();
					this.setValue(oldv);
					this.unblockEvent();

					let options = [];

					for (let modeOption of self.parent.config.lightingModeOptions) {

						let newSelection = modeOption.id === newv ? true : false;

						options.push({
							id: modeOption.id,
							label: i18n.t(modeOption.label),
							icon: modeOption.icon,
							description: i18n.t(modeOption.label + "_LIGHTING_DESCRIPTION"),
							default: newSelection
						});
					}

					let optionDialog = new OptionDialogBox(i18n.t("LBL_SELECT_ANY_OPTION"), options, changeMode, OptionDialogBox.MODE_TOGGLE);
					optionDialog.show(oldv);


					function changeMode(newModeId, callbackCloseDialog) {

						let ms = new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_LIGHTING_CHANGE_OPTION_CONFIRM_DESCRIPTION"), null, onAccept, null);
						ms.show();

						function onAccept() {

							callbackCloseDialog(true);
							let command = self.parent.config.commands.SEND_LIGHTING_MODE;
							command.elementId = self.selectedTube.id;
							command.commandElementValues[0].value = newModeId;

							util.element.sendCommand([command], function(res) {

								if (res) {

									(new MessageBox(MessageBox.INFO, i18n.t("LBL_COMMAND_SEND_SUCCESSFULLY"), null, null, null)).show();
								}

							});
						}
					}
				}
			}
		};

		viewContentToolbar.elements.push({});
		viewContentToolbar.elements.push(actionPanelSwitcher);

		return viewContentToolbar;
	}

	getContentBody() {

		let self = this;
		let contentBody;

		contentBody = {
			view: "form",
			id: "view-content-body-" + self.parent.uid,
			css: { "box-sizing": "border-box", "border": "1px solid #dadee0 !important" },
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

			let lightingRegimeConfig = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.lightingRegimeConfig);
			let availableRegimes;
			let regime;

			if (lightingRegimeConfig) {

				try {

					lightingRegimeConfig = JSON.parse(lightingRegimeConfig.value);

					availableRegimes = lightingRegimeConfig.regimes.map(function(regime) {
						return {
							id: regime.id.toString(),
							value: i18n.t(regime.labelAlias)
						};
					});

					updateInfoRegime(self.getCurrentRegime());

				} catch (error) {

					console.error("El Json de regimenes no esta correctamente formateado.")
				}
			}

			regime = {
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
										id: "label-regimen-1-" + self.parent.uid,
										template: '<div><span class="global-text-badge" style="background:#9c70303b; color: #9c7030a0;">' + i18n.t(self.getCurrentRegime() ? self.getCurrentRegime().labelAlias : "LBL_UNKNOWN") + '</span></div>',
										css: "global-text"
									},
									{ width: 12 },
									{
										view: "label",
										//width: 180,
										height: 24,
										id: "label-regimen-2-" + self.parent.uid,
										template: '<div><span class="global-text-badge" style="background:#9c70303b; color: #9c7030a0;">' + i18n.t(self.getProposedRegime() ? self.getProposedRegime().labelAlias : "LBL_UNKNOWN") + '</span></div>',
										css: "global-text"
									},
									{ width: 12 },
									{
										id: "label-regimen-3-" + self.parent.uid,
										//width: 180,
										height: 28,
										view: "richselect",
										value: self.getCurrentRegime() ? self.getCurrentRegime().id : undefined,
										options: availableRegimes,
										disabled: !self.parent.commandEquipmentPermission,
										on: {
											onChange(newv, oldv) {

												updateInfoRegime(self.getLightingRegime(newv));

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
					{ width: 50 },
					{
						rows: [
							getViewDocument(),
							getViewDocumentText(),
							{ height: 12 },
							{
								cols: [
									{},
									getButtonChangeRegime()
								]
							}
						]
					}
				]
			};

			function getButtonChangeRegime() {

				let buttonChangeRegime = { height: 38 };

				if (self.parent.commandEquipmentPermission) {

					buttonChangeRegime = {
						id: "view-content-button-change-regime-" + self.parent.uid,
						width: 130,
						view: "button",
						align: "right",
						css: "button-cta",
						value: i18n.t("LBL_CHANGE_REGIME"),
						disabled: self.parent.config.lightingModeOptions.find(option => option.id == self.lightingModeValueParam.value).disable || (self.parent.args && self.parent.args.readOnly === true),
						click: function() {

							let command = self.parent.config.commands.SEND_LIGHTING_REGIME;
							command.elementId = self.tubeId;
							command.commandElementValues[0].value = $$("label-regimen-3-" + self.parent.uid).getValue();

							if (!command.commandElementValues[0].value || command.commandElementValues[0].value === 0) {

								let ms = new MessageBox(MessageBox.WARN, i18n.t("LBL_NO_REGIME_SELECTED_DESCRIPTION"), null, null, null);
								ms.show();

							} else {

								util.element.sendCommand([command], function(res) {

									if (res) {

										(new MessageBox(MessageBox.INFO, i18n.t("LBL_COMMAND_SEND_SUCCESSFULLY"), null, null, null)).show();
									}
								}, i18n.t("LBL_SEND_LIGHTING_REGIME_CONFIRM_DESCRIPTION"));
							}
						}
					};
				}

				return buttonChangeRegime;
			}

			function getViewDocument() {

				let viewDocument;

				viewDocument = {
					id: "view-content-regime-document-" + self.parent.uid,
					height: 26
				};

				if (self.infoRegime.documentUrl) {

					viewDocument = {
						id: "view-content-regime-document-" + self.parent.uid,
						cols: [
							{
								height: 26,
								align: "right",
								//width: 500,
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

				let viewDocumentText;

				viewDocumentText = {
					id: "view-content-regime-document-text-" + self.parent.uid,
					height: 60,
					//width: 500,
					borderless: true,
					view: "template",
					template: `<div class="ventilation-description-box"><span > ${self.infoRegime.description}</span></div>`
				};

				return viewDocumentText;
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

		function getContent() {

			let viewContent = {};

			viewContent = {
				view: "tabview",
				id: "content-" + self.parent.uid,
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

			viewContent.cells.push(
				{
					header: i18n.t("LBL_CIRCUITS"),
					body: {
						rows: getCircuitsTable()
					}
				}
			);

			if (self.parent.config.viewEquipmentMeasures && self.parent.config.viewEquipmentMeasures.enabled === true) {

				let elementsId = [];

				for (let equipment of self.restTubeEquipments) {

					let isIncluded = self.parent.config.viewEquipmentMeasures.elementTypes.find(elementType => elementType === equipment.elementType.id);

					if (isIncluded) {

						elementsId.push(equipment.id);
					}
				}

				if (Array.isArray(elementsId) && elementsId.length > 0) {

					self.equipmentMeasures = new EquipmentMeasures(elementsId, null, self.parent.config.viewEquipmentMeasures.options);

					viewContent.cells.push({
						header: i18n.t("LBL_OTHERS"),
						body: self.equipmentMeasures.getView()
					});
				}
			}

			return viewContent;
		}

		function getCircuitsTable() {

			let viewCircuits;

			let data = self.tubeCircuits;

			if (Array.isArray(data) && data.length > 0) {

				let metadata = webix.copy(self.circuitsGrid.metadata);

				metadata.gridCommands.push({
					id: 1,
					default_: true,
					icon: "mdi mdi-information-outline",
					label: "LBL_INFO",
					multiselect: false,
					position: 1,
					showText: false,
					itemRequired: true
				});
				
				if (self.parent.commandEquipmentPermission) {

					metadata.gridCommands.push(
						{
							id: 2,
							default_: false,
							icon: "mdi mdi-toggle-switch",
							label: "LBL_SWITCH_ON",
							multiselect: true,
							position: 2,
							showText: true,
							itemRequired: true
						},
						{
							id: 3,
							default_: false,
							icon: "mdi mdi-toggle-switch-off",
							label: "LBL_SWITCH_OFF",
							multiselect: true,
							position: 3,
							showText: true,
							itemRequired: true
						},
						{
							id: 4,
							default_: false,
							icon: "mdi mdi-broom",
							label: "LBL_CLEAN",
							multiselect: true,
							position: 4,
							showText: false,
							itemRequired: true
						}
					);
				}

				let events = {};
				events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];

				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 2,
					function: () => {

						self.tubeCircuitsDataTable.getSelectedItems().forEach(item => {

							item.circuitStateToSend = CIRCUIT_COMMAND_SEND_ON;
							item.circuitStateToSendAlias = i18n.t("LBL_CIRCUIT_COMMAND_SEND_ON");
							self.tubeCircuitsDataTable.update(item);
						});

						self.checkDisableChangeStatusButton();
					}
				});

				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 3,
					function: () => {

						self.tubeCircuitsDataTable.getSelectedItems().forEach(item => {

							item.circuitStateToSend = CIRCUIT_COMMAND_SEND_OFF;
							item.circuitStateToSendAlias = i18n.t("LBL_CIRCUIT_COMMAND_SEND_OFF");
							self.tubeCircuitsDataTable.update(item);
						});

						self.checkDisableChangeStatusButton();
					}
				});

				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 4,
					function: () => {

						self.tubeCircuitsDataTable.getSelectedItems().forEach(item => {

							item.circuitStateToSend = null;
							item.circuitStateToSendAlias = "-";
							self.tubeCircuitsDataTable.update(item);
						});

						self.checkDisableChangeStatusButton();
					}
				});

				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 1,
					function: () => {

						let selectedItems = self.tubeCircuitsDataTable.getSelectedItems();

						if (Array.isArray(selectedItems) && selectedItems.length == 1) {

							let selectedItem = selectedItems[0];

							if (selectedItem) {

								let module = util.module.getFromModuleAction(self.parent.config.equipmentViewer);

								if (module) {

									let moduleAction = { id: self.parent.config.equipmentViewer };
									let args = { selectedPks: [selectedItem.id] };

									util.module.open(module, moduleAction, VIEW_TYPE_WINDOW, null, args);
								}
							}
						}
					}
				});

				self.tubeCircuitsDataTable = new DataTable(null, null, metadata, data, events);

				viewCircuits = self.tubeCircuitsDataTable.getView();
			}
			else {

				viewCircuits = [util.ui.getViewNoData()];
			}

			return viewCircuits;
		}

		function getDalisTable() {

			let viewDalis;
			let data = self.tubeDalis;

			if (Array.isArray(data) && data.length > 0) {

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
									icon: "./img/state/stateUnknown.png",
									showValue: false,
									value: "ElementTypeState:85:0",
									labelValue: "LBL_ELEMENT_TYPE_STATE_UNKNOWN"
								},
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
							label: "LBL_DALI",
							labelTooltip: "LBL_DALI",
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
							default_: true,
							icon: "mdi mdi-information-outline",
							label: "LBL_INFO",
							multiselect: false,
							position: 1,
							showText: false,
							itemRequired: true
						}
					]
				};

				if (self.parent.commandEquipmentPermission) {

					metadata.gridCommands.push(
						{
							id: 2,
							default_: false,
							icon: "mdi mdi-pen",
							label: "LBL_CHANGE_REGIME",
							multiselect: false,
							position: 2,
							showText: true,
							itemRequired: true
						}
					);
				}

				let events = {};
				events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];

				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 2,
					function: () => {

						let options = [];
						let selectedItem = self.tubeDalisDataTable.getSelectedItems()[0];
						let dali = self.tubeDalis.find(d => d.id == selectedItem.id);

						let daliRegimeConfig = dali[self.parent.config.params.daliRegimeConfig];
						daliRegimeConfig = JSON.parse(daliRegimeConfig);

						for (let regime of daliRegimeConfig.regimes) {

							options.push({
								id: regime.id,
								label: regime.alias,
								description: i18n.t("LBL_MODE_" + regime.alias.toUpperCase() + "_DESCRIPTION"),
								default: regime.id == selectedItem[self.parent.config.params.daliState]
							});
						}

						let optionDialog = new OptionDialogBox(i18n.t("LBL_SELECT_ANY_OPTION"), options, changeRegime);
						optionDialog.show();
					}
				});

				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 1,
					function: () => {

						let selectedItems = self.tubeDalisDataTable.getSelectedItems();

						if (Array.isArray(selectedItems) && selectedItems.length == 1) {

							let selectedItem = selectedItems[0];

							if (selectedItem) {

								let module = util.module.getFromModuleAction(self.parent.config.equipmentViewer);

								if (module) {

									let moduleAction = { id: self.parent.config.equipmentViewer };
									let args = { selectedPks: [selectedItem.id] };

									util.module.open(module, moduleAction, VIEW_TYPE_WINDOW, null, args);
								}
							}
						}
					}
				});

				self.tubeDalisDataTable = new DataTable(null, null, metadata, data, events);

				viewDalis = self.tubeDalisDataTable.getView();

				function changeRegime(newRegimeId, callbackCloseDialog) {

					let ms = new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_LIGHTING_CHANGE_OPTION_CONFIRM_DESCRIPTION"), null, onAccept, null);
					ms.show();

					function onAccept() {

						callbackCloseDialog(true);
						let command = self.parent.config.commands.SEND_DALI_REGIME;
						let dali = self.tubeDalis.find(d => d.id == self.tubeDalisDataTable.getSelectedItems()[0].id);

						command.elementId = dali.id;
						command.commandElementValues[0].value = newRegimeId;

						util.element.sendCommand([command], function(res) {

							if (res) {

								(new MessageBox(MessageBox.INFO, i18n.t("LBL_COMMAND_SEND_SUCCESSFULLY"), null, null, null)).show();
							}
						});
					}
				}

			} else {

				viewDalis = [util.ui.getViewNoData()];
			}

			return viewDalis;
		}

		function getBottom() {

			let bottom;

			bottom = {
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
						disabled: true,
						hidden: (self.selectedTable === "action-panel-switcher-dali" + self.parent.uid) || !self.tubeCircuitsDataTable || !self.parent.commandEquipmentPermission,
						click: function() {

							let ms = new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_LIGHTING_CHANGE_STATUS_CONFIRM_DESCRIPTION"), null, onAccept, null);
							ms.show();

							function onAccept() {

								let responseSend = 0;
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

									executeCircuitCommand(CIRCUIT_COMMAND_SEND_ON, circuitsOnCommand, onExecuted);
								}

								if (circuitsOffCommand.length > 0) {

									executeCircuitCommand(CIRCUIT_COMMAND_SEND_OFF, circuitsOffCommand, onExecuted);
								}

								function onExecuted() {

									if (++responseSend == circuitsOnCommand.length + circuitsOffCommand.length) {

										responseSend = 0;

										(new MessageBox(MessageBox.INFO, i18n.t("LBL_COMMAND_SEND_SUCCESSFULLY"), null, null, null)).show();
									}
								}

								function executeCircuitCommand(commandAction, values, onExecuted) {

									let command = self.parent.config.commands[commandAction];
									command.elementId = self.selectedTube.id;

									command.commandElementValues[0].value = JSON.stringify(values);

									util.element.sendCommand([command], function(res) {

										self.tubeCircuitsDataTable.getAllItems().forEach(row => {

											row.circuitStateToSend = null;
											row.circuitStateToSendAlias = null;
											self.tubeCircuitsDataTable.update(row);
										});

										self.checkDisableChangeStatusButton();

										if (res && onExecuted) {

											onExecuted();
										}
									});
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

	checkDisableChangeStatusButton() {

		let self = this;

		let changeStatusButton = $$("view-content-button-change-circuits-status-" + self.parent.uid);

		if (changeStatusButton) {

			let lightingMode = self.getLightingMode();

			let modeEnableLightingActions = !(!lightingMode || self.parent.config.lightingModeOptions.find(option => option.id == lightingMode).disable);

			if (self.tubeCircuitsDataTable && self.tubeCircuitsDataTable.getAllItems() && Array.isArray(self.tubeCircuitsDataTable.getAllItems()) && self.tubeCircuitsDataTable.getAllItems().length > 0 && modeEnableLightingActions) {

				let enabled = self.tubeCircuitsDataTable.getAllItems().find(item => (item.circuitStateToSend != undefined && item.circuitStateToSend != null));

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

	connectWS() {

		let self = this;

		let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

		if (stompSubscriptorService) {

			if (self.tubeId) {

				let topic = self.parent.config.topics.tube + "." + self.tubeId;
				self.idStompSubscriptorTube = stompSubscriptorService.subscribe(appConfig.stompUrl, topic, onMessageTube);
			}

			if (Array.isArray(self.tubeCircuits) && self.tubeCircuits.length > 0) {

				let fields = [];
				fields.push({
					target: "id",
					values: []
				});
				self.tubeCircuits.forEach(element => fields[0].values.push(element.id));

				self.idStompSubscriptorCircuits = stompSubscriptorService.subscribe(appConfig.stompUrl, self.parent.config.topics.circuits, onMessageCircuit, fields);
			}

			if (Array.isArray(self.tubeDalis) && self.tubeDalis.length > 0) {

				let fields = [];
				fields.push({
					target: "id",
					values: []
				});
				self.tubeDalis.forEach(element => fields[0].values.push(element.id));

				self.idStompSubscriptorDalis = stompSubscriptorService.subscribe(appConfig.stompUrl, self.parent.config.topics.dalis, onMessageDali, fields);
			}
		}

		function onMessageTube(data) {

			if (data) {

				self.selectedTube = eval("adapter." + self.parent.config.adapters.element)(self.selectedTube, data);
				let lightingMode = self.getLightingMode();

				webix.ui(self.getContentHeader(), $$("view-content-header-" + self.parent.uid));
				webix.ui(self.getContentToolbar(), $$("view-content-toolbar-" + self.parent.uid));

				$$("label-regimen-1-" + self.parent.uid).setValue('<div><span class="global-text-badge" style="background:#9c70303b; color: #9c7030a0;">' + i18n.t(self.getCurrentRegime() ? self.getCurrentRegime().labelAlias : "LBL_UNKNOWN") + '</span></div>')
				$$("label-regimen-2-" + self.parent.uid).setValue('<div><span class="global-text-badge" style="background:#9c70303b; color: #9c7030a0;">' + i18n.t(self.getProposedRegime() ? self.getProposedRegime().labelAlias : "LBL_UNKNOWN") + '</span></div>')

				if (!lightingMode || self.parent.config.lightingModeOptions.find(option => option.id == lightingMode).disable) {

					if (self.tubeCircuitsDataTable) {

						self.enableGridButtons(self.tubeCircuitsDataTable, false);
					}

					if (self.tubeDalisDataTable) {

						self.enableGridButtons(self.tubeDalisDataTable, false);
					}

					if ($$("view-content-button-change-regime-" + self.parent.uid)) {

						$$("view-content-button-change-regime-" + self.parent.uid).disable();
					}


				} else {

					if (self.tubeCircuitsDataTable) {

						self.enableGridButtons(self.tubeCircuitsDataTable, true);
					}

					if (self.tubeDalisDataTable) {

						self.enableGridButtons(self.tubeDalisDataTable, true);
					}

					if ($$("view-content-button-change-regime-" + self.parent.uid)) {

						$$("view-content-button-change-regime-" + self.parent.uid).enable();
					}
				}

				self.checkDisableChangeStatusButton();
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

	enableGridButtons(table, enable) {

		const INFO_BUTTON_ID = 1;

		if (this.parent.args && this.parent.args.readOnly) {

			enable &= (this.parent.args.readOnly !== true);
		}

		for (let command of table.metadata.gridCommands) {

			if (enable) {

				table.commandEnable(command.id);

			} else {

				if (command.id != INFO_BUTTON_ID) {

					table.commandDisable(command.id);
				}
			}
		}
	}
}
