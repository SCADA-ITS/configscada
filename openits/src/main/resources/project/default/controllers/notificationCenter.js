export const NotificationType = Object.freeze({
	ALARMS: 1,
	EVENTS: 2
});

export default class NotificationCenter {

	constructor(option) {

		this.mainView = this.createView(option);
		this.option = option;
	}

	show(option) {

		if (!this.hidden()) {

			return;
		}
		
		$$("tabMain").setValue('tab_' + option);

		this.mainView.show();
	}

	hide() {

		this.mainView.hide();
	}

	hidden() {

		return this.mainView.config.hidden;
	}

	createView(option) {

		// for using the object inside callbacks
		let self = this;

		let head = {
			view: "toolbar",
			css: "notification-center-head",
			height: 35,
			elements: [
				{ view: "label", css: "notification-center-title", template: i18n.t("LBL_NOTIFICATION_CENTER")},
				{
					view: "icon",
					css: "notification-center-close-btn",
					icon: "mdi mdi-close",
					on: {
						onItemClick: function(id) {
							self.hide();
						}
					}
				}
			],
			borderless: 1
		};

		var tabbar = {
			view: "tabbar", id: "tabMain", type: "bottom", multiview: true, options: [
				{ value: `<span class='webix_icon mdi mdi-bell'></span><span class='notification-center-tab-option'>${i18n.t("LBL_ALARMS")}</span>`, id: 'tab_' + NotificationType.ALARMS },
				{ value: `<span class='webix_icon mdi mdi-comment'></span><span class='notification-center-tab-option'>${i18n.t("LBL_EVENTS")}</span>`, id: 'tab_' + NotificationType.EVENTS },
				{ value: `<span class='webix_icon mdi mdi-cog'></span><span class='notification-center-tab-option'>${i18n.t("LBL_SETTINGS")}</span>`, id: 'tab_' + NotificationType.SETTINGS }
			], height: 50
		};


		return webix.ui({
			view: "window",
			id: "windowNotificationCenter",
			move: true,
			css: "notification-center-main",
			hidden: true,
			head: head,
			body: {
				rows: [
					{
						height: 375,
						width: 500
					},
					tabbar		
				]	
			}, 
			position: function(state) {
				state.left = document.documentElement.clientWidth - 512;
				state.top = 67;
			},
			on: {
				onBeforeShow: function() {
					//					self.showToolbarActions(option);
				}
			}
		});

		//this.showToolbarActions(option);
	}
}
