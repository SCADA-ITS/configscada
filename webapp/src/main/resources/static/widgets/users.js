import Table from "../classes/Table.js";

var graph =  `GraphQL->{ 
  getUsers{
    id
    name
    password
    isAdmin
    fullName
    email
    language
    timezone
    role{
      id
      alias
    }
  }
}`;

let genericTablei18n = {
		id:"usersTable",
		"view":"datatable",
		"autoConfig":true,
		"select":true,
		"columns":[
			{ "id":"id", "header":"Id", hidden: true, width:200},
			{ "id":"name", "header":"Name", width:300, sort:"text" },
			{ "id":"fullName", "header":"FullName", width:300, sort:"text" },
			{ "id":"email", "header":"Email", width:300, sort:"text" },
			{ "id":"language", "header":"Language", sort:"text" },
			{ "id":"timezone", "header":"Timezone", width:300, sort:"text" },
			{ "id":"isAdmin", "header":"Admin", sort:"text" },
			{ "id":"role", "header":"Role", "template":function(obj){ return $.i18n(obj.role.alias); }, width:200, sort:"text" }
		]
	};

let table = new Table(graph, genericTablei18n ).getComponent();
table.scrollY=true;

export let users = {
	rows:[table]
};
