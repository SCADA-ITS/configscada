import { util } from "../../lib/util.js";

export default class IncidentReportTaskInfo {

	constructor(taskState, index, uid) {

		this.taskState = taskState;
		this.index = index;
		this.uid = uid;
	}

	getView() {
		
		let self = this;
		let viewInfo;
		
		viewInfo = {

			id: "task-details-info-" + self.index + "-" + self.uid,
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
									id: "task-details-info-labelAlias-" + self.index + "-" + self.uid,
									height: 50,
									borderless: true,
									template: `<div><span class="ico webix_icon mdi mdi-view-grid"></span><span class="field">${i18n.t("LBL_TASK_TYPE")}</span></div>
										   <div class="value">${i18n.t(self.taskState.incidentTypeTask.taskType.labelAlias)}</div>`
								},
								{
									id: "task-details-info-alias-" + self.index + "-" + self.uid,
									height: 50,
									borderless: true,
									hidden: self.taskState.incidentTypeTask.operationDescription ? true : false,
									template: `<div><span class="ico webix_icon mdi mdi-order-bool-ascending-variant"></span><span class="field">${i18n.t("LBL_INCIDENT_TASK_ALIAS")}</span></div>
										   <div class="value">${self.taskState.incidentTypeTask.alias}</div>`
								},
								{
									id: "task-details-info-description-" + self.index + "-" + self.uid,
									height: 50,
									borderless: true,
									hidden: self.taskState.incidentTypeTask.operationDescription ? true : false,
									template: `<div><span class="ico webix_icon mdi mdi-view-headline"></span><span class="field">${i18n.t("LBL_INCIDENT_TASK_DESCRIPTION")}</span></div>
										   <div class="value">${self.taskState.incidentTypeTask.description}</div>`
								},
								{
									id: "task-details-info-operation-" + self.index + "-" + self.uid,
									height: 50,
									borderless: true,
									hidden: self.taskState.incidentTypeTask.operationDescription ? false : true,
									template: `<div><span class="ico webix_icon mdi mdi-view-headline"></span><span class="field">${i18n.t("LBL_INCIDENT_TASK_OPERATION")}</span></div>
										   <div class="value" style="overflow: hidden; text-overflow: ellipsis; display: -webkit-box; -webkit-line-clamp: 2; line-clamp: 2; -webkit-box-orient: vertical;">${self.taskState.incidentTypeTask.operationDescription}</div>`
								},
								{
									id: "task-details-info-type-" + self.index + "-" + self.uid,
									height: 50,
									borderless: true,
									template: function() {

										let typeTask = i18n.t("LBL_INCIDENT_TASK_MANUAL");

										if (self.taskState.incidentTypeTask.auto) {

											typeTask = i18n.t("LBL_INCIDENT_TASK_AUTO");
										}

										return `<div><span class="ico webix_icon mdi mdi-animation"></span><span class="field">${i18n.t("LBL_INCIDENT_TASK_TYPE")}</span></div>
										   	<div class="value">${typeTask}</div>`;
									}
								},
								{
									id: "task-details-info-automatism-" + self.index + "-" + self.uid,
									height: 50,
									borderless: true,
									template: function() {
										
										let typeTaskAutomatism = i18n.t("LBL_INCIDENT_TASK_WO_AUTOMATISM");
													
										if (Array.isArray(self.taskState.taskPlanStates) && self.taskState.taskPlanStates.length > 0) {
											
											typeTaskAutomatism = i18n.t("LBL_INCIDENT_TASK_AUTOMATISM_PLANS");
										}
										else if (self.taskState.incidentTypeTask.onChange) {

											typeTaskAutomatism = i18n.t("LBL_INCIDENT_TASK_AUTOMATISM_RULES");
										}
										
										return `<div><span class="ico webix_icon mdi mdi-cog"></span><span class="field">${i18n.t("LBL_INCIDENT_TASK_AUTOMATISM")}</span></div>
										   	<div class="value">${typeTaskAutomatism}</div>`;
									}
								},
								{
									id: "task-details-info-mandatory-" + self.index + "-" + self.uid,
									height: 50,
									borderless: true,
									template: `<div><span class="ico webix_icon mdi mdi-asterisk"></span><span class="field">${i18n.t("LBL_INCIDENT_TASK_REQUIRED")}</span></div>
										   <div class="value">${self.taskState.incidentTypeTask.mandatory ? i18n.t("LBL_YES") : i18n.t("LBL_NO")}</div>`
								},
								{
									id: "task-details-info-completedAt-" + self.index + "-" + self.uid,
									height: 50,
									borderless: true,
									template: function() {

										let completedAt = i18n.t("LBL_INCIDENT_TASK_NO_COMPLETED");

										if (self.taskState.completedAt) {

											completedAt = util.toLocaleString(self.taskState.completedAt / 1000);
										}

										return `<div><span class="ico webix_icon mdi mdi-flag-checkered"></span><span class="field">${i18n.t("LBL_INCIDENT_TASK_COMPLETED")}</span></div>
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
		
		return viewInfo;
	}
}