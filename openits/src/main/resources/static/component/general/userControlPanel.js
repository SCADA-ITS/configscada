import { util } from "../../lib/util.js";
import GenericControlPanel from "./genericControlPanel.js";
import SubscriptorService from "../../services/subscriptorService.js";

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
			text: i18n.t("LBL_CONFIRM_CLOSE_SESSION"),
			callback: function(result) {

				if (result === "0") {

					util.user.logout(
						
						function onResponse(userSession) {
							
							if (userSession && userSession.access === LOGIN_MESSAGE.LOGOUT.id) {
							
								let subscriptorService = window.sm.getService(Services.SUBSCRIPTOR_SERVICE);
								
								if (subscriptorService) {
									
									subscriptorService.sendMessage(SubscriptorService.TYPE_SESSION_LOGOUT, "");
								}
								
								util.user.closeSession();
								util.user.redirectLogin();
							}
						}
					);
				}
			}
		});
		
	}

	createView() {

		// for using the object inside callbacks
		let self = this;
		let panelWidth = 400;
		let sessionData = webix.storage.session.get(SESSION_STORAGE_SESSION_DATA);
		let userData = undefined;
		
		if (sessionData && sessionData.user) {
			
			userData = sessionData.user;	
		}
		
		let pendingTasksView = {
			view: "toolbar",
			css: "control-panel-button-toolbar",
			height: 150,
			elements: [
				{},
				{
					rows: [
						{
							view: "label",
							id: "user-control-panel-alarms",
							css: "control-panel-notification",
							align: "center",
							tooltip: i18n.t("LBL_NUMBER_ALARMS_NO_RECOGNIZED"),
							hidden: true,
							width: 350,
							height: 50,
							label: function() {
								
								return `<div>
											<span class="icon ico webix_icon mdi mdi-bell"></span>
											<span class="value-alarm">${i18n.t("LBL_WO_ALARMS_PENDING")}</span>
										</div>`
							}(),
							on: {
								onItemClick: function(id) {
	
									self.openModule(self.config.module.alarmMonitor.id);
									self.hide();
								}
							}
						},
						{
							view: "label",
							id: "user-control-panel-incident",
							css: "control-panel-notification",
							align: "center",
							tooltip: i18n.t("LBL_NUMBER_INCIDENTS_PENDING"),
							hidden: true,
							width: 350,
							height: 50,
							label: function() {

								return `<div>
											<span class="icon ico webix_icon mdi mdi-map-marker-alert"></span>
											<span class="value-incident">${i18n.t("LBL_WO_PENDING_INCIDENTS")}</span>
										</div>`
							}(),
							on: {
								onItemClick: function(id) {
	
									self.openModule(self.config.module.incidentReportModule.id);
									self.hide();
								}
							}
						},
						{
							view: "label",
							id: "user-control-panel-event",
							css: "control-panel-notification",
							align: "center",
							tooltip: i18n.t("LBL_NUMBER_EVENTS_PENDING"),
							hidden: true,
							width: 350,
							height: 50,
							label: function() {

								return `<div>
											<span class="icon ico webix_icon mdi mdi-map-marker-alert"></span>
											<span class="value-event">${i18n.t("LBL_WO_PENDING_EVENTS")}</span>
										</div>`
							}(),
							on: {
								onItemClick: function(id) {
	
									self.openModule(self.config.module.auditLogs.id);
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
							view: "label",
							id: "user-control-panel-notif-alarms",
							css: "control-panel-notification",
							align: "center",
							tooltip: i18n.t("LBL_SHOW_ALARMS_NOTIFIED"),
							hidden: true,
							width: 350,
							height: 50,
							label: function() {
								
								return `<div>
											<span class="icon ico webix_icon mdi mdi-bell"></span>
											<span class="value-alarm">${i18n.t("LBL_WO_NOTIFIED_ALARMS")}</span>
										</div>`
							}(),
							on: {
								onItemClick: function(id) {
	
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
							view: "label",
							id: "user-control-panel-notif-incidents",
							css: "control-panel-notification",
							align: "center",
							tooltip: i18n.t("LBL_SHOW_INCIDENTS_NOTIFIED"),
							hidden: true,
							width: 350,
							height: 50,
							label: function() {

								return `<div>
											<span class="icon ico webix_icon mdi mdi-map-marker-alert"></span>
											<span class="value-incident">${i18n.t("LBL_WO_NOTIFIED_INCIDENTS")}</span>
										</div>`
							}(),
							on: {
								onItemClick: function(id) {
	
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
							view: "label",
							id: "user-control-panel-notif-events",
							css: "control-panel-notification",
							align: "center",
							tooltip: i18n.t("LBL_SHOW_EVENTS_NOTIFIED"),
							hidden: true,
							width: 350,
							height: 50,
							label: function() {
			
								return `<div>
											<span class="icon ico webix_icon mdi mdi-alert"></span>
											<span class="value-event">${i18n.t("LBL_WO_NOTIFIED_EVENTS")}</span>
										</div>`
							}(),
							on: {
								onItemClick: function(id) {
	
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

										let args = { selectedPks: [util.user.getUserData().id] };

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

										let args = { selectedPks: [util.user.getUserData().id] };

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
						$$("user-control-panel-alarms").config.label = getLabelPending(stats.noRecognizedAlarms);
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
					$$("user-control-panel-notif-alarms").config.label = getLabelNotification(notifications.length);
					$$("user-control-panel-notif-alarms").refresh();
					
					pendingTasks |= (notifications.length > 0);	
				}
				else {
					
					$$("user-control-panel-notif-alarms").hide();
				}
			}
			
			function getLabelNotification(notifications) {
				
				let label = notifications > 0 ? notifications + " " + i18n.t("LBL_ALARMS_NOTIFIED") : i18n.t("LBL_WO_NOTIFIED_ALARMS"); 
													
				return `<div>
							<span class="icon ico webix_icon mdi mdi-bell"></span>
							<span class="value-alarm">${label}</span>
						</div>`
			}
			
			function getLabelPending(notifications) {
				
				return `<div>
					<span class="icon ico webix_icon mdi mdi-bell"></span>
					<span class="value-alarm">${notifications > 0 ? notifications + " " +  i18n.t("LBL_ALARMS_NO_RECOGNIZED") : i18n.t("LBL_WO_ALARMS_PENDING")}</span>
				</div>`
			}
		}
		
		function updateDataIncidents() {
			
			let incidentService = window.sm.getService(Services.INCIDENT_SERVICE);
		
			if (incidentService) {
	
				let stats = incidentService.getData().stats;
	
				if (stats) {
	
					if (stats.incidentPendingToConfirm > 0 && self.viewIncidentPermission) {
	
						$$("user-control-panel-incident").show();
						$$("user-control-panel-incident").config.label = getLabelPending(stats.incidentPendingToConfirm);
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
						$$("user-control-panel-notif-incidents").config.label = getLabelNotification(notifications.length);
						$$("user-control-panel-notif-incidents").refresh();

						pendingTasks |= (notifications.length > 0);
					}
				}
				else {
					
					$$("user-control-panel-notif-incidents").hide();
				}
			}
			
			function getLabelNotification(notifications) {
				
				let label = notifications > 0 ? notifications + " " + i18n.t("LBL_INCIDENTS_NOTIFIED") : i18n.t("LBL_WO_NOTIFIED_INCIDENTS"); 
													
				return `<div>
							<span class="icon ico webix_icon mdi mdi-map-marker-alert"></span>
							<span class="value-incident">${label}</span>
						</div>`
			}
			
			function getLabelPending(incidentPendingToConfirm) {
				
				return `<div>
					<span class="icon ico webix_icon mdi mdi-map-marker-alert"></span>
					<span class="value-incident">${incidentPendingToConfirm > 0 ? incidentPendingToConfirm + " " + i18n.t("LBL_INCIDENTS_NO_CONFIRMED") : i18n.t("LBL_WO_PENDING_INCIDENTS")}</span>
				</div>`
			}
		}
		
		function updateDataEvents() {
			
			let eventService = window.sm.getService(Services.EVENT_SERVICE);
		
			if (eventService) {
				
				let stats = eventService.getData().stats;
	
				if (stats) {
	
					if (stats.eventPendingToConfirm > 0) {
	
						$$("user-control-panel-event").show();
						$$("user-control-panel-event").config.label = getLabelPending(stats.eventPendingToConfirm);
						$$("user-control-panel-event").refresh();
	
						pendingTasks |= true;
	
					} else {
						
						$$("user-control-panel-event").hide();
					}
				}
					
				let notifications = eventService.getData().notifications;
				
				if ((Array.isArray(notifications) && notifications.length > 0) ||
				    (eventService.getNotification() && eventService.getNotification().getRecognizedNotifications().length > 0)) {
					
					$$("user-control-panel-notif-events").show();
					$$("user-control-panel-notif-events").config.label = getLabelNotification(notifications.length);
					$$("user-control-panel-notif-events").refresh();

					pendingTasks |= (notifications.length > 0);
				}
				else {
					
					$$("user-control-panel-notif-events").hide();
				}
			}
			
			function getLabelNotification(notifications) {
				
				let label = notifications > 0 ? notifications + " " + i18n.t("LBL_EVENTS_NOTIFIED") : i18n.t("LBL_WO_NOTIFIED_EVENTS"); 
													
				return `<div>
							<span class="icon ico webix_icon mdi mdi-alert"></span>
							<span class="value-event">${label}</span>
						</div>`
			}
			
			function getLabelPending(eventPendingToConfirm) {
				
				return `<div>
					<span class="icon ico webix_icon mdi mdi-map-marker-alert"></span>
					<span class="value-event">${eventPendingToConfirm > 0 ? eventPendingToConfirm + " " +  i18n.t("LBL_EVENTS_NO_CONFIRMED") : i18n.t("LBL_WO_PENDING_EVENTS")}</span>
				</div>`
			}
		}
	}
}
