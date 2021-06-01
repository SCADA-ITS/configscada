import GenericModule from "../modules/genericModule.js";
import { util } from "../lib/util.js";
import CronUi from "../component/CronUi.js";
import SearchDialogBox from "../component/searchDialogBox.js";


export default class PlanScheduleModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.planSchedule = undefined;
		this.stompClient = undefined;
		this.requestReport = true;
		this.disconnectStomp = true;
		this.closed = false;
		this.form = {};
		this.newPlan = null;

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
		let newPlan = self.newPlan;

		let viewMain = {
			id: this.uid,
			cols: [
				{
					rows: [
						getViewHeader(),
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

			let header = {};
			let label = undefined;
			let description = undefined;

			if (self.moduleAction.id === self.config.moduleAction.add) {
				label = "LBL_NEW_PLANSCHEDULE";
				description = "LBL_NEW_PLANSCHEDULE_DESCRIPTION";

			} else if (self.moduleAction.id === self.config.moduleAction.edit) {
				label = "LBL_EDIT_PLANSCHEDULE";
				description = "LBL_EDIT_PLANSCHEDULE_DESCRIPTION";
			}

			header = {

				id: "viewHeader-" + self.uid,
				cols: [
					{
						view: "label",
						label: "<img src='img/grid/sheduler.png' class='grid-icon'/>",
						height: 75,
						css: "global-header-icon"
					},
					{
						view: "label",
						height: 75,
						id: "label-bottom-" + self.uid,
						template: "<span>" + i18n.t(label) + "</span> <br> <span style='font-size: 13px'>" + i18n.t(description) + "</span>",
						css: "userrole-header-label"
					}
				]
			};

			return header;
		};

		//		function getViewHeader() {
		//
		//			if (self.moduleAction.id === self.config.moduleAction.edit) {
		//			
		//				let viewHeader = {
		//
		//					id: "viewHeader-" + self.uid,
		//					borderless: true,
		//					rows: [
		//						{
		//							id: "view-bar-color-level-" + self.uid,
		//							height: 15,
		//						},
		//						{
		//							id: "view-header-info-" + self.uid,
		//							height: 80,
		//							template: function() {
		//
		//								let incidentIcon = self.planSchedule.icon;
		//
		//								if (incidentIcon == undefined) {
		//									incidentIcon = "not_found_50.png";
		//								}
		//								return `<image class="ims-incident-report-icon" src="img/${incidentIcon}" />
		//										<div class="ims-incident-report-title">
		//											${self.planSchedule.alias} ${self.planSchedule.description}
		//										</div>`;
		//							}
		//						}
		//					]
		//				};
		//
		//				return viewHeader;
		//			}
		//
		//			if (self.moduleAction.id === self.config.moduleAction.add) {
		//
		//				let viewHeader = {
		//
		//					id: "viewHeader-" + self.uid,
		//					borderless: true,
		//					rows: [
		//						{
		//							id: "view-bar-color-level-" + self.uid,
		//							height: 15,
		//							//css: { "background": self.incidentReport.incidentLevel.color }
		//						},
		//						{
		//							id: "view-header-info-" + self.uid,
		//							height: 80,
		//							template: function() {
		//	
		//								return `<image class="ims-incident-report-icon" src="img/not_found_50.png" />
		//										<div class="ims-incident-report-title">
		//											${$.i18n("LBL_NEW_PLANSCHEDULE")}
		//										</div>`;
		//							}
		//						}
		//					]
		//				};
		//	
		//				return viewHeader;
		//
		//			}
		//
		//		}


		function getViewContent() {

			let viewContent = {

				id: "viewContent-" + self.uid,
				css: "plan-form",
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

			let cronExpressionDesc = undefined;
			
				if (self.planSchedule && self.planSchedule != undefined ) {

					let translate = prettyCron.toString(self.planSchedule.cronExpression, true);

					if (translate != undefined) {

						cronExpressionDesc = translate;
					}
				}


				let form = {
					view: "form",
					id: "form-edit-core-" + self.uid,
					elementsConfig: { labelAlign: "left" },
					css: { "padding-left": "0px !important" },
					elements: [
						{
							view: "text",
							label: i18n.t("LBL_ALIAS"),
							name: "alias",
							value: type === "edit" ? self.planSchedule.alias : "",
							required: true,
							labelWidth: 200
						},
						{
							view: "text",
							label: i18n.t("LBL_DESCRIPTION"),
							name: "description",
							value: type === "edit" ? self.planSchedule.description : "",
							required: true,
							labelWidth: 200
						},
						{
							cols: [
								{
									view: "text",
									label: i18n.t("LBL_SCHEDULER"),
									name: "cronExpressionDescription",
									id: "cronExpressionDescription",
									value: type === "edit" ? cronExpressionDesc : "",
									required: true,
									labelWidth: 200,
									disabled: true
								}
								,
								{
									view: "text",
									label: i18n.t("LBL_PLAN_CRON"),
									name: "cronExpression",
									id: "cronExpression",
									value: type === "edit" ? self.planSchedule.cronExpression : "",
									required: true,
									labelWidth: 200,
									hidden: true
								},
								{
									view: "icon", icon: "wxi-search",
									on: {
										onItemClick: () => {

											let popupid = "cronUiPopup" + self.uid;
											let box = $$("form-edit-core-" + self.uid);
											let cron = new CronUi(box.config.id, popupid);
											
											let cronExpression = (self.planSchedule != undefined) ? self.planSchedule.cronExpression : null;

											cron.getComponent(cronExpression);

											//											webix.ui({
											//												id: popupid,
											//												view: "popup",
											//												position: "center",
											//												close: true,
											//												width: 800,
											//												body: cron.getComponent()
											////												on: {
											////													onHide: (obj) => {
											////														$$(popupid).getChildViews()[0].destructor();
											////														$$(popupid).destructor();
											////														$$(id).enable();
											////													},
											////													onShow: (obj) => {
											////														cron.loadData();
											////														$$(id).disable();
											////													}
											////												}
											//											}).show();

										}
									}
								}
							]

						},
						{
							cols: [
								{
									view: "text",
									label: i18n.t("LBL_PLAN_ID"),
									name: "planId",
									value: type === "edit" ? self.planSchedule.plan.id : "",
									hidden: true,
									disabled: true,
									required: true
								},
								{
									view: "text",
									label: i18n.t("LBL_PLAN"),
									name: "plan",
									value: type === "edit" ? self.planSchedule.plan.alias : "",
									required: true,
									labelWidth: 200,
									disabled: true
									//options: self.planList
								},
								{
									view: "icon", icon: "wxi-search",
									on: {
										onItemClick: function() {

											newPlan = $$(form.id).getValues();
											let dialogBox = new SearchDialogBox("LBL_SELECT_PLAN", SearchDialogBox.PLANS, selectPlan);
											dialogBox.show();
										}
									}
								}
							]

						}
					],
					rules: {
						"alias": webix.rules.isNotEmpty,
						"description": webix.rules.isNotEmpty,
						"cronExpression": webix.rules.isNotEmpty,
						"planId": webix.rules.isNotEmpty,
						"plan": webix.rules.isNotEmpty
					}
				}

				self.form = form;
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
								{ width: 60 },
								{
									borderless: true,
									rows: [
										getPlanScheduleForm("edit")
									]
								},
								{ width: 60 }
							]
						},
						{ height: 40 }
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
								{ width: 60 },
								{
									borderless: true,
									rows: [
										getPlanScheduleForm("add")
									]
								},
								{ width: 60 }
							]
						},
						{ height: 40 }
					]
				};

				return formAdd;
			}

			return viewContent;
		}


		function selectPlan(selection) {

			newPlan.plan = selection[0].alias;
			newPlan.planId = selection[0].id;

			self.newPlan = newPlan;
			self.setValues();
		}


		function getToolbarActions() {

			let viewToolbarActions = {
				//view: "toolbar",
				height: 45,
				borderless: true,
				rows: [
					{
						cols: [
							{ width: 20 },
							{
								view: "button",
								css: "dialog-cancel",
								width: 130,
								height: 41,
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
								width: 130,
								height: 41,
								label: i18n.t("LBL_ACCEPT"),
								on: {
									onItemClick() {

										let data = $$("form-edit-core-" + self.uid).getValues();
										let formValidate = $$("form-edit-core-" + self.uid).validate({ disabled: true });
										const CONTENT_TYPE_JSON = { "Content-type": "application/json" };

										if (formValidate) {

											if (self.moduleAction.id === self.config.moduleAction.add) {

												webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.actionEndpoints.save, {
													planSchedules: [
														{
															alias: data.alias,
															description: data.description,
															action: "START",
															planId: data.planId,
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

											if (self.moduleAction.id === self.config.moduleAction.edit) {

												webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.actionEndpoints.save, {
													planSchedules: [
														{
															id: self.planSchedule.id,
															alias: data.alias,
															description: data.description,
															action: "RESET",
															planId: data.planId,
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

										} else {

											webix.alert({
												title: i18n.t("LBL_FIELDS_NOT_COMPLETE"),
												text: i18n.t("LBL_REQUIRED_FIELDS_NOT_COMPLETED_DESCRIPTION"),
												type: "alert-error",
												width: 600
											});
										}
									}
								}
							},
							{ width: 20 }
						]
					},
					{}
				]
			};

			return viewToolbarActions;
		}

		webix.ui(viewMain, $$(this.uid));
	}


	setValues() {

		let self = this;
		let form = self.form;
		let newPlan = self.newPlan;

		$$(form.id).setValues(newPlan);
	}


	restAction(endpoint) {

		webix.ajax().post(appConfig.restUrl + endpoint).then(function(dat) {

			if (dat != null) {

				let resAction = dat.json();
			}
		}, function(response) { util.ajax.handleError(response); });
	}

}
