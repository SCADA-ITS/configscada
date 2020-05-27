export let genericTable = {
	"id":"genericTable",
	"view":"datatable",
	"autoConfig":true,
	"select":true
};

export let menu_data_multi  = [
{ id: "structure", icon: "mdi mdi-view-column", value: $.i18n("menu.dashboard.title"), data:[
		{ id: "metro", icon:"mdi mdi-circle", value: $.i18n("menu.dashboard.line")},
		{ id: "global", icon:"mdi mdi-circle", value: $.i18n("menu.dashboard.global")},
		{ id: "map", icon:"mdi mdi-circle", value: $.i18n("menu.dashboard.map")}
	]},
	{ id: "tables", icon: "mdi mdi-calendar", value:$.i18n("menu.tables.title"), data:[
		{ id: "equipments", icon:"mdi mdi-circle", value: $.i18n("menu.tables.equipments")},
		{ id: "users", icon:"mdi mdi-circle", value: $.i18n("menu.tables.users")},
		{ id: "roles", icon:"mdi mdi-circle", value: $.i18n("menu.tables.roles")},
		{ id: "alarmconfigs", icon:"mdi mdi-circle", value: $.i18n("menu.tables.alarmsconfig")}
	]},
	{ id: "alarms", icon: "mdi mdi-bell", value:$.i18n("menu.alarms.title")},
	{ id: "plans", icon: "mdi mdi-clock", value:$.i18n("menu.plans.title")},
	{ id: "grafana", icon: "mdi mdi-monitor", value:$.i18n("menu.grafana")},
	{ id: "jasper", icon: "mdi mdi-file", value:$.i18n("menu.jasper")}	
];

export let toolbar_data_menu  = {
	id:"mainToolbar",
	view: "toolbar", 
	padding:3, 
	elements: [
		{ view: "icon", icon: "mdi mdi-menu"},
		{ view: "label", label: "Open Its"},
		{ gravity: 20 },
		{ id: "nameUser", view: "label", label: ""},
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

