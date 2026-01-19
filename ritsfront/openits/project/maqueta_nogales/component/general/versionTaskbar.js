import { util } from "../../lib/util.js";

const VERSION_TASKBAR_ID = "versionTaskbar";
const ABOUT_DIALOG_HEIGHT = 600;
const ABOUT_DIALOG_WIDTH = 800;

export default class VersionTaskbar {

	constructor() {

		this.clientInfo = undefined;
	}

	getView() {

		let self = this;
		let view;
		
		view = {
			cols: [
				
				{
					view: "label",
					label: `<img class='logo-revenga' src='./img/open_its_full.png'>`,
					align: "right",
					width: 150
				},
				{
					id: VERSION_TASKBAR_ID,
					view: "label",
					label: `<span class='open-its-version'>0.0.1-SNAPSHOT</span>`,
					align: "right",
					width: 55,
					on: {
						
						onAfterRender: webix.once(function() {
							
							
							let labelVersion = this;
							
							webix.ajax(appConfig.restUrl + "client/info").then(function(response) {
								
								if (response) {
									
									self.clientInfo = response.json();
									
									labelVersion.setValue(`<span class='open-its-version'>1.0.0</span>`);
									labelVersion.adjust();
								}
							});	
						})
					}
				}
			]
		}

		return view;
	}
}
