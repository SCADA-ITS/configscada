const MAIN_TOOLBAR_ID = "mainToolbar";

export default class MainToolbar {

	constructor() {

	}

	getView() {

		let view = {
			id: MAIN_TOOLBAR_ID,
			view: "toolbar",
			height: 46,
			css: "head-color main-toolbar",
			//padding: 3,
			elements: [
				{
					view: "label",
					label: "<img class='logo-project' src='./img/logo-project.svg'>",
					width: 175
				},
				//this.mainMenu.getView(),
				//{ view: "template", css: "head-color main-toolbar", role: "placeholder", borderless: 1, minWidth: 500 }
				{}			
			]
		};

		return view;
	}
}
