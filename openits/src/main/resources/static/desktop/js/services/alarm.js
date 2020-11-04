const MODULE_PARAM_CONFIG = 2;

app.alarms = {};

function getDrawServiceAlarm(module, args, position) {

	let view = "view-svc-" + module.id;
	let toolbar = $$("toolbar");
	
	if (!webix.isUndefined(toolbar)) {
		
		toolbar.addView({ id:"service-alarm", view:"icon", badge:0, icon:"mdi mdi-bell", value:"Alert", css: "alarm-template", width: 75});
	}
}

function assingCallbackServiceAlarm(module, args) {
	
	loadConfig(module);
	updateStatistics(module, args)
}

function loadConfig(module) {
	
	if (module != undefined) {
		
		let moduleValue = module.moduleValues.filter(e => e.moduleParam.id === MODULE_PARAM_CONFIG);
		
		if (moduleValue != undefined) {
			
			app.alarms.config = JSON.parse(moduleValue[0].value);
			app.alarms.stats = {}; 
		}
	}
}

function updateStatistics() {
	
	updateActiveAlarms();
	updateNoRecognizedAlarms();
	updateMutedAlarms();
}

function updateActiveAlarms() {
	
	webix.ajax(appConfig.restUrl + app.alarms.config.stats.rest.active_alarms).then(function(data) {
							
		if (data.json() != undefined) {
			
			app.alarms.stats.active_alarms = data.json().value;
			$$("service-alarm").data.badge = app.alarms.stats.active_alarms;
			$$("service-alarm").refresh();
				
			app.util.stomp.connect(appConfig.stompUrl, app.alarms.config.stats.ws.active_alarms, null, on_message);

			function on_message(message) {
		
				var dataWS = JSON.parse(JSON.parse(message.body));
		
				console.log("S T O M P   O V E R   W E B S O C K E T   M E S S A G E");
				console.log(dataWS);
		
				$$("service-alarm").data.badge = dataWS.basicElementsVO[0].value;
				$$("service-alarm").refresh();
			}
			
		} 
	});
}

function updateNoRecognizedAlarms() {
	
	webix.ajax(appConfig.restUrl + app.alarms.config.stats.rest.no_recognize_alarms).then(function(data) {
							
		if (data.json() != undefined) {
			
			app.alarms.stats.no_recognize_alarms = data.json().value;
			app.util.stomp.connect(appConfig.stompUrl, app.alarms.config.stats.ws.no_recognize_alarms, null, on_message);

			function on_message(message) {
		
				var dataWS = JSON.parse(JSON.parse(message.body));
		
				console.log("S T O M P   O V E R   W E B S O C K E T   M E S S A G E");
				console.log(dataWS);
		
				app.alarms.stats.no_recognize_alarms = dataWS.basicElementsVO[0].value;
			}
			
		} 
	});
}

function updateMutedAlarms() {
	
	webix.ajax(appConfig.restUrl + app.alarms.config.stats.rest.muted_alarms).then(function(data) {
							
		if (data.json() != undefined) {
			
			app.alarms.stats.muted_alarms = data.json().value;
			app.util.stomp.connect(appConfig.stompUrl, app.alarms.config.stats.ws.muted_alarms, null, on_message);

			function on_message(message) {
		
				var dataWS = JSON.parse(JSON.parse(message.body));
		
				console.log("S T O M P   O V E R   W E B S O C K E T   M E S S A G E");
				console.log(dataWS);
		
				app.alarms.stats.muted_alarms = dataWS.basicElementsVO[0].value;
			}
			
		} 
	});
}
