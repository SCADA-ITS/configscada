import {util} from "../lib/util.js";
import GenericModule from "../modules/genericModule.js";
import NotificationControlPanel  from "../controllers/notificationControlPanel.js";

export default class NotificationService extends GenericModule {
	
	constructor(module, mod, args){
		
		super(module, null, args);
		
		this.module = module;
		this.mod = mod;
		this.args = args;
		
		this.stomp = {};
		this.notifications = {};
	}
	
	getView() {

		let toolbar = $$(MAIN_TOOLBAR_ID);
		
		if (!webix.isUndefined(toolbar)) {
			
			let iconNotification = { 
				id: "icon-notification-service", 
				view: "icon", 
				css: "notification-template",
				//width: 75,
				badge:0, 
				icon: "mdi mdi-comment", 
				value:"Notification",
				tooltip: i18n.t("LBL_NOTIFICATIONS") 
			} 
			
			toolbar.addView(iconNotification);
		}
	}
	
	loadData() {
		
		let self = this;
		let moduleValue = this.module.moduleValues.find(e => e.moduleParam.id === PARAM_MODULE_TYPE_SERVICE_CONFIG);
		
		if (moduleValue != undefined) {
			
			$$("icon-notification-service").attachEvent("onItemClick", function(id, e) {

				self.callNotificationControlPanel();
			});
			
			this.notifications.config = JSON.parse(moduleValue.value);
			this.notifications.stats = {};
			
			this.updateStatistics(); 
		}
	}
	
	updateStatistics() {
	
		this.updateNotifications();
	}
	
	updateNotifications() {
		
		let self = this;
	}
	
	callNotificationControlPanel() {

		if (!notificationControlPanel) {

			notificationControlPanel = new NotificationControlPanel();
		}

		if (notificationControlPanel.hidden()) {
			notificationControlPanel.show();
		} else {
			notificationControlPanel.hide();
		}
	}
}
