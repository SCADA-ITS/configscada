export let genericTable = {
	"id":"genericTable",
	"view":"datatable",
	"autoConfig":true,
	"select":true
};

export let menu_data_multi  = [
	{ id: "structure", icon: "mdi mdi-view-column", value:"Dashboard", data:[
		{ id: "metro", icon:"mdi mdi-circle", value: "Lineal"},
		{ id: "global", icon:"mdi mdi-circle", value: "Global"}	
	]},
	{ id: "tables", icon: "mdi mdi-calendar", value:"Tables", data:[
		{ id: "equipments", icon:"mdi mdi-circle", value: "Equipments"},
		{ id: "users", icon:"mdi mdi-circle", value: "Users"},
		{ id: "roles", icon:"mdi mdi-circle", value: "Roles"},
		{ id: "alarmconfigs", icon:"mdi mdi-circle", value: "Alarms Config"}
	]},
	{ id: "alarms", icon: "mdi mdi-bell", value:"Alarms"},
	{ id: "plans", icon: "mdi mdi-clock", value:"Gestor de Planes"},
	{ id: "grafana", icon: "mdi mdi-monitor", value:"Control Panel"},
	{ id: "jasper", icon: "mdi mdi-file", value:"Informes"}	
];

export let toolbar_data_menu  = {
	id:"mainToolbar",
	view: "toolbar", 
	padding:3, 
	elements: [
		{ view: "icon", icon: "mdi mdi-menu"},
		{ view: "label", label: "Open Its"},
		{ id:"bell", view: "icon", icon: "mdi mdi-bell"},
		{ view: "icon", icon: "webix_icon wxi-pencil" }
	]
}

