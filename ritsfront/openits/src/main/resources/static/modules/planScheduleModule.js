import { util } from "../lib/util.js";
import GenericModule from "../modules/genericModule.js";
import CronUi from "../component/cronUi.js";
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
						css: { "background-color": "white" },
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
					webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.planSchedule, { id: planScheduleId })).then(function(dat) {

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

				let ms = new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_PLANSCHEDULE_CONFIRM_ENABLE_DESCRIPTION"), null, onAccept, null);
				ms.show();
				
				function onAccept() {
					
					actionExecution("PLAY", planScheduleList, self.config.actionEndpoints.enable);
				}
			}
		}

		function actionDisable() {

			if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {

				let planScheduleList = self.args.selectedPks;

				let ms = new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_PLANSCHEDULE_CONFIRM_DISABLE_DESCRIPTION"), null, onAccept, null);
				ms.show();
				
				function onAccept() {
					
					actionExecution("PAUSE", planScheduleList, self.config.actionEndpoints.disable);
				}
			}
		}

		function actionDelete() {

			if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {

				let planScheduleList = self.args.selectedPks;

				let ms = new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_PLANSCHEDULE_CONFIRM_DELETE_DESCRIPTION"), null, onAccept, null);
				ms.show();
				
				function onAccept() {
					
					actionExecution("STOP", planScheduleList, self.config.actionEndpoints.delete);
				}
			}
		}

		function actionExecution(action, planScheduleList, actionEndpoints) {

			let endpoint = actionEndpoints;

			planScheduleList.forEach(planScheduleId => {

				webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.planSchedule, { id: planScheduleId })).then(function(dat) {

					if (dat) {
						
						let planSchedule = dat.json();
						
						if (planSchedule) {
							
							let body = {
								planSchedules: [{
									id: planSchedule.id,
									planId: planSchedule.plan.id,
									cronExpression: planSchedule.cronExpression,
									action: action
								}]
							};
							
							webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + endpoint, body);
						}
					}
				});
			});
		}


		function drawComponents() {

			let newPlan = self.newPlan;
			let viewMain = {};
			let label = undefined;
			let description = undefined;
			let type = undefined;

			if (self.moduleAction.id === self.config.moduleAction.add) {

				label = "LBL_NEW_PLANSCHEDULE";
				description = "LBL_NEW_PLANSCHEDULE_DESCRIPTION";
				type = "add";

			} else if (self.moduleAction.id === self.config.moduleAction.edit) {

				label = "LBL_EDIT_PLANSCHEDULE";
				description = "LBL_EDIT_PLANSCHEDULE_DESCRIPTION";
				type = "edit";
			}

			viewMain = {
				id: self.uid,
				css: { "box-sizing": "border-box" },
				rows: [
					getViewHeader(),
					getViewContent(),
					getToolbarActions(),
				]
			};

			function getViewHeader() {

				let header = {};

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

				let form = {};
				let cronExpressionDesc = undefined;

				if (self.planSchedule && self.planSchedule != undefined) {

					let translate = prettyCron.toString(self.planSchedule.cronExpression, true);

					if (translate != undefined) {

						cronExpressionDesc = translate;
					}
				}

				form = {
					view: "form",
					id: "form-edit-core-" + self.uid,
					elementsConfig: { labelAlign: "left" },
					css: "plan-form",
					elements: [
						{
							cols: [
								{ width: 45 },
								{
									rows: [
										{},
										{
											view: "text",
											label: i18n.t("LBL_ALIAS"),
											name: "alias",
											value: type === "edit" ? self.planSchedule.alias : "",
											required: true,
											labelWidth: 275
										},
										{
											view: "text",
											label: i18n.t("LBL_DESCRIPTION"),
											name: "description",
											value: type === "edit" ? self.planSchedule.description : "",
											required: true,
											labelWidth: 275
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
													labelWidth: 275,
													disabled: true
												},
												{
													view: "text",
													label: i18n.t("LBL_PLAN_CRON"),
													name: "cronExpression",
													id: "cronExpression",
													value: type === "edit" ? self.planSchedule.cronExpression : "",
													required: true,
													labelWidth: 275,
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
													labelWidth: 275,
													disabled: true
												},
												{
													view: "icon", icon: "wxi-search",
													on: {
														onItemClick: function() {

															newPlan = $$(form.id).getValues();
															
															searchPlan();
														}
													}
												}
											]
										},
										{}
									]
								},
								{ width: 45 }
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
				};

				self.form = form;
				return form;

			}

			function searchPlan() {
				
				let dialogPlanEntity = webix.copy(SearchDialogBox.CALLBACK);				
				dialogPlanEntity.fillDialogEntity = fillDialogEntity;
				dialogPlanEntity.dataType = SearchDialogBox.DIALOG_DATA_TYPE_TREE;
				dialogPlanEntity.height = 600;
				dialogPlanEntity.width = 800;
				dialogPlanEntity.data = {};
				
				function fillDialogEntity(dialogEntity, onFilledDialogEntity) {

					let promises = [];

					promises.push(webix.ajax().get(appConfig.restUrl + self.config.restEndpoints.planNodes));
					promises.push(webix.ajax().get(appConfig.restUrl + self.config.restEndpoints.planHierarchies));
					promises.push(webix.ajax().get(appConfig.restUrl + self.config.restEndpoints.plans));

					webix.promise.all(promises).then(function(responses) {

						if (Array.isArray(responses) && responses.length === 3) {
							
							let itemsTree = [];
							let itemRelationships = [];

							let nodes = (responses[0] && responses[0].json()) ? responses[0].json() : [];
							let relationships = responses[1] && responses[1].json() ? responses[1].json() : [];
							let plans = responses[2] && responses[2].json() ? responses[2].json() : [];

							if (Array.isArray(nodes) && nodes.length > 0) {

								itemsTree = nodes.map(e => {

									let plan = plans.find(p => (e.plan && p.id === e.plan.id));

									return {

										id: e.id,
										label: plan ? plan.alias : e.alias,
										aux: {
											plan: plan
										},
										folder: !e.plan
									}
								});
							}

							if (Array.isArray(relationships) && relationships.length > 0) {

								itemRelationships = relationships.map(e => {

									return {

										id: e.id,
										nodeParentId: e.parent.id,
										nodeChildId: e.child.id
									}
								});
							}

							dialogEntity.data.nodes = itemsTree;
							dialogEntity.data.relationships = itemRelationships;
							dialogEntity.data.nodeParentId = "nodeParentId";
							dialogEntity.data.nodeChildId = "nodeChildId";
							
							onFilledDialogEntity(dialogEntity);
						}
					});					
				}
				
				
				let s = new SearchDialogBox(i18n.t("LBL_SELECT_PLAN"), dialogPlanEntity, onAccept);
				s.show();
				
				function onAccept(selection) {

					if (Array.isArray(selection) && selection.length > 0 && selection[0].aux && selection[0].aux.plan) {
						
						let plan = selection[0].aux.plan;
						
						newPlan.plan = plan.alias;
						newPlan.planId = plan.id;
						self.newPlan = newPlan;
		
						$$(self.form.id).setValues(self.newPlan);	
					}				
				}
			}

			function getToolbarActions() {

				let viewToolbarActions = {
					borderless: true,
					rows: [
						{
							cols: [
								{ width: 15 },
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
												
												let ms = new MessageBox (MessageBox.ERROR, i18n.t("LBL_REQUIRED_FIELDS_NOT_COMPLETED_DESCRIPTION"), null, null, null);
												ms.show();

											}
										}
									}
								},
								{ width: 15 }
							]
						}
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
