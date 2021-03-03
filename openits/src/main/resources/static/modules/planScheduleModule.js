import GenericModule from "../modules/genericModule.js";
import { util } from "../lib/util.js";
import CronUi from "../component/CronUi.js";

export default class PlanScheduleModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.planSchedule = undefined;
		this.stompClient = undefined;
		this.requestReport = true;
		this.disconnectStomp = true;
		this.closed = false;

		//this.config = JSON.parse(module.moduleValues.find(e => e.moduleParam.id === PARAM_MODULE_TYPE_CUSTOM_WINDOW_CONFIG).value);
		this.config = module.config;
		
		let modules = window.em.getAll(EntityType.PLAN);
		this.planList = [];

		modules.forEach(e=>{
			this.planList.push({ id: e.id, value: e.alias });
		});
		if (this.moduleAction.id === this.config.moduleAction.edit || this.moduleAction.id === this.config.moduleAction.play || this.moduleAction.id === this.config.moduleAction.stop || this.moduleAction.id === this.config.moduleAction.pause ){//|| this.moduleAction.id === this.config.moduleAction.add ) {
			
			if (this.args && this.args.selectedPks && this.args.selectedPks.length > 0) {
				this.planScheduleId = this.args.selectedPks[0];
			}
		}
	}

	getView() {

		let view = undefined;
		if (this.moduleAction.id === this.config.moduleAction.edit || this.moduleAction.id === this.config.moduleAction.add) {
			
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

		if (this.moduleAction.id === this.config.moduleAction.edit || this.moduleAction.id === this.config.moduleAction.add) {

			this.refresh();
		}
		else if (this.moduleAction.id === this.config.moduleAction.play) {

			this.actionPlay();
		}
		else if (this.moduleAction.id === this.config.moduleAction.pause) {

			this.actionPause();
		}
		else if (this.moduleAction.id === this.config.moduleAction.stop) {

			this.actionStop();
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

	actionPlay() {

		let self = this;
		
		if (Array.isArray(this.args.selectedPks) && this.args.selectedPks.length > 0) {
			let planScheduleList = this.args.selectedPks;
			
			webix.confirm({
				title: i18n.t("LBL_PLANSCHEDULE_CONFIRM_PLAY"),
				ok: i18n.t("LBL_YES"),
				cancel: i18n.t("LBL_NO"),
				text: i18n.t("LBL_PLANSCHEDULE_CONFIRM_PLAY_DESCRIPTION"),
				width: 600
			}).then(function() {
				
				self.actionExecution("PLAY", planScheduleList, self.config.actionEndpoints.play);

			});
		}
	}

	actionPause() {

		let self = this;
		
		if (Array.isArray(this.args.selectedPks) && this.args.selectedPks.length > 0) {
			let planScheduleList = this.args.selectedPks;
			
			webix.confirm({
				title: i18n.t("LBL_PLANSCHEDULE_CONFIRM_PAUSE"),
				ok: i18n.t("LBL_YES"),
				cancel: i18n.t("LBL_NO"),
				text: i18n.t("LBL_PLANSCHEDULE_CONFIRM_PAUSE_DESCRIPTION"),
				width: 600
			}).then(function() {
			
				self.actionExecution("PAUSE", planScheduleList, self.config.actionEndpoints.pause);

			});
		}
	}

	actionStop() {

		let self = this;
		
		if (Array.isArray(this.args.selectedPks) && this.args.selectedPks.length > 0) {
			let planScheduleList = this.args.selectedPks;
			
			webix.confirm({
				title: i18n.t("LBL_PLANSCHEDULE_CONFIRM_DELETE"),
				ok: i18n.t("LBL_YES"),
				cancel: i18n.t("LBL_NO"),
				text: i18n.t("LBL_PLANSCHEDULE_CONFIRM_DELETE_DESCRIPTION"),
				width: 600
			}).then(function() {
			
				self.actionExecution("STOP", planScheduleList, self.config.actionEndpoints.stop);

			});
		}
	}

	actionExecution(action,elementList,actionEndpoints) {

		let self = this;
		let endpoint = actionEndpoints;

		elementList.forEach(element => {

			webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoint, { id: element })).then(function(dat) {
				
				if (dat != null) {
					let planSchedule = dat.json();

					const CONTENT_TYPE_JSON = {"Content-type": "application/json"};
					webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + endpoint, { 
						planSchedules: [{ 
							id: element, 
							planId: planSchedule.plan.id, 
							cronExpression: planSchedule.cronExpression, 
							action: action 
						}]
					}).then(function(data2) {
						//console.log(data2);
					});

				}

			});

		});

	}

	refresh() {

		let self = this;

		$$("loading-" + this.uid).show();
		$$(this.uid).hide();

		if (this.requestReport) {

			if (this.moduleAction.id === this.config.moduleAction.edit) {

				this.requestReport = false;
				webix.ajax().get(appConfig.restUrl + util.fillTemplate(this.config.restEndpoint, { id: self.planScheduleId })).then(function(dat) {

					if (dat != null) {
						self.planSchedule = dat.json();
						self.update();
					}

				}, function(response) { util.ajax.handleError(response); });
				
			}

			if (this.moduleAction.id === this.config.moduleAction.add) {

				this.update();	
			}

		}
		else {

			this.update();
		}
	}

	update() {

		if (this.planSchedule) {

			this.drawComponents();

			if (this.moduleAction.id === this.config.moduleAction.edit && !this.stompClient) {

				this.callbacks();
			}

			$$("loading-" + this.uid).hide();
			$$(this.uid).show();
		}
		if (this.moduleAction.id === this.config.moduleAction.add) {

			this.drawComponents();
			$$("loading-" + this.uid).hide();
			$$(this.uid).show();
		}
	}

	callbacks() {

	}

	drawComponents() {

		let self = this;

		let viewMain = {
			id: this.uid,
			cols: [
				{
					rows: [
						//getViewHeader(),
						{ height: 20 },
						getViewContent(),
						{ height: 20 },
						getToolbarActions(),
						{ height: 10 },
					]
				},
			]
		};

		function getViewHeader() {

			if (self.moduleAction.id === self.config.moduleAction.edit) {
			
				let viewHeader = {

					id: "viewHeader-" + self.uid,
					borderless: true,
					rows: [
						{
							id: "view-bar-color-level-" + self.uid,
							height: 15,
						},
						{
							id: "view-header-info-" + self.uid,
							height: 80,
							template: function() {

								let incidentIcon = self.planSchedule.icon;

								if (incidentIcon == undefined) {
									incidentIcon = "not_found_50.png";
								}
								return `<image class="ims-incident-report-icon" src="img/${incidentIcon}" />
										<div class="ims-incident-report-title">
											${self.planSchedule.alias} ${self.planSchedule.description}
										</div>`;
							}
						}
					]
				};

				return viewHeader;
			}

			if (self.moduleAction.id === self.config.moduleAction.add) {

				let viewHeader = {

					id: "viewHeader-" + self.uid,
					borderless: true,
					rows: [
						{
							id: "view-bar-color-level-" + self.uid,
							height: 15,
							//css: { "background": self.incidentReport.incidentLevel.color }
						},
						{
							id: "view-header-info-" + self.uid,
							height: 80,
							template: function() {
	
								return `<image class="ims-incident-report-icon" src="img/not_found_50.png" />
										<div class="ims-incident-report-title">
											${$.i18n("LBL_NEW_PLANSCHEDULE")}
										</div>`;
							}
						}
					]
				};
	
				return viewHeader;

			}

		}


		function getViewContent() {

			let viewContent = {

				id: "viewContent-" + self.uid,
				rows: [
					{
						cols: [
							{ width: 15 },
							self.moduleAction.id === self.config.moduleAction.edit ? getFormEdit() : getFormAdd(),
							{ width: 15 }
						]
					},
					{ height: 2 }
				]

			};

			function getPlanScheduleForm(type) {
				
				let form = {
					view: "form",
					id: "form-edit-core-" + self.uid,
					elements: [
						{
							view: "text",
							label: i18n.t("LBL_ALIAS"),
							name: "alias",
							value: type === "edit" ? self.planSchedule.alias : "",
							required: true,
							labelWidth: 200, 
						},
						{
							view: "text",
							label: i18n.t("LBL_DESCRIPTION"),
							name: "description",
							value: type === "edit" ? self.planSchedule.description : "",
							required: true,
							labelWidth: 200, 
						},		
						{
							view: "text",
							label: i18n.t("LBL_PLAN_CRON") ,
							name: "cronExpression",
							value: type === "edit" ? self.planSchedule.cronExpression : "",
							required: true,
							labelWidth: 200, 
							on: {
								"onFocus": (id)=>{
									let popupid = "cronUiPopup" + self.uid;
									let cron = new CronUi(id,popupid);
									webix.ui({
										id:popupid,
										view:"popup",	
										position:"center",
										close: true,
										width: 800,
										body: cron.getComponent(),
										on: {
											onHide: (obj)=>{
												$$(popupid).getChildViews()[0].destructor();
												$$(popupid).destructor();
												$$(id).enable();
											},
											onShow: (obj)=>{
												cron.loadData();
												$$(id).disable();
											}
										}
									}).show();
								}
							} 
						},			
						{
							view: "combo",
							label: i18n.t("LBL_PLAN"),
							name: "plan",
							value: type === "edit" ? self.planSchedule.plan.id : "",
							required: true,
							labelWidth: 200, 
							options: self.planList
						}													
					]
				}
				return form;
			}

			function getFormEdit() {

				let formEdit = {
					id: "form-edit-" + self.uid,
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
										getPlanScheduleForm("edit")
									]
								},
								{ width: 40 }
							]
						},
						{}
					]
				};

				return formEdit;
			}

			function getFormAdd() {

				let formAdd = {
					id: "form-add-" + self.uid,
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
										getPlanScheduleForm("add")
									]
								},
								{ width: 40 }
							]
						},
						{}
					]
				};

				return formAdd;
			}

			return viewContent;
		}
		
		function getToolbarActions() {

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
						label: i18n.t("LBL_ACCEPT"),
						on: {
							onItemClick() {
								
								let data = $$("form-edit-core-" + self.uid).getValues();
											
								const CONTENT_TYPE_JSON = {"Content-type": "application/json"};
								if(self.moduleAction.id === self.config.moduleAction.add) {
									webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.actionEndpoints.save, {
										planSchedules: [
											{ 
												alias: data.alias,
												description: data.description,
												action: "START",
												planId: data.plan,
												cronExpression: data.cronExpression
											}
										]
									}).then(function(data2) {
										let container = $$(self.args.containerId);

										if (container) {

											container.close();
										}
									});
								}
								if(self.moduleAction.id === self.config.moduleAction.edit) {
									webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.actionEndpoints.save, {
										planSchedules: [
											{  
												id: self.planSchedule.id,
												alias: data.alias,
												description: data.description,
												action: "RESET",
												planId: data.plan,
												cronExpression: data.cronExpression
											}
										]
									}).then(function(data2) {
										let container = $$(self.args.containerId);

										if (container) {

											container.close();
										}
									});
								}

							}
						}
					}
				]
			};

			return viewToolbarActions;
		}

		webix.ui(viewMain, $$(this.uid));
	}

	restAction(endpoint) {

		webix.ajax().post(appConfig.restUrl + endpoint).then(function(dat) {

			if (dat != null) {

				let resAction = dat.json();
			}
		}, function(response) { util.ajax.handleError(response); });
	}

}
