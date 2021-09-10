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
		
		this.incidentReportTaskPlan = undefined;
		this.incidentReportTaskParam = undefined;
		this.incidentReportTaskInfo = undefined;
	}

	getView() {
		
		let self = this;
		let view;
		
		view = {
			id: "taskContent-" + self.index + "-" + self.uid,
			css: { "border-width": "1px !important" },
			rows: [
				getViewHeader(),
				{ height: 10 },
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
				css: { "background": "#F4F5F9", "margin-left": "1px !important" },
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
			
			viewBody = {
				view: "tabview",
				css: "auxiliary-tab",
				animate: false,
				cells: [],
				tabbar: {
					tabMargin: 6
				}	
			}
			
			if (Array.isArray(self.taskState.taskPlanStates) && self.taskState.taskPlanStates.length > 0) {
				
				self.incidentReportTaskPlan = new IncidentReportTaskPlan(self.config, self.incidentReport, self.taskState, self.index, self.uid);
				
				viewBody.cells.push({
					header: i18n.t("LBL_PLANS"),
					body: self.incidentReportTaskPlan.getView()
				});
			}
			
			// TODO: Task param
			if (self.taskState.incidentTypeTask && self.taskState.incidentTypeTask.taskType && 
				Array.isArray(self.taskState.incidentTypeTask.taskType.taskTypeParams) && 
				self.taskState.incidentTypeTask.taskType.taskTypeParams.length > 0) {
				
				self.incidentReportTaskParam = new IncidentReportTaskParam(self.taskState, self.index, self.uid);
				
				viewBody.cells.push({
					header: i18n.t("LBL_PARAMS"),
					body: self.incidentReportTaskParam.getView()
				});
			}
			
			// END

			
			self.incidentReportTaskInfo = new IncidentReportTaskInfo(self.taskState, self.index, self.uid);
			
			viewBody.cells.push({
				header: i18n.t("LBL_INFO"),
				body: self.incidentReportTaskInfo.getView()
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
										
										let body = {};
										
										if (self.incidentReportTaskParam) {
										
											body = { incidentTypeTaskValues: self.incidentReportTaskParam.getIncidentTypePaskValues() };	
										}
		
										console.log("Incident report action => " + endpoint);

										webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + endpoint, body).then(function(dat) {

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