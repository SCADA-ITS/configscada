import { util } from "../lib/util.js";
import GenericModule from "../modules/genericModule.js";
import UserControlPanel  from "../component/general/userControlPanel.js";
import MessageBox  from "../component/messageBox.js";
import SubscriptorService from "./subscriptorService.js";
import EntitiesManager from "../managers/entitiesManager.js";

export default class UserService extends GenericModule {
	
	constructor(module){
		
		super(module, null, null);
		
		this.module = module;
		this.config = module.config;
		
		new MessageBox(MessageBox.WARN, null, null, null);
		
		this.userData = util.user.getUserData();
		this.keepAliveInterval = undefined;
	}
	
	destroy() {
	
		if (this.keepAliveInterval) {
			
			clearInterval(this.keepAliveInterval);	
		}
	}
	
	getView() {

		let self = this;
		let views = [];
		let cssStyleInvertUser = (window.lightMode) ? "user-template light-templates" : "user-template";
		let calculatedWidth;
		let webixToolbarElem = document.querySelector('.webix_toolbar');
		
		let lblUserName = self.userData.name;
		let lblUserArea = undefined;
		let lblUserAreaDescription = undefined;
		
		const sessionData = util.user.getSessionData();
		
		if (sessionData && sessionData.managementAreaId) {
			
			if (Array.isArray(sessionData.user.managementAreas)) {
				
				const managementArea = sessionData.user.managementAreas.find(e => e.id === sessionData.managementAreaId);
				
				if (managementArea) {
					
					lblUserArea = managementArea.alias;
					lblUserAreaDescription = managementArea.description;  
				}
			}
		}
		
		if (webixToolbarElem) {
			
			let webixToolbarStyle = getComputedStyle(webixToolbarElem);
			
			const calculatedWidthUserName = util.ui.calculateTextWidth(lblUserName, webixToolbarStyle.fontFamily, webixToolbarStyle.fontSize);
			
			calculatedWidth = calculatedWidthUserName;
			
			if (lblUserArea) {
				
				const calculatedWidthUserArea = util.ui.calculateTextWidth(lblUserArea, webixToolbarStyle.fontFamily, webixToolbarStyle.fontSize);
				
				if (calculatedWidthUserArea > calculatedWidth) {
					
					calculatedWidth = calculatedWidthUserArea;
				}	
			}
				
		}

		let cssStyleInvertTimer = (window.lightMode) ? "time-template light-templates" : "time-template";

		let labelUser = {
			view: "template",
			css: cssStyleInvertTimer,
			maxWidth: calculatedWidth,
			template: function() {
				
				let html = `<div class='toolbar-user-single' title='${lblUserName}'>${lblUserName}</div>`;
				 
				if (lblUserArea) {
					
					html = `<div class='toolbar-user' title='${lblUserName}'>${lblUserName}</div><div class='toolbar-user' title='${lblUserAreaDescription ?? lblUserArea}'>${lblUserArea}</div>`;
				}
				
				return html;
			} 
		}
				
		let iconUser = { 
			id: "icon-user-service", 
			view: "icon", 
			css: cssStyleInvertUser,
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
		
		if (subscriptorService) {
			
			if (!util.ui.isExternal() && $$("icon-user-service")) {
				
				window.userControlPanel = new UserControlPanel(self.config);
				
				$$("icon-user-service").attachEvent("onItemClick", function(id, e) {
	
					if (window.userControlPanel.hidden()) {
						window.userControlPanel.show();
					} else {
						window.userControlPanel.hide();
					}
				});	
			
				let userData = util.user.getUserData();
				
				if (userData) {
					
					self.keepAliveInterval = setInterval(function() {

						util.user.keepAlive(
							
							function(sessionData) {
								
								if (sessionData) {
									
									if (sessionData.access === LOGIN_MESSAGE.AUTHORIZED.id) {
										
										webix.storage.session.put(SESSION_STORAGE_SESSION_DATA, sessionData);
										
										changeEntity(sessionData.entitiesChanged);
									}
									else {
										
										if (self.keepAliveInterval) {
				
											clearInterval(self.keepAliveInterval);	
										}
										
										let labelMsg = LOGIN_MESSAGE[sessionData.access].label;
										
										subscriptorService.sendMessage(SubscriptorService.TYPE_SESSION_UNAUTHORIZED, "");

										util.ui.clearScreen();
										util.user.closeSession();
										
										(new MessageBox(MessageBox.WARN, i18n.t(labelMsg), null,
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
								
								(new MessageBox(MessageBox.WARN, i18n.t("LBL_SESSION_WITHOUT_COMM"), null,  
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
										
										(new MessageBox(MessageBox.WARN, i18n.t("LBL_SESSION_CLOSED_BY_EXCEED_IDLE_TIME"), null,  
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
		
		function changeEntity(entitiesChanged) {
			
			if (Array.isArray(entitiesChanged) && entitiesChanged.length > 0) {
				
				entitiesChanged.forEach(entitiesChange => {
					
					let entityType = window.em.getEntityTypeById(entitiesChange.entityTypeId);
					
					if (entityType) {
						
						if (entitiesChange.operation === EntitiesManager.ACTION_CREATED) {
						
							window.em.add(entityType, entitiesChange.id, entitiesChange.entity);
						}
						else if (entitiesChange.operation === EntitiesManager.ACTION_UPDATED) {
							
							window.em.update(entityType, entitiesChange.id, entitiesChange.entity);
						}
						else if (entitiesChange.operation === EntitiesManager.ACTION_DELETED) {
							
							window.em.delete(entityType, entitiesChange.id);
						}	
					}
				});
			}
		}
	}
}
