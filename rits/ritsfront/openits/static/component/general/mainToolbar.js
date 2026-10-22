const MAIN_TOOLBAR_ID = "mainToolbar";

export default class MainToolbar {

	constructor() {

		// This is intentional
	}

	getView() {

		let view;
		let cssStyleHeader = (window.lightMode) ? "light-mode" : "head-color main-toolbar";

		view = {
			id: MAIN_TOOLBAR_ID,
			view: "toolbar",
			height: 46,
			css: cssStyleHeader,
			elements: [
				{
					view: "label",
					label: "<img class='logo-project' src='./img/logo-project.svg'>"
					//width: 300
				}
				//this.mainMenu.getView(),
				//{ view: "template", css: "head-color main-toolbar", role: "placeholder", borderless: 1, minWidth: 500 }
				//{}			
			]
		};

		return view;
	}
}
