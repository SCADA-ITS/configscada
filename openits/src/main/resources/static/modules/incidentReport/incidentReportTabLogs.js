import { util } from "../../lib/util.js";
import DataTable from "../../component/datatable.js";

export default class IncidentReportTabLog {

	constructor(parent) {

		this.parent = parent;
		this.uid = parent.uid;
		this.config = parent.config;
		this.incidentReport = parent.incidentReport;
		this.dtLogs = undefined;
	}

	getView() {

		let self = this;
		this.dtLogs = undefined;
		
		let tabLogs = {
			id: "tab-logs-" + self.uid,
			borderless: true,
			rows: [
				{ height: 10 },
				{
					cols: [
						{ width: 10 },
						{
							rows: getLogs()
						},
						{ width: 10 }
					]
				},
				{ height: 10 }
			]
		};

		function getLogs() {

			let incidentReportId = self.incidentReport.id;

			let addLogIncidentReportEndPoint = self.config.actionEndpoints.addLogIncidentReport;
			let deleteLogIncidentReportEndPoint = self.config.actionEndpoints.deleteLogIncidentReport;

			const defaultGridOptionValues = [
				{ gridOption: { id: 1 }, value: "true" },
				{ gridOption: { id: 2 }, value: "true" },
				{ gridOption: { id: 3 }, value: undefined },
				{ gridOption: { id: 4 }, value: undefined },
				{ gridOption: { id: 5 }, value: undefined },
				{ gridOption: { id: 6 }, value: undefined },
				{ gridOption: { id: 7 }, value: "false" },
				{ gridOption: { id: 8 }, value: "false" },
				{ gridOption: { id: 9 }, value: "true" },
				{ gridOption: { id: 10 }, value: "true" },
				{ gridOption: { id: 11 }, value: "true" },
				{ gridOption: { id: 12 }, value: "true" },
				{ gridOption: { id: 13 }, value: "true" },
				{ gridOption: { id: 14 }, value: "false" },
				{ gridOption: { id: 15 }, value: "false" },
				{ gridOption: { id: 16 }, value: "img/grid/default.png" }
			];

			let metadata = {
				filter: true,
				label: "LBL_LOGS",
				visible: true,
				gridOptionValues: defaultGridOptionValues,
				gridCommands: [
					{
						id: 1,
						value: "LBL_ADD",
						icon: "wxi-plus",
						default_: true,
						label: "LBL_CMD_ADD",
						//showText: true,
						position: 1
					},
					{
						id: 2,
						value: "LBL_DELETE",
						icon: "wxi-trash",
						label: "LBL_CMD_DELETE",
						default_: false,
						//showText: true,
						position: 2,
						multiselect: true,
						itemSelected: true
					}
				],
				gridFields: [
					{
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_ID",
						labelTooltip: "LBL_ID",
						needsTranslation: false,
						parameterized: false,
						position: 1,
						required: true,
						sourceField: "id",
						visible: false,
						widthPx: 300,
						gridFieldDataType: {
							id: 1,
							name: "string"
						},
						gridFieldIcons: [
						]
					},
					{
						extended: false,
						filtered: false,
						isEpochMilli: true,
						isPk: false,
						label: "LBL_TIME_STAMP",
						needsTranslation: false,
						parameterized: false,
						position: 2,
						//required: true,
						sourceField: "incidentLogTime",
						visible: true,
						widthPx: 180,
						gridFieldDataType: {
							id: 4,
							name: "date"
						},
						gridFieldIcons: [
						]
					},
					{
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_LEVEL",
						labelTooltip: "LBL_LEVEL",
						needsTranslation: false,
						parameterized: false,
						position: 3,
						//required: true,
						sourceField: "incidentLogLevel",
						visible: false,
						widthPx: 200,
						gridFieldDataType: {
							id: 1,
							name: "string"
						},
						gridFieldIcons: [
						]
					},
					{
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_ID",
						labelTooltip: "LBL_ID",
						needsTranslation: false,
						parameterized: false,
						position: 1,
						required: true,
						sourceField: "incidentLogSubtypeId",
						visible: false,
						widthPx: 300,
						gridFieldDataType: {
							id: 1,
							name: "string"
						},
						gridFieldIcons: [
						]
					},
					{
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_LOG_SUBTYPE",
						labelTooltip: "LBL_LOG_SUBTYPE",
						needsTranslation: true,
						parameterized: false,
						position: 4,
						required: true,
						sourceField: "incidentLogSubtype",
						visible: true,
						widthPx: 120,
						gridFieldDataType: {
							id: 1,
							name: "string"
						},
						gridFieldIcons: [
						]
					},
					{
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_USER",
						labelTooltip: "LBL_USER",
						needsTranslation: false,
						parameterized: false,
						position: 5,
						required: true,
						sourceField: "incidentLogUser",
						visible: true,
						widthPx: 100,
						gridFieldDataType: {
							id: 1,
							name: "string"
						},
						gridFieldIcons: [
						]
					},
					{
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LOG_IMS_PARAM_1_STATE",
						labelTooltip: "LOG_IMS_PARAM_1_STATE",
						needsTranslation: true,
						parameterized: false,
						position: 6,
						required: true,
						sourceField: "incidentLogValueState",
						visible: true,
						widthPx: 100,
						gridFieldDataType: {
							id: 1,
							name: "string"
						},
						gridFieldIcons: [
						]
					},
					{
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LOG_IMS_PARAM_ARG1",
						labelTooltip: "LOG_IMS_PARAM_ARG1",
						needsTranslation: false,
						parameterized: false,
						position: 7,
						required: true,
						sourceField: "incidentLogValueArg1",
						visible: true,
						widthPx: 600,
						gridFieldDataType: {
							id: 1,
							name: "string"
						},
						gridFieldIcons: [
						]
					},
					{
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LOG_IMS_PARAM_ARG2",
						labelTooltip: "LOG_IMS_PARAM_ARG2",
						needsTranslation: true,
						parameterized: false,
						position: 8,
						required: true,
						sourceField: "incidentLogValueArg2",
						visible: true,
						widthPx: 150,
						gridFieldDataType: {
							id: 1,
							name: "string"
						},
						gridFieldIcons: [
						]
					}
				]
			};

			let data = [];
			let logList = self.incidentReport.incidentLogs !== undefined ? self.incidentReport.incidentLogs : undefined;

			if (logList !== undefined && Array.isArray(logList) && logList.length) {

				logList.forEach(element => {

					let row = {};
					row["id"] = element.id;
					row["incidentLogTime"] = element.time;
					row["incidentLogLevel"] = element.level;
					row["incidentLogSubtypeId"] = element.logSubtype.id
					row["incidentLogSubtype"] = element.logSubtype.labelAlias;
					row["incidentLogUser"] = element.user.name;

					row["incidentLogValueState"] = null;
					row["incidentLogValueArg1"] = null;
					row["incidentLogValueArg2"] = null;

					element.incidentReportLogValues.forEach(e => {
						e.logTypeParam.id === INCIDENTS_LOGS_VALUE_STATE ? row["incidentLogValueState"] = e.value : null;
						e.logTypeParam.id === INCIDENTS_LOGS_VALUE_ARG1 ? row["incidentLogValueArg1"] = e.value : null;
						e.logTypeParam.id === INCIDENTS_LOGS_VALUE_ARG2 ? row["incidentLogValueArg2"] = e.value : null;
					});

					data.push(row);

				});

			}

			let events = {};
			events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];
			events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
				commandId: 1,
				function: () => {

					let title = i18n.t("LBL_DIALOG_CREATE_LOG");
					let text = i18n.t("LBL_DIALOG_CREATE_LOG_TEXT");
					let userData = util.user.auth();
					let logType = window.em.getByField(EntityType.LOG_TYPE, "id", self.config.logIncidentType);

					let options = []
					self.config.logIncidentSubTypes.forEach(e => {
						let subType = logType.logSubtypes.find(element => element.id === e);
						options.push({ id: subType.id, value: i18n.t(subType.labelAlias) });
					});

					let iReportLogValuesParamIdState = self.config.iReportLogValuesParamIdState;
					let iReportLogValuesParamIdComment = self.config.iReportLogValuesParamIdComment;

					let body = {
						view: "window",
						modal: true,
						height: 350,
						width: 600,
						css: "dialog-input-custom",
						head: title,
						position: "center",
						body: {
							rows: [
								{
									view: "combo",
									id: "combo-dialog-" + self.uid,
									label: i18n.t('LBL_INCIDENTREPORT_LOG_SUBTYPE'),
									labelWidth: 100,
									value: options[0],
									options: options
								},
								{ height: 10 },
								{ template: text, borderless: true },
								{
									view: "textarea",
									id: "txt-dialog-" + self.uid,
									height: 80,
									placeholder: i18n.t("LBL_COMMENTS_HERE_OPTIONAL")
								},
								{},
								{
									cols: [
										{},
										{
											view: "button",
											value: i18n.t("LBL_NO"),
											width: 100,
											click: function() {

												const window = this.getTopParentView();
												window.close();
											}
										},
										{ width: 5 },
										{
											view: "button",
											value: i18n.t("LBL_YES"),
											width: 100,
											click: function() {

												const window = this.getTopParentView();
												const logComment = $$("txt-dialog-" + self.uid).getValue();
												const logType = $$("combo-dialog-" + self.uid).getValue();

												let mutationBody = {
													"incidentReportLogs":
														[
															{
																"incidentReportId": incidentReportId,
																"incidentReportLogValues": [
																	{
																		"logTypeParamId": iReportLogValuesParamIdState,
																		"value": "LBL_IMS_INCIDENT_STATE_INITIAL"
																	},
																	{
																		"logTypeParamId": iReportLogValuesParamIdComment,
																		"value": logComment
																	}
																],
																"level": "INFO",
																"logSubtypeId": logType,
																"userId": userData.id
															}
														]
												};

												webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + addLogIncidentReportEndPoint, mutationBody).then(function(data) {
													//console.log(data);
												});

												window.close();
											}
										},
										{}
									]
								}
							],
							type: "form"
						}
					}

					webix.ui(body).show();

				}
			});

			events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
				commandId: 2,
				function: () => {

					let rowsSelected = self.dtLogs.rowsSelected;
					let control = false;
					self.dtLogs.rowsSelected.forEach(e => {
						let row = self.dtLogs.data.find(dato => dato.id === e);
						console.log(self.config.logIncidentSubTypes.includes(row.incidentLogSubtypeId));
						!self.config.logIncidentSubTypes.includes(row.incidentLogSubtypeId) ? control = true : null;
					});

					if (!control) {

						webix.confirm({
							title: i18n.t("LBL_INCIDENT_LOG_DELETE_CONFIRM"),
							ok: i18n.t("LBL_YES"),
							cancel: i18n.t("LBL_NO"),
							text: i18n.t("LBL_INCIDENT_LOG_DELETE_CONFIRM_DESCRIPTION"),
							width: 600
						}).then(() => {

							rowsSelected.forEach(e => {

								let endpoint = util.fillTemplate(deleteLogIncidentReportEndPoint, {
									id: e
								});

								self.parent.restAction(endpoint);
							});

						});

					} else {
						webix.alert({
							title: i18n.t("LBL_INCIDENT_LOG_SUBTYPE_ERROR"),
							ok: i18n.t("LBL_YES"),
							text: i18n.t("LBL_INCIDENT_LOG_SUBTYPE_ERROR_DESCRIPTION"),
							type: "alert-warning",
							width: 600
						});
					}
				}
			});

			self.dtLogs = new DataTable(null, null, metadata, data, events);

			return self.dtLogs.getView();

		}

		return tabLogs;
	}

	init() {

		if (this.dtLogs) {
			this.dtLogs.callbacks();
		}
	}

}