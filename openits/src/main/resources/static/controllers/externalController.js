import { util } from "../lib/util.js"
import I18n from "../managers/i18n.js";
import EntitiesManager from "../managers/entitiesManager.js";
import WindowManager from "../managers/windowManager.js";
import ServiceManager from "../managers/serviceManager.js";



window.onload = function() {
	
	var userData = util.user.auth();

	if (userData) {
		
		initialize();
	}
	else {
		
		window.location = "#";
	}
	
	function initialize() {
		
		document.getElementById("pagestyle").setAttribute("href", "./lib/webix/skins/material.css");
		
		const queryString = window.location.search;
		console.log(queryString);
		const urlParams = new URLSearchParams(queryString);
				
		const height = urlParams.get('height') ? parseInt(urlParams.get('height')) : 1024;
		const width = urlParams.get('width') ? parseInt(urlParams.get('width')) : 768;
	
		let main_screen = {
			id: "main-view",
			type: "space",
			rows: [
				{},
				{ 
					height: height,
					cols: [
						{},
						{
							id: "loading-main",
							width: width,
							rows: [util.ui.getViewDrawLoading(true)] 
						},
						{ 
							id: "view-content",
							width: width,
							hidden: true,
							rows: [{ id: "view-module"}]
							
						},
						{}
					] 
				},
				{}
			],
			borderless: 0
		};
		
		webix.ui(main_screen);
		
		window.i18n = new I18n(userData.locale, onLoadI18n);
		
		window.isExternal = true;
		
		function onLoadI18n() {
		
			window.em = new EntitiesManager();
			window.wm = new WindowManager();
			util.module.checkSystemPermissions(window.em.getAll(EntityType.MODULE), userData);
			
			window.sm = new ServiceManager(onLoadServiceManager);
			
			function onLoadServiceManager() {
			
				const moduleId = parseInt(urlParams.get('module'));
				const moduleActionId = urlParams.get('moduleAction') ? parseInt(urlParams.get('moduleAction')) : undefined;
				
				let args = urlParams.get('args') ? JSON.parse(urlParams.get('args')) : {};
				
				args.containerId = "view-content";
				
				let module = window.em.getByField(EntityType.MODULE, "id", moduleId);
				let moduleAction = moduleActionId ? { id: moduleActionId } : undefined;
				
				if (module) {
			
					$$("loading-main").hide();
					$$("view-content").show();
					
					util.module.open(module, moduleAction, VIEW_TYPE_EXTERNAL, drawView, args);
					
					if (module.windowValues) {
						
						let windowParamValue = module.windowValues.find(e => e.windowParam.id === WindowParam.LABEL);
						
						if (windowParamValue) {
						
							document.title = i18n.t(windowParamValue.value);	
						}
					}
					
					
					function drawView(view) {
			
						webix.ui(view, $$("view-module"));
					}
				}	
			}	
		}	
	}
}

