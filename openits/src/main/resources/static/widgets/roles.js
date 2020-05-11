import Table from "../classes/Table.js";

var graph =  `GraphQL->{ 
	  getRoles{
	    id
	    preset
	    alias
	    description
	  }
}`;

let genericTablei18n = {
		id:"rolesTable",
		"view":"datatable",
		"autoConfig":true,
		"select":true,
		"columns":[
			{ "id":"id", "header":$.i18n("rol.d.id"), hidden: true},
			{ "id":"alias", "header":$.i18n("rol.d.alias"),width:300,  sort:"text" },
			{ "id":"description", "header":$.i18n("rol.d.description"), width:300,  sort:"text" },
			{ "id":"preset", "header":$.i18n("rol.d.preset"), width:300,  sort:"text" },
		]
	};

let table = new Table(graph, genericTablei18n ).getComponent();
table.scrollY=true;

export let roles = {
	rows:[table]
};
