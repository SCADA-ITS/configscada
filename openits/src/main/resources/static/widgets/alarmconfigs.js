import Table from "../classes/Table.js";

var graph =  `GraphQL->{ 
  getAlarmConfigs {
      id
      alias
      description
      alarmType
      alarmLevel
      element{id alias}
      elementType{id alias}
      mine
      treatment
  }
}`;

let genericTablei18n = {
		id:"alarmconfigsTable",
		"view":"datatable",
		"autoConfig":true,
		"select":true,
		"columns":[
			{ "id":"id", "header":"Id", hidden: true},
			{ "id":"alias", "header":"Alias", width:300, sort:"text" },
			{ "id":"description", "header":"Description", width:300,  sort:"text" },
			{ "id":"alarmType", "header":"Type", adjust:true,  sort:"int" },
			{ "id":"alarmLevel", "header":"Level", adjust:true,  sort:"int" },
			{ "id":"element", "header":"Element", width:300,  sort:"text", "template": function (obj) {return obj.element ? obj.element.alias : "-"} },
			{ "id":"elementType", "header":"Element Type", width:300, adjust:true,  sort:"text", "template": function (obj) {return obj.elementType ? obj.elementType.alias : "-"} },
			{ "id":"mine", "header":"Mine", adjust:true,  sort:"int" },
			{ "id":"treatment", "header":"Treatment", adjust:true,  sort:"int" },
		]
	};

let table = new Table(graph, genericTablei18n ).getComponent();
table.scrollY=true;

export let alarmconfigs= {
	rows:[table]
};
