import { util } from "../../lib/util.js";
import CalendarDialogBox from "../../component/calendarDialogBox.js";
import TreeView from "../../component/treeview.js";
import DataTable from "../../component/datatable.js";
import MessageBox from "../../component/messageBox.js";

export default class AlarmFilteredWizard {

	constructor(parent) {

		this.parent = parent;
		this.tableAlarmsModal = undefined;
		this.treeView = undefined;
		this.steps = undefined;
		this.disableAlarm = true;
		this.alarmMutedElementSelect = undefined;
		this.calendar = undefined;
		this.arrSummary = [];
		this.createView = undefined;
	}


	drawComponents() {

		let view = {
			id: self.uid,
			css: "custom_window",
			position: "center",
			cols: [
				{
					rows: [
						{ height: 6 },
						{
							cols: [
								{ width: 6 },
								{
									rows: [this.drawMain()],
								},
								{ width: 6 }
							]
						},
						//{height:30 }
					]
				}
			]
		};

		this.createView = util.ui.loadWindow(
			i18n.t("LBL_CREATE_ALARMS_FILTER"),
			view,
			700,
			1200,
			true
		);

	}


	drawMain() {

		let self = this;
		let parent = self.parent;

		let multiView = {

			view: "multiview",
			id: "multi" + parent.uid,
			animate: false,
			cells: []
		};


		let drawContent = function(position) {

			let step = self.getStep(position);

			let drawStepRadeonOptions = function() {

				let viewContent;
				
				viewContent = {
					cols: [
						{},
						{
							height: 385,
							rows: [
								{
									view: "label",
									borderless: true,
									width: 1000,
									height: 23,
									css: "headers",
									label: i18n.t("LBL_ALARM_FILTER_TYPE")
								},
								{
									borderless: true,
									css: {
										"border": "1px solid #dadee0 !important",
										"width": "998px !important",
										"height": "361px !important"
									},
									cols: [
										{},
										{
											//width: 300,
											rows: [
												{ height: 50 },
												{
													view: "radio",
													vertical: true,
													align: "center",
													height: 350,
													css: "alarm-radio-options",
													value: 1, options: [
														{ "id": 1, "value": i18n.t("LBL_ALARM_FILTER_RADIO_DISABLE") },
														{ "id": 2, "value": i18n.t("LBL_ALARM_FILTER_RADIO_MUTE") }
													],
													on: {
														onChange: function(newv) {
															self.disableAlarm = (newv === "1");
															self.tableAlarmsModal.clear();
															self.treeView.unselectAll();
															self.treeView.collapse();
														}
													}
												},
											]
										},
										{}
									]
								}
							]
						},
						{}
					]
				}

				return viewContent
			}


			let drawStepCreateTreeGrid = function() {

				let aconfigArray = [];
				
				let viewContent = {
					id: "treeGridStepView" + parent.uid,
					cols: []
				}

				webix.ajax().get(appConfig.restUrl + util.fillTemplate(parent.config.restEndpoints.trees, { id: parent.config.treeElementType })).then(function(dat) {

					if (dat != null) {

						let metadata = dat.json().metadata;
						let data = dat.json().data;
						let events = {};

						events[TreeView.EventTypes.ON_SELECT_ITEM] = function(item) {

							self.tableAlarmsModal.clear();

							let element = window.em.getByField(EntityType.ELEMENT, "id", item.id);
							self.alarmMutedElementSelect = element;

							let elementTypeId = element.elementType.id;
							let alarmConfigs = window.em.getAll(EntityType.ALARM_CONFIG);
							aconfigArray = alarmConfigs.filter(e => e.elementType.id === elementTypeId);

							if (Array.isArray(aconfigArray) && aconfigArray.length > 0) {

								aconfigArray.forEach(alarmConfig => {
									
									let row = {};

									row.id = alarmConfig.id;
									row.alarmConfigAlias = i18n.t(alarmConfig.labelAlias);
									row.alarmLevelLabelAlias = i18n.t(alarmConfig.alarmLevel.labelAlias);

									self.tableAlarmsModal.add(row);
								});
							} 
						};

						self.treeView = new TreeView(metadata, data, events);

						let viewMain = {
							id: "viewTree" + parent.uid,
							width: 406,
							cols: [
								{ width: 96 },
								{
									height: 385,
									rows: [
										{
											view: "label",
											borderless: true,
											height: 23,
											css: "headers",
											label: i18n.t("LBL_ELEMENT_TYPES")
										},
										{
											css: {
												"border": "1px solid #dadee0 !important",
												"border-top": "none !important",
												"height": "362px !important"
											},
											cols: [self.treeView.getView()]
										}
									]
								},
								{ width: 5 }
							]
						};

						webix.ajax().get(appConfig.restUrl + util.fillTemplate(parent.config.restEndpoints.grids, { gridAlarmsConfig: parent.config.gridAlarmsConfig })).then((d) => {

							if (d != null) {

								let meta = d.json().metadata;

								self.tableAlarmsModal = new DataTable(null, null, meta, []);

								let viewMaintable = {
									id: "viewTable" + parent.uid,
									cols: [
										{ width: 7 },
										{
											//width: 741,
											height: 385,
											rows: [
												{
													view: "label",
													borderless: true,
													//width: 591,
													height: 23,
													css: "headers",
													label: i18n.t("LBL_ALARM_TYPES")
												},
												{
													height: 5,
													css: {
														"border-left": "1px solid #dadee0 !important",
														"border-right": "1px solid #dadee0 !important",
														"width": "739px !important"
													}
												},
												{

													css: {
														"border": "1px solid #dadee0 !important",
														"border-top": "none !important",
														"border-left": "none !important",
														"width": "740px !important", 
														"height": "358px !important"
													},
													cols: self.tableAlarmsModal.getView()
												}
											]
										},
										{ width: 100 }
									]
								};

								$$("treeGridStepView" + parent.uid).addView(viewMain, 1);
								$$("treeGridStepView" + parent.uid).addView(viewMaintable, 2);

								$$(self.tableAlarmsModal.getView()[0].id).attachEvent("onAfterSelect", function(selection, preserve) {
									$$("button-next-" + position + "-" + parent.uid).enable();
									$$("calendar1-undefined").setValue(new Date());
									
									let alarmSelected = aconfigArray.find(e => e.id === selection.id);
									
									if (alarmSelected){
										self.arrSummary.alarmConfigAlias = i18n.t(alarmSelected.labelAlias);
										self.arrSummary.alarmLevelLabelAlias = i18n.t(alarmSelected.alarmLevel.labelAlias);										
									}
								});

								$$(self.treeView.getView().rows[3].id).attachEvent("onAfterSelect", function(id) {
									$$("button-next-" + position + "-" + parent.uid).disable();
								});
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
					null,
					null
				);

				let viewCalendar;
				
				viewCalendar = {
					id: "viewCalendar" + parent.uid,
					cols: [
						{},
						{
							width: 1000,
							height: 385,
							rows: [
								{
									view: "label",
									borderless: true,
									//width: 850,
									height: 23,
									css: "headers",
									label: i18n.t("LBL_DATE_UNTIL")
								},
								{
									height: 60,
									css: {
										"border-left": "1px solid #dadee0 !important",
										"border-right": "1px solid #dadee0 !important",
										"width": "998px !important"
									}
								},
								{
									id: "calendar-" + parent.uid,
									css: {
										"border": "1px solid #dadee0 !important",
										"border-top": "none !important",
										"width": "998px !important",
										"height": "303px !important"
									},
									cols: [self.calendar.getView()]
								}
							]
						},
						{}
					]
				}

				return viewCalendar;
			}


			let drawSummary = function() {

				let viewSummary;
				
				viewSummary = {
					id: "viewSummary" + parent.uid,
					cols: [
						{},
						{
							width: 1000,
							height: 385,
							css: { "border": "1px solid #dadee0 !important", "box-sizing": "border-box" },
							rows: [
								{ height: 50 },
								{
									cols: [
										{ width: 80 },
										{
											css: "ims-incident-report-tab-details",
											rows: [
												{
													view: "label",
													id: "alarm-title"
												},
												{ height: 10 },
												{
													view: "label",
													id: "alarm-name"
												},
												{ height: 10 },
												{
													view: "label",
													id: "alarm-level"

												},
												{ height: 10 },
												{
													view: "label",
													id: "alarm-date"
												},
												{ height: 10 },
												{
													view: "label",
													id: "alarm-user"
												},
												{ height: 10 },
												{
													view: "label",
													id: "alarm-comment"
												}
											]
										},
										//{ width: 80 }
									]
								},
								{ height: 40 }
							]
						},
						{}
					]
				}

				return viewSummary;
			}

			return eval(step.drawFunction)();

		}


		let drawHeader = function(position) {

			let step = self.getStep(position);
			let maxWidthLabels = 500;
			let container = $$(parent.args.containerId);

			if (container) {

				maxWidthLabels = container.config.width - 600;
			}

			let viewHeader;
			
			viewHeader = {
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
														  <span class="header-wizard-second-line">${i18n.t("LBL_STEP") + " " + step.position + " " + i18n.t("LBL_OF") + " " + parent.steps.length + ": " + i18n.t(step.headerLabelDescription)} </span>
													</div>`;
								}
							},
							{},
							{
								view: "template",
								//width: 140,
								borderless: true,
								css: { "background": "transparent" },
								template: function() {

									return `<div class="text">
														  <span class="header-wizard-step">${(step.position < parent.steps.length) ? (i18n.t("LBL_STEP") + " " + step.position) : i18n.t("LBL_LAST_STEP")}</span>
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

			let viewSteps;
			
			viewSteps = {
				rows: [
					{
						height: 5,
						//width: 850
					},
					{
						height: 66,
						css: { "margin-top": "-24px" },
						borderless: true,
						template: function() {

							if (Array.isArray(parent.steps) && parent.steps.length > 0) {

								let progressBar = `<div class="progressbar-wrapper">
													  <ul class="progressbar">`;

								parent.steps.forEach(e => {

									progressBar += `<li style="width: ${100 / parent.steps.length}% !important" `;

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


		let drawStepAlarm = function(position) {

			let icon = (position === parent.config.summaryStepPosition) ? "ico webix_icon mdi mdi-flag-checkered" : "ico webix_icon mdi mdi-numeric-" + position + "-box";
			let label = parent.steps.find(e => e.position === position);

			let viewStepAlarm;
			
			viewStepAlarm = {
				cols: [
					{},
					{
						width: 1000,
						rows: [
							{ height: 15 },
							{
								view: "label",
								label: `<span class="${icon}" style="font-size: 30px;"></span><span class="content-wizard-label">${i18n.t(label.stepLabel)}</span>`
							},
							{ height: 17 }
						]
					},
					{}
				]
			};

			return viewStepAlarm;
		}


		let drawToolbarActions = function(position) {

			let firstStep = false;
			let lastStep = false;
			let step = self.getStep(position);

			if (Array.isArray(parent.steps) && parent.steps.length > 0) {

				firstStep = (step.position === 1);
				lastStep = (!firstStep && step.position === parent.steps.length);
			}

			let viewToolbarActionsButtons = [
				{ width: 15 },
				{
					id: "button-cancel-" + position + "-" + parent.uid,
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
					id: "button-next-" + position + "-" + parent.uid,
					view: "button",
					css: !lastStep ? "dialog-accept" : "dialog-finish",
					width: 130,
					height: 35,
					label: !lastStep ? i18n.t("LBL_NEXT") : i18n.t("LBL_FINALIZE"),
					on: {
						onItemClick() {

							if (!lastStep) {

								let pos = position + 1;
								let next = $$("multi" + parent.uid).getChildViews()[step.position];

								if (next) {

									next.show();
									self.setCurrentStep(step.position + 1);
								}

								if (position === parent.config.steps.fromNoMap[0].position && self.tableAlarmsModal.getSelectedIds().length === 0) {

									$$("button-next-" + pos + "-" + parent.uid).disable();
								}

								if (pos === parent.config.summaryStepPosition) {

									//let user = JSON.parse(sessionStorage.getItem('user.data'));
									let user = util.user.getUserData();
									let date = util.toLocaleString(self.calendar.getSelectedDates().from.getTime() / 1000);

									self.arrSummary.alarmDisabled = self.disableAlarm;
									self.arrSummary.filteredUntil = date;
									self.arrSummary.user = user.name;

									$$("alarm-name").setValue(`<div><span class="ico webix_icon mdi mdi-exclamation-thick" style="margin-top: 7px"></span><span class="field">${i18n.t("LBL_ALARM")}</span></div><div class="value">${self.arrSummary.alarmConfigAlias}</div>`);
									$$("alarm-level").setValue(`<div><span class="ico webix_icon mdi mdi-signal-cellular-3" style="margin-top: 7px"></span><span class="field">${i18n.t("LBL_LEVEL")}</span></div><div class="value">${self.arrSummary.alarmLevelLabelAlias}</div>`);
									$$("alarm-date").setValue(`<div><span class="ico webix_icon wxi-calendar" style="margin-top: 7px"></span><span class="field">${i18n.t("LBL_UNTIL")}</span></div><div class="value">${self.arrSummary.filteredUntil}</div>`);
									$$("alarm-user").setValue(`<div><span class="ico webix_icon mdi mdi-account" style="margin-top: 7px"></span><span class="field">${i18n.t("LBL_USER")}</span></div><div class="value">${self.arrSummary.user}</div>`);
									$$("alarm-comment").setValue(`<div>
																	<span class="ico webix_icon mdi mdi-comment-edit" style="margin-top: 7px"></span>
																	<span class="field">${i18n.t("LBL_COMMENT")}</span>
																</div>
																<div class="value">
																<textarea id="textarea-alarm-filtered" class="alarm-view-rectangle-back" style="resize: none; border-width: 1px; overflow: auto; display: inline-block; vertical-align: top; margin-top: 0px; width: 626px;"></textarea>
																</div>`);
									
									let titleSummary = (self.arrSummary.alarmDisabled) ? `<div><span style="font-size: 13px">${i18n.t("LBL_ALARM_FILTER_RADIO_DISABLED")}</span></div>` : `<div><span style="font-size: 14px; color: #475466">${i18n.t("LBL_ALARM_FILTER_RADIO_MUTE")}</span></div>`;

									$$("alarm-title").setValue(titleSummary);

								}

							} else {

								let ms = new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_ALARM_CREATE_ALARMS_FILTER_CONFIRM_DESCRIPTION"), onAccept, null);
								ms.show();
								
								function onAccept() {

									let filteredAlarms = [];
									//let user = JSON.parse(sessionStorage.getItem('user.data'));
									let user = util.user.getUserData();
									let textareaValue = $$("alarm-comment").getNode().querySelector("#textarea-alarm-filtered").value;

									self.tableAlarmsModal.getSelectedIds().forEach(alarm => {

										filteredAlarms.push({

											alarmConfigId: alarm,
											filteredUntil: self.calendar.getSelectedDates().from.getTime(),
											elementId: self.alarmMutedElementSelect.id,
											enable: true,
											alarmDisabled: self.disableAlarm,
											userId: user.id,
											comment: textareaValue
										});
									});

									if (Array.isArray(filteredAlarms) && filteredAlarms.length > 0) {

										let body = {

											alarmFiltereds: filteredAlarms
										};

										webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + parent.config.actionEndpoints.mute, body).then(function() {
											parent.args.callbacks[0](1000);
											$$(self.createView).close();

										});
									}									
								}
							}
						}
					}
				},
				{ width: 15 }
			];

			let viewToolbarActions = {
				cols: [
					{
						rows: [
							{
								view: "toolbar",
								height: 45,
								borderless: true,
								elements: viewToolbarActionsButtons
							}						]
					}
				]
			};

			if (!firstStep) {

				viewToolbarActionsButtons.splice(3, 0, {
					id: "button-previous-" + position + "-" + parent.uid,
					view: "button",
					css: "dialog-previous",
					width: 130,
					height: 35,
					label: i18n.t("LBL_PREVIOUS"),
					on: {
						onItemClick() {

							$$("multi" + parent.uid).back();

							self.setCurrentStep(step.position - 1);
						}
					}
				});
			}

			return viewToolbarActions;
		};

		if (Array.isArray(parent.steps) && parent.steps.length > 0) {

			parent.steps.forEach(e => {

				let stepContent = [];

				stepContent.push(drawHeader(e.position));
				stepContent.push(drawProgressBar(e.position));
				stepContent.push(drawStepAlarm(e.position));
				stepContent.push(drawContent(e.position));
				stepContent.push({ rows: [{ height: 22 }] });
				stepContent.push(drawToolbarActions(e.position));
				stepContent.push({ rows: [{ height: 15 }] });

				multiView.cells.push({ rows: stepContent });

			});
		}

		return multiView;
	}


	getStep(position) {

		let step = undefined;

		step = this.parent.steps.find(e => e.position === position);

		return step;
	}


	setCurrentStep(position) {

		let currentStep = this.getCurrentStep();
		let newStep = undefined;

		if (position > 0 && position <= this.parent.steps.length) {

			currentStep.active = false;

			newStep = this.getStep(position);
			newStep.active = true;

			if (position === this.parent.steps.length) {

				$$("button-next-" + position + "-" + this.parent.uid).enable();
			}
		}

		return !newStep ? currentStep : newStep;
	}


	getCurrentStep() {

		let currentStep = undefined;

		currentStep = this.parent.steps.find(e => e.active);

		return currentStep;
	}

}