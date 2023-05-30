import { util } from "../lib/util.js"
import MainTab from "../component/general/mainTab.js";
import MainToolbar from "../component/general/mainToolbar.js";
import MenuToolbar from "../component/general/menuToolbar.js";
import VersionTaskbar from "../component/general/versionTaskbar.js";
import I18n from "../managers/i18n.js";
import EntitiesManager from "../managers/entitiesManager.js";
import WindowManager from "../managers/windowManager.js";
import ServiceManager from "../managers/serviceManager.js";
import SplashScreen from "../component/general/splashScreen.js";

util.user.auth();

var userData = util.user.getUserData();

if (!userData) {
	
	window.location = "./login.html";
}

window.sessionUID = webix.uid();

document.getElementById("pagestyle").setAttribute("href", "./lib/webix/skins/material.css");

window.i18n = new I18n(userData.locale, initialize);

window.splashScreen = new SplashScreen(null);

window.onload = function() {

	$.ajaxSetup({
		statusCode: {
			401: function() {
				window.location = "./login.html";
			}
		}
	});

//	webix.delay(function() {
//
//		initialize();
//	}, null, null, 1000);
}

function initialize() {
	
	try {
		
		document.title = APP_TITLE ? APP_TITLE : "OpenITS";	
	}
	catch {
		
	}

	window.alarmControlPanel = undefined;
	window.notificationControlPanel = undefined;
	window.userControlPanel = undefined;
	window.serverTime = undefined;

	window.em = new EntitiesManager();
	window.wm = new WindowManager();

	window.stompClient = undefined;

	util.module.checkSystemPermissions(window.em.getAll(EntityType.MODULE), userData);
	
	if (userData.theme) {

		util.ui.changeColorTheme(userData.theme);
	}	
	
	var mainTab = new MainTab();
	var mainToolbar = new MainToolbar();
	var menuToolbar = new MenuToolbar(onLoad);
	var versionTaskbar = new VersionTaskbar();
	
	function onLoad() {
		
		let cssLightMode = false;
		let cssStyleHeader = "head-color main-toolbar";
		let cssStyleMainScreen = "";

		try {

			window.lightMode = (APP_LIGTH_MODE) ? true : false;
			cssLightMode = (window.lightMode) ? true : false;
			cssStyleHeader = (cssLightMode) ? "light-mode" : "head-color main-toolbar";
			cssStyleMainScreen = (cssLightMode) ? "light-main-screen" : "";

		} catch {

		}
		
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
				versionTaskbar.getView()
//				{
//					view: "label",
//					label: `<img class='logo-revenga' src='./img/open_its_full.png'>`,
//					align: "right",
//					width: 150
//				},
//				{
//					view: "label",
//					label: `<span class='open-its-version'>version 0.01</span>`,
//					align: "right",
//					width: 85
//				}
			],
			borderless: 0
		};
		
		let main_screen = {
			type: "space",
			id: "mainScreen",
			css: cssStyleMainScreen,
			cols: [
				{ width: 0.1 },
				{
					id: "mainscreen",
					rows: [
						{
							height: 80,
							css: cssStyleHeader,
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
									width: 600,
									rows: [
										{},
										{
											id: MAIN_TOOLBAR_ICONS_ID,
											view: "toolbar",
											//height: 42,
											css: cssStyleHeader,
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
						{
							view: "template",
							id: MAIN_BACKGROUND_IMAGE_ID,
							template: function(data) {
								
								let logo = "<img src='img/open_its_full.png' align='center'>";
				
								return `<div class='welcome-screen-inner'>${logo}</div>`;
							}  
						},	
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

	
}