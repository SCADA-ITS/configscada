import { util } from "../lib/util.js"
import I18n from "../managers/i18n.js";
import EntitiesManager from "../managers/entitiesManager.js";
import WindowManager from "../managers/windowManager.js";
import ServiceManager from "../managers/serviceManager.js";

var userData = util.user.auth();

if (!userData) {
	window.location = "#";
}

document.getElementById("pagestyle").setAttribute("href", "./lib/webix/skins/material.css");

window.i18n = new I18n(userData.locale);

window.onload = function() {

	webix.delay(function() {

		initialize();
	}, null, null, 1000);
}

function initialize() {

	let main_screen = {};
	window.em = new EntitiesManager();
	window.wm = new WindowManager();
	util.module.checkSystemPermissions(window.em.getAll(EntityType.MODULE), userData);
	
	window.sm = new ServiceManager(onLoad);
	
	main_screen = {
		
		id: "main-view",
		cols: [util.ui.getViewDrawLoading()]
	}
	
	main_screen = {
		id: "main-view",
		type: "space",
		rows: [
			{
				id: "loading-main",
				cols: util.ui.getViewDrawLoading() 
			},
			{ 
				id: "view-main",
				hidden: true 
			}
		],
		borderless: 0
	};
	
	webix.ui(main_screen);
	
	function onLoad() {
	
		const queryString = window.location.search;
		console.log(queryString);
		const urlParams = new URLSearchParams(queryString);
		
		const moduleId = parseInt(urlParams.get('module'));
		const moduleActionId = urlParams.get('moduleAction') ? parseInt(urlParams.get('moduleAction')) : undefined;
		const args = urlParams.get('args') ? JSON.parse(urlParams.get('args')) : undefined;
		
		let module = window.em.getByField(EntityType.MODULE, "id", moduleId);
		let moduleAction = moduleActionId ? { id: moduleActionId } : undefined; 
		
		if (module) {
	
			$$("loading-main").hide();
			$$("view-main").show();
			
			util.module.open(module, moduleAction, VIEW_TYPE_CUSTOM, drawView, args);
			
			function drawView(view) {
	
				webix.ui(view, $$("view-main"));
			}
		}	
	}
	
}