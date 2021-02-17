import { util } from "../lib/util.js"
import MainTab from "./mainTab.js";
import MainToolbar from "./mainToolbar.js";
import MenuToolbar from "./menuToolbar.js";
import I18n from "../managers/i18n.js";
import EntitiesManager from "../managers/entitiesManager.js";
import ServiceManager from "../managers/serviceManager.js";
import SplashScreen from "./splashScreen.js";

const WELCOME_SCREEN_MODULE_ID = 1;

//var isLogin = webix.storage.session.get("loginSession");

//if (!isLogin) {
//	
//}

var userData = util.user.auth();

if (!userData) {
	window.location = "./login.html";
}

document.getElementById("pagestyle").setAttribute("href", "./lib/webix/skins/material.css");

//var language = webix.storage.session.get("app_user_language");
//var country = webix.storage.session.get("app_user_country");
window.i18n = new I18n(userData.locale);
//window.splashScreen = new SplashScreen(initialize);
//window.splashScreen.show();

window.splashScreen = new SplashScreen(null);

window.onload = function() {
	
	//$$("splashScreen").refresh();
	
	$.ajaxSetup({
        statusCode: {
            401: function(){
                    window.location = "./login.html";
                }
        }
    });
	
	webix.delay(function() {

		initialize();
	}, null, null, 1000);
}

function initialize() {
    
	window.alarmControlPanel = undefined;
	window.notificationControlPanel = undefined;
	window.userControlPanel = undefined;
	window.serverTime = undefined;
	
	window.em = new EntitiesManager();
	window.stompClient = undefined;
	
	var mainTab = new MainTab();
	var mainToolbar = new MainToolbar();
	var menuToolbar = new MenuToolbar();
	
	let main_footer = {
		view: "toolbar",
		css: "main-footer",
		id: "mainFooter",
		height: 40,
		elements: [
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
					mainToolbar.getView(),
					menuToolbar.getView(),
					{ view: "template", css: "head-color", role: "placeholder", borderless: 1, "height": 10 },
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
	
	//splashScreen.hide();
	//window.splashScreen.hide();
	
	$$("splashScreen").hide();
	
	webix.ui(main_screen);
	mainTab.attachOnBeforeTabClose();
	
	window.sm = new ServiceManager();
	
	loadWelcomeScreen();
	
	function loadWelcomeScreen() {
	
		let module = window.em.getByField(EntityType.MODULE, "id", WELCOME_SCREEN_MODULE_ID);
								
		if (module) {
		
			util.module.open(module, null, null, null, null);	
		}	
	}
}