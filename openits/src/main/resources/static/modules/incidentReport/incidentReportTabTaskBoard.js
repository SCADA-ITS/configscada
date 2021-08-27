import { util } from "../../lib/util.js";
import DataTable from "../../component/datatable.js";

export default class IncidentReportTabTaskBoard {

	constructor(parent, taskStates, index) {

		this.index = index;
		this.parent = parent;
		this.tasks = taskStates;

		this.uid = parent.uid;
		this.config = parent.config;
		this.incidentReport = parent.incidentReport;

		this.dtTasks = undefined;

		if (Array.isArray(this.tasks) && this.tasks.length) {

			this.tasks.sort((a, b) => (a.incidentTypeTask.position > b.incidentTypeTask.position) ? 1 : ((b.incidentTypeTask.position > a.incidentTypeTask.position) ? -1 : 0));

			this.parent.currentTask[this.index] = this.tasks.find(e => {

				return !e.completedAt;
			});
		}
	}

	getView() {

		let self = this;

		function getTaskDetails(incidentTypeTaskId) {

			let taskDetailsBody = {};
			let task = undefined;
			let taskDetails = {};

			if (!incidentTypeTaskId && self.parent.currentTask[self.index]) {

				task = self.parent.currentTask[self.index];
			} else {

				if (Array.isArray(self.tasks) && self.tasks.length) {

					task = self.tasks.find(e => {

						return e.incidentTypeTask.id === incidentTypeTaskId;
					});

					if (!task) {

						task = self.tasks[0];
					}

					self.parent.currentTask[self.index] = task;
				}
			}

			if (task) {

				taskDetailsBody = {
					id: "taskDetailsBody-" + self.index + "-" + self.uid,
					borderless: true,
					css: "ims-incident-report-tab-details",
					rows: [
						{ height: 40 },
						{
							cols: [
								{ width: 40 },
								{
									borderless: true,
									rows: [
										{
											height: 50,
											borderless: true,
											template: `<div><span class="ico webix_icon mdi mdi-format-list-bulleted-type"></span><span class="field">${i18n.t("LBL_INCIDENT_TASK_TYPE")}</span></div>
												   <div class="value">${i18n.t(task.incidentTypeTask.taskType.labelAlias)}</div>`
										},
										{
											height: 50,
											borderless: true,
											template: `<div><span class="ico webix_icon mdi mdi-form-textbox"></span><span class="field">${i18n.t("LBL_INCIDENT_TASK_ALIAS")}</span></div>
												   <div class="value">${task.incidentTypeTask.alias}</div>`
										},
										{
											height: 50,
											borderless: true,
											template: `<div><span class="ico webix_icon mdi mdi-image-text"></span><span class="field">${i18n.t("LBL_INCIDENT_TASK_DESCRIPTION")}</span></div>
												   <div class="value">${task.incidentTypeTask.description}</div>`
										},
										{
											height: 50,
											borderless: true,
											template: function() {

												let typeTask = i18n.t("LBL_INCICENT_TASK_MANUAL");

												if (task.incidentTypeTask.auto) {

													typeTask = i18n.t("LBL_INCICENT_TASK_AUTO");
												}

												return `<div><span class="ico webix_icon mdi mdi-animation"></span><span class="field">${i18n.t("LBL_INCIDENT_TASK_TYPE")}</span></div>
												   	<div class="value">${typeTask}</div>`;
											}
										},
										{
											height: 50,
											borderless: true,
											template: `<div><span class="ico webix_icon mdi mdi-asterisk"></span><span class="field">${i18n.t("LBL_INCIDENT_TASK_REQUIRED")}</span></div>
												   <div class="value">${task.incidentTypeTask.mandatory ? i18n.t("LBL_YES") : i18n.t("LBL_NO")}</div>`
										},
										{
											height: 50,
											borderless: true,
											template: function() {

												let completedAt = i18n.t("LBL_INCIDENT_TASK_NO_COMPLETED");

												if (task.completedAt) {

													completedAt = util.toLocaleString(task.completedAt / 1000);
												}

												return `<div><span class="ico webix_icon mdi mdi-map-marker"></span><span class="field">${i18n.t("LBL_INCIDENT_TASK_COMPLETED")}</span></div>
												   <div class="value">${completedAt}</div>`
											}
										}
									]
								},
								{ width: 40 }
							]
						},
						{}
					]
				};

				taskDetails = {

					id: "taskDetails-" + self.index + "-" + self.uid,
					borderless: true,
					rows: [
						{ height: 5 },
						{
							view: "toolbar",
							cols: [
								{ width: 4 },
								{
									view: "label",
									label: `<span class="ico webix_icon mdi mdi-format-list-bulleted"></span><span class="grid-title">${i18n.t("LBL_TASK_DETAILS")}</span>`
								}
							]
						},
						taskDetailsBody,
						{
							view: "toolbar",
							height: 45,
							cols: [
								{},
								{
									view: "button",
									css: (function() {

										if (!task.incidentTypeTask.auto && !task.completedAt) {

											return "action-button"
										}
										else {

											return "action-button-dis";
										}

									})(),
									height: 41,
									width: 130,
									label: (function() {

										return i18n.t("LBL_INCIDENT_END_TASK");
									})(),
									on: {

										onItemClick() {

											if (task) {

												if (!task.incidentTypeTask.auto && !task.completedAt) {

													let endpoint = util.fillTemplate(self.config.actionEndpoints.finalizeTask, {
														id: self.incidentReport.id,
														taskId: self.parent.currentTask[self.index].incidentTypeTask.id
													});

													self.parent.restAction(endpoint);
												}
											}
										}
									}
								},
							]
						},
						{ height: 5 },
					]
				};
			}

			return taskDetails;
		}

		let tabTasksBoard = {
			id: "tab-task-board-" + self.index + "-" + self.uid,
			borderless: true,
			rows: [
				{ height: 10 },
				{
					cols: [
						{ width: 10 },
						{
							width: 425,
							rows: getTasks()
						},
						{ view: "resizer" },
						getTaskDetails(),
						{ width: 10 }
					]
				},
				{ height: 10 }
			]
		};

		function getTasks() {

			const defaultGridOptionValues = [
				{ gridOption: { id: 1 }, value: "false" },
				{ gridOption: { id: 2 }, value: "false" },
				{ gridOption: { id: 3 }, value: undefined },
				{ gridOption: { id: 4 }, value: undefined },
				{ gridOption: { id: 5 }, value: undefined },
				{ gridOption: { id: 6 }, value: undefined },
				{ gridOption: { id: 7 }, value: "false" },
				{ gridOption: { id: 8 }, value: "false" },
				{ gridOption: { id: 9 }, value: "true" },
				{ gridOption: { id: 10 }, value: "false" },
				{ gridOption: { id: 11 }, value: "false" },
				{ gridOption: { id: 12 }, value: "false" },
				{ gridOption: { id: 13 }, value: "false" },
				{ gridOption: { id: 14 }, value: "false" },
				{ gridOption: { id: 15 }, value: "false" },
				{ gridOption: { id: 16 }, value: "img/grid/default.png" }
			];

			let metadata = {
				filter: true,
				label: "LBL_TASKS",
				visible: true,
				gridOptionValues: defaultGridOptionValues,
				gridFields: [
					{
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
						needsTranslation: false,
						parameterized: false,
						position: 2,
						required: true,
						sourceField: "status",
						visible: true,
						widthPx: 35,
						gridFieldDataType: {
							id: 1,
							name: "string"
						},
						gridFieldIcons: [
							{
								icon: "./img/grid/confirmed.png",
								showValue: false,
								value: "confirmed"
							},
							{
								icon: "./img/grid/not_confirmed.png",
								showValue: false,
								value: "not_confirmed"
							}
						]
					},
					{
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						needsTranslation: false,
						parameterized: false,
						position: 3,
						required: true,
						sourceField: "position",
						visible: true,
						widthPx: 25,
						gridFieldDataType: {
							id: 2,
							name: "int"
						}
					},
					{
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_TASK",
						labelTooltip: "LBL_TASK",
						needsTranslation: false,
						parameterized: false,
						position: 4,
						required: true,
						sourceField: "alias",
						visible: true,
						widthPx: 230,
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
						label: "LBL_TYPE",
						labelTooltip: "LBL_TYPE",
						needsTranslation: false,
						parameterized: false,
						position: 5,
						required: true,
						sourceField: "type",
						visible: true,
						widthPx: 45,
						gridFieldDataType: {
							id: 2,
							name: "int"
						},
						gridFieldIcons: [
							{
								icon: "./img/grid/auto.png",
								showValue: false,
								value: "auto"
							},
							{
								icon: "./img/grid/manual.png",
								showValue: false,
								value: "manual"
							}
						]
					},

					{
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_REQUIRED",
						labelTooltip: "LBL_REQUIRED",
						needsTranslation: false,
						parameterized: false,
						position: 6,
						required: true,
						sourceField: "required",
						visible: true,
						widthPx: 45,
						gridFieldDataType: {
							id: 1,
							name: "string"
						},
						gridFieldIcons: [
							{
								icon: "./img/grid/required.png",
								showValue: false,
								value: "yes"
							},
							{
								icon: "./img/grid/not_confirmed.png",
								showValue: false,
								value: "no"
							}
						]
					}
				]
			};

			let data = [];

			if (Array.isArray(self.tasks) && self.tasks.length) {

				self.tasks.sort((a, b) => (a.incidentTypeTask.position > b.incidentTypeTask.position) ? 1 : ((b.incidentTypeTask.position > a.incidentTypeTask.position) ? -1 : 0));

				for (let i = 0; i < self.tasks.length; i++) {

					let row = {};

					row.id = self.tasks[i].incidentTypeTask.id;
					row.status = self.tasks[i].completedAt ? "confirmed" : "not_confirmed";
					row.position = i + 1;
					row.alias = self.tasks[i].incidentTypeTask.alias;
					row.type = self.tasks[i].incidentTypeTask.auto ? "auto" : "manual";
					row.required = self.tasks[i].incidentTypeTask.mandatory ? "yes" : "no";

					data.push(row);
				}
			}

			let events = {};

			events[DataTable.EventTypes.ON_SELECT_ROW] = function(rows) {

				if (Array.isArray(rows) && rows.length) {

					let taskDetailsBody = $$("taskDetails-" + self.index + "-" + self.uid);

					if (taskDetailsBody) {

						webix.ui(getTaskDetails(rows[0]), taskDetailsBody);
					}

				}
			};

			self.dtTasks = new DataTable(null, null, metadata, data, events);

			return self.dtTasks.getView();
		}


		return tabTasksBoard;
	}

	init() {

		if (this.dtTasks) {

			this.dtTasks.callbacks();

			if (this.parent.currentTask[self.index]) {

				this.dtTasks.select(this.parent.currentTask[self.index].incidentTypeTask.id);

			} else {

				this.dtTasks.select(this.tasks[0].incidentTypeTask.id);
			}
		}
	}
}