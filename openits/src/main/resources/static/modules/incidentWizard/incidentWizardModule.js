import GenericModule from "../../modules/genericModule.js";
import IncidentWizardDrawContent from "./incidentWizardDrawContent.js";
import IncidentWizardAction from "./incidentWizardAction.js";
import { util } from "../../lib/util.js";

export default class IncidentWizardModule extends GenericModule {
	
	static get FIELD_STRETCH_ID() { return "id" };
	static get FIELD_STRETCH_ALIAS() { return "alias" };
	
	static get FIELD_LOCATION_ID() { return "id" };
	static get FIELD_LOCATION_ALIAS() { return "alias" };
	
	static get FIELD_INCIDENT_GROUP_ID() { return "id" };
	static get FIELD_INCIDENT_GROUP_ALIAS() { return "alias" };
	
	static get FIELD_INCIDENT_TYPE_ID() { return "id" };
	static get FIELD_INCIDENT_TYPE_ALIAS() { return "alias" };
	static get FIELD_INCIDENT_TYPE_DESCRIPTION() { return "description" };
	
	static get FIELD_INCIDENT_LEVEL_ID() { return "levelId" };
	static get FIELD_INCIDENT_LEVEL_LABEL_ALIAS() { return "levelLabelAlias" };
	static get FIELD_INCIDENT_LEVEL_ICON() { return "levelIcon" };
	static get FIELD_INCIDENT_LEVEL_COLOR() { return "levelColor" };
	
	static get FIELD_INCIDENT_USER_ID() { return "userId" };
	static get FIELD_INCIDENT_USER() { return "user" };

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();

		this.config = module.config;
		
		this.steps = undefined;
		
		this.listViewStrechs = undefined;
		this.dtLocations = undefined;
		this.listViewIncidentGroups = undefined;
		this.dtIncidentTypes = undefined;

		this.drawContent = new IncidentWizardDrawContent(this);		
		this.incidentWizardAction = new IncidentWizardAction(this);
		
		this.incidentData = {
			stretchId: undefined,
			stretch: undefined,
			locationId: undefined,
			location: undefined,
			incidentGroupId: undefined,
			incidentGroup: undefined,
			incidentTypeId: undefined,
			incidentType: undefined,
			incidentTypeDesc: undefined,
			incidentLevelId: undefined,
			incidentLevelLabelAlias: undefined,
			incidentLevelIcon: undefined,
			incidentLevelColor: undefined,
			userId: undefined,
			user: undefined
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
			
		
		
		let drawMain = function() {
			
			let multiView = {
				
				view: "multiview",
				id: self.uid,
				animate: false,
				cells: []
			};
			
			let drawHeader = function(position) {
				
				let step = self.incidentWizardAction.getStep(position);
				
				let maxWidthLabels = 500;
			
				let container = $$(self.args.containerId);
	
				if (container) {
	
					maxWidthLabels = container.config.width - 200;
				}
				
				let viewHeader = {
					css: { "background": "#567eba" },
					rows: [
						{
							height: 52,
							cols: [
								{
									view: "template",
									width: maxWidthLabels,
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
									width: 140,
									borderless: true,
									css: { "background": "transparent" },
									template: function() {
										
										return `<div class="text">
											  		<span class="header-wizard-step">${(step.position < self.steps.length) ? (i18n.t("LBL_STEP") + " " + step.position) : i18n.t("LBL_LAST_STEP")}</span>
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
						{ height: 5 },
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
		
		
			let drawToolbarActions = function(position) {

				let firstStep = false;
				let lastStep = false;
				let step = self.incidentWizardAction.getStep(position);
				
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
						disabled: !lastStep,
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
										
										self.incidentWizardAction.setCurrentStep(step.position + 1);
									}
								}
								else {
									
									self.incidentWizardAction.createIncident();
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
								
								self.incidentWizardAction.setCurrentStep(step.position -1);
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
					stepContent.push(self.drawContent.draw(e.position));
					stepContent.push({ rows: [{ height: 20 }] });
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
				
				if (self.listViewStrechs) {
					
					self.listViewStrechs.selectByIndex(0);	
				}
				
			}
				
		}, 500);
	}
}	
