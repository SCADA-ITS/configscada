import { util } from "../lib/util.js";
import GenericModule from "../modules/genericModule.js";
import CronUi from "../component/CronUi.js";
import SearchDialogBox from "../component/searchDialogBox.js";
import MessageBox from "../component/messageBox.js";

export default class PlanScheduleModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.planSchedule = undefined;
		this.requestReport = true;
		this.closed = false;
		this.form = {};
		this.newPlan = null;
		this.config = module.config;
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
										rows: [util.ui.getViewDrawLoading()],
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

		let self = this;

		if (self.moduleAction.id === self.config.moduleAction.edit || self.moduleAction.id === self.config.moduleAction.add) {

			$$("loading-" + self.uid).show();
			$$(self.uid).hide();

			if (self.requestReport) {

				if (self.moduleAction.id === self.config.moduleAction.edit) {

					let planScheduleId = self.args.selectedPks[0];

					self.requestReport = false;
					webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoint, { id: planScheduleId })).then(function(dat) {

						if (dat != null) {
							self.planSchedule = dat.json();
							update();
						}

					}, function(response) { util.ajax.handleError(response); });

				}

				if (self.moduleAction.id === self.config.moduleAction.add) {

					update();
				}

			} else {

				update();
			}

		} else if (self.moduleAction.id === self.config.moduleAction.enable) {

			actionEnable();

		} else if (self.moduleAction.id === self.config.moduleAction.disable) {

			actionDisable();

		} else if (self.moduleAction.id === self.config.moduleAction.delete) {

			actionDelete();
		}


		function update() {

			if (self.planSchedule) {

				drawComponents();

				$$("loading-" + self.uid).hide();
				$$(self.uid).show();
			}
			if (self.moduleAction.id === self.config.moduleAction.add) {

				drawComponents();
				$$("loading-" + self.uid).hide();
				$$(self.uid).show();
			}
		}


		function actionEnable() {

			if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {

				let planScheduleList = self.args.selectedPks;

				let ms = new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_PLANSCHEDULE_CONFIRM_ENABLE_DESCRIPTION"), onAccept, null);
				ms.show();
				
				function onAccept() {
					
					actionExecution("PLAY", planScheduleList, self.config.actionEndpoints.enable);
				};
			}
		}

		function actionDisable() {

			if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {

				let planScheduleList = self.args.selectedPks;

				let ms = new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_PLANSCHEDULE_CONFIRM_DISABLE_DESCRIPTION"), onAccept, null);
				ms.show();
				
				function onAccept() {
					
					actionExecution("PAUSE", planScheduleList, self.config.actionEndpoints.disable);
				}
			}
		}

		function actionDelete() {

			if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {

				let planScheduleList = self.args.selectedPks;

				let ms = new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_PLANSCHEDULE_CONFIRM_DELETE_DESCRIPTION"), onAccept, null);
				ms.show();
				
				function onAccept() {
					
					actionExecution("STOP", planScheduleList, self.config.actionEndpoints.delete);
				};
			}
		}

		function actionExecution(action, elementList, actionEndpoints) {

			let endpoint = actionEndpoints;

			elementList.forEach(element => {

				webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoint, { id: element })).then(function(dat) {

					if (dat != null) {
						let planSchedule = dat.json();

						const CONTENT_TYPE_JSON = { "Content-type": "application/json" };
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


		function drawComponents() {

			let newPlan = self.newPlan;

			let viewMain = {
				id: self.uid,
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
							css: "global-header-label"
						}
					]
				};

				return header;
			};

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

					if (self.planSchedule && self.planSchedule != undefined) {

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
									},
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

												let cronExpression = (self.planSchedule != undefined) ? self.planSchedule.cronExpression : ($$("cronExpression").getValue()) ? ($$("cronExpression").getValue()) : null;

												cron.getComponent(cronExpression);
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
									},
									{
										view: "icon", icon: "wxi-search",
										on: {
											onItemClick: function() {

												newPlan = $$(form.id).getValues();
												let dialogBox = new SearchDialogBox(i18n.t("LBL_SELECT_PLAN"), SearchDialogBox.PLANS, selectPlan);
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

				$$(self.form.id).setValues(self.newPlan);
			}

			function getToolbarActions() {

				let viewToolbarActions = {
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
												
												let ms = new MessageBox (MessageBox.ERROR, i18n.t("LBL_REQUIRED_FIELDS_NOT_COMPLETED_DESCRIPTION"), null, null);
												ms.show();

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

			webix.ui(viewMain, $$(self.uid));
		}

	}


	//	restAction(endpoint) {
	//
	//		webix.ajax().post(appConfig.restUrl + endpoint).then(function(dat) {
	//
	//			if (dat != null) {
	//
	//				let resAction = dat.json();
	//			}
	//		}, function(response) { util.ajax.handleError(response); });
	//	}

}
