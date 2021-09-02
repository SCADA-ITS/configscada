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
									template: `<div><span class="ico webix_icon mdi mdi-view-grid"></span><span class="field">${i18n.t("LBL_TASK_TYPE")}</span></div>
										   <div class="value">${i18n.t(self.taskState.incidentTypeTask.taskType.labelAlias)}</div>`
								},
								{
									height: 50,
									borderless: true,
									template: `<div><span class="ico webix_icon mdi mdi-order-bool-ascending-variant"></span><span class="field">${i18n.t("LBL_INCIDENT_TASK_ALIAS")}</span></div>
										   <div class="value">${self.taskState.incidentTypeTask.alias}</div>`
								},
								{
									height: 50,
									borderless: true,
									template: `<div><span class="ico webix_icon mdi mdi-view-headline"></span><span class="field">${i18n.t("LBL_INCIDENT_TASK_DESCRIPTION")}</span></div>
										   <div class="value">${self.taskState.incidentTypeTask.description}</div>`
								},
								{
									height: 50,
									borderless: true,
									template: function() {

										let typeTask = i18n.t("LBL_INCICENT_TASK_MANUAL");

										if (self.taskState.incidentTypeTask.auto) {

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
										   <div class="value">${self.taskState.incidentTypeTask.mandatory ? i18n.t("LBL_YES") : i18n.t("LBL_NO")}</div>`
								},
								{
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