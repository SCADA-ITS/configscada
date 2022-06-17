import { util } from "../../lib/util.js";

export default class MainMenu {

	constructor(onLoadMainMenu, menuToolbar) {

		this.onLoadMainMenu = onLoadMainMenu;
		this.menuToolbar = menuToolbar;
		this.menus = undefined;
		
		this.loadMenus();
	}
	
	loadMenus() {
		
		let self = this;
		
		webix.ajax().get(appConfig.restUrl + "menus").then(
			function(response){
		
			if (response) {
			
				self.menus = response.json();
				
				if (self.onLoadMainMenu && self.menuToolbar) {
					
					self.onLoadMainMenu(self.menuToolbar);
				}
			}
		});
	}

	getView() {

		let view;
		
		view = {
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

						let module = window.em.getByField(EntityType.MODULE, "id", this.getMenuItem(id).moduleId);

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

		let self = this;
		let menuResponse = [];

		if (Array.isArray(self.menus)) {

			self.menus.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));

			self.menus.forEach(menu => {

				var mainMenu = {};

				mainMenu.id = menu.id;
				mainMenu.value = i18n.t(menu.label);
				mainMenu.config = { css: "menu-custom" };

				var submenus = menu.menuItems;

				if (Array.isArray(submenus)) {

					submenus.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));

					mainMenu.submenu = [];

					submenus.forEach(submenu => {

						var mainSubmenu = {};
						
						if (submenu.module) {
							
							if (util.module.isAllowedModule(window.em.getAll(EntityType.MODULE), submenu.module.id)) {
								
								mainSubmenu.id = mainMenu.id + "." + submenu.id;
								mainSubmenu.value = i18n.t(submenu.label);
								mainSubmenu.moduleId = submenu.module.id;
								mainSubmenu.icon = submenu.icon;	
								mainMenu.submenu.push(mainSubmenu);
							}
							
						} else {
							
							mainSubmenu.$template = "Separator";
							mainMenu.submenu.push(mainSubmenu);
						}
					});
				}

				if (mainMenu.submenu.length > 0) {

					menuResponse.push(mainMenu);
				}

			});
		}

		return menuResponse;
	}
}
