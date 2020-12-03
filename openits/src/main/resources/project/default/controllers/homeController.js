import { util } from "../lib/util.js"
import MainTab from "./mainTab.js";
import MainToolbar from "./mainToolbar.js";
import I18n from "../repositories/i18n.js";
import EntitiesManager from "../repositories/entitiesManager.js";

var isLogin = webix.storage.local.get("loginSession");

if (!isLogin) {
	window.location = "./login.html";
}

document.getElementById("pagestyle").setAttribute("href", "./lib/webix/skins/material.css");

var language = webix.storage.local.get("app_user_language");
var country = webix.storage.local.get("app_user_country");
window.i18n = new I18n(`${language}_${country}`);
window.em = new EntitiesManager();
window.stompClient = undefined;


var mainTab = new MainTab();
var mainToolbar = new MainToolbar();

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

let main_screen = {
	type: "space",
	rows: [
		mainToolbar.getView(),
		//{ view: "template", role: "placeholder", borderless: 0, height: 1 },
		{
			cols: [
				{ view: "template", role: "placeholder", borderless: 1, width: 10 },
				{
					rows: [
						{ view: "template", role: "placeholder", borderless: 1, "height": 10 },
						mainTab.getView(),
						{ view: "template", role: "placeholder", borderless: 1, "height": 10 },
					],
					borderless: 1
				},
				{ view: "template", template: "Content", role: "placeholder", borderless: 1, width: 10 }
			],
			borderless: 0
		},
		//{ view: "template", template: "Footer ", role: "placeholder", borderless: 0, height: 40 },
		main_footer
	]
};

var mainScreen = webix.ui(main_screen);
mainTab.attachOnBeforeTabClose();
mainToolbar.attachOnClickEvents();
