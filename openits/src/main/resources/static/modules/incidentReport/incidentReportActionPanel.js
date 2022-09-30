import { util } from "../../lib/util.js";
import IncidentReportChangeLocation from "./incidentReportChangeLocation.js";
import IncidentReportChangeType from "./incidentReportChangeType.js";
import FileManager from "../../component/fileManager.js";
import DataTable from "../../component/datatable.js";
import MessageBox from "../../component/messageBox.js";
import SearchDialogBox from "../../component/searchDialogBox.js";

const MARGIN_ACTION_PANEL = 15;
const TOOLBARS_ALLOWED = 3;

export default class IncidentReportActionPanel {

	constructor(parent) {

		this.parent = parent;
		this.containerId = parent.args.containerId;
		this.viewTypeId = parent.args.viewTypeId;
		this.uid = parent.uid;
		this.config = parent.config;
		this.closed = parent.closed;
		this.incidentReport = parent.incidentReport;
		this.currentSequenceOfState = parent.currentSequenceOfState;
		this.userData = parent.userData;
		this.incidentType = window.em.getByField(EntityType.IMS_INCIDENT_TYPE, "id", this.incidentReport.incidentType.id);
		this.tableAlarms = undefined;
		this.autoAssignPermissions = parent.autoAssignPermissions;
		this.assignAnotherPermissions = parent.assignAnotherPermissions;
		this.cancelIncidentPermissions = util.module.isAllowedPermission(this.config.requiredPermissions.cancelIncident);
		this.confirmIncidentPermissions = util.module.isAllowedPermission(this.config.requiredPermissions.confirmIncident);
		this.timeOut = undefined;
	}

