import { util } from "../lib/util.js"
import MainTab from "./mainTab.js";
import MainToolbar from "./mainToolbar.js";
import MenuToolbar from "./menuToolbar.js";
import I18n from "../repositories/i18n.js";
import EntitiesManager from "../repositories/entitiesManager.js";

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
window.i18n = new I18n(`${userData.locale.id}`);
window.em = new EntitiesManager();
window.stompClient = undefined;


var mainTab = new MainTab();
var mainToolbar = new MainToolbar();
var menuToolbar = new MenuToolbar();

window.notificationCenter = undefined;

let main_footer = {
	view: "toolbar",
	css: "main-footer",
	id: "mainFooter",
	height: 40,
	elements: [
		{},
		{
			view: "label",
			label: `<img class='logo-revenga' src='../img/open_its_full.png'>`,
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

//let main_screen = {
//	type: "space",
//	rows: [
//		mainToolbar.getView(),
//		//{ view: "template", role: "placeholder", borderless: 0, height: 1 },
//		menuToolbar.getView(),
//		{
//			cols: [
//				{ view: "template", role: "placeholder", borderless: 1, width: 10 },
//				{
//					rows: [
//						{ view: "template", role: "placeholder", borderless: 1, "height": 10 },
//						mainTab.getView(),
//						{ view: "template", role: "placeholder", borderless: 1, "height": 10 },
//					],
//					borderless: 1
//				},
//				{ view: "template", template: "Content", role: "placeholder", borderless: 1, width: 10 }
//			],
//			borderless: 0
//		},
//		//{ view: "template", template: "Footer ", role: "placeholder", borderless: 0, height: 40 },
//		main_footer
//	]
//};

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
				{ view: "template", role: "placeholder", borderless: 1, "height": 10 },
				main_footer
			],
			borderless: 1,
		},
		//{ view: "template", template: "Content", role: "placeholder", borderless: 1, width: 10 }
		{ width: 0.1 }
	],
	borderless: 0

};

var mainScreen = webix.ui(main_screen);
mainTab.attachOnBeforeTabClose();
mainToolbar.attachOnClickEvents();
