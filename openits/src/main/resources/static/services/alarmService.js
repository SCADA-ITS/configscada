import { util } from "../lib/util.js";
import GenericService from "./genericService.js";
import AlarmControlPanel from "../component/general/alarmControlPanel.js";
import Notification from "./notification.js";
import EntitiesManager from "../managers/entitiesManager.js";

const LATEST_ALARMS_MAX = 10;

export default class AlarmService extends GenericService {

	constructor(module) {

		super(module, null, null);

		this.module = module;
		this.config = module.config;

		this.stomp = {};
		this.alarms = {
			config: undefined,
			data: {
				stats: {
					activeAlarms: 0,
					noRecognizedAlarms: 0,
					filteredAlarms: 0
				},
				latest: [],
				notifications: []
			}
		};
		this.notification = undefined;
		this.idStompSubscriptor = undefined;
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
		
		return this.alarms.data;
	}

	getView() {

		let views = [];

		let iconAlarm = {
			id: "icon-alarm-service",
			view: "icon",
			css: "alarm-template",
			badge: 0,
			icon: "mdi mdi-bell",
			value: "Alert",
			tooltip: i18n.t("LBL_ALARMS")
		}

		views.push(iconAlarm);

		return views;
	}

	loadData() {

		let self = this;
		
		if (!util.ui.isExternal()) {
			
			if (!window.alarmControlPanel) {

				window.alarmControlPanel = new AlarmControlPanel(self.config);
			}
			
			if ($$("icon-alarm-service")) {
				
				$$("icon-alarm-service").attachEvent("onItemClick", function(id, e) {
	
					callAlarmControlPanel();
				});	
			}	
		}

		self.alarms.stats = {};
		self.alarms.latest = [];
		self.alarms.notifications = [];
		
		loadActiveAlarms();
		loadNoRecognizedAlarms();
		loadFilteredAlarms();
		
		if (!util.ui.isExternal()) {
			
			let hnd;
			
			hnd = setInterval(function() {
				
				let entityState = window.em.getState(EntityType.ALARM_CONFIG);
				
				if (entityState === EntitiesManager.ENTITY_STATE_LOADED) {
					
					clearInterval(hnd);
					
					if (self.config.notification.enabled) {
			
						loadNotifications();	
					}
					
					loadAlarms();
					
					log.debug("Alarm service has loaded initial notifications and alarms")
				}
	
			}, 100);
		}
		
		function loadActiveAlarms() {

			webix.ajax(appConfig.restUrl + self.config.rest.active_alarms).then(function(data) {
	
				let activeAlarms = 0;
				
				if (data.json() != undefined) {
					
					activeAlarms = data.json().value;
				}
				
				self.alarms.data.stats.activeAlarms = activeAlarms;
				
				self.executeCallbacks(self.alarms.data);
					
				if (window.alarmControlPanel) {
					
					window.alarmControlPanel.updateData();	
				}
				
				connectWS();
				
				function connectWS() {
		
					let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);
		
					if (stompSubscriptorService) {
		
						self.idStompSubscriptor = stompSubscriptorService.subscribe(appConfig.stompUrl, self.config.ws.active_alarms, onMessage);
					}
		
					function onMessage(data) {
		
						self.alarms.data.stats.activeAlarms = data.value;
						
						if (window.alarmControlPanel) {
						
							window.alarmControlPanel.updateData();	
						}
						
						self.executeCallbacks(self.alarms.data);
					}
				}
			});
		}
	
