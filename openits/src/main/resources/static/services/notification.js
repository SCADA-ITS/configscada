import { util } from "../lib/util.js";
import MessageBox from "../component/messageBox.js";


const DEFAULT_TIMEOUT_NOTIFICATION_MS = 300000;
const MAX_INACTIVE_NOTICATIONS = 10;

export default class Notification {
	
	static get ALARM() { return { id: 1, label: "LBL_NOTIFICATION_ALARM", css: "notification-alarm-window", icon: "alarm.png", windowWidth: 750, windowHeight: 500 } }
	static get INCIDENT() { return { id: 2, label: "LBL_NOTIFICATION_INCIDENT", css: "notification-incident-window", icon: "incident.png", windowWidth: 750, windowHeight: 500 } }
	static get EVENT() { return { id: 3, label: "LBL_NOTIFICATION_EVENT", css: "notification-event-window", icon: "event.png", windowWidth: 750, windowHeight: 500 } }

	constructor(config, notificationType, notifications) {

		this.uid = webix.uid();
		this.config = config;
		this.notificationType = notificationType;
		this.notifications = notifications;
		this.config.timeoutNotificationMs = config.timeoutNotificationMs ? config.timeoutNotificationMs : DEFAULT_TIMEOUT_NOTIFICATION_MS;
		
		this.instance = undefined;
		this.audio = undefined;
		
		this.recognizedNotifications = [];
		this.intervalNotification = undefined;
	}

	load() {
		
		let view;
		let self = this;

		let notificationId = "notification-" + self.uid;

		let head = {
			view: "toolbar",
			css: self.notificationType.css + "-head",
			elements: [
				{

					view: "label",
					template: i18n.t(self.notificationType.label)
				},
				{
					view: "icon",
					icon: "mdi mdi-close",
					on: {
						onItemClick: function() {

							self.hide();
						}
					}
				}
			],
			borderless: 1
		};

		view = {
			view: "window",
			id: notificationId,
			hidden: true,
			css: self.notificationType.css,
			move: true,
			toFront: true,
			head: head,
			width: self.notificationType.windowWidth,
			height: self.notificationType.windowHeight,
			body: getViewNotification(),
			position: function(state) {
				state.left = Math.floor((document.documentElement.clientWidth - self.notificationType.windowWidth) / 2);
				state.top = Math.floor((document.documentElement.clientHeight - self.notificationType.windowHeight) / 2);
			}
		};
		
		function getViewNotification() {
			
			let viewNotification;
			
			viewNotification = {
				cols: [
					{ width: 20 },
					{
						rows: [
							{ height: 10 },
							getViewToolbar(),
							{ height: 5 },
							getList(),
							{ height: 20 },
							getToolbarActions(),
							{ height: 10 },			
						]
					},	
					{ width: 20 },
				]
			};
			
			return viewNotification;
		}
		
		function getViewToolbar() {
			
			const TOOLBAR_BUTTONS_WIDTH = 130;
			
			let viewToolbar = {
				height: 39,
				css: {"margin-left":"2px !important"},
				cols: []
			};
			
			viewToolbar.cols.push({
				width: 70,
				template: function() {

					return `<image class="notification-icon" src="img/notification/${self.notificationType.icon}" />`;
				},
				borderless: true
			});
			
			viewToolbar.cols.push({
				width: 300,
				template: function() {
	
					return `<div class="notification-title">${i18n.t(self.notificationType.label)}</div>`;
				},
				borderless: true
			});
			
			viewToolbar.cols.push({});
			
			viewToolbar.cols.push(
				{ 
					view: "button", 
					type: "icon", 
					icon: "mdi mdi-check", 
					css: "toolbar-button-text", 
					width: TOOLBAR_BUTTONS_WIDTH, 
					label: i18n.t("LBL_RECOGNIZE"), 
					tooltip: i18n.t("LBL_RECOGNIZE_ALARM"),
					on: {
						onItemClick() {
							
							let alarmIds = getSelectedAlarms().map(e => { return e.alarmId });
							
							if (Array.isArray(alarmIds) && alarmIds.length > 0) {
								
								let moduleActionId = self.config.recognize;
								let module = util.module.getFromModuleAction(moduleActionId);
								
								if (module) {
									
									let args = { selectedPks: alarmIds };
									let moduleAction = { id: moduleActionId };
					
									util.module.open(module, moduleAction, VIEW_TYPE_WINDOW, null, args);	
								}
							}
							else {
								
								(new MessageBox (MessageBox.ERROR, i18n.t("LBL_NO_SELECTED_ALARMS"), null, null)).show();
							}
						}
					} 
				}
			);
			
			viewToolbar.cols.push(
				{ 
					view: "button", 
					type: "icon", 
					icon: "mdi mdi-volume-off", 
					css: "toolbar-button-text", 
					width: TOOLBAR_BUTTONS_WIDTH, 
					label: i18n.t("LBL_SNOOZE"), 
					tooltip: i18n.t("LBL_SNOOZE_ALARM"),
					on: {
						onItemClick() {
							
							let alarms = getSelectedAlarms();
							
							if (Array.isArray(alarms) && alarms.length > 0) {
								
								(new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_ARE_YOU_SURE_SNOOZE_SELECTED_ALARMS"),
								
									// onAccept
									function() {
										
										alarms.forEach(alarm => {
								
											if (alarm.alarmConfig.alarmLevel.sound) {
												
												alarm.snooze = true;
											}
										});
										
										let list = $$("list-" + self.uid);
										
										if (list) {
											
											list.refresh();
											
											list.unselectAll();
											
											list.select(alarms.map(alarm => parseInt(alarm.id)));
										}
									}
								)).show();
								
								
							}
							else {
								
								(new MessageBox (MessageBox.ERROR, i18n.t("LBL_NO_SELECTED_ALARMS"), null, null)).show();
							}
						}
					}
				}
			);
			
			function getSelectedAlarms() {
		
				let list = $$("list-" + self.uid);
				let alarms = [];
				
				if (list) {
			
					let ids = list.getSelectedId(true);
								
					if (Array.isArray(ids) && ids.length > 0) {
						
						ids = ids.map(e => parseInt(e));					

						alarms = self.notifications.filter(e => ids.includes(parseInt(e.id)));
					}	
				}
				
				return alarms;
			}
			
			return viewToolbar;
		}
		
