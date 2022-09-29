import { util } from "../../lib/util.js";
import GenericControlPanel from "./genericControlPanel.js";

export default class AlarmControlPanel extends GenericControlPanel {

	constructor(config) {
		super(config);
		this.mainView = this.createView();
	}

	show() {

		if (!this.hidden()) {

			return;
		}

		this.mainView.show();
	}

	hide() {

		this.mainView.hide();
	}

	hidden() {

		return this.mainView.config.hidden;
	}

	createView() {

		let self = this;
		let panelWidth = 400;
		let cssLightMode = (window.lightMode) ? true : false;
		let cssStyleBox = (cssLightMode) ? "box-info light-mode" : "box-info";
		let cssStyleBoxComponent = (cssLightMode) ? "box-component light-component" : "box-component";
		let cssStyleBoxSeparator = (cssLightMode) ? "box-separator light-box-separator" : "box-separator";
		let cssStyleLightButtonToolbar = (cssLightMode) ? "control-panel-button-toolbar light-mode" : "control-panel-button-toolbar";
		let cssStyleControlPanel = (cssLightMode) ? "control-panel-main light-mode" : "control-panel-main";
		let lightMode = (cssLightMode) ? "light-templates" : "";
		
		let recent_alarms = {
			cols: [
				{ width: 20 },
				{
					view: "list",
					id: "alarm-control-panel-recent-list",
					css: "control-panel-list",
					height: 550,
					select: false,
					type: {

						template: function(obj) {
							
							let stateAlarm = obj.removed ? "alarm-state-deactive" : "alarm-state-active";

							if (obj.removed) {

								return `<div class="${stateAlarm}"></div>
										<div class="text">
									  		<span class="first-line" style="text-decoration:line-through;">${obj.alias} </span>
									  		<span class="second-line">${util.toLocaleString(obj.updated / 1000)} : ${obj.description}</span>
										</div>`;
							}
							else {

								return `<div class="${stateAlarm}"></div>
										<div class="text">
									  		<span class="first-line">${obj.alias} </span>
									  		<span class="second-line">${util.toLocaleString(obj.updated / 1000)} : ${obj.description}</span>
										</div>`;
							}

						},
						height: 66
					}
				},
				{ width: 20 }
			]

		};

		let alarm_actions = {
			view: "toolbar",
			css: cssStyleLightButtonToolbar,
			height: 50,
			elements: [
				{},
				{
					view: "button",
					width: 180,
					label: i18n.t("LBL_ALARMS_RECOGNIZE_ALL"),
					tooltip: i18n.t("LBL_ALARMS_RECOGNIZE_ALL_DESCRIPTION"),
					type: "icon",
					icon: "mdi mdi-check-bold",
					on: {

						onItemClick() {

							self.doAction(self.config.module.alarm.id, self.config.module.alarm.action.recognizeAll);
							self.hide();
						}
					}
				},
				//{ width: 2 },
				{
					view: "button",
					width: 180,
					label: i18n.t("LBL_ALARMS_MONITOR"),
					tooltip: i18n.t("LBL_ALARMS_MONITOR_DESCRIPTION"),
					type: "icon",
					icon: "mdi mdi-bell",
					on: {

						onItemClick() {

							self.openModule(self.config.module.alarmMonitor.id);
							self.hide();
						}
					}
				},
				{}
			],
			borderless: true
		};
		
		let box_stats = {
			css: cssStyleBox,
			rows: [
				{
					view: "template",
					css: cssStyleBoxComponent,
					height: 33,
					template: function() {
						return `<div class="box-title ${lightMode}"><span>${i18n.t("LBL_ALARMS_STATS")}</span></div>`;
					}
				},
				{
					cols: [
						{
							//width: "33%",
							rows: [
								{
									view: "template",
									id: "alarm-control-panel-box-active",
									css: cssStyleBoxComponent,
									height: 50,
									template: function() {
										return `<span class="box-value ${lightMode}">${9999}</span>`;
									}
								},
								{
									view: "template",
									css: cssStyleBoxComponent,
									height: 20,
									template: function() {
										return `<span class="box-description ${lightMode}">${i18n.t("LBL_ALARMS_STATS_ACTIVE")}</span>`;
									}
								}
							]
						},
						{
							view: "template",
							css: cssStyleBoxSeparator,
							width: 5,
							borderless: true,
							template: function() {
								return `<span></span>`
							}
						},
						{
							//width: "33%",
							rows: [
								{
									view: "template",
									id: "alarm-control-panel-box-no-recognized",
									css: cssStyleBoxComponent,
									height: 50,
									template: function() {
										return `<span class="box-value ${lightMode}">${9999}</span>`;
									}
								},
								{
									view: "template",
									css: cssStyleBoxComponent,
									height: 20,
									template: function() {
										return `<span class="box-description ${lightMode}">${i18n.t("LBL_ALARMS_STATS_NO_RECOGNIZED")}</span>`;
									}
								}
							]
						},
						{
							view: "template",
							css: cssStyleBoxSeparator,
							width: 5,
							borderless: true,
							template: function() {
								return `<span></span>`
							}
						},
						{
							//width: "33%",
							rows: [
								{
									view: "template",
									id: "alarm-control-panel-box-filtered",
									css: cssStyleBoxComponent,
									height: 50,
									template: function() {
										return `<span class="box-value ${lightMode}">${9999}</span>`;
									}
								},
								{
									view: "template",
									css: cssStyleBoxComponent,
									height: 20,
									template: function() {
										return `<span class="box-description ${lightMode}">${i18n.t("LBL_ALARMS_STATS_FILTERED")}</span>`;
									}
								}
							]
						}
					]
				}

			]
		};

		return webix.ui({
			view: "popup",
			id: "alarmControlPanel",
			move: true,
			css: cssStyleControlPanel,
			//hidden: true,
			height: document.documentElement.clientHeight - 147,
			width: panelWidth,
			body: {
				borderless: true,
				rows: [
					//					{
					//						view: "label",
					//						css: "control-panel-title",
					//						label: `<span class="title-text">${i18n.t("LBL_ALARMS_INFORMATION")}</span>`,
					//						borderless: true 
					//					},
					{ height: 30 },
					{
						height: 115,
						cols: [
							{ width: 20 },
							box_stats,
							{ width: 20 }
						]
					},
					{ height: 20 },
					alarm_actions,
					{ height: 20 },
					{
						height: 20,
						borderless: true,
						cols: [
							{ width: 12 },
							{
								view: "template",
								borderless: true,
								css: "section-title",
								template: function() {
									return `<div>
												<h2 class="section-title-text ${lightMode}">${i18n.t("LBL_RECENT_ALARMS")}</h2>
											</div>`;
								}
							},
							{
								view: "template",
								borderless: true,
								css: "section-title",
								template: function() {
									return `<div class="section-title-divider"></div>`;
								}
							},
							{ width: 12 },
						]
					},
					{ height: 20 },
					recent_alarms,
					{}
				]
			},
			position: function(state) {
				state.left = document.documentElement.clientWidth - panelWidth - 20;
				state.top = 89;
			},
			on: {
				onBeforeShow: function() {

					this.config.height = document.documentElement.clientHeight - 139;
					this.adjust();
					self.updateData();
				}
			}
		});
	}

