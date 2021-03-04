import GenericModule from "../modules/genericModule.js";
import DataTable from "../component/datatable.js";
import SearchDialogBox from "../component/searchDialogBox.js";
//import { EventTypes } from "../component/datatable.js";
import { util } from "../lib/util.js";

//const INCIDENT_REPORT = Object.freeze({
//	moduleAction: {
//		edit: 100401,
//		confirm: 100402,
//		cancel: 100403
//	},
//	restEndpoint: "incidents/reports/${id}",
//	topic: "/topic/incidentReport",
//	actionEndpoints: {
//		cancel: "incidents/reports/${id}/cancel",
//		confirm: "incidents/reports/${id}/confirm",
//		forward: "incidents/reports/${id}/forward",
//		finalizeTask: "incidents/reports/${id}/finalize_task/${taskId}"
//	}
//});

export default class IncidentReportModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.currentTask = undefined;
		this.currentSequenceOfState = undefined;
		this.dtTasks = undefined;
		this.incidentReport = undefined;
		this.stompClient = undefined;
		this.requestReport = true;
		this.disconnectStomp = true;
		this.closed = false;
		
		//this.config = JSON.parse(module.moduleValues.find(e => e.moduleParam.id === PARAM_MODULE_TYPE_CUSTOM_WINDOW_CONFIG).value);
		this.config = module.config;

		if (this.moduleAction.id === this.config.moduleAction.edit) {

			if (this.args && this.args.selectedPks && this.args.selectedPks.length > 0) {

				this.incidentReportId = this.args.selectedPks[0];

				// TODO: Delete this hardcode line
				//this.incidentReportId = "ImsIncidentReport:1";
			}
		}
	}

	getView() {

		let view = undefined;

		if (this.moduleAction.id === this.config.moduleAction.edit) {

			view = {
				css: "custom_window",
				cols: [
					{
						rows: [
							{ height: 6 },
							{
								cols: [
									{ width: 6 },
									{
										id: this.uid,
										rows: [],
										hidden: false
									},
									{
										id: "loading-" + this.uid,
										rows: [this.getViewDrawNoData()],
										hidden: true
									},
									{ width: 6 }
								]
							},
							{ height: 6 }
						]
					}
				]
			}
		}

		return view;
	}

	loadData() {

		if (this.moduleAction.id === this.config.moduleAction.edit) {

			this.refresh();
		}
		else if (this.moduleAction.id === this.config.moduleAction.confirm) {

			this.actionConfirm();
		}
		else if (this.moduleAction.id === this.config.moduleAction.cancel) {

			this.actionCancel();
		}

	}

	getViewDrawNoData() {

		return {
			view: "template",
			template: function() {

				let icon = "<img src='img/loading.svg' align='center'>";

				return "<div class='empty-app-inner'>" + icon + "<span class='empty-app-inner-text'>" + i18n.t("LBL_LOADING_DATA") + "</span></div>";
			}
		}
	}

	actionConfirm() {

		let self = this;
		let incidentReportsToConfirm = [];
		
		if (Array.isArray(this.args.selectedPks) && this.args.selectedPks.length > 0) {

			let pendingRequest = this.args.selectedPks.length;
			
			self.args.selectedPks.forEach(e => {
			
				webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoint, { id: e })).then(function(dat) {
					
					if (dat != null) {
						
						let incidentReport = dat.json();
						
						if (!incidentReport.confirm) {
							
							incidentReportsToConfirm.push(incidentReport.id);
						}
					}
					
					if (--pendingRequest == 0) {
						
						if (incidentReportsToConfirm.length > 0) {
						
							webix.confirm({
								title: i18n.t("LBL_INCIDENT_CONFIRM_CONFIRM"),
								ok: i18n.t("LBL_YES"),
								cancel: i18n.t("LBL_NO"),
								text: i18n.t("LBL_INCIDENT_CONFIRM_CONFIRM_DESCRIPTION"),
								width: 600
							}).then(function() {
								
								incidentReportsToConfirm.forEach(incidentReportToConfirmId => {
									
									let endpoint = util.fillTemplate(self.config.actionEndpoints.confirm, { id: incidentReportToConfirmId });
									
									console.log(endpoint);
				
									self.restAction(endpoint);
				
								});
							});	
						}
						else {
							
							webix.alert({
								title: i18n.t("LBL_INCIDENT_CONFIRM_NOTHING_TO_CONFIRM"),
								ok: i18n.t("LBL_YES"),
								text: i18n.t("LBL_INCIDENT_CONFIRM_NOTHING_TO_CONFIRM_DESCRIPTION"),
								type:"alert-warning",
								width: 600
							});
						}
					}
				});				
			});
		}
	}

	actionCancel() {

		let self = this;
		
		if (Array.isArray(this.args.selectedPks) && this.args.selectedPks.length > 0) {
			
			webix.confirm({
				title: i18n.t("LBL_INCIDENT_CONFIRM_CANCEL"),
				ok: i18n.t("LBL_YES"),
				cancel: i18n.t("LBL_NO"),
				text: i18n.t("LBL_INCIDENT_CONFIRM_CANCEL_DESCRIPTION"),
				width: 600
			}).then(function() {
				
				self.args.selectedPks.forEach(e => {
					
					let endpoint = util.fillTemplate(self.config.actionEndpoints.cancel, { id: e });
					
					console.log(endpoint)

					self.restAction(endpoint);

				});
			});
		}
	}

	refresh() {

		let self = this;

		$$("loading-" + this.uid).show();
		$$(this.uid).hide();

		if (this.requestReport) {

			if (this.moduleAction.id === this.config.moduleAction.edit) {

				this.requestReport = false;

				webix.ajax().get(appConfig.restUrl + util.fillTemplate(this.config.restEndpoint, { id: self.incidentReportId })).then(function(dat) {

					if (dat != null) {

						self.incidentReport = dat.json();

						self.update();
					}

				}, function(response) { util.ajax.handleError(response); });
			}
		}
		else {

			this.update();
		}
	}
	update() {


		if (this.incidentReport) {

			this.currentSequenceOfState = this.incidentReport.sequenceOfStates.find(e => {

				return (e.state.id == this.incidentReport.incidentState.id);
			});

			if (this.currentSequenceOfState) {

				let tasks = this.currentSequenceOfState.taskStates;

				if (Array.isArray(tasks) && tasks.length) {

					this.currentTask = tasks.find(e => {

						return !e.completedAt;
					});
				}

				this.drawComponents();

				if (this.dtTasks) {

					this.dtTasks.callbacks();

					if (this.currentTask) {

						this.dtTasks.select(this.currentTask.incidentTypeTask.id);
					}
					else {

						this.dtTasks.select(tasks[0].incidentTypeTask.id);
					}
				}
			}

			if (this.moduleAction.id === this.config.moduleAction.edit && !this.stompClient) {

				this.callbacks();
			}

			this.initTabViewContent();

			$$("loading-" + this.uid).hide();
			$$(this.uid).show();
		}
	}

	callbacks() {

		this.connect_ws();
		this.on_destroy();
	}

	initTabViewContent() {

		let tabViewContent = $$("tabview-content" + this.uid);
		let tabDetails = $$("tab-details-" + this.uid);
		let tabTaskBoard = $$("tab-task-board-" + this.uid);
		let tabLogs = $$("tab-logs-" + this.uid);

		if (this.incidentReport && tabViewContent) {

			if (this.incidentReport.confirm) {

				if (tabTaskBoard) {

					tabViewContent.getTabbar().setValue(tabTaskBoard.config.id);
				}
			}
			else {

				if (tabDetails) {

					tabViewContent.getTabbar().setValue(tabDetails.config.id);
				}

				if (tabTaskBoard) {

					tabTaskBoard.disable();
				}

				if (tabLogs) {

					tabLogs.disable();
				}
			}
		}
	}

	on_destroy() {

		let self = this;
		let view = $$(this.uid);

		if (view) {

			view.attachEvent("onDestruct", function() {

				self.disconnect_ws();
			});
		}
	}

	drawComponents() {

		let self = this;
		this.dtTasks = undefined;

		let viewMain = {
			id: this.uid,
			cols: [
				{
					rows: [
						getViewHeader(),
						{ height: 20 },
						getViewStateTransitions(),
						{ height: 6 },
						getViewContent(),
						{ height: 6 }
					]
				},
				{ width: 6 },
				drawActionPanel()
			]
		};

		function getViewHeader() {

			let viewHeader = {

				id: "viewHeader-" + self.uid,
				borderless: true,
				rows: [
					{
						id: "view-bar-color-level-" + self.uid,
						height: 15,
						css: { "background": self.incidentReport.incidentLevel.color }
					},
					{
						id: "view-header-info-" + self.uid,
						height: 80,
						template: function() {

							let incidentIcon = self.incidentReport.incidentType.icon;

							if (incidentIcon == undefined) {
								incidentIcon = "not_found_50.png";
							}
							return `<image class="ims-incident-report-icon" src="img/${incidentIcon}" />
									<div class="ims-incident-report-title">
										${self.incidentReport.incidentType.alias} ${self.incidentReport.incidentType.description}
									</div>
									<div class="ims-incident-report-title">
										<span class="ims-incident-badge" style="background: ${self.incidentReport.incidentLevel.color}; color: white;">${i18n.t(self.incidentReport.incidentLevel.labelAlias)}</span>
									</div>`;
						}
					}
				]
			};

			return viewHeader;
		}

		function getViewStateTransitions() {

			let tbStates = {
				view: "toolbar",
				css: "ims-incident-report-state-box",
				borderless: true,
				height: 68,
				id: "tb-states" + self.uid,
				elements: []
			};

			let labelNoCss = false;

			let lblState = {
				rows: [
					{
						height: 66,
						css: { "margin-top": "-24px" },
						borderless: true,
						template: function() {

							if (self.incidentReport.confirm && self.incidentReport.confirm == true) {

								let steps = `<div class="progressbar-wrapper">
										      <ul class="progressbar">`;

								self.incidentReport.sequenceOfStates.forEach(e => {

									steps += `<li style="width: ${100 / self.incidentReport.sequenceOfStates.length}% !important" `;
									if (e.state.id === self.incidentReport.incidentState.id) {
										//if (e.state.id === 	"ImsIncidentState:2") {

										labelNoCss = true;
										steps += `class="current">`;
									}
									else {

										if (!labelNoCss) {

											steps += `class="active">`;
										}
										else {

											steps += `>`;
										}
									}

									steps += `${i18n.t(e.state.labelAlias)}</li>`;

								});

								steps += `  </ul>
										  </div>`;

								return steps;
							}
							else {

								return `<div></div>`;
							}
						}
					}
				]
			};

			tbStates.elements.push(lblState);

			return lblState;
		}

		function getViewContent() {

			let contentTabs = {
				view: "tabview",
				id: "tabview-content" + self.uid,
				css: "secondary-tab",
				//				tabbar: {
				//					optionWidth: 283
				//				},
				animate: false,
				cells: [],
				tabbar: {
					on: {
						onBeforeTabClick: function(id) {
							return $$(id).isEnabled();
						}
					}
				}
			};

			let tasks = self.currentSequenceOfState.taskStates;

			contentTabs.cells.push({
				header: i18n.t("LBL_DETAILS"),
				body: getTabDetails()
			});

			if (Array.isArray(tasks) && tasks.length) {

				contentTabs.cells.push({
					header: i18n.t("LBL_TASK_BOARD"),
					body: getTabTasksBoard()
				});
			}

			contentTabs.cells.push({
				header: i18n.t("LBL_LOGS"),
				body: getTabLogs()
			});

			let viewContent = {

				id: "viewContent-" + self.uid,
				rows: [
					{
						cols: [
							{ width: 15 },
							contentTabs,
							{ width: 15 }
						]
					},
					{ height: 2 }
				]

			};

			function getTabDetails() {

				let tabDetails = {
					id: "tab-details-" + self.uid,
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
											template: `<div><span class="ico webix_icon mdi mdi-identifier"></span><span class="field">${i18n.t("LBL_INCIDENT_REPORT")}</span></div>
													   <div class="value">${util.getSimpleId(self.incidentReport.id)}</div>`
										},
										{
											height: 50,
											borderless: true,
											template: `<div><span class="ico webix_icon mdi mdi-exclamation-thick"></span><span class="field">${i18n.t("LBL_INCIDENT")}</span></div>
													   <div class="value">${self.incidentReport.incidentType.alias} - ${self.incidentReport.incidentType.description}</div>`
										},
										{
											height: 50,
											borderless: true,
											template: `<div><span class="ico webix_icon mdi mdi-map-marker"></span><span class="field">${i18n.t("LBL_INCIDENT_LOCATION")}</span></div>
													   <div class="value">${self.incidentReport.location.alias}</div>`
										},
										{
											height: 50,
											borderless: true,
											template: `<div><span class="ico webix_icon mdi mdi-road"></span><span class="field">${i18n.t("LBL_INCIDENT_STRETCH")}</span></div>
													   <div class="value">${self.incidentReport.affectionStretch.alias}</div>`
										},
										{
											height: 50,
											borderless: true,
											template: `<div><span class="ico webix_icon mdi mdi-account"></span><span class="field">${i18n.t("LBL_INCIDENT_USER_ASSIGNED")}</span></div>
													   <div class="value">${self.incidentReport.currentUser.name}</div>`
										},
										{
											height: 50,
											borderless: true,
											template: `<div><span class="ico webix_icon mdi mdi-clock-time-three-outline"></span><span class="field">${i18n.t("LBL_INCIDENT_CREATED")}</span></div>
													   <div class="value">${util.toLocaleString(self.incidentReport.generatedAt / 1000)}</div>`
										},
										{
											height: 50,
											borderless: true,
											template: `<div><span class="ico webix_icon mdi mdi-refresh"></span><span class="field">${i18n.t("LBL_INCIDENT_LAST_UPDATED")}</span></div>
													   <div class="value">-</div>`
										},
										{
											height: 50,
											borderless: true,
											template: `<div><span class="ico webix_icon mdi mdi-calendar"></span><span class="field">${i18n.t("LBL_INCIDENT_VALID_UNTIL")}</span></div>
													   <div class="value">-</div>`
										}
									]
								},
								{ width: 40 }
							]
						},
						{}
					]
				};

				return tabDetails;
			}

			function getTabTasksBoard() {

				let tabTasksBoard = {
					id: "tab-task-board-" + self.uid,
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
								self.getTaskDetails(),
								{ width: 10 }
							]
						},
						{ height: 10 }
					]
				};

				function getTasks() {

					if (self.currentSequenceOfState) {

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

						if (Array.isArray(tasks) && tasks.length) {

							tasks.sort((a, b) => (a.incidentTypeTask.position > b.incidentTypeTask.position) ? 1 : ((b.incidentTypeTask.position > a.incidentTypeTask.position) ? -1 : 0));

							for (let i = 0; i < tasks.length; i++) {

								let row = {};

								row.id = tasks[i].incidentTypeTask.id;
								row.status = tasks[i].completedAt ? "confirmed" : "not_confirmed";
								row.position = i + 1;
								row.alias = tasks[i].incidentTypeTask.alias;
								row.type = tasks[i].incidentTypeTask.auto ? "auto" : "manual";
								row.required = tasks[i].incidentTypeTask.mandatory ? "yes" : "no";

								data.push(row);
							}
						}

						let events = {};

						events[DataTable.EventTypes.ON_SELECT_ROW] = function(rows) {

							if (Array.isArray(rows) && rows.length) {

								let taskDetailsBody = $$("taskDetails-" + self.uid);

								if (taskDetailsBody) {

									webix.ui(self.getTaskDetails(rows[0]), taskDetailsBody);
								}

							}
						};

						self.dtTasks = new DataTable(null, null, metadata, data, events);

						return self.dtTasks.getView();
					}
				}

				return tabTasksBoard;
			}

			function getTabLogs() {

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

					if (self.currentSequenceOfState) {

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
							gridFields: [
								{
									extended: false,
									filtered: false,
									isEpochMilli: false,
									isPk: false,
									label: "LBL_COLUMN",
									labelTooltip: "LBL_COLUMN",
									needsTranslation: false,
									parameterized: false,
									position: 1,
									required: true,
									sourceField: "id",
									visible: true,
									widthPx: 300,
									gridFieldDataType: {
										id: 1,
										name: "string"
									}
								}
							]
						};

						let dtLogs = new DataTable(null, null, metadata, []);

						return dtLogs.getView();
					}
				}

				return tabLogs;
			}

			return viewContent;
		}

		function drawActionPanel() {

			const marginActionPanel = 15;
			
			let viewActionPanel = {

				id: "viewActionPanel-" + self.uid,
				width: 300,
				css: "panel-action",
				rows: [
					{
						height: 95,
						cols: [
							{ width: marginActionPanel },
							{
								rows: [
									{ height: 20 },
									{
										view: "button",
										css: (function() {

											if (!self.closed && self.incidentReport.confirm === true) {

												if (Array.isArray(self.currentSequenceOfState.taskStates) && self.currentSequenceOfState.taskStates.length) {

													let tasksCompleted = self.currentSequenceOfState.taskStates.filter(e => e.completedAt);

													if (tasksCompleted.length < self.currentSequenceOfState.taskStates.length) {

														return "action-button-dis";
													}
												}
											}

											return "action-button";

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
										//type: "icon",
										on: {

											onItemClick() {

												if (!self.closed) {

													if (self.incidentReport.confirm === true) {

														let endpoint = util.fillTemplate(self.config.actionEndpoints.forward, {
															id: self.incidentReportId
														});

														if (Array.isArray(self.currentSequenceOfState.taskStates) && self.currentSequenceOfState.taskStates.length) {

															let tasksCompleted = self.currentSequenceOfState.taskStates.filter(e => e.completedAt);

															if (tasksCompleted.length == self.currentSequenceOfState.taskStates.length) {

																self.restAction(endpoint);
															}
														}
														else {

															self.restAction(endpoint);
														}
													}
													else {

														let endpoint = util.fillTemplate(self.config.actionEndpoints.confirm, {
															id: self.incidentReportId
														});

														self.restAction(endpoint);
													}
												}
												else {

													let container = $$(self.args.containerId);

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
							{ width: marginActionPanel }
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
									{ width: marginActionPanel },
									{
										view: "toolbar",
										height: 75,
										css: "tb-actions",
										elements: [
											{
												view: "button",
												width: 135,
												label: i18n.t("LBL_INCIDENT_VIEW_CAMERAS"),
												tooltip: i18n.t("LBL_INCIDENT_VIEW_CAMERAS_DESCRIPTION"),
												type: "iconTop",
												icon: "mdi mdi-cctv",
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
												label: i18n.t("LBL_INCIDENT_VIEW_IN_MAP"),
												tooltip: i18n.t("LBL_INCIDENT_VIEW_IN_MAP_DESCRIPTION"),
												type: "iconTop",
												icon: "mdi mdi-map-marker",
												on: {
		
													onItemClick() {
		
														util.ui.notImplementedMessage();
													}
												}
											}
										],
										borderless: true
									},
									{ width: marginActionPanel }
								]
							},
							{
								cols: [
									{ width: marginActionPanel },
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
												type: "iconTop",
												icon: "mdi mdi-account",
												on: {
		
													onItemClick() {
		
														function resultSearch(data) {
														
															// TODO: Implement mutation to change user here
															console.log(data);
														}
														
														let s = new SearchDialogBox("LBL_SELECT_USER", SearchDialogBox.USERS, resultSearch);
														
														s.show();
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
														
														if (self.incidentReport.alarms !== undefined && self.incidentReport.alarms.length > 0) {

															const defaultGridOptionValues = [
																{ gridOption: { id: 1 }, value: "true" },
																{ gridOption: { id: 2 }, value: "true" },
																{ gridOption: { id: 3 }, value: undefined },
																{ gridOption: { id: 4 }, value: undefined },
																{ gridOption: { id: 5 }, value: undefined },
																{ gridOption: { id: 6 }, value: undefined },
																{ gridOption: { id: 7 }, value: "true" },
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
																label: "LBL_ALARMS",
																visible: true,
																gridOptionValues: defaultGridOptionValues,
																gridFields: [
																	{
																		extended: false,
																		filtered: false,
																		isEpochMilli: false,
																		isPk: false,
																		needsTranslation: true,
																		parameterized: false,
																		position: 1,
																		required: true,
																		sourceField: "alarmConfigAlias",
																		visible: true,
																		label: "LBL_ALARM_CONFIG_ALIAS",
																		widthPx: 300,
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
																		position: 2,
																		required: true,
																		sourceField: "elementTypeLabelAlias",
																		visible: true,
																		label: "LBL_ELEMENT_TYPE_ALIAS",
																		widthPx: 200,
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
																		sourceField: "elementAlias",
																		visible: true,
																		label: "LBL_ELEMENT_ALIAS",
																		widthPx: 200,
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
									
																	row.alarmConfigAlias = alarms[i].alarmConfig.labelAlias;
																	row.elementTypeLabelAlias = alarms[i].element.elementType.labelAlias;
																	row.elementAlias = alarms[i].element.alias;
									
																	data.push(row);
																}
															}

															let tableAlarms = new DataTable(null, null, metadata, data);
															let h = document.documentElement.clientHeight - 200;
															let w = document.documentElement.clientWidth - 900;
															//let h = 400;
															//let w =  800;

															let body = {
																rows: tableAlarms.getView()
															};
		
															util.ui.loadWindow(
																"LBL_ALARMS",
																body,
																h,
																w
															);	
														}
													}
												}
											}
										],
										borderless: true
									},
									{ width: marginActionPanel }
								]
							}
						]
					},
					{ height: 40 },
					{
						view: "label",
						css: "section",
						label: `<div class="title"><span class="section-text">${i18n.t("LBL_INCIDENT_SECTION_DOCUMENTS")}</span></div>`,
						borderless: true
					},
					{ height: 20 },
					{
						height: 150,
						cols: [
							{ width: 50 },
							{
								rows: [
									{
										view: "button",
										css: "panel-button",
										label: i18n.t("LBL_INCIDENT_OPERATIONAL_PLAN"),
										tooltip: i18n.t("LBL_INCIDENT_OPERATIONAL_PLAN_DESCRIPTION"),
										type: "icon",
										icon: "mdi mdi-sitemap",
										on: {

											onItemClick() {

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
														"LBL_OPERATION_PLAN",
														body,
														h,
														w
													);
												};

												img.src = self.incidentReport.operationalPlanUrl;
											}
										}
									},
									{ height: 10 },
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

												util.ui.notImplementedMessage();
											}
										}
									},
									{ height: 10 },
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


											}
										}
									}
								]
							},
							{ width: 50 }
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
										
										webix.confirm({
											title: i18n.t("LBL_INCIDENT_CONFIRM_CANCEL"),
											ok: i18n.t("LBL_YES"),
											cancel: i18n.t("LBL_NO"),
											text: i18n.t("LBL_INCIDENT_CONFIRM_CANCEL_DESCRIPTION"),
											width: 600
										}).then(function() {

											let endpoint = util.fillTemplate(self.config.actionEndpoints.cancel, {
												id: self.incidentReportId
											});
	
											self.restAction(endpoint);											
										});
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

										let container = $$(self.args.containerId);

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

			return viewActionPanel;
		}

		webix.ui(viewMain, $$(this.uid));
	}

	restAction(endpoint) {

		console.log("Incident report action => " + endpoint);

		webix.ajax().post(appConfig.restUrl + endpoint).then(function(dat) {

			if (dat != null) {

				let resAction = dat.json();
			}
		}, function(response) { util.ajax.handleError(response); });
	}

	getTaskDetails(incidentTypeTaskId) {

		let self = this;
		let taskDetailsBody = {};
		let task = undefined;
		let taskDetails = {};

		if (!incidentTypeTaskId && self.currentTask) {

			task = self.currentTask;
		}
		else {

			let tasks = self.currentSequenceOfState.taskStates;

			if (Array.isArray(tasks) && tasks.length) {

				task = tasks.find(e => {

					return e.incidentTypeTask.id === incidentTypeTaskId;
				});

				if (!task) {

					task = tasks[0];
				}
			}
		}

		if (task) {

			taskDetailsBody = {
				id: "taskDetailsBody-" + self.uid,
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

				id: "taskDetails-" + self.uid,
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
						height: 55,
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
								height: 50,
								width: 150,
								label: (function() {

									return i18n.t("LBL_INCIDENT_END_TASK");
								})(),
								on: {

									onItemClick() {

										if (task) {

											if (!task.incidentTypeTask.auto && !task.completedAt) {

												let endpoint = util.fillTemplate(self.config.actionEndpoints.finalizeTask, {
													id: self.incidentReportId,
													taskId: self.currentTask.incidentTypeTask.id
												});

												self.restAction(endpoint);
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

	connect_ws() {

		let self = this;

		function on_connected() {

		}

		function on_message(message) {

			var data = JSON.parse(JSON.parse(message.body));

			self.disconnectStomp = false;

			if (data.action === "UPDATE" && !self.closed) {

				console.log(data);

				self.requestReport = true;

				self.refresh();
			}
			else if (data.action === "DELETE") {

				console.log(data);

				self.requestReport = false;

				if (Array.isArray(self.incidentReport.sequenceOfStates) && self.incidentReport.sequenceOfStates.length > 0) {

					let lastState = self.incidentReport.sequenceOfStates[self.incidentReport.sequenceOfStates.length - 1];

					self.incidentReport.incidentState = lastState.state;
					self.incidentReport.incidentState.finalState = true;

					self.closed = true;
					self.refresh();
				}


				//				webix.alert({
				//				    title: i18n.t("LBL_INCIDENT_CLOSED") + " " + self.incidentReport.id,
				//				    ok: "LBL_OK",
				//				    text: "LBL_INCIDENT_CLOSED DESCRIPTION",
				//					width: 600,
				//				    type:"alert-warning"
				//				}).then(function(){
				//				    
				//					let container = $$(self.args.containerId); 	
				//										
				//					if (container) {
				//						
				//						container.close();
				//					}
				//				});

			}
		}

		let url = appConfig.stompUrl;
		let topic = this.config.topic + "." + this.incidentReportId;

		this.stompClient = util.stomp.connect(url, topic, on_connected, on_message);
	}

	disconnect_ws() {

		if (this.disconnectStomp) {

			if (this.stompClient && this.stompClient.connected) {

				util.stomp.disconnect(this.stompClient);
				this.stompClient = undefined;
			}
		}
		else {

			this.disconnectStomp = true;
		}

	}
}
