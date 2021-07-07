import { util } from "../../../lib/util.js";
import DataTable from "../../../component/datatable.js";
import { adapter } from "../../../lib/adapter.js";

const FAN_COMMAND_SEND_FANS_STOP = "SEND_FANS_STOP";
const FAN_COMMAND_SEND_FANS_START = "SEND_FANS_START";
const FAN_COMMAND_SEND_FANS_START_REVERSE = "SEND_FANS_START_REVERSE";

export default class TubeContent {

	constructor(tubeId, parent) {

		this.tubeId = tubeId;
		this.parent = parent;
		this.uid = parent.uid;
		this.enabledFanIds = parent.selectedFansIds;

		this.selectedTube = undefined;
		this.tubeFans = undefined;
		this.tubeFansDataTable = undefined;;
		this.infoRegime = undefined;

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

			webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.parent.config.restEndpoints.elementById, { id: self.tubeId })).then(function(data) {

				if (data != null) {

					self.selectedTube = data.json();
					let fans = self.selectedTube.childs.filter(element => element.elementType.id == self.parent.config.elementTypeFan);
					self.tubeFans = eval("adapter." + self.parent.config.adapters.fan)(fans, null);

					webix.ui(drawContent(), $$("view-tube-content-" + self.uid));
					$$("view-tube-loading-" + self.uid).hide();

					if (self.tubeFansDataTable) {

						self.tubeFansDataTable.callbacks();

						if (self.enabledFanIds) {

							self.tubeFansDataTable.select(self.enabledFanIds);
						}

						if (getVentilationMode() && self.parent.config.ventilationModeOptions.find(option => option.id == getVentilationMode()).disable) {

							self.tubeFansDataTable.disable();
						}
					}

					connectWS();
				}
			});
		}

		function getCurrentVentilationState() {

			let currentVentilationState = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.currentVentilationState);
			return currentVentilationState && currentVentilationState.value ? window.em.getByField(EntityType.ELEMENT_TYPE_STATE, "id", currentVentilationState.value) : undefined;
		}

		function getDisabledChangeRegime() {

			let currentVentilationState = getCurrentVentilationState();

			if (currentVentilationState && currentVentilationState.id &&
				self.parent.config.ventilationStates[currentVentilationState.id].actions.includes("SEND_VENTILATION_REGIME")) {

				return false;

			} else {

				return true;
			}
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

			let header = {
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
				elements: [],
				borderless: true
			}

			let actionPanelSwitcher = {
				view: "segmented",
				css: "segment-custom-2",
				value: getVentilationMode(),
				width: 500,
				options: ventilationModeOptions,
				tooltip: true,
				on: {
					onChange(newv, oldv) {

						webix.confirm({
							title: i18n.t("LBL_VENTILATION_CHANGE_MODE_CONFIRM"),
							ok: i18n.t("LBL_ACCEPT"),
							cancel: i18n.t("LBL_CANCEL"),
							text: i18n.t("LBL_VENTILATION_CHANGE_MODE_CONFIRM_DESCRIPTION"),
							width: 600
						}).then(function() {

							let command = self.parent.config.commands.SEND_VENTILATION_MODE;
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

		function connectWS() {

			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

			if (stompSubscriptorService) {

				if (self.tubeId) {

					let topic = self.parent.config.topics.tube + "." + self.tubeId;
					self.idStompSubscriptorTube = stompSubscriptorService.subscribe(util.stomp.getDataSourceFromTopic(topic), onMessageTube);

				}

				if (self.tubeFans && Array.isArray(self.tubeFans) && self.tubeFans.length > 0) {

					let fields = [];
					fields.push({
						target: "id",
						values: []
					});
					self.tubeFans.forEach(element => fields[0].values.push(element.id));

					self.idStompSubscriptorFans = stompSubscriptorService.subscribe(util.stomp.getDataSourceFromTopic(self.parent.config.topics.fan), onMessageFan, fields);
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

					if (!ventilationMode || self.parent.config.ventilationModeOptions.find(option => option.id == ventilationMode).disable) {

						self.tubeFansDataTable.disable();
						$$("view-content-button-change-regime-" + self.uid).disable();
						$$("view-content-button-change-fans-status-" + self.uid).disable();

					} else {

						if (getDisabledChangeRegime()) {

							$$("view-content-button-change-regime-" + self.uid).disable();

						} else {

							$$("view-content-button-change-regime-" + self.uid).enable();
						}

						self.tubeFansDataTable.enable();
						$$("view-content-button-change-fans-status-" + self.uid).enable();
					}
				}
			}

			function onMessageFan(data) {

				if (data) {

					let oldFanData = self.tubeFansDataTable.getItem(data.id);

					if (oldFanData) {

						data.fanStateToSend = oldFanData.statusSendId;
						data.fanStateToSendAlias = oldFanData.statusSendAlias;
						data.alias = oldFanData.alias;

						let fanData = eval("adapter." + self.parent.config.adapters.fan)([data], null);
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
							{ width: 6 },
							getContentToolbar(),
							{ width: 6 }
						]
					},
					{
						cols: [
							{ width: 6 },
							getContentBody(),
							{ width: 6 }
						]
					}
				]
			};

			return viewContent;

			function getContentBody() {

				let contentBody = {
					view: "form",
					id: "view-content-body-" + self.uid,
					rows: [
						getRegime(),
						getButtons(),
						{
							cols: [
								{
									rows: getTable()
								}
							]
						},
						getBottom()
					]
				}

				function getRegime() {

					let ventilationRegimesConfig = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.parent.config.params.ventilationRegimesConfig);
					let availableRegimes;

					try {

						ventilationRegimesConfig = JSON.parse(ventilationRegimesConfig.value);

						availableRegimes = ventilationRegimesConfig.ventilationRegimes.map(function(regime) {
							return {
								id: regime.id,
								value: i18n.t(regime.labelAlias)
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
										cols: [
											{
												view: "label",
												height: 24,
												width: 180,
												template: "<span>" + i18n.t("LBL_CURRENT_REGIME") + "</span>",
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
												id: "label-regimen-1-" + self.uid,
												template: '<div><span class="global-text-badge" style="background:#9c70303b; color: #9c7030a0;">' + i18n.t(getCurrentRegime() ? getCurrentRegime().labelAlias : "LBL_UNKNOWN") + '</span></div>',
												css: "global-text"
											},
											{ width: 12 },
											{
												view: "label",
												width: 180,
												height: 24,
												id: "label-regimen-2-" + self.uid,
												template: '<div><span class="global-text-badge" style="background:#9c70303b; color: #9c7030a0;">' + i18n.t(getProposedRegime() ? getProposedRegime().labelAlias : "LBL_UNKNOWN") + '</span></div>',
												css: "global-text"

											},
											{ width: 12 },
											{
												id: "label-regimen-3-" + self.uid,
												width: 180,
												height: 28,
												view: "combo",
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
							{},
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
												disabled: self.parent.config.ventilationModeOptions.find(option => option.id == getVentilationMode()).disable ||
													getDisabledChangeRegime(),
												click: function() {

													let command = self.parent.config.commands.SEND_VENTILATION_REGIME;
													command.elementId = self.tubeId;
													command.commandElementValues[0].value = $$("label-regimen-3-" + self.uid).getValue();

													if (!command.commandElementValues[0].value || command.commandElementValues[0].value == 0) {

														webix.alert({
															title: i18n.t("LBL_NO_REGIME_SELECTED"),
															text: i18n.t("LBL_NO_REGIME_SELECTED_DESCRIPTION"),
															type: "alert-warning",
															width: 500
														});

													} else {

														webix.confirm({
															title: i18n.t("LBL_SEND_VENTILATION_REGIME_CONFIRM"),
															ok: i18n.t("LBL_ACCEPT"),
															cancel: i18n.t("LBL_CANCEL"),
															text: i18n.t("LBL_SEND_VENTILATION_REGIME_CONFIRM_DESCRIPTION"),
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
							id: "view-content-regime-document-" + self.uid,
							height: 26
						};

						if (self.infoRegime.documentUrl) {

							viewDocument = {
								id: "view-content-regime-document-" + self.uid,
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
							id: "view-content-regime-document-text-" + self.uid,
							height: 60,
							width: 500,
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

				function getButtons() {

					let buttons = {
						cols: [
							{
								view: "segmented",
								css: "segment-custom-2",
								value: "action-panel-switcher-fan" + self.uid,
								width: 200,
								options: [
									{
										id: "action-panel-switcher-fan" + self.uid,
										value: "<span>" + i18n.t("LBL_FAN") + "</span>",
									}

								],
								tooltip: function(obj) {

									if (obj.id === "action-panel-switcher-fan" + self.uid) {

										return i18n.t("LBL_FAN");
									}

								},
								on: {
									onChange(newv, oldv) {

										if (newv === "action-panel-switcher-fan" + self.uid) {

											webix.message("show-all");
										}
										else if (newv === "action-panel-switcher-tube" + self.uid) {

											webix.message("show-top");
										}
									}
								}
							},
							{}
						]
					};

					return buttons;
				}

				function getTable() {

					let data = self.tubeFans;

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
						label: "LBL_FAN",
						visible: true,
						gridOptionValues: defaultGridOptionValues,
						gridFields: [
							{
								id: 102701,
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
								id: 102502,
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
								id: 102703,
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
								id: 102504,
								extended: false,
								filtered: false,
								isEpochMilli: false,
								isPk: false,
								label: "LBL_FAN_STATE",
								labelTooltip: "LBL_FAN_STATE",
								parameterized: false,
								position: 4,
								required: true,
								sourceField: "ElementTypeParam:9:2:1",
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
								id: 102505,
								extended: false,
								filtered: false,
								isEpochMilli: false,
								isPk: false,
								parameterized: false,
								position: 5,
								required: false,
								sourceField: "fanStateToSend",
								visible: false,
								widthPx: 1,
								gridFieldDataType: {
									id: 1,
									name: "string"
								}
							}, {
								id: 102506,
								extended: false,
								filtered: false,
								isEpochMilli: false,
								isPk: false,
								label: "LBL_FAN_STATE_TO_SEND",
								labelTooltip: "LBL_FAN_STATE_TO_SEND",
								needsTranslation: false,
								parameterized: false,
								position: 6,
								required: true,
								sourceField: "fanStateToSendAlias",
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
								icon: "mdi mdi-rotate-right",
								label: "LBL_FAN_PLAY",
								multiselect: true,
								position: 1,
								showText: false,
								itemRequired: true
							},
							{
								id: 2,
								default_: false,
								icon: "mdi mdi-rotate-left",
								label: "LBL_FAN_REVERSE",
								multiselect: true,
								position: 2,
								showText: false,
								itemRequired: true
							},
							{
								id: 3,
								default_: false,
								icon: "mdi mdi-stop-circle-outline",
								label: "LBL_FAN_STOP",
								multiselect: true,
								position: 3,
								showText: false,
								itemRequired: true
							},
							{
								id: 4,
								default_: false,
								icon: "mdi mdi-cancel",
								label: "LBL_CLEAR",
								multiselect: true,
								position: 4,
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

							self.tubeFansDataTable.getSelectedItems().forEach(item => {

								item.fanStateToSend = FAN_COMMAND_SEND_FANS_START;
								item.fanStateToSendAlias = i18n.t("LBL_SEND_FAN_START");
								self.tubeFansDataTable.update(item);
							});
						}
					});

					events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
						commandId: 2,
						function: () => {

							self.tubeFansDataTable.getSelectedItems().forEach(item => {

								item.fanStateToSend = FAN_COMMAND_SEND_FANS_START_REVERSE;
								item.fanStateToSendAlias = i18n.t("LBL_SEND_FAN_START_REVERSE");
								self.tubeFansDataTable.update(item);
							});
						}
					});

					events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
						commandId: 3,
						function: () => {

							self.tubeFansDataTable.getSelectedItems().forEach(item => {

								item.fanStateToSend = FAN_COMMAND_SEND_FANS_STOP;
								item.fanStateToSendAlias = i18n.t("LBL_SEND_FAN_STOP");
								self.tubeFansDataTable.update(item);
							});
						}
					});

					events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
						commandId: 4,
						function: () => {

							self.tubeFansDataTable.getSelectedItems().forEach(item => {

								item.fanStateToSend = null;
								item.fanStateToSendAlias = "-";
								self.tubeFansDataTable.update(item);
							});
						}
					});

					self.tubeFansDataTable = new DataTable(null, null, metadata, data, events);

					return self.tubeFansDataTable.getView();
				};

				function getBottom() {

					let bottom = {
						cols: [
							{},
							{
								id: "view-content-button-change-fans-status-" + self.uid,
								width: 130,
								view: "button",
								align: "right",
								css: "button-cta",
								value: i18n.t("LBL_VENTILATION_CHANGE_STATUS"),
								disabled: self.parent.config.ventilationModeOptions.find(option => option.id == getVentilationMode()).disable,
								click: function() {

									webix.confirm({
										title: i18n.t("LBL_VENTILATION_CHANGE_STATUS_CONFIRM"),
										ok: i18n.t("LBL_ACCEPT"),
										cancel: i18n.t("LBL_CANCEL"),
										text: i18n.t("LBL_VENTILATION_CHANGE_STATUS_CONFIRM_DESCRIPTION"),
										width: 600
									}).then(function() {

										let allTableItems = self.tubeFansDataTable.getAllItems();

										let fansStopCommand = [];
										let fansStartCommand = [];
										let fansStartReverseCommand = [];

										allTableItems.forEach(row => {

											if (row.fanStateToSend == FAN_COMMAND_SEND_FANS_STOP) {

												fansStopCommand.push(row.id);

											} else if (row.fanStateToSend == FAN_COMMAND_SEND_FANS_START) {

												fansStartCommand.push(row.id);

											} else if (row.fanStateToSend == FAN_COMMAND_SEND_FANS_START_REVERSE) {

												fansStartReverseCommand.push(row.id);
											}
										});

										if (fansStopCommand.length > 0) {

											executeFansCommand(FAN_COMMAND_SEND_FANS_STOP, fansStopCommand);
										}

										if (fansStartCommand.length > 0) {

											executeFansCommand(FAN_COMMAND_SEND_FANS_START, fansStartCommand);
										}

										if (fansStartReverseCommand.length > 0) {

											executeFansCommand(FAN_COMMAND_SEND_FANS_START_REVERSE, fansStartReverseCommand);
										}

										function executeFansCommand(commandAction, values) {

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

												self.tubeFansDataTable.getAllItems().forEach(row => {

													row.fanStateToSend = null;
													row.fanStateToSendAlias = null;
													self.tubeFansDataTable.update(row);
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

		}
	}
}