		function loadNoRecognizedAlarms() {
	
			webix.ajax(appConfig.restUrl + self.config.rest.no_recognized_alarms).then(function(data) {
	
				let noRecognizedAlarms = 0;
				
				if (data.json() != undefined) {
					
					noRecognizedAlarms = data.json().value;
				}
				
				self.alarms.data.stats.noRecognizedAlarms = noRecognizedAlarms;
				
				self.executeCallbacks(self.alarms.data);
					
				if (window.alarmControlPanel) {
				
					window.alarmControlPanel.updateData();	
				}
				
				if (window.userControlPanel) {
					
					window.userControlPanel.updateData();	
				}
				
				connectWS();
				
				function connectWS() {
		
					let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);
		
					if (stompSubscriptorService) {
		
						stompSubscriptorService.subscribe(appConfig.stompUrl, self.config.ws.no_recognized_alarms, onMessage);
					}
		
					function onMessage(data) {
		
						self.alarms.data.stats.noRecognizedAlarms = data.value;
						
						self.executeCallbacks(self.alarms.data);
		
						if (window.alarmControlPanel) {
						
							window.alarmControlPanel.updateData();	
						}
					
						if (window.userControlPanel) {
							
							window.userControlPanel.updateData();	
						}	
					}
				}
			});
		}
	
		function loadFilteredAlarms() {
	
			webix.ajax(appConfig.restUrl + self.config.rest.filtered_alarms).then(function(data) {
				
				let filteredAlarms = 0;
	
				if (data.json() != undefined) {
					
					filteredAlarms = data.json().value;
				}
				
				self.alarms.data.stats.filteredAlarms = filteredAlarms;
				
				self.executeCallbacks(self.alarms.data);
					
				if (window.alarmControlPanel) {
				
					window.alarmControlPanel.updateData();	
				}
				
				connectWS();
				
				function connectWS() {
		
					let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);
		
					if (stompSubscriptorService) {
		
						stompSubscriptorService.subscribe(appConfig.stompUrl, self.config.ws.filtered_alarms, onMessage);
					}
		
					function onMessage(data) {
		
						self.alarms.data.stats.filteredAlarms = data.value;
						
						self.executeCallbacks(self.alarms.data);
		
						if (window.alarmControlPanel) {
						
							window.alarmControlPanel.updateData();	
						}
					}
				}
			});
		}
		
		function loadNotifications() {
			
			let alarmsConfigs = window.em.getAll(EntityType.ALARM_CONFIG);
			
			if (Array.isArray(alarmsConfigs)) {
				
				webix.ajax(appConfig.restUrl + self.config.rest.alarms).then(function(data) {
					
					let alarms = [];
	
					if (data.json() != undefined) {
						
						alarms = data.json();
					}
		
					let alarmsFiltered = alarms.filter(alarm => {
						
						if (!alarm.deactivation && !alarm.recognized) {
							
							let alarmConfig = alarmsConfigs.find(e => e.id === alarm.alarmConfig.id);
												
							if (alarmConfig && alarmConfig.alarmLevel && alarmConfig.alarmLevel.notification === "true") {
							
								alarm.active = true;
								
								return alarm;	
							}	
						}
					});
					
					if (Array.isArray(alarmsFiltered) && alarmsFiltered.length > 0) {
						
						self.alarms.data.notifications = alarmsFiltered.map(e => createNotification(e));
						
						self.alarms.data.notifications.sort((a, b) => (a.created < b.created) ? 1 : ((b.created < a.created) ? -1 : 0))
					}
					
					if (window.userControlPanel) {
						
						window.userControlPanel.updateData();	
					}
					
					if (self.config.notification.enabled) {
							
						self.notification = new Notification(self.config.notification, Notification.ALARM, self.alarms.data.notifications);
					
						self.notification.load();	
					}
				});	
			}
		}
		
		function createNotification(alarm) {
			
			let notification = undefined;
			let timeService = window.sm.getService(Services.TIME_SERVICE);
			let element = window.em.getByField(EntityType.ELEMENT, "id", alarm.targetElement.id);
			let alarmConfig = window.em.getByField(EntityType.ALARM_CONFIG, "id", alarm.alarmConfig.id);
			
			if (timeService && element && alarmConfig) {

				notification = {
					id: webix.uid(),
					notificationId: alarm.id,
					level: alarmConfig.alarmLevel.alias.replace("LEVEL_", ""),
					notification: alarmConfig.alarmLevel.notification,
					color: alarmConfig.alarmLevel.color ? alarmConfig.alarmLevel.color : "rgb(230, 0, 0)",
					sound: alarmConfig.alarmLevel.sound ? alarmConfig.alarmLevel.sound : undefined, 
					img: element.elementType.elementTypePhoto ? "img/" + element.elementType.elementTypePhoto : undefined,
					recognized: alarm.recognized,
					created: alarm.activation,
					updated: timeService.getEpoch(),
					removed: alarm.deactivation,
					alias: i18n.t(alarmConfig.labelAlias),
					description: element.alias
				};
			}
			
			return notification;
		}
		
		function loadAlarms() {

			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);
	
			if (stompSubscriptorService) {
	
				self.idStompSubscriptor = stompSubscriptorService.subscribe(appConfig.stompUrl, self.config.ws.alarms, onMessage);
			}
			
			function onMessage(be) {
	
				let alarmNotification = createNotification(be);
					
				if (alarmNotification) {
					
					self.recentAlarmCount++;
					
					self.alarms.data.latest.push(webix.copy(alarmNotification));
					
					if (self.config.notification.enabled) {
						
						chkNotifyAlarm();	
					}
					
					// Sort descending
					self.alarms.data.latest.sort((a, b) => (a.updated < b.updated) ? 1 : ((b.updated < a.updated) ? -1 : 0));
					
					if (Array.isArray(self.alarms.data.latest) && self.alarms.data.latest.length > LATEST_ALARMS_MAX) {
						
						self.alarms.data.latest.splice(LATEST_ALARMS_MAX, 1);
					}
					
					if (window.alarmControlPanel) {
						
						window.alarmControlPanel.updateData();	
					}
					
					
					
					function chkNotifyAlarm() {
						
						//let alarmLevel = window.em.getByField(EntityType.ALARM_LEVEL, "id", alarmNotification.alarmConfig.alarmLevel.id);
						
						if (alarmNotification.notification === 'true' && !isFiltered()) {
							
							// NEW ALARM NOTIF AND NOT RECOGNIZED
							if (!alarmNotification.removed && !isRecognized()) {
									
									self.alarms.data.notifications.push(alarmNotification);
								
									self.notification.add(alarmNotification);	
							}
							// EXIST ALARM NOTIF
							else {
								
								let idx = self.alarms.data.notifications.findIndex(e => e.notificationId === alarmNotification.notificationId);
									
								if (idx !== -1) {
									
									alarmNotification.id = self.alarms.data.notifications[idx].id; 
																
									// EXIST ALARM NOTIF IS RECOGNIZED
									if (!alarmNotification.removed && isRecognized()) {
										
										self.notification.recognize(alarmNotification);
									}
									// EXIST ALARM NOTIF REMOVED
									else if (alarmNotification.removed) {
									
										self.notification.remove(alarmNotification);		
									}
									
									self.alarms.data.notifications.splice(idx, 1);
									
									if (self.alarms.data.notifications.length === 0 && self.notification.getRecognizedNotifications().length === 0) {
										
										self.notification.hide();
									}
								}	
							}
							
							if (window.userControlPanel) {
							
								window.userControlPanel.updateData();	
							}
						}
					}
				}
				
				function isFiltered() {
				
					//let timeService = window.sm.getService(Services.TIME_SERVICE);
					
					//return (be.filteredUntil && timeService.getEpoch() < be.filteredUntil);
					
					return (be.filteredUntil != undefined);
				}
				
				function isRecognized() {
					
					//let timeService = window.sm.getService(Services.TIME_SERVICE);
					
					//return (be.recognized && timeService.getEpoch() > be.recognized);
					return (be.recognized != undefined);
				}
			}
		}
		
		function callAlarmControlPanel() {
		
			if (window.alarmControlPanel) {
				
				if (window.alarmControlPanel.hidden()) {
				
					window.alarmControlPanel.show();
				} 
				else {
					
					window.alarmControlPanel.hide();
				}	
			}
		}
	}
}
