import Table from "../classes/Table.js";
import RoleForm from "../classes/RoleForm.js";

var graph =  `GraphQL->{ 
	  getRoles{
	    id
	    preset
	    alias
	    description
	  }
}`;


function role_select(id){
	//let item = $$('usersTable').getItem(id);
	//console.log(item);
	$$("b_details_role").show();
	$$("b_edit_role" ).show();
	$$("b_delete_role").show();
}

function detail_role(id){
	let selectPlan = $$("rolesTable").getSelectedId();
	let item = $$('rolesTable').getItem(selectPlan);
	try {
		$$("role_win").destructor();
	} catch  {}

	let userForm = new RoleForm(item.id,null,0);
	webix
	  .ui({ 
		id: "role_win",
		view: "window",
		width: 400,
		move: true,
		close: true,
		position: "right",
		body: userForm.getComponent(),
	  })
	  .show();
}

function new_role(id){
	try {
		$$("role_win").destructor();
	} catch  {}
	let userForm = new RoleForm(null,null,2);
	webix
	  .ui({ 
		id: "role_win",
		view: "window",
		width: 400,
		move: true,
		close: true,
		position: "right",
		body: userForm.getComponent(),
	  })
	  .show();

}

function delete_role(id){
	let selectPlan = $$("rolesTable").getSelectedId();
	let item = $$('rolesTable').getItem(selectPlan);
	try {
		$$("role_win").destructor();
	} catch  {}

	let userForm = new RoleForm(item.id,null,3);
	webix
	  .ui({ 
		id: "role_win",
		view: "window",
		width: 400,
		move: true,
		close: true,
		position: "right",
		body: userForm.getComponent(),
	  })
	  .show();	
}

function edit_role(id){
	let selectPlan = $$("rolesTable").getSelectedId();
	let item = $$('rolesTable').getItem(selectPlan);
	try {
		$$("role_win").destructor();
	} catch  {}

	let userForm = new RoleForm(item.id,null,1);
	webix
	  .ui({ 
		id: "role_win",
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
		id:"rolesTable",
		"view":"datatable",
		"autoConfig":true,
		"select":true,
		"columns":[
			{ "id":"id", "header":$.i18n("rol.d.id"), hidden: true},
			{ "id":"alias", "header": [$.i18n("rol.d.alias"), {content:"textFilter"}], width:300,  sort:"text" },
			{ "id":"description", "header":[$.i18n("rol.d.description"), {content:"textFilter"}], width:300,  sort:"text" },
			{ "id":"preset", "header": [$.i18n("rol.d.preset"), {content:"textFilter"}], width:300,  sort:"text" },
		],
		on:{
			onItemClick: role_select,
		}
	};

let table = new Table(graph, genericTablei18n ).getComponent();
table.scrollY=true;

export let roles = {
	rows:[
		table,
		{
			view: "toolbar",
			id: "toolbar_role",
			heigth: 50,
			//hidden:true,
			align:"right",
			rows: [{
			  cols:[
				{ gravity: 8}, 
				{ view:"button", id: "b_new_role", align:"right", autowidth:true, type:"icon", tooltip:$.i18n("role.button.tooltip.addrole"), icon:"mdi mdi-plus", 
				  click:new_role
				},
				{ view:"button", id: "b_details_role", align:"right", hidden:true, autowidth:true, type:"icon", tooltip:$.i18n("role.button.tooltip.details"), icon:"mdi mdi-format-align-justify", 
				  click:detail_role
				},
				{ view:"button", id: "b_edit_role" , type:"icon", hidden:true, icon:"mdi mdi-grease-pencil", width:50, tooltip:$.i18n("role.button.tooltip.edit") ,
				  click: edit_role            
				},
				{ view:"button", id: "b_delete_role", align:"right", hidden:true, autowidth:true, type:"icon", tooltip:$.i18n("role.button.tooltip.delete"), icon:"wxi-trash",
				  click:delete_role
				},
				//{ gravity: 4},
			  ]
			}]
		}
	]
};
