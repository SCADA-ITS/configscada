import MainMenu from "./mainMenu.js";

const MENU_TOOLBAR_ID = "menuToolbar";

export default class MenuToolbar {

	constructor() {

		this.mainMenu = new MainMenu();
	}

	getView() {

		let view = {
			id: MENU_TOOLBAR_ID,
			css: "head-color menu-toolbar",
			view: "toolbar",
			height: 32,
			elements: [
				this.mainMenu.getView(),
			]
		};

		return view;
	}
}
