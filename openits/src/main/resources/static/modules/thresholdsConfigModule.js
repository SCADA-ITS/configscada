import { util } from "../lib/util.js";
import GenericModule from "../modules/genericModule.js";
import DataTable from "../component/datatable.js";
import InputDialogBox from "../component/inputDialogBox.js";
import MessageBox from "../component/messageBox.js";
import ListView from "../component/listview.js";

export default class ThresholdsConfigModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.module = module;
		this.moduleAction = moduleAction;
		this.args = args;
		this.config = module.config;
		this.stateMachines = undefined;
		this.thresholdTable = undefined;
		this.listView = undefined;

		this.uid = webix.uid();
	}


	getView() {

		let self = this;
		let view;

		view = {
			css: "custom_window",
			cols: [
				{
					css: { "background-color": "white" },
					rows: [
						{ height: 6 },
						{
							cols: [
								{ width: 6 },
								{
									id: self.uid,
									rows: [util.ui.getViewDrawLoading()]
								},
								{ width: 6 }
							]
						},
						{ height: 6 }
					]
				}
			]
		};

		return view;
	}


	loadData() {

		let self = this;
		let smThresholdsMap = new Map();
		let thresholdsValuesMap = new Map();
		let stateMachinesArr = [];
		let windowLabel = undefined;;

		webix.ajax().get(appConfig.restUrl + self.config.restEndpoints.stateMachineIds).then(function(stateMachinesResponse) {

			if (stateMachinesResponse != null) {

				self.stateMachines = stateMachinesResponse.json();

				if (Array.isArray(self.stateMachines) && self.stateMachines.length > 0) {

					self.stateMachines.forEach(sm => {

						for (let smThreshold of sm.smThresholds) {

							let stateMachinesSet = new Set();

							if (smThresholdsMap.has(smThreshold.id)) {

								let item = smThresholdsMap.get(smThreshold.id);
								item.add(sm.id);

							} else {

								stateMachinesSet.add(sm.id)
								smThresholdsMap.set(smThreshold.id, stateMachinesSet);
								thresholdsValuesMap.set(smThreshold.id, smThreshold.value);
							}
						}
					});

					if (self.moduleAction && self.moduleAction.id === self.config.moduleAction.fromOtherModule) {

						for (let smId of self.args.selectedPks) {

							let smToShow = self.stateMachines.find(sm => sm.id === smId);

							if (smToShow) {

								stateMachinesArr.push(smToShow);
								windowLabel = smToShow.labelAlias;
							}
						}

					} else {

						stateMachinesArr = webix.copy(self.stateMachines);
					}
				}

				load();
			}
		});


		function load() {

			webix.ui(drawContent(), $$(self.uid));
			//			$$("loading-" + self.uid).hide();

			if (self.thresholdTable) {

				self.thresholdTable.callbacks();
			}

			if (self.listView) {

				self.listView.selectByIndex(0);
			}

			let label = (windowLabel) ? i18n.t(windowLabel) : i18n.t("LBL_STATE_MACHINES");
			util.ui.setTitle(self.args.containerId, label, null);


			function drawContent() {

				let viewMain;

				if (Array.isArray(stateMachinesArr) && stateMachinesArr.length > 0) {

					viewMain = {
						id: self.uid,
						cols: [
							{
								hidden: (stateMachinesArr.length > 1) ? false : true,
								header: function() {

									return `<div>
											<span class='ico webix_icon mdi mdi-view-headline'></span>
											<span>${i18n.t("LBL_STATE_MACHINES")}</span>
										</div>`;
								},
								body: {
									width: 350,
									rows: [getList()]
								}
							},
							{
								hidden: (stateMachinesArr.length > 1) ? false : true,
								view: "resizer"
							},
							{
								id: "table-" + self.uid,
								rows: [getTable()]
							},
							{
								id: "no-data-" + self.uid,
								rows: [util.ui.getViewDrawLoading()]
							}
						]
					};

				} else {

					viewMain = {
						id: self.uid,
						cols: [util.ui.getViewNoData()]
					}
				}



				function getList() {

					stateMachinesArr.forEach(item => {
						item.labelAlias = i18n.t(item.labelAlias);
						item.alias = item.labelAlias;
						item.labelDescription = i18n.t(item.labelDescription);
						item.description = item.labelDescription;
					});

					stateMachinesArr.sort((a, b) => (a.id > b.id) ? 1 : ((b.id > a.id) ? -1 : 0));

					let options = {};

					options[ListView.Options.MODE] = ListView.DrawMode.DEFAULT;

					let events = {};

					events[ListView.EventTypes.ON_SELECT_ROW] = function(ids) {

						$$("no-data-" + self.uid).show();
						$$("table-" + self.uid).hide();

						self.thresholdTable.clear();

						let itemSelected = stateMachinesArr.find(sm => sm.id === ids[0]);
						//let smThresholdsCopy = webix.copy(itemSelected.smThresholds);

						for (let smThreshold of itemSelected.smThresholds) {

							let item = smThresholdsMap.get(smThreshold.id);
							let itemValue = thresholdsValuesMap.get(smThreshold.id);
							let smLinkedIds = Array.from(item).filter(e => e != itemSelected.id);

							let smLinkedLabelAlias = smLinkedIds.map(e => {

								let labelAlias = undefined;
								let smId = self.stateMachines.find(sm => sm.id === e);

								if (smId) {

									labelAlias = i18n.t(smId.labelAlias);
								}
								return labelAlias;
							});

							smThreshold.linked = smLinkedLabelAlias.join(", ");
							smThreshold.value = itemValue;
							smThreshold.labelAlias = i18n.t(smThreshold.labelAlias);
						}

						self.thresholdTable.add(itemSelected.smThresholds);

						$$("table-" + self.uid).show();
						$$("no-data-" + self.uid).hide();
					}

					self.listView = new ListView(stateMachinesArr, options, events);

					return self.listView.getView();
				}

				function getHeader() {

					let header;

					header = {
						id: "viewHeader-" + self.uid,
						css: { "background-color": "white" },
						cols: [
							{
								view: "label",
								label: "<img src='img/grid/config.png' class='grid-icon'/>",
								height: 75,
								css: "global-header-icon"
							},
							{
								view: "label",
								height: 75,
								id: "label-bottom-" + self.uid,
								template: "<span>" + i18n.t("LBL_CONFIG_MODULE") + "</span> <br> <span style='font-size: 13px'>" + i18n.t("LBL_CONFIG_MODULE_DESCRIPTION") + "</span>",
								css: "global-header-label"
							}
						]
					};

					return header;
				};

				function getTable() {

					let tabs;

					tabs = {
						id: "tabview-" + self.uid,
						rows: getThresholdConfig()
					};

					function getThresholdConfig() {

						const defaultGridOptionValues = [
							{ gridOption: { id: 1 }, value: "false" },
							{ gridOption: { id: 2 }, value: "false" },
							{ gridOption: { id: 3 }, value: "9" },
							{ gridOption: { id: 4 }, value: "asc" },
							{ gridOption: { id: 7 }, value: "true" },
							{ gridOption: { id: 8 }, value: "false" },
							{ gridOption: { id: 9 }, value: "true" },
							{ gridOption: { id: 11 }, value: "false" },
							{ gridOption: { id: 13 }, value: "true" },
							{ gridOption: { id: 16 }, value: "img/grid/config.png" },
							{ gridOption: { id: 21 }, value: "custom" },
							{ gridOption: { id: 23 }, value: "false" }
						];

						let metadata = {
							filter: true,
							label: "LBL_CONFIG_THRESHOLDS",
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
									label: "LBL_THRESHOLD",
									needsTranslation: true,
									parameterized: false,
									position: 2,
									required: true,
									sourceField: "labelAlias",
									visible: true,
									widthPx: 350,
									//adjust: true,
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
									label: "LBL_THRESHOLD_VALUE",
									needsTranslation: false,
									parameterized: false,
									position: 3,
									required: true,
									sourceField: "value",
									visible: true,
									widthPx: 150,
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
									label: "LBL_LINKED_STATE_MACHINES",
									needsTranslation: true,
									parameterized: false,
									position: 4,
									required: true,
									sourceField: "linked",
									visible: true,
									widthPx: 350,
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
									showText: true,
									position: 1,
									multiselect: false,
									itemRequired: true
								}
							]
						};

						let events = {};
						events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];

						events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
							commandId: 1,
							function: () => {

								let configMeasuresSelected = self.thresholdTable.getSelectedItems();

								let dataType = {
									id: DataType.DECIMAL
								};

								let inputDialogBox = new InputDialogBox(i18n.t(configMeasuresSelected[0].labelAlias), i18n.t("LBL_ENTER_VALUE"), onAccept, false, configMeasuresSelected[0].value, dataType);
								inputDialogBox.show();

								function onAccept(obj) {

									let ms = new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_SAVE_NEW_THRESHOLD_VALUE"),
										function() {

											if (configMeasuresSelected[0].value != obj.toString()) {

												configMeasuresSelected[0].value = obj.toString();
												thresholdsValuesMap.set(configMeasuresSelected[0].id, obj.toString());
												self.thresholdTable.update(configMeasuresSelected);

												webix.extend($$("dt-" + self.thresholdTable.uid), webix.ProgressBar);

												$$("dt-" + self.thresholdTable.uid).showProgress({
													type: "bottom",
													hide: false
												});

												let body = {
													smThresholds: {
														id: configMeasuresSelected[0].id,
														value: obj.toString()
													}
												};

												webix.ajax().headers(CONTENT_TYPE_JSON).put(appConfig.restUrl + self.config.restEndpoints.updateThreshold, body);

												self.thresholdTable.refresh();
												$$("dt-" + self.thresholdTable.uid).hideProgress();
											}
										}, null);
									ms.show();
								}
							}
						});
						
						let data = [];

						self.thresholdTable = new DataTable(null, null, metadata, data, events);

						return self.thresholdTable.getView();
					}

					return tabs;
				}

				return viewMain;
			}
		}
	}
}