import { util } from "../lib/util.js";
import GenericModule from "../modules/genericModule.js";
import Notification from "./notification.js";
import StompSubscriptorService from "../services/stompSubscriptorService.js";

export default class EventService extends GenericModule {

	constructor(module) {

		super(module, null, null);

		this.module = module;
		this.config = module.config;

		this.stomp = {};
		this.events = {
			data: {
				stats: {
					eventPendingToConfirm: 0
				},
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

		return this.events.data;
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
		
		loadEventsPendingToConfirm();
		
		if (self.config.notification.enabled) {
		
			loadNotifications();
			loadEvents();
		}
		
		function createNotification(event) {
			
			let notification = undefined;
			let timeService = window.sm.getService(Services.TIME_SERVICE);
			let description = "";
			let auditLogValues = [];
			let logType = undefined;
			let logSubtype = undefined;
			
			if (event.logType) {
				
				logType = window.em.getByField(EntityType.LOG_TYPE, "id", event.logType.id);
				logSubtype = logType && Array.isArray(logType.logSubtypes) ? logType.logSubtypes.find(e => e.id === event.logSubtype.id) : undefined;
			}
			
			if (event.user) {
				
				description += "[" + window.em.getByField(EntityType.USER, "id", event.user.id).name + "] ";
			}
			
			if (logType && logSubtype) {
				
				if (Array.isArray(event.auditLogValues) && Array.isArray(logType.logTypeParams)) {
				
					auditLogValues = event.auditLogValues.filter(e => e.value != undefined && e.value != "" && e.value != "null");
					
					auditLogValues.sort((a, b) => (a.logTypeParam.id < b.logTypeParam.id) ? 1 : ((b.logTypeParam.id < a.logTypeParam.id) ? -1 : 0));
					
					
					
					auditLogValues.forEach(auditLogValue => {
						
						let logTypeParam = logType.logTypeParams.find(e => e.id === auditLogValue.logTypeParam.id);
						
						if (logTypeParam) {
							
							description += i18n.t(logTypeParam.labelAlias) + ": " + (auditLogValue.value.indexOf("LBL") !== -1 ? i18n.t(auditLogValue.value) : auditLogValue.value) + "; ";	
						}
					});
				}
				
				if (logSubtype.filterEventLevel && LogLevel[logSubtype.filterEventLevel] && 
					event.level && LogLevel[event.level].level >= LogLevel[logSubtype.filterEventLevel].level) {
				
					notification = {
						id: webix.uid(),
						notificationId: event.id,
						level: event.level,
						notification: "true",
						color: event.level[event.level] ? LogLevel[event.level].color : "#ff9800",
						sound: self.config.notification.sound, 
						img: event.logSubtype.img ? logSubtype.img : (logType.img ? logType.img : undefined),
						recognized: undefined,
						created: event.time,
						updated: timeService.getEpoch(),
						removed: undefined,
						alias: (event.logType ? i18n.t(logType.labelAlias) : undefined) + " - " + (logSubtype ? i18n.t(logSubtype.labelAlias) : undefined),
						description: description
					};		
				}
			}
			
			return notification;
		}
		
		function loadEventsPendingToConfirm() {
		
			if (self.config && self.config.stats && self.config.stats.restEndpoint && self.config.stats.topic) {
				
				webix.ajax(appConfig.restUrl + self.config.stats.restEndpoint.eventPendingToConfirm).then(function(data) {
	
					if (data.json() != undefined) {
		
						let eventPendingToConfirm = data.json().value;
		
						self.events.data.stats.eventPendingToConfirm = eventPendingToConfirm;
		
						window.userControlPanel.updateData();
		
						connectWS();
					}
				});
			}
	
			function connectWS() {
	
				let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);
	
				if (stompSubscriptorService) {
	
					self.idStompSubscriptor = stompSubscriptorService.subscribe(appConfig.stompUrl, self.config.stats.topic.eventPendingToConfirm, onMessage);
				}
	
				function onMessage(data) {
	
					self.events.data.stats.eventPendingToConfirm = data.value;
	
					window.userControlPanel.updateData();
				}
			}
		}
		
		function loadNotifications() {
			
			webix.ajax(appConfig.restUrl + self.config.rest.events).then(function(data) {

				if (data.json() != undefined) {
					
					let events = data.json();
	
					if (Array.isArray(events) && events.length > 0) {
						
						self.events.data.notifications = events.map(e => createNotification(e));
						
						self.events.data.notifications.sort((a, b) => (a.created < b.created) ? 1 : ((b.created < a.created) ? -1 : 0));
					}
				}
				
				if (window.userControlPanel) {
							
					window.userControlPanel.updateData();	
				}
				
				if (self.config.notification.enabled) {
						
					self.notification = new Notification(self.config.notification, Notification.EVENT, self.events.data.notifications);
				
					self.notification.load();	
				}
			});
		}
		
		function loadEvents() {

			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);
	
			if (stompSubscriptorService) {
	
				stompSubscriptorService.subscribe(appConfig.stompUrl, self.config.ws.events, onMessage);
			}
			
			function onMessage(be, action, timestamp) {
								
				if (be) {
					
					let eventNotification = undefined;
					
					if (action === StompSubscriptorService.ACTION_CREATE) {
						
						eventNotification = createNotification(be);
						
						if (eventNotification) {
							
							self.events.data.notifications.push(eventNotification);
								
							self.notification.add(eventNotification);	
						}
					}
					else if (action === StompSubscriptorService.ACTION_DELETE) {
						
						let idxEventNotification = self.events.data.notifications.findIndex(e => e.notificationId === be.id);
						
						if (idxEventNotification !== -1) {
						
							eventNotification = self.events.data.notifications[idxEventNotification];
							
							if (eventNotification) {
								
								self.notification.remove(eventNotification);
								
								self.events.data.notifications.splice(idxEventNotification, 1);
								
								if (self.events.data.notifications.length === 0) {
												
									self.notification.hide();
								}						
							}
						}
					}
					
					if (window.userControlPanel) {
	
						window.userControlPanel.updateData();	
					}
				}
			}
		}
	}
}
