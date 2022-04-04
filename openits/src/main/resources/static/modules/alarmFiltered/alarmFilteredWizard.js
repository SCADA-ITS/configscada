import { util } from "../../lib/util.js";
import CalendarDialogBox from "../../component/calendarDialogBox.js";
import DataTable from "../../component/datatable.js";
import MessageBox from "../../component/messageBox.js";
import ListView from "../../component/listview.js";

export default class AlarmFilteredWizard {

	constructor(parent) {

		this.parent = parent;
		this.tableElements = undefined;
		this.tableAlarmsModal = undefined;
		this.treeView = undefined;
		this.steps = undefined;
		this.disableAlarm = true;
		this.alarmMutedElementsIdSelect = [];
		this.alarmMutedAlarmsIdSelect = [];
		this.alarmSelectedArr = [];
		this.elementAlias = [];
		this.calendar = undefined;
		this.arrSummary = [];
		this.createView = undefined;
		this.viewMaintable = undefined;
		this.viewMain = undefined;
		this.aconfigArray = [];
		this.initialAlarmsArr = [];

	}


	drawComponents() {

		let view = {
			id: this.uid,
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
						}
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

		if (this.tableElements) {

			this.tableElements.callbacks();
		}

		if (this.listView) {

			this.listView.selectByIndex(0);
		}
	}


