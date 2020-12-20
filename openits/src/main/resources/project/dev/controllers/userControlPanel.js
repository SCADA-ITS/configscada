import {util} from "../lib/util.js";

export default class UserControlPanel {

	constructor() {

		this.mainView = this.createView();
	}

	show() {

		if (!this.hidden()) {

			return;
		}

		this.mainView.show();
	}

	hide() {

		this.mainView.hide();
	}

	hidden() {

		return this.mainView.config.hidden;
	}
	
	changePassword() {
		
		util.ui.notImplementedMessage();
	}
	
	settings() {
		
		util.ui.notImplementedMessage();
	}
	
	logout() {
		
		webix.modalbox({
			title: i18n.t("LBL_CLOSE_SESSION"),
			buttons: [i18n.t("LBL_YES"), i18n.t("LBL_NO")],
			width: 400,
			text: i18n.t("LBL_CONFIR_CLOSE_SESSION"),
			callback: function(result) {

				if (result === "0") {

					let response = webix.ajax().sync().post(appConfig.restUrl + "auth/logout");

					if (response != undefined) {

						let login = JSON.parse(response.responseText);

						if (login.access === "LOGOUT") {

							webix.storage.session.remove("loginSession");
							webix.storage.session.remove("app_user_language");
							webix.storage.session.remove("app_user_name");
							webix.storage.session.remove("app_user_permissions");
							webix.storage.session.remove(SESSION_STORAGE_USER_DATA);
							window.location = "./login.html";
						}
					}
				}
			}
		});
	}

	createView() {

		// for using the object inside callbacks
		let self = this;
		let panelWidth = 400;
		let userData = webix.storage.session.get(SESSION_STORAGE_USER_DATA);

		return webix.ui({
			view: "popup",
			id: "userControlPanel",
			move: true,
			css: "control-panel-main",
			hidden: true,
			height: document.documentElement.clientHeight - 147,
			width: panelWidth,
			body: {
				rows: [
					{
						height: 10,
						borderless: true
					},
					{
						view: "label",
						label: `<span class="user-panel-title">${i18n.t("LBL_USER_INFORMATION")}</span>`,
						borderless: true 
					},
					{
						view: "template", 
						css: "user-avatar",
						template: function() {
														
							return `<img src="https://avatars.dicebear.com/4.5/api/initials/${userData.name}.svg"/>`;							
						},
						borderless: true
 
					},
					{
						view: "label",
						label: `<span class="user-fullname">${userData.fullName}</span>`,
						borderless: true 
					},
					{
						view: "label",
						label: `<span class="user-email">${userData.email}</span>`,
						borderless: true 
					},
					{
					},
					{
						view: "toolbar",
						css: "control-panel-main-toolbar",
						height: 75,
						elements: [
							{},
							{
							    view: "button",
								width: 125, 
								label: i18n.t("LBL_CHANGE_PASSWORD"),
								tooltip: i18n.t("LBL_CHANGE_PASSWORD_DESCRIPTION"),
							    type: "iconTop", 
								icon: "mdi mdi-key-variant", 
								on: {
								
									onItemClick() {
										
										self.changePassword();
									}
								}
							},
							{
							    view: "button",
								width: 125, 
								label: i18n.t("LBL_SETTINGS"),
								tooltip: i18n.t("LBL_SETTINGS_DESCRIPTION"),
							    type: "iconTop", 
								icon: "mdi mdi-cog", 
								on: {
								
									onItemClick() {
										
										self.settings();
									}
								}
							},
							{
							    view: "button",
								width: 125, 
								label: i18n.t("LBL_LOGOUT"),
								tooltip: i18n.t("LBL_LOGOUT_DESCRIPTION"), 
							    type: "iconTop", 
								icon: "mdi mdi-exit-to-app", 
								on: {
								
									onItemClick() {
										
										self.logout();
									}
								}
							}
						],
						borderless: true 
					}
				]	
			}, 
			position: function(state) {
				state.left = document.documentElement.clientWidth - panelWidth - 20;
				state.top = 96;
			},
			on: {
				onBeforeShow: function() {
					
					this.config.height = document.documentElement.clientHeight - 147; 
					this.adjust(); 
				}
			}
		});
	}
}
