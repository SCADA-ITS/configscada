import { util } from "../lib/util.js";
import GenericModule from "../modules/genericModule.js";
import AlarmControlPanel from "../component/general/alarmControlPanel.js";
import Notification from "../component/notification.js";
import { NotificationType } from "../component/notification.js";

const LATEST_ALARMS_MAX = 10;

export default class AlarmService extends GenericModule {

	constructor(module, mod) {

		super(module, null, null);

		this.module = module;
		this.config = module.config;
		this.mod = mod;

		//this.recentAlarmCount = 0;
		this.stomp = {};
		this.alarms = {
			config: undefined,
			data: {
				stats: {
					activeAlarms: 0,
					noRecognizedAlarms: 0,
					filteredAlarms: 0
				},
				latest: []
			}
		};
	}
	
	getData() {
		
		return this.alarms.data;
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
			
			if (!window.alarmControlPanel) {

				window.alarmControlPanel = new AlarmControlPanel(this.config);
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
		this.updateFilteredAlarms();
		this.updateAlarms();
	}

	updateActiveAlarms() {

		let self = this;

		webix.ajax(appConfig.restUrl + this.alarms.config.stats.rest.active_alarms).then(function(data) {

			if (data.json() != undefined) {
				
				let activeAlarms = data.json().value;

				self.alarms.data.stats.activeAlarms = activeAlarms;
//				$$("icon-alarm-service").data.badge = activeAlarms;
//				$$("icon-alarm-service").refresh();
				
				window.alarmControlPanel.updateData();

//				$$("alarm-control-panel-box-active").setHTML(`<span class="box-value">${activeAlarms}</span>`);
//				$$("alarm-control-panel-box-active").refresh();

				util.stomp.connect(appConfig.stompUrl, self.alarms.config.stats.ws.active_alarms, null, on_message);

				function on_message(message) {

					var dat = JSON.parse(JSON.parse(message.body));
					
					self.alarms.data.stats.activeAlarms = dat.basicElementsVO[0].value;

//					$$("icon-alarm-service").data.badge = dat.basicElementsVO[0].value;
//					$$("icon-alarm-service").refresh();
					
					window.alarmControlPanel.updateData();

//					$$("alarm-control-panel-box-active").setHTML(`<span class="box-value">${dat.basicElementsVO[0].value}</span>`);
//					$$("alarm-control-panel-box-active").refresh();
				}
			}
		});
	}

	updateNoRecognizedAlarms() {

		let self = this;

		webix.ajax(appConfig.restUrl + this.alarms.config.stats.rest.no_recognized_alarms).then(function(data) {

			if (data.json() != undefined) {
				
				let noRecognizedAlarms = data.json().value;

				self.alarms.data.stats.noRecognizedAlarms = noRecognizedAlarms;
				
				window.alarmControlPanel.updateData();
				window.userControlPanel.updateData();

//				$$("alarm-control-panel-box-no-recognized").setHTML(`<span class="box-value">${noRecognizedAlarms}</span>`);
//				$$("alarm-control-panel-box-no-recognized").refresh();

				util.stomp.connect(appConfig.stompUrl, self.alarms.config.stats.ws.no_recognized_alarms, null, on_message);

				function on_message(message) {

					var dat = JSON.parse(JSON.parse(message.body));
					
					self.alarms.data.stats.noRecognizedAlarms = dat.basicElementsVO[0].value;
					
					window.alarmControlPanel.updateData();
					window.userControlPanel.updateData();

//					$$("alarm-control-panel-box-no-recognized").setHTML(`<span class="box-value">${dat.basicElementsVO[0].value}</span>`);
//					$$("alarm-control-panel-box-no-recognized").refresh();
				}
			}
		});
	}

	updateFilteredAlarms() {

		let self = this;

		webix.ajax(appConfig.restUrl + this.alarms.config.stats.rest.filtered_alarms).then(function(data) {

			if (data.json() != undefined) {
				
				let filteredAlarms = data.json().value;

				self.alarms.data.stats.filteredAlarms = filteredAlarms;
				
				window.alarmControlPanel.updateData();


				util.stomp.connect(appConfig.stompUrl, self.alarms.config.stats.ws.filtered_alarms, null, on_message);

				function on_message(message) {

					var dat = JSON.parse(JSON.parse(message.body));
					
					self.alarms.data.stats.filteredAlarms = dat.basicElementsVO[0].value;
					
					window.alarmControlPanel.updateData();

				}
			}
		});
	}

	updateAlarms() {

		let self = this;

		let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

		if (stompSubscriptorService) {

			let dataSource = {
				dataSourceValues: [
					{ value: appConfig.stompUrl },
					{ value: self.alarms.config.stats.ws.alarms }
				]
			};

			this.idStompSubscriptor = stompSubscriptorService.subscribe(dataSource, onMessage);
		}
		
		let isFiltered = function(data) {
			
			let timeService = window.sm.getService(Services.TIME_SERVICE);
			
			return (data.filteredUntil && timeService.getDateTime() < data.filteredUntil / 1000);
		}
		
		let isRecognized = function(data) {
			
			let timeService = window.sm.getService(Services.TIME_SERVICE);
			
			return (data.recognized && timeService.getDateTime() > data.recognized / 1000);
		}

		function onMessage(be) {

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

					if (alarmLevel.notification === 'true' && !isFiltered(be) && !isRecognized(be)) {

						let notification = new Notification(NotificationType.ALARM, date, equipment, alarmConfig.alias, alarmLevel.sound, undefined);

						let winNotif = webix.ui(notification.getView());

						winNotif.show();
					}
				}

				let alarm = {
					id: webix.uid(),
					alarmId: be.id,
					alarmConfig: alarmConfig,
					date: date,
					dateFormatted: util.toLocaleString(date),
					equipment: equipment,
					activation: be.activation,
					deactivation: be.deactivation,
					recognized: be.recognized
				};
				
				self.alarms.data.latest.push(alarm);
				
				// Sort descending
				self.alarms.data.latest.sort((a, b) => (a.date < b.date) ? 1 : ((b.date < a.date) ? -1 : 0));
				
				if (Array.isArray(self.alarms.data.latest) && self.alarms.data.latest.length > LATEST_ALARMS_MAX) {
					
					self.alarms.data.latest.splice(LATEST_ALARMS_MAX, 1);
				}
				
				window.alarmControlPanel.updateData();

//				if (self.recentAlarmCount > 10) {
//
//					$$("alarm-control-panel-recent-list").remove(self.recentAlarmCount - 10);
//				}
//
//				$$("alarm-control-panel-recent-list").add(alarm);
//				$$("alarm-control-panel-recent-list").sort("#date#", "desc", "int");

				//console.log(be);	
			}
		}
	}

	callAlarmControlPanel() {
		
		if (window.alarmControlPanel.hidden()) {
			window.alarmControlPanel.show();
		} else {
			window.alarmControlPanel.hide();
		}
	}
}
