export let genericTable = {
	"id":"genericTable",
	"view":"datatable",
	"autoConfig":true,
	"select":true
};

export let menu_data_multi  = [
{ id: "structure", icon: "mdi mdi-view-column", value: $.i18n("menu.dashboard.title"), data:[
		{ id: "syn1", icon:"mdi mdi-circle", value: $.i18n("menu.dashboard.line")},
		{ id: "syn2", icon:"mdi mdi-circle", value: $.i18n("menu.dashboard.global")},
		{ id: "map", icon:"mdi mdi-circle", value: $.i18n("menu.dashboard.map")},
		{ id: "syn3", icon:"mdi mdi-circle", value: $.i18n("menu.dashboard.cts")},
		{ id: "syn4", icon:"mdi mdi-circle", value: $.i18n("menu.dashboard.unifilar_csa")},
		//{ id: "syn5", icon:"mdi mdi-circle", value: $.i18n("menu.dashboard.unifilar_csb")}
	]},
	{ id: "tables", icon: "mdi mdi-calendar", value:$.i18n("menu.tables.title"), data:[
		{ id: "equipments", icon:"mdi mdi-circle", value: $.i18n("menu.tables.equipments")},
		{ id: "users", icon:"mdi mdi-circle", value: $.i18n("menu.tables.users")},
		{ id: "roles", icon:"mdi mdi-circle", value: $.i18n("menu.tables.roles")},
		{ id: "alarmconfigs", icon:"mdi mdi-circle", value: $.i18n("menu.tables.alarmsconfig")}
	]},
	{ id: "alarms", icon: "mdi mdi-bell", value:$.i18n("menu.alarms.title")},
	{ id: "cctv", icon: "mdi mdi-video", value:$.i18n("menu.cctv.title")},
	{ id: "plans1", icon: "mdi mdi-clock", value:"Plans", data:[
		{ id: "plans", icon:"mdi mdi-circle", value: $.i18n("menu.plans.title")},
		{ id: "planschedule", icon:"mdi mdi-circle", value: $.i18n("menu.plans.schedule")}]},
	{ id: "grafana", icon: "mdi mdi-monitor", value:$.i18n("menu.grafana")},
	{ id: "jasper", icon: "mdi mdi-file", value:$.i18n("menu.jasper")},
	{ id: "usersmanager", icon: "mdi mdi-account", value:$.i18n("defaultheader.users.manager")}
		
];

export let toolbar_data_menu  = {
	id:"mainToolbar",
	view: "toolbar", 
	padding:3, 
	elements: [
		{ view: "icon", icon: "mdi mdi-menu"},
		{ view: "label", label: "Open Its"},
		{ gravity: 5 },
		{ id: "nameUser", view: "label", label: "", align:"right", maxWidth:350},
		{ id:"user", view: "icon", icon: "mdi mdi-account-circle", 	popup: {
			view: 'contextmenu',
			data: [
			  { id: 'logout', value: 'Logout' },
			],
			on: {
			  onMenuItemClick(id) {
				if (id === 'logout') {
					webix.message('Loging out...');

					webix.storage.session.get("url.auth.path");
					var paramUser = webix.storage.session.get("userId");
					webix.proxy("GraphQL", `query($rowId: String!){
						getUser(userId: $rowId){
							id
							name
							password
						}
					}`).load({
						rowId: paramUser
					}).then(data2 => {
						// const encodedString = btoa((data2.name + ':' + data2.password).toString('utf8')).toString('base64');
						// console.log(data2.name);
						// console.log(data2.password);
						// webix.ajax().headers({ 'Authorization': 'Basic ' + encodedString }).post(webix.storage.session.get("url.auth.path") + "logout").then(function(data){
							webix.storage.session.remove("loginSession");
							webix.storage.session.remove("app_user_language");
							webix.storage.session.remove("app_user_name");
							webix.storage.session.remove("app_user_permissions");
							window.location = "./login.html";
						// });
					});
				}
			  }
			}
		  }},
		{ id:"bell", view: "icon", icon: "mdi mdi-bell"},
		{ view: "icon", icon: "webix_icon wxi-pencil" },
		{ 
			id: "connectedFlag",
			view:"button", 
			type: "image", 
			image:"./img/icons/checkbox-blank-circle_grey.png", 
			width: 28,
			css:"webix_transparent", 
		}
	]
}

