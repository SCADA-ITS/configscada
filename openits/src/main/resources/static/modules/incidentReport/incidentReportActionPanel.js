import SearchDialogBox from "../../component/searchDialogBox.js";
import FilesManager from "../../component/filesManager.js";
import FileManager from "../../component/fileManager.js";
import DataTable from "../../component/datatable.js";
import { util } from "../../lib/util.js";

import IncidentReportChangeLocationAction from "./incidentReportChangeLocationAction.js";

const MARGIN_ACTION_PANEL = 15;

export default class IncidentReportActionPanel {

	constructor(parent) {

		this.parent = parent;
		this.containerId = parent.args.containerId;
		this.uid = parent.uid;
		this.config = parent.config;
		this.closed = parent.closed;
		this.incidentReport = parent.incidentReport;
		this.currentSequenceOfState = parent.currentSequenceOfState;
		this.incidentType = window.em.getByField(EntityType.IMS_INCIDENT_TYPE, "id", this.incidentReport.incidentType.id);
		this.tableAlarms = undefined;
	}

	getView() {

		let self = this;

		function viewCameras() {

			if (self.config && self.config.action && self.config.action.cctvScenario &&
				self.incidentReport.scenario && self.incidentReport.scenario.id) {

				let module = util.module.getFromModuleAction(self.config.action.cctvScenario);

				if (module) {

					let moduleAction = { id: self.config.action.cctvScenario };

					let args = { selectedPks: [self.incidentReport.scenario.id] };

					util.module.open(module, moduleAction, VIEW_TYPE_WINDOW, null, args);
				}

			} else {

				util.ui.notImplementedMessage();
			}
		}

		function changeUser() {

			function resultSearch(data) {

				let body = {
					id: self.incidentReport.id,
					userId: data[0].id
				};
				let endpoint = util.fillTemplate(self.config.actionEndpoints.assignUserIncidentReport, body);
				self.parent.restAction(endpoint);
				$$("loading-" + self.uid).show();
				$$(self.uid).hide();

			}

			let s = new SearchDialogBox(i18n.t("LBL_SELECT_USER"), SearchDialogBox.USERS, resultSearch);

			s.show();
		}

		function showAlarms() {

			if (self.incidentReport.alarms !== undefined && self.incidentReport.alarms.length > 0) {

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
					{ gridOption: { id: 11 }, value: "false" },
					{ gridOption: { id: 12 }, value: "false" },
					{ gridOption: { id: 13 }, value: "true" },
					{ gridOption: { id: 14 }, value: "false" },
					{ gridOption: { id: 15 }, value: "false" },
					{ gridOption: { id: 16 }, value: "img/grid/default.png" }
				];

				let metadata = {
					filter: true,
					label: "LBL_INCIDENT_REPORT_ALARMS",
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
							isEpochMilli: true,
							isPk: false,
							needsTranslation: false,
							parameterized: false,
							position: 2,
							required: true,
							sourceField: "activation",
							visible: true,
							label: "LBL_ALARM_ACTIVATION",
							widthPx: 165,
							gridFieldDataType: {
								id: 4,
								name: "date"
							}
						},
						{
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							needsTranslation: true,
							parameterized: false,
							position: 3,
							required: true,
							sourceField: "alarmConfigAlias",
							visible: true,
							label: "LBL_ALARM_CONFIG_ALIAS",
							widthPx: 255,
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
							needsTranslation: true,
							parameterized: false,
							position: 4,
							required: true,
							sourceField: "elementTypeLabelAlias",
							visible: true,
							label: "LBL_ELEMENT_TYPE_ALIAS",
							widthPx: 175,
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
							position: 5,
							required: true,
							sourceField: "elementAlias",
							visible: true,
							label: "LBL_ELEMENT_ALIAS",
							widthPx: 125,
							gridFieldDataType: {
								id: 1,
								name: "string"
							}
						}

					]
				};


				let data = [];

				if (Array.isArray(self.incidentReport.alarms) && self.incidentReport.alarms.length) {

					let alarms = self.incidentReport.alarms;

					for (let i = 0; i < alarms.length; i++) {
						let row = {};
						row.id = i;
						row.activation = alarms[i].activation;
						row.alarmConfigAlias = alarms[i].alarmConfig.labelAlias;
						row.elementTypeLabelAlias = alarms[i].element.elementType.labelAlias;
						row.elementAlias = alarms[i].element.alias;

						data.push(row);
					}
				}

				self.tableAlarms = new DataTable(null, null, metadata, data);
				let h = 500;
				let w = 800;

