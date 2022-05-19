import { util } from "../lib/util.js";
import GenericModule from "../modules/genericModule.js";
import DataTable from "../component/datatable.js";

export default class ConfigModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.module = module;
		this.moduleAction = moduleAction;
		this.args = args;
		this.thresholdOperations = undefined;
		this.systemParams = undefined;
		this.thresholdTable = undefined;

		this.uid = webix.uid();
	}


	getView() {

		let self = this;
		let view;

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

		return view;
	}


	loadData() {

		let self = this;

		webix.ajax().get(appConfig.restUrl + "thresholdOperations").then(function(thresholdResponse) {

			if (thresholdResponse != null) {

				self.thresholdOperations = thresholdResponse.json();

				if (Array.isArray(self.thresholdOperations) && self.thresholdOperations.length > 0) {

					webix.ajax().get(appConfig.restUrl + "system_params").then(function(systemParamsResponse) {

						if (systemParamsResponse != null) {

							self.systemParams = systemParamsResponse.json();

							webix.ui(drawContent(), $$(self.uid));
							$$("loading-" + self.uid).hide();

							if (self.thresholdTable) {

								self.thresholdTable.callbacks();
							}
						}
					});
				}
			}
		});

		function drawContent() {

			let viewMain;

			viewMain = {
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

				let header;

				header = {
					id: "viewHeader-" + self.uid,
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

			function getTabs() {

				let tabs;

				tabs = {
					view: "tabview",
					id: "tabview-" + self.uid,
					borderless: true,
					css: "secondary-tab",
					cells: [
						{
							header: i18n.t("LBL_GENERAL_CONFIG"),
							body: getThresholdConfig()
						},
						{
							header: i18n.t("LBL_SYSTEM_PARAMS_CONFIG"),
							body: getSystemParamsConfig()
						}
					],
					tabbar: {
						tabMargin: 6,
					}
				}

				function getThresholdConfig() {

					let data = self.thresholdOperations;

					const defaultGridOptionValues = [
						{ gridOption: { id: 1 }, value: "true" },
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
								label: "LBL_THRESHOLD_OPERATION",
								needsTranslation: false,
								parameterized: false,
								position: 2,
								required: true,
								sourceField: "labelAlias",
								visible: true,
								widthPx: 100,
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

							//						let configMeasuresSelected = self.tableMeasures.getSelectedItems();
							//						getParamWindow(configMeasuresSelected[0]);
						}
					});

					events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
						commandId: 2,
						function: () => {

							//						getParamWindow();
						}
					});

					events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
						commandId: 3,
						function: (dataArr) => {

							if (Array.isArray(dataArr) && dataArr.length > 0) {

								let configsToDelete = [];

								for (let dat of dataArr) {

									configsToDelete.push({
										id: dat
									});
								}

								self.thresholdTable.delete(configsToDelete);
							}
						}
					});

					self.thresholdTable = new DataTable(null, null, metadata, data, events);

					let table = {
						css: { "box-sizing": "border-box", "border-bottom": "1px solid #e2e2e2", "border-left": "1px solid #e2e2e2", "border-right": "1px solid #e2e2e2" },
						rows: self.thresholdTable.getView()
					};

					return table;
				}

				function getSystemParamsConfig() {

					let systemParamsDetails;

					if (Array.isArray(self.systemParams) && self.systemParams.length > 0) {

						self.systemParams.sort((a, b) => (a.id > b.id) ? 1 : ((b.id > a.id) ? -1 : 0));

						systemParamsDetails = {
							id: "form-" + self.uid,
							view: "form",
							css: "user-role-form",
							elementsConfig: { labelAlign: "left" },
							elements: [
								{
									cols: [
										{ width: 100 },
										{
											rows: [
												{},
												{ view: "text", id: "system-param-1-" + self.uid, name: self.systemParams[0].id, required: false, label: i18n.t(self.systemParams[0].labelAlias), value: self.systemParams[0].value, labelWidth: 400 },
												{ view: "text", id: "system-param-2-" + self.uid, name: self.systemParams[1].id, required: false, label: i18n.t(self.systemParams[1].labelAlias), value: self.systemParams[1].value, labelWidth: 400 },
												{ view: "text", id: "system-param-3-" + self.uid, name: self.systemParams[2].id, required: false, label: i18n.t(self.systemParams[2].labelAlias), value: self.systemParams[2].value, labelWidth: 400 },
												{ view: "text", id: "system-param-4-" + self.uid, name: self.systemParams[3].id, required: false, label: i18n.t(self.systemParams[3].labelAlias), value: self.systemParams[3].value, labelWidth: 400 },
												{ view: "text", id: "system-param-5-" + self.uid, name: self.systemParams[4].id, required: false, label: i18n.t(self.systemParams[4].labelAlias), value: self.systemParams[4].value, labelWidth: 400 },
												{ view: "text", id: "system-param-6-" + self.uid, name: self.systemParams[5].id, required: false, label: i18n.t(self.systemParams[5].labelAlias), value: self.systemParams[5].value, labelWidth: 400 },
												{ view: "text", id: "system-param-7-" + self.uid, name: self.systemParams[6].id, required: false, label: i18n.t(self.systemParams[6].labelAlias), value: self.systemParams[6].value, labelWidth: 400 },
												{ view: "text", id: "system-param-8-" + self.uid, name: self.systemParams[7].id, required: false, label: i18n.t(self.systemParams[7].labelAlias), value: self.systemParams[7].value, labelWidth: 400 },
												{ view: "text", id: "system-param-9-" + self.uid, name: self.systemParams[8].id, required: false, label: i18n.t(self.systemParams[8].labelAlias), value: self.systemParams[8].value, labelWidth: 400 },
												{}
											]
										},
										{ width: 100 }
									]
								}
							]
						};

						return systemParamsDetails;
					}
				}

				return tabs;
			}

			function getButtons() {

				let viewToolbarActions = {};

				viewToolbarActions = {
					view: "toolbar",
					height: 45,
					borderless: true,
					elements: [
						{ width: 15 },
						{
							view: "button",
							css: "dialog-cancel",
							width: 130,
							height: 35,
							value: i18n.t("LBL_CANCEL"),
							click: function() {
								$$(self.args.containerId).close();
							}
						},
						{},
						{
							id: "button-accept-" + self.uid,
							view: "button",
							css: "dialog-accept",
							width: 130,
							height: 35,
							value: i18n.t("LBL_ACCEPT"),
							click: function() {

								console.log($$("form-" + self.uid).getValues());
							}
						},
						{ width: 15 }
					]
				}

				return viewToolbarActions;
			}

			return viewMain;
		}
	}
}