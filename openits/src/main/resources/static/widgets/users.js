import Table from "../classes/Table.js";
import UserForm from "../classes/UserForm.js";

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

function user_select(id){
	//let item = $$('usersTable').getItem(id);
	//console.log(item);
	$$("b_details_user").show();
	$$("b_edit_user" ).show();
	$$("b_delete_user").show();
}

function detail_user(id){
	let selectPlan = $$("usersTable").getSelectedId();
	let item = $$('usersTable').getItem(selectPlan);
	try {
		$$("user_win").destructor();
	} catch  {}

	let userForm = new UserForm(item.id,null,0);
	webix
	  .ui({ 
		id: "user_win",
		view: "window",
		width: 400,
		move: true,
		close: true,
		position: "right",
		body: userForm.getComponent(),
	  })
	  .show();
}

function new_user(id){
	try {
		$$("user_win").destructor();
	} catch  {}
	let userForm = new UserForm(null,null,2);
	webix
	  .ui({ 
		id: "user_win",
		view: "window",
		width: 400,
		move: true,
		close: true,
		position: "right",
		body: userForm.getComponent(),
	  })
	  .show();

}

function delete_user(id){
	let selectPlan = $$("usersTable").getSelectedId();
	let item = $$('usersTable').getItem(selectPlan);
	try {
		$$("user_win").destructor();
	} catch  {}

	let userForm = new UserForm(item.id,null,3);
	webix
	  .ui({ 
		id: "user_win",
		view: "window",
		width: 400,
		move: true,
		close: true,
		position: "right",
		body: userForm.getComponent(),
	  })
	  .show();	
}

function edit_user(id){
	let selectPlan = $$("usersTable").getSelectedId();
	let item = $$('usersTable').getItem(selectPlan);
	try {
		$$("user_win").destructor();
	} catch  {}

	let userForm = new UserForm(item.id,null,1);
	webix
	  .ui({ 
		id: "user_win",
		view: "window",
		width: 400,
		move: true,
		close: true,
		position: "right",
		body: userForm.getComponent(),
	  })
	  .show();
}

let genericTablei18n = {
		id:"usersTable",
		"view":"datatable",
		"autoConfig":true,
		"select":true,
		"columns":[
			{ "id":"id", "header":$.i18n("user.d.id"), hidden: true, width:0},
			{ "id":"name", "header":$.i18n("user.d.name"), width:300, sort:"text" },
			{ "id":"fullName", "header":$.i18n("user.d.fullname"), width:300, sort:"text" },
			{ "id":"email", "header":$.i18n("user.d.email"), width:300, sort:"text" },
			{ "id":"language", "header":$.i18n("user.d.language"), width:200, sort:"text" },
			{ "id":"timezone", "header":$.i18n("user.d.timezone"), width:150, sort:"text" },
			{ "id":"isAdmin", "header":$.i18n("user.d.admin"), width:200, sort:"text" },
			{ "id":"role", "header":$.i18n("user.d.role"), "template":function(obj){ return $.i18n(obj.role.alias); }, width:200, sort:"text" },
		],
		on:{
			onItemClick: user_select,
		}
	};

let table = new Table(graph, genericTablei18n ).getComponent();
table.scrollY=true;

export let users = {
	rows:[
		table,
		{
			view: "toolbar",
			id: "toolbar_user",
			heigth: 50,
			//hidden:true,
			align:"right",
			rows: [{
			  cols:[
				{ gravity: 8}, 
				{ view:"button", id: "b_new_user", align:"right", autowidth:true, type:"icon", tooltip:$.i18n("user.button.tooltip.addUser"), icon:"mdi mdi-plus", 
				  click:new_user
				},
				{ view:"button", id: "b_details_user", align:"right", hidden:true, autowidth:true, type:"icon", tooltip:$.i18n("user.button.tooltip.details"), icon:"mdi mdi-format-align-justify", 
				  click:detail_user
				},
				{ view:"button", id: "b_edit_user" , type:"icon", hidden:true, icon:"mdi mdi-grease-pencil", width:50, tooltip:$.i18n("user.button.tooltip.edit") ,
				  click: edit_user             
				},
				{ view:"button", id: "b_delete_user", align:"right", hidden:true, autowidth:true, type:"icon", tooltip:$.i18n("user.button.tooltip.delete"), icon:"wxi-trash",
				  click:delete_user
				},
				//{ gravity: 4},
			  ]
			}]
		}
	]
};
