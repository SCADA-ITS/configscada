import { util } from "../lib/util.js";
import GenericModule from "../modules/genericModule.js";
import UserControlPanel  from "../component/general/userControlPanel.js";
import MessageBox  from "../component/messageBox.js";
import SubscriptorService from "./subscriptorService.js";

export default class UserService extends GenericModule {
	
	constructor(module){
		
		super(module, null, null);
		
		this.module = module;
		this.config = module.config;
		
		this.userData = util.user.getUserData();
		this.keepAliveInterval = undefined;
	}
	
	destroy() {
	
		if (this.keepAliveInterval) {
			
			clearInterval(this.keepAliveInterval);	
		}
	}
	
	getView() {

		let views = [];
		
		let labelUser = { 
			id: "label-user-service",
			view: "label", 
			label: this.userData.name, 
			align: "right", 
			maxWidth: 350 
		};
				
		let iconUser = { 
			id: "icon-user-service", 
			view: "icon", 
			css: "user-template",
			badge: "",
			icon: "mdi mdi-account-circle", 
			value:"User",
			tooltip: i18n.t("LBL_USER") 
		};
			
		views.push(labelUser);
		views.push(iconUser);
		
		return views;
	}
	
	loadData() {
		
		let self = this;
		let subscriptorService = window.sm.getService(Services.SUBSCRIPTOR_SERVICE);
		
		if (!window.userControlPanel && subscriptorService) {

			window.userControlPanel = new UserControlPanel(self.config);
			
			if (!util.ui.isExternal() && $$("icon-user-service")) {
				
				$$("icon-user-service").attachEvent("onItemClick", function(id, e) {
	
					if (window.userControlPanel.hidden()) {
						window.userControlPanel.show();
					} else {
						window.userControlPanel.hide();
					}
				});	
			
				let userData = util.user.getUserData();
				let mainScreen = $$("mainscreen");
				
				if (userData) {
					
					self.keepAliveInterval = setInterval(function() {

						util.user.keepAlive(
							
							function(sessionData) {
								
								if (sessionData) {
									
									if (sessionData.access === LOGIN_MESSAGE.AUTHORIZED.id) {
										
										webix.storage.session.put(SESSION_STORAGE_SESSION_DATA, sessionData);	
									}
									else {
										
										if (self.keepAliveInterval) {
				
											clearInterval(self.keepAliveInterval);	
										}
										
										let labelMsg = LOGIN_MESSAGE[sessionData.access].label;
										
										subscriptorService.sendMessage(SubscriptorService.TYPE_SESSION_UNAUTHORIZED, "");

										util.ui.clearScreen();
										util.user.closeSession();
										
										(new MessageBox(MessageBox.WARN, i18n.t(labelMsg),  
											function onAccept() {
												
												util.user.redirectLogin();
											}
										)).show();
									}
								}
							},
							function(error) {
								
								subscriptorService.sendMessage(SubscriptorService.TYPE_SESSION_UNAUTHORIZED, "");
								
								if (self.keepAliveInterval) {
				
									clearInterval(self.keepAliveInterval);	
								}

								util.ui.clearScreen();
								util.user.closeSession();
								
								(new MessageBox(MessageBox.WARN, i18n.t("LBL_SESSION_WITHOUT_COMM"),  
									function onAccept() {
										
										
										util.user.redirectLogin();
									}
								)).show();
							}
						)
							
					}, self.config.keepAliveSecs * 1000);
					
					if (userData.sessionExpireTimeS > 0) {
					
						idle({
							onIdle: function () {
								
								subscriptorService.sendMessage(SubscriptorService.TYPE_SESSION_LOGOUT, "Exceeded idle time");
								
								util.ui.clearScreen();
								util.user.closeSession();
								
								if (self.keepAliveInterval) {
				
									clearInterval(self.keepAliveInterval);	
								}
								
								util.user.logout(
									
									function onResponse(userSession) {
										
										(new MessageBox(MessageBox.WARN, i18n.t("LBL_SESSION_CLOSED_BY_EXCEED_IDLE_TIME"),  
											function onAccept() {

												util.user.redirectLogin();	
											}
										)).show();
									}
								);
						  	},
							idle: userData.sessionExpireTimeS * 1000
						}).start();	
					}
				}
				
				window.addEventListener("beforeunload", function (e) {        
			      
			    	subscriptorService.sendMessage(SubscriptorService.TYPE_SESSION_LOGOUT, "Main closed");
			    });	
			}
		}
	}
}
