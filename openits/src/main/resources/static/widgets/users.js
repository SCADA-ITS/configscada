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
			{ "id":"id", "header":$.i18n("user.d.id"), hidden: true, width:200},
			{ "id":"name", "header":$.i18n("user.d.name"), width:300, sort:"text" },
			{ "id":"fullName", "header":$.i18n("user.d.fullname"), width:300, sort:"text" },
			{ "id":"email", "header":$.i18n("user.d.email"), width:300, sort:"text" },
			{ "id":"language", "header":$.i18n("user.d.language"), sort:"text" },
			{ "id":"timezone", "header":$.i18n("user.d.timezone"), width:300, sort:"text" },
			{ "id":"isAdmin", "header":$.i18n("user.d.admin"), sort:"text" },
			{ "id":"role", "header":$.i18n("user.d.role"), "template":function(obj){ return $.i18n(obj.role.alias); }, width:200, sort:"text" }
		]
	};

let table = new Table(graph, genericTablei18n ).getComponent();
table.scrollY=true;

export let users = {
	rows:[table]
};
