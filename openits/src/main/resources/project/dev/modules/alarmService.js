import {util} from "../lib/util.js";
import GenericModule from "../modules/genericModule.js";
import AlarmControlPanel  from "../controllers/alarmControlPanel.js";

export default class AlarmService extends GenericModule {
	
	constructor(module, mod, args){
		
		super(module, null, args);
		
		this.module = module;
		this.mod = mod;
		this.args = args;
		
		this.stomp = {};
		this.alarms = {};
	}
	
	getView() {

		let toolbar = $$(MAIN_TOOLBAR_ID);
		
		if (!webix.isUndefined(toolbar)) {
			
			let iconAlarm = { 
				id: "icon-alarm-service", 
				view: "icon", 
				css: "alarm-template",
				//width: 75,
				badge:0, 
				icon: "mdi mdi-bell", 
				value:"Alert",
				tooltip: i18n.t("LBL_ALARMS") 
			} 
			
			toolbar.addView(iconAlarm);
		}
	}
	
	loadData() {
		
		let self = this;
		let moduleValue = this.module.moduleValues.find(e => e.moduleParam.id === PARAM_MODULE_TYPE_SERVICE_CONFIG);
		
		if (moduleValue != undefined) {
			
			$$("icon-alarm-service").attachEvent("onItemClick", function(id, e) {

				self.callAlarmControlPanel();
			});
			
			this.alarms.config = JSON.parse(moduleValue.value);
			this.alarms.stats = {};
			
			this.updateStatistics(); 
		}
	}
	
	updateStatistics() {
	
		this.updateActiveAlarms();
	}
	
	updateActiveAlarms() {
		
		let self = this;
		
		webix.ajax(appConfig.restUrl + this.alarms.config.stats.rest.active_alarms).then(function(data) {
								
			if (data.json() != undefined) {
				
				self.alarms.stats.active_alarms = data.json().value;
				$$("icon-alarm-service").data.badge = self.alarms.stats.active_alarms;
				$$("icon-alarm-service").refresh();
					
				self.stomp.client = util.stomp.connect(appConfig.stompUrl, self.alarms.config.stats.ws.active_alarms, null, on_message);
				
				function on_message(message) {
			
					var data = JSON.parse(JSON.parse(message.body));
			
					$$("icon-alarm-service").data.badge = data.basicElementsVO[0].value;
					$$("icon-alarm-service").refresh();
				}
			} 
		});
	}
	
	callAlarmControlPanel() {

		if (!alarmControlPanel) {

			alarmControlPanel = new AlarmControlPanel();
		}

		if (alarmControlPanel.hidden()) {
			alarmControlPanel.show();
		} else {
			alarmControlPanel.hide();
		}
	}
}
