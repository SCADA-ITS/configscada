import GenericModule from "../modules/genericModule.js";
import DataTable from "../component/datatable.js";
import SearchDialogBox from "../component/searchDialogBox.js";
import InputDialogBox from "../component/inputDialogBox.js";
import { util } from "../lib/util.js";



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
		this.dtLogs = undefined;

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
			
			if (!this.currentSequenceOfState) {
				
				this.currentSequenceOfState = this.incidentReport.sequenceOfStates[this.incidentReport.sequenceOfStates.length - 1];
			}

			if (this.currentSequenceOfState) {
				
				this.closed = (this.currentSequenceOfState.state.id === this.incidentReport.sequenceOfStates[this.incidentReport.sequenceOfStates.length - 1].state.id);
				this.currentSequenceOfState.finalState = this.closed;

				let tasks = this.currentSequenceOfState.taskStates;

				if (Array.isArray(tasks) && tasks.length) {

					this.currentTask = tasks.find(e => {

						return !e.completedAt;
					});
				}


				this.incidentType = window.em.getByField(EntityType.IMS_INCIDENT_TYPE, "id", this.incidentReport.incidentType.id);
				
				let container = $$(this.args.containerId);

				if (container) {
				
					util.ui.setWindowTitle(container, i18n.t("LBL_INCIDENT_REPORT") + " [" + util.getSimpleId(this.incidentReport.id) + "]" + " - " + this.incidentReport.incidentType.alias);
				}

				this.drawComponents();

				if (this.dtLogs) {
					this.dtLogs.callbacks();
				}

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
			
			if (this.closed) {
				
				webix.alert({
					title: i18n.t("LBL_INCIDENT_CANCEL_OR_DELETE"),
					ok: i18n.t("LBL_YES"),
					text: i18n.t("LBL_INCIDENT_CANCEL_OR_DELETE_DESCRIPTION"),
					type:"alert-warning",
					width: 600
				});	
			}
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
				borderless: true,
				cells: [],
				tabbar: {
					tabMargin: 6,
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
											template: `<div><span class="ico webix_icon mdi mdi-identifier"></span><span class="field">${i18n.t("LBL_INCIDENT_REPORT_ID")}</span></div>
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

						let tableLogs = self.getTableLogs();

						self.dtLogs = tableLogs;

						return tableLogs.getView();
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
//										css: (function() {
//
//											if (!self.closed && self.incidentReport.confirm === true) {
//
//												if (Array.isArray(self.currentSequenceOfState.taskStates) && self.currentSequenceOfState.taskStates.length) {
//
//													let tasksCompleted = self.currentSequenceOfState.taskStates.filter(e => e.completedAt);
//
//													if (tasksCompleted.length < self.currentSequenceOfState.taskStates.length) {
//
//														return "action-button-dis";
//													}
//												}
//											}
//
//											return "action-button";
//
//										})(),
										css: "action-button",
										disabled: (function() {

											if (!self.closed && self.incidentReport.confirm === true) {

												if (Array.isArray(self.currentSequenceOfState.taskStates) && self.currentSequenceOfState.taskStates.length) {

													let tasksCompleted = self.currentSequenceOfState.taskStates.filter(e => e.completedAt);

													if (tasksCompleted.length < self.currentSequenceOfState.taskStates.length) {

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
															
															let body = {
																id:  self.incidentReportId,
																userId: data[0].id
															};
															let endpoint = util.fillTemplate(self.config.actionEndpoints.assignUserIncidentReport, body);
															self.restAction(endpoint);

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
																label: "LBL_INCIDENT_REPORT_ALARMS",
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
																		widthPx: 400,
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
																		position: 3,
																		required: true,
																		sourceField: "elementAlias",
																		visible: true,
																		label: "LBL_ELEMENT_ALIAS",
																		widthPx: 150,
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
															let h = document.documentElement.clientHeight - 600;
															let w = document.documentElement.clientWidth - 1100;
															//let h = 400;
															//let w =  800;

															let body = {
																rows: tableAlarms.getView()
															};
		
															util.ui.loadWindow(
																i18n.t("LBL_INCIDENT_REPORT_ALARMS"),
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
														i18n.t("LBL_OPERATION_PLAN"),
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
										disabled: self.incidentType.incidentTypeDocuments !== undefined && self.incidentType.incidentTypeDocuments.length > 0 ? false : true,
										on: {

											onItemClick() {
												
												let incidentType = self.incidentType;
												let windowId = webix.uid();
												let filePanelViewList = [];
												let fileDownloadList = [];
												let panelHiddenList = [];
												let panelViewList = [];
												
												function getDocumentName(documentUrl) {
													
														let documentNameAux = documentUrl.split("/");
														let documentName = documentNameAux[documentNameAux.length - 1];
														return documentName;
												} 
												
												const getDocumentsTypeWindowsHeader = () => {

													let viewHeader = {
														borderless: true,
														rows: [
															{ height: 20 },
															{
																cols: [
																	{width: 10},
																	{
																		borderless: true,
																		height: 60,
																		width: 50,
																		template: function() {
																			let headerIcon = "regular-file-64.png";
																			return `<image height="80%" src="img/${headerIcon}" />`;
																		}
																	},
																	{width: 10},
																	{
																		view:"label", 
																		label: `<div class="title"><span class="section-text" style="font-size: large;">${i18n.t("LBL_IMS_INCIDENTS_FILES")}</span></div>`, 
																		width: 300,
																		align:"left"
																	},
																	{gravity: 5},
																	{
																		rows:[
																			{height: 10},
																			{
																				id: windowId + "_search",
																				view: "search",
																				placeholder: i18n.t("LBL_SEARCH"),
																				name: "plan",
																				icon: "webix_input_icon wxi-search",
																				width: 400,
																				height: 40,
																				align:"right",
																				on: {
																					onKeyPress(code,code2){

																						let value = $$(windowId + "_search").getValue();
																						if(code === 8){
																							value = value.slice(0, -1);
																						} else {
																							value = value + code2.key;
																						}
																						value = value.toLowerCase();

																						panelViewList.forEach(e=>{
																							let control = true;
																							
																							if(control){
																								if(e.alias.toLowerCase().search(value) !== -1){
																									control = false;
																								}
																							}
																							if(control){
																								if(e.description.toLowerCase().search(value) !== -1){
																									control = false;
																								}
																							}
																							if(control){
																								if(getDocumentName(e.documentUrl).toLowerCase().search(value) !== -1){
																									control = false;
																								}
																							}

																							if(control === true){
																								panelHiddenList.push(e.id);
																								$$(e.id + "_absPanel").hide();
																							} else {
																								panelHiddenList = panelHiddenList.filter((value) => { 
																									return value !== e.id;
																								});
																								$$(e.id + "_absPanel").show();
																							}
																						});

																					}
																				}
																			},
																			{height: 10}
																		]
																	}
																]

															}
														]
													};
									
													return viewHeader;
												}

												const drawpanel = (id,selected) => {
													let v_selected =   { "background": "#94bbf2ff" };
													let v_unSelected = { "background": "#ffffff" };
													let body = {
														view:"panel", 
														top: 30, 
														left:20, 
														id: id + "_Panel", 
														width:130, 
														height: 100,  
														type: "line", 
														body: { 
															id: id + "_PanelTemplate", 
															css: selected ? v_selected : v_unSelected, 
															template: "<div></div>" }
													};
													return body;
												}

												const drawsquare = (id,selected) => {
													let v_selected =   { "background": "#94bbf2ff" };
													let v_unSelected = { "background": "#ffffff" };
													let body = {
														borderless: true,
														height: 60,
														width: 50,
														top: 55,
														left: 55,
														id: id + "_Square",
														css: selected ? v_selected : v_unSelected,
														template: function() {
															let headerIcon = "regular-file-64.png";
															return `<image height="90%" src="img/${headerIcon}" />`;
														}
													};
													return body;
												}

												const getDocumentsTypeWindowsContent = () => {

													let body = [
														{height: 6},
														{ 	
															space: "line",
															cols: [
																{ width: 30 },
																{
																	view: "scrollview",
																	width: document.documentElement.clientWidth - 900,
																	scroll:"x", 
																	height:200,
																	body:{
																		cols: filePanelViewList
																	}
																},
																{ width: 20 }
															]
														},
														{height: 6}
													]
													return body;

												}

												const downloadFileAction = (fileDownloadList, panelHiddenList, panelViewList) => {
													fileDownloadList.forEach(element =>{

														if(!panelHiddenList.includes(element)){
															let docToDownload = panelViewList.find(e=> e.id === element ).documentUrl;
															window.open(docToDownload.replace(" ","%20"),'_blank' );
														}

													});
												}

												const getDocumentsTypeFooterBody = () => {

													let body = [					
														{
															height: 250,
															rows:[
																{
																	cols: [
																		{																			
																			view:"label", 
																			label: `<div class="title"><i style="color:#d1d5d9;; font-size: 2em;" class="mdi mdi-view-headline"></i></div>`, 
																			align:"left",
																			height: 50,
																			width: 40 
																		},
																		{   
																			view:"label", 
																			label: `<div class="title"><span class="section-text" style="color:#8e9aaa; font-size: medium;">${i18n.t("LBL_DETAILS_DESCRIPTION")}</span></div>`, 
																			align:"left",
																			height: 50
																		},
																		{}
																	]
																},
																{
																	height: 60,
																	cols:[
																		{
																			width: 40
																		},
																		{
																			width: 80,
																			rows: [
																				{
																					view:"label", 
																					label: `<div class="title"><span class="section-text" style="color:#8e9aaa; font-size: small;">${i18n.t("LBL_NAME")}</span></div>`, 
																					align:"left",
																					height: 20
																				
																				},
																				{
																					view:"label", 
																					label: `<div class="title"><span class="section-text" style="color:#8e9aaa; font-size: small;">${i18n.t("LBL_TASK")}</span></div>`, 
																					align:"left",
																					height: 20
																				
																				},
																			]
																		},
																		{
																			rows: [
																				{
																					id: windowId + "_labelFooterName",
																					view:"label", 
																					label: `<div class="title"><span class="section-text" style="font-size: small;">${getDocumentName(incidentType.incidentTypeDocuments[0].documentUrl)}</span></div>`, 
																					align:"left",
																					height: 20
																				
																				},
																				{
																					id: windowId + "_labelFooterTask",
																					view:"label", 
																					label: `<div class="title"><span class="section-text" style="font-size: small;">${incidentType.incidentTypeDocuments[0].alias}</span></div>`, 
																					align:"left",
																					height: 20
																				
																				}
																			]
																		}
																	]
																},
																{
																	height: 10
																},
																{
																	height: 110,
																	cols:[
																		{	
																			id: windowId + "_labelFooterDescription",
																			borderless: true,
																			template: `<div style="background: #edf0f7ff; padding: 10px;"><p >${incidentType.incidentTypeDocuments[0].description}</p></div>`, 
																			
																		},
																		{
																			width: 40
																		},
																		{
																			width: 250,
																			rows:[
																				{},
																				{ 
																					view: "button", 
																					css: "dialog-download",
																					width: 250, 
																					label: i18n.t("LBL_DOWNLOAD"),
																					type:"icon",
																					icon: "webix_icon wxi-download",
																					on: {
																						onItemClick() {
																							downloadFileAction(fileDownloadList, panelHiddenList, panelViewList);
																						}
																					}
																				}
																			]
																		}
																	]
																}
															]
														}
													];

													return body;
												}
											
												const getDocumentBody = () => {

													let body = [
														{
															cols:[
																{
																	width: 30
																},
																
																	getDocumentsTypeWindowsHeader()
																,
																{
																	width: 20
																}
															]
														},
														{ height: 6 },
														{
															type:"line",
															rows: getDocumentsTypeWindowsContent()
														},
														{ height: 6 },
														{
															cols: [
																{ width: 30 },
																{
																	rows: getDocumentsTypeFooterBody()
																},
																{
																	width: 20
																}
															]
														}
													]
													return body;

												}

												if (incidentType.incidentTypeDocuments.length > 0) {
													let counter = 0;
													incidentType.incidentTypeDocuments.map(e =>{
														
														let panelId = "panelDocType" + webix.uid();
														counter === 0 ? fileDownloadList.push(panelId) : null;
														
														panelViewList.push({ id: panelId, alias: e.alias, description: e.description, documentUrl: e.documentUrl});
														filePanelViewList.push(
															{    
																id: panelId + "_absPanel",           
																view:"abslayout",
																css: { "font-size": "x-small;" },
																hidden: false,
																width:200, height:200, borderless: true,
																cells:[
																	drawpanel(panelId,counter === 0 ? true : false),
																	drawsquare(panelId,counter === 0 ? true : false),
																	{
																		view:"checkbox", top: 25, left:120, id: panelId + "_Check", width:40, height: 40, css: "dialog-download",
																		data: { alias: e.alias, description: e.description, documentName: getDocumentName(e.documentUrl) },
																		value: counter === 0 ? 1 : 0,

																		click: (id) => {

																			if($$(id.split("_")[0] + "_Check").data.value === 1){

																				fileDownloadList.push(id.split("_")[0]);
																				$$(windowId + "_labelFooterName").setValue(`<div class="title"><span class="section-text" style="font-size: small;">${$$(id.split("_")[0] + "_Check").data.data.documentName}</span></div>`);
																				$$(windowId + "_labelFooterTask").setValue(`<div class="title"><span class="section-text" style="font-size: small;">${$$(id.split("_")[0] + "_Check").data.data.alias}</span></div>`);

																				let description = {																		
																					id: windowId + "_labelFooterDescription",
																					borderless: true,
																					template: `<div style="background: #edf0f7ff; padding: 10px;"><p >${$$(id.split("_")[0] + "_Check").data.data.description}</p></div>`, 
																					
																				}

																				webix.ui( description, $$(windowId + "_labelFooterDescription"));
																				webix.ui( drawpanel(id.split("_")[0],true), $$(id.split("_")[0] + "_Panel"));
																				webix.ui( drawsquare(id.split("_")[0],true), $$(id.split("_")[0] + "_Square"));
																			} 

																			if($$(id.split("_")[0] + "_Check").data.value === 0){

																				fileDownloadList = fileDownloadList.filter((value) => { 
																					return value !== id.split("_")[0];
																				});

																				webix.ui( drawpanel(id.split("_")[0],false), $$(id.split("_")[0] + "_Panel"));
																				webix.ui( drawsquare(id.split("_")[0],false), $$(id.split("_")[0] + "_Square"));

																			}
																		}
																	},
																	{
																		view:"label", 
																		label: `<div class="title"><span class="section-text" style="font-size: x-small;">${getDocumentName(e.documentUrl)}</span></div>`, 
																		align:"left",
																		height: 30,
																		width: 160,
																		top: 125,
																		left: 30
																	},
																	{
																		view:"label", 
																		label: `<div class="title"><span class="section-text" style="font-size: x-small;">${e.alias}</span></div>`, 
																		align:"left",
																		height: 30,
																		width: 160,
																		top: 145,
																		left: 30,
																	}
																]
															}
														);
														counter =+ 1;
													});
												}

												let h = document.documentElement.clientHeight ;
												let w =  document.documentElement.clientWidth - 850;

												let body = {
													rows: getDocumentBody()
												};
												
												util.ui.loadWindow(
													i18n.t("LBL_ICNCIDENT_DOCUMENT_TYPE") + " " + incidentType.alias,
													body,
													h,
													w
												);

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

												util.ui.notImplementedMessage();
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

	getTableLogs = () => {
		console.log(this);
		let incidentReportId = this.incidentReportId;
		
		let addLogIncidentReportEndPoint = this.config.actionEndpoints.addLogIncidentReport;
		let deleteLogIncidentReportEndPoint = this.config.actionEndpoints.deleteLogIncidentReport;

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
			gridCommands:[
				{
					id: 1,
					value: "LBL_ADD",
					icon: "wxi-plus",
					default_: true,
					label: "LBL_CMD_ADD",
					//showText: true,
					position: 1
				},
				{
					id: 2,
					value: "LBL_DELETE",
					icon: "wxi-trash",
					label: "LBL_CMD_DELETE",
					default_: false,
					//showText: true,
					position: 2,
					multiselect: true,
					itemSelected: true
				}
			],
			gridFields: [
				{
					extended: false,
					filtered: false,
					isEpochMilli: false,
					isPk: false,
					label: "LBL_ID",
					labelTooltip: "LBL_ID",
					needsTranslation: false,
					parameterized: false,
					position: 1,
					required: true,
					sourceField: "id",
					visible: false,
					widthPx: 300,
					gridFieldDataType: {
						id: 1,
						name: "string"
					},									
					gridFieldIcons: [
					]
				},
				{
					extended: false,
					filtered: false,
					isEpochMilli: true,
					isPk: false,
					label: "LBL_TIME_STAMP",
					needsTranslation: false,
					parameterized: false,
					position: 2,
					//required: true,
					sourceField: "incidentLogTime",
					visible: true,
					widthPx: 180,
					gridFieldDataType: {
						id: 4,
						name: "date"
					},									
					gridFieldIcons: [
					]
				},
				{
					extended: false,
					filtered: false,
					isEpochMilli: false,
					isPk: false,
					label: "LBL_LEVEL",
					labelTooltip: "LBL_LEVEL",
					needsTranslation: false,
					parameterized: false,
					position: 3,
					//required: true,
					sourceField: "incidentLogLevel",
					visible: false,
					widthPx: 200,
					gridFieldDataType: {
						id: 1,
						name: "string"
					},									
					gridFieldIcons: [
					]
				},
				{
					extended: false,
					filtered: false,
					isEpochMilli: false,
					isPk: false,
					label: "LBL_ID",
					labelTooltip: "LBL_ID",
					needsTranslation: false,
					parameterized: false,
					position: 1,
					required: true,
					sourceField: "incidentLogSubtypeId",
					visible: false,
					widthPx: 300,
					gridFieldDataType: {
						id: 1,
						name: "string"
					},									
					gridFieldIcons: [
					]
				},
				{
					extended: false,
					filtered: false,
					isEpochMilli: false,
					isPk: false,
					label: "LBL_LOG_SUBTYPE",
					labelTooltip: "LBL_LOG_SUBTYPE",
					needsTranslation: true,
					parameterized: false,
					position: 4,
					required: true,
					sourceField: "incidentLogSubtype",
					visible: true,
					widthPx: 120,
					gridFieldDataType: {
						id: 1,
						name: "string"
					},									
					gridFieldIcons: [
					]
				},
				{
					extended: false,
					filtered: false,
					isEpochMilli: false,
					isPk: false,
					label: "LBL_USER",
					labelTooltip: "LBL_USER",
					needsTranslation: false,
					parameterized: false,
					position: 5,
					required: true,
					sourceField: "incidentLogUser",
					visible: true,
					widthPx: 100,
					gridFieldDataType: {
						id: 1,
						name: "string"
					},									
					gridFieldIcons: [
					]
				},
				{
					extended: false,
					filtered: false,
					isEpochMilli: false,
					isPk: false,
					label: "LOG_IMS_PARAM_1_STATE",
					labelTooltip: "LOG_IMS_PARAM_1_STATE",
					needsTranslation: true,
					parameterized: false,
					position: 6,
					required: true,
					sourceField: "incidentLogValueState",
					visible: true,
					widthPx: 100,
					gridFieldDataType: {
						id: 1,
						name: "string"
					},									
					gridFieldIcons: [
					]
				},
				{
					extended: false,
					filtered: false,
					isEpochMilli: false,
					isPk: false,
					label: "LOG_IMS_PARAM_ARG1",
					labelTooltip: "LOG_IMS_PARAM_ARG1",
					needsTranslation: false,
					parameterized: false,
					position: 7,
					required: true,
					sourceField: "incidentLogValueArg1",
					visible: true,
					widthPx: 600,
					gridFieldDataType: {
						id: 1,
						name: "string"
					},									
					gridFieldIcons: [
					]
				},
				{
					extended: false,
					filtered: false,
					isEpochMilli: false,
					isPk: false,
					label: "LOG_IMS_PARAM_ARG2",
					labelTooltip: "LOG_IMS_PARAM_ARG2",
					needsTranslation: true,
					parameterized: false,
					position: 8,
					required: true,
					sourceField: "incidentLogValueArg2",
					visible: true,
					widthPx: 150,
					gridFieldDataType: {
						id: 1,
						name: "string"
					},									
					gridFieldIcons: [
					]
				}
			]
		};

		let data = [];
		let logList = this.incidentReport.incidentLogs !== undefined ? this.incidentReport.incidentLogs : undefined; 
		if (logList !== undefined && Array.isArray(logList) && logList.length) {

			logList.forEach(element => {

				let row = {};
				row["id"] = element.id;
				row["incidentLogTime"]  = element.time;
				row["incidentLogLevel"] = element.level;
				row["incidentLogSubtypeId"] = element.logSubtype.id
				row["incidentLogSubtype"] = element.logSubtype.labelAlias;
				row["incidentLogUser"] = element.user.name;

				row["incidentLogValueState"] = null;
				row["incidentLogValueArg1"]  = null;
				row["incidentLogValueArg2"]  = null;

				element.incidentReportLogValues.forEach(e => {
					e.logTypeParam.id === INCIDENTS_LOGS_VALUE_STATE ? row["incidentLogValueState"] = e.value : null;
					e.logTypeParam.id === INCIDENTS_LOGS_VALUE_ARG1  ? row["incidentLogValueArg1"]  = e.value : null;
					e.logTypeParam.id === INCIDENTS_LOGS_VALUE_ARG2  ? row["incidentLogValueArg2"]  = e.value : null;
				});

				data.push(row);

			});
			
		}

		let events = {};
		events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];
		events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
			commandId: 1,
			function: () => {

				(new InputDialogBox(i18n.t("LBL_DIALOG_CREATE_LOG"), i18n.t("LBL_DIALOG_CREATE_LOG_TEXT"), function(comments) {

					let userData = util.user.auth();
					
					let body = {
								"incidentReportLogs":
									[
										{
											"incidentReportId": incidentReportId,
											"incidentReportLogValues": [
												{
													"logTypeParamId": "LogTypeParam:1:1",
													"value": "LBL_IMS_INCIDENT_STATE_INITIAL"
												},
												{
													"logTypeParamId": "LogTypeParam:1:2",
													"value": comments
												}
											],
											"level": "INFO",
											"logSubtypeId": "LogSubtype:17",
											"userId": userData.id
										}
									]
								};
					
					webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + addLogIncidentReportEndPoint, body).then(function(data) {
						//console.log(data);
					});
					
				})).show();

			}
		});
		
		events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
			commandId: 2,
			function: () => {

				let rowsSelected = this.dtLogs.rowsSelected;
				if(!this.dtLogs.rowsSelected.length>0){
					webix.alert({
						title: i18n.t("LBL_INCIDENT_LOG_NOT_SELECTED"),
						ok: i18n.t("LBL_YES"),
						text: i18n.t("LBL_INCIDENT_LOG_NOT_SELECTED_DESCRIPTION"),
						type:"alert-warning",
						width: 600
					});	
				} else {
					let control = false;
					this.dtLogs.rowsSelected.forEach(e => {
						let row = this.dtLogs.data.find(dato => dato.id === e);
						row.incidentLogSubtypeId !== INCIDENTS_LOGS_MANUAL_SUBTYPE ? control = true : null;
					});

					if(!control) {

						webix.confirm({
							title: i18n.t("LBL_INCIDENT_LOG_DELETE_CONFIRM"),
							ok: i18n.t("LBL_YES"),
							cancel: i18n.t("LBL_NO"),
							text: i18n.t("LBL_INCIDENT_LOG_DELETE_CONFIRM_DESCRIPTION"),
							width: 600
						}).then(() => {

							rowsSelected.forEach(e => {
								
								let endpoint = util.fillTemplate(deleteLogIncidentReportEndPoint, {
									id: e
								});
			
								this.restAction(endpoint);	
							});
										
						});

					} else {
						webix.alert({
							title: i18n.t("LBL_INCIDENT_LOG_SUBTYPE_ERROR"),
							ok: i18n.t("LBL_YES"),
							text: i18n.t("LBL_INCIDENT_LOG_SUBTYPE_ERROR_DESCRIPTION"),
							type:"alert-warning",
							width: 600
						});	
					}
				}

			}
		});

		let dtLogs = new DataTable(null, null, metadata, data, events);
		return dtLogs;

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
