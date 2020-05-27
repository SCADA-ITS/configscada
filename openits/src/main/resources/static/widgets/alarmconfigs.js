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
			{ "id":"id", "header": $.i18n("alarmsconfig.d.id"), hidden: true},
			{ "id":"alias", "header": [$.i18n("alarmsconfig.d.alias"), {content:"textFilter"}], width:300, sort:"text" },
			{ "id":"description", "header": [$.i18n("alarmsconfig.d.description"), {content:"textFilter"}], width:300,  sort:"text" },
			{ "id":"alarmType", "header": [$.i18n("alarmsconfig.d.type"), {content:"textFilter"}], adjust:true,  sort:"int" },
			{ "id":"alarmLevel", "header": [$.i18n("alarmsconfig.d.level"), {content:"textFilter"}], adjust:true,  sort:"int" },
			{ "id":"element", "header": [$.i18n("alarmsconfig.d.element"), {content:"textFilter"}], width:300,  sort:"text", "template": function (obj) {return obj.element ? obj.element.alias : "-"} },
			{ "id":"elementType", "header": [$.i18n("alarmsconfig.d.elementtype"), {content:"textFilter"}], width:300, sort:"text", "template": function (obj) {return obj.elementType ? obj.elementType.alias : "-"} },
			{ "id":"mine", "header": [$.i18n("alarmsconfig.d.mine"), {content:"textFilter"}], adjust:true,  sort:"int" },
			{ "id":"treatment", "header": [$.i18n("alarmsconfig.d.treatment"), {content:"textFilter"}], adjust:true,  sort:"int"}
		]
	};

let table = new Table(graph, genericTablei18n ).getComponent();
table.scrollY=true;

export let alarmconfigs= {
	rows:[table]
};
