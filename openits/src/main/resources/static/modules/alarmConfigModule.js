import { util } from "../lib/util.js";
import GenericModule from "./genericModule.js";
import DataTable from "../component/datatable.js";
import InputDialogBox from "../component/inputDialogBox.js";
import MessageBox from "../component/messageBox.js";

export default class AlarmConfigModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.config = module.config;
		this.moduleAction = moduleAction;
		this.args = args;

		this.tableMeasures = undefined;
		this.paramWindow = undefined;

		this.operationsOptionsArr = [];
		this.elementTypeParamMeasures = [];

		this.uid = webix.uid();
	}

	getView() {

		let self = this;
		let view = undefined;

		if (self.moduleAction.id === self.config.moduleAction.edit) {

			view = {
				cols: [
					{
						id: "loading-" + self.uid,
						hidden: false,
						rows: [util.ui.getViewDrawLoading()]
					},
					{
						id: self.uid,
						hidden: true
					}
				]
			};
		}

		return view;
	}


	loadData() {

		let self = this;
		let alarmConfigData;

		webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.alarmConfig, { id: self.args.selectedPks[0] })).then(function(alarmConfigDataResponse) {

			if (alarmConfigDataResponse != null) {

				alarmConfigData = alarmConfigDataResponse.json();

				webix.ui(drawContent(), $$(self.uid));

				$$(self.uid).show();
				$$("loading-" + self.uid).hide();

				if (self.tableMeasures) {

					self.tableMeasures.callbacks();
				}

				util.ui.setTitle(self.args.containerId, i18n.t(alarmConfigData.elementType.labelAlias) + " - " + i18n.t(alarmConfigData.labelAlias));
			}
		});

		function drawContent() {

			let viewContent;

			viewContent = {
				id: self.uid,
				css: { "box-sizing": "border-box" },
				rows: [
					getHeader(),
					{
						cols: [
							{ width: 6 },
							getTabs(),
							{ width: 6 }
						]
					},
					getButtons()
				]
			};


			function getHeader() {

				let header = {};
				let label = undefined;
				let description = undefined;

				if (self.moduleAction.id === self.config.moduleAction.edit) {

					label = "LBL_EDIT_ALARM_CONFIG";
					description = "LBL_EDIT_ALARM_CONFIG_DESCRIPTION";

				} else if (self.moduleAction.id === self.config.moduleAction.add) {

					label = "LBL_ADD_ALARM_CONFIG";
					description = "LBL_ADD_ALARM_CONFIG_DESCRIPTION";
				}

				header = {
					id: "viewHeader-" + self.uid,
					cols: [
						{
							view: "label",
							label: "<img src='img/grid/alarmConfig.png' class='grid-icon'/>",
							height: 75,
							css: "global-header-icon"
						},
						{
							view: "label",
							height: 75,
							id: "label-bottom-" + self.uid,
							template: "<span>" + i18n.t(label) + "</span> <br> <span style='font-size: 13px'>" + i18n.t(description) + "</span>",
							css: "global-header-label"
						}
					]
				};

				return header;
			};


			function getTabs() {

				let tabs;

				tabs = {
					view: "tabview",
					id: "tabview-" + self.uid,
					borderless: true,
					css: "secondary-tab",
					cells: [],
					tabbar: {
						tabMargin: 6,
					}
				}

				tabs.cells.push({
					header: i18n.t("LBL_ALARM_CONFIG_INFO"),
					body: getInfo(),
				});

				tabs.cells.push({
					header: i18n.t("LBL_ALARM_CONFIG_THRESHOLDS"),
					body: getDataTable()
				});


				function getInfo() {

					let infoDetails;

					infoDetails = {
						css: { "box-sizing": "border-box", "border-bottom": "1px solid #e2e2e2", "border-left": "1px solid #e2e2e2", "border-right": "1px solid #e2e2e2" },
						cols: [
							{ width: 40 },
							{
								rows: [
									{ height: 90 },
									{
										view: "label",
										css: "alarm-view-title-label",
										label: `<div><span class="alarm-view-title-label">${i18n.t("LBL_ALIAS")}</span></div>`,
										align: "left",
										height: 20
									},
									{ height: 24 },
									{
										view: "label",
										css: "alarm-view-title-label",
										label: `<div><span class="alarm-view-title-label">${i18n.t("LBL_ELEMENT_TYPE")}</span></div>`,
										align: "left",
										height: 20
									},
									{ height: 24 },
									{
										view: "label",
										css: "alarm-view-title-label",
										label: `<div><span class="alarm-view-title-label">${i18n.t("LBL_ELEMENT_ALIAS")}</span></div>`,
										align: "left",
										height: 20
									},
									{ height: 24 },
									{
										view: "label",
										css: "alarm-view-title-label",
										label: `<div><span class="alarm-view-title-label">${i18n.t("LBL_ALARM_LEVEL")}</span></div>`,
										align: "left",
										height: 20
									},
									{ height: 24 },
									{
										view: "label",
										css: "alarm-view-title-label",
										label: `<div><span class="alarm-view-title-label">${i18n.t("LBL_CRITICAL_LEVEL")}</span></div>`,
										align: "left",
										height: 20
									},
									{ height: 24 },
									{
										view: "label",
										css: "alarm-view-title-label",
										label: `<div><span class="alarm-view-title-label">${i18n.t("LBL_ALARM_TYPE")}</span></div>`,
										align: "left",
										height: 20
									},
									{}
								]
							},
							{
								rows: [
									{ height: 90 },
									{
										view: "label",
										label: `<span id="labelAlarm" class="alarm-view-text">${i18n.t(alarmConfigData.labelAlias)}</span>`,
										align: "left",
										height: 20,
									},
									{ height: 24 },
									{
										view: "label",
										label: `<span class="alarm-view-text">${i18n.t(alarmConfigData.elementType.labelAlias)}</span>`,
										align: "left",
										height: 20
									},
									{ height: 24 },
									{
										view: "label",
										label: function() {

											if (alarmConfigData.element) {

												return `<span class="alarm-view-text">${i18n.t(alarmConfigData.element.labelAlias)}</span>`;

											} else {

												return `<span class="alarm-view-text">-</span>`;
											}

										}(),
										align: "left",
										height: 20
									},
									{ height: 24 },
									{
										view: "label",
										label: `<span class="alarm-view-text">${i18n.t(alarmConfigData.alarmLevel.labelAlias)}</span>`,
										align: "left",
										height: 20
									},
									{ height: 24 },
									getAlarmLevelIcon(),
									{ height: 24 },
									getAlarmTypeIcon(),
									{}
								]
							}
						]
					};


					function getAlarmTypeIcon() {

						let isManual = self.config.alarmType.alarmTypeAuto.includes(alarmConfigData.alarmType.id) ? false : true;
						let icon = isManual ? "./img/grid/manual.png" : "./img/grid/auto.png";
						let vlabel = isManual ? "LBL_ALARM_MANUAL" : "LBL_ALARM_AUTO";
						let numType = alarmConfigData.alarmType.id.charAt(alarmConfigData.alarmType.id.length - 1);
						let view;

						view = {
							cols: [
								{
									width: 34,
									borderless: true,
									height: 27,
									template: `<img style="max-width:100%; width:auto; width: -moz-available; width: -webkit-fill-available; height:auto; padding-right: 10px;" src="${icon}">`,
								},
								{
									width: 365,
									view: "label",
									label: `<span class="alarm-view-text">${i18n.t("LBL_ALARM_TYPE")} ${numType}, ${i18n.t(vlabel)}</span>`,
									align: "left",
									height: 27
								},
								{}
							]
						};

						return view;
					}

					function getAlarmLevelIcon() {

						let isCritical = self.config.levels.critical.includes(alarmConfigData.alarmLevel.id) ? true : false;
						let isWarn = self.config.levels.warn.includes(alarmConfigData.alarmLevel.id) ? true : false;
						let isInfo = self.config.levels.info.includes(alarmConfigData.alarmLevel.id) ? true : false;

						let iconInfo = "./img/grid/info.png";
						let iconWarn = "./img/grid/warn.png";
						let iconCritical = "./img/grid/critical.png";
						let labelInfo = "LBL_ALARM_LEVEL_INFO";
						let labelWarn = "LBL_ALARM_LEVEL_WARN";
						let labelCritical = "LBL_ALARM_LEVEL_CRITICAL";
						let icon = isCritical ? iconCritical : isWarn ? iconWarn : isInfo ? iconInfo : "";
						let vlabel = isCritical ? labelCritical : isWarn ? labelWarn : isInfo ? labelInfo : "";
						let view;

						view = {
							cols: [
								{
									width: 34,
									borderless: true,
									height: 27,
									template: `<img style="max-width:100%; width:auto; width: -moz-available; width: -webkit-fill-available; height:auto; padding-right: 10px;" src="${icon}">`,
								},
								{
									width: 130,
									view: "label",
									label: `<span class="alarm-view-text">${i18n.t(vlabel)}</span>`,
									align: "left",
									height: 27
								},
								{}
							]
						};

						return view;
					}

					return infoDetails;
				}

				function getDataTable() {

					let data = [];

					if (Array.isArray(alarmConfigData.alarmConfigMeasures) && alarmConfigData.alarmConfigMeasures.length > 0) {

						for (let configMeasures of alarmConfigData.alarmConfigMeasures) {

							data.push({
								id: configMeasures.elementTypeParam.id,
								elementAlias: alarmConfigData.elementType.labelDescription,
								parameter: i18n.t(configMeasures.elementTypeParam.labelAlias),
								//elementState: alarmConfigData.alarmTypeLabelAlias,
								operation: i18n.t(configMeasures.alarmMeasureOperation.labelAlias),
								operationVal1: configMeasures.thresholdVal1,
								operationVal2: configMeasures.thresholdVal2,
								alarmMeasureOperation: configMeasures.alarmMeasureOperation,
								elementTypeParam: configMeasures.elementTypeParam
							});
						}
					}

					const defaultGridOptionValues = [
						{ gridOption: { id: 1 }, value: "false" },
						{ gridOption: { id: 2 }, value: "false" },
						{ gridOption: { id: 3 }, value: "9" },
						{ gridOption: { id: 4 }, value: "asc" },
						{ gridOption: { id: 7 }, value: "false" },
						{ gridOption: { id: 8 }, value: "false" },
						{ gridOption: { id: 9 }, value: "true" },
						{ gridOption: { id: 11 }, value: "false" },
						{ gridOption: { id: 13 }, value: "true" },
						{ gridOption: { id: 16 }, value: "img/grid/default.png" },
						{ gridOption: { id: 21 }, value: "auto" },
						{ gridOption: { id: 23 }, value: "false" }
					];

					let metadata = {
						filter: true,
						label: "LBL_INCIDENT_REPORT_ALARMS",
						visible: true,
						gridOptionValues: defaultGridOptionValues,
						gridFields: [
							{
								id: 1,
								extended: false,
								filtered: false,
								isEpochMilli: false,
								isPk: true,
								needsTranslation: false,
								parameterized: false,
								position: 1,
								required: true,
								sourceField: "id",
								visible: false,
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
								label: "LBL_ALARM_CONFIG_THRESHOLD",
								needsTranslation: false,
								parameterized: false,
								position: 2,
								required: true,
								sourceField: "parameter",
								visible: true,
								gridFieldDataType: {
									id: 1,
									name: "string"
								}
							},
							{
								id: 3,
								extended: false,
								filtered: false,
								isEpochMilli: false,
								isPk: false,
								label: "LBL_THRESHOLD_OPERATION",
								needsTranslation: false,
								parameterized: false,
								position: 3,
								required: false,
								sourceField: "operation",
								visible: true,
								gridFieldDataType: {
									id: 1,
									name: "string"
								}
							},
							{
								id: 4,
								extended: false,
								filtered: false,
								isEpochMilli: false,
								isPk: false,
								label: "LBL_THRESHOLD_OPERATION_ONE",
								needsTranslation: false,
								parameterized: false,
								position: 4,
								required: false,
								sourceField: "operationVal1",
								visible: true,
								gridFieldDataType: {
									id: 1,
									name: "string"
								}
							},
							{
								id: 5,
								extended: false,
								filtered: false,
								isEpochMilli: false,
								isPk: false,
								label: "LBL_THRESHOLD_OPERATION_TWO",
								needsTranslation: false,
								parameterized: false,
								position: 5,
								required: false,
								sourceField: "operationVal2",
								visible: true,
								gridFieldDataType: {
									id: 1,
									name: "string"
								}
							}
						],
						gridCommands: [
							{
								id: 1,
								value: "LBL_EDIT",
								icon: "mdi mdi-pencil",
								default_: true,
								label: "LBL_CMD_EDIT",
								showText: false,
								position: 2,
								multiselect: false,
								itemRequired: true
							},
							{
								id: 2,
								value: "LBL_ADD",
								icon: "mdi mdi-plus",
								default_: false,
								label: "LBL_CMD_ADD",
								showText: false,
								position: 1,
								multiselect: false,
								itemRequired: false
							},
							{
								id: 3,
								value: "LBL_DELETE",
								icon: "mdi mdi-delete",
								default_: false,
								label: "LBL_CMD_DELETE",
								showText: false,
								position: 3,
								multiselect: true,
								itemRequired: true
							}
						]
					};

					let events = {};
					events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];

					events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
						commandId: 1,
						function: () => {

							let configMeasuresSelected = self.tableMeasures.getSelectedItems();
							getParamWindow(configMeasuresSelected[0]);
						}
					});

					events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
						commandId: 2,
						function: () => {

							getParamWindow();
						}
					});

					events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
						commandId: 3,
						function: (dataArr) => {

							if (Array.isArray(dataArr) && dataArr.length > 0) {

								let alarmsConfigToDelete = [];

								for (let dat of dataArr) {

									alarmsConfigToDelete.push({
										id: dat
									});
								}

								self.tableMeasures.delete(alarmsConfigToDelete);
							}
						}
					});

					self.tableMeasures = new DataTable(null, null, metadata, data, events);

					let table = {
						css: { "box-sizing": "border-box", "border-bottom": "1px solid #e2e2e2", "border-left": "1px solid #e2e2e2", "border-right": "1px solid #e2e2e2" },
						rows: self.tableMeasures.getView()
					};

					function getParamWindow(measures) {

						let paramWindowBody;
						let newThreshold = measures ? false : true;

						paramWindowBody = {
							cols: [
								{ width: 6 },
								{
									rows: [
										{ height: 6 },
										{
											view: "form",
											elementsConfig: { labelAlign: "left" },
											css: "global-form condition-dialog-box",
											elements: [
												{ view: "template", template: i18n.t(alarmConfigData.elementType.labelAlias), type: "section" },
												getElementTypeParamRow(),
												getThresholdOperationRow(),
												getThresholdVal1Row(),
												getThresholdVal2Row()
											]
										},
										getToolbarActions()
									]
								},
								{ width: 6 }
							]
						};

						self.paramWindow = util.ui.loadWindow(i18n.t("LBL_ALARM_CONFIG_THRESHOLDS"), paramWindowBody, 360, 755, true);

						function getElementTypeParamRow() {

							let elementTypeParamRow;
							let paramOptions = [];
							let elementTypeParamArr = window.em.getByField(EntityType.ELEMENT_TYPE, "id", alarmConfigData.elementType.id).elementTypeParams;
							let actualParams = self.tableMeasures.getAllItems();
							let elementTypeParamMeasures = [];

							self.elementTypeParamMeasures = elementTypeParamArr.filter(e => e.paramType.id === self.config.paramTypeMeasure);
							elementTypeParamMeasures = webix.copy(self.elementTypeParamMeasures);

							if (Array.isArray(actualParams) && actualParams.length > 0) {

								actualParams.forEach(e => {

									let sameTypeParam = elementTypeParamMeasures.find(item => item.id === e.id);

									if (sameTypeParam) {

										elementTypeParamMeasures.splice(elementTypeParamMeasures.indexOf(sameTypeParam), 1);
									}
								});

								//								for (let actualParam of actualParams) {
								//
								//									elementTypeParamMeasures = self.elementTypeParamMeasures.filter(typeParam => typeParam.id != actualParam.id);
								//								}
							}

							for (let elementTypeParam of elementTypeParamMeasures) {

								paramOptions.push({
									id: elementTypeParam.id,
									value: i18n.t(elementTypeParam.labelAlias)
								});
							}

							elementTypeParamRow = {
								cols: [
									{
										view: "label",
										label: i18n.t("LBL_ALARM_CONFIG_THRESHOLD"),
										width: 326,
										css: "parameter-window"
									},
									{
										id: "change-condition-element-type-param-row-" + self.uid,
										width: 290,
										view: "richselect",
										value: measures ? { id: measures.elementTypeParam.id, value: measures.parameter } : "",
										disabled: (measures ? true : false) || (paramOptions.length === 0),
										options: paramOptions,
										on: {
											onChange(newv, oldv) {

												$$("change-condition-threshold-operation-row-" + self.uid).enable();
												$$("change-condition-threshold-operation-row-" + self.uid).setValue("");

												$$("value1" + self.uid).setValue("");
												$$("value2" + self.uid).setValue("");
											}
										}
									},
									{}
								]
							};

							return elementTypeParamRow;
						}

						function getThresholdOperationRow() {

							let thresholdOperationRow;
							let operationOptions = [];

							webix.ajax().get(appConfig.restUrl + self.config.restEndpoints.thresholdOperations).then(function(operationsResponse) {

								if (operationsResponse != null) {

									let operations = operationsResponse.json();

									if (Array.isArray(operations) && operations.length > 0) {

										for (let operation of operations) {

											self.operationsOptionsArr.push({
												id: operation.id,
												value: i18n.t(operation.labelAlias)
											});
										}

										let selectOperation = $$("change-condition-threshold-operation-row-" + self.uid);
										let listOptions = selectOperation.getPopup().getList();

										listOptions.clearAll();
										listOptions.parse(self.operationsOptionsArr);

										if (measures) {

											selectOperation.setValue(measures.alarmMeasureOperation.id);
											selectOperation.refresh();
										}
									}
								}
							});

							if (measures) {

								operationOptions.push({
									id: measures.alarmMeasureOperation.id,
									value: measures.operation
								});
							}

							thresholdOperationRow = {
								cols: [
									{
										view: "label",
										label: i18n.t("LBL_THRESHOLD_OPERATION"),
										width: 326,
										css: "parameter-window"
									},
									{
										id: "change-condition-threshold-operation-row-" + self.uid,
										width: 290,
										view: "richselect",
										value: measures ? measures.operation : "",
										options: operationOptions,
										disabled: measures ? false : true
									},
									{}
								]
							}

							return thresholdOperationRow;
						}

						function getThresholdVal1Row() {

							let thresholdVal1Row;

							thresholdVal1Row = {
								id: "change-threshold-value-one-row-" + self.uid,
								cols: [
									{
										id: "value1" + self.uid,
										view: "text",
										value: measures ? measures.operationVal1 : "",
										disabled: true,
										required: false,
										label: i18n.t("LBL_THRESHOLD_OPERATION_ONE"),
										labelWidth: 325
									},
									{
										view: "icon", icon: "mdi mdi-pencil",
										on: {
											onItemClick: function() {

												let param = $$("change-condition-element-type-param-row-" + self.uid);
												let input = $$("value1" + self.uid);
												let paramValue = undefined;
												let inputValue = undefined;
												let dataType = undefined;

												if (param) {

													paramValue = param.getValue();

													if (paramValue) {

														let typeParam = self.elementTypeParamMeasures.find(tParam => tParam.id === paramValue);

														if (typeParam) {

															dataType = {
																id: typeParam.dataType.id
															};
														}
													}
												}

												if (input) {

													inputValue = input.getValue();
												}

												let i = new InputDialogBox(i18n.t("LBL_THRESHOLD_OPERATION_ONE"), i18n.t("LBL_ENTER_VALUE"), onAccept, false, inputValue, dataType);
												i.show();

												function onAccept(value) {

													$$("value1" + self.uid).setValue(value);
												}
											}
										}
									},
									{ width: 90 }
								]
							};

							return thresholdVal1Row;
						}

						function getThresholdVal2Row() {

							let thresholdVal2Row;

							thresholdVal2Row = {
								id: "change-threshold-value-two-row-" + self.uid,
								cols: [
									{
										id: "value2" + self.uid,
										view: "text",
										value: measures ? measures.operationVal2 : "",
										disabled: true,
										required: false,
										label: i18n.t("LBL_THRESHOLD_OPERATION_TWO"),
										labelWidth: 325
									},
									{
										view: "icon", icon: "mdi mdi-pencil",
										on: {
											onItemClick: function() {

												let param = $$("change-condition-element-type-param-row-" + self.uid);
												let input = $$("value2" + self.uid);
												let paramValue = undefined;
												let inputValue = undefined;
												let dataType = undefined;

												if (param) {

													paramValue = param.getValue();

													if (paramValue) {

														let typeParam = self.elementTypeParamMeasures.find(tParam => tParam.id === paramValue);

														if (typeParam) {

															dataType = {
																id: typeParam.dataType.id
															};
														}
													}
												}

												if (input) {

													inputValue = input.getValue();
												}

												let i = new InputDialogBox(i18n.t("LBL_THRESHOLD_OPERATION_ONE"), i18n.t("LBL_ENTER_VALUE"), onAccept, false, inputValue, dataType);
												i.show();

												function onAccept(value) {

													$$("value2" + self.uid).setValue(value);
												}
											}
										}
									},
									{ width: 90 }
								]
							};

							return thresholdVal2Row;
						}

						function getToolbarActions() {

							let viewToolbarActions = {};

							viewToolbarActions = {
								view: "toolbar",
								height: 45,
								borderless: true,
								rows: [
									{
										cols: [
											{ width: 15 },
											{
												view: "button",
												css: "dialog-cancel",
												width: 130,
												height: 41,
												label: i18n.t("LBL_CANCEL"),
												on: {
													onItemClick() {

														$$(self.paramWindow).close();
													}
												}
											},
											{},
											{
												view: "button",
												css: "dialog-accept",
												width: 130,
												height: 41,
												label: i18n.t("LBL_ACCEPT"),
												on: {
													onItemClick() {

														let val1 = $$("value1" + self.uid).getValue();
														let paramValue = $$("change-condition-element-type-param-row-" + self.uid).getValue();
														let operation = $$("change-condition-threshold-operation-row-" + self.uid).getValue();

														if (paramValue != "" && val1 != "" && operation != "") {


															let val2 = $$("value2" + self.uid).getValue();
															let threshold = undefined;
															let parameter = undefined;
															let alarmConfigMeasures = undefined;

															let newParam = self.elementTypeParamMeasures.find(p => p.id === paramValue);

															if (newParam) {

																parameter = i18n.t(newParam.labelAlias);
															}

															let newOperation = self.operationsOptionsArr.find(op => op.id === operation);

															if (newOperation) {

																threshold = newOperation.value;
															}

															if (newThreshold) {

																self.tableMeasures.add({
																	id: newParam.id,
																	parameter: parameter,
																	operation: threshold,
																	operationVal1: val1,
																	operationVal2: val2,
																	alarmMeasureOperation: newOperation,
																	elementTypeParam: newParam
																});

															} else {

																let actualData = self.tableMeasures.getSelectedItems();
																alarmConfigMeasures = actualData.find(dat => dat.id === newParam.id);

																if (alarmConfigMeasures) {

																	self.tableMeasures.update({
																		id: alarmConfigMeasures.id,
																		parameter: parameter,
																		operation: threshold,
																		operationVal1: val1,
																		operationVal2: val2,
																		alarmMeasureOperation: newOperation,
																		elementTypeParam: newParam
																	});
																}
															}

															$$(self.paramWindow).close();

														} else {

															let ms = new MessageBox(MessageBox.WARN, i18n.t("LBL_PLAN_LIBRARY_REQUIRED_PARAMS"), null, null);
															ms.show();
														}
													}
												}
											},
											{ width: 15 }
										]
									}
								]
							};

							return viewToolbarActions;
						}
					}

					return table;
				}

				return tabs;
			}

			function getButtons() {

				let viewToolbarActions = {};

				viewToolbarActions = {
					id: "buttons-" + self.uid,
					borderless: true,
					css: { "margin-top": "1px !important" },
					rows: [
						{
							cols: [
								{ width: 15 },
								{
									view: "button",
									value: i18n.t("LBL_CANCEL"),
									width: 130,
									height: 41,
									css: "dialog-cancel",
									click:
										function() {
											$$(self.args.containerId).close();
										}
								},
								{},
								{
									view: "button",
									value: i18n.t("LBL_ACCEPT"),
									width: 130,
									height: 41,
									css: "dialog-accept",
									click:
										function() {

											let newData = self.tableMeasures.getAllItems();

											let ms = new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_ALARM_CONFIG_SAVE"), onAccept, null);
											ms.show();

											function onAccept() {

												let alarmConfigMeasuresArr = [];

												let body = {
													alarmConfig: {
														id: alarmConfigData.id,
														alarmTypeId: alarmConfigData.alarmType.id,
														alarmLevelId: alarmConfigData.alarmLevel.id,
														mine: alarmConfigData.mine,
														treatment: alarmConfigData.treatment,
														alarmConfigMeasures: []
													}
												};

												if (Array.isArray(newData) && newData.length > 0) {

													for (let data of newData) {

														alarmConfigMeasuresArr.push({
															elementTypeParamId: data.elementTypeParam.id,
															alarmMeasureOperationId: data.alarmMeasureOperation.id,
															thresholdVal1: data.operationVal1,
															thresholdVal2: data.operationVal2
														});
													}

													body.alarmConfig.alarmConfigMeasures = alarmConfigMeasuresArr;
												}

												webix.ajax().headers(HEADER_POST).put(appConfig.restUrl + self.config.restEndpoints.updateAlarmConfig, JSON.stringify(body)).then(function(updateItemResponse) {

													if (updateItemResponse != null) {

														self.args.callbacks[0]();
													}

												});

												$$(self.args.containerId).close();
											}
										}
								},
								{ width: 15 }
							]
						}
					]
				}

				return viewToolbarActions;
			}

			return viewContent;
		}

	}
}