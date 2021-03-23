import GenericModule from "../modules/genericModule.js";
import DataTable from "../component/datatable.js";
import ListView from "../component/listview.js";
import { util } from "../lib/util.js";

export default class IncidentWizardModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();

		this.config = module.config;
		
		this.steps = undefined;
		
		this.listViewStrechs = undefined;
		this.dtLocations = undefined;
		this.listViewIncidentGroups = undefined;
		this.dtIncidentTypes = undefined;
		
		this.incidentData = {
			stretchId: undefined,
			locationId: undefined,
			incidentGroupId: undefined,
			incidentTypeId: undefined
		}
	}

	getView() {

		let view = {
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
									hidden: true
								},
								{
									id: "loading-" + this.uid,
									rows: [getViewDrawNoData()],
									hidden: false
								},
								{ width: 6 }
							]
						},
						{ height: 6 }
					]
				}
			]
		};
		
		function getViewDrawNoData() {

			return {
				view: "template",
				template: function() {
	
					let icon = "<img src='img/loading.svg' align='center'>";
	
					return "<div class='empty-app-inner'>" + icon + "<span class='empty-app-inner-text'>" + i18n.t("LBL_LOADING_DATA") + "</span></div>";
				}
			}
		}

		return view;
	}

	loadData() {

		let self = this;
			
		let getCurrentStep = function() {
			
			let currentStep = undefined;
			
			currentStep = self.steps.find(e => e.active);
			
			return currentStep;
		};
		
		let getStep = function(position) {
			
			let step = undefined;
			
			step = self.steps.find(e => e.position === position);
			
			return step;
		};
		
		let setCurrentStep = function(position) {
			
			let currentStep = getCurrentStep();
			let newStep = undefined;
			
			if (position > 0 && position <= self.steps.length) {
				
				currentStep.active = false;
				
				newStep = getStep(position);
				newStep.active = true;	
			}
			
			return !newStep ? currentStep : newStep;
		}
		
		let resetFromStep = function(step) {
			
			let resetStepStretchAndLocation = function() {
				
				self.incidentData.stretchId = undefined;
				self.incidentData.locationId = undefined;
				
				self.listViewStrechs.select()
				self.dtLocations.clear();
			}
			
			let resetStepIncidentType = function() {
				
				self.incidentData.incidentGroupId = undefined;
				self.incidentData.incidentTypeId = undefined;
				
				self.listViewIncidentGroups.select()
				self.dtIncidentTypes.clear();
			}
			
			let resetStepSummary = function() {
				
			}
			
			if (step) {
			
				let stepsToReset = self.steps.filter(e => e.position > step.position);
			
				if (Array.isArray(stepsToReset) && stepsToReset.length > 0) {
					
					stepsToReset.forEach(e => {
						
						eval(e.resetFunction)();
						
						e.valid = false;
						
						$$("button-next-" + e.position + "-" + self.uid).disable();
					});
				}	
			}
		}
		
		let validateStep = function(step) {
			
			let valid = false;
			let summaryIncidentType = $$("summary-incident-" + self.uid);
			let summaryLocation = $$("summary-location-" + self.uid);
			let summaryStretch = $$("summary-stretch-" + self.uid);
			let summaryUser = $$("summary-user-" + self.uid);
			
			let validateStepStretchAndLocation = function() {
				
				
				
				self.incidentData.stretchId = self.listViewStrechs.getSelectedId();
				self.incidentData.locationId = self.dtLocations.getSelectedIds()[0];
				
				valid = self.incidentData.stretchId && self.incidentData.locationId;
			}
			
			let validateStepIncidentType = function() {
				
				self.incidentData.incidentGroupId = self.listViewIncidentGroups.getSelectedId();
				self.incidentData.incidentTypeId = self.dtIncidentTypes.getSelectedIds()[0];
				
				valid = self.incidentData.incidentGroupId && self.incidentData.incidentTypeId;
			}
			
			let validateStepSummary = function() {
				
			}
			
			if (step) {
			
				eval(step.validateFunction)();
				
				step.valid = valid;
				
				if (valid) {
					
					if (summaryIncidentType) {
					
						summaryIncidentType.setValues({ incidentTypeId: self.incidentData.incidentTypeId });
					}
					
					if (summaryLocation) {
						
						summaryLocation.setValues({ locationId: self.incidentData.locationId });
					}
					
					if (summaryStretch) {
						
						summaryStretch.setValues({ stretchId: self.incidentData.stretchId });
					}
					
					if (summaryUser) {
						
						//summaryStretch.setValues({ stretchId: self.incidentData.stretchId });
					}
					
					$$("button-next-" + step.position + "-" + self.uid).enable();
				}
				else {
					
					$$("button-next-" + step.position + "-" + self.uid).disable();
				}
				
				resetFromStep(step);
			}			
		}
		
		let drawMain = function() {
			
			let multiView = {
				
				view: "multiview",
				id: self.uid,
				animate: false,
				cells: []
			};
			
			let drawHeader = function(position) {
				
				let step = getStep(position);
				
				let viewHeader = {
					css: { "background": "#567eba" },
					rows: [
						{
							height: 52,
							cols: [
								{
									view: "template",
									width: 500,
									borderless: true,
									css: { "background": "transparent" },
									template: function() {
										return `<div class="text">
											  		<span class="header-wizard-first-line">${i18n.t(step.headerLabel)} </span>
											  		<span class="header-wizard-second-line">${i18n.t("LBL_STEP") + " " + step.position + " " + i18n.t("LBL_OF") + " " + self.steps.length + ": " + i18n.t(step.headerLabelDescription)} </span>
												</div>`;
									}
								},
								{},
								{
									view: "template",
									borderless: true,
									css: { "background": "transparent" },
									template: function() {
										return `<div class="text">
											  		<span class="header-wizard-step">${i18n.t("LBL_STEP") + " " + step.position} </span>
												</div>`;
									}
								},
							]
						}
					]
				};
				
				return viewHeader;
			};
			
			let drawProgressBar = function(position) {
				
				let labelNoCss = false;
				
				let viewSteps = {
					rows: [
						{ height: 20 },
						{
							height: 66,
							css: { "margin-top": "-24px" },
							borderless: true,
							template: function() {
								
								if (Array.isArray(self.steps) && self.steps.length > 0) {
									
									let progressBar = `<div class="progressbar-wrapper">
											      <ul class="progressbar">`;
					
									self.steps.forEach(e => {
										
										progressBar += `<li style="width: ${100 / self.steps.length}% !important" `;
										
										if (e.position === position) {
	
											labelNoCss = true;
											progressBar += `class="current">`;
										}
										else {
	
											if (!labelNoCss) {
	
												progressBar += `class="active">`;
											}
											else {
	
												progressBar += `>`;
											}
										}
										
										progressBar += i18n.t(e.labelAlias);
									});
									
									progressBar += `  </ul>
											  </div>`;
	
									return progressBar;
								}
								else {
	
									return `<div></div>`;
								}
							}
						}
					]
				};
				
				return viewSteps;
			}
			
			let drawContent = function(position) {
				
				let step = getStep(position);
				
				let getViewStretchs = function() {
					
					let viewStretchs = {};
					let stretchs = window.em.getAll(EntityType.STRETCH);
					
					if (Array.isArray(stretchs) && stretchs.length > 0) {
												
						let stretchView = stretchs.map(e => {
		
							return {
								id: e.id,
								alias: e.alias,
								description: e.description
							};
						});
						
						let options = {};
						let events = {};
						
						options[ListView.Options.ICON] = false;
						options[ListView.Options.ROW_HEIGHT] = 41;
						
						events[ListView.EventTypes.ON_SELECT_ROW] = function(data) {
							
							webix.extend($$("view-locations-" + self.uid), webix.ProgressBar);
							
							$$("view-locations-" + self.uid).showProgress({
								type: "bottom",
								hide: false
							}); 
							
							self.dtLocations.clear();
							
							webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.locationsByStretch, { id: data })).then(function(dat) {
							
								if (dat != null) {
							
									let locations = dat.json();
									
									if (Array.isArray(locations) && locations.length > 0) {
										
										let dataLocations = locations.map(e => {
											
											return {
												id: e.id,
												alias: e.alias
											}
										});
										
										self.dtLocations.add(dataLocations);
									}
									
									$$("view-locations-" + self.uid).hideProgress();
								}	
							});
							
							validateStep(getCurrentStep());
						};
						
						self.listViewStrechs = new ListView(stretchView, options, events);
						
						viewStretchs = {
							id: "view-stretchs-" + self.uid,
							width: 250,
							rows: [
								{
									view: "label",
									borderless: true,
									height: 23,
									css: "headers",
									label: i18n.t("LBL_STRETCHS")
								},
								{ 
									borderless: false,
									cols: [
										{ width: 1, borderless: false },
										{
											rows: [
												{ height: 1, borderless: false },
												self.listViewStrechs.getView(),
												{ height: 2, borderless: false }
											]
										},
										{ width: 1, borderless: false }
									]
								},
								{ height: 1, borderless: false }
							]
						};
					}
					
					return viewStretchs;
				}
				
				let getViewLocations = function() {
					
					let viewLocations = {};
					
					const defaultGridOptionValues = [
						{ gridOption: { id: 1 }, value: "false" },
						{ gridOption: { id: 2 }, value: "false" },
						{ gridOption: { id: 3 }, value: "2" },
						{ gridOption: { id: 4 }, value: "asc" },
						{ gridOption: { id: 7 }, value: "false" },
						{ gridOption: { id: 8 }, value: "false" },
						{ gridOption: { id: 10 }, value: "true" },
						{ gridOption: { id: 11 }, value: "false" },
						{ gridOption: { id: 12 }, value: "false" },
						{ gridOption: { id: 16 }, value: "img/grid/default.png" },
						{ gridOption: { id: 21 }, value: "auto" },
						{ gridOption: { id: 22 }, value: "0" },
						{ gridOption: { id: 23 }, value: "false" },
						{ gridOption: { id: 24 }, value: "false" }
					];
					
					let metadata = {
						filter: true,
						label: "LBL_LOCATIONS",
						visible: true,
						gridOptionValues: defaultGridOptionValues,
						gridFields: [
							{ id: 1, isPk: true, position: 1, required: true, sourceField: "id", visible: false, widthPx: 1, gridFieldDataType: { id: 1, name: "string" } },
							{ id: 2, isPk: false, position: 2, required: true, sourceField: "alias", visible: true, widthPx: 200, gridFieldDataType: { id: 1, name: "string" } }
						]
					};
					
					let events = {};

					events[DataTable.EventTypes.ON_SELECT_ROW] = function(rows) {

						if (Array.isArray(rows) && rows.length > 0) {

							validateStep(getCurrentStep());

						}
					};
					
					self.dtLocations = new DataTable(null, null, metadata, [], events);
					
					viewLocations = {
						id: "view-locations-" + self.uid,
						rows: [
							{
								view: "label",
								borderless: true,
								height: 23,
								css: "headers",
								label: i18n.t("LBL_LOCATIONS")
							},
							{ height: 5 },
							{ 
								borderless: false,
								cols: [
									{ width: 1, borderless: false },
									{
										rows: self.dtLocations.getView()
									},
									{ width: 1, borderless: false }
								]
							},
							{ height: 1, borderless: false }
						]
					};
					
					return viewLocations;
				}
				
				let getViewIncidentGroups = function() {
					
					let viewIncidentGroups = {};
					let incidentGroups = window.em.getAll(EntityType.IMS_INCIDENT_GROUP);
					
					if (Array.isArray(incidentGroups) && incidentGroups.length > 0) {
												
						let incidentGroupView = incidentGroups.map(e => {
		
							return {
								id: e.id,
								alias: e.alias,
								description: e.description
							};
						});
						
						let options = {};
						let events = {};
						
						options[ListView.Options.ICON] = false;
						options[ListView.Options.ROW_HEIGHT] = 41;
						
						events[ListView.EventTypes.ON_SELECT_ROW] = function(data) {
							
							webix.extend($$("view-incident-types-" + self.uid), webix.ProgressBar);
							
							self.dtIncidentTypes.clear();
							
							if (Array.isArray(data) && data.length > 0) {
								
								$$("view-incident-types-" + self.uid).showProgress({
									type: "bottom",
									hide: false
								});
								
								webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.incidentTypesByStretch, { id: self.incidentData.stretchId })).then(function(dat) {
							
									if (dat != null) {
								
										let incidentTypesByStretch = dat.json();
										
										if (Array.isArray(incidentTypesByStretch) && incidentTypesByStretch.length > 0) {
											
											let incidentTypes = window.em.getAll(EntityType.IMS_INCIDENT_TYPE);
								
											let incidentTypesByGroup = incidentTypes.filter(e => e.incidentGroup.id === data[0] );
											
											if (Array.isArray(incidentTypesByGroup) && incidentTypesByGroup.length > 0) {
												
												let incidentTypesByGroupFiltered = incidentTypesByGroup.filter(itByGroup => {
													
													return (incidentTypesByStretch.find(itByStretch => itByStretch.id === itByGroup.id) != undefined);
												});
												
												let dataIncidentTypes = incidentTypesByGroupFiltered.map(e => {
															
													return {
														id: e.id,
														alias: e.alias,
														description: e.description
													}
												});
												
												self.dtIncidentTypes.add(dataIncidentTypes);
											}
										}
									}
									
									$$("view-incident-types-" + self.uid).hideProgress();	
								});
							}
							
							validateStep(getCurrentStep());
						};
						
						self.listViewIncidentGroups = new ListView(incidentGroupView, options, events);
						
						viewIncidentGroups = {
							id: "view-incident-groups-" + self.uid,
							width: 250,
							rows: [
								{
									view: "label",
									borderless: true,
									height: 23,
									css: "headers",
									label: i18n.t("LBL_INCIDENT_GROUPS")
								},
								{ 
									borderless: false,
									cols: [
										{ width: 1, borderless: false },
										{
											rows: [
												{ height: 1, borderless: false },
												self.listViewIncidentGroups.getView(),
												{ height: 2, borderless: false }
											]
										},
										{ width: 1, borderless: false }
									]
								},
								{ height: 1, borderless: false }
							]
						};
					}
					
					return viewIncidentGroups;
				}
				
				let getViewIncidentTypes = function() {
					
					let viewIncidentTypes = {};
					
					const defaultGridOptionValues = [
						{ gridOption: { id: 1 }, value: "false" },
						{ gridOption: { id: 2 }, value: "false" },
						{ gridOption: { id: 3 }, value: "2" },
						{ gridOption: { id: 4 }, value: "asc" },
						{ gridOption: { id: 7 }, value: "false" },
						{ gridOption: { id: 8 }, value: "false" },
						{ gridOption: { id: 10 }, value: "true" },
						{ gridOption: { id: 11 }, value: "false" },
						{ gridOption: { id: 12 }, value: "false" },
						{ gridOption: { id: 16 }, value: "img/grid/default.png" },
						{ gridOption: { id: 21 }, value: "auto" },
						{ gridOption: { id: 22 }, value: "0" },
						{ gridOption: { id: 23 }, value: "false" },
						{ gridOption: { id: 24 }, value: "false" }
					];
					
					let metadata = {
						filter: true,
						label: "LBL_INCIDENT_TYPES",
						visible: true,
						gridOptionValues: defaultGridOptionValues,
						gridFields: [
							{ id: 1, isPk: true, position: 1, required: true, sourceField: "id", visible: false, widthPx: 1, gridFieldDataType: { id: 1, name: "string" } },
							{ id: 2, isPk: false, position: 2, required: true, sourceField: "alias", visible: true, widthPx: 150, gridFieldDataType: { id: 1, name: "string" } },
							{ id: 3, isPk: false, position: 3, required: true, sourceField: "description", visible: true, widthPx: 300, gridFieldDataType: { id: 1, name: "string" } }
						]
					};
					
					let events = {};

					events[DataTable.EventTypes.ON_SELECT_ROW] = function(rows) {

						if (Array.isArray(rows) && rows.length > 0) {

							validateStep(getCurrentStep());

						}
					};
					
					self.dtIncidentTypes = new DataTable(null, null, metadata, [], events);
					
					viewIncidentTypes = {
						id: "view-incident-types-" + self.uid,
						rows: [
							{
								view: "label",
								borderless: true,
								height: 23,
								css: "headers",
								label: i18n.t("LBL_INCIDENT_TYPES")
							},
							{ height: 5 },
							{ 
								borderless: false,
								cols: [
									{ width: 1, borderless: false },
									{
										rows: self.dtIncidentTypes.getView()
									},
									{ width: 1, borderless: false }
								]
							},
							{ height: 1, borderless: false }
						]
					};
					
					return viewIncidentTypes;
				}
				
				let getViewSummary = function() {
					
					let viewSummary = {};
					
					viewSummary = {
						cols: [
							{ width: 1, borderless: false, css: { "border-left": "transparent !important"} },
							{
								rows: [
									{ height: 1, borderless: false, css: { "border-bottom": "transparent !important"} },
									{
										cols: [
											{ width: 30, borderless: true },
											{
												id: "summary-" + self.uid,
												css: "ims-incident-report-tab-details",
										    	rows: [
													{ height: 30, borderless: true },
													{
														id : "summary-incident-" + self.uid,
														height: 50,
														borderless: true,
														template: function(obj) { 
															return `<div><span class="ico webix_icon mdi mdi-exclamation-thick"></span><span class="field">${i18n.t("LBL_INCIDENT")}</span></div>
																   <div class="value">${obj.incidentTypeId ? obj.incidentTypeId : ''}</div>`
														}
													},
													{
														id : "summary-location-" + self.uid,
														height: 50,
														borderless: true,
														template: function(obj) { 
															return `<div><span class="ico webix_icon mdi mdi-map-marker"></span><span class="field">${i18n.t("LBL_INCIDENT_LOCATION")}</span></div>
																   <div class="value">${obj.locationId ? obj.locationId : ''}</div>`
														}
													},
													{
														id : "summary-stretch-" + self.uid,
														height: 50,
														borderless: true,
														template: function(obj) { 
															return `<div><span class="ico webix_icon mdi mdi-road"></span><span class="field">${i18n.t("LBL_INCIDENT_STRETCH")}</span></div>
																   <div class="value">${obj.stretchId ? obj.stretchId : ''}</div>`
														}
													},
													{
														id : "summary-user-" + self.uid,
														height: 50,
														borderless: true,
														template: function(obj) { 
															return `<div><span class="ico webix_icon mdi mdi-account"></span><span class="field">${i18n.t("LBL_INCIDENT_USER_ASSIGNED")}</span></div>
																   <div class="value"></div>`
														}
													},
													{}
												]
											},
											{ width: 30, borderless: true }							
										]
									},
									{ height: 1, borderless: false, css: { "border-bottom": "transparent !important"}}
								]
							},
							{ width: 1, borderless: false, css: { "border-right": "transparent !important"} }
						]
				    }
					
					return viewSummary;	
				}
				
				let drawStepStretchAndLocation = function() {
			
					let viewStepStretchAndLocation = {
						
						rows: [
							{
								cols: [
									{ width: 45 },
									{
										rows: [
											{ height: 15 },
											{
												view: "label",
												label: `<span class="ico webix_icon mdi mdi-format-list-bulleted" style="font-size: 26px;"></span><span class="grid-title">${i18n.t(step.stepLabel)}</span>`
											},
											{ height: 17 },
											{
												cols: [
													getViewStretchs(),
													{ width : 25 },
													{
														rows: [
															getViewLocations(),		
														]
													}
												]
											}
										]
									},
									{ width: 45 }
								]
							}
						]
					};
					
					return viewStepStretchAndLocation;
				};
				
				let drawStepIncidentType = function() {
					
					let viewStepIncidentType = {
						
						rows: [
							{
								cols: [
									{ width: 45 },
									{
										rows: [
											{ height: 15 },
											{
												view: "label",
												label: `<span class="ico webix_icon mdi mdi-format-list-bulleted" style="font-size: 26px;"></span><span class="grid-title">${i18n.t(step.stepLabel)}</span>`
											},
											{ height: 17 },
											{
												cols: [
													getViewIncidentGroups(),
													{ width : 25 },
													{
														rows: [
															getViewIncidentTypes(),		
														]
													}
													
												]
											}
										]
									},
									{ width: 45 }
								]
							}
						]
					};
					
					return viewStepIncidentType;
				};
				
				let drawStepSummary = function() {
					
					let viewStepSummary = {
						
						rows: [
							{
								cols: [
									{ width: 45 },
									{
										rows: [
											{ height: 15 },
											{
												view: "label",
												label: `<span class="ico webix_icon mdi mdi-format-list-bulleted" style="font-size: 26px;"></span><span class="grid-title">${i18n.t(step.stepLabel)}</span>`
											},
											{ height: 17 },
											getViewSummary()	
										]
									},
									{ width: 45 }
								]
							}
						]
					};
					
					return viewStepSummary;
				};
				
				return eval(step.drawFunction)();
			}
			
			let drawToolbarActions = function(position) {

				let firstStep = false;
				let lastStep = false;
				let step = getStep(position);
				
				if (Array.isArray(self.steps) && self.steps.length > 0) {
										
					firstStep = (step.position === 1);
					lastStep = (!firstStep && step.position === self.steps.length);   
				}
				
				let viewToolbarActionsButtons = [
					{ width: 18 },
					{ 
						id: "button-cancel-" + position + "-" + self.uid,
						view: "button",
						css: "dialog-cancel", 
						width: 130,
						height: 35,
						label: i18n.t("LBL_CANCEL"),
						on: {
							onItemClick() {
							
								let container = $$(self.args.containerId);

								if (container) {

									container.close();
								}
							}
						}
					},
					{},
					{
						id: "button-next-" + position + "-" + self.uid,
						view: "button", 
						disabled: true,
						css: !lastStep ? "dialog-accept" : "dialog-finish",
						width: 130,
						height: 35,
						label: !lastStep ? i18n.t("LBL_NEXT") : i18n.t("LBL_FINALIZE"),
						on: {
							onItemClick() {

								if (!lastStep) {
								
									let next = $$(self.uid).getChildViews()[step.position]; 
									
									if (next) {
										
										next.show();
										
										setCurrentStep(step.position + 1);
									}
								}
							}
						}
					},
					{ width: 18 }
				];
								
				let viewToolbarActions = {
					cols: [
						{
							rows: [
								{ height: 10},
								{
									view: "toolbar",
									height: 45,
									borderless: true,
									elements: viewToolbarActionsButtons
								},
								{ height: 10}
							]
						}
					]
				};
				
				if (!firstStep) {
					
					viewToolbarActionsButtons.splice(3, 0, { 
						id: "button-previous-" + position + "-" + self.uid,
						view: "button", 
						css: "dialog-previous",
						width: 130,
						height: 35,
						label: i18n.t("LBL_PREVIOUS"),
						on: {
							onItemClick() {
								
								$$(self.uid).back();
								
								setCurrentStep(step.position -1);
							}
						}
					});	
				}
				
				return viewToolbarActions;
			};
			
			if (Array.isArray(self.steps) && self.steps.length > 0) {
				
				self.steps.forEach(e => {
					
					let stepContent = [];
					
					stepContent.push(drawHeader(e.position));
					stepContent.push(drawProgressBar(e.position));
					stepContent.push(drawContent(e.position));
					stepContent.push({ rows: [{ height: 35 }] });
					stepContent.push(drawToolbarActions(e.position));
			
					multiView.cells.push({rows: stepContent }); 	
				});
			}
			
			return multiView;
		}
		
		let fromMap = function() {

			util.ui.notImplementedMessage();
		};
		
		let fromSynoptic = function() {

			util.ui.notImplementedMessage();
		};

		let fromIncidentsNoMap = function() {
			
			self.steps = webix.copy(self.config.steps.fromMap);
		
			return drawMain();
		}
		
		let fromIncidentsMap = function() {
			
			util.ui.notImplementedMessage();
		};

		let fromAlarms = function() {

			util.ui.notImplementedMessage();
		};
		
		let viewMain = undefined;
		
		$$("loading-" + this.uid).show();
		$$(this.uid).hide();

		if (this.moduleAction.id === this.config.moduleAction.fromMap) {

			viewMain = fromMap();
		}
		else if (this.moduleAction.id === this.config.moduleAction.fromSynoptic) {

			viewMain = fromSynoptic();
		}
		else if (this.moduleAction.id === this.config.moduleAction.fromIncidentsNoMap) {

			viewMain = fromIncidentsNoMap();
		}
		else if (this.moduleAction.id === this.config.moduleAction.fromIncidentsMap) {

			viewMain = fromIncidentsMap();
		}
		else if (this.moduleAction.id === this.config.moduleAction.fromAlarms) {

			viewMain = fromAlarms();
		}
		
		setTimeout(function() {
			
			$$("loading-" + self.uid).hide();
			$$(self.uid).show();
			
			if (viewMain) {
		
			webix.ui(viewMain, $$(self.uid));
			
			if (self.dtLocations) {
				self.dtLocations.callbacks();
			}	
			
			if (self.dtIncidentTypes) {
				self.dtIncidentTypes.callbacks();
			}
		}
				
		}, 500);
	}
}	
