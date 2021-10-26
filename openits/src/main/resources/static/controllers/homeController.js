import { util } from "../lib/util.js"
import MainTab from "../component/general/mainTab.js";
import MainToolbar from "../component/general/mainToolbar.js";
import MenuToolbar from "../component/general/menuToolbar.js";
import I18n from "../managers/i18n.js";
import EntitiesManager from "../managers/entitiesManager.js";
import WindowManager from "../managers/windowManager.js";
import ServiceManager from "../managers/serviceManager.js";
import SplashScreen from "../component/general/splashScreen.js";

var userData = util.user.auth();

if (!userData) {
	window.location = "./login.html";
}
document.getElementById("pagestyle").setAttribute("href", "./lib/webix/skins/material.css");

window.i18n = new I18n(userData.locale);

window.splashScreen = new SplashScreen(null);

//window.extenalizeTab = function(tabId, module, moduleAction, args) {
//	
//	util.module.open(module, moduleAction, VIEW_TYPE_WINDOW, null, args);
//	
//	if ($$(MAIN_TAB_ID)) {
//		
//		$$(MAIN_TAB_ID).removeView(tabId.replace("tab", "tab_body"));
//	}
//}

window.onload = function() {

	$.ajaxSetup({
		statusCode: {
			401: function() {
				window.location = "./login.html";
			}
		}
	});

	webix.delay(function() {

		initialize();
	}, null, null, 1000);
}

function initialize() {
	
	document.title = APP_TITLE ? APP_TITLE : "OpenITS";

	window.alarmControlPanel = undefined;
	window.notificationControlPanel = undefined;
	window.userControlPanel = undefined;
	window.serverTime = undefined;

	window.em = new EntitiesManager();
	window.wm = new WindowManager();

	window.stompClient = undefined;

	util.module.checkSystemPermissions(window.em.getAll(EntityType.MODULE), userData);
	
	var mainTab = new MainTab();
	var mainToolbar = new MainToolbar();
	var menuToolbar = new MenuToolbar();

	let main_footer = {
		view: "toolbar",
		css: "main-footer",
		id: "mainFooter",
		height: 40,
		cols: [
			{
				id: TASKBAR_ID
			},
			{},
			{
				view: "label",
				label: `<img class='logo-revenga' src='./img/open_its_full.png'>`,
				align: "right",
				width: 150
			},
			{
				view: "label",
				label: `<span class='open-its-version'>version 0.01</span>`,
				align: "right",
				width: 85
			}
		],
		borderless: 0
	};

	let main_screen = {
		type: "space",

		cols: [
			//{ view: "template", role: "placeholder", borderless: 1, width: 10 },
			{ width: 0.1 },
			{
				rows: [
					{
						height: 80,
						css: "head-color",
						cols: [
							{
								rows: [
									{},
									mainToolbar.getView(),
									menuToolbar.getView(),
									{}
								]
							},
							{
								width: 400,
								rows: [
									{},
									{
										id: MAIN_TOOLBAR_ICONS_ID,
										view: "toolbar",
										//height: 42,
										css: "head-color main-toolbar",
										//padding: 3,
										elements: [{}]
									},									
									{}
								]
							}
						]
					},
					
					//{ view: "template", css: "head-color", role: "placeholder", borderless: 1, "height": 10 },
					mainTab.getView(),
					/*{ view: "template", role: "placeholder", borderless: 1, "height": 10 },*/
					main_footer
				],
				borderless: 1,
			},
			//{ view: "template", template: "Content", role: "placeholder", borderless: 1, width: 10 }
			{ width: 0.1 }
		],
		borderless: 0

	};

	$$("splashScreen").hide();

	webix.ui(main_screen);
	mainTab.attachOnBeforeTabClose();

	window.sm = new ServiceManager();

	autoloadModules();

	function autoloadModules() {

		let modules = window.em.getAll(EntityType.MODULE);

		if (Array.isArray(modules) && modules.length > 0) {
			
			modules.forEach(module => {
				
				if (module.autoload && module.moduleType.id != MODULE_TYPE_SERVICE) {
	
					util.module.open(module);					
				}
			});
		}		
	}
}