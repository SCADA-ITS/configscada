import { util } from "../lib/util.js";
import GenericModule from "../modules/genericModule.js";
import Notification from "./notification.js";
import StompSubscriptorService from "./stompSubscriptorService.js";

const PICTOGRAM_INCIDENT_TYPE_PATH = "incident_type";
const PICTOGRAM_INCIDENT_TYPE_UNKNOWN = "incident_type/undefined.png";

export default class IncidentService extends GenericModule {

	constructor(module) {

		super(module, null, null);

		this.module = module;
		this.config = module.config;

		this.stomp = {};
		this.incidents = {
			data: {
				stats: {
					incidentPendingToConfirm: 0
				},
				notifications: []
			}
		};
		
		this.notification = undefined;
		this.idStompSubscriptor = undefined;
		this.defaultTimeConfirmIncidentSecs = 0;
		
		this.viewIncidentPermission = util.module.isAllowedPermission(module.config.notification.viewIncidentPermission);
	}
	
	destroy() {
		
		let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);
	
		if (stompSubscriptorService && self.idStompSubscriptor) {

			stompSubscriptorService.unsubscribe(self.idStompSubscriptor);
		}
	}
	
	getNotification() {
		
		return this.notification;
	}

	getData() {

		return this.incidents.data;
	}

	getView() {

		let views = [];

		return views;
	}

	loadData() {

		let self = this;
		
		if (util.ui.isExternal()) {
			
			return;
		}

		loadDefaultTimeConfirmIncident(function() {
			
			loadIncidentsPendingToConfirm();
		
			if (self.config.notification.enabled) {
			
				loadNotifications();
				loadIncidents();
			}	
		});
		
		
		function loadDefaultTimeConfirmIncident(onLoad) {
			
			webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.rest.systemParamConfirmIncident, { id: self.config.systemParamConfirmIncident })).then(function(systemParamsResponse) {

				if (systemParamsResponse) {

					let systemParam = systemParamsResponse.json();
					
					if (systemParam && !isNaN(systemParam.value)) {
						
						self.defaultTimeConfirmIncidentSecs = parseInt(systemParam.value); 	
					}
					
					if (onLoad) {
						
						onLoad();
					}
				}
			});
		}
		
		function loadIncidentsPendingToConfirm() {
		
			if (self.config && self.config.stats && self.config.stats.restEndpoint && self.config.stats.topic) {
				
				webix.ajax(appConfig.restUrl + self.config.stats.restEndpoint.incidentPendingToConfirm).then(function(data) {
	
					if (data.json() != undefined) {
		
						let incidentPendingToConfirm = data.json().value;
		
						self.incidents.data.stats.incidentPendingToConfirm = incidentPendingToConfirm;
		
						window.userControlPanel.updateData();
		
						connectWS();
					}
				});
			}
	
			function connectWS() {
	
				let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);
	
				if (stompSubscriptorService) {
	
					self.idStompSubscriptor = stompSubscriptorService.subscribe(appConfig.stompUrl, self.config.stats.topic.incidentPendingToConfirm, onMessage);
				}
	
				function onMessage(data) {
	
					self.incidents.data.stats.incidentPendingToConfirm = data.value;
	
					window.userControlPanel.updateData();
				}
			}
		}
		
		function loadNotifications() {
			
			webix.ajax(appConfig.restUrl + self.config.rest.incidentReports).then(function(data) {

				if (data.json() != undefined) {
					
					let incidentReports = data.json();
					
					let incidentReportsFiltered = incidentReports.filter(e => !e.confirm && IS_TRUE(e.incidentLevel.notification));
						
					if (Array.isArray(incidentReportsFiltered) && incidentReportsFiltered.length > 0) {
						
						self.incidents.data.notifications = incidentReportsFiltered.map(e => createNotification(e));
						
						self.incidents.data.notifications.sort((a, b) => (a.created < b.created) ? 1 : ((b.created < a.created) ? -1 : 0));
					}
				}
				
				if (window.userControlPanel) {
							
					window.userControlPanel.updateData();	
				}
				
				if (self.config.notification.enabled && self.viewIncidentPermission) {
						
					self.notification = new Notification(self.config.notification, Notification.INCIDENT, self.incidents.data.notifications);
				
					self.notification.load();	
				}
			});	
		}
		
		function createNotification(incidentReport) {
			
			let timeService = window.sm.getService(Services.TIME_SERVICE);
			
			let notification = {
				id: webix.uid(),
				notificationId: incidentReport.id,
				level: incidentReport.incidentLevel.alias.replace("LEVEL_", ""),
				notification: incidentReport.incidentLevel.notification,
				color: incidentReport.incidentLevel.color ? incidentReport.incidentLevel.color : "rgb(230, 0, 0)",
				sound: incidentReport.incidentLevel.sound ? incidentReport.incidentLevel.sound : undefined, 
				img: incidentReport.incidentType.icon ? "img/" + PICTOGRAM_INCIDENT_TYPE_PATH + "/" + incidentReport.incidentType.icon : "img/" + PICTOGRAM_INCIDENT_TYPE_UNKNOWN,
				recognized: incidentReport.confirm,
				countdownRecognize: {
					timeRef: "created",
					timeConfirmSecs: self.defaultTimeConfirmIncidentSecs,
					value: 0
				},
				created: incidentReport.generatedAt,
				updated: timeService.getEpoch(),
				removed: undefined,
				alias: incidentReport.incidentType.description,
				description: incidentReport.incidentType.alias + " " + 
							 i18n.t("LBL_AFFECTION_STRETCH") + ": " + incidentReport.affectionStretch.alias + " " + 
							 i18n.t("LBL_LOCATION") + ": " + incidentReport.location.alias + " " +
							 i18n.t("LBL_USER") + ": " + incidentReport.currentUser.name,
				userId: incidentReport.currentUser.id,
				cameraChecked: false,
				scenario: incidentReport.scenario,
				cancelWithoutConfirm: (!incidentReport.confirm && incidentReport.incidentState.finalState) ? true : false
					
			}
			
			if (incidentReport.incidentType.autoConfirmIncident) {
				
				notification.countdownRecognize.timeConfirmSecs = incidentReport.incidentType.autoConfirmIncident;
			}
			
			return notification;
		}
		
		function loadIncidents() {

			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);
	
			if (stompSubscriptorService) {
	
				stompSubscriptorService.subscribe(appConfig.stompUrl, self.config.ws.incidentReports, onMessage);
			}
			
			function onMessage(be, action, timestamp) {
				
				//console.log("[" + util.toLocaleString(timestamp / 1000) + "][Incident service] IncidentReport[" + be.id + "] => " + action);
				
				if (action === StompSubscriptorService.ACTION_BLOCK) {
					
					return;
				}
				
				webix.ajax(appConfig.restUrl + util.fillTemplate(self.config.rest.incidentReport, { id: be.id })).then(function(data) {

					if (data.json()) {
					
						let incidentReport = data.json();
						
						if (incidentReport) {
							
							let incidentLevel = window.em.getByField(EntityType.IMS_INCIDENT_LEVEL, "id", incidentReport.incidentLevel.id);
						
							if (incidentLevel && IS_TRUE(incidentLevel.notification)) {
								
								let incidentNotification = createNotification(incidentReport);
								
								if (incidentNotification) {
									
									if (action === StompSubscriptorService.ACTION_CREATE) {
										
										actionCreate(incidentNotification);
									}
									else if (action === StompSubscriptorService.ACTION_UPDATE) {
										
										actionUpdate(incidentNotification);
									}
									else if (action === StompSubscriptorService.ACTION_DELETE) {
										
										actionDelete(incidentNotification);
									}
								}
							}
						}
					}
					else {
						
						if (action === StompSubscriptorService.ACTION_DELETE) {
							
							let incidentNotification = {
								
								id: webix.uid(),
								notificationId: be.id	
							}
							
							actionDelete(incidentNotification);
						}
					}
				});
				
				function actionCreate(incidentNotification) {
					
					let idx = -1;
					
					if (Array.isArray(self.incidents.data.notifications)) {
						
						idx = self.incidents.data.notifications.findIndex(e => e.notificationId === incidentNotification.notificationId);
					
						if (!incidentNotification.recognized && idx === -1 && Array.isArray(self.incidents.data.notifications)) {
							
							self.incidents.data.notifications.push(incidentNotification);
							
							self.notification.add(incidentNotification);
							
							if (window.userControlPanel) {
			
								window.userControlPanel.updateData();	
							}	
						}
					}
				}
				
				function actionUpdate(incidentNotification) {
					
					let idx = -1;
					
					if (Array.isArray(self.incidents.data.notifications) && self.incidents.data.notifications.length > 0) {
						
						idx = self.incidents.data.notifications.findIndex(e => e.notificationId === incidentNotification.notificationId);
				
						if (idx > -1) {
						
							incidentNotification.id = self.incidents.data.notifications[idx].id;
							
							if (incidentNotification.recognized || incidentNotification.cancelWithoutConfirm) {
							
								self.notification.recognize(incidentNotification);
								
								self.incidents.data.notifications.splice(idx, 1);
						
								if (self.incidents.data.notifications.length === 0 && self.notification.getRecognizedNotifications().length === 0) {
									
									self.notification.hide();
								}
								
								if (window.userControlPanel) {
			
									window.userControlPanel.updateData();	
								}
							}
							else {
								
								self.notification.update(incidentNotification);
							}	
						}		
					}
				}
				
				function actionDelete(incidentNotification) {
					
					let idx = -1;
					
					if (Array.isArray(self.incidents.data.notifications) && self.incidents.data.notifications.length > 0) {
						
						idx = self.incidents.data.notifications.findIndex(e => e.notificationId === incidentNotification.notificationId);
						
						if (idx > -1) {
					
							self.notification.remove(incidentNotification);

							self.incidents.data.notifications.splice(idx, 1);
						
							if (self.incidents.data.notifications.length === 0 && self.notification.getRecognizedNotifications().length === 0) {
							
								self.notification.hide();
							}
							
							if (window.userControlPanel) {
		
								window.userControlPanel.updateData();	
							}	
						}
					}						
				}
			}
		}
	}
}
