import { util } from "../lib/util.js";

export default class UserControlPanel {

	constructor(config) {

		this.config = config;
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

		if (this.config != null && this.config != undefined &&
			this.config.action != null && this.config.action != undefined &&
			this.config.action.settings != null && this.config.action.settings != undefined) {

			let module = util.module.getFromModuleAction(this.config.action.settings);

			if (module) {
				
				var userData = util.user.auth();
				let moduleAction = { id: this.config.action.settings };
				let args = { selectedPks: [userData.id] };

				util.module.open(module, moduleAction, VIEW_TYPE_WINDOW, null, args);

			}

		} else {

			util.ui.notImplementedMessage();
		}

	}

	settings() {

		if (this.config != null && this.config != undefined &&
			this.config.action != null && this.config.action != undefined &&
			this.config.action.changePassword != null && this.config.action.changePassword != undefined) {

			let module = util.module.getFromModuleAction(this.config.action.changePassword);

			if (module) {
				var userData = util.user.auth();
				let moduleAction = { id: this.config.action.changePassword };
				let args = { selectedPks: [userData.id] };

				util.module.open(module, moduleAction, VIEW_TYPE_WINDOW, null, args);

			}
		} else {

			util.ui.notImplementedMessage();
		}
	}

	recognizeAll() {

		if (this.config != null && this.config != undefined &&
			this.config.action != null && this.config.action != undefined &&
			this.config.action.recognizeAll != null && this.config.action.recognizeAll != undefined) {

			let module = util.module.getFromModuleAction(this.config.action.recognizeAll);
			if (module) {

				let moduleAction = { id: this.config.action.recognizeAll };

				let args = { selectedPks: [] };

				util.module.open(module, moduleAction, VIEW_TYPE_WINDOW, null, args);

			}

		} else {

			util.ui.notImplementedMessage();
		}
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

		let user_action_alarms = {
			view: "toolbar",
			css: "control-panel-button-toolbar",
			height: 50,
			elements: [
				{},
				{
					view: "button",
					id: "user-control-panel-alarms",
					width: 350,
					tooltip: i18n.t("LBL_ALARMS_RECOGNIZE"),
					type: "icon",
					icon: "webix_icon_btn mdi mdi-bell",
					hidden: true,
					on: {

						onItemClick() {

							self.recognizeAll();
						}
					}
				},
				{}
			],
			borderless: true
		};


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
					//					{
					//						view: "label",
					//						css: "control-panel-title",
					//						label: `<span class="title-text">${i18n.t("LBL_USER_INFORMATION")}</span>`,
					//						borderless: true 
					//					},
					{ height: 20 },
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
					{ height: 20 },
					{
						height: 20,
						borderless: true,
						cols: [
							{ width: 12 },
							{
								view: "template",
								borderless: true,
								css: "section-title",
								template: function() {
									return `<div>
												<h2 class="section-title-text">${i18n.t("LBL_PENDING_TASKS")}</h2>
											</div>`;
								}
							},
							{
								view: "template",
								borderless: true,
								css: "section-title",
								template: function() {
									return `<div class="section-title-divider"></div>`;
								}
							},
							{ width: 12 },
						]
					},
					{ height: 20 },
					user_action_alarms,
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

	updateData() {

		let alarmService = window.sm.getService(Services.ALARM_SERVICE);

		if (alarmService) {

			let stats = alarmService.getData().stats;

			if (stats) {

				if (stats.noRecognizedAlarms > 0) {

					$$("user-control-panel-alarms").show();
					$$("user-control-panel-alarms").config.label = stats.noRecognizedAlarms + ' ' + i18n.t("LBL_ALARMS_NO_RECOGNIZED");
					$$("user-control-panel-alarms").refresh();

					$$("icon-user-service").data.badge = " ";
					$$("icon-user-service").refresh();
				}
				else {

					$$("user-control-panel-alarms").hide();

					$$("icon-user-service").data.badge = "";
					$$("icon-user-service").refresh();
				}
			}
		}
	}
}
