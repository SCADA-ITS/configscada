import { util } from "../lib/util.js";
import GenericModule from "./genericModule.js";
import DataTable from "../component/datatable.js";
import InputDialogBox from "../component/inputDialogBox.js";
import MessageBox from "../component/messageBox.js";

const NO_VALUE = -1;

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

		if ((self.moduleAction.id === self.config.moduleAction.add) || (self.moduleAction.id === self.config.moduleAction.edit)) {

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
		let alarmConfigStatesByElementType;
		let elementType;
		let alarmLevelOptionsArr = [];
		let elementTypeStateOptionsArr = [];
		let elementSubtypesArr = [];
		let alarmConfigs = window.em.getAll(EntityType.ALARM_CONFIG);
		let alarmConfigsByElementTypeWithState = [];
		let usedStatesList = [];

		if (self.moduleAction.id === self.config.moduleAction.edit) {

			webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.alarmConfig, { id: self.args.selectedPks[0] })).then(function(alarmConfigDataResponse) {

				if (alarmConfigDataResponse != null) {

					alarmConfigData = alarmConfigDataResponse.json();

					webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.elementTypeStates, { id: alarmConfigData.elementType.id })).then(function(elementTypeStatesResponse) {

						if (elementTypeStatesResponse != null) {

							alarmConfigsByElementTypeWithState = alarmConfigs.filter(alarmConf => (alarmConf.elementType.id === alarmConfigData.elementType.id) && (Array.isArray(alarmConf.alarmConfigStates) && alarmConf.alarmConfigStates.length > 0));
							alarmConfigStatesByElementType = elementTypeStatesResponse.json();
							elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", alarmConfigData.elementType.id);

							alarmConfigsByElementTypeWithState.forEach(aConfig => {

								for (let ac of aConfig.alarmConfigStates) {

									usedStatesList.push({
										id: ac.elementTypeState.id,
										alias: (aConfig.labelAlias) ? i18n.t(aConfig.labelAlias) : aConfig.alias,
										alarmConfigId: aConfig.id
									});
								}
							});

							webix.ui(drawContent(), $$(self.uid));

							$$(self.uid).show();
							$$("loading-" + self.uid).hide();

							if (self.tableMeasures) {

								self.tableMeasures.callbacks();
							}

							let titleLabel = (alarmConfigData.labelAlias) ? i18n.t(alarmConfigData.labelAlias) : alarmConfigData.alias;

							util.ui.setTitle(self.args.containerId, i18n.t(alarmConfigData.elementType.labelAlias) + " - " + titleLabel);
							setValuesForm();
						}
					});
				}
			});

		} else if (self.moduleAction.id === self.config.moduleAction.add) {

			alarmConfigsByElementTypeWithState = alarmConfigs.filter(alarmConf => (alarmConf.elementType.id === self.args.elementType.id) && (Array.isArray(alarmConf.alarmConfigStates) && alarmConf.alarmConfigStates.length > 0));
			alarmConfigStatesByElementType = self.args.elementType.elementTypeStates;
			elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", self.args.elementType.id);

			alarmConfigsByElementTypeWithState.forEach(aConfig => {

				for (let ac of aConfig.alarmConfigStates) {

					usedStatesList.push({
						id: ac.elementTypeState.id,
						alias: (aConfig.labelAlias) ? i18n.t(aConfig.labelAlias) : aConfig.alias,
						alarmConfigId: aConfig.id
					});
				}
			});

			webix.ui(drawContent(), $$(self.uid));

			$$(self.uid).show();
			$$("loading-" + self.uid).hide();

			if (self.tableMeasures) {

				self.tableMeasures.callbacks();
			}

			util.ui.setTitle(self.args.containerId, i18n.t("LBL_ADD_NEW_ALARM_CONFIG"));
			setValuesForm();

		} else if (self.moduleAction.id === self.config.moduleAction.delete) {

			deleteAlarmConfig();
		}

		function drawContent() {

			let viewContent;

			viewContent = {
				id: self.uid,
				css: { "box-sizing": "border-box" },
				rows: [
					getHeader(),
					{ height: 15 },
					{
						cols: [
							{ width: 15 },
							getTabs(),
							{ width: 15 }
						]
					},
					getButtons(),
					{ height: 15 }
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

					label = "LBL_ADD_NEW_ALARM_CONFIG";
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
							width: 450,
							id: "label-bottom-" + self.uid,
							template: "<span>" + i18n.t(label) + "</span> <br> <span style='font-size: 13px'>" + i18n.t(description) + "</span>",
							css: "global-header-label"
						}
					]
				};

				return header;
			}

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
					body: getForm(),
				});

				tabs.cells.push({
					header: i18n.t("LBL_ALARM_CONFIG_THRESHOLDS"),
					body: getDataTable()
				});

				tabs.cells.push({
					header: i18n.t("LBL_STATE"),
					body: getDbList()
				});

				function getForm() {

					let alarmLevel = window.em.getAll(EntityType.ALARM_LEVEL);

					alarmLevelOptionsArr = alarmLevel.map(op => {

						let alarmLevelIconArr = op.id.split(":");
						let icon = "./img/grid/alarm_level_" + alarmLevelIconArr[1] + ".png";

						return { id: op.id, value: `<div style="display: flex;"><img style="width:20px; height:20px; padding-right: 10px; padding-top:5px;" src="${icon}"><span>${i18n.t(op.labelAlias)}</span></div>` };
					});

					alarmLevelOptionsArr.sort((a, b) => (a.value > b.value) ? 1 : ((b.value > a.value) ? -1 : 0));
					
					elementTypeStateOptionsArr = alarmConfigStatesByElementType.map(e => {
						
						return {
							id: e.id,
							value: i18n.t(e.labelAlias)
						}
					});
					
					elementTypeStateOptionsArr.push({
						id: NO_VALUE,
						value: "" 
					});

					elementTypeStateOptionsArr.sort((a, b) => (a.value > b.value) ? 1 : ((b.value > a.value) ? -1 : 0));
					
					if(Array.isArray(elementType.elementSubtypes) && elementType.elementSubtypes.length >0){
						
						elementSubtypesArr = elementType.elementSubtypes.map(e => {
							
							return {
								id: e.id,
								value: i18n.t(e.labelAlias)
							}
						});
						
						if (Array.isArray(elementSubtypesArr) && elementSubtypesArr.length > 0) {
							
							elementSubtypesArr.push({
								
								id: NO_VALUE,
								value: ""
							});
							
							elementSubtypesArr.sort((a, b) => (a.value > b.value) ? 1 : ((b.value > a.value) ? -1 : 0));
						}
					}

					let form;
					
					form = {
						id: "form-" + self.uid,
						view: "form",
						css: "user-role-form",
						elementsConfig: { labelAlign: "left" },
						elements: [
							{
								cols: [
									{ width: 45 },
									{
										rows: [
											{},
											{ view: "text", id: "label_alias-" + self.uid, name: "labelAlias", required: true, label: i18n.t("LBL_ALIAS"), labelWidth: 275 },
											{ view: "text", id: "element_type_id-" + self.uid, name: "elementTypeId", hidden: true },
											{ view: "text", id: "element_type-" + self.uid, name: "elementTypeLabelAlias", required: true, label: i18n.t("LBL_ELEMENT_TYPE"), labelWidth: 275, disabled: true },
											{
												view: "richselect",
												id: "element_type_subtype-" + self.uid,
												name: "elementSubtypeId",
												label: i18n.t("LBL_ELEMENT_SUBTYPE"),
												labelWidth: 275,
												options: elementSubtypesArr,
												hidden: (Array.isArray(elementType.elementSubtypes) && elementType.elementSubtypes.length >0) ? false : true,
												required: false
											},
											{
												cols: [
													{
														view: "richselect",
														id: "alarm_level-" + self.uid,
														name: "alarmLevelId",
														label: i18n.t("LBL_ALARM_LEVEL"),
														labelWidth: 275,
														options: alarmLevelOptionsArr,
														required: true,
														on: {
															onChange: function(newv) {

																let isCritical = self.config.levels.critical.includes(newv) ? true : false;
																let isWarn = self.config.levels.warn.includes(newv) ? true : false;
																let isInfo = self.config.levels.info.includes(newv) ? true : false;

																let iconInfo = "./img/grid/info.png";
																let iconWarn = "./img/grid/warn.png";
																let iconCritical = "./img/grid/critical.png";
																let labelInfo = "LBL_ALARM_LEVEL_INFO";
																let labelWarn = "LBL_ALARM_LEVEL_WARN";
																let labelCritical = "LBL_ALARM_LEVEL_CRITICAL";
																let icon = isCritical ? iconCritical : isWarn ? iconWarn : isInfo ? iconInfo : "";
																let vlabel = isCritical ? labelCritical : isWarn ? labelWarn : isInfo ? labelInfo : "";

																let iconLevel = $$("icon-" + self.uid);

																if (iconLevel) {

																	iconLevel.setValues(`<img title="${i18n.t(vlabel)}" style="width:20px; height:20px; padding: 10px;" src="${icon}">`);
																}
															}
														}
													},
													{
														view: "template",
														id: "icon-" + self.uid,
														borderless: true,
														width: 38,
														height: 38,
														template: function(obj) {

															return (typeof (obj) === "object") ? `<img style="width:20px; height:20px; padding: 10px" src="./img/grid/empty.png">` : obj;
														}
													}
												]
											},
											{
												view: "richselect",
												id: "element_type_state-" + self.uid,
												name: "elementTypeStateId",
												label: i18n.t("LBL_ELEMENT_TYPE_STATE_WITH_ALARM"),
												labelWidth: 275,
												options: elementTypeStateOptionsArr,
												required: false
											},
											{
												view: "checkbox",
												id: "treatment-" + self.uid,
												name: "treatment",
												label: i18n.t("LBL_TREATMENT_REQUIRED"),
												labelWidth: 275,
												value: false
											},
											{}
										]
									},
									{ width: 45 }
								]
							}
						],
						rules: {
							"labelAlias": webix.rules.isNotEmpty,
							"elementTypeId": webix.rules.isNotEmpty,
							"elementTypeLabelAlias": webix.rules.isNotEmpty,
							"alarmLevelId": webix.rules.isNotEmpty,
							"elementTypeStateId": webix.rules.isNotEmpty
						}
					};

					return form;
				}

				function getDataTable() {

					let data = [];

					if (alarmConfigData && Array.isArray(alarmConfigData.alarmConfigMeasures) && alarmConfigData.alarmConfigMeasures.length > 0) {

						for (let configMeasures of alarmConfigData.alarmConfigMeasures) {

							data.push({
								id: configMeasures.elementTypeParam.id,
								elementAlias: alarmConfigData.elementType.labelDescription,
								parameter: i18n.t(configMeasures.elementTypeParam.labelAlias),
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
						{ gridOption: { id: 2 }, value: "true" },
						{ gridOption: { id: 3 }, value: "2" },
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

							let elementTypeValue = undefined;

							if ($$("element_type-" + self.uid)) {

								elementTypeValue = $$("element_type_id-" + self.uid).getValue();
							}

							if (elementTypeValue === "" || elementTypeValue === undefined) {

								let ms = new MessageBox(MessageBox.WARN, i18n.t("LBL_ELEMENT_TYPE_REQUIRED"), null, null);
								ms.show();

							} else {

								getParamWindow();
							}
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
						//let labelAlias = (alarmConfigData && alarmConfigData.elementType.labelAlias) ? i18n.t(alarmConfigData.elementType.labelAlias) : i18n.t("LBL_NEW_THRESHOLD");
						let windowTitle = (alarmConfigData && alarmConfigData.elementType.labelAlias) ? i18n.t("LBL_EDIT_THRESHOLD") : i18n.t("LBL_NEW_THRESHOLD");

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
												//{ view: "template", template: labelAlias, type: "section" },
												{ height: 20 },
												getElementTypeParamRow(),
												getThresholdOperationRow(),
												getThresholdVal1Row(),
												getThresholdVal2Row(),
												{ height: 20 }
											]
										},
										getToolbarActions()
									]
								},
								{ width: 6 }
							]
						};

						self.paramWindow = util.ui.loadWindow(windowTitle, paramWindowBody, 360, 755, true);

						function getElementTypeParamRow() {

							let elementTypeParamRow;
							let paramOptions = [];
							let elementTypeParamArr = []
							let actualParams = self.tableMeasures.getAllItems();
							let elementTypeParamMeasures = [];

							if (alarmConfigData && alarmConfigData.elementType.id) {

								elementTypeParamArr = window.em.getByField(EntityType.ELEMENT_TYPE, "id", alarmConfigData.elementType.id).elementTypeParams;

							} else {

								if ($$("element_type-" + self.uid)) {

									let elementTypeValue = $$("element_type_id-" + self.uid).getValue();
									elementTypeParamArr = window.em.getByField(EntityType.ELEMENT_TYPE, "id", elementTypeValue).elementTypeParams;
								}
							}

							if (Array.isArray(elementTypeParamArr) && elementTypeParamArr.length > 0) {

								self.elementTypeParamMeasures = elementTypeParamArr.filter(e => e.paramType.id === self.config.paramTypeMeasure);
								elementTypeParamMeasures = webix.copy(self.elementTypeParamMeasures);
							}

							if (Array.isArray(actualParams) && actualParams.length > 0) {

								actualParams.forEach(e => {

									let sameTypeParam = elementTypeParamMeasures.find(item => item.id === e.id);

									if (sameTypeParam) {

										elementTypeParamMeasures.splice(elementTypeParamMeasures.indexOf(sameTypeParam), 1);
									}
								});
							}

							for (let elementTypeParam of elementTypeParamMeasures) {

								paramOptions.push({
									id: elementTypeParam.id,
									value: i18n.t(elementTypeParam.labelAlias)
								});
							}

							paramOptions.sort((a, b) => (a.value > b.value) ? 1 : ((b.value > a.value) ? -1 : 0));

							elementTypeParamRow = {
								cols: [
//									{
//										view: "label",
//										label: i18n.t("LBL_ALARM_CONFIG_THRESHOLD"),
//										width: 326,
//										css: "parameter-window"
//									},
									{
										id: "change-condition-element-type-param-row-" + self.uid,
										view: "richselect",
										label: i18n.t("LBL_PARAM"),
										labelWidth: 326,
										required: true,
										width: 616,
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

										self.operationsOptionsArr.sort((a, b) => (a.value > b.value) ? 1 : ((b.value > a.value) ? -1 : 0));

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
//									{
//										view: "label",
//										label: i18n.t("LBL_THRESHOLD_OPERATION"),
//										width: 326,
//										css: "parameter-window"
//									},
									{
										id: "change-condition-threshold-operation-row-" + self.uid,
										label: i18n.t("LBL_THRESHOLD_OPERATION"),
										labelWidth: 326,
										required: true,
										width: 616,
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

												onInputValue($$("value1" + self.uid));
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

												onInputValue($$("value2" + self.uid));
											}
										}
									},
									{ width: 90 }
								]
							};

							return thresholdVal2Row;
						}

						function onInputValue(viewInput) {

							let param = $$("change-condition-element-type-param-row-" + self.uid);
							let input = viewInput;
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

								if (dataType.id != DataType.BOOLEAN) {

									inputValue = input.getValue();
								}
								else {

									inputValue = input.getValue() ? IS_TRUE(input.getValue()) : false;
								}
							}

							let i = new InputDialogBox(i18n.t("LBL_THRESHOLD_OPERATION_ONE"), i18n.t("LBL_ENTER_VALUE"), onAccept, false, inputValue, dataType);
							i.show();

							function onAccept(value) {

								viewInput.setValue(value.toString());
							}
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

														if (paramValue != "" && operation != "") {

															val1 = (val1 === undefined) ? "" : val1;
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

				function getDbList() {

					let dbList;
					let dbListData = [];
					let dbListSelected = [];
					let elemenTypeId = (self.moduleAction.id === self.config.moduleAction.edit) ? self.args.selectedPks[0] : self.args.elementType.id;

					if (Array.isArray(usedStatesList) && usedStatesList.length > 0) {

						for (let usedState of usedStatesList) {

							let used = alarmConfigStatesByElementType.find(state => state.id === usedState.id);

							if (used) {

								used.disabled = true;
								used.usedIn = (usedState.alarmConfigId === elemenTypeId) ? "" : usedState.alias;
							}
						}
					}

					for (let acState of alarmConfigStatesByElementType) {

						let disabled = (acState.disabled) ? true : false;
						let acUsed = (acState.usedIn) ? " " + "(" + acState.usedIn + ")" : "";

						dbListData.push({
							id: acState.id,
							value: `<div title="${i18n.t(acState.labelAlias)}  ${acUsed}"><span>${i18n.t(acState.labelAlias)}  ${acUsed}</span></div>`,
							disabled: disabled
						});
					}

					dbListData.sort((a, b) => (a.value > b.value) ? 1 : ((b.value > a.value) ? -1 : 0));

					if (self.moduleAction.id === self.config.moduleAction.edit) {

						if (Array.isArray(alarmConfigData.alarmConfigStates) && alarmConfigData.alarmConfigStates.length > 0) {

							for (let acState of alarmConfigData.alarmConfigStates) {

								let state = dbListData.find(state => state.id === acState.elementTypeState.id);

								if (state) {

									state.disabled = false;

									dbListSelected.push(
										state.id
									);
								}
							}
						}
					}

					dbList = {
						css: { "box-sizing": "border-box", "border-bottom": "1px solid #e2e2e2", "border-left": "1px solid #e2e2e2", "border-right": "1px solid #e2e2e2" },
						cols: [
							{ width: 10 },
							{
								rows: [
									{ height: 10 },
									{
										cols: [
											{ width: 15 },
											{
												view: "label",
												align: "center",
												label: i18n.t("LBL_ALARM_CONFIG_DBLIST_DESCRIPTION")
											},
											{ width: 15 }
										]
									},
									{
										id: "view-dbllist-" + self.uid,
										view: "dbllist",
										css: "dbllist",
										labelLeft: i18n.t("LBL_AVAILABLE"),
										labelRight: i18n.t("LBL_SELECTED"),
										data: dbListData,
										value: dbListSelected,
										on: {
											onChange: function() {

												this.blockEvent();

												let selectedItemsArr = this.getValue().split(",");

												for (let selectedItem of selectedItemsArr) {

													let itemToDisable = dbListData.find(item => item.id === selectedItem && item.disabled);

													if (itemToDisable) {

														this.select(selectedItem, false);
													}
												}

												this.unblockEvent();
											}
										}
									},
									{ height: 10 }
								]
							},
							{ width: 10 }
						]
					};

					return dbList;
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

											let validation = $$("form-" + self.uid).validate({ disabled: true });

											if (validation) {

												let ms = new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_ALARM_CONFIG_SAVE"), onAccept, null);
												ms.show();

											} else {

												$$("form-" + self.uid).show();
											}

											function onAccept() {

												let alarmConfigMeasuresArr = [];
												let alarmConfigStatesArr = [];
												let formValues = $$("form-" + self.uid).getValues();
												let dbListValues = $$("view-dbllist-" + self.uid).getValue();

												if (self.moduleAction.id === self.config.moduleAction.edit) {

													let body = {
														alarmConfig: {
															id: alarmConfigData.id,
															labelAlias: !i18n.isTranslatable(formValues.labelAlias) ? formValues.labelAlias : undefined,
															elementTypeId: formValues.elementTypeId,
															alarmTypeId: self.config.defaultAlarmType,
															alarmLevelId: formValues.alarmLevelId,
															mine: alarmConfigData.mine,
															treatment: (formValues.treatment) ? true : false,
															elementTypeStateId: formValues.elementTypeStateId != NO_VALUE ? formValues.elementTypeStateId : undefined, 
															alarmConfigMeasures: [],
															alarmConfigStates: [],
															elementSubtypeId: formValues.elementSubtypeId != NO_VALUE ? formValues.elementSubtypeId : undefined
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

													if (dbListValues != "") {

														let dbListValuesArr = dbListValues.split(",");

														for (let value of dbListValuesArr) {

															alarmConfigStatesArr.push({
																elementTypeId: formValues.elementTypeId,
																stateId: value
															});
														}
														
														if (Array.isArray(alarmConfigStatesArr) && alarmConfigStatesArr.length > 0) {
															
															body.alarmConfig.elementTypeStateId = undefined;
														}

														body.alarmConfig.alarmConfigStates = alarmConfigStatesArr;
													}

													webix.ajax().headers(HEADER_POST).put(appConfig.restUrl + self.config.restEndpoints.updateAlarmConfig, JSON.stringify(body)).then(function(updateItemResponse) {

														if (updateItemResponse != null) {

															let updateItem = updateItemResponse.json();
															window.em.update(EntityType.ALARM_CONFIG, updateItem.id, updateItem);

															if (self.args.callbacks) {

																self.args.callbacks[0]();
															}
														}
													});

												} else if (self.moduleAction.id === self.config.moduleAction.add) {

													let body = {
														alarmConfig: {
															alias: formValues.labelAlias,
															labelAlias: formValues.labelAlias,
															elementTypeId: formValues.elementTypeId,
															elementTypeStateId: formValues.elementTypeStateId != NO_VALUE ? formValues.elementTypeStateId : undefined, 
															alarmLevelId: formValues.alarmLevelId,
															alarmTypeId: self.config.defaultAlarmType,
															mine: true,
															treatment: (formValues.treatment) ? true : false,
															alarmConfigMeasures: [],
															elementSubtypeId: formValues.elementSubtypeId !== NO_VALUE ? formValues.elementSubtypeId : undefined
														}
													}

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

													if (dbListValues != "") {

														let dbListValuesArr = dbListValues.split(",");

														for (let value of dbListValuesArr) {

															alarmConfigStatesArr.push({
																elementTypeId: formValues.elementTypeId,
																stateId: value
															});
														}
														
														if (Array.isArray(alarmConfigStatesArr) && alarmConfigStatesArr.length > 0) {
															
															body.alarmConfig.elementTypeStateId = undefined;
														}

														body.alarmConfig.alarmConfigStates = alarmConfigStatesArr;
													}

													webix.ajax().headers(HEADER_POST).post(appConfig.restUrl + "alarms/createAlarmConfig", JSON.stringify(body)).then(function(createItemResponse) {

														if (createItemResponse != null) {

															let createItem = createItemResponse.json();
															window.em.add(EntityType.ALARM_CONFIG, createItem.id, createItem);
														}

														if (self.args.callbacks) {

															self.args.callbacks[0]();
														}
													});
												}

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

		function deleteAlarmConfig() {

			if (self.args.selectedPks != undefined && self.args.selectedPks[0] && self.args.selectedPks.length > 0) {

				let ms = new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_QUESTION_DELETE_ALARM_CONFIG_DESCRIPTION"), onAccept, null);
				ms.show();

				function onAccept() {

					let itemsReponse = 0;
					self.args.selectedPks.forEach(e => {

						webix.ajax().del(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.deleteAlarmConfig, { id: e })).then(function(deleteResponse) {

							if (deleteResponse != null) {

								let deleteItem = deleteResponse.json();
								window.em.delete(EntityType.ALARM_CONFIG, deleteItem.id);
							}

							if (self.args.selectedPks.length === ++itemsReponse) {

								self.args.callbacks[0]();
							}
						});
					});
				}
			}
		}

		function setValuesForm() {

			let form = $$("form-" + self.uid);
			let alarmLevelSelect = $$("alarm_level-" + self.uid);
			let elementTypeStateSelect = $$("element_type_state-" + self.uid);
			let labelAliasSelect = $$("label_alias-" + self.uid);
			let elementSubtype = $$("element_type_subtype-" + self.uid);

			if (form) {

				if (alarmConfigData) {

					form.setValues({
						labelAlias: i18n.t(alarmConfigData.labelAlias),
						elementTypeId: alarmConfigData.elementType.id,
						elementTypeLabelAlias: i18n.t(alarmConfigData.elementType.labelAlias),
						treatment: (alarmConfigData.treatment) ? true : false,
						changeEquipmentState: (alarmConfigData.elementTypeState) ? true : false, 
					});

					let alarmLevel = alarmLevelOptionsArr.find(alarmLvl => alarmConfigData.alarmLevel && alarmLvl.id === alarmConfigData.alarmLevel.id);
					alarmLevelSelect.enable();

					if (alarmLevel) {

						alarmLevelSelect.setValue(alarmLevel.id);
					}
					
					let elementTypeState = elementTypeStateOptionsArr.find(e => alarmConfigData.elementTypeState && e.id === alarmConfigData.elementTypeState.id);
					elementTypeStateSelect.enable();
					
					if (elementTypeState) {

						elementTypeStateSelect.setValue(elementTypeState.id);
					}
					else {
						
						elementTypeStateSelect.setValue(NO_VALUE);
					}
					
					if (i18n.isTranslatable(alarmConfigData.labelAlias)) {
						
						labelAliasSelect.disable();
					}
					
					if(Array.isArray(elementType.elementSubtypes) && elementType.elementSubtypes.length > 0 && Array.isArray(self.args.selectedRows) && self.args.selectedRows.length > 0){
						
						let subtype = elementType.elementSubtypes.find(subtype => subtype.id === alarmConfigData.elementSubtype.id); 
						
						if (subtype){

							elementSubtype.setValue(subtype.id);
						}
					}

				} else {
					
					let defaultElementTypeStateId = self.args.elementType.id.replace("ElementType", "ElementTypeState") + ":" + self.config.defaultElementTypeStateAlarmId; 
					
					let elementTypeState = alarmConfigStatesByElementType.find(e => e.id === defaultElementTypeStateId);

					form.setValues({
						elementTypeId: self.args.elementType.id,
						elementTypeLabelAlias: i18n.t(self.args.elementType.labelAlias),
						elementTypeStateId: elementTypeState ? elementTypeState : NO_VALUE
					});
				}
			}
		}
	}
}