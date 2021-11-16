import { util } from "../lib/util.js";
import GenericModule from "../modules/genericModule.js";
import Notification from "./notification.js";
import StompSubscriptorService from "./stompSubscriptorService.js";

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

		loadIncidentsPendingToConfirm();
		
		if (self.config.notification.enabled) {
		
			loadNotifications();
			loadIncidents();
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
	
					stompSubscriptorService.subscribe(util.stomp.getDataSourceFromTopic(self.config.stats.topic.incidentPendingToConfirm), onMessage);
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
	
					let incidentReportsFiltered = incidentReports.filter(e => !e.confirm);
						
					if (Array.isArray(incidentReportsFiltered) && incidentReportsFiltered.length > 0) {
						
						self.incidents.data.notifications = incidentReportsFiltered.map(e => createNotification(e));
						
						self.incidents.data.notifications.sort((a, b) => (a.created < b.created) ? 1 : ((b.created < a.created) ? -1 : 0))
					}
				}
				
				if (window.userControlPanel) {
							
					window.userControlPanel.updateData();	
				}
				
				if (self.config.notification.enabled) {
						
					self.notification = new Notification(self.config.notification, Notification.INCIDENT, self.incidents.data.notifications);
				
					self.notification.load();	
				}
			});	
		}
		
		function createNotification(incidentReport) {
			
			let timeService = window.sm.getService(Services.TIME_SERVICE);
			
			return {
				id: webix.uid(),
				notificationId: incidentReport.id,
				level: incidentReport.incidentLevel.alias.replace("LEVEL_", ""),
				notification: incidentReport.incidentLevel.notification,
				color: incidentReport.incidentLevel.color ? incidentReport.incidentLevel.color : "rgb(230, 0, 0)",
				sound: incidentReport.incidentLevel.sound ? incidentReport.incidentLevel.sound : undefined, 
				img: incidentReport.incidentType.icon ? "img/incident_type/" + incidentReport.incidentType.icon : undefined,
				recognized: incidentReport.confirm,
				created: incidentReport.generatedAt,
				updated: timeService.getEpoch(),
				removed: undefined,
				alias: incidentReport.incidentType.description,
				description: incidentReport.incidentType.alias + " " + 
							 i18n.t("LBL_AFFECTION_STRETCH") + ": " + incidentReport.affectionStretch.alias + " " + 
							 i18n.t("LBL_LOCATION") + ": " + incidentReport.location.alias  
			};
		}
		
		function loadIncidents() {

			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);
	
			if (stompSubscriptorService) {
	
				let dataSource = {
					dataSourceValues: [
						{ value: appConfig.stompUrl },
						{ value: self.config.ws.incidentReports }
					]
				};
	
				stompSubscriptorService.subscribe(dataSource, onMessage);
			}
			
			function onMessage(be, action, timestamp) {
				
				console.log("[" + util.toLocaleString(timestamp / 1000) + "][Incident service] IncidentReport[" + be.id + "] => " + action);
				
				if (action === StompSubscriptorService.ACTION_BLOCK) {
					
					return;
				}
				
				webix.ajax(appConfig.restUrl + util.fillTemplate(self.config.rest.incidentReport, { id: be.id })).then(function(data) {

					if (data.json() != undefined) {
					
						let incidentReport = data.json();
						
						if (incidentReport) {
							
							let incidentLevel = window.em.getByField(EntityType.IMS_INCIDENT_LEVEL, "id", incidentReport.incidentLevel.id);
						
							if (incidentLevel && incidentLevel.notification === 'true') {
								
								let incidentNotification = createNotification(incidentReport);
								
								if (incidentNotification) {
									
									let idx = self.incidents.data.notifications.findIndex(e => e.notificationId === incidentNotification.notificationId);
									
									// NEW INCIDENT REPORT NOTIF AND NOT CONFIRMED
									if (!incidentNotification.recognized && idx === -1) {
										
										self.incidents.data.notifications.push(incidentNotification);
											
										self.notification.add(incidentNotification);
										
										if (window.userControlPanel) {
						
											window.userControlPanel.updateData();	
										}
									}							
									// EXIST INCIDENT REPORT NOTIF
									else {
										
										if (action === StompSubscriptorService.ACTION_UPDATE && !self.incidents.data.notifications[idx].confirm) {
											
											incidentNotification.id = self.incidents.data.notifications[idx].id;
											
											self.notification.recognize(incidentNotification);
											
											self.incidents.data.notifications.splice(idx, 1);
									
											if (self.incidents.data.notifications.length === 0 && self.notification.getRecognizedNotifications().length === 0) {
												
												self.notification.hide();
											}
											
											if (window.userControlPanel) {
						
												window.userControlPanel.updateData();	
											}	
										}
										else if (action === StompSubscriptorService.ACTION_DELETE) {
											
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
				});
			}
		}
	}
}
