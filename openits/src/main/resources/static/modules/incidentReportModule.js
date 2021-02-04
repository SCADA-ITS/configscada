import GenericModule from "../modules/genericModule.js";
import DataTable from "../component/datatable.js";
import { util } from "../lib/util.js";


const INCIDENT_REPORT = Object.freeze({
	moduleAction: {
		edit: 100401,
		confirm: 100402,
		cancel: 100403 
	},
	restEndpoint: "incidents/reports/",
	topic: "/topic/incidentReport"
});

export default class IncidentReportModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.currentSequenceOfState = undefined;
		this.currentTask = undefined;
		this.dtTasks = undefined;
		this.incidentReport = undefined;
		this.stompClient = undefined;

		if (this.moduleAction.id === INCIDENT_REPORT.moduleAction.edit) {

			if (this.args && this.args.selectedPks && this.args.selectedPks.length > 0) {

				this.incidentReportId = this.args.selectedPks[0];

				// TODO: Delete this hardcode line
				//this.incidentReportId = "ImsIncidentReport:1";
			}
		}
	}

	getView() {

		let view = undefined;

		if (this.moduleAction.id === INCIDENT_REPORT.moduleAction.edit) {

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
										rows: [this.getViewDrawNoData()]
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

		this.refresh();
	}

	getViewDrawNoData() {

		return {
			view: "template",
			id: "no-data-" + this.uid,
			template: function(data) {

				let icon = "<img src='img/loading.svg' align='center'>";

				return "<div class='empty-app-inner'>" + icon + "<span class='empty-app-inner-text'>" + i18n.t("LBL_LOADING_DATA") + "</span></div>";
			}
		}
	}

	refresh() {

		let self = this;

		if (this.moduleAction.id === INCIDENT_REPORT.moduleAction.edit) {

			webix.ajax().get(appConfig.restUrl + INCIDENT_REPORT.restEndpoint + "/" + self.incidentReportId).then(function(dat) {

				if (dat != null) {

					self.incidentReport = dat.json();
					
					if (self.incidentReport) {
						
						self.currentSequenceOfState = self.incidentReport.sequenceOfStates.find(e => {
						
							return (e.state.id == self.incidentReport.incidentState.id);
						});
	
						self.drawComponents();
						
						if (self.dtTasks) {
							
							self.dtTasks.callbacks();
							
							let tasks = self.currentSequenceOfState.taskStates;
							
							if (Array.isArray(tasks) && tasks.length) {
								
								let idxTask = tasks.findIndex(e => {
									
									return !e.completed;
								});
								
								if (idxTask !== -1) {
								
									self.dtTasks.select(idxTask + 1);
								}
							}
						}	
					}
					
					if (self.moduleAction.id === INCIDENT_REPORT.moduleAction.edit && !self.stompClient) {

						self.callbacks();
					}
					
					self.initTabViewContent();
				}

			}, function(response) { util.ajax.handleError(response); });
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
			
			if (this.incidentReport.confirm && this.incidentReport.confirm == true && tabTaskBoard) {
				
				tabViewContent.getTabbar().setValue(tabTaskBoard.config.id);
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
						css: {"background": self.incidentReport.incidentLevel.color}
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
						css: { "margin-top": "-24px"},
						borderless: true,
						template: function() {
							
							if (self.incidentReport.confirm && self.incidentReport.confirm == true) {
								
								let steps = `<div class="progressbar-wrapper">
										      <ul class="progressbar">`;
								
								self.incidentReport.sequenceOfStates.forEach(e => {
									
									steps += `<li style="width: ${100/self.incidentReport.sequenceOfStates.length}% !important" `;
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
				tabbar:{
					on:{
				    	onBeforeTabClick:function(id){
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
							{ width: 15},
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
						{ height: 40},
						{
							cols: [
								{ width: 40},
								{
									borderless: true,
									rows: [
										{
											height: 50,
											borderless: true,
											template: `<div><span class="ico webix_icon mdi mdi-exclamation-thick"></span><span class="field">${i18n.t("LBL_INCIDENT")}</span></div>
													   <div class="value">${self.incidentReport.incidentType.alias} - ${self.incidentReport.incidentType.description}</div>`	
										},
										{
											height: 50,
											borderless: true,
											template: `<div><span class="ico webix_icon mdi mdi-identifier"></span><span class="field">${i18n.t("LBL_INCIDENT_REPORT")}</span></div>
													   <div class="value">${self.incidentReport.id}</div>`
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
								{ width: 40}
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
						{ height: 10},
						{
							cols: [
								{ width: 10},
								{
									width: 425,
									rows: getTasks() 
								},
								{ view: "resizer" },
								getTaskDetails(),
								{ width: 10}
							]
						},
						{ height: 10}
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
							        isPk: false,
							        needsTranslation: false,
							        parameterized: false,
							        position: 1,
							        required: true,
							        sourceField: "status",
							        visible: true,
							        widthPx: 15,
							        gridFieldDataType: {
							          	id: 1,
										name: "string"
							        }	
								},
								{
							        extended: false,
							        filtered: false,
							        isEpochMilli: false,
							        isPk: true,
							        needsTranslation: false,
							        parameterized: false,
							        position: 2,
							        required: true,
							        sourceField: "id",
							        visible: true,
							        widthPx: 15,
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
							        position: 3,
							        required: true,
							        sourceField: "alias",
							        visible: true,
							        widthPx: 250,
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
							        position: 4,
							        required: true,
							        sourceField: "type",
							        visible: true,
							        widthPx: 50,
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
							        label: "LBL_REQUIRED",
							        labelTooltip: "LBL_REQUIRED",
							        needsTranslation: false,
							        parameterized: false,
							        position: 5,
							        required: true,
							        sourceField: "required",
							        visible: true,
							        widthPx: 50,
							        gridFieldDataType: {
							          	id: 1,
										name: "string"
							        }	
								}
							]
						};
						
						let data = [];
						
						let tasks = self.currentSequenceOfState.taskStates;
						
						if (Array.isArray(tasks) && tasks.length) {
							
							tasks.sort((a, b) => (a.incidentTypeTask.position > b.incidentTypeTask.position) ? 1 : ((b.incidentTypeTask.position > a.incidentTypeTask.position) ? -1 : 0));
						
							for (let i = 0; i < tasks.length; i++) {
						
								let row = {};
								
								row.status = tasks[i].completed ? "executed" : "pending";
								row.id =  i + 1;
								row.alias = tasks[i].incidentTypeTask.alias;
								row.type = tasks[i].incidentTypeTask.auto ? "auto" : "manual";
								row.required = tasks[i].incidentTypeTask.mandatory ? "yes" : "no";
								
								data.push(row);	
							}	
						}
						
						self.dtTasks = new DataTable(null, null, metadata, data);
					
						return self.dtTasks.getView();
					}
				}
				
				function getTaskDetails() {
					
					let taskDetails = {
						
						borderless: false,
						rows: [
							{
								view: "toolbar",
								cols:[
									{ width: 4 },
									{ 
										view: "label", 
										label: `<span class="ico webix_icon mdi mdi-format-list-bulleted"></span><span class="grid-title">${i18n.t("LBL_TASK_DETAILS")}</span>`
									}
								]	
							},
							{}	
						]
					};
					
					
					return taskDetails;
				}
				
				return tabTasksBoard;
			}
			
			function getTabLogs() {
				
				let tabLogs = {
					id: "tab-logs-" + self.uid,
					borderless: true,
					rows: [
						{ height: 10},
						{
							cols: [
								{ width: 10},
								{
									rows: getLogs() 
								},
								{ width: 10}
							]
						},
						{ height: 10}
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

			let viewActionPanel = {

				id: "viewActionPanel-" + self.uid,
				width: 300,
				css: "panel-action",
				rows: [
					{ 
						height: 95,
						cols: [
							{ width: 30},
							{
								rows: [
									{ height: 20 },
									{
									    view: "button",
										css: (function() {
											
											if (self.incidentReport.confirm === true) {
												
												if (Array.isArray(self.currentSequenceOfState.taskStates) && self.currentSequenceOfState.taskStates.length) {
												
													let tasksCompleted = self.currentSequenceOfState.taskStates.filter(e => e.completed);
													
													if (tasksCompleted.length < self.currentSequenceOfState.taskStates.length)	{
														
														return "action-button-dis";
													}
												}
											}
											
											return "action-button";
												
										})(), 
										height: 50,
										label: (function() {
											
											if (self.incidentReport.confirm === true) {
												
												//return i18n.t("LBL_INCIDENT_ACTION_NEXT");											
												return "Next";
											}
											else {
												
												//return i18n.t("LBL_INCIDENT_ACTION_CONFIRM");
												return "Confirm";
											}
										})(),
									    type: "icon", 
										icon: (function() {
											
											if (self.incidentReport.confirm === true) {
																						
												return "mdi mdi-skip-next";
											}
											else {
												
												return "mdi mdi-check-bold";
											}
										})(),
										on: {
										
											onItemClick() {
												
												
											}
										}
									},
									{ height: 20 }			
								]
							},
							{ width: 30}
						]
					},
//					{
//						view: "label",
//						css: "section",
//						label: `<span class="section-text">${i18n.t("LBL_INCIDENT_SECTION_INFO")}</span>`,
//						borderless: true 
//					},
//					{ 
//						height: 280,
//						cols: [
//							{ width: 40 },
//							{
//								width: 200,
//								rows: [
//									{ height: 20},
//									{
//										css: "section-info",
//										borderless: true,
//										template: `<div><span class="field">${i18n.t("LBL_INCIDENT_SINCE")}</span></div>
//											  	   <div>
//													<span class="ico webix_icon mdi mdi-clock-time-three-outline"></span>
//											  	   	<span class="value">${util.toLocaleString(self.incidentReport.generatedAt / 1000)}</span>
//												   </div>`
//									},
//									{
//										css: "section-info",
//										borderless: true,
//										template: `<div><span class="field">${i18n.t("LBL_INCIDENT_LOCATION")}</span></div>
//											  	   <div>
//													<span class="ico webix_icon mdi mdi-map-marker"></span>
//											  	   	<span class="value">${self.incidentReport.location.alias}</span>
//												   </div>`
//									},
//									{
//										css: "section-info",
//										borderless: true,
//										template: `<div><span class="field">${i18n.t("LBL_INCIDENT_STRETCH")}</span></div>
//											  	   <div>
//													<span class="ico webix_icon mdi mdi-road"></span>
//											  	   	<span class="value">${self.incidentReport.affectionStretch.alias}</span>
//												   </div>`
//									},
//									{
//										css: "section-info",
//										borderless: true,
//										template: `<div><span class="field">${i18n.t("LBL_INCIDENT_ASIGNED_USER")}</span></div>
//											  	   <div>
//													<span class="ico webix_icon mdi mdi-account"></span>
//											  	   	<span class="value">${self.incidentReport.currentUser.name}</span>
//												   </div>`
//									}
//								]
//							},
//							{}
//						]
//					},
					{
						view: "label",
						css: "section",
						label: `<span class="section-text">${i18n.t("LBL_INCIDENT_SECTION_ACTIONS")}</span>`,
						borderless: true 
					},
					{ 
						height: 200,
						rows: [
							{ height: 10 },
							{
								view: "toolbar",
								height: 75,
								css: "tb-actions",
								elements: [
									{},
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
									},
									{}
								],
								borderless: true 
							},
							{
								view: "toolbar",
								height: 75,
								css: "tb-actions",
								elements: [
									{},
									{
									    view: "button",
										width: 135, 
										label: i18n.t("LBL_INCIDENT_CHANGE_ASSIGNMENT"),
										tooltip: i18n.t("LBL_INCIDENT_CHANGE_ASSIGNMENT_DESCRIPTION"),
									    type: "iconTop", 
										icon: "mdi mdi-account", 
										on: {
										
											onItemClick() {
												
												util.ui.notImplementedMessage();
											}
										}
									},
									{
									    view: "button",
										width: 135, 
										label: i18n.t("LBL_INCIDENT_SHOW_ALARMS"),
										tooltip: i18n.t("LBL_INCIDENT_SHOW_ALARMS_DESCRIPTION"),
									    type: "iconTop", 
										icon: "mdi mdi-bell", 
										on: {
										
											onItemClick() {
												
												util.ui.notImplementedMessage();
											}
										}
									},
									{}
								],
								borderless: true 
							},
							{}
						]
					},
					{
						view: "label",
						css: "section",
						label: `<span class="section-text">${i18n.t("LBL_INCIDENT_SECTION_DOCUMENTS")}</span>`,
						borderless: true 
					},
					{ 
						height: 150,
						cols: [
							{ width: 50},
							{
								rows: [
									{ height: 10 },
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
									},
									{ height: 10 }
								]
							},
							{ width: 50}
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
								on: {
								
									onItemClick() {
										
										
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

	connect_ws() {
		
		let self = this;

		function on_connected() {

		}

		function on_message(message) {

			var data = JSON.parse(JSON.parse(message.body));
			
			if (data.action === "UPDATE") {
			
				console.log(data);
				
				self.refresh();	
			}
		}

		let url = appConfig.stompUrl;
		let topic = INCIDENT_REPORT.topic + "." + this.incidentReportId;

		this.stompClient = util.stomp.connect(url, topic, on_connected, on_message);
	}

	disconnect_ws() {

		if (this.stompClient && this.stompClient.connected) {

			util.stomp.disconnect(this.stompClient);
			this.stompClient = undefined;
		}
	}
}
