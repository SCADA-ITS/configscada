import GenericModule from "../modules/genericModule.js";
import { util } from "../lib/util.js";

export default class IncidentWizardModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();

		this.config = module.config;
		
		this.steps = undefined;
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
									hidden: false
								},
								{
									id: "loading-" + this.uid,
									rows: [getViewDrawNoData()],
									hidden: true
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
									borderless: true,
									css: { "background": "transparent" },
									template: function() {
										return `<div class="text">
											  		<span class="list-first-line">${i18n.t(step.headerLabel)} </span>
											  		<span class="list-second-line">${i18n.t(step.headerLabelDescription)} </span>
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
											  		<span class="list-first-line">${i18n.t("LBL_STEP") + " " + step.position} </span>
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
				
				let drawStepStretchAndLocation = function() {
			
					let viewStepStretchAndLocation = {
						
						rows: [
							{
								cols: [
									{ width: 48 },
									{
										rows: [
											{
												view: "label",
												label: `<span class="ico webix_icon mdi mdi-format-list-bulleted"></span><span class="grid-title">${i18n.t(step.stepLabel)}</span>`
											}			
										]
									},
									{ width: 48 }
								]
							},
							{}
						]
					};
					
					return viewStepStretchAndLocation;
				};
				
				let drawStepIncidentType = function() {
					
					let viewStepIncidentType = {
						
						rows: [
							{
								cols: [
									{ width: 48 },
									{
										rows: [
											{
												view: "label",
												label: `<span class="ico webix_icon mdi mdi-format-list-bulleted"></span><span class="grid-title">${i18n.t(step.stepLabel)}</span>`
											}			
										]
									},
									{ width: 48 }
								]
							},
							{}
						]
					};
					
					return viewStepIncidentType;
				};
				
				let drawStepSummary = function() {
					
					let viewStepSummary = {
						
						rows: [
							{
								cols: [
									{ width: 48 },
									{
										rows: [
											{
												view: "label",
												label: `<span class="ico webix_icon mdi mdi-format-list-bulleted"></span><span class="grid-title">${i18n.t(step.stepLabel)}</span>`
											}			
										]
									},
									{ width: 48 }
								]
							},
							{}
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
								
				let viewToolbarActions = {
					view: "toolbar",
					height: 50,
					borderless: true,
					elements: [
						{ 
							view: "button",
							css: "dialog-cancel", 
							width: 150, 
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
							view: "button", 
							css: "dialog-accept",
							width: 150, 
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
						}
					]
				};
				
				if (!firstStep) {
					
					viewToolbarActions.elements.splice(2, 0, { 
						view: "button", 
						css: "dialog-accept",
						width: 150, 
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
		
		if (viewMain) {
		
			webix.ui(viewMain, $$(this.uid));	
		}
		
		$$("loading-" + this.uid).hide();
		$$(this.uid).show();
	}
}	