				let body = {

					cols: [
						{ width: 20 },
						{
							rows: [
								{ height: 20 },
								{
									height: 375,
									widht: 750,
									rows: self.tableAlarms.getView()
								},
								{ height: 20 },
								{
									cols: [
										{},
										{
											view: "button",
											css: "dialog-cancel",
											height: 41,
											width: 130,
											label: i18n.t("LBL_CLOSE"),
											on: {

												onItemClick() {

													$$(alarmsWindow).close();
												}
											}
										}

									]
								},
								{ height: 7 }
							]
						},
						{ width: 20 }
					]
				}

				let alarmsWindow = util.ui.loadWindow(
					i18n.t("LBL_INCIDENT_REPORT_ALARMS") + " - " + self.incidentReport.incidentType.alias,
					body,
					h,
					w
				);

				if (self.tableAlarms) {
					self.tableAlarms.callbacks();
				}
			}
		}

		function showOperationalPlan() {
			let h = document.documentElement.clientHeight - 200;
			let w = document.documentElement.clientWidth - 500;
			const img = new Image();

			img.onload = function() {

				h = this.height + 50;
				w = this.width;

				let body = {
					rows: [
						{
							template: function() {

								return `<div><img style="max-width:100%;width:auto;height:auto;" src="${img.src}"></div> `
							}
						}
					]
				};

				util.ui.loadWindow(
					i18n.t("LBL_OPERATION_PLAN"),
					body,
					h,
					w
				);
			};

			img.src = self.incidentReport.operationalPlanUrl;
		}

		function showDocuments() {
			let files = [];

			files = self.incidentType.incidentTypeDocuments.map(incidentTypeDocument => {

				return {
					file: incidentTypeDocument.alias,
					description: incidentTypeDocument.description,
					documentUrl: incidentTypeDocument.documentUrl
				};
			});

			let fileManager = new FilesManager(self.incidentType.alias, files, null);
			fileManager.show();

		}
		
		function showAttachments() {
			
			let fileManager = new FileManager(i18n.t("LBL_INCIDENT_ATTACHMENTS"), self.incidentType.alias + " - " + self.incidentType.description, ["test"], FileManager.MODE_DOWNLOAD_UPLOAD);
			
			fileManager.show();
		}

		function cancelIncidentReport() {

			webix.confirm({

				title: i18n.t("LBL_INCIDENT_CONFIRM_CANCEL"),
				ok: i18n.t("LBL_YES"),
				cancel: i18n.t("LBL_NO"),
				text: i18n.t("LBL_INCIDENT_CONFIRM_CANCEL_DESCRIPTION"),
				width: 600

			}).then(function() {

				let endpoint = util.fillTemplate(self.config.actionEndpoints.cancel, {
					id: self.incidentReport.id
				});

				self.parent.restAction(endpoint);
				$$("loading-" + self.uid).show();
				$$(self.uid).hide();
			});
		}

		function changeLocation() {

			let changeLocationAction = new IncidentReportChangeLocationAction(self.config, self.incidentReport);
			changeLocationAction.show();
		}

		function getLowerButtonsViewArray() {

			let body = []

			if (self.incidentReport.operationalPlanUrl !== undefined) {

				body.push(
					{
						view: "button",
						css: "panel-button",
						label: i18n.t("LBL_INCIDENT_OPERATIONAL_PLAN"),
						tooltip: i18n.t("LBL_INCIDENT_OPERATIONAL_PLAN_DESCRIPTION"),
						type: "icon",
						icon: "mdi mdi-sitemap",
						on: {

							onItemClick() {

								showOperationalPlan();
							}
						}
					}
				);

				body.push({ height: 10 });
			}

			if (self.incidentType.incidentTypeDocuments !== undefined && self.incidentType.incidentTypeDocuments.length > 0 ? true : false) {

				body.push(
					{
						view: "button",
						heigth: 50,
						css: "panel-button",
						label: i18n.t("LBL_INCIDENT_DOCUMENTS"),
						tooltip: i18n.t("LBL_INCIDENT_DOCUMENTS_DESCRIPTION"),
						type: "icon",
						icon: "mdi mdi-file-document-multiple-outline",
						disabled: self.incidentType.incidentTypeDocuments !== undefined && self.incidentType.incidentTypeDocuments.length > 0 ? false : true,
						on: {

							onItemClick() {

								showDocuments();
							}
						}
					});
				body.push({ height: 10 });
			}

			body.push(
				{
					view: "button",
					heigth: 50,
					css: "panel-button",
					label: i18n.t("LBL_INCIDENT_ATTACHMENTS"),
					tooltip: i18n.t("LBL_INCIDENT_ATTACHMENTS_DESCRIPTION"),
					type: "icon",
					icon: "mdi mdi-attachment",
					on: {

						onItemClick() {

							showAttachments();
						}
					}
				}
			);

			return body;
		}

		return {

			id: "viewActionPanel-" + self.uid,
			width: 300,
			css: "panel-action",
			rows: [
				{
					height: 95,
					cols: [
						{ width: MARGIN_ACTION_PANEL },
						{
							rows: [
								{ height: 20 },
								{
									view: "button",
									css: "action-button",
									disabled: (function() {

										if (!self.closed && self.incidentReport.confirm === true) {

											// Mandatory tasks with state must be completed in penultimate state
											if (Array.isArray(self.incidentReport.taskStates) && self.incidentReport.taskStates.length && self.incidentReport.sequenceOfStates.length > 1) {

												let penUltimateState = self.incidentReport.sequenceOfStates[self.incidentReport.sequenceOfStates.length - 2];

												if (penUltimateState && penUltimateState.state && self.incidentReport.incidentState.id == penUltimateState.state.id) {

													let tasksCompleted = self.incidentReport.taskStates.filter(e => e.completedAt && e.incidentTypeTask.mandatory === true);
													let tasksMandatory = self.incidentReport.taskStates.filter(e => e.incidentTypeTask.mandatory === true);

													if (tasksCompleted.length < tasksMandatory.length) {

														return true;
													}
												}
											}

											if (Array.isArray(self.currentSequenceOfState.taskStates) && self.currentSequenceOfState.taskStates.length) {

												let tasksCompleted = self.currentSequenceOfState.taskStates.filter(e => e.completedAt && e.incidentTypeTask.mandatory === true);
												let tasksMandatory = self.currentSequenceOfState.taskStates.filter(e => e.incidentTypeTask.mandatory === true);

												if (tasksCompleted.length < tasksMandatory.length) {

													return true;
												}
											}
										}

										return false;

									})(),
									height: 50,
									label: (function() {

										if (!self.closed) {

											if (self.incidentReport.confirm === true) {

												return i18n.t("LBL_INCIDENT_ACTION_NEXT");
											}
											else {

												return i18n.t("LBL_INCIDENT_ACTION_CONFIRM");
											}
										}
										else {

											return i18n.t("LBL_INCIDENT_ACTION_FINALIZE");
										}

									})(),
									tooltip: "#label#",
									on: {

										onItemClick() {

											let bCancel = false;
											if (!self.closed) {

												if (self.incidentReport.confirm === true) {

													let ireportType = window.em.getByField(EntityType.IMS_INCIDENT_TYPE, "id", self.incidentReport.incidentType.id);

													if (Array.isArray(ireportType.incidentTypeCauses) && ireportType.incidentTypeCauses.length > 0) {

														let comboCauseValue = $$("fieldCause-combo-" + self.uid).getValue();

														if (comboCauseValue === "") {

															webix.alert({
																title: i18n.t("LBL_CAUSE_OF_INCIDENT_NOT_SELECTED"),
																ok: i18n.t("LBL_ACCEPT"),
																text: i18n.t("LBL_CAUSE_OF_INCIDENT_NOT_SELECTED_DESCRIPTION"),
																type: "alert-warning",
																width: 600
															}).then(function() {
																$$("tab-details-" + self.uid).show();

																bCancel = true;
															});
														}
													}

													if (!bCancel) {

														let endpoint = util.fillTemplate(self.config.actionEndpoints.forward, {
															id: self.incidentReport.id
														});

														if (Array.isArray(self.currentSequenceOfState.taskStates) && self.currentSequenceOfState.taskStates.length) {

															let tasksCompleted = self.currentSequenceOfState.taskStates.filter(e => e.completedAt && e.incidentTypeTask.mandatory === true);
															let tasksMandatory = self.currentSequenceOfState.taskStates.filter(e => e.incidentTypeTask.mandatory === true);

															if (tasksCompleted.length === tasksMandatory.length) {

																self.parent.restAction(endpoint);
																$$("loading-" + self.uid).show();
																$$(self.uid).hide();
															}

														} else {

															self.parent.restAction(endpoint);
															$$("loading-" + self.uid).show();
															$$(self.uid).hide();
														}
													}

												} else {

													webix.confirm({
														title: i18n.t("LBL_INCIDENT_CONFIRM_CONFIRM"),
														ok: i18n.t("LBL_YES"),
														cancel: i18n.t("LBL_NO"),
														text: i18n.t("LBL_INCIDENT_CONFIRM_CONFIRM_DESCRIPTION"),
														width: 600
													}).then(function() {

														let endpoint = util.fillTemplate(self.config.actionEndpoints.confirm, {
															id: self.incidentReport.id
														});

														self.parent.restAction(endpoint);
														$$("loading-" + self.uid).show();
														$$(self.uid).hide();
													});

												}

											} else {

												let container = $$(self.containerId);

												if (container) {

													container.close();
												}
											}
										}
									}
								},
								{ height: 20 }
							]
						},
						{ width: MARGIN_ACTION_PANEL }
					]
				},
				{
					view: "label",
					css: "section",
					label: `<div class="title"><span class="section-text">${i18n.t("LBL_INCIDENT_SECTION_ACTIONS")}</span></div>`,
					borderless: true
				},
				{
					rows: [
						{ height: 10 },
						{
							cols: [
								{ width: MARGIN_ACTION_PANEL },
								{
									view: "toolbar",
									height: 75,
									css: "tb-actions",
									elements: [
										{
											view: "button",
											width: 135,
											label: i18n.t("LBL_INCIDENT_VIEW_IN_MAP"),
											tooltip: i18n.t("LBL_INCIDENT_VIEW_IN_MAP_DESCRIPTION"),
											type: "iconTop",
											icon: "mdi mdi-map",
											on: {

												onItemClick() {

													util.ui.notImplementedMessage();
												}
											}
										},
										{},
										{
											view: "button",
											width: 135,
											label: i18n.t("LBL_INCIDENT_VIEW_CAMERAS"),
											tooltip: i18n.t("LBL_INCIDENT_VIEW_CAMERAS_DESCRIPTION"),
											type: "iconTop",
											icon: "mdi mdi-cctv",
											hidden: !self.incidentReport.scenario || !self.incidentReport.scenario.id,
											on: {

												onItemClick() {

													viewCameras();
												}
											}
										}
									],
									borderless: true
								},
								{ width: MARGIN_ACTION_PANEL }
							]
						},
						{
							cols: [
								{ width: MARGIN_ACTION_PANEL },
								{
									view: "toolbar",
									height: 75,
									css: "tb-actions",
									elements: [
										{
											view: "button",
											width: 135,
											label: i18n.t("LBL_INCIDENT_CHANGE_ASSIGNMENT"),
											tooltip: i18n.t("LBL_INCIDENT_CHANGE_ASSIGNMENT_DESCRIPTION"),
											disabled: !self.incidentReport.confirm,
											type: "iconTop",
											icon: "mdi mdi-account",
											on: {

												onItemClick() {

													changeUser();
												}
											}
										},
										{},
										{
											view: "button",
											width: 135,
											label: i18n.t("LBL_INCIDENT_SHOW_ALARMS"),
											tooltip: i18n.t("LBL_INCIDENT_SHOW_ALARMS_DESCRIPTION"),
											type: "iconTop",
											disabled: self.incidentReport.alarms !== undefined && self.incidentReport.alarms.length > 0 ? false : true,
											icon: "mdi mdi-bell",
											on: {

												onItemClick() {

													showAlarms();
												}
											}
										}
									],
									borderless: true
								},
								{ width: MARGIN_ACTION_PANEL }
							]
						},
						{
							cols: [
								{ width: MARGIN_ACTION_PANEL },
								{
									view: "toolbar",
									height: 75,
									css: "tb-actions",
									elements: [
										{
											view: "button",
											width: 135,
											hidden: self.incidentReport.confirm,
											label: i18n.t("LBL_INCIDENT_CHANGE_LOCATION"),
											tooltip: i18n.t("LBL_INCIDENT_CHANGE_LOCATION_DESCRIPTION"),
											type: "iconTop",
											icon: "mdi mdi-map-marker",
											on: {

												onItemClick() {

													changeLocation();
												}
											}
										},
										{}
									],
									borderless: true
								},
								{ width: MARGIN_ACTION_PANEL }
							]
						}
					]
				},
				{ height: 40 },
				{
					view: "label",
					css: "section",
					label: `<div class="title"><span class="section-text">${i18n.t("LBL_INCIDENT_SECTION_DOCUMENTS")}</span></div>`,
					borderless: true,
					hidden: !self.incidentReport.confirm 
				},
				{ height: 20 },
				{
					height: 150,
					cols: [
						{ width: 15 },
						{
							hidden: !self.incidentReport.confirm,
							rows: getLowerButtonsViewArray()
						},
						{ width: 15 }
					]
				},
				{},
				{
					view: "toolbar",
					height: 75,
					css: "tb-buttons",
					elements: [
						{},
						{
							view: "button",
							width: 135,
							label: i18n.t("LBL_INCIDENT_CANCEL"),
							tooltip: i18n.t("LBL_INCIDENT_CANCEL_DESCRIPTION"),
							type: "iconTop",
							icon: "mdi mdi-close-thick",
							hidden: self.closed,
							on: {

								onItemClick() {

									cancelIncidentReport();
								}
							}
						},
						{
							view: "button",
							width: 135,
							label: i18n.t("LBL_INCIDENT_CLOSE"),
							tooltip: i18n.t("LBL_INCIDENT_CLOSE_DESCRIPTION"),
							type: "iconTop",
							icon: "mdi mdi-exit-to-app",
							on: {

								onItemClick() {

									let container = $$(self.containerId);

									if (container) {

										container.close();
									}
								}
							}
						},
						{}
					],
					borderless: true
				}
			]
		};
	}

}