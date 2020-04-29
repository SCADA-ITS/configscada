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
			{ "id":"id", "header":"Id", hidden: true},
			{ "id":"alias", "header":"Alias",width:300,  sort:"text" },
			{ "id":"description", "header":"Description", width:300,  sort:"text" },
			{ "id":"preset", "header":"Preset", width:300,  sort:"text" },
		]
	};

let table = new Table(graph, genericTablei18n ).getComponent();
table.scrollY=true;

export let roles = {
	rows:[table]
};
