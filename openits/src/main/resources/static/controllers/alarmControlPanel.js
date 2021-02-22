import {util} from "../lib/util.js";

export default class AlarmControlPanel {

	constructor() {

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
							
							let stateAlarm = "alarm-state-active";
							 
							if (obj.deactivation) {
								
								stateAlarm = "alarm-state-deactive";
							}
							
							if (obj.deactivation) {
								
								return `<div class="${stateAlarm}"></div>
										<div class="text">
									  		<span class="first-line" style="text-decoration:line-through;">${obj.alarmConfig.alias} </span>
									  		<span class="second-line">${obj.dateFormatted} : ${obj.equipment}</span>
										</div>`;	
							}
							else {
								
								return `<div class="${stateAlarm}"></div>
										<div class="text">
									  		<span class="first-line">${obj.alarmConfig.alias} </span>
									  		<span class="second-line">${obj.dateFormatted} : ${obj.equipment}</span>
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
			css: "control-panel-button-toolbar",
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
							
							util.ui.notImplementedMessage();
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
							
							util.ui.notImplementedMessage();
						}
					}
				},
				{}
			],
			borderless: true 
		};
		
		let box_stats = {
			css: "box-info",
			rows: [
				{
					view: "template",
					css: "box-component",
					height: 33,
					template: function() {
						return `<div class="box-title"><span>${i18n.t("LBL_ALARMS_STATS")}</span></div>`;
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
									css: "box-component", 
									height: 50,
									template: function() {
										return `<span class="box-value">${9999}</span>`;
									}
								},
								{
									view: "template",
									css: "box-component",
									height: 20, 
									template: function() {
										return `<span class="box-description">${i18n.t("LBL_ALARMS_STATS_ACTIVE")}</span>`;
									}
								}
							]
						},
						{
							view: "template",
							css: "box-separator",
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
									css: "box-component",
									height: 50,
									template: function() {
										return `<span class="box-value">${9999}</span>`;
									}
								},
								{
									view: "template", 
									css: "box-component",
									height: 20,
									template: function() {
										return `<span class="box-description">${i18n.t("LBL_ALARMS_STATS_NO_RECOGNIZED")}</span>`;
									}
								}
							]
						},
						{
							view: "template",
							css: "box-separator",
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
									id: "alarm-control-panel-box-muted",
									css: "box-component", 
									height: 50,
									template: function() {
										return `<span class="box-value">${9999}</span>`;
									}
								},
								{
									view: "template",
									css: "box-component",
									height: 20, 
									template: function() {
										return `<span class="box-description">${i18n.t("LBL_ALARMS_STATS_MUTED")}</span>`;
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
			css: "control-panel-main",
			hidden: true,
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
					{ height: 20},
					alarm_actions,
					{ height: 20},
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
												<h2 class="section-title-text">${i18n.t("LBL_RECENT_ALARMS")}</h2>
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
					{ height: 20},
					recent_alarms,
					{}
				]	
			}, 
			position: function(state) {
				state.left = document.documentElement.clientWidth - panelWidth - 20;
				state.top = 96;
			},
			on: {
				onBeforeShow: function() {
					
					this.config.height = document.documentElement.clientHeight - 147; 
					this.adjust();
					self.updateData();
				}
			}
		});
	}
	
	updateData() {
		
		let alarmService = window.sm.getService(Services.ALARM_SERVICE);

		if (alarmService) {
		
			let stats = alarmService.getData().stats;
			let latest = alarmService.getData().latest;

			if (stats) {
				
				if (stats.activeAlarms) {
					
					$$("alarm-control-panel-box-active").setHTML(`<span class="box-value">${stats.activeAlarms}</span>`);
					$$("alarm-control-panel-box-active").refresh();
					
					$$("icon-alarm-service").data.badge = stats.activeAlarms;
					$$("icon-alarm-service").refresh();
				}
				
				if (stats.noRecognizedAlarms) {
				
					$$("alarm-control-panel-box-no-recognized").setHTML(`<span class="box-value">${stats.noRecognizedAlarms}</span>`);
					$$("alarm-control-panel-box-no-recognized").refresh();	
				}
				
				if (stats.mutedAlarms) {
				
					$$("alarm-control-panel-box-muted").setHTML(`<span class="box-value">${stats.mutedAlarms}</span>`);
					$$("alarm-control-panel-box-muted").refresh();	
				}
			}
			
			if (Array.isArray(latest) && latest.length > 0) {
				
				$$("alarm-control-panel-recent-list").clearAll();
				$$("alarm-control-panel-recent-list").parse(latest);
			}			
		}
		
	}
	
	viewAlarms() {
		
		util.ui.notImplementedMessage();
	}
	
	viewConfig() {
		
		util.ui.notImplementedMessage();
	}
}
