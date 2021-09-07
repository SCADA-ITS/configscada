import { util } from "../../lib/util.js";
import DataTable from "../../component/datatable.js";
import IncidentReportTaskContent from "./incidentReportTaskContent.js";

export default class IncidentReportTabTaskBoard {

	constructor(parent, index) {

		this.index = index;
		this.parent = parent;

		this.uid = parent.uid;
		this.config = parent.config;
		this.incidentReport = undefined;

		this.dtTasks = undefined;
		this.tasks = undefined;
		this.currentTask = undefined;
		
		this.incidentReportTaskContent = undefined;
	}

	getView(taskStates) {

		let self = this;

		this.incidentReport = this.parent.incidentReport;
		this.tasks = taskStates;
		this.dtTasks = undefined;
		
		if (Array.isArray(this.tasks) && this.tasks.length) {

			if (!this.currentTask || !this.tasks.find(e => { return (e.id === this.currentTask.id) })) {

				this.tasks.sort((a, b) => (a.incidentTypeTask.position > b.incidentTypeTask.position) ? 1 : ((b.incidentTypeTask.position > a.incidentTypeTask.position) ? -1 : 0));

				this.currentTask = this.tasks.find(e => {

					return !e.completedAt;
				});
			}

		}
		
		function getTaskDetails(incidentTypeTaskId) {
			
			let task = undefined;
			let taskDetails = {};

			if (!incidentTypeTaskId && self.currentTask) {

				task = self.currentTask;
			} 
			else {

				if (Array.isArray(self.tasks) && self.tasks.length) {

					task = self.tasks.find(e => {

						return e.incidentTypeTask.id === incidentTypeTaskId;
					});

					if (!task) {

						task = self.tasks[0];
					}

					self.currentTask = task;
				}
			}

			if (task) {
				
				self.incidentReportTaskContent = new IncidentReportTaskContent(self, task);
				
				taskDetails = self.incidentReportTaskContent.getView();
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
								value: "confirmed",
								labelValue: "LBL_INCIDENT_TASK_COMPLETED"
							},
							{
								icon: "./img/grid/not_confirmed.png",
								showValue: false,
								value: "not_confirmed",
								labelValue: ""
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
						label: "",
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
						label: "",
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
								value: "auto",
								labelValue: "LBL_AUTO"
							},
							{
								icon: "./img/grid/manual.png",
								showValue: false,
								value: "manual",
								labelValue: "LBL_MANUAL"
							}
						]
					},

					{
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "",
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
								value: "yes",
								labelValue: "LBL_REQUIRED"
							},
							{
								icon: "./img/grid/not_confirmed.png",
								showValue: false,
								value: "no",
								labelValue: ""
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

					//let taskDetailsBody = $$("taskDetails-" + self.index + "-" + self.uid);
					
					let taskContent = $$(self.incidentReportTaskContent.getView().id);

					if (taskContent) {

						webix.ui(getTaskDetails(rows[0]), taskContent);
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

			if (this.currentTask) {

				this.dtTasks.select(this.currentTask.incidentTypeTask.id);

			} else {

				this.dtTasks.select(this.tasks[0].incidentTypeTask.id);
			}
		}
	}
}