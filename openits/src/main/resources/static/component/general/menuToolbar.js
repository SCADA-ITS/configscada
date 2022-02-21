import MainMenu from "./mainMenu.js";

const MENU_TOOLBAR_ID = "menuToolbar";

export default class MenuToolbar {

	constructor(onLoadToolbar) {

		this.onLoadToolbar = onLoadToolbar;
		this.mainMenu = new MainMenu(this.onLoadMainMenu, this);
	}
	
	onLoadMainMenu(menuToolbar) {
		
		if (menuToolbar && menuToolbar.onLoadToolbar) {
			
			menuToolbar.onLoadToolbar();
		}
	}

	getView() {

		let view;
		
		view = {
			id: MENU_TOOLBAR_ID,
			css: "head-color menu-toolbar",
			view: "toolbar",
			height: 32,
			elements: [
				this.mainMenu.getView()
			]
		};

		return view;
	}
}
