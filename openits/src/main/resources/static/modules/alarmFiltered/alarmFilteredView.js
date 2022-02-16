import { util } from "../../lib/util.js";

export default class AlarmFilteredView {

	constructor(parent) {

		this.parent = parent;
		this.uid = webix.uid();
	}

	show() {

		let self = this;
		let view = {};

		view = {
			id: "view-" + self.uid,
			cols: [
				{
					id: "view-loading-" + self.uid,
					rows: [util.ui.getViewDrawLoading()]
				},
				{
					id: "view-content-" + self.uid,
					hidden: true
				}
			]
		};

		loadData();

		function loadData() {

			let alarm, alarmFiltered, alarmConfig, element;

			webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.parent.config.restEndpoints.alarmMuted, { id: self.parent.args.selectedPks[0] })).then(function(alarmFilteredResponse) {

				if (alarmFilteredResponse != null) {

					alarmFiltered = alarmFilteredResponse.json();
					alarmConfig = window.em.getByField(EntityType.ALARM_CONFIG, "id", alarmFiltered.alarmConfig.id);
					element = window.em.getByField(EntityType.ELEMENT, "id", alarmFiltered.element.id);

					webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.parent.config.restEndpoints.elementAlarms, { id: element.id })).then(function(alarmResponse) {

						if (alarmResponse != null) {

							alarm = alarmResponse.json()[0];
							webix.ui(drawContent(), $$("view-content-" + self.uid));
							$$("view-loading-" + self.uid).hide();
							util.ui.setTitle(alarmFilteredViewWindow, i18n.t(alarmConfig.labelAlias) + " - " + element.alias, element.alias);
						}
					});
				}
			});

			function drawContent() {

				let viewContent = {
					cols: [
						{
							rows: [
								{ height: 6 },
								{
									cols: [
										{ width: 6 },
										{
											rows: [drawMain()],
										},
										{ width: 6 }
									]
								}
							]
						}
					]
				};

				function drawMain() {

					let viewMain = {
						cols: [
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
												css: "alarm-view-header-details-text",
												label: `<span class="alarm-view-header-details-text">${i18n.t("LBL_DETAILS_DESCRIPTION")}</span>`,
												align: "left",
												height: 50
											},
											{}
										]
									}
									,
									{
										//css: "alarm-borders-top",
										css: { "border": "1px solid #e2e2e2;", "box-sizing": "border-box" },
										cols: [
											{ width: 42 },
											{
												width: 194,
												rows: [
													{ height: 16 },
													{
														view: "label",
														css: "alarm-view-title-label",
														label: `<div><span class="alarm-view-title-label">${i18n.t("LBL_FILTERED_UNTIL")}</span></div>`,
														align: "left",
														height: 20
													},
													{ height: 24 },
													{
														view: "label",
														css: "alarm-view-title-label",
														label: `<div><span class="alarm-view-title-label">${i18n.t("LBL_ALARM_CONFIG_ALIAS")}</span></div>`,
														align: "left",
														height: 20
													},
													{ height: 24 },
													{
														view: "label",
														css: "alarm-view-title-label",
														label: `<div><span class="alarm-view-title-label">${i18n.t("LBL_ELEMENT_TYPE_ALIAS")}</span></div>`,
														align: "left",
														height: 20
													},
													{ height: 24 },
													{
														view: "label",
														css: "alarm-view-title-label",
														label: `<div><span class="alarm-view-title-label">${i18n.t("LBL_ELEMENT_ALIAS")}</span></div>`,
														align: "left",
														height: 20
													},
													{ height: 24 },
													{
														view: "label",
														css: "alarm-view-title-label",
														label: `<div><span class="alarm-view-title-label">${i18n.t("LBL_ALARM_LEVEL")}</span></div>`,
														align: "left",
														height: 20
													},
													{ height: 24 },
													{
														view: "label",
														css: "alarm-view-title-label",
														label: `<div><span class="alarm-view-title-label">${i18n.t("LBL_ALARM_TYPE")}</span></div>`,
														align: "left",
														height: 27
													},
													{ height: 24 },
													{
														view: "label",
														css: "alarm-view-title-label",
														label: `<div><span class="alarm-view-title-label">${i18n.t("LBL_ALARM_FILTER_HAS_ALARMS")}</span></div>`,
														align: "left",
														height: 27
													},
													{ height: 24 },
													{
														view: "label",
														css: "alarm-view-title-label",
														label: `<div><span class="alarm-view-title-label">${i18n.t("LBL_USER")}</span></div>`,
														align: "left",
														height: 27
													},
													{ height: 24 },
													{
														view: "label",
														css: "alarm-view-title-label",
														label: `<div><span class="alarm-view-title-label">${i18n.t("LBL_COMMENT")}</span></div>`,
														align: "left",
														hidden: (!alarmFiltered.comment) ? true : false,
														height: 27
													},
													{ height: 24 }
												]
											},
											{
												width: 400,
												rows: [
													{ height: 16 },
													{
														view: "label",
														label: `<span class="alarm-view-text">${new Date(alarmFiltered.filteredUntil).toLocaleString(i18n.getLocale(), FORMAT_NUMERIC_DATES)}</span>`,
														align: "left",
														height: 20
													},
													{ height: 24 },
													{
														view: "label",
														label: `<span id="labelAlarm" class="alarm-view-text">${i18n.t(alarmConfig.labelAlias)}</span>`,
														align: "left",
														height: 20
													},
													{ height: 24 },
													{
														view: "label",
														label: `<span class="alarm-view-text">${i18n.t(alarmConfig.elementType.labelAlias)}</span>`,
														align: "left",
														height: 20
													},
													{ height: 24 },
													{
														view: "label",
														label: `<span class="alarm-view-text">${element.alias}</span>`,
														align: "left",
														height: 20
													},
													{ height: 24 },
													{
														view: "label",
														label: `<span class="alarm-view-text">${i18n.t(alarmConfig.alarmLevel.labelAlias)}</span>`,
														align: "left",
														height: 20
													},
													{ height: 24 },
													getAlarmTypeIcon(alarm),
													{ height: 24 },
													getAlarmFilteredIcon(alarm),
													{ height: 24 },
													{
														view: "label",
														label: `<span class="alarm-view-text">${alarmFiltered.user.name}</span>`,
														align: "left",
														height: 20
													},
													{ height: 24 },
													paintFieldFilteredComments(alarm),
													{ height: 24 }
												]
											}
										]
									},
									{ height: 20 },
									{
										cols: [
											{ width: 600 },
											{
												view: "button",
												css: "dialog-cancel",
												height: 41,
												width: 130,
												label: i18n.t("LBL_CLOSE"),
												on: {
													onItemClick() {

														$$(alarmFilteredViewWindow).close();
													}
												}
											},
											{}
										]
									},
									{ height: 10 }
								]
							}
						]
					};


					function getAlarmTypeIcon(alarm) {

						const DISABLED = "LBL_ALARM_FILTER_DISABLED_ALARM";
						const MUTED = "LBL_ALARM_FILTER_MUTED";

						let disabled = (alarmFiltered.alarmDisabled) ? true : false;
						let icon = disabled ? "./img/grid/alarm_disabled.png" : "./img/grid/no-sound.png";
						let vlabel = disabled ? DISABLED : MUTED;

						let alarmRecognized;

						alarmRecognized = {
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
									label: `<span class="alarm-view-text">${i18n.t(vlabel)}</span>`,
									align: "left",
									height: 27
								}
							]
						};

						return alarmRecognized;
					};

					function getAlarmFilteredIcon(alarm) {

						const NO_FILTERED = "LBL_NO";
						const YES_FILTERED = "LBL_YES";

						let filtered = (alarm.filteredUntil) ? true : false;
						let vlabel = filtered ? YES_FILTERED : NO_FILTERED;

						let view;

						view = {
							cols: [
								{
									width: 130,
									view: "label",
									label: `<span class="alarm-view-text">${i18n.t(vlabel)}</span>`,
									align: "left",
									height: 27
								}
							]
						};

						if (filtered) {

							view.cols.unshift({
								width: 34,
								borderless: true,
								height: 27,
								template: `<img style="max-width:100%; width:auto; width: -moz-available; width: -webkit-fill-available; height:auto; padding-right: 10px;" src="./img/grid/alarm_filtered_active.png">`,
							});

						}
						
						return view;

					};

					function paintFieldFilteredComments(alarm) {

						const LABEL_PREFIX = "LBL_";
						let label = "";
						let filteredComments = {};

						if (alarmFiltered.comment) {

							label = alarmFiltered.comment.startsWith(LABEL_PREFIX) ? i18n.t(alarmFiltered.comment) : alarmFiltered.comment;
						}

						filteredComments = {
							hidden: !label,
							rows: [
								{
									height: 45,
									width: 450,
									scroll: "auto",
									css: "alarm-view-rectangle-back",
									template: `<div class="alarm-view-comments">${label}</div>`
								}
							]
						};

						return filteredComments;
					}

					return viewMain;

				}

				return viewContent;
			}

		}

		let alarmFilteredViewWindow = util.ui.loadWindow(
			i18n.t("LBL_FILTERED_ALARM"),
			view,
			605,
			755,
			true
		);


	}
}