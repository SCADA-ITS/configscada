import Alarms from "../classes/Alarms.js";

var graphAlarm =  webix.proxy(
	"GraphQL",`
	query { 
  getAlarms {
	  id
	  recognized
	  recognizedComments
	  activation
	  activationComments
	  deactivation
	  deactivationComments
      alarmConfig {id alias alarmLevel}
      sourceElement{alias}
      targetElement{alias} 
  }
}`);


function getDate(timestamp)
{
	return timestamp !== null ? new Date(timestamp).toISOString() : '-';
}

let genericTablei18n = {
		"id":"alarmsTable",
		"view":"datatable",
		"autoConfig":true,
		"select":true,
		"columns":[
			{ "id":"id", "header":"Id", hidden: true, width:200},
			{ "id":"activation", "header":"Activation", "template": function (obj) {return getDate(obj.activation)}, width:200},
			{ "id":"recognized", "header":"Recognized", "template": function (obj) {return getDate(obj.recognized)}, width:200},
			{ "id":"deactivation", "header":"Deactivation", "template": function (obj) {return getDate(obj.deactivation)}, width:200},
			{ "id":"alarmConfig", "header":"Alarm Config", "template": function(obj){ return obj.alarmConfig.alias; }, width:500},
			{ "id":"alarmLevel", "header":"Level", "template": function(obj){ return obj.alarmConfig.alarmLevel; }, width:100},
			{ "id":"sourceElement", "header":"Source","template": function(obj){ return obj.sourceElement.alias ; }, width:150},
			{ "id":"targetElement", "header":"Target", "template": function(obj){ return obj.targetElement.alias ; }, width:150},
		],
		url: function() {
			table.init();
		  }
	};

let table = new Alarms(graphAlarm, null );
//console.log(table.data);
//table.init();
table.scrollY=true;

export let alarms = {
	//rows:[
	//{
		//view:"pager", 
		//id:"pagerA",
	//},
	//{ 
		body:genericTablei18n //},
	//{}
	//]
};
