import { util } from "../../lib/util.js";
import GenericControlPanel from "./genericControlPanel.js";

export default class UserControlPanel extends GenericControlPanel {

	constructor(config) {

		super(config);
		this.mainView = this.createView();
		this.viewIncidentPermission = util.module.isAllowedPermission(config.module.user.viewIncidentPermission);
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

	logout() {

		webix.modalbox({
			title: i18n.t("LBL_CLOSE_SESSION"),
			buttons: [i18n.t("LBL_YES"), i18n.t("LBL_NO")],
			width: 400,
			text: i18n.t("LBL_CONFIR_CLOSE_SESSION"),
			callback: function(result) {

				if (result === "0") {

					let response = webix.ajax().sync().post(appConfig.restUrl + "auth/logout");

					if (response != undefined) {

						let login = JSON.parse(response.responseText);

						if (login.access === "LOGOUT") {

							webix.storage.session.remove("loginSession");
							webix.storage.session.remove("app_user_language");
							webix.storage.session.remove("app_user_name");
							webix.storage.session.remove("app_user_permissions");
							webix.storage.session.remove(SESSION_STORAGE_USER_DATA);
							window.location = "./login.html";
						}
					}
				}
			}
		});
	}

	createView() {

		// for using the object inside callbacks
		let self = this;
		let panelWidth = 400;
		let userData = webix.storage.session.get(SESSION_STORAGE_USER_DATA);

		let pendingTasksView = {
			view: "toolbar",
			css: "control-panel-button-toolbar",
			height: 150,
			elements: [
				{},
				{
					rows: [
						{
							view: "button",
							id: "user-control-panel-alarms",
							width: 350,
							height: 50,
							tooltip: i18n.t("LBL_ALARMS_RECOGNIZE"),
							type: "icon",
							icon: "webix_icon_btn mdi mdi-bell",
							hidden: true,
							on: {

								onItemClick() {

									self.openModule(self.config.module.alarmMonitor.id);
									self.hide();
								}
							}
						},
						{
							view: "button",
							id: "user-control-panel-incident",
							width: 350,
							height: 50,
							tooltip: i18n.t("LBL_INCIDENTS_PENDING"),
							type: "icon",
							icon: "webix_icon_btn mdi mdi-alert",
							hidden: true,
							on: {

								onItemClick() {

									self.openModule(self.config.module.incidentReportModule.id);
									self.hide();
								}
							}
						}
					]
				},
				{}
			],
			borderless: true
		};
		
		let notificationsView = {
			view: "toolbar",
			css: "control-panel-button-toolbar",
			height: 150,
			elements: [
				{},
				{
					rows: [
						{
							view: "button",
							id: "user-control-panel-notif-alarms",
							width: 350,
							height: 50,
							tooltip: i18n.t("LBL_ALARMS"),
							type: "icon",
							icon: "webix_icon_btn mdi mdi-bell",
							hidden: false,
							on: {

								onItemClick() {
									
									let alarmService = window.sm.getService(Services.ALARM_SERVICE);
		
									if (alarmService) {
										
										let notification = alarmService.getNotification();
										
										if (notification) {
											
											notification.show();
											self.hide();
										}
									}
								}
							}
						},
						{
							view: "button",
							id: "user-control-panel-notif-incidents",
							width: 350,
							height: 50,
							tooltip: i18n.t("LBL_INCIDENTS"),
							type: "icon",
							icon: "webix_icon_btn mdi mdi-alert",
							hidden: (self.viewIncidentPermission) ? false : true,
							on: {

								onItemClick() {

									let incidentService = window.sm.getService(Services.INCIDENT_SERVICE);
		
									if (incidentService) {
										
										let notification = incidentService.getNotification();
										
										if (notification) {
											
											notification.show();
											self.hide();
										}
									}
								}
							}
						},
						{
							view: "button",
							id: "user-control-panel-notif-events",
							width: 350,
							height: 50,
							tooltip: i18n.t("LBL_EVENTS"),
							type: "icon",
							icon: "webix_icon_btn mdi mdi-calendar",
							hidden: true,
							on: {

								onItemClick() {

									let eventService = window.sm.getService(Services.EVENT_SERVICE);
		
									if (eventService) {
										
										let notification = eventService.getNotification();
										
										if (notification) {
											
											notification.show();
											self.hide();
										}
									}
								}
							}
						}
					]
				},
				{}
			],
			borderless: true
		};
		
		return webix.ui({
			view: "popup",
			id: "userControlPanel",
			move: true,
			css: "control-panel-main",
			hidden: true,
			height: document.documentElement.clientHeight - 147,
			width: panelWidth,
			body: {
				rows: [
					{ height: 20 },
					{
						view: "template",
						css: "user-avatar",
						template: function() {

							return `<img src="${util.generateAvatar(userData.name)}"/>`;
						},
						borderless: true

					},
					{
						view: "label",
						label: `<span class="user-fullname">${userData.fullName}</span>`,
						borderless: true
					},
					{
						view: "label",
						label: `<span class="user-email">${userData.email}</span>`,
						borderless: true
					},
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
												<h2 class="section-title-text">${i18n.t("LBL_NOTIFICATIONS")}</h2>
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
					notificationsView,
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
												<h2 class="section-title-text">${i18n.t("LBL_PENDING_TASKS")}</h2>
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
					pendingTasksView,
					{},
					{
						view: "toolbar",
						css: "control-panel-main-toolbar",
						height: 75,
						elements: [
							{},
							{
								view: "button",
								width: 125,
								label: i18n.t("LBL_CHANGE_PASSWORD"),
								tooltip: i18n.t("LBL_CHANGE_PASSWORD_DESCRIPTION"),
								type: "iconTop",
								icon: "mdi mdi-key-variant",
								on: {

									onItemClick() {

										let args = { selectedPks: [util.user.auth().id] };

										self.doAction(self.config.module.user.id, self.config.module.user.action.changePassword, args);
										self.hide();
									}
								}
							},
							{
								view: "button",
								width: 125,
								label: i18n.t("LBL_SETTINGS"),
								tooltip: i18n.t("LBL_SETTINGS_DESCRIPTION"),
								type: "iconTop",
								icon: "mdi mdi-cog",
								on: {

									onItemClick() {

										let args = { selectedPks: [util.user.auth().id] };

										self.doAction(self.config.module.user.id, self.config.module.user.action.settings, args);
										self.hide();
									}
								}
							},
							{
								view: "button",
								width: 125,
								label: i18n.t("LBL_LOGOUT"),
								tooltip: i18n.t("LBL_LOGOUT_DESCRIPTION"),
								type: "iconTop",
								icon: "mdi mdi-exit-to-app",
								on: {

									onItemClick() {

										self.logout();
									}
								}
							}
						],
						borderless: true
					}
				]
			},
			position: function(state) {
				state.left = document.documentElement.clientWidth - panelWidth - 20;
				state.top = 88;
			},
			on: {
				onBeforeShow: function() {

					this.config.height = document.documentElement.clientHeight - 137;
					this.adjust();
				}
			}
		});
	}

	updateData() {
		
		let self = this;
		let pendingTasks = false;
		
		updateDataAlarms();
		updateDataIncidents();
		updateDataEvents();
		
		if (pendingTasks) {

			$$("icon-user-service").data.badge = " ";
			$$("icon-user-service").refresh();

		} else {

			$$("icon-user-service").data.badge = "";
			$$("icon-user-service").refresh();
		}
		
		function updateDataAlarms() {
			
			let alarmService = window.sm.getService(Services.ALARM_SERVICE);
		
			if (alarmService) {
	
				let stats = alarmService.getData().stats;
				
				if (stats) {
	
					if (stats.noRecognizedAlarms > 0) {
	
						$$("user-control-panel-alarms").show();
						$$("user-control-panel-alarms").config.label = i18n.t("LBL_ALARMS_NO_RECOGNIZED") + " " + stats.noRecognizedAlarms;
						$$("user-control-panel-alarms").refresh();
	
						pendingTasks |= true;
	
					} else {
						$$("user-control-panel-alarms").hide();
					}
				}
				
				let notifications = alarmService.getData().notifications;
				
				if ((Array.isArray(notifications) && notifications.length > 0) ||
				    (alarmService.getNotification() && alarmService.getNotification().getRecognizedNotifications().length > 0)) {
					
					$$("user-control-panel-notif-alarms").show();
					$$("user-control-panel-notif-alarms").config.label = i18n.t("LBL_ALARMS") + " " + notifications.length;
					$$("user-control-panel-notif-alarms").refresh();
					
					pendingTasks |= true;
				}
				else {
					
					$$("user-control-panel-notif-alarms").hide();
				}
			}	
		}
		
		function updateDataIncidents() {
			
			let incidentService = window.sm.getService(Services.INCIDENT_SERVICE);
		
			if (incidentService) {
	
				let stats = incidentService.getData().stats;
	
				if (stats) {
	
					if (stats.incidentPendingToConfirm > 0 && self.viewIncidentPermission) {
	
						$$("user-control-panel-incident").show();
						$$("user-control-panel-incident").config.label = i18n.t("LBL_INCIDENTS_PENDING_CONFIRMATION") + " " + stats.incidentPendingToConfirm;
						$$("user-control-panel-incident").refresh();
	
						pendingTasks |= true;
	
					} else {
						$$("user-control-panel-incident").hide();
					}
				}
				
				let notifications = incidentService.getData().notifications;
				
				if ((Array.isArray(notifications) && notifications.length > 0) ||
				    (incidentService.getNotification() && incidentService.getNotification().getRecognizedNotifications().length > 0)) {
					
					if (self.viewIncidentPermission) {

						$$("user-control-panel-notif-incidents").show();
						$$("user-control-panel-notif-incidents").config.label = i18n.t("LBL_INCIDENTS") + " " + notifications.length;
						$$("user-control-panel-notif-incidents").refresh();

						pendingTasks |= true;
					}
				}
				else {
					
					$$("user-control-panel-notif-incidents").hide();
				}
			}	
		}
		
		function updateDataEvents() {
			
			let eventService = window.sm.getService(Services.EVENT_SERVICE);
		
			if (eventService) {
					
				let notifications = eventService.getData().notifications;
				
				if ((Array.isArray(notifications) && notifications.length > 0) ||
				    (eventService.getNotification() && eventService.getNotification().getRecognizedNotifications().length > 0)) {
					
					$$("user-control-panel-notif-events").show();
					$$("user-control-panel-notif-events").config.label = i18n.t("LBL_EVENTS") + " " + notifications.length;
					$$("user-control-panel-notif-events").refresh();

					pendingTasks |= true;
				}
				else {
					
					$$("user-control-panel-notif-events").hide();
				}
			}	
		}
	}
}
