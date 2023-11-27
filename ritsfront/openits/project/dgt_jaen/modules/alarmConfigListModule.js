import { util } from "../lib/util.js";
import GenericGridModule from "./genericGridModule.js";
import DataTable from "../component/datatable.js";
import ListView from "../component/listview.js";

const DATA_GRID_ID = 1002;
const ELEMENT_TYPE_STATE_ALARM = 3;

export default class AlarmConfigListModule extends GenericGridModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.config = module.config;
		this.moduleAction = moduleAction;
		this.args = args;
		this.dataTable = undefined;
		this.listView = undefined;
		this.uid = webix.uid();

		this.editAlarmConfigsPermission = util.module.isAllowedPermission(this.config.requiredPermissions.edit);
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
		}

		return view;
	}


	loadData() {

		let self = this;

		webix.ui(drawContent(), $$(self.uid));

		if (self.dataTable) {

			self.dataTable.callbacks();
		}

		if (self.listView) {

			self.listView.selectByIndex(0);
		}

		function drawContent() {

			let main;

			main = {
				id: "main-content-" + self.uid,
				cols: [
					{
						header: function() {

							return `<div>
								<span class='ico webix_icon mdi mdi-view-headline'></span>
								<span>${i18n.t("LBL_EQUIPMENT_TYPES")}</span>
							</div>`;
						},
						body: {
							width: 350,
							rows: [getList()]
						}
					},
					{ view: "resizer" },
					{
						id: "table-" + self.uid,
						rows: getTable()
					},
					{
						id: "no-data-" + self.uid,
						rows: [util.ui.getViewDrawLoading()]
					}
				]
			};

			function getList() {

				let elementTypes = window.em.getAll(EntityType.ELEMENT_TYPE);
				let elementTypesFiltered = webix.copy(elementTypes.filter(e => e.visible === true && e.grid));

				elementTypesFiltered.forEach(item => {

					item.labelAlias = i18n.t(item.labelAlias);
					item.alias = item.labelAlias;
					item.labelAlias = i18n.t(item.labelAlias);
					item.labelDescription = i18n.t(item.labelDescription);
					item.description = item.labelDescription;
				});

				elementTypesFiltered.sort((a, b) => (a.labelAlias > b.labelAlias) ? 1 : ((b.labelAlias > a.labelAlias) ? -1 : 0));

				let options = {};

				options[ListView.Options.MODE] = ListView.DrawMode.DEFAULT;

				let events = {};

				events[ListView.EventTypes.ON_SELECT_ROW] = function(ids) {

					$$("no-data-" + self.uid).show();
					$$("table-" + self.uid).hide();

					webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.alarmConfigByElementType, { id: ids[0] })).then(function(alarmsConfigSelectedResponse) {

						if (alarmsConfigSelectedResponse != null) {

							let alarmsConfigSelected = alarmsConfigSelectedResponse.json();
							
							self.dataTable.clear();

							if (Array.isArray(alarmsConfigSelected) && alarmsConfigSelected.length > 0) {

								let alarmConfigToTable = [];

								for (let alarmConfigSelected of alarmsConfigSelected) {

									alarmConfigToTable.push({
										id: alarmConfigSelected.id,
										labelAlias: (alarmConfigSelected.labelAlias) ? i18n.t(alarmConfigSelected.labelAlias) : alarmConfigSelected.alias,
										alarmLevelLabelAlias: i18n.t(alarmConfigSelected.alarmLevel.labelAlias),
										measures: (alarmConfigSelected.alarmConfigMeasures && alarmConfigSelected.alarmConfigMeasures[0].alarmMeasureOperation) ? true : false,
										alarmLevelId: alarmConfigSelected.alarmLevel.id,
										elementSubtype: (alarmConfigSelected.elementSubtype && alarmConfigSelected.elementSubtype.labelAlias) ? i18n.t(alarmConfigSelected.elementSubtype.labelAlias) : "",
										elementSubtypeId: (alarmConfigSelected.elementSubtype && alarmConfigSelected.elementSubtype.id) ? i18n.t(alarmConfigSelected.elementSubtype.id) : ""
										
									});
								}

								self.dataTable.add(alarmConfigToTable);

								if ($$("table-" + self.uid)) {

									let tableTitle = $$("table-" + self.uid).getNode().querySelector('.grid-title');

									if (tableTitle) {

										tableTitle.textContent = i18n.t("LBL_ALARM_CONFIGS_TITLE") + ": " + i18n.t(alarmsConfigSelected[0].elementType.labelAlias);
									}
								}
							}
							
							$$("table-" + self.uid).show();
							$$("no-data-" + self.uid).hide();
						}
					});
				}

				self.listView = new ListView(elementTypesFiltered, options, events);

				return self.listView.getView();
			}

			function getTable() {

				const defaultGridOptionValues = [
					{ gridOption: { id: 1 }, value: "false" },
					{ gridOption: { id: 2 }, value: "true" },
					{ gridOption: { id: 3 }, value: 4 },
					{ gridOption: { id: 4 }, value: "asc" },
					{ gridOption: { id: 5 }, value: undefined },
					{ gridOption: { id: 6 }, value: undefined },
					{ gridOption: { id: 7 }, value: "true" },
					{ gridOption: { id: 8 }, value: "false" },
					{ gridOption: { id: 9 }, value: "true" },
					{ gridOption: { id: 10 }, value: "true" },
					{ gridOption: { id: 11 }, value: "true" },
					{ gridOption: { id: 12 }, value: "true" },
					{ gridOption: { id: 13 }, value: "true" },
					{ gridOption: { id: 14 }, value: "false" },
					{ gridOption: { id: 15 }, value: "false" },
					{ gridOption: { id: 16 }, value: "img/grid/alarmConfig.png" },
					{ gridOption: { id: 17 }, value: "true" },
					{ gridOption: { id: 20 }, value: "true" }
				];

				let metadata = {
					filter: true,
					label: "LBL_ALARMS",
					visible: true,
					gridOptionValues: defaultGridOptionValues,
					gridFields: [
						{
							id: 1,
							enabled: true,
							extended: false,
							filtered: false,
							groupingSummary: false,
							isEpochMilli: false,
							isPk: true,
							label: "LBL_ID",
							labelTooltip: "LBL_ID",
							needsTranslation: false,
							parameterized: false,
							position: 1,
							required: true,
							sourceField: "id",
							visible: false,
							widthPx: 500,
							gridFieldDataType: {
								id: 1,
								description: "string",
								name: "string"
							}
						},
						{
							id: 3,
							enabled: true,
							extended: false,
							filtered: false,
							groupingSummary: false,
							isEpochMilli: true,
							isPk: false,
							label: "",
							labelTooltip: "",
							needsTranslation: false,
							parameterized: false,
							position: 2,
							required: true,
							sourceField: "alarmLevelId",
							visible: true,
							widthPx: 35,
							gridFieldIcons: [
								{
									id: 5,
									avatar: false,
									badgeColor: false,
									enabled: true,
									icon: "./img/grid/critical.png",
									showValue: false,
									value: "AlarmLevel:6",
									labelValue: "LBL_ALARM_LEVEL_CLASIF_CRITICAL"
								},
								{
									id: 6,
									avatar: false,
									badgeColor: false,
									enabled: true,
									icon: "./img/grid/warn.png",
									showValue: false,
									value: "AlarmLevel:5",
									labelValue: "LBL_ALARM_LEVEL_CLASIF_WARNING"
								},
								{
									id: 7,
									avatar: false,
									badgeColor: false,
									enabled: true,
									icon: "./img/grid/info.png",
									showValue: false,
									value: "AlarmLevel:4",
									labelValue: "LBL_ALARM_LEVEL_CLASIF_INFO"
								},
								{
									id: 8,
									avatar: false,
									badgeColor: false,
									enabled: true,
									icon: "./img/grid/critical.png",
									showValue: false,
									value: "AlarmLevel:3",
									labelValue: "LBL_ALARM_LEVEL_CLASIF_CRITICAL"
								},
								{
									id: 9,
									avatar: false,
									badgeColor: false,
									enabled: true,
									icon: "./img/grid/warn.png",
									showValue: false,
									value: "AlarmLevel:2",
									labelValue: "LBL_ALARM_LEVEL_CLASIF_WARNING"
								},
								{
									id: 10,
									avatar: false,
									badgeColor: false,
									enabled: true,
									icon: "./img/grid/info.png",
									showValue: false,
									value: "AlarmLevel:1",
									labelValue: "LBL_ALARM_LEVEL_CLASIF_INFO"
								}
							],

							gridFieldDataType: {
								id: 1,
								description: "string",
								name: "string"
							}
						},
						{
							id: 4,
							enabled: true,
							extended: false,
							filtered: false,
							groupingSummary: false,
							isEpochMilli: false,
							isPk: false,
							label: "LBL_ALIAS",
							labelTooltip: "LBL_ALIAS",
							needsTranslation: true,
							parameterized: false,
							position: 3,
							required: true,
							sourceField: "labelAlias",
							visible: true,
							widthPx: 600,
							gridFieldDataType: {
								id: 1,
								description: "string",
								name: "string"
							}
						},
						{
							id: 7,
							enabled: true,
							extended: false,
							filtered: true,
							groupingSummary: false,
							isEpochMilli: false,
							isPk: false,
							label: "LBL_ELEMENT_SUBTYPE",
							labelTooltip: "LBL_ELEMENT_SUBTYPE",
							needsTranslation: true,
							parameterized: true,
							position: 4,
							required: true,
							sourceField: "elementSubtype",
							visible: true,
							widthPx: 500,
							gridFieldDataType: {
								id: 1,
								description: "string",
								name: "string"
							}
						},
						{
							id: 5,
							enabled: true,
							extended: false,
							filtered: false,
							groupingSummary: false,
							isEpochMilli: false,
							isPk: false,
							label: "LBL_ALARM_CONFIG_THRESHOLDS",
							labelTooltip: "LBL_ALARM_CONFIG_THRESHOLDS",
							needsTranslation: false,
							parameterized: false,
							position: 5,
							required: true,
							sourceField: "measures",
							visible: true,
							widthPx: 100,
							gridFieldIcons: [
								{
									icon: "./img/grid/confirmed.png",
									showValue: false,
									value: true,
									labelValue: "LBL_ALARM_CONFIG_THRESHOLDS"
								},
								{
									icon: "./img/grid/empty.png",
									showValue: false,
									value: false,
									labelValue: ""
								},
							],
							gridFieldDataType: {
								id: 1,
								description: "string",
								name: "string"
							}
						},
						{
							id: 6,
							enabled: true,
							extended: false,
							filtered: true,
							groupingSummary: false,
							isEpochMilli: false,
							isPk: false,
							label: "LBL_ALARM_LEVEL_LABEL_ALIAS",
							labelTooltip: "LBL_ALARM_LEVEL_LABEL_ALIAS",
							needsTranslation: true,
							parameterized: true,
							position: 6,
							required: true,
							sourceField: "alarmLevelLabelAlias",
							visible: true,
							widthPx: 200,
							gridFieldIcons: [
								{
									id: 11,
									avatar: false,
									badgeColor: false,
									enabled: true,
									icon: "./img/grid/alarm_level_1.png",
									showValue: false,
									value: "LEVEL_0",
									labelValue: "LBL_ALARM_LEVEL_0"
								},
								{
									id: 12,
									avatar: false,
									badgeColor: false,
									enabled: true,
									icon: "./img/grid/alarm_level_2.png",
									showValue: false,
									value: "LEVEL_1",
									labelValue: "LBL_ALARM_LEVEL_1"
								},
								{
									id: 13,
									avatar: false,
									badgeColor: false,
									enabled: true,
									icon: "./img/grid/alarm_level_3.png",
									showValue: false,
									value: "LEVEL_2",
									labelValue: "LBL_ALARM_LEVEL_2"
								},
								{
									id: 14,
									avatar: false,
									badgeColor: false,
									enabled: true,
									icon: "./img/grid/alarm_level_4.png",
									showValue: false,
									value: "LEVEL_3",
									labelValue: "LBL_ALARM_LEVEL_3"
								},
								{
									id: 15,
									avatar: false,
									badgeColor: false,
									enabled: true,
									icon: "./img/grid/alarm_level_5.png",
									showValue: false,
									value: "LEVEL_4",
									labelValue: "LBL_ALARM_LEVEL_4"
								},
								{
									id: 16,
									avatar: false,
									badgeColor: false,
									enabled: true,
									icon: "./img/grid/alarm_level_6.png",
									showValue: false,
									value: "LEVEL_5",
									labelValue: "LBL_ALARM_LEVEL_5"
								}
							],
							gridFieldDataType: {
								id: 1,
								description: "string",
								name: "string"
							}
						}
					],
					gridCommands: [],
					refreshCallback: function() {

						let idSelected = self.listView.getSelectedId()

						webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.alarmConfigByElementType, { id: idSelected })).then(function(alarmsConfigToUpdateResponse) {

							if (alarmsConfigToUpdateResponse != null) {

								let alarmsConfigToUpdate = alarmsConfigToUpdateResponse.json();

								if (Array.isArray(alarmsConfigToUpdate) && alarmsConfigToUpdate.length > 0) {

									let alarmConfigToTable = [];

									for (let alarmConfigToUpdate of alarmsConfigToUpdate) {

										alarmConfigToTable.push({
											id: alarmConfigToUpdate.id,
											labelAlias: (alarmConfigToUpdate.labelAlias) ? i18n.t(alarmConfigToUpdate.labelAlias) : alarmConfigToUpdate.alias,
											alarmLevelLabelAlias: i18n.t(alarmConfigToUpdate.alarmLevel.labelAlias),
											measures: (alarmConfigToUpdate.alarmConfigMeasures && alarmConfigToUpdate.alarmConfigMeasures[0].alarmMeasureOperation) ? true : false,
											alarmLevelId: alarmConfigToUpdate.alarmLevel.id,
											elementSubtype: (alarmConfigToUpdate.elementSubtype && alarmConfigToUpdate.elementSubtype.labelAlias) ? i18n.t(alarmConfigToUpdate.elementSubtype.labelAlias) : "",
											elementSubtypeId: (alarmConfigToUpdate.elementSubtype && alarmConfigToUpdate.elementSubtype.id) ? i18n.t(alarmConfigToUpdate.elementSubtype.id) : ""
										});
									}

									self.dataTable.clear();
									self.dataTable.add(alarmConfigToTable);
								}
							}
						});
					}
				};

				if (self.editAlarmConfigsPermission) {

					metadata.gridCommands.push(
						{
							id: 1,
							value: "LBL_EDIT",
							icon: "mdi mdi-pencil",
							default_: true,
							label: "LBL_CMD_EDIT",
							showText: false,
							position: 2,
							multiselect: false,
							itemRequired: true,
							moduleAction: { id: self.config.action.edit }
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
							itemRequired: true,
							moduleAction: { id: self.config.action.delete }
						}
					);
				}

				let events = {};

				events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];

				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 2,
					function: () => {
						let itemSelected = self.listView.getSelectedItems();
						let mod = util.module.getFromModuleAction(self.config.action.add);

						let moduleAction = {
							id: self.config.action.add
						};

						let args = {
							elementType: itemSelected[0],
							callbacks: [metadata.refreshCallback]
						};

						util.module.open(mod, moduleAction, VIEW_TYPE_WINDOW, null, args);
					}
				});

				let data = [];
				
				self.dataTable = new DataTable(DATA_GRID_ID, null, metadata, data, events);

				return self.dataTable.getView();
			}

			return main;
		}
	}
}