	getView() {

		let self = this;
		let currentUser = window.em.getByField(EntityType.USER, "id", self.incidentReport.currentUser.id);
		let currentUserRole = currentUser.role.id;
		let sameRole = (self.userData.role.id === currentUserRole);
		let sameUser = (self.userData.id === currentUser.id);
		let changeAssignIncident = ((self.autoAssignPermissions && sameRole && !sameUser) || self.assignAnotherPermissions) ? true : false;
		let onlyAutoassignIncident = (self.autoAssignPermissions && !self.assignAnotherPermissions) ? true : false;
		let drawCounterLabel = false;
		let viewActionPanel = {};

		viewActionPanel = {
			id: "viewActionPanel-" + self.uid,
			width: 300,
			css: "panel-action",
			rows: [
				{
					view: "template",
					height: 1,
					borderless: true,
					on: {

						onDestruct: function() {

							clearInterval(self.timeOut);
						},
						onAfterRender: function() {

							if (drawCounterLabel) {

								drawCounterLabel = false;
								getTimeRemaining();
							}
						}
					}
				},
				{
					height: 95,
					cols: [
						{ width: MARGIN_ACTION_PANEL },
						{
							rows: [
								{ height: 20 },
								{
									height: 50,
									cols: [
										{
											id: "backward-button-" + self.uid,
											view: "button",
											css: "action-button",
											disabled: (function() {
		
												let disabled = false;
												
												disabled |= !sameUser;
												disabled |= (self.incidentReport.sequenceOfStates.findIndex(e => e.state.id === self.incidentReport.incidentState.id) === 0);
												disabled |= self.closed; 
		
												return disabled;
											})(),
											hidden: !self.incidentReport.confirm,
											label: i18n.t("LBL_INCIDENT_ACTION_PREVIOUS"),
											tooltip: "#label#",
											on: {
		
												onItemClick() {
		
													if (self.incidentReport.confirm === true && sameUser) {
	
														let endpoint = util.fillTemplate(self.config.actionEndpoints.backward, {
															id: self.incidentReport.id
														});
														
														self.parent.restAction(endpoint);
													}
												}
											}
										},
										{ width: self.incidentReport.confirm ? MARGIN_ACTION_PANEL : 1 },
										{
											id: "action-button-" + self.uid,
											view: "button",
											css: "action-button",
											disabled: (function() {
		
												if (!self.closed && self.incidentReport.confirm === true && sameUser) {
		
													// Mandatory tasks with state must be completed in penultimate state
													if (Array.isArray(self.incidentReport.taskStates) && self.incidentReport.taskStates.length && self.incidentReport.sequenceOfStates.length > 1) {
		
														let penUltimateState = self.incidentReport.sequenceOfStates[self.incidentReport.sequenceOfStates.length - 2];
		
														if (penUltimateState && penUltimateState.state && self.incidentReport.incidentState.id == penUltimateState.state.id) {
		
															let tasksCompleted = filterTasksByRoadImpact(self.incidentReport.taskStates.filter(e => e.completedAt && e.incidentTypeTask.mandatory === true));
															let tasksMandatory = filterTasksByRoadImpact(self.incidentReport.taskStates.filter(e => e.incidentTypeTask.mandatory === true));
		
															if (tasksCompleted.length < tasksMandatory.length) {
		
																return true;
															}
														}
													}
		
													if (Array.isArray(self.currentSequenceOfState.taskStates) && self.currentSequenceOfState.taskStates.length) {
		
														let tasksCompleted = filterTasksByRoadImpact(self.currentSequenceOfState.taskStates.filter(e => e.completedAt && e.incidentTypeTask.mandatory === true));
														let tasksMandatory = filterTasksByRoadImpact(self.currentSequenceOfState.taskStates.filter(e => e.incidentTypeTask.mandatory === true));
		
														if (tasksCompleted.length < tasksMandatory.length) {
		
															return true;
														}
													}
		
												} else if (!self.closed && self.incidentReport.confirm !== true && self.confirmIncidentPermissions) {
		
													if (sameUser || (onlyAutoassignIncident && sameRole) || self.assignAnotherPermissions) {
		
														return false;
													}
		
													return true;
		
												} else {
		
													return true;
												}
		
											})(),
											label: (function() {
		
												let label = i18n.t("LBL_INCIDENT_ACTION_FINALIZE");
												let positionCurrentState = self.incidentReport.sequenceOfStates.findIndex(seq => seq.state.id === self.incidentReport.incidentState.id)
												let isLastState = self.incidentReport.sequenceOfStates.length > 1 && positionCurrentState >= self.incidentReport.sequenceOfStates.length - 2;
		
												if (!self.closed) {
		
													if (self.incidentReport.confirm === true) {
		
														if (!isLastState) {
		
															label = i18n.t("LBL_INCIDENT_ACTION_NEXT");
														}
													}
													else {
		
														label = i18n.t("LBL_INCIDENT_ACTION_CONFIRM");
														drawCounterLabel = true;
													}
												}
		
												return label;
		
											})(),
											tooltip: "#label#",
											on: {
		
												onItemClick() {
		
													if (!self.closed) {
		
														if (self.incidentReport.confirm === true) {
		
															let ireportType = window.em.getByField(EntityType.IMS_INCIDENT_TYPE, "id", self.incidentReport.incidentType.id);
		
															if (Array.isArray(ireportType.incidentTypeCauses) && ireportType.incidentTypeCauses.length > 0 && (!self.incidentReport.alarms || self.incidentReport.alarms.length === 0)) {
		
																let comboCauseValue = $$("fieldCause-combo-" + self.uid).getValue();
		
																if (comboCauseValue === "") {
		
																	let ms = new MessageBox(MessageBox.WARN, i18n.t("LBL_CAUSE_OF_INCIDENT_NOT_SELECTED_DESCRIPTION"), null, onAccept, null);
																	ms.show();
		
																	function onAccept() {
		
																		$$("tab-details-" + self.uid).show();
																	};
																}
															}
		
															let endpoint = util.fillTemplate(self.config.actionEndpoints.forward, {
																id: self.incidentReport.id
															});
		
															if (Array.isArray(self.currentSequenceOfState.taskStates) && self.currentSequenceOfState.taskStates.length) {
		
																let tasksCompleted = filterTasksByRoadImpact(self.currentSequenceOfState.taskStates.filter(e => e.completedAt && e.incidentTypeTask.mandatory === true));
																let tasksMandatory = filterTasksByRoadImpact(self.currentSequenceOfState.taskStates.filter(e => e.incidentTypeTask.mandatory === true));
		
																if (tasksCompleted.length === tasksMandatory.length) {
		
																	self.parent.restAction(endpoint);
																}
		
															} else {
		
																self.parent.restAction(endpoint);
															}
		
														} else {
		
															if (sameUser) {
		
																let ms = new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_INCIDENT_CONFIRM_CONFIRM_DESCRIPTION"), null, onAccept, null);
																ms.show();
		
															} else {
		
																let ms = new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_INCIDENT_CONFIRM_CONFIRM_DESCRIPTION"), null, function() {
		
																	onAccept(true);
		
																}, null);
																ms.show();
															}
		
															function onAccept(changeUser) {
		
																let userId = (changeUser) ? self.userData.id : null;
		
																let body = {
																	id: self.incidentReport.id,
																	userId: userId
																};
		
																let endpoint = util.fillTemplate(self.config.actionEndpoints.confirm, body);
																self.parent.restAction(endpoint);
															}
														}
													}
												}
											}
										}
									]
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
				drawActionsButtons(),
				{ height: 40 },
				{
					view: "label",
					css: "section",
					label: `<div class="title"><span class="section-text">${i18n.t("LBL_INCIDENT_SECTION_DOCUMENTS")}</span></div>`,
					borderless: true,
					hidden: !self.incidentReport.confirm || !(self.config.showOperationalPlan || self.config.showAttachments || self.config.showDocuments)
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
							disabled: function() {

								if (self.incidentReport.confirm === true && !sameUser) {

									return true

								} else {

									if (self.assignAnotherPermissions || (onlyAutoassignIncident && sameRole) || sameUser) {

										return false;
									}

									return true;
								}
							}(),
							hidden: self.closed || !self.cancelIncidentPermissions,
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
							hidden: (self.viewTypeId != VIEW_TYPE_WINDOW),
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
		
		function getRoadImpacts() {
		
			let roadImpacts;
			
			if (self.incidentReport && self.incidentReport.roadImpacts && Array.isArray(self.incidentReport.roadImpacts)) {
					
					roadImpacts = self.incidentReport.roadImpacts;  	
			}
			
			return roadImpacts;
		}

		function drawActionsButtons() {

			let actionsButtons = { rows: [] };
			let buttonsArray = [];
			let numberOfButtonsToDraw = 1;
			let mapButton = {};
			let camerasButton = {};
			let changeUserButton = {};
			let showAlarmsButton = {};
			let changeIncidentButton = {};
			let changeRoadImpactButton = {};

			changeUserButton = {
				view: "button",
				width: 135,
				label: (onlyAutoassignIncident) ? i18n.t("LBL_AUTOASSIGN") : i18n.t("LBL_INCIDENT_CHANGE_ASSIGNMENT"),
				tooltip: i18n.t("LBL_INCIDENT_CHANGE_ASSIGNMENT_DESCRIPTION"),
				disabled: !changeAssignIncident || self.closed,  
				type: "iconTop",
				icon: "mdi mdi-account",
				on: {

					onItemClick() {

						let moduleAction = { id: self.parent.module.config.moduleAction.assign };
						let args = { 
							selectedPks: [self.incidentReport.id],
							selectedRows: [{
								id: self.incidentReport.id,
								currentUserId: self.incidentReport.currentUser.id 
							}] 
						};

						util.module.open(self.parent.module, moduleAction, null, null, args);
					}
				}
			};

			if (self.config.action.loadIncidentInMap) {

				mapButton = {
					view: "button",
					width: 135,
					label: i18n.t("LBL_INCIDENT_VIEW_IN_MAP"),
					tooltip: i18n.t("LBL_INCIDENT_VIEW_IN_MAP_DESCRIPTION"),
					type: "iconTop",
					icon: "mdi mdi-map",
					on: {
						onItemClick() {

							openMap();
						}
					}
				};

				buttonsArray.push(mapButton);
				numberOfButtonsToDraw++;
			}

			if (self.config.action.cctvScenario) {

				camerasButton = {
					view: "button",
					width: 135,
					label: i18n.t("LBL_INCIDENT_VIEW_CAMERAS"),
					tooltip: i18n.t("LBL_INCIDENT_VIEW_CAMERAS_DESCRIPTION"),
					type: "iconTop",
					icon: "mdi mdi-cctv",
					disabled: !self.incidentReport.scenario,
					on: {

						onItemClick() {

							viewCameras();
						}
					}
				};

				buttonsArray.push(camerasButton);
				numberOfButtonsToDraw++;
			}

			buttonsArray.push(changeUserButton);

			if (self.incidentReport.alarms !== undefined && self.incidentReport.alarms.length > 0) {

				showAlarmsButton = {
					view: "button",
					width: 135,
					label: i18n.t("LBL_INCIDENT_SHOW_ALARMS"),
					tooltip: i18n.t("LBL_INCIDENT_SHOW_ALARMS_DESCRIPTION"),
					type: "iconTop",
					icon: "mdi mdi-bell",
					on: {

						onItemClick() {

							showAlarms();
						}
					}
				};

				buttonsArray.push(showAlarmsButton);
				numberOfButtonsToDraw++;
			}

			if (!self.incidentReport.confirm) {

				changeIncidentButton = {
					view: "button",
					width: 135,
					disabled: (self.userData.id != self.incidentReport.currentUser.id) ? true : false,
					label: i18n.t("LBL_INCIDENT_CHANGE"),
					tooltip: i18n.t("LBL_INCIDENT_CHANGE_DESCRIPTION"),
					type: "iconTop",
					icon: "mdi mdi-pen",
					on: {

						onItemClick() {

							changeIncident();
						}
					}
				};

				buttonsArray.push(changeIncidentButton);
				numberOfButtonsToDraw++;
			}
			
			actionsButtons.rows.push({ height: 10 });
			
			let roadImpacts = getRoadImpacts();
			
			if (Array.isArray(roadImpacts) && roadImpacts.length > 0) {
				
				changeRoadImpactButton = {
					view: "button",
					width: 135,
					disabled: !(Array.isArray(roadImpacts) && roadImpacts.length > 0) ? true : false,
					label: i18n.t("LBL_ROAD_IMPACT_CHANGE"),
					tooltip: i18n.t("LBL_ROAD_IMPACT_CHANGE_DESCRIPTION"),
					type: "iconTop",
					icon: "mdi mdi-selection",
					on: {

						onItemClick() {

							changeRoadImpact();
						}
					}
				};

				buttonsArray.push(changeRoadImpactButton);
				numberOfButtonsToDraw++;
			}

			let elements = [];
			let buttonsInToolbar = 0;
			let toolbarsComplete = 0;
			let toolbarsNeeded = Math.ceil(numberOfButtonsToDraw / 2);

			for (let button of buttonsArray) {

				elements.push(button);
				buttonsInToolbar++;
				numberOfButtonsToDraw--;

				if (buttonsInToolbar === 1) {

					if (numberOfButtonsToDraw === 0) {

						actionsButtons.rows.push({
							cols: [
								{ width: MARGIN_ACTION_PANEL },
								{
									view: "toolbar",
									height: 75,
									css: "tb-actions",
									elements: elements,
									borderless: true
								},
								{ width: MARGIN_ACTION_PANEL }
							]
						});

						toolbarsComplete++;
					}

				} else if (buttonsInToolbar === 2) {

					actionsButtons.rows.push({
						cols: [
							{ width: MARGIN_ACTION_PANEL },
							{
								view: "toolbar",
								height: 75,
								css: "tb-actions",
								elements: elements,
								borderless: true
							},
							{ width: MARGIN_ACTION_PANEL }
						]
					});

					elements = [];
					buttonsInToolbar = 0;
					toolbarsComplete++;
				}

				if ((numberOfButtonsToDraw === 0) && (toolbarsComplete === toolbarsNeeded)) {

					let toolbarsAllowed = TOOLBARS_ALLOWED - toolbarsNeeded;

					for (let i = 1; i <= toolbarsAllowed; i++) {

						actionsButtons.rows.push({ height: 75 });
					}
				}
			}

			return actionsButtons;
		}

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


		function openMap() {

			let containerId = $$(self.containerId).config.id;
			let taskManagerService = window.sm.getService(Services.TASK_MANAGER_SERVICE);
			let module = util.module.getFromModuleAction(self.config.action.loadIncidentInMap);
			let moduleAction = { id: self.config.action.loadIncidentInMap };

			if (module) {

				if (self.parent.args.viewTypeId === VIEW_TYPE_EXTERNAL) {

					let mod = {
						module: module,
						moduleAction: moduleAction,
						args: {
							selectedPks: [self.incidentReport.id],
						}
					};

					taskManagerService.externalize(self.uid, mod);

				} else {

					if (self.parent.args.viewTypeId === VIEW_TYPE_WINDOW) {

						taskManagerService.minimize(containerId);
					}

					let args = {
						selectedPks: [self.incidentReport.id],
						containerId: containerId
					}

					util.module.open(module, moduleAction, VIEW_TYPE_TAB, null, args, null);
				}
			}
		}
	
		function showOperationalPlan() {
			
			let h = document.documentElement.clientHeight - 50;
			let w = document.documentElement.clientWidth - 50;
			let x = 0;
			let y = 0;
			
			const img = new Image();

			img.onload = function() {

				x = this.width < w ? (document.documentElement.clientWidth - this.width) / 2 : 0;
				y = this.height + 50 < h ? (document.documentElement.clientHeight - this.height + 50) / 2 : 0;
				h = this.height + 50 < h ? this.height + 50 : h;
				w = this.width < w ? this.width : w;
				
				const winHtml = `<!DOCTYPE html>
				    <html>
				        <head>
				        	<meta charset="utf-8">
				            <title>${i18n.t("LBL_OPERATION_PLAN") + " - " + self.incidentReport.incidentType.alias}</title>
				        </head>
				        <body style='margin: 0 0 0 0;'>
				            <img src='${img.src}'/>
				        </body>
				    </html>`;
				
				const winUrl = URL.createObjectURL(
				    new Blob([winHtml], { type: "text/html" })
				);
				
				window.open(
				    winUrl,
				    "win",
				    `width=${w},height=${h},screenX=${x},screenY=${y}`
				);
			};
	
			img.src = self.incidentReport.operationalPlanUrl;
		}

		function showDocuments() {

			let paths;

			if (Array.isArray(self.config.defaultDocDirs) && self.config.defaultDocDirs.length > 0) {

				paths = self.config.defaultDocDirs.map(path => {

					return util.fillTemplate(path, { incidentTypeId: util.getDirNameFromBasicElementId(self.incidentType.id) });
				})

				let fileManager = new FileManager(i18n.t("LBL_INCIDENT_DOCUMENTS"), self.incidentType.alias + " - " + self.incidentType.description, paths, FileManager.MODE_DOWNLOAD);

				fileManager.show();
			}
		}

		function showAttachments() {

			if (self.config.defaultAttachmentDir) {

				let events = {};
				let fileManager = undefined;
				let path = util.fillTemplate(self.config.defaultAttachmentDir, { incidentReportId: util.getDirNameFromBasicElementId(self.incidentReport.id) });

				events[FileManager.EventTypes.ON_TRACE_FILES] = onTraceFile;

				if (self.userData.id === self.incidentReport.currentUser.id) {

					fileManager = new FileManager(i18n.t("LBL_INCIDENT_ATTACHMENTS"), self.incidentType.alias + " - " + self.incidentType.description, [path], FileManager.MODE_DOWNLOAD_UPLOAD, events);

				} else {

					fileManager = new FileManager(i18n.t("LBL_INCIDENT_ATTACHMENTS"), self.incidentType.alias + " - " + self.incidentType.description, [path], FileManager.MODE_DOWNLOAD, events);

				}

				fileManager.show();
			}

			function onTraceFile(logFiles) {

				if (Array.isArray(logFiles) && logFiles.length > 0) {

					let body = { "incidentReportLogs": [] };

					logFiles.forEach(logFile => {

						let logSubtypeId = undefined;

						if (logFile.file && logFile.action) {

							if (logFile.action == FileManager.FILE_UPLOADED) {

								logSubtypeId = self.config.logIncidentSubTypeAttachmentUploaded;
							}
							else if (logFile.action == FileManager.FILE_DELETED) {

								logSubtypeId = self.config.logIncidentSubTypeAttachmentDeleted;
							}

							if (logSubtypeId) {

								body.incidentReportLogs.push({
									"incidentReportId": self.incidentReport.id,
									"incidentReportLogValues": [
										{
											"logTypeParamId": self.config.iReportLogValuesParamIdState,
											"value": self.incidentReport.incidentState ? self.incidentReport.incidentState.labelAlias : "-"
										},
										{
											"logTypeParamId": self.config.iReportLogValuesParamIdComment,
											"value": logFile.file.fileName + " - " + util.fileSize(logFile.file.size)
										}
									],
									"level": "INFO",
									"logSubtypeId": logSubtypeId,
									"userId": self.userData.id
								});
							}
						}
					});

					if (Array.isArray(body.incidentReportLogs) && body.incidentReportLogs.length > 0) {

						webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.actionEndpoints.addLogIncidentReport, body).then(function(data) {


						});
					}
				}
			}
		}
		
		function filterTasksByRoadImpact(tasks) {
			
			let filteredTasks = undefined;
			let roadImpact = self.incidentReport.roadImpact;
			
			if (Array.isArray(tasks)) {
				
				filteredTasks = tasks.filter(e => !e.incidentTypeTask.roadImpact || (roadImpact && e.incidentTypeTask.roadImpact.id === roadImpact.id));
			}
			
			return filteredTasks;
		}

		function cancelIncidentReport() {

			if (sameUser) {

				let ms = new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_INCIDENT_CONFIRM_CANCEL_DESCRIPTION"), null, onAccept, null);
				ms.show();

			} else {

				let ms = new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_INCIDENT_CONFIRM_CANCEL_DESCRIPTION"), null, function() {

					onAccept(true);

				}, null);
				ms.show();
			}

			function onAccept(changeUser) {

				let userId = (changeUser) ? self.userData.id : null;

				let endpoint = util.fillTemplate(self.config.actionEndpoints.cancel, {
					id: self.incidentReport.id,
					userId: userId
				});

				self.parent.restAction(endpoint);
			}
		}

		function acceptChangeIncident(selectedIncidentTypeId, selectedStretchId, selectedLocationId, selectedRoadImpactId) {
				
			(new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_CHANGE_CONFIRM_DESCRIPTION"), null, onAccept, null)).show();

			function onAccept(res) {

				let body = {
					id: self.incidentReport.id,
					incidentTypeId: selectedIncidentTypeId,
					stretchId: selectedStretchId,
					locationId: selectedLocationId,
					roadImpactId: selectedRoadImpactId
				};

				let endpoint = util.fillTemplate(self.config.actionEndpoints.changeIncident, body);

				webix.ajax().post(appConfig.restUrl + endpoint).then(undefined, function(response) { util.ajax.handleError(response); });
			}
		}

		function changeIncident() {

			let changeLocationType = new IncidentReportChangeType(self.config, self.incidentReport, undefined, onAcceptChangeType);
			changeLocationType.show();

			function onAcceptChangeType(selectedIncidentTypeId) {

				let changeLocation = new IncidentReportChangeLocation(self.config, self.incidentReport, selectedIncidentTypeId, onAcceptChangeLocation);
				changeLocation.show();
				
				function onAcceptChangeLocation(selectedStretchId, selectedLocationId) {
					
					let selectedRoadImpactId = undefined;
					
					webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.incidentTypeStretchs, { incidentTypeId: selectedIncidentTypeId, stretchId: selectedStretchId })).then(

						function(responseIncidentTypeStretchs) {
	
							if (responseIncidentTypeStretchs != null) {
	
								let incidentTypeStretch = responseIncidentTypeStretchs.json();
	
								if (incidentTypeStretch && Array.isArray(incidentTypeStretch.roadImpacts) && incidentTypeStretch.roadImpacts.length > 0) {
									
									let dialogRoadImpacts = webix.copy(SearchDialogBox.CUSTOM);
									let label = i18n.t("LBL_ROAD_IMPACTS");

									dialogRoadImpacts.data = [];

									dialogRoadImpacts.pkField = "id"
									dialogRoadImpacts.showEmptyRowCol = SearchDialogBox.ID_NOT_SELECTED;
									dialogRoadImpacts.fields = ["alias=LBL_ALIAS", "description=LBL_DESCRIPTION"];
									dialogRoadImpacts.sortFieldId = 1;
	
									incidentTypeStretch.roadImpacts.sort((a, b) => (a.alias > b.alias) ? 1 : ((b.alias > a.alias) ? -1 : 0));
	
									incidentTypeStretch.roadImpacts.forEach(p => {
	
										dialogRoadImpacts.data.push({
											id: p.id,
											alias: p.alias,
											description: p.description
										})
									});
	
									let s = new SearchDialogBox(label, dialogRoadImpacts, function(items) {
	
										if (Array.isArray(items) && items.length === 1) {
	
											let roadImpact = items[0];
	
											if (roadImpact.id !== SearchDialogBox.ID_NOT_SELECTED) {
	
												selectedRoadImpactId = roadImpact.id;
											}
											
											acceptChangeIncident(selectedIncidentTypeId, selectedStretchId, selectedLocationId, selectedRoadImpactId);
										}
									});
									
									s.show();
									
								} else {
									
									acceptChangeIncident(selectedIncidentTypeId, selectedStretchId, selectedLocationId);
								}
							}
						}
					);				
				}
			}
		}
		
		function changeRoadImpact() {
			
			let dialogRoadImpacts = webix.copy(SearchDialogBox.CUSTOM);
			let label = i18n.t("LBL_ROAD_IMPACTS");
			let roadImpacts = getRoadImpacts();
			
			dialogRoadImpacts.data = [];
			dialogRoadImpacts.pkField = "id"
			
			if (self.incidentReport.roadImpact) {
				
				roadImpacts = roadImpacts.filter(e => self.incidentReport.roadImpact && self.incidentReport.roadImpact.id != e.id);
				dialogRoadImpacts.showEmptyRowCol = SearchDialogBox.ID_NOT_SELECTED;
			}
			
			dialogRoadImpacts.fields = ["alias=LBL_ALIAS", "description=LBL_DESCRIPTION"];
			dialogRoadImpacts.sortFieldId = 1;
			
			if (Array.isArray(roadImpacts) && roadImpacts.length > 0) {
				
				roadImpacts.sort((a, b) => (a.alias > b.alias) ? 1 : ((b.alias > a.alias) ? -1 : 0));
				
				roadImpacts.forEach(e => {

					dialogRoadImpacts.data.push({
						id: e.id,
						alias: e.alias,
						description: e.description
					})
				});
			}

			let s = new SearchDialogBox(label, dialogRoadImpacts, function(items) {

				if (Array.isArray(items) && items.length === 1) {
					
					let roadImpact = items[0];
					let roadImpactId = undefined;
					
					if (roadImpact.id != SearchDialogBox.ID_NOT_SELECTED && 
						((self.incidentReport.roadImpact && self.incidentReport.roadImpact.id != roadImpact.id) || 
							!self.incidentReport.roadImpact)) {
						
						roadImpactId = roadImpact.id
					}
					
					let body = {
						id: self.incidentReport.id,
						roadImpactId: roadImpactId
					};

					let endpoint = util.fillTemplate(self.config.actionEndpoints.changeRoadImpactIncidentReport, body);

					webix.ajax().post(appConfig.restUrl + endpoint).then(undefined, function(response) { util.ajax.handleError(response); });
				}
			});

			s.show();
		}

		function getLowerButtonsViewArray() {

			let body = []

			if (self.config.showOperationalPlan && self.incidentReport.operationalPlanUrl !== undefined) {

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

			if (self.config.showDocuments) {
				body.push(
					{
						view: "button",
						heigth: 50,
						css: "panel-button",
						label: i18n.t("LBL_INCIDENT_DOCUMENTS"),
						tooltip: i18n.t("LBL_INCIDENT_DOCUMENTS_DESCRIPTION"),
						type: "icon",
						icon: "mdi mdi-file-document-multiple-outline",
						on: {

							onItemClick() {

								showDocuments();
							}
						}
					});
				body.push({ height: 10 });
			}


			if (self.config.showAttachments) {
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
			}


			return body;
		}


		function getTimeRemaining() {

			let generatedAt = self.incidentReport.generatedAt;
			let valueInSecs = undefined;

			if (self.incidentReport.incidentType.autoConfirmIncident) {

				valueInSecs = self.incidentReport.incidentType.autoConfirmIncident;
				setTimeInterval();

			} else {

				if (self.config.restEndpoints.systemParamConfirmIncident && self.config.systemParamConfirmIncident) {

					webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.systemParamConfirmIncident, { id: self.config.systemParamConfirmIncident })).then(function(systemParamsResponse) {

						if (systemParamsResponse != null) {

							let systemParams = systemParamsResponse.json();
							valueInSecs = systemParams.value;
							setTimeInterval();
						}
					});
				}
			}

			function setTimeInterval() {

				let button = $$("action-button-" + self.uid);
				let timeService = window.sm.getService(Services.TIME_SERVICE);
				let actualDate = timeService.serverTimeMs;
				let millisOfDiference = actualDate - generatedAt;

				if (millisOfDiference < (valueInSecs * 1000) && button) {

					let pendingSecs = ((valueInSecs * 1000) - millisOfDiference) / 1000;

					self.timeOut = setInterval(function() {

						button.config.label = i18n.t("LBL_INCIDENT_ACTION_CONFIRM") + ": " + Math.floor(pendingSecs) + " " + i18n.t("LBL_SECONDS");
						button.refresh();
						pendingSecs--;

						if (pendingSecs < 0) {

							button.config.label = i18n.t("LBL_INCIDENT_ACTION_CONFIRM") + ": - " + i18n.t("LBL_SECONDS");
							button.refresh();
						}

					}, 999);
				}
			}
		}


		return viewActionPanel;
	}

}