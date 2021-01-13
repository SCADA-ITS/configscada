import { util } from "../lib/util.js";
import GenericModule from "../modules/genericModule.js";
import AlarmControlPanel from "../controllers/alarmControlPanel.js";
import Notification from "../component/notification.js";
import { NotificationType } from "../component/notification.js";

export default class AlarmService extends GenericModule {

	constructor(module, mod) {

		super(module, null, null);

		this.module = module;
		this.mod = mod;

		this.recentAlarmCount = 0;
		this.stomp = {};
		this.alarms = {};
	}

	getView() {

		let views = [];

		let iconAlarm = {
			id: "icon-alarm-service",
			view: "icon",
			css: "alarm-template",
			//width: 75,
			badge: 0,
			icon: "mdi mdi-bell",
			value: "Alert",
			tooltip: i18n.t("LBL_ALARMS")
		}

		views.push(iconAlarm);

		return views;
	}

	loadData() {

		let self = this;
		let moduleValue = this.module.moduleValues.find(e => e.moduleParam.id === PARAM_MODULE_TYPE_SERVICE_CONFIG);

		if (moduleValue != undefined) {

			if (!alarmControlPanel) {

				alarmControlPanel = new AlarmControlPanel();
			}

			$$("icon-alarm-service").attachEvent("onItemClick", function(id, e) {

				self.callAlarmControlPanel();
			});

			this.alarms.stats = {};

			if (moduleValue.value) {

				this.alarms.config = JSON.parse(moduleValue.value);
			}

			self.updateStatistics();
		}
	}

	updateStatistics() {

		this.updateActiveAlarms();
		this.updateNoRecognizedAlarms();
		this.updateMutedAlarms();
		this.updateAlarms();
	}

	updateActiveAlarms() {

		let self = this;

		webix.ajax(appConfig.restUrl + this.alarms.config.stats.rest.active_alarms).then(function(data) {

			if (data.json() != undefined) {

				self.alarms.stats.active_alarms = data.json().value;
				$$("icon-alarm-service").data.badge = self.alarms.stats.active_alarms;
				$$("icon-alarm-service").refresh();

				$$("alarm-control-panel-box-active").setHTML(`<span class="box-value">${self.alarms.stats.active_alarms}</span>`);
				$$("alarm-control-panel-box-active").refresh();

				util.stomp.connect(appConfig.stompUrl, self.alarms.config.stats.ws.active_alarms, null, on_message);

				function on_message(message) {

					var data = JSON.parse(JSON.parse(message.body));

					$$("icon-alarm-service").data.badge = data.basicElementsVO[0].value;
					$$("icon-alarm-service").refresh();

					$$("alarm-control-panel-box-active").setHTML(`<span class="box-value">${data.basicElementsVO[0].value}</span>`);
					$$("alarm-control-panel-box-active").refresh();
				}
			}
		});
	}

	updateNoRecognizedAlarms() {

		let self = this;

		webix.ajax(appConfig.restUrl + this.alarms.config.stats.rest.no_recognized_alarms).then(function(data) {

			if (data.json() != undefined) {

				self.alarms.stats.no_recognized_alarms = data.json().value;

				$$("alarm-control-panel-box-no-recognized").setHTML(`<span class="box-value">${self.alarms.stats.no_recognized_alarms}</span>`);
				$$("alarm-control-panel-box-no-recognized").refresh();

				util.stomp.connect(appConfig.stompUrl, self.alarms.config.stats.ws.no_recognized_alarms, null, on_message);

				function on_message(message) {

					var data = JSON.parse(JSON.parse(message.body));

					$$("alarm-control-panel-box-no-recognized").setHTML(`<span class="box-value">${data.basicElementsVO[0].value}</span>`);
					$$("alarm-control-panel-box-no-recognized").refresh();
				}
			}
		});
	}

	updateMutedAlarms() {

		let self = this;

		webix.ajax(appConfig.restUrl + this.alarms.config.stats.rest.muted_alarms).then(function(data) {

			if (data.json() != undefined) {

				self.alarms.stats.muted_alarms = data.json().value;

				$$("alarm-control-panel-box-muted").setHTML(`<span class="box-value">${self.alarms.stats.muted_alarms}</span>`);
				$$("alarm-control-panel-box-muted").refresh();

				util.stomp.connect(appConfig.stompUrl, self.alarms.config.stats.ws.muted_alarms, null, on_message);

				function on_message(message) {

					var data = JSON.parse(JSON.parse(message.body));

					$$("alarm-control-panel-box-muted").setHTML(`<span class="box-value">${data.basicElementsVO[0].value}</span>`);
					$$("alarm-control-panel-box-muted").refresh();
				}
			}
		});
	}

	updateAlarms() {

		let self = this;

		util.stomp.connect(appConfig.stompUrl, self.alarms.config.stats.ws.alarms, null, on_message);

		function on_message(message) {

			var data = JSON.parse(JSON.parse(message.body));

			data.basicElementsVO.forEach(be => {

				let date = undefined;

				let equipment = window.em.getByField(EntityType.ELEMENT, undefined, be.targetElement.id).alias;
				let alarmConfig = window.em.getByField(EntityType.ALARM_CONFIG, "id", be.alarmConfig.id);
				let alarmLevel = window.em.getByField(EntityType.ALARM_LEVEL, "id", alarmConfig.alarmLevel.id);

				if (alarmConfig && alarmLevel && equipment) {
					
					self.recentAlarmCount++;
				
					if (be.deactivation) {
						date = be.deactivation / 1000;
					}
					else {
						date = be.activation / 1000;
	
						if (alarmLevel.notification === 'true') {
							
							let notification = new Notification(NotificationType.ALARM, date, equipment, alarmConfig.alias, alarmLevel.sound, undefined);
						
							let winNotif = webix.ui(notification.getView());
							
							winNotif.show();	
						}
					}
	
					let alarm = {
						id: self.recentAlarmCount,
						alarmId: be.id,
						alarmConfig: alarmConfig,
						date: date,
						dateFormatted: util.toLocaleString(date),
						equipment: equipment,
						activation: be.activation,
						deactivation: be.deactivation,
						recognized: be.recognized
					};
	
					if (self.recentAlarmCount > 10) {
	
						$$("alarm-control-panel-recent-list").remove(self.recentAlarmCount - 10);
					}
	
					$$("alarm-control-panel-recent-list").add(alarm);
					$$("alarm-control-panel-recent-list").sort("#date#", "desc", "int");
	
					console.log(be);	
				}
				
			});
		}
	}

	callAlarmControlPanel() {

		if (alarmControlPanel.hidden()) {
			alarmControlPanel.show();
		} else {
			alarmControlPanel.hide();
		}
	}
}