		function getList() {

			let list;

			list = {
				view: "list",
				id: "list-" + self.uid,
				css: "notification-list",
				select: "multiselect",
				type: {

					template: function(obj) {
					
						return drawNotification(obj);	
					}
				},
				data: self.notifications,
				on: {
					onAfterSelect: function() {
						
						if (self.notificationType.id === Notification.ALARM.id) {
						
							let id = this.getSelectedId(true);
							
							let alarm = self.notifications.find(e => e.id == id);
							
							if (alarm && !alarm.recognized && !alarm.snooze) {
								
								self.playAudio(alarm.alarmConfig.alarmLevel.sound);	
							}
							else {
								
								self.stopAudio();
							}
						}
					},
					onAfterDelete: function(id) {
						
						let ids = this.getSelectedId(true);
						
						if (Array.isArray(ids) && ids.length === 0) {
							
							self.stopAudio();
						}
					}
				}
			};
			
			function drawNotification(obj) {
				
				let html;

				if (self.notificationType.id === Notification.ALARM.id) {
					
					let levelBackgroundColor = obj.alarmConfig.alarmLevel.color ? obj.alarmConfig.alarmLevel.color : "rgb(230, 0, 0)";
					let level = obj.alarmConfig.alarmLevel.alias.replace("LEVEL_", "");
					
					html = `<div class="${!obj.recognized ? 'state-enabled' : 'state-disabled'}"></div>
							<image class="image" src="img/${obj.element.elementType.elementTypePhoto}" onerror="this.onerror=null;this.src='img/not_found_50.png';" />
							<div class="level" style="background-color: ${levelBackgroundColor}">${level}</div>
							<div class="sound"><span style="${obj.recognized ? 'color:rgb(146, 171, 207);' : ''}" class="${obj.alarmConfig.alarmLevel.sound && !obj.snooze ? "mdi mdi-volume-high" : "mdi mdi-volume-off"}"></span></div>
							<div class="text">
						  		<span class="first-line" style="${obj.recognized ? 'text-decoration:line-through;color:rgb(146, 171, 207);' : ''}" >${i18n.t(obj.alarmConfig.labelAlias)} </span>
					  			<span class="second-line" style="${obj.recognized ? 'text-decoration:line-through;color:rgb(146, 171, 207);' : ''}">${util.toLocaleString(obj.activation / 1000)} : ${obj.element.alias}</span>
							</div>`;
				}
				else if (self.notificationType.id === Notification.INCIDENT.id) {
					
					// TODO
				}				
				else if (self.notificationType.id === Notification.EVENT.id) {
					
					// TODO
				}
				
				return html;
			}
	
			return list;
		}
		
