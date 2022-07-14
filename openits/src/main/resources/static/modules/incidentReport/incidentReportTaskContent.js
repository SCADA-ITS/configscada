import { util } from "../../lib/util.js";
import IncidentReportTaskDetailInfo from "./incidentReportTaskDetailInfo.js";
import IncidentReportTaskDetailParam from "./incidentReportTaskDetailParam.js";
import IncidentReportTaskDetailPlan from "./incidentReportTaskDetailPlan.js";
import IncidentReportTaskDetailSignallingSpath from "./incidentReportTaskDetailSignallingSpath.js";
import IncidentReportTaskDetailElement from "./incidentReportTaskDetailElement.js";
import MessageBox from "../../component/messageBox.js";

export default class IncidentReportTaskContent {

	constructor(parent, taskState) {

		this.config = parent.config;
		this.parent = parent;
		this.incidentReport = parent.incidentReport;
		this.taskState = taskState;
		this.index = parent.index;
		this.uid = parent.uid;

		this.incidentReportTaskDetailPlan = undefined;
		this.incidentReportTaskDetailParam = undefined;
		this.incidentReportTaskDetailInfo = undefined;
		this.incidentReportTaskDetailSignallingSpath = undefined;
	}

	getView() {

		let self = this;
		let view;
		let userData = self.parent.parent.userData;
		let stretchsInfo = self.parent.parent.stretchsInfo;
		let sameUser = (userData.id === self.incidentReport.currentUser.id);

		view = {
			id: "taskContent-" + self.index + "-" + self.uid,
			css: { "border-width": "1px !important", "box-sizing": "border-box" },
			rows: [
				getViewHeader(),
				{ height: 5 },
				{
					cols: [
						{ width: 10 },
						getViewBody(),
						{ width: 10 }
					]
				},
				getViewFooter(),
				{ height: 4 }
			]
		};

		function getViewHeader() {

			let viewHeader;

			viewHeader = {
				//view: "toolbar",
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

				self.incidentReportTaskDetailPlan = new IncidentReportTaskDetailPlan(self.config, self.incidentReport, self.taskState, self.index, self.uid);

				viewBody.cells.push({
					header: i18n.t("LBL_PLANS"),
					body: self.incidentReportTaskDetailPlan.getView()
				});
			}

			if (self.taskState.incidentTypeTask && self.taskState.incidentTypeTask.taskType &&
				Array.isArray(self.taskState.incidentTypeTask.taskType.taskTypeParams) &&
				self.taskState.incidentTypeTask.taskType.id != self.config.taskTypeSignalling &&
				self.taskState.incidentTypeTask.taskType.taskTypeParams.length > 0 &&
				self.taskState.incidentTypeTask.taskType.taskTypeParams.findIndex(e => self.config.taskParamTypes.includes(e.paramType.id)) !== -1) {

				self.incidentReportTaskDetailParam = new IncidentReportTaskDetailParam(self.config, self.incidentReport, self.taskState, self.index, self.uid);

				viewBody.cells.push({
					header: i18n.t("LBL_PARAMS"),
					body: self.incidentReportTaskDetailParam.getView()
				});
			}

			if (self.taskState.incidentTypeTask && self.taskState.incidentTypeTask.taskType &&
				self.taskState.incidentTypeTask.taskType.id == self.config.taskTypeSignalling &&
				Array.isArray(self.taskState.incidentTypeTask.incidentTypeTaskValues) &&
				self.taskState.incidentTypeTask.incidentTypeTaskValues.length === 1 &&

				self.taskState.incidentTypeTask.incidentTypeTaskValues[0].taskTypeParam.id == self.config.taskTypeParamSignallingToSpath
			) {

				self.incidentReportTaskDetailSignallingSpath = new IncidentReportTaskDetailSignallingSpath(self.config, self.incidentReport, self.taskState, self.index, self.uid, self.parent && self.parent.parent ? self.parent.parent.spaths : undefined);

				viewBody.cells.push({
					header: i18n.t("LBL_SIGNALLING"),
					body: self.incidentReportTaskDetailSignallingSpath.getView()
				});
			}

			if (Array.isArray(self.taskState.elements) && self.taskState.elements.length > 0) {

				let incidentReportTaskDetailElement = new IncidentReportTaskDetailElement(self.config, self.incidentReport, self.taskState, self.index, self.uid);

				viewBody.cells.push({
					header: i18n.t("LBL_EQUIPMENTS"),
					body: incidentReportTaskDetailElement.getView()
				});
			}

			self.incidentReportTaskDetailInfo = new IncidentReportTaskDetailInfo(self.config, self.incidentReport, self.taskState, self.index, self.uid);

			viewBody.cells.push({
				header: i18n.t("LBL_INFO"),
				body: self.incidentReportTaskDetailInfo.getView()
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

							if ((!self.taskState.incidentTypeTask.auto && !self.taskState.completedAt) || self.taskState.incidentTypeTask.rerun === true) {

								return "action-button"

							} else {

								return "action-button-dis";
							}

						})(),
						height: 41,
						width: 130,
						disabled: !self.incidentReport.confirm || !sameUser,
						hidden: !self.taskState.incidentTypeTask.rerun && (self.taskState.incidentTypeTask.auto || self.taskState.completedAt),
						label: (function() {

							let labelButton = (self.taskState.incidentTypeTask.incidentTaskFinalizeButton) ? i18n.t(self.taskState.incidentTypeTask.incidentTaskFinalizeButton) : i18n.t("LBL_INCIDENT_END_TASK");

							return labelButton;
						})(),
						on: {

							onItemClick() {

								let label = undefined;

								if (Array.isArray(self.taskState.taskPlanStates) && self.taskState.taskPlanStates.length > 0 &&
									self.taskState.taskPlanStates.filter(item => item.completedAt).length == 0) {

									label = "LBL_INCIDENT_CONFIRM_END_TASK_DESCRIPTION_WITH_ANY_PLAN_COMPLETED";

								} else if (self.taskState.incidentTypeTask.incidentTaskFinalizeMessage) {
									
									label = self.taskState.incidentTypeTask.incidentTaskFinalizeMessage;
									
								} else {
									label = "LBL_INCIDENT_CONFIRM_END_TASK_DESCRIPTION";
								}


								let ms = new MessageBox(MessageBox.CONFIRM, i18n.t(label), onAccept, null);
								ms.show();

								function onAccept() {

									if (self.taskState.incidentTypeTask.rerun === true || (self.taskState && !self.taskState.incidentTypeTask.auto && !self.taskState.completedAt)) {

										let endpoint = util.fillTemplate(self.config.actionEndpoints.finalizeTask, {
											id: self.incidentReport.id,
											taskId: self.parent.currentTask.incidentTypeTask.id
										});

										let body = {};
										let incidentTypeTaskValues = undefined;

										if (self.incidentReportTaskDetailParam) {

											incidentTypeTaskValues = self.incidentReportTaskDetailParam.getIncidentTypeTaskValues();
										}
										else if (self.incidentReportTaskDetailSignallingSpath) {

											incidentTypeTaskValues = self.incidentReportTaskDetailSignallingSpath.getIncidentTypeTaskValues();
										}

										if (incidentTypeTaskValues) {

											body = { incidentTypeTaskValues: incidentTypeTaskValues };
										}

										log.debug("Incident report action => " + endpoint);

										webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + endpoint, body).then(function(dat) {

											self.parent.currentTask = undefined

										}, function(response) { util.ajax.handleError(response); });
									}
								};
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