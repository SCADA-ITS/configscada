import CalendarDialogBox from "../../component/calendarDialogBox.js";
import TreeView from "../../component/treeview.js";
import DataTable from "../../component/datatable.js";
import { util } from "../../lib/util.js";

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
						{ height: 30 }
					]
				}
			]
		};

		let h = document.documentElement.clientHeight - 370;
		let w = document.documentElement.clientWidth - 850;

		this.createView = util.ui.loadWindow(
			i18n.t("LBL_CREATE_ALARMS_FILTER"),
			view,
			h,
			w,
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
			height: 0,
			width: 1000,
			cells: []
		};


		let drawContent = function(position) {

			let step = self.getStep(position);

			let drawStepRadeonOptions = function() {

				let viewContent = {
					cols: [
						{},
						{
							height: 285,
							rows: [
								{
									view: "label",
									borderless: true,
									width: 850,
									height: 23,
									css: "headers",
									label: i18n.t("LBL_ALARM_FILTER_TYPE")
								},
								{
									borderless: true,
									css: { "border": "1px solid #dadee0 !important", "width": "848px !important", "height": "261px !important" },
									cols: [
										{},
										{
											width: 300,
											rows: [
												{ height: 50 },
												{
													view: "radio",
													vertical: true,
													align: "center",
													height: 250,
													css: "alarm-radio-options",
													value: 1, options: [
														{ "id": 1, "value": i18n.t("LBL_ALARM_FILTER_RADIO_DISABLE") },
														{ "id": 2, "value": i18n.t("LBL_ALARM_FILTER_RADIO_MUTE") }
													],
													on: {
														onChange: function(newv) {
															newv === "1" ? self.disableAlarm = true : self.disableAlarm = false;
														}
													}
												},
											]
										},
										{}
									]
								},
								{ height: 30 },
							]
						},
						{}
					]
				}

				return viewContent
			}


			let drawStepCreateTreeGrid = function() {

				let viewContent = {
					id: "treeGridStepView" + parent.uid,
					cols: []
				}

				webix.ajax().get(appConfig.restUrl + util.fillTemplate("trees/${id}", { id: parent.config.treeElementType })).then(function(dat) {

					if (dat != null) {

						let metadata = dat.json().metadata;
						let data = dat.json().data;
						let events = {};

						events[TreeView.EventTypes.ON_SELECT_ITEM] = function(item) {

							self.tableAlarmsModal.clear();

							let element = window.em.getByField(EntityType.ELEMENT, undefined, item.id);
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

									self.arrSummary.alarmConfigAlias = row.alarmConfigAlias;
									self.arrSummary.alarmLevelLabelAlias = row.alarmLevelLabelAlias;

								}
							} else {

								let row = {};

								row.id = aconfitArray.id;
								row.alarmConfigAlias = aconfitArray.alias;
								row.alarmTypeLabelAlias = i18n.t(aconfitArray.alarmType.labelAlias);
								row.alarmLevelLabelAlias = i18n.t(aconfitArray.alarmLevel.labelAlias);

								self.tableAlarmsModal.add(row);

								self.arrSummary.alarmConfigAlias = row.alarmConfigAlias;
								self.arrSummary.alarmLevelLabelAlias = row.alarmLevelLabelAlias;
							}

						};

						self.treeView = new TreeView(metadata, data, events);

						let viewMain = {
							id: "viewTree" + parent.uid,
							width: 329,
							cols: [
								{},
								{
									width: 250,
									height: 285,
									rows: [
										{
											view: "label",
											borderless: true,
											width: 250,
											height: 23,
											css: "headers",
											label: i18n.t("LBL_ELEMENT_TYPES")
										},
										{
											css: { "border": "1px solid #dadee0 !important", "border-top": "none !important", "width": "248px !important", "height": "262px !important" },
											cols: [self.treeView.getView()]
										}
									]
								},
								{ width: 5 }
							]
						};

						webix.ajax().get(appConfig.restUrl + "grids/" + parent.config.gridAlarmsConfig + "/" + "id").then((dat) => {

							if (dat != null) {

								let metadata = dat.json().metadata;

								self.tableAlarmsModal = new DataTable(null, null, metadata, []);

								let viewMaintable = {
									id: "viewTable" + parent.uid,
									cols: [
										{ width: 7 },
										{
											width: 591,
											height: 285,
											rows: [
												{
													view: "label",
													borderless: true,
													width: 591,
													height: 23,
													css: "headers",
													label: i18n.t("LBL_ALARM_TYPES")
												},
												{
													height: 5,
													css: { "border-left": "1px solid #dadee0 !important", "border-right": "1px solid #dadee0 !important", "width": "589px !important" }
												},
												{

													css: { "border": "1px solid #dadee0 !important", "border-top": "none !important", "border-left": "none !important", "width": "590px !important", "height": "258px !important" },
													cols: self.tableAlarmsModal.getView()
												}
											]
										},
										{}
									]
								};

								$$("treeGridStepView" + parent.uid).addView(viewMain, 1);
								$$("treeGridStepView" + parent.uid).addView(viewMaintable, 2);

								$$(self.tableAlarmsModal.getView()[0].id).attachEvent("onAfterSelect", function(selection, preserve) {
									$$("button-next-" + position + "-" + parent.uid).enable();
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

				let viewCalendar = {
					id: "viewCalendar" + parent.uid,
					cols: [
						{},
						{
							width: 850,
							height: 285,
							rows: [
								{
									view: "label",
									borderless: true,
									width: 850,
									height: 23,
									css: "headers",
									label: i18n.t("LBL_DATE_UNTIL")
								},
								{
									height: 5,
									css: { "border-left": "1px solid #dadee0 !important", "border-right": "1px solid #dadee0 !important", "width": "848px !important" }
								},
								{
									id: "calendar-" + parent.uid,
									css: { "border": "1px solid #dadee0 !important", "border-top": "none !important", "width": "848px !important", "height": "258px !important" },
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

				let viewSummary = {
					id: "viewSummary" + parent.uid,
					cols: [
						{},
						{
							width: 850,
							height: 285,
							css: { "border": "1px solid #dadee0 !important", "box-sizing": "border-box" },
							rows: [
								{ height: 10 },
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
												{
													view: "label",
													id: "alarm-name"
												},
												{
													view: "label",
													id: "alarm-level"

												},
												{
													view: "label",
													id: "alarm-date"
												},
												{
													view: "label",
													id: "alarm-user"
												}
											]
										},
										{ width: 80 }
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
														  <span class="header-wizard-second-line">${i18n.t("LBL_STEP") + " " + step.position + " " + i18n.t("LBL_OF") + " " + parent.steps.length + ": " + i18n.t(step.headerLabelDescription)} </span>
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

			let viewSteps = {
				rows: [
					{ height: 5, width: 850 },
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

			let viewStepAlarm = {
				cols: [
					{},
					{
						width: 850,
						rows: [
							{ height: 15 },
							{
								view: "label",
								label: `<span class="${icon}" style="font-size: 30px;"></span><span class="content-wizard-label">${label.stepLabel}</span>`
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

								let next = $$("multi" + parent.uid).getChildViews()[step.position];

								if (next) {

									next.show();
									self.setCurrentStep(step.position + 1);
								}

								if (position === parent.config.steps.fromNoMap[0].position) {
									let pos = position + 1;
									$$("button-next-" + pos + "-" + parent.uid).disable();
								}

								if (self.tableAlarmsModal && self.tableAlarmsModal.getSelectedIds().length > 0 && self.calendar.getSelectedDates()) {

									let user = JSON.parse(sessionStorage.getItem('user.data'));

									self.arrSummary.alarmDisabled = self.disableAlarm;
									self.arrSummary.filteredUntil = new Date(self.calendar.getSelectedDates().from.getTime());
									self.arrSummary.user = user.name;


									$$("alarm-name").setValue(`<div><span class="ico webix_icon mdi mdi-exclamation-thick" style="margin-top: 7px"></span><span class="field">${i18n.t("LBL_ALARM")}</span></div><div class="value">${self.arrSummary.alarmConfigAlias}</div>`);
									$$("alarm-level").setValue(`<div><span class="ico webix_icon mdi mdi-signal-cellular-3" style="margin-top: 7px"></span><span class="field">${i18n.t("LBL_LEVEL")}</span></div><div class="value">${self.arrSummary.alarmLevelLabelAlias}</div>`);
									$$("alarm-date").setValue(`<div><span class="ico webix_icon wxi-calendar" style="margin-top: 7px"></span><span class="field">${i18n.t("LBL_UNTIL")}</span></div><div class="value" style="max-width: 300px; overflow:hidden; white-space:nowrap; text-overflow:ellipsis">${self.arrSummary.filteredUntil}</div>`);
									$$("alarm-user").setValue(`<div><span class="ico webix_icon mdi mdi-account" style="margin-top: 7px"></span><span class="field">${i18n.t("LBL_USER")}</span></div><div class="value">${self.arrSummary.user}</div>`);

									let titleSummary = (self.arrSummary.alarmDisabled) ? `<div><span style="font-size: 13px">${i18n.t("LBL_ALARM_FILTER_RADIO_DISABLED")}</span></div>` : `<div><span style="font-size: 14px; color: #475466">${i18n.t("LBL_ALARM_FILTER_RADIO_MUTE")}</span></div>`;

									$$("alarm-title").setValue(titleSummary);

								}

							} else {

								webix.confirm({
									title: i18n.t("LBL_CREATE_ALARMS_FILTER_CONFIRM"),
									ok: i18n.t("LBL_YES"),
									cancel: i18n.t("LBL_NO"),
									text: i18n.t("LBL_ALARM_CREATE_ALARMS_FILTER_CONFIRM_DESCRIPTION"),
									width: 600
								}).then(function() {

									let mutedAlarms = [];

									self.tableAlarmsModal.getSelectedIds().forEach(alarm => {

										mutedAlarms.push({

											alarmConfigId: alarm,
											filteredUntil: self.calendar.getSelectedDates().from.getTime(),
											elementId: self.alarmMutedElementSelect.id,
											enable: true,
											alarmDisabled: self.disableAlarm

										});
									});

									if (Array.isArray(mutedAlarms) && mutedAlarms.length > 0) {

										let body = {

											alarmFiltereds: mutedAlarms
										};
								
										webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + parent.config.actionEndpoints.mute, body).then(function(data) {
											parent.args.callbacks[0](2000);
											$$(self.createView).close();

										});
									}
								});
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
							}
						]
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

							let pos = parent.config.gridStepPosition;

							$$("multi" + parent.uid).back();

							self.setCurrentStep(step.position - 1);

							if (step.position - 1 === pos) {
								self.tableAlarmsModal.clear();
								self.treeView.unselectAll();
								$$("button-next-" + pos + "-" + parent.uid).disable();
							}
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
				stepContent.push({ rows: [{ height: 22 }] });

				multiView.cells.push({ rows: stepContent });

			});
		}

		return multiView;
	}


	getStep(position) {

		let step = undefined;

		step = this.parent.steps.find(e => e.position === position);

		return step;
	};


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
	};

}