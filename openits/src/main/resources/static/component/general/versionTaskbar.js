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
						
						onAfterRender: function() {
							
							let labelVersion = this;
							
							webix.ajax(appConfig.restUrl + "client/info").then(function(response) {
								
								if (response) {
									
									self.clientInfo = response.json();
									
									labelVersion.setValue(`<span class='open-its-version'>${self.clientInfo.version}</span>`);
									labelVersion.adjust();
								}
							});
						},
						onItemClick: function() {
							
							showAboutDialog();
						}
					}
				}
			]
		}
		
		function showAboutDialog() {
			
			let viewAbout = {
				rows: [
					{
						view: "template",
						css: "splash-screen-logo-inner",
						template: function(data) {
							
							let logo = "<img src='img/open_its_full.png' align='center'>";
			
							return `<div class="splash-screen-logo-inner">${logo}</div>`;
						}		
					},
					{ height: 50 },
					{
						cols: [
							{ width: ABOUT_DIALOG_WIDTH * 0.2 },
							{
								rows: function() {
									
									let rows = [];
									
									if (self.clientInfo.version) {
										
										rows.push({ view: "text", label: i18n.t("LBL_VERSION"), readonly: true, labelWidth: 200, value: self.clientInfo.version});
									}
									
									if (self.clientInfo.gitInfo) {
									
										rows.push({ view: "text", label: i18n.t("LBL_GIT_COMMIT"), readonly: true, labelWidth: 200, value: self.clientInfo.gitInfo.commit});
										rows.push({ view: "text", label: i18n.t("LBL_GIT_AUTHOR"), readonly: true, labelWidth: 200, value: self.clientInfo.gitInfo.author});
										rows.push({ view: "text", label: i18n.t("LBL_GIT_DATE"), readonly: true, labelWidth: 200, value: self.clientInfo.gitInfo.date});
										rows.push({ view: "textarea", label: i18n.t("LBL_GIT_COMMIT_MESSAGE"), readonly: true, labelWidth: 200, value: self.clientInfo.gitInfo.message});
									}
									
									if (rows.length > 0) {
										
										rows.push({});
									}
									
									return rows;
								}()
							},
							{ width: ABOUT_DIALOG_WIDTH * 0.2 }
						]
					}
				]
			};
			
			util.ui.loadWindow(i18n.t("LBL_ABOUT"), viewAbout, ABOUT_DIALOG_HEIGHT, ABOUT_DIALOG_WIDTH, true);
		}

		return view;
	}
}
