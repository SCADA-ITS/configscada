import { util } from "../../lib/util.js";
import IncidentReportTaskInfo from "./incidentReportTaskInfo.js";
import IncidentReportTaskParam from "./incidentReportTaskParam.js";
import IncidentReportTaskPlan from "./incidentReportTaskPlan.js";

export default class IncidentReportTaskContent {

	constructor(parent, taskState) {

		this.config = parent.config;
		this.parent = parent;
		this.incidentReport = parent.incidentReport; 
		this.taskState = taskState;
		this.index = parent.index;
		this.uid = parent.uid;
	}

	getView() {
		
		let self = this;
		let view;
		
		view = {
			id: "taskContent-" + self.index + "-" + self.uid,
			css: { "border-width": "1px !important" },
			rows: [
				getViewHeader(),
				{
					css: { "border-width": "0px !important" },
					cols: [
						{ width: 10 },
						getViewBody(),		
						{ width: 10 }
					]
				},
				getViewFooter(),
				{ height: 10 , css: { "border-bottom-width": "1px !important" } },
				{ height: 4 }
			]
		};
		
		function getViewHeader() {
			
			let viewHeader;
			
			viewHeader = {
				view: "toolbar",
				css: { "margin-left": "1px !important" },
				borderless: true,
				cols: [
					{ width: 4 },
					{
						view: "label",
						label: `<span class="ico webix_icon mdi mdi-order-bool-ascending-variant"></span><span class="grid-title">${self.taskState.incidentTypeTask.alias + " - " + self.taskState.incidentTypeTask.description}</span>`
					}
				]
			}
			
			return viewHeader;
		}
		
		function getViewBody() {
			
			let viewBody;
			let incidentReportTaskPlan;
			let incidentReportTaskParam;
			let incidentReportTaskInfo;
			
			viewBody = {
				view: "tabview",
				css: "auxiliary-tab",
				animate: false,
				cells: [],
				tabbar: {
					tabMargin: 6
				}	
			}
			
			if (Array.isArray(self.taskState.taskPlanState) && self.taskState.taskPlanState.length > 0) {
				
				incidentReportTaskPlan = new IncidentReportTaskPlan(self.taskState, self.index, self.uid);
				
				viewBody.cells.push({
					header: i18n.t("LBL_PLANS"),
					body: incidentReportTaskPlan.getView()
				});
			}
			
			// TODO: Task param
			
			// ...
			
			// END

			
			incidentReportTaskInfo = new IncidentReportTaskInfo(self.taskState, self.index, self.uid);
			
			viewBody.cells.push({
				header: i18n.t("LBL_INFO"),
				body: incidentReportTaskInfo.getView()
			});
			
			return viewBody;			
		}
		
		function getViewFooter() {
			
			let viewFooter;
			
			viewFooter = {
				height: 45,
				cols: [
					{},
					{
						view: "button",
						css: (function() {

							if (!self.taskState.incidentTypeTask.auto && !self.taskState.completedAt) {

								return "action-button"
							}
							else {

								return "action-button-dis";
							}

						})(),
						height: 41,
						width: 130,
						disabled: !self.incidentReport.confirm,
						hidden: self.taskState.incidentTypeTask.auto || self.taskState.completedAt,
						label: (function() {

							return i18n.t("LBL_INCIDENT_END_TASK");
						})(),
						on: {

							onItemClick() {
								
								webix.confirm({
									title: i18n.t("LBL_INCIDENT_CONFIRM_END_TASK"),
									ok: i18n.t("LBL_YES"),
									cancel: i18n.t("LBL_NO"),
									text: i18n.t("LBL_INCIDENT_CONFIRM_END_TASK_DESCRIPTION"),
									width: 600
								}).then(function() {
	
									if (self.taskState && !self.taskState.incidentTypeTask.auto && !self.taskState.completedAt) {
	
										let endpoint = util.fillTemplate(self.config.actionEndpoints.finalizeTask, {
											id: self.incidentReport.id,
											taskId: self.parent.currentTask.incidentTypeTask.id
										});

										console.log("Incident report action => " + endpoint);

										webix.ajax().post(appConfig.restUrl + endpoint).then(function(dat) {

											self.parent.currentTask = undefined;

										}, function(response) { util.ajax.handleError(response); });
									}	
								});
							}
						}
					},
					{ width: 10 }
				]
			}
			
			return viewFooter;
		}
		
		return view;
	}
}