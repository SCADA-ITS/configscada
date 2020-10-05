var timeElapsed = -1;
var lastUTCSeconds = 0;

function getDrawServiceAlarm(module, args) {

	let view = "view-svc-" + module.id;
	let toolbar = $$("toolbar");
	
	if (!webix.isUndefined(toolbar)) {
		
		toolbar.addView({ view:"icon", badge:0, icon:"mdi mdi-bell", value:"Alert", css: "alarm-template", width: 75});
	}
}

function assingCallbackServiceAlarm(module, args) {

}
