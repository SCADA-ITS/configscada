export let genericTable = {
	"id":"genericTable",
	"view":"datatable",
	"autoConfig":true,
	"select":true
};

export let menu_data_multi  = [
{ id: "structure", icon: "mdi mdi-view-column", value: $.i18n("menu.dashboard.title"), data:[
	{ id: "bratislava", icon:"mdi mdi-circle", value: $.i18n("defaultheader.dashboard")},
	{ id: "cabinets", icon:"mdi mdi-circle", value: $.i18n("defaultheader.cabinets")}
	]},
	{ id: "tables", icon: "mdi mdi-calendar", value:$.i18n("menu.tables.title"), data:[
		{ id: "equipments", icon:"mdi mdi-circle", value: $.i18n("defaultheader.equipment")},
		{ id: "users", icon:"mdi mdi-circle", value: $.i18n("defaultheader.users")},
		{ id: "roles", icon:"mdi mdi-circle", value: $.i18n("menu.tables.roles")},
		{ id: "alarmconfigs", icon:"mdi mdi-circle", value: $.i18n("menu.tables.alarmsconfig")}
	]},
	{ id: "alarms", icon: "mdi mdi-bell", value:$.i18n("defaultheader.managementAlarms")},
	{ id: "jasper", icon: "mdi mdi-file", value:$.i18n("menu.jasper")}	
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
				  webix.storage.session.remove("loginSession");
				  webix.storage.session.remove("app_user_language");
				  webix.storage.session.remove("app_user_name");
				  webix.storage.session.remove("app_user_permissions");
				  window.location = "./login.html";
				}
			  }
			}
		  }},
		{ id:"bell", view: "icon", icon: "mdi mdi-bell"},
		{ view: "icon", icon: "webix_icon wxi-pencil" }
	]
}

