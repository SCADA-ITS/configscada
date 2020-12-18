//import MainMenu from "./mainMenu.js";
import NotificationCenter from "./notificationCenter.js";
import {NotificationType}  from "./notificationCenter.js";

export default class MainToolbar {

	constructor() {

		//this.mainMenu = new MainMenu();
	}

	getView() {

		let view = {
			id: MAIN_TOOLBAR_ID,
			view: "toolbar",
			height: 46,
			css: "head-color main-toolbar",
			//padding: 3,
			elements: [
				{
					view: "label",
					label: "<img class='logo-project' src='../img/logo-project.svg'>",
					width: 175
				},
				//this.mainMenu.getView(),
				{ view: "template", css: "head-color main-toolbar", role: "placeholder", borderless: 1, minWidth: 500 },
				{ id: "nameUser", view: "label", label: "admin", align: "right", maxWidth: 350 },
				{
					id: "tb-notification-center-user", view: "icon", icon: "mdi mdi-account-circle",
					//, tooltip: i18n.t("LBL_USER"), 
					popup: {
						view: 'contextmenu',
						data: [
							{ id: 'logout', value: 'Logout' },
						],
						on: {
							onMenuItemClick(id) {
								if (id === 'logout') {
									webix.message('Loging out...');

									webix.storage.session.get("url.auth.path");
									var paramUser = webix.storage.session.get("userId");
									webix.proxy("GraphQL", `query($rowId: String!){
						getUser(userId: $rowId){
							id
							name
							password
						}
					}`).load({
										rowId: paramUser
									}).then(data2 => {
										// const encodedString = btoa((data2.name + ':' + data2.password).toString('utf8')).toString('base64');
										// console.log(data2.name);
										// console.log(data2.password);
										// webix.ajax().headers({ 'Authorization': 'Basic ' + encodedString }).post(webix.storage.session.get("url.auth.path") + "logout").then(function(data){
										webix.storage.session.remove("loginSession");
										webix.storage.session.remove("app_user_language");
										webix.storage.session.remove("app_user_name");
										webix.storage.session.remove("app_user_permissions");
										window.location = "./login.html";
										// });
									});
								}
							}
						}
					}
				},
				{ id: "tb-notification-center-alarms", view: "icon", icon: "mdi mdi-bell", tooltip: i18n.t("LBL_ALARM_CENTER") },
				{ id: "tb-notification-center-events", view: "icon", icon: "mdi mdi-comment", tooltip: i18n.t("LBL_NOTIFICATION_CENTER") },
				{ id: "tb-notification-center-config", view: "icon", icon: "mdi mdi-cog", tooltip: i18n.t("LBL_CONFIGURATION") }
			]
		};

		return view;
	}

	attachOnClickEvents() {
		
		var self = this;

		$$("tb-notification-center-user").attachEvent("onItemClick", function(id, e) {

			
		});

		$$("tb-notification-center-alarms").attachEvent("onItemClick", function(id, e) {

			self.callNotificationCenter(NotificationType.ALARMS);
		});

		$$("tb-notification-center-events").attachEvent("onItemClick", function(id, e) {

			self.callNotificationCenter(NotificationType.EVENTS);
		});

		$$("tb-notification-center-config").attachEvent("onItemClick", function(id, e) {

			
		});
	}

	callNotificationCenter(option) {

		if (!notificationCenter) {

			notificationCenter = new NotificationCenter(option);
		}

		if (notificationCenter.hidden()) {
			notificationCenter.show(option);
		} else {
			notificationCenter.hide();
		}
	}
}
