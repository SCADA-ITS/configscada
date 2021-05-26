import GenericModule from "../modules/genericModule.js";
import CalendarDialogBox from "../component/calendarDialogBox.js";
import TreeView from "../component/treeview.js";
import DataTable from "../component/datatable.js";
import { util } from "../lib/util.js";

export default class AlarmMutedModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();

		this.requestAlarm = true;
		this.stompClient = undefined;
		this.tableAlarmsModal = undefined;
		this.requestReport = true;
		this.config = module.config;
		this.args = args;

		this.steps = undefined;
		this.disableAlarm = true;


		this.alarmMutedElementSelect = undefined;
		this.calendar = undefined;

    }

	getView() {

		return super.getViewCheckModuleAction();
	}
    
	loadData() {

		let self = this;
		
		if (this.moduleAction.id === this.config.moduleAction.mute) {

			mute(true);
		}
		else if (this.moduleAction.id === this.config.moduleAction.unmute) {

			mute(false);
		}
		else if (this.moduleAction.id === this.config.moduleAction.createMuteAlarm) {

			self.steps = webix.copy(self.config.steps.fromNoMap);
			
			let view = {
				css: "custom_window",
				cols: [
					{
						rows: [
							{ height: 6 },
							{
								id: "custom_window" + self.uid,
								type: "line",
								cols: [
									{ width: 6 },
									{
										id: "view" + "loading-" + this.uid,
										rows: [this.getViewDrawNoData()],
										height: 500,
										width: 834,
										hidden: true
									},
									{ width: 6 }
								]
							},
							{ height: 30 }
						]
					}
				]
			};

			let h = document.documentElement.clientHeight - 400;
			let w = document.documentElement.clientWidth - 1000;

			this.createView = util.ui.loadWindow(
				i18n.t("LBL_CREATE_ALARMS_FILTER"),
				view,
				h,
				w,
				true
			);
		
			$$("view" + "loading-" + this.uid).show();

			let drawMain = function(pos) {
			
				let multiView = {
					
					view: "multiview",
					id: "multi" + self.uid,
					animate: false,
					height: 0,
					cells: []
				};
				
				let drawContent = function(position) {

					let step = getStep(position);

					let drawStepRadeonOptions = function() {
						let viewContent = {
							rows: [
								{
									height: 30,
								},
								{
									borderless: false,
									cols: [
										{
											width: 160,
										},
										{
											view:"radio",
											//vertical:true,
											//align:"center", 
											value:1, options:[
												{"id":1, "value": i18n.t("LBL_ALARM_FILTER_RADIO_DISABLE")}, 
												{"id":2, "value": i18n.t("LBL_ALARM_FILTER_RADIO_ACTIVE")}
											],
											on: {
													onChange: function(newv) {
														newv === "1" ? self.disableAlarm = true : self.disableAlarm = false;
													}
												}
										},
										{
											width: 60,
										}
									]
								},
								{								
									height: 30,
								},
							]
						}

						return viewContent
					}

					let drawStepCreateTreeGrid = function() {

						let viewContent = {
							id: "treeGridStepView" + self.uid,
							cols:[]
						}

						webix.ajax().get(appConfig.restUrl + util.fillTemplate("trees/${id}", { id: self.config.treeElementType })).then(function(dat) {

							if (dat != null) {
			
								let metadata = dat.json().metadata;
								let data = dat.json().data;
								
								let events = {};
							
								events[TreeView.EventTypes.ON_SELECT_ITEM] = function(item) {
			
									self.tableAlarmsModal.clear();
			
									let element = window.em.getByField(EntityType.ELEMENT, undefined, item.id_);
									self.alarmMutedElementSelect = element;
			
									let elementTypeId = element.elementType.id;
									let alarmConfig = window.em.getAll(EntityType.ALARM_CONFIG);
									let aconfitArray = alarmConfig.find(e => e.elementType.id === elementTypeId);
			
									if (Array.isArray(aconfitArray) && aconfitArray.length) {
										let alarmsConfig = aconfitArray;
										for (let i = 0; i < alarmsConfig.length; i++) {
			
											let row = {};
			
											row.id = alarmsConfig[i].id;
											row.alarmConfigAlias = alarmsConfig[i].alias;
											row.alarmTypeLabelAlias = i18n.t(alarmsConfig[i].alarmType.labelAlias);
											row.alarmLevelLabelAlias = i18n.t(alarmsConfig[i].alarmLevel.labelAlias);
			
											self.tableAlarmsModal.add(row);
										}
									} else {
			
										let row = {};
			
										row.id = aconfitArray.id;
										row.alarmConfigAlias = aconfitArray.alias;
										row.alarmTypeLabelAlias = i18n.t(aconfitArray.alarmType.labelAlias);
										row.alarmLevelLabelAlias = i18n.t(aconfitArray.alarmLevel.labelAlias);
			
										self.tableAlarmsModal.add(row);
									}
									
								};
					
								let treeView = new TreeView(metadata, data, events);
					
								let viewMain = {
									id: "viewTree" + self.uid,
									cols: [
										{
											width: 250,
											rows: [
												treeView.getView()		
											]
										}
									]
								};
			
								webix.ajax().get(appConfig.restUrl + "grids/" + self.config.gridAlarmsConfig + "/" + "id").then((dat)=>{
			
									if (dat != null) {
			
										let metadata = dat.json().metadata;
			
										self.tableAlarmsModal = new DataTable(null, null, metadata, []);
			
										let viewMaintable = {
											id: "viewTable" + self.uid,
											cols: [
												{
													width: 600,
													rows: self.tableAlarmsModal.getView()
													
												}
											]
										};
										
										$$("treeGridStepView" + self.uid).addView(viewMain, 1);
										$$("treeGridStepView" + self.uid).addView(viewMaintable, 2);

									}
			
								});
			
							}
						});

						return viewContent
					}

					let drawStepCalendar = function() {

						self.calendar = new CalendarDialogBox(i18n.t("LBL_DIALOG_MUTE_ALARMS"), i18n.t("LBL_DIALOG_UNMUTE_ALARMS"), false, 
					
						function(date) {
							
							return (Math.floor(date) < Math.floor(new Date()));
						},
						null
					);
						return self.calendar.getView();
					}

					return eval(step.drawFunction)();

				}

				let drawHeader = function(position) {
					
					let step = getStep(position);
					
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

									$$(self.createView).close();

								}
							}
						},
						{},
						{
							id: "button-next-" + position + "-" + self.uid,
							view: "button", 
							css: !lastStep ? "dialog-accept" : "dialog-finish",
							width: 130,
							height: 35,
							label: !lastStep ? i18n.t("LBL_NEXT") : i18n.t("LBL_FINALIZE"),
							on: {
								onItemClick() {
	
									if (!lastStep) {
									
										//grid logic
										console.log(self.config.gridStepPosition);
										if(position === self.config.gridStepPosition){

											if(Array.isArray(self.tableAlarmsModal.getSelectedIds()) && self.tableAlarmsModal.getSelectedIds().length > 0)
											{

												let next = $$("multi" + self.uid).getChildViews()[step.position]; 
												
												if (next) {
													
													next.show();
													setCurrentStep(step.position + 1);
													
												}

											}else{

												webix.alert({
													title: i18n.t("LBL_COMMAND_NO_ROWS_SELECTED"),
													text: i18n.t("LBL_COMMAND_NO_ROWS_SELECTED_DESCRIPTION"),
													type: "alert-warning",
													width: 500
												}); 

											}

										}else{

											let next = $$("multi" + self.uid).getChildViews()[step.position]; 

											if (next) {

												next.show();
												setCurrentStep(step.position + 1);
												
											}

										}

									}
									else {

										let mutedAlarms = [];
									
										self.tableAlarmsModal.getSelectedIds().forEach(alarm => {
											
											mutedAlarms.push({
												
												alarmConfigId: alarm,
												mutedUntil: self.calendar.getSelectedDates().from.getTime(),
												elementId: self.alarmMutedElementSelect.id,
												enable: true,
												alarmDisabled: self.disableAlarm

											});
										});
										
										if (Array.isArray(mutedAlarms) && mutedAlarms.length > 0) {
											
											let body = {
											
												alarmMuteds: mutedAlarms							
											};
											
											webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.actionEndpoints.mute, body).then(function(data) {
												self.args.callbacks[0]();
												$$(self.createView).close();
											});									
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
									
									$$("multi" + self.uid).back();
									
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
						stepContent.push({ rows: [{ height: 20 }] });
						stepContent.push(drawToolbarActions(e.position));
				
						multiView.cells.push({rows: stepContent }); 

					});
				}
				
				return multiView;
			}

			$$("custom_window" + self.uid).addView(drawMain(), 1);
			
			$$("view" + "loading-" + self.uid).hide();

		};

		function getStep(position) {
		
			let step = undefined;
			
			step = self.steps.find(e => e.position === position);
			
			return step;
		};

		function setCurrentStep(position) {
		
			
			let currentStep = getCurrentStep();
			let newStep = undefined;
			
			if (position > 0 && position <= self.steps.length) {
				
				currentStep.active = false;
				
				newStep = getStep(position);
				newStep.active = true;	
				
				if (position === self.steps.length) {
					
					$$("button-next-" + position + "-" + self.uid).enable();	
				}
			}
			
			return !newStep ? currentStep : newStep;
		}

		function getCurrentStep() {
				
			let currentStep = undefined;
			
			currentStep = self.steps.find(e => e.active);
			
			return currentStep;
		};

		function mute(action) {

			let title;
			let text;
			
			if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {

				title = action ? i18n.t("LBL_DIALOG_MUTE_ALARMS") : i18n.t("LBL_DIALOG_UNMUTE_ALARMS");
				text = action ? i18n.t("LBL_ARE_YOU_SURE_MUTE") : i18n.t("LBL_ARE_YOU_SURE_UNMUTE");

				if(action === true){

					(new CalendarDialogBox(title, text, false, 
					
						function(date) {
							
							return (Math.floor(date) < Math.floor(new Date()));
						},
						function(dates) {
							
							webix.ajax().headers(HEADER_POST).post(appConfig.restUrl + self.config.restEndpoints.alarmsArray, JSON.stringify(self.args.selectedPks)).then(function(data) {
							
								let responseAlarms = data.json();
								
								if (Array.isArray(responseAlarms) && responseAlarms.length > 0) {
									
									let mutedAlarms = [];
									
									responseAlarms.forEach(alarm => {
										
										mutedAlarms.push({
											
											alarmConfigId: alarm.alarmConfig.id,
											mutedUntil: dates.from.getTime(),
											elementId: alarm.targetElement.id,
											enable: true
										});
									});
									
									if (Array.isArray(mutedAlarms) && mutedAlarms.length > 0) {
										
										let body = {
										
											alarmMuteds: mutedAlarms							
										};

										webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.actionEndpoints.mute, body).then((data) => {

											self.args.callbacks[0]();

										});									
									} 
								}
							});					
						}
					)).show();

				}

				if(action === false){
					
					webix.confirm({
						title: i18n.t("LBL_ALARM_CONFIRM_CONFIRM"),
						ok: i18n.t("LBL_YES"),
						cancel: i18n.t("LBL_NO"),
						text: i18n.t("LBL_ALARM_CONFIRM_CONFIRM_DESCRIPTION"),
						width: 600
					}).then(function() {

						if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {

							let mutedAlarms = [];
							
							self.args.selectedPks.forEach(e => {

								webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.alarmMuted, { id: e })).then(function(dat) {

									if (dat != null) {
										
										let responseAlarms = dat.json();
									
										mutedAlarms.push({
											
											alarmConfigId: responseAlarms.alarmConfig.id,
											mutedUntil: responseAlarms.mutedUntil,
											elementId: responseAlarms.element.id,
											enable: false

										});

										if (Array.isArray(mutedAlarms) && mutedAlarms.length > 0) {
															
											let body = {
											
												alarmMuteds: mutedAlarms							
											};
											
											webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.actionEndpoints.unmute, body).then((data) => {

												self.args.callbacks[0]();

											});									
										} 
	
									}
	
								});
	
							});
	
						}
						
					});

				}
				
			}
		}

	}

	getViewDrawNoData = () => {

		return {
			view: "template",
			template: function() {

				let icon = "<img src='img/loading.svg' align='center'>";

				return "<div class='empty-app-inner'>" + icon + "<span class='empty-app-inner-text'>" + i18n.t("LBL_LOADING_DATA") + "</span></div>";
			}
		}
	}
    
}
