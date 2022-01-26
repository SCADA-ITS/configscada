import GenericModule from "../modules/genericModule.js";
import Notification from "./notification.js";

export default class EventService extends GenericModule {

	constructor(module) {

		super(module, null, null);

		this.module = module;
		this.config = module.config;

		this.stomp = {};
		this.events = {
			data: {
				notifications: []
			}
		};
		
		this.notification = undefined;
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
			
			return notification;
		}
		
		function loadNotifications() {
			
			self.notification = new Notification(self.config.notification, Notification.EVENT);
				
			self.notification.load();
		}
		
		function loadEvents() {

			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);
	
			if (stompSubscriptorService) {
	
				stompSubscriptorService.subscribe(appConfig.stompUrl, self.config.ws.events, onMessage);
			}
			
			function onMessage(be, action, timestamp) {
								
				if (be) {
				
					let event = be;
					
					if (event && event.level && LogLevel[event.level].level >= LogLevel[self.config.notification.level].level) {
						
						let eventNotification = createNotification(event);
						
						if (eventNotification) {
							
							self.events.data.notifications.push(eventNotification);
										
							self.notification.add(eventNotification);
							
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
