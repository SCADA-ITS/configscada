import { util } from "../lib/util.js";
import GenericModule from "../modules/genericModule.js";
import InputDialogBox from "../component/inputDialogBox.js";
import TreeView from "../component/treeview.js";
import DataTable from "../component/datatable.js";
import MessageBox from "../component/messageBox.js";
import BackOfficeModule from "./backOffice/backOfficeModule.js";


export default class AlarmModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();
		this.userData = util.user.getUserData();
		this.requestAlarm = true;
		this.stompClient = undefined;
		this.requestReport = true;
		this.config = module.config;

		this.editAlarmConfigsPermission = util.module.isAllowedPermission(this.config.requiredPermissions.edit);

	}

	getView() {

		let view = undefined;

		if (this.moduleAction.id === this.config.moduleAction.view) {

			view = super.getView();
		}

		return view;
	}

	loadData() {

		let self = this;

		if (this.moduleAction.id === this.config.moduleAction.view) {

			this.refresh();

		}
		else if (this.moduleAction.id === this.config.moduleAction.recognize) {

			recognize();
		}
		else if (this.moduleAction.id === this.config.moduleAction.deactivate) {

			deactivate();
		}
		else if (this.moduleAction.id === this.config.moduleAction.createManualAlarm) {

			createManualAlarm();
		}

		function recognize() {

			let recog = function(alarmsId) {

				//				(new InputDialogBox(i18n.t("LBL_DIALOG_RECOGNIZE_ALARMS"), i18n.t("LBL_ARE_YOU_SURE_RECOGNIZE"), function(comments) {
				//					
				//					let body = {
				//						alarmIds: alarmsId,
				//						recognizedComments: comments,
				//						userId: self.userData.id
				//					};
				//
				//					webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.actionEndpoints.recognize, body).then(function(data) {
				//						console.log(data);
				//					});
				//					
				//				})).show();

				showDialogRecognize(function(data) {

					let body = {
						alarmIds: alarmsId,
						recognizedComments: data.comments,
						disagree: data.disagree,
						userId: self.userData.id
					};

					webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.actionEndpoints.recognize, body).then(
						function(data) {
							console.log(data);
						}
					);
				});
			};

			function showDialogRecognize(onAccept) {

				let body = {
					width: 600,
					height: 250,
					css: "dialog-input-custom",
					rows: [
						{
							template: i18n.t("LBL_ARE_YOU_SURE_RECOGNIZE"),
							height: 40,
							borderless: true,
							css: "dialog-input-text"
						},
						{
							view: "form",
							borderless: true,
							rows: [
								{ height: 1 },
								{
									cols: [
										{ width: 1 },
										{
											view: "textarea",
											id: "input-dialog-" + self.uid,
											name: "input-dialog-" + self.uid,
											height: 70,
											scroll: "auto",
											value: "",
											on: {
												onAfterRender: function() {

													this.focus();

													if (this.getValue()) {

														this.getInputNode().setSelectionRange(0, this.getValue().length);
													}
												}
											}
										},
										{ width: 1 }
									]
								},
								{ height: 1 },
								{
									cols: [
										{ width: 1 },
										{
											view: "checkbox",
											id: "chk-disagree-" + self.uid,
											css: { "margin-left": "-75px !important" },
											labelRight: i18n.t("LBL_DISAGREE"),
											tooltip: i18n.t("LBL_CHECK_TO_DISAGREE_THIS_ALARM"),
											value: false
										},
										{ width: 1 }
									]
								},
								{ height: 1 }
							]
						},
						{},
						{
							height: 41,
							cols: [
								{ width: 20 },
								{
									view: "button",
									css: "dialog-cancel",
									width: 130,
									height: 35,
									label: i18n.t("LBL_CANCEL"),
									value: i18n.t("LBL_CANCEL"),
									click: function() {

										const window = this.getTopParentView();
										window.close();
									}
								},
								{},
								{
									view: "button",
									id: "button-accept-" + self.uid,
									css: "dialog-accept",
									width: 130,
									height: 35,
									label: i18n.t("LBL_ACCEPT"),
									value: i18n.t("LBL_ACCEPT"),
									click: function() {

										const window = this.getTopParentView();
										const txtDialogReconize = $$("input-dialog-" + self.uid);
										const chkDisagree = $$("chk-disagree-" + self.uid);

										let data = {
											comments: txtDialogReconize.getValue(),
											disagree: (chkDisagree.getValue() === 1)
										};

										// Prevent z-index disorder. Bug preventing inputboxes contained in tabs from being selected
										setTimeout(function() {

											onAccept(data);

										}, 1);

										window.close();
									}
								},
								{ width: 20 }
							]
						},
						{ height: 15 }
					]
				};

				util.ui.loadWindow(i18n.t("LBL_DIALOG_RECOGNIZE_ALARMS"), body, 400, 600, true);
			}

			function recognizeAll() {

				webix.ajax().get(appConfig.restUrl + self.config.restEndpoints.alarms).then(function(dat) {

					if (dat != null) {

						let alarms = dat.json();

						if (Array.isArray(alarms) && alarms.length > 0) {

							let alarmsToRecognize = alarms.filter(alarm => {

								return !alarm.recognized;
							});

							if (Array.isArray(alarmsToRecognize) && alarmsToRecognize.length > 0) {

								let alarmsId = alarmsToRecognize.map(alarm => {

									return alarm.id;
								});

								recog(alarmsId);
							}
						}
					}
				});
			}

			// Recognize selectedPks
			if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {

				recog(self.args.selectedPks);
			}
			// Recognize all
			else {

				recognizeAll();
			}
		}

		function deactivate() {

			if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {

				let allAlarmsManual = true;

				webix.ajax().headers(HEADER_POST).post(appConfig.restUrl + self.config.restEndpoints.alarmsArray, JSON.stringify(self.args.selectedPks)).then(function(data) {

					let responseAlarms = data.json();

					if (Array.isArray(responseAlarms) && responseAlarms.length > 0) {

						responseAlarms.forEach(alarm => {

							let alarmConfig = window.em.getByField(EntityType.ALARM_CONFIG, "id", alarm.alarmConfig.id);

							if (alarmConfig) {

								let alarmType = alarmConfig.alarmType.id;

								if (alarmType) {

									allAlarmsManual &= Array.isArray(self.config.alarmTypeManual) && (self.config.alarmTypeManual.indexOf(alarmType) !== -1);

								}
							}
						});

						if (allAlarmsManual) {

							(new InputDialogBox(i18n.t("LBL_DIALOG_DEACTIVATE_ALARMS"), i18n.t("LBL_ARE_YOU_SURE_DEACTIVATE"), function(comments) {

								let body = {
									alarmIds: self.args.selectedPks,
									deactivationComments: comments,
									userId: self.userData.id
								};

								webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.actionEndpoints.deactivate, body).then(function(data) {
									console.log(data);
								});

							})).show();
						}
						else {

							let ms = new MessageBox(MessageBox.ERROR, i18n.t("LBL_DEACTIVATE_ALARMS_ERROR_SELECTED_AUTO_ALARMS_NOT_COMPATIBLE"), null, null, null);
							ms.show();

						}
					}
				});
			}
		}

		function createManualAlarm() {

			function drawHeader() {

				let header;

				header = {
					id: "viewHeader-" + self.uid,
					rows: [
						{ height: 10 },
						{
							cols: [
								{
									view: "label",
									label: "<img src='img/grid/alarmConfig.png' class='grid-icon'/>",
									height: 75,
									css: "global-header-icon"
								},
								{
									view: "label",
									height: 75,
									id: "label-bottom-" + self.uid,
									template: "<span>" + i18n.t("LBL_CREATE_MANUAL_ALARM") + "</span> <br> <span style='font-size: 13px'>" + i18n.t("LBL_CREATE_MANUAL_ALARM_DESCRIPTION") + "</span>",
									css: "global-header-label"
								}
							]
						},
						{ height: 10 }
					]

				};

				return header;
			};


			function drawGrid() {

				let view = {};

				const defaultGridOptionValues = [
					{ id: 1024, value: "false", gridOption: { id: 7, description: "bool", enabled: "true", name: "tb_main", visible: "true" } },
					{ id: 1023, value: "false", gridOption: { id: 10, description: "bool", enabled: "true", name: "tb_actions", visible: "true" } },
					{ id: 1022, value: "false", gridOption: { id: 11, description: "bool", enabled: "true", name: "tb_actions_buttons_commons", visible: "true" } },
					{ id: 1025, value: "img/grid/alarmConfig.png", gridOption: { id: 16, description: "string", enabled: "true", name: "icon", visible: "true" } }
				];

				let metadata = {
					filter: false,
					label: "LBL_ALARM_CONFIG",
					visible: true,
					gridOptionValues: defaultGridOptionValues,
					gridFields: [
						{
							id: 101101,
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: true,
							label: "LBL_ID",
							labelTooltip: "LBL_ID",
							needsTranslation: false,
							parameterized: false,
							position: 1,
							required: true,
							sourceField: "id",
							visible: false,
							widthPx: 50,
							gridFieldDataType: {
								id: 1,
								name: "string"
							}
						},
						{
							id: 101102,
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: "LBL_ALARM_CONFIG_ALIAS",
							labelTooltip: "LBL_ALARM_CONFIG_ALIAS",
							needsTranslation: false,
							parameterized: false,
							position: 2,
							required: true,
							sourceField: "alarmConfigAlias",
							visible: true,
							widthPx: 350,
							gridFieldDataType: {
								id: 1,
								name: "string"
							}
						},
						{
							id: 101104,
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							needsTranslation: true,
							parameterized: false,
							position: 4,
							required: true,
							sourceField: "alarmLevelLabelAlias",
							visible: true,
							label: "LBL_ALARM_LEVEL_ALIAS",
							labelTooltip: "LBL_ALARM_LEVEL_ALIAS",
							widthPx: 100,
							gridFieldDataType: {
								id: 1,
								name: "string"
							}
						}

					]
				};

				self.tableAlarmsModal = new DataTable(null, null, metadata, []);

				view = {
					id: "viewTable" + self.uid,
					css: { "box-sizing": "border-box" },
					width: 590,
					cols: [
						{
							css: { "box-sizing": "border-box" },
							width: 580,
							height: 350,
							rows: self.tableAlarmsModal.getView()

						}
					]
				};

				return view;

			}

			function drawTree() {

				let view = {};

				let alarmsConfig = window.em.getAll(EntityType.ALARM_CONFIG);
				let alarmsConfigManual = alarmsConfig.filter(e => self.config.alarmTypeManual.includes(e.alarmType.id));

				let data = [];
				let elementsTree = [];
				let elements = window.em.getAll(EntityType.ELEMENT);

				alarmsConfigManual.forEach(e => {

					let elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", e.elementType.id);

					let parent = {
						id: elementType.id,
						id_: elementType.id,
						value: elementType.alias,
						data: [],
						props: {
							folder: true,
							translate: false
						}
					}

					if (e.element && e.element.id) {

						elementsTree = elements.filter(item => item.id === e.element.id);

					} else {

						elementsTree = elements.filter(item => item.elementType.id === e.elementType.id);
					}

					if (Array.isArray(elementsTree) && elementsTree.length > 0) {

						elementsTree.forEach(item => {

							let child = {

								id: item.id,
								id_: item.id,
								value: item.alias,
								props: {}
							}

							parent.data.push(child);
						});
					}

					data.push(parent);
				});

				let events = {};

				let metadata = {
					id: -1,
					label: "LBL_EQUIPMENT_HIERARCHY",
					treeOptionValues: [
						{ value: "img/tree/equipment.png", treeOption: { id: 1 } },
						{ value: false, treeOption: { id: 2 } },
						{ value: true, treeOption: { id: 3 } },
						{ value: true, treeOption: { id: 4 } },
						{ value: true, treeOption: { id: 5 } },
						{ value: false, treeOption: { id: 6 } }
					]
				};

				events[TreeView.EventTypes.ON_SELECT_ITEM] = function(item) {

					self.tableAlarmsModal.clear();

					let element = window.em.getByField(EntityType.ELEMENT, "id", item.id_);
					self.alarmMutedElementSelect = element;

					let elementTypeId = element.elementType.id;
					let alarmConfig = window.em.getAll(EntityType.ALARM_CONFIG);
					let alarmsCfgManual = alarmConfig.filter(e => self.config.alarmTypeManual.includes(e.alarmType.id));
					let aconfitArray = alarmsCfgManual.filter(e => e.elementType && e.elementType.id === elementTypeId && (!e.element || (e.element && e.element.id === element.id)));

					if (Array.isArray(aconfitArray) && aconfitArray.length > 0 && aconfitArray != undefined) {

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

				view = {
					id: "viewTree" + self.uid,
					cols: [
						{
							width: 250,
							hight: 370,
							rows: [
								treeView.getView()
							]
						}
					]
				};

				return view;

			}

			function drawButtons() {

				return {
					cols: [
						{
							rows: [
								{ height: 20 },
								{
									view: "toolbar",
									height: 45,
									borderless: true,
									elements: [
										{ width: 10 },
										{
											id: "button-cancel-" + "-" + self.uid,
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
											id: "button-next-" + "-" + self.uid,
											view: "button",
											css: "dialog-accept",
											width: 130,
											height: 35,
											label: i18n.t("LBL_ACCEPT"),
											on: {
												onItemClick() {

													if (Array.isArray(self.tableAlarmsModal.getSelectedIds()) && self.tableAlarmsModal.getSelectedIds().length > 0) {

														let userData = util.user.getUserData();

														(new InputDialogBox(i18n.t("LBL_DIALOG_CREATE_MANUAL_ALARM"), i18n.t("LBL_ARE_YOU_SURE_CREATE_MANUAL_ALARM"), function(comments) {

															let body = {
																"alarmConfigIds": self.tableAlarmsModal.getSelectedIds(),
																"targetElementIds": [self.alarmMutedElementSelect.id],
																"userId": userData.id,
																"creationComments": comments
															};

															webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.actionEndpoints.createManualAlarm, body).then(function(data) {
																console.log(data);
																$$(self.createView).close();
															});

														})).show();

													} else {

														let ms = new MessageBox(MessageBox.WARN, i18n.t("LBL_COMMAND_NO_ROWS_SELECTED_DESCRIPTION"), null, null, null);
														ms.show();

													}
												}
											}
										},
										{ width: 10 }
									]
								},
								{ height: 10 }
							]
						}
					]
				};

			}

			let view = {
				css: "custom_window",
				height: 545,
				cols: [
					{
						rows: [
							{
								id: "custom_window" + self.uid,
								type: "line",
								height: 535,
								cols: [
									{ width: 6 },
									{
										id: "view" + "createManual" + "loading-" + self.uid,
										rows: [util.ui.getViewDrawLoading()],
										width: 834,
										hidden: true
									},
									{ width: 6 }
								]
							}]
					}
				]
			};

			//			let h = document.documentElement.clientHeight - 400;
			//			let w = document.documentElement.clientWidth - 1000;

			self.createView = util.ui.loadWindow(
				i18n.t("LBL_CREATE_MANUAL_ALARM"),
				view,
				575,
				850,
				true
			);

			$$("view" + "createManual" + "loading-" + self.uid).show();

			let multiView = {

				view: "multiview",
				id: "multi" + self.uid,
				animate: false,
				cells: [
					{
						rows: [
							drawHeader(),
							{
								id: "treeGridView" + self.uid,
								css: "alarm-multiview",
								cols: []
							},
							{
								rows: [{ height: 1 }]
							},
							drawButtons()
						]
					}
				]

			};

			$$("custom_window" + self.uid).addView(multiView, 1);
			$$("treeGridView" + self.uid).addView(drawTree(), 1);
			$$("treeGridView" + self.uid).addView(drawGrid(), 2);
			$$("view" + "createManual" + "loading-" + self.uid).hide();

		}

	}

	refresh() {

		let self = this;

		$$("loading-" + this.uid).show();
		$$(this.uid).hide();

		if (this.requestAlarm) {

			if (this.moduleAction.id === this.config.moduleAction.view) {

				this.requestAlarm = false;

				webix.ajax().get(appConfig.restUrl + util.fillTemplate(this.config.restEndpoints.alarm, { id: self.args.selectedPks[0] })).then(function(dat) {

					if (dat != null) {
						console.log(dat.json());
						self.alarm = dat.json();
						self.update();
					}

				}, function(response) { util.ajax.handleError(response); });
			}

		}
		else {

			this.update();
		}

	}

	update() {

		this.targetElement = window.em.getByField(EntityType.ELEMENT, "id", this.alarm.targetElement.id);
		this.targetElementAlias = this.targetElement.alias;
		this.alarmConfig = window.em.getByField(EntityType.ALARM_CONFIG, "id", this.alarm.alarmConfig.id);
		this.alarmConfigLabelAlias = i18n.t(this.alarmConfig.labelAlias);
		this.alarmLevel = window.em.getByField(EntityType.ALARM_LEVEL, "id", this.alarmConfig.alarmLevel.id);
		this.alarmLevelId = this.alarmLevel.id;
		this.alarmLevelAlias = this.alarmLevel.labelAlias;
		this.targetElementTypeId = this.targetElement.elementType.id;
		this.targetElementTypeAlias = i18n.t(window.em.getByField(EntityType.ELEMENT_TYPE, "id", this.targetElement.elementType.id).labelAlias);

		if (this.alarm.recognized) {

			this.recognized = new Date(this.alarm.recognized);
		}

		if (this.alarm.deactivation) {

			this.alarm.isActive = String(false);
		}
		else {

			this.alarm.isActive = String(true);
		}

		this.alarm.alarmType = this.alarmConfig.alarmType.id;

		let container = $$(this.args.containerId);

		if (container) {

			util.ui.setTitle(container.config.id, this.alarmConfigLabelAlias + " - " + this.targetElementAlias);
		}

		this.drawComponents();

		$$("loading-" + this.uid).hide();
		$$(this.uid).show();

	}

	getViewDrawLoading() {

		return {
			view: "template",
			template: function() {

				let icon = "<img src='img/loading.svg' align='center'>";

				return "<div class='empty-app-inner'>" + icon + "<span class='empty-app-inner-text'>" + i18n.t("LBL_LOADING_DATA") + "</span></div>";
			}
		}
	}

	drawComponents = () => {

		let self = this;
		let alarmWindow = $$(self.args.containerId);

		const getAlarmRecognizedIcon = (alarm) => {

			const NO_RECOGNIZED = "LBL_NO";
			const YES_RECOGNIZED = "LBL_YES";
			const DISAGREE = "LBL_DISAGREE";

			let recognize = alarm.recognized !== undefined && alarm.recognized !== null ? true : false;
			let icon = recognize ? "./img/grid/bell_recognized.png" : "./img/grid/bell_no_recognized.png";
			let vlabel = (recognize ? YES_RECOGNIZED : NO_RECOGNIZED) + (alarm.disagree ? " " + DISAGREE : "");
			let user = alarm.recognizedUser !== undefined && alarm.recognizedUser !== null ? alarm.recognizedUser : null;
			let userName = user !== null ? window.em.getByField(EntityType.USER, "id", user.id).name : "";
			let userLabel = userName + " " + new Date(this.alarm.recognized).toLocaleString(i18n.getLocale(), FORMAT_NUMERIC_DATES);

			let view;

			view = {
				cols: [
					{
						width: 34,
						borderless: true,
						height: 27,
						template: `<img style="max-width:100%; width:auto; width: -moz-available; width: -webkit-fill-available; height:auto; padding-right: 10px;" src="${icon}">`,
					},
					{
						width: 130,
						view: "label",
						label: `<span class="form-text-font">${i18n.t(vlabel)}</span>`,
						align: "left",
						height: 27
					},
					{
						width: 230,
						view: "label",
						label: user !== null ? `<span class="alarm-view-user_label">${userLabel}</span>` : "",
						align: "left",
						hidden: user !== null ? false : true,
						height: 27
					}
				]
			};

			return view;
		};

		const getAlarmDeactivationIcon = () => {

			const NO_DEACTIVATION = "LBL_ACTIVE";
			const YES_DEACTIVATION = "LBL_ICON_ALARM_INACTIVE";

			let deactivation = this.alarm.deactivation !== undefined && this.alarm.deactivation !== null ? true : false;
			let icon = deactivation ? "./img/grid/alarm_inactive.png" : "./img/grid/alarm_active.png";
			let vlabel = deactivation ? YES_DEACTIVATION : NO_DEACTIVATION;
			let deactivationDateLabel = deactivation ? new Date(this.alarm.deactivation).toLocaleString(i18n.getLocale(), FORMAT_NUMERIC_DATES) : "";

			let view;

			view = {
				cols: [
					{
						width: 34,
						borderless: true,
						height: 27,
						template: `<img style="max-width:100%; width:auto; width: -moz-available; width: -webkit-fill-available; height:auto; padding-right: 10px;" src="${icon}">`,
					},
					{
						width: 130,
						view: "label",
						label: `<span class="form-text-font">${i18n.t(vlabel)}</span>`,
						align: "left",
						height: 27
					},
					{
						width: 230,
						view: "label",
						label: `<span class="alarm-view-user_label">${deactivationDateLabel}</span>`,
						align: "left",
						height: 27
					}
				]
			};

			return view;
		};

		const getAlarmTypeIcon = (typeId) => {

			let isManual = this.config.alarmTypeAuto.includes(typeId) ? false : true;
			let icon = isManual ? "./img/grid/manual.png" : "./img/grid/auto.png";
			let vlabel = isManual ? "LBL_ALARM_MANUAL" : "LBL_ALARM_AUTO";
			let numType = typeId.charAt(typeId.length - 1);
			let view;

			view = {
				cols: [
					{
						width: 34,
						borderless: true,
						height: 27,
						template: `<img style="max-width:100%; width:auto; width: -moz-available; width: -webkit-fill-available; height:auto; padding-right: 10px;" src="${icon}">`,
					},
					{
						width: 365,
						view: "label",
						label: `<span class="form-text-font">${i18n.t("LBL_ALARM_TYPE")} ${numType}, ${i18n.t(vlabel)}</span>`,
						align: "left",
						height: 27
					},
					{}
				]
			};
			return view;

		}

		const getAlarmLevelIcon = (levelId) => {

			let isCritical = this.config.levels.critical.includes(levelId) ? true : false;
			let isWarn = this.config.levels.warn.includes(levelId) ? true : false;
			let isInfo = this.config.levels.info.includes(levelId) ? true : false;

			let iconInfo = "./img/grid/info.png";
			let iconWarn = "./img/grid/warn.png";
			let iconCritical = "./img/grid/critical.png";
			let labelInfo = "LBL_ALARM_LEVEL_INFO";
			let labelWarn = "LBL_ALARM_LEVEL_WARN";
			let labelCritical = "LBL_ALARM_LEVEL_CRITICAL";
			let icon = isCritical ? iconCritical : isWarn ? iconWarn : isInfo ? iconInfo : "";
			let vlabel = isCritical ? labelCritical : isWarn ? labelWarn : isInfo ? labelInfo : "";
			let view;

			view = {
				cols: [
					{
						width: 34,
						borderless: true,
						height: 27,
						template: `<img style="max-width:100%; width:auto; width: -moz-available; width: -webkit-fill-available; height:auto; padding-right: 10px;" src="${icon}">`,
					},
					{
						width: 365,
						view: "label",
						label: `<span class="form-text-font">${i18n.t(vlabel)}</span>`,
						align: "left",
						height: 27
					},
					{}
				]
			};
			return view;
		}

		function paintFieldRecognizedComments(alarm) {

			const LABEL_PREFIX = "LBL_";
			let recognizedComments = {};

			let label = alarm.recognizedComments ? (alarm.recognizedComments.startsWith(LABEL_PREFIX) ? i18n.t(alarm.recognizedComments) : alarm.recognizedComments) : undefined;

			recognizedComments = {
				css: { "box-sizing": "border-box", "border-left ": "1px solid #e2e2e2", "border-right": "1px solid #e2e2e2" },
				hidden: !label,
				rows: [
					{ height: 5 },
					{
						cols: [
							{ width: 42 },
							{
								height: 55,
								width: 626,
								scroll: "auto",
								css: "alarm-view-rectangle-back",
								template: `<div class="alarm-view-comments">${label}</div>`
							},
							{ width: 42 }
						]
					}
				]
			};

			return recognizedComments;
		}

		let labelAlarm = this.alarmConfigLabelAlias;
		let container = $$(self.args.containerId);
		let ellipsis = undefined;

		if (container) {

			ellipsis = `style='display: inline-block; max-width: ${container.$width * 0.5}px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis'`;
		}

		let viewMain = {
			id: this.uid,
			cols: [
				{ width: 1 },
				{
					width: 744,
					rows: [
						{
							cols: [
								{
									view: "label",
									label: `<div class="title"><i style="color:#33425e; font-size: 2em" class="mdi mdi-view-headline"></i></div>`,
									align: "center",
									height: 50,
									width: 56
								},
								{
									view: "label",
									css: "view-header-details-text",
									label: `<span class="view-header-details-text">${i18n.t("LBL_DETAILS_DESCRIPTION")}</span>`,
									align: "left",
									height: 50
								},
								{}
							]
						},
						{
							css: { "box-sizing": "border-box", "border-top": "1px solid #e2e2e2", "border-left ": "1px solid #e2e2e2", "border-right": "1px solid #e2e2e2" },
							cols: [
								{ width: 42 },
								{
									width: 194,
									rows: [
										{ height: 16 },
										{
											view: "label",
											label: i18n.t("LBL_ALARM_ACTIVATION"),
											align: "left",
											height: 20
										},
										{ height: 24 },
										{
											view: "label",
											label: i18n.t("LBL_ALARM_DEACTIVATION"),
											align: "left",
											hidden: (this.alarm.deactivation !== undefined && this.alarm.deactivation !== null) ? false : true,
											height: 20
										},
										{
											height: 24,
											hidden: (this.alarm.deactivation !== undefined && this.alarm.deactivation !== null) ? false : true
										},
										{
											view: "label",
											label: i18n.t("LBL_DESCRIPTION"),
											align: "left",
											height: 20
										},
										{ height: 24 },
										{
											view: "label",
											label: i18n.t("LBL_ELEMENT_TYPE_ALIAS"),
											align: "left",
											height: 20
										},
										{ height: 24 },
										{
											view: "label",
											label: i18n.t("LBL_ELEMENT_ALIAS"),
											align: "left",
											height: 20
										},
										{ height: 24 },
										{
											view: "label",
											label: i18n.t("LBL_ALARM_LEVEL"),
											align: "left",
											height: 20
										},
										{ height: 24 },
										{
											view: "label",
											label: i18n.t("LBL_ALARM_RECOGNIZED"),
											align: "left",
											height: 27
										},
									]
								},
								{
									width: 400,
									rows: [
										{ height: 16 },
										{
											view: "label",
											label: `<span class="form-text-font">${new Date(this.alarm.activation).toLocaleString(i18n.getLocale(), FORMAT_NUMERIC_DATES)}</span>`,
											align: "left",
											height: 20
										},
										{ height: 24 },
										{
											view: "label",
											label: `<span class="form-text-font">${new Date(this.alarm.deactivation).toLocaleString(i18n.getLocale(), FORMAT_NUMERIC_DATES)}</span>`,
											align: "left",
											hidden: (this.alarm.deactivation !== undefined && this.alarm.deactivation !== null) ? false : true,
											height: 20
										},
										{
											height: 24,
											hidden: (this.alarm.deactivation !== undefined && this.alarm.deactivation !== null) ? false : true
										},
										{
											cols: [
												{
													id: "label-alarm-" + self.uid,
													view: "label",
													label: `<span id="labelAlarm" class="form-text-font" ${ellipsis}>${labelAlarm}</span>`,
													align: "left",
													height: 20,
													tooltip: function(obj) {

														let textWidth;
														let nodeValue = $$("label-alarm-" + self.uid).getNode().querySelector("#labelAlarm");
														let allowedSpace = $$("label-alarm-" + self.uid).getNode().clientWidth;

														if (nodeValue != null) {

															let elementStyle = window.getComputedStyle(nodeValue);
															let fontSize = elementStyle.getPropertyValue('font-size');
															let fontFamily = elementStyle.getPropertyValue('font-family');
															let letterSpacing = elementStyle.getPropertyValue('letter-spacing');

															textWidth = util.ui.calculateTextWidth(labelAlarm, fontFamily, fontSize, letterSpacing);
														}

														if (textWidth > allowedSpace * 0.95) {

															return labelAlarm;

														} else {

															return "";
														}
													}
												},
												{
													view: "icon",
													width: 24,
													height: 24,
													css: "alarm-module-window-edit-button",
													icon: "mdi mdi-pencil",
													value: i18n.t("LBL_EDIT_ALARM_CONFIG"),
													hidden: !self.editAlarmConfigsPermission,
													tooltip: true,
													on: {
														onItemClick: function() {

															let moduleActionId = self.config.action.alarmConfig;
															let mod = util.module.getFromModuleAction(moduleActionId);
															let moduleAction = { id: moduleActionId };
															let args = { selectedPks: [self.alarmConfig.id] };

															util.module.open(mod, moduleAction, VIEW_TYPE_WINDOW, null, args);
														}
													}
												}
											]
										},
										{ height: 24 },
										{
											view: "label",
											label: `<span class="form-text-font">${this.targetElementTypeAlias}</span>`,
											align: "left",
											height: 20
										},
										{ height: 24 },
										{
											view: "label",
											label: `<span class="form-text-font">${this.targetElementAlias}</span>`,
											align: "left",
											height: 20
										},
										{ height: 24 },
										{
											view: "label",
											label: `<span class="form-text-font">${i18n.t(this.alarmLevelAlias)}</span>`,
											align: "left",
											height: 20
										},
										{ height: 24 },
										getAlarmRecognizedIcon(this.alarm)
									]
								}
							]
						},
						paintFieldRecognizedComments(this.alarm),
						{
							css: { "box-sizing": "border-box", "border-bottom": "1px solid #e2e2e2", "border-left ": "1px solid #e2e2e2", "border-right": "1px solid #e2e2e2" },
							height: 182,
							rows: [
								{
									cols: [
										{ width: 42 },
										{
											width: 194,
											rows: [
												{ height: 24 },
												{
													view: "label",
													label: i18n.t("LBL_CRITICAL_LEVEL"),
													align: "left",
													height: 27
												},
												{ height: 24 },
												{
													view: "label",
													label: i18n.t("LBL_ALARM_STATE"),
													align: "left",
													height: 27
												},
											]
										},
										{
											width: 400,
											rows: [
												{ height: 24 },
												getAlarmLevelIcon(this.alarmLevelId),
												{ height: 24 },
												getAlarmDeactivationIcon()
											]
										}
									]
								},
								{ height: 24 },
								{
									cols: [
										{ width: 42 },
										{
											view: "label",
											label: i18n.t("LBL_MORE_INFO"),
											tooltip: i18n.t("LBL_MORE_INFO_DESCRIPTION"),
											hidden: !getBackOffice(),
											css: "label-window-alarms",
											align: "left",
											height: 27,
											on: {
												onItemClick: function() {
													let mod = util.module.getFromModuleAction(BackOfficeModule.Config.MODULE_ACTION_OPEN_ID);

													let moduleAction = { id: BackOfficeModule.Config.MODULE_ACTION_OPEN_ID };

													let args = {
														backOffice: getBackOffice()
													};

													util.module.open(mod, moduleAction, VIEW_TYPE_WINDOW, null, args);

												}
											}
										}
									]

								}]
						},
						{ height: 20 },
						{
							cols: [
								{},
								{
									view: "button",
									css: "dialog-cancel",
									height: 41,
									width: 130,
									label: i18n.t("LBL_CLOSE"),
									on: {

										onItemClick() {

											$$(alarmWindow).close();
										}
									}
								}
							]
						}
					]
				},
				{ width: 1 }
			]
		};

		webix.ui(viewMain, $$(this.uid));
		
		let contentHeight = $$(this.uid).$height;
		let winHeight = $$(this.uid).getTopParentView().$height;

		if ((contentHeight / winHeight) > 0.9) {

			let winWidth = $$(this.uid).getTopParentView().$width;
			util.ui.resizeWindow(self.args.containerId, winWidth, winHeight * 1.1);
		}

		function getBackOffice() {

			let backOffice;

			let elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", self.targetElement.elementType.id);

			if (elementType) {

				backOffice = self.config.backOffice ? self.config.backOffice : undefined;
				backOffice = elementType.alarmBackOffice ? JSON.parse(elementType.alarmBackOffice) : backOffice;

				if (backOffice && self.alarm.id) {

					let splitStr = self.alarm.id.split(":");
					backOffice.filterColumnValue = splitStr[1];
				}
			}


			return backOffice;
		}
	}

}
