//import ModuleLoader from "./moduleLoader.js";
import {util} from "../lib/util.js";

export default class MainMenu {

	constructor() {
		
	}
	
	getView() {
		
		let view = {
			id: "mainMenu",
			view: "menu",
			data: this.getMainMenuData(),
			css: "menu-custom",
			openAction: "click",
			type: {
				subsign: true
			},
			template: (obj) => {
		
				if (!obj.default) {
					return obj.value;
				}
				else {
					return `<span class = 'webix_icon ${obj.icon}'></span> <span style='font-weight: 500;'>${obj.value}</span>`;
				}
		
			},
			submenuConfig: {
				width: 300,
				template: (obj) => {
		
					if (!obj.icon) {
						return `<span class='submenu-text'>${obj.value}</span>`;
					}
					else {
						return `<div><img class='submenu-icon' src='${obj.icon}'/><span class='submenu-text'>${obj.value}</span></div>`;
					}
		
				}
			},
			on: {
		
				onMenuItemClick: function(id) {
		
					if (this.getMenuItem(id).moduleId) {
						
						let module = window.em.getByField(EntityType.MODULE.id, "id", this.getMenuItem(id).moduleId);
						
						if (module) {
						
							util.module.open(module, null, null, null, null);	
						}
					}
				}
			}
		};
		
		return view;
	}
	
	getMainMenuData() {

		let menuResponse = [];
		let response = webix.ajax().sync().get(appConfig.restUrl + "menus");
	
		if (response.responseText) {
	
			let menus = JSON.parse(response.responseText);
	
			if (Array.isArray(menus)) {

				// TODO: Change id for position in table ui.menu_groups				
				menus.sort((a, b) => (a.id > b.id) ? 1 : ((b.id > a.id) ? -1 : 0));
	
				for (var menuIdx = 0; menuIdx < menus.length; menuIdx++) {
	
					var mainMenu = {};
	
					mainMenu.id = menus[menuIdx].id;
					mainMenu.value = i18n.t(menus[menuIdx].label);
					mainMenu.config = { css: "menu-custom" };
	
					var submenu = menus[menuIdx].menuItems;
	
					if (Array.isArray(submenu)) {
						
						submenu.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));
	
						mainMenu.submenu = [];
	
						for (var submenuIdx = 0; submenuIdx < submenu.length; submenuIdx++) {
	
							var mainSubmenu = {};
	
							mainSubmenu.id = mainMenu.id + "." + submenu[submenuIdx].id;
							mainSubmenu.value = i18n.t(submenu[submenuIdx].label);
							mainSubmenu.moduleId = submenu[submenuIdx].module.id;
							mainSubmenu.icon = submenu[submenuIdx].icon;
	
							mainMenu.submenu.push(mainSubmenu);
						}
					}
	
					menuResponse.push(mainMenu);
				}
			}
		}
	
		return menuResponse;
	}
}
