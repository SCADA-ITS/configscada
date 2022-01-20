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
		this.events = {
			data: {
				stats: {
					eventPendingToConfirm: 0
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

		return this.events.data;
	}

	getView() {

		let views = [];

		return views;
	}

	loadData() {

		let self = this;

		loadEventsPendingToConfirm();
		
		if (self.config.notification.enabled) {
		
			loadNotifications();
			loadEvents();
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
	
					stompSubscriptorService.subscribe(util.stomp.getDataSourceFromTopic(self.config.stats.topic.eventPendingToConfirm), onMessage);
				}
	
				function onMessage(data) {
	
					self.incidents.data.stats.eventPendingToConfirm = data.value;
	
					window.userControlPanel.updateData();
				}
			}
		}
		
		function loadNotifications() {
			
			webix.ajax(appConfig.restUrl + self.config.rest.events).then(function(data) {

				if (data.json() != undefined) {
					
					let events = data.json();


					// TODO	
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
		
		function createNotification(event) {
			
			let timeService = window.sm.getService(Services.TIME_SERVICE);
			
			return {
				id: webix.uid(),
				notificationId: undefined,
				level: undefined,
				notification: undefined,
				color: undefined,
				sound: undefined, 
				img: undefined,
				recognized: undefined,
				created: undefined,
				updated: undefined,
				removed: undefined,
				alias: undefined,
				description: undefined 
			};
		}
		
		function loadEvents() {

			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);
	
			if (stompSubscriptorService) {
	
				let dataSource = {
					dataSourceValues: [
						{ value: appConfig.stompUrl },
						{ value: self.config.ws.events }
					]
				};
	
				stompSubscriptorService.subscribe(dataSource, onMessage);
			}
			
			function onMessage(be, action, timestamp) {
				
				if (action === StompSubscriptorService.ACTION_BLOCK) {
					
					return;
				}
				
				webix.ajax(appConfig.restUrl + util.fillTemplate(self.config.rest.event, { id: be.id })).then(function(data) {

					if (data.json() != undefined) {
					
						let event = data.json();
						
						if (event) {
							
						}
					}
				});
			}
		}
	}
}