		function getToolbarActions() {
		
			let viewToolbarActions = {};
			
			viewToolbarActions = {
				view: "toolbar",
				height: 45,
				borderless: true,
				elements: []
			};
			

			viewToolbarActions.elements.push({});
			viewToolbarActions.elements.push({ 
				view: "button",
				css: "dialog-cancel", 
				width: 130,
				height: 35, 
				label: i18n.t("LBL_CLOSE"),
				on: {
					onItemClick() {
					
						self.hide();
					}
				}
			});
			
			return viewToolbarActions;	
		}
		
		self.instance = webix.ui(view);
		
		this.enableReminder();
	}
	
	enableReminder() {
		
		let self = this;
		if (this.intervalNotification) {
	
			disableReminder();
		}
		
		this.intervalNotification = setInterval(function() {

			if (Array.isArray(self.notifications) && self.notifications.length > 0) {
				
				self.show();	
			}

		}, this.config.timeoutNotificationMs);
	}
	
	disableReminder() {
		
		if (this.intervalNotification) {
			
			clearInterval(this.intervalNotification);
			
			this.intervalNotification = undefined;
		}
	}
	
	getRecognizedNotifications() {
		
		return this.recognizedNotifications;
	}
	
	show(notification) {

		let list = $$("list-" + this.uid);
		
		if (this.instance && list) {
			
			if (notification) {
				
				list.select(notification.id);
				list.showItem(notification.id);	
			}
			else {
				
				list.unselectAll();
			}
			
			this.instance.show();
			
			util.ui.centerWindow(this.instance.config.id);
			
			this.disableReminder();
		}		
	}
	
	hide() {
		
		if (this.instance) {
			
			this.instance.hide();
			
			this.stopAudio();
			
			this.enableReminder();
		}
	}
	
	add(notification) {
		
		let list = $$("list-" + this.uid);
		
		if (list) {
			
			list.add(notification, 0);
			
			this.show(notification);
		}
	}
	
	recognize(notification) {
		
		let list = $$("list-" + this.uid);
		
		if (list && Array.isArray(this.notifications) && this.notifications.length > 0) {
			
			let idxList = list.getIndexById(notification.id);
			
			if (idxList !== -1) {
				
				list.remove(notification.id);
				list.add(notification, idxList);
			
				this.recognizedNotifications.push(notification);
			
				// Sort descending
				this.recognizedNotifications.sort((a, b) => (a.activation < b.activation) ? 1 : ((b.activation < a.activation) ? -1 : 0));			
				
				if (Array.isArray(this.recognizedNotifications) && this.recognizedNotifications.length > MAX_INACTIVE_NOTICATIONS) {
					
					list.remove(this.recognizedNotifications[MAX_INACTIVE_NOTICATIONS].id)
					
					this.recognizedNotifications.splice(MAX_INACTIVE_NOTICATIONS, 1);
				}	
			}
		}
	}
	
	remove(notification) {
		
		let list = $$("list-" + this.uid);
		
		if (list && Array.isArray(this.notifications) && this.notifications.length > 0) {
			
			let idxList = list.getIndexById(notification.id);
			
			if (idxList !== -1) {
				
				list.remove(notification.id);
			}
		}
	}
	
	playAudio(sound) {
		
		this.stopAudio();
		
		if (sound && !this.audio) {
			
			this.audio = new Audio('./sound/' + sound);
			this.audio.loop = true;
	  		this.audio.play();	
		}
	}
	
	stopAudio() {
		
		if (this.audio) {
			
			this.audio.pause();
    		this.audio.currentTime = 0;
			this.audio = undefined;
		}
	}
}
