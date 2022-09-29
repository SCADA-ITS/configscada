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
		let cssStyleHeader = (window.lightMode) ? "light-mode" : "head-color main-toolbar";
		
		view = {
			id: MENU_TOOLBAR_ID,
			css: cssStyleHeader,
			view: "toolbar",
			height: 32,
			elements: [
				this.mainMenu.getView()
			]
		};

		return view;
	}
}
