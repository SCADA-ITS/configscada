import Table from "../classes/Table.js";
import UserForm from "../classes/UserForm.js";
import {RULE} from "../data/GLOBAL_PROPERTIES.js";


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


export function users(){
	let widgetId=webix.uid();
	let btnDetailId= "btn_detail"+widgetId;
	let btnEditId= "btn_edit"+widgetId;
	let btnDeleteId= "btn_delete"+widgetId;


	function user_select(id){
		$$(btnDetailId).show();
		$$(btnEditId).show();
		$$(btnDeleteId).show();
		
		if (userPermissions !== "") {
			if (checkUserPermission(RULE.EDIT_USER)) {//Edit User
				console.log();
			} else {
				$$(btnEditId).disable();
				$$(btnDeleteId).disable();	
			}
		} else {
			$$(btnEditId).disable();
			$$(btnDeleteId).disable();
		}
	}
	
	function detail_user(id){
		let selectPlan = $$(widgetId).getSelectedId();
		let item = $$(widgetId).getItem(selectPlan);
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
		let selectPlan = $$(widgetId).getSelectedId();
		let item = $$(widgetId).getItem(selectPlan);
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
		let selectPlan = $$(widgetId).getSelectedId();
		let item = $$(widgetId).getItem(selectPlan);
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
			id:widgetId,
			"view":"datatable",
			"autoConfig":true,
			"select":true,
			"columns":[
				{ "id":"id", "header":$.i18n("user.d.id"), hidden: true, width:0},
				{ "id":"name", "header":[$.i18n("user.d.name"), {content:"textFilter"}], width:310, sort:"text" },
				{ "id":"fullName", "header":[$.i18n("user.d.fullname"), {content:"textFilter"}], width:310, sort:"text" },
				{ "id":"email", "header":[$.i18n("user.d.email"), {content:"textFilter"}], width:310, sort:"text" },
				{ "id":"language", "header":[$.i18n("user.d.language"), {content:"textFilter"}], width:200, sort:"text" },
				{ "id":"timezone", "header":[$.i18n("user.d.timezone"), {content:"textFilter"}], width:200, sort:"text" },
				{ "id":"role", "header":[$.i18n("user.d.role"), {content:"textFilter"}], "template":function(obj){ return $.i18n(obj.role.alias); }, width:250, sort:"text" },
			],
			on:{
				onItemClick: user_select,
			}
		};
	
	let table = new Table(graph, genericTablei18n ).getComponent();
	table.scrollY=true;


	let result ={
		rows:[
			table,
			{
				view: "toolbar",
				heigth: 50,
				//hidden:true,
				align:"right",
				rows: [{
				cols:[
					{ gravity: 8}, 
					{ view:"button", disabled:!checkUserPermission(RULE.EDIT_USER), align:"right", autowidth:true, type:"icon", tooltip:$.i18n("user.button.tooltip.addUser"), icon:"mdi mdi-plus", 
					click:new_user
					},
					{ view:"button", id: btnDetailId, align:"right", hidden:true, autowidth:true, type:"icon", tooltip:$.i18n("user.button.tooltip.details"), icon:"mdi mdi-format-align-justify", 
					click:detail_user
					},
					{ view:"button", id: btnEditId , type:"icon", hidden:true, icon:"mdi mdi-grease-pencil", width:50, tooltip:$.i18n("user.button.tooltip.edit") ,
					click: edit_user             
					},
					{ view:"button", id: btnDeleteId, align:"right", hidden:true, autowidth:true, type:"icon", tooltip:$.i18n("user.button.tooltip.delete"), icon:"wxi-trash",
					click:delete_user
					},
					//{ gravity: 4},
				]
				}]
			}
		]
	};
	return result;
}