	updateData() {

		let alarmService = window.sm.getService(Services.ALARM_SERVICE);
		let lightMode = (window.lightMode) ? "light-mode" : "";

		if (alarmService) {

			let stats = alarmService.getData().stats;
			let latest = alarmService.getData().latest;

			if (stats) {

				if (stats.activeAlarms > -1) {

					$$("alarm-control-panel-box-active").setHTML(`<span class="box-value ${lightMode}">${stats.activeAlarms}</span>`);
					$$("alarm-control-panel-box-active").refresh();

					$$("icon-alarm-service").data.badge = stats.activeAlarms;
					$$("icon-alarm-service").refresh();
				}

				if (stats.noRecognizedAlarms > -1) {

					$$("alarm-control-panel-box-no-recognized").setHTML(`<span class="box-value ${lightMode}">${stats.noRecognizedAlarms}</span>`);
					$$("alarm-control-panel-box-no-recognized").refresh();
				}

				if (stats.filteredAlarms > -1) {

					$$("alarm-control-panel-box-filtered").setHTML(`<span class="box-value ${lightMode}">${stats.filteredAlarms}</span>`);
					$$("alarm-control-panel-box-filtered").refresh();
				}
			}

			if (Array.isArray(latest) && latest.length > 0) {

				$$("alarm-control-panel-recent-list").clearAll();
				$$("alarm-control-panel-recent-list").parse(latest);
			}
		}

	}
}