	drawMain() {

		let self = this;
		let parent = self.parent;

		let multiView = {

			view: "multiview",
			id: "multi" + parent.uid,
			animate: false,
			keepViews: true,
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
															self.listView.selectByIndex(0);

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


			let drawStepCreateListGrid = function() {

				let viewContent;
				let elementTypeView = [];
				let elementTypesList = window.em.getAll(EntityType.ELEMENT_TYPE);
				let elementTypesFiltered = elementTypesList.filter(e => e.visible === true && e.grid);

				viewContent = {
					id: "listGridStepView" + parent.uid,
					cols: []
				};

				if (Array.isArray(elementTypesFiltered) && elementTypesFiltered.length > 0) {

					elementTypesFiltered.forEach(e => {

						elementTypeView.push({
							id: e.id,
							alias: i18n.t(e.labelAlias),
							description: i18n.t(e.labelDescription),
							image: e.elementTypePhoto,
							grid: e.grid
						});
					});
				}

				if (Array.isArray(elementTypeView) != undefined && elementTypeView.length > 0) {

					elementTypeView.sort((a, b) => (a.alias > b.alias) ? 1 : ((b.alias > a.alias) ? -1 : 0));

					let events = {};

					events[ListView.EventTypes.ON_SELECT_ROW] = function(ids, callbackEnable) {

						if (callbackEnable) {

							callbackEnable(false);
						}

						if (Array.isArray(ids) && ids.length > 0) {

							self.tableElements.clear();

							let elements = window.em.getAll(EntityType.ELEMENT);
							let elementsByType = elements.filter(element => element.elementType.id === ids[0]);

							if (Array.isArray(elementsByType) && elementsByType.length > 0) {

								elementsByType.forEach(element => {

									let row = {};

									row.id = element.id;
									row.alias = element.alias;
									row.description = element.description;

									self.tableElements.add(row);
								});

								self.tableElements.selectByIndex(0);

								if (callbackEnable) {

									callbackEnable(true);
								}
							}
						}
					};

					let options = {}

					options[ListView.Options.MODE] = ListView.DrawMode.COMPACT;

					self.listView = new ListView(elementTypeView, options, events);
				}

				let viewMain = {
					id: "viewList" + parent.uid,
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
									cols: [self.listView.getView()]
								}
							]
						},
						{ width: 5 }
					]
				};

				let metadata = {};
				let events = {};

				const defaultGridOptionValues = [
					{ gridOption: { id: 2 }, value: "true" },
					{ gridOption: { id: 3 }, value: "2" },
					{ gridOption: { id: 4 }, value: "asc" },
					{ gridOption: { id: 5 }, value: undefined },
					{ gridOption: { id: 6 }, value: undefined },
					{ gridOption: { id: 7 }, value: "false" },
					{ gridOption: { id: 8 }, value: "false" },
					{ gridOption: { id: 10 }, value: "false" },
					{ gridOption: { id: 11 }, value: "false" },
					{ gridOption: { id: 12 }, value: "false" },
					{ gridOption: { id: 13 }, value: "false" },
					{ gridOption: { id: 16 }, value: "img/grid/default.png" },
					{ gridOption: { id: 23 }, value: "false" }
				];

				metadata = {
					filter: true,
					label: "LBL_ELEMENTS",
					visible: true,
					gridOptionValues: defaultGridOptionValues,
					gridFields: [
						{
							id: 1,
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: true,
							needsTranslation: false,
							parameterized: false,
							position: 1,
							required: true,
							sourceField: "id",
							visible: false,
							widthPx: 1,
							gridFieldDataType: {
								id: 1,
								name: "string"
							},
							gridFieldIcons: []
						},
						{
							id: 2,
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: "LBL_ALIAS",
							labelTooltip: "LBL_ALIAS",
							needsTranslation: true,
							parameterized: false,
							position: 2,
							required: true,
							sourceField: "alias",
							visible: true,
							widthPx: 230,
							gridFieldDataType: {
								id: 1,
								name: "string"
							},
							gridFieldIcons: []
						},
						{
							id: 3,
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: "LBL_DESCRIPTION",
							labelTooltip: "LBL_DESCRIPTION",
							needsTranslation: true,
							parameterized: false,
							position: 3,
							required: true,
							sourceField: "description",
							visible: true,
							fillspace: true,
							widthPx: 400,
							gridFieldDataType: {
								id: 1,
								name: "string"
							},
							gridFieldIcons: []
						}
					],
					gridCommands: []
				};

				events[DataTable.EventTypes.ON_SELECT_ROW] = function(elementsId) {

					if (Array.isArray(elementsId) && elementsId.length > 0) {

						self.alarmMutedElementsIdSelect = [];

						if (self.tableAlarmsModal) {

							self.tableAlarmsModal.clear();
						}

						for (let elementId of elementsId) {

							self.alarmMutedElementsIdSelect.push(elementId);
						}

						let element = window.em.getByField(EntityType.ELEMENT, "id", elementsId[0]);
						let elementTypeId = element.elementType.id;
						let alarmConfigs = window.em.getAll(EntityType.ALARM_CONFIG);
						self.aconfigArray = alarmConfigs.filter(e => e.elementType.id === elementTypeId);

						if (Array.isArray(self.aconfigArray) && self.aconfigArray.length > 0) {

							self.aconfigArray.forEach(alarmConfig => {

								let row = {};

								row.id = alarmConfig.id;
								row.alarmConfigAlias = i18n.t(alarmConfig.labelAlias);
								row.alarmLevelLabelAlias = i18n.t(alarmConfig.alarmLevel.labelAlias);

								if (self.tableAlarmsModal) {

									self.tableAlarmsModal.add(row);
									self.tableAlarmsModal.selectByIndex(0);

								} else {

									self.initialAlarmsArr.push(row);
								}
							});
						}

						$$("button-next-" + position + "-" + parent.uid).enable();
						$$("calendar1-undefined").setValue(new Date());

					} else {

						$$("button-next-" + position + "-" + parent.uid).disable();
					}
				};

				self.tableElements = new DataTable(null, null, metadata, [], events);

				let viewMaintable = {
					id: "view-elements-table" + parent.uid,
					cols: [
						{ width: 7 },
						{
							height: 385,
							rows: [
								{
									view: "label",
									borderless: true,
									height: 23,
									css: "headers",
									label: i18n.t("LBL_EQUIPMENT_S")
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
									cols: self.tableElements.getView()
								}
							]
						},
						{ width: 96 }
					]
				};

				viewContent.cols.push(viewMain, viewMaintable);

				return viewContent
			}

			let drawStepCreateAlarmGrid = function() {

				let viewContent = {
					id: "alarmGridStepView" + parent.uid,
					cols: []
				};

				webix.ajax().get(appConfig.restUrl + util.fillTemplate(parent.config.restEndpoints.grids, { gridAlarmsConfig: parent.config.gridAlarmsConfig })).then((d) => {

					if (d != null) {

						let meta = d.json().metadata;
						let data = [];
						console.log(meta)

						if (Array.isArray(self.initialAlarmsArr) && self.initialAlarmsArr.length > 0) {

							data = self.initialAlarmsArr;
						}

						self.tableAlarmsModal = new DataTable(null, null, meta, data);

						let viewMaintable = {
							id: "viewTable" + parent.uid,
							cols: [
								{ width: 96 },
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
												"height": "358px !important"
											},
											cols: self.tableAlarmsModal.getView()
										}
									]
								},
								{ width: 96 }
							]
						};

						$$("alarmGridStepView" + parent.uid).addView(viewMaintable, 2);

						$$(self.tableAlarmsModal.getView()[0].id).attachEvent("onAfterSelect", function(selection, preserve) {

							$$("button-next-" + position + "-" + parent.uid).enable();
							$$("calendar1-undefined").setValue(new Date());
						});

						$$(self.tableAlarmsModal.getView()[0].id).attachEvent("onAfterUnSelect", function(selection, preserve) {

							if (self.tableAlarmsModal.getSelectedIds().length === 0) {

								$$("button-next-" + position + "-" + parent.uid).disable();
							}
						});

						if (self.tableAlarmsModal) {

							self.tableAlarmsModal.callbacks();
							self.tableAlarmsModal.selectByIndex(0);
						}
					}
				});

				return viewContent
			}

			let drawStepCalendar = function() {

				let startDate = Date.now();
				let minToAdd = self.parent.config.minAddToCurrentDate * 60000;
				let newDate = new Date(startDate + minToAdd);

				self.calendar = new CalendarDialogBox(i18n.t("LBL_DIALOG_MUTE_ALARMS"), i18n.t("LBL_DIALOG_UNMUTE_ALARMS"), newDate, false,

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
								{ height: 30 },
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
													id: "alarm-equipments",
													tooltip: function(obj) {

														let tooltip = [];

														if (Array.isArray(self.elementAlias) && self.elementAlias.length > 1) {

															for (let alias of self.elementAlias) {

																tooltip.push(alias);
															}
														}

														return tooltip.join(",");
													}
												},
												{ height: 10 },
												{
													view: "label",
													id: "alarm-name",
													tooltip: function(obj) {

														let tooltip = [];

														if (Array.isArray(self.alarmSelectedArr) && self.alarmSelectedArr.length > 1) {

															for (let alarmSelected of self.alarmSelectedArr) {

																tooltip.push(alarmSelected.alias);
															}
														}

														return tooltip.join(",");
													}
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
										}
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

								if (position === parent.config.steps.fromNoMap[0].position && self.tableElements.getSelectedIds().length === 0) {

									$$("button-next-" + pos + "-" + parent.uid).disable();
								}

								if (pos === parent.config.summaryStepPosition) {

									self.alarmSelectedArr = [];
									self.elementAlias = [];
									let user = util.user.getUserData();
									let date = util.toLocaleString(self.calendar.getSelectedDates().from.getTime() / 1000);
									let alarmsIdSelected = self.tableAlarmsModal.getSelectedIds();

									for (let alarmId of alarmsIdSelected) {

										let alarmSelected = self.aconfigArray.find(e => e.id === alarmId);

										if (alarmSelected) {

											let elementId = (alarmSelected.element && alarmSelected.element.id) ? alarmSelected.element.id : undefined;

											self.alarmSelectedArr.push({
												alias: i18n.t(alarmSelected.labelAlias),
												level: i18n.t(alarmSelected.alarmLevel.labelAlias),
												elementTypeId: alarmSelected.elementType.id,
												elementId: elementId
											});
										}
									}

									for (let elementId of self.alarmMutedElementsIdSelect) {

										let element = window.em.getByField(EntityType.ELEMENT, "id", elementId);
										self.elementAlias.push(element.alias);
									}

									let alarmEllipsis = (self.alarmSelectedArr.length > 1) ? "..." : "";
									let equipmentEllipsis = (self.elementAlias.length > 1) ? "..." : "";

									$$("alarm-equipments").setValue(`<div><span class="ico webix_icon mdi mdi-engine" style="margin-top: 7px"></span><span class="field">${i18n.t("LBL_EQUIPMENT_S")}</span></div><div class="value">${self.elementAlias[0]}${equipmentEllipsis}</div>`);
									$$("alarm-name").setValue(`<div><span class="ico webix_icon mdi mdi-exclamation-thick" style="margin-top: 7px"></span><span class="field">${i18n.t("LBL_ALARM")}</span></div><div class="value">${self.alarmSelectedArr[0].alias}${alarmEllipsis}</div>`);									
									$$("alarm-date").setValue(`<div><span class="ico webix_icon wxi-calendar" style="margin-top: 7px"></span><span class="field">${i18n.t("LBL_UNTIL")}</span></div><div class="value">${date}</div>`);
									$$("alarm-user").setValue(`<div><span class="ico webix_icon mdi mdi-account" style="margin-top: 7px"></span><span class="field">${i18n.t("LBL_USER")}</span></div><div class="value">${user.name}</div>`);
									$$("alarm-comment").setValue(`<div>
																	<span class="ico webix_icon mdi mdi-comment-edit" style="margin-top: 7px"></span>
																	<span class="field">${i18n.t("LBL_COMMENTS_HERE")}</span>
																</div>
																<div class="value">
																<textarea id="textarea-alarm-filtered" class="alarm-view-rectangle-back" style="resize: none; border-width: 1px; overflow: auto; display: inline-block; vertical-align: top; margin-top: 0px; width: 626px;"></textarea>
																</div>`);

									let titleSummary = (self.disableAlarm) ? `<div><span style="font-size: 13px">${i18n.t("LBL_ALARM_FILTER_RADIO_DISABLED")}</span></div>` : `<div><span style="font-size: 14px; color: #475466">${i18n.t("LBL_ALARM_FILTER_RADIO_MUTE")}</span></div>`;

									$$("alarm-title").setValue(titleSummary);
								}

							} else {

								let alarmsWithElementId = self.alarmSelectedArr.filter(alarm => alarm.elementId);

								if (Array.isArray(alarmsWithElementId) && alarmsWithElementId.length > 0) {

									let alarmToSave = undefined;
									let counter = 0;
									let onlyOneElement = (Array.isArray(self.alarmMutedElementsIdSelect) && self.alarmMutedElementsIdSelect.length === 1);

									for (let alarmWithElementId of alarmsWithElementId) {

										if (onlyOneElement) {

											counter = (alarmWithElementId.elementId === self.alarmMutedElementsIdSelect[0]) ? counter + 1 : counter;
											alarmToSave = (counter === alarmsWithElementId.length);

										} else {

											alarmToSave = false;
										}
									}

									if (alarmToSave) {

										saveFilteredAlarm();

									} else {

										let ms = new MessageBox(MessageBox.ERROR, i18n.t("LBL_ALARM_FILTERED_SELECTED_NOT_CONFIGURED_FOR_EQUIPMENT"), null, null);
										ms.show();
									}

								} else {

									saveFilteredAlarm();
								}

								function saveFilteredAlarm() {

									let ms = new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_ALARM_CREATE_ALARMS_FILTER_CONFIRM_DESCRIPTION"), onAccept, null);
									ms.show();

									function onAccept() {

										let currentTime = Date.now();
										let selectedTime = self.calendar.getSelectedDates().from.getTime();

										if ((self.parent.config.minAddToCurrentDate * 60000) > (selectedTime - currentTime)) {

											let ms = new MessageBox(MessageBox.ERROR, i18n.t("LBL_ALARM_FILTERED_LESS_THAN_MINIMUM_ESTABLISHED"), null, null);
											ms.show();

										} else {

											let user = util.user.getUserData();
											let textareaValue = $$("alarm-comment").getNode().querySelector("#textarea-alarm-filtered").value;

											self.tableAlarmsModal.getSelectedIds().forEach(alarm => {

												for (let alarmMutedElementIdSelect of self.alarmMutedElementsIdSelect) {

													let filteredAlarms = [];

													filteredAlarms.push({

														alarmConfigId: alarm,
														filteredUntil: self.calendar.getSelectedDates().from.getTime(),
														elementId: alarmMutedElementIdSelect,
														enable: true,
														alarmDisabled: self.disableAlarm,
														userId: user.id,
														comment: textareaValue
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
											});
										}
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
							}]